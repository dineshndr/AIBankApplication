package com.example.BankWebApplication.Service;

import com.example.BankWebApplication.Model.*;
import com.example.BankWebApplication.Repository.*;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class PaymentService {

    private final PaymentRepository paymentRepository;
    private final CustomerRepository customerRepository;
    private final BankRepository bankRepository;

    public Payment createPayment(Long senderCustomerId, Long receiverCustomerId, Double amount, Long senderBankId, Long receiverBankId) {
        Customer senderCustomer = customerRepository.findByUserId(senderCustomerId)
                .orElseThrow(() -> new RuntimeException("Sender Customer not found"));

        Customer receiverCustomer = customerRepository.findByUserId(receiverCustomerId)
                .orElseThrow(() -> new RuntimeException("Receiver Customer not found"));

        // Fetch Bank entities using the IDs
        Bank senderBank = bankRepository.findById(senderBankId)
                .orElseThrow(() -> new RuntimeException("Sender Bank not found"));

        Bank receiverBank = bankRepository.findById(receiverBankId)
                .orElseThrow(() -> new RuntimeException("Receiver Bank not found"));

        senderCustomer.setBankBalance(senderCustomer.getBankBalance()-amount);
        receiverCustomer.setBankBalance(receiverCustomer.getBankBalance()+amount);
        // Create Payment object
        Payment payment = new Payment(senderCustomer, receiverCustomer, amount, senderBank, receiverBank);

        return paymentRepository.save(payment);
    }

    public Optional<Bank> getBankById(Long paymentId) {
        return bankRepository.findById(paymentId);
    }

    public Optional<Payment> getPaymentById(Long paymentId) {
        return paymentRepository.findById(paymentId);
    }

    public List<Payment> getPaymentHistoryByUserId(Long userId) {
        return paymentRepository.findByCustomer_UserId(userId);
    }

    public Optional<Customer> getCustomerByCustomerId(Long customerId) {
        return customerRepository.findByCustomerId(customerId);
    }

    public List<Payment> getPaymentsByStatus(String status) {
        return paymentRepository.findByStatus(status);
    }
}
