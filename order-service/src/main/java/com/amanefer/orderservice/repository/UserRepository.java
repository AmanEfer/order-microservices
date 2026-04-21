package com.amanefer.orderservice.repository;

import com.amanefer.orderservice.model.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {

    @Query("FROM User u LEFT JOIN FETCH u.roles")
    @Override
    List<User> findAll();

    @Query("FROM User u LEFT JOIN FETCH u.roles WHERE u.username = :username")
    Optional<User> findByUsername(@Param("username") String username);

    boolean existsByUsername(String username);

    boolean existsByEmail(String email);
}
