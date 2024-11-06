package com.margulan.uniproject.Service;

import com.margulan.uniproject.Model.Dto.UserDto;
import com.margulan.uniproject.Model.User;

import java.util.List;

public interface UsersService {

    void createUser(UserDto userDto);
    List<UserDto> getUsers();
    void deleteUser(String id);
//    String authenticate(UserDto userDto);
    User login(UserDto userDto);

}
