package cdw.cdwproject.service;

import cdw.cdwproject.model.order.payment.PaymentMethod;
import cdw.cdwproject.repository.PaymentMethodRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
public class PaymentMethodServiceImp implements PaymentMethodService{
    @Autowired
    private PaymentMethodRepository paymentMethodRepository;

    @Override
    public List<PaymentMethod> getPaymentMethods() {
        return paymentMethodRepository.findAll();
    }

    @Override
    public PaymentMethod getPaymentMethodByID(int id) {
        return paymentMethodRepository.getPaymentMethodById(id);
    }
}
