package com.example.secret_store.exception;

public class InsufficientUserPermissionsException extends RuntimeException {
  public InsufficientUserPermissionsException(final Long userId) {
    super("User with id: " + userId + " does not have rights to view this item");
  }
}
