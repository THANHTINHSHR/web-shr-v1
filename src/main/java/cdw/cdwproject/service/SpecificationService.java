package cdw.cdwproject.service;

import cdw.cdwproject.model.product.Specification;

import java.util.List;

public interface SpecificationService {
   void  save(Specification specification);

    List<Specification> getSpecificationsByProductID(int pid);

    void remove(Specification spec);
}
