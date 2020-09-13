package com.springboot.learning.dao;

import com.springboot.learning.model.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

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
        assertThat(users).hasSize(1);

        User user = users.get(0);

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
        assertThat(optionalUser.isPresent()).isTrue();
        assertThat(optionalUser.get()).isEqualToComparingFieldByField(anu);
    }

    @Test
    void shouldNotSelectUserByRandomUid() {
        Optional<User> randomUid = fakeDataDao.selectUserByUserUid(UUID.randomUUID());
        assertThat(randomUid.isPresent()).isFalse();
    }
    @Test
    void updateUser() {
    }

    @Test
    void deleteUserByUid() {
    }

    @Test
    void insertUser() {
    }
}