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

import java.util.List;
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




    @GetMapping("/{teacherId}/students")
    public ResponseEntity<List<String>> getStudentIdsByTeacherId(@PathVariable("teacherId") String teacherId) {
        List<String> studentIds = teacherDao.findStudentIdsByTeacherId(teacherId);
        return ResponseEntity.ok(studentIds);
    }
    @GetMapping("/b")
    public String getStudentIdsByTeacherId() {
        return "Asdas";
    }
}