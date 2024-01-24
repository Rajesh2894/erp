package com.abm.mainet.adh.rest.ui.controller;

import java.util.List;

import javax.servlet.annotation.HttpMethodConstraint;
import javax.servlet.annotation.ServletSecurity;
import javax.servlet.annotation.ServletSecurity.TransportGuarantee;

import org.apache.commons.collections.CollectionUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.abm.mainet.adh.domain.ADHBillMasEntity;
import com.abm.mainet.adh.service.IADHBillMasService;
import com.abm.mainet.adh.service.IAdvertisementContractMappingService;
import com.abm.mainet.common.constant.MainetConstants;
import com.abm.mainet.common.master.dto.ContractAgreementSummaryDTO;
import com.abm.mainet.common.master.service.IContractAgreementService;
import com.abm.mainet.common.utility.ApplicationContextProvider;
import com.abm.mainet.common.utility.ApplicationSession;

@ServletSecurity(httpMethodConstraints = {
        @HttpMethodConstraint(value = "POST", transportGuarantee = TransportGuarantee.CONFIDENTIAL) })
@RestController
@RequestMapping("/adhBillPaymentRestController")
public class ADHContractBillPaymentRestController {
    private static final Logger LOGGER = Logger.getLogger(ADHContractBillPaymentRestController.class);
    @Autowired
    private IContractAgreementService iContractAgreementService;
    @Autowired
    private IADHBillMasService ADHBillMasService;

    @RequestMapping(value = "/getPaymentData", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.ALL_VALUE)
    public ContractAgreementSummaryDTO getPaymentData(@RequestBody ContractAgreementSummaryDTO summaryDTO) {
        LOGGER.info("Begin -->  " + this.getClass().getSimpleName() + " getPaymentData method");
        ContractAgreementSummaryDTO contractAgreementSummaryDTO = iContractAgreementService
                .findByContractNo(summaryDTO.getOrgId(), summaryDTO.getContNo());
        if (contractAgreementSummaryDTO != null) {
            Long contractExist = ApplicationContextProvider.getApplicationContext()
                    .getBean(IAdvertisementContractMappingService.class)
                    .findContractByContIdAndOrgId(contractAgreementSummaryDTO.getContId(), summaryDTO.getOrgId());
            if (contractExist != null && contractExist > 0) {
                List<ADHBillMasEntity> adhBillMasList = ADHBillMasService.finByContractId(
                        contractAgreementSummaryDTO.getContId(), summaryDTO.getOrgId(),
                        MainetConstants.FlagN, MainetConstants.FlagB);

                if (CollectionUtils.isNotEmpty(adhBillMasList)) {
                    contractAgreementSummaryDTO = ADHBillMasService.getReceiptAmountDetailsForBillPayment(
                            contractAgreementSummaryDTO.getContId(),
                            contractAgreementSummaryDTO, summaryDTO.getOrgId(), adhBillMasList);
                    contractAgreementSummaryDTO.setOrgId(summaryDTO.getOrgId());
                    contractAgreementSummaryDTO.setStatus(MainetConstants.WebServiceStatus.SUCCESS);

                } else {
                    contractAgreementSummaryDTO.setStatus(MainetConstants.WebServiceStatus.FAIL);
                    contractAgreementSummaryDTO
                            .setErrorMsg(ApplicationSession.getInstance().getMessage("adh.validate.no.dues.exist") + " "
                                    + summaryDTO.getContNo());
                }
            } else {
                contractAgreementSummaryDTO.setStatus(MainetConstants.WebServiceStatus.FAIL);
                contractAgreementSummaryDTO.setErrorMsg(ApplicationSession.getInstance()
                        .getMessage("Contract No: " + summaryDTO.getContNo() + " is not mapped with any of the hoarding"));
            }
        } else {
            contractAgreementSummaryDTO = new ContractAgreementSummaryDTO();
            contractAgreementSummaryDTO.setStatus(MainetConstants.WebServiceStatus.FAIL);
            contractAgreementSummaryDTO.setErrorMsg(
                    ApplicationSession.getInstance().getMessage("adh.validate.no.record.found") + " " + summaryDTO.getContNo());
        }
        LOGGER.info("End -->  " + this.getClass().getSimpleName() + " getPaymentData method");
        return contractAgreementSummaryDTO;

    }
}
