package cdw.cdwproject.repository;

import cdw.cdwproject.model.order.OrderItem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface OrderItemRepository extends JpaRepository <OrderItem, Long> {


//    List<OrderItem> getOrderItemsByOrderId(int orderId);
    @Query("SELECT o FROM OrderItem o WHERE o.order.orderId  = ?1")
    List<OrderItem> getOrderItemsByOrderId(int orderId);
}
