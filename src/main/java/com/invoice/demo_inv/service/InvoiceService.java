package com.invoice.demo_inv.service;

import com.invoice.demo_inv.entity.Invoice;
import com.invoice.demo_inv.entity.User;
import com.invoice.demo_inv.repository.InvoiceRepository;
import com.invoice.demo_inv.repository.UserRepository;
import com.invoice.demo_inv.utils.InvoiceDto;
import com.invoice.demo_inv.utils.InvoiceUpdateDto;

import lombok.RequiredArgsConstructor;

import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.PDPageContentStream;
import org.apache.pdfbox.pdmodel.font.PDType1Font;
import org.apache.pdfbox.pdmodel.font.Standard14Fonts;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.List;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;

@Service
@RequiredArgsConstructor
public class InvoiceService {
    @Autowired
    private InvoiceRepository invoiceRepository;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private JavaMailSender javaMailSender;

    public List<Invoice> getAllInvoices() {
        return invoiceRepository.findAll();
    }

    public Invoice getInvoiceById(int id) {
        return invoiceRepository.findById(id).orElse(null);
    }

    public List<Invoice> getInvoicesByUserId(int userId) {
        return invoiceRepository.findByUserId(userId);
    }

    public Invoice createInvoice(InvoiceDto invoice) {
        System.out.println("Id: " + invoice.getUserId());
        User user = userRepository.findById(invoice.getUserId());
        if (user == null) {
            throw new IllegalArgumentException("Invalid user ID");
        }
        Invoice newInvoice = new Invoice();
        newInvoice.setUser(user);
        newInvoice.setInvoiceDate(invoice.getInvoiceDate());
        newInvoice.setDueDate(invoice.getDueDate());
        newInvoice.setTotalAmount(invoice.getTotalAmount());

        return invoiceRepository.save(newInvoice);
    }

    public Invoice updateInvoice(int id, InvoiceUpdateDto invoiceDetails) {
        Invoice invoice = invoiceRepository.findById(id).orElse(null);
        if (invoice != null) {
            invoice.setInvoiceDate(invoiceDetails.getInvoiceDate());
            invoice.setDueDate(invoiceDetails.getDueDate());
            invoice.setTotalAmount(invoiceDetails.getTotalAmount());
            return invoiceRepository.save(invoice);
        }
        return null;
    }

    public void deleteInvoice(int id) {
        invoiceRepository.deleteById(id);
    }

    public byte[] generatePdf(Invoice invoice) throws IOException {
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        PDDocument document = new PDDocument();
        PDPage page = new PDPage();
        document.addPage(page);

        try (PDPageContentStream contentStream = new PDPageContentStream(document, page)) {
            contentStream.setFont(new PDType1Font(Standard14Fonts.FontName.HELVETICA_BOLD), 16);
            contentStream.beginText();
            contentStream.newLineAtOffset(100, 750);
            contentStream.showText("Invoice");
            contentStream.setFont(new PDType1Font(Standard14Fonts.FontName.HELVETICA_BOLD), 12);
            contentStream.newLineAtOffset(0, -40);

            contentStream.showText("Invoice ID: " + invoice.getInvoiceId());
            contentStream.newLineAtOffset(0, -20);
            contentStream.showText(
                    "Client Name: " + invoice.getUser().getFirstName() + " " + invoice.getUser().getLastName());
            contentStream.newLineAtOffset(0, -20);
            contentStream.showText("Invoice Date: " + invoice.getInvoiceDate().toString());
            contentStream.newLineAtOffset(0, -20);
            contentStream.showText("Due Date: " + invoice.getDueDate().toString());
            contentStream.newLineAtOffset(0, -20);
            contentStream.showText("Total Amount: $" + invoice.getTotalAmount());

            contentStream.newLineAtOffset(0, -40);
            contentStream.showText("Item Description: Example Item");
            contentStream.newLineAtOffset(0, -20);
            contentStream.showText("Quantity: 1");
            contentStream.newLineAtOffset(0, -20);
            contentStream.showText("Unit Price: $100.00");
            contentStream.newLineAtOffset(0, -20);
            contentStream.showText("Total: $100.00");

            contentStream.endText();
        }
        document.save(outputStream);
        document.close();
        // System.out.println("PDF generated");
        return outputStream.toByteArray();
    }

    @Async
    public ResponseEntity<String> sendEmail(int invoiceId)
            throws MessagingException {
        try {
            Invoice invoice = invoiceRepository.findById(invoiceId).orElse(null);
            byte[] pdf = generatePdf(invoice);
            MimeMessage mimeMessage = javaMailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, true);
            helper.setTo(invoice.getUser().getEmail());
            helper.setSubject("Your Invoice");
            helper.setText("Dear " + invoice.getUser().getFirstName() + ",\n\nPlease find attached your invoice");
            helper.addAttachment("invoice.pdf", new ByteArrayResource(pdf));
            javaMailSender.send(mimeMessage);
        } catch (IOException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error generating PDF");
        }

        return ResponseEntity.ok("Email sent to user.");
    }

    @Async
    public ResponseEntity<String> sendBulkEmails() {
        try {
            List<Invoice> invoices = invoiceRepository.findAll();

            for (Invoice invoice : invoices) {
                User user = invoice.getUser();

                if (user != null) {
                    byte[] pdf = generatePdf(invoice);
                    MimeMessage mimeMessage = javaMailSender.createMimeMessage();
                    MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, true);
                    helper.setTo(user.getEmail());
                    helper.setSubject("Your Invoice");
                    helper.setText("Dear " + user.getFirstName()
                            + ",\n\nPlease find attached your invoice.\n\nBest regards,\nCompany Name");
                    helper.addAttachment("invoice_" + invoice.getInvoiceId() + ".pdf", new ByteArrayResource(pdf));
                    javaMailSender.send(mimeMessage);
                    System.out.println("Email sent to: " + invoice.getInvoiceId());
                } else {
                    System.out.println("User not found for invoice ID: " + invoice.getInvoiceId());
                }
            }
        } catch (IOException | MessagingException e) {
            return ResponseEntity.internalServerError().body("Error sending emails");
        }
        return ResponseEntity.ok("Emails sent successfully");
    }
}
