package com.margulan.uniproject.Service.Impl;

import com.margulan.uniproject.Exception.UserNotFoundException;
import com.margulan.uniproject.Model.Message;
import com.margulan.uniproject.Model.User;
import com.margulan.uniproject.Repository.MessageRepository;
import com.margulan.uniproject.Repository.UsersRepository;
import com.margulan.uniproject.Service.MessageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MessageServiceImpl implements MessageService {

    @Autowired
    UsersRepository usersRepository;

    @Autowired
    MessageRepository messageRepository;

    private Authentication getLoggedUser() {
        return SecurityContextHolder.getContext().getAuthentication();
    }

    @Override
    public List<Message> getAllMessages() {
        return messageRepository.findAll();
    }

    @Override
    public List<Message> getMessagesForCurrentUser() {
        User user = usersRepository.findByEmail(getLoggedUser().getName()).orElseThrow(
                () -> new UserNotFoundException("User not found"));
        return messageRepository.findByUserId(user.getId());
    }

    @Override
    public void addMessage(String notificationDescr, String userEmail) {
        User user = usersRepository.findByEmail(userEmail).orElseThrow(
                () -> new UserNotFoundException("User not found"));
        messageRepository.save(new Message(notificationDescr, user));
    }

}
