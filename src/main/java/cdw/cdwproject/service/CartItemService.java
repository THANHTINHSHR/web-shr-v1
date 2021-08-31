package cdw.cdwproject.service;

import cdw.cdwproject.model.User.User;
import cdw.cdwproject.model.cart.CartItem;

import java.util.List;

public interface CartItemService {
    public List<CartItem> findCartsByUser(User user);
    public CartItem finCartItemByID(int id);
    public  int addProduct(int productID, int quantity, User user);
    public void removeProduct(int productID, User user);
    public float updateQuantity(int productID, int newQuantity, User user); // return subTotal
    public float getCartItemTotal(List<CartItem> cartItems);
    public void deteteCartItemByProductIdAndUserId(int productID, int  userID);
    public void deleteCartItemsByUserId(int userID);
    public void saveCartItems(List<CartItem> cartItems);
}
