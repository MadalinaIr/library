package com.library.library.dao;

import com.library.library.model.security.Role;

public interface RoleDao {
    public Role findRoleByName(String theRoleName);
}
