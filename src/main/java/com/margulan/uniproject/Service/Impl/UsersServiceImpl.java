package com.margulan.uniproject.Service.Impl;

import com.margulan.uniproject.Exception.*;
import com.margulan.uniproject.Mapper.UserMapper;
import com.margulan.uniproject.Model.Dto.UserDto;
import com.margulan.uniproject.Model.User;
import com.margulan.uniproject.Repository.UsersRepository;
import com.margulan.uniproject.Service.RedisService;
import com.margulan.uniproject.Service.UsersService;
import jdk.jshell.spi.ExecutionControlProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Service
public class UsersServiceImpl implements UsersService {

    private static final String USERS_CACHE_KEY = "#userId";

//    @Autowired
//    JwtTokenUtil jwtTokenUtil;

    @Autowired
    private UserMapper userMapper;

    @Autowired
    private UsersRepository usersRepository;

    @Autowired
    private RedisService redisService;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    public void createUser(UserDto userDto) {

        if (usersRepository.findByEmail(userDto.getEmail()).isPresent()) {
//            throw new DuplicateResourceException("Duplicate email -> " + userDto.getEmail());
            throw new DuplicateEmailException("Duplicate email");
        } else {
            User user = userMapper.toEntity(userDto);
            user.setPassword(passwordEncoder.encode(userDto.getPassword()));
            user.setRole("USER");
            usersRepository.save(user);
        }

            // Save user to Redis
//            redisService.saveData(USERS_CACHE_KEY, user);

    }

    @Override
    public List<UserDto> getUsers() {
        try {
            List<User> users = usersRepository.findAll();
            List<UserDto> userDtos = new ArrayList<>();
            for (User user : users) {
                userDtos.add(userMapper.toDto(user));
            }
            return userDtos;
        } catch (RuntimeException e) {
            throw new RuntimeException("Cannot get users.");
        }
    }

    @Override
    public void deleteUser(String id) {
        if (usersRepository.existsById(id)) {
            usersRepository.deleteById(id);
        } else {
            throw new ResourceNotFoundException("User not found: id - " + id);
        }
    }

//    @Override
//    public String authenticate(UserDto userDto) {
//        User user = usersRepository.findByUsername(userDto.getUsername());
//        if (user != null && user.getPassword().equals(userDto.getPassword())) {
//            return jwtTokenUtil.generateToken(user);
//        }
//        throw new BadRequestException("Invalid credentials");
//    }

    @Override
    public User login(UserDto userDto) {
        User user = userMapper.toEntity(userDto);
        User foundUser = usersRepository.findByEmail(user.getEmail()).orElseThrow(
                () -> new ResourceNotFoundException("Incorrect email or password")
        );

        if (passwordEncoder.matches(user.getPassword(), foundUser.getPassword())) {
            return foundUser;
        } else {
            throw new UserNotFoundException("User not found");
        }
    }

}
