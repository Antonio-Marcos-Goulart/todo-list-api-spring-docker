package com.todo_list.demo.repository;

import com.todo_list.demo.model.Task;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface TaskRepository extends JpaRepository<Task, Long> {

    List<Task> findByTaskTitleContainingIgnoreCase(String title);

    List<Task> findByTaskGroup_TaskgroupId(Long taskGroupId);

    List<Task> findByTaskGroup_TaskgroupIdAndTaskTitleContainingIgnoreCase(
            Long taskgroupId,
            String taskTitle
    );

    Optional<Task> findByTaskIdAndTaskGroup_TaskgroupId(
            Long taskId,
            Long taskGroupId
    );
}
