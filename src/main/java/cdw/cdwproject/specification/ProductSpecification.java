package cdw.cdwproject.specification;

import cdw.cdwproject.model.order.Order;
import cdw.cdwproject.model.order.OrderItem;
import cdw.cdwproject.model.product.Product;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Component;

import javax.persistence.criteria.Join;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import javax.persistence.criteria.Subquery;

@Component
public class ProductSpecification {
    public Specification<Product> getPopularProductsByBrandID(int brandID ){
        return (root, query, builder) -> {
//            Join

            return null;
        };
    }

}
