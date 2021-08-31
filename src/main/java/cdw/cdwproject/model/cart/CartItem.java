package cdw.cdwproject.model.cart;


import cdw.cdwproject.model.product.Product;
import cdw.cdwproject.model.User.User;

import javax.persistence.*;

@Entity
@Table(name = "CART_ITEMS")
public class CartItem {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;
	@ManyToOne
	@JoinColumn(name = "USER_ID")
	private User user;
	@ManyToOne
	@JoinColumn (name = "PRODUCT_ID")
	private Product product;

	@Column (name ="QUANTITY")
	private int quantity;

	public CartItem(User user, Product productID, int quantity) {
		this.user = user;
		this.product = productID;
		this.quantity = quantity;
	}

	public CartItem(){

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

	public int getQuantity() {
		return quantity;
	}

	public void setQuantity(int quantity) {
		this.quantity = quantity;
	}

	@Override
	public String toString() {
		return "Cart{" +
				"id=" + id +
				", userID=" + user +
				", productID=" + product +
				", quantity=" + quantity +
				'}';
	}
}


