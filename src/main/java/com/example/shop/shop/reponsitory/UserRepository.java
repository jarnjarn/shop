package com.example.shop.shop.reponsitory;

import com.example.shop.shop.Entity.User;
import com.example.shop.shop.common.Credential;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Integer> {
    User findByUsername(String username);

    //@Query selectNewUserCredentialByPhone

    @Query("select new com.example.shop.shop.common.Credential(u.password, u.role,false ) from User u where u.username = ?1")

    Optional<Credential> selectNewUserCredentialByUsername(String username);

}

