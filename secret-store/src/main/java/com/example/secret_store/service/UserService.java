package com.example.secret_store.service;

import java.util.List;

import com.example.secret_store.entity.User;

public interface UserService {
  User getUser(final Long id);
  List<User> getUsers();
  void deleteUser(final Long id);
  User saveUser(User user);
  User updateUser(User user, Long userId);
}
