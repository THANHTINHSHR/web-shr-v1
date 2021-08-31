package cdw.cdwproject.service;

import cdw.cdwproject.model.brand.Brand;
import cdw.cdwproject.repository.BrandRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class BrandServiceImp implements BrandService {
    @Autowired
    BrandRepository brandRepository;
    @Override
    public List<Brand> getBrandsAmount() {
List<Brand> result = new ArrayList<>();
        List<Object[]> rawResult = brandRepository.getMapBrandAmount();
        for (Object[] obj: rawResult
             ) {
        Brand brand = new Brand();
//            System.out.println((Integer) obj[0] +"\t" + (String) obj[1] +"\t" +(Long) obj[2]);
        brand.setId((Integer) obj[0]);
        brand.setName((String) obj[1]);
        brand.setAmount(((Long) obj[2]).intValue());
        result.add(brand);
        }
        return result;
    }

    @Override
    public Brand getBrandByID(int brandID) {
        return brandRepository.getBrandById(brandID);
    }

    @Override
    public List<Brand> getAllBrand() {
        return brandRepository.findAll();
    }

    @Override
    public void save(Brand brand) {
        brandRepository.save(brand);
    }
}
