package cdw.cdwproject.model.order.shipping;

import javax.persistence.*;

@Entity
@Table (name="SHIPPING_METHODS")
public class ShippingMethod {
    @Id
    @Column(name = "ID")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @Column(name="NAME")
    private String name;
    @Column(name="DESCRIPTION")
    private String description;
    @Column(name ="SHIPPING_COST")
    private int shippingCost;
    @Column(name="EARLIEST_DELIVERY_DAY")
    private int earlyDay;
    @Column(name="LATEST_DELIVERY_DAY")
    private int lateDay;
    @Column(name = "IMAGE")
    private String image;

    public ShippingMethod(){

    }

    public ShippingMethod(int id, String name, String description, int shippingCost, int earlyDay, int lateDay, String image) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.shippingCost = shippingCost;
        this.earlyDay = earlyDay;
        this.lateDay = lateDay;
        this.image = image;
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

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getShippingCost() {
        return shippingCost;
    }

    public void setShippingCost(int shippingCost) {
        this.shippingCost = shippingCost;
    }

    public int getEarlyDay() {
        return earlyDay;
    }

    public void setEarlyDay(int earlyDay) {
        this.earlyDay = earlyDay;
    }

    public int getLateDay() {
        return lateDay;
    }

    public void setLateDay(int lateDay) {
        this.lateDay = lateDay;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }
}
