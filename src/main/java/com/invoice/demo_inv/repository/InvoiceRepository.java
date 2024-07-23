package com.invoice.demo_inv.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.invoice.demo_inv.entity.Invoice;

import java.util.List;

@Repository
public interface InvoiceRepository extends JpaRepository<Invoice, Integer> {
    List<Invoice> findByUserId(int userId);

    Invoice findByInvoiceId(String invoiceId);

}
