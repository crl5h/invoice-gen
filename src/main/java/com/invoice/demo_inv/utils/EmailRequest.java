package com.invoice.demo_inv.utils;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class EmailRequest {
    private int invoiceId;
}
