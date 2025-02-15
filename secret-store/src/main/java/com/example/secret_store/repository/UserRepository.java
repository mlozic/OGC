package com.example.secret_store.repository;

import org.springframework.data.repository.CrudRepository;

import com.example.secret_store.entity.User;

public interface UserRepository extends CrudRepository<User, Long> {
  
}
