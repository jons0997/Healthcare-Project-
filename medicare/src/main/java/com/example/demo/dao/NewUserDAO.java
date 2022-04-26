package com.example.demo.dao;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import com.example.demo.entity.Account;
import com.example.demo.model.NewUserInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Transactional
@Repository
public class NewUserDAO {

    @Autowired
    private SessionFactory sessionFactory;

    public Account findAccount(String userName) {
        Session session = this.sessionFactory.getCurrentSession();
        return session.find(Account.class, userName);
    }

    public void saveUser(NewUserInfo userInfo) {
    	
    }
}