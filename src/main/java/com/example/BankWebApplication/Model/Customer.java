package com.example.BankWebApplication.Model;

import com.fasterxml.jackson.annotation.*;
import jakarta.persistence.*;
import lombok.*;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "customerId")
@Table(name = "customer")
public class Customer {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long customerId;

    // Explicitly map to "user_id" column
    @Column(name = "user_id", nullable = false, unique = true)
    private Long userId;

    @Column(nullable = false)
    private Double bankBalance = 50000.0; // Default balance

    @Column(nullable = false)
    private String name;

    @Column(name="customer_email")
    private String customerEmail;

    @JsonIgnore
    @OneToMany(mappedBy = "customer", cascade = CascadeType.ALL)
    private List<Payment> payments;

    @ManyToOne
    @JoinColumn(name = "bank_id", referencedColumnName = "bankId", nullable = false)
    private Bank bank;

    @JsonProperty("bankName")
    public String getBankName() {
        return this.bank != null ? this.bank.getBankName() : null;
    }
}
