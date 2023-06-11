package com.kaab.dao;

import com.kaab.entity.Request;
import org.springframework.data.repository.CrudRepository;

public interface RequestDao extends CrudRepository<Request,Long> {

}
