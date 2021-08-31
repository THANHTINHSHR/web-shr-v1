package cdw.cdwproject.service;

import cdw.cdwproject.model.category.Category;
import cdw.cdwproject.repository.CategoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
public class CategoryServiceImp implements CategoryService {
    @Autowired
    private CategoryRepository categoryRepository;
    @Override
    public List<Category> getAllCategory() {
        return categoryRepository.findAll();
    }

    @Override
    public Category getCategoryByID(int categoryID) {
        return categoryRepository.getCategoryById(categoryID);
    }

    @Override
    public void save(Category category) {
        categoryRepository.save(category);
    }
}
