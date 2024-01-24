/**
 *
 */
package com.abm.mainet.common.integration.rest.ui.controller;

import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.annotation.HttpMethodConstraint;
import javax.servlet.annotation.ServletSecurity;
import javax.servlet.annotation.ServletSecurity.TransportGuarantee;
import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.util.Assert;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.abm.mainet.cfc.challan.domain.ChallanMaster;
import com.abm.mainet.cfc.challan.dto.ChallanReceiptPrintDTO;
import com.abm.mainet.cfc.challan.service.IChallanBankDetailsService;
import com.abm.mainet.cfc.challan.service.IChallanService;
import com.abm.mainet.common.constant.MainetConstants;
import com.abm.mainet.common.domain.Organisation;
import com.abm.mainet.common.dto.CommonChallanDTO;
import com.abm.mainet.common.exception.FrameworkException;
import com.abm.mainet.common.integration.dto.OfflineBankDto;
import com.abm.mainet.common.integration.payment.service.IPostPaymentService;
import com.abm.mainet.common.service.IFinancialYearService;

/**
 * @author Rahul.Yadav
 *
 */
@ServletSecurity(httpMethodConstraints = {
        @HttpMethodConstraint(value = "POST", transportGuarantee = TransportGuarantee.CONFIDENTIAL) })
@RestController
@RequestMapping("/ChallanNumberGenerationController")
public class ChallanNumberGenerationController {

    private static final Logger logger = LoggerFactory.getLogger(ChallanNumberGenerationController.class);

    @Resource
    private IChallanService iChallanService;

    @Resource
    private IChallanBankDetailsService iChallanBankDetailsService;

    @Resource
    private IFinancialYearService iFinancialYearService;
    
    @Autowired
    IPostPaymentService  iPostPaymentService;

    @RequestMapping(value = "/generateChallanNumber", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.ALL_VALUE)
    @ResponseStatus(value = HttpStatus.OK)
    @ResponseBody
    public Object generateChallanNumber(@RequestBody final CommonChallanDTO requestDTO,
            final HttpServletRequest request, final BindingResult bindingResult) {
        Assert.notNull(requestDTO, "Request DTO Can not be null");
        if (!bindingResult.hasErrors()) {

            try {
                final Object[] finData = iFinancialYearService.getFinacialYearByDate(new Date());
                if ((finData != null) && (finData.length > 0)) {
                    requestDTO.setFinYearEndDate((Date) finData[2]);
                    requestDTO.setFinYearStartDate((Date) finData[1]);
                    requestDTO.setFaYearId(finData[0].toString());
                }
                final ChallanMaster challanNumber = iChallanService.InvokeGenerateChallan(requestDTO);
                requestDTO.setChallanNo(challanNumber.getChallanNo());
                requestDTO.setChallanValidDate(challanNumber.getChallanValiDate());
            } catch (final Exception ex) {
                logger.error("Exception in generate Challan number" + ex);
            }
        }
        return requestDTO;

    }

    // this method is called via POS for all payment through card , cheque or cash
    @RequestMapping(value = "/insertIntoReceiptMaster", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.ALL_VALUE)
    @ResponseStatus(value = HttpStatus.OK)
    @ResponseBody
    public Object insertIntoReceiptMaster(@RequestBody final CommonChallanDTO requestDTO,
            final HttpServletRequest request, final BindingResult bindingResult) {
        Assert.notNull(requestDTO, "Request DTO Can not be null");
        if (!bindingResult.hasErrors()) {
            try {
                final Object[] finData = iFinancialYearService.getFinacialYearByDate(new Date());
                if ((finData != null) && (finData.length > 0)) {
                    requestDTO.setFaYearId(finData[0].toString());
                }

                ChallanReceiptPrintDTO dto = iChallanService.updateDataAfterPayment(requestDTO);
                if (dto != null) {
                    requestDTO.setManualReceiptNo(String.valueOf(dto.getReceiptNo()));
                    requestDTO.setManualReeiptDate(new Date());
                }

            } catch (final Exception ex) {
                logger.error("Exception in insert in to receipt master" + ex);
                throw new FrameworkException(ex.getMessage());
            }
        }
        return requestDTO;

    }

    @RequestMapping(value = "/updatePaymentFailureStatus", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.ALL_VALUE)
    public ResponseEntity<?> updatePaymentFailureStatus(@RequestBody final CommonChallanDTO requestDTO) {
        ResponseEntity<?> responseEntity = null;
        try {
            final Object[] finData = iFinancialYearService.getFinacialYearByDate(new Date());
            if ((finData != null) && (finData.length > 0)) {
                requestDTO.setFaYearId(finData[0].toString());
            }
            iPostPaymentService.postPaymentFailure(requestDTO);
            responseEntity = ResponseEntity.status(HttpStatus.OK).body(requestDTO);
        } catch (final Exception ex) {
            responseEntity = ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Exception in updating payment failure status " + ex.getMessage());
            logger.error("Exception in updating payment failure status " + ex);
        }
        return responseEntity;

    }

    @RequestMapping(value = "/getULBBanksForChallanAtBank", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.ALL_VALUE)
    @ResponseStatus(value = HttpStatus.OK)
    @ResponseBody
    public Object getUlbBanks(@RequestBody final CommonChallanDTO requestDTO, final HttpServletRequest request,
            final BindingResult bindingResult) {
        Assert.notNull(requestDTO, "Request DTO Can not be null");
        List<String> data = null;
        if (!bindingResult.hasErrors()) {

            try {
                data = iChallanService.getBankDetailsList(requestDTO.getBankaAccId(), requestDTO.getOrgId());
                requestDTO.setBankData(data);
            } catch (final Exception ex) {
                logger.error("Exception in getULBBanksForChallanAtBank" + ex);
            }
        }
        return requestDTO;
    }

    @RequestMapping(value = "/getUlbBanksList", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.ALL_VALUE)
    @ResponseStatus(value = HttpStatus.OK)
    @ResponseBody
    public Object getUlbBanks(@RequestBody final OfflineBankDto requestDTO, final HttpServletRequest request,
            final BindingResult bindingResult) {
        Assert.notNull(requestDTO, "Request DTO Can not be null");
        if (!bindingResult.hasErrors()) {
            final Organisation org = new Organisation();
            org.setOrgid(requestDTO.getOrgId());
            try {
                final Map<Long, String> data = iChallanBankDetailsService.getBankList(org);
                requestDTO.setBankData(data);
                requestDTO.setStatus(MainetConstants.FlagS);
            } catch (final Exception ex) {
                requestDTO.setStatus(MainetConstants.RnLCommon.F_FLAG);
                logger.error("Exception in getUlbBanksList" + ex);
            }
        }
        return requestDTO;
    }
}
