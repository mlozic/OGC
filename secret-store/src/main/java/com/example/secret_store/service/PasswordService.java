package com.example.secret_store.service;

import java.util.List;

import com.example.secret_store.entity.Password;

public interface PasswordService {
  Password getPassword(final Long id, final String username);

  List<Password> getAllPasswords(final String username);

  List<Password> getAllPasswords();

  List<Password> getDevOpsPasswords();

  void deletePassword(Long id, final String username);

  Password savePassword(Password password, final Long userId);

  Password updatePassword(Password password, final Long passwordId, final String username);
}
