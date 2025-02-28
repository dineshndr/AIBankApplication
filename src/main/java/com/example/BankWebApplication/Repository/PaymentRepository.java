package com.example.BankWebApplication.Repository;

import com.example.BankWebApplication.Model.Payment;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;
import java.util.Optional;

public interface PaymentRepository extends JpaRepository<Payment, Long> {
    List<Payment> findByCustomer_UserId(Long userId);
    List<Payment> findByStatus(String status);

}
