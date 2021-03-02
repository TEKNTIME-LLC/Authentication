package com.tekntime.mfa.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.tekntime.mfa.model.UserLogin;

@Repository
public interface UserRepository extends JpaRepository<UserLogin, Long> {
    UserLogin findByEmail(String email);

    void delete(UserLogin user);

}
