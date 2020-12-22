package com.lwl.repository;

import com.lwl.entity.Borrow;

import java.util.List;

public interface BorrowRepository {
    void insert(Integer bookid,Integer readerid,
                String borrowtime,String returntime,Integer adminid,
                Integer state);
    List<Borrow> findAllByReaderId(Integer id,Integer index,Integer length);
    int count(Integer readerid);
    List<Borrow> findAllByState(Integer state,Integer index,Integer length);
    int countByState(Integer state);
    void handle(Integer id,Integer state,Integer adminid);
}
