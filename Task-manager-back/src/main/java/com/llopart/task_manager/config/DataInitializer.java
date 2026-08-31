package com.llopart.task_manager.config;

import com.llopart.task_manager.entity.Category;
import com.llopart.task_manager.repository.CategoryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
public class DataInitializer implements CommandLineRunner {

    private final CategoryRepository categoryRepository;

    @Override
    public void run(String... args) {
        if (categoryRepository.findByIsDefaultTrue().isEmpty()) {
            List<Category> defaults = List.of(
                    Category.builder().name("Trabajo").isDefault(true).user(null).build(),
                    Category.builder().name("Personal").isDefault(true).user(null).build(),
                    Category.builder().name("Estudio").isDefault(true).user(null).build(),
                    Category.builder().name("Salud").isDefault(true).user(null).build(),
                    Category.builder().name("Hogar").isDefault(true).user(null).build()
            );
            categoryRepository.saveAll(defaults);
            System.out.println("Categorías default creadas correctamente");
        }
    }
}