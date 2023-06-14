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
import org.springframework.security.access.prepost.PreAuthorize;
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

    @Autowired
    private StudentDao studentDao;
    @Autowired
    private AdminService teacherService;
    @Autowired
    private PasswordEncoder passwordEncoder;


    //  ONLY ADMIN COULD SEE ALL THE USERS
    @GetMapping("/admin/users")
    @PreAuthorize("hasRole('ADMIN')")
    public List<User> getAllUsers() {
        return userDao.findAll();
    }

    @GetMapping("/admin/teachers")
    @PreAuthorize("hasRole('ADMIN')")
    public List<Teacher> getAllTeachers() {
        return (List<Teacher>) teacherDao.findAll();
    }

    @GetMapping("/admin/students")
    @PreAuthorize("hasRole('ADMIN')")
    public List<Student> getAllStudents() {
        return (List<Student>) studentDao.findAll();
    }

    @PostMapping({"/admin/registerNewUser"})
    @PreAuthorize("hasRole('ADMIN')")
    public User registerNewUser(@RequestBody User user) {
        return userDao.save(user);
    }

    @Transactional
    @PutMapping({"/admin/registerNewStudent"})
    @PreAuthorize("hasRole('ADMIN')")
    public Student registerNewStudent(@RequestBody Student student) {
        student.getUser().setUserPassword(passwordEncoder.encode(student.getUser().getUserPassword()));
        student.getUser().setActivationStatus(ActivationStatus.ACTIVE);
        return studentDao.save(student);
    }

    @Transactional
    @PostMapping({"/admin/registerNewTeacher"})
    @PreAuthorize("hasRole('ADMIN')")
    public Teacher registerTeacher(@RequestBody Teacher teacher) {
        teacher.getUser().setUserPassword(passwordEncoder.encode(teacher.getUser().getUserPassword()));
        return teacherDao.save(teacher);
    }

    // THIS IS FOR ADMIN ONLY
    // ADMIN CAN FIND ANY USER WITH THE USER ID
    @GetMapping("/admin/users/{stringId}")        // ok
    @PreAuthorize("hasRole('ADMIN')")
    public User getUserDataByAdmin(@PathVariable String stringId) {
        Optional<User> userOptional = userDao.findById(stringId);

        return userOptional.orElseThrow(() -> new RuntimeException("User not found with string ID: " + stringId));
    }


    @PutMapping("/admin/activeAccount/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public User makeAccountActive(@PathVariable String id) {
        User currentUser = userDao.findById(id)
                .orElseThrow(() -> new RuntimeException("User not found with ID: " + id));
        currentUser.setActivationStatus(ActivationStatus.ACTIVE);
        return userDao.save(currentUser);
    }

    @PutMapping("/admin/deactiveAccount/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public User makeAccountDeactive(@PathVariable String id) {
        User currentUser = userDao.findById(id)
                .orElseThrow(() -> new RuntimeException("User not found with ID: " + id));
        currentUser.setActivationStatus(ActivationStatus.DEACTIVE);
        return userDao.save(currentUser);
    }


}