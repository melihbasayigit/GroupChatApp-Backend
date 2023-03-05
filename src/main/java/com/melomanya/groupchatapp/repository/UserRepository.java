package com.melomanya.groupchatapp.repository;

import com.melomanya.groupchatapp.data.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, String> {
}