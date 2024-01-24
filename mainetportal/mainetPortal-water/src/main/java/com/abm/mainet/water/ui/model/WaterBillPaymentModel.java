
package com.abm.mainet.water.ui.model;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.collections.CollectionUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.abm.mainet.common.constant.MainetConstants;
import com.abm.mainet.common.domain.Organisation;
import com.abm.mainet.common.domain.PortalService;
import com.abm.mainet.common.dto.ApplicantDetailDTO;
import com.abm.mainet.common.dto.CommonChallanDTO;
import com.abm.mainet.common.dto.TbBillMas;
import com.abm.mainet.common.dto.WardZoneBlockDTO;
import com.abm.mainet.common.exception.FrameworkException;
import com.abm.mainet.common.service.IServiceMasterService;
import com.abm.mainet.common.ui.model.AbstractFormModel;
import com.abm.mainet.common.ui.validator.CommonOfflineMasterValidator;
import com.abm.mainet.common.util.ApplicationSession;
import com.abm.mainet.common.util.CommonMasterUtility;
import com.abm.mainet.common.util.LookUp;
import com.abm.mainet.common.util.UserSession;
import com.abm.mainet.common.util.Utility;
import com.abm.mainet.common.utility.ApplicationContextProvider;
import com.abm.mainet.payment.dto.PaymentRequestDTO;
import com.abm.mainet.payment.service.IChallanService;
import com.abm.mainet.water.dto.TbCsmrInfoDTO;
import com.abm.mainet.water.dto.WaterBillRequestDTO;
import com.abm.mainet.water.dto.WaterBillResponseDTO;
import com.abm.mainet.water.dto.WaterBillTaxDTO;
import com.abm.mainet.water.dto.WaterDataEntrySearchDTO;
import com.abm.mainet.water.service.INewWaterConnectionService;
import com.abm.mainet.water.service.IWaterBillPaymentService;
import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;

/**
 * @author Rahul.Yadav
 *
 */
@Component
@Scope("session")
public class WaterBillPaymentModel extends AbstractFormModel {

	private static final long serialVersionUID = 7613524106924997028L;

	protected final Logger logger = Logger.getLogger(this.getClass());

	@Autowired
	private IChallanService iChallanService;

	@Autowired
	private IServiceMasterService serviceMaster;

	@Autowired
	private IWaterBillPaymentService iWaterBillPaymentService;

	private TbBillMas billMas = null;

	private Double payAmount;

	private PortalService portalService = null;

	private List<WaterBillTaxDTO> taxes = null;

	private String ccnNumber;

	private String message = null;

	private String advancePayment = null;
	private Long applicationNo = null;

	private Double rebateAmount;

	private Double excessAmount;

	private double balanceExcessAmount;

	private double surchargeAmount = 0d;
	
    private String oldccnNumber;
	
	private WaterDataEntrySearchDTO searchDTO = new WaterDataEntrySearchDTO();
	
    private String mode;
	
	private String showMode;
	
	private String waterReceiptDate;
	
	private Long orgId;

	public double getBalanceExcessAmount() {
		return balanceExcessAmount;
	}

	public void setBalanceExcessAmount(final double balanceExcessAmount) {
		this.balanceExcessAmount = balanceExcessAmount;
	}

	private Long csIdn;

