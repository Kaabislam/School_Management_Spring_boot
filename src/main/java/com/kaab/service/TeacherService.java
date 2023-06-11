package com.kaab.service;

import com.kaab.dao.RoleDao;
import com.kaab.dao.TeacherDao;
import com.kaab.dao.UserDao;
import com.kaab.entity.Role;
import com.kaab.entity.Teacher;
import com.kaab.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.Query;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Set;

@Service
public class TeacherService {
    @Autowired
    private UserDao userDao;

    @Autowired
    private RoleDao roleDao;
    @Autowired
    private TeacherDao teacherDao;



}
