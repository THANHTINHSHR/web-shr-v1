package cdw.cdwproject.service;

import cdw.cdwproject.model.product.Specification;
import cdw.cdwproject.repository.SpecificationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SpecificationServiceImp implements SpecificationService{
    @Autowired
    private SpecificationRepository specificationRepository;

    @Override
    public void save(Specification specification) {
        specificationRepository.save(specification);
    }

    @Override
    public List<Specification> getSpecificationsByProductID(int pid) {
        return specificationRepository.getSpecificationsByProductID(pid);
    }

    @Override
    public void remove(Specification spec) {
        specificationRepository.delete(spec);
    }
}
