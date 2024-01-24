package com.abm.mainet.tradeLicense.ui.model;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.context.WebApplicationContext;

import com.abm.mainet.cfc.challan.dto.ChallanReceiptPrintDTO;
import com.abm.mainet.cfc.challan.service.IChallanService;
import com.abm.mainet.cfc.challan.ui.validator.CommonOfflineMasterValidator;
import com.abm.mainet.common.constant.MainetConstants;
import com.abm.mainet.common.domain.ServiceMaster;
import com.abm.mainet.common.dto.CommonChallanDTO;
import com.abm.mainet.common.integration.dms.service.IFileUploadService;
import com.abm.mainet.common.integration.dto.DocumentDetailsVO;
import com.abm.mainet.common.integration.dto.RequestDTO;
import com.abm.mainet.common.service.ServiceMasterService;
import com.abm.mainet.common.ui.model.AbstractFormModel;
import com.abm.mainet.common.utility.CommonMasterUtility;
import com.abm.mainet.common.utility.UserSession;
import com.abm.mainet.tradeLicense.dto.TradeMasterDetailDTO;

/**
 * @author Arun Shinde
 *
 */
@Component
@Scope(value = WebApplicationContext.SCOPE_SESSION)
public class TradeManualReceiptEntryModel extends AbstractFormModel {

	private static final long serialVersionUID = 1L;

	private String ownerFullName;

	private String manualReceiptNo;

	private Date manualReceiptDate;

	private Double receiptAmount;

	private TradeMasterDetailDTO tradeMasterDetailDTO = new TradeMasterDetailDTO();

	@Autowired
	private IFileUploadService fileUpload;

	@Autowired
	private IChallanService iChallanService;

	@Autowired
	private IFileUploadService fileUploadService;

	@Autowired
	private ServiceMasterService seviceMasterService;

	@Override
	public boolean saveForm() {
		setCustomViewName("TradeManualReceiptEntryDetails");
		final CommonChallanDTO offline = getOfflineDTO();
		final TradeMasterDetailDTO tradeDto = getTradeMasterDetailDTO();
		List<DocumentDetailsVO> docs = null;
		final String modeDesc = getNonHierarchicalLookUpObject(offline.getOflPaymentMode()).getLookUpCode();
		offline.setOfflinePaymentText(modeDesc);
		offline.setManualReceiptNo(getManualReceiptNo());
		offline.setManualReeiptDate(getManualReceiptDate());
		validateBean(offline, CommonOfflineMasterValidator.class);

		if (StringUtils.isEmpty(getManualReceiptNo())) {
			addValidationError(getAppSession().getMessage("trade.mandateReceiptNo"));
		}
		if (MainetConstants.PAYMENT.OFFLINE.equals(offline.getOnlineOfflineCheck())) {
			addValidationError(getAppSession().getMessage("trade.validOfflineMode"));
		}
		if (getReceiptAmount() <= 0.0) {
			addValidationError(getAppSession().getMessage("trade.validManualRecAmt"));
		}

		docs = fileUploadService.setFileUploadMethod(new ArrayList<DocumentDetailsVO>());

		if (hasValidationErrors()) {
			return false;
		}

		offline.setOfflinePaymentText(modeDesc);
		Map<Long, Double> details = new HashMap<>(0);
		final Map<Long, Long> billDetails = new HashMap<>(0);
		setChallanDToandSaveChallanData(offline, details, billDetails, tradeDto);

		// To Save Doc
		if (!docs.isEmpty()) {
			RequestDTO dto = new RequestDTO();
			dto.setDeptId(offline.getDeptId());
			dto.setServiceId(offline.getServiceId());
			dto.setReferenceId(getReceiptDTO().getReceiptId().toString());
			dto.setOrgId(offline.getOrgId());
			dto.setUserId(offline.getUserId());
			dto.setLangId(Long.valueOf(offline.getLangId()));
			fileUpload.doFileUpload(docs, dto);
		}

		return true;
	}

