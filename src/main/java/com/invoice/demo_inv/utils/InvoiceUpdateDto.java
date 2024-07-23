package com.invoice.demo_inv.utils;

import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
public class InvoiceUpdateDto {
    private int invoiceNumber;
    private Date invoiceDate;
    private Date dueDate;
    private double totalAmount;
}
