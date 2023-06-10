package com.kaab.controller;

import com.kaab.dao.TeacherDao;
import com.kaab.dao.UserDao;
import com.kaab.entity.Teacher;
import com.kaab.entity.User;
import com.kaab.service.JwtService;
import com.kaab.service.TeacherService;
import com.kaab.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
public class TeacherController {
    @Autowired
    private JwtService jwtService;
    @Autowired
    private UserService userService;

    @Autowired
    private UserDao userDao;
    @Autowired
    private TeacherDao teacherDao;
    @Autowired
    private TeacherService teacherService;




//    @GetMapping("/teacher/{stringId}")
//    public User getUserDataByAdmin(@PathVariable String stringId) {
//        Optional<User> userOptional = userDao.findById(stringId);
//
//        return userOptional.orElseThrow(() -> new RuntimeException("User not found with string ID: " + stringId));
//    }

}
