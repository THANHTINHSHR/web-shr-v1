package cdw.cdwproject.controller.admin;

import cdw.cdwproject.model.category.Category;
import cdw.cdwproject.model.category.FormDataCreateCategory;
import cdw.cdwproject.service.CategoryServiceImp;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class CategoryManagerController {
    @Autowired
    private CategoryServiceImp categoryServiceImp;
    @PostMapping("/admin/category/create")
    @ResponseBody
    public String createCategory(@ModelAttribute FormDataCreateCategory formDataCreateCategory){
        try {
            String name = formDataCreateCategory.getName();
            String classIcon = formDataCreateCategory.getClassIcon();
            Category category = new Category();
            category.setName(name);
            category.setClassIcon(classIcon);

            categoryServiceImp.save(category);

            return "Create Category Success";
        }
        catch (Exception e){
            return "Create Category Fail";
        }

    }

}
