package cdw.cdwproject.controller;

import cdw.cdwproject.model.User.MyUserDetails;
import cdw.cdwproject.model.brand.Brand;
import cdw.cdwproject.model.category.Category;
import cdw.cdwproject.model.product.Product;
import cdw.cdwproject.model.review.Review;
import cdw.cdwproject.model.review.ReviewResponse;
import cdw.cdwproject.service.BrandServiceImp;
import cdw.cdwproject.service.CategoryServiceImp;
import cdw.cdwproject.service.ProductServiceImp;
import cdw.cdwproject.service.ReviewServiceImp;
import net.minidev.json.JSONObject;
import net.minidev.json.annotate.JsonIgnore;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.query.Param;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@RequestMapping("/product/*")
@Controller
public class ProductController {
    @Autowired
    ProductServiceImp productServiceImp;
    @Autowired
    BrandServiceImp brandServiceImp;
    @Autowired
    CategoryServiceImp categoryServiceImp;
    @Autowired
    ReviewServiceImp reviewServiceImp;

    @GetMapping("/{productID}")
    public String showProductDetails(HttpServletRequest request, Model model, @PathVariable(value = "productID") int productID) {
        int reviewSize = 5;
        List<Category> categories = categoryServiceImp.getAllCategory();
        List<Brand> brands = getBrandAmount();

        Product product = productServiceImp.getProductByID(productID);

        Pageable pageable = PageRequest.of(0, reviewSize);
        Page<Review> reviews = reviewServiceImp.getReviewsByProductID(productID, pageable);

        // only get 5 product same brand (popular products)
        List<Product> productsSameBrand = productServiceImp.getPopularProductsByBrand(product.getBrand().getId(), PageRequest.of(0, 5)).getContent();
        // only get 10 product same brand or same category (popular products)
        List<Product> relatedProducts = productServiceImp.getRelatedProducts(product.getBrand().getId(), product.getCategory().getId(), PageRequest.of(0, 10)).getContent();
        // listReview
        model.addAttribute("product", product);
        model.addAttribute("images", getListProductImages(request, product));
        model.addAttribute("categories", categories);
        model.addAttribute("brands", brands);
        model.addAttribute("reviews", reviews.getContent());
        model.addAttribute("currentPage", 1);
        model.addAttribute("totalPages", reviews.getTotalPages());

        model.addAttribute("productsSameBrand", productsSameBrand);
        model.addAttribute("relatedProducts", relatedProducts);

        return "normal/product-detail";
    }

    @PostMapping("/reviews/page")
    @ResponseBody
    public ResponseEntity<List<JSONObject>> getReviewsByAjaxCall(@Param("page") int page, @Param("pid") int pid) {
        int pageSize = 5;
        Pageable pageable = PageRequest.of(page - 1, pageSize);
        Page<Review> reviews = reviewServiceImp.getReviewsByProductID(pid, pageable);

        List<JSONObject> jsonObjects = new ArrayList<>();
        for (Review review : reviews.getContent()
        ) {
            JSONObject entity = new JSONObject();
            entity.put("userName", review.getUser().getName());
            entity.put("pid", review.getProduct().getId());
            entity.put("review", review.getReview());
            entity.put("createTime", review.getCreateTime().toString());
            entity.put("buyTime", review.getBuyTime().toString());

            entity.put("image",  review.getImage());
            entity.put("rating", review.getRating());
            jsonObjects.add(entity);
        }


        return new ResponseEntity<List<JSONObject>>(jsonObjects, HttpStatus.OK);
    }

    /*
    methold get list image of product
     */
    private List<String> getListProductImages(HttpServletRequest request, Product product) {
        List<String> listProductImages = new ArrayList<>();
        String rootPath = request.getContextPath();
//listProductImages = Files.walk(Paths.get(rootPath+ File.separator+"img"+File.separator+product.getImagesPath())).collect(Coll)
//        System.out.println("ROOT PATH" + rootPath);
//      File file = new File(rootPath+ File.separator+"img"+File.separator+product.getImagesPath());
//        System.out.println("FILE PATH " + rootPath + File.separator + "img" + File.separator + product.getImagesPath());
        File file = new File(new File(rootPath).getAbsolutePath() + File.separator + "src/main/resources/static/img/" + product.getImagesPath());
        for (File child : file.listFiles()) {
            listProductImages.add(product.getImagesPath() + "/" + child.getName());
        }
//        for (String s : listProductImages
//        ) {
//            System.out.println("image product : " + s);
//        }
        return listProductImages;

    }

    @GetMapping("/category/{categoryID}/{sortBy}/{pageNo}")
    public String showProductsCategory(Model model, @PathVariable("categoryID") int categoryID, @PathVariable(value = "pageNo") int pageNo, @PathVariable("sortBy") String sortBy) {
        int pageSize = 8;
        List<Category> categories = categoryServiceImp.getAllCategory();
        Category category = categoryServiceImp.getCategoryByID(categoryID);
        Pageable pageable = PageRequest.of(pageNo - 1, pageSize);
        Page<Product> products;
        switch (sortBy) {
            case "a":
                products = productServiceImp.getProductsByCategoryID(categoryID, pageable);
                break;
            case "p":
                products = productServiceImp.getPopularProductsByCategory(categoryID, pageable);
                break;
            case "b":
                products = productServiceImp.getBestSellerProductsByCategory(categoryID, pageable);
                break;
            case "n":
                products = productServiceImp.getNewProductsByCategory(categoryID, pageable);
                break;
            case "e":
                products = productServiceImp.getExpensiveProductsByCategory(categoryID, pageable);
                break;
            case "l":
                products = productServiceImp.getLowPriceProductsByCategory(categoryID, pageable);
                break;
            case "r":
                products = productServiceImp.getProductsByCategoryOrderByRating(categoryID, pageable);
                break;
            default:
                products = productServiceImp.getProductsByCategoryID(categoryID, pageable);

        }


        // change
        model.addAttribute("products", products);
        model.addAttribute("category", category);
        model.addAttribute("currentPage", pageNo);
        model.addAttribute("totalPages", products.getTotalPages());
        model.addAttribute("totalItems", products.getTotalElements());
        model.addAttribute("sortBy", sortBy);

        // default
        List<Brand> brands = getBrandAmount();
        model.addAttribute("categories", categories);
        model.addAttribute("brands", brands);

        return "normal/products-category";

    }

