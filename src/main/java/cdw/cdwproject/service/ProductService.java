package cdw.cdwproject.service;

import cdw.cdwproject.model.product.Product;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Map;

public interface ProductService {
    List<Product> getAllProduct();

    List<Product> getNewProduct();

    List<Product> getFeaturedProducts();

    Product getProductByID(int id);

    Page<Product> getPaginated(int pageNo, int pageSize);

    Page<Product> getProductsByCategoryID(int categoryID, Pageable pageable);

    Page<Product> getProductsByBrandID(int brandID, Pageable pageable);

    Page<Product> getPopularProductsByCategory(int categoryID, Pageable pageable);

    Page<Product> getPopularProductsByBrand(int brandID, Pageable pageable);

    Page<Product> getBestSellerProductsByCategory(int categoryID, Pageable pageable);

    Page<Product> getBestSellerProductsByBrand(int brandID, Pageable pageable);

    Page<Product> getNewProductsByCategory(int categoryID, Pageable pageable);

    Page<Product> getNewProductsByBrand(int brandID, Pageable pageable);

    Page<Product> getExpensiveProductsByCategory(int categoryID, Pageable pageable);

    Page<Product> getExpensiveProductsByBrand(int brandID, Pageable pageable);

    Page<Product> getLowPriceProductsByCategory(int categoryID, Pageable pageable);

    Page<Product> getLowPriceProductsByBrand(int brandID, Pageable pageable);

    Page<Product> getProductsByCategoryOrderByRating(int categoryID, Pageable pageable);

    Page<Product> getProductsByBrandOrderByRating(int brandID, Pageable pageable);

    Page<Product> getProductBySearch(String searchText, Pageable pageable);

    Page<Product> getProductsBySearchOrderByPopular(String searchText, Pageable pageable);

    Page<Product> getProductsBySearchOrderByBestSeller(String searchText, Pageable pageable);

    Page<Product> getProductsBySearchOrderByCreatDate(String searchText, Pageable pageable);

    Page<Product> getProductsBySearchOrderByPriceDesc(String searchText, Pageable pageable);

    Page<Product> getProductsBySearchOrderByPriceAsc(String searchText, Pageable pageable);

    Page<Product> getProductsBySearchOrderByRating(String searchText, Pageable pageable);


    Page<Product> getRelatedProducts(int brandId, int categoryId, Pageable pageable);

    void save(Product product);

   Product getLastProduct();
}
