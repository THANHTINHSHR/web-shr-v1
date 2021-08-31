package cdw.cdwproject.controller;


import cdw.cdwproject.upload.MyUploadForm;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.List;

@Controller
public class MyFileUploadController {

    @RequestMapping(value = "/upload")
    public String homePage() {

        return "upload/_index";
    }

    // GET: Hiển thị trang form upload
    @RequestMapping(value = "/uploadOneFile", method = RequestMethod.GET)
    public String uploadOneFileHandler(Model model) {

        MyUploadForm myUploadForm = new MyUploadForm();
        model.addAttribute("myUploadForm", myUploadForm);

        return "upload/uploadOneFile";
    }

    // POST: Sử lý Upload
    @RequestMapping(value = "/uploadOneFile", method = RequestMethod.POST)
    public String uploadOneFileHandlerPOST(HttpServletRequest request, //
                                           Model model, //
                                           @ModelAttribute("myUploadForm") MyUploadForm myUploadForm) {

        return this.doUpload(request, model, myUploadForm);

    }

    // GET: Hiển thị trang form upload
    @RequestMapping(value = "/uploadMultiFile", method = RequestMethod.GET)
    public String uploadMultiFileHandler(Model model) {

        MyUploadForm myUploadForm = new MyUploadForm();
        model.addAttribute("myUploadForm", myUploadForm);

        return "upload/uploadMultiFile";
    }

    // POST: Sử lý Upload
    @RequestMapping(value = "/uploadMultiFile", method = RequestMethod.POST)
    public String uploadMultiFileHandlerPOST(HttpServletRequest request, //
                                             Model model, //
                                             @ModelAttribute("myUploadForm") MyUploadForm myUploadForm) {

        return this.doUpload(request, model, myUploadForm);

    }

    private String doUpload(HttpServletRequest request, Model model, //
                            MyUploadForm myUploadForm) {

        String description = myUploadForm.getDescription();
        System.out.println("Description: " + description);

        // Thư mục gốc upload file.

//        String rootRoPath = request.getServletContext().getRealPath("img");
//        String uploadRootPath = request.getServletContext().getRealPath("upload");
//        String uploadRootPath = myUploadForm.getFilePath();
        // nó sẽ trỏ tới không có gì.
        String uploadRootPath = request.getContextPath();
//        uploadRootPath = uploadRootPath + File.separator +"src/main/resources/static/img/" + myUploadForm.getFilePath();

        System.out.println("uploadRootPath=" + uploadRootPath);

        File uploadRootDir = new File(new File (uploadRootPath).getAbsolutePath()+File.separator +"src/main/resources/static/img/"+ myUploadForm.getFilePath());
        // Tạo thư mục gốc upload nếu nó không tồn tại.
        if (!uploadRootDir.exists()) {
            System.out.println(" file not exist");
            uploadRootDir.mkdirs();
        }
        MultipartFile[] fileDatas = myUploadForm.getFileDatas();
        //
        List<File> uploadedFiles = new ArrayList<File>();
        List<String> failedFiles = new ArrayList<String>();

        for (MultipartFile fileData : fileDatas) {

            // Tên file gốc tại Client.
            String name = fileData.getOriginalFilename();
            System.out.println("Client File Name = " + name);

            if (name != null && name.length() > 0) {
                try {
                    // Tạo file tại Server.
//                    File serverFile = new File(uploadRootDir.getAbsolutePath() + File.separator +"src/main/resources/static/img/" + myUploadForm.getFilePath()+ File.separator+  name);
                    File serverFile = new File(uploadRootDir.getAbsolutePath() + File.separator+  name);
//if(!serverFile.exists())serverFile.mkdirs();
                    BufferedOutputStream stream = new BufferedOutputStream(new FileOutputStream(serverFile));
                    stream.write(fileData.getBytes());
                    stream.close();
                    //
                    uploadedFiles.add(serverFile);
                    System.out.println("Write file: " + serverFile);
                } catch (Exception e) {
                    System.out.println("Error Write file: " + name + e);
                    failedFiles.add(name);
                }
            }
        }
        model.addAttribute("description", description);
        model.addAttribute("uploadedFiles", uploadedFiles);
        model.addAttribute("failedFiles", failedFiles);
        return "upload/uploadResult";
    }

}