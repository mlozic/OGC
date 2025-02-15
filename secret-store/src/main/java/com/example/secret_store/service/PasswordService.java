package com.example.secret_store.service;

import java.util.List;

import com.example.secret_store.entity.Password;

public interface PasswordService {
  Password getPassword(Long id);
  List<Password> getUserPasswords(Long userId);
  List<Password> getAllPasswords();
  void deletePassword(Long id);
  Password savePassword(Password password, Long userId);
  Password updatePassword(Password password, Long passwordId);
}
