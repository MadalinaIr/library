package com.library.library.service;

import com.library.library.model.security.User;
import org.springframework.security.core.userdetails.UserDetailsService;

public interface UserService extends UserDetailsService {
    public User findByUsername(String username);
}
