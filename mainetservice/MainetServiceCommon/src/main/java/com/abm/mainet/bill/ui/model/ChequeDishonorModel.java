package com.abm.mainet.bill.ui.model;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.abm.mainet.bill.service.ChequeDishonorService;
import com.abm.mainet.common.constant.MainetConstants;
import com.abm.mainet.common.constant.PrefixConstants;
import com.abm.mainet.common.domain.Organisation;
import com.abm.mainet.common.integration.acccount.domain.TbSrcptModesDetEntity;
import com.abm.mainet.common.integration.acccount.dto.TbServiceReceiptMasBean;
import com.abm.mainet.common.integration.acccount.dto.TbSrcptFeesDetBean;
import com.abm.mainet.common.integration.acccount.dto.TbSrcptModesDetBean;
import com.abm.mainet.common.master.dto.TbDepartment;
import com.abm.mainet.common.master.dto.TbTaxMas;
import com.abm.mainet.common.master.service.TbTaxMasService;
import com.abm.mainet.common.service.IOrganisationService;
import com.abm.mainet.common.ui.controller.ReceivableDemandEntryController;
import com.abm.mainet.common.ui.model.AbstractFormModel;
import com.abm.mainet.common.utility.ApplicationSession;
import com.abm.mainet.common.utility.CommonMasterUtility;
import com.abm.mainet.common.utility.LookUp;
import com.abm.mainet.common.utility.UserSession;
import com.abm.mainet.common.utility.Utility;

/**
 * @author Rahul.Yadav
 *
 */
@Component
@Scope("session")
public class ChequeDishonorModel extends AbstractFormModel {

    private static final long serialVersionUID = 9216878570227670869L;
    
    private static final Logger logger = Logger.getLogger(ChequeDishonorModel.class);

    @Autowired
    private ChequeDishonorService chequeDishonorService;
    
    @Autowired
	private IOrganisationService iOrganisationService;
    
    @Autowired
   	private TbTaxMasService tbTaxMasService;


    private List<TbDepartment> department = null;

    private List<TbServiceReceiptMasBean> feeDetail = null;

    private Long deptId;

    private Date fromDate;

    private Date toDate;

    private Long receiptNo;

    private Long chequeNo;
    
    private double dishonorCharge;

    private Long bankId;

    private String accountActive;

    public String getAccountActive() {
        return accountActive;
    }

    public void setAccountActive(final String accountActive) {
        this.accountActive = accountActive;
    }

    private Map<Long, String> banks = new HashMap<>(0);

    public Map<Long, String> getBanks() {
        return banks;
    }

    public void setBanks(final Map<Long, String> banks) {
        this.banks = banks;
    }

    public List<TbDepartment> getDepartment() {
        return department;
    }

    public void setDepartment(final List<TbDepartment> department) {
        this.department = department;
    }

    public List<TbServiceReceiptMasBean> getFeeDetail() {
        return feeDetail;
    }

    public void setFeeDetail(final List<TbServiceReceiptMasBean> feeDetail) {
        this.feeDetail = feeDetail;
    }

    public Long getDeptId() {
        return deptId;
    }

    public void setDeptId(final Long deptId) {
        this.deptId = deptId;
    }

    public Date getFromDate() {
        return fromDate;
    }

    public void setFromDate(final Date fromDate) {
        this.fromDate = fromDate;
    }

    public Date getToDate() {
        return toDate;
    }

    public void setToDate(final Date toDate) {
        this.toDate = toDate;
    }

    public Long getReceiptNo() {
        return receiptNo;
    }

    public void setReceiptNo(final Long receiptNo) {
        this.receiptNo = receiptNo;
    }

    public Long getChequeNo() {
        return chequeNo;
    }

    public void setChequeNo(final Long chequeNo) {
        this.chequeNo = chequeNo;
    }

    public String constantNo = MainetConstants.Common_Constant.NO;
    public String constantYes = MainetConstants.Common_Constant.YES;

    /**
     * @param model
     * @return
     */
    public boolean validateSearchData(final ChequeDishonorModel model) {
        boolean result = true;
        if ((model.getDeptId() == null) | (model.getDeptId() <= 0)) {
            addValidationError(ApplicationSession.getInstance().getMessage("adjustment.department.select"));
        }
        if ((model.getFromDate() != null) || (model.getToDate() != null)) {
            if ((model.getFromDate() == null) || (model.getToDate() == null)) {
                addValidationError(ApplicationSession.getInstance().getMessage("cheque.date.select"));
            }
        }
        if (hasValidationErrors()) {
            result = false;
        }
        return result;
    }

