package com.abm.mainet.account.ui.model;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import javax.annotation.Resource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.abm.mainet.account.dto.AccountLoanDetDto;
import com.abm.mainet.account.dto.AccountLoanMasterDto;
import com.abm.mainet.account.dto.AccountLoanReportDTO;
import com.abm.mainet.account.dto.PaymentEntryDto;
/*import com.abm.mainet.account.dto.AccountLoanReportDTO;*/
import com.abm.mainet.account.service.AccountLoanMasterService;
import com.abm.mainet.common.constant.MainetConstants;
import com.abm.mainet.common.constant.MainetConstants.AccountConstants;
import com.abm.mainet.common.dto.TbAcVendormaster;
import com.abm.mainet.common.integration.acccount.dto.TbServiceReceiptMasBean;
import com.abm.mainet.common.integration.acccount.service.SecondaryheadMasterService;
import com.abm.mainet.common.master.repository.TbFinancialyearJpaRepository;
import com.abm.mainet.common.master.service.TbFinancialyearService;
import com.abm.mainet.common.ui.model.AbstractFormModel;
import com.abm.mainet.common.utility.ApplicationSession;
import com.abm.mainet.common.utility.SeqGenFunctionUtility;
import com.abm.mainet.common.utility.UserSession;
import com.abm.mainet.common.utility.Utility;

@Component
@Scope("session")
public class LoanMasterModel extends AbstractFormModel {
	private static final long serialVersionUID = 6049192030222033945L;

	@Autowired
	private AccountLoanMasterService accountLoanMasterService;

	@Resource
	private SeqGenFunctionUtility seqGenFunctionUtility;

	@Resource
	private SecondaryheadMasterService secondaryheadMasterService;

	@Resource
	private TbFinancialyearJpaRepository tbFinancialyearJpaRepository;
	
	@Resource
	private TbFinancialyearService tbFinancialYearService;

	private List<TbServiceReceiptMasBean> receiptMasBeanList = new ArrayList<TbServiceReceiptMasBean>();

	private AccountLoanMasterDto accountLoanMasterDto = new AccountLoanMasterDto();

	private List<AccountLoanMasterDto> accountLoanMasterDtoList = new ArrayList<AccountLoanMasterDto>();

	private List<AccountLoanDetDto> lmDetDtoList = new ArrayList<AccountLoanDetDto>();

	private List<TbAcVendormaster> vendorList = new ArrayList<TbAcVendormaster>();

	private AccountLoanDetDto accountLoanDetDto=new AccountLoanDetDto();
		
	private AccountLoanReportDTO accountLoanReportDto =new AccountLoanReportDTO();
	
	private List<AccountLoanReportDTO> loanReportDtoList = new ArrayList<AccountLoanReportDTO>();
	
	private String saveMode;

	private Map<Long, String> depList = null;
	
	private Map<Long, String> listOfFinancialYear = null;
	
	List<String> id ;
	
	private List<PaymentEntryDto> PaymentList=new ArrayList<>();


	@Autowired
	private TbFinancialyearService financialyearService;
	private static final String GENERATE_BILL_NO_FIN_YEAR_ID = "Grant sequence number generation, The financial year id is getting null value";
	private final String TB_AC_GRNTMST = "TB_AC_GRNTMST";
	private final String TB_AC_LNMST = "TB_AC_LNMST";
	private final String LN_LOANID = "LN_LOANID";
	private final String GRT_NO = "GRT_NO";

	// this method is called when save button is clicked and not the Controller's
	// save method

