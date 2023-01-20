package com.miguelbarrios.daytrader.accountservice.repository;

import com.miguelbarrios.daytrader.accountservice.entities.Account;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface AccountRepository extends JpaRepository<Account, Integer> {
   boolean existsByUserId(int userId);
   Account findByUserId(int userId);
}
