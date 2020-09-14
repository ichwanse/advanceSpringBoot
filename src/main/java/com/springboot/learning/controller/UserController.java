package com.springboot.learning.controller;

import com.springboot.learning.model.User;
import com.springboot.learning.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@RestController
@RequestMapping(path = "/api/v1/users")
public class UserController {
    @Autowired
    private UserService userService;

    @GetMapping
    public List<User> fetchUser(){
        return userService.getAllUsers();
    }

    @GetMapping(path = "/id/{userUid}")
    public ResponseEntity<?> fetchUserByUid(@PathVariable("userUid") UUID userUid){
        Optional<User> userOptional = userService.getUser(userUid);
        if(userOptional.isPresent()){
            return ResponseEntity.ok(userOptional);
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body(new ErrorMessage("userUid " + userUid + " not found."));

    }

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Integer> insertNewUser(@RequestBody User user) {
        int result = userService.insertUser(user);
        return getIntegerResponseEntity(result);
    }

    @PutMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> updateUser(@RequestBody User user){
        int result = userService.updateUser(user);
        return  getIntegerResponseEntity(result);
    }

    @DeleteMapping(path = "/id/{userUid}")
    public ResponseEntity<?> deleteUserByUid(@PathVariable("userUid") UUID userUid){
        int result = userService.removeUser(userUid);
        return getIntegerResponseEntity(result);

    }
    private ResponseEntity<Integer> getIntegerResponseEntity(int result) {
        if (result == 1) {
            return ResponseEntity.status(HttpStatus.OK).build();
        }
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
    }

    public class ErrorMessage {
        String errorMessage;

        public ErrorMessage(String errorMessage) {
            this.errorMessage = errorMessage;
        }

        public String getErrorMessage() {
            return errorMessage;
        }

        public void setErrorMessage(String errorMessage) {
            this.errorMessage = errorMessage;
        }
    }
}
