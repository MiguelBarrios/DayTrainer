package com.miguelbarrios.daytrader.userservice.repository;

import com.miguelbarrios.daytrader.userservice.model.AppUser;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<AppUser, Long> {
    AppUser findByUsername(String username);



}