	public boolean getBillPaymentData() throws JsonParseException, JsonMappingException, IOException {
		setMessage(null);
		setBillMas(new TbBillMas());
		setApplicationNo(null);
		setExcessAmount(null);
		setRebateAmount(null);
		setBalanceExcessAmount(0d);
		setTaxes(new ArrayList<WaterBillTaxDTO>(0));
		final TbBillMas entity = getBillMas();
		if (((entity.getBmNo() == null) || entity.getBmNo().isEmpty())
				&& ((getCcnNumber() == null) || getCcnNumber().isEmpty()) && ((getOldccnNumber() == null) || getOldccnNumber().isEmpty()) ) {
			addValidationError(getAppSession().getMessage("water.billPayment.search"));
		}

		if (hasValidationErrors()) {
			return false;
		}
		final WaterBillRequestDTO requestDto = new WaterBillRequestDTO();
		requestDto.setIpAddress(UserSession.getCurrent().getEmployee().getEmppiservername());
		if(getOrgId()!=null && getOrgId()>0) {
			requestDto.setOrgid(getOrgId());
		}else {
			requestDto.setOrgid(Utility.getOrgId());
		}
		requestDto.setCcnNumber(getCcnNumber().trim());
		requestDto.setOldccNumber(getOldccnNumber());
		final WaterBillResponseDTO outPutData = iWaterBillPaymentService.fetchBillingData(requestDto);
		if(outPutData.getCcnNumber()!=null && !outPutData.getCcnNumber().equals(MainetConstants.BLANK)) {
			setCcnNumber(outPutData.getCcnNumber());
		}
		if(outPutData.getOldccnNumber()!=null && !outPutData.getOldccnNumber().equals(MainetConstants.BLANK)) {
			setOldccnNumber(outPutData.getOldccnNumber());
		}
		if ((outPutData != null) && (outPutData.getTotalPayableAmount() > 0d)
				&& MainetConstants.FlagS.equals(outPutData.getStatus())) {
			entity.setOrgid(outPutData.getApplicantDto().getOrgId());
			entity.setCsIdn(outPutData.getCsIdn());
			entity.setBmTotalOutstanding(Math.floor(outPutData.getTotalPayableAmount()));
			setTaxes(outPutData.getTaxes());
			setApplicationNo(outPutData.getApplicationNumber());
			setRebateAmount(outPutData.getRebateAmount());
			setExcessAmount(outPutData.getExcessAmount());
			setBalanceExcessAmount(outPutData.getBalanceExcessAmount());
			setApplicantDetailDto(outPutData.getApplicantDto());
			setSurchargeAmount(outPutData.getSurchargeAmount());
			this.setShowMode("Y");
			return true;
		} else if (MainetConstants.NO.equals(outPutData.getStatus())) {
			addValidationError(getAppSession().getMessage("water.bill.search"));
			return false;
		} else if (MainetConstants.FlagS.equals(outPutData.getStatus())) {
			entity.setOrgid(outPutData.getApplicantDto().getOrgId());
			entity.setCsIdn(outPutData.getCsIdn());
			setApplicationNo(outPutData.getApplicationNumber());
			setApplicantDetailDto(outPutData.getApplicantDto());
			setMessage(getAppSession().getMessage("water.bill.nodue.advancemsg"));
			return false;
		} else {
			throw new FrameworkException("Some problem while search for water bill payment");
		}
	}

	@Override
	public boolean saveForm() {
		if ((getPayAmount() == null) || getPayAmount().equals(0D)) {
			addValidationError(getAppSession().getMessage("water.billPayment.amount"));
		}
		if ((getCcnNumber() == null) || getCcnNumber().isEmpty()) {
			addValidationError(getAppSession().getMessage("water.billPayment.ccnNumber"));
		}
		/*
		 * if (getPayAmount() < getSurchargeAmount()) {
		 * addValidationError(getAppSession().getMessage(
		 * "water.billpayment.validate.surcharge")); }
		 */
		final CommonChallanDTO offline = getOfflineDTO();
		final String modeDesc = getNonHierarchicalLookUpObject(offline.getOflPaymentMode()).getLookUpCode();
		offline.setOfflinePaymentText(modeDesc);
		validateBean(offline, CommonOfflineMasterValidator.class);

		if (hasValidationErrors()  /*(ApplicationSession.getInstance().getMessage("citizen.noUser.loginName")
				.equals(UserSession.getCurrent().getEmployee().getEmploginname())
				&& !MainetConstants.PAYMENT.ONLINE.equals(offline.getOnlineOfflineCheck()))*/) {
			return false;
		}
		if (getMessage() != null) {
			setAdvancePayment(MainetConstants.YES);
		}
		Map<Long, Double> details = new HashMap<>(0);
		Map<Long, Long> billDetails = new HashMap<>(0);
		final WaterBillRequestDTO inputData = new WaterBillRequestDTO();
		WaterBillResponseDTO outputData = null;
		inputData.setRebateAmount(getRebateAmount());
		inputData.setOrgid(getApplicantDetailDto().getOrgId());
		inputData.setAmountPaid(getPayAmount());
		inputData.setTotalOutstanding(getBillMas().getBmTotalOutstanding());
		inputData.setSurcharge(getSurchargeAmount());
		setCsIdn(getBillMas().getCsIdn());
		/*if ((getAdvancePayment() == null) || getAdvancePayment().isEmpty()) {
			inputData.setCsIdn(getBillMas().getCsIdn());
			try {
				outputData = iWaterBillPaymentService.saveOrUpdateBillPaid(inputData);
				if ((outputData != null) && outputData.getStatus().equals(MainetConstants.FlagF) && CollectionUtils.isNotEmpty(outputData.getValidationList())) {
					outputData.getValidationList().forEach(vlidation ->{
						addValidationError(getAppSession().getMessage(vlidation));
					});
				}
				if (hasValidationErrors()) {
					return false;
				}
			} catch (final Exception e) {
				throw new FrameworkException("Exception in saveBillPayment rest call response ", e);
			}
			if ((outputData != null) && outputData.getStatus().equals(MainetConstants.FlagS)) {
				details = outputData.getDetails();
				billDetails = outputData.getBilldetailsId();
				setCsIdn(outputData.getCsIdn());
			} else {
				throw new FrameworkException("error while saving bill payment normal ");
			}
		} else if (MainetConstants.YES.equals(getAdvancePayment())) {
			inputData.setCcnNumber(getCcnNumber());
			if (UserSession.getCurrent().getEmployee().getEmpId() != null) {
				inputData.setUserId(UserSession.getCurrent().getEmployee().getEmpId());
			}
			try {
				outputData = iWaterBillPaymentService.saveOrUpdateAdvancePayment(inputData);
			} catch (final Exception e) {
				throw new FrameworkException("Exception in getTaxDetailAndCsIdn for advance rest call response ", e);
			}
			if ((outputData != null) && outputData.getStatus().equals(MainetConstants.FlagS)) {
				details = outputData.getDetails();
				setApplicationNo(outputData.getApplicationNumber());
				setCsIdn(outputData.getCsIdn());
			} else {
				throw new FrameworkException("error while saving bill payment for advance ");
			}
		}*/
		final PortalService serviceMasterData = serviceMaster.getPortalServiceMaster(
				"BPW", getApplicantDetailDto().getOrgId());
		setPortalService(serviceMasterData);
		setChallanDToandSaveChallanData(offline, details, billDetails, getApplicationNo());
		return true;
	}

