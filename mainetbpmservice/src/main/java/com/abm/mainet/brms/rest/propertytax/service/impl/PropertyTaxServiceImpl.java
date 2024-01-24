package com.abm.mainet.brms.rest.propertytax.service.impl;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.abm.mainet.brms.rest.propertytax.service.PropertyTaxService;
import com.abm.mainet.rule.propertytax.datamodel.ALVMasterModel;
import com.abm.mainet.rule.propertytax.datamodel.FactorMasterModel;
import com.abm.mainet.rule.propertytax.datamodel.PropertyRateMasterModel;
import com.abm.mainet.rule.propertytax.datamodel.PropertyTaxDataModel;

/**
 * 
 * @author Vivek.Kumar
 * @since 27/04/2017
 */
@Service
public class PropertyTaxServiceImpl implements PropertyTaxService {

    @Override
    public ResponseEntity<?> validateALVModel(ALVMasterModel alvModel) {

        StringBuilder builder = new StringBuilder();
        if (alvModel.getOrgId() == 0.0d) {
            builder.append("orgId, ");
        }
        if (alvModel.getConstructionClass() == null || alvModel.getConstructionClass().isEmpty()) {
            builder.append("constructionClass, ");
        }
        if (alvModel.getUsageSubtype1() == null || alvModel.getUsageSubtype1().isEmpty()) {
            builder.append("usageType, ");
        }
        if (alvModel.getRateStartDate() == 0) {
            builder.append("rateStartDate ");
        }

        return builder.toString().isEmpty()
                ? ResponseEntity.status(HttpStatus.ACCEPTED).body("data validated")
                : ResponseEntity.status(HttpStatus.BAD_REQUEST)
                        .body("ALVMasterModel fields [" + builder.append("] cannot be null or empty or zero").toString());

    }

    @Override
    public ResponseEntity<?> validateFactorModel(FactorMasterModel factorModel) {

        StringBuilder builder = new StringBuilder();
        if (factorModel.getOrgId() == 0.0d) {
            builder.append("orgId, ");
        }
        if (factorModel.getFactor() == null || factorModel.getFactor().isEmpty()) {
            builder.append("factor, ");
        }
        if (factorModel.getFactorValue() == null || factorModel.getFactorValue().isEmpty()) {
            builder.append("factorValue, ");
        }
        if (factorModel.getRateStartDate() == 0) {
            builder.append("rateStartDate ");
        }

        return builder.toString().isEmpty()
                ? ResponseEntity.status(HttpStatus.ACCEPTED).body("data validated")
                : ResponseEntity.status(HttpStatus.BAD_REQUEST)
                        .body("FactorMasterModel fields [" + builder.append("] cannot be null or empty or zero").toString());

    }

    @Override
    public ResponseEntity<?> validateRateModel(PropertyRateMasterModel rateModel) {

        StringBuilder builder = new StringBuilder();
        if (rateModel.getOrgId() == 0.0d) {
            builder.append("orgId, ");
        }
        if (rateModel.getRateStartDate() == 0) {
            builder.append("rateStartDate ");
        }
        return builder.toString().isEmpty()
                ? ResponseEntity.status(HttpStatus.ACCEPTED).body("data validated")
                : ResponseEntity.status(HttpStatus.BAD_REQUEST).body(
                        "PropertyRateMasterModel fields [" + builder.append("] cannot be null or empty or zero").toString());
    }

    @Override
    public ResponseEntity<?> validateSDDRModel(PropertyTaxDataModel dataModel) {
        StringBuilder builder = new StringBuilder();
        if (dataModel.getOrgId() == 0.0d) {
            builder.append("orgId, ");
        }
        if (dataModel.getRateStartDate() == 0) {
            builder.append("rateStartDate ");
        }
        return builder.toString().isEmpty()
                ? ResponseEntity.status(HttpStatus.ACCEPTED).body("data validated")
                : ResponseEntity.status(HttpStatus.BAD_REQUEST).body(
                        "PropertyTaxDataModel fields [" + builder.append("] cannot be null or empty or zero").toString());
    }

}
