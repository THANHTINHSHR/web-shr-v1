package cdw.cdwproject.repository;

import cdw.cdwproject.model.order.Order;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface OrderRepository extends JpaRepository<Order, Integer>, JpaSpecificationExecutor {
    @Query(value = "SELECT * FROM ORDERS WHERE USER_ID= ?1", nativeQuery = true)
    Page<Order> getOrdersByUserId(int userId, Pageable pageable);


    @Query(value = "SELECT * FROM ORDERS WHERE USER_ID = ?1 AND( CAST(ORDER_ID AS NVARCHAR(100))  LIKE %?2% OR CAST(CREATE_AT AS NVARCHAR(100))  LIKE %?2% OR CAST(GRAND_TOTAL AS NVARCHAR(100))  LIKE %?2% OR CAST(STATUS AS NVARCHAR(100))  LIKE %?2%)", nativeQuery = true)
    Page<Order> getOrdersByUserSearch(int userID, String searchS, Pageable pageable);

    Order getOrderByUserIdAndOrderId(int userId, int orderID);

}
