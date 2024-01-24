package com.abm.mainet.common.rest.ui.controller;

import javax.servlet.annotation.HttpMethodConstraint;
import javax.servlet.annotation.ServletSecurity;
import javax.servlet.annotation.ServletSecurity.TransportGuarantee;
import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.util.Assert;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.abm.mainet.cfc.loi.dto.LoiPaymentSearchDTO;
import com.abm.mainet.cfc.loi.dto.TbLoiMas;
import com.abm.mainet.cfc.loi.service.TbLoiDetService;
import com.abm.mainet.cfc.loi.service.TbLoiMasService;
import com.abm.mainet.common.constant.MainetConstants;
import com.abm.mainet.common.domain.CFCApplicationAddressEntity;
import com.abm.mainet.common.domain.TbCfcApplicationMstEntity;
import com.abm.mainet.common.master.dto.TbServicesMst;
import com.abm.mainet.common.master.service.TbServicesMstService;
import com.abm.mainet.common.service.ICFCApplicationAddressService;
import com.abm.mainet.common.service.ICFCApplicationMasterService;

/**
 * @author Rahul.Yadav
 *
 */
@ServletSecurity(httpMethodConstraints = {
        @HttpMethodConstraint(value = "POST", transportGuarantee = TransportGuarantee.CONFIDENTIAL) })
@RestController
@RequestMapping("/LoiPaymentController")
public class LoiPaymentRestController {

    @Autowired
    private TbLoiMasService itbLoiMasService;

    @Autowired
    private TbLoiDetService itbLoiDetService;

    @Autowired
    private ICFCApplicationMasterService iCFCApplicationMasterService;

    @Autowired
    private TbServicesMstService iTbServicesMstService;

    @Autowired
    private ICFCApplicationAddressService iCFCApplicationAddressService;

    @RequestMapping(value = "/LoiNumberSearch", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.ALL_VALUE)
    @ResponseStatus(value = HttpStatus.OK)
    @ResponseBody
    public Object getLoiData(
            @RequestBody final LoiPaymentSearchDTO requestDTO,
            final HttpServletRequest request, final BindingResult bindingResult) {
        Assert.notNull(requestDTO, "Request DTO Can not be null");
        if (!bindingResult.hasErrors()) {
            LoiPaymentSearchDTO dto = null;
            try {
                final TbLoiMas master = itbLoiMasService.findLoiMasBySearchCriteria(requestDTO,MainetConstants.PAY_STATUS.NOT_PAID);

                if (master != null) {
                	//Defect #135030 change request dto orgid to master orgid
                    dto = itbLoiDetService.findLoiDetailsByLoiMasAndOrgId(master, master.getOrgid());
                    final TbCfcApplicationMstEntity applicationMaster = iCFCApplicationMasterService
                            .getCFCApplicationByApplicationId(master.getLoiApplicationId(), requestDTO.getOrgId());
                    String userName = (applicationMaster.getApmFname() == null ? "" : applicationMaster.getApmFname() + " ");
                    userName += applicationMaster.getApmMname() == null ? "" : applicationMaster.getApmMname() + " ";
                    userName += applicationMaster.getApmLname() == null ? "" : applicationMaster.getApmLname();
                    requestDTO.setApplicantName(userName);

                    final TbServicesMst serviceMst = iTbServicesMstService.findById(master.getLoiServiceId());
                    if (serviceMst != null) {
                        requestDTO.setServiceName(serviceMst.getSmServiceName());
                    }
                    final CFCApplicationAddressEntity address = iCFCApplicationAddressService
                            .getApplicationAddressByAppId(master.getLoiApplicationId(), requestDTO.getOrgId());
                    if (address != null) {
                        requestDTO.setEmail(address.getApaEmail());
                        requestDTO.setMobileNo(address.getApaMobilno());
                        requestDTO.setAddress(address.getApaAreanm());
                    }
                }
                requestDTO.setLoiMasData(master);
                if (dto != null) {
                    requestDTO.setChargeDesc(dto.getChargeDesc());
                    requestDTO.setTotal(dto.getTotal());
                    //125445
                    requestDTO.setLoiCharges(dto.getLoiCharges());
                }
            } catch (final Exception ex) {
                ex.printStackTrace();
            }
        }
        return requestDTO;

    }

    @RequestMapping(value = "/loiPaymentSave", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.ALL_VALUE)
    @ResponseStatus(value = HttpStatus.OK)
    @ResponseBody
    public Object loiPaymentSave(
            @RequestBody final TbLoiMas requestDTO,
            final HttpServletRequest request, final BindingResult bindingResult) {
        Assert.notNull(requestDTO, "Request DTO Can not be null");
        if (!bindingResult.hasErrors()) {
            try {
                itbLoiMasService.updateLoiMaster(requestDTO);
            } catch (final Exception ex) {
                ex.printStackTrace();
            }
        }
        return requestDTO;
    }

}
