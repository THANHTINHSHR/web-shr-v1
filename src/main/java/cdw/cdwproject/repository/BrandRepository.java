package cdw.cdwproject.repository;

import cdw.cdwproject.model.brand.Brand;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BrandRepository extends JpaRepository<Brand, Integer> {
    @Query(" SELECT b.id, b.name, COUNT (p.brand) FROM Product p, Brand b WHERE p.brand = b GROUP BY b.id, b.name")
    List<Object[]> getMapBrandAmount();

    Brand getBrandById(int brandID);
}
