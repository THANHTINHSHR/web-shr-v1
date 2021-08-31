package cdw.cdwproject.model.review;

import org.springframework.web.multipart.MultipartFile;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class UploadReviewForm {
    private int pid;

    private MultipartFile addImage;

    private int ratingStar;
    private String reviewText;
    private Date ocd;

    public int getPid() {
        return pid;
    }

    public void setPid(int pid) {
        this.pid = pid;
    }

    public MultipartFile getAddImage() {
        return addImage;
    }

    public void setAddImage(MultipartFile addImage) {
        this.addImage = addImage;
    }

    public int getRatingStar() {
        return ratingStar;
    }

    public void setRatingStar(int ratingStar) {
        this.ratingStar = ratingStar;
    }

    public String getReviewText() {
        return reviewText;
    }

    public void setReviewText(String reviewText) {
        this.reviewText = reviewText;
    }

    public Date getOcd() {
        return ocd;
    }

    public void setOcd(String  ocd) throws ParseException {
        System.out.println("set ocd :" + ocd);
        this.ocd =  new SimpleDateFormat("yyyy-MM-dd").parse(ocd);
    }
}
