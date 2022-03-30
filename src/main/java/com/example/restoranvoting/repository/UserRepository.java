package com.example.restoranvoting.repository;

import org.springframework.transaction.annotation.Transactional;
import com.example.restoranvoting.model.User;

import java.util.Optional;

@Transactional(readOnly = true)
public interface UserRepository extends BaseRepository<User> {
    Optional<User> getByEmail(String email);
}