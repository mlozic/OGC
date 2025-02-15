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

import com.example.secret_store.entity.User;
import com.example.secret_store.service.UserService;

import lombok.AllArgsConstructor;

@AllArgsConstructor
@RestController
@RequestMapping("/user")
public class UserController {

  UserService userService;

  @GetMapping("/{id}")
  public ResponseEntity<User> getUser(@PathVariable final Long id){
      return new ResponseEntity<>(userService.getUser(id), HttpStatus.OK);
  }

  @GetMapping("/all")
  public ResponseEntity<List<User>> getUsers(){
      return new ResponseEntity<>(userService.getUsers(), HttpStatus.OK);
  }

  @PostMapping
  public ResponseEntity<User> saveUser(@RequestBody User user){
    return new ResponseEntity<>(userService.saveUser(user), HttpStatus.CREATED);
  }

  @DeleteMapping("/{id}")
  public ResponseEntity<HttpStatus> deleteUser(@PathVariable final Long id){
    userService.deleteUser(id);
    return new ResponseEntity<>(HttpStatus.NO_CONTENT);
  }

  @PutMapping("/{id}")
  public ResponseEntity<User> updateUser(@RequestBody User user, @PathVariable final Long id){
    return new ResponseEntity<>(userService.updateUser(user, id), HttpStatus.OK);
  }
}
