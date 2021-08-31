package cdw.cdwproject.model.product;

import javax.persistence.*;

@Entity
@Table(name = "SOLD_PRODUCTS")
public class SoldProduct {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID")
    private int id;
    @OneToOne
    @JoinColumn(name ="PRODUCT_ID")
    private Product product;
    @Column(name = "SALE_NUMBER")
    private int saleNumber;
    @Column(name = "SELL_NUMBER")
    private int sellNumber;
    public SoldProduct(){

    }

    public SoldProduct(Product product, int saleNumber, int sellNumber) {
        this.product = product;
        this.saleNumber = saleNumber;
        this.sellNumber = sellNumber;
    }

    public SoldProduct(int id, Product product, int saleNumber, int sellNumber) {
        this.id = id;
        this.product = product;
        this.saleNumber = saleNumber;
        this.sellNumber = sellNumber;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }

    public int getSaleNumber() {
        return saleNumber;
    }

    public void setSaleNumber(int saleNumber) {
        this.saleNumber = saleNumber;
    }

    public int getSellNumber() {
        return sellNumber;
    }

    public void setSellNumber(int sellNumber) {
        this.sellNumber = sellNumber;
    }

    @Override
    public String toString() {
        return "SoldProduct{" +
                "id=" + id +
                ", product=" + product +
                ", saleNumber=" + saleNumber +
                ", sellNumber=" + sellNumber +
                '}';
    }
}