    private List<Brand> getBrandAmount() {
        return brandServiceImp.getBrandsAmount();
    }

    @GetMapping("/brand/{brandID}/{sortBy}/{pageNo}")
    public String showProductsBrand(Model model, @PathVariable("brandID") int brandID, @PathVariable("pageNo") int pageNo, @PathVariable("sortBy") String sortBy) {
        int pageSize = 8;
        List<Category> categories = categoryServiceImp.getAllCategory();
        Brand brand = brandServiceImp.getBrandByID(brandID);
        Pageable pageable = PageRequest.of(pageNo - 1, pageSize);
        Page<Product> products;
        switch (sortBy) {
            case "a":
                products = productServiceImp.getProductsByBrandID(brandID, pageable);
                break;
            case "p":
                products = productServiceImp.getPopularProductsByBrand(brandID, pageable);
                break;
            case "b":
                products = productServiceImp.getBestSellerProductsByBrand(brandID, pageable);
                break;
            case "n":
                products = productServiceImp.getNewProductsByBrand(brandID, pageable);
                break;
            case "e":
                products = productServiceImp.getExpensiveProductsByBrand(brandID, pageable);
                break;
            case "l":
                products = productServiceImp.getLowPriceProductsByBrand(brandID, pageable);
                break;
            case "r":
                products = productServiceImp.getProductsByBrandOrderByRating(brandID, pageable);
                break;
            default:
                products = productServiceImp.getProductsByBrandID(brandID, pageable);

        }

        List<Brand> brands = getBrandAmount();
        // change
        model.addAttribute("brand", brand);
        model.addAttribute("products", products);
        model.addAttribute("currentPage", pageNo);
        model.addAttribute("totalPages", products.getTotalPages());
        model.addAttribute("totalItems", products.getTotalElements());
        model.addAttribute("sortBy", sortBy);
        // default
        model.addAttribute("brands", brands);
        model.addAttribute("categories", categories);
        return "normal/products-brand";
    }

    @GetMapping("/search/{searchText}/{sortBy}/{pageNo}")
    public String showProductsBySearch(Model model, @PathVariable("searchText") String searchText, @PathVariable(value = "pageNo") int pageNo, @PathVariable("sortBy") String sortBy) {
        int pageSize = 8;
        List<Category> categories = categoryServiceImp.getAllCategory();
        List<Brand> brands = getBrandAmount();
        Pageable pageable = PageRequest.of(pageNo - 1, pageSize);
        Page<Product> products;
        switch (sortBy) {
            case "a":
                products = productServiceImp.getProductBySearch(searchText, pageable);
                break;
            case "p":
                products = productServiceImp.getProductsBySearchOrderByPopular(searchText, pageable);
                break;
            case "b":
                products = productServiceImp.getProductsBySearchOrderByBestSeller(searchText, pageable);
                break;
            case "n":
                products = productServiceImp.getProductsBySearchOrderByCreatDate(searchText, pageable);
                break;
            case "e":
                products = productServiceImp.getProductsBySearchOrderByPriceDesc(searchText, pageable);
                break;
            case "l":
                products = productServiceImp.getProductsBySearchOrderByPriceAsc(searchText, pageable);
                break;
            case "r":
                products = productServiceImp.getProductsBySearchOrderByRating(searchText, pageable);
                break;
            default:
                products = productServiceImp.getProductBySearch(searchText, pageable);

        }
        model.addAttribute("products", products);
        model.addAttribute("currentPage", pageNo);
        model.addAttribute("totalPages", products.getTotalPages());
        model.addAttribute("totalItems", products.getTotalElements());
        model.addAttribute("sortBy", sortBy);
        model.addAttribute("searchText", searchText);
        // default
        model.addAttribute("brands", brands);
        model.addAttribute("categories", categories);
        return "normal/products-search";
    }

    @GetMapping("/product/search")
    public String searchProducts(Model model, @AuthenticationPrincipal MyUserDetails userDetails, @Param("searchText") String searchText) {

        int pageSize = 8;
        List<Category> categories = categoryServiceImp.getAllCategory();
        List<Brand> brands = getBrandAmount();
        Pageable pageable = PageRequest.of(0, pageSize);
        Page<Product>
                products = productServiceImp.getProductBySearch(searchText, pageable);
        model.addAttribute("products", products);
        model.addAttribute("currentPage", 1);
        model.addAttribute("totalPages", products.getTotalPages());
        model.addAttribute("totalItems", products.getTotalElements());
        model.addAttribute("sortBy", "a");
        model.addAttribute("searchText", searchText);
        // default
        model.addAttribute("brands", brands);
        model.addAttribute("categories", categories);
        return "normal/products-search";
    }
}
