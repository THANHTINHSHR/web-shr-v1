package cdw.cdwproject.model.review;

import cdw.cdwproject.model.User.User;
import cdw.cdwproject.model.product.Product;
import com.fasterxml.jackson.annotation.JsonIgnore;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.Date;

@Entity
@Table(name = "REVIEWS")
public class Review {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID")
    private int id;
    @ManyToOne
    @JsonIgnore
    @JoinColumn(name = "USER_ID")
    private User user;
    @ManyToOne
    @JsonIgnore
    @JoinColumn(name = "PRODUCT_ID")
    private Product product;
    @Column(name = "REVIEW")
    private String review;
    @Column(name = "CREATE_TIME")
    @Temporal(TemporalType.DATE)
    private Date createTime;
    @Column(name = "RATING")
    private int rating;
    @Column(name = "IMAGE")
    private String image;
    @Column(name = "BUY_TIME")
    @Temporal(TemporalType.DATE)
    private Date buyTime;

    public Review() {

    }

    public Review(int id, User user, Product product, String review, Date createTime, int rating, String image) {
        this.id = id;
        this.user = user;
        this.product = product;
        this.review = review;
        this.createTime = createTime;
        this.rating = rating;
        this.image = image;
    }

    public Review(User user, Product product, String review, Date createTime, int rating, String image) {
        this.user = user;
        this.product = product;
        this.review = review;
        this.createTime = createTime;
        this.rating = rating;
        this.image = image;
    }

    public Review(User user, Product product, String review, Date createTime, int rating) {
        this.user = user;
        this.product = product;
        this.review = review;
        this.createTime = createTime;
        this.rating = rating;
    }

    public Review(User user, Product product, String review, Date createTime, int rating, String image, Date buyTime) {
        this.user = user;
        this.product = product;
        this.review = review;
        this.createTime = createTime;
        this.rating = rating;
        this.image = image;
        this.buyTime = buyTime;
    }

    public Review(User user, Product product, String review, Date createTime, int rating, Date buyTime) {
        this.user = user;
        this.product = product;
        this.review = review;
        this.createTime = createTime;
        this.rating = rating;
        this.buyTime = buyTime;
    }

    public Date getBuyTime() {
        return buyTime;
    }

    public void setBuyTime(Date buyTime) {
        this.buyTime = buyTime;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }

    public String getReview() {
        return review;
    }

    public void setReview(String review) {
        this.review = review;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public int getRating() {
        return rating;
    }

    public void setRating(int rating) {
        this.rating = rating;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    @Override
    public String toString() {
        return "Review{" +
                "id=" + id +
                ", user=" + user +
                ", product=" + product +
                ", review='" + review + '\'' +
                ", createTime=" + createTime +
                ", rating=" + rating +
                ", image='" + image + '\'' +
                ", buyTime=" + buyTime +
                '}';
    }
}
