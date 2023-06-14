package com.kaab.controller;

import com.kaab.dao.StudentDao;
import com.kaab.dao.TeacherDao;
import com.kaab.dao.UserDao;
import com.kaab.entity.RoleType;
import com.kaab.entity.Student;
import com.kaab.entity.Teacher;
import com.kaab.entity.User;
import com.kaab.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import javax.annotation.PostConstruct;
import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

@RestController
public class UserController {

    @Autowired
    private UserService userService;

    @Autowired
    private UserDao userDao;
    @Autowired
    private StudentDao studentDao;
    @Autowired
    private TeacherDao teacherDao;
    @Autowired
    private PasswordEncoder passwordEncoder;
    public UserController(UserService theUserService,UserDao theUserDao){

        userService = theUserService;
        userDao = theUserDao;

    }
    @PostConstruct
    public void initRoleAndUser() {
        userService.initRoleAndUser();
    }


    // COMMON FOR ADMIN,STUDENT AND TEACHER
    @PutMapping("/users/resetPassword")
    public User resetPassword(@RequestBody User updatedUser) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String userId = null;
        if (authentication != null && authentication.isAuthenticated()) {
            userId = authentication.getName();
        }
        String finalUserId = userId;
        User currentLoggedUser = userDao.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found with ID: " + finalUserId));
        currentLoggedUser.setUserPassword(getEncodedPassword(updatedUser.getUserPassword()));
        return userDao.save(currentLoggedUser);
    }


    // EVERY ROLE CAN ACCESS THIS TO VIEW HIS OWN PROFILE
    @GetMapping("users/viewProfile")
    public ResponseEntity<Optional<User>> getCurrentUser(Authentication authentication) {
        String currentUserId = authentication.getName();
        Optional<User> userOptional = userDao.findById(currentUserId);
        if (userOptional.isPresent()) {
            User currentUser = userOptional.get();
            return ResponseEntity.ok(Optional.of(currentUser));
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    public String getEncodedPassword(String password) {
        return passwordEncoder.encode(password);
    }

}