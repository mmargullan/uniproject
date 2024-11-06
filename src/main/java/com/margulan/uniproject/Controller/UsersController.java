package com.margulan.uniproject.Controller;

import com.margulan.uniproject.Exception.BadRequestException;
import com.margulan.uniproject.Exception.DuplicateEmailException;
import com.margulan.uniproject.Exception.DuplicateResourceException;
import com.margulan.uniproject.Model.Dto.UserDto;
import com.margulan.uniproject.Model.User;
import com.margulan.uniproject.Service.UsersService;
import jdk.jshell.spi.ExecutionControlProvider;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/users")
public class UsersController {

    private final UsersService usersService;

    public UsersController(UsersService usersService) {
        this.usersService = usersService;
    }

    @GetMapping("/register")
    public String getRegister(Model model) {
        model.addAttribute("registerRequest", new UserDto());
        return "register_page";
    }

    @PostMapping("/register")
    public String register(@ModelAttribute UserDto userDto) {
        usersService.createUser(userDto);
        return "redirect:/users/login";
    }

    @GetMapping("/login")
    public String getLogin(Model model) {
        model.addAttribute("registerRequest", new UserDto());
        return "login_page";
    }

    @PostMapping("/login")
    public String login(@ModelAttribute UserDto userDto) {
        usersService.login(userDto);
        return "redirect:/personalPage/home";
    }

//    @GetMapping("/all")
//    public List<UserDto> getUsers() {
//        return usersService.getUsers();
//    }
//
//    @PostMapping
//    public void createUser(@RequestBody UserDto userDto) {
//        usersService.createUser(userDto);
//    }
//
//    @DeleteMapping("/{id}")
//    public void deleteUser(@PathVariable String id) {
//        usersService.deleteUser(id);
//    }

//    @PostMapping("/authenticate")
//    public String authenticate(@RequestBody UserDto userDto) {
//        return usersService.authenticate(userDto);
//    }

//    @ExceptionHandler(DuplicateResourceException.class)
//    public String handleRegistrationException(DuplicateResourceException ex, Model model) {
//        model.addAttribute("duplicateError", ex.getMessage());
//        return "register_page";
//    }
//
    @ExceptionHandler(DuplicateEmailException.class)
    public String handleRegistrationException(DuplicateEmailException ex, Model model) {
        model.addAttribute("duplicateEmailError", ex.getMessage());
        return "register_page";
    }

//    @ExceptionHandler(LoginException.class)
//    public String handleLoginException(LoginException ex, Model model) {
//        model.addAttribute("errorMessage", ex.getMessage());
//        return "loginError"; // Thymeleaf template for login errors
//    }

}
