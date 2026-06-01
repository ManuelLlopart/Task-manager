package com.llopart.task_manager.service;

import com.llopart.task_manager.dto.request.CategoryRequest;
import com.llopart.task_manager.dto.response.CategoryResponse;
import com.llopart.task_manager.entity.Category;
import com.llopart.task_manager.entity.User;
import com.llopart.task_manager.repository.CategoryRepository;
import com.llopart.task_manager.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class CategoryService {

    private final CategoryRepository categoryRepository;
    private final UserRepository userRepository;

    public List<CategoryResponse> getAvailableCategories(String email) {
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));

        List<Category> defaults = categoryRepository.findByIsDefaultTrue();
        List<Category> userCategories = categoryRepository.findByUserId(user.getId());

        List<Category> all = new ArrayList<>(defaults);
        all.addAll(userCategories);

        return all.stream()
                .map(c -> CategoryResponse.builder()
                        .id(c.getId())
                        .name(c.getName())
                        .isDefault(c.isDefault())
                        .build())
                .toList();
    }

    public CategoryResponse createCategory(CategoryRequest request, String email) {
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));

        Category category = Category.builder()
                .name(request.getName())
                .isDefault(false)
                .user(user)
                .build();

        categoryRepository.save(category);

        return CategoryResponse.builder()
                .id(category.getId())
                .name(category.getName())
                .isDefault(false)
                .build();
    }

    public void deleteCategory(Long categoryId, String email) {
        Category category = categoryRepository.findById(categoryId)
                .orElseThrow(() -> new RuntimeException("Categoría no encontrada"));

        if (category.isDefault()) {
            throw new RuntimeException("No se pueden eliminar categorías por defecto");
        }

        if (!category.getUser().getEmail().equals(email)) {
            throw new RuntimeException("No tenés permiso para eliminar esta categoría");
        }

        categoryRepository.delete(category);
    }

    public CategoryResponse updateCategory(Long categoryId, CategoryRequest request, String email) {
        Category category = categoryRepository.findById(categoryId)
                .orElseThrow(() -> new RuntimeException("Categoría no encontrada"));

        if (category.isDefault()) {
            throw new RuntimeException("No se pueden editar categorías por defecto");
        }

        if (!category.getUser().getEmail().equals(email)) {
            throw new RuntimeException("No tenés permiso para editar esta categoría");
        }

        category.setName(request.getName());
        categoryRepository.save(category);

        return CategoryResponse.builder()
                .id(category.getId())
                .name(category.getName())
                .isDefault(false)
                .build();
    }
}