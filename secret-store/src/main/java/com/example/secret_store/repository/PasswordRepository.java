package com.example.secret_store.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.repository.CrudRepository;

import com.example.secret_store.entity.Password;

import jakarta.transaction.Transactional;

public interface PasswordRepository extends CrudRepository<Password, Long> {
    Optional<Password> findByIdAndUserId(final Long passwordId, final Long userId);

    Optional<Password> findById(final Long passwordId);

    List<Password> findAllByUserId(final Long userId);

    List<Password> findAllByUserIdAndUserRole(final Long userId, final String userRole);

    List<Password> findByUserRole(final String userRole);

    @Transactional
    void deleteByUserId(final Long userId);
}
