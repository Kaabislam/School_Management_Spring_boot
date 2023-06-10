package com.kaab.service;

import com.kaab.dao.RoleDao;
import com.kaab.dao.TeacherDao;
import com.kaab.dao.UserDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class TeacherService {
    @Autowired
    private UserDao userDao;

    @Autowired
    private RoleDao roleDao;

    @Autowired
    private PasswordEncoder passwordEncoder;


    @Autowired
    private TeacherDao teacherDao;


}
