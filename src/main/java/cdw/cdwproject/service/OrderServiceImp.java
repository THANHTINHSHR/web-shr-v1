package cdw.cdwproject.service;

import cdw.cdwproject.model.cart.CartItem;
import cdw.cdwproject.model.order.OrderItem;
import cdw.cdwproject.repository.OrderItemRepository;
import cdw.cdwproject.repository.OrderRepository;

import cdw.cdwproject.model.order.Order;
import cdw.cdwproject.specification.OrderSpecification;
import cdw.cdwproject.specification.SearchCriteria;
import cdw.cdwproject.specification.SearchOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class OrderServiceImp implements OrderService {
    @Autowired
    private OrderRepository orderRepository;
    @Autowired
    private OrderItemRepository orderItemRepository;
//    @Autowired
//    private OrderSpecification orderSpecification;

    @Override
    public void saveOrder(Order order) {
        orderRepository.save(order);
    }

    @Override
    public void saveOrderItems(Order order, List<CartItem> cartItems) {
        for (CartItem cartItem : cartItems) {
            OrderItem orderItem = new OrderItem();
            orderItem.setOrder(order);
            orderItem.setProduct(cartItem.getProduct());
            orderItem.setQuantity(cartItem.getQuantity());
            orderItemRepository.save(orderItem);
        }
    }

    @Override
    public Order getOrderByUserIDAndOrderID(int userID, int orderID) {
        return orderRepository.getOrderByUserIdAndOrderId(userID, orderID);
    }

    @Override
    public Page<Order> getOrdersByUserID(int userID, Pageable pageable) {
        return orderRepository.getOrdersByUserId(userID, pageable);
    }

    @Override
    public Page<Order> getOrdersByUserSearch(int userID, String searchS, Pageable pageable) {
        return orderRepository.getOrdersByUserSearch(userID, searchS, pageable);
    }

    @Override
    public List<OrderItem> getOrderItemsByOrderId(int orderId) {
//        return orderItemRepository.getOrderItemsByOrderId(orderId);
    return orderItemRepository.getOrderItemsByOrderId(orderId);
    }

    @Override
    public List<Order> getAllOrder() {
        return orderRepository.findAll();
    }

    @Override
    public Order getOrderByOrderID(int oID) {
        return orderRepository.getById(oID);
    }


}
