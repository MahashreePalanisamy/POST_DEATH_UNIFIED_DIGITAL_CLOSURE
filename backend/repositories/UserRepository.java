package com.postdeath.repositories;

import com.postdeath.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.Optional;
import java.util.List;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByAadharNumber(String aadharNumber);
    Optional<User> findByEmail(String email);
    List<User> findByStatus(User.UserStatus status);
    List<User> findByState(String state);
}