	private void setChallanDToandSaveChallanData(final CommonChallanDTO offline, final Map<Long, Double> details,
			final Map<Long, Long> billDetailId, final Long applicationNo) {
		UserSession session = UserSession.getCurrent();
		if (applicationNo != 0)
			offline.setApplNo(applicationNo);
		offline.setAmountToPay(String.valueOf(getPayAmount()));
		final ApplicantDetailDTO emp = getApplicantDetailDto();
		Organisation org = new Organisation();
		org.setOrgid(emp.getOrgId());
		offline.setEmailId(emp.getEmailId());
		offline.setMobileNumber(emp.getMobileNo());
		String userName = (emp.getApplicantFirstName() == null ? MainetConstants.BLANK
				: emp.getApplicantFirstName() + MainetConstants.WHITE_SPACE);
		userName += emp.getApplicantMiddleName() == null ? MainetConstants.BLANK
				: emp.getApplicantMiddleName() + MainetConstants.WHITE_SPACE;
		userName += emp.getApplicantLastName() == null ? MainetConstants.BLANK
				: emp.getApplicantLastName();
		offline.setApplicantName(userName);
		offline.setChallanServiceType(MainetConstants.CHALLAN_RECEIPT_TYPE.REVENUE_BASED);
		offline.setDocumentUploaded(false);
		offline.setUniquePrimaryId(getCsIdn().toString());
		offline.setUserId(session.getEmployee().getEmpId());
		offline.setOrgId(emp.getOrgId());
		offline.setEmpType(session.getEmployee().getEmplType());
		offline.setLangId(session.getLanguageId());
		offline.setLgIpMac(session.getEmployee().getEmppiservername());
		TbCsmrInfoDTO tbCsmrInfoDTO = ApplicationContextProvider.getApplicationContext().getBean(INewWaterConnectionService.class)
				.fetchConnectionDetailsByConnNo(getCcnNumber(), Utility.getOrgId());
		if(tbCsmrInfoDTO != null) {
				WardZoneBlockDTO wardDto = new WardZoneBlockDTO();
				if (tbCsmrInfoDTO.getCodDwzid1() != null)
					wardDto.setAreaDivision1(tbCsmrInfoDTO.getCodDwzid1());
				if (tbCsmrInfoDTO.getCodDwzid2() != null)
					wardDto.setAreaDivision2(tbCsmrInfoDTO.getCodDwzid2());
				if (tbCsmrInfoDTO.getCodDwzid3() != null)
					wardDto.setAreaDivision3(tbCsmrInfoDTO.getCodDwzid3());
				if (tbCsmrInfoDTO.getCodDwzid4() != null)
					wardDto.setAreaDivision4(tbCsmrInfoDTO.getCodDwzid4());
				if (tbCsmrInfoDTO.getCodDwzid5() != null)
					wardDto.setAreaDivision5(tbCsmrInfoDTO.getCodDwzid5());
				offline.setDwzDTO(wardDto);
				if(tbCsmrInfoDTO.getTrmGroup1() != null) {
					String tarrifCate = null;
					final List<LookUp> categorylist = CommonMasterUtility.getLevelData("TRF", MainetConstants.ENGLISH,
							UserSession.getCurrent().getOrganisation());
					for (final LookUp lookup : categorylist) {
						if (lookup.getLookUpId() == tbCsmrInfoDTO.getTrmGroup1()) {
							tarrifCate = lookup.getLookUpDesc();
						}
					}
					offline.setUsageType(tarrifCate);
				}
				offline.setApplicantFullName(userName);
				offline.setReferenceNo(tbCsmrInfoDTO.getCsOldccn());
				offline.setPropNoConnNoEstateNoL(getAppSession().getMessage("water.ConnectionNo"));
				offline.setPropNoConnNoEstateNoV(tbCsmrInfoDTO.getCsCcn());
				offline.setApplicantAddress(tbCsmrInfoDTO.getCsAdd());
				offline.setPlotNo(tbCsmrInfoDTO.getCsOflatno());
		}
		if ((getAdvancePayment() != null) && getAdvancePayment().equals(MainetConstants.YES)) {
			offline.setPaymentCategory(MainetConstants.ADVANCE);
		} else {
			offline.setPaymentCategory(MainetConstants.BILL);
		}
		if ((details != null) && !details.isEmpty()) {
			offline.setFeeIds(details);
		}
		offline.setBillDetIds(billDetailId);
		offline.setServiceId(getPortalService().getServiceId());
		offline.setDeptId(getPortalService().getPsmDpDeptid());
		offline.setOfflinePaymentText(
				CommonMasterUtility.getNonHierarchicalLookUpObject(offline.getOflPaymentMode(), org).getLookUpCode());
		if ((offline.getOnlineOfflineCheck() != null)
				&& offline.getOnlineOfflineCheck().equals(MainetConstants.PAYMENT_TYPE.OFFLINE)) {
			final CommonChallanDTO master = iChallanService.generateChallanNumber(offline);
			offline.setChallanValidDate(master.getChallanValidDate());
			offline.setChallanNo(master.getChallanNo());
			setSuccessMessage(getAppSession().getMessage("water.bill.challan"));
		} else {
			setSuccessMessage(getAppSession().getMessage("water.bill.payonline"));
		}
		setOfflineDTO(offline);
	}

