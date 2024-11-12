package com.margulan.uniproject.Service;

import com.margulan.uniproject.Model.Task;

import java.util.List;

public interface TaskService {

    List<Task> getAllTasks();
    List<Task> getTasksForCurrentUser();
    void addTaskForCurrentUser(Task task);
    void editTask(String selectedTaskByTitle, Task task);
    void deleteByTitle(String selectedTaskByTitle);
}
