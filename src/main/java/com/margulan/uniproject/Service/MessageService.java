package com.margulan.uniproject.Service;

import com.margulan.uniproject.Model.Message;

import java.util.List;

public interface MessageService {

    List<Message> getAllMessages();
    List<Message> getMessages();
    void addMessage(String notificationDescr, String userEmail);

}
