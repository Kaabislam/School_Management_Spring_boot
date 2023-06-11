package com.kaab.controller;

import com.kaab.dao.RequestDao;
import com.kaab.dao.StudentDao;
import com.kaab.dao.TeacherDao;
import com.kaab.entity.*;
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

    // student send request on teachers end
    // if sending is successful then request status will be pending initially
    @Transactional
    @PostMapping("/request/{studentId}")
    public Request createRequest(@PathVariable String studentId, @RequestBody String teacherId) {
        Student currentStudent = studentDao.findById(studentId)
                .orElseThrow(() -> new RuntimeException("User not found with Student ID: " + studentId));
        Teacher currentTeacher = teacherDao.findById(teacherId)
                .orElseThrow(() -> new RuntimeException("User not found with teacher ID: " + teacherId));
        return requestService.createRequest(currentStudent,currentTeacher);
    }
    // find all the request
    // can be applicable for admin only
    @Transactional
    @GetMapping("/request")
    public List<Request> getAllRequest() throws Exception {
        return requestDao.findAll();
    }

    // find all the request for a teacher using teacher id
    // use request table
    @GetMapping("/request/{teacherId}")
    public ResponseEntity<List<Request>> getAllRequest(@PathVariable("teacherId") String teacherId) {
        List<Request> requestList = requestDao.findAllByTeacherId(teacherId);
        return ResponseEntity.ok(requestList);
    }

    // accept or reject a request
    // from teachers end
    @Transactional
    @PutMapping("/acceptRequest/{requestId}")
    public ResponseEntity<Request> acceptRequest(@PathVariable("requestId") Long requestId){
        Request currentRequest = requestDao.findById(requestId)
                .orElseThrow(() -> new RuntimeException("Request not found with ID: " + requestId));
        currentRequest.setStatus(RequestStatus.ACCEPTED);
        return ResponseEntity.ok(currentRequest);
    }

    @Transactional
    @PutMapping("/rejectRequest/{requestId}")
    public ResponseEntity<Request> rejectRequest(@PathVariable("requestId") Long requestId){
        Request currentRequest = requestDao.findById(requestId)
                .orElseThrow(() -> new RuntimeException("Request not found with ID: " + requestId));
        currentRequest.setStatus(RequestStatus.REJECTED);
        return ResponseEntity.ok(currentRequest);
    }
}