package com.abm.mainet.brms.rest.account.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.abm.mainet.brms.rest.account.service.TDSCalculationService;
import com.abm.mainet.brms.rest.service.RuleEngineService;
import com.abm.mainet.rule.account.datamodel.TDSCalculation;

/**
 * 
 * @author Vivek.Kumar
 * @since 14/04/2017
 */
@Service
public class TDSCalculationServiceImpl implements TDSCalculationService {

    @Autowired
    private RuleEngineService ruleEngineService;

    @Override
    public ResponseEntity<?> validate(TDSCalculation tds) {

        StringBuilder builder = new StringBuilder();
        if (tds.getBillAmount() == 0.0d) {
            builder.append("billAmount, ");
        }
        if (tds.getBillAmountType() == null || tds.getBillAmountType().isEmpty()) {
            builder.append("billAmountType, ");
        }
        if (tds.getDepartmentCode() == null || tds.getDepartmentCode().isEmpty()) {
            builder.append("departmentCode, ");
        }
        if (tds.getPanCardHolder() == null || tds.getPanCardHolder().isEmpty()) {
            builder.append("panCardHolder, ");
        }
        if (tds.getTaxCode() == null || tds.getTaxCode().isEmpty()) {
            builder.append("taxCode, ");
        }
        if (tds.getVendorSubType() == null || tds.getVendorSubType().isEmpty()) {
            builder.append("vendorSubType, ");
        }
        if (tds.getVendorType() == null || tds.getVendorType().isEmpty()) {
            builder.append("vendorType, ");
        }

        return builder.toString().isEmpty()
                ? ResponseEntity.status(HttpStatus.ACCEPTED).body("data validated")
                : ResponseEntity.status(HttpStatus.BAD_REQUEST)
                        .body("TDSCalculation fields [" + builder.append("] cannot be null or empty or zero").toString());
    }

    @Override
    public ResponseEntity<?> calculateTdsRate(TDSCalculation factModel) {

        TDSCalculation result = ruleEngineService.calculateTdsRate(factModel);

        return ResponseEntity.status(HttpStatus.OK).body(result);
    }

}
