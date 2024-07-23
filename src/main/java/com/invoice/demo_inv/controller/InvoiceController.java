package com.invoice.demo_inv.controller;

import com.invoice.demo_inv.entity.Invoice;
import com.invoice.demo_inv.service.InvoiceService;
import com.invoice.demo_inv.utils.InvoiceDto;
import com.invoice.demo_inv.utils.InvoiceUpdateDto;

import lombok.RequiredArgsConstructor;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.List;

import javax.mail.MessagingException;

@RestController
@RequestMapping("/api/invoice")
@RequiredArgsConstructor
public class InvoiceController {

    private final InvoiceService invoiceService;

    @GetMapping
    public ResponseEntity<List<Invoice>> getAllInvoices() {
        List<Invoice> invoices = invoiceService.getAllInvoices();
        return ResponseEntity.ok(invoices);
    }

    @PostMapping
    public ResponseEntity<Invoice> createInvoice(@RequestBody InvoiceDto invoiceDto) {
        Invoice createdInvoice = invoiceService.createInvoice(invoiceDto);
        return new ResponseEntity<>(createdInvoice, HttpStatus.CREATED);
    }

    @GetMapping("/{invoiceNumber}")
    public ResponseEntity<Invoice> getInvoiceById(@PathVariable int invoiceNumber) {
        Invoice invoice = invoiceService.getInvoiceById(invoiceNumber);
        if (invoice != null) {
            return ResponseEntity.ok(invoice);
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
    }

    @PutMapping("/{invoiceNumber}")
    public ResponseEntity<Invoice> updateInvoice(@PathVariable int invoiceNumber,
            @RequestBody InvoiceUpdateDto invoiceUpdateDto) {
        Invoice updatedInvoice = invoiceService.updateInvoice(invoiceNumber, invoiceUpdateDto);
        if (updatedInvoice != null) {
            return ResponseEntity.ok(updatedInvoice);
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
    }

    @DeleteMapping("/{invoiceNumber}")
    public ResponseEntity<Void> deleteInvoice(@PathVariable int invoiceNumber) {
        try {
            invoiceService.deleteInvoice(invoiceNumber);
            return ResponseEntity.noContent().build();
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }

        @GetMapping("/download/{invoiceId}")
    public ResponseEntity<byte[]> getPdf(@PathVariable int invoiceId) {
        try {
            Invoice invoice = invoiceService.getInvoiceById(invoiceId);
            byte[] response = invoiceService.generatePdf(invoice);
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_PDF);
            headers.setContentDispositionFormData("filename", "invoice.pdf");
            headers.setCacheControl("must-revalidate, post-check=0, pre-check=0");
            // System.out.println("Sending PDF");
            return new ResponseEntity<>(response, headers, HttpStatus.OK);
        } catch (IOException e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping("/email")
    public ResponseEntity<String> sendInoviceByEmail(@RequestBody int invoiceId) {
        try {
            invoiceService.sendEmail(invoiceId);
            return new ResponseEntity<>("Email sent", HttpStatus.OK);
        } catch (MessagingException e) {
            return new ResponseEntity<>("Email not sent", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping("/bulk-email")
    public ResponseEntity<String> sendBulkEmailsToAllUsers() {
        invoiceService.sendBulkEmails();
        return new ResponseEntity<>("Bulk emails sent", HttpStatus.OK);
    }
}


// /api/invoice/download/{id} GET, returns a single invoice as a PDF
// /api/invoice/email/ POST, sendsEmail to a single user. PARAMS: invoiceId
// /api/invoice/bulk-email POST, sendsEmail with invoices to all users. PARAMS: none

// /api/invoice/ GET, returns all invoices
// /api/invoice/{id} GET, returns a single invoice
// /api/invoice/ POST, creates a new invoice. PARAMS: invoiceNumber,invoiceDate, invoiceAmount, customerName
// /api/invoice/{id} PUT, updates a single invoice. PARAMS: invoiceNumber, invoiceDate, invoiceAmount,customerName
