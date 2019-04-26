package com.lendico.plangenerator.service;

import com.lendico.plangenerator.entity.LoanDetails;
import com.lendico.plangenerator.entity.Repayment;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.math.BigDecimal;
import java.time.ZonedDateTime;
import java.util.List;

import static org.junit.Assert.*;

@RunWith(SpringJUnit4ClassRunner.class)
public class PlanGeneratorServiceTest {

    private PlanGeneratorService planGeneratorService = new PlanGeneratorService();

    @Test(expected = IllegalArgumentException.class)
    public void testGeneratePlan_LoanLessThan0() {
        planGeneratorService.generatePlan(new LoanDetails(new BigDecimal("-50"), new BigDecimal("5.0"), 24, ZonedDateTime.parse("2018-01-01T00:00:01Z")));
    }

    @Test
    public void testGeneratePlan_TotalPayment() {
        List<Repayment> repaymentPlan = planGeneratorService.generatePlan(new LoanDetails(new BigDecimal("5000"), new BigDecimal("5.0"), 24, ZonedDateTime.parse("2018-01-01T00:00:01Z")));

        BigDecimal totalPayment = new BigDecimal(0);
        for (Repayment repayment : repaymentPlan) {
            totalPayment = repayment.getBorrowerPaymentAmount().add(totalPayment);
        }

        assertTrue(totalPayment.compareTo(new BigDecimal("5264.56")) == 0);
    }

    @Test
    public void testGeneratePlan_TotalPrincipalEqualsToLoanAmount() {
        List<Repayment> repaymentPlan = planGeneratorService.generatePlan(new LoanDetails(new BigDecimal("5000"), new BigDecimal("5.0"), 24, ZonedDateTime.parse("2018-01-01T00:00:01Z")));

        BigDecimal totalPrincipal = new BigDecimal(0);
        for (Repayment repayment : repaymentPlan) {
            totalPrincipal = repayment.getPrincipal().add(totalPrincipal);
        }

        assertTrue(totalPrincipal.compareTo(new BigDecimal("5000")) == 0);
    }

    @Test
    public void testGeneratePlan_TotalInterest() {
        List<Repayment> repaymentPlan = planGeneratorService.generatePlan(new LoanDetails(new BigDecimal("5000"), new BigDecimal("5.0"), 24, ZonedDateTime.parse("2018-01-01T00:00:01Z")));

        BigDecimal totalInterest = new BigDecimal(0);
        for (Repayment repayment : repaymentPlan) {
            totalInterest = repayment.getInterest().add(totalInterest);
        }

        assertTrue(totalInterest.compareTo(new BigDecimal("264.56")) == 0);
    }

    @Test
    public void testGeneratePlan_LastRemainingOutstandingPrincipal0() {
        List<Repayment> repaymentPlan = planGeneratorService.generatePlan(new LoanDetails(new BigDecimal("5000"), new BigDecimal("5.0"), 24, ZonedDateTime.parse("2018-01-01T00:00:01Z")));

        Repayment lastRepayment = repaymentPlan.get(23);

        assertTrue(lastRepayment.getRemainingOutstandingPrincipal().compareTo(new BigDecimal("0")) == 0);
    }

    @Test
    public void testGeneratePlan_CheckNumberOfRepayments() {
        List<Repayment> repaymentPlan = planGeneratorService.generatePlan(new LoanDetails(new BigDecimal("5000"), new BigDecimal("5.0"), 24, ZonedDateTime.parse("2018-01-01T00:00:01Z")));

        assertTrue(repaymentPlan.size() == 24);
    }
}