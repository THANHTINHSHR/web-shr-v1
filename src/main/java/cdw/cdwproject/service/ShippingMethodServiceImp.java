package cdw.cdwproject.service;

import cdw.cdwproject.model.order.shipping.ShippingMethod;
import cdw.cdwproject.repository.ShippingMethodRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
public class ShippingMethodServiceImp implements ShippingMethodService{
@Autowired
private ShippingMethodRepository shippingMethodRepository;
    @Override
    public List<ShippingMethod> getShippingMethods() {
        List<ShippingMethod>  shippingMethods = shippingMethodRepository.getAllShippingMethods();
        for (ShippingMethod sm: shippingMethods
             ) {
            System.out.println(sm.getDescription());
        }
        return shippingMethods;
    }

    @Override
    public ShippingMethod getShippingMethodByID(int id) {
        return shippingMethodRepository.getShippingMethodById(id);
    }
}
