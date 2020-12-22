package com.lwl.repository.impl;

import com.lwl.entity.Book;
import com.lwl.entity.Borrow;
import com.lwl.entity.Reader;
import com.lwl.repository.BorrowRepository;
import com.lwl.utils.JDBCTools;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class BorrowRepositoryImpl implements BorrowRepository {
    /**
     * 添加书本借阅记录信息的方法
     * @param bookid 被借阅的书本的id
     * @param readerid 读者的id
     * @param borrowtime 借阅的时间
     * @param returntime 还书时间
     * @param adminid 图书管理员id
     * @param state 图书状态
     */
    @Override
    public void insert( Integer bookid, Integer readerid,
                       String borrowtime, String returntime, Integer adminid,
                       Integer state) {
        Connection connection = JDBCTools.getConnection();
        String sql = "insert into borrow(bookid,readerid,borrowtime,returntime,state) values(?,?,?,?,0)";
        PreparedStatement preparedStatement = null;
        try {
            preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setInt(1,bookid);
            preparedStatement.setInt(2,readerid);
            preparedStatement.setString(3,borrowtime);
            preparedStatement.setString(4,returntime);
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }finally{
            JDBCTools.close(connection,preparedStatement,null);
        }
    }

    /**
     * 列出该读者该读者所有借阅信息
     * @param id 读者id
     * @return 返回包含读者借阅信息的list集合
     */
    @Override
    public List<Borrow> findAllByReaderId(Integer id,Integer index,Integer length) {
        Connection connection = JDBCTools.getConnection();
        PreparedStatement preparedStatement = null;
        ResultSet rs = null;
        List<Borrow> list = new ArrayList<>();
        String sql = "select br.id,b.name bookname,b.author,b.publish,r.name,r.tel,r.cardid,br.borrowtime,br.returntime ," +
                "br.state from borrow br join reader r join book b on br.readerid = r.id and br.bookid = b.id and r.id = ? limit ?,?";
        try {
            preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setInt(1,id);
            preparedStatement.setInt(2,index);
            preparedStatement.setInt(3,length);
            rs = preparedStatement.executeQuery();
            while(rs.next()){
                //封装
                //尽量节省栈空间
                Borrow borrow = new Borrow(rs.getInt(1),
                        new Book(rs.getString(2),rs.getString(3),rs.getString(4)),
                        new Reader(rs.getString(5),rs.getString(6),rs.getString(7)),
                        rs.getString(8),rs.getString(9),rs.getInt(10));
                list.add(borrow);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }finally{
            JDBCTools.close(connection,preparedStatement,rs);
        }
        return list;
    }

    @Override
    public int count(Integer readerid) {
        Connection connection = JDBCTools.getConnection();
        String sql = "select count(*) from borrow br join reader r join book b on br.readerid = r.id and br.bookid = b.id and r.id = ?";
        PreparedStatement preparedStatment = null;
        ResultSet rs = null;
        int count = 0;
        try {
            preparedStatment = connection.prepareStatement(sql);
            preparedStatment.setInt(1,readerid);
            rs = preparedStatment.executeQuery();
            if (rs.next()){//避免出现空指针异常
                count = rs.getInt(1);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        finally{
            JDBCTools.close(connection,preparedStatment,rs);
        }
        return count;
    }

    @Override
    public List<Borrow> findAllByState(Integer state,Integer index,Integer length) {
        Connection connection = JDBCTools.getConnection();
        PreparedStatement preparedStatement = null;
        ResultSet rs = null;
        List<Borrow> list = new ArrayList<>();
        String sql = "select br.id,b.name bookname,b.author,b.publish,r.name,r.tel,r.cardid,br.borrowtime," +
                "br.returntime,br.state from borrow br join reader r join book b on br.readerid = r.id and " +
                "br.bookid = b.id and br.state= ? limit ?,?";
        try {
            preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setInt(1,state);
            preparedStatement.setInt(2,index);
            preparedStatement.setInt(3,length);
            rs = preparedStatement.executeQuery();
            while(rs.next()){
                //封装
                //尽量节省栈空间
                Borrow borrow = new Borrow(rs.getInt(1),
                        new Book(rs.getString(2),rs.getString(3),rs.getString(4)),
                        new Reader(rs.getString(5),rs.getString(6),rs.getString(7)),
                        rs.getString(8),rs.getString(9),rs.getInt(10));
                list.add(borrow);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }finally{
            JDBCTools.close(connection,preparedStatement,rs);
        }
        return list;
    }

    @Override
    public int countByState(Integer state) {
        Connection connection = JDBCTools.getConnection();
        String sql = "select count(*) from borrow br join reader r join book b on br.readerid = r.id and " +
                "br.bookid = b.id and br.state= ?";
        PreparedStatement preparedStatment = null;
        ResultSet rs = null;
        int count = 0;
        try {
            preparedStatment = connection.prepareStatement(sql);
            preparedStatment.setInt(1,state);
            rs = preparedStatment.executeQuery();
            if (rs.next()){//避免出现空指针异常
                count = rs.getInt(1);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        finally{
            JDBCTools.close(connection,preparedStatment,rs);
        }
        return count;
    }

    @Override
    public void handle(Integer id, Integer state, Integer adminid) {
        Connection connection = JDBCTools.getConnection();
        PreparedStatement preparedStatment = null;
        String sql = "update borrow set state = ? ,adminid = ? where id = ?";
        try {
            preparedStatment = connection.prepareStatement(sql);
            preparedStatment.setInt(1,state);
            preparedStatment.setInt(2,adminid);
            preparedStatment.setInt(3,id);
            preparedStatment.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        finally{
            JDBCTools.close(connection,preparedStatment,null);
        }
    }
}

