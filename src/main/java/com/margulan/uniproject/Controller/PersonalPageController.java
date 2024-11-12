package com.margulan.uniproject.Controller;

import com.margulan.uniproject.Exception.DuplicateEmailException;
import com.margulan.uniproject.Model.Dto.UserDto;
import com.margulan.uniproject.Model.Message;
import com.margulan.uniproject.Model.Task;
import com.margulan.uniproject.Service.CategoryService;
import com.margulan.uniproject.Service.MessageService;
import com.margulan.uniproject.Service.TaskService;
import com.margulan.uniproject.Service.UsersService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/personalPage")
public class PersonalPageController {

    private final TaskService taskService;
    private final UsersService usersService;
    private final CategoryService categoryService;
    private final MessageService messageService;

    public PersonalPageController(TaskService taskService, CategoryService categoryService, UsersService usersService, MessageService messageService) {
        this.taskService = taskService;
        this.categoryService = categoryService;
        this.usersService = usersService;
        this.messageService = messageService;
    }

    @GetMapping("/home")
    public String getPersonalPage(Model model) {
        String loggedUsername = taskService.getLoggedUsernameByEmail(SecurityContextHolder.getContext().getAuthentication().getName());
        model.addAttribute("loginUsername", loggedUsername);
        return "user_page";
    }

    @GetMapping("/manageTasks")
    public String getManageTask(Model model) {
        model.addAttribute("tasksForTheCurrentUser", taskService.getTasksForCurrentUser());
        model.addAttribute("categories", categoryService.getAllCategories());
        model.addAttribute("task", new Task());
        return "user_page";
    }

    @PostMapping("/manageTasks/add")
    public String manageAddTask(@ModelAttribute Task task) {
        taskService.addTaskForCurrentUser(task);
        return "redirect:/personalPage/manageTasks#task";
    }

    @PostMapping("/manageTasks/edit")
    public String manageEditTask(@RequestParam String selectedTaskByTitle, @ModelAttribute Task task) {
        taskService.editTask(selectedTaskByTitle, task);
        return "redirect:/personalPage/manageTasks#task";
    }

    @PostMapping("/manageTasks/delete")
    public String manageDeleteTask(@RequestParam String deleteTaskByTitle) {
        taskService.deleteByTitle(deleteTaskByTitle);
        return "redirect:/personalPage/manageTasks#task";
    }

    @GetMapping("/manageCategories")
    public String getManageCategory(Model model) {
        model.addAttribute("categories", categoryService.getAllCategories());
        return "user_page";
    }

    @PostMapping("/manageCategories/add")
    public String addCategory(@RequestParam String newCategory) {
        if (newCategory != null && !newCategory.isEmpty()) {
            categoryService.addCategory(newCategory);
        }
        return "redirect:/personalPage/manageCategories#category";
    }

    @GetMapping("/manageUsers")
    public String getManageUser(Model model) {
        model.addAttribute("allUsers", usersService.getAllUsers());
        return "user_page";
    }

    @GetMapping("/manageAllTasks")
    public String getManageTasks(Model model) {
        model.addAttribute("allTasks", taskService.getAllTasks());
        model.addAttribute("task", new Task());
        return "user_page";
    }

    @GetMapping("/manageNotifications")
    public String getMessage(Model model) {
        model.addAttribute("allNotifications", messageService.getAllMessages());
        model.addAttribute("notification", new Message());
        return "user_page";
    }

    @PostMapping("/manageNotifications/add")
    public String addMessage(@RequestParam String notificationDescr, @RequestParam String userEmail) {
        messageService.addMessage(notificationDescr, userEmail);
        return "redirect:/personalPage/manageNotifications#notifications";
    }


    @ExceptionHandler(IllegalArgumentException.class)
    public String handleRegistrationException(IllegalArgumentException ex, RedirectAttributes redirectAttributes) {
        String action = ex.getMessage().split(" - ")[0];
        String message = ex.getMessage().split(" - ")[1];
        if (action.equals("Add")) redirectAttributes.addFlashAttribute("illegalArgumentAdd", message);
        if (action.equals("Edit")) redirectAttributes.addFlashAttribute("illegalArgumentEdit", message);
        return "redirect:/personalPage/manageTasks#task";
    }
}
