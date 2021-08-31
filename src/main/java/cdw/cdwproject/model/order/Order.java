package cdw.cdwproject.model.order;

import cdw.cdwproject.model.User.User;
import cdw.cdwproject.model.order.payment.PaymentMethod;
import cdw.cdwproject.model.order.shipping.ShippingMethod;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "ORDERS")
public class Order {
    @Id
    @Column(name = "ORDER_ID")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int orderId;
    @ManyToOne
    @JoinColumn(name = "USER_ID")
    private User user;

    @Column(name = "RECIPIENT")
    private String recipient;
    @Column(name = "EMAIL")

    private String email;
    @Column(name = "PHONE")
    private String phone;
    @Column(name = "SHIPPING_ADDRESS")
    private String shippingAddress;
    @Column(name = "NOTE")
    private String note;
    @Column(name = "GRAND_TOTAL")
    private long grandTotal;
    @ManyToOne
    @JoinColumn(name = "SHIPPING_METHOD_ID")
    private ShippingMethod shippingMethod;
    @ManyToOne
    @JoinColumn(name = "PAYMENT_METHOD_ID")
    private PaymentMethod paymentMethod;
    @Column(name = "EARLIEST_DELIVERY_TIME")
    @Temporal(TemporalType.DATE)
    private Date earlyDeliveryTime;
    @Column(name = "LATEST_DELIVERY_TIME")
    @Temporal(TemporalType.DATE)
    private Date lateDeliveryTime;
    @Column(name = "SUCCESSFUL_DELIVERY_TIME")
    @Temporal(TemporalType.DATE)
    private Date successDeliveryTime;
    @Column(name = "STATUS")
    @Enumerated(EnumType.STRING)
    private OrderStatus status;
    @Column(name = "CREATE_AT")
    @Temporal(TemporalType.DATE)
    private Date createDate;
    @Column(name = "UPDATE_AT")
    @Temporal(TemporalType.DATE)
    private Date updateTime;


    public Order() {
        // TODO Auto-generated constructor stub
    }

    public Order(User user, String recipient, String email, String phone, String shippingAddress, String note, long grandTotal, ShippingMethod shippingMethod, PaymentMethod paymentMethod, Date earlyDeliveryTime, Date lateDeliveryTime, Date successDeliveryTime, OrderStatus status, Date createDate, Date updateTime) {
        this.user = user;
        this.recipient = recipient;
        this.email = email;
        this.phone = phone;
        this.shippingAddress = shippingAddress;
        this.note = note;
        this.grandTotal = grandTotal;
        this.shippingMethod = shippingMethod;
        this.paymentMethod = paymentMethod;
        this.earlyDeliveryTime = earlyDeliveryTime;
        this.lateDeliveryTime = lateDeliveryTime;
        this.successDeliveryTime = successDeliveryTime;
        this.status = status;
        this.createDate = createDate;
        this.updateTime = updateTime;
    }

    public int getOrderId() {
        return orderId;
    }

    public void setOrderId(int orderId) {
        this.orderId = orderId;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public String getRecipient() {
        return recipient;
    }

    public void setRecipient(String recipient) {
        this.recipient = recipient;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getShippingAddress() {
        return shippingAddress;
    }

    public void setShippingAddress(String shippingAddress) {
        this.shippingAddress = shippingAddress;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }

    public long getGrandTotal() {
        return grandTotal;
    }

    public void setGrandTotal(long grandTotal) {
        this.grandTotal = grandTotal;
    }

    public ShippingMethod getShippingMethod() {
        return shippingMethod;
    }

    public void setShippingMethod(ShippingMethod shippingMethod) {
        this.shippingMethod = shippingMethod;
    }

    public PaymentMethod getPaymentMethod() {
        return paymentMethod;
    }

    public void setPaymentMethod(PaymentMethod paymentMethod) {
        this.paymentMethod = paymentMethod;
    }

    public OrderStatus getStatus() {
        return status;
    }

    public void setStatus(OrderStatus status) {
        this.status = status;
    }

    public Date getCreateDate() {
        return createDate;
    }

    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }

    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

    public Date getEarlyDeliveryTime() {
        return earlyDeliveryTime;
    }

    public void setEarlyDeliveryTime(Date earlyDeliveryTime) {
        this.earlyDeliveryTime = earlyDeliveryTime;
    }

    public Date getLateDeliveryTime() {
        return lateDeliveryTime;
    }

    public void setLateDeliveryTime(Date lateDeliveryTime) {
        this.lateDeliveryTime = lateDeliveryTime;
    }

    public Date getSuccessDeliveryTime() {
        return successDeliveryTime;
    }

    public void setSuccessDeliveryTime(Date successDeliveryTime) {
        this.successDeliveryTime = successDeliveryTime;
    }

    @Override
    public String toString() {
        return "Order{" +
                "orderId=" + orderId +
                ", user=" + user +
                ", recipient='" + recipient + '\'' +
                ", email='" + email + '\'' +
                ", phone='" + phone + '\'' +
                ", shippingAddress='" + shippingAddress + '\'' +
                ", note='" + note + '\'' +
                ", grandTotal=" + grandTotal +
                ", shippingMethod='" + shippingMethod + '\'' +
                ", paymentMethod='" + paymentMethod + '\'' +
                ", earlyDeliveryTime=" + earlyDeliveryTime +
                ", lateDeliveryTime=" + lateDeliveryTime +
                ", successDeliveryTime=" + successDeliveryTime +
                ", status=" + status +
                ", createDate=" + createDate +
                ", updateTime=" + updateTime +
                '}';
    }
}
