package cdw.cdwproject.model.product;

import javax.persistence.*;

@Entity
@Table(name="SPECIFICATIONS")
public class Specification {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID")
    private int id;
    @ManyToOne
    @JoinColumn(name = "PRODUCT_ID")
    private Product product;
    @Column(name="ORDINAL")
    private int ordinal;
    @Column(name="PARAMETER")
    private String parameter;
    @Column(name="DETAIL")
    private String detail;
    public Specification(){


    }

    public Specification(int id, Product product, int ordinal, String parameter, String detail) {
        this.id = id;
        this.product = product;
        this.ordinal = ordinal;
        this.parameter = parameter;
        this.detail = detail;
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

    public int getOrdinal() {
        return ordinal;
    }

    public void setOrdinal(int ordinal) {
        this.ordinal = ordinal;
    }

    public String getParameter() {
        return parameter;
    }

    public void setParameter(String parameter) {
        this.parameter = parameter;
    }

    public String getDetail() {
        return detail;
    }

    public void setDetail(String detail) {
        this.detail = detail;
    }

    @Override
    public String toString() {
        return "Specification{" +
                "id=" + id +
                ", product=" + product +
                ", ordinal=" + ordinal +
                ", parameter='" + parameter + '\'' +
                ", detail='" + detail + '\'' +
                '}';
    }
}
