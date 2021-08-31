package cdw.cdwproject.service;

import cdw.cdwproject.model.review.Review;
import cdw.cdwproject.repository.ReviewRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ReviewServiceImp implements ReviewService{
    @Autowired
    private ReviewRepository reviewRepository;

    @Override
    public Page<Review> getReviewsByProductID(int productID, Pageable pageable) {
        return reviewRepository.getReviewsByProductId(productID, pageable);
    }

    @Override
    public void save(Review review) {
        reviewRepository.save(review);
    }
}
