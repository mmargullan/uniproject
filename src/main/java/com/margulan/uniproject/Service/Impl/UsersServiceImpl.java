package com.margulan.uniproject.Service.Impl;

import com.margulan.uniproject.Config.JwtTokenUtil;
import com.margulan.uniproject.Exception.BadRequestException;
import com.margulan.uniproject.Exception.ResourceNotFoundException;
import com.margulan.uniproject.Mapper.UserMapper;
import com.margulan.uniproject.Model.Dto.UserDto;
import com.margulan.uniproject.Model.User;
import com.margulan.uniproject.Repository.UsersRepository;
import com.margulan.uniproject.Service.RedisService;
import com.margulan.uniproject.Service.UsersService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Service
public class UsersServiceImpl implements UsersService {

    private static final String USERS_CACHE_KEY = "#userId";

    @Autowired
    JwtTokenUtil jwtTokenUtil;

    @Autowired
    private UserMapper userMapper;

    @Autowired
    private UsersRepository usersRepository;

    @Autowired
    private RedisService redisService;

    @Override
    public void createUser(UserDto userDto) {

        Pattern pattern = Pattern.compile("\\w{2,12}+@\\w{2,8}+\\.\\w{2,6}");
        Matcher matcher = pattern.matcher(userDto.getEmail());

        if(matcher.matches()) {
            User user = userMapper.toEntity(userDto);
            usersRepository.save(user);

            // Save user to Redis
            redisService.saveData(USERS_CACHE_KEY, user);
        }else{
            throw new BadRequestException("Invalid email");
        }

    }

    @Override
    public List<UserDto> getUsers(){
        try{
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
        if(usersRepository.existsById(id)){
            usersRepository.deleteById(id);
        }else{
            throw new ResourceNotFoundException("User not found: id - " + id);
        }
    }


    public String authenticate(UserDto userDto) {
        User user = usersRepository.findByUsername(userDto.getUsername());
        if (user != null && user.getPassword().equals(userDto.getPassword())) {
            return jwtTokenUtil.generateToken(user);
        }
        throw new BadRequestException("Invalid credentials");
    }

}
