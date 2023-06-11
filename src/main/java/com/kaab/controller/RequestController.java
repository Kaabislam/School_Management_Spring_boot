package com.kaab.controller;

import com.kaab.dao.RequestDao;
import com.kaab.dao.StudentDao;
import com.kaab.dao.TeacherDao;
import com.kaab.entity.Request;
import com.kaab.entity.Student;
import com.kaab.entity.Teacher;
import com.kaab.entity.User;
import com.kaab.service.RequestService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class RequestController {
    @Autowired
    private final RequestService requestService;

    @Autowired
    private StudentDao studentDao;
    @Autowired
    private TeacherDao teacherDao;
    @Autowired
    private RequestDao requestDao;

    @Autowired
    public RequestController(RequestService requestService) {
        this.requestService = requestService;
    }

    @Transactional
    @PostMapping("/request/{studentId}")
    public Request createRequest(@PathVariable String studentId, @RequestBody String teacherId) {
        Student currentStudent = studentDao.findById(studentId)
                .orElseThrow(() -> new RuntimeException("User not found with Student ID: " + studentId));
        Teacher currentTeacher = teacherDao.findById(teacherId)
                .orElseThrow(() -> new RuntimeException("User not found with teacher ID: " + teacherId));
        return requestService.createRequest(currentStudent,currentTeacher);
    }
    @Transactional
    @GetMapping("/request")
    public List<Request> getAllRequest() throws Exception {
        return requestDao.findAll();
    }

    @GetMapping("/request/{teacherId}")
    public ResponseEntity<List<Request>> getAllRequest(@PathVariable("teacherId") String teacherId) {
        List<Request> requestList = requestDao.findAllByTeacherId(teacherId);
        return ResponseEntity.ok(requestList);
    }
}