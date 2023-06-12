package com.kaab.service;

import com.kaab.dao.UserDao;
import com.kaab.entity.ActivationStatus;
import com.kaab.entity.RoleType;
import com.kaab.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Set;

@Service
public class UserService {

    @Autowired
    private UserDao userDao;



    @Autowired
    private PasswordEncoder passwordEncoder;




    public void initRoleAndUser() {


        User adminUser = new User();
        adminUser.setUserName("admin");
        adminUser.setUserPassword(getEncodedPassword("admin"));
        adminUser.setActivationStatus(ActivationStatus.ACTIVE);
        adminUser.setUserFirstName("admin");
        adminUser.setUserLastName("admin");
        adminUser.setRoleType(RoleType.ADMIN);
        userDao.save(adminUser);



    }




    public String getEncodedPassword(String password) {
        return passwordEncoder.encode(password);
    }



}
