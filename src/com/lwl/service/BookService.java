package com.lwl.service;

import com.lwl.entity.Book;
import com.lwl.entity.Borrow;

import java.util.List;

public interface BookService {
    List<Book> findAll(int page);
    int getPages();
    int getBorrowPages(Integer id);
    void addBorrow(Integer bookid,Integer readerid);
    List<Borrow> findAllBorrowByReaderId(Integer readerid,Integer page);
    List<Borrow> findAllBorrowByState(Integer state,Integer page);
    int getBorrowPagesByState(Integer state);
    void handleBorrow(Integer id,Integer state,Integer adminid);
}
