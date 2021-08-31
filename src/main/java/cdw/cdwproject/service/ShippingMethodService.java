package cdw.cdwproject.service;

import cdw.cdwproject.model.order.shipping.ShippingMethod;

import java.util.List;

public interface ShippingMethodService {
    public List<ShippingMethod> getShippingMethods();
    public ShippingMethod getShippingMethodByID(int id);
}
