package cdw.cdwproject.repository;

import cdw.cdwproject.model.review.Review;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ReviewRepository extends JpaRepository<Review,Integer> {

//    @Query("SELECT r FROM Review R WHERE R.product.id = ?1")
    Page<Review> getReviewsByProductId(int productID, Pageable pageable);
}
