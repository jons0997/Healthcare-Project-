package com.example.demo.dao;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import com.example.demo.entity.Account;
import com.example.demo.form.NewUserForm;
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

    @Transactional(rollbackFor = Exception.class)
	public void saveUser(NewUserForm userForm) {
		Session session = this.sessionFactory.getCurrentSession();

		Account account = new Account();
		account.setActive(true);
		account.setUserName(userForm.getUsername());
		account.setEncrytedPassword(userForm.getPassword());
		account.setUserRole("ROLE_EMPLOYEE");
		session.persist(account);
		session.flush();
	}
}