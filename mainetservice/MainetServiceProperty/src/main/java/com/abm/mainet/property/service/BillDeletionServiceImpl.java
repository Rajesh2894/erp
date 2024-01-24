package com.abm.mainet.property.service;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;

import javax.jws.WebMethod;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.abm.mainet.common.constant.MainetConstants;
import com.abm.mainet.common.constant.ServiceEndpoints;
import com.abm.mainet.common.exception.FrameworkException;
import com.abm.mainet.common.integration.acccount.dto.VoucherPostExternalDTO;
import com.abm.mainet.common.integration.acccount.dto.VoucherPostExternalListDTO;
import com.abm.mainet.common.integration.dto.TbBillMas;
import com.abm.mainet.common.utility.RestClient;
import com.abm.mainet.property.ui.controller.PropertyBillDeletionController;

@Service
public class BillDeletionServiceImpl implements BillDeletionService {

    @Autowired
    private IProvisionalAssesmentMstService iProvisionalAssesmentMstService;

    final static Logger logger = LoggerFactory.getLogger(PropertyBillDeletionController.class);

    @Override
    @Transactional
    public BigInteger[] validateBillDeletion(String propNo, Long orgId, List<TbBillMas> tbBillMasList) {

        long bmIdno = tbBillMasList.get(0).getBmIdno();

        return iProvisionalAssesmentMstService.validateBill(propNo, orgId, bmIdno);

    }

    @WebMethod(exclude = true)
    @Override
    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public void externalVoucherPostingInAccount(Long orgId, VoucherPostExternalDTO dto) {
        VoucherPostExternalListDTO dtos = new VoucherPostExternalListDTO();
        dtos.setVoucherextsysdto(new ArrayList<VoucherPostExternalDTO>());
        dtos.getVoucherextsysdto().add(dto);
        final ResponseEntity<?> response = RestClient.callRestTemplateClient(dtos,
                ServiceEndpoints.ACCOUNT_EXT_SYS_POSTING);
        if ((response != null) && (response.getStatusCode() == HttpStatus.OK)) {
            logger.info("Account Voucher Posting done successfully");
        } else {
            logger.error("Account Voucher Posting failed due to :"
                    + (response != null ? response.getBody() : MainetConstants.BLANK));

            throw new FrameworkException("depreciation calculation successfull. Account posting failed");
        }
    }

}
