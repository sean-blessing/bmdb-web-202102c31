package com.bmdb.db;

import org.springframework.data.jpa.repository.JpaRepository;

import com.bmdb.business.Credit;

public interface CreditRepo extends JpaRepository<Credit, Integer> {

}
