package com.springboot.learning.service;

import com.springboot.learning.dao.UserDao;
import com.springboot.learning.model.User;

import java.util.List;
import java.util.UUID;

public class UserService {
    private UserDao userDao;

    public UserService(UserDao userDao) {
        this.userDao = userDao;
    }

    public List<User> getAllUsers() {
        return null;
    }

    public User getUser(UUID userUid) {
        return null;
    }

    public int updateUser(User user) {
        return 1;
    }

    public int removeUser(UUID userUid) {
        return 1;
    }

    public int insertUser(UUID userUid, User user) {
        return 1;
    }
}
