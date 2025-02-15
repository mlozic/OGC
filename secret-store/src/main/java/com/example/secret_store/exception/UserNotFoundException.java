package com.example.secret_store.exception;

public class UserNotFoundException extends RuntimeException{
  public UserNotFoundException(final Long id) {
        super("User with id: '" + id + "' does not exist in our store");
    }
}
