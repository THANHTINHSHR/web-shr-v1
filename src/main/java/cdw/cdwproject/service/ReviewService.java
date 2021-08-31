package cdw.cdwproject.service;

import cdw.cdwproject.model.review.Review;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface ReviewService {
    Page<Review> getReviewsByProductID(int productID, Pageable pageable);
    void save(Review review);

}
