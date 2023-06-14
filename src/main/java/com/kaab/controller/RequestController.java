// SENDING REQUEST , ACCEPT OR REJECT REQUEST , FIND ALL THE REQUESTS ARE HANDLED IN REQUEST CONTROLLER

package com.kaab.controller;

import com.kaab.dao.RequestDao;
import com.kaab.dao.StudentDao;
import com.kaab.dao.TeacherDao;
import com.kaab.entity.*;
import com.kaab.service.RequestService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
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

    // ONLY STUDENT CAN SEND REQUEST TO TEACHERS
    // if sending is successful then request status will be pending initially
    @Transactional
    @PostMapping("/request")
    @PreAuthorize("hasRole('STUDENT')")
    public Request createRequest(@RequestBody Request request) {
        return requestDao.save(request);
    }

    // GET ALL THE REQUEST ON DATABASE
    // ONLY ADMIN AUTHORIZED CAN ACCESS
    @Transactional
    @GetMapping("/request")
    @PreAuthorize("hasRole('ADMIN')")
    public List<Request> getAllRequest() throws Exception {
        return requestDao.findAll();
    }

    // A TEACHER FIND OUT ALL THE REQUEST CURRENTLY REQUESTED
    // USE REQUEST TABLE
    @GetMapping("/request/{teacherId}")
    @PreAuthorize("hasRole('TEACHER')")
    public ResponseEntity<List<Request>> getAllRequest(@PathVariable("teacherId") String teacherId) throws Exception{
        List<Request> requestList = requestDao.findAllByTeacherId(teacherId);
        return ResponseEntity.ok(requestList);
    }

    // ACCEPT A REQUEST FROM TEACHERS END
    // ALSO UPDATES ON STUDENT TABLE MAKE THE TEACHER AS ADVISOR OF REQUESTED STUDENT
    @Transactional
    @PutMapping("/acceptRequest/{requestId}")
    @PreAuthorize("hasRole(TEACHER)")
    public ResponseEntity<Request> acceptRequest(@PathVariable("requestId") Long requestId){
        Request currentRequest = requestDao.findById(requestId)
                .orElseThrow(() -> new RuntimeException("Request not found with ID: " + requestId));
        // UPDATE REQUEST TABLE
        currentRequest.setStatus(RequestStatus.ACCEPTED);
        // ALSO MODIFIED ON STUDENT TABLE
        currentRequest.getStudent().setAdvisorId(currentRequest.getTeacher().getUserName());
        return ResponseEntity.ok(currentRequest);
    }


    // REJECT A REQUEST
    @Transactional
    @PutMapping("/rejectRequest/{requestId}")
    @PreAuthorize("hasRole(TEACHER)")
    public ResponseEntity<Request> rejectRequest(@PathVariable("requestId") Long requestId){
        Request currentRequest = requestDao.findById(requestId)
                .orElseThrow(() -> new RuntimeException("Request not found with ID: " + requestId));
        currentRequest.setStatus(RequestStatus.REJECTED);
        return ResponseEntity.ok(currentRequest);
    }
}