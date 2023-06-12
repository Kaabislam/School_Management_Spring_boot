package com.kaab.controller;

import com.kaab.dao.StudentDao;
import com.kaab.dao.TeacherDao;
import com.kaab.dao.UserDao;
import com.kaab.entity.ActivationStatus;
import com.kaab.entity.Student;
import com.kaab.entity.Teacher;
import com.kaab.entity.User;
import com.kaab.service.AdminService;
import com.kaab.service.JwtService;
import com.kaab.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import javax.transaction.Transactional;
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
    //    @Autowired
//    private RoleDao roleDao;
    @Autowired
    private StudentDao studentDao;
    @Autowired
    private AdminService teacherService;
    @Autowired
    private PasswordEncoder passwordEncoder;


    @GetMapping("/admin/users")     // ok
    public List<User> getAllUsers() {
        return userDao.findAll();
    }
    @GetMapping("/admin/teachers")  // ok
    public List<Teacher> getAllTeachers() {
        return (List<Teacher>) teacherDao.findAll();
    }

    @GetMapping("/admin/students")  // ok
    public List<Student> getAllStudents() {
        return (List<Student>) studentDao.findAll();
    }

    @PostMapping({"/admin/registerNewUser"})  // ok
//    @PreAuthorize("hasRole('Admin')")
    public User registerNewUser(@RequestBody User user) {
        return userDao.save(user);
    }

    @Transactional
    @PutMapping({"/admin/registerNewStudent"})   //ok
//    @PreAuthorize("hasRole('Admin')")
    public Student registerNewStudent(@RequestBody Student student) {
        student.getUser().setUserPassword(passwordEncoder.encode(student.getUser().getUserPassword()));
        student.getUser().setActivationStatus(ActivationStatus.ACTIVE);
        return studentDao.save(student);
    }

    @Transactional          //ok
    @PostMapping({"/admin/registerNewTeacher"})
//    @PreAuthorize("hasRole('Admin')")
    public Teacher registerTeacher(@RequestBody Teacher teacher) {
        teacher.getUser().setUserPassword(passwordEncoder.encode(teacher.getUser().getUserPassword()));
        return teacherDao.save(teacher);
    }




    @PutMapping("/admin/activeAccount/{id}")        // ok
    public User makeAccountActive(@PathVariable String id) {
        User currentUser = userDao.findById(id)
                .orElseThrow(() -> new RuntimeException("User not found with ID: " + id));
        currentUser.setActivationStatus(ActivationStatus.ACTIVE);
        return userDao.save(currentUser);
    }

    @PutMapping("/admin/deactiveAccount/{id}")        // ok
    public User makeAccountDeactive(@PathVariable String id) {
        User currentUser = userDao.findById(id)
                .orElseThrow(() -> new RuntimeException("User not found with ID: " + id));
        currentUser.setActivationStatus(ActivationStatus.DEACTIVE);
        return userDao.save(currentUser);
    }


}