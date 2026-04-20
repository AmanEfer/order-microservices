package com.amanefer.orderservice.repository;

import com.amanefer.orderservice.model.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {
}
