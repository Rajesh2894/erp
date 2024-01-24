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

import com.abm.mainet.account.dto.AccountFinancialReportDTO;
import com.abm.mainet.account.dto.AccountInvestmentMasterDto;
import com.abm.mainet.account.service.AccountInvestmentService;
import com.abm.mainet.common.constant.MainetConstants;
import com.abm.mainet.common.constant.MainetConstants.AccountConstants;
import com.abm.mainet.common.integration.acccount.dto.AccountFundMasterBean;
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
public class AccountInvestmentMasterModel extends AbstractFormModel {
	private static final long serialVersionUID = -3136713545378931283L;

	@Autowired
	private AccountInvestmentService accountInvestmentService;


	@Resource
	private SeqGenFunctionUtility seqGenFunctionUtility;

	@Resource
	private SecondaryheadMasterService secondaryheadMasterService;
	
	@Resource
	private TbFinancialyearJpaRepository tbFinancialyearJpaRepository;
	
	@Resource
	private TbFinancialyearService tbFinancialYearService;
	
	
	private String saveMode;
	

	private AccountInvestmentMasterDto accountInvestmentMasterDto = new AccountInvestmentMasterDto();

	private List<AccountInvestmentMasterDto> acInvstDtoList = new ArrayList<>();

	private AccountFinancialReportDTO acfrdto = new AccountFinancialReportDTO();
	
	private Map<Long, String> bankMap =null;
	
	private List<AccountFundMasterBean> fundList;
	
	private List<String> invstId;
	
	private String empName;
	
	@Autowired
	private TbFinancialyearService financialyearService;
	private static final String GENERATE_BILL_NO_FIN_YEAR_ID = "Grant sequence number generation, The financial year id is getting null value";
	private final String TB_AC_INVMST = "TB_AC_INVMST";
	private final String IN_INVID = "IN_INVID";

	
	
	
	@Override
	public boolean saveForm() {
		if (accountInvestmentMasterDto.getInvstId() == null) {
			//Code modified by rahul.chaubey
			//getting the list of financial year less than SLI date for generation of transaction year.
			
			Map<Long,String> listOfinalcialyear = tbFinancialYearService.getAllFinincialYearByStatusWise(UserSession.getCurrent().getOrganisation().getOrgid());
			Long financialYearId = tbFinancialyearJpaRepository.getFinanciaYearIdByFromDate(accountInvestmentMasterDto.getInvstDate());
			String fYear = listOfinalcialyear.entrySet().stream()
			.filter(map -> map.getKey().equals(financialYearId))
			.map(map -> map.getValue()).collect(Collectors.joining());
			String inVNo = generateBillNumber(UserSession.getCurrent().getLanguageId(),Utility.dateToString(accountInvestmentMasterDto.getInvstDate()));
			String invNoGenrator = UserSession.getCurrent().getOrganisation().getOrgShortNm() + "/" + "INV/" + fYear.replace("-20", "-")+ "/" + inVNo+" ";
			//accountInvestmentMasterDto.setInstRate();
			accountInvestmentMasterDto.setStatus(MainetConstants.Common_Constant.ACTIVE_FLAG);
			accountInvestmentMasterDto.setLangId(217L);
			accountInvestmentMasterDto.setInvstNo(invNoGenrator);
			accountInvestmentMasterDto.setOrgId(UserSession.getCurrent().getOrganisation().getOrgid());
			accountInvestmentMasterDto.setCreatedBy(UserSession.getCurrent().getEmployee().getEmpId());
			accountInvestmentMasterDto.setCreatedDate(new Date());
			accountInvestmentMasterDto.setLgIpMac(UserSession.getCurrent().getEmployee().getEmppiservername());
			accountInvestmentService.saveInvestMentMaster(accountInvestmentMasterDto);
			setSuccessMessage(invNoGenrator  + MainetConstants.WHITE_SPACE + ApplicationSession.getInstance().getMessage("investment.record.sucessfully"));
		} else {
			accountInvestmentMasterDto.setOrgId(UserSession.getCurrent().getOrganisation().getOrgid());
			accountInvestmentMasterDto.setUpdatedBy(UserSession.getCurrent().getEmployee().getEmpId());
			accountInvestmentMasterDto.setUpdatedDate(new Date());
			accountInvestmentMasterDto.setLgIpMac(UserSession.getCurrent().getEmployee().getEmppiservername());
			accountInvestmentService.saveInvestMentMaster(accountInvestmentMasterDto);
			setSuccessMessage(ApplicationSession.getInstance().getMessage("accounts.updated.successfully"));
		}
		return true;
	}

	public String getSaveMode() {
		return saveMode;
	}

	public void setSaveMode(String saveMode) {
		this.saveMode = saveMode;
	}

	public AccountInvestmentMasterDto getAccountInvestmentMasterDto() {
		return accountInvestmentMasterDto;
	}

	public void setAccountInvestmentMasterDto(AccountInvestmentMasterDto accountInvestmentMasterDto) {
		this.accountInvestmentMasterDto = accountInvestmentMasterDto;
	}

	public List<AccountInvestmentMasterDto> getAcInvstDtoList() {
		return acInvstDtoList;
	}

	public void setAcInvstDtoList(List<AccountInvestmentMasterDto> acInvstDtoList) {
		this.acInvstDtoList = acInvstDtoList;
	}

	public AccountFinancialReportDTO getAcfrdto() {
		return acfrdto;
	}

	public void setAcfrdto(AccountFinancialReportDTO acfrdto) {
		this.acfrdto = acfrdto;
	}

	public Map<Long, String> getBankMap() {
		return bankMap;
	}

	public void setBankMap(Map<Long, String> bankMap) {
		this.bankMap = bankMap;
	}

	public List<AccountFundMasterBean> getFundList() {
		return fundList;
	}

	public void setFundList(List<AccountFundMasterBean> fundList) {
		this.fundList = fundList;
	}

	
	public List<String> getInvstId() {
		return invstId;
	}

	public void setInvstId(List<String> invstId) {
		this.invstId = invstId;
	}

	public String getEmpName() {
		return empName;
	}

	public void setEmpName(String empName) {
		this.empName = empName;
	}

	private String generateBillNumber(int languageId, String dateToString) {
		// TODO Auto-generated method stub
		Long finYearId = financialyearService.getFinanciaYearIdByFromDate(Utility.stringToDate(dateToString));
		if (finYearId == null) {
			throw new NullPointerException(GENERATE_BILL_NO_FIN_YEAR_ID);
		}
		final Long billNumber = seqGenFunctionUtility.generateSequenceNo(AccountConstants.AC.toString(), TB_AC_INVMST,
				IN_INVID, UserSession.getCurrent().getOrganisation().getOrgid(),
				MainetConstants.SECONDARY_MASTER.SECONDARY_SEQ_CONTINUE_VALUE, finYearId);
		return billNumber.toString();
	}
	
	
}
