package com.example.secret_store.service;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.example.secret_store.entity.Password;
import com.example.secret_store.entity.User;
import com.example.secret_store.exception.PasswordNotFoundException;
import com.example.secret_store.repository.UserRepository;
import com.example.secret_store.repository.PasswordRepository;

import lombok.AllArgsConstructor;

@AllArgsConstructor
@Service
public class PasswordServiceImpl implements PasswordService {

  PasswordRepository passwordRepository;
  UserRepository userRepository;

  @Override
  public Password getPassword(final Long id) {
    Optional<Password> password = passwordRepository.findById(id);
    return handlePasswordOptional(password, id);
  }

  @Override
  public List<Password> getUserPasswords(final Long userId) {
    return passwordRepository.findAllByUserId(userId);
  }

  @Override
  public List<Password> getAllPasswords() {
    return (List<Password>)passwordRepository.findAll();
  }

  @Override
  public Password savePassword(Password password, final Long userId) {
    User user = UserServiceImpl.handleUserOptional(userRepository.findById(userId), userId);
    password.setUser(user);
    return passwordRepository.save(password);
  }

  @Override
  public void deletePassword(Long id) {
    passwordRepository.deleteById(id);
  }

  @Override
  public Password updatePassword(final Password password, final Long id) {
    Password updatedPassword = getPassword(id);
    updatedPassword.setPasswords(password.getPasswords());
    updatedPassword.setPasswordName(password.getPasswordName());
    return passwordRepository.save(updatedPassword);
  }

  static Password handlePasswordOptional(Optional<Password> password, final Long id){
    if (password.isPresent()) {
      return password.get();
    } else throw new PasswordNotFoundException(id);
  }
}
