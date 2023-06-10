package com.kaab.dao;

import com.kaab.entity.Teacher;
import com.kaab.entity.User;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface TeacherDao extends CrudRepository<Teacher,String> {
    Optional<Teacher> findById(String id);

}
