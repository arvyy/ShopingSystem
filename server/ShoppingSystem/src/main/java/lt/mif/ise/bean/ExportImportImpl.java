package lt.mif.ise.bean;

import lt.mif.ise.domain.Category;
import lt.mif.ise.domain.Product;
import lt.mif.ise.jpa.ProductRepository;
import lt.mif.ise.service.CategoryService;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;

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
                Iterator<Cell> cellsInRow = currentRow.iterator();

                Product product = new Product();

                //set product id
                product.setId(cellsInRow.next().getStringCellValue());

                // set product name
                product.setName(cellsInRow.next().getStringCellValue());

                //set product description
                product.setDescription(cellsInRow.next().getStringCellValue());

                //set product image url
                product.setImageUrl(cellsInRow.next().getStringCellValue());

                //set product category
                Category category = categoryService.getOrCreate(cellsInRow.next().getStringCellValue());
                product.setCategory(category);

                //set product price
                product.setPrice(((int) cellsInRow.next().getNumericCellValue()));
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

            row.createCell(0).setCellValue(product.getId());
            row.createCell(1).setCellValue(product.getName());
            row.createCell(2).setCellValue(product.getDescription());
            row.createCell(3).setCellValue(product.getImageUrl());
            row.createCell(4).setCellValue(product.getCategory().getName());
            row.createCell(5).setCellValue(product.getPrice());
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
