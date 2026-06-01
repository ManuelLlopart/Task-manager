package com.llopart.task_manager.controller;

import com.llopart.task_manager.dto.request.CategoryRequest;
import com.llopart.task_manager.dto.response.CategoryResponse;
import com.llopart.task_manager.service.CategoryService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/categories")
@RequiredArgsConstructor
public class CategoryController {

    private final CategoryService categoryService;

    @GetMapping
    public ResponseEntity<List<CategoryResponse>> getCategories(Authentication authentication) {
        return ResponseEntity.ok(categoryService.getAvailableCategories(authentication.getName()));
    }

    @PostMapping
    public ResponseEntity<CategoryResponse> createCategory(@Valid @RequestBody CategoryRequest request,
                                                           Authentication authentication) {
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(categoryService.createCategory(request, authentication.getName()));
    }

    @PutMapping("/{id}")
    public ResponseEntity<CategoryResponse> updateCategory(@PathVariable Long id,
                                                           @Valid @RequestBody CategoryRequest request,
                                                           Authentication authentication) {
        return ResponseEntity.ok(categoryService.updateCategory(id, request, authentication.getName()));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteCategory(@PathVariable Long id,
                                               Authentication authentication) {
        categoryService.deleteCategory(id, authentication.getName());
        return ResponseEntity.noContent().build();
    }
}