	@Override
	public boolean saveForm() {
		if (accountLoanMasterDto.getLoanId() == null) {
			//Code modified by rahul.chaubey
			//getting the list of financial year less than SLI date for generation of transaction year.
			
		Map<Long,String> listOfinalcialyear = tbFinancialYearService.getAllFinincialYearByStatusWise(UserSession.getCurrent().getOrganisation().getOrgid());
			Long financialYearId = tbFinancialyearJpaRepository
					.getFinanciaYearIdByFromDate(accountLoanMasterDto.getSanctionDate());
			
			String fYear = listOfinalcialyear.entrySet().stream().filter(map -> map.getKey().equals(financialYearId))
					.map(map -> map.getValue()).collect(Collectors.joining());
			String lnrNo = generateBillNumber(UserSession.getCurrent().getLanguageId(),
					Utility.dateToString(accountLoanMasterDto.getSanctionDate()));
			String lnrNoGenrator = UserSession.getCurrent().getOrganisation().getOrgShortNm() + "/" + "LNR/" + fYear.replace("-20", "-")
					+ "/" + lnrNo+" ";
			accountLoanMasterDto.setLnNo(lnrNoGenrator);
			accountLoanMasterDto.setLnStatus('A');
			//HARD CODING THE VALUE OF AUTH_BY  INST_FREQ		
			accountLoanMasterDto.setAuthBy(9L);
			//accountLoanMasterDto.setInstAmt(20l);
			accountLoanMasterDto.setInstCount(12L);
			accountLoanMasterDto.setInstFreq(345l);
			accountLoanMasterDto.setLangId(Long.valueOf(UserSession.getCurrent().getLanguageId()));
			accountLoanMasterDto.setOrgId(UserSession.getCurrent().getOrganisation().getOrgid());
			accountLoanMasterDto.setCreatedBy(UserSession.getCurrent().getEmployee().getEmpId());
			accountLoanMasterDto.setCreatedDate(new Date());
			// hard coding instCount because previously it was being mapped to no of
			// installments
			accountLoanMasterDto.setLgIpMac(UserSession.getCurrent().getEmployee().getEmppiservername());
			for (AccountLoanDetDto accountLoanDetDto : accountLoanMasterDto.getAccountLoanDetList()) {
				accountLoanDetDto.setBalPrnpalamt(accountLoanMasterDto.getSantionAmount());
				if (accountLoanDetDto.getLnDetId() == null) {
					accountLoanDetDto.setInstStatus("A");
					//change the bal principal amount once the repayment integeration is done
					accountLoanDetDto.setBalPrnpalamt(accountLoanMasterDto.getSantionAmount());
					//accountLoanDetDto.setInstDueDate(new Date());
					accountLoanDetDto.setOrgId(UserSession.getCurrent().getOrganisation().getOrgid());
					accountLoanDetDto.setCreatedBy(UserSession.getCurrent().getEmployee().getEmpId());
					accountLoanDetDto.setCreatedDate(new Date());
					accountLoanDetDto.setInstSeqno(24L);
					accountLoanDetDto.setLgIpMac(UserSession.getCurrent().getEmployee().getEmppiservername());
					accountLoanDetDto.setBalPrnpalamt(new BigDecimal(1000));
					accountLoanDetDto.setLangId(Long.valueOf(UserSession.getCurrent().getLanguageId()));
					
					BigDecimal intAmt = accountLoanDetDto.getIntAmount();
					BigDecimal prnpAmt = accountLoanDetDto.getPrnpalAmount();
					//Double intAmount =intAmt.doubleValue();
					//Double prnpalAmt = prnpAmt.doubleValue();
					accountLoanDetDto.setBalIntAmt(intAmt.add(prnpAmt));
				}
			}
			accountLoanMasterService.saveLoanMaster(accountLoanMasterDto);
			this.setSuccessMessage(lnrNoGenrator+(getAppSession().getMessage("account.loan.record.save")));
			
		} else {
			//change the bal principal amount once the repayment integeration is done
			accountLoanDetDto.setBalPrnpalamt(accountLoanMasterDto.getSantionAmount());
			accountLoanMasterDto.setOrgId(UserSession.getCurrent().getOrganisation().getOrgid());
			accountLoanMasterDto.setUpdatedBy(UserSession.getCurrent().getEmployee().getEmpId());
			accountLoanMasterDto.setUpdatedDate(new Date());
			accountLoanMasterDto.setLgIpMac(UserSession.getCurrent().getEmployee().getEmppiservername());
			for (AccountLoanDetDto accountLoanDetDto : accountLoanMasterDto.getAccountLoanDetList()) {
				accountLoanDetDto.setBalPrnpalamt(accountLoanMasterDto.getSantionAmount());
				accountLoanDetDto.setCreatedBy(UserSession.getCurrent().getEmployee().getEmpId());
				accountLoanDetDto.setCreatedDate(new Date());
				accountLoanDetDto.setInstSeqno(24L);
				accountLoanDetDto.setBalPrnpalamt(accountLoanMasterDto.getSantionAmount());
				//accountLoanDetDto.setInstDueDate(new Date());
				accountLoanDetDto.setOrgId(UserSession.getCurrent().getOrganisation().getOrgid());
				accountLoanDetDto.setCreatedBy(UserSession.getCurrent().getEmployee().getEmpId());
				accountLoanDetDto.setCreatedDate(new Date());
				accountLoanDetDto.setLgIpMac(UserSession.getCurrent().getEmployee().getEmppiservername());
				accountLoanDetDto.setBalPrnpalamt(new BigDecimal(1000));
				accountLoanDetDto.setLangId(Long.valueOf(UserSession.getCurrent().getLanguageId()));
				
				accountLoanDetDto.getBalIntAmt();
				/*if (accountLoanDetDto.getLnDetId() != null) {
					//change the bal principal amount once the repayment integeration is done
					accountLoanDetDto.setBalPrnpalamt(accountLoanMasterDto.getSantionAmount());
					accountLoanDetDto.setOrgId(UserSession.getCurrent().getOrganisation().getOrgid());
					accountLoanDetDto.setUpdatedBy(UserSession.getCurrent().getEmployee().getEmpId());
					accountLoanDetDto.setUpdatedDate(new Date());
					accountLoanDetDto.setLgIpMac(UserSession.getCurrent().getEmployee().getEmppiservername());
				}*/
			}
			accountLoanMasterService.saveLoanMaster(accountLoanMasterDto);
			setSuccessMessage(ApplicationSession.getInstance().getMessage("Updated Successfully"));
		}
		return true;
	}

