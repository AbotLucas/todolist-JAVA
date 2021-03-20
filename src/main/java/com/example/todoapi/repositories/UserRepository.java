package com.example.todoapi.repositories;

import com.example.todoapi.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    List<User> findByEmailAndPassword(String email, String password);
    Optional<User> findByEmail(String email);
    /*@Query(value="SELECT * FROM users u WHERE u.email LIKE %?1%")
    /*@Query(value="SELECT p FROM users p WHERE u.email LIKE %:email% AND u.password LIKE %:password%")
    Optional<User> loginTry(@Param())*/
}
