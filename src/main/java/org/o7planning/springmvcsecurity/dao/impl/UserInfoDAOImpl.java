package org.o7planning.springmvcsecurity.dao.impl;
 
import java.util.List;
 
import javax.sql.DataSource;
 
import org.o7planning.springmvcsecurity.dao.UserInfoDAO;
import org.o7planning.springmvcsecurity.mapper.UserInfoMapper;
import org.o7planning.springmvcsecurity.model.UserInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.support.JdbcDaoSupport;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
 
@Service
@Transactional
public class UserInfoDAOImpl extends JdbcDaoSupport implements UserInfoDAO {
 
    @Autowired
    public UserInfoDAOImpl(DataSource dataSource) {
        this.setDataSource(dataSource);
    }
  
 
    @Override
    public UserInfo findUserInfo(String userName) {
        String sql = "Select u.username, u.password, u.std_id, u.enabled  "//
                + " from user u where u.username = ? ";
 
        Object[] params = new Object[] { userName };
        UserInfoMapper mapper = new UserInfoMapper();
        try {
            UserInfo userInfo = this.getJdbcTemplate().queryForObject(sql, params, mapper);
            return userInfo;
        } catch (EmptyResultDataAccessException e) {
            return null;
        }
    }
 
 
    @Override
    public List<String> getUserRoles(String userName) {
        String sql = "Select r.user_role "//
                + " from user_roles r where r.user_username = ? ";
         
        Object[] params = new Object[] { userName };
         
        List<String> roles = this.getJdbcTemplate().queryForList(sql,params, String.class);
         
        return roles;
    }
     
}