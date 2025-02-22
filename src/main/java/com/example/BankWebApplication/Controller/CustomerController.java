package com.example.BankWebApplication.Controller;

import com.example.BankWebApplication.Repository.CustomerRepository;
import com.example.BankWebApplication.Repository.PaymentRepository;
import com.example.BankWebApplication.Service.PaymentService;
import com.example.BankWebApplication.Model.Payment;
import com.example.BankWebApplication.Model.Customer;
import com.example.BankWebApplication.Service.OpenAIService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

@RestController
@CrossOrigin(origins = "http://localhost:3000")
@RequestMapping("/api/customer")
public class CustomerController {

    @Autowired
    private PaymentService paymentService;

    @Autowired
    private OpenAIService openAIService;
    @Autowired
    private PaymentRepository paymentRepository;
    @Autowired
    private CustomerRepository customerRepository;



    @PostMapping("/transaction/history")
    public List<Payment> processCustomerQuery(@RequestBody String customerQuery, @RequestParam Long customerId) {
        // Declare the variable outside the try-catch block
        String processedRequest = null;

        // Step 1: Get customer transaction history based on request processed by OpenAI
        try {
            processedRequest = openAIService.processCustomerRequest(customerQuery);
            System.out.println("OpenAI Response: " + processedRequest);
        } catch (Exception e) {
            System.err.println("An error occurred while processing the request: " + e.getMessage());
            e.printStackTrace();
            // Optionally, return an empty list or throw a custom exception
            return Collections.emptyList();
        }

        // Step 2: If request is related to transaction history, fetch payments for the customer

        if (processedRequest != null && processedRequest.contains("transaction history")) {
            return paymentService.getPaymentHistoryByUserId(customerId);
        }


        // Default return if the request is not related to transaction history
        return Collections.emptyList();
    }


    @PostMapping("/transactionById")
    public Optional<Payment> processIndividualCustomerQuery(@RequestBody String customerQuery, @RequestParam Long customerId) {
        // Declare the variable outside the try-catch block
        String processedRequest = null;

        // Step 1: Get customer transaction history based on request processed by OpenAI
        try {
            processedRequest = openAIService.processCustomerRequest(customerQuery);
            System.out.println("OpenAI Response: " + processedRequest);
        } catch (Exception e) {
            System.err.println("An error occurred while processing the request: " + e.getMessage());
            e.printStackTrace();
            // Optionally, return an empty list or throw a custom exception
            return null;
        }

        // Step 2: If request is related to transaction history, fetch payments for the customer
        if ((processedRequest != null) && (processedRequest.contains("latest") || processedRequest.contains("recent") || processedRequest.contains("last"))) {
            List<Payment> payment_history = paymentService.getPaymentHistoryByUserId(customerId);
            Payment latest_payment = payment_history.getLast();
            //System.out.println("Latest Payment : " + latest_payment);
            return paymentRepository.findById((latest_payment.getId()));
        }
        return Optional.empty();
    }


    @GetMapping("/getCustomerDetails")
    public Optional<Customer> getCustomerDetails(@RequestParam String customerEmail) {
        //System.out.println(customerRepository.findByCustomerEmail(customerEmail).toString());

        return customerRepository.findByCustomerEmail(customerEmail);
    }
}


