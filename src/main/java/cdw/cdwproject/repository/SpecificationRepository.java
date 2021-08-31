package cdw.cdwproject.repository;

import cdw.cdwproject.model.product.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SpecificationRepository extends JpaRepository<Specification, Integer> {

@Query("SELECT s FROM Specification  s WHERE s.product.id = ?1")
    List<Specification> getSpecificationsByProductID(int pid);
}
