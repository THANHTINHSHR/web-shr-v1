package cdw.cdwproject.upload;


import org.springframework.web.multipart.MultipartFile;

public class MyUploadForm {

    private String description;
    private String filePath;


    // Upload files.
    private MultipartFile[] fileDatas;

    public String getFilePath() {
        return filePath;
    }

    public void setFilePath(String filePath) {
        this.filePath = filePath;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public MultipartFile[] getFileDatas() {
        return fileDatas;
    }

    public void setFileDatas(MultipartFile[] fileDatas) {
        this.fileDatas = fileDatas;
    }

}