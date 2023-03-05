package com.melomanya.groupchatapp.service;

import com.melomanya.groupchatapp.model.User;
import com.melomanya.groupchatapp.repository.UserRepository;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    private final UserRepository repository;

    public UserService(UserRepository repository) {
        this.repository = repository;
    }

    public void registerUser(User user) {
        // add user to database. If already exist rewrite user
        repository.save(user);
    }

    public String getDisplayNameFromId(String socketId) {
        // get display name from user table with socket id
        User user = repository.findById(socketId).orElse(null);
        assert user != null;
        return user.getDisplayName();
    }
}