	@Override
	public void redirectToPayDetails(final HttpServletRequest httpServletRequest,
			final PaymentRequestDTO payURequestDTO) {
		payURequestDTO.setUdf1(getPortalService().getServiceId().toString());
		payURequestDTO.setUdf2(getApplicationNo().toString());
		final ApplicantDetailDTO applicant = getApplicantDetailDto();
		payURequestDTO.setUdf3("CitizenHome.html");
		payURequestDTO.setUdf5(MainetConstants.NewWaterServiceConstants.WNC);
		payURequestDTO.setUdf7(getApplicationNo().toString());
		payURequestDTO.setServiceId(getPortalService().getServiceId());
		final PortalService serviceMasterData = serviceMaster.getPortalServiceMaster(
				"BPW", getApplicantDetailDto().getOrgId());
		if(getPortalService().getServiceId().equals(serviceMasterData.getServiceId()) && getCcnNumber()!=null) {
			payURequestDTO.setApplicationId(getCcnNumber());
		}else {
			if (getApplicationNo() != 0) {
				payURequestDTO.setApplicationId(getApplicationNo().toString());
			}else {
				payURequestDTO.setApplicationId(getCcnNumber());
			}
		}
		if (getPayAmount() != null) {
			payURequestDTO.setDueAmt(new BigDecimal(getPayAmount()));
		}
		payURequestDTO.setEmail(applicant.getEmailId());
		payURequestDTO.setMobNo(applicant.getMobileNo());
		String userName = (applicant.getApplicantFirstName() == null ? MainetConstants.BLANK
				: applicant.getApplicantFirstName().trim() + MainetConstants.WHITE_SPACE);
		userName += applicant.getApplicantMiddleName() == null ? MainetConstants.BLANK
				: applicant.getApplicantMiddleName().trim() + MainetConstants.WHITE_SPACE;
		userName += applicant.getApplicantLastName() == null ? MainetConstants.BLANK
				: applicant.getApplicantLastName().trim();
		payURequestDTO.setApplicantName(userName.trim());
		payURequestDTO.setUdf10(getPortalService().getPsmDpDeptid().toString());
		if (UserSession.getCurrent().getLanguageId() == MainetConstants.ENGLISH) {
			payURequestDTO.setServiceName(getPortalService().getServiceName());
		} else {
			payURequestDTO.setServiceName(getPortalService().getServiceNameReg());
		}
		//Added for The Urban Pay Issue
		payURequestDTO.setOrgId(applicant.getOrgId());
		payURequestDTO.setAddField6(MainetConstants.DEPT_SHORT_NAME.WATER);//Water department code
	}

