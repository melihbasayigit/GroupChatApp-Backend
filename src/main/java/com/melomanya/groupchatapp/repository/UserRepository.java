package com.melomanya.groupchatapp.repository;

import com.melomanya.groupchatapp.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, String> {
}