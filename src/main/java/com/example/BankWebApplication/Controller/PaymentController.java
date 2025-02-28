package com.example.BankWebApplication.Controller;

import com.example.BankWebApplication.DTO.PaymentRequestDTO;
import com.example.BankWebApplication.DTO.BankDTO;
import com.example.BankWebApplication.Model.Bank;
import com.example.BankWebApplication.Model.Customer;
import com.example.BankWebApplication.Model.Payment;
import com.example.BankWebApplication.Service.PaymentService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;
import java.util.Optional;

@RestController
@CrossOrigin(origins = "http://localhost:3000")
@RequestMapping("/api/payments")
@RequiredArgsConstructor
public class PaymentController {

    private final PaymentService paymentService;

    // Endpoint to create a payment
    @PostMapping("/create")
    public ResponseEntity<Payment> createPayment(@RequestBody PaymentRequestDTO request) {
        Payment savedPayment = paymentService.createPayment(
                request.getUserId(),
                request.getReceiverCustomerId(),
                request.getAmount(),
                request.getSenderBankId(),
                request.getReceiverBankId()
        );
        return new ResponseEntity<>(savedPayment, HttpStatus.CREATED);
    }

    // Endpoint to get a payment by ID
    @GetMapping("/{paymentId}")
    public Optional<Payment> getPaymentById(@PathVariable Long paymentId) {
        return paymentService.getPaymentById(paymentId);
    }

    // Endpoint to get payment history for a user
    @GetMapping("/history/{userId}")
    public ResponseEntity<List<Payment>> getPaymentHistoryByUserId(@PathVariable Long userId) {
        List<Payment> paymentHistory = paymentService.getPaymentHistoryByUserId(userId);
        return !paymentHistory.isEmpty()
                ? new ResponseEntity<>(paymentHistory, HttpStatus.OK)
                : new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    // Endpoint to get a customer by customerId
    @GetMapping("/customer/{customerId}")
    public ResponseEntity<Customer> getCustomerByCustomerId(@PathVariable Long customerId) {
        Optional<Customer> customer = paymentService.getCustomerByCustomerId(customerId);
        return customer.map(c -> new ResponseEntity<>(c, HttpStatus.OK))
                .orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    // Endpoint to get payments by status
    @GetMapping("/status/{status}")
    public ResponseEntity<List<Payment>> getPaymentsByStatus(@PathVariable String status) {
        List<Payment> payments = paymentService.getPaymentsByStatus(status);
        return !payments.isEmpty()
                ? new ResponseEntity<>(payments, HttpStatus.OK)
                : new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    // Endpoint to get a bank by ID (returning BankDTO)
    @GetMapping("/banks/{bankId}")
    public ResponseEntity<BankDTO> getBankById(@PathVariable Long bankId) {
        Optional<Bank> bankOptional = paymentService.getBankById(bankId);
        if (bankOptional.isPresent()) {
            Bank bank = bankOptional.get();
            BankDTO bankDTO = new BankDTO();
            bankDTO.setBankId(bank.getBankId());
            bankDTO.setBankName(bank.getBankName());
            return new ResponseEntity<>(bankDTO, HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }
}
