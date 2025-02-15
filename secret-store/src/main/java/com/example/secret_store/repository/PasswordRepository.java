package com.example.secret_store.repository;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

import com.example.secret_store.entity.Password;

import jakarta.transaction.Transactional;

public interface PasswordRepository extends CrudRepository<Password, Long> {
    List<Password> findAllByUserId(Long userId);
    @Transactional
    void deletebyUserId(Long userId);
}
