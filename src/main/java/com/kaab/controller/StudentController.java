package com.kaab.controller;

import com.kaab.dao.StudentDao;
import com.kaab.dao.UserDao;
import com.kaab.entity.Student;
import com.kaab.entity.User;
import com.kaab.service.AdminService;
import com.kaab.service.JwtService;
import com.kaab.service.StudentService;
import com.kaab.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.transaction.Transactional;

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
    @Autowired
    private StudentDao studentDao;

    @Transactional
    @PutMapping("/student/editProfile/{id}")
    @PreAuthorize("hasRole('STUDENT')")
    public Student editProfile(@PathVariable String id, @RequestBody Student updatedUser) {
        Student existingUser = studentDao.findById(id)
                .orElseThrow(() -> new RuntimeException("User not found with ID: " + id));
        existingUser.setUser(existingUser.getUser());
        return studentDao.save(updatedUser);
    }


}