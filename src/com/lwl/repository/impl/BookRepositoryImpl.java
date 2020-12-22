package com.lwl.repository.impl;

import com.lwl.entity.Book;
import com.lwl.entity.BookCase;
import com.lwl.repository.BookRepository;
import com.lwl.utils.JDBCTools;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class BookRepositoryImpl implements BookRepository {
    /**
     * 获取每页展示的书目信息和条数的方法
     * @param index 每页书目的起始标号
     * @param length 每页展示的书目条数
     * @return
     */
    @Override
    public List<Book> findAll(int index,int length) {
        Connection connection = JDBCTools.getConnection();
        String sql = "select * from book b,bookcase bc where b.bookcaseid = bc.id limit ?,?";
        PreparedStatement preparedStatment = null;
        ResultSet rs = null;
        List<Book> list = new ArrayList<>();
        try {
            preparedStatment = connection.prepareStatement(sql);
            preparedStatment.setInt(1,index);
            preparedStatment.setInt(2,length);
            rs = preparedStatment.executeQuery();
            while(rs.next()){
                //这样写可以最大节省栈空间
                list.add(new Book(rs.getInt(1),rs.getString(2),
                        rs.getString(3),rs.getString(4),rs.getInt(5),
                        rs.getDouble(6),
                        new BookCase(rs.getInt("bc.id"),rs.getString("bc.name"))));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }

    @Override
    public int count() {
        Connection connection = JDBCTools.getConnection();
        String sql = "select count(*) pages from book b,bookcase bc where b.bookcaseid = bc.id";
        PreparedStatement preparedStatment = null;
        ResultSet rs = null;
        int count = 0;
        try {
            preparedStatment = connection.prepareStatement(sql);
            rs = preparedStatment.executeQuery();
            if (rs.next()){//避免出现空指针异常
            count = rs.getInt("pages");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }finally{
            JDBCTools.close(connection,preparedStatment,rs);
        }
        return count;
    }
}
