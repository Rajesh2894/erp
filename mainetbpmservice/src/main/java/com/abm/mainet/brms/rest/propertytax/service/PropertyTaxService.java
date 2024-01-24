package com.abm.mainet.brms.rest.propertytax.service;

import org.springframework.http.ResponseEntity;

import com.abm.mainet.rule.propertytax.datamodel.ALVMasterModel;
import com.abm.mainet.rule.propertytax.datamodel.FactorMasterModel;
import com.abm.mainet.rule.propertytax.datamodel.PropertyRateMasterModel;
import com.abm.mainet.rule.propertytax.datamodel.PropertyTaxDataModel;

/**
 * 
 * @author Vivek.Kumar
 * @since 27/04/2017
 */
public interface PropertyTaxService {

    public ResponseEntity<?> validateALVModel(ALVMasterModel alvModel);

    public ResponseEntity<?> validateFactorModel(FactorMasterModel factorModel);

    public ResponseEntity<?> validateRateModel(PropertyRateMasterModel rateModel);

    public ResponseEntity<?> validateSDDRModel(PropertyTaxDataModel dataModel);
}
