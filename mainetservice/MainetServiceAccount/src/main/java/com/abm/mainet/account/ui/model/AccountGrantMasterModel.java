package com.abm.mainet.account.ui.model;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import javax.annotation.Resource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.abm.mainet.account.dto.AccountBillEntryMasterBean;
import com.abm.mainet.account.dto.AccountGrantMasterDto;
import com.abm.mainet.account.dto.PaymentEntryDto;
import com.abm.mainet.account.service.AccountGrantMasterService;
import com.abm.mainet.common.constant.MainetConstants;
import com.abm.mainet.common.constant.MainetConstants.AccountConstants;
import com.abm.mainet.common.integration.acccount.dto.AccountFundMasterBean;
import com.abm.mainet.common.integration.acccount.dto.TbServiceReceiptMasBean;
import com.abm.mainet.common.integration.acccount.service.SecondaryheadMasterService;
import com.abm.mainet.common.master.service.TbFinancialyearService;
import com.abm.mainet.common.ui.model.AbstractFormModel;
import com.abm.mainet.common.utility.ApplicationSession;
import com.abm.mainet.common.utility.SeqGenFunctionUtility;
import com.abm.mainet.common.utility.UserSession;
import com.abm.mainet.common.utility.Utility;

@Component
@Scope("session")
public class AccountGrantMasterModel extends AbstractFormModel {

	private static final long serialVersionUID = -6689590850101045187L;

	@Autowired
	private AccountGrantMasterService accountGrantMasterService;

	@Resource
	private SeqGenFunctionUtility seqGenFunctionUtility;

	@Resource
	private SecondaryheadMasterService secondaryheadMasterService;
	
	@Resource
	private TbFinancialyearService tbFinancialYearService;

	private List<TbServiceReceiptMasBean> receiptMasBeanList = new ArrayList<TbServiceReceiptMasBean>();

	private String saveMode;

	private AccountGrantMasterDto accountGrantMasterDto = new AccountGrantMasterDto();

	private List<AccountGrantMasterDto> accountGrantMasterDtoList;

	private List<AccountFundMasterBean> fundList;

	private Map<Long, String> listOfinalcialyear = null;
	
	private Map<String, String> grtName;
	
	private String faYearName;
	
	private String date;
	
	private List<AccountBillEntryMasterBean> billDtoList = new ArrayList<>();
	
	private List<PaymentEntryDto> PaymentList=new ArrayList<>();


	@Autowired
	private TbFinancialyearService financialyearService;
	private static final String GENERATE_BILL_NO_FIN_YEAR_ID = "Grant sequence number generation, The financial year id is getting null value";
	private final String TB_AC_GRNTMST = "TB_AC_GRNTMST";
	private final String GRT_NO = "GRT_NO";

	public boolean saveForm() {
		if (accountGrantMasterDto.getGrntId() == null) {

			//Code modified by rahul.chaubey
			//getting the list of financial year less than SLI date for generation of transaction year.
			
			Map<Long,String> financialYear = tbFinancialYearService.getAllFinincialYearByStatusWise(UserSession.getCurrent().getOrganisation().getOrgid());
			String fYear = financialYear.entrySet().stream()
					.filter(map -> map.getKey().equals(accountGrantMasterDto.getFromPerd())).map(map -> map.getValue())
					.collect(Collectors.joining());
			
			String grtNo = generateBillNumber(UserSession.getCurrent().getLanguageId(),
					Utility.dateToString(accountGrantMasterDto.getGrtDate()));
			String grtNoGenrator = UserSession.getCurrent().getOrganisation().getOrgShortNm() + "/" + "GRT/" + fYear.replace("-20", "-")+ "/" + grtNo;
			// String invNoGenrator = UserSession.getCurrent().getOrganisation().getOrgShortNm() + "/" + "INV/" + fYear.replace("-20", "-")+ "/" + inVNo+" ";
			accountGrantMasterDto.setGrtNo(grtNoGenrator);
			accountGrantMasterDto.setLangId(Long.valueOf(UserSession.getCurrent().getLanguageId()));
			accountGrantMasterDto.setOrgId(UserSession.getCurrent().getOrganisation().getOrgid());
			accountGrantMasterDto.setCreatedBy(UserSession.getCurrent().getEmployee().getEmpId());
			accountGrantMasterDto.setCreatedDate(new Date());
			accountGrantMasterDto.setLgIpMac(UserSession.getCurrent().getEmployee().getEmppiservername());
			accountGrantMasterService.saveGrantMaster(accountGrantMasterDto);
			setSuccessMessage(ApplicationSession.getInstance().getMessage(grtNoGenrator)+" "+ApplicationSession.getInstance().getMessage("account.saved.successfully"));
			
		} else {
			accountGrantMasterDto.setOrgId(UserSession.getCurrent().getOrganisation().getOrgid());
			accountGrantMasterDto.setUpdatedBy(UserSession.getCurrent().getEmployee().getEmpId());
			accountGrantMasterDto.setUpdatedDate(new Date());
			accountGrantMasterDto.setLgIpMac(UserSession.getCurrent().getEmployee().getEmppiservername());
			accountGrantMasterService.saveGrantMaster(accountGrantMasterDto);
			setSuccessMessage(ApplicationSession.getInstance().getMessage("account.updated.sccuesfully"));
		}
		return true;
	}

