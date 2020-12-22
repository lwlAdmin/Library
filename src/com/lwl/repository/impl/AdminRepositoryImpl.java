package com.lwl.repository.impl;

import com.lwl.entity.Admin;
import com.lwl.entity.Reader;
import com.lwl.repository.AdminRepository;
import com.lwl.utils.JDBCTools;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class AdminRepositoryImpl implements AdminRepository {
    @Override
    public Admin login(String username, String password) {
        Connection connection = JDBCTools.getConnection();
        String sql = "select id,username,password from bookadmin where username = ? and password = ?";
        PreparedStatement statement = null;
        ResultSet resultSet = null;
        Admin admin = null;
        try {
            //与数据库建立连接，查询Admin的账号信息，然后返回查询结果
            statement = connection.prepareStatement(sql);
            statement.setString(1,username);
            statement.setString(2,password);
            resultSet = statement.executeQuery();
            if (resultSet.next()){
                admin = new Admin(resultSet.getInt("id"),resultSet.getString("username"),resultSet.getString("password"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }finally{
            JDBCTools.close(connection,statement,resultSet);
        }
        return admin;
    }
}
