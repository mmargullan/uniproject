package com.margulan.uniproject.Controller;

import com.margulan.uniproject.Model.Dto.UserDto;
import com.margulan.uniproject.Service.UsersService;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/users")
public class UsersController {

    private final UsersService usersService;

    public UsersController(UsersService usersService) {
        this.usersService = usersService;
    }

    @GetMapping("/all")
    public List<UserDto> getUsers() {
        return usersService.getUsers();
    }

    @PostMapping
    public void createUser(@RequestBody UserDto userDto) {
        usersService.createUser(userDto);
    }

    @DeleteMapping("/{id}")
    public void deleteUser(@PathVariable String id) {
        usersService.deleteUser(id);
    }

    @PostMapping("/authenticate")
    public String authenticate(@RequestBody UserDto userDto) {
        return usersService.authenticate(userDto);
    }

}
