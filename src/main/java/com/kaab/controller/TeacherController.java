package com.kaab.controller;

import com.kaab.dao.StudentDao;
import com.kaab.dao.TeacherDao;
import com.kaab.dao.UserDao;
import com.kaab.entity.Student;
import com.kaab.entity.Teacher;
import com.kaab.service.JwtService;
import com.kaab.service.TeacherService;
import com.kaab.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.util.List;

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

    @Autowired
    private StudentDao studentDao;

    @Transactional
    @PutMapping("/teacher/editProfile/{id}")
    @PreAuthorize("hasRole('TEACHER')")       // preauthorize korte hobe nahole token chara edit kora hoye jacche
    public Teacher editProfile(@PathVariable String id, @RequestBody Teacher updatedUser) {
        Teacher existingUser = teacherDao.findById(id)
                .orElseThrow(() -> new RuntimeException("User not found with ID: " + id));
        existingUser.setUser(existingUser.getUser());
        return teacherDao.save(updatedUser);
    }

    // find all the student whose are in this teacher advising list
    @GetMapping("/{teacherId}/students")
    public ResponseEntity<List<String>> getStudentIdsByTeacherId(@PathVariable("teacherId") String teacherId) {
        List<String> studentIds = teacherDao.findStudentIdsByTeacherId(teacherId);
        return ResponseEntity.ok(studentIds);
    }

    // remove an student from advising list
    @Transactional
    @PutMapping("/teacher/{studentId}")
    public Student removeFromAdvisingList(@PathVariable("studentId") String studentId) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String userId = null;
        if (authentication != null && authentication.isAuthenticated()) {
            userId = authentication.getName();

        }
        Student currentStudent = studentDao.findById(studentId)
                .orElseThrow(() -> new RuntimeException("User not found with ID: " + studentId));
        if(!currentStudent.getAdvisorId().equals(userId)){

            throw new RuntimeException("this student is not in your advising list - "+ studentId );
        }

        currentStudent.setAdvisorId(null);
        currentStudent.getUser().setAdvisorId(currentStudent.getAdvisorId());
        return  currentStudent;
    }
}