package com.kaab.controller;

import com.kaab.dao.UserDao;
import com.kaab.service.JwtService;
import com.kaab.service.TeacherService;
import com.kaab.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class TeacherController {
    @Autowired
    private JwtService jwtService;
    @Autowired
    private UserService userService;

    @Autowired
    private UserDao userDao;
    @Autowired
    private TeacherService teacherService;

}
