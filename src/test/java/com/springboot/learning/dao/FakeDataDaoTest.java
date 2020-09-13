package com.springboot.learning.dao;

import com.springboot.learning.model.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.assertj.core.api.Assertions.as;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

class FakeDataDaoTest {

    private FakeDataDao fakeDataDao;

    @BeforeEach
    void setUp() throws Exception{
        this.fakeDataDao = new FakeDataDao();
    }

    @Test
    public void shouldSelectAllUsers() throws Exception{
        List<User> users = fakeDataDao.selectAllUsers();
        //prove test will get users has Size 1
        assertThat(users).hasSize(1);

        //prove test get user with index 0 is true
        User user = users.get(0);

        //prove the user with index 0 have value is equal with test input value
        assertThat(user.getAge()).isEqualTo(28);
        assertThat(user.getFirstName()).isEqualTo("Ikhwan");
        assertThat(user.getLastName()).isEqualTo("Suciadi");
        assertThat(user.getGender()).isEqualTo(User.Gender.MALE);
        assertThat(user.getEmail()).isEqualTo("ichwan100@gmail.com");
        assertThat(user.getUserUid()).isNotNull();
    }

    @Test
    void shouldSelectUserByUserUid() {
        UUID anuUserUId = UUID.randomUUID();
        User anu = new User(anuUserUId, "Anulah", "inilah", User.Gender.FEMALE, 25, "annu@email.com");
        fakeDataDao.insertUser(anuUserUId, anu);
        assertThat(fakeDataDao.selectAllUsers()).hasSize(2);

        Optional<User> optionalUser = fakeDataDao.selectUserByUserUid(anuUserUId);

        //prove that optionalUser has present in our fakedata is true
        assertThat(optionalUser.isPresent()).isTrue();

        //prove that optionalUser field is equal to field of user with username anu
        assertThat(optionalUser.get()).isEqualToComparingFieldByField(anu);
    }

    @Test
    void shouldNotSelectUserByRandomUid() {
        Optional<User> randomUid = fakeDataDao.selectUserByUserUid(UUID.randomUUID());
        //prove that randomUid is not present in our user
        assertThat(randomUid.isPresent()).isFalse();
    }
    @Test
    void shouldUpdateUser() {
        UUID ikhwanUid = fakeDataDao.selectAllUsers().get(0).getUserUid(); //and this code line will return userUid with user with index 0
        User newUser = new User(ikhwanUid, "Anulah", "inilah", User.Gender.FEMALE, 25, "annu@email.com");
        fakeDataDao.updateUser(newUser);

        Optional<User> optionalUser = fakeDataDao.selectUserByUserUid(ikhwanUid);
        assertThat(optionalUser.isPresent()).isTrue();

        assertThat(fakeDataDao.selectAllUsers()).hasSize(1);
        assertThat(optionalUser.get()).isEqualToComparingFieldByField(newUser);
    }

    @Test
    void deleteUserByUid() {
        UUID ikhwanUid = fakeDataDao.selectAllUsers().get(0).getUserUid(); //and this code line will return userUid with user with index 0
        fakeDataDao.deleteUserByUid(ikhwanUid);

        //prove that user with ikhwanUid is not present in our fake data anymore
        assertThat(fakeDataDao.selectUserByUserUid(ikhwanUid).isPresent()).isFalse();
        //prove that fake Data is empty
        assertThat(fakeDataDao.selectAllUsers()).isEmpty();
    }

    @Test
    void insertUser() {
        UUID userUid = UUID.randomUUID();
        User newUser = new User(userUid, "Anulah", "inilah", User.Gender.FEMALE, 25, "annu@email.com");
        fakeDataDao.insertUser(userUid, newUser);
        List<User> users = fakeDataDao.selectAllUsers();
        assertThat(users).hasSize(2);
        assertThat(fakeDataDao.selectUserByUserUid(userUid).get()).isEqualToComparingFieldByField(newUser);

    }
}