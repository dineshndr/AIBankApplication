package com.example.BankWebApplication.Repository;

import com.example.BankWebApplication.Model.Customer;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;

public interface CustomerRepository extends JpaRepository<Customer, Long> {
    Optional<Customer> findByCustomerId(Long customerId);
    Optional<Customer> findByUserId(Long userId);
    Optional<Customer> findByCustomerEmail(String email);

}
