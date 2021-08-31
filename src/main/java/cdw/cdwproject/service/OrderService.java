package cdw.cdwproject.service;

import cdw.cdwproject.model.cart.CartItem;
import cdw.cdwproject.model.order.Order;
import cdw.cdwproject.model.order.OrderItem;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;

import java.util.List;

public interface OrderService {
    void saveOrder(Order order);

    void saveOrderItems(Order order, List<CartItem> cartItems);

    Order getOrderByUserIDAndOrderID(int userId, int orderID);
    Page<Order> getOrdersByUserID(int userId, Pageable pageable);
    Page<Order> getOrdersByUserSearch(int userId, String searchS, Pageable pageable);
    List<OrderItem> getOrderItemsByOrderId(int orderId);

    List<Order> getAllOrder();

    Order getOrderByOrderID(int oID);

}
