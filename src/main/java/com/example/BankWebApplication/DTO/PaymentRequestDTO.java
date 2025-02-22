package com.example.BankWebApplication.DTO;

import lombok.Data;

@Data
public class PaymentRequestDTO {
    private Long userId;
    private Long receiverCustomerId;
    private Double amount;
    private Long senderBankId;
    private Long receiverBankId;
}
