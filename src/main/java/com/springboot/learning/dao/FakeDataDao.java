package com.springboot.learning.dao;

import com.springboot.learning.model.User;

import java.util.*;

public class FakeDataDao implements UserDao{
    //Create inisilize data from Model DAO
    private static Map<UUID, User> database;

    //Add User data
    static {
        database = new HashMap<>();
        UUID ikhwanUserUid = UUID.randomUUID();
        database.put(ikhwanUserUid, new User(ikhwanUserUid, "Ikhwan", "Suciadi", User.Gender.MALE, 28, "ichwan100@gmail.com"));
    }

}
