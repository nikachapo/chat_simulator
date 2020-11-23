package com.company.service;

import com.company.model.Message;
import com.company.model.User;
import com.sun.istack.internal.Nullable;

import java.util.ArrayList;
import java.util.List;

public class ChatService {

    private static final List<Message> messages = new ArrayList<>();
    private static final List<User> users =  new ArrayList<>();


    public static synchronized void addUser(User user) {
        users.add(user);
    }

    public List<User> getUsers() {
        return users;
    }

    @Nullable
    public static User getUserByNumber(String number) {
        for (User u : users) {
            if (number.equals(u.getName())) return u;
        }
        return null;
    }

    public void sendMessage(Message message) {
        messages.add(message);
    }

    public List<Message> getMessages() {
        return messages;
    }
}