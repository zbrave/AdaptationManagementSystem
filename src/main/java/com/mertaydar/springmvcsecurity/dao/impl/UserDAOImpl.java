package com.mertaydar.springmvcsecurity.dao.impl;
 
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.mertaydar.springmvcsecurity.dao.ActivationDAO;
import com.mertaydar.springmvcsecurity.dao.UserDAO;
import com.mertaydar.springmvcsecurity.dao.UserRoleDAO;
import com.mertaydar.springmvcsecurity.model.UserInfo;
import com.mertaydar.springmvcsecurity.entity.Activation;
import com.mertaydar.springmvcsecurity.entity.User;
 
@Service
@Transactional
public class UserDAOImpl implements UserDAO {
 
	@Autowired
	private UserRoleDAO userRoleDAO;
	
	@Autowired ActivationDAO actiavationDAO;
	
	@Autowired
	private SessionFactory sessionFactory;

	@Override
	public User findUser(Integer id) {
        Session session = sessionFactory.getCurrentSession();
        Criteria crit = session.createCriteria(User.class);
        crit.add(Restrictions.eq("id", id));
        return (User) crit.uniqueResult();
	}

	@Override
	public void saveUser(UserInfo userInfo) {
		Integer id = userInfo.getId();
		User user = null;
		if( id!=null ){
			user = this.findUser(id);
		}
		boolean isNew = false;
		if( user == null ){
			isNew = true;
			user = new User();
		}
		user.setId(userInfo.getId());
	    user.setEmail(userInfo.getEmail());
	    user.setUsername(userInfo.getUsername());
	    user.setPassword(userInfo.getPassword());
	    user.setStudentId(userInfo.getStudentId());
	    user.setEnabled(false);
	    Activation act = new Activation();
	    act.setUsername(user.getUsername());
	    act.setCode(getSaltString());
	    actiavationDAO.saveActivation(act);
	    if(isNew){
	    	Session session=this.sessionFactory.getCurrentSession();
	    	session.persist(user);
	    }
	}
	
	protected String getSaltString() {
        String SALTCHARS = "ABCDEFGHIJKLMNOPQRSTUVWXYZ1234567890";
        StringBuilder salt = new StringBuilder();
        Random rnd = new Random();
        while (salt.length() < 18) { // length of the random string.
            int index = (int) (rnd.nextFloat() * SALTCHARS.length());
            salt.append(SALTCHARS.charAt(index));
        }
        String saltStr = salt.toString();
        return saltStr;

    }

	@Override
	public UserInfo findUserInfo(Integer id) {
		User user = this.findUser(id);
		if(user==null){
			return null;
		}
		return new UserInfo(user.getId(),user.getEmail(),user.getUsername(),user.getPassword(),user.getStudentId(),user.isEnabled());
	}

	@Override
	public void deleteUser(Integer id) {
		User user = this.findUser(id);
		if(user!=null){
			this.sessionFactory.getCurrentSession().delete(user);
		}
		
	}

	@Override
	public User findLoginUser(String username) {
    	Session session = sessionFactory.getCurrentSession();
        Criteria crit = session.createCriteria(User.class);
        crit.add(Restrictions.eq("username", username));
        return (User) crit.uniqueResult();
	}

	@Override
	public UserInfo findLoginUserInfo(String username) {
		User user = this.findLoginUser(username);
        if (user == null) {
            return null;
        }
        return new UserInfo(user.getId(),user.getEmail(),user.getUsername(),user.getPassword(),user.getStudentId(),user.isEnabled());
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<UserInfo> listUserInfos() {
        Session session = sessionFactory.getCurrentSession();
        Criteria crit = session.createCriteria(User.class);
        List<User> users =(List<User>) crit.list();
        List<UserInfo> userInfos=new ArrayList<UserInfo>();
        for(User u : users){
        	userInfos.add((UserInfo)findUserInfo(u.getId()));
        }
        return userInfos;
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public List<UserInfo> listUserInfosRoleAdmin() {
        boolean isAdmin = false;
		Session session = sessionFactory.getCurrentSession();
        Criteria crit = session.createCriteria(User.class);
        List<User> users =(List<User>) crit.list();
        List<UserInfo> userInfos=new ArrayList<UserInfo>();
        for(User u : users){
        	isAdmin = false;
        	List<String> roles = userRoleDAO.getUserRoles(u.getId());
        	for (String s : roles) {
        		if (s.equals("ADMIN")) {
        			isAdmin = true;
        		}
        	}
        	if (isAdmin == true)
        		userInfos.add((UserInfo)findUserInfo(u.getId()));
        }
        return userInfos;
	}

}