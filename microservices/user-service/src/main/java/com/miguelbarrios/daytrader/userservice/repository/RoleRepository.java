package com.miguelbarrios.daytrader.userservice.repository;

import com.miguelbarrios.daytrader.userservice.model.Role;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RoleRepository extends JpaRepository<Role, Long> {
    Role findByName(String name);
}
