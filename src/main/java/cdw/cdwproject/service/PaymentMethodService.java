package cdw.cdwproject.service;

import cdw.cdwproject.model.order.payment.PaymentMethod;

import java.util.List;

public interface PaymentMethodService {
    public List<PaymentMethod> getPaymentMethods();
    public PaymentMethod getPaymentMethodByID(int id);
}
