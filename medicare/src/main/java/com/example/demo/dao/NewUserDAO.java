package com.example.demo.dao;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import com.example.demo.entity.Account;
import com.example.demo.form.NewUserForm;
import com.example.demo.model.NewUserInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@Transactional
@Repository
public class NewUserDAO {

	@Autowired
	private BCryptPasswordEncoder bCryptPasswordEncoder;
	
    @Autowired
    private SessionFactory sessionFactory;

    public Account findAccount(String userName) {
        Session session = this.sessionFactory.getCurrentSession();
        return session.find(Account.class, userName);
    }

    @Transactional(propagation = Propagation.REQUIRES_NEW, rollbackFor = Exception.class)
	public void saveUser(NewUserForm userForm) {
    	System.out.println("userForm.getUsername "+userForm.getUsername());
    	System.out.println("userForm.getPassword "+userForm.getPassword());
		Session session = this.sessionFactory.getCurrentSession();

		Account account = new Account();
		String encodedPassword = bCryptPasswordEncoder.encode(userForm.getPassword());
		account.setActive(true);
		account.setUserName(userForm.getUsername());
		account.setEncrytedPassword(encodedPassword);
		account.setUserRole("ROLE_USER");
		
		/*
		String username = request.getParameter("username");
		String password = request.getParameter("password");
		account.setUserName(username);
		account.setEncrytedPassword(password);
		*/
		
		session.persist(account);
		session.flush();
	}
}