package com.amanefer.orderservice.controller;

import com.amanefer.orderservice.model.dto.UserRequest;
import com.amanefer.orderservice.model.entity.User;
import com.amanefer.orderservice.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/v1/users")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @PostMapping
    public ResponseEntity<User> createNewUser(@RequestBody UserRequest userRequest) {
        var response = userService.createNewUser(userRequest);

        return ResponseEntity.ok().body(response);
    }

    @GetMapping
    public ResponseEntity<List<User>> getAllUsers() {
        var response = userService.getAllUsers();

        return ResponseEntity.ok().body(response);
    }

    @GetMapping("/{id}")
    public ResponseEntity<User> getUserById(@PathVariable("id") Long id) {
        var response = userService.getUserById(id);

        return ResponseEntity.ok().body(response);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteUserById(@PathVariable("id") Long id) {
        var response = userService.deleteUserById(id);

        return ResponseEntity.ok().body(response);
    }
}
