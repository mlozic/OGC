package com.example.secret_store.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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

import lombok.AllArgsConstructor;

@AllArgsConstructor
@RestController
@RequestMapping("/password")
public class PasswordController {

  PasswordService passwordService;

  @GetMapping("/{id}")
  public ResponseEntity<Password> getPassword(@PathVariable final Long id){
    return new ResponseEntity<>(passwordService.getPassword(id), HttpStatus.OK);
  }

    @GetMapping("/user/{id}")
  public ResponseEntity<List<Password>> getUserPassword(@PathVariable final Long userId){
    return new ResponseEntity<>(passwordService.getUserPasswords(userId), HttpStatus.OK);
  }

  @GetMapping("/all")
  public ResponseEntity<List<Password>> getAllPasswords(){
    return new ResponseEntity<>(passwordService.getAllPasswords(), HttpStatus.OK);
  }

  @PostMapping("/{userId}")
  public ResponseEntity<Password> savePassword(@RequestBody Password password, @PathVariable Long userId){
    return new ResponseEntity<>(passwordService.savePassword(password, userId), HttpStatus.CREATED);
  }

  @DeleteMapping("/{id}")
  public ResponseEntity<HttpStatus> deletePassword(@PathVariable final Long id){
    passwordService.deletePassword(id);
    return new ResponseEntity<>(HttpStatus.OK);
  }

  @PutMapping("/{id}")
  public ResponseEntity<Password> updatePassword(@RequestBody Password password, @PathVariable final Long id){
    return new ResponseEntity<>(passwordService.updatePassword(password, id), HttpStatus.OK);
  }
}
