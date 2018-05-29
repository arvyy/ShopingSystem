package lt.mif.ise.bean;

import lt.mif.ise.domain.Category;
import lt.mif.ise.domain.Product;
import lt.mif.ise.jpa.ProductRepository;
import lt.mif.ise.service.CategoryService;
import lt.mif.ise.service.ProductService;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;

import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Optional;

public class ExportImportImpl implements ExportImport{

    @Autowired
    ProductRepository productRepository;

    @Autowired
    ProductService productService;

    @Autowired
    CategoryService categoryService;

    private static String[] COLUMNS = {"Product Name", "Title", "Price", "Image", "SKU Code", "Description", "Category", "Properties"};

    @Override
    @Async
    public Iterable<Product> importProducts(InputStream inputStream) {
        List<Product> productList = new ArrayList<>();

        try {
            Workbook workbook = new XSSFWorkbook(inputStream);

            Iterator<Sheet> sheetIterator = workbook.sheetIterator();

            while (sheetIterator.hasNext()) {
                Sheet currentSheet = sheetIterator.next();

                Iterator<Row> rowIterator = currentSheet.rowIterator();
                rowIterator.next();
                while (rowIterator.hasNext()) {
                    Row currentRow = rowIterator.next();
                    Product product = new Product();

                    Iterator<Cell> cellIterator = currentRow.cellIterator();
                    // raed cells
                    Cell cell = cellIterator.next();

                    //read Product Name
                    switch (cell.getCellTypeEnum()) {
                        case NUMERIC:
                            product.setName(String.valueOf(cell.getNumericCellValue()));
                            break;
                        case STRING:
                            product.setName(cell.getStringCellValue());
                            break;
                    }

                    //read Title
                    cell = cellIterator.next();
                    switch (cell.getCellTypeEnum()) {
                        case NUMERIC:
                            product.setTitle(String.valueOf(cell.getNumericCellValue()));
                            break;
                        case STRING:
                            product.setTitle(cell.getStringCellValue());
                            break;
                    }

                    //read price
                    cell = cellIterator.next();
                    switch (cell.getCellTypeEnum()) {
                        case NUMERIC:
                            BigDecimal price = new BigDecimal(cell.getNumericCellValue()).setScale(2, BigDecimal.ROUND_HALF_EVEN);
                            product.setPrice(price);
                            break;
                        case STRING:
                            continue;
                        default:
                            continue;
                    }

                    //read Image
                    cell = cellIterator.next();
                    switch (cell.getCellTypeEnum()) {
                        case NUMERIC:
                            continue;
                        case STRING:
                            product.setImageUrl(cell.getStringCellValue());
                            break;
                    }

                    //read id
                    cell = cellIterator.next();
                    switch (cell.getCellTypeEnum()) {
                        case NUMERIC:
                            if (!productService.isProductIdValid(String.valueOf(cell.getNumericCellValue()))){
                                continue;
                            }
                            product.setProductId(String.valueOf(cell.getNumericCellValue()));
                            break;
                        case STRING:
                            if (!productService.isProductIdValid(cell.getStringCellValue())){
                                continue;
                            }
                            product.setProductId(cell.getStringCellValue());
                            break;
                        default:
                            continue;
                    }

                    //read description
                    cell = cellIterator.next();
                    switch (cell.getCellTypeEnum()) {
                        case NUMERIC:
                            product.setDescription(String.valueOf(cell.getNumericCellValue()));
                            break;
                        case STRING:
                            product.setDescription(cell.getStringCellValue());
                            break;
                    }

                    //read Category
                    Category cat;
                    cell = cellIterator.next();
                    switch (cell.getCellTypeEnum()) {
                        case NUMERIC:
                            cat = categoryService.getOrCreate(String.valueOf(cell.getNumericCellValue()));
                            product.setCategory(cat);
                            break;
                        case STRING:
                            cat = categoryService.getOrCreate(cell.getStringCellValue());
                            product.setCategory(cat);
                            break;
                    }

                    // Delete existing product to avoid duplicates
                    Optional<Product> productToDelete = productRepository.findByProductId(product.getProductId());
                    if (productToDelete.isPresent()) {
                        productRepository.delete(productToDelete.get());
                    }
                    productRepository.save(product);
                    productList.add(product);
                }
            }
            workbook.close();
        } catch (Exception e) {
            throw new RuntimeException(e.getMessage());
        }

        return productList;
    }

    @Override
    public ByteArrayOutputStream exportProducts() {
        Workbook workbook = new XSSFWorkbook();

        Sheet sheet = workbook.createSheet("Products");

        CellStyle cellStyle = workbook.createCellStyle();
        cellStyle.setAlignment(HorizontalAlignment.CENTER);

        Font headerFont = workbook.createFont();
        headerFont.setBold(true);
        headerFont.setColor(IndexedColors.BLACK.getIndex());
        headerFont.setFontHeightInPoints((short)12);
        headerFont.setFontName("Arial");

        CellStyle headerCellStyle = workbook.createCellStyle();
        headerCellStyle.setFont(headerFont);
        headerCellStyle.setAlignment(HorizontalAlignment.CENTER);

        // Row for header
        Row headerRow = sheet.createRow(0);

        // Header
        for (int col = 0; col < COLUMNS.length; col++) {
            Cell cell = headerRow.createCell(col);
            cell.setCellValue(COLUMNS[col]);
            cell.setCellStyle(headerCellStyle);
        }

        int rowIdx = 1;
        for (Product product : productRepository.findAll()) {
            Row row = sheet.createRow(rowIdx++);
            //product name cell
            Cell cell = row.createCell(0);
            cell.setCellType(CellType.STRING);
            cell.setCellValue(product.getName());
            cell.setCellStyle(cellStyle);

            //product title cell
            cell = row.createCell(1);
            cell.setCellType(CellType.STRING);
            cell.setCellValue(product.getTitle());
            cell.setCellStyle(cellStyle);

            //price
            cell = row.createCell(2);
            cell.setCellType(CellType.NUMERIC);
            cell.setCellValue(product.getPrice().doubleValue());
            cell.setCellStyle(cellStyle);

            //image
            cell = row.createCell(3);
            cell.setCellType(CellType.STRING);
            cell.setCellValue(product.getImageUrl());
            cell.setCellStyle(cellStyle);

            //SKU cell
            cell = row.createCell(4);
            cell.setCellType(CellType.STRING);
            cell.setCellValue(product.getProductId());
            cell.setCellStyle(cellStyle);

            //description cell
            cell = row.createCell(5);
            cell.setCellType(CellType.STRING);
            cell.setCellValue(product.getDescription());
            cell.setCellStyle(cellStyle);

            //category
            cell = row.createCell(6);
            cell.setCellType(CellType.STRING);
            cell.setCellValue((product.getCategory() != null) ? product.getCategory().getName() : "");
            cell.setCellStyle(cellStyle);
        }

        for (int i = 0; i < COLUMNS.length; i++){
            sheet.autoSizeColumn(i);
        }

        try {
            ByteArrayOutputStream stream = new ByteArrayOutputStream();
            workbook.write(stream);
            workbook.close();
            return stream;
        } catch (Exception e) {
            throw new RuntimeException(e.getMessage());
        }
    }
}
