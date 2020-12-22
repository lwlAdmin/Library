package com.lwl.repository;

import com.lwl.entity.Reader;

public interface ReaderRepository {
     Reader login(String username,String password);
}
