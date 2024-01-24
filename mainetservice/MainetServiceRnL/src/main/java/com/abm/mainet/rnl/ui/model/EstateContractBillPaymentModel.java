
package com.abm.mainet.rnl.ui.model;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.abm.mainet.bill.service.BillMasterCommonService;
import com.abm.mainet.cfc.challan.domain.ChallanMaster;
import com.abm.mainet.cfc.challan.dto.ChallanReceiptPrintDTO;
import com.abm.mainet.cfc.challan.dto.ChallanReportDTO;
import com.abm.mainet.cfc.challan.service.IChallanService;
import com.abm.mainet.cfc.challan.ui.validator.CommonOfflineMasterValidator;
import com.abm.mainet.common.constant.MainetConstants;
import com.abm.mainet.common.domain.ContractInstalmentDetailEntity;
import com.abm.mainet.common.domain.LocationMasEntity;
import com.abm.mainet.common.domain.Organisation;
import com.abm.mainet.common.domain.ServiceMaster;
import com.abm.mainet.common.dto.CommonChallanDTO;
import com.abm.mainet.common.integration.acccount.domain.AccountFieldMasterEntity;
import com.abm.mainet.common.integration.acccount.domain.AccountHeadSecondaryAccountCodeMasterEntity;
import com.abm.mainet.common.integration.acccount.domain.TbServiceReceiptMasEntity;
import com.abm.mainet.common.integration.acccount.repository.TbAcFieldMasterJpaRepository;
import com.abm.mainet.common.integration.acccount.service.SecondaryheadMasterService;
import com.abm.mainet.common.integration.dto.TbBillMas;
import com.abm.mainet.common.master.dto.ContractAgreementSummaryDTO;
import com.abm.mainet.common.master.dto.TbTaxMas;
import com.abm.mainet.common.master.repository.ContractAgreementRepository;
import com.abm.mainet.common.master.repository.TbTaxMasJpaRepository;
import com.abm.mainet.common.master.service.TbDepartmentService;
import com.abm.mainet.common.master.service.TbTaxMasService;
import com.abm.mainet.common.repository.ReceiptRepository;
import com.abm.mainet.common.service.ILocationMasService;
import com.abm.mainet.common.service.IReceiptEntryService;
import com.abm.mainet.common.service.ServiceMasterService;
import com.abm.mainet.common.ui.model.AbstractFormModel;
import com.abm.mainet.common.utility.ApplicationSession;
import com.abm.mainet.common.utility.CommonMasterUtility;
import com.abm.mainet.common.utility.LookUp;
import com.abm.mainet.common.utility.UserSession;
import com.abm.mainet.common.utility.Utility;
import com.abm.mainet.rnl.domain.RLBillMaster;
import com.abm.mainet.rnl.service.IRLBILLMasterService;

/**
 * @author ritesh.patil
 *
 */
@Component
@Scope("session")
public class EstateContractBillPaymentModel extends AbstractFormModel {
	private static final long serialVersionUID = 1L;

	private String contractNo;
	private String propertyContractNo;
	private String formFlag = "N";
	private TbBillMas billMas = new TbBillMas();
	private List<TbBillMas> billMasList = new ArrayList<>();
	private ContractAgreementSummaryDTO contractAgreementSummaryDTO = new ContractAgreementSummaryDTO();
	private List<ContractAgreementSummaryDTO> contractAgreementList = new ArrayList<ContractAgreementSummaryDTO>();
	private Double bmTotalAmount;
	private Double bmTotalBalAmount;
	private Double payAmount;
	private Double inputAmount;
	private ServiceMaster serviceMaster;
	private Long vendorId;
	private String taxDesc;
	private List<TbBillMas> forTaxCalulation;
	private String brmsTaxCode;
	private List<Object[]> propertyDetails = new ArrayList<Object[]>();
	private String Usage;
	private Date receiptBackDate;
	private String mobileNo;
	private List<ContractAgreementSummaryDTO> listContractAgreementDTO = new ArrayList<ContractAgreementSummaryDTO>();
	private String narration;
	private List<Object[]> estateDetails = new ArrayList<Object[]>();
	private String estateName;
	private String manualRecptNo;

	@Autowired
	private IChallanService iChallanService;

	@Autowired
	private ServiceMasterService serviceMasterService;

	@Autowired
	private BillMasterCommonService billMasterCommonService;

	@Autowired
	private IRLBILLMasterService rlBillMasterService;

	@Autowired
	private ContractAgreementRepository contractAgreementRepository;

	@Autowired
	TbDepartmentService tbDepartmentService;

	@Autowired
	private TbTaxMasService tbTaxMasService;

