package cdw.cdwproject.service;

import cdw.cdwproject.model.brand.Brand;

import java.util.List;

public interface BrandService {
   List<Brand> getBrandsAmount();
   Brand getBrandByID(int brandID);

    List<Brand> getAllBrand();
    void save(Brand brand);
}
