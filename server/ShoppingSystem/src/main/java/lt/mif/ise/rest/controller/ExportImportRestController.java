package lt.mif.ise.rest.controller;

import lt.mif.ise.bean.ExportImport;
import lt.mif.ise.domain.Product;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.io.ByteArrayOutputStream;

@RestController
@RequestMapping("api/excel")
public class ExportImportRestController {
    @Autowired
    private ExportImport exportImport;

    @RequestMapping(value = "upload", method = RequestMethod.POST)
    public Iterable<Product> upload(@RequestParam(value = "file")MultipartFile file){
        try {
            return exportImport.importProducts(file.getInputStream());
        } catch(Exception e) {
            throw new RuntimeException(e.getMessage());
        }
    }

    @RequestMapping(value = "export", method = RequestMethod.GET, produces = MediaType.APPLICATION_OCTET_STREAM_VALUE)
    @ResponseBody
    public ResponseEntity<ByteArrayResource> export(HttpServletResponse response) {
        try {
            ByteArrayOutputStream stream = exportImport.exportProducts();
            ByteArrayResource resource = new ByteArrayResource(stream.toByteArray());
            response.setHeader("Content-Disposition", "attachment; filename=export.xlsx");
            return new ResponseEntity<>(resource, HttpStatus.OK);
        } catch (Exception e) {
            throw new RuntimeException(e.getMessage());
        }
    }
}
