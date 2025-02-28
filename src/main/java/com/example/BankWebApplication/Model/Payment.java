package com.example.BankWebApplication.Model;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "id")
@Table(name = "payment")
public class Payment {



    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "user_id", referencedColumnName = "user_id", nullable = false)
    private Customer customer;

    @Column(nullable = false)
    private Double amount;

    @Column(nullable = false)
    private String status;

    @Column(name = "created_at", nullable = false, updatable = false)
    private LocalDateTime createdAt;

    @ManyToOne
    @JoinColumn(name = "sender_bank_id", referencedColumnName = "bankId", nullable = false)
    private Bank senderBank;

    @ManyToOne
    @JoinColumn(name = "receiver_bank_id", referencedColumnName = "bankId", nullable = false)
    private Bank receiverBank;

    @ManyToOne
    @JoinColumn(name="receiver_customer_id",referencedColumnName = "user_id",nullable=false)
    private Customer receiverCustomer;

    @PrePersist
    protected void onCreate() {
        createdAt = LocalDateTime.now();
    }

    public Payment(Customer senderCustomer, Customer receiverCustomer, Double amount, Bank SenderBank, Bank ReceiverBank) {
        this.customer = senderCustomer;
        this.receiverCustomer = receiverCustomer;
        this.amount = amount;
        this.senderBank=SenderBank;
        this.receiverBank=ReceiverBank;
        this.status="pending";
    }
}
