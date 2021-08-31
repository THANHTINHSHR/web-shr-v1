package cdw.cdwproject.controller;

import cdw.cdwproject.model.User.MyUserDetails;
import cdw.cdwproject.model.User.User;
import cdw.cdwproject.model.product.Product;
import cdw.cdwproject.model.review.Review;
import cdw.cdwproject.model.review.UploadReviewForm;
import cdw.cdwproject.oauth.CustomOAuth2User;
import cdw.cdwproject.service.ProductServiceImp;
import cdw.cdwproject.service.ReviewServiceImp;
import cdw.cdwproject.service.UserServiceImp;
import cdw.cdwproject.upload.UploadForm;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.query.Param;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.util.Date;

@Controller
public class ReviewController {
    @Autowired
    private ReviewServiceImp reviewServiceImp;
    @Autowired
    private ProductServiceImp productServiceImp;
    @Autowired
    private UserServiceImp userServiceImp;

    @PostMapping("/review/new")
    @ResponseBody
    public void addNewReview(@AuthenticationPrincipal MyUserDetails myUserDetails, @Param(value = "productID") int productID, @Param(value = "rating") int rating, @Param(value = "review") String review, @Param(value = "image") String image,  @AuthenticationPrincipal CustomOAuth2User oAuth2User) {
        Date createTime = new Date();
        User user;
        try {
            user = userServiceImp.getUserByEmail(oAuth2User.getEmail());
        } catch (NullPointerException e) {


            user = myUserDetails.getUser();
        }
        Product product = productServiceImp.getProductByID(productID);
        Review reviewDB = new Review(user, product, review, createTime, rating, image);
        reviewServiceImp.save(reviewDB);

    }

    @PostMapping("/review/new2")
    @ResponseBody
    private void uploadImage(@AuthenticationPrincipal MyUserDetails myUserDetails, HttpServletRequest request, @ModelAttribute UploadReviewForm uploadReviewForm,  @AuthenticationPrincipal CustomOAuth2User oAuth2User) throws IOException {
        int pid = uploadReviewForm.getPid();
        Date ocd = uploadReviewForm.getOcd();
        String review = uploadReviewForm.getReviewText();
        int rating = uploadReviewForm.getRatingStar();
        Date createTime = new Date();
        User user;
        try {
            user = userServiceImp.getUserByEmail(oAuth2User.getEmail());
        } catch (NullPointerException e) {


            user = myUserDetails.getUser();
        }
        Product product = productServiceImp.getProductByID(pid);
        MultipartFile file = uploadReviewForm.getAddImage();
        if(!file.isEmpty()) {


            String uploadRootPath = request.getContextPath();
            // Make sure directory exists! --> create images Path
            File uploadDir = new File(new File(uploadRootPath).getAbsolutePath() + File.separator + "src/main/resources/static/img/Review_Images/product_" + pid);
            String s = uploadDir.getAbsolutePath();
            uploadDir.mkdirs();
            // unique name
            String fileName = new SimpleDateFormat("yyyyMMddHHmmss").format(new Date()) + file.getOriginalFilename();
            String uploadFilePath = s + "/" + fileName;
            byte[] bytes = file.getBytes();
            Path path = Paths.get(uploadFilePath);
            Files.write(path, bytes);
            Review reviewDB = new Review(user, product, review, createTime, rating, fileName, ocd);

            reviewServiceImp.save(reviewDB);
        }
        else {
            Review reviewDB = new Review(user, product, review, createTime, rating, ocd);

            reviewServiceImp.save(reviewDB);
        }

    }


}
