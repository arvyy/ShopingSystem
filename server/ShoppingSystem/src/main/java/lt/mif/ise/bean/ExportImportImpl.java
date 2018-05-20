package lt.mif.ise.bean;

import lt.mif.ise.domain.Category;
import lt.mif.ise.domain.Product;
import lt.mif.ise.jpa.ProductRepository;
import lt.mif.ise.service.CategoryService;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.UUID;

public class ExportImportImpl implements ExportImport{

    @Autowired
    ProductRepository productRepository;

    @Autowired
    CategoryService categoryService;

    private static String[] COLUMNS = {"Id", "Name", "Description", "ImageUrl", "Category", "Price"};

    @Override
    @Async
    public Iterable<Product> importProducts(String path) {
        List<Product> productList = new ArrayList<>();

        try {
            FileInputStream excelFile = new FileInputStream(new File(path));
            Workbook workbook = new XSSFWorkbook(excelFile);

            Sheet sheet = workbook.getSheet("Products");

            Iterator<Row> rows = sheet.iterator();
            rows.next(); // skip header row
            while (rows.hasNext()) {
                Row currentRow = rows.next();

                Product product = new Product();
                Iterator<Cell> cellIterator = currentRow.iterator();

                    //read id
                    Cell cell = cellIterator.next();
                    switch (cell.getCellTypeEnum()){
                        case NUMERIC:
                            product.setId(String.valueOf(cell.getNumericCellValue()));
                            break;
                        case STRING:
                            product.setId(cell.getStringCellValue());
                            break;
                    }

                    //read name
                    cell = cellIterator.next();
                    switch (cell.getCellTypeEnum()){
                        case NUMERIC:
                            product.setName(String.valueOf(cell.getNumericCellValue()));
                            break;
                        case STRING:
                            product.setName(cell.getStringCellValue());
                            break;
                    }

                    //read description
                    cell = cellIterator.next();
                    switch (cell.getCellTypeEnum()){
                        case NUMERIC:
                            product.setDescription(String.valueOf(cell.getNumericCellValue()));
                            break;
                        case STRING:
                            product.setDescription(cell.getStringCellValue());
                            break;
                    }

                    //read ImageUrl
                    cell = cellIterator.next();
                    switch (cell.getCellTypeEnum()){
                        case NUMERIC:
                            continue;
                        case STRING:
                            product.setImageUrl(cell.getStringCellValue());
                            break;
                    }

                    //read Category
                    Category cat;
                    cell = cellIterator.next();
                    switch (cell.getCellTypeEnum()){
                        case NUMERIC:
                            cat = categoryService.getOrCreate(String.valueOf(cell.getNumericCellValue()));
                            product.setCategory(cat);
                            break;
                        case STRING:
                            cat = categoryService.getOrCreate(cell.getStringCellValue());
                            product.setCategory(cat);
                            break;
                    }

                    //read price
                    cell = cellIterator.next();
                    switch (cell.getCellTypeEnum()){
                        case NUMERIC:
                            product.setPrice(new BigDecimal(cell.getNumericCellValue()));
                            break;
                        case STRING:
                            continue;
                    }

                    productRepository.save(product);
                    productList.add(product);
            }
            workbook.close();
            excelFile.close();
        } catch (Exception e) {
            System.err.println(e.getMessage());
            return null;
        }

        return productList;
    }

    @Override
    public String exportProducts() {
        Workbook workbook = new XSSFWorkbook();

        CreationHelper creationHelper = workbook.getCreationHelper();

        Sheet sheet = workbook.createSheet("Products");

        Font headerFont = workbook.createFont();
        headerFont.setBold(true);
        headerFont.setColor(IndexedColors.BLUE.getIndex());

        CellStyle headerCellStyle = workbook.createCellStyle();
        headerCellStyle.setFont(headerFont);

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
            //id cell
            Cell cell = row.createCell(0);
            cell.setCellType(CellType.STRING);
            cell.setCellValue(product.getId());

            //product name cell
            cell = row.createCell(1);
            cell.setCellType(CellType.STRING);
            cell.setCellValue(product.getName());

            //description cell
            cell = row.createCell(2);
            cell.setCellType(CellType.STRING);
            cell.setCellValue(product.getDescription());

            //image url
            cell = row.createCell(3);
            cell.setCellType(CellType.STRING);
            cell.setCellValue(product.getImageUrl());

            //category
            cell = row.createCell(4);
            cell.setCellType(CellType.STRING);
            cell.setCellValue(product.getCategory().getName());

            //price
            cell = row.createCell(5);
            cell.setCellType(CellType.NUMERIC);
            cell.setCellValue(product.getPrice().doubleValue());
        }

        try {
            String path = "products_" + UUID.randomUUID().toString() + ".xlsx";
            File excelFile = new File(path);
            FileOutputStream fileOut = new FileOutputStream(excelFile);
            workbook.write(fileOut);
            fileOut.close();
            workbook.close();
            return excelFile.getAbsolutePath();
        } catch (Exception e) {
            System.err.println(e.getMessage());
        }
        return "";
    }
}
