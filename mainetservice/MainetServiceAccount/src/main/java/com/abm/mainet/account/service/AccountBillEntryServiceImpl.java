package com.abm.mainet.account.service;

import java.math.BigDecimal;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;
import javax.annotation.Resource;
import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.jws.WebService;
import javax.persistence.NonUniqueResultException;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.abm.mainet.account.dao.AccountBillEntryDao;
import com.abm.mainet.account.dao.AccountJournalVoucherEntryDao;
import com.abm.mainet.account.domain.AccountBillEntryDeductionDetEntity;
import com.abm.mainet.account.domain.AccountBillEntryDeductionDetHistEntity;
import com.abm.mainet.account.domain.AccountBillEntryExpenditureDetEntity;
import com.abm.mainet.account.domain.AccountBillEntryExpenditureDetHistEntity;
import com.abm.mainet.account.domain.AccountBillEntryMasterEnitity;
import com.abm.mainet.account.domain.AccountBillEntryMasterHistEnitity;
import com.abm.mainet.account.domain.AccountBillEntryMeasurementDetEntity;
import com.abm.mainet.account.domain.AccountVoucherEntryDetailsEntity;
import com.abm.mainet.account.domain.AccountVoucherEntryEntity;
import com.abm.mainet.account.dto.AccountBillEntryDeductionDetBean;
import com.abm.mainet.account.dto.AccountBillEntryExpenditureDetBean;
import com.abm.mainet.account.dto.AccountBillEntryMasterBean;
import com.abm.mainet.account.dto.AccountBillEntryMeasurementDetBean;
import com.abm.mainet.account.dto.AccountBudgetProjectedExpenditureBean;
import com.abm.mainet.account.dto.AccountDepositBean;
import com.abm.mainet.account.dto.VoucherReversalDTO;
import com.abm.mainet.account.mapper.AccountBudgetProjectedExpenditureServiceMapper;
import com.abm.mainet.account.repository.AccountDepositRepository;
import com.abm.mainet.account.repository.AccountTenderEntryJpaRepository;
import com.abm.mainet.account.repository.AccountVoucherEntryRepository;
import com.abm.mainet.account.repository.AdvanceEntryRepository;
import com.abm.mainet.account.repository.BillEntryRepository;
import com.abm.mainet.account.repository.BudgetHeadRepository;
import com.abm.mainet.account.repository.ContraEntryVoucherRepository;
import com.abm.mainet.account.rest.dto.VendorBillApprovalExtDTO;
import com.abm.mainet.account.rest.dto.VendorBillDedDetailExtDTO;
import com.abm.mainet.account.rest.dto.VendorBillExpDetailExtDTO;
import com.abm.mainet.account.utility.AccountWorkflowUtility;
import com.abm.mainet.common.audit.service.AuditService;
import com.abm.mainet.common.constant.MainetConstants;
import com.abm.mainet.common.constant.MainetConstants.AccountConstants;
import com.abm.mainet.common.constant.PrefixConstants;
import com.abm.mainet.common.constant.PrefixConstants.AccountJournalVoucherEntry;
import com.abm.mainet.common.constant.PrefixConstants.AccountPrefix;
import com.abm.mainet.common.dao.IDepartmentDAO;
import com.abm.mainet.common.domain.Department;
import com.abm.mainet.common.domain.FinancialYear;
import com.abm.mainet.common.domain.Organisation;
import com.abm.mainet.common.domain.ServiceMaster;
import com.abm.mainet.common.domain.TbAcVendormasterEntity;
import com.abm.mainet.common.domain.TbComparamDetEntity;
import com.abm.mainet.common.dto.CommonSequenceConfigDto;
import com.abm.mainet.common.dto.SequenceConfigMasterDTO;
import com.abm.mainet.common.dto.TbAcVendormaster;
import com.abm.mainet.common.exception.FrameworkException;
import com.abm.mainet.common.integration.acccount.domain.AccountBudgetCodeEntity;
import com.abm.mainet.common.integration.acccount.domain.AccountFundMasterEntity;
import com.abm.mainet.common.integration.acccount.dto.AccountFieldMasterBean;
import com.abm.mainet.common.integration.acccount.dto.AccountFundMasterBean;
import com.abm.mainet.common.integration.acccount.dto.VendorBillApprovalDTO;
import com.abm.mainet.common.integration.acccount.dto.VendorBillDedDetailDTO;
import com.abm.mainet.common.integration.acccount.dto.VendorBillExpDetailDTO;
import com.abm.mainet.common.integration.acccount.dto.VoucherPostDTO;
import com.abm.mainet.common.integration.acccount.dto.VoucherPostDetailDTO;
import com.abm.mainet.common.integration.acccount.repository.SecondaryheadMasterJpaRepository;
import com.abm.mainet.common.integration.acccount.service.AccountFieldMasterService;
import com.abm.mainet.common.integration.acccount.service.SecondaryheadMasterService;
import com.abm.mainet.common.integration.acccount.service.TbAcCodingstructureMasService;
import com.abm.mainet.common.integration.dms.dao.IAttachDocsDao;
import com.abm.mainet.common.master.dto.EmployeeBean;
import com.abm.mainet.common.master.dto.LocOperationWZMappingDto;
import com.abm.mainet.common.master.dto.TbLocationMas;
import com.abm.mainet.common.master.repository.TbDepartmentJpaRepository;
import com.abm.mainet.common.master.service.DepartmentService;
import com.abm.mainet.common.master.service.TbAcVendormasterService;
import com.abm.mainet.common.master.service.TbFinancialyearService;
import com.abm.mainet.common.master.service.TbOrganisationService;
import com.abm.mainet.common.service.IEmployeeService;
import com.abm.mainet.common.service.ILocationMasService;
import com.abm.mainet.common.service.IOrganisationService;
import com.abm.mainet.common.service.ServiceMasterService;
import com.abm.mainet.common.utility.ApplicationContextProvider;
import com.abm.mainet.common.utility.ApplicationSession;
import com.abm.mainet.common.utility.CommonMasterUtility;
import com.abm.mainet.common.utility.LookUp;
import com.abm.mainet.common.utility.SeqGenFunctionUtility;
import com.abm.mainet.common.utility.UserSession;
import com.abm.mainet.common.utility.Utility;
import com.abm.mainet.common.utility.UtilityService;
import com.abm.mainet.common.workflow.dao.IWorkflowTypeDAO;
import com.abm.mainet.common.workflow.domain.WorkflowMas;
import com.abm.mainet.common.workflow.dto.TaskAssignment;
import com.abm.mainet.common.workflow.dto.WorkflowProcessParameter;
import com.abm.mainet.common.workflow.dto.WorkflowRequest;
import com.abm.mainet.common.workflow.dto.WorkflowTaskAction;
import com.abm.mainet.common.workflow.service.ITaskAssignmentService;
import com.abm.mainet.common.workflow.service.IWorkflowExecutionService;
import com.abm.mainet.common.workflow.service.IWorkflowRequestService;
import com.abm.mainet.common.workflow.service.IWorkflowTaskService;
import com.abm.mainet.common.workflow.service.IWorkflowTyepResolverService;
import com.ibm.icu.text.SimpleDateFormat;

/**
 * @author tejas.kotekar
 *
 */
@WebService(targetNamespace = "http://service.account.mainet.abm.com/", portName = "AccountBillEntryServiceImplPort", serviceName = "AccountBillEntryServiceImplService")
@Service
public class AccountBillEntryServiceImpl implements AccountBillEntryService {

	@Resource
	private BillEntryRepository billEntryServiceJpaRepository;
	// @Resource
	// private TbFinancialyearJpaRepository financialYearJpaRepository;
	@Resource
	private SeqGenFunctionUtility seqGenFunctionUtility;
	@Resource
	AccountBillEntryDao accountBillEntryDao;
	@Resource
	private BudgetCodeService budgetCodeService;
	@Resource
	private BudgetHeadRepository budgetHeadRepository;
	@Resource
	private AccountDepositService depositService;
	@Resource
	private AccountBudgetProjectedExpenditureServiceMapper accountBudgetProjectedExpenditureServiceMapper;
	@Resource
	private AccountContraVoucherEntryService contraVoucherEntryService;

	@Resource
	private ILocationMasService locationMasService;

	@Resource
	private AdvanceEntryRepository advanceEntryRepository;
	@Resource
	private AccountDepositRepository accountDepositJparepository;
	@Resource
	private TbDepartmentJpaRepository tbDepartmentJpaRepository;
	@Resource
	AccountJournalVoucherEntryDao journalVoucherDao;
	@Autowired
	private IDepartmentDAO departmentDAO;
	@Resource
	private AccountVoucherEntryRepository accountVoucherEntryRepository;

	@Autowired
	private TbFinancialyearService financialyearService;

	@Resource
	private AccountVoucherPostService accountVoucherPostService;

	@Resource
	ContraEntryVoucherRepository contraEntryVoucherJpaRepository;

	@Resource
	private IEmployeeService employeeService;

	@Resource
	private TbAcVendormasterService vendorMasterService;

	@Resource
	private SecondaryheadMasterJpaRepository secondaryheadMasterJpaRepository;

	@Resource
	private AccountBudgetProjectedExpenditureService budgetProjectedExpenditureService;

	@Resource
	private DepartmentService departmentService;

	@Resource
	private AccountTenderEntryJpaRepository accountTenderEntryJparepository;
	@Resource
	private AuditService auditService;

	@Autowired
	private ServiceMasterService serviceMasterService;

	@Autowired
	private IWorkflowTyepResolverService workflowTyepResolverService;
	
	@Autowired
	private IWorkflowTypeDAO iWorkflowTypeDAO;

	@Autowired
	private IWorkflowExecutionService workflowExecutionService;

	@Autowired
	private IWorkflowRequestService workflowRequestService;

	@Resource
	private AccountDepositRepository accountDepositRepository;
	@Resource
	private SalaryBillReversalProvisionService salaryBillReversalProvisionService;
	@Autowired
	private IAttachDocsDao iAttachDocsDao;
	
	@Autowired
	private ITaskAssignmentService iTaskAssignmentService;
	
	@Resource
	private SecondaryheadMasterService secondaryheadMasterService;
	
	@Resource
	private AccountFieldMasterService accountFieldMasterService;
	
	@Resource
	private TbAcCodingstructureMasService tbAcCodingstructureMasService;
	
	@Autowired
	private TbOrganisationService tbOrganisationService;
	
	@Autowired
	private IWorkflowTaskService iWorkflowTaskService;
	

	private static final String SEQUENCE_NO_ERROR = "unable to generate sequenceNo for orgId=";
	private static final String SEQUENCE_NO = "0000000000";
	private static final String TEMPLATE_ID_NOT_FOUND_VOU = "templateTypeId not found for provided parameter[voucherType=";
	private static final String ORG_ID = ", orgId=";
	private static final String PREFIX_VOT = ",prefix=VOT]";

