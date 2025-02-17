package com.example.secret_store.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.secret_store.entity.Password;
import com.example.secret_store.service.PasswordService;

@RestController
@RequestMapping("/password")
public class PasswordController {

  @Autowired
  PasswordService passwordService;

  @GetMapping("/{id}")
  public ResponseEntity<Password> getPassword(@PathVariable final Long id) {
    Authentication auth = SecurityContextHolder.getContext().getAuthentication();
    return new ResponseEntity<>(passwordService.getPassword(id, auth.getName()), HttpStatus.OK);
  }

  @GetMapping("/all")
  public ResponseEntity<List<Password>> getAllPasswords() {
    Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
    return new ResponseEntity<>(passwordService.getAllPasswords(authentication.getName()), HttpStatus.OK);
  }

  @PostMapping("/{userId}")
  public ResponseEntity<Password> savePassword(@RequestBody Password password, @PathVariable final Long userId) {
    return new ResponseEntity<>(passwordService.savePassword(password, userId), HttpStatus.CREATED);
  }

  @DeleteMapping("/{id}")
  public ResponseEntity<HttpStatus> deletePassword(@PathVariable final Long id) {
    Authentication auth = SecurityContextHolder.getContext().getAuthentication();
    passwordService.deletePassword(id, auth.getName());
    return new ResponseEntity<>(HttpStatus.OK);
  }

  @PutMapping("/{id}")
  public ResponseEntity<Password> updatePassword(@RequestBody Password password, @PathVariable final Long id) {
    Authentication auth = SecurityContextHolder.getContext().getAuthentication();
    return new ResponseEntity<>(passwordService.updatePassword(password, id, auth.getName()), HttpStatus.OK);
  }
}
