package com.todo_list.demo.service;

import com.todo_list.demo.DTOS.TaskGroupDTO;
import com.todo_list.demo.model.TaskGroup;
import com.todo_list.demo.repository.TaskGroupRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class TaskGroupService {

    private final TaskGroupRepository taskGroupRepository;

    @Autowired
    public TaskGroupService(TaskGroupRepository taskGroupRepository) {
        this.taskGroupRepository = taskGroupRepository;
    }

    @Transactional
    public TaskGroup createTaskGroup(TaskGroupDTO dto) {
        TaskGroup taskGroup =  new TaskGroup();
        taskGroup.setTaskGroupTitle(dto.getTaskGroupTitle());
        return taskGroupRepository.save(taskGroup);
    }

    @Transactional(readOnly = true)
    public TaskGroup getGroupById(Long id) {
        return taskGroupRepository.findById(id)
                .orElseThrow(() ->
                        new IllegalArgumentException("Task not found with id: " + id));
    }

    @Transactional
    public TaskGroup updateTaskGroup(Long id, TaskGroupDTO dto) {
        TaskGroup existingGroup = getGroupById(id);

        if (dto.getTaskGroupTitle() != null) {
            existingGroup.setTaskGroupTitle(dto.getTaskGroupTitle());
        }
        return existingGroup;
    }

    @Transactional(readOnly = true)
    public List<TaskGroup> getAllGroups() {
        return taskGroupRepository.findAll();
    }

    @Transactional
    public void deleteTaskGroup(Long id) {
        if (!taskGroupRepository.existsById(id)) {
            throw new IllegalArgumentException("Task not found with id: " + id);
        }
        taskGroupRepository.deleteById(id);
    }
}