	/**
	 * @return the billMas
	 */
	public TbBillMas getBillMas() {
		return billMas;
	}

	/**
	 * @param billMas
	 *            the billMas to set
	 */
	public void setBillMas(final TbBillMas billMas) {
		this.billMas = billMas;
	}

	public Double getPayAmount() {
		return payAmount;
	}

	public void setPayAmount(final Double payAmount) {
		this.payAmount = payAmount;
	}

	public PortalService getPortalService() {
		return portalService;
	}

	public void setPortalService(final PortalService portalService) {
		this.portalService = portalService;
	}

	public String getCcnNumber() {
		return ccnNumber;
	}

	public void setCcnNumber(final String ccnNumber) {
		this.ccnNumber = ccnNumber;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(final String message) {
		this.message = message;
	}

	public String getAdvancePayment() {
		return advancePayment;
	}

	public void setAdvancePayment(final String advancePayment) {
		this.advancePayment = advancePayment;
	}

	public Long getApplicationNo() {
		return applicationNo;
	}

	public void setApplicationNo(final Long applicationNo) {
		this.applicationNo = applicationNo;
	}

	public Long getCsIdn() {
		return csIdn;
	}

	public void setCsIdn(final Long csIdn) {
		this.csIdn = csIdn;
	}

	public Double getRebateAmount() {
		return rebateAmount;
	}

	public void setRebateAmount(final Double rebateAmount) {
		this.rebateAmount = rebateAmount;
	}

	public List<WaterBillTaxDTO> getTaxes() {
		return taxes;
	}

	public void setTaxes(final List<WaterBillTaxDTO> taxes) {
		this.taxes = taxes;
	}

	public Double getExcessAmount() {
		return excessAmount;
	}

	public void setExcessAmount(final Double excessAmount) {
		this.excessAmount = excessAmount;
	}

	/**
	 * @return the surchargeAmount
	 */
	public double getSurchargeAmount() {
		return surchargeAmount;
	}

	/**
	 * @param surchargeAmount
	 *            the surchargeAmount to set
	 */
	public void setSurchargeAmount(double surchargeAmount) {
		this.surchargeAmount = surchargeAmount;
	}

	public String getOldccnNumber() {
		return oldccnNumber;
	}

	public void setOldccnNumber(String oldccnNumber) {
		this.oldccnNumber = oldccnNumber;
	}


	public WaterDataEntrySearchDTO getSearchDTO() {
		return searchDTO;
	}

	public void setSearchDTO(WaterDataEntrySearchDTO searchDTO) {
		this.searchDTO = searchDTO;
	}

	public String getMode() {
		return mode;
	}

	public void setMode(String mode) {
		this.mode = mode;
	}

	public String getShowMode() {
		return showMode;
	}

	public void setShowMode(String showMode) {
		this.showMode = showMode;
	}

	public String getWaterReceiptDate() {
		return waterReceiptDate;
	}

	public void setWaterReceiptDate(String waterReceiptDate) {
		this.waterReceiptDate = waterReceiptDate;
	}

	public Long getOrgId() {
		return orgId;
	}

	public void setOrgId(Long orgId) {
		this.orgId = orgId;
	}
	
	

}
