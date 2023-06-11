package com.kaab.dao;

import com.kaab.entity.Student;
import com.kaab.entity.User;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface StudentDao extends CrudRepository<Student,String> {

    Optional<Student> findById(String id);

}
