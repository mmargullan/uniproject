package com.margulan.uniproject.Controller;

import com.margulan.uniproject.Model.Dto.UserDto;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/personalPage")
public class PersonalPageController {

    @GetMapping("/home")
    public String getLogin(Model model) {
        model.addAttribute("user", new UserDto());
        return "user_page";
    }

//    @PostMapping("/home")
//    public String login(@ModelAttribute UserDto userDto) {
//        usersService.login(userDto);
//        return "user_page";
//    }
}
