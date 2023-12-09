package com.jonas.PaymentAccounts.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name ="tb_transaction")
public class Transaction {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    @ManyToOne
    @JoinColumn(nullable = false)
    private User payer;
    @ManyToOne
    @JoinColumn(nullable = false)
    private User payee;
    @Column(name = "Tansaction_value")
    private BigDecimal value;
}
