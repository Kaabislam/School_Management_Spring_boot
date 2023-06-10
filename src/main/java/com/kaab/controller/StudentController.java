package com.kaab.controller;

import com.kaab.dao.UserDao;
import com.kaab.service.AdminService;
import com.kaab.service.JwtService;
import com.kaab.service.StudentService;
import com.kaab.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class StudentController {
    @Autowired
    private JwtService jwtService;
    @Autowired
    private UserService userService;

    @Autowired
    private UserDao userDao;
    @Autowired
    private StudentService studentService;
}
