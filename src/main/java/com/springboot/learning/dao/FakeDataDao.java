package com.springboot.learning.dao;

import com.springboot.learning.model.User;
import org.springframework.stereotype.Repository;

import java.util.*;

@Repository
public class FakeDataDao implements UserDao{
    //Create inisilize data from Model DAO
    private Map<UUID, User> database;

    //Add User data


    public FakeDataDao() {
        database = new HashMap<>();
        UUID ikhwanUserUid = UUID.randomUUID();
        database.put(ikhwanUserUid, new User(ikhwanUserUid, "Ikhwan", "Suciadi", User.Gender.MALE, 28, "ichwan100@gmail.com"));
    }

    @Override
    public List<User> selectAllUsers() {
        return new ArrayList<>(database.values());
    }

    @Override
    public Optional<User> selectUserByUserUid(UUID userUid) {
        return Optional.ofNullable(database.get(userUid));
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
