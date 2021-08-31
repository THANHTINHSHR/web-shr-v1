package cdw.cdwproject.controller.admin;

import cdw.cdwproject.model.brand.Brand;
import cdw.cdwproject.model.brand.FormDataCreateBrand;
import cdw.cdwproject.service.BrandServiceImp;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class BrandManagerController {
    @Autowired
    private BrandServiceImp brandServiceImp;

    @PostMapping("/admin/brand/create")
    @ResponseBody
    public String createBrand(@ModelAttribute FormDataCreateBrand formDataCreateBrand) {
        try {
            String name = formDataCreateBrand.getName();
            Brand brand = new Brand();
            brand.setName(name);

            brandServiceImp.save(brand);
            return "Create Brand Success";
        } catch (Exception e) {
            return "Create Brand Fail";
        }

    }

}
