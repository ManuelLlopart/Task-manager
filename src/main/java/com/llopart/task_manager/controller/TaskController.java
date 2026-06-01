package com.llopart.task_manager.controller;

import com.llopart.task_manager.dto.request.TaskRequest;
import com.llopart.task_manager.dto.response.TaskResponse;
import com.llopart.task_manager.entity.TaskStatus;
import com.llopart.task_manager.service.TaskService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/tasks")
@RequiredArgsConstructor
public class TaskController {

    private final TaskService taskService;

    @PostMapping
    public ResponseEntity<TaskResponse> createTask(@Valid @RequestBody TaskRequest request,
                                                   Authentication authentication) {
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(taskService.createTask(request, authentication.getName()));
    }

    @GetMapping
    public ResponseEntity<List<TaskResponse>> getTasks(Authentication authentication) {
        return ResponseEntity.ok(taskService.getTasksByUser(authentication.getName()));
    }

    @PatchMapping("/{id}/status")
    public ResponseEntity<TaskResponse> updateStatus(@PathVariable Long id,
                                                     @RequestParam TaskStatus status,
                                                     Authentication authentication) {
        return ResponseEntity.ok(taskService.updateStatus(id, status, authentication.getName()));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteTask(@PathVariable Long id,
                                           Authentication authentication) {
        taskService.deleteTask(id, authentication.getName());
        return ResponseEntity.noContent().build();
    }
}