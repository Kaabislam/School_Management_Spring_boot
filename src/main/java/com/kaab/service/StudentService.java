package com.kaab.service;

import com.kaab.dao.StudentDao;
import com.kaab.dao.UserDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class StudentService {
    @Autowired
    private UserDao userDao;


    @Autowired
    private StudentDao studentDao;

}