	private String generateBillNumber(int languageId, String dateToString) {
		// TODO Auto-generated method stub
		Long finYearId = financialyearService.getFinanciaYearIdByFromDate(Utility.stringToDate(dateToString));
		if (finYearId == null) {
			throw new NullPointerException(GENERATE_BILL_NO_FIN_YEAR_ID);
		}
		final Long billNumber = seqGenFunctionUtility.generateSequenceNo(AccountConstants.AC.toString(), TB_AC_LNMST,
				LN_LOANID, UserSession.getCurrent().getOrganisation().getOrgid(),
				MainetConstants.SECONDARY_MASTER.SECONDARY_SEQ_CONTINUE_VALUE, finYearId);
		return billNumber.toString();
	}

	public String getSaveMode() {
		return saveMode;
	}
	
	public AccountLoanMasterDto getAccountLoanMasterDto() {
		return accountLoanMasterDto;
	}

	public void setAccountLoanMasterDto(AccountLoanMasterDto accountLoanMasterDto) {
		this.accountLoanMasterDto = accountLoanMasterDto;
	}

	public void setSaveMode(String saveMode) {
		this.saveMode = saveMode;
	}

	public List<AccountLoanDetDto> getLmDetDtoList() {
		return lmDetDtoList;
	}

	public void setLmDetDtoList(List<AccountLoanDetDto> lmDetDtoList) {
		this.lmDetDtoList = lmDetDtoList;
	}

	public List<AccountLoanMasterDto> getAccountLoanMasterDtoList() {
		return accountLoanMasterDtoList;
	}

	public void setAccountLoanMasterDtoList(List<AccountLoanMasterDto> accountLoanMasterDtoList) {
		this.accountLoanMasterDtoList = accountLoanMasterDtoList;
	}

	public Map<Long, String> getDepList() {
		return depList;
	}

	public void setDepList(Map<Long, String> depList) {
		this.depList = depList;
	}

	public List<TbAcVendormaster> getVendorList() {
		return vendorList;
	}

	public void setVendorList(List<TbAcVendormaster> vendorList) {
		this.vendorList = vendorList;
	}

	public List<TbServiceReceiptMasBean> getReceiptMasBeanList() {
		return receiptMasBeanList;
	}

	public void setReceiptMasBeanList(List<TbServiceReceiptMasBean> receiptMasBeanList) {
		this.receiptMasBeanList = receiptMasBeanList;
	}
	
	public Map<Long, String> getListOfFinancialYear() {
		return listOfFinancialYear;
	}

	public void setListOfFinancialYear(Map<Long, String> listOfFinancialYear) {
		this.listOfFinancialYear = listOfFinancialYear;
	}

	public AccountLoanDetDto getAccountLoanDetDto() {
		return accountLoanDetDto;
	}

	public void setAccountLoanDetDto(AccountLoanDetDto accountLoanDetDto) {
		this.accountLoanDetDto = accountLoanDetDto;
	}

	public AccountLoanReportDTO getAccountLoanReportDto() {
		return accountLoanReportDto;
	}

	public void setAccountLoanReportDto(AccountLoanReportDTO accountLoanReportDto) {
		this.accountLoanReportDto = accountLoanReportDto;
	}

	public List<AccountLoanReportDTO> getLoanReportDtoList() {
		return loanReportDtoList;
	}

	public void setLoanReportDtoList(List<AccountLoanReportDTO> loanReportDtoList) {
		this.loanReportDtoList = loanReportDtoList;
	}

	public List<String> getId() {
		return id;
	}

	public void setId(List<String> id) {
		this.id = id;
	}

	public List<PaymentEntryDto> getPaymentList() {
		return PaymentList;
	}

	public void setPaymentList(List<PaymentEntryDto> paymentList) {
		PaymentList = paymentList;
	}


}
