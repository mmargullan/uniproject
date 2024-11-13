package com.margulan.uniproject.Service;

import com.margulan.uniproject.Model.Task;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface TaskService {

    List<Task> getAllTasks();

    Page<Task> getAllTasks(Pageable pageable);

    List<Task> getTasksForCurrentUser();

    void addTaskForCurrentUser(Task task);

    void editTask(String selectedTaskByTitle, Task task);

    void deleteByTitle(String selectedTaskByTitle);

    Page<Task> searchTasksForCurrentUser(String title, String status, String priority, Pageable pageable);
}
