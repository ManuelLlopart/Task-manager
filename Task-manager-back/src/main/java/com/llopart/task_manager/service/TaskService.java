package com.llopart.task_manager.service;

import com.llopart.task_manager.dto.request.TaskRequest;
import com.llopart.task_manager.dto.response.CategoryResponse;
import com.llopart.task_manager.dto.response.TaskResponse;
import com.llopart.task_manager.entity.Category;
import com.llopart.task_manager.entity.Task;
import com.llopart.task_manager.entity.TaskStatus;
import com.llopart.task_manager.entity.User;
import com.llopart.task_manager.repository.CategoryRepository;
import com.llopart.task_manager.repository.TaskRepository;
import com.llopart.task_manager.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class TaskService {

    private final TaskRepository taskRepository;
    private final CategoryRepository categoryRepository;
    private final UserRepository userRepository;

    public TaskResponse createTask(TaskRequest request, String email) {
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));

        List<Category> categories = categoryRepository.findAllById(request.getCategoryIds());

        Task task = Task.builder()
                .name(request.getName())
                .description(request.getDescription())
                .status(TaskStatus.PENDING)
                .date(request.getDate())
                .endDate(request.getEndDate())
                .user(user)
                .categories(categories)
                .build();

        taskRepository.save(task);
        return mapToTaskResponse(task);
    }

    public List<TaskResponse> getTasksByUser(String email) {
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));

        return taskRepository.findByUserId(user.getId())
                .stream()
                .map(this::mapToTaskResponse)
                .toList();
    }

    public TaskResponse updateStatus(Long taskId, TaskStatus status, String email) {
        Task task = taskRepository.findById(taskId)
                .orElseThrow(() -> new RuntimeException("Tarea no encontrada"));

        if (!task.getUser().getEmail().equals(email)) {
            throw new RuntimeException("No tenés permiso para modificar esta tarea");
        }

        task.setStatus(status);
        taskRepository.save(task);
        return mapToTaskResponse(task);
    }

    public void deleteTask(Long taskId, String email) {
        Task task = taskRepository.findById(taskId)
                .orElseThrow(() -> new RuntimeException("Tarea no encontrada"));

        if (!task.getUser().getEmail().equals(email)) {
            throw new RuntimeException("No tenés permiso para eliminar esta tarea");
        }

        taskRepository.delete(task);
    }

    private TaskResponse mapToTaskResponse(Task task) {
        List<CategoryResponse> categories = task.getCategories()
                .stream()
                .map(c -> CategoryResponse.builder()
                        .id(c.getId())
                        .name(c.getName())
                        .isDefault(c.isDefault())
                        .build())
                .toList();

        return TaskResponse.builder()
                .id(task.getId())
                .name(task.getName())
                .description(task.getDescription())
                .status(task.getStatus())
                .date(task.getDate())
                .endDate(task.getEndDate())
                .categories(categories)
                .build();
    }
}