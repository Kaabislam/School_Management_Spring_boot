package com.kaab.dao;

import com.kaab.entity.Teacher;
import com.kaab.entity.User;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface TeacherDao extends CrudRepository<Teacher,String> {
    Optional<Teacher> findById(String id);

    @Query("SELECT s.id FROM Student s WHERE s.advisorId = :teacherId")
    List<String> findStudentIdsByTeacherId(@Param("teacherId") String teacherId);


}
