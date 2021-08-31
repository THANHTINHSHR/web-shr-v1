package cdw.cdwproject.service;

import cdw.cdwproject.model.product.Product;
import cdw.cdwproject.model.User.User;
import cdw.cdwproject.model.cart.CartItem;
import cdw.cdwproject.repository.CartItemRepository;
import cdw.cdwproject.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CartItemServiceImp implements CartItemService {
    @Autowired
    private CartItemRepository cartItemRepository;
    @Autowired
    private ProductRepository productRepository;


    @Override
    public List<CartItem> findCartsByUser(User user) {
        return cartItemRepository.findCartItemsByUser(user);
    }

    @Override
    public CartItem finCartItemByID(int id) {
        return cartItemRepository.getById(id);
    }

    @Override
    public int addProduct(int productID, int quantity, User user) {
        Product product = productRepository.getById(productID);
        CartItem cartItem = cartItemRepository.findCartItemByProductAndUser(product, user);
        int addedQuantity = quantity;
        if (cartItem != null) {
            addedQuantity = cartItem.getQuantity() + quantity;
            cartItem.setQuantity(addedQuantity);
        } else {
            cartItem = new CartItem();
            cartItem.setProduct(product);
            cartItem.setQuantity(quantity);
            cartItem.setUser(user);
        }
        cartItemRepository.save(cartItem);
        return addedQuantity;
    }


    @Override
    public void removeProduct(int productID, User user) {
//        cartItemRepository.


    }

    @Override
    public float updateQuantity(int productID, int newQuantity, User user) {
        cartItemRepository.updateQuantity(newQuantity, productID, user.getId());
        Product product = productRepository.getById(productID);
        float subTotal = product.getPrice() * newQuantity;
        return subTotal;
    }

    @Override
    public float getCartItemTotal(List<CartItem> cartItems) {
        float cartTotal = 0;
        for(int i =0; i < cartItems.size() ; i ++){
            cartTotal +=  cartItems.get(i).getQuantity() * cartItems.get(i).getProduct().getPrice();
        }
        return cartTotal;
    }

    @Override
    public void deteteCartItemByProductIdAndUserId(int productID, int userID) {
        cartItemRepository.deleteCartItemByProductIdAndUserId(productID, userID);
    }

    @Override
    public void deleteCartItemsByUserId(int userID) {
        cartItemRepository.deleteCartItemsByUserId(userID);
    }

    @Override
    public void saveCartItems(List<CartItem> cartItems) {
        cartItemRepository.saveAll(cartItems);
    }

}