	// set values and payment
	private void setChallanDToandSaveChallanData(final CommonChallanDTO offline, final Map<Long, Double> details,
			final Map<Long, Long> billDetails, TradeMasterDetailDTO tradeDto) {
		final UserSession session = UserSession.getCurrent();
		offline.setAmountToPay(Double.toString(getReceiptAmount()));
		offline.setUserId(session.getEmployee().getEmpId());
		offline.setOrgId(session.getOrganisation().getOrgid());
		offline.setLangId(session.getLanguageId());
		offline.setLgIpMac(session.getEmployee().getEmppiservername());
		if ((details != null) && !details.isEmpty()) {
			offline.setFeeIds(details);
		}
		if ((billDetails != null) && !billDetails.isEmpty()) {
			offline.setBillDetIds(billDetails);
		}
		offline.setUniquePrimaryId(tradeDto.getTrdLicno());
		offline.setPropNoConnNoEstateNoL(getAppSession().getMessage("trade.propertyNo"));
		offline.setPropNoConnNoEstateNoV(tradeDto.getPmPropNo());
		offline.setFaYearId(UserSession.getCurrent().getFinYearId());
		offline.setPaymentCategory(MainetConstants.NewWaterServiceConstants.BILL_SCHEDULE_DATE);
		offline.setFinYearStartDate(UserSession.getCurrent().getFinStartDate());
		offline.setFinYearEndDate(UserSession.getCurrent().getFinEndDate());
		offline.setApplicantName(getOwnerFullName());
		offline.setApplicantFullName(getOwnerFullName());
		offline.setApplicantAddress(tradeDto.getTrdBusadd());

		List<String> mobNoList = tradeDto.getTradeLicenseOwnerdetailDTO().stream().map(data -> data.getTroMobileno())
				.collect(Collectors.toList());
		if (!mobNoList.isEmpty())
			offline.setMobileNumber(mobNoList.get(0));

		ServiceMaster serviceMas = seviceMasterService.getServiceByShortName(MainetConstants.TradeLicense.SERVICE_CODE,
				session.getOrganisation().getOrgid());
		offline.setServiceId(serviceMas.getSmServiceId());
		offline.setDeptId(serviceMas.getTbDepartment().getDpDeptid());

		offline.setChallanServiceType(MainetConstants.CHALLAN_RECEIPT_TYPE.NON_REVENUE_BASED);
		offline.setWorkflowEnable(MainetConstants.FlagN); // to skip workflow
		offline.setDocumentUploaded(false);
		offline.setEmpType(UserSession.getCurrent().getEmployee().getEmplType());
		offline.setOfflinePaymentText(CommonMasterUtility
				.getNonHierarchicalLookUpObject(offline.getOflPaymentMode(), UserSession.getCurrent().getOrganisation())
				.getLookUpCode());
		offline.setManualReceiptNo(getManualReceiptNo());
		offline.setManualReeiptDate(getManualReceiptDate());
		if (tradeDto.getTrdWard1() != null) {
			offline.getDwzDTO().setAreaDivision1(tradeDto.getTrdWard1());
		}
		if (tradeDto.getTrdWard2() != null) {
			offline.getDwzDTO().setAreaDivision2(tradeDto.getTrdWard2());
		}
		if (tradeDto.getTrdWard3() != null) {
			offline.getDwzDTO().setAreaDivision3(tradeDto.getTrdWard3());
		}
		if (tradeDto.getTrdWard4() != null) {
			offline.getDwzDTO().setAreaDivision4(tradeDto.getTrdWard4());
		}
		if (tradeDto.getTrdWard5() != null) {
			offline.getDwzDTO().setAreaDivision5(tradeDto.getTrdWard5());
		}

		if ((offline.getOnlineOfflineCheck() != null)
				&& offline.getOnlineOfflineCheck().equals(MainetConstants.PAYMENT_TYPE.PAY_AT_COUNTER)) {
			final ChallanReceiptPrintDTO printDto = iChallanService.savePayAtUlbCounter(offline, getServiceName());
			setReceiptDTO(printDto);
			setSuccessMessage(getAppSession().getMessage("trade.bill.paid"));
		}
		setOfflineDTO(offline);
	}

	public String getOwnerFullName() {
		return ownerFullName;
	}

	public void setOwnerFullName(String ownerFullName) {
		this.ownerFullName = ownerFullName;
	}

	public String getManualReceiptNo() {
		return manualReceiptNo;
	}

	public void setManualReceiptNo(String manualReceiptNo) {
		this.manualReceiptNo = manualReceiptNo;
	}

	public Date getManualReceiptDate() {
		return manualReceiptDate;
	}

	public void setManualReceiptDate(Date manualReceiptDate) {
		this.manualReceiptDate = manualReceiptDate;
	}

	public Double getReceiptAmount() {
		return receiptAmount;
	}

	public void setReceiptAmount(Double receiptAmount) {
		this.receiptAmount = receiptAmount;
	}

	public TradeMasterDetailDTO getTradeMasterDetailDTO() {
		return tradeMasterDetailDTO;
	}

	public void setTradeMasterDetailDTO(TradeMasterDetailDTO tradeMasterDetailDTO) {
		this.tradeMasterDetailDTO = tradeMasterDetailDTO;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

}
