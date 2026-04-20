package com.amanefer.orderservice.service;

import com.amanefer.orderservice.model.dto.UserRequest;
import com.amanefer.orderservice.model.entity.User;

import java.util.List;

public interface UserService {

    User createNewUser(UserRequest userRequest);

    List<User> getAllUsers();

    User getUserById(Long id);

    String deleteUserById(Long id);
}