	@Resource
	private SecondaryheadMasterService secondaryheadMasterService;

	@Autowired
	private ILocationMasService locMasService;
	
	@Resource
	private TbTaxMasJpaRepository tbTaxMasJpaRepository;
	
	@Autowired
    private ReceiptRepository receiptRepository;
	
	@Autowired
    private TbAcFieldMasterJpaRepository tbAcFieldMasterJpaRepository;
	
	@Autowired
	private IReceiptEntryService iReceiptEntryService;

	public String getContractNo() {
		return contractNo;
	}

	public void setContractNo(final String contractNo) {
		this.contractNo = contractNo;
	}

	public String getPropertyContractNo() {
		return propertyContractNo;
	}

	public void setPropertyContractNo(String propertyContractNo) {
		this.propertyContractNo = propertyContractNo;
	}

	public String getFormFlag() {
		return formFlag;
	}

	public void setFormFlag(String formFlag) {
		this.formFlag = formFlag;
	}

	public TbBillMas getBillMas() {
		return billMas;
	}

	public void setBillMas(final TbBillMas billMas) {
		this.billMas = billMas;
	}

	public List<TbBillMas> getBillMasList() {
		return billMasList;
	}

	public void setBillMasList(final List<TbBillMas> billMasList) {
		this.billMasList = billMasList;
	}

	public ContractAgreementSummaryDTO getContractAgreementSummaryDTO() {
		return contractAgreementSummaryDTO;
	}

	public void setContractAgreementSummaryDTO(final ContractAgreementSummaryDTO contractAgreementSummaryDTO) {
		this.contractAgreementSummaryDTO = contractAgreementSummaryDTO;
	}

	public List<ContractAgreementSummaryDTO> getContractAgreementList() {
		return contractAgreementList;
	}

	public void setContractAgreementList(List<ContractAgreementSummaryDTO> contractAgreementList) {
		this.contractAgreementList = contractAgreementList;
	}

	public Double getBmTotalAmount() {
		return bmTotalAmount;
	}

	public void setBmTotalAmount(final Double bmTotalAmount) {
		this.bmTotalAmount = bmTotalAmount;
	}

	public Double getBmTotalBalAmount() {
		return bmTotalBalAmount;
	}

	public void setBmTotalBalAmount(final Double bmTotalBalAmount) {
		this.bmTotalBalAmount = bmTotalBalAmount;
	}

	public Double getPayAmount() {
		return payAmount;
	}

