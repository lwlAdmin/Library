package com.lwl.repository.impl;

import com.lwl.entity.Reader;
import com.lwl.repository.ReaderRepository;
import com.lwl.utils.JDBCTools;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class ReaderRepositoryImpl implements ReaderRepository {

    @Override
    public Reader login(String username, String password) {
        Connection connection = JDBCTools.getConnection();
        String sql = "select * from reader where username = ? and password = ?";
        PreparedStatement statement = null;
        ResultSet rs = null;
        Reader reader = null;
        try {
            //与数据库建立连接，查询reader的账号信息，然后返回查询结果
            statement = connection.prepareStatement(sql);
            statement.setString(1,username);
            statement.setString(2,password);
            rs = statement.executeQuery();
            if (rs.next()){
                reader = new Reader(
                        rs.getInt("id"),rs.getString("username"),
                        rs.getString("password"),rs.getString("name"),
                        rs.getString("tel"),rs.getString("cardid"),
                        rs.getString("gender")
                );
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }finally{
            JDBCTools.close(connection,statement,rs);
        }
        return reader;
    }
}
