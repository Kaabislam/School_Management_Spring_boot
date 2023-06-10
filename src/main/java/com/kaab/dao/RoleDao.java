package com.kaab.dao;

import com.kaab.entity.Role;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RoleDao extends CrudRepository<Role, String> {
    Role findByRoleName(String roleName);

}
