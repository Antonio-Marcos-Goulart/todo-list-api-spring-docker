package com.todo_list.demo.controller;

import com.todo_list.demo.DTOS.TasksDTO;
import com.todo_list.demo.model.Task;
import com.todo_list.demo.service.TaskService;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/tasks")
public class TaskController {

    private final TaskService taskService;

    @Autowired
    public TaskController(TaskService taskService) {
        this.taskService = taskService;
    }

    @PostMapping
    @Operation(summary = "Create task", description = "Create task")
    public ResponseEntity<TasksDTO> createTask(@Valid @RequestBody TasksDTO dto) {
        Task saved = taskService.createTask(dto);
        return ResponseEntity.status(HttpStatus.CREATED).body(new TasksDTO(saved));
    }

    @PutMapping("/{id}")
    @Operation(summary = "Update task by id", description = "Update an existing task by its id")
    public ResponseEntity<TasksDTO> updateTask(
            @PathVariable Long id,
            @Valid @RequestBody TasksDTO dto) {
        Task updatedTask = taskService.updateTask(id, dto);
        return ResponseEntity.ok(new TasksDTO(updatedTask));
    }

    @GetMapping("/{id}")
    @Operation(summary = "Search task by id", description = "Search task by Id")
    public ResponseEntity<TasksDTO> getTaskById(@PathVariable Long id) {
        Task task = taskService.getTaskById(id);
        return ResponseEntity.ok(new TasksDTO(task));
    }

    @GetMapping
    public ResponseEntity<List<TasksDTO>> search(@RequestParam(required = false) String title) {
            List<Task> tasks = (title == null || title.isBlank())
                    ? taskService.getAllTasks() // If the title is null or blank - get all tasks
                    : taskService.searchByTitle(title); // Task searched

            List<TasksDTO> tasksDTOList = tasks.stream()
                    .map(TasksDTO::new)
                    .toList();
        return ResponseEntity.ok(tasksDTOList);
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Delete tasks by Id", description = "Delete tasks by Id")
    public ResponseEntity<Void> deleteTask(@PathVariable Long id) {
        taskService.deleteTask(id);
        return ResponseEntity.noContent().build(); // no response - no content
    }
}
