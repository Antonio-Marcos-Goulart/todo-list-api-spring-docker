package com.todo_list.demo.service;

import com.todo_list.demo.DTOS.TasksDTO;
import com.todo_list.demo.model.Task;
import com.todo_list.demo.model.TaskGroup;
import com.todo_list.demo.repository.TaskGroupRepository;
import com.todo_list.demo.repository.TaskRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class TaskService {

    private final TaskRepository taskRepository;
    private final TaskGroupRepository taskGroupRepository;

    @Autowired
    public TaskService(TaskRepository taskRepository, TaskGroupRepository taskGroupRepository) {
        this.taskRepository = taskRepository;
        this.taskGroupRepository = taskGroupRepository;
    }

    @Transactional
    public Task createTask(TasksDTO dto) {
        if (dto.getTaskGroupId() == null) {
            throw new IllegalArgumentException("TaskGroupId is required");
        }
        TaskGroup group = taskGroupRepository.findById(dto.getTaskGroupId())
                .orElseThrow(() ->
                        new IllegalArgumentException(
                                "TaskGroup not found with id " + dto.getTaskGroupId()
                ));

        Task task = new Task();
        task.setTaskTitle(dto.getTaskTitle());
        task.setTaskDescription(dto.getTaskDescription());
        task.setTaskStatus(dto.getTaskStatus());
        task.setTaskPriority(dto.getTaskPriority());

        task.setTaskGroup(group);

        return taskRepository.save(task);
    }

    @Transactional(readOnly = true)
    public Task getTaskById(Long id) {
        return taskRepository.findById(id)
                .orElseThrow(() ->
                        new IllegalArgumentException("Task not found with id: " + id));
    }

    @Transactional(readOnly = true)
    public List<Task> getAllTasks() {
        return taskRepository.findAll();
    }

    @Transactional(readOnly = true)
    public List<Task> searchByTitle(String title) {
        return taskRepository.findByTaskTitleContainingIgnoreCase(title);

    }

    @Transactional
    public Task updateTask(Long id, TasksDTO dto) {
        Task existingTask = getTaskById(id);

        if (dto.getTaskTitle() != null) {
            existingTask.setTaskTitle(dto.getTaskTitle());
        }
        if (dto.getTaskDescription() != null) {
            existingTask.setTaskDescription(dto.getTaskDescription());
        }
        if (dto.getTaskStatus() != null) {
            existingTask.setTaskStatus(dto.getTaskStatus());
        }
        if (dto.getTaskPriority() != null) {
            existingTask.setTaskPriority(dto.getTaskPriority());
        }
        if (dto.getTaskGroupId() != null) {
            TaskGroup group = taskGroupRepository.findById(dto.getTaskGroupId())
                    .orElseThrow(() ->
                            new IllegalArgumentException(
                            "Task Group not found with id " + dto.getTaskGroupId()
                    ));
            existingTask.setTaskGroup(group);
        }
        return existingTask;
    }

    @Transactional
    public void deleteTask(Long id) {
        if (!taskRepository.existsById(id)) {
            throw new IllegalArgumentException("Task not found with id: " + id);
        }
        taskRepository.deleteById(id);
    }
}
