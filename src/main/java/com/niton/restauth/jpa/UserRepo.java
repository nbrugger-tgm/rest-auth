package com.niton.restauth.jpa;

import com.niton.restauth.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepo extends JpaRepository<User,String> { }
