package com.example.secret_store.service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.example.secret_store.entity.User;
import com.example.secret_store.exception.EntityNotFoundException;
import com.example.secret_store.repository.UserRepository;

import lombok.AllArgsConstructor;

@AllArgsConstructor
@Service
public class UserServiceImpl implements UserService {

  private UserRepository userRepository;
  private BCryptPasswordEncoder passwordEncoder;

  @Override
  public User getUser(final Long id) {
    Optional<User> user = userRepository.findById(id);
    return handleUserOptional(user, id);
  }

  @Override
  public User getUser(String username) {
    Optional<User> user = userRepository.findByUsername(username);
    return handleUserOptional(user, -1L); // If User does not exist App will return -1 as id for UserNotFoundException
                                          // message
  }

  @Override
  public List<String> getUsers() {
    final List<User> listOfUsers = (List<User>) userRepository.findAll();
    List<String> listOfUsernames = listOfUsers.stream()
        .map(User::getUsername)
        .collect(Collectors.toList());
    return listOfUsernames;

  }

  @Override
  public void deleteUser(Long id) {
    getUser(id);
    userRepository.deleteById(id);
  }

  @Override
  public User saveUser(User user) {
    user.setAppPassword(passwordEncoder.encode(user.getAppPassword()));
    return userRepository.save(user);
  }

  @Override
  public User updateUser(User user, final Long id) {
    User updatedUser = getUser(id);
    updatedUser.setRoleType(user.getRoleType());
    updatedUser.setUsername(user.getUsername());
    return userRepository.save(updatedUser);
  }

  static User handleUserOptional(Optional<User> user, final Long id) {
    if (user.isPresent()) {
      return user.get();
    } else
      throw new EntityNotFoundException(id, User.class);
  }
}
