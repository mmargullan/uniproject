package com.margulan.uniproject.Controller;

import com.margulan.uniproject.Exception.DuplicateEmailException;
import com.margulan.uniproject.Model.Dto.UserDto;
import com.margulan.uniproject.Service.UsersService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

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

    @ExceptionHandler(DuplicateEmailException.class)
    public String handleRegistrationException(DuplicateEmailException ex, Model model) {
        model.addAttribute("duplicateEmailError", ex.getMessage());
        return "register_page";
    }
}
