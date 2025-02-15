package com.example.secret_store.service;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.example.secret_store.entity.User;
import com.example.secret_store.exception.UserNotFoundException;
import com.example.secret_store.repository.UserRepository;

import lombok.AllArgsConstructor;

@AllArgsConstructor
@Service
public class UserServiceImpl implements UserService {

  UserRepository userRepository;
  
  @Override
  public User getUser(final Long id) {
    Optional<User> user = userRepository.findById(id);
    return handleUserOptional(user, id);
  }

  @Override
  public List<User> getUsers() {
    return (List<User>) userRepository.findAll();
  }

  @Override
  public void deleteUser(Long id) {
    getUser(id);
    userRepository.deleteById(id);
  }

  @Override
  public User saveUser(User user) {
    return userRepository.save(user);
  }

  @Override
  public User updateUser(User user, final Long id) {
    User updatedUser = getUser(id);
    updatedUser.setRoleType(user.getRoleType());
    updatedUser.setUsername(user.getUsername());
    return userRepository.save(updatedUser);
  }
  
  static User handleUserOptional(Optional<User> user, final Long id){
    if (user.isPresent()) {
      return user.get();
    } else throw new UserNotFoundException(id);
  }
}
