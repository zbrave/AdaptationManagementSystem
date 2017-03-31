package com.mertaydar.springmvcsecurity.mapper;
 
import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import com.mertaydar.springmvcsecurity.model.UserInfo;
 
public class UserInfoMapper implements RowMapper<UserInfo> {
 
    @Override
    public UserInfo mapRow(ResultSet rs, int rowNum) throws SQLException {
 
        String username = rs.getString("username");
        String password = rs.getString("password");
        Integer stdId = rs.getInt("std_id");
        Boolean enabled = rs.getBoolean("enabled");
        return new UserInfo(username, password, stdId, enabled);
    }
 
}