package com.lendico.plangenerator.service;

import com.lendico.plangenerator.entity.LoanDetails;
import com.lendico.plangenerator.entity.Repayment;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.MathContext;
import java.math.RoundingMode;
import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
public class PlanGeneratorService {

    public List<Repayment> generatePlan(LoanDetails loanDetails) {

        BigDecimal nominalRateInPercent = loanDetails.getNominalRate().divide(new BigDecimal(100), 2, RoundingMode.HALF_UP);
        BigDecimal monthlyRate = nominalRateInPercent.divide(new BigDecimal(12), 6, RoundingMode.HALF_UP);
        BigDecimal annuity = calculateAnnuity(loanDetails.getLoanAmount(), loanDetails.getDuration(), monthlyRate).setScale(2, RoundingMode.HALF_UP);

        List<Repayment> repaymentPlan = new ArrayList<>();

        BigDecimal initialOutstandingPrincipal = loanDetails.getLoanAmount().setScale(2);
        if (initialOutstandingPrincipal.compareTo(new BigDecimal(0)) < 0) {
            throw new IllegalArgumentException("Loan amount must be greater than 0");
        }

        BigDecimal remainingOutstandingPrincipal = initialOutstandingPrincipal;
        ZonedDateTime repaymentDate = loanDetails.getStartDate();

        BigDecimal principal;
        BigDecimal interest;

        for (int i = 0; i < loanDetails.getDuration() - 1; i++) {
            interest = calculateInterest(nominalRateInPercent, initialOutstandingPrincipal);
            principal = annuity.subtract(interest);
            remainingOutstandingPrincipal = remainingOutstandingPrincipal.subtract(principal);

            repaymentPlan.add(new Repayment(principal.add(interest), repaymentDate, initialOutstandingPrincipal, interest, principal, remainingOutstandingPrincipal));

            initialOutstandingPrincipal = remainingOutstandingPrincipal;
            repaymentDate = repaymentDate.plusMonths(1);
        }

        // last repayment can be different from the rest due to rounding of previous repayments
        interest = calculateInterest(nominalRateInPercent, initialOutstandingPrincipal);
        repaymentPlan.add(new Repayment(remainingOutstandingPrincipal.add(interest), repaymentDate, initialOutstandingPrincipal, interest, remainingOutstandingPrincipal, new BigDecimal(0)));

        return repaymentPlan;
    }

    private static BigDecimal calculateInterest(BigDecimal nominalRateInPercent, BigDecimal initialOutstandingPrincipal) {
        // (Nominal-Rate * Days in Month * Initial Outstanding Principal) / days in year
        return nominalRateInPercent.multiply(new BigDecimal(30).multiply(initialOutstandingPrincipal)).divide(new BigDecimal(360), 2, RoundingMode.HALF_UP);
    }

    private static BigDecimal calculateAnnuity(BigDecimal loanAmount, Integer duration, BigDecimal monthlyRate) {
        BigDecimal dividend = monthlyRate.multiply(loanAmount); // r * (PV)
        BigDecimal divisor = new BigDecimal(1).subtract(monthlyRate.add(new BigDecimal(1)).pow(-1 * duration, MathContext.DECIMAL32)); // 1 - (1+r)^(-n)
        return dividend.divide(divisor, 2, RoundingMode.HALF_UP);
    }
}
