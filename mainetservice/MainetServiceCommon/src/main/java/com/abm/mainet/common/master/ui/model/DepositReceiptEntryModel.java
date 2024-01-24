package com.abm.mainet.common.master.ui.model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.abm.mainet.common.constant.MainetConstants;
import com.abm.mainet.common.dto.CommonChallanDTO;
import com.abm.mainet.common.integration.acccount.dto.TbServiceReceiptMasBean;
import com.abm.mainet.common.integration.acccount.dto.TbSrcptFeesDetBean;
import com.abm.mainet.common.integration.acccount.dto.TbSrcptModesDetBean;
import com.abm.mainet.common.master.dto.TbServicesMst;
import com.abm.mainet.common.master.service.IDepositReceiptService;
import com.abm.mainet.common.service.IReceiptEntryService;
import com.abm.mainet.common.ui.model.AbstractFormModel;
import com.abm.mainet.common.utility.CommonMasterUtility;
import com.abm.mainet.common.utility.UserSession;
import com.abm.mainet.common.utility.Utility;

/**
 * @author Saiprasad.Vengurlekar
 * @modified By Vishwajeet.kumar
 */

@Component
@Scope("session")
public class DepositReceiptEntryModel extends AbstractFormModel {

    private static final long serialVersionUID = 492644288141757589L;

    @Autowired
    private IDepositReceiptService depositReceiptService;
    
    @Autowired
	IReceiptEntryService receiptEntryService;

    private List<TbServiceReceiptMasBean> receiptBeanList = new ArrayList<>();
    private List<TbServiceReceiptMasBean> receiptMasBeanList = new ArrayList<>();
    private List<TbServicesMst> serviceMasList = Collections.emptyList();
    private String saveMode;
    private String envFlag;

    public TbServiceReceiptMasBean prepareAndSaveReceiptEntity(TbServiceReceiptMasBean receiptBean) {
        Long empId = UserSession.getCurrent().getEmployee().getEmpId();
        CommonChallanDTO requestDto = new CommonChallanDTO();
        Map<Long, Double> detValues = new HashMap<>(0);
        requestDto.setOrgId(UserSession.getCurrent().getOrganisation().getOrgid());
        requestDto.setDeptId(receiptBean.getDpDeptId());
        requestDto.setLoggedLocId(UserSession.getCurrent().getLoggedLocId());
        requestDto.setAmountToPay(receiptBean.getRmAmount());
        requestDto.setApplicantName(receiptBean.getRmReceivedfrom());
        requestDto.setNarration(receiptBean.getRmNarration());
        requestDto.setUniquePrimaryId(receiptBean.getAdditionalRefNo());
        requestDto.setReceiptcategoryId(receiptBean.getRecCategoryTypeId());
        requestDto.setUserId(empId);
        requestDto.setManualReceiptNo(receiptBean.getManualReceiptNo());
        requestDto.setVendorId(receiptBean.getVmVendorId());
        requestDto.setManualReeiptDate(Utility.stringToDate(receiptBean.getTransactionDate()));
        requestDto.setPaymentCategory(CommonMasterUtility.findLookUpCode(MainetConstants.BILL_MASTER_COMMON.PREFIX_CODE_RV,
                UserSession.getCurrent().getOrganisation().getOrgid(), receiptBean.getRecCategoryTypeId()));
        requestDto.setChallanServiceType(MainetConstants.FlagN);
        requestDto.setEmailId(receiptBean.getEmailId());
        requestDto.setMobileNumber(receiptBean.getMobileNumber());
        TbSrcptModesDetBean receiptModeDet = receiptBean.getReceiptModeDetailList();
        requestDto.setPayModeIn(receiptModeDet.getCpdFeemode());
        requestDto.setCbBankId(receiptModeDet.getCbBankid());
        requestDto.setBmChqDDNo(receiptModeDet.getRdChequeddno());
        requestDto.setBmChqDDDate(Utility.stringToDate(receiptModeDet.getRdChequedddatetemp()));
        requestDto.setBmDrawOn(receiptModeDet.getRdDrawnon());
        requestDto.setBankaAccId(receiptModeDet.getBaAccountid());
        requestDto.setLgIpMac(UserSession.getCurrent().getEmployee().getEmppiservername());
        requestDto.setGstNo(receiptBean.getGstNo());
       // #149536
        if (receiptModeDet.getAccountNumber() != null)
        requestDto.setBmBankAccountId(receiptModeDet.getAccountNumber());
        if (receiptBean.getReceiptFeeDetail() != null) {
            for (TbSrcptFeesDetBean feesDetBean : receiptBean.getReceiptFeeDetail()) {
                detValues.put(feesDetBean.getSacHeadId(), feesDetBean.getRfFeeamount().doubleValue());
            }
            requestDto.setFeeIds(detValues);
        }
        if(receiptBean.getRmAddress()!=null)
        requestDto.setApplicantAddress(receiptBean.getRmAddress());
        if(receiptBean.getSmServiceId()!=null)
        requestDto.setServiceId(receiptBean.getSmServiceId());
        TbServiceReceiptMasBean tbServiceReceiptMasBean = depositReceiptService.saveReceiptEntry(requestDto);
        if (Utility.isEnvPrefixAvailable(UserSession.getCurrent().getOrganisation(), MainetConstants.ENV_SKDCL)) {
         if (tbServiceReceiptMasBean.getRmRcptno() != null && tbServiceReceiptMasBean.getDpDeptId() != null)
           tbServiceReceiptMasBean.setRmReceiptNo(receiptEntryService.getCustomReceiptNo(tbServiceReceiptMasBean.getDpDeptId(),Long.valueOf(tbServiceReceiptMasBean.getRmRcptno())));
        }
        else if (Utility.isEnvPrefixAvailable(UserSession.getCurrent().getOrganisation(), MainetConstants.ENV_TSCL)) {
        	tbServiceReceiptMasBean.setRmReceiptNo(receiptEntryService.getTSCLCustomReceiptNo(tbServiceReceiptMasBean.getFieldId(),tbServiceReceiptMasBean.getSmServiceId(),Long.valueOf(tbServiceReceiptMasBean.getRmRcptno()),tbServiceReceiptMasBean.getRmDate(),tbServiceReceiptMasBean.getOrgId()));
        }else{
        	if (tbServiceReceiptMasBean.getRmRcptno() != null) 
        	tbServiceReceiptMasBean.setRmReceiptNo(tbServiceReceiptMasBean.getRmRcptno().toString());
       }
        return tbServiceReceiptMasBean;
    }

    public List<TbServiceReceiptMasBean> getReceiptBeanList() {
        return receiptBeanList;
    }

    public void setReceiptBeanList(List<TbServiceReceiptMasBean> receiptBeanList) {
        this.receiptBeanList = receiptBeanList;
    }

    public String getSaveMode() {
        return saveMode;
    }

    public void setSaveMode(String saveMode) {
        this.saveMode = saveMode;
    }

    public List<TbServiceReceiptMasBean> getReceiptMasBeanList() {
        return receiptMasBeanList;
    }

    public void setReceiptMasBeanList(List<TbServiceReceiptMasBean> receiptMasBeanList) {
        this.receiptMasBeanList = receiptMasBeanList;
    }

	public String getEnvFlag() {
		return envFlag;
	}

	public void setEnvFlag(String envFlag) {
		this.envFlag = envFlag;
	}

	public List<TbServicesMst> getServiceMasList() {
		return serviceMasList;
	}

	public void setServiceMasList(List<TbServicesMst> serviceMasList) {
		this.serviceMasList = serviceMasList;
	}
}