	private final String TB_AC_BILL_MAS = "TB_AC_BILL_MAS";
	private final String BM_BILLNO = "BM_BILLNO";
	private static final String EMPTY_SEARCH = "Please select at least one criteria for search";
	private static final String CANNOT_BE_NULL_EMPTY_ZERO = " cannot be null, empty or zero.";
	private static final String CANNOT_BE_NULL_EMPTY = " cannot be null or empty.";
	private static final String GENERATE_BILL_NO_FIN_YEAR_ID = "Bill sequence number generation, The financial year id is getting null value";
	private static final String GENERATE_VOUCHER_NO_FIN_YEAR_ID = "Voucher sequence number generation, The financial year id is getting null value";
	private static final String GENERATE_VOUCHER_NO_VOU_TYPE_ID = "Voucher sequence number generation, The voucher type id is getting null value";
	private static final String FI_YEAR_DATE_MAP = "Financial year Status is missing in the given financial year Date : ";
	private static final String ORGID_IS = " and orgid is : ";
	private static final String FI_YEAR_STATUS_CLOSED = "This Financial year status is already closed";
	private static Logger LOGGER = Logger.getLogger(AccountBillEntryServiceImpl.class);

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.abm.mainetservice.account.service.AccountBillEntryService#createBillEntry
	 * (com.abm.mainetservice.account.bean. AccountBillEntryMasterBean)
	 */
	@Override
	@Transactional(rollbackFor = Exception.class)
	public AccountBillEntryMasterEnitity createBillEntry(final AccountBillEntryMasterBean billEntryBean)
			throws ParseException {
		final AccountBillEntryMasterEnitity billMasterEntity = new AccountBillEntryMasterEnitity();
		AccountBillEntryMasterHistEnitity billHistEntity = null;
		AccountBillEntryExpenditureDetHistEntity billExpHistEntity = null;
		AccountBillEntryDeductionDetHistEntity billDedHistEntity = null;
		final LookUp lookUp = CommonMasterUtility.getValueFromPrefixLookUp(PrefixConstants.NEC.ADVOCATE,
				MainetConstants.BUG_HEAD_OPENING_BALANCE_MASTER.SLI_PREFIX_VALUE,
				UserSession.getCurrent().getOrganisation());
		String transactDate = billEntryBean.getTransactionDate();
		String sliDate = lookUp.getOtherField();
		Date transactionalDate = null;
		Date SLIDate = null;
		try {
			transactionalDate = new SimpleDateFormat("dd/MM/yyyy").parse(transactDate);
			SLIDate = new SimpleDateFormat("dd/MM/yyyy").parse(sliDate);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		if (billEntryBean.getBillIntRefId() != null) {
			billMasterEntity.setBillIntRefId(billEntryBean.getBillIntRefId());
		}
		
		if(StringUtils.isNotBlank(billEntryBean.getGrantFlag()) && StringUtils.equals(billEntryBean.getGrantFlag(), MainetConstants.FlagY)) {
			billMasterEntity.setBillIntRefId(billEntryBean.getGrantId());
		}
		
		if(StringUtils.isNotBlank(billEntryBean.getLoanFlag()) && StringUtils.equals(billEntryBean.getLoanFlag(), MainetConstants.FlagY)) {
			billMasterEntity.setBillIntRefId(billEntryBean.getLoanId());
		}
		saveCommonBillEntryFields(billEntryBean, billMasterEntity);
		setBillMasterDetails(billEntryBean, billMasterEntity);
		Objects.requireNonNull(billEntryBean.getTransactionDate(),
				ApplicationSession.getInstance().getMessage("account.bill.entry.service.transactiondate")
						+ billEntryBean);
		billMasterEntity.setBillEntryDate(Utility.stringToDate(billEntryBean.getTransactionDate()));
		
		//This condition is for if there is no workflow then authorization task should be generated - 24777
		// #156305
		if(StringUtils.equals(billEntryBean.getAuthorizationMode(), "Auth") || Utility.compareDate(transactionalDate,SLIDate) ) {
			billMasterEntity.setCheckerAuthorization(MainetConstants.CommonConstants.CHAR_Y);
		}else {
			billMasterEntity.setCheckerAuthorization(MainetConstants.CommonConstants.CHAR_N);
		}
		
		billMasterEntity.setCheckerUser(UserSession.getCurrent().getEmployee().getEmpId());
		// transaction date will become checker date in case of Authorization not
		// required
		billMasterEntity.setCheckerDate(Utility.stringToDate(billEntryBean.getTransactionDate()));
		billMasterEntity.setPayStatus(MainetConstants.CommonConstants.CHAR_N);
		saveBillExpenditureDetails(billEntryBean, billMasterEntity);

		billMasterEntity.setFieldId(billEntryBean.getFieldId());
		billEntryBean.setBillEntryDateDt(billMasterEntity.getBillEntryDate());
		
		
		if(!Utility.compareDate(transactionalDate,SLIDate)) {
		// bill entry voucher posting validate to given VoucherPostDTO
		billEntryVoucherPostingValidation(billEntryBean);
		}
		String billNo = null;
		if ((billEntryBean.getId() != null) && (billEntryBean.getId() > 0L)) {
			billMasterEntity.setId(billEntryBean.getId());
			billMasterEntity.setBillNo(billEntryBean.getBillNo());
			billNo = billEntryBean.getBillNo();
		} else {
			billNo = generateBillNumber(billEntryBean.getOrgId(),
					Utility.dateToString(billEntryBean.getBillEntryDateDt()));
			billMasterEntity.setBillNo(billNo);
			
		}
		billEntryBean.setBillNo(billNo);

		saveBillDeductionDetails(billEntryBean, billMasterEntity);
		// this is use for the advance register because advanceId reference is not going
		// in to the bill mas table
		billMasterEntity.setAdvanceTypeId(billEntryBean.getPrAdvEntryId());
		AccountBillEntryMasterEnitity newBillMasterEntity = billEntryServiceJpaRepository.save(billMasterEntity);

		if (billEntryBean.getId() != null && billEntryBean.getId() > 0L) {
			String expIds = billEntryBean.getDeletedExpIds();
			if (expIds != null && !expIds.isEmpty()) {
				String array[] = expIds.split(MainetConstants.operator.COMMA);
				for (String id : array) {
					billEntryServiceJpaRepository.deleteByExpenditureDetails(Long.valueOf(id));
				}
			}
			String ids = billEntryBean.getDeletedDedIds();
			if (ids != null && !ids.isEmpty()) {
				String array[] = ids.split(MainetConstants.operator.COMMA);
				for (String id : array) {
					billEntryServiceJpaRepository.deleteByDeductionDetails(Long.valueOf(id));
				}
			}
		}

		if ((billEntryBean.getId() != null) && (billEntryBean.getId() > 0L)) {

			billHistEntity = new AccountBillEntryMasterHistEnitity();
			billHistEntity.sethStatus(MainetConstants.CommonConstants.CHAR_U);
			try {
				auditService.createHistory(newBillMasterEntity, billHistEntity);
			} catch (Exception ex) {
				LOGGER.error("Could not make audit entry for " + newBillMasterEntity, ex);
			}
			List<AccountBillEntryExpenditureDetEntity> expList = newBillMasterEntity.getExpenditureDetailList();
			for (AccountBillEntryExpenditureDetEntity accountBillEntryExpenditureDetEntity : expList) {
				billExpHistEntity = new AccountBillEntryExpenditureDetHistEntity();
				billExpHistEntity.sethStatus(MainetConstants.CommonConstants.CHAR_U);
				try {
					auditService.createHistory(accountBillEntryExpenditureDetEntity, billExpHistEntity);
				} catch (Exception ex) {
					LOGGER.error("Could not make audit entry for " + accountBillEntryExpenditureDetEntity, ex);
				}
			}

			List<AccountBillEntryDeductionDetEntity> dedList = newBillMasterEntity.getDeductionDetailList();
			if (dedList != null && !dedList.isEmpty()) {
				for (AccountBillEntryDeductionDetEntity accountBillEntryDeductionDetEntity : dedList) {
					billDedHistEntity = new AccountBillEntryDeductionDetHistEntity();
					billDedHistEntity.sethStatus(MainetConstants.CommonConstants.CHAR_U);
					try {
						auditService.createHistory(accountBillEntryDeductionDetEntity, billDedHistEntity);
					} catch (Exception ex) {
						LOGGER.error("Could not make audit entry for " + accountBillEntryDeductionDetEntity, ex);
					}
				}
			}

		} else {
			billHistEntity = new AccountBillEntryMasterHistEnitity();
			billHistEntity.sethStatus(MainetConstants.CommonConstants.CHAR_C);
			try {
				auditService.createHistory(newBillMasterEntity, billHistEntity);
			} catch (Exception ex) {
				LOGGER.error("Could not make audit entry for " + newBillMasterEntity, ex);
			}

			List<AccountBillEntryExpenditureDetEntity> expList = newBillMasterEntity.getExpenditureDetailList();
			for (AccountBillEntryExpenditureDetEntity accountBillEntryExpenditureDetEntity : expList) {
				billExpHistEntity = new AccountBillEntryExpenditureDetHistEntity();
				billExpHistEntity.sethStatus(MainetConstants.CommonConstants.CHAR_C);
				try {
					auditService.createHistory(accountBillEntryExpenditureDetEntity, billExpHistEntity);
				} catch (Exception ex) {
					LOGGER.error("Could not make audit entry for " + accountBillEntryExpenditureDetEntity, ex);
				}
			}

			List<AccountBillEntryDeductionDetEntity> dedList = newBillMasterEntity.getDeductionDetailList();
			if (dedList != null && !dedList.isEmpty()) {
				for (AccountBillEntryDeductionDetEntity accountBillEntryDeductionDetEntity : dedList) {
					billDedHistEntity = new AccountBillEntryDeductionDetHistEntity();
					billDedHistEntity.sethStatus(MainetConstants.CommonConstants.CHAR_C);
					try {
						auditService.createHistory(accountBillEntryDeductionDetEntity, billDedHistEntity);
					} catch (Exception ex) {
						LOGGER.error("Could not make audit entry for " + accountBillEntryDeductionDetEntity, ex);
					}
				}
			}
		}

		if ((billEntryBean.getAdvanceFlag() != null) && !billEntryBean.getAdvanceFlag().isEmpty()) {
			if (billEntryBean.getAdvanceFlag().equals(MainetConstants.MENU.Y)) {
				final Long advId = billEntryBean.getPrAdvEntryId();
				BigDecimal balanceAmount = null;
				for (final AccountBillEntryExpenditureDetBean billEntryExpenditureDet : billEntryBean
						.getExpenditureDetailList()) {
					final BigDecimal feeAmount = billEntryExpenditureDet.getBillChargesAmount();
					final BigDecimal balAmt = billEntryBean.getBalAmount();
					balanceAmount = balAmt.subtract(feeAmount);
				}
				advanceEntryRepository.updateAdvanceBalanceAmountInAdvanceTable(advId, balanceAmount,
						billEntryBean.getOrgId());
			}
		}

		if ((billEntryBean.getDepositFlag() != null) && !billEntryBean.getDepositFlag().isEmpty()) {
			if (billEntryBean.getDepositFlag().equals(MainetConstants.MENU.Y)) {
				final Long depId = billEntryBean.getDepId();
				final Long statusId = CommonMasterUtility.lookUpIdByLookUpCodeAndPrefix(
						PrefixConstants.AccountBillEntry.RCI, PrefixConstants.NewWaterServiceConstants.RDC,
						billEntryBean.getOrgId());
				accountDepositJparepository.updateDepositBalanceAmountInDepositTable(depId, statusId,
						billEntryBean.getOrgId());
			}
		}
		// billEntryBean.setBillEntryDateDt(billMasterEntity.getBillEntryDate());
		
		//postInvoiceBillEntry should be done at the time of authorization only.
		//D#164164
       if (!Utility.compareDate(transactionalDate, SLIDate)) {
			postInvoiceBillEntry(billEntryBean);
		}
		
		return billMasterEntity;
	}

	private void billEntryVoucherPostingValidation(AccountBillEntryMasterBean billEntryBean) {
		LOGGER.info("Process for account bill entry voucher posting validation:" + billEntryBean);

		final VoucherPostDTO postDto = new VoucherPostDTO();
		List<VoucherPostDTO> postDtoList = new ArrayList<>();
		final List<VoucherPostDetailDTO> voucherDetails = new ArrayList<>();
		VoucherPostDetailDTO postDetailDtoCr = null;
		VoucherPostDetailDTO postDetailDtoDr = null;
		VoucherPostDetailDTO postDetailDtoCrVendor = null;
		postDto.setVoucherDate(billEntryBean.getBillEntryDateDt());
		final Long voucherSubTypeId = CommonMasterUtility.lookUpIdByLookUpCodeAndPrefix(AccountConstants.BI.toString(),
				AccountPrefix.TDP.toString(), billEntryBean.getOrgId());
		postDto.setVoucherSubTypeId(voucherSubTypeId);
		/// final Long departmentId =
		/// departmentService.getDepartmentIdByDeptCode(AccountConstants.AC.toString());
		postDto.setDepartmentId(billEntryBean.getDepartmentId());
		// postDto.setVoucherReferenceNo(billEntryBean.getBillNo()); //optional
		postDto.setNarration(billEntryBean.getNarration());
		postDto.setPayerOrPayee(billEntryBean.getVendorDesc());// Vendor name - optional
		postDto.setEntryType(MainetConstants.BILL_MASTER_COMMON.ENTRY_TYPE);

		postDto.setFieldId(billEntryBean.getFieldId());
		postDto.setOrgId(billEntryBean.getOrgId());
		postDto.setCreatedBy(billEntryBean.getCreatedBy());
		postDto.setCreatedDate(billEntryBean.getCreatedDate());
		postDto.setLgIpMac(billEntryBean.getLgIpMacAddress());
		postDto.setBillVouPostingDate(Utility.stringToDate(billEntryBean.getAuthorizationDate())); // ur logic

		/* Credit side */
		final Long crId = CommonMasterUtility.lookUpIdByLookUpCodeAndPrefix(AccountConstants.CR.toString(),
				PrefixConstants.DCR, billEntryBean.getOrgId());
		final List<AccountBillEntryDeductionDetBean> dedDetList = billEntryBean.getDeductionDetailList();
		if ((dedDetList != null) && !dedDetList.isEmpty()) {
			if (dedDetList.get(0).getBudgetCodeId() != null) {
				if ((dedDetList != null) && !dedDetList.isEmpty()) {
					for (final AccountBillEntryDeductionDetBean dedBean : dedDetList) {
						postDetailDtoCr = new VoucherPostDetailDTO();
						postDetailDtoCr.setVoucherAmount(dedBean.getDeductionAmount());
						postDetailDtoCr.setDrCrId(crId); // ur logic
						postDetailDtoCr.setSacHeadId(dedBean.getBudgetCodeId()); // optional
						voucherDetails.add(postDetailDtoCr);
					}
				}
			}
		}

		/* Debit side */
		final Long drId = CommonMasterUtility.lookUpIdByLookUpCodeAndPrefix(AccountConstants.DR.toString(),
				PrefixConstants.DCR, billEntryBean.getOrgId());
		final List<AccountBillEntryExpenditureDetBean> expDetList = billEntryBean.getExpenditureDetailList();
		for (final AccountBillEntryExpenditureDetBean expBean : expDetList) {

			postDetailDtoDr = new VoucherPostDetailDTO();
			postDetailDtoDr.setVoucherAmount(expBean.getBillChargesAmount());
			postDetailDtoDr.setDrCrId(drId);
			postDetailDtoDr.setSacHeadId(expBean.getBudgetCodeId());
			voucherDetails.add(postDetailDtoDr);
		}
		// Vendor credit entry
		postDetailDtoCrVendor = new VoucherPostDetailDTO();

		postDetailDtoCrVendor.setDrCrId(crId);
		postDetailDtoCrVendor.setVoucherAmount(billEntryBean.getNetPayable());
		Long status = CommonMasterUtility.lookUpIdByLookUpCodeAndPrefix(MainetConstants.CommonConstants.ACTIVE,
				AccountPrefix.ACN.toString(), billEntryBean.getOrgId());
		final Long vendorHeadID = billEntryServiceJpaRepository
				.getVendorSacHeadIdByVendorId(billEntryBean.getVendorId(), billEntryBean.getOrgId(), status);
		postDetailDtoCrVendor.setSacHeadId(vendorHeadID);

		voucherDetails.add(postDetailDtoCrVendor);
		postDto.setVoucherDetails(voucherDetails);
		postDtoList.add(postDto);
		List<String> responseValidation = accountVoucherPostService.checkVoucherPostValidateInput(postDtoList);
		if (responseValidation.size() > 0) {
			throw new NullPointerException(
					"improper input parameter for VoucherPostDTO in bill entry -> " + responseValidation);
		}
	}

	@Override
	@Transactional(rollbackFor = Exception.class)
	public AccountBillEntryMasterEnitity createBillEntryWithMakerChecker(final AccountBillEntryMasterBean billEntryBean,
			final String ipMacAddress) throws ParseException {

		final AccountBillEntryMasterEnitity billMasterEntity = new AccountBillEntryMasterEnitity();
		AccountBillEntryMasterHistEnitity billHistEntity = null;
		AccountBillEntryExpenditureDetHistEntity billExpHistEntity = null;
		AccountBillEntryDeductionDetHistEntity billDedHistEntity = null;
		
		
		
		if(StringUtils.isNotBlank(billEntryBean.getGrantFlag()) && StringUtils.equals(billEntryBean.getGrantFlag(), MainetConstants.FlagY)) {
			billMasterEntity.setBillIntRefId(billEntryBean.getGrantId());
		}
		
		if(StringUtils.isNotBlank(billEntryBean.getLoanFlag()) && StringUtils.equals(billEntryBean.getLoanFlag(), MainetConstants.FlagY)) {
			billMasterEntity.setBillIntRefId(billEntryBean.getLoanId());
		}
		if(billEntryBean.getDueDate()!=null) {
			billMasterEntity.setDueDate(Utility.stringToDate(billEntryBean.getDueDate()));
		}
		
		
		boolean fiYearHeadClosed = false;
		if (billEntryBean.getAuthorizationDate() != null && !billEntryBean.getAuthorizationDate().isEmpty()) {
			Date hardClosedFiYearDate = Utility.stringToDate(billEntryBean.getAuthorizationDate());
			Long finYeadStatus = financialyearService.checkHardClosedFinYearDateExists(hardClosedFiYearDate,
					billEntryBean.getOrgId());
			if (finYeadStatus == null) {
				throw new NullPointerException(
						FI_YEAR_DATE_MAP + hardClosedFiYearDate + ORGID_IS + billEntryBean.getOrgId());
			} else {
				Long finYeadMonthStatus = financialyearService.checkSoftClosedFinYearDateExists(hardClosedFiYearDate,
						billEntryBean.getOrgId());
				Organisation org = new Organisation();
				org.setOrgid(billEntryBean.getOrgId());
				String fiYearStatusCode = CommonMasterUtility.getNonHierarchicalLookUpObject(finYeadStatus, org)
						.getLookUpCode();
				String fiYearMonthStatusCode = "";
				if (finYeadMonthStatus != null) {
					fiYearMonthStatusCode = CommonMasterUtility.getNonHierarchicalLookUpObject(finYeadMonthStatus, org)
							.getLookUpCode();
				}
				 if ((fiYearStatusCode != null && !fiYearStatusCode.isEmpty())
	                        && (fiYearMonthStatusCode !=null && !fiYearMonthStatusCode.isEmpty())) {
	                    if (fiYearStatusCode.equals(PrefixConstants.LookUp.OPN) || fiYearMonthStatusCode.equals(PrefixConstants.LookUp.OPN) ) {
	                        fiYearHeadClosed = true;
	                    }
	                }
			}
			if (fiYearHeadClosed == false) {
			throw new NullPointerException(FI_YEAR_STATUS_CLOSED);
			}
		}
		

		String billNo = null;
		if ((billEntryBean.getId() != null) && (billEntryBean.getId() > 0L)) {
			billMasterEntity.setId(billEntryBean.getId());
			billMasterEntity.setBillNo(billEntryBean.getBillNo());
			billNo = billEntryBean.getBillNo();
		} else {
			//Need to configure here
			SequenceConfigMasterDTO configMasterDTO = null;
	        Long deptId = departmentService.getDepartmentIdByDeptCode(MainetConstants.AccountConstants.AC.getValue(),
	                PrefixConstants.STATUS_ACTIVE_PREFIX);
	        configMasterDTO = seqGenFunctionUtility.loadSequenceData(billEntryBean.getOrgId(), deptId,
	                MainetConstants.AccountBillEntry.TB_AC_BILL_MAS, MainetConstants.AccountBillEntry.BM_BILLNO);
	        if (configMasterDTO.getSeqConfigId() == null) {
			billNo = generateBillNumber(billEntryBean.getOrgId(),
					Utility.dateToString(billEntryBean.getBillEntryDateDt()));
			billNo=billEntryBean.getOrgShortName() + "/VB/" + billEntryBean.getTransactionDate() + "/" + billNo;
	        }else {
	        	 CommonSequenceConfigDto commonDto = new CommonSequenceConfigDto();
	        	 AccountFieldMasterBean fieldMaster = accountFieldMasterService.findById(billEntryBean.getFieldId());
	        	 if(fieldMaster!=null) {
	        		 commonDto.setCustomField(fieldMaster.getFieldDesc());
	        	 }
	        	 billNo=seqGenFunctionUtility.generateNewSequenceNo(configMasterDTO, commonDto);
	        }
		}
		 billEntryBean.setBillNo(billNo);
    	 billMasterEntity.setBillNo(billNo);
		ServiceMaster service = serviceMasterService.getServiceByShortName("VB", billEntryBean.getOrgId());
		if (service != null) {
			//Checking Workflow amount wise have or not
			WorkflowMas workflowMas = null;
			//Checking workflow bill Type wise or not
			LookUp lookup= null;
			try {
				lookup = CommonMasterUtility.getLookUpFromPrefixLookUpValue("BTW", "AIC", 1,
						UserSession.getCurrent().getOrganisation());
			} catch (FrameworkException e) {
				LOGGER.info("Bill Type perfix not define in AIC perfix");
			}
			if (lookup != null && lookup.getOtherField() != null && lookup.getOtherField().equals("Y")) {
				List<WorkflowMas> worKFlowList = iWorkflowTypeDAO.getDefinedActiveWorkFlows(service.getOrgid(),
						service.getTbDepartment().getDpDeptid(), service.getSmServiceId(), null,
						billEntryBean.getBillTypeId(), null);
				if (CollectionUtils.isNotEmpty(worKFlowList)) {
					for (WorkflowMas mas : worKFlowList) {
						if (mas.getStatus().equalsIgnoreCase("Y")) {
							if (mas.getToAmount() != null) {
								workflowMas = workflowTyepResolverService.resolveServiceWorkflowType(service.getOrgid(),
										service.getTbDepartment().getDpDeptid(), service.getSmServiceId(),
										billEntryBean.getTotalSanctionedAmount(), null, billEntryBean.getBillTypeId(),
										billEntryBean.getWorkFlowLevel1(), billEntryBean.getWorkFlowLevel2(),
										billEntryBean.getWorkFlowLevel3(), billEntryBean.getWorkFlowLevel4(),
										billEntryBean.getWorkFlowLevel5());
								break;
							} else {
								workflowMas = workflowTyepResolverService.resolveServiceWorkflowType(service.getOrgid(),
										service.getTbDepartment().getDpDeptid(), service.getSmServiceId(), null, null,
										billEntryBean.getBillTypeId(), billEntryBean.getWorkFlowLevel1(),
										billEntryBean.getWorkFlowLevel2(), billEntryBean.getWorkFlowLevel3(),
										billEntryBean.getWorkFlowLevel4(), billEntryBean.getWorkFlowLevel5());
								break;
							}
						}
					}
				} else {
			 throw new FrameworkException("Workflow Not Found");
		}
			}
			else {
				List<WorkflowMas> worKFlowList = iWorkflowTypeDAO.getAllWorkFlows(service.getOrgid(),
						service.getTbDepartment().getDpDeptid(), service.getSmServiceId());
				if(CollectionUtils.isNotEmpty(worKFlowList)) {
				for(WorkflowMas mas:worKFlowList) {
				   if(mas.getStatus().equalsIgnoreCase("Y")) {
					if(mas.getToAmount()!=null ) {
						workflowMas = workflowTyepResolverService.resolveServiceWorkflowType(service.getOrgid(),
								service.getTbDepartment().getDpDeptid(), service.getSmServiceId(),
								billEntryBean.getTotalSanctionedAmount(), null, billEntryBean.getWorkFlowLevel1(),
								billEntryBean.getWorkFlowLevel2(), billEntryBean.getWorkFlowLevel3(),
								billEntryBean.getWorkFlowLevel4(), billEntryBean.getWorkFlowLevel5());
						break;
					}else {
						workflowMas = workflowTyepResolverService.resolveServiceWorkflowType(service.getOrgid(),
								service.getTbDepartment().getDpDeptid(), service.getSmServiceId(),
								billEntryBean.getWorkFlowLevel1(), billEntryBean.getWorkFlowLevel2(),
								billEntryBean.getWorkFlowLevel3(), billEntryBean.getWorkFlowLevel4(),
								billEntryBean.getWorkFlowLevel5());
						break;
					}
				 }
				}
			}else {
				 throw new FrameworkException("Workflow Not Found");
			}
			}
			/*WorkflowMas workflowAount = workflowTyepResolverService.resolveServiceWorkflowType(service.getOrgid(),
					service.getTbDepartment().getDpDeptid(), service.getSmServiceId(),
					billEntryBean.getWorkFlowLevel1(), billEntryBean.getWorkFlowLevel2(),
					billEntryBean.getWorkFlowLevel3(), billEntryBean.getWorkFlowLevel4(),
					billEntryBean.getWorkFlowLevel5());
			/*if (workflowAount.getToAmount() != null) {
				workflowMas = workflowTyepResolverService.resolveServiceWorkflowType(service.getOrgid(),
						service.getTbDepartment().getDpDeptid(), service.getSmServiceId(),
						billEntryBean.getTotalSanctionedAmount(), null, billEntryBean.getWorkFlowLevel1(),
						billEntryBean.getWorkFlowLevel2(), billEntryBean.getWorkFlowLevel3(),
						billEntryBean.getWorkFlowLevel4(), billEntryBean.getWorkFlowLevel5());
			} */
			if (workflowMas != null) {
				try {
					Long level=null;
					if(billEntryBean.getActualTaskId()!=null)
					level=iWorkflowTaskService.findByTaskId(billEntryBean.getActualTaskId()).getCurentCheckerLevel();
					if(level!=null)
					billEntryBean.setLevelCheck(level);
					WorkflowProcessParameter processParameter = AccountWorkflowUtility
							.prepareInitAccountBilllEntryProcessParameter(billEntryBean, workflowMas,
									billMasterEntity.getBillNo());
					TaskAssignment assignment = new TaskAssignment();
					assignment.setUrl("AccountBillAuthorization.html");
					assignment.setDeptId(billEntryBean.getDepartmentId());
					assignment.setServiceId(service.getSmServiceId());
					if (billEntryBean.getCheckerAuthorization() == null) {
						assignment.setActorId(billEntryBean.getCreatedBy().toString());
						assignment.setOrgId(billEntryBean.getOrgId());
						processParameter.setRequesterTaskAssignment(assignment);
						//processParameter.getWorkflowTaskAction().setSendBackToGroup(0);
						//processParameter.getWorkflowTaskAction().setSendBackToLevel(0);
						WorkflowTaskAction workflowAction =processParameter.getWorkflowTaskAction();
						workflowAction.setDecision(MainetConstants.WorkFlow.Decision.SUBMITTED);
						workflowExecutionService.initiateWorkflow(processParameter);
					} else {
						workflowExecutionService.updateWorkflow(processParameter);
					}
				} catch (Exception e) {
					LOGGER.error("Unsuccessful initiation/updation of task for application : "
							+ billMasterEntity.getBillNo() + e);
				}
			}
		}
		if (billEntryBean.getBillIntRefId() != null) {
			billMasterEntity.setBillIntRefId(billEntryBean.getBillIntRefId());
		}
		saveCommonBillEntryFields(billEntryBean, billMasterEntity);
		setBillMasterDetails(billEntryBean, billMasterEntity);
		billMasterEntity.setCheckerAuthorization(MainetConstants.CommonConstants.CHAR_N);

		
		
		
		
		if (billEntryBean.getAuthorizationMode().equals(MainetConstants.AccountBillEntry.AUTH)) {
			if (billEntryBean.getActualTaskId() == null) {
				billMasterEntity.setCheckerAuthorization(billEntryBean.getCheckerAuthorization());
				billMasterEntity.setCheckerUser(billEntryBean.getCreatedBy());
				Objects.requireNonNull(billEntryBean.getAuthorizationDate(),
						ApplicationSession.getInstance().getMessage("account.bill.entry.service.authorizationdate")
								+ billEntryBean);
				billMasterEntity.setUpdatedBy(billEntryBean.getCreatedBy());
				billMasterEntity.setUpdatedDate(billEntryBean.getUpdatedDate());
				// billMasterEntity.setUpdatedDate(Utility.stringToDate(billEntryBean.getAuthorizationDate()));
				billMasterEntity.setLgIpMacAddressUpdated(ipMacAddress);
			}
			billMasterEntity.setCheckerDate(Utility.stringToDate(billEntryBean.getAuthorizationDate()));
			billMasterEntity.setCheckerRemarks(billEntryBean.getCheckerRemarks());
			billMasterEntity.setBillEntryDate(billEntryBean.getBillEntryDateDt());
		}

		if (billEntryBean.getCheckerAuthorization() != null) {
			if (!(billEntryBean.getCheckerAuthorization().equals(MainetConstants.CommonConstants.CHAR_N)
					|| billEntryBean.getCheckerAuthorization().equals(MainetConstants.AccountBillEntry.R))
					&& billEntryBean.getAuthorizationMode().equals(MainetConstants.AccountBillEntry.AUTH)) {
				billMasterEntity.setPayStatus(MainetConstants.CommonConstants.CHAR_N);
			}
		}
		// billEntryBean.setBillNo(billNo);
		saveBillExpenditureDetails(billEntryBean, billMasterEntity);
		saveBillDeductionDetails(billEntryBean, billMasterEntity);
		saveBillMeasurmentDetails(billEntryBean, billMasterEntity);
		billMasterEntity.setFieldId(billEntryBean.getFieldId());
		final Department department = new Department();
		department.setDpDeptid(billEntryBean.getDepartmentId());
		billMasterEntity.setDepartmentId(department);

		AccountBillEntryMasterEnitity newBillMasterEntity = billEntryServiceJpaRepository.save(billMasterEntity);

		if (billEntryBean.getId() != null && billEntryBean.getId() > 0L) {
			String expIds = billEntryBean.getDeletedExpIds();
			if (expIds != null && !expIds.isEmpty()) {
				String array[] = expIds.split(MainetConstants.operator.COMMA);
				for (String id : array) {
					billEntryServiceJpaRepository.deleteByExpenditureDetails(Long.valueOf(id));
				}
			}
			String ids = billEntryBean.getDeletedDedIds();
			if (ids != null && !ids.isEmpty()) {
				String array[] = ids.split(MainetConstants.operator.COMMA);
				for (String id : array) {
					billEntryServiceJpaRepository.deleteByDeductionDetails(Long.valueOf(id));
				}
			}
			List<Long> removeMbIdList = null;
			String mbIds = billEntryBean.getDeletedMbIds();
			if (mbIds != null && !mbIds.isEmpty()) {
				removeMbIdList = new ArrayList<>();
				String array[] = mbIds.split(MainetConstants.operator.COMMA);
				for (String id : array) {
				 removeMbIdList.add(Long.valueOf(id));
				}
				billEntryServiceJpaRepository.deleteByMeasurementDetails(removeMbIdList);
			}

		}

		if ((billEntryBean.getId() != null) && (billEntryBean.getId() > 0L)) {

			billHistEntity = new AccountBillEntryMasterHistEnitity();
			billHistEntity.sethStatus(MainetConstants.CommonConstants.CHAR_U);
			try {
				auditService.createHistory(newBillMasterEntity, billHistEntity);
			} catch (Exception ex) {
				LOGGER.error("Could not make audit entry for " + newBillMasterEntity, ex);
			}
			List<AccountBillEntryExpenditureDetEntity> expList = newBillMasterEntity.getExpenditureDetailList();
			for (AccountBillEntryExpenditureDetEntity accountBillEntryExpenditureDetEntity : expList) {
				billExpHistEntity = new AccountBillEntryExpenditureDetHistEntity();
				billExpHistEntity.sethStatus(MainetConstants.CommonConstants.CHAR_U);
				try {
					auditService.createHistory(accountBillEntryExpenditureDetEntity, billExpHistEntity);
				} catch (Exception ex) {
					LOGGER.error("Could not make audit entry for " + accountBillEntryExpenditureDetEntity, ex);
				}
			}

			List<AccountBillEntryDeductionDetEntity> dedList = newBillMasterEntity.getDeductionDetailList();
			if (dedList != null && !dedList.isEmpty()) {
				for (AccountBillEntryDeductionDetEntity accountBillEntryDeductionDetEntity : dedList) {
					billDedHistEntity = new AccountBillEntryDeductionDetHistEntity();
					billDedHistEntity.sethStatus(MainetConstants.CommonConstants.CHAR_U);
					try {
						auditService.createHistory(accountBillEntryDeductionDetEntity, billDedHistEntity);
					} catch (Exception ex) {
						LOGGER.error("Could not make audit entry for " + accountBillEntryDeductionDetEntity, ex);
					}
				}
			}
		} else {
			billHistEntity = new AccountBillEntryMasterHistEnitity();
			billHistEntity.sethStatus(MainetConstants.CommonConstants.CHAR_C);
			try {
				auditService.createHistory(newBillMasterEntity, billHistEntity);
			} catch (Exception ex) {
				LOGGER.error("Could not make audit entry for " + newBillMasterEntity, ex);
			}

			List<AccountBillEntryExpenditureDetEntity> expList = newBillMasterEntity.getExpenditureDetailList();
			for (AccountBillEntryExpenditureDetEntity accountBillEntryExpenditureDetEntity : expList) {
				billExpHistEntity = new AccountBillEntryExpenditureDetHistEntity();
				billExpHistEntity.sethStatus(MainetConstants.CommonConstants.CHAR_C);
				try {
					auditService.createHistory(accountBillEntryExpenditureDetEntity, billExpHistEntity);
				} catch (Exception ex) {
					LOGGER.error("Could not make audit entry for " + accountBillEntryExpenditureDetEntity, ex);
				}
			}

			List<AccountBillEntryDeductionDetEntity> dedList = newBillMasterEntity.getDeductionDetailList();
			if (dedList != null && !dedList.isEmpty()) {
				for (AccountBillEntryDeductionDetEntity accountBillEntryDeductionDetEntity : dedList) {
					billDedHistEntity = new AccountBillEntryDeductionDetHistEntity();
					billDedHistEntity.sethStatus(MainetConstants.CommonConstants.CHAR_C);
					try {
						auditService.createHistory(accountBillEntryDeductionDetEntity, billDedHistEntity);
					} catch (Exception ex) {
						LOGGER.error("Could not make audit entry for " + accountBillEntryDeductionDetEntity, ex);
					}
				}
			}
		}

		if (billEntryBean.getActualTaskId() == null) {
			if (billEntryBean.getCheckerAuthorization() != null) {
				if (!(billEntryBean.getCheckerAuthorization().equals(MainetConstants.CommonConstants.CHAR_N)
						|| billEntryBean.getCheckerAuthorization().equals(MainetConstants.AccountBillEntry.R))
						&& billEntryBean.getAuthorizationMode().equals(MainetConstants.AccountBillEntry.AUTH)) {
					postInvoiceBillEntry(billEntryBean);
				}
			}
		}

		if ((billEntryBean.getAdvanceFlag() != null) && !billEntryBean.getAdvanceFlag().isEmpty()) {
			if (billEntryBean.getId() == null) {
				final Long advId = billEntryBean.getPrAdvEntryId();
				BigDecimal balanceAmount = null;
				/*for (final AccountBillEntryExpenditureDetBean billEntryExpenditureDet : billEntryBean
						.getExpenditureDetailList()) {
					final BigDecimal feeAmount = billEntryExpenditureDet.getBillChargesAmount();
					final BigDecimal balAmt = billEntryBean.getBalAmount();
					balanceAmount = balAmt.subtract(feeAmount);
				}*/
				for (final AccountBillEntryDeductionDetBean billEntryExpenditureDet : billEntryBean
						.getDeductionDetailList()) {
					final BigDecimal feeAmount =billEntryExpenditureDet.getDeductionAmount()==null?BigDecimal.ZERO:billEntryExpenditureDet.getDeductionAmount();
					final BigDecimal balAmt = billEntryBean.getBalAmount();
					balanceAmount = balAmt.subtract(feeAmount);
				}
				advanceEntryRepository.updateAdvanceBalanceAmountInAdvanceTable(advId, balanceAmount,
						billEntryBean.getOrgId());
			}
		}

		if ((billEntryBean.getDepositFlag() != null) && !billEntryBean.getDepositFlag().isEmpty()) {
			if (billEntryBean.getDepositFlag().equals(MainetConstants.MENU.Y)) {
				final Long depId = billEntryBean.getDepId();
				final Long statusId = CommonMasterUtility.lookUpIdByLookUpCodeAndPrefix(
						PrefixConstants.AccountBillEntry.RCI, PrefixConstants.NewWaterServiceConstants.RDC,
						billEntryBean.getOrgId());
				accountDepositJparepository.updateDepositBalanceAmountInDepositTable(depId, statusId,
						billEntryBean.getOrgId());
			}
		}
		return billMasterEntity;
	}

	private void setBillMasterDetails(final AccountBillEntryMasterBean billEntryBean,
			final AccountBillEntryMasterEnitity billMasterEntity) {
		final TbComparamDetEntity billTypeId = new TbComparamDetEntity();
		final Department departmentId = new Department();
		final TbAcVendormasterEntity vendorId = new TbAcVendormasterEntity();
		final AccountFundMasterEntity fundId = new AccountFundMasterEntity();
		billTypeId.setCpdId(billEntryBean.getBillTypeId());
		billMasterEntity.setBillTypeId(billTypeId);
		billMasterEntity.setBillEntryDate(billEntryBean.getBillEntryDateDt());
		departmentId.setDpDeptid(billEntryBean.getDepartmentId());
		billMasterEntity.setDepartmentId(departmentId);
		vendorId.setVmVendorid(billEntryBean.getVendorId());
		if(billEntryBean.getFundId() != null && billEntryBean.getFundId()>0L)
		{fundId.setFundId(billEntryBean.getFundId());
		billMasterEntity.setFundId(fundId);
		}
		billMasterEntity.setVendorId(vendorId);
		TbAcVendormaster vendor = vendorMasterService.findById(billEntryBean.getVendorId(), billEntryBean.getOrgId());
		billMasterEntity.setVendorName(vendor.getVmVendorname());
		billMasterEntity.setInvoiceNumber(billEntryBean.getInvoiceNumber());
		if (billEntryBean.getInvoiceDate() != null) {
			billMasterEntity
					.setInvoiceDate(UtilityService.convertStringDateToDateFormat(billEntryBean.getInvoiceDate()));
		}
		billMasterEntity.setInvoiceValue(billEntryBean.getInvoiceValue());
		billMasterEntity.setWorkOrPurchaseOrderNumber(billEntryBean.getWorkOrPurchaseOrderNumber());
		if (billEntryBean.getWorkOrPurchaseOrderDate() != null) {
			billMasterEntity.setWorkOrPurchaseOrderDate(
					UtilityService.convertStringDateToDateFormat(billEntryBean.getWorkOrPurchaseOrderDate()));
		}
		billMasterEntity.setResolutionNumber(billEntryBean.getResolutionNumber());
		if (billEntryBean.getResolutionDate() != null) {
			billMasterEntity
					.setResolutionDate(UtilityService.convertStringDateToDateFormat(billEntryBean.getResolutionDate()));
		}

		billMasterEntity.setNarration(billEntryBean.getNarration());
		billMasterEntity.setBillTotalAmount(billEntryBean.getBillTotalAmount());
		billMasterEntity.setBalanceAmount(billEntryBean.getNetPayable());
		billMasterEntity.setBmTaxableValue(billEntryBean.getBmTaxableValue());
	}

	private void saveBillExpenditureDetails(final AccountBillEntryMasterBean billEntryBean,
			final AccountBillEntryMasterEnitity billMasterEntity) {

		final List<AccountBillEntryExpenditureDetBean> expDetList = billEntryBean.getExpenditureDetailList();
		AccountBillEntryExpenditureDetEntity billExpDetEntity = null;
		AccountBudgetCodeEntity budgetCode = null;
		final List<AccountBillEntryExpenditureDetEntity> billExpDetEntityList = new ArrayList<>();
		if ((expDetList != null) && !expDetList.isEmpty()) {
			for (final AccountBillEntryExpenditureDetBean expDetBean : expDetList) {
				billExpDetEntity = new AccountBillEntryExpenditureDetEntity();
				billExpDetEntity.setSacHeadId(expDetBean.getBudgetCodeId());
				budgetCode = findBudgetHeadIdBySacHeadId(expDetBean.getBudgetCodeId(), billEntryBean.getOrgId());
				if (budgetCode != null) {
					budgetCode.setPrBudgetCodeid(budgetCode.getprBudgetCodeid());
					billExpDetEntity.setBudgetCodeId(budgetCode);
				} else {
					LOGGER.error("No Budget Head defined or map against Account Head[sacHeadId="
							+ expDetBean.getBudgetCodeId() + "]");
				}
				billExpDetEntity.setActualAmount(expDetBean.getActualAmount());
				if (expDetBean.getBillChargesAmount() != null) {
					billExpDetEntity.setBillChargesAmount(expDetBean.getBillChargesAmount());
				}
				if (expDetBean.getDisallowedAmount() != null) {
					billExpDetEntity.setDisallowedAmount(expDetBean.getDisallowedAmount());
					billExpDetEntity.setDisallowedRemark(billEntryBean.getDisallowedRemark());
				} else {
					billExpDetEntity.setDisallowedAmount(
							expDetBean.getActualAmount().subtract(expDetBean.getBillChargesAmount()));
					billExpDetEntity.setDisallowedRemark(billEntryBean.getDisallowedRemark());
				}
				if ((expDetBean.getId() != null) && (expDetBean.getId() > 0l)) {
					billExpDetEntity.setId(expDetBean.getId());
				}
				// updateProjectedExpenditureBalanceAmount(expDetBean);
				saveCommonBillExpenditureFields(billEntryBean, billExpDetEntity);
				billExpDetEntity.setBillMasterId(billMasterEntity);
				billExpDetEntityList.add(billExpDetEntity);
			}
		}
		billMasterEntity.setExpenditureDetailList(billExpDetEntityList);
	}

	@Transactional
	private void updateProjectedExpenditureBalanceAmount(final AccountBillEntryExpenditureDetBean expDetBean) {
		final Long projectedExpId = expDetBean.getProjectedExpenditureId();
		final BigDecimal newBalanceAmount = expDetBean.getNewBalanceAmount();
		billEntryServiceJpaRepository.updateProjectedExpenditureBalanceAmount(projectedExpId, newBalanceAmount);
	}

	@Transactional(readOnly = true)
	private AccountBudgetCodeEntity findBudgetHeadIdBySacHeadId(final Long sacHeadId, final Long orgId) {
		AccountBudgetCodeEntity budgetCode = null;
		try {
			budgetCode = budgetHeadRepository.findBudgetHeadIdBySacHeadId(sacHeadId, orgId);
		} catch (final NonUniqueResultException ex) {
			LOGGER.error("duplicate budget Head map against Account Head [sacHeadId=" + sacHeadId, ex);
			throw new IllegalArgumentException("duplicate budget Head map against Account Head [sacHeadId=" + sacHeadId,
					ex);
		}
		return budgetCode;
	}

	private void saveBillDeductionDetails(final AccountBillEntryMasterBean billEntryBean,
			final AccountBillEntryMasterEnitity billMasterEntity) throws ParseException {

		AccountBillEntryDeductionDetEntity billDedDetEntity = null;
		final List<AccountBillEntryDeductionDetBean> deductionDetList = billEntryBean.getDeductionDetailList();
		AccountBudgetCodeEntity budgetCode = null;
		final List<AccountBillEntryDeductionDetEntity> billDedDetEntityList = new ArrayList<>();
		if ((deductionDetList != null) && !deductionDetList.isEmpty()) {
			if (deductionDetList.get(0).getBudgetCodeId() != null) {
				if ((deductionDetList != null) && !deductionDetList.isEmpty()) {
					for (final AccountBillEntryDeductionDetBean dedDetBean : deductionDetList) {
						billDedDetEntity = new AccountBillEntryDeductionDetEntity();
						billDedDetEntity.setSacHeadId(dedDetBean.getBudgetCodeId());
						if (dedDetBean.getBchId() != null) {
							billDedDetEntity.setBchId(dedDetBean.getBchId());
						}
						budgetCode = findBudgetHeadIdBySacHeadId(dedDetBean.getBudgetCodeId(),
								billEntryBean.getOrgId());
						if (budgetCode != null) {
							budgetCode.setPrBudgetCodeid(budgetCode.getprBudgetCodeid());
							billDedDetEntity.setBudgetCodeId(budgetCode);
						} else {
							LOGGER.error("No Budget Head defined or map against Account Head[sacHeadId="
									+ dedDetBean.getBudgetCodeId() + "]");
						}
						billDedDetEntity.setDeductionRate(dedDetBean.getDeductionRate());
						billDedDetEntity.setDeductionAmount(dedDetBean.getDeductionAmount());
						billDedDetEntity.setDeductionBalAmt(dedDetBean.getDeductionAmount());
						billDedDetEntity.setRaTaxFact(dedDetBean.getRaTaxFact());
						billDedDetEntity.setRaTaxPercent(dedDetBean.getRaTaxPercent());
						if ((dedDetBean.getId() != null) && (dedDetBean.getId() > 0l)) {
							billDedDetEntity.setId(dedDetBean.getId());
						}
						saveCommonDeductionFields(billEntryBean, billDedDetEntity);
						billDedDetEntity.setBillMasterId(billMasterEntity);
						billDedDetEntityList.add(billDedDetEntity);
						if (billEntryBean.getCheckerAuthorization() != null
								&& billEntryBean.getActualTaskId() == null) {
							depositEntry(billEntryBean, dedDetBean);
						}
						// This condition is not required because even maker checker case at the authorization only we need to save deposit details
						/*else if (billEntryBean.getIsMakerChecker() != null) {
							if (billEntryBean.getIsMakerChecker().equals(MainetConstants.Y_FLAG)
									&& billEntryBean.getActualTaskId() == null) {
								depositEntry(billEntryBean, dedDetBean);
							}
						}*/
					}
				}
			}
		}

		billMasterEntity.setDeductionDetailList(billDedDetEntityList);
	}
	
	
	private void saveBillMeasurmentDetails(final AccountBillEntryMasterBean billEntryBean,
			final AccountBillEntryMasterEnitity billMasterEntity) throws ParseException {

		AccountBillEntryMeasurementDetEntity billMbDetEntity = null;
		final List<AccountBillEntryMeasurementDetBean> measurementDetList = billEntryBean.getMeasurementDetailList();
		final List<AccountBillEntryMeasurementDetEntity> billMbDetEntityList = new ArrayList<>();
		if ((measurementDetList != null) && !measurementDetList.isEmpty()) {
			for (final AccountBillEntryMeasurementDetBean mbDetBean : measurementDetList) {
				billMbDetEntity = new AccountBillEntryMeasurementDetEntity();
				BeanUtils.copyProperties(mbDetBean, billMbDetEntity);
				
					billMbDetEntity.setCreatedBy(billEntryBean.getCreatedBy());
					billMbDetEntity.setCreatedDate(billEntryBean.getCreatedDate());
					billMbDetEntity.setLgIpMacAddress(billEntryBean.getLgIpMacAddress());
					if (mbDetBean.getMbId() != null) {
					billMbDetEntity.setUpdatedBy(billEntryBean.getUpdatedBy());
					billMbDetEntity.setUpdatedDate(billEntryBean.getUpdatedDate());
					billMbDetEntity.setLgIpMacAddressUpdated(billEntryBean.getLgIpMacAddressUpdated());
				}
				billMbDetEntity.setOrgid(billEntryBean.getOrgId());
				billMbDetEntity.setBillMasterId(billMasterEntity);
				if(StringUtils.isNotBlank(mbDetBean.getMbItemDesc()))
				billMbDetEntityList.add(billMbDetEntity);
			}
		}
		billMasterEntity.setMeasuremetDetailList(billMbDetEntityList);
	}

	private void depositEntry(final AccountBillEntryMasterBean billEntryBean,
			final AccountBillEntryDeductionDetBean deductionBean) throws ParseException {

		/*
		 * CommonMasterUtility.lookUpIdByLookUpCodeAndPrefix(PrefixConstants.
		 * AccountBillEntry.DP, PrefixConstants.TbAcVendormaster.SAM,
		 * billEntryBean.getOrgId());
		 */
		Long accountTypeId = CommonMasterUtility.lookUpIdByLookUpCodeAndPrefix(PrefixConstants.AccountBillEntry.DP,
				PrefixConstants.TbAcVendormaster.SAM, billEntryBean.getOrgId());
		final List<Long> objList = budgetHeadRepository.getMappingForDeposit(billEntryBean.getOrgId(), accountTypeId,
				deductionBean.getBudgetCodeId());

		if ((objList != null) && !objList.isEmpty()) {
			for (final Long obj : objList) {
				if (obj != null) {
					final List<Long> budgetCodeIdList = new ArrayList<>();
					final AccountDepositBean depositBean = new AccountDepositBean();
					depositBean.setDepReceiptno(billEntryBean.getBillNo());
					depositBean
							.setDepEntryDate(UtilityService.convertDateToDDMMYYYY(billEntryBean.getBillEntryDateDt()));
					depositBean.setDepAmount(deductionBean.getDeductionAmount());
					depositBean.setDepRefundBal(deductionBean.getDeductionAmount());
					depositBean.setDepReceiptdt(billEntryBean.getBillEntryDateDt());
					depositBean.setVmVendorid(billEntryBean.getVendorId());
					depositBean.setDepNarration(billEntryBean.getNarration());
					depositBean.setDepReceivedfrom(billEntryBean.getVendorDesc());
					depositBean.setCpdDepositType((Long) obj);
					/*Defect #95457*/
					/*depositBean.setDpDeptid(departmentDAO.getDepartmentIdByDeptCode(
							MainetConstants.RECEIPT_MASTER.Module, PrefixConstants.STATUS_ACTIVE_PREFIX));*/
					depositBean.setDpDeptid(billEntryBean.getDepartmentId());
					depositBean.setOrgid(billEntryBean.getOrgId());
					depositBean.setDmFlag(AccountConstants.S.toString());
					final Long sourceTypeId = CommonMasterUtility.lookUpIdByLookUpCodeAndPrefix(
							PrefixConstants.AccountBillEntry.BP, PrefixConstants.AccountBillEntry.TOS,
							billEntryBean.getOrgId());
					depositBean.setCpdSourceType(sourceTypeId);
					depositBean.setCpdStatus(
							CommonMasterUtility.lookUpIdByLookUpCodeAndPrefix(PrefixConstants.AccountBillEntry.DO,
									PrefixConstants.NewWaterServiceConstants.RDC, billEntryBean.getOrgId()));
					depositBean.setLangId((billEntryBean.getLanguageId()));
					depositBean.setCreatedBy(billEntryBean.getCreatedBy());
					depositBean.setCreatedDate(new Date());
					depositBean.setLgIpMac(billEntryBean.getLgIpMacAddress());
					budgetCodeIdList.add(deductionBean.getBudgetCodeId());
					for (final Long budgetCode : budgetCodeIdList) {
						depositBean.setSacHeadId(budgetCode);
						depositBean.setAdv_del_flag(MainetConstants.CommonConstants.B);
						depositService.create(depositBean);
					}
				}
			}
		}
	}

	private void postInvoiceBillEntry(final AccountBillEntryMasterBean billEntryBean) {

		final VoucherPostDTO postDto = new VoucherPostDTO();
		List<VoucherPostDTO> postDtoList = new ArrayList<>();
		final List<VoucherPostDetailDTO> voucherDetails = new ArrayList<>();
		VoucherPostDetailDTO postDetailDtoCr = null;
		VoucherPostDetailDTO postDetailDtoDr = null;
		VoucherPostDetailDTO postDetailDtoCrVendor = null;
		postDto.setVoucherDate(billEntryBean.getBillEntryDateDt());
		final Long voucherSubTypeId = CommonMasterUtility.lookUpIdByLookUpCodeAndPrefix(AccountConstants.BI.toString(),
				AccountPrefix.TDP.toString(), billEntryBean.getOrgId());
		postDto.setVoucherSubTypeId(voucherSubTypeId);
		// final Long departmentId =
		// departmentService.getDepartmentIdByDeptCode(AccountConstants.AC.toString());
		postDto.setDepartmentId(billEntryBean.getDepartmentId());
		postDto.setVoucherReferenceNo(billEntryBean.getBillNo()); // optional
		postDto.setNarration(billEntryBean.getNarration());
		postDto.setPayerOrPayee(billEntryBean.getVendorDesc());// Vendor name - optional
		postDto.setEntryType(MainetConstants.BILL_MASTER_COMMON.ENTRY_TYPE);
		postDto.setFieldId(billEntryBean.getFieldId());
		postDto.setOrgId(billEntryBean.getOrgId());
		postDto.setCreatedBy(billEntryBean.getCreatedBy());
		postDto.setCreatedDate(billEntryBean.getCreatedDate());
		postDto.setLgIpMac(billEntryBean.getLgIpMacAddress());
		postDto.setBillVouPostingDate(Utility.stringToDate(billEntryBean.getAuthorizationDate())); // ur logic

		/* Credit side */
		final Long crId = CommonMasterUtility.lookUpIdByLookUpCodeAndPrefix(AccountConstants.CR.toString(),
				PrefixConstants.DCR, billEntryBean.getOrgId());
		final List<AccountBillEntryDeductionDetBean> dedDetList = billEntryBean.getDeductionDetailList();
		if ((dedDetList != null) && !dedDetList.isEmpty()) {
			if (dedDetList.get(0).getBudgetCodeId() != null) {
				if ((dedDetList != null) && !dedDetList.isEmpty()) {
					for (final AccountBillEntryDeductionDetBean dedBean : dedDetList) {
						postDetailDtoCr = new VoucherPostDetailDTO();
						postDetailDtoCr.setVoucherAmount(dedBean.getDeductionAmount());
						postDetailDtoCr.setDrCrId(crId); // ur logic
						postDetailDtoCr.setSacHeadId(dedBean.getBudgetCodeId()); // optional
						voucherDetails.add(postDetailDtoCr);
					}
				}
			}
		}

		/* Debit side */
		final Long drId = CommonMasterUtility.lookUpIdByLookUpCodeAndPrefix(AccountConstants.DR.toString(),
				PrefixConstants.DCR, billEntryBean.getOrgId());
		final List<AccountBillEntryExpenditureDetBean> expDetList = billEntryBean.getExpenditureDetailList();
		for (final AccountBillEntryExpenditureDetBean expBean : expDetList) {

			postDetailDtoDr = new VoucherPostDetailDTO();
			postDetailDtoDr.setVoucherAmount(expBean.getBillChargesAmount());
			postDetailDtoDr.setDrCrId(drId);
			postDetailDtoDr.setSacHeadId(expBean.getBudgetCodeId());
			voucherDetails.add(postDetailDtoDr);
		}
		// Vendor credit entry
		postDetailDtoCrVendor = new VoucherPostDetailDTO();

		postDetailDtoCrVendor.setDrCrId(crId);
		postDetailDtoCrVendor.setVoucherAmount(billEntryBean.getNetPayable());
		Long status = CommonMasterUtility.lookUpIdByLookUpCodeAndPrefix(MainetConstants.CommonConstants.ACTIVE,
				AccountPrefix.ACN.toString(), billEntryBean.getOrgId());
		final Long vendorHeadID = billEntryServiceJpaRepository
				.getVendorSacHeadIdByVendorId(billEntryBean.getVendorId(), billEntryBean.getOrgId(), status);
		postDetailDtoCrVendor.setSacHeadId(vendorHeadID);

		if (postDetailDtoCrVendor.getVoucherAmount().compareTo(BigDecimal.ZERO) > 0 )
			voucherDetails.add(postDetailDtoCrVendor);
		postDto.setVoucherDetails(voucherDetails);
		ApplicationSession session = ApplicationSession.getInstance();
		postDtoList.add(postDto);
		String responseValidation = accountVoucherPostService.validateInput(postDtoList);
		AccountVoucherEntryEntity response = null;
		if (responseValidation.isEmpty()) {
			response = accountVoucherPostService.voucherPosting(postDtoList);
			if (response == null) {
				throw new IllegalArgumentException("Voucher Posting failed");
			}
		} else {
			LOGGER.error(session.getMessage("account.voucher.service.posting")
					+ session.getMessage("account.voucher.posting.improper.input") + responseValidation);
			throw new IllegalArgumentException(
					"Voucher Posting failed : proper data is not exist" + responseValidation);
		}
	}

	private void saveCommonBillEntryFields(final AccountBillEntryMasterBean billEntryBean,
			final AccountBillEntryMasterEnitity billMasterEntity) {
		billMasterEntity.setOrgId(billEntryBean.getOrgId());
		billMasterEntity.setCreatedBy(billEntryBean.getCreatedBy());
		billMasterEntity.setCreatedDate(billEntryBean.getCreatedDate());
		billMasterEntity.setLanguageId(billEntryBean.getLanguageId());
		billMasterEntity.setLgIpMacAddress(billEntryBean.getLgIpMacAddress());
		if (billEntryBean.getId() != null) {
			billMasterEntity.setUpdatedBy(billEntryBean.getUpdatedBy());
			billMasterEntity.setUpdatedDate(billEntryBean.getUpdatedDate());
			billMasterEntity.setLgIpMacAddressUpdated(billEntryBean.getLgIpMacAddressUpdated());
		}
	}

	private void saveCommonBillExpenditureFields(final AccountBillEntryMasterBean billEntryBean,
			final AccountBillEntryExpenditureDetEntity billExpDetEntity) {
		billExpDetEntity.setOrgid(billEntryBean.getOrgId());
		billExpDetEntity.setCreatedBy(billEntryBean.getCreatedBy());
		billExpDetEntity.setCreatedDate(billEntryBean.getCreatedDate());
		billExpDetEntity.setLgIpMacAddress(billEntryBean.getLgIpMacAddress());
		if (billEntryBean.getId() != null) {
			billExpDetEntity.setUpdatedBy(billEntryBean.getUpdatedBy());
			billExpDetEntity.setUpdatedDate(billEntryBean.getUpdatedDate());
			billExpDetEntity.setLgIpMacAddressUpdated(billEntryBean.getLgIpMacAddressUpdated());
		}
	}

	private void saveCommonDeductionFields(final AccountBillEntryMasterBean billEntryBean,
			final AccountBillEntryDeductionDetEntity billDedDetEntity) {
		billDedDetEntity.setOrgid(billEntryBean.getOrgId());
		billDedDetEntity.setCreatedBy(billEntryBean.getCreatedBy());
		billDedDetEntity.setCreatedDate(billEntryBean.getCreatedDate());
		billDedDetEntity.setLgIpMacAddress(billEntryBean.getLgIpMacAddress());
		if (billEntryBean.getId() != null) {
			billDedDetEntity.setUpdatedBy(billEntryBean.getUpdatedBy());
			billDedDetEntity.setUpdatedDate(billEntryBean.getUpdatedDate());
			billDedDetEntity.setLgIpMacAddressUpdated(billEntryBean.getLgIpMacAddressUpdated());
		}
	}

	private String generateBillNumber(final Long orgId, String billEntryDate) {

		Long finYearId = financialyearService.getFinanciaYearIdByFromDate(Utility.stringToDate(billEntryDate));
		if (finYearId == null) {
			throw new NullPointerException(GENERATE_BILL_NO_FIN_YEAR_ID);
		}
		final Long billNumber = seqGenFunctionUtility.generateSequenceNo(AccountConstants.AC.toString(), TB_AC_BILL_MAS,
				BM_BILLNO, orgId, MainetConstants.SECONDARY_MASTER.SECONDARY_SEQ_CONTINUE_VALUE, finYearId);
		return billNumber.toString();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.abm.mainetservice.account.service.AccountBillEntryService#
	 * getBillEntryDetails(java.lang.Long)
	 */
	@Transactional(readOnly = true)
	@Override
	public List<AccountBillEntryMasterEnitity> getBillEntryDetailsByOrgId(final Long orgId) {
		return billEntryServiceJpaRepository.getBillEntryDetailsByOrgId(orgId);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.abm.mainetservice.account.service.AccountBillEntryService#
	 * getBillEntryDetails(java.lang.Long, java.lang.String, java.lang.Long,
	 * java.lang.Long)
	 */
	@Transactional(readOnly = true)
	@Override
	public List<AccountBillEntryMasterEnitity> getBillEntryDetails(final Long orgId, final String fromDate,
			final String toDate, final String billNo, final Long billType, final Long vendorId,
			final Long departmentId) {

		return accountBillEntryDao.getBillEntryDetails(orgId, fromDate, toDate, billNo, billType, vendorId,
				departmentId);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.abm.mainetservice.account.service.AccountBillEntryService#
	 * getAllBillEntryData(java.lang.Long)
	 */
	@Transactional(readOnly = true)
	@Override
	public List<AccountBillEntryMasterEnitity> getAllBillEntryData(final Long orgId) {
		return billEntryServiceJpaRepository.getAllBillEntryData(orgId);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.abm.mainetservice.account.service.AccountBillEntryService#
	 * findBillEntryById(java.lang.Long, java.lang.Long)
	 */
	@Transactional(readOnly = true)
	@Override
	public AccountBillEntryMasterEnitity findBillEntryById(final Long orgId, final Long bmId) {
		return billEntryServiceJpaRepository.findBillEntryById(orgId, bmId);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.abm.mainetservice.account.service.AccountBillEntryService#
	 * deleteBillEntryData(java.lang.Long)
	 */
	@Transactional
	@Override
	public void deleteBillEntryData(final Long bmId) {
		billEntryServiceJpaRepository.delete(bmId);
	}

	@Override
	public String isMakerChecker(final Organisation org) {
		final long lookUpId = CommonMasterUtility.lookUpIdByLookUpCodeAndPrefix(AccountConstants.BE.toString(),
				AccountPrefix.AUT.toString(), org.getOrgid());
		if (lookUpId > 0) {
			return MainetConstants.MENU.TRUE;
		} else {
			return MainetConstants.MENU.FALSE;
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.abm.mainet.account.service.AccountBillEntryService#getBillNumbersByOrgId(
	 * java.lang.Long)
	 */
	@Transactional(readOnly = true)
	@Override
	public List<Object[]> getBillNumbers(final Long orgId, final Long billTypeId, final Long vendorId,
			final Date payDate) {
		return billEntryServiceJpaRepository.getBillNumbers(orgId, billTypeId, vendorId, payDate);
	}
	
	@Transactional(readOnly = true)
	@Override
	public List<Object[]> getBillNumbersWithFieldId(final Long orgId, final Long billTypeId, final Long vendorId,
			final Date payDate,final Long fieldId) {
		return billEntryServiceJpaRepository.getBillNumbersWithFieldId(orgId, billTypeId, vendorId, payDate,fieldId);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.abm.mainet.account.service.AccountBillEntryService#getBillDataByBillId(
	 * java.lang.Long, java.lang.Long)
	 */
	@Transactional(readOnly = true)
	@Override
	public List<AccountBillEntryMasterEnitity> getBillDataByBillId(final Long billId, final Long orgId) {
		return billEntryServiceJpaRepository.getBillDataByBillId(billId, orgId);
	}

	/*
	 * ##################### Methods for RESTful web services
	 * ################################
	 */

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.abm.mainet.account.service.AccountBillEntryService#
	 * getBillEntryDetailsForSearch(com.abm.mainet.account.dto.
	 * AccountBillEntryMasterBean)
	 */
	@Transactional(readOnly = true)
	@Override
	public List<VendorBillApprovalDTO> getBillEntryDetailsForSearch(final VendorBillApprovalDTO billApprovalDto) {

		final List<AccountBillEntryMasterEnitity> billDeatilList = accountBillEntryDao.getBillEntryDetails(
				billApprovalDto.getOrgId(), billApprovalDto.getFromDate(), billApprovalDto.getToDate(),
				billApprovalDto.getBillNo(), billApprovalDto.getBillTypeId(), billApprovalDto.getVendorId(),
				billApprovalDto.getDepartmentId());

		VendorBillApprovalDTO approvalDto = null;
		final List<VendorBillApprovalDTO> approvalDtoList = new ArrayList<>();
		if ((billDeatilList != null) && !billDeatilList.isEmpty()) {
			for (final AccountBillEntryMasterEnitity list : billDeatilList) {
				BigDecimal totalDeductionAmount = BigDecimal.ZERO;
				BigDecimal totalDisallowedAmount = BigDecimal.ZERO;
				BigDecimal netPayableAmount = BigDecimal.ZERO;
				approvalDto = new VendorBillApprovalDTO();
				approvalDto.setId(list.getId());
				approvalDto.setBillNo(list.getBillNo());
				approvalDto.setBillAmount(list.getBillTotalAmount());
				final String billAmountStr = CommonMasterUtility.getAmountInIndianCurrency(list.getBillTotalAmount());
				approvalDto.setBillAmountStr(billAmountStr);
				if (list.getCheckerAuthorization() != null) {
					if (list.getCheckerAuthorization().equals(MainetConstants.CommonConstants.CHAR_N)) {
						approvalDto.setAuthorizationStatus(AccountConstants.UNAUTHORIZED.getValue());
					}
					if (list.getCheckerAuthorization().equals(MainetConstants.CommonConstants.CHAR_Y)) {
						approvalDto.setAuthorizationStatus(AccountConstants.AUTHORIZED.getValue());
					}
					if (list.getCheckerAuthorization().equals(MainetConstants.AccountBillEntry.R)) {
						approvalDto.setAuthorizationStatus(AccountConstants.REJECTED.getValue());
					}
				}
				final List<AccountBillEntryExpenditureDetEntity> expDetList = list.getExpenditureDetailList();
				if ((expDetList != null) && !expDetList.isEmpty()) {
					for (final AccountBillEntryExpenditureDetEntity expDetEntity : expDetList) {
						if ((expDetEntity.getDisallowedAmount() != null)
								&& !expDetEntity.getDisallowedAmount().equals(MainetConstants.BLANK)) {
							totalDisallowedAmount = totalDisallowedAmount.add(expDetEntity.getDisallowedAmount());
						}
					}
				}
				final List<AccountBillEntryDeductionDetEntity> deductionList = list.getDeductionDetailList();
				if ((deductionList != null) && !deductionList.isEmpty()) {
					for (final AccountBillEntryDeductionDetEntity dedDetEntity : deductionList) {
						if (dedDetEntity.getDeductionAmount() != null) {
							totalDeductionAmount = totalDeductionAmount.add(dedDetEntity.getDeductionAmount());
							approvalDto.setDeductions(totalDeductionAmount);
							final String deductionsStr = CommonMasterUtility
									.getAmountInIndianCurrency(totalDeductionAmount);
							approvalDto.setDeductionsStr(deductionsStr);
						}
					}
				}
				netPayableAmount = approvalDto.getBillAmount()
						.subtract(totalDisallowedAmount.add(totalDeductionAmount));
				approvalDto.setNetPayables(netPayableAmount);
				final String netPayableStr = CommonMasterUtility.getAmountInIndianCurrency(netPayableAmount);
				approvalDto.setNetPayablesStr(netPayableStr);
				approvalDtoList.add(approvalDto);
			}
		}
		return approvalDtoList;
	}

	@Override
	public String validateBillSearch(final VendorBillApprovalDTO billApprovalDto) {

		final StringBuilder builder = new StringBuilder();
		if ((((billApprovalDto.getFromDate() == null)
				|| (billApprovalDto.getFromDate().equals(StringUtils.EMPTY) && (billApprovalDto.getToDate() == null))
				|| billApprovalDto.getToDate().equals(StringUtils.EMPTY)) && (billApprovalDto.getBillNo() == null))
				|| (billApprovalDto.getBillNo().equals(StringUtils.EMPTY) && (billApprovalDto.getBillTypeId() == null)
						&& (billApprovalDto.getVendorId() == null) && (billApprovalDto.getDepartmentId() == null))) {
			builder.append(EMPTY_SEARCH);
		}
		if (billApprovalDto.getOrgId() == null) {
			builder.append(MainetConstants.AccountBillEntry.ORG_ID);
			builder.append(CANNOT_BE_NULL_EMPTY);
		}
		return builder.toString();
	}

	@Override
	public String validateOrgIdAndLangId(final Long orgId, final Long languagetId) {

		final StringBuilder builder = new StringBuilder();
		if (orgId == null) {
			builder.append(MainetConstants.AccountBillEntry.APPEND_ORG_ID);
		}
		if (languagetId == null) {
			builder.append(MainetConstants.AccountBillEntry.LANG_ID);
		}
		if (!builder.toString().isEmpty()) {
			builder.append(CANNOT_BE_NULL_EMPTY);
		}
		return builder.toString();
	}

	@Override
	public String validateGetDepartments(final Long orgId) {

		final StringBuilder builder = new StringBuilder();
		if (orgId == null) {
			builder.append(MainetConstants.AccountBillEntry.APPEND_ORG_ID);
		}
		if (!builder.toString().isEmpty()) {
			builder.append(CANNOT_BE_NULL_EMPTY);
		}
		return builder.toString();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.abm.mainet.account.service.AccountBillEntryService#validate(com.abm.
	 * mainet.common.integration.dto.WSRequestDTO)
	 */
	@Override
	public String validate(final VendorBillApprovalDTO vendorBillApprovalDto) {
		StringBuilder builder = new StringBuilder();
		// ResponseEntity<?> responseEntity = null;
		if (vendorBillApprovalDto == null) {
			builder.append(AccountConstants.WEB_REQ_DTO_ERR.getValue());
		}
		return builder.toString();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.abm.mainet.account.service.AccountBillEntryService#getRecordForView(com.
	 * abm.mainet.account.dto. AccountBillEntryMasterBean)
	 */
	@Transactional(readOnly = true)
	@Override
	public VendorBillApprovalDTO getRecordForView(final Long orgId, final Long billId) {

		final VendorBillApprovalDTO vendorBillApprovalDto = new VendorBillApprovalDTO();
		String billTypeDesc = null;
		String invoiceValue = null;
		String totalBill = null;
		EmployeeBean empBean = null;
		final AccountBillEntryMasterEnitity billEntity = billEntryServiceJpaRepository.findBillEntryById(orgId, billId);
		vendorBillApprovalDto.setId(billEntity.getId());
		vendorBillApprovalDto.setBillNo(billEntity.getBillNo());
		vendorBillApprovalDto.setBillTypeId(billEntity.getBillTypeId().getCpdId());
		billTypeDesc = CommonMasterUtility.findLookUpDesc(AccountPrefix.ABT.toString(), orgId,
				billEntity.getBillTypeId().getCpdId());
		vendorBillApprovalDto.setBillType(billTypeDesc);
		vendorBillApprovalDto.setBillEntryDate(UtilityService.convertDateToDDMMYYYY(billEntity.getBillEntryDate()));
		vendorBillApprovalDto.setDepartmentId(billEntity.getDepartmentId().getDpDeptid());
		vendorBillApprovalDto.setVendorId(billEntity.getVendorId().getVmVendorid());
		vendorBillApprovalDto.setVendorDesc(billEntity.getVendorName());
		vendorBillApprovalDto.setInvoiceNumber(billEntity.getInvoiceNumber());
		if (billEntity.getInvoiceDate() != null) {
			vendorBillApprovalDto.setInvoiceDate(UtilityService.convertDateToDDMMYYYY(billEntity.getInvoiceDate()));
		}
		vendorBillApprovalDto.setOrderNumber(billEntity.getWorkOrPurchaseOrderNumber());
		if (billEntity.getWorkOrPurchaseOrderDate() != null) {
			vendorBillApprovalDto
					.setOrderDate(UtilityService.convertDateToDDMMYYYY(billEntity.getWorkOrPurchaseOrderDate()));
		}
		vendorBillApprovalDto.setResolutionNumber(billEntity.getResolutionNumber());
		if (billEntity.getResolutionDate() != null) {
			vendorBillApprovalDto
					.setResolutionDate(UtilityService.convertDateToDDMMYYYY(billEntity.getResolutionDate()));
		}
		invoiceValue = CommonMasterUtility.getAmountInIndianCurrency(billEntity.getInvoiceValue());
		vendorBillApprovalDto.setInvoiceAmount(billEntity.getInvoiceValue());
		vendorBillApprovalDto.setInvoiceAmountStr(invoiceValue);
		vendorBillApprovalDto.setNarration(billEntity.getNarration());
		vendorBillApprovalDto.setBillAmount(billEntity.getBillTotalAmount());
		totalBill = CommonMasterUtility.getAmountInIndianCurrency(billEntity.getBillTotalAmount());
		vendorBillApprovalDto.setBillAmountStr(totalBill);
		vendorBillApprovalDto.setCheckerAuthorization(billEntity.getCheckerAuthorization());
		vendorBillApprovalDto.setCheckerRemarks(billEntity.getCheckerRemarks());
		if (billEntity.getCheckerAuthorization().equals(MainetConstants.AccountBillEntry.R)) {
			vendorBillApprovalDto.setCheckerRemarks(billEntity.getCheckerRemarks());
			vendorBillApprovalDto.setAuthorizationStatus(AccountConstants.REJECTED.getValue());
		}
		if (!billEntity.getCheckerAuthorization().equals(MainetConstants.CommonConstants.CHAR_N)) {
			String empName = "";
			empBean = employeeService.findById(billEntity.getCheckerUser());
			if ((empBean.getEmpmname() != null) && !empBean.getEmpmname().isEmpty()) {
				empName = empBean.getEmpname() + " " + empBean.getEmpmname() + " " + empBean.getEmplname();
			} else {
				if (empBean.getEmplname() != null && !empBean.getEmplname().isEmpty()) {
					empName = empBean.getEmpname() + " " + empBean.getEmplname();
				} else {
					empName = empBean.getEmpname();
				}
			}
			vendorBillApprovalDto.setAuthorizerEmployee(empName);
		}
		getBillEntryExpenditureDetails(billEntity, vendorBillApprovalDto);
		getBillEntryDedcutionDetails(billEntity, vendorBillApprovalDto);
		return vendorBillApprovalDto;
	}

	@Override
	public String validateForViewAndEdit(final Long orgId, final Long billId) {

		final StringBuilder builder = new StringBuilder();
		if (orgId == null) {
			builder.append(MainetConstants.AccountBillEntry.ORG);
		}
		if (billId == null) {
			builder.append(MainetConstants.AccountBillEntry.BILL_ID);
		}
		if (!builder.toString().isEmpty()) {
			builder.append(CANNOT_BE_NULL_EMPTY);
		}
		return builder.toString();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.abm.mainet.account.service.AccountBillEntryService#getRecordForEdit(java.
	 * lang.Long, com.abm.mainet.common.domain.Organisation, java.lang.Long)
	 */
	@Transactional(readOnly = true)
	@Override
	public VendorBillApprovalDTO getRecordForEdit(final Long orgId, final Long billId) {

		final VendorBillApprovalDTO vendorBillApprovalDto = new VendorBillApprovalDTO();
		vendorBillApprovalDto.setAuthorizationMode(AccountConstants.AUTHORIZATION_MODE.getValue());
		final AccountBillEntryMasterEnitity billEntity = billEntryServiceJpaRepository.findBillEntryById(orgId, billId);
		vendorBillApprovalDto.setId(billEntity.getId());
		vendorBillApprovalDto.setBillNo(billEntity.getBillNo());
		vendorBillApprovalDto.setBillTypeId(billEntity.getBillTypeId().getCpdId());
		vendorBillApprovalDto.setBillEntryDate(UtilityService.convertDateToDDMMYYYY(billEntity.getBillEntryDate()));
		vendorBillApprovalDto.setDepartmentId(billEntity.getDepartmentId().getDpDeptid());
		vendorBillApprovalDto.setVendorId(billEntity.getVendorId().getVmVendorid());
		vendorBillApprovalDto.setVendorDesc(billEntity.getVendorName());
		vendorBillApprovalDto.setInvoiceNumber(billEntity.getInvoiceNumber());
		if (billEntity.getInvoiceDate() != null) {
			vendorBillApprovalDto.setInvoiceDate(UtilityService.convertDateToDDMMYYYY(billEntity.getInvoiceDate()));
		}
		vendorBillApprovalDto.setOrderNumber(billEntity.getWorkOrPurchaseOrderNumber());
		if (billEntity.getWorkOrPurchaseOrderDate() != null) {
			vendorBillApprovalDto
					.setOrderDate(UtilityService.convertDateToDDMMYYYY(billEntity.getWorkOrPurchaseOrderDate()));
		}
		vendorBillApprovalDto.setResolutionNumber(billEntity.getResolutionNumber());
		if (billEntity.getResolutionDate() != null) {
			vendorBillApprovalDto
					.setResolutionDate(UtilityService.convertDateToDDMMYYYY(billEntity.getResolutionDate()));
		}
		vendorBillApprovalDto.setInvoiceAmount(billEntity.getInvoiceValue());
		vendorBillApprovalDto.setNarration(billEntity.getNarration());
		vendorBillApprovalDto.setBillAmount(billEntity.getBillTotalAmount());
		vendorBillApprovalDto.setCheckerAuthorization(billEntity.getCheckerAuthorization());
		vendorBillApprovalDto.setCheckerRemarks(billEntity.getCheckerRemarks());
		if (billEntity.getCheckerAuthorization().equals(MainetConstants.AccountBillEntry.R)) {
			vendorBillApprovalDto.setCheckerRemarks(billEntity.getCheckerRemarks());
			vendorBillApprovalDto.setAuthorizationStatus(AccountConstants.REJECTED.getValue());
		}
		getBillEntryExpenditureDetails(billEntity, vendorBillApprovalDto);
		getBillEntryDedcutionDetails(billEntity, vendorBillApprovalDto);

		return vendorBillApprovalDto;
	}

	private void getBillEntryExpenditureDetails(final AccountBillEntryMasterEnitity billEntity,
			final VendorBillApprovalDTO vendorBillApprovalDto) {

		final List<AccountBillEntryExpenditureDetEntity> expDetList = billEntity.getExpenditureDetailList();
		List<AccountBudgetProjectedExpenditureBean> projectedExpList = null;
		VendorBillExpDetailDTO expDetDto = null;
		BigDecimal totalSanctionedAmount = BigDecimal.ZERO;
		BigDecimal totalDisallowedAmount = BigDecimal.ZERO;
		final List<VendorBillExpDetailDTO> expDetailDtoList = new ArrayList<>();

		final Map<Long, String> expPacHeadMap = new LinkedHashMap<>();
		final List<String> budgetCodeList = new ArrayList<>();
		if ((expDetList != null) && !expDetList.isEmpty()) {

			final FinancialYear financialYear = financialyearService.getFinanciaYearByDate(new Date());
			final Long finYearId = financialYear.getFaYear();
			projectedExpList = budgetProjectedExpenditureService.findExpenditureDataByFinYearId(billEntity.getOrgId(),
					finYearId);
			if ((projectedExpList != null) && !projectedExpList.isEmpty()) {
				for (final AccountBudgetProjectedExpenditureBean expPac : projectedExpList) {
					final List<Object[]> expenditureList = budgetCodeService
							.getExpenditutreBudgetHeads(billEntity.getOrgId(), expPac.getPrBudgetCodeid());
					final Organisation organisation = new Organisation();
					organisation.setOrgid(billEntity.getOrgId());
					if ((expenditureList != null) && !expenditureList.isEmpty()) {
						for (final Object[] expArray : expenditureList) {
							final LookUp transactionHeadBudgetCode = CommonMasterUtility.getLookUpFromPrefixLookUpValue(
									AccountConstants.BC.getValue(), AccountPrefix.TSH.toString(),
									billEntity.getLanguageId().intValue(), organisation);
							if (transactionHeadBudgetCode.getDefaultVal().equals(MainetConstants.MENU.Y)) {
								expPacHeadMap.put((Long) expArray[0], expArray[1].toString());
							} else {
								expPacHeadMap.put((Long) expArray[0], expArray[1].toString());
							}
						}
					}
					budgetCodeList.add(expPac.getPrExpBudgetCode());
				}
			}
			for (final AccountBillEntryExpenditureDetEntity expDetEntity : expDetList) {
				expDetDto = new VendorBillExpDetailDTO();
				expDetDto.setId(expDetEntity.getId());
				expDetDto.setBudgetCodeId(expDetEntity.getBudgetCodeId().getprBudgetCodeid());
				expDetDto.setSanctionedAmount(expDetEntity.getBillChargesAmount());
				vendorBillApprovalDto.setSanctionedAmountStr(
						CommonMasterUtility.getAmountInIndianCurrency(expDetEntity.getBillChargesAmount()));

				totalSanctionedAmount = totalSanctionedAmount.add(expDetEntity.getBillChargesAmount());
				expDetDto.setAmount(expDetEntity.getActualAmount());
				vendorBillApprovalDto.setActualAmountStr(
						CommonMasterUtility.getAmountInIndianCurrency(expDetEntity.getActualAmount()));
				if ((expDetEntity.getDisallowedAmount() != null)
						&& !expDetEntity.getDisallowedAmount().equals(MainetConstants.BLANK)) {
					expDetDto.setDisallowedAmount(expDetEntity.getDisallowedAmount());
					totalDisallowedAmount = totalDisallowedAmount.add(expDetEntity.getDisallowedAmount());
					vendorBillApprovalDto.setDisallowedRemark(expDetEntity.getDisallowedRemark());
					vendorBillApprovalDto.setDisallowedAmount(totalDisallowedAmount);
					vendorBillApprovalDto.setDisallowedAmountStr(
							CommonMasterUtility.getAmountInIndianCurrency(totalDisallowedAmount));
				} else {
					vendorBillApprovalDto.setDisallowedAmount(totalDisallowedAmount);
					vendorBillApprovalDto.setDisallowedAmountStr(
							CommonMasterUtility.getAmountInIndianCurrency(totalDisallowedAmount));
				}
				expDetailDtoList.add(expDetDto);
			}
			vendorBillApprovalDto.setExpDetListDto(expDetailDtoList);
			vendorBillApprovalDto.setTotalSanctionedAmount(totalSanctionedAmount);
			vendorBillApprovalDto
					.setSanctionedAmountStr(CommonMasterUtility.getAmountInIndianCurrency(totalSanctionedAmount));
		}
	}

	private void getBillEntryDedcutionDetails(final AccountBillEntryMasterEnitity billEntity,
			final VendorBillApprovalDTO vendorBillApprovalDto) {

		final List<AccountBillEntryDeductionDetEntity> dedDetList = billEntity.getDeductionDetailList();
		VendorBillDedDetailDTO dedDetDto = null;
		BigDecimal totalDeductionAmount = BigDecimal.ZERO;
		BigDecimal netPayableAmount = BigDecimal.ZERO;
		final List<VendorBillDedDetailDTO> dedDetDtoList = new ArrayList<>();
		if ((dedDetList != null) && !dedDetList.isEmpty()) {
			for (final AccountBillEntryDeductionDetEntity dedDetEntity : dedDetList) {
				dedDetDto = new VendorBillDedDetailDTO();
				dedDetDto.setId(dedDetEntity.getId());
				dedDetDto.setBudgetCodeId(dedDetEntity.getBudgetCodeId().getPrBudgetCodeid());
				if (dedDetEntity.getDeductionRate() != null) {
					dedDetDto.setRate(new BigDecimal(dedDetEntity.getDeductionRate()));
					dedDetDto.setDeductionAmount(dedDetEntity.getDeductionAmount());
					dedDetDto.setDeductionAmountStr(
							CommonMasterUtility.getAmountInIndianCurrency(dedDetEntity.getDeductionAmount()));
					totalDeductionAmount = totalDeductionAmount.add(dedDetEntity.getDeductionAmount());
				}
				dedDetDtoList.add(dedDetDto);
			}
			vendorBillApprovalDto.setDedDetListDto(dedDetDtoList);
			vendorBillApprovalDto.setDeductions(totalDeductionAmount);
			vendorBillApprovalDto
					.setDedcutionAmountStr(CommonMasterUtility.getAmountInIndianCurrency(totalDeductionAmount));
			netPayableAmount = vendorBillApprovalDto.getBillAmount()
					.subtract(vendorBillApprovalDto.getDisallowedAmount().add(totalDeductionAmount));
			vendorBillApprovalDto.setNetPayableStr(CommonMasterUtility.getAmountInIndianCurrency(netPayableAmount));
		}
		netPayableAmount = vendorBillApprovalDto.getBillAmount()
				.subtract(vendorBillApprovalDto.getDisallowedAmount().add(totalDeductionAmount));
		vendorBillApprovalDto.setNetPayables(netPayableAmount);

	}

	@WebMethod(operationName = "saveBillApproval", action = "urn:SaveBillApproval")
	@Override
	@Transactional(rollbackFor = Exception.class)
	public VendorBillApprovalDTO saveBillApproval(
			final @WebParam(name = "arg0") VendorBillApprovalDTO vendorBillApprovalDto) throws Exception {

		final AccountBillEntryMasterEnitity billMasterEntity = new AccountBillEntryMasterEnitity();
		AccountBillEntryMasterHistEnitity billHistEntity = null;
		AccountBillEntryExpenditureDetHistEntity billExpHistEntity = null;
		AccountBillEntryDeductionDetHistEntity billDedHistEntity = null;

		String billNo = null;
		Organisation org = new Organisation();
		org.setOrgid(vendorBillApprovalDto.getOrgId());
		String makerChecker = isMakerChecker(org);
		ServiceMaster service = serviceMasterService.getServiceByShortName("VB", vendorBillApprovalDto.getOrgId());
		LookUp serviceActiveLookUp = CommonMasterUtility.getNonHierarchicalLookUpObject(service.getSmServActive(), org);
		if ((vendorBillApprovalDto.getId() != null) && (vendorBillApprovalDto.getId() > 0L)) {
			billMasterEntity.setId(vendorBillApprovalDto.getId());
			billMasterEntity.setBillNo(vendorBillApprovalDto.getBillNo());
			billNo = vendorBillApprovalDto.getBillNo();
		} else {
			if(!((StringUtils.equals(serviceActiveLookUp.getLookUpCode(), MainetConstants.FlagI)) && (makerChecker.equals(MainetConstants.BUG_HEAD_OPENING_BALANCE_MASTER.TRUE) || makerChecker.equals(MainetConstants.BUG_HEAD_OPENING_BALANCE_MASTER.FALSE)))) {
				billNo = generateBillNumber(vendorBillApprovalDto.getOrgId(), vendorBillApprovalDto.getBillEntryDate());
				Organisation organisationById = ApplicationContextProvider.getApplicationContext().getBean(IOrganisationService.class).getOrganisationById(vendorBillApprovalDto.getOrgId());
				billMasterEntity.setBillNo(organisationById.getOrgShortNm()+"/HO/AC/" + Utility.getCurrentFinancialYear()+"/"+ billNo);
			}else {
				billNo = generateBillNumber(vendorBillApprovalDto.getOrgId(), vendorBillApprovalDto.getBillEntryDate());
				billMasterEntity.setBillNo(billNo);
			}
			
		}
		
		billMasterEntity.setOrgId(vendorBillApprovalDto.getOrgId());
		billMasterEntity.setCreatedBy(vendorBillApprovalDto.getCreatedBy());
		billMasterEntity.setCreatedDate(Utility.stringToDate(vendorBillApprovalDto.getCreatedDate()));
		billMasterEntity.setLanguageId(vendorBillApprovalDto.getLanguageId());
		billMasterEntity.setLgIpMacAddress(vendorBillApprovalDto.getLgIpMacAddress());
		billMasterEntity.setBillIntRefId(vendorBillApprovalDto.getBillIntRefId());
	
		//#38616 fields are set to the entity those are coming through RA approval request
		if(vendorBillApprovalDto.getInvoiceNumber()!=null) {
			billMasterEntity.setInvoiceNumber(vendorBillApprovalDto.getInvoiceNumber());
		}
		if(vendorBillApprovalDto.getOrderDate()!=null) {
			try {
			billMasterEntity.setWorkOrPurchaseOrderDate(Utility.stringToDate(vendorBillApprovalDto.getOrderDate().replace("-", "/")));}
			catch (Exception e) {
				LOGGER.info("Exception occure while converting String to date "+vendorBillApprovalDto.getOrderDate());
				}
		}
		if(vendorBillApprovalDto.getOrderNumber()!=null) {
			billMasterEntity.setWorkOrPurchaseOrderNumber(vendorBillApprovalDto.getOrderNumber());
		}
		setApprovalMasterDetails(vendorBillApprovalDto, billMasterEntity);
		billMasterEntity.setCheckerAuthorization(MainetConstants.CommonConstants.CHAR_N);
		billMasterEntity.setPayStatus(MainetConstants.CommonConstants.CHAR_N);

		saveBillExpenditureDetails(vendorBillApprovalDto, billMasterEntity);
		saveBillDeductionDetails(vendorBillApprovalDto, billMasterEntity);
		
		if (!((StringUtils.equals(serviceActiveLookUp.getLookUpCode(), MainetConstants.FlagI)) && (makerChecker.equals(MainetConstants.BUG_HEAD_OPENING_BALANCE_MASTER.TRUE) || makerChecker.equals(MainetConstants.BUG_HEAD_OPENING_BALANCE_MASTER.FALSE)))) {
			initializeWorkflowForOtherModules(billMasterEntity, vendorBillApprovalDto,service);
		}
		final TbLocationMas locMas = ApplicationContextProvider.getApplicationContext().getBean(ILocationMasService.class).findById(vendorBillApprovalDto.getFieldId());
		if ((locMas.getLocRevenueWZMappingDto() != null) && !locMas.getLocRevenueWZMappingDto().isEmpty()) {
			billMasterEntity.setFieldId(locMas.getLocRevenueWZMappingDto().get(0).getCodIdRevLevel1());
		}
		vendorBillApprovalDto.setBillNo(billMasterEntity.getBillNo());
		AccountBillEntryMasterEnitity newBillMasterEntity = billEntryServiceJpaRepository.save(billMasterEntity);

		billHistEntity = new AccountBillEntryMasterHistEnitity();
		billHistEntity.sethStatus(MainetConstants.CommonConstants.CHAR_C);
		try {
			auditService.createHistory(newBillMasterEntity, billHistEntity);
		} catch (Exception ex) {
			LOGGER.error("Could not make audit entry for " + newBillMasterEntity, ex);
		}

		List<AccountBillEntryExpenditureDetEntity> expList = newBillMasterEntity.getExpenditureDetailList();
		for (AccountBillEntryExpenditureDetEntity accountBillEntryExpenditureDetEntity : expList) {
			billExpHistEntity = new AccountBillEntryExpenditureDetHistEntity();
			billExpHistEntity.sethStatus(MainetConstants.CommonConstants.CHAR_C);
			try {
				auditService.createHistory(accountBillEntryExpenditureDetEntity, billExpHistEntity);
			} catch (Exception ex) {
				LOGGER.error("Could not make audit entry for " + accountBillEntryExpenditureDetEntity, ex);
			}
		}

		List<AccountBillEntryDeductionDetEntity> dedList = newBillMasterEntity.getDeductionDetailList();
		if (dedList != null && !dedList.isEmpty()) {
			for (AccountBillEntryDeductionDetEntity accountBillEntryDeductionDetEntity : dedList) {
				billDedHistEntity = new AccountBillEntryDeductionDetHistEntity();
				billDedHistEntity.sethStatus(MainetConstants.CommonConstants.CHAR_C);
				try {
					auditService.createHistory(accountBillEntryDeductionDetEntity, billDedHistEntity);
				} catch (Exception ex) {
					LOGGER.error("Could not make audit entry for " + accountBillEntryDeductionDetEntity, ex);
				}
			}
		}
		
		

		vendorBillApprovalDto.setSalaryBillExitFlag(MainetConstants.Y_FLAG);
		return vendorBillApprovalDto;
	}

	/**
	 * @param vendorBillApprovalDto
	 *//*
		 * private void postInvoiceBillEntry(final VendorBillApprovalDTO
		 * vendorBillApprovalDto) { final VoucherPostDTO postDto = new VoucherPostDTO();
		 * List<VoucherPostDTO> postDtoList = new ArrayList<>(); final
		 * List<VoucherPostDetailDTO> voucherDetails = new ArrayList<>();
		 * VoucherPostDetailDTO postDetailDtoCr = null; VoucherPostDetailDTO
		 * postDetailDtoDr = null; VoucherPostDetailDTO postDetailDtoCrVendor = null;
		 * postDto.setVoucherDate(UtilityService.convertStringDateToDateFormat(
		 * vendorBillApprovalDto.getBillEntryDate()));
		 * postDto.setVoucherType(AccountConstants.JV.toString()); final Long
		 * voucherSubTypeId =
		 * CommonMasterUtility.lookUpIdByLookUpCodeAndPrefix(AccountConstants.BI.
		 * toString(), AccountPrefix.TDP.toString(), vendorBillApprovalDto.getOrgId());
		 * postDto.setVoucherSubTypeId(voucherSubTypeId); final Long departmentId =
		 * departmentService.getDepartmentIdByDeptCode(AccountConstants.AC.toString());
		 * postDto.setDepartmentId(departmentId);
		 * postDto.setVoucherReferenceNo(vendorBillApprovalDto.getBillNo());
		 * postDto.setNarration(vendorBillApprovalDto.getNarration());
		 * postDto.setPayerOrPayee(vendorBillApprovalDto.getVendorDesc());// Vendor name
		 * final Long fieldId =
		 * locationMasService.getcodIdRevLevel1ByLocId(vendorBillApprovalDto.getFieldId(
		 * ), vendorBillApprovalDto.getOrgId()); postDto.setFieldId(fieldId);
		 * postDto.setOrgId(vendorBillApprovalDto.getOrgId());
		 * postDto.setCreatedBy(vendorBillApprovalDto.getCreatedBy());
		 * postDto.setCreatedDate(new Date());
		 * postDto.setAuthFlag(MainetConstants.MENU.Y);
		 * postDto.setLangId(Integer.valueOf(vendorBillApprovalDto.getLanguageId().
		 * toString()).intValue());
		 * postDto.setLgIpMac(vendorBillApprovalDto.getLgIpMacAddress());
		 * postDto.setTemplateType(AccountConstants.PN.toString()); Credit side final
		 * Long crId =
		 * CommonMasterUtility.lookUpIdByLookUpCodeAndPrefix(AccountConstants.CR.
		 * toString(), PrefixConstants.DCR, vendorBillApprovalDto.getOrgId()); final
		 * List<VendorBillDedDetailDTO> dedDetList =
		 * vendorBillApprovalDto.getDedDetListDto(); if
		 * (dedDetList.get(0).getBudgetCodeId() != null) { if ((dedDetList != null) &&
		 * !dedDetList.isEmpty()) { for (final VendorBillDedDetailDTO dedDto :
		 * dedDetList) { postDetailDtoCr = new VoucherPostDetailDTO();
		 * postDetailDtoCr.setVoucherAmount(dedDto.getDeductionAmount());
		 * postDetailDtoCr.setDrCrId(crId);
		 * postDetailDtoCr.setBudgetCodeId(dedDto.getBudgetCodeId());
		 * voucherDetails.add(postDetailDtoCr); } } } Debit side final Long drId =
		 * CommonMasterUtility.lookUpIdByLookUpCodeAndPrefix(AccountConstants.DR.
		 * toString(), PrefixConstants.DCR, vendorBillApprovalDto.getOrgId()); final
		 * List<VendorBillExpDetailDTO> expDetList =
		 * vendorBillApprovalDto.getExpDetListDto(); for (final VendorBillExpDetailDTO
		 * expBean : expDetList) { postDetailDtoDr = new VoucherPostDetailDTO();
		 * postDetailDtoDr.setVoucherAmount(expBean.getSanctionedAmount());
		 * postDetailDtoDr.setDrCrId(drId);
		 * postDetailDtoDr.setBudgetCodeId(expBean.getBudgetCodeId());
		 * voucherDetails.add(postDetailDtoDr); } // Vendor credit entry
		 * postDetailDtoCrVendor = new VoucherPostDetailDTO();
		 * postDetailDtoCrVendor.setDrCrId(crId);
		 * postDetailDtoCrVendor.setVoucherAmount(vendorBillApprovalDto.getNetPayables()
		 * ); Long status = CommonMasterUtility.lookUpIdByLookUpCodeAndPrefix(
		 * MainetConstants.CommonConstants.ACTIVE,
		 * AccountPrefix.ACN.toString(),vendorBillApprovalDto.getOrgId()); final Long
		 * vendorBudgetCodeID = billEntryServiceJpaRepository
		 * .getVendorSacHeadIdByVendorId(vendorBillApprovalDto.getVendorId(),
		 * vendorBillApprovalDto.getOrgId(), status);
		 * postDetailDtoCrVendor.setBudgetCodeId(vendorBudgetCodeID);
		 * voucherDetails.add(postDetailDtoCrVendor);
		 * postDto.setVoucherDetails(voucherDetails); ApplicationSession session =
		 * ApplicationSession.getInstance(); postDtoList.add(postDto); String
		 * responseValidation = accountVoucherPostService.validateInput(postDtoList);
		 * AccountVoucherEntryEntity response = null; if (responseValidation.isEmpty())
		 * { response = accountVoucherPostService.voucherPosting(postDtoList); if
		 * (response == null) { throw new
		 * IllegalArgumentException("Voucher Posting failed"); } } else {
		 * LOGGER.error(session.getMessage("account.voucher.service.posting") +
		 * session.getMessage("account.voucher.posting.improper.input") +
		 * responseValidation); throw new
		 * IllegalArgumentException("Voucher Posting failed : proper data is not exist"+
		 * responseValidation); } }
		 */

	private void setApprovalMasterDetails(final VendorBillApprovalDTO vendorBillApprovalDto,
			final AccountBillEntryMasterEnitity billMasterEntity) {

		final TbComparamDetEntity billTypeId = new TbComparamDetEntity();
		final Department departmentId = new Department();
		final TbAcVendormasterEntity vendorId = new TbAcVendormasterEntity();
		billTypeId.setCpdId(vendorBillApprovalDto.getBillTypeId());
		billMasterEntity.setBillTypeId(billTypeId);
		if (vendorBillApprovalDto.getBillEntryDate() != null && !vendorBillApprovalDto.getBillEntryDate().isEmpty()) {
			billMasterEntity.setBillEntryDate(Utility.stringToDate(vendorBillApprovalDto.getBillEntryDate()));
			billMasterEntity.setInvoiceDate(Utility.stringToDate(vendorBillApprovalDto.getBillEntryDate()));
		} else {
			billMasterEntity.setBillEntryDate(new Date());
		}
		departmentId.setDpDeptid(vendorBillApprovalDto.getDepartmentId());
		billMasterEntity.setDepartmentId(departmentId);
		vendorId.setVmVendorid(vendorBillApprovalDto.getVendorId());
		billMasterEntity.setVendorId(vendorId);
		billMasterEntity.setVendorName(vendorBillApprovalDto.getVendorDesc());
		billMasterEntity.setInvoiceNumber(vendorBillApprovalDto.getInvoiceNumber());
		// billMasterEntity.setInvoiceDate(new Date());
		billMasterEntity.setInvoiceValue(vendorBillApprovalDto.getInvoiceAmount());
		// billMasterEntity.setWorkOrPurchaseOrderNumber(vendorBillApprovalDto.getOrderNumber());
		// billMasterEntity.setWorkOrPurchaseOrderDate(new Date());
		billMasterEntity.setResolutionNumber(vendorBillApprovalDto.getResolutionNumber());
		// billMasterEntity.setResolutionDate(new Date());
		billMasterEntity.setNarration(vendorBillApprovalDto.getNarration());
		billMasterEntity.setBillTotalAmount(vendorBillApprovalDto.getInvoiceAmount());
		// billMasterEntity.setBalanceAmount(vendorBillApprovalDto.getInvoiceAmount());
		if(vendorBillApprovalDto.getFundId()!=null) {
			AccountFundMasterEntity fund=new AccountFundMasterEntity();
			fund.setFundId(vendorBillApprovalDto.getFundId());
			billMasterEntity.setFundId(fund);
		}
		
		if(vendorBillApprovalDto.getFieldId()!=null) {
			billMasterEntity.setFieldId(vendorBillApprovalDto.getFieldId());
		}
	}

	/**
	 * @param vendorBillApprovalDto
	 * @param billMasterEntity
	 */
	private void saveBillExpenditureDetails(final VendorBillApprovalDTO vendorBillApprovalDto,
			final AccountBillEntryMasterEnitity billMasterEntity) {

		final List<VendorBillExpDetailDTO> expDetList = vendorBillApprovalDto.getExpDetListDto();
		AccountBillEntryExpenditureDetEntity billExpDetEntity = null;
		AccountBudgetCodeEntity budgetCode = null;
		final List<AccountBillEntryExpenditureDetEntity> billExpDetEntityList = new ArrayList<>();
		if ((expDetList != null) && !expDetList.isEmpty()) {
			for (final VendorBillExpDetailDTO expDetDto : expDetList) {
				billExpDetEntity = new AccountBillEntryExpenditureDetEntity();
				billExpDetEntity.setSacHeadId(expDetDto.getBudgetCodeId());
				budgetCode = findBudgetHeadIdBySacHeadId(expDetDto.getBudgetCodeId(), vendorBillApprovalDto.getOrgId());
				if (budgetCode != null) {
					budgetCode.setPrBudgetCodeid(budgetCode.getprBudgetCodeid());
					billExpDetEntity.setBudgetCodeId(budgetCode);
				} else {
					LOGGER.error("No Budget Head defined or map against Account Head[sacHeadId="
							+ expDetDto.getBudgetCodeId() + "]");
				}
				// budgetCode = new AccountBudgetCodeEntity();
				// budgetCode.setPrBudgetCodeid(expDetDto.getBudgetCodeId());
				// billExpDetEntity.setBudgetCodeId(budgetCode);
				billExpDetEntity.setActualAmount(expDetDto.getAmount());
				if (expDetDto.getSanctionedAmount() != null) {
					billExpDetEntity.setBillChargesAmount(expDetDto.getSanctionedAmount());
				}
				if (expDetDto.getDisallowedAmount() != null) {
					billExpDetEntity.setDisallowedAmount(expDetDto.getDisallowedAmount());
					billExpDetEntity.setDisallowedRemark(vendorBillApprovalDto.getDisallowedRemark());
				}
				if ((expDetDto.getId() != null) && (expDetDto.getId() > 0l)) {
					billExpDetEntity.setId(expDetDto.getId());
				}
				billExpDetEntity.setOrgid(vendorBillApprovalDto.getOrgId());
				billExpDetEntity.setCreatedBy(vendorBillApprovalDto.getCreatedBy());
				billExpDetEntity.setCreatedDate(Utility.stringToDate(vendorBillApprovalDto.getCreatedDate()));
				billExpDetEntity.setLgIpMacAddress(vendorBillApprovalDto.getLgIpMacAddress());
				billExpDetEntity.setBillMasterId(billMasterEntity);
				billExpDetEntityList.add(billExpDetEntity);
			}
		}
		billMasterEntity.setExpenditureDetailList(billExpDetEntityList);
	}

	/**
	 * @param vendorBillApprovalDto
	 * @param billMasterEntity
	 */
	private void saveBillDeductionDetails(final VendorBillApprovalDTO vendorBillApprovalDto,
			final AccountBillEntryMasterEnitity billMasterEntity) {

		AccountBillEntryDeductionDetEntity billDedDetEntity = null;
		final List<VendorBillDedDetailDTO> deductionDetList = vendorBillApprovalDto.getDedDetListDto();
		AccountBudgetCodeEntity budgetCode = null;
		final List<AccountBillEntryDeductionDetEntity> billDedDetEntityList = new ArrayList<>();
		if ((deductionDetList != null) && !deductionDetList.isEmpty()) {
			if (deductionDetList.get(0).getBudgetCodeId() != null) {
				for (final VendorBillDedDetailDTO dedDetDto : deductionDetList) {
					billDedDetEntity = new AccountBillEntryDeductionDetEntity();
					billDedDetEntity.setSacHeadId(dedDetDto.getBudgetCodeId());
					billDedDetEntity.setBchId(dedDetDto.getBchId());
					budgetCode = findBudgetHeadIdBySacHeadId(dedDetDto.getBudgetCodeId(),
							vendorBillApprovalDto.getOrgId());
					if (budgetCode != null) {
						budgetCode.setPrBudgetCodeid(budgetCode.getprBudgetCodeid());
						billDedDetEntity.setBudgetCodeId(budgetCode);
					} else {
						LOGGER.error("No Budget Head defined or map against Account Head[sacHeadId="
								+ dedDetDto.getBudgetCodeId() + "]");
					}
					// budgetCode = new AccountBudgetCodeEntity();
					// budgetCode.setPrBudgetCodeid(dedDetDto.getBudgetCodeId());
					// billDedDetEntity.setBudgetCodeId(budgetCode);
					// billDedDetEntity.setDeductionRate(Long.valueOf(dedDetDto.getRate().toString()));
					billDedDetEntity.setDeductionAmount(dedDetDto.getDeductionAmount());
					if ((dedDetDto.getId() != null) && (dedDetDto.getId() > 0l)) {
						billDedDetEntity.setId(dedDetDto.getId());
					}
					billDedDetEntity.setOrgid(vendorBillApprovalDto.getOrgId());
					billDedDetEntity.setCreatedBy(vendorBillApprovalDto.getCreatedBy());
					billDedDetEntity.setCreatedDate(Utility.stringToDate(vendorBillApprovalDto.getCreatedDate()));
					billDedDetEntity.setLgIpMacAddress(vendorBillApprovalDto.getLgIpMacAddress());
					billDedDetEntity.setBillMasterId(billMasterEntity);
					billDedDetEntityList.add(billDedDetEntity);
				}
			}
		}
		billMasterEntity.setDeductionDetailList(billDedDetEntityList);
	}

	@Override
	public String validateInputBeforeSave(final VendorBillApprovalDTO dto) {

		final StringBuilder builder = new StringBuilder();
		if (dto.getBillEntryDate() == null || dto.getBillEntryDate().isEmpty()) {
			builder.append(MainetConstants.AccountBillEntry.BILL_ENTRY_DATE);
		}
		if ((dto.getDepartmentId() == null) || (dto.getDepartmentId() == 0l)) {
			builder.append(MainetConstants.AccountBillEntry.DEPARTMENT_ID);
		}
		if ((dto.getFieldId() == null) || (dto.getFieldId() == 0l)) {
			builder.append(MainetConstants.AccountBillEntry.FIELD_ID);
		}
		if ((dto.getCreatedBy() == null) || (dto.getCreatedBy() == 0l)) {
			builder.append(MainetConstants.AccountBillEntry.CREATED_BY);
		}
		if ((dto.getCreatedDate() == null) || dto.getCreatedDate().isEmpty()) {
			builder.append(MainetConstants.AccountBillEntry.CREATED_DATE);
		}
		if ((dto.getLgIpMacAddress() == null) || dto.getLgIpMacAddress().isEmpty()) {
			builder.append(MainetConstants.AccountBillEntry.LG_IP_MAC);
		}
		if (dto.getOrgId() == null) {
			builder.append(MainetConstants.AccountBillEntry.ORG);
		}
		if (dto.getBillTypeId() == null) {
			builder.append(MainetConstants.AccountBillEntry.BILL_TYPE_ID);
		}
		if (dto.getVendorId() == null) {
			builder.append(MainetConstants.AccountBillEntry.VENDOR_ID);
		}
		if (dto.getInvoiceAmount() == null) {
			builder.append(MainetConstants.AccountBillEntry.INVOICE_AMT);
		}
		if ((dto.getNarration() == null) || dto.getNarration().isEmpty()) {
			builder.append(MainetConstants.AccountBillEntry.NARRATION);
		}

		// Validating expenditure details
		if (dto.getExpDetListDto() == null) {
			builder.append(MainetConstants.AccountBillEntry.EXP_LIST_DTO);
		} else {
			int count = 0;
			for (final VendorBillExpDetailDTO detail : dto.getExpDetListDto()) {
				if (detail.getBudgetCodeId() == null) {
					builder.append(MainetConstants.EXP_DET_LIST_DTO + count)
							.append(MainetConstants.AccountBillEntry.BUDGET_CODE_ID);
				}
				if (detail.getAmount() == null) {
					builder.append(MainetConstants.EXP_DET_LIST_DTO + count)
							.append(MainetConstants.AccountBillEntry.AMOUNT);
				}
				if (detail.getSanctionedAmount() == null) {
					builder.append(MainetConstants.EXP_DET_LIST_DTO + count)
							.append(MainetConstants.AccountBillEntry.SANCTION_AMT);
				}
				if (detail.getDisallowedAmount() != null) {
					if ((detail.getDisallowedRemark() == null) && detail.getDisallowedRemark().isEmpty()) {
						builder.append(MainetConstants.EXP_DET_LIST_DTO + count)
								.append(MainetConstants.AccountBillEntry.DIS_ALLOWED_REMARK);
					}
				}
				count++;
			}
		}
		// Validating deduction details
		if (dto.getDedDetListDto() != null) {

			int count = 0;
			for (final VendorBillDedDetailDTO detail : dto.getDedDetListDto()) {
				if (detail.getBudgetCodeId() == null) {
					builder.append(MainetConstants.AccountBillEntry.DED_DET_LIST + count)
							.append(MainetConstants.AccountBillEntry.BUDGET_CODE_ID);
				}
				if (detail.getBchId() == null) {
					builder.append(MainetConstants.AccountBillEntry.DED_DET_LIST + count)
							.append(MainetConstants.AccountBillEntry.BCH_ID);
				}
				if (detail.getDeductionAmount() == null) {
					builder.append(MainetConstants.AccountBillEntry.DED_DET_LIST + count)
							.append(MainetConstants.AccountBillEntry.SANCTION_AMT);
				}
				count++;
			}
		}
		if (!builder.toString().isEmpty()) {
			builder.append(CANNOT_BE_NULL_EMPTY_ZERO);
		}

		return builder.toString();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.abm.mainet.account.service.AccountBillEntryService#
	 * validateOrgIdAndSuperOrgId(java.lang.Long, java.lang.Long)
	 */
	@Override
	public String validateOrgIdAndSuperOrgId(final Long orgId, final Long superOrgId) {
		final StringBuilder builder = new StringBuilder();
		if (orgId == null) {
			builder.append(MainetConstants.AccountBillEntry.APPEND_ORG_ID);
		}
		if (superOrgId == null) {
			builder.append(MainetConstants.AccountBillEntry.SUP_ORG_ID);
		}
		if (!builder.toString().isEmpty()) {
			builder.append(CANNOT_BE_NULL_EMPTY);
		}
		return builder.toString();

	}

	@Override
	@Transactional(rollbackFor = Exception.class)
	public void reverseBillOrInvoice(final List<String> transactionIds, final VoucherReversalDTO dto,
			final long fieldId, final long orgId, int langId, final String ipMacAddress) {

		// do reversal for each
		for (final String primaryKey : transactionIds) {
			final Long billId = Long.parseLong(primaryKey);
			accountBillEntryDao.reverseBillInvoice(dto, billId, orgId, ipMacAddress);
			if (billId != null) {
				List<Long> depositIdList = accountDepositRepository.findAccountDepositEntityByDepReceiptno(dto.getTransactionNo(), orgId);
				if (CollectionUtils.isNotEmpty(depositIdList)) {
					for (Long depositId : depositIdList) {
						String dep_del_flag = MainetConstants.MENU.Y;
						final Long billDepStausId = CommonMasterUtility.lookUpIdByLookUpCodeAndPrefix(
								PrefixConstants.AccountBillEntry.RD, PrefixConstants.NewWaterServiceConstants.RDC,
								orgId);
						accountDepositRepository.updateDep_del_flagOfAccountDepositEntity(depositId, orgId,
								dep_del_flag, billDepStausId);
						}
				}else {
					//This condition is to reverse the refunded bill changing status deleted to open - 32090
						Long depositNo = accountDepositRepository.findAccountDepositEntityByDepBiilId(billId, orgId);
						if(depositNo != null && depositNo > 0) {
							final Long billDepStausId = CommonMasterUtility.lookUpIdByLookUpCodeAndPrefix(
									PrefixConstants.AccountBillEntry.DO, PrefixConstants.NewWaterServiceConstants.RDC, orgId);
							accountDepositRepository.updateDepStatusByBillId(depositNo, billDepStausId, orgId);
						}
				}
				Long dpDeptid = departmentService.getDepartmentIdByDeptCode(AccountConstants.AC.toString());
				Long salBillRefId = billEntryServiceJpaRepository.gettingSalBillRefIdInPropertyTax(billId, dpDeptid,
						orgId);
				if (salBillRefId != null) {
					salaryBillReversalProvisionService.updateSalaryBillReversalDelFlag(salBillRefId);
				}
			}final LookUp lookUp = CommonMasterUtility.getValueFromPrefixLookUp(PrefixConstants.NEC.ADVOCATE,
					MainetConstants.BUG_HEAD_OPENING_BALANCE_MASTER.SLI_PREFIX_VALUE,
					UserSession.getCurrent().getOrganisation());
			String transactDate = dto.getTransactionDate();
			String sliDate = lookUp.getOtherField();
			Date transactionalDate = null;
			Date SLIDate = null;
			try {
				transactionalDate = new SimpleDateFormat("dd/MM/yyyy").parse(transactDate);
				SLIDate = new SimpleDateFormat("dd/MM/yyyy").parse(sliDate);
			} catch (ParseException e) {
				e.printStackTrace();
			}
			if(!Utility.compareDate(transactionalDate,SLIDate)) {
			voucherPostingAgainstBillReversal(dto, billEntryServiceJpaRepository.findBillEntryById(orgId, billId),
					fieldId, orgId, langId, ipMacAddress);
			}
		
		}
	}

	private void voucherPostingAgainstBillReversal(final VoucherReversalDTO reversalDTO,
			final AccountBillEntryMasterEnitity entity, final long fieldId, final long orgId, int langId,
			final String ipMacAddress) {

		final Long voucherTypeId = CommonMasterUtility.lookUpIdByLookUpCodeAndPrefix(
				PrefixConstants.AccountJournalVoucherEntry.JV, AccountPrefix.VOT.toString(), orgId);
		// final Long voucherSubTypeId =
		// CommonMasterUtility.lookUpIdByLookUpCodeAndPrefix(PrefixConstants.AccountBillEntry.BI,
		// AccountPrefix.TDP.toString(), orgId);
		Long voucherSubTypeId = CommonMasterUtility.lookUpIdByLookUpCodeAndPrefix(AccountConstants.RRE.toString(),
				AccountPrefix.TDP.toString(), orgId);
		Long voucherSubTypeId1 = CommonMasterUtility.lookUpIdByLookUpCodeAndPrefix(AccountConstants.DSR.toString(),
				AccountPrefix.TDP.toString(), orgId);
		Long voucherSubTypeId2 = CommonMasterUtility.lookUpIdByLookUpCodeAndPrefix(AccountConstants.RBI.toString(),
				AccountPrefix.TDP.toString(), orgId);
		Long voucherSubTypeId3 = CommonMasterUtility.lookUpIdByLookUpCodeAndPrefix(AccountConstants.RBP.toString(),
				AccountPrefix.TDP.toString(), orgId);
		List<Long> vouSubTypes = new ArrayList<>(0);
		vouSubTypes.add(voucherSubTypeId);
		vouSubTypes.add(voucherSubTypeId1);
		vouSubTypes.add(voucherSubTypeId2);
		vouSubTypes.add(voucherSubTypeId3);
		long entryTypeId = CommonMasterUtility.lookUpIdByLookUpCodeAndPrefix(AccountJournalVoucherEntry.MAN,
				AccountJournalVoucherEntry.VET, orgId);
		if (entryTypeId == 0l) {
			throw new NullPointerException(
					"entryType id not found for for lookUpCode[MAN] from VET Prefix in bill reverse voucher posting.");
		}
		final List<AccountVoucherEntryEntity> acVouEntryEntity = journalVoucherDao.getReceiptReversalVoucherDetails(
				entity.getBillNo().toString(), entity.getBillEntryDate(), voucherTypeId, orgId,
				entity.getDepartmentId().getDpDeptid(), vouSubTypes, entryTypeId);

		if ((acVouEntryEntity == null) || acVouEntryEntity.isEmpty()) {
			throw new NullPointerException("No records found from TB_AC_VOUCHER [billNo="
					+ entity.getBillNo().toString() + ",billEntryDate=" + entity.getCheckerDate() + ",voucherTypeId="
					+ voucherTypeId + ",voucherSubTypeId=" + voucherSubTypeId + ",orgId=" + orgId + "]");
		}

		for (final AccountVoucherEntryEntity acVoucherEntryEntity : acVouEntryEntity) {

			AccountVoucherEntryEntity bean = new AccountVoucherEntryEntity();
			final List<AccountVoucherEntryDetailsEntity> detailsEntity = new ArrayList<>();
			AccountVoucherEntryDetailsEntity postDetailEntityCr = null;
			AccountVoucherEntryDetailsEntity postDetailEntityDr = null;

			// postDto.setVoucherDate(new Date());
			final String voucherType = CommonMasterUtility.findLookUpCode(AccountPrefix.VOT.toString(), orgId,
					acVoucherEntryEntity.getVouTypeCpdId());

			final Organisation org = new Organisation();
			org.setOrgid(orgId);

			if (voucherType.equals(PrefixConstants.AccountJournalVoucherEntry.JV)) {

				final LookUp voucherSubType = CommonMasterUtility.getLookUpFromPrefixLookUpValue(
						PrefixConstants.AccountBillEntry.RBI, PrefixConstants.REV_TYPE_CPD_VALUE, langId, org);
				final Long voucherSubTypeid = voucherSubType.getLookUpId();
				bean.setVouSubtypeCpdId(voucherSubTypeid);
				bean.setVouReferenceNo(entity.getBillNo().toString());// rcptno
				bean.setVouDate(entity.getBillEntryDate());
				bean.setNarration(reversalDTO.getNarration());
				bean.setOrg(orgId);
				bean.setCreatedBy(reversalDTO.getApprovedBy());
				bean.setLmodDate(new Date());
				bean.setLgIpMac(ipMacAddress);
				// bean.setTemplateType(AccountConstants.PN.toString());
				bean.setEntryType(CommonMasterUtility.lookUpIdByLookUpCodeAndPrefix(
						MainetConstants.BILL_MASTER_COMMON.ENTRY_TYPE, AccountPrefix.VET.toString(), orgId));
				bean.setAuthoDate(acVoucherEntryEntity.getAuthoDate());
				bean.setLmodDate(acVoucherEntryEntity.getLmodDate());
				bean.setUpdatedDate(acVoucherEntryEntity.getUpdatedDate());
				bean.setVouPostingDate(acVoucherEntryEntity.getVouPostingDate());
				bean.setVouReferenceNoDate(acVoucherEntryEntity.getVouReferenceNoDate());
				bean.setAuthoFlg(MainetConstants.MENU.Y);
				bean.setAuthoId(acVoucherEntryEntity.getAuthoId());
				bean.setAuthRemark(acVoucherEntryEntity.getAuthRemark());
				bean.setPayerPayee(acVoucherEntryEntity.getPayerPayee());
				bean.setUpdatedby(acVoucherEntryEntity.getUpdatedby());

				String vouNo = generateVoucherNo(PrefixConstants.AccountJournalVoucherEntry.JV, org.getOrgid(),
						entity.getBillEntryDate());
				bean.setVouNo(vouNo);
				bean.setVouTypeCpdId(CommonMasterUtility.lookUpIdByLookUpCodeAndPrefix(
						PrefixConstants.AccountJournalVoucherEntry.JV, AccountPrefix.VOT.toString(), orgId));
				bean.setDpDeptid(acVoucherEntryEntity.getDpDeptid());
				bean.setFieldId(acVoucherEntryEntity.getFieldId());

				for (final AccountVoucherEntryDetailsEntity detEntity : acVoucherEntryEntity.getDetails()) {
					final String drcrCpdId = CommonMasterUtility.findLookUpCode(PrefixConstants.DCR, orgId,
							detEntity.getDrcrCpdId());
					if (drcrCpdId.equals(MainetConstants.BUG_HEAD_OPENING_BALANCE_MASTER.BUG_BAL_TYPE_ASSETS)) {
						// Debit side
						postDetailEntityDr = new AccountVoucherEntryDetailsEntity(); // BeanUtils.copyProperties(postDetailEntityDr,
																						// detEntity);
						// postDetailEntityDr.setVoudetId(detEntity.getVoudetId());
						long drId = CommonMasterUtility.getValueFromPrefixLookUp(
								PrefixConstants.AccountJournalVoucherEntry.DR, PrefixConstants.DCR, org).getLookUpId();
						postDetailEntityDr.setCreatedBy(detEntity.getCreatedBy());
						postDetailEntityDr.setDrcrCpdId(drId);
						postDetailEntityDr.setOrgId(detEntity.getOrgId());
						postDetailEntityDr.setSacHeadId(detEntity.getSacHeadId());
						postDetailEntityDr.setUpdatedBy(detEntity.getUpdatedBy());
						postDetailEntityDr.setBudgetCode(detEntity.getBudgetCode());
						postDetailEntityDr.setFi04D1(detEntity.getFi04D1());
						postDetailEntityDr.setFieldCode(detEntity.getFieldCode());
						postDetailEntityDr.setFunctionCode(detEntity.getFunctionCode());
						postDetailEntityDr.setFundCode(detEntity.getFundCode());
						postDetailEntityDr.setLgIpMac(detEntity.getLgIpMac());
						postDetailEntityDr.setLgIpMacUpd(detEntity.getLgIpMacUpd());
						postDetailEntityDr.setLmodDate(detEntity.getLmodDate());
						postDetailEntityDr.setMaster(bean);
						postDetailEntityDr.setPrimaryHeadCode(detEntity.getPrimaryHeadCode());
						postDetailEntityDr.setSecondaryHeadCode(detEntity.getSecondaryHeadCode());
						postDetailEntityDr.setUpdatedDate(detEntity.getUpdatedDate());
						postDetailEntityDr.setVoudetAmt(detEntity.getVoudetAmt());
						detailsEntity.add(postDetailEntityDr);
					} else if (drcrCpdId.equals(PrefixConstants.AccountJournalVoucherEntry.DR)) {

						long crId = CommonMasterUtility.getValueFromPrefixLookUp(
								MainetConstants.BUG_HEAD_OPENING_BALANCE_MASTER.BUG_BAL_TYPE_ASSETS,
								PrefixConstants.DCR, org).getLookUpId();
						// Credit side
						postDetailEntityCr = new AccountVoucherEntryDetailsEntity();
						// postDetailEntityCr.setVoudetId(detEntity.getVoudetId());
						postDetailEntityCr.setCreatedBy(detEntity.getCreatedBy());
						postDetailEntityCr.setDrcrCpdId(crId);
						postDetailEntityCr.setOrgId(detEntity.getOrgId());
						postDetailEntityCr.setSacHeadId(detEntity.getSacHeadId());
						postDetailEntityCr.setUpdatedBy(detEntity.getUpdatedBy());
						postDetailEntityCr.setBudgetCode(detEntity.getBudgetCode());
						postDetailEntityCr.setFi04D1(detEntity.getFi04D1());
						postDetailEntityCr.setFieldCode(detEntity.getFieldCode());
						postDetailEntityCr.setFunctionCode(detEntity.getFunctionCode());
						postDetailEntityCr.setFundCode(detEntity.getFundCode());
						postDetailEntityCr.setLgIpMac(detEntity.getLgIpMac());
						postDetailEntityCr.setLgIpMacUpd(detEntity.getLgIpMacUpd());
						postDetailEntityCr.setLmodDate(detEntity.getLmodDate());
						postDetailEntityCr.setMaster(bean);
						postDetailEntityCr.setPrimaryHeadCode(detEntity.getPrimaryHeadCode());
						postDetailEntityCr.setSecondaryHeadCode(detEntity.getSecondaryHeadCode());
						postDetailEntityCr.setUpdatedDate(detEntity.getUpdatedDate());
						postDetailEntityCr.setVoudetAmt(detEntity.getVoudetAmt());
						// BeanUtils.copyProperties(postDetailEntityCr, detEntity);
						detailsEntity.add(postDetailEntityCr);
					}
				}
				bean.setDetails(detailsEntity);
				LOGGER.info("voucherPostingService - voucherPosting -JV voucherPostingAgainstBillReversal:" + bean);
				accountVoucherEntryRepository.save(bean);
			}
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.abm.mainet.account.service.AccountBillEntryService#validateViewDetails(
	 * java.lang.Long, java.lang.Long, java.math.BigDecimal)
	 */
	@Override
	public String validateViewDetails(final Long orgId, final Long budgetCodeId, final BigDecimal sanctionedAmount) {

		final StringBuilder builder = new StringBuilder();
		if (orgId == null) {
			builder.append(MainetConstants.AccountBillEntry.APPEND_ORG_ID);
		}
		if (budgetCodeId == null) {
			builder.append(MainetConstants.AccountBillEntry.BUDGET_CODE);
		}
		if (sanctionedAmount == null) {
			builder.append(MainetConstants.AccountBillEntry.SANCTIONED_AMOUNT);
		}
		if (!builder.toString().isEmpty()) {
			builder.append(CANNOT_BE_NULL_EMPTY);
		}
		return builder.toString();
	}

	@Override
	@Transactional(readOnly = true)
	public AccountBillEntryMasterBean populateBillEntryViewData(final AccountBillEntryMasterBean accountBillEntryBean,
			final Long bmId, final Long orgId) {

		final AccountBillEntryMasterEnitity billEntity = findBillEntryById(
				UserSession.getCurrent().getOrganisation().getOrgid(), bmId);
		accountBillEntryBean.setId(billEntity.getId());
		accountBillEntryBean.setBillNo(billEntity.getBillNo());
		accountBillEntryBean.setBillTypeId(billEntity.getBillTypeId().getCpdId());
		final String billTypeDesc = CommonMasterUtility.findLookUpDesc(AccountPrefix.ABT.toString(),
				UserSession.getCurrent().getOrganisation().getOrgid(), billEntity.getBillTypeId().getCpdId());
		accountBillEntryBean.setBillTypeDesc(billTypeDesc);
		final String billTypeCode = CommonMasterUtility.findLookUpCode(AccountPrefix.ABT.toString(),
				UserSession.getCurrent().getOrganisation().getOrgid(), billEntity.getBillTypeId().getCpdId());
		accountBillEntryBean.setBillTypeCode(billTypeCode);
		accountBillEntryBean.setBillEntryDate(UtilityService.convertDateToDDMMYYYY(billEntity.getBillEntryDate()));
		accountBillEntryBean.setDueDate(Utility.dateToString(billEntity.getDueDate()));
		accountBillEntryBean.setDepartmentId(billEntity.getDepartmentId().getDpDeptid());
		accountBillEntryBean.setVendorId(billEntity.getVendorId().getVmVendorid());
		accountBillEntryBean.setVendorDesc(billEntity.getVendorId().getVmVendorname());
		accountBillEntryBean.setInvoiceNumber(billEntity.getInvoiceNumber());
		accountBillEntryBean.setFieldId(billEntity.getFieldId());
		accountBillEntryBean.setBmTaxableValue(billEntity.getBmTaxableValue());
		if(billEntity.getFundId()!= null)
		{
			accountBillEntryBean.setFundId(billEntity.getFundId().getFundId());
		}
		else {
			accountBillEntryBean.setFundId(0l);
		}
		
		
		if (billEntity.getInvoiceDate() != null) {
			accountBillEntryBean.setInvoiceDate(UtilityService.convertDateToDDMMYYYY(billEntity.getInvoiceDate()));
		}
		accountBillEntryBean.setWorkOrPurchaseOrderNumber(billEntity.getWorkOrPurchaseOrderNumber());
		if (billEntity.getWorkOrPurchaseOrderDate() != null) {
			accountBillEntryBean.setWorkOrPurchaseOrderDate(
					UtilityService.convertDateToDDMMYYYY(billEntity.getWorkOrPurchaseOrderDate()));
		}
		accountBillEntryBean.setResolutionNumber(billEntity.getResolutionNumber());
		if (billEntity.getResolutionDate() != null) {
			accountBillEntryBean
					.setResolutionDate(UtilityService.convertDateToDDMMYYYY(billEntity.getResolutionDate()));
		}
		final String invoiceValue = CommonMasterUtility.getAmountInIndianCurrency(billEntity.getInvoiceValue());
		accountBillEntryBean.setInvoiceValue(billEntity.getInvoiceValue());
		accountBillEntryBean.setInvoiceValueStr(invoiceValue);
		accountBillEntryBean.setNarration(billEntity.getNarration());
		accountBillEntryBean.setBillTotalAmount(billEntity.getBillTotalAmount());
		final String totalBill = CommonMasterUtility.getAmountInIndianCurrency(billEntity.getBillTotalAmount());
		accountBillEntryBean.setTotalBillAmountStr(totalBill);
		accountBillEntryBean.setCheckerAuthorization(billEntity.getCheckerAuthorization());
		accountBillEntryBean.setCheckerRemarks(billEntity.getCheckerRemarks());
		if (billEntity.getCheckerAuthorization().equals(MainetConstants.AccountBillEntry.R)) {
			accountBillEntryBean.setCheckerRemarks(billEntity.getCheckerRemarks());
			accountBillEntryBean.setAuthorizationStatus(AccountConstants.REJECTED.getValue());
		}
		EmployeeBean empBean = null;
		if (!billEntity.getCheckerAuthorization().equals(MainetConstants.CommonConstants.CHAR_N)) {
			String empName = "";
			empBean = employeeService.findById(billEntity.getCheckerUser());
			if ((empBean.getEmpmname() != null) && !empBean.getEmpmname().isEmpty()) {
				empName = empBean.getEmpname() + " " + empBean.getEmpmname() + " " + empBean.getEmplname();
			} else {
				if (empBean.getEmplname() != null && !empBean.getEmplname().isEmpty()) {
					empName = empBean.getEmpname() + " " + empBean.getEmplname();
				} else {
					empName = empBean.getEmpname();
				}
			}
			accountBillEntryBean.setAuthorizerEmployee(empName);
		}
		if (billEntity.getCheckerDate() != null) {
			accountBillEntryBean.setAuthorizationDate(Utility.dateToString(billEntity.getCheckerDate()));
		}
		accountBillEntryBean.setExpenditureDetailList(populateExpenditureDetails(billEntity, accountBillEntryBean));
		accountBillEntryBean.setDeductionDetailList(populateDeductionDetails(billEntity, accountBillEntryBean));
		accountBillEntryBean.setMeasurementDetailList(populateMeasurementDetails(billEntity, accountBillEntryBean));
		// accountBillEntryBean.setNetPayable(billEntity.getBalanceAmount());
		BigDecimal netPayableAmount = BigDecimal.ZERO;
		netPayableAmount = accountBillEntryBean.getBillTotalAmount().subtract(
				accountBillEntryBean.getTotalDisallowedAmount().add(accountBillEntryBean.getTotalDeductions()));
		accountBillEntryBean.setNetPayable(netPayableAmount);
		return accountBillEntryBean;
	}

	private List<AccountBillEntryExpenditureDetBean> populateExpenditureDetails(
			final AccountBillEntryMasterEnitity billEntry, final AccountBillEntryMasterBean billEntryDto) {

		final List<AccountBillEntryExpenditureDetBean> expenditureList = new ArrayList<>();
		if ((billEntry.getExpenditureDetailList() == null) || billEntry.getExpenditureDetailList().isEmpty()) {
			throw new NullPointerException(
					"No Expenditure record found from Expenditure table for [bmId=" + billEntry.getId() + "]");
		}
		BigDecimal totalSanctionedAmount = BigDecimal.ZERO.setScale(2);
		BigDecimal totalDisallowedAmount = BigDecimal.ZERO.setScale(2);
		for (final AccountBillEntryExpenditureDetEntity expenditure : billEntry.getExpenditureDetailList()) {
			final AccountBillEntryExpenditureDetBean dto = new AccountBillEntryExpenditureDetBean();
			BeanUtils.copyProperties(expenditure, dto);
			dto.setAcHeadCode(secondaryheadMasterJpaRepository.findSacHeadCodeBySacHeadId(expenditure.getSacHeadId()));
			totalSanctionedAmount = sumAmount(expenditure.getBillChargesAmount(), totalSanctionedAmount);
			totalDisallowedAmount = sumAmount(expenditure.getDisallowedAmount(), totalDisallowedAmount);
			billEntryDto.setDisallowedRemark(expenditure.getDisallowedRemark());
			dto.setActualAmount(dto.getActualAmount().setScale(2, BigDecimal.ROUND_HALF_EVEN));
			dto.setBillChargesAmount(dto.getBillChargesAmount().setScale(2, BigDecimal.ROUND_HALF_EVEN));
			expenditureList.add(dto);
		}
		billEntryDto.setTotalSanctionedAmount(totalSanctionedAmount);
		billEntryDto.setTotalDisallowedAmount(totalDisallowedAmount);

		return expenditureList;
	}

	private BigDecimal sumAmount(final BigDecimal amount1, final BigDecimal amount2) {

		return amount2 == null ? amount1 : amount2.add(amount1 == null ? BigDecimal.ZERO.setScale(2) : amount1);
	}

	private List<AccountBillEntryDeductionDetBean> populateDeductionDetails(
			final AccountBillEntryMasterEnitity billEntry, final AccountBillEntryMasterBean billEntryDto) {

		final List<AccountBillEntryDeductionDetBean> deductionList = new ArrayList<>();
		if ((billEntry.getExpenditureDetailList() == null) || billEntry.getExpenditureDetailList().isEmpty()) {
			throw new NullPointerException(
					"No Deduction record found from Deduction table for [bmId=" + billEntry.getId() + "]");
		}
		BigDecimal totalDeductionAmount = BigDecimal.ZERO.setScale(2);
		for (final AccountBillEntryDeductionDetEntity deduction : billEntry.getDeductionDetailList()) {
			final AccountBillEntryDeductionDetBean dto = new AccountBillEntryDeductionDetBean();
			BeanUtils.copyProperties(deduction, dto);
			dto.setBchId(deduction.getBchId());
			dto.setAcHeadCode(secondaryheadMasterJpaRepository.findSacHeadCodeBySacHeadId(deduction.getSacHeadId()));
			totalDeductionAmount = sumAmount(deduction.getDeductionAmount(), totalDeductionAmount);
			if (dto.getDeductionAmount() != null) {
				dto.setDeductionAmount(dto.getDeductionAmount().setScale(2, BigDecimal.ROUND_HALF_EVEN));
			}
			if(dto.getRaTaxFact()!=null)
				dto.setRaTaxFactDesc((CommonMasterUtility.getNonHierarchicalLookUpObjectByPrefix(dto.getRaTaxFact(),UserSession.getCurrent().getOrganisation().getOrgid(),MainetConstants.WorksManagement.VTY)).getLookUpDesc());
			deductionList.add(dto);
		}
		if(CollectionUtils.isEmpty(deductionList)) {
			AccountBillEntryDeductionDetBean dto = new AccountBillEntryDeductionDetBean();
			deductionList.add(dto);
		}
		billEntryDto.setTotalDeductions(totalDeductionAmount);
		return deductionList;
	}
	
	private List<AccountBillEntryMeasurementDetBean> populateMeasurementDetails(
			final AccountBillEntryMasterEnitity billEntry, final AccountBillEntryMasterBean billEntryDto) {
		final List<AccountBillEntryMeasurementDetBean> measurementList = new ArrayList<>();
		if ((billEntry.getMeasuremetDetailList() != null) || !billEntry.getMeasuremetDetailList().isEmpty()) {
			for (final AccountBillEntryMeasurementDetEntity deduction : billEntry.getMeasuremetDetailList()) {
				final AccountBillEntryMeasurementDetBean dto = new AccountBillEntryMeasurementDetBean();
				BeanUtils.copyProperties(deduction, dto);
				dto.setOrgId(deduction.getOrgid());
				measurementList.add(dto);
			}
		}
		else {
			AccountBillEntryMeasurementDetBean billMeasureDetBean = null;
			measurementList.add(billMeasureDetBean);
		}
		return measurementList;
	}

	@Override
	public AccountBillEntryMasterBean populateBillEntryEditData(final AccountBillEntryMasterBean accountBillEntryBean,
			final Long bmId, final Long orgId) {

		final AccountBillEntryMasterEnitity billEntity = findBillEntryById(orgId, bmId);
		accountBillEntryBean.setId(billEntity.getId());
		accountBillEntryBean.setBillNo(billEntity.getBillNo());
		accountBillEntryBean.setBillTypeId(billEntity.getBillTypeId().getCpdId());
		accountBillEntryBean.setBillEntryDate(UtilityService.convertDateToDDMMYYYY(billEntity.getBillEntryDate()));
		accountBillEntryBean.setDueDate(Utility.dateToString(billEntity.getDueDate()));
		accountBillEntryBean.setTransactionDate(Utility.dateToString(billEntity.getBillEntryDate()));
		accountBillEntryBean.setDepartmentId(billEntity.getDepartmentId().getDpDeptid());
		accountBillEntryBean.setVendorId(billEntity.getVendorId().getVmVendorid());
		accountBillEntryBean.setVendorDesc(billEntity.getVendorName());
		accountBillEntryBean.setInvoiceNumber(billEntity.getInvoiceNumber());
		accountBillEntryBean.setFieldId(billEntity.getFieldId());
		accountBillEntryBean.setBmTaxableValue(billEntity.getBmTaxableValue());
		if(billEntity.getFundId()!= null)
			{
				accountBillEntryBean.setFundId(billEntity.getFundId().getFundId());
			}
		else {accountBillEntryBean.setFundId(0l);}
		final String billTypeCode = CommonMasterUtility.findLookUpCode(AccountPrefix.ABT.toString(),
				UserSession.getCurrent().getOrganisation().getOrgid(), billEntity.getBillTypeId().getCpdId());
		accountBillEntryBean.setBillTypeCode(billTypeCode);
		if (billEntity.getInvoiceDate() != null) {
			accountBillEntryBean.setInvoiceDate(UtilityService.convertDateToDDMMYYYY(billEntity.getInvoiceDate()));
		}
		accountBillEntryBean.setWorkOrPurchaseOrderNumber(billEntity.getWorkOrPurchaseOrderNumber());
		if (billEntity.getWorkOrPurchaseOrderDate() != null) {
			accountBillEntryBean.setWorkOrPurchaseOrderDate(
					UtilityService.convertDateToDDMMYYYY(billEntity.getWorkOrPurchaseOrderDate()));
		}
		accountBillEntryBean.setResolutionNumber(billEntity.getResolutionNumber());
		if (billEntity.getResolutionDate() != null) {
			accountBillEntryBean
					.setResolutionDate(UtilityService.convertDateToDDMMYYYY(billEntity.getResolutionDate()));
		}
		accountBillEntryBean.setInvoiceValue(billEntity.getInvoiceValue().setScale(2, BigDecimal.ROUND_HALF_EVEN));
		accountBillEntryBean.setNarration(billEntity.getNarration());
		accountBillEntryBean
				.setBillTotalAmount(billEntity.getBillTotalAmount().setScale(2, BigDecimal.ROUND_HALF_EVEN));
		accountBillEntryBean.setCheckerAuthorization(billEntity.getCheckerAuthorization());
		accountBillEntryBean.setCheckerRemarks(billEntity.getCheckerRemarks());
		if (billEntity.getCheckerAuthorization().equals(MainetConstants.AccountBillEntry.R)) {
			accountBillEntryBean.setCheckerRemarks(billEntity.getCheckerRemarks());
			accountBillEntryBean.setAuthorizationStatus(AccountConstants.REJECTED.getValue());
		}
		accountBillEntryBean.setExpenditureDetailList(populateExpenditureDetails(billEntity, accountBillEntryBean));
		accountBillEntryBean.setDeductionDetailList(populateDeductionDetails(billEntity, accountBillEntryBean));
		accountBillEntryBean.setMeasurementDetailList(populateMeasurementDetails(billEntity, accountBillEntryBean));
		accountBillEntryBean.setNetPayable(billEntity.getBalanceAmount());

		accountBillEntryBean.setBillIntRefId(billEntity.getBillIntRefId());
		accountBillEntryBean.setCreatedBy(billEntity.getCreatedBy());
		accountBillEntryBean.setDupCreatedDate(Utility.dateToString(billEntity.getCreatedDate()));
		accountBillEntryBean.setLanguageId(billEntity.getLanguageId());
		accountBillEntryBean.setLgIpMacAddress(billEntity.getLgIpMacAddress());

		return accountBillEntryBean;
	}

	@Override
	@Transactional
	public boolean isPaymentDateisExists(Long orgid, Date paymentDate) {
		// TODO Auto-generated method stub
		return accountBillEntryDao.isPaymentDateisExists(orgid, paymentDate);
	}

	@Override
	@Transactional
	public void forUpdateBillIdInToDepositEntryTable(AccountBillEntryMasterBean dto,
			AccountBillEntryMasterEnitity entity) throws ParseException {
		// TODO Auto-generated method stub
		if ((dto.getDepositFlag() != null) && !dto.getDepositFlag().isEmpty()) {
			if (dto.getDepositFlag().equals(MainetConstants.MENU.Y)) {
				final Long depId = dto.getDepId();
				final Long trTenderId = dto.getTrTenderId();
				if (depId != null) {
					accountDepositJparepository.forUpdateBillIdInToDepositEntryTable(depId, entity.getId(),
							dto.getOrgId());
				}
				if (trTenderId != null) {
					accountTenderEntryJparepository.forUpdateBillIdInToWorkOrderEntryTable(trTenderId, entity.getId(),
							dto.getOrgId());
				}
			}
		}
		// In workflow, events last level only voucher posting is required.
		if (dto.getActualTaskId() != null) {
			WorkflowRequest workflowRequest = workflowRequestService.getWorkflowRequestByAppIdOrRefId(null,
					dto.getBillNo(), dto.getOrgId());
			if (workflowRequest != null && workflowRequest.getStatus().equals(MainetConstants.WorkFlow.Status.CLOSED)
					&& workflowRequest.getLastDecision().equals(MainetConstants.WorkFlow.Decision.APPROVED)) {
				Character authFlag = 'Y';
				Date authDate = Utility.stringToDate(dto.getAuthorizationDate());
				billEntryServiceJpaRepository.updateBillEntryAuthFlagDetailsInWorkflow(entity.getId(), dto.getOrgId(),
						Long.valueOf(dto.getCheckerUser()), authDate, dto.getCheckerRemarks(), authFlag);
				saveBillDeductionDepositEntryDetails(dto);
				postInvoiceBillEntry(dto);
			}
		}
	}

	private void saveBillDeductionDepositEntryDetails(AccountBillEntryMasterBean dto) throws ParseException {
		// TODO Auto-generated method stub
		final List<AccountBillEntryDeductionDetBean> deductionDetList = dto.getDeductionDetailList();
		if ((deductionDetList != null) && !deductionDetList.isEmpty()) {
			for (final AccountBillEntryDeductionDetBean dedDetBean : deductionDetList) {
				// saveCommonDeductionFields(dto, billDedDetEntity);
				depositEntry(dto, dedDetBean);
			}
		}
	}

	private String generateVoucherNo(final String voucherType, final long orgId, Date billEntryDate) {
		Long finYearId = financialyearService.getFinanciaYearIdByFromDate(billEntryDate);
		if (finYearId == null) {
			throw new NullPointerException(GENERATE_VOUCHER_NO_FIN_YEAR_ID);
		}
		final Long voucherTypeId = voucherTypeId(voucherType, orgId);
		if (voucherTypeId == null) {
			throw new NullPointerException(GENERATE_VOUCHER_NO_VOU_TYPE_ID);
		}
		String resetIdValue = voucherTypeId.toString() + finYearId.toString();
		Long resetId = Long.valueOf(resetIdValue);
		final Object sequenceNo = seqGenFunctionUtility.generateSequenceNo(MainetConstants.RECEIPT_MASTER.Module,
				com.abm.mainet.common.constant.MainetConstants.AccountJournalVoucherEntry.TB_AC_VOUCHER,
				com.abm.mainet.common.constant.MainetConstants.AccountJournalVoucherEntry.VOU_NO, orgId,
				MainetConstants.SECONDARY_MASTER.SECONDARY_SEQ_CONTINUE_VALUE, resetId);
		if (sequenceNo == null) {
			throw new NullPointerException(SEQUENCE_NO_ERROR + orgId);
		}
		return voucherType.substring(0, 2) + SEQUENCE_NO.substring(sequenceNo.toString().length())
				+ sequenceNo.toString();
	}

	private Long voucherTypeId(final String voucherType, final long orgId) {
		final Long voucherTypeId = CommonMasterUtility.lookUpIdByLookUpCodeAndPrefix(voucherType,
				AccountPrefix.VOT.toString(), orgId);
		if (voucherTypeId == 0l) {
			throw new IllegalArgumentException(TEMPLATE_ID_NOT_FOUND_VOU + voucherType + ORG_ID + orgId + PREFIX_VOT);
		}
		return voucherTypeId;
	}

	@Transactional(readOnly = true)
	@Override
	public String findAccountHeadCodeBySacHeadId(Long sacHeadId, Long orgId) {

		return budgetHeadRepository.findAccountHeadCodeBySacHeadId(sacHeadId, orgId);

	}

	@Transactional(readOnly = true)
	@Override
	public Long getVendorSacHeadIdByVendorId(Long vendorId, long orgid) {
		// TODO Auto-generated method stub
		Long status = CommonMasterUtility.lookUpIdByLookUpCodeAndPrefix(MainetConstants.CommonConstants.ACTIVE,
				AccountPrefix.ACN.toString(), orgid);
		final Long vendorSacHeadIdDr = billEntryServiceJpaRepository.getVendorSacHeadIdByVendorId(vendorId, orgid,
				status);
		return vendorSacHeadIdDr;
	}

	@Transactional(readOnly = true)
	@Override
	public AccountBudgetCodeEntity findBudgetHeadIdByUsingSacHeadId(Long sacHeadId, Long orgId) {
		// TODO Auto-generated method stub
		AccountBudgetCodeEntity budgetCode = null;
		try {
			budgetCode = budgetHeadRepository.findBudgetHeadIdBySacHeadId(sacHeadId, orgId);
		} catch (final NonUniqueResultException ex) {
			LOGGER.error("duplicate budget Head map against Account Head [sacHeadId=" + sacHeadId, ex);
			throw new IllegalArgumentException("duplicate budget Head map against Account Head [sacHeadId=" + sacHeadId,
					ex);
		}
		return budgetCode;
	}

	@Transactional(readOnly = true)
	@Override
	public BigDecimal findPaymentAmount(Long billId, Long orgId) {
		// TODO Auto-generated method stub
		BigDecimal sumPaymentAmount = BigDecimal.ZERO;
		sumPaymentAmount = billEntryServiceJpaRepository.findPaymentAmount(billId, orgId);
		return sumPaymentAmount;
	}

	@Override
	@Transactional(readOnly = true)
	public AccountBillEntryMasterBean populateBillEntryWorkFlowData(
			final AccountBillEntryMasterBean accountBillEntryBean, final String bmNo, final Long orgId,
			Long actualTaskId) {

		final AccountBillEntryMasterEnitity billEntity = findBillEntryByBillNo(orgId, bmNo);
		accountBillEntryBean.setId(billEntity.getId());
		accountBillEntryBean.setBillNo(billEntity.getBillNo());
		accountBillEntryBean.setBillTypeId(billEntity.getBillTypeId().getCpdId());
		accountBillEntryBean.setBillEntryDate(UtilityService.convertDateToDDMMYYYY(billEntity.getBillEntryDate()));
		accountBillEntryBean.setDueDate(Utility.dateToString(billEntity.getDueDate()));
		accountBillEntryBean.setTransactionDate(Utility.dateToString(billEntity.getBillEntryDate()));
		accountBillEntryBean.setDepartmentId(billEntity.getDepartmentId().getDpDeptid());
		accountBillEntryBean.setVendorId(billEntity.getVendorId().getVmVendorid());
		accountBillEntryBean.setVendorDesc(billEntity.getVendorName());
		accountBillEntryBean.setInvoiceNumber(billEntity.getInvoiceNumber());
		final String billTypeCode = CommonMasterUtility.findLookUpCode(AccountPrefix.ABT.toString(),
				UserSession.getCurrent().getOrganisation().getOrgid(), billEntity.getBillTypeId().getCpdId());
		accountBillEntryBean.setBillTypeCode(billTypeCode);
		if (billEntity.getInvoiceDate() != null) {
			accountBillEntryBean.setInvoiceDate(UtilityService.convertDateToDDMMYYYY(billEntity.getInvoiceDate()));
		}
		accountBillEntryBean.setWorkOrPurchaseOrderNumber(billEntity.getWorkOrPurchaseOrderNumber());
		if (billEntity.getWorkOrPurchaseOrderDate() != null) {
			accountBillEntryBean.setWorkOrPurchaseOrderDate(
					UtilityService.convertDateToDDMMYYYY(billEntity.getWorkOrPurchaseOrderDate()));
		}
		accountBillEntryBean.setResolutionNumber(billEntity.getResolutionNumber());
		if (billEntity.getResolutionDate() != null) {
			accountBillEntryBean
					.setResolutionDate(UtilityService.convertDateToDDMMYYYY(billEntity.getResolutionDate()));
		}
		if(billEntity.getFieldId()!=null) {
			accountBillEntryBean.setFieldId(billEntity.getFieldId());
		}
		accountBillEntryBean.setInvoiceValue(billEntity.getInvoiceValue().setScale(2, BigDecimal.ROUND_HALF_EVEN));
		accountBillEntryBean.setNarration(billEntity.getNarration());
		accountBillEntryBean
				.setBillTotalAmount(billEntity.getBillTotalAmount().setScale(2, BigDecimal.ROUND_HALF_EVEN));
		accountBillEntryBean.setCheckerAuthorization(billEntity.getCheckerAuthorization());
		accountBillEntryBean.setCheckerRemarks(billEntity.getCheckerRemarks());
		if (billEntity.getCheckerAuthorization().equals(MainetConstants.AccountBillEntry.R)) {
			accountBillEntryBean.setCheckerRemarks(billEntity.getCheckerRemarks());
			accountBillEntryBean.setAuthorizationStatus(AccountConstants.REJECTED.getValue());
		}
		accountBillEntryBean.setExpenditureDetailList(populateExpenditureDetails(billEntity, accountBillEntryBean));
		accountBillEntryBean.setDeductionDetailList(populateDeductionDetails(billEntity, accountBillEntryBean));
		accountBillEntryBean.setMeasurementDetailList(populateMeasurementDetails(billEntity, accountBillEntryBean));
		accountBillEntryBean.setNetPayable(billEntity.getBalanceAmount());

		accountBillEntryBean.setBillIntRefId(billEntity.getBillIntRefId());
		accountBillEntryBean.setActualTaskId(actualTaskId);

		accountBillEntryBean.setCreatedBy(billEntity.getCreatedBy());
		accountBillEntryBean.setDupCreatedDate(Utility.dateToString(billEntity.getCreatedDate()));
		// accountBillEntryBean.setCreatedDate(billEntity.getCreatedDate());
		accountBillEntryBean.setLanguageId(billEntity.getLanguageId());
		accountBillEntryBean.setLgIpMacAddress(billEntity.getLgIpMacAddress());
		accountBillEntryBean.setBmTaxableValue(billEntity.getBmTaxableValue());
		if(billEntity.getFundId() != null)
		{
			
			accountBillEntryBean.setFundId(billEntity.getFundId().getFundId());
		}
		else {
			accountBillEntryBean.setFundId(0l);
		}
		return accountBillEntryBean;
	}

	private AccountBillEntryMasterEnitity findBillEntryByBillNo(Long orgId, String bmNo) {
		return billEntryServiceJpaRepository.findBillEntryByBillNo(orgId, bmNo);
	}

	@Override
	@Transactional
	public void updateUploadInvoiceDeletedRecords(List<Long> removeFileById, Long updatedBy) {
		// TODO Auto-generated method stub
		iAttachDocsDao.updateRecord(removeFileById, updatedBy, MainetConstants.RnLCommon.Flag_D);
	}

	@Override
	public String validateViewBudgetInputDetails(VendorBillApprovalDTO vendorApprovalDto) {

		final StringBuilder builder = new StringBuilder();
		if (vendorApprovalDto.getBudgetCodeId() == null) {
			builder.append(MainetConstants.AccountBillEntry.BUDGET_CODE_ID);
		}
		if (vendorApprovalDto.getFaYearid() == null || vendorApprovalDto.getFaYearid() == 0L) {
			builder.append(MainetConstants.AccountBillEntry.FIN_YEAR_ID);
		}
		if (vendorApprovalDto.getDepartmentId() == null) {
			builder.append(MainetConstants.AccountBillEntry.DEPARTMENT_ID);
		}
		if (vendorApprovalDto.getOrgId() == null) {
			builder.append(MainetConstants.AccountBillEntry.APPEND_ORG_ID);
		}
		if (vendorApprovalDto.getBillAmount() == null) {
			builder.append(MainetConstants.AccountBillEntry.AMOUNT);
		}
		if (!builder.toString().isEmpty()) {
			builder.append(CANNOT_BE_NULL_EMPTY);
		}
		return builder.toString();
	}
	
	private void initializeWorkflowForOtherModules(AccountBillEntryMasterEnitity billMasterEntity,VendorBillApprovalDTO vendorBillApprovalDto, ServiceMaster service) {

		
		long fieldId = 0;
		Long workFlowLevel1 = null;
		Long workFlowLevel2 = null;
		Long workFlowLevel3 = null;
		Long workFlowLevel4 = null;
		Long workFlowLevel5 = null;
		if (vendorBillApprovalDto.getFieldId() != null && vendorBillApprovalDto.getFieldId() > 0) {
			
			final TbLocationMas locMas = ApplicationContextProvider.getApplicationContext().getBean(ILocationMasService.class).findById(vendorBillApprovalDto.getFieldId());
			if ((locMas.getLocRevenueWZMappingDto() != null) && !locMas.getLocRevenueWZMappingDto().isEmpty()) {
				fieldId = locMas.getLocRevenueWZMappingDto().get(0).getCodIdRevLevel1();
			}
			// In workflow, events wise Location Operation WZMapping is required.
			if ((locMas.getLocOperationWZMappingDto() != null) && !locMas.getLocOperationWZMappingDto().isEmpty()) {
				LocOperationWZMappingDto operLocationAndDeptId = ApplicationContextProvider.getApplicationContext().getBean(ILocationMasService.class).findOperLocationAndDeptId(locMas.getLocId(),service.getTbDepartment().getDpDeptid());
				if(operLocationAndDeptId!=null) {
				workFlowLevel1 = operLocationAndDeptId.getCodIdOperLevel1();
				workFlowLevel2 = operLocationAndDeptId.getCodIdOperLevel2();
				workFlowLevel3 = operLocationAndDeptId.getCodIdOperLevel3();
				workFlowLevel4 = operLocationAndDeptId.getCodIdOperLevel4();
				workFlowLevel5 = operLocationAndDeptId.getCodIdOperLevel5();
				}
			}
		}
		
		//Checking Workflow amount wise have or not
		/*WorkflowMas workflowAount = workflowTyepResolverService.resolveServiceWorkflowType(service.getOrgid(),
				service.getTbDepartment().getDpDeptid(), service.getSmServiceId(),
				workFlowLevel1, workFlowLevel2,workFlowLevel3,workFlowLevel4,workFlowLevel5);*/
		
		/*Defact #93353*/
		WorkflowMas workflowAount = null;
		BigDecimal amount = null;
		for(VendorBillExpDetailDTO vbeDTO:vendorBillApprovalDto.getExpDetListDto()) {
			amount = vbeDTO.getSanctionedAmount();
		}
		//Checking workflow bill Type wise or not
		LookUp lookup= null;
		try {
			Organisation organisationById = ApplicationContextProvider.getApplicationContext().getBean(IOrganisationService.class).getOrganisationById(vendorBillApprovalDto.getOrgId());
			lookup = CommonMasterUtility.getLookUpFromPrefixLookUpValue("BTW", "AIC", 1,
					organisationById);
		} catch (FrameworkException e) {
			LOGGER.info("Bill Type perfix not define in AIC perfix");
		}
		if (lookup != null && lookup.getOtherField() != null && lookup.getOtherField().equals("Y")) {
			List<WorkflowMas> worKFlowList = iWorkflowTypeDAO.getDefinedActiveWorkFlows(service.getOrgid(),
					service.getTbDepartment().getDpDeptid(), service.getSmServiceId(), null,
					vendorBillApprovalDto.getBillTypeId(),null);
			if (CollectionUtils.isNotEmpty(worKFlowList)) {
				for (WorkflowMas mas : worKFlowList) {
					if (mas.getStatus().equalsIgnoreCase("Y")) {
						if (mas.getToAmount() != null) {
							workflowAount = workflowTyepResolverService.resolveServiceWorkflowType(service.getOrgid(),
									service.getTbDepartment().getDpDeptid(), service.getSmServiceId(),
									amount, null, vendorBillApprovalDto.getBillTypeId(),
									workFlowLevel1, 
									workFlowLevel2,workFlowLevel3, workFlowLevel4,
									workFlowLevel5);
							break;
						} else {
							workflowAount = workflowTyepResolverService.resolveServiceWorkflowType(service.getOrgid(),
									service.getTbDepartment().getDpDeptid(), service.getSmServiceId(), null, null,
									vendorBillApprovalDto.getBillTypeId(), workFlowLevel1,
									workFlowLevel2, workFlowLevel3,
									workFlowLevel4, workFlowLevel5);
							break;
						}
					}
				}
			} else {
		 throw new FrameworkException("Workflow Not Found");
	}
		}
		else {
		List<WorkflowMas> worKFlowList = iWorkflowTypeDAO.getAllWorkFlows(service.getOrgid(),
				service.getTbDepartment().getDpDeptid(), service.getSmServiceId());
		if(CollectionUtils.isNotEmpty(worKFlowList)) {
		for(WorkflowMas mas:worKFlowList) {
		   if(mas.getStatus().equalsIgnoreCase("Y")) {
			if(mas.getToAmount()!=null ) {
				/*Getting workflow according to amount range*/
				workflowAount = workflowTyepResolverService.resolveServiceWorkflowType(service.getOrgid(),
						service.getTbDepartment().getDpDeptid(), service.getSmServiceId(),
						amount, null, workFlowLevel1,
						workFlowLevel2, workFlowLevel3,workFlowLevel4,workFlowLevel5);
				break;
			}else {
				workflowAount = workflowTyepResolverService.resolveServiceWorkflowType(service.getOrgid(),
						service.getTbDepartment().getDpDeptid(), service.getSmServiceId(),
						 workFlowLevel1,workFlowLevel2, workFlowLevel3,workFlowLevel4,workFlowLevel5);
				break;
			}
		 }
		}
	}else {
		 throw new FrameworkException("Workflow Not Found");
	}
}	
		AccountBillEntryMasterBean billEntryBean =new AccountBillEntryMasterBean();
		billEntryBean.setOrgId(billMasterEntity.getOrgId());
		billEntryBean.setCreatedBy(billMasterEntity.getCreatedBy());
		billEntryBean.setCreatedDate(billMasterEntity.getCreatedDate());
		billEntryBean.setBillNo(billMasterEntity.getBillNo());
		billEntryBean.setCheckerAuthorization(billMasterEntity.getCheckerAuthorization());
		if(workflowAount != null) {
			try {
				WorkflowProcessParameter processParameter = AccountWorkflowUtility
						.prepareInitAccountBilllEntryProcessParameter(billEntryBean, workflowAount,
								billMasterEntity.getBillNo());
					
					TaskAssignment assignment = new TaskAssignment();
					processParameter.setRequesterTaskAssignment(assignment);
					assignment.setActorId(billMasterEntity.getCreatedBy().toString());
					assignment.setOrgId(billMasterEntity.getOrgId());
					assignment.setUrl("AccountBillAuthorization.html");
					assignment.setDeptId(billMasterEntity.getDepartmentId().getDpDeptid());
					processParameter.setRequesterTaskAssignment(assignment);
					workflowExecutionService.initiateWorkflow(processParameter);
				
			} catch (Exception e) {
				LOGGER.error("Unsuccessful initiation/updation of task for application : "
						+ billMasterEntity.getBillNo() + e);
			}
		}
			
	
	}

	@Override
	public Long getTotalBillAmountByIntRefIdAndBillTypeId(Long intRefId, Long billTypeId, Long orgId) {
		return billEntryServiceJpaRepository.getTotalBillAmountByIntRefIdAndBillTypeId(intRefId, billTypeId, orgId);
	}

	@Override
	@Transactional
	public List<AccountBillEntryMasterBean> getBillDetailsByIntRefIdAndOrgId(Long intRefId, Long orgId) {
		List<AccountBillEntryMasterBean> billDtoList  = new ArrayList<>();
		List<AccountBillEntryMasterEnitity> billEntityList = billEntryServiceJpaRepository.getAllBillListByIntRefIdAndOrgId(intRefId, orgId);
		if(CollectionUtils.isNotEmpty(billEntityList)) {
			billEntityList.forEach(billEntity ->{
				AccountBillEntryMasterBean billDto = new AccountBillEntryMasterBean();
				billDto.setBillNo(billEntity.getBillNo());
				billDto.setId(billEntity.getId());
				billDto.setBillTypeCode(billEntity.getBillTypeId().getCpdValue());
				billDto.setBillIntRefId(billEntity.getBillIntRefId());
				billDto.setBillBalanceAmt(String.valueOf(billEntity.getBillTotalAmount()));
				billDto.setBillEntryDate(String.valueOf(billEntity.getBillEntryDate()));
				billDto.setInvoiceValue(billEntity.getInvoiceValue());
				billDto.setBillDate(new SimpleDateFormat(MainetConstants.DATE_FORMAT)
                        .format(billEntity.getBillEntryDate()));
				billDto.setVendorName(billEntity.getVendorName());
				billDto.setNarration(billEntity.getNarration());
				billDtoList.add(billDto);
			});
		}
		return billDtoList;
	}

	@Override
	@Transactional
	public VendorBillApprovalDTO convertExternaDtoToInternaDto(VendorBillApprovalExtDTO dto,List<String> ValidateData) {
		VendorBillApprovalDTO intDto=new VendorBillApprovalDTO();
		List<VendorBillExpDetailDTO> expDetListDto=new ArrayList<>();
		List<VendorBillDedDetailDTO> dedDetListDto=new ArrayList<>();
		Organisation organisation = ApplicationContextProvider.getApplicationContext().getBean(IOrganisationService.class).getActiveOrgByUlbShortCode(dto.getUlbCode());
     	 if(organisation!=null)
		   intDto.setOrgId(organisation.getOrgid());
		  else
			  ValidateData.add("Please provide valid ULB");
     	intDto.setBillTypeId(CommonMasterUtility.lookUpIdByLookUpCodeAndPrefix(dto.getBillType(),MainetConstants.ABT,organisation.getOrgid()));
     	intDto.setBillType(dto.getBillType());
     	Long deptId = departmentService.getDepartmentIdByDeptCode(dto.getDepartmentName(),
				MainetConstants.STATUS.ACTIVE);
     	if(deptId!=null && deptId>0)
     	  intDto.setDepartmentId(deptId);
     	else
     		ValidateData.add("Please provide valid department Name");
     	intDto.setLanguageId(1L);
     	intDto.setBillEntryDate(dto.getBillEntryDate());
     	intDto.setNarration(dto.getNarration());
     	intDto.setInvoiceAmount(new BigDecimal(dto.getInvoiceAmount()));
     	//intDto.setBillAmount(new BigDecimal(dto.getBillAmount()));
		Long fieldId = ApplicationContextProvider.getApplicationContext().getBean(AccountFieldMasterService.class)
				.getFieldIdByFieldCompositCode(dto.getFiled(), organisation.getOrgid());
		if (fieldId != null && fieldId > 0)
			intDto.setFieldId(fieldId);
		else
			ValidateData.add("Please provide valid Field");
		
		List<AccountFundMasterBean> fundList =getFundDataBasedOnConfiguration(organisation);
			
		if (CollectionUtils.isNotEmpty(fundList) && StringUtils.isNotEmpty(dto.getFundName())) {
			AccountFundMasterBean fund = fundList.stream().filter(
					entity -> (entity.getFundCompositecode()).equals(dto.getFundName().replaceAll(" ","")))
					.collect(Collectors.toList()).get(0);
			if (fund != null && fund.getFundId() > 0)
				intDto.setFundId(fund.getFundId());
		}
		   
     	final LookUp lookUpVendorStatus = CommonMasterUtility.getLookUpFromPrefixLookUpValue(
				MainetConstants.AccountConstants.AC.getValue(), PrefixConstants.VSS, 1, organisation);
		final Long vendorStatus = lookUpVendorStatus.getLookUpId();
		final LookUp lookUpSacStatus = CommonMasterUtility.getLookUpFromPrefixLookUpValue(MainetConstants.MASTER.A,
				PrefixConstants.ACN, 1,
				organisation);
		final Long activeStatusId = lookUpSacStatus.getLookUpId();
		final List<TbAcVendormaster> vendorList = vendorMasterService
				.getActiveStatusVendorsAndSacAcHead(organisation.getOrgid(), vendorStatus, activeStatusId);
		for(TbAcVendormaster master:vendorList) {
			if((master.getVmVendorcode().trim()).equalsIgnoreCase(dto.getVendorName().trim())) {
				intDto.setVendorId(master.getVmVendorid());
				intDto.setVendorDesc(master.getVmVendorcode()+"-"+master.getVmVendorname());
			}
		}
		if(intDto.getVendorId()==null)
			ValidateData.add("Please provide valid Vendor");
		
	  List<VendorBillExpDetailExtDTO> expDetailExtDetail = dto.getExpDetListDto();
	  int count=0;
	  Map<Long, String> map = secondaryheadMasterService.findExpenditureHeadMapAccountTypeIsOthers(organisation.getOrgid());
	   for(VendorBillExpDetailExtDTO expExtDto:expDetailExtDetail) {
		   VendorBillExpDetailDTO expdto=new VendorBillExpDetailDTO();
		   for(Map.Entry<Long,String> entry : map.entrySet()) {
			   if(entry.getValue().replaceAll(" ","").equalsIgnoreCase(expExtDto.getAccountHead().replaceAll(" ",""))) {
				   expdto.setBudgetCodeId(entry.getKey());
				   count++;
			   } 
		   }
		  expdto.setAmount(new BigDecimal(expExtDto.getAmount()));
		  expdto.setSanctionedAmount(new BigDecimal(expExtDto.getSanctionedAmount()));
		  expDetListDto.add(expdto);  
	   }
	  
	   if(count==0) {
			ValidateData.add("Please provide valid account head"); 
	   }
	   
	 List<VendorBillDedDetailExtDTO> deducDetailist = dto.getDedDetListDto();
	 Long taxMasLookUpId = null;
	  List<LookUp> taxMaslookUpList = CommonMasterUtility.getLevelData(MainetConstants.CommonMasterUi.TAC, 1, organisation);
		for (LookUp lookUp : taxMaslookUpList) {
			if (lookUp.getLookUpCode().equals(MainetConstants.StandardAccountHeadMapping.TDS)) {
				taxMasLookUpId = lookUp.getLookUpId();
			}
		}
    Map<Long, String> deduMap = secondaryheadMasterService.getTaxMasBillDeductionAcHeadAllDetails(organisation.getOrgid(), taxMasLookUpId);
    int count1=0;
		if (CollectionUtils.isNotEmpty(deducDetailist)) {
			for (VendorBillDedDetailExtDTO deduExtDto : deducDetailist) {
				VendorBillDedDetailDTO dedudto = new VendorBillDedDetailDTO();
				for (Map.Entry<Long, String> entry : deduMap.entrySet()) {
					if (entry.getValue().replaceAll(" ", "")
							.equalsIgnoreCase(deduExtDto.getAccountcode().replaceAll(" ", ""))) {
						dedudto.setBudgetCodeId(entry.getKey());
						count1++;
					}
				}

				dedudto.setDeductionAmount(new BigDecimal(deduExtDto.getDeductionAmount()));
				// dedudto.setBchId(expDetListDto.get(0).getBudgetCodeId());
				map.forEach((key, value) -> {
					if (value.replaceAll(" ", "")
							.equalsIgnoreCase(deduExtDto.getExpHeadAgainstDeductionHead().replaceAll(" ", ""))) {
						dedudto.setBchId(key);
					}
				});
				dedDetListDto.add(dedudto);
			}
			if (count1 == 0) {
				ValidateData.add("Please provide valid deduction account code");
			}

		}
    
		intDto.setCreatedBy(dto.getCreatedBy());
		if (dto.getCreatedDate() != null)
			intDto.setCreatedDate(dto.getCreatedDate());
		else
			intDto.setCreatedDate(Utility.dateToString(new Date()));
		intDto.setLgIpMacAddress(dto.getlIpMac());
		intDto.setExpDetListDto(expDetListDto);
		intDto.setDedDetListDto(dedDetListDto);
		return intDto;
	}

	@Override
	@Transactional
	public List<String> ValidateExternalRequest(VendorBillApprovalExtDTO dto) {
		List<String> errorMessageListNew = new ArrayList<>();
		StringBuilder builder = new StringBuilder();

		if (StringUtils.isEmpty(dto.getUlbCode())) {
			builder.append(" Please provide ULB code ");
		} else {
			Organisation organisation = ApplicationContextProvider.getApplicationContext()
					.getBean(IOrganisationService.class).getActiveOrgByUlbShortCode(dto.getUlbCode());
			if (organisation == null) {
				builder.append(" Wrong ULB code provide valid one");
			}
		}

		if (dto.getCreatedBy() == null && dto.getCreatedBy() == 0) {
			builder.append("Please provide created by");
		}

		if (StringUtils.isEmpty(dto.getBillType())) {
			builder.append("Please provide Bill Type");
		}

		if (StringUtils.isEmpty(dto.getDepartmentName())) {
			builder.append(" Please provide Department Name ");
		}

		if (StringUtils.isEmpty(dto.getVendorName())) {
			builder.append(" Please provide Vendor Name ");
		}

		if (StringUtils.isEmpty(dto.getBillEntryDate())) {
			builder.append(" Please provide Bill Entry Date ");
		} else {
			if (Utility.compareDate(new Date(), Utility.stringToDate(dto.getBillEntryDate()))) {
				builder.append(" Bill entry date can not be greater than current date");
			}
		}

		if (StringUtils.isEmpty(dto.getFiled())) {
			builder.append("Please provide Field Code  ");
		}

		if (StringUtils.isEmpty(dto.getInvoiceAmount())) {
			builder.append(" Please provide Invoice Amount  ");
		}

		/*
		 * if(StringUtils.isEmpty(dto.getTaxableAmount())) {
		 * builder.append(" Taxable  Amount  "); }
		 */

		if (StringUtils.isEmpty(dto.getNarration())) {
			builder.append(" Please provide Narration  ");
		}
		List<VendorBillExpDetailExtDTO> expDetailist = dto.getExpDetListDto();

		BigDecimal amount = BigDecimal.ZERO;
		BigDecimal SactionAmount = BigDecimal.ZERO;

		if (CollectionUtils.isNotEmpty(expDetailist)) {
			for (VendorBillExpDetailExtDTO expDto : expDetailist) {
				if (StringUtils.isEmpty(expDto.getAccountHead())) {
					builder.append(" Please provide Account Head ");
				}
				if (StringUtils.isEmpty(expDto.getAmount())) {
					builder.append(" Please provide Amount ");
				}
				if (StringUtils.isEmpty(expDto.getSanctionedAmount())) {
					builder.append(" Please provide Sanctioned Amount ");
				}
				amount = amount.add(new BigDecimal(expDto.getAmount()));
				SactionAmount = SactionAmount.add(new BigDecimal(expDto.getSanctionedAmount()));
			}
		} else {
			builder.append(" Provide Expediture Detail ");
		}

		if (amount.compareTo(new BigDecimal(dto.getInvoiceAmount())) == 1) {
			builder.append(" Amount should not be greater than invoice amount ");
		}
		if (SactionAmount.compareTo(new BigDecimal(dto.getInvoiceAmount())) == 1) {
			builder.append(" Saction Amount should not be greater than invoice amount ");
		}

		if (SactionAmount.compareTo(amount) == 1) {
			builder.append(" Saction Amount Should Not Be Greater Than Amount ");
		}

		List<VendorBillDedDetailExtDTO> deduDetailist = dto.getDedDetListDto();

		BigDecimal deductionAmount = BigDecimal.ZERO;

		if (CollectionUtils.isNotEmpty(deduDetailist)) {
			for (VendorBillDedDetailExtDTO deduDto : deduDetailist) {

				if (StringUtils.isEmpty(deduDto.getAccountcode())) {
					builder.append(" Please provide Account Code ");
				}

				if (StringUtils.isEmpty(deduDto.getDeductionAmount())) {
					builder.append(" Please provide Deduction Amounts ");
				}
				deductionAmount = deductionAmount.add(new BigDecimal(deduDto.getDeductionAmount()));
			}
		}

		if (deductionAmount.compareTo(amount) == 1) {
			builder.append(" Deduction Amount Should Not Be Greater Than Amount ");
		}

		if (deductionAmount.compareTo(SactionAmount) == 1) {
			builder.append(" Deduction Amount Should Not Be Greater Saction Amount ");
		}

		if (!builder.toString().isEmpty()) {
			errorMessageListNew.add(builder.toString());
		}
		return errorMessageListNew;
	 
	}

	private List<AccountFundMasterBean> getFundDataBasedOnConfiguration(Organisation org) {
		boolean fieldDefaultFlag = false;
		final boolean isDafaultOrgExist = tbOrganisationService.defaultexist(MainetConstants.MASTER.Y);
		boolean fundDefaultFlag = false;
		if (isDafaultOrgExist) {
			fundDefaultFlag = tbAcCodingstructureMasService.checkDefaultFlagIsExists(PrefixConstants.FUND_CPD_VALUE,
					ApplicationSession.getInstance().getSuperUserOrganization().getOrgid(), MainetConstants.MASTER.Y);
		} else {
			fundDefaultFlag = tbAcCodingstructureMasService.checkDefaultFlagIsExists(PrefixConstants.FUND_CPD_VALUE,
					org.getOrgid(), MainetConstants.MASTER.Y);
		}
		Organisation defultorg = null;
		Long defultorgId = null;
		if (isDafaultOrgExist && fundDefaultFlag) {
			defultorg = ApplicationSession.getInstance().getSuperUserOrganization();
			defultorgId = ApplicationSession.getInstance().getSuperUserOrganization().getOrgid();
		} else if (isDafaultOrgExist && (fundDefaultFlag == false)) {
			defultorg = org;
			defultorgId =org.getOrgid();
		} else {
			defultorg = org;
			defultorgId = org.getOrgid();
		}
		final LookUp fundLookup = CommonMasterUtility.getLookUpFromPrefixLookUpValue(PrefixConstants.FUND_CPD_VALUE,
				PrefixConstants.CMD, 1,
				org);
		List<AccountFundMasterBean> fundList = tbAcCodingstructureMasService.getFundMasterActiveStatusList(defultorgId, defultorg,
				fundLookup.getLookUpId(), 1);
		return fundList;
	}

	@Transactional(readOnly = true)
	@Override
	public List<Object[]> getBillNumbers(Long orgId, Long vendorId, Date payDate) {
		// TODO Auto-generated method stub
	  return billEntryServiceJpaRepository.getBillNumbers(orgId,vendorId, payDate);
	}

	@Transactional
	@Override
	public List<AccountBillEntryMasterEnitity> getAllPendingPaymentBillEntryData(Long orgId) {
		// TODO Auto-generated method stub
		return billEntryServiceJpaRepository.getAllPendingPaymentBillEntryData(orgId);
	}
	
}
