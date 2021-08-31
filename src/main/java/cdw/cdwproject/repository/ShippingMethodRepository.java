package cdw.cdwproject.repository;

import cdw.cdwproject.model.order.shipping.ShippingMethod;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
@Repository
public interface ShippingMethodRepository  extends JpaRepository<ShippingMethod, Integer> {

    @Query("SELECT s FROM ShippingMethod  s")
    public List<ShippingMethod> getAllShippingMethods();
    public ShippingMethod getShippingMethodById(int id);
}
