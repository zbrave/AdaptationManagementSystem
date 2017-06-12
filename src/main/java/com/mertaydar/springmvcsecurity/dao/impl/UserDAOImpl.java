package com.mertaydar.springmvcsecurity.dao.impl;
 
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.mertaydar.springmvcsecurity.dao.ActivationDAO;
import com.mertaydar.springmvcsecurity.dao.MailSend;
import com.mertaydar.springmvcsecurity.dao.UserDAO;
import com.mertaydar.springmvcsecurity.dao.UserRoleDAO;
import com.mertaydar.springmvcsecurity.model.DeptInfo;
import com.mertaydar.springmvcsecurity.model.StudentInfo;
import com.mertaydar.springmvcsecurity.model.UniInfo;
import com.mertaydar.springmvcsecurity.model.UserInfo;
import com.mertaydar.springmvcsecurity.entity.Activation;
import com.mertaydar.springmvcsecurity.entity.Student;
import com.mertaydar.springmvcsecurity.entity.User;
 
@Service
@Transactional
public class UserDAOImpl implements UserDAO {
 
	@Autowired
	private UserRoleDAO userRoleDAO;
	
	@Autowired 
	private ActivationDAO activationDAO;
	
	@Autowired
	private SessionFactory sessionFactory;
	
	@Autowired
	private MailSend mailSend;

