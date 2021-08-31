package test;

import cdw.cdwproject.model.product.Product;
import cdw.cdwproject.model.User.User;
import cdw.cdwproject.model.cart.CartItem;
import cdw.cdwproject.repository.CartItemRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
@SpringBootTest
@DataJpaTest
@Rollback(value = false)
@AutoConfigureTestDatabase (replace  = AutoConfigureTestDatabase.Replace.NONE)
public class ShoppingCartTest {
@Autowired
    private CartItemRepository cartItemRepository;
@Autowired
    private TestEntityManager entityManager;
@Test
public void testAddOneCartItem(){
   Product product=  entityManager.find(Product.class,5);
    User user = entityManager.find(User.class,5);

    CartItem newItem = new CartItem();
    newItem.setUser(user);
    newItem.setProduct(product);
newItem.setQuantity(1);

CartItem savedCartItem = cartItemRepository.save(newItem);
assert(savedCartItem.getId() >0);



}
}
