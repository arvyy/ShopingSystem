package lt.mif.ise.rest.controller;

import lt.mif.ise.bean.ExportImport;
import lt.mif.ise.jpa.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.FileSystemResource;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

@RestController
@RequestMapping("api/excel/")
public class ExportImportRestController {
    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private ExportImport exportImport;

    private void saveFile(InputStream inputStream, String path){
        try {
            OutputStream outputStream = new FileOutputStream(new File(path));
            int read = 0;
            byte[] bytes = new byte[1024];
            while ((read = inputStream.read(bytes)) != -1){
                outputStream.write(bytes, 0, read);
            }

            outputStream.flush();
            outputStream.close();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void importExcel(String path){
        try {
            exportImport.importProducts(path);
        } catch (Exception e) {
            System.err.println(e.getMessage());
        }
    }

    @RequestMapping(value = "upload", method = RequestMethod.POST)
    private String upload(@RequestParam(value = "file")MultipartFile file, HttpServletRequest request){
        try {
            String fileName= file.getOriginalFilename();
            String path = request.getServletContext().getRealPath("") + fileName;
            System.out.println(path);
            saveFile(file.getInputStream(), path);
            importExcel(path);
            return fileName;
        } catch(Exception e) {
            return null;
        }
    }

    @RequestMapping(value = "export", method = RequestMethod.GET, produces = MediaType.APPLICATION_OCTET_STREAM_VALUE)
    @ResponseBody
    private FileSystemResource export() {
        String path = exportImport.exportProducts();
        return new FileSystemResource(path);
    }
}
