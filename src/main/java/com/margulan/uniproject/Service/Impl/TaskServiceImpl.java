package com.margulan.uniproject.Service.Impl;

import com.margulan.uniproject.Exception.UserNotFoundException;
import com.margulan.uniproject.Model.Task;
import com.margulan.uniproject.Model.User;
import com.margulan.uniproject.Repository.TasksRepository;
import com.margulan.uniproject.Repository.UsersRepository;
import com.margulan.uniproject.Service.TaskService;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
public class TaskServiceImpl implements TaskService {

    @Autowired
    TasksRepository tasksRepository;

    @Autowired
    UsersRepository usersRepository;

    @Override
    public List<Task> getAllTasks() {
        return tasksRepository.findAll();
    }

    @Override
    public Page<Task> getAllTasks(Pageable pageable) {
        return tasksRepository.findAll(pageable);
    }

    @Override
    public List<Task> getTasksForCurrentUser() {
        User user = usersRepository.findByEmail(getLoggedUser().getName()).orElseThrow(
                () -> new UserNotFoundException("User not found"));
        return tasksRepository.findByUserId(user.getId());
    }

    @Override
    public void addTaskForCurrentUser(Task task) {
        if (task.getDue_date().isBefore(LocalDate.now())) {
            throw new IllegalArgumentException("Add - Due date cannot be in the past");
        }
        User user = usersRepository.findByEmail(getLoggedUser().getName()).orElseThrow(
                () -> new UserNotFoundException("User not found"));
        task.setUser(user);
        tasksRepository.save(task);
    }

    @Override
    public void editTask(String selectedTaskByTitle, Task task) {
        if (task.getDue_date().isBefore(LocalDate.now())) {
            throw new IllegalArgumentException("Edit - Due date cannot be in the past");
        }

        Task foundTask = tasksRepository.findByTitle(selectedTaskByTitle);
        if (task.getTitle() != null && !task.getTitle().isBlank()) {
            foundTask.setTitle(task.getTitle());
        }

        if (task.getDescription() != null && !task.getDescription().isBlank()) {
            foundTask.setDescription(task.getDescription());
        }

        if (task.getDue_date() != null) {
            foundTask.setDue_date(task.getDue_date());
        }

        if (task.getCategory() != null) {
            foundTask.setCategory(task.getCategory());
        }

        tasksRepository.save(foundTask);
    }

    @Override
    @Transactional
    public void deleteByTitle(String deleteTaskByTitle) {
        tasksRepository.deleteByTitle(deleteTaskByTitle);
    }

    @Override
    public Page<Task> searchTasksForCurrentUser(String title, String status, String priority, Pageable pageable) {
        User user = usersRepository.findByEmail(getLoggedUser().getName()).orElseThrow(
                () -> new UserNotFoundException("User not found"));
        return tasksRepository.searchTasksByUserId(user.getId(), title, status, priority, pageable);
    }

    // Returns email of currently logged user
    private Authentication getLoggedUser() {
        return SecurityContextHolder.getContext().getAuthentication();
    }

}
