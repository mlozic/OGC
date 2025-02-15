package com.example.secret_store.exception;

public class PasswordNotFoundException extends RuntimeException{
  public PasswordNotFoundException(Long id) {
        super("Password id: '" + id + "' does not exist in our store");
    }
}
