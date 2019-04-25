package com.lendico.plangenerator.controller;

import com.lendico.plangenerator.entity.LoanDetails;
import com.lendico.plangenerator.entity.Repayment;
import com.lendico.plangenerator.service.PlanGeneratorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class PlanGeneratorController {

    @Autowired
    private PlanGeneratorService planGeneratorService;

    @PostMapping(path = "/generate-plan")
    @ResponseBody
    public ResponseEntity<List<Repayment>> generatePlan(@RequestBody LoanDetails loanDetails) {

        try {
            List<Repayment> repaymentPlan = planGeneratorService.generatePlan(loanDetails);
            if (repaymentPlan == null || repaymentPlan.isEmpty()) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
            }

            return ResponseEntity.ok(repaymentPlan);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }
}
