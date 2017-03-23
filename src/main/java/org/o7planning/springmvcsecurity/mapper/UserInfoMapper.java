package org.o7planning.springmvcsecurity.mapper;
 
import java.sql.ResultSet;
import java.sql.SQLException;
 
import org.o7planning.springmvcsecurity.model.UserInfo;
import org.springframework.jdbc.core.RowMapper;
 
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