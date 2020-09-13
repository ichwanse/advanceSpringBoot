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

    @Override
    public List<User> selectAllUsers() {
        return new ArrayList<>(database.values());
    }

    @Override
    public User selectUserByUserUid(UUID userUid) {
        return database.get(userUid);
    }

    @Override
    public int updateUser(User user) {
        database.put(user.getUserUid(), user);
        return 1;
    }

    @Override
    public int deleteUserByUid(UUID userUid) {
        database.remove(userUid);
        return 1;
    }

    @Override
    public int insertUser(UUID userUid, User user) {
        database.put(userUid, user);
        return 1;
    }
}
