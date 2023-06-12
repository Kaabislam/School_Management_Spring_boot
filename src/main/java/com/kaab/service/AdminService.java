package com.kaab.service;

import com.kaab.dao.AdminDao;
import com.kaab.dao.UserDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AdminService {
    @Autowired
    private UserDao userDao;


    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private AdminDao adminDao;

}
