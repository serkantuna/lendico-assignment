package com.lendico.plangenerator.entity;

import com.fasterxml.jackson.annotation.JsonFormat;

import java.math.BigDecimal;
import java.time.ZonedDateTime;

public class Repayment {

    @JsonFormat(shape = JsonFormat.Shape.STRING)
    private BigDecimal borrowerPaymentAmount;
    @JsonFormat
    private ZonedDateTime date;
    @JsonFormat(shape = JsonFormat.Shape.STRING)
    private BigDecimal initialOutstandingPrincipal;
    @JsonFormat(shape = JsonFormat.Shape.STRING)
    private BigDecimal interest;
    @JsonFormat(shape = JsonFormat.Shape.STRING)
    private BigDecimal principal;
    @JsonFormat(shape = JsonFormat.Shape.STRING)
    private BigDecimal remainingOutstandingPrincipal;

    public Repayment(BigDecimal borrowerPaymentAmount, ZonedDateTime date, BigDecimal initialOutstandingPrincipal, BigDecimal interest, BigDecimal principal, BigDecimal remainingOutstandingPrincipal) {
        this.borrowerPaymentAmount = borrowerPaymentAmount;
        this.date = date;
        this.initialOutstandingPrincipal = initialOutstandingPrincipal;
        this.interest = interest;
        this.principal = principal;
        this.remainingOutstandingPrincipal = remainingOutstandingPrincipal;
    }
}
