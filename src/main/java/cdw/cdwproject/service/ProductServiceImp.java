package cdw.cdwproject.service;

import cdw.cdwproject.model.product.Product;
import cdw.cdwproject.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class ProductServiceImp implements ProductService {
    @Autowired
    private ProductRepository productRepository;

    @Override
    public List<Product> getAllProduct() {
        return productRepository.findAll();
    }

    @Override
    public List<Product> getNewProduct() {
        // get top 10 newest products
        return productRepository.getNewProducts(PageRequest.of(0, 10));

    }

    @Override
    public List<Product> getFeaturedProducts() {
        // get top 10 products have biggest sell number
        return productRepository.getFeaturedProducts(PageRequest.of(0, 10));
    }

    @Override
    public Product getProductByID(int id) {
        return productRepository.findById(id).get();
    }

    @Override
    public Page<Product> getPaginated(int pageNo, int pageSize) {
        Pageable pageable = PageRequest.of(pageNo - 1, pageSize);
        return productRepository.findAll(pageable);
    }

    @Override
    public Page<Product> getProductsByCategoryID(int categoryID, Pageable pageable) {
        return productRepository.getProductsByCategory(categoryID, pageable);
    }



    @Override
    public Page<Product> getProductsByBrandID(int brandID, Pageable pageable) {
     return  productRepository.getProductsByBrand(brandID,pageable);
    }

    @Override
    public Page<Product> getPopularProductsByCategory(int categoryID, Pageable pageable) {
        return productRepository.getPopularProductsByCategory(categoryID, pageable);
    }

    @Override
    public Page<Product> getPopularProductsByBrand(int brandID, Pageable pageable) {
        return productRepository.getPopularProductsByBrand(brandID, pageable);
    }

    @Override
    public Page<Product> getBestSellerProductsByCategory(int categoryID, Pageable pageable) {
        return productRepository.getBestSellerProductsByCategory(categoryID, pageable);
    }

    @Override
    public Page<Product> getBestSellerProductsByBrand(int brandID, Pageable pageable) {
        return productRepository.getBestSellerProductsByBrand(brandID, pageable);
    }

    @Override
    public Page<Product> getNewProductsByCategory(int categoryID, Pageable pageable) {
        return productRepository.getNewProductsByCategory(categoryID, pageable);
    }

    @Override
    public Page<Product> getNewProductsByBrand(int brandID, Pageable pageable) {
        return productRepository.getNewProductsByBrand(brandID, pageable);
    }

    @Override
    public Page<Product> getExpensiveProductsByCategory(int categoryID, Pageable pageable) {
        return productRepository.getExpensiveProductsByCategory(categoryID, pageable);
    }

    @Override
    public Page<Product> getExpensiveProductsByBrand(int brandID, Pageable pageable) {
        return productRepository.getExpensiveProductsByBrand(brandID, pageable);
    }

    @Override
    public Page<Product> getLowPriceProductsByCategory(int categoryID, Pageable pageable) {
        return productRepository.getLowPriceProductsByCategory(categoryID, pageable);
    }

    @Override
    public Page<Product> getLowPriceProductsByBrand(int brandID, Pageable pageable) {
        return productRepository.getLowPriceProductsByBrand(brandID, pageable);
    }

    @Override
    public Page<Product> getProductsByCategoryOrderByRating(int categoryID, Pageable pageable) {
        return productRepository.getProductsByCategoryOrderByRating(categoryID, pageable);
    }

    @Override
    public Page<Product> getProductsByBrandOrderByRating(int brandID, Pageable pageable) {
        return productRepository.getProductsByBrandOrderByRating(brandID, pageable);
    }

    @Override
    public Page<Product> getProductBySearch(String searchText, Pageable pageable) {
        return productRepository.getProductsBySearch(searchText, pageable);
    }

    @Override
    public Page<Product> getProductsBySearchOrderByPopular(String searchText, Pageable pageable) {
        return productRepository.getProductsBySearchOrderByPopular(searchText, pageable);
    }

    @Override
    public Page<Product> getProductsBySearchOrderByBestSeller(String searchText, Pageable pageable) {
        return productRepository.getProductsBySearchOrderByBestSeller(searchText, pageable);
    }

    @Override
    public Page<Product> getProductsBySearchOrderByCreatDate(String searchText, Pageable pageable) {
        return productRepository.getProductsBySearchOrderByCreatDate(searchText, pageable);
    }

    @Override
    public Page<Product> getProductsBySearchOrderByPriceDesc(String searchText, Pageable pageable) {
        return productRepository.getProductsBySearchOrderByPriceDesc(searchText, pageable);
    }

    @Override
    public Page<Product> getProductsBySearchOrderByPriceAsc(String searchText, Pageable pageable) {
        return productRepository.getProductsBySearchOrderByPriceAsc(searchText, pageable);
    }

    @Override
    public Page<Product> getProductsBySearchOrderByRating(String searchText, Pageable pageable) {
        return productRepository.getProductsBySearchOrderByRating(searchText, pageable);
    }

    @Override
    public  Page<Product>  getRelatedProducts(int brandId, int categoryId, Pageable pageable) {
        return productRepository.getRelatedProducts(brandId, categoryId, pageable);
    }

    @Override
    public void save(Product product) {
        productRepository.save(product);
    }

    @Override
    public Product getLastProduct() {
       return productRepository.getLastProduct();
    }




}
