package cdw.cdwproject.service;

import cdw.cdwproject.model.category.Category;

import java.util.List;

public interface CategoryService {
    List<Category> getAllCategory();
    Category getCategoryByID(int categoryID);

    void save(Category category);
}