	public void setPayAmount(final Double payAmount) {
		this.payAmount = payAmount;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.abm.mainetservice.web.core.ui.model.AbstractFormModel#saveForm()
	 */

	public Double getInputAmount() {
		return inputAmount;
	}

	public void setInputAmount(Double inputAmount) {
		this.inputAmount = inputAmount;
	}

	@Override
	public boolean saveForm() {
		if ((getPayAmount() == null) || getPayAmount().equals(0D)) {
			addValidationError(getAppSession().getMessage(MainetConstants.EstateContract.WATER_BILL_AMNT));
		}
		final long orgId = UserSession.getCurrent().getOrganisation().getOrgid();
		final Organisation org = UserSession.getCurrent().getOrganisation();

		if ((getContractNo() == null) || getContractNo().isEmpty()) {
			addValidationError(getAppSession().getMessage(MainetConstants.EstateContract.WATER_BILL_CCNO));
		}

//
//		// check if paidAmt is more than alreadyPaidAmt
		Long taxMasLookUpId = null;
		String Usagelookupcode = null;
		List<LookUp> lookUp2 = CommonMasterUtility.getLevelData("USA", 1, org);
		for (LookUp lookUp : lookUp2) {
			if (getUsage() != null && !getUsage().isEmpty()) {
				if (lookUp.getLookUpId() == Long.parseLong(getUsage())) {
					taxMasLookUpId = lookUp.getLookUpId();
					Usagelookupcode = lookUp.getLookUpCode();
				}
			}
		}
		Double balanceAmount =0.0;
		
		balanceAmount   = rlBillMasterService.getBalanceAmountByContractId(contractAgreementSummaryDTO.getContId(),
				orgId, MainetConstants.CommonConstants.N);
		final ServiceMaster service = serviceMasterService.getServiceByShortName(MainetConstants.EstateContract.CBP,
				orgId);
		Double balanceAmountwithGst=balanceAmount;
		if (!Utility.isEnvPrefixAvailable(org, MainetConstants.ENV_PSCL)) {
			if (Usagelookupcode != null && !Usagelookupcode.equals("RESNG")) {
				balanceAmountwithGst = balanceAmountwithGst + (balanceAmountwithGst * 18 / 100);
			}
		}
		if (getInputAmount() > balanceAmountwithGst) {
			addValidationError(getAppSession().getMessage("rnl.bill.pay.more.amount"));
		}
		if(Utility.isEnvPrefixAvailable(org, MainetConstants.ENV_TSCL) && StringUtils.isNotBlank(getManualRecptNo())){
			boolean status = iReceiptEntryService.findDuplicateManualReceiptExist(getManualRecptNo(),service.getSmServiceId(),service.getTbDepartment().getDpDeptid(),orgId);
			if(status) {
				addValidationError(getAppSession().getMessage("rnl.bill.duplicate.manual"));
			}
		}

		final CommonChallanDTO offline = getOfflineDTO();
		final String modeDesc = getNonHierarchicalLookUpObject(offline.getOflPaymentMode()).getLookUpCode();
		offline.setOfflinePaymentText(modeDesc);
		validateBean(offline, CommonOfflineMasterValidator.class);
		if (hasValidationErrors()) {
			return false;
		}

		Map<Long, Double> details = new HashMap<>(0);
		final Map<Long, Long> billDetails = new HashMap<>(0);

		setServiceMaster(service);
		setChallanDToandSaveChallanData(offline, details, billDetails);
		ChallanReceiptPrintDTO printDto = getReceiptDTO();
		balanceAmount = balanceAmount-getInputAmount();
		printDto.setBalanceAmount(balanceAmount);
		TbServiceReceiptMasEntity masEntity = receiptRepository.findByRmRcptidAndOrgId(printDto.getReceiptId(), orgId);
		if (masEntity.getFieldId() != null) {
			final AccountFieldMasterEntity field = tbAcFieldMasterJpaRepository.findOne(masEntity.getFieldId());
			printDto.setLocEng(field.getFieldDesc());
			printDto.setLocReg(field.getFieldDesc());
		}
		printDto.getPaymentList().sort(
				Comparator.comparing(ChallanReportDTO::getDisplaySeq, Comparator.nullsLast(Comparator.naturalOrder())));
		printDto.setNarration(getNarration());
		printDto.setServiceCode(MainetConstants.EstateContract.CBP);
		printDto.setReceiverName(UserSession.getCurrent().getEmployee().getFullName());
		setReceiptDTO(printDto);
		return true;
	}

	private void setChallanDToandSaveChallanData(final CommonChallanDTO offline, final Map<Long, Double> details,
			final Map<Long, Long> billDetails) {

		final UserSession userSession = UserSession.getCurrent();

		offline.setAmountToPay(String.valueOf(getPayAmount()));
		offline.setEmailId(getContractAgreementSummaryDTO().getEmailId());
		offline.setMobileNumber(getContractAgreementSummaryDTO().getMobileNo());
		offline.setApplicantAddress(getContractAgreementSummaryDTO().getAddress());
		offline.setApplicantName(getContractAgreementSummaryDTO().getContp2Name());
		offline.setApplicantFullName(getContractAgreementSummaryDTO().getContp2Name());
		offline.setChallanServiceType(MainetConstants.CHALLAN_RECEIPT_TYPE.REVENUE_BASED);
		// offline.setChallanServiceType(MainetConstants.CHALLAN_RECEIPT_TYPE.NON_REVENUE_BASED);
		offline.setDocumentUploaded(false);
		offline.setUniquePrimaryId(getContractAgreementSummaryDTO().getContNo());

		offline.setPaymentCategory("B");// BILL_SCHEDULE_DATE
		if (getReceiptBackDate() != null) {
			offline.setManualReeiptDate(getReceiptBackDate());
		}
		if(StringUtils.isNotBlank(getManualRecptNo())){
			offline.setManualReceiptNo(getManualRecptNo());
			offline.setManualReeiptDate(new Date());
		}
		if(StringUtils.isNotBlank(getNarration())) {
			offline.setNarration(getNarration());
		}else {
			if (Utility.isEnvPrefixAvailable(UserSession.getCurrent().getOrganisation(), MainetConstants.ENV_TSCL)) {
				offline.setNarration("-");
			}	
		}

		offline.setUserId(userSession.getEmployee().getEmpId());
		offline.setOrgId(userSession.getOrganisation().getOrgid());
		offline.setLangId(userSession.getLanguageId());
		offline.setLgIpMac(UserSession.getCurrent().getEmployee().getEmppiservername());
		offline.setFaYearId(userSession.getFinYearId());
		offline.setFinYearStartDate(userSession.getFinStartDate());
		offline.setFinYearEndDate(userSession.getFinEndDate());
		offline.setLoggedLocId(UserSession.getCurrent().getLoggedLocId());
		if ((details != null) && !details.isEmpty()) {
			offline.setFeeIds(details);
		}

		if ((billDetails != null) && !billDetails.isEmpty()) {
			offline.setBillDetIds(billDetails);
		}
		offline.setServiceId(getServiceMaster().getSmServiceId());

		offline.setDeptId(getServiceMaster().getTbDepartment().getDpDeptid());

		offline.setDeptCode(
				tbDepartmentService.findDepartmentShortCodeByDeptId(offline.getDeptId(), offline.getOrgId()));
		offline.setOfflinePaymentText(CommonMasterUtility
				.getNonHierarchicalLookUpObject(offline.getOflPaymentMode(), userSession.getOrganisation())
				.getLookUpCode());

		if ((offline.getOnlineOfflineCheck() != null)
				&& offline.getOnlineOfflineCheck().equals(MainetConstants.PAYMENT_TYPE.OFFLINE)) {
			// D#39506
			if (getApmApplicationId() != 0) {
				offline.setApplNo(getApmApplicationId());
			}
			final ChallanMaster master = iChallanService.InvokeGenerateChallan(offline);
			offline.setChallanValidDate(master.getChallanValiDate());
			offline.setChallanNo(master.getChallanNo());
			setSuccessMessage(
					ApplicationSession.getInstance().getMessage(MainetConstants.EstateContract.PRINT_CHALLAN_STATUS));
		} else if ((offline.getOnlineOfflineCheck() != null)
				&& offline.getOnlineOfflineCheck().equals(MainetConstants.PAYMENT_TYPE.PAY_AT_COUNTER)) {
			// D#39506
			offline.setApplNo(getContractAgreementSummaryDTO().getContId());
			final ChallanReceiptPrintDTO printDto = iChallanService.savePayAtUlbCounter(offline,
					getServiceMaster().getSmServiceName());
			setReceiptDTO(printDto);
			setSuccessMessage(
					ApplicationSession.getInstance().getMessage(MainetConstants.EstateContract.PAYMENT_RECEIPT_STATUS));
		}
		setOfflineDTO(offline);

	}

	public ServiceMaster getServiceMaster() {
		return serviceMaster;
	}

	public void setServiceMaster(final ServiceMaster serviceMaster) {
		this.serviceMaster = serviceMaster;
	}

	public Long getVendorId() {
		return vendorId;
	}

	public void setVendorId(final Long vendorId) {
		this.vendorId = vendorId;
	}

	public String getTaxDesc() {
		return taxDesc;
	}

	public void setTaxDesc(final String taxDesc) {
		this.taxDesc = taxDesc;
	}

	public List<TbBillMas> getForTaxCalulation() {
		return forTaxCalulation;
	}

	public void setForTaxCalulation(final List<TbBillMas> forTaxCalulation) {
		this.forTaxCalulation = forTaxCalulation;
	}

	public String getBrmsTaxCode() {
		return brmsTaxCode;
	}

	public void setBrmsTaxCode(final String brmsTaxCode) {
		this.brmsTaxCode = brmsTaxCode;
	}

	public List<Object[]> getPropertyDetails() {
		return propertyDetails;
	}

	public void setPropertyDetails(List<Object[]> propertyDetails) {
		this.propertyDetails = propertyDetails;
	}

	public String getUsage() {
		return Usage;
	}

	public void setUsage(String usage) {
		Usage = usage;
	}

	public Date getReceiptBackDate() {
		return receiptBackDate;
	}

	public void setReceiptBackDate(Date receiptBackDate) {
		this.receiptBackDate = receiptBackDate;
	}

	public String getMobileNo() {
		return mobileNo;
	}

	public void setMobileNo(String mobileNo) {
		this.mobileNo = mobileNo;
	}

	public List<ContractAgreementSummaryDTO> getListContractAgreementDTO() {
		return listContractAgreementDTO;
	}

	public void setListContractAgreementDTO(List<ContractAgreementSummaryDTO> listContractAgreementDTO) {
		this.listContractAgreementDTO = listContractAgreementDTO;
	}

	public String getNarration() {
		return narration;
	}

	public void setNarration(String narration) {
		this.narration = narration;
	}

	public List<Object[]> getEstateDetails() {
		return estateDetails;
	}

	public void setEstateDetails(List<Object[]> estateDetails) {
		this.estateDetails = estateDetails;
	}

	public String getEstateName() {
		return estateName;
	}

	public void setEstateName(String estateName) {
		this.estateName = estateName;
	}
	
	public String getManualRecptNo() {
		return manualRecptNo;
	}

	public void setManualRecptNo(String manualRecptNo) {
		this.manualRecptNo = manualRecptNo;
	}
}
