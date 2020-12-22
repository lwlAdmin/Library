package com.lwl.service.impl;

import com.lwl.entity.Admin;
import com.lwl.entity.Reader;
import com.lwl.repository.AdminRepository;
import com.lwl.repository.ReaderRepository;
import com.lwl.repository.impl.AdminRepositoryImpl;
import com.lwl.repository.impl.ReaderRepositoryImpl;
import com.lwl.service.LoginService;

public class LoginServiceImpl implements LoginService {
    private ReaderRepository readerRepository = new ReaderRepositoryImpl();
    private AdminRepository adminRepository = new AdminRepositoryImpl();
    @Override
    public Object login(String username, String password, String type) {
        Object object = null;
        switch (type){
            case "reader":
                object = readerRepository.login(username,password);
                break;
            case "admin":
                object = adminRepository.login(username,password);
                break;
        }
        return object;
    }
}
