package com.example.secret_store.repository;

import java.util.Optional;

import org.springframework.data.repository.CrudRepository;

import com.example.secret_store.entity.User;

public interface UserRepository extends CrudRepository<User, Long> {
  Optional<User> findByUsername(String username);
}
