package com.springboot.learning.dao;

import com.google.inject.internal.util.ImmutableList;
import com.springboot.learning.model.User;
import com.springboot.learning.service.UserService;
import org.hibernate.validator.internal.util.stereotypes.Immutable;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;


class UserDaoTest {
    @Mock
    private FakeDataDao fakeDataDao;
    private UserService userService;


    @BeforeEach
    void setUp() {
        MockitoAnnotations.initMocks(this);
        userService = new UserService(fakeDataDao);
    }

    @Test
    void shouldGetAllUsers() {
        UUID anuUserUId = UUID.randomUUID();
        User anu = new User(anuUserUId, "Anulah", "inilah", User.Gender.FEMALE, 25, "annu@email.com");

        ImmutableList<User> users = new ImmutableList.Builder<User>().add(anu).build();
        given(fakeDataDao.selectAllUsers()).willReturn(users);

        List<User> allUsers = userService.getAllUsers();
        assertThat(allUsers).hasSize(1);
        User user = users.get(0);

        assertUserFields(user);
    }



    @Test
    void shouldGetUser() {
        UUID anuUserUId = UUID.randomUUID();
        User anu = new User(anuUserUId, "Anulah", "inilah", User.Gender.FEMALE, 25, "annu@email.com");

        given(fakeDataDao.selectUserByUserUid(anuUserUId)).willReturn(Optional.of(anu));
        Optional<User> userOptional = userService.getUser(anuUserUId);

        assertThat(userOptional.isPresent()).isTrue();

        User user = userOptional.get();
        assertUserFields(user);
    }

    @Test
    void shouldUpdateUser() {
        UUID anuUserUId = UUID.randomUUID();
        User anu = new User(anuUserUId, "Anulah", "inilah", User.Gender.FEMALE, 25, "annu@email.com");

        given(fakeDataDao.selectUserByUserUid(anuUserUId)).willReturn(Optional.of(anu));
        given(fakeDataDao.updateUser(anu)).willReturn(1);

        ArgumentCaptor<User> captor = ArgumentCaptor.forClass(User.class);

        int updateResult = userService.updateUser(anu);
        verify(fakeDataDao).selectUserByUserUid(anuUserUId);
        verify(fakeDataDao).updateUser(captor.capture());

        assertThat(updateResult).isEqualTo(1);
        User user = captor.getValue();
        assertUserFields(user);
    }

    @Test
    void shouldDeleteUserByUid() {
        UUID anuUserUId = UUID.randomUUID();
        User anu = new User(anuUserUId, "Anulah", "inilah", User.Gender.FEMALE, 25, "annu@email.com");

        given(fakeDataDao.selectUserByUserUid(anuUserUId)).willReturn(Optional.of(anu));
        given(fakeDataDao.deleteUserByUid(anuUserUId)).willReturn(1);

        ArgumentCaptor<UUID> captor = ArgumentCaptor.forClass(UUID.class);

        int deleteUserUid = userService.removeUser(anuUserUId);
        verify(fakeDataDao).selectUserByUserUid(anuUserUId);
        verify(fakeDataDao).deleteUserByUid(captor.capture());

        assertThat(deleteUserUid).isEqualTo(1);
        UUID user = captor.getValue();
        assertThat(user).isEqualTo(anuUserUId);

    }

    @Test
    void shouldInsertUser() {
        User anu = new User(null, "Anulah", "inilah", User.Gender.FEMALE, 25, "annu@email.com");

        given(fakeDataDao.insertUser(any(UUID.class), eq(anu))).willReturn(1);
        ArgumentCaptor<User> captor = ArgumentCaptor.forClass(User.class);

        int insertResult = userService.insertUser(anu);
        verify(fakeDataDao).insertUser(any(UUID.class), captor.capture());

        User user = captor.getValue();
        assertThat(user).isEqualTo(anu);

        assertThat(insertResult).isEqualTo(1);
    }

    private void assertUserFields(User user) {
        assertThat(user.getAge()).isEqualTo(25);
        assertThat(user.getFirstName()).isEqualTo("Anulah");
        assertThat(user.getLastName()).isEqualTo("inilah");
        assertThat(user.getGender()).isEqualTo(User.Gender.FEMALE);
        assertThat(user.getEmail()).isEqualTo("annu@email.com");
        assertThat(user.getUserUid()).isNotNull();
    }
}