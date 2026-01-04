package com.todo_list.demo.DTOS;

import com.todo_list.demo.model.TaskGroup;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class TaskGroupDTO {

    private Long taskgroupId;
    private String taskGroupTitle;

    public TaskGroupDTO(TaskGroup taskGroup) {
        this.taskgroupId = taskgroupId;
        this.taskGroupTitle = taskGroupTitle;
    }

}
