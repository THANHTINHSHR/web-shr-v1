package cdw.cdwproject.specification;

import cdw.cdwproject.model.User.User;
import cdw.cdwproject.model.User.User_;
import cdw.cdwproject.model.order.Order;
import cdw.cdwproject.model.order.Order_;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Component;

import javax.persistence.criteria.*;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
@Component
public class OrderSpecification {
   public  Specification<Order> getOrdersLikeOrderID(String text) {
        try {
            int inputID = Integer.parseInt(text);
        } catch (Exception e) {
            return null;
        }
        if (text == null) return null;

        return new Specification<Order>() {
            @Override
            public Predicate toPredicate(Root<Order> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) {
                return criteriaBuilder.like(root.get(Order_.ORDER_ID), "%" + text + "%");
            }

        };
    }

    public  Specification<Order> getOrdersLikeCreateDate(String text) {
                return (root, query, criteriaBuilder) -> criteriaBuilder.like(root.get(Order_.CREATE_DATE), "%" + text + "%");
    }

    public  Specification<Order> getOrdersLikeGrandTotal(String text) {
                return (root, query, criteriaBuilder) -> criteriaBuilder.like(root.get(Order_.GRAND_TOTAL).as(String.class), "%" + text + "%");
    }

    public  Specification<Order> getOrdersLikeStatus(String text) {
        return (root, query, criteriaBuilder) ->  criteriaBuilder.like(root.get(Order_.STATUS), "%" + text + "%");
    }
    public  Specification<Order> getOrdersByUserID(int userID) {
       return ( root, query, criteriaBuilder) -> criteriaBuilder.equal(root.get(Order_.USER), userID);
    }
    public  Specification<Order> getOrderUserSearch(int userID,String text){
//        return (root, query, criteriaBuilder) -> criteriaBuilder.and(getOrdersByUserID(userID), criteriaBuilder.or(getOrdersLikeOrderID(text), getOrdersLikeCreateDate(text));

        return (root, query, builder) -> {
            Subquery<Order> subquery = query.subquery(Order.class);
            Root<Order> operation = subquery.from(Order.class);
            System.out.println("2 values input equal user id : " + operation.get(Order_.USER).get(User_.ID).as(String.class) +"\t" + userID);
            Predicate pUserId = builder.equal(operation.get(Order_.USER).get(User_.ID).as(String.class), userID);
            Predicate pCreateDate = builder.like(operation.get(Order_.CREATE_DATE).as(String.class), ""+text+"");
            Predicate pGrandTotal = builder.like(operation.get(Order_.GRAND_TOTAL.toString()).as(String.class),"%"+text+"%");
            Predicate pStatus = builder.like(operation.get(Order_.STATUS).as(String.class), "%"+text+"%");
//            Predicate pOr = builder.or(pCreateDate, pGrandTotal, pStatus);
//            Predicate pAnd = builder.and(pUserId, pOr);


            subquery.select(operation).where(pUserId);

            return builder.exists(subquery);
        };

    }
}