	@Override
	public User findUser(Integer id) {
        Session session = sessionFactory.getCurrentSession();
        Criteria crit = session.createCriteria(User.class);
        crit.add(Restrictions.eq("id", id));
        return (User) crit.uniqueResult();
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public List<UserInfo> listUserInfos(Integer pageid, Integer total) {
		// sql query has to have exact names from own class variable 
		String sql = "Select new " + UserInfo.class.getName()//
                + "(a.id, a.email, a.username, a.password, a.studentId, a.enabled, a.tel) "//
                + " from " + User.class.getName() + " a ";
		System.out.println(sql.toString());
        Session session = sessionFactory.getCurrentSession();
        Query query = session.createQuery(sql).setFirstResult(pageid-1).setMaxResults(total);
        return query.list();
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public List<UserInfo> listUserInfosBySearch(Integer pageid, Integer total, String val, String cate) {
		// sql query has to have exact names from own class variable 
		String sql = "Select new " + UserInfo.class.getName()//
                + "(a.id, a.email, a.username, a.password, a.studentId, a.enabled, a.tel) "//
                + " from " + User.class.getName() + " a where "+cate+" like '%"+val+"%'";
		System.out.println(sql.toString());
        Session session = sessionFactory.getCurrentSession();
        Query query = session.createQuery(sql).setFirstResult(pageid-1).setMaxResults(total);
        return query.list();
	}
	
	@Override
	public UserInfo findLoginUserInfoWithEmail(String email) {
		Session session = sessionFactory.getCurrentSession();
        Criteria crit = session.createCriteria(User.class);
        crit.add(Restrictions.eq("email", email));
        User user = (User) crit.uniqueResult();
        if (user == null) {
            return null;
        }
        return new UserInfo(user.getId(),user.getEmail(),user.getUsername(),user.getPassword(),user.getStudentId(),user.isEnabled(),user.getTel());
	}
	
	/* ListUser func. */
	@SuppressWarnings("unchecked")
	@Override
	public List<UserInfo> listUserInfosRoleUser() {
        boolean isManager = false;
		Session session = sessionFactory.getCurrentSession();
        Criteria crit = session.createCriteria(User.class);
        List<User> users =(List<User>) crit.list();
        List<UserInfo> userInfos=new ArrayList<UserInfo>();
        for(User u : users){
        	isManager = false;
        	List<String> roles = userRoleDAO.getUserRoles(u.getId());
        	for (String s : roles) {
        		if (s.equals("USER")) {
        			isManager = true;
        		}
        	}
        	if (isManager == true)
        		userInfos.add((UserInfo)findUserInfo(u.getId()));
        }
        return userInfos;
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public List<UserInfo> listUserInfosRoleUser(Integer pageid, Integer total) {
		// sql query has to have exact names from own class variable 
		String sql = "Select new " + UserInfo.class.getName()//
                + "(a.id, a.email, a.username, a.password, a.studentId, a.enabled, a.tel) "//
                + " from " + User.class.getName() + " a ";
		System.out.println(sql.toString());
        Session session = sessionFactory.getCurrentSession();
        Query query = session.createQuery(sql);
        List<UserInfo> users = query.list();
        List<UserInfo> userInfos = new ArrayList<UserInfo>();
        for(UserInfo u : users){
        	boolean isManager = false;
        	List<String> roles = userRoleDAO.getUserRoles(u.getId());
        	for (String s : roles) {
        		if (s.equals("USER")) {
        			isManager = true;
        		}
        	}
        	if (isManager == true)
        		userInfos.add((UserInfo)findUserInfo(u.getId()));
        }
        if (pageid+total-1 < userInfos.size())
        	return userInfos.subList(pageid-1, pageid+total-1);
        return userInfos.subList(pageid-1, userInfos.size());
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public List<UserInfo> listUserInfosBySearchRoleUser(Integer pageid, Integer total, String val, String cate) {
		// sql query has to have exact names from own class variable 
		String sql = "Select new " + UserInfo.class.getName()//
                + "(a.id, a.email, a.username, a.password, a.studentId, a.enabled, a.tel) "//
                + " from " + User.class.getName() + " a where "+cate+" like '%"+val+"%'";
		System.out.println(sql.toString());
        Session session = sessionFactory.getCurrentSession();
        Query query = session.createQuery(sql);
        List<UserInfo> users = query.list();
        List<UserInfo> userInfos = new ArrayList<UserInfo>();
        for(UserInfo u : users){
        	boolean isManager = false;
        	List<String> roles = userRoleDAO.getUserRoles(u.getId());
        	for (String s : roles) {
        		if (s.equals("USER")) {
        			isManager = true;
        		}
        	}
        	if (isManager == true)
        		userInfos.add((UserInfo)findUserInfo(u.getId()));
        }
        if (pageid+total-1 < userInfos.size())
        	return userInfos.subList(pageid-1, pageid+total-1);
        return userInfos.subList(pageid-1, userInfos.size());
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public List<UserInfo> listUserInfosBySearchSizeRoleUser(String val, String cate) {
		// sql query has to have exact names from own class variable 
		String sql = "Select new " + UserInfo.class.getName()//
                + "(a.id, a.email, a.username, a.password, a.studentId, a.enabled, a.tel) "//
                + " from " + User.class.getName() + " a where "+cate+" like '%"+val+"%'";
		System.out.println(sql.toString());
        Session session = sessionFactory.getCurrentSession();
        Query query = session.createQuery(sql);
        List<UserInfo> users = query.list();
        List<UserInfo> userInfos = new ArrayList<UserInfo>();
        for(UserInfo u : users){
        	boolean isManager = false;
        	List<String> roles = userRoleDAO.getUserRoles(u.getId());
        	for (String s : roles) {
        		if (s.equals("USER")) {
        			isManager = true;
        		}
        	}
        	if (isManager == true)
        		userInfos.add((UserInfo)findUserInfo(u.getId()));
        }
        return userInfos;
	}
	/* ListUserStu func. */
	@SuppressWarnings("unchecked")
	@Override
	public List<UserInfo> listUserInfosRoleUserStu() {
        boolean isManager = false;
		Session session = sessionFactory.getCurrentSession();
        Criteria crit = session.createCriteria(User.class);
        List<User> users =(List<User>) crit.list();
        List<UserInfo> userInfos=new ArrayList<UserInfo>();
        for(User u : users){
        	isManager = false;
        	List<String> roles = userRoleDAO.getUserRoles(u.getId());
        	for (String s : roles) {
        		if (s.equals("USER")) {
        			isManager = true;
        		}
        	}
        	if (isManager == true && u.getStudentId() != null)
        		userInfos.add((UserInfo)findUserInfo(u.getId()));
        }
        return userInfos;
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public List<UserInfo> listUserInfosRoleUserStu(Integer pageid, Integer total) {
		// sql query has to have exact names from own class variable 
		String sql = "Select new " + UserInfo.class.getName()//
                + "(a.id, a.email, a.username, a.password, a.studentId, a.enabled, a.tel) "//
                + " from " + User.class.getName() + " a ";
		System.out.println(sql.toString());
        Session session = sessionFactory.getCurrentSession();
        Query query = session.createQuery(sql);
        List<UserInfo> users = query.list();
        List<UserInfo> userInfos = new ArrayList<UserInfo>();
        for(UserInfo u : users){
        	boolean isManager = false;
        	List<String> roles = userRoleDAO.getUserRoles(u.getId());
        	for (String s : roles) {
        		if (s.equals("USER")) {
        			isManager = true;
        		}
        	}
        	if (isManager == true && u.getStudentId() != null)
        		userInfos.add((UserInfo)findUserInfo(u.getId()));
        }
        if (pageid+total-1 < userInfos.size())
        	return userInfos.subList(pageid-1, pageid+total-1);
        return userInfos.subList(pageid-1, userInfos.size());
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public List<UserInfo> listUserInfosBySearchRoleUserStu(Integer pageid, Integer total, String val, String cate) {
		// sql query has to have exact names from own class variable 
		String sql = "Select new " + UserInfo.class.getName()//
                + "(a.id, a.email, a.username, a.password, a.studentId, a.enabled, a.tel) "//
                + " from " + User.class.getName() + " a where "+cate+" like '%"+val+"%'";
		System.out.println(sql.toString());
        Session session = sessionFactory.getCurrentSession();
        Query query = session.createQuery(sql);
        List<UserInfo> users = query.list();
        List<UserInfo> userInfos = new ArrayList<UserInfo>();
        for(UserInfo u : users){
        	boolean isManager = false;
        	List<String> roles = userRoleDAO.getUserRoles(u.getId());
        	for (String s : roles) {
        		if (s.equals("USER")) {
        			isManager = true;
        		}
        	}
        	if (isManager == true && u.getStudentId() != null)
        		userInfos.add((UserInfo)findUserInfo(u.getId()));
        }
        if (pageid+total-1 < userInfos.size())
        	return userInfos.subList(pageid-1, pageid+total-1);
        return userInfos.subList(pageid-1, userInfos.size());
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public List<UserInfo> listUserInfosBySearchSizeRoleUserStu(String val, String cate) {
		// sql query has to have exact names from own class variable 
		String sql = "Select new " + UserInfo.class.getName()//
                + "(a.id, a.email, a.username, a.password, a.studentId, a.enabled, a.tel) "//
                + " from " + User.class.getName() + " a where "+cate+" like '%"+val+"%'";
		System.out.println(sql.toString());
        Session session = sessionFactory.getCurrentSession();
        Query query = session.createQuery(sql);
        List<UserInfo> users = query.list();
        List<UserInfo> userInfos = new ArrayList<UserInfo>();
        for(UserInfo u : users){
        	boolean isManager = false;
        	List<String> roles = userRoleDAO.getUserRoles(u.getId());
        	for (String s : roles) {
        		if (s.equals("USER")) {
        			isManager = true;
        		}
        	}
        	if (isManager == true && u.getStudentId() != null)
        		userInfos.add((UserInfo)findUserInfo(u.getId()));
        }
        return userInfos;
	}
	
	/* ListNewUser func. */
	@SuppressWarnings("unchecked")
	@Override
	public List<UserInfo> listUserInfosRoleNewUser() {
        boolean isManager = false;
		Session session = sessionFactory.getCurrentSession();
        Criteria crit = session.createCriteria(User.class);
        List<User> users =(List<User>) crit.list();
        List<UserInfo> userInfos=new ArrayList<UserInfo>();
        for(User u : users){
        	isManager = false;
        	List<String> roles = userRoleDAO.getUserRoles(u.getId());
    		if (roles.isEmpty()) {
    			isManager = true;
    		}
        	if (isManager == true)
        		userInfos.add((UserInfo)findUserInfo(u.getId()));
        }
        return userInfos;
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public List<UserInfo> listUserInfosRoleNewUser(Integer pageid, Integer total) {
		// sql query has to have exact names from own class variable 
		String sql = "Select new " + UserInfo.class.getName()//
                + "(a.id, a.email, a.username, a.password, a.studentId, a.enabled, a.tel) "//
                + " from " + User.class.getName() + " a ";
		System.out.println(sql.toString());
        Session session = sessionFactory.getCurrentSession();
        Query query = session.createQuery(sql);
        List<UserInfo> users = query.list();
        List<UserInfo> userInfos = new ArrayList<UserInfo>();
        for(UserInfo u : users){
        	boolean isManager = false;
        	List<String> roles = userRoleDAO.getUserRoles(u.getId());
        	if (roles.isEmpty()) {
    			isManager = true;
    		}
        	if (isManager == true)
        		userInfos.add((UserInfo)findUserInfo(u.getId()));
        }
        if (pageid+total-1 < userInfos.size())
        	return userInfos.subList(pageid-1, pageid+total-1);
        return userInfos.subList(pageid-1, userInfos.size());
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public List<UserInfo> listUserInfosBySearchRoleNewUser(Integer pageid, Integer total, String val, String cate) {
		// sql query has to have exact names from own class variable 
		String sql = "Select new " + UserInfo.class.getName()//
                + "(a.id, a.email, a.username, a.password, a.studentId, a.enabled, a.tel) "//
                + " from " + User.class.getName() + " a where "+cate+" like '%"+val+"%'";
		System.out.println(sql.toString());
        Session session = sessionFactory.getCurrentSession();
        Query query = session.createQuery(sql);
        List<UserInfo> users = query.list();
        List<UserInfo> userInfos = new ArrayList<UserInfo>();
        for(UserInfo u : users){
        	boolean isManager = false;
        	List<String> roles = userRoleDAO.getUserRoles(u.getId());
        	if (roles.isEmpty()) {
    			isManager = true;
    		}
        	if (isManager == true)
        		userInfos.add((UserInfo)findUserInfo(u.getId()));
        }
        if (pageid+total-1 < userInfos.size())
        	return userInfos.subList(pageid-1, pageid+total-1);
        return userInfos.subList(pageid-1, userInfos.size());
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public List<UserInfo> listUserInfosBySearchSizeRoleNewUser(String val, String cate) {
		// sql query has to have exact names from own class variable 
		String sql = "Select new " + UserInfo.class.getName()//
                + "(a.id, a.email, a.username, a.password, a.studentId, a.enabled, a.tel) "//
                + " from " + User.class.getName() + " a where "+cate+" like '%"+val+"%'";
		System.out.println(sql.toString());
        Session session = sessionFactory.getCurrentSession();
        Query query = session.createQuery(sql);
        List<UserInfo> users = query.list();
        List<UserInfo> userInfos = new ArrayList<UserInfo>();
        for(UserInfo u : users){
        	boolean isManager = false;
        	List<String> roles = userRoleDAO.getUserRoles(u.getId());
        	if (roles.isEmpty()) {
    			isManager = true;
    		}
        	if (isManager == true)
        		userInfos.add((UserInfo)findUserInfo(u.getId()));
        }
        return userInfos;
	}
	
	/* ListManager func. */
	@SuppressWarnings("unchecked")
	@Override
	public List<UserInfo> listUserInfosRoleManager() {
        boolean isManager = false;
		Session session = sessionFactory.getCurrentSession();
        Criteria crit = session.createCriteria(User.class);
        List<User> users =(List<User>) crit.list();
        List<UserInfo> userInfos=new ArrayList<UserInfo>();
        for(User u : users){
        	isManager = false;
        	List<String> roles = userRoleDAO.getUserRoles(u.getId());
        	for (String s : roles) {
        		if (s.equals("MANAGER")) {
        			isManager = true;
        		}
        	}
        	if (isManager == true)
        		userInfos.add((UserInfo)findUserInfo(u.getId()));
        }
        return userInfos;
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public List<UserInfo> listUserInfosRoleManager(Integer pageid, Integer total) {
		// sql query has to have exact names from own class variable 
		String sql = "Select new " + UserInfo.class.getName()//
                + "(a.id, a.email, a.username, a.password, a.studentId, a.enabled, a.tel) "//
                + " from " + User.class.getName() + " a ";
		System.out.println(sql.toString());
        Session session = sessionFactory.getCurrentSession();
        Query query = session.createQuery(sql);
        List<UserInfo> users = query.list();
        List<UserInfo> userInfos = new ArrayList<UserInfo>();
        for(UserInfo u : users){
        	boolean isManager = false;
        	List<String> roles = userRoleDAO.getUserRoles(u.getId());
        	for (String s : roles) {
        		if (s.equals("MANAGER")) {
        			isManager = true;
        		}
        	}
        	if (isManager == true)
        		userInfos.add((UserInfo)findUserInfo(u.getId()));
        }
        if (pageid+total-1 < userInfos.size())
        	return userInfos.subList(pageid-1, pageid+total-1);
        return userInfos.subList(pageid-1, userInfos.size());
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public List<UserInfo> listUserInfosBySearchRoleManager(Integer pageid, Integer total, String val, String cate) {
		// sql query has to have exact names from own class variable 
		String sql = "Select new " + UserInfo.class.getName()//
                + "(a.id, a.email, a.username, a.password, a.studentId, a.enabled, a.tel) "//
                + " from " + User.class.getName() + " a where "+cate+" like '%"+val+"%'";
		System.out.println(sql.toString());
        Session session = sessionFactory.getCurrentSession();
        Query query = session.createQuery(sql);
        List<UserInfo> users = query.list();
        List<UserInfo> userInfos = new ArrayList<UserInfo>();
        for(UserInfo u : users){
        	boolean isManager = false;
        	List<String> roles = userRoleDAO.getUserRoles(u.getId());
        	for (String s : roles) {
        		if (s.equals("MANAGER")) {
        			isManager = true;
        		}
        	}
        	if (isManager == true)
        		userInfos.add((UserInfo)findUserInfo(u.getId()));
        }
        if (pageid+total-1 < userInfos.size())
        	return userInfos.subList(pageid-1, pageid+total-1);
        return userInfos.subList(pageid-1, userInfos.size());
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public List<UserInfo> listUserInfosBySearchSizeRoleManager(String val, String cate) {
		// sql query has to have exact names from own class variable 
		String sql = "Select new " + UserInfo.class.getName()//
                + "(a.id, a.email, a.username, a.password, a.studentId, a.enabled, a.tel) "//
                + " from " + User.class.getName() + " a where "+cate+" like '%"+val+"%'";
		System.out.println(sql.toString());
        Session session = sessionFactory.getCurrentSession();
        Query query = session.createQuery(sql);
        List<UserInfo> users = query.list();
        List<UserInfo> userInfos = new ArrayList<UserInfo>();
        for(UserInfo u : users){
        	boolean isManager = false;
        	List<String> roles = userRoleDAO.getUserRoles(u.getId());
        	for (String s : roles) {
        		if (s.equals("MANAGER")) {
        			isManager = true;
        		}
        	}
        	if (isManager == true)
        		userInfos.add((UserInfo)findUserInfo(u.getId()));
        }
        return userInfos;
	}
	
	/* ListAdmin func. */
	@SuppressWarnings("unchecked")
	@Override
	public List<UserInfo> listUserInfosRoleAdmin() {
        boolean isManager = false;
		Session session = sessionFactory.getCurrentSession();
        Criteria crit = session.createCriteria(User.class);
        List<User> users =(List<User>) crit.list();
        List<UserInfo> userInfos=new ArrayList<UserInfo>();
        for(User u : users){
        	isManager = false;
        	List<String> roles = userRoleDAO.getUserRoles(u.getId());
        	for (String s : roles) {
        		if (s.equals("ADMIN")) {
        			isManager = true;
        		}
        	}
        	if (isManager == true)
        		userInfos.add((UserInfo)findUserInfo(u.getId()));
        }
        return userInfos;
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public List<UserInfo> listUserInfosRoleAdmin(Integer pageid, Integer total) {
		// sql query has to have exact names from own class variable 
		String sql = "Select new " + UserInfo.class.getName()//
                + "(a.id, a.email, a.username, a.password, a.studentId, a.enabled, a.tel) "//
                + " from " + User.class.getName() + " a ";
		System.out.println(sql.toString());
        Session session = sessionFactory.getCurrentSession();
        Query query = session.createQuery(sql);
        List<UserInfo> users = query.list();
        List<UserInfo> userInfos = new ArrayList<UserInfo>();
        for(UserInfo u : users){
        	boolean isManager = false;
        	List<String> roles = userRoleDAO.getUserRoles(u.getId());
        	for (String s : roles) {
        		if (s.equals("ADMIN")) {
        			isManager = true;
        		}
        	}
        	if (isManager == true)
        		userInfos.add((UserInfo)findUserInfo(u.getId()));
        }
        if (pageid+total-1 < userInfos.size())
        	return userInfos.subList(pageid-1, pageid+total-1);
        return userInfos.subList(pageid-1, userInfos.size());
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public List<UserInfo> listUserInfosBySearchRoleAdmin(Integer pageid, Integer total, String val, String cate) {
		// sql query has to have exact names from own class variable 
		String sql = "Select new " + UserInfo.class.getName()//
                + "(a.id, a.email, a.username, a.password, a.studentId, a.enabled, a.tel) "//
                + " from " + User.class.getName() + " a where "+cate+" like '%"+val+"%'";
		System.out.println(sql.toString());
        Session session = sessionFactory.getCurrentSession();
        Query query = session.createQuery(sql);
        List<UserInfo> users = query.list();
        List<UserInfo> userInfos = new ArrayList<UserInfo>();
        for(UserInfo u : users){
        	boolean isManager = false;
        	List<String> roles = userRoleDAO.getUserRoles(u.getId());
        	for (String s : roles) {
        		if (s.equals("ADMIN")) {
        			isManager = true;
        		}
        	}
        	if (isManager == true)
        		userInfos.add((UserInfo)findUserInfo(u.getId()));
        }
        if (pageid+total-1 < userInfos.size())
        	return userInfos.subList(pageid-1, pageid+total-1);
        return userInfos.subList(pageid-1, userInfos.size());
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public List<UserInfo> listUserInfosBySearchSizeRoleAdmin(String val, String cate) {
		// sql query has to have exact names from own class variable 
		String sql = "Select new " + UserInfo.class.getName()//
                + "(a.id, a.email, a.username, a.password, a.studentId, a.enabled, a.tel) "//
                + " from " + User.class.getName() + " a where "+cate+" like '%"+val+"%'";
		System.out.println(sql.toString());
        Session session = sessionFactory.getCurrentSession();
        Query query = session.createQuery(sql);
        List<UserInfo> users = query.list();
        List<UserInfo> userInfos = new ArrayList<UserInfo>();
        for(UserInfo u : users){
        	boolean isManager = false;
        	List<String> roles = userRoleDAO.getUserRoles(u.getId());
        	for (String s : roles) {
        		if (s.equals("ADMIN")) {
        			isManager = true;
        		}
        	}
        	if (isManager == true)
        		userInfos.add((UserInfo)findUserInfo(u.getId()));
        }
        return userInfos;
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
			user.setEnabled(false);
			Activation act = new Activation();
			act.setUsername(userInfo.getUsername());
		    act.setCode(getSaltString());
		    activationDAO.saveActivation(act);
		    String text = "IYS hesabını aktif etmek için aşağıdaki linke tıklayın.\n\n";
		    text = text.concat("http://localhost:8080/AMS/activate?code="+act.getCode().toString());
		    mailSend.sendSimpleMessage(userInfo.getEmail(), "IYS Aktivasyon", text);
		}
		user.setId(userInfo.getId());
	    user.setEmail(userInfo.getEmail());
	    user.setUsername(userInfo.getUsername());
	    user.setPassword(userInfo.getPassword());
	    user.setStudentId(userInfo.getStudentId());
	    user.setEnabled(userInfo.isEnabled());
	    user.setTel(userInfo.getTel());
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
		return new UserInfo(user.getId(),user.getEmail(),user.getUsername(),user.getPassword(),user.getStudentId(),user.isEnabled(),user.getTel());
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
        return new UserInfo(user.getId(),user.getEmail(),user.getUsername(),user.getPassword(),user.getStudentId(),user.isEnabled(),user.getTel());
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
	public List<UserInfo> listUserInfosBySearchSize(String val, String cate) {
		// sql query has to have exact names from own class variable 
		String sql = "Select new " + UserInfo.class.getName()//
                + "(a.id, a.email, a.username, a.password, a.studentId, a.enabled, a.tel) "//
                + " from " + User.class.getName() + " a where "+cate+" like '%"+val+"%'";
		System.out.println(sql.toString());
        Session session = sessionFactory.getCurrentSession();
        Query query = session.createQuery(sql);
        return query.list();
	}
	
	
	
	
}