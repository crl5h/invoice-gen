package com.invoice.demo_inv.utils;

import java.util.Date;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class InvoiceDto {
    private int userId;
    private Date invoiceDate;
    private Date dueDate;
    private Double totalAmount;

    // Getters and Setters
}
