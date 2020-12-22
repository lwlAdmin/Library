package com.lwl.service.impl;

import com.lwl.entity.Book;
import com.lwl.entity.Borrow;
import com.lwl.repository.BookRepository;
import com.lwl.repository.BorrowRepository;
import com.lwl.repository.impl.BookRepositoryImpl;
import com.lwl.repository.impl.BorrowRepositoryImpl;
import com.lwl.service.BookService;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class BookServiceImpl implements BookService {
    private BookRepository bookRepository = new BookRepositoryImpl();
    public static final int LENGTH = 6;
    BorrowRepository borrowRepository = new BorrowRepositoryImpl();
    /**
     * 查询图书信息的方法
     * @param page 当前页面
     * @return 当前页面图书数据
     */
    @Override
    public List<Book> findAll(int page) {
        int index = (page-1)*LENGTH;
        return bookRepository.findAll(index,LENGTH);
    }

    /**
     * 获取图书记录总页数的方法
     * @return 图书记录总页数
     */
    @Override
    public int getPages() {
        int count = bookRepository.count();
        int pages;
        if(count % LENGTH == 0){
            pages = count / LENGTH;
        }else{
            pages = count / LENGTH + 1;
        }
        return pages;
    }

    /**
     * 获取借阅记录总页数的方法
     * @return 借阅记录总页数
     */
    @Override
    public int getBorrowPages(Integer id) {
        int count = borrowRepository.count(id);
        int pages;
        if(count % LENGTH == 0){
            pages = count / LENGTH;
        }else{
            pages = count / LENGTH + 1;
        }
        return pages;
    }

    /**
     * 添加借阅记录的方法
     * @param bookid 书本的id
     * @param readerid 读者的id
     */
    @Override
    public void addBorrow(Integer bookid, Integer readerid) {
        //借书时间
        Date date = new Date();
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
        String borrowTime = simpleDateFormat.format(date);
        //还书时间，借书时间+14天
        Calendar calendar = Calendar.getInstance();
        //将给定的日历字段设置为给定的值。
        calendar.set(Calendar.DAY_OF_YEAR,calendar.get(Calendar.DAY_OF_YEAR) + 14);
        Date date1 = calendar.getTime();
        String returnTime = simpleDateFormat.format(date1);
        borrowRepository.insert(bookid,readerid,borrowTime,returnTime,null,0);
    }

    /**
     * 找出读者所有借阅记录的方法
     * @param readerid 读者id
     * @return 返回包含读者借阅信息的list集合
     */
    @Override
    public List<Borrow> findAllBorrowByReaderId(Integer readerid,Integer page) {
        int index = (page-1)*LENGTH;
        return borrowRepository.findAllByReaderId(readerid,index,LENGTH);
    }

    @Override
    public List<Borrow> findAllBorrowByState(Integer state,Integer page) {
        int index = (page-1)*LENGTH;
        return borrowRepository.findAllByState(state,index,LENGTH);
    }

    @Override
    public int getBorrowPagesByState(Integer state) {
       int count = borrowRepository.countByState(state);
       int pages;
        if (count % LENGTH == 0) {
            pages = count / LENGTH;
        }else{
            pages = count / LENGTH + 1;
        }
        return pages;
    }

    @Override
    public void handleBorrow(Integer id, Integer state, Integer adminid) {
        borrowRepository.handle(id,state,adminid);
    }
}
