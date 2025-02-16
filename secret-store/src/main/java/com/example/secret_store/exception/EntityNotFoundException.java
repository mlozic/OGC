package com.example.secret_store.exception;

public class EntityNotFoundException extends RuntimeException{
  public EntityNotFoundException(final Long id, Class<?> entity){
      super(entity.getSimpleName() + "with id: " + id + "does not exist");
  }
}
