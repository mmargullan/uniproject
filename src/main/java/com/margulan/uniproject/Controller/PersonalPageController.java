package com.margulan.uniproject.Controller;

import com.margulan.uniproject.Model.Message;
import com.margulan.uniproject.Model.PasswordResetToken;
import com.margulan.uniproject.Model.Task;
import com.margulan.uniproject.Model.User;
import com.margulan.uniproject.Repository.TasksRepository;
import com.margulan.uniproject.Service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;

@Controller
@RequestMapping("/personalPage")
public class PersonalPageController {

    @Value("${app.url}")  // Base URL for password reset (e.g., http://localhost:8080)
    private String appUrl;

    @Autowired
    TasksRepository tasksRepository;

    private final TaskService taskService;
    private final UsersService usersService;
    private final CategoryService categoryService;
    private final MessageService messageService;
    private final PasswordResetTokenService tokenService;

    public PersonalPageController(TaskService taskService, CategoryService categoryService,
                                  UsersService usersService, MessageService messageService,
                                  PasswordResetTokenService tokenService) {
        this.taskService = taskService;
        this.categoryService = categoryService;
        this.usersService = usersService;
        this.messageService = messageService;
        this.tokenService = tokenService;
    }

    // Username of the logged-in user
    // @ModelAttribute to make "loginUsername" available automatically in the model for all methods within the controller
    @ModelAttribute("loginUsername")
    public String addLoggedUsernameToModel() {
        return usersService.getLoggedUsernameByEmail(
                SecurityContextHolder.getContext().getAuthentication().getName());
    }

    @GetMapping("/home")
    public String getPersonalPage() {
        return "user_page";
    }

    @GetMapping("/manageAllTasks")
    public String getManageTask(Model model) {
        model.addAttribute("allTasks", taskService.getAllTasks());
        model.addAttribute("categories", categoryService.getAllCategories());
        model.addAttribute("task", new Task());
        return "user_page";
    }

    @PostMapping("/manageAllTasks/add")
    public String manageAddTask(@ModelAttribute Task task) {
        taskService.addTaskForCurrentUser(task);
        return "redirect:/personalPage/manageAllTasks#tasks";
    }

    @PostMapping("/manageAllTasks/edit")
    public String manageEditTask(@RequestParam String selectedTaskByTitle, @ModelAttribute Task task) {
        taskService.editTask(selectedTaskByTitle, task);
        return "redirect:/personalPage/manageAllTasks#tasks";
    }

    @PostMapping("/manageAllTasks/delete")
    public String manageDeleteTask(@RequestParam String deleteTaskByTitle) {
        taskService.deleteByTitle(deleteTaskByTitle);
        return "redirect:/personalPage/manageAllTasks#tasks";
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

    @GetMapping("/manageUserTasks")
    public String getManageTasks(Model model,
                                       @RequestParam(name = "filterTitle", required = false) String title,
                                       @RequestParam(name = "filterStatus", required = false) String status,
                                       @RequestParam(name = "filterPriority", required = false) String priority,
                                       @RequestParam(required = false, defaultValue = "0") int page,
                                       @RequestParam(required = false, defaultValue = "10") int size) {
        Pageable pageable = PageRequest.of(page, size, Sort.by("id"));
        model.addAttribute("tasksUserPaginated",
                taskService.searchTasksForCurrentUser(title, status, priority, pageable));
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

    @GetMapping("/manageUserNotifications")
    public String getUserMessage(Model model) {
        model.addAttribute("userNotifications", messageService.getMessagesForCurrentUser());
        return "user_page";
    }

//    @GetMapping("/manageTasksPaginated")
//    public String getManageTaskPaginated(Model model, @RequestParam(required = true) int limit) {
//        Pageable pageable = PageRequest.of(1, limit);
//        return "user_page";
//    }

    @GetMapping("/settings")
    public String getSettings() {
        return "user_page";
    }

    @PostMapping("/settings/passwordResetRequest")
    public String processPasswordResetRequest(@RequestParam String email, Model model) {
        User user = usersService.findByEmail(email);
        PasswordResetToken token = tokenService.createPasswordResetToken(user);

        // Send email with the reset link
        tokenService.sendPasswordResetEmail(appUrl, user.getEmail(), token.getToken());
        model.addAttribute("requestSentSuccess", "Request successfully sent");
        return "redirect:/personalPage/settings#settings";
    }

//    @GetMapping("/searchTasks")
//    public List<Task> searchTasks(
//            @RequestParam int user_id,
//            @RequestParam(required = false) String title,
//            @RequestParam(required = false) String status,
//            @RequestParam(required = false) String priority,
//            @RequestParam int page) {
//        Pageable pageable = PageRequest.of(page, 10);
//        return tasksRepository.searchTasksByUserId(user_id, title, status, priority, pageable);
//    }

    @ExceptionHandler(IllegalArgumentException.class)
    public String handleRegistrationException(IllegalArgumentException ex, RedirectAttributes redirectAttributes) {
        String action = ex.getMessage().split(" - ")[0];
        String message = ex.getMessage().split(" - ")[1];
        if (action.equals("Add")) redirectAttributes.addFlashAttribute("illegalArgumentAdd", message);
        if (action.equals("Edit")) redirectAttributes.addFlashAttribute("illegalArgumentEdit", message);
        return "redirect:/personalPage/manageAllTasks#tasks";
    }
}
