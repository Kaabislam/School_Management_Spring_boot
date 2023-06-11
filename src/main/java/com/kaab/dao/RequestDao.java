package com.kaab.dao;

import com.kaab.entity.Request;
import com.kaab.entity.User;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RequestDao extends CrudRepository<Request,Long> {
    List<Request> findAll();
    @Query("SELECT r FROM Request r WHERE r.teacher.id = :teacherId")
    List<Request> findAllByTeacherId(String teacherId);

}
