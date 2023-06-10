package com.kaab.controller;

import com.kaab.dao.StudentDao;
import com.kaab.dao.TeacherDao;
import com.kaab.dao.UserDao;
import com.kaab.entity.Role;
import com.kaab.entity.Student;
import com.kaab.entity.Teacher;
import com.kaab.entity.User;
import com.kaab.service.AdminService;
import com.kaab.service.JwtService;
import com.kaab.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@RestController
public class AdminController {
    @Autowired
    private JwtService jwtService;
    @Autowired
    private UserService userService;

    @Autowired
    private UserDao userDao;
    @Autowired
    private TeacherDao teacherDao;
    @Autowired
    private StudentDao studentDao;
    @Autowired
    private AdminService teacherService;


    @GetMapping("/admin/users")
    public List<User> getAllUsers() {
        return userDao.findAll();
    }
    @GetMapping("/admin/teachers")
    public List<Teacher> getAllTeachers() {
        return (List<Teacher>) teacherDao.findAll();
    }

    @GetMapping("/admin/students")
    public List<Student> getAllStudents() {
        return (List<Student>) studentDao.findAll();
    }

    @PutMapping("/admin/upadateActivationInfo/{id}")
    public User updateActivationInfo(@PathVariable String id, @RequestBody User updatedUser) {
        User existingUser = userDao.findById(id)
                .orElseThrow(() -> new RuntimeException("User not found with ID: " + id));

//        existingUser.setUserName(updatedUser.getUserName());
        existingUser.setIsActivated(updatedUser.getIsActivated());

        return userDao.save(existingUser);
    }

//    @PutMapping("/admin/addRole/{id}")
//    public User assignRole(@PathVariable String id, @RequestBody User updatedUser) {
//        Set<Role> roles = new HashSet<>();
//        roles.add(new Role());
//        updatedUser.setRole(roles);
//        updatedUser.setUserName(updatedUser.getUserName());
//        return userDao.save(updatedUser);
//
//    }



}
