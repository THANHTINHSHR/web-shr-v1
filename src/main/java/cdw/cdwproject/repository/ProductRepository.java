package cdw.cdwproject.repository;

import cdw.cdwproject.model.product.Product;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProductRepository extends JpaRepository<Product, Integer>, JpaSpecificationExecutor {
    @Query("  SELECT p FROM Product p ORDER BY p.createDate DESC")
    List<Product> getNewProducts(Pageable pageable);

    @Query("SElECT  p FROM Product p LEFT JOIN SoldProduct s ON p.id = s.product.id ORDER BY s.sellNumber DESC")
    List<Product> getFeaturedProducts(Pageable pageable);

    //            @Query(value = "SELECT * from Products where PRODUCTS.CATEGORY_ID = ?1",nativeQuery = true)
    @Query("SELECT p FROM Product p WHERE p.category.id = ?1")
    Page<Product> getProductsByCategory(int categoryID, Pageable pageable);


    @Query("SELECT p FROM Product p WHERE p.brand.id = ?1")
    Page<Product> getProductsByBrand(int brandID, Pageable pageable);

    @Query("SElECT p FROM Product p LEFT JOIN SoldProduct s ON p.id = s.product.id WHERE p.category.id = ?1 ORDER BY s.saleNumber DESC")
    Page<Product> getPopularProductsByCategory(int categoryID, Pageable pageable);


    @Query("SElECT p FROM Product p LEFT JOIN SoldProduct s ON p.id = s.product.id WHERE p.brand.id = ?1 ORDER BY s.saleNumber DESC")
    Page<Product> getPopularProductsByBrand(int brandID, Pageable pageable);

    @Query("SElECT p FROM Product p LEFT JOIN SoldProduct s ON p.id = s.product.id WHERE p.category.id = ?1 ORDER BY s.sellNumber DESC")
    Page<Product> getBestSellerProductsByCategory(int categoryID, Pageable pageable);

    @Query("SElECT p FROM Product p LEFT JOIN SoldProduct s ON p.id = s.product.id WHERE p.brand.id = ?1 ORDER BY s.sellNumber DESC")
    Page<Product> getBestSellerProductsByBrand(int categoryID, Pageable pageable);

    @Query("  SELECT p FROM Product p WHERE p.category.id = ?1 ORDER BY p.createDate DESC")

    Page<Product> getNewProductsByCategory(int categoryID, Pageable pageable);

    @Query("  SELECT p FROM Product p WHERE p.brand.id = ?1 ORDER BY p.createDate DESC")
    Page<Product> getNewProductsByBrand(int brandID, Pageable pageable);

    @Query("SELECT p FROM Product p WHERE p.category.id = ?1 ORDER BY p.price DESC")
    Page<Product> getExpensiveProductsByCategory(int categoryID, Pageable pageable);

    @Query("SELECT p FROM Product p WHERE p.brand.id = ?1 ORDER BY p.price DESC")
    Page<Product> getExpensiveProductsByBrand(int brandID, Pageable pageable);

    @Query("SELECT p FROM Product p WHERE p.category.id = ?1 ORDER BY p.price ASC")
    Page<Product> getLowPriceProductsByCategory(int categoryID, Pageable pageable);

    @Query("SELECT p FROM Product p WHERE p.brand.id = ?1 ORDER BY p.price ASC ")
    Page<Product> getLowPriceProductsByBrand(int brandID, Pageable pageable);

    @Query("SELECT p FROM Product p WHERE p.category.id = ?1 ORDER BY p.rating DESC")
    Page<Product> getProductsByCategoryOrderByRating(int brandID, Pageable pageable);

    @Query("SELECT p FROM Product p WHERE p.brand.id = ?1 ORDER BY p.rating DESC")
    Page<Product> getProductsByBrandOrderByRating(int brandID, Pageable pageable);

    @Query("SELECT p FROM Product p WHERE p.name LIKE %?1% OR p.brand.name LIKE %?1% OR p.category.name LIKE %?1%  OR p.description LIKE %?1% ")
    Page<Product> getProductsBySearch(String searchText, Pageable pageable);

    @Query("SELECT p FROM Product p LEFT JOIN SoldProduct s ON p.id = s.product.id WHERE p.name LIKE %?1% OR p.brand.name LIKE %?1% OR p.category.name LIKE %?1%  OR p.description LIKE %?1% ORDER BY s.saleNumber DESC ")
    Page<Product> getProductsBySearchOrderByPopular(String searchText, Pageable pageable);

    @Query("SELECT p FROM Product p LEFT JOIN SoldProduct s ON p.id = s.product.id WHERE p.name LIKE %?1% OR p.brand.name LIKE %?1% OR p.category.name LIKE %?1%  OR p.description LIKE %?1% ORDER BY s.sellNumber DESC ")
    Page<Product> getProductsBySearchOrderByBestSeller(String searchText, Pageable pageable);

    @Query("SELECT p FROM Product p WHERE p.name LIKE %?1% OR p.brand.name LIKE %?1% OR p.category.name LIKE %?1%  OR p.description LIKE %?1% ORDER BY p.createDate DESC")
    Page<Product> getProductsBySearchOrderByCreatDate(String searchText, Pageable pageable);

    @Query("SELECT p FROM Product p WHERE p.name LIKE %?1% OR p.brand.name LIKE %?1% OR p.category.name LIKE %?1%  OR p.description LIKE %?1% ORDER BY p.price DESC")
    Page<Product> getProductsBySearchOrderByPriceDesc(String searchText, Pageable pageable);

    @Query("SELECT p FROM Product p WHERE p.name LIKE %?1% OR p.brand.name LIKE %?1% OR p.category.name LIKE %?1%  OR p.description LIKE %?1% ORDER BY p.price ASC")
    Page<Product> getProductsBySearchOrderByPriceAsc(String searchText, Pageable pageable);

    @Query("SELECT p FROM Product p WHERE p.name LIKE %?1% OR p.brand.name LIKE %?1% OR p.category.name LIKE %?1%  OR p.description LIKE %?1% ORDER BY p.rating DESC")
    Page<Product> getProductsBySearchOrderByRating(String searchText, Pageable pageable);

    @Query("SElECT p FROM Product p LEFT JOIN SoldProduct s ON p.id = s.product.id WHERE p.brand.id = ?1 OR p.category.id = ?2 ORDER BY s.saleNumber DESC")
    Page<Product> getRelatedProducts(int brandId, int categoryId, Pageable pageable);

    @Query(value = "SELECT top 1 * FROM PRODUCTS ORDER BY ID DESC", nativeQuery = true)
    Product getLastProduct();
}

