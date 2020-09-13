package com.springboot.learning.dao;

import com.springboot.learning.model.User;

import java.util.List;
import java.util.UUID;

public interface UserDao {

    List<User> selectAllUsers();

    User selectUserByUserUid(UUID userUid);

    int updateUser(User user);

    int deleteUserByUid(UUID userUid);

    int insertUser(UUID userUid, User user);
}
