package com.invoice.demo_inv.entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

import com.fasterxml.jackson.annotation.JsonIgnore;

import java.util.Date;

@Entity
@Table(name = "invoices")
@Getter
@Setter
public class Invoice {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "invoice_id")
    private int invoiceId;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    // @JsonBackReference
    @JsonIgnore
    private User user;

    @Column(name = "invoice_date", nullable = false)
    private Date invoiceDate;

    @Column(name = "due_date", nullable = false)
    private Date dueDate;

    @Column(name = "total_amount", nullable = false)
    private Double totalAmount;

}
