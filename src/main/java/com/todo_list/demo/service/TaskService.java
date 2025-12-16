package com.todo_list.demo.service;

import com.todo_list.demo.model.Task;
import com.todo_list.demo.repository.TaskRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TaskService {
    private final TaskRepository taskRepository;

    public TaskService(TaskRepository taskRepository) {
        this.taskRepository = taskRepository;
    }

    public Task createTask(Task task) {
        if (task.getTaskTitle() == null || task.getTaskTitle().length() < 3) {
            throw new IllegalArgumentException("The task title cannot be null or shorter than 3 characters");
        }
        if (task.getTaskStatus() == null) {
            throw new IllegalArgumentException("The task status cannot be null");
        }
        if (task.getTaskPriority() == null) {
            throw new IllegalArgumentException("The task priority cannot be null");
        }
        return taskRepository.save(task);

    }

    public Task getTaskById(Long id) {
        return taskRepository.findById(id) .orElseThrow(() -> new RuntimeException("Task not found with id: " + id));
    }

    public List<Task> searchTask(Long id, String title) {
        List<Task> dataTask = List.of();
        if (id != null) {
            Task task = taskRepository.findById(id).orElse(null);
            if (task != null) {
                dataTask = List.of(task);
            }
        } else if (title != null && title.isBlank()) {
            dataTask = taskRepository.findByTaskTitleContainingIgnoreCase(title);
        }

        if (dataTask.isEmpty()) {
            throw new IllegalArgumentException("No tasks were found that matched the provided criteria");
        }
        return dataTask;
    }

    public Task updateTask(Long id, Task updatedTask) {
        Task existingTask = taskRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Task not found with id: " + id));

        if (updatedTask.getTaskTitle() != null && updatedTask.getTaskTitle().length() >= 3) {
            existingTask.setTaskTitle(updatedTask.getTaskTitle());
        }

        if (updatedTask.getTaskStatus() != null) {
            existingTask.setTaskStatus(updatedTask.getTaskStatus());
        }

        if (updatedTask.getTaskPriority() != null) {
            existingTask.setTaskPriority(updatedTask.getTaskPriority());
        }

        if (updatedTask.getTaskDescription() != null) {
            existingTask.setTaskDescription(updatedTask.getTaskDescription());
        }

        return taskRepository.save(existingTask);
    }

    public void deleteTask(Long id) {
        if (!taskRepository.existsById(id)) {
            throw new IllegalArgumentException("Task not found with id: " + id);
        }
        taskRepository.deleteById(id);
    }
}
