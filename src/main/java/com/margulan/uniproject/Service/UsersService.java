package com.margulan.uniproject.Service;

import com.margulan.uniproject.Model.Dto.UserDto;
import com.margulan.uniproject.Model.User;

import java.util.List;

public interface UsersService {

    void createUser(UserDto userDto);
    List<UserDto> getAllUsers();
    void deleteUser(String id);
//    String authenticate(UserDto userDto);
    User login(UserDto userDto);
    String getLoggedUsernameByEmail(String email);
    User findByEmail(String email);
    void resetUserWithNewPassword(User user);

}
