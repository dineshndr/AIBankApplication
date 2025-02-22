package com.example.BankWebApplication.DTO;
import lombok.Data;

@Data
public class CustomerDTO {
    private Long customerId;
    private Long userId;
    private Double bankBalance;
    private String name;
    private BankDTO bank;
    private String customerEmail;


}