    /*
     * (non-Javadoc)
     * @see com.abm.mainetservice.web.core.ui.model.AbstractFormModel#saveForm()
     */
    @Override
    public boolean saveForm() {
        TbSrcptModesDetEntity feeDet = null;
        int count = 0;
        for (final TbServiceReceiptMasBean feedetailDto : getFeeDetail()) {
			for (final TbSrcptModesDetBean modeDetails : feedetailDto.getReceiptModeList()) {
				if (constantYes.equals(modeDetails.getRdSrChkDis())) {
					if (modeDetails.getRdSrChkDate() == null || StringUtils.isEmpty(modeDetails.getRd_dishonor_remark())) {
						addValidationError(ApplicationSession.getInstance().getMessage("cheque.dishonor.date.charge.rod.enter")
								+ modeDetails.getRdChequeddno());						
					}
					if(!Utility.isEnvPrefixAvailable(UserSession.getCurrent().getOrganisation(), MainetConstants.ENV_PSCL) && modeDetails.getRdSrChkDisChg() == null) {
						addValidationError(ApplicationSession.getInstance().getMessage("cheque.dishonor.charge.not.found")
								+ modeDetails.getRdChequeddno());
					}
					++count;
				} else if (constantNo.equals(modeDetails.getRdSrChkDis())) {
					if (modeDetails.getRdSrChkDate() == null) {
						addValidationError(ApplicationSession.getInstance().getMessage("cheque.doc.enter")
								+ modeDetails.getRdChequeddno());
					}
					++count;
				}
				modeDetails.setAddRefNo(feedetailDto.getAdditionalRefNo());
			}
			
		}
        if(hasValidationErrors()) { //Defect #123568
        	return false;
        }
        if (count <= 0) {
            addValidationError(ApplicationSession.getInstance().getMessage("cheque.dishonour.select.one"));
            return false;
        } else {
        	for (final TbServiceReceiptMasBean feedetailDto : getFeeDetail()) {
			for (final TbSrcptModesDetBean modeDetails : feedetailDto.getReceiptModeList()) {
				if (constantYes.equals(modeDetails.getRdSrChkDis())) {
					if ((feedetailDto.getRmLoiNo() != null) && !feedetailDto.getRmLoiNo().isEmpty()) {
						chequeDishonorService.updateLoiNotPaid(feedetailDto.getRmLoiNo(), modeDetails.getOrgid());
					}
					List<TbSrcptFeesDetBean> recDet = feedetailDto.getReceiptFeeDetail();
					if(Utility.isEnvPrefixAvailable(UserSession.getCurrent().getOrganisation(), MainetConstants.ENV_PSCL)) {
					recDet.forEach(det->{
						TbTaxMas taxMas = tbTaxMasService.findById(det.getTaxId(), UserSession.getCurrent().getOrganisation().getOrgid());
						 if(PrefixConstants.NewWaterServiceConstants.SURCHARGE.equals(taxMas.getTaxDesc()))  {
							 modeDetails.getPenaltyWiseBillNoAndAmountMap().put(det.getBmIdNo(), det.getRfFeeamount().doubleValue());
						 }else if(!PrefixConstants.NewWaterServiceConstants.ADVANCE_PAYMENT.equals(taxMas.getTaxDesc())) {
							 if(modeDetails.getBillNoAndAmountMap().get(det.getBmIdNo()) != null) {
								 Double existingVal = modeDetails.getBillNoAndAmountMap().get(det.getBmIdNo());
								 modeDetails.getBillNoAndAmountMap().put(det.getBmIdNo(), 
										 (existingVal + det.getRfFeeamount().doubleValue()));
							 }else {
								 modeDetails.getBillNoAndAmountMap().put(det.getBmIdNo(), det.getRfFeeamount().doubleValue());
							 }
						 }					 
					});
						
					}
					
					chequeDishonorService.revertBills(modeDetails,
							UserSession.getCurrent().getEmployee().getEmpId(),
							UserSession.getCurrent().getEmployee().getEmppiservername(),feedetailDto.getDpDeptId());
				} else if (constantNo.equals(modeDetails.getRdSrChkDis())) {					
					final List<TbSrcptModesDetEntity> feeDetList = chequeDishonorService
							.fetchReceiptFeeDetails(modeDetails.getModeKeyList(), modeDetails.getOrgid());
						if (feeDetList != null && !feeDetList.isEmpty()) {
							for (TbSrcptModesDetEntity det : feeDetList) {
								det.setRdSrChkDate(modeDetails.getRdSrChkDate());
								det.setRdSrChkDis(modeDetails.getRdSrChkDis());
								// Defect #119702
								LookUp CLRPrefix = CommonMasterUtility.getValueFromPrefixLookUp(
										MainetConstants.Property.CLEARED, MainetConstants.Property.CLR,
										UserSession.getCurrent().getOrganisation());
								if (CLRPrefix != null)
									det.setCheckStatus(CLRPrefix.getLookUpId());
							}
							chequeDishonorService.saveReceiptModeDetails(feeDetList);
						}
					
				}
			}
		}
        setSuccessMessage(ApplicationSession.getInstance().getMessage("cheque.reconciliation.success.msg"));
        return true;}
    }

    public Long getBankId() {
        return bankId;
    }

    public void setBankId(final Long bankId) {
        this.bankId = bankId;
    }

	public double getDishonorCharge() {
		return dishonorCharge;
	}

	public void setDishonorCharge(double dishonorCharge) {
		this.dishonorCharge = dishonorCharge;
	}

}
