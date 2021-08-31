package cdw.cdwproject.repository;

import cdw.cdwproject.model.order.payment.PaymentMethod;
import org.springframework.data.domain.Example;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
@Repository
public interface PaymentMethodRepository extends JpaRepository<PaymentMethod, Integer> {
    public List<PaymentMethod> findAll();
    public PaymentMethod getPaymentMethodById(int id);
}
