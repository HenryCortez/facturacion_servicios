package com.servicios.facturacion.facturacion_servicios.product.category;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CategoryService {
    @Autowired
    private CategoryRepository categoryRepository;

    public Category saveCategory(Category category) {
        return categoryRepository.save(category);
    }

    public Category getCategoryById(Long id) {
        return categoryRepository.findById(id).orElse(null);
    }

    public Category getCategoryByName(String name) {
        return categoryRepository.findByName(name).orElse(null);
    }

    public boolean deleteCategory(Long id) {
        Category categoryToDelete = categoryRepository.findById(id).orElse(null);
        if (categoryToDelete != null) {
            categoryToDelete.setStatus(false);
            categoryRepository.save(categoryToDelete);
            return true;
        }
        return false;
    }

    public Category updateCategory(Long id, Category category) {
        Category categoryToUpdate = categoryRepository.findById(id).orElse(null);
        if (!categoryToUpdate.isStatus()) {
            return null;
        }
        if (category.getName() != null) {
            categoryToUpdate.setName(category.getName());
        }
        if (category.getDescription() != null) {
            categoryToUpdate.setDescription(category.getDescription());
        }
        return categoryRepository.save(categoryToUpdate);

    }

    public List<Category> findAll() {
        return categoryRepository.findByStatus(true);
    }
}
