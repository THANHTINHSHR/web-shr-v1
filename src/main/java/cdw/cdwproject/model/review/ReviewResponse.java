package cdw.cdwproject.model.review;

import java.util.List;

public class ReviewResponse {
    List<Review> reviews;

    public List<Review> getReviews() {
        return reviews;
    }

    public void setReviews(List<Review> reviews) {
        this.reviews = reviews;
    }

    public ReviewResponse(List<Review> reviews) {
        this.reviews = reviews;
    }
}
