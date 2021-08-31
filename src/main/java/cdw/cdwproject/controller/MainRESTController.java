package cdw.cdwproject.controller;

import cdw.cdwproject.upload.UploadForm;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

@RestController
public class MainRESTController {
    // Linux: /home/{user}/test
    // Windows: C:/Users/{user}/test
//    private static String UPLOAD_DIR = System.getProperty("user.home") + "/test";

    @PostMapping("/rest/uploadMultiFiles")
    public ResponseEntity<?> multiUploadFileModel(@ModelAttribute UploadForm form, HttpServletRequest request) {

        System.out.println("Description:" + form.getDescription());

        String result = null;
        try {

            result = this.saveUploadedFiles(form.getFiles(), request);

        }
        // Here Catch IOException only.
        // Other Exceptions catch by RestGlobalExceptionHandler class.
        catch (IOException e) {
            e.printStackTrace();
            return new ResponseEntity<>("Error: " + e.getMessage(), HttpStatus.BAD_REQUEST);
        }

        return new ResponseEntity<String>("Uploaded to: <br/>" + result, HttpStatus.OK);

    }

    // Save Files
    private String saveUploadedFiles(MultipartFile[] files, HttpServletRequest request) throws IOException {

        String uploadRootPath = request.getContextPath();
//        File uploadRootDir = new File(new File(uploadRootPath).getAbsolutePath() + File.separator + "src/main/resources/static/img/product/");
        // Make sure directory exists!

        File uploadDir = new File(new File(uploadRootPath).getAbsolutePath() + File.separator + "src/main/resources/static/img/Review_Images/product_1");
       String s = uploadDir.getAbsolutePath();
        uploadDir.mkdirs();

        StringBuilder sb = new StringBuilder();

        for (MultipartFile file : files) {

            if (file.isEmpty()) {
                continue;
            }
            String uploadFilePath = s + "/" + file.getOriginalFilename();

            byte[] bytes = file.getBytes();
            Path path = Paths.get(uploadFilePath);
            Files.write(path, bytes);

            sb.append(uploadFilePath).append("<br/>");
        }
        return sb.toString();
    }

}
