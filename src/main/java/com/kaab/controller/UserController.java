package com.kaab.controller;

import com.kaab.dao.UserDao;
import com.kaab.entity.User;
import com.kaab.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import javax.annotation.PostConstruct;
import java.util.List;
import java.util.Optional;

@RestController
public class UserController {

    @Autowired
    private UserService userService;

    @Autowired
    private UserDao userDao;
    public UserController(UserService theUserService,UserDao theUserDao){

        userService = theUserService;
        userDao = theUserDao;

    }
    @PostConstruct
    public void initRoleAndUser() {
        userService.initRoleAndUser();
    }

    @PostMapping({"/registerNewUser"})
//    @PreAuthorize("hasRole('Admin')")
    public User registerNewUser(@RequestBody User user) {
        return userService.registerNewUser(user);
    }

    @GetMapping({"/forAdmin"})
    @PreAuthorize("hasRole('Admin')")
    public String forAdmin(){
        return "This URL is only accessible to the admin";
    }

    @GetMapping({"/forUser"})
    @PreAuthorize("hasRole('User')")
    public String forUser(){
        return "This URL is only accessible to the user";
    }


    @GetMapping("/users")
    public List<User> getAllUsers() {
        return userDao.findAll();
    }

//    @GetMapping("/users/{identifier}")
//    public User getUserByIdentifier(@PathVariable String identifier) {
//        Optional<User> userOptional = userDao.findByIdentifier(identifier);
//
//        return userOptional.orElseThrow(() -> new RuntimeException("User not found with identifier: " + identifier));
//    }


    // this is for admin
    // admin can find any user with his id
    @GetMapping("/users/{stringId}")
    @PreAuthorize("hasRole('Admin')")
    public User getUserDataByAdmin(@PathVariable String stringId) {
        Optional<User> userOptional = userDao.findById(stringId);

        return userOptional.orElseThrow(() -> new RuntimeException("User not found with string ID: " + stringId));
    }


    @GetMapping("/users/{stringId}")
    @PreAuthorize("hasRole('User')")
    public User getUserDataByTeacherAndStudent(@PathVariable String stringId) {
        // Retrieve the currently authenticated user
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        Long userId = Long.parseLong(authentication.getName());

        Optional<User> userOptional = userDao.findByIdAndUserId(stringId);

        return userOptional.orElseThrow(() -> new RuntimeException("User not found with ID: " + stringId));

    }


    @PutMapping("/users/{id}")
    public User updateUserActivationInfo(@PathVariable String id, @RequestBody User updatedUser) {
        User existingUser = userDao.findById(id)
                .orElseThrow(() -> new RuntimeException("User not found with ID: " + id));

//        existingUser.setUserName(updatedUser.getUserName());
        existingUser.setIsActivated(updatedUser.getIsActivated());

        return userDao.save(existingUser);
    }

    @PutMapping("/users/editProfile/{id}")
    public User editTeacherOrStudentProfile(@PathVariable String id, @RequestBody User updatedUser) {
        User existingUser = userDao.findById(id)
                .orElseThrow(() -> new RuntimeException("User not found with ID: " + id));

        // existingUser.setUserName(updatedUser.getUserName());
        // user name is the id
        // it will not be changed
        existingUser.setUserFirstName(updatedUser.getUserFirstName());
        existingUser.setUserLastName(updatedUser.getUserLastName());
        existingUser.setDepartmentName(updatedUser.getDepartmentName());

        return userDao.save(existingUser);
    }



}
