package cdw.cdwproject.controller.admin;

import cdw.cdwproject.model.User.MyUserDetails;
import cdw.cdwproject.model.User.User;
import cdw.cdwproject.model.brand.Brand;
import cdw.cdwproject.model.category.Category;
import cdw.cdwproject.model.product.FormDataCreateProduct;
import cdw.cdwproject.model.product.FormDataEditProduct;
import cdw.cdwproject.model.product.Product;
import cdw.cdwproject.model.product.Specification;
import cdw.cdwproject.oauth.CustomOAuth2User;
import cdw.cdwproject.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Controller
public class ProductManagerController {

    @Autowired
    private ProductServiceImp productServiceImp;
    @Autowired
    private UserServiceImp userServiceImp;
    @Autowired
    private BrandServiceImp brandServiceImp;
    @Autowired
    private CategoryServiceImp categoryServiceImp;
    @Autowired
    private SpecificationServiceImp specificationServiceImp;

    @GetMapping({"/admin/index", "/admin/product-list", "/admin"})
    public String showProductList(Model model, @AuthenticationPrincipal MyUserDetails myUserDetails, @AuthenticationPrincipal CustomOAuth2User oAuth2User) {
        System.out.println("csssssssssssssssssssssscvvvvv");

        User user;
        try {
            user = userServiceImp.getUserByEmail(oAuth2User.getEmail());
        } catch (NullPointerException e) {


            user = myUserDetails.getUser();
        }
//        oAuth2User.
        List<Product> productList = productServiceImp.getAllProduct();
        model.addAttribute("userName", user.getName());
        model.addAttribute("products", productList);

        return "admin/index";

    }

    @GetMapping("/admin/product/edit/{pid}")
    public String showEditProductPage(Model model, @AuthenticationPrincipal MyUserDetails myUserDetails, HttpServletRequest request, @PathVariable("pid") int pid, @AuthenticationPrincipal CustomOAuth2User oAuth2User) {
        User user;
        try {
            user = userServiceImp.getUserByEmail(oAuth2User.getEmail());
        } catch (NullPointerException e) {


            user = myUserDetails.getUser();
        }
        model.addAttribute("userName", user.getName());

        Product product = productServiceImp.getProductByID(pid);
        List<Specification> specifications = product.getSpecifications();
        String textSpecification = "";
        for (Specification spec : specifications
        ) {
            textSpecification = textSpecification + spec.getParameter() + " : " + spec.getDetail() + "\n";
        }

        List<Brand> brands = brandServiceImp.getAllBrand();
        List<Category> categories = categoryServiceImp.getAllCategory();

        model.addAttribute("product", product);
        model.addAttribute("brands", brands);
        model.addAttribute("categories", categories);
        model.addAttribute("images", getListProductImages(request, product));
        model.addAttribute("textSpecification", textSpecification);
        return "admin/edit-product";

    }

    private List<String> getListProductImages(HttpServletRequest request, Product product) {
        List<String> listProductImages = new ArrayList<>();
        String rootPath = request.getContextPath();
//listProductImages = Files.walk(Paths.get(rootPath+ File.separator+"img"+File.separator+product.getImagesPath())).collect(Coll)
        System.out.println("ROOT PATH" + rootPath);
//      File file = new File(rootPath+ File.separator+"img"+File.separator+product.getImagesPath());
//        System.out.println("FILE PATH " + rootPath + File.separator + "img" + File.separator + product.getImagesPath());
        File file = new File(new File(rootPath).getAbsolutePath() + File.separator + "src/main/resources/static/img/" + product.getImagesPath());
        for (File child : file.listFiles()) {
            listProductImages.add(child.getName());
        }
        return listProductImages;

    }

    @PostMapping("/admin/product/edit")

    @ResponseBody
    private String editProduct(@ModelAttribute FormDataEditProduct formDataEditProduct) {
        try {
            int pid = formDataEditProduct.getPid();
            String namme = formDataEditProduct.getName();
            int price = formDataEditProduct.getPrice();
            int quantity = formDataEditProduct.getQuantity();
            int categoryID = formDataEditProduct.getCategoryID();
            int brandID = formDataEditProduct.getBrandID();
            String description = formDataEditProduct.getDescription();


            String firstImage = formDataEditProduct.getFirstImage();


            Product product = productServiceImp.getProductByID(pid);
            Brand brand = brandServiceImp.getBrandByID(brandID);
            Category category = categoryServiceImp.getCategoryByID(categoryID);

            product.setBrand(brand);
            product.setCategory(category);
            product.setName(namme);
            product.setPrice(price);
            product.setQuantity(quantity);
            product.setDescription(description);
            product.setFirstImage(firstImage);

            productServiceImp.save(product);

            // remove old spec
            // old specification
            List<Specification> oldSpecification = specificationServiceImp.getSpecificationsByProductID(pid);
            for (Specification spec : oldSpecification
            ) {
                specificationServiceImp.remove(spec);
            }

            // save new spec
            String textSpecification = formDataEditProduct.getSpecification();
            List<Specification> specifications = covertToListSpecification(product, textSpecification);

            for (Specification spec : specifications
            ) {
                specificationServiceImp.save(spec);
            }
            return "Update Success";
        } catch (Exception e) {
            return "Update Fail";
        }

    }

    private List<Specification> covertToListSpecification(Product product, String textSpecification) {

        List<Specification> result = new ArrayList<>();
        String[] rows = textSpecification.split("\n");
        int genrateOrdinal = 1;
        for (String row : rows
        ) {
            Specification spec = new Specification();
            spec.setProduct(product);
            spec.setOrdinal(genrateOrdinal++);
            spec.setParameter(row.split(":")[0]);
            spec.setDetail(row.split(":")[1]);
            result.add(spec);
        }
        return result;
    }

    @GetMapping("/admin/product/create")
    public String showCreatProductPage(Model model, @AuthenticationPrincipal MyUserDetails userDetails, @AuthenticationPrincipal CustomOAuth2User oAuth2User) {
        User user;
        try {
            user = userServiceImp.getUserByEmail(oAuth2User.getEmail());
        } catch (NullPointerException e) {


            user = userDetails.getUser();
        }
        List<Brand> brands = brandServiceImp.getAllBrand();
        List<Category> categories = categoryServiceImp.getAllCategory();

        model.addAttribute("userName", user.getName());

        model.addAttribute("brands", brands);
        model.addAttribute("categories", categories);


        return "admin/create-product";

    }

    @PostMapping("/admin/product/create")
    @ResponseBody
    private String createProduct(@ModelAttribute FormDataCreateProduct formDataCreateProduct, HttpServletRequest request) {

        try {
            // get product properties
            String name = formDataCreateProduct.getName();
            int price = formDataCreateProduct.getPrice();
            int quantity = formDataCreateProduct.getQuantity();
            int brandID = formDataCreateProduct.getBrandID();
            int categoryID = formDataCreateProduct.getCategoryID();
            String description = formDataCreateProduct.getDescription();
            Brand brand = brandServiceImp.getBrandByID(brandID);
            Category category = categoryServiceImp.getCategoryByID(categoryID);


            MultipartFile firstImage = formDataCreateProduct.getFirstImage();
            MultipartFile[] images = formDataCreateProduct.getImages();

            // saave raw product first
            Product product = new Product();
            product.setName(name);
            product.setPrice(price);
            product.setQuantity(quantity);
            product.setBrand(brand);
            product.setCategory(category);
            product.setDescription(description);
//            product.setImagesPath(imagesPath);
//            product.setFirstImage(fileName);
            product.setCreateDate(new Date());

            productServiceImp.save(product);

            // after saved, get new id and make ImagesPath
            Product newProduct = productServiceImp.getLastProduct();
            String imagesPath = "IMAGES_PATH " + (newProduct.getId());

            /*
            create images path
             */
            String uploadRootPath = request.getContextPath();
            // Make sure directory exists!
            File uploadDir = new File(new File(uploadRootPath).getAbsolutePath() + File.separator + "src/main/resources/static/img/" + imagesPath);
            String s = uploadDir.getAbsolutePath();
            uploadDir.mkdirs();
            // upload firstImage
            // unique name
            String fileName = new SimpleDateFormat("yyyyMMddHHmmss").format(new Date()) + firstImage.getOriginalFilename();
            String uploadFilePath = s + "/" + fileName;
            byte[] bytes = firstImage.getBytes();
            Path path = Paths.get(uploadFilePath);
            Files.write(path, bytes);
            // upload Images
            for (MultipartFile subImage : images
            ) {
                String subFileName = new SimpleDateFormat("yyyyMMddHHmmss").format(new Date()) + subImage.getOriginalFilename();
                String subUploadFilePath = s + "/" + subFileName;
                byte[] subBytes = subImage.getBytes();
                Path subPath = Paths.get(subUploadFilePath);
                Files.write(subPath, subBytes);
            }
            // save image --> save product again
            newProduct.setImagesPath(imagesPath);
            product.setFirstImage(fileName);
            productServiceImp.save(newProduct);


            // save specification after product saved
            String textSpecification = formDataCreateProduct.getSpecification();
            List<Specification> specifications = covertToListSpecification(product, textSpecification);

            for (Specification spec : specifications
            ) {
                specificationServiceImp.save(spec);
            }

            return "CREATE SUCCESS";
        } catch (Exception e) {
            return "CREATE FAIL";
        }
    }

}
