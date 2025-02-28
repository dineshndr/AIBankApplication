package com.example.BankWebApplication.Repository;

import com.example.BankWebApplication.Model.Bank;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BankRepository extends JpaRepository<Bank, Long> {
}
