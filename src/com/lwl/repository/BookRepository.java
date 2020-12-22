package com.lwl.repository;

import com.lwl.entity.Book;

import java.util.List;

public interface BookRepository {
    List<Book> findAll(int index,int length);
    int count();
}
