package com.rupesh.userservice.repository;

import com.rupesh.userservice.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    @Query(nativeQuery = true, value = "SELECT * FROM users WHERE LOWER(email) = LOWER(?1)")
    Optional<User> findByUsername(final String username);

    @Query(nativeQuery = true, value = "SELECT * FROM users WHERE user_id = ?1")
    Optional<User> findByUserId(final String userId);

}
