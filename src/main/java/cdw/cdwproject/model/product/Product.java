package cdw.cdwproject.model.product;

import cdw.cdwproject.model.brand.Brand;
import cdw.cdwproject.model.category.Category;
import cdw.cdwproject.model.review.Review;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

import javax.persistence.*;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Entity
@Table(name = "PRODUCTS")
public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID")

    private int id;
    @Column(name = "NAME")
    private String name;
    @Column(name = "IMAGES_PATH")
    private String imagesPath;
    @Column(name = "PRICE")
    private int price;
    @Column(name = "DESCRIPTION")
    private String description;
    @ManyToOne
    @JoinColumn(name = "BRAND_ID")
    private Brand brand;
    @ManyToOne
    @JoinColumn(name = "CATEGORY_ID")
    private Category category;
    @Column(name = "QUANTITY")
    private int quantity;
    @Column(name = "FIRST_IMAGE")
    private String firstImage;
    @Column(name = "CREATE_AT")
    @Temporal(TemporalType.DATE)
    private Date createDate;
    @OneToMany(mappedBy = "product", cascade = CascadeType.ALL)
    private List<Specification> specifications;
    @OneToMany(mappedBy = "product", cascade = CascadeType.ALL)
    private List<Review> reviews;
    @Column(name = "RATING")
    private int rating;

    public Product() {
    }




    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getImagesPath() {
        return imagesPath;
    }

    public void setImagesPath(String imagesPath) {
        this.imagesPath = imagesPath;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Brand getBrand() {
        return brand;
    }

    public void setBrand(Brand brand) {
        this.brand = brand;
    }

    public Category getCategory() {
        return category;
    }

    public void setCategory(Category category) {
        this.category = category;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public String getFirstImage() {
        return firstImage;
    }

    public void setFirstImage(String firstImage) {
        this.firstImage = firstImage;
    }

    public Date getCreateDate() {
        return createDate;
    }

    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }

    public List<Specification> getSpecifications() {
        return specifications;
    }

    public void setSpecifications(List<Specification> specifications) {
        this.specifications = specifications;
    }


    public List<Review> getReviews() {
        return reviews;
    }

    public void setReviews(List<Review> reviews) {
        this.reviews = reviews;
    }

    public int getRating() {
        return rating;
    }

    public void setRating(int rating) {
        this.rating = rating;
    }


    @Override
    public String toString() {
        return "Product{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", imagesPath='" + imagesPath + '\'' +
                ", price=" + price +
                ", description='" + description + '\'' +
                ", brand=" + brand +
                ", category=" + category +
                ", quantity=" + quantity +
                ", firstImage='" + firstImage + '\'' +
                ", createDate=" + createDate +
                ", specifications=" + specifications +
                ", reviews=" + reviews +
                ", rating=" + rating +
                '}';
    }
}
