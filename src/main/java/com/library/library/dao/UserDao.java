package com.library.library.dao;

import com.library.library.model.security.User;

public interface UserDao {
    User findByUsername(String username);
}
