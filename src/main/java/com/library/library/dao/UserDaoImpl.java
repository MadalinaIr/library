package com.library.library.dao;

import com.library.library.model.security.User;
import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;

import org.springframework.stereotype.Repository;

@Repository
public class UserDaoImpl implements UserDao{
    EntityManager entityManager;

    public UserDaoImpl(EntityManager theEntityManager) {
        this.entityManager = theEntityManager;
    }

    @Override
    public User findByUsername(String userName) {
// retrieve/read from database using username
        TypedQuery<User> theQuery = entityManager.createQuery("from User where username=:uName", User.class);
        theQuery.setParameter("uName", userName);

        User theUser = null;
        try {
            theUser = theQuery.getSingleResult();
        } catch (Exception e) {
            theUser = null;
        }

        return theUser;
    }

}
