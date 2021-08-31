package cdw.cdwproject.repository;

import cdw.cdwproject.model.product.Product;
import cdw.cdwproject.model.User.User;
import cdw.cdwproject.model.cart.CartItem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CartItemRepository extends JpaRepository<CartItem, Integer> {

    List<CartItem> findCartItemsByUser(User user);

    CartItem findCartItemByProductAndUser(Product product, User user);

    @Query("UPDATE CartItem  c set c.quantity = ?1 WHERE c.product.id = ?2 AND c.user.id = ?3")
    @Modifying
    void updateQuantity(int quantity, int productId, int userId);

    @Query("DELETE FROM CartItem c WHERE c.product.id = ?1 AND c.user.id = ?2")
    @Modifying
    void deleteCartItemByProductIdAndUserId(int productId, int userId);

    @Query ("DELETE FROM CartItem  c WHERE c.user.id = ?1")
    @Modifying
    void deleteCartItemsByUserId(int userID);

}
