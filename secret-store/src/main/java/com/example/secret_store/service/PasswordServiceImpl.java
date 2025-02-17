package com.example.secret_store.service;

import java.nio.charset.StandardCharsets;
import java.util.Base64;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.springframework.stereotype.Service;

import com.example.secret_store.entity.Password;
import com.example.secret_store.entity.User;
import com.example.secret_store.exception.EntityNotFoundException;
import com.example.secret_store.exception.InsufficientUserPermissionsException;
import com.example.secret_store.security.Constants;
import com.example.secret_store.repository.PasswordRepository;

import lombok.AllArgsConstructor;

@AllArgsConstructor
@Service
public class PasswordServiceImpl implements PasswordService {

  private PasswordRepository passwordRepository;
  private UserService userService;

  @Override
  public Password getPassword(final Long passwordId, final String username) {
    Password password = handlePasswordOptional(passwordRepository.findById(passwordId), passwordId);
    User user = userService.getUser(username);
    return getSinglePasswordWithRoleAccess(password, user);
  }

  @Override
  public List<Password> getAllPasswords() {
    return decodedListOfPasswords((List<Password>) passwordRepository.findAll());
  }

  @Override
  public List<Password> getDevOpsPasswords() {
    List<Password> listOfPasswords = Stream
        .of(Optional.ofNullable(passwordRepository.findByUserRole(Constants.ROLE_DEVOPS))
            .orElse(Collections.emptyList()),
            Optional.ofNullable(passwordRepository.findByUserRole(Constants.ROLE_DEVELOPER))
                .orElse(Collections.emptyList()))
        .flatMap(Collection::stream)
        .collect(Collectors.toList());
    return decodedListOfPasswords(listOfPasswords);
  }

  @Override
  public List<Password> getAllPasswords(final String username) {
    User user = userService.getUser(username);
    if (user.getRoleType().name().equals(Constants.ROLE_MANAGEMENT)) {
      return decodedListOfPasswords((List<Password>) passwordRepository.findAll());
    } else if (user.getRoleType().name().equals(Constants.ROLE_DEVOPS)) {
      return getDevOpsPasswords();
    }
    return decodedListOfPasswords(
        passwordRepository.findAllByUserIdAndUserRole(user.getId(), user.getRoleType().name()));
  }

  @Override
  public Password savePassword(Password password, final Long userId) {
    User user = userService.getUser(userId);
    password.setPasswordName(password.getPasswordName());
    password.setPasswords(encoder(password.getPasswords()));
    password.setUser(user);
    password.setUserRole(user.getRoleType().name());
    return passwordRepository.save(password);
  }

  @Override
  public void deletePassword(final Long passwordId, final String username) {
    if (checkIfUsersMatch(passwordId, username)) {
      passwordRepository.deleteById(passwordId);
    } else
      throw new InsufficientUserPermissionsException(userService.getUser(username).getId());
  }

  @Override
  public Password updatePassword(final Password password, final Long passwordId, final String username) {
    if (checkIfUsersMatch(passwordId, username)) {
      Password updatedPassword = handlePasswordOptional(passwordRepository.findById(passwordId), passwordId);
      updatedPassword.setPasswords(encoder(password.getPasswords()));
      updatedPassword.setPasswordName(password.getPasswordName());
      return passwordRepository.save(updatedPassword);
    } else
      throw new InsufficientUserPermissionsException(userService.getUser(username).getId());
  }

  static Password handlePasswordOptional(final Optional<Password> password, final Long passwordId) {
    if (password.isPresent()) {
      return password.get();
    } else
      throw new EntityNotFoundException(passwordId, Password.class);
  }

  private Boolean checkIfUsersMatch(final Long passwordId, final String username) {
    Password password = handlePasswordOptional(passwordRepository.findById(passwordId), passwordId);
    User user = userService.getUser(username);
    return password.getUser().getId() == user.getId();
  }

  private String encoder(final String password) {
    return Base64.getEncoder().encodeToString(password.getBytes(StandardCharsets.UTF_8));
  }

  private Password getSinglePasswordWithRoleAccess(final Password password, final User currentUser) {
    String currentUserRole = currentUser.getRoleType().name();
    if (currentUserRole.equals(Constants.ROLE_MANAGEMENT)) {
      return password;
    } else if (currentUserRole.equals(Constants.ROLE_DEVOPS)
        && !password.getUserRole().equals(Constants.ROLE_MANAGEMENT)) {
      return password;
    } else if (currentUserRole.equals(Constants.ROLE_DEVELOPER)
        && password.getUserRole().equals(Constants.ROLE_DEVELOPER)) {
      return password;
    }
    throw new InsufficientUserPermissionsException(currentUser.getId());
  }

  static List<Password> decodedListOfPasswords(List<Password> listOfPasswords) {
    for (Password password : listOfPasswords) {
      byte[] decodedBytes = Base64.getDecoder().decode(password.getPasswords());
      String decodedPassword = new String(decodedBytes, StandardCharsets.UTF_8);
      password.setPasswords(decodedPassword);
    }
    return listOfPasswords;
  }
}