	private String generateBillNumber(int languageId, String dateToString) {
		// TODO Auto-generated method stub
		Long finYearId = financialyearService.getFinanciaYearIdByFromDate(Utility.stringToDate(dateToString));
		if (finYearId == null) {
			throw new NullPointerException(GENERATE_BILL_NO_FIN_YEAR_ID);
		}
		final Long billNumber = seqGenFunctionUtility.generateSequenceNo(AccountConstants.AC.toString(), TB_AC_GRNTMST,
				GRT_NO, UserSession.getCurrent().getOrganisation().getOrgid(),
				MainetConstants.SECONDARY_MASTER.SECONDARY_SEQ_CONTINUE_VALUE, finYearId);
		return billNumber.toString();
	}

	public String getSaveMode() {
		return saveMode;
	}

	public void setSaveMode(String saveMode) {
		this.saveMode = saveMode;
	}

	public AccountGrantMasterDto getAccountGrantMasterDto() {
		return accountGrantMasterDto;
	}

	public void setAccountGrantMasterDto(AccountGrantMasterDto accountGrantMasterDto) {
		this.accountGrantMasterDto = accountGrantMasterDto;
	}

	public List<AccountGrantMasterDto> getAccountGrantMasterDtoList() {
		return accountGrantMasterDtoList;
	}

	public void setAccountGrantMasterDtoList(List<AccountGrantMasterDto> accountGrantMasterDtoList) {
		this.accountGrantMasterDtoList = accountGrantMasterDtoList;
	}

	public List<AccountFundMasterBean> getFundList() {
		return fundList;
	}

	public void setFundList(List<AccountFundMasterBean> fundList) {
		this.fundList = fundList;
	}

	public Map<Long, String> getListOfinalcialyear() {
		return listOfinalcialyear;
	}

	public void setListOfinalcialyear(Map<Long, String> listOfinalcialyear) {
		this.listOfinalcialyear = listOfinalcialyear;
	}

	public List<TbServiceReceiptMasBean> getReceiptMasBeanList() {
		return receiptMasBeanList;
	}

	public void setReceiptMasBeanList(List<TbServiceReceiptMasBean> receiptMasBeanList) {
		this.receiptMasBeanList = receiptMasBeanList;
	}

	public String getFaYearName() {
		return faYearName;
	}

	public void setFaYearName(String faYearName) {
		this.faYearName = faYearName;
	}

	public String getDate() {
		return date;
	}

	public void setDate(String date) {
		this.date = date;
	}

	public Map<String, String> getGrtName() {
		return grtName;
	}

	public void setGrtName(Map<String, String> grtName) {
		this.grtName = grtName;
	}

	public List<AccountBillEntryMasterBean> getBillDtoList() {
		return billDtoList;
	}

	public void setBillDtoList(List<AccountBillEntryMasterBean> billDtoList) {
		this.billDtoList = billDtoList;
	}

	public List<PaymentEntryDto> getPaymentList() {
		return PaymentList;
	}

	public void setPaymentList(List<PaymentEntryDto> paymentList) {
		PaymentList = paymentList;
	}


}
