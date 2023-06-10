package com.kaab.dao;

import com.kaab.entity.User;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserDao extends CrudRepository<User, String> {
    List<User> findAll();
    Optional<User> findById(String id);
//    Optional<User> findByIdentifier(String identifi
    Optional<User> findByIdAndUserId(String userName);



}
