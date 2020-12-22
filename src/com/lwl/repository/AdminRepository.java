package com.lwl.repository;

import com.lwl.entity.Admin;

public interface AdminRepository {
    Admin login(String username,String password);
}
