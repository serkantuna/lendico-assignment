package com.lendico.plangenerator.entity;

import com.fasterxml.jackson.annotation.JsonFormat;

import java.math.BigDecimal;
import java.time.ZonedDateTime;

public class LoanDetails {

    private BigDecimal loanAmount;
    private BigDecimal nominalRate;
    private Integer duration;
    @JsonFormat
    private ZonedDateTime startDate;

    public LoanDetails(BigDecimal loanAmount, BigDecimal nominalRate, Integer duration, ZonedDateTime startDate) {
        this.loanAmount = loanAmount;
        this.nominalRate = nominalRate;
        this.duration = duration;
        this.startDate = startDate;
    }

    public BigDecimal getLoanAmount() {
        return loanAmount;
    }

    public BigDecimal getNominalRate() {
        return nominalRate;
    }

    public Integer getDuration() {
        return duration;
    }

    public ZonedDateTime getStartDate() {
        return startDate;
    }
}
