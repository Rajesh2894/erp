package com.abm.mainet.account.service;

import java.lang.reflect.InvocationTargetException;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import javax.annotation.Resource;

import org.apache.commons.collections.CollectionUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.abm.mainet.account.dao.AccountChequeDishonourDao;
import com.abm.mainet.account.dao.AccountChequeOrCashDepositeDao;
import com.abm.mainet.account.dao.AccountJournalVoucherEntryDao;
import com.abm.mainet.account.domain.AccountVoucherEntryDetailsEntity;
import com.abm.mainet.account.domain.AccountVoucherEntryEntity;
import com.abm.mainet.account.dto.AccountChequeDishonourDTO;
import com.abm.mainet.account.dto.VoucherReversePostDTO;
import com.abm.mainet.account.dto.VoucherReversePostDetailDTO;
import com.abm.mainet.account.repository.AccountBankDepositeSlipMasterJpaRepository;
import com.abm.mainet.account.repository.AccountDepositRepository;
import com.abm.mainet.account.repository.AccountReceiptEntryJpaRepository;
import com.abm.mainet.account.repository.AccountVoucherEntryRepository;
import com.abm.mainet.account.repository.BillEntryRepository;
import com.abm.mainet.account.repository.ContraEntryVoucherRepository;
import com.abm.mainet.cfc.loi.service.TbLoiDetService;
import com.abm.mainet.common.constant.MainetConstants;
import com.abm.mainet.common.constant.MainetConstants.AccountConstants;
import com.abm.mainet.common.constant.PrefixConstants;
import com.abm.mainet.common.constant.PrefixConstants.AccountJournalVoucherEntry;
import com.abm.mainet.common.constant.PrefixConstants.AccountPrefix;
import com.abm.mainet.common.domain.BankAccountMasterEntity;
import com.abm.mainet.common.domain.Organisation;
import com.abm.mainet.common.integration.acccount.domain.TbServiceReceiptMasEntity;
import com.abm.mainet.common.integration.acccount.dto.VoucherPostDTO;
import com.abm.mainet.common.integration.acccount.dto.VoucherPostDetailDTO;
import com.abm.mainet.common.master.repository.TbFinancialyearJpaRepository;
import com.abm.mainet.common.master.service.BankMasterService;
import com.abm.mainet.common.master.service.DepartmentService;
import com.abm.mainet.common.service.TbBankmasterService;
import com.abm.mainet.common.utility.ApplicationSession;
import com.abm.mainet.common.utility.CommonMasterUtility;
import com.abm.mainet.common.utility.LookUp;
import com.abm.mainet.common.utility.SeqGenFunctionUtility;
import com.abm.mainet.common.utility.UserSession;
import com.abm.mainet.common.utility.Utility;
import com.abm.mainet.common.utility.UtilityService;
import com.abm.mainet.smsemail.dto.SMSAndEmailDTO;
import com.abm.mainet.smsemail.service.ISMSAndEmailService;

@Service
public class AccountChequeDishonourServiceImpl implements AccountChequeDishonourService {

	@Resource
	private AccountBankDepositeSlipMasterJpaRepository accountBankDepositeSlipMasterJpaRepository;
	@Resource
	private TbBankmasterService banksMasterService;
	@Resource
	private AccountChequeDishonourDao accountChequeDishonourDao;
	@Resource
	private BankMasterService bankMasterService;
	@Resource
	private AccountChequeOrCashDepositeDao accountChequeOrCashDepositeDao;
	@Resource
	private AccountVoucherPostService accountVoucherPostService;
	@Resource
	BudgetCodeService budgetCodeService;
	@Resource
	private BillEntryRepository billEntryRepository;
	@Resource
	AccountJournalVoucherEntryDao journalVoucherDao;
	@Resource
	ContraEntryVoucherRepository contraEntryVoucherJpaRepository;
	@Resource
	private AccountReceiptEntryJpaRepository accountReceiptEntrRrepository;
	@Resource
	private AccountVoucherEntryRepository accountVoucherEntryRepository;
	@Resource
	private SeqGenFunctionUtility seqGenFunctionUtility;
	@Resource
	private AccountDepositRepository accountDepositRepository;

	@Resource
	private DepartmentService departmentService;
	@Resource
	private TbFinancialyearJpaRepository financialYearJpaRepository;
	@Resource
	private ReceiptReversalProvisionService receiptReversalProvisionService;
	@Resource
	private TbLoiDetService tbLoiDetService;
	@Autowired
	private ISMSAndEmailService iSMSAndEmailService;

	private static final String SEQUENCE_NO_ERROR = "unable to generate sequenceNo for orgId=";
	private static final String SEQUENCE_NO = "0000000000";

	private static final String TEMPLATE_ID_NOT_FOUND_VOU = "templateTypeId not found for provided parameter[voucherType=";
	private static final String ORG_ID = ", orgId=";
	private static final String PREFIX_VOT = ",prefix=VOT]";

	private static final Logger LOGGER = Logger.getLogger(AccountChequeOrCashServiceImpl.class);

	private static final String GENERATE_VOUCHER_NO_FIN_YEAR_ID = "Voucher sequence number generation, The financial year id is getting null value";
	private static final String GENERATE_VOUCHER_NO_VOU_TYPE_ID = "Voucher sequence number generation, The voucher type id is getting null value";

	@Override
	@Transactional(readOnly = true)
	public Map<Long, String> getBankAccountData(final Long orgId) {

		final Map<Long, String> bankAccountMap = new LinkedHashMap<>();
		final Long accountActiveStatusId = CommonMasterUtility
				.lookUpIdByLookUpCodeAndPrefix(MainetConstants.CommonConstants.U, PrefixConstants.BAS, orgId);
		final List<BankAccountMasterEntity> bankNamesList = banksMasterService.getActiveBankAccountDetailsByOrgId(orgId,
				accountActiveStatusId);
		for (BankAccountMasterEntity bankAccountMasterEntity : bankNamesList) {
			if (bankAccountMasterEntity.getBaAccountId() != null && bankAccountMasterEntity.getBaAccountNo() != null
					&& (bankAccountMasterEntity.getBaAccountName() != null
							&& !bankAccountMasterEntity.getBaAccountName().isEmpty())) {
				bankAccountMap.put(bankAccountMasterEntity.getBaAccountId(),
						bankAccountMasterEntity.getBankId().getBank() + MainetConstants.SEPARATOR
								+ bankAccountMasterEntity.getBaAccountNo() + MainetConstants.SEPARATOR
								+ bankAccountMasterEntity.getBaAccountName());
			}
		}
		return bankAccountMap;
	}

	@Override
	@Transactional(readOnly = true)
	public Map<Long, String> getChequeDDNoBankAccountData(final Long orgId) {

		final Map<Long, String> bankAccountMap = new LinkedHashMap<>();

		final List<Object[]> bankAccount = accountBankDepositeSlipMasterJpaRepository
				.getChequeDDNoBankAccountData(orgId);

		for (final Object bankAccountId : bankAccount) {
			if (bankAccountId != null) {
				final List<Object[]> bankNamesList = bankMasterService
						.getChequeDDNoBankAccountNames(Long.valueOf(bankAccountId.toString()));

				for (final Object[] objects : bankNamesList) {

					if ((objects[0] != null) && (objects[1] != null)) {
						bankAccountMap.put(Long.valueOf(objects[0].toString()), objects[1].toString());
					}
				}
			}
		}
		return bankAccountMap;
	}

	@Override
	@Transactional(readOnly = true)
	public List<AccountChequeDishonourDTO> findByAllGridPayInSlipSearchData(final String number, final Date date,
			final BigDecimal amount, final Long bankAccount, final Long orgId) {
		final List<AccountChequeDishonourDTO> dto = new ArrayList<>();

		final List<Object[]> entities = accountChequeDishonourDao.findByAllGridPayInSlipSearchData(number, date, amount,
				bankAccount, orgId);

		// #120771
		List<LookUp> lookUpList = CommonMasterUtility.lookUpListByPrefix(PrefixConstants.LookUpPrefix.CLR, orgId);
		List<LookUp> lookUpLists = lookUpList.stream()
				.filter(lookUps -> lookUps.getLookUpCode() != null
						&& lookUps.getLookUpCode().equals(MainetConstants.AccountConstants.CLEARED.getValue())
						|| lookUps.getLookUpCode().equals(MainetConstants.AccountConstants.DISHONORED.getValue()))
				.collect(Collectors.toList());

		for (final Object[] objects : entities) {
			if ((objects[0] != null) && (objects[1] != null) && (objects[2] != null) && (objects[3] != null)
					&& (objects[4] != null) && (objects[5] != null) && (objects[7] != null)) {
				if ((objects[6] == null) || (!objects[6].equals(lookUpLists.get(0).getLookUpId())
						&& !objects[6].equals(lookUpLists.get(1).getLookUpId()))) {
					final AccountChequeDishonourDTO bean = new AccountChequeDishonourDTO();
					bean.setChequeDishonourId(Long.valueOf(objects[0].toString()));
					bean.setNumber((objects[1].toString()));
					final Date depositSlipdate = (Date) objects[2];
					final String chkDate = Utility.dateToString(depositSlipdate);
					bean.setDate(chkDate);
					bean.setId(Long.valueOf(objects[3].toString()));
					bean.setChequeddno(Long.valueOf(objects[4].toString()));
					final Date chequedddate = (Date) objects[5];
					final String cheqDate = Utility.dateToString(chequedddate);
					bean.setChequedddate(cheqDate);
					final BigDecimal bd = new BigDecimal(objects[7].toString());
					bean.setAmount(CommonMasterUtility.getAmountInIndianCurrency(bd));
					dto.add(bean);
				}
			}
		}
		return dto;
	}

	@Override
	@Transactional(readOnly = true)
	public List<AccountChequeDishonourDTO> findByAllGridChequeDDNoSearchData(final Long number, final Date date,
			final BigDecimal amount, final Long bankAccount, final Long orgId) {
		final List<AccountChequeDishonourDTO> dto = new ArrayList<>();

		final List<Object[]> entities = accountChequeDishonourDao.findByAllGridChequeDDNoSearchData(number, date,
				amount, bankAccount, orgId);

		// #120771
		List<LookUp> lookUpList = CommonMasterUtility.lookUpListByPrefix(PrefixConstants.LookUpPrefix.CLR, orgId);
		List<LookUp> lookUpLists = lookUpList.stream()
				.filter(lookUps -> lookUps.getLookUpCode() != null
						&& lookUps.getLookUpCode().equals(MainetConstants.AccountConstants.CLEARED.getValue())
						|| lookUps.getLookUpCode().equals(MainetConstants.AccountConstants.DISHONORED.getValue()))
				.collect(Collectors.toList());

		for (final Object[] objects : entities) {
			if ((objects[0] != null) && (objects[1] != null) && (objects[2] != null) && (objects[3] != null)
					&& (objects[4] != null) && (objects[5] != null) && (objects[7] != null)) {
				if ((objects[6] == null) || (!objects[6].equals(lookUpLists.get(0).getLookUpId())
						&& !objects[6].equals(lookUpLists.get(1).getLookUpId()))) {
					final AccountChequeDishonourDTO bean = new AccountChequeDishonourDTO();
					bean.setChequeDishonourId(Long.valueOf(objects[0].toString()));
					bean.setNumber((objects[1].toString()));
					final Date depositSlipdate = (Date) objects[2];
					final String chkDate = Utility.dateToString(depositSlipdate);
					bean.setDate(chkDate);
					bean.setId(Long.valueOf(objects[3].toString()));
					bean.setChequeddno(Long.valueOf(objects[4].toString()));
					final Date chequedddate = (Date) objects[5];
					final String cheqDate = Utility.dateToString(chequedddate);
					bean.setChequedddate(cheqDate);
					final BigDecimal bd = new BigDecimal(objects[7].toString());
					bean.setAmount(CommonMasterUtility.getAmountInIndianCurrency(bd));
					dto.add(bean);
				}
			}
		}
		return dto;
	}

	@Override
	@Transactional
	public AccountChequeDishonourDTO saveAccountChequeDishonourFormData(
			final AccountChequeDishonourDTO tbAcChequeDishonourDTO, final Long orgId, final long fieldId,
			final int langId, final Long empId, final String ipMacAddress)
			throws IllegalAccessException, InvocationTargetException {
		final List<AccountChequeDishonourDTO> dtoList = new ArrayList<>();
		final String dishonourId = tbAcChequeDishonourDTO.getDishonourIds();
		final String[] obj = dishonourId.split(MainetConstants.operator.COMMA);
		for (final String object : obj) {
			final AccountChequeDishonourDTO dto = new AccountChequeDishonourDTO();
			dto.setReceiptModeId(Long.valueOf(object.toString()));
			dtoList.add(dto);
		}
		tbAcChequeDishonourDTO.setChequeDishonourDtoList(dtoList);
		String chequeDisRefNo = "";
		Date chequeDisDate = null;
		TbServiceReceiptMasEntity receiptMaster=null;
		final List<AccountVoucherEntryEntity> voucherDetailsCr = new ArrayList<>();
		for (final AccountChequeDishonourDTO acChequeDishonourDTO : tbAcChequeDishonourDTO
				.getChequeDishonourDtoList()) {
			tbAcChequeDishonourDTO.setReceiptModeId(acChequeDishonourDTO.getReceiptModeId());
			final Long receiptModeId = tbAcChequeDishonourDTO.getReceiptModeId();
			Date chequeDishonourDate = null;
			if ((tbAcChequeDishonourDTO.getDishonourDate() != null)
					&& !tbAcChequeDishonourDTO.getDishonourDate().isEmpty()) {
				chequeDishonourDate = Utility.stringToDate(tbAcChequeDishonourDTO.getDishonourDate());
			} else {
				chequeDishonourDate = new Date();
			}
			final Double chequeDisChgAmt = Double.valueOf(tbAcChequeDishonourDTO.getDishonourAmount());
			final String remarks = tbAcChequeDishonourDTO.getRemarks();
			// #120771
			String flag = "";
			LookUp lookUp = null;
			try {
				lookUp = CommonMasterUtility.getValueFromPrefixLookUp(
						MainetConstants.AccountConstants.DISHONORED.getValue(), PrefixConstants.LookUpPrefix.CLR,
						new Organisation(orgId));
			} catch (Exception e) {
				LOGGER.error("Error while getting prefix value", e);
			}
			if (lookUp != null)
				flag = String.valueOf(lookUp.getLookUpId());// MainetConstants.Common_Constant.NUMBER.FOUR;
			final String receiptTypeFlag = MainetConstants.MENU.D;
			String deposittypeFlag = MainetConstants.MENU.R;
			char coTypeFlag = deposittypeFlag.charAt(0);
			String recDelFlag = MainetConstants.Y_FLAG;
			accountChequeDishonourDao.saveAccountChequeDishonourFormData(receiptModeId, chequeDishonourDate,
					chequeDisChgAmt, remarks, flag, orgId);
			final List<Object[]> rcptidAndDepsitid = accountReceiptEntrRrepository
					.gettingReceiptMasEntryId(receiptModeId, orgId);
			
			for (final Object[] recptDepsitIds : rcptidAndDepsitid) {
				if (recptDepsitIds[0] != null) {
					accountReceiptEntrRrepository.updateReceiptEntryFlag((Long) recptDepsitIds[0], receiptTypeFlag,
							recDelFlag, orgId);

					List<Long> depositIdList = accountDepositRepository
							.findAccountDepositEntityByDepReceiptno(recptDepsitIds[2].toString(), orgId);
					if (CollectionUtils.isNotEmpty(depositIdList)) {
						for(Long depositId:depositIdList) {
						String dep_del_flag = MainetConstants.MENU.Y;
						final Long ReceiptDeleted = CommonMasterUtility.lookUpIdByLookUpCodeAndPrefix(
								PrefixConstants.AccountBillEntry.RD, PrefixConstants.NewWaterServiceConstants.RDC,
								orgId);
						accountDepositRepository.updateDep_del_flagOfAccountDepositEntity(depositId, orgId,
								dep_del_flag, ReceiptDeleted);
						}
					}
					
					// property tax integration purpose.
					Long dpDeptid = departmentService.getDepartmentIdByDeptCode(AccountConstants.AS.toString());
					Long recRefId = accountReceiptEntrRrepository.gettingRecRefIdInPropertyTax((Long) recptDepsitIds[0],
							dpDeptid, orgId);
					if (recRefId != null) {
						receiptReversalProvisionService.updateReceiptReversalDelFlag(recRefId);
						// accountReceiptEntrRrepository.updateRecRefIdInPropertyTax(recRefId, orgId);
					}
					
				}
				if (recptDepsitIds[1] != null) {
					accountReceiptEntrRrepository.updateDepositSlipEntryFlag((Long) recptDepsitIds[1], coTypeFlag,
							orgId);
				}
			}
			// LOI Integration with other modules start
			if (CollectionUtils.isNotEmpty(rcptidAndDepsitid)) {
				TbServiceReceiptMasEntity receiptMas = accountReceiptEntrRrepository
						.findAllByReceiptId(Long.valueOf(rcptidAndDepsitid.get(0)[0].toString()), orgId);
				receiptMaster=receiptMas;
				if (receiptMas != null) {
					String deptCode = departmentService.getDeptCode(receiptMas.getDpDeptId());
					List<LookUp> lookuoList = CommonMasterUtility.getLookUps(
							MainetConstants.LandEstate.MODULE_LIVE_INTEGRATION,
							UserSession.getCurrent().getOrganisation());
					List<LookUp> mliLookup = lookuoList.stream()
							.filter(lookup -> lookup.getLookUpCode().equalsIgnoreCase(deptCode))
							.collect(Collectors.toList());
					if (CollectionUtils.isNotEmpty(mliLookup) && mliLookup.size() > 0) {
						// for saving dishonour charges in TbloiDet table D#130924
						tbLoiDetService.saveDishonourCharges(Long.valueOf(rcptidAndDepsitid.get(0)[0].toString()),
								new BigDecimal(tbAcChequeDishonourDTO.getDishonourAmount()), orgId, empId,remarks);
					}
				}
			}
			// LOI Integration with other modules end
			String depositNo = null;
			Date depositDate = null;
			final List<Object[]> depositSlipNoDate = accountChequeOrCashDepositeDao
					.getAllBankDepositSlipEntryData(receiptModeId, orgId);
			for (final Object[] objects : depositSlipNoDate) {
				if (objects[0] != null) {
					depositNo = (objects[0].toString());
				}
				if (objects[1] != null) {
					depositDate = ((Date) objects[1]);
				}
			}
			Long recieptNo = null;
			Date receiptDate = null;
			BigDecimal chequeAmt = null;
			Long deptId = null;
			final List<Object[]> receiptNo = accountChequeOrCashDepositeDao.getAllReceiptEntryData(receiptModeId,
					orgId);
			for (final Object[] objects : receiptNo) {
				if (objects[0] != null) {
					recieptNo = ((Long) objects[0]);
					chequeDisRefNo = (objects[0].toString());
				}
				if (objects[1] != null) {
					receiptDate = ((Date) objects[1]);
					chequeDisDate = ((Date) objects[1]);
				}
				if (objects[2] != null) {
					chequeAmt = (new BigDecimal(objects[2].toString()));
				}
				if (objects[3] != null) {
					deptId = ((Long) objects[3]);
				}
			}
			LOGGER.info("postContraEntry -outside - postContraEntry AccountChequeDishonour:" + depositNo + orgId
					+ fieldId + empId + langId);
			if ((depositNo != null) && (receiptNo != null)) {
				LOGGER.info("postContraEntry - inside - postContraEntry AccountChequeDishonour:" + depositNo
						+ depositDate + tbAcChequeDishonourDTO + chequeAmt + orgId + fieldId + langId + empId);
				postContraReversalEntry(depositNo, depositDate, tbAcChequeDishonourDTO, chequeAmt, orgId, fieldId,
						langId, empId, ipMacAddress);
				LOGGER.info("postReverseReceiptPaymentEntry - postReverseReceiptPaymentEntry AccountChequeDishonour:"
						+ recieptNo + receiptDate + tbAcChequeDishonourDTO + chequeAmt + orgId + fieldId + langId
						+ empId);
				postReceiptReversalEntry(recieptNo, receiptDate, tbAcChequeDishonourDTO, chequeAmt, orgId, fieldId,
						langId, empId, ipMacAddress, deptId);
			}
		}
		LOGGER.info("postTaxMasterBankPaymentEntry - postTaxMasterBankPaymentEntry AccountChequeDishonour:"
				+ tbAcChequeDishonourDTO + voucherDetailsCr + orgId + fieldId + empId + langId);
		postChequeDishonorChargesEntry(tbAcChequeDishonourDTO, orgId, fieldId, langId, empId, chequeDisRefNo,
				chequeDisDate);
		//SMS and Email Configuration start
		  if(receiptMaster!=null)
		 sendSmsAndEmail(tbAcChequeDishonourDTO,receiptMaster);
		//SMS and Email Configuration end
		
		
		return tbAcChequeDishonourDTO;
	}

	private void postContraReversalEntry(final String depositSlipNo, final Date depositDate,
			final AccountChequeDishonourDTO tbAcChequeDishonourDTO, final BigDecimal chequeAmt, final Long orgId,
			final long fieldId, final int langId, final Long empId, final String ipMacAddress)
			throws IllegalAccessException, InvocationTargetException {

		final Long voucherTypeId = CommonMasterUtility.lookUpIdByLookUpCodeAndPrefix(
				PrefixConstants.ContraVoucherEntry.CV, AccountPrefix.VOT.toString(), orgId);
		// final Long voucherSubTypeId =
		// CommonMasterUtility.lookUpIdByLookUpCodeAndPrefix(PrefixConstants.ChequeDishonour.DS,
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
					"entryType id not found for for lookUpCode[MAN] from VET Prefix in cheque dishonour contra reverse voucher posting.");
		}
		final List<AccountVoucherEntryEntity> acVoucherEntryEntity = journalVoucherDao.getReceiptReversalVoucherDetails(
				depositSlipNo.toString(), depositDate, voucherTypeId, orgId,
				departmentService.getDepartmentIdByDeptCode(AccountConstants.AC.toString()), vouSubTypes, entryTypeId);

		// final List<VoucherPostDetailDTO> voucherDetailsCr = new ArrayList<>();
		VoucherReversePostDTO bean = null;
		List<VoucherReversePostDetailDTO> detEntity = null;
		final Organisation org = new Organisation();
		org.setOrgid(orgId);
		for (final AccountVoucherEntryEntity accountVoucherEntryEntity : acVoucherEntryEntity) {

			bean = new VoucherReversePostDTO();
			detEntity = new ArrayList<>();
			VoucherReversePostDetailDTO postDetailEntityCr = null;
			VoucherReversePostDetailDTO postDetailEntityDr = null;

			// postDto.setVoucherDate(new Date());
			final String voucherType = CommonMasterUtility.findLookUpCode(AccountPrefix.VOT.toString(), orgId,
					accountVoucherEntryEntity.getVouTypeCpdId());

			if (voucherType.equals(PrefixConstants.ContraVoucherEntry.CV)) {

				final LookUp voucherSubType = CommonMasterUtility.getLookUpFromPrefixLookUpValue(
						PrefixConstants.Prefix.DSR, PrefixConstants.REV_TYPE_CPD_VALUE, langId, org);
				final Long voucherSubTypeid = voucherSubType.getLookUpId();
				bean.setVouSubtypeCpdId(voucherSubTypeid);
				Date dishonourDate = UtilityService
						.convertStringDateToDateFormat(tbAcChequeDishonourDTO.getDishonourDate());
				// changes by sanket
				bean.setVouDate(dishonourDate);
				bean.setVouReferenceNo(depositSlipNo.toString());
				bean.setNarration(tbAcChequeDishonourDTO.getRemarks());
				bean.setOrg(orgId);
				bean.setCreatedBy(empId);
				bean.setLmodDate(new Date());
				bean.setLgIpMac(ipMacAddress);
				// bean.setTemplateType(AccountConstants.PN.toString());
				bean.setEntryType(CommonMasterUtility.lookUpIdByLookUpCodeAndPrefix(
						MainetConstants.BILL_MASTER_COMMON.ENTRY_TYPE, AccountPrefix.VET.toString(), orgId));
				bean.setLmodDate(accountVoucherEntryEntity.getLmodDate());
				bean.setUpdatedDate(accountVoucherEntryEntity.getUpdatedDate());
				bean.setVouReferenceNoDate(depositDate);
				// bean.setAuthoFlg(accountVoucherEntryEntity.getAuthoFlg());
				bean.setPayerPayee(accountVoucherEntryEntity.getPayerPayee());
				bean.setUpdatedby(accountVoucherEntryEntity.getUpdatedby());
				String vouNo = generateVoucherNo(PrefixConstants.ContraVoucherEntry.CV, org.getOrgid(),
						tbAcChequeDishonourDTO.getDishonourDate());
				// bean.setVouNo(vouNo);
				// Autho Flag
				final LookUp payEntryMakerChecker = CommonMasterUtility.getLookUpFromPrefixLookUpValue(
						PrefixConstants.ContraVoucherEntry.CV, AccountPrefix.AUT.toString(), langId, org);
				if (payEntryMakerChecker.getLookUpCode() == null || payEntryMakerChecker.getLookUpCode().isEmpty()) {
					bean.setAuthoFlg(MainetConstants.MENU.Y);
					bean.setAuthoId(accountVoucherEntryEntity.getAuthoId());
					bean.setAuthoDate(dishonourDate);
					bean.setAuthRemark(accountVoucherEntryEntity.getAuthRemark());
					// changes by sanket
					bean.setVouPostingDate(dishonourDate);
					bean.setVouNo(vouNo);
				} else {
					bean.setAuthoFlg(AccountConstants.N.getValue());
					bean.setVouPostingDate(null);
				}

				bean.setVouTypeCpdId(accountVoucherEntryEntity.getVouTypeCpdId());
				bean.setDpDeptid(accountVoucherEntryEntity.getDpDeptid());
				bean.setFieldId(accountVoucherEntryEntity.getFieldId());

				for (final AccountVoucherEntryDetailsEntity entity : accountVoucherEntryEntity.getDetails()) {
					final String drcrCpdId = CommonMasterUtility.findLookUpCode(PrefixConstants.DCR, orgId,
							entity.getDrcrCpdId());
					if (drcrCpdId.equals(MainetConstants.BUG_HEAD_OPENING_BALANCE_MASTER.BUG_BAL_TYPE_ASSETS)) {
						// Debit side
						postDetailEntityDr = new VoucherReversePostDetailDTO();
						// BeanUtils.copyProperties(postDetailEntityDr, entity);
						// postDetailEntityDr.setVoudetId(entity.getVoudetId());
						long drId = CommonMasterUtility.getValueFromPrefixLookUp(
								PrefixConstants.AccountJournalVoucherEntry.DR, PrefixConstants.DCR, org).getLookUpId();
						postDetailEntityDr.setCreatedBy(entity.getCreatedBy());
						postDetailEntityDr.setDrcrCpdId(drId);
						postDetailEntityDr.setOrgId(entity.getOrgId());
						postDetailEntityDr.setSacHeadId(entity.getSacHeadId());
						postDetailEntityDr.setUpdatedBy(entity.getUpdatedBy());
						if (entity.getBudgetCode() != null && (entity.getBudgetCode().getprBudgetCodeid() != null)) {
							postDetailEntityDr.setBudgetCode(entity.getBudgetCode().getprBudgetCodeid());
						}
						// postDetailEntityDr.setFi04D1(entity.getFi04D1());
						postDetailEntityDr.setFieldCode(entity.getFieldCode());
						postDetailEntityDr.setFunctionCode(entity.getFunctionCode());
						postDetailEntityDr.setFundCode(entity.getFundCode());
						postDetailEntityDr.setLgIpMac(entity.getLgIpMac());
						postDetailEntityDr.setLgIpMacUpd(entity.getLgIpMacUpd());
						postDetailEntityDr.setLmodDate(entity.getLmodDate());
						// postDetailEntityDr.setMaster(entity.getMaster());
						postDetailEntityDr.setMaster(bean);
						postDetailEntityDr.setPrimaryHeadCode(entity.getPrimaryHeadCode());
						postDetailEntityDr.setSecondaryHeadCode(entity.getSecondaryHeadCode());
						postDetailEntityDr.setUpdatedDate(entity.getUpdatedDate());
						postDetailEntityDr.setVoudetAmt(chequeAmt);
						detEntity.add(postDetailEntityDr);
					} else if (drcrCpdId.equals(PrefixConstants.AccountJournalVoucherEntry.DR)) {

						long crId = CommonMasterUtility.getValueFromPrefixLookUp(
								MainetConstants.BUG_HEAD_OPENING_BALANCE_MASTER.BUG_BAL_TYPE_ASSETS,
								PrefixConstants.DCR, org).getLookUpId();
						// Credit side
						postDetailEntityCr = new VoucherReversePostDetailDTO();
						// postDetailEntityCr.setVoudetId(entity.getVoudetId());
						postDetailEntityCr.setCreatedBy(entity.getCreatedBy());
						postDetailEntityCr.setDrcrCpdId(crId);
						postDetailEntityCr.setOrgId(entity.getOrgId());
						postDetailEntityCr.setSacHeadId(entity.getSacHeadId());
						postDetailEntityCr.setUpdatedBy(entity.getUpdatedBy());
						if (entity.getBudgetCode() != null && (entity.getBudgetCode().getprBudgetCodeid() != null)) {
							postDetailEntityCr.setBudgetCode(entity.getBudgetCode().getprBudgetCodeid());
						}
						// postDetailEntityCr.setFi04D1(entity.getFi04D1());
						postDetailEntityCr.setFieldCode(entity.getFieldCode());
						postDetailEntityCr.setFunctionCode(entity.getFunctionCode());
						postDetailEntityCr.setFundCode(entity.getFundCode());
						postDetailEntityCr.setLgIpMac(entity.getLgIpMac());
						postDetailEntityCr.setLgIpMacUpd(entity.getLgIpMacUpd());
						postDetailEntityCr.setLmodDate(entity.getLmodDate());
						// postDetailEntityCr.setMaster(entity.getMaster());
						postDetailEntityDr.setMaster(bean);
						postDetailEntityCr.setPrimaryHeadCode(entity.getPrimaryHeadCode());
						postDetailEntityCr.setSecondaryHeadCode(entity.getSecondaryHeadCode());
						postDetailEntityCr.setUpdatedDate(entity.getUpdatedDate());
						postDetailEntityCr.setVoudetAmt(chequeAmt);
						// BeanUtils.copyProperties(postDetailEntityCr, entity);
						detEntity.add(postDetailEntityCr);
					}
				}
				bean.setDetails(detEntity);
				// LOGGER.info("voucherPostingService - voucherReversePosting - CV save:" +
				// bean);
				// accountVoucherEntryRepository.save(bean);
				ApplicationSession session = ApplicationSession.getInstance();
				String responseValidation = accountVoucherPostService.validateReversePostInput(bean);
				AccountVoucherEntryEntity response = null;
				if (responseValidation.isEmpty()) {
					response = accountVoucherPostService.voucherReversePosting(bean);
					if (response == null) {
						throw new IllegalArgumentException("Voucher Reverse Posting failed");
					}
				} else {
					LOGGER.error(session.getMessage("account.voucher.service.posting")
							+ session.getMessage("account.voucher.posting.improper.input") + responseValidation);
					throw new IllegalArgumentException(
							"Voucher Reverse Posting failed : proper data is not exist" + responseValidation);
				}
			}
		}
	}

	private String generateVoucherNo(final String voucherType, final long orgId, String dishonourDate) {
		Long finYearId = financialYearJpaRepository.getFinanciaYearIdByFromDate(Utility.stringToDate(dishonourDate));
		if (finYearId == null) {
			throw new NullPointerException(GENERATE_VOUCHER_NO_FIN_YEAR_ID);
		}
		Long voucherTypeId = voucherTypeId(voucherType, orgId);
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

	private void postReceiptReversalEntry(final Long receiptNo, final Date receiptDate,
			final AccountChequeDishonourDTO tbAcChequeDishonourDTO, final BigDecimal chequeAmt, final Long orgId,
			final long fieldId, final int langId, final Long empId, final String ipMacAddress, Long deptId)
			throws IllegalAccessException, InvocationTargetException {

		final Long voucherTypeId = CommonMasterUtility.lookUpIdByLookUpCodeAndPrefix(PrefixConstants.REV_SUB_CPD_VALUE,
				AccountPrefix.VOT.toString(), orgId);
		// final Long voucherSubTypeId =
		// CommonMasterUtility.lookUpIdByLookUpCodeAndPrefix(PrefixConstants.REV_SUB_CPD_VALUE,
		// AccountPrefix.TDP.toString(), orgId);
		Long voucherSubTypeId1 = CommonMasterUtility.lookUpIdByLookUpCodeAndPrefix(AccountConstants.DSR.toString(),
				AccountPrefix.TDP.toString(), orgId);
		Long voucherSubTypeId2 = CommonMasterUtility.lookUpIdByLookUpCodeAndPrefix(AccountConstants.RBI.toString(),
				AccountPrefix.TDP.toString(), orgId);
		Long voucherSubTypeId3 = CommonMasterUtility.lookUpIdByLookUpCodeAndPrefix(AccountConstants.RBP.toString(),
				AccountPrefix.TDP.toString(), orgId);
		Long voucherSubTypeId4 = CommonMasterUtility.lookUpIdByLookUpCodeAndPrefix(AccountConstants.RRE.toString(),
				AccountPrefix.TDP.toString(), orgId);
		List<Long> vouSubTypes = new ArrayList<>(0);
		vouSubTypes.add(voucherSubTypeId1);
		vouSubTypes.add(voucherSubTypeId2);
		vouSubTypes.add(voucherSubTypeId3);
		vouSubTypes.add(voucherSubTypeId4);
		long entryTypeId = CommonMasterUtility.lookUpIdByLookUpCodeAndPrefix(AccountJournalVoucherEntry.MAN,
				AccountJournalVoucherEntry.VET, orgId);
		if (entryTypeId == 0l) {
			throw new NullPointerException(
					"entryType id not found for for lookUpCode[MAN] from VET Prefix in cheque dishonour receipt reverse voucher posting.");
		}
		final List<AccountVoucherEntryEntity> tbAcVoucherEntryEntity = journalVoucherDao
				.getReceiptReversalVoucherDetails(receiptNo.toString(), receiptDate, voucherTypeId, orgId, deptId,
						vouSubTypes, entryTypeId);

		VoucherReversePostDTO bean = null;
		List<VoucherReversePostDetailDTO> detEntity = null;
		final Organisation org = new Organisation();
		org.setOrgid(orgId);

		for (final AccountVoucherEntryEntity acVoucherEntryEntity : tbAcVoucherEntryEntity) {

			bean = new VoucherReversePostDTO();
			detEntity = new ArrayList<>();
			VoucherReversePostDetailDTO postDetailEntityCr = null;
			VoucherReversePostDetailDTO postDetailEntityDr = null;

			// postDto.setVoucherDate(new Date());
			final String voucherType = CommonMasterUtility.findLookUpCode(AccountPrefix.VOT.toString(), orgId,
					acVoucherEntryEntity.getVouTypeCpdId());

			if (voucherType.equals(PrefixConstants.REV_SUB_CPD_VALUE)) {

				final LookUp voucherSubType = CommonMasterUtility.getLookUpFromPrefixLookUpValue(
						PrefixConstants.ChequeDishonour.RRE, PrefixConstants.REV_TYPE_CPD_VALUE, langId, org);
				final Long voucherSubTypeid = voucherSubType.getLookUpId();
				bean.setVouSubtypeCpdId(voucherSubTypeid);
				// changes by sanket
				Date dishonourDate = UtilityService
						.convertStringDateToDateFormat(tbAcChequeDishonourDTO.getDishonourDate());
				// changes by sanket
				bean.setVouDate(dishonourDate);
				bean.setVouReferenceNo(receiptNo.toString());
				bean.setNarration(tbAcChequeDishonourDTO.getRemarks());
				bean.setOrg(orgId);
				bean.setCreatedBy(empId);
				bean.setLmodDate(new Date());
				bean.setLgIpMac(ipMacAddress);
				// bean.setTemplateType(AccountConstants.PN.toString());
				bean.setEntryType(CommonMasterUtility.lookUpIdByLookUpCodeAndPrefix(
						MainetConstants.BILL_MASTER_COMMON.ENTRY_TYPE, AccountPrefix.VET.toString(), orgId));
				bean.setLmodDate(acVoucherEntryEntity.getLmodDate());
				bean.setUpdatedDate(acVoucherEntryEntity.getUpdatedDate());
				bean.setVouReferenceNoDate(receiptDate);
				// bean.setAuthoFlg(acVoucherEntryEntity.getAuthoFlg());
				bean.setPayerPayee(acVoucherEntryEntity.getPayerPayee());
				bean.setUpdatedby(acVoucherEntryEntity.getUpdatedby());
				String vouNo = generateVoucherNo(PrefixConstants.DirectPaymentEntry.PV, org.getOrgid(),
						tbAcChequeDishonourDTO.getDishonourDate());
				// bean.setVouNo(vouNo);

				// Autho Flag
				final LookUp payEntryMakerChecker = CommonMasterUtility.getLookUpFromPrefixLookUpValue(
						PrefixConstants.REV_SUB_CPD_VALUE, AccountPrefix.AUT.toString(), langId, org);
				if (payEntryMakerChecker.getLookUpCode() == null || payEntryMakerChecker.getLookUpCode().isEmpty()) {
					bean.setAuthoFlg(MainetConstants.MENU.Y);
					bean.setAuthoId(acVoucherEntryEntity.getAuthoId());
					bean.setAuthoDate(dishonourDate);
					bean.setAuthRemark(acVoucherEntryEntity.getAuthRemark());
					// changes by sanket
					bean.setVouPostingDate(dishonourDate);
					bean.setVouNo(vouNo);
				} else {
					bean.setAuthoFlg(AccountConstants.N.getValue());
					bean.setVouPostingDate(null);
				}

				bean.setVouTypeCpdId(CommonMasterUtility.lookUpIdByLookUpCodeAndPrefix(
						PrefixConstants.DirectPaymentEntry.PV, AccountPrefix.VOT.toString(), orgId));
				bean.setDpDeptid(acVoucherEntryEntity.getDpDeptid());
				bean.setFieldId(acVoucherEntryEntity.getFieldId());

				for (final AccountVoucherEntryDetailsEntity entity : acVoucherEntryEntity.getDetails()) {
					final String drcrCpdId = CommonMasterUtility.findLookUpCode(PrefixConstants.DCR, orgId,
							entity.getDrcrCpdId());
					if (drcrCpdId.equals(MainetConstants.BUG_HEAD_OPENING_BALANCE_MASTER.BUG_BAL_TYPE_ASSETS)) {
						// Debit side
						postDetailEntityDr = new VoucherReversePostDetailDTO();
						// BeanUtils.copyProperties(postDetailEntityDr, entity);
						// postDetailEntityDr.setVoudetId(entity.getVoudetId());
						long drId = CommonMasterUtility.getValueFromPrefixLookUp(
								PrefixConstants.AccountJournalVoucherEntry.DR, PrefixConstants.DCR, org).getLookUpId();
						postDetailEntityDr.setCreatedBy(entity.getCreatedBy());
						postDetailEntityDr.setDrcrCpdId(drId);
						postDetailEntityDr.setOrgId(entity.getOrgId());
						postDetailEntityDr.setSacHeadId(entity.getSacHeadId());
						postDetailEntityDr.setUpdatedBy(entity.getUpdatedBy());
						if (entity.getBudgetCode() != null && (entity.getBudgetCode().getprBudgetCodeid() != null)) {
							postDetailEntityDr.setBudgetCode(entity.getBudgetCode().getprBudgetCodeid());
						}
						// postDetailEntityDr.setFi04D1(entity.getFi04D1());
						postDetailEntityDr.setFieldCode(entity.getFieldCode());
						postDetailEntityDr.setFunctionCode(entity.getFunctionCode());
						postDetailEntityDr.setFundCode(entity.getFundCode());
						postDetailEntityDr.setLgIpMac(entity.getLgIpMac());
						postDetailEntityDr.setLgIpMacUpd(entity.getLgIpMacUpd());
						postDetailEntityDr.setLmodDate(entity.getLmodDate());
						// postDetailEntityDr.setMaster(entity.getMaster());
						postDetailEntityDr.setMaster(bean);
						postDetailEntityDr.setPrimaryHeadCode(entity.getPrimaryHeadCode());
						postDetailEntityDr.setSecondaryHeadCode(entity.getSecondaryHeadCode());
						postDetailEntityDr.setUpdatedDate(entity.getUpdatedDate());
						postDetailEntityDr.setVoudetAmt(entity.getVoudetAmt());
						detEntity.add(postDetailEntityDr);
					} else if (drcrCpdId.equals(PrefixConstants.AccountJournalVoucherEntry.DR)) {

						long crId = CommonMasterUtility.getValueFromPrefixLookUp(
								MainetConstants.BUG_HEAD_OPENING_BALANCE_MASTER.BUG_BAL_TYPE_ASSETS,
								PrefixConstants.DCR, org).getLookUpId();
						// Credit side
						postDetailEntityCr = new VoucherReversePostDetailDTO();
						// postDetailEntityCr.setVoudetId(entity.getVoudetId());
						postDetailEntityCr.setCreatedBy(entity.getCreatedBy());
						postDetailEntityCr.setDrcrCpdId(crId);
						postDetailEntityCr.setOrgId(entity.getOrgId());
						postDetailEntityCr.setSacHeadId(entity.getSacHeadId());
						postDetailEntityCr.setUpdatedBy(entity.getUpdatedBy());
						if (entity.getBudgetCode() != null && (entity.getBudgetCode().getprBudgetCodeid() != null)) {
							postDetailEntityCr.setBudgetCode(entity.getBudgetCode().getprBudgetCodeid());
						}
						// postDetailEntityCr.setFi04D1(entity.getFi04D1());
						postDetailEntityCr.setFieldCode(entity.getFieldCode());
						postDetailEntityCr.setFunctionCode(entity.getFunctionCode());
						postDetailEntityCr.setFundCode(entity.getFundCode());
						postDetailEntityCr.setLgIpMac(entity.getLgIpMac());
						postDetailEntityCr.setLgIpMacUpd(entity.getLgIpMacUpd());
						postDetailEntityCr.setLmodDate(entity.getLmodDate());
						// postDetailEntityCr.setMaster(entity.getMaster());
						postDetailEntityDr.setMaster(bean);
						postDetailEntityCr.setPrimaryHeadCode(entity.getPrimaryHeadCode());
						postDetailEntityCr.setSecondaryHeadCode(entity.getSecondaryHeadCode());
						postDetailEntityCr.setUpdatedDate(entity.getUpdatedDate());
						postDetailEntityCr.setVoudetAmt(entity.getVoudetAmt());
						// BeanUtils.copyProperties(postDetailEntityCr, entity);
						detEntity.add(postDetailEntityCr);
					}
				}
				bean.setDetails(detEntity);
				// LOGGER.info("voucherPostingService - voucherReversePosting -RV
				// AccountChequeDishonour:" + bean);
				// accountVoucherEntryRepository.save(bean);
				ApplicationSession session = ApplicationSession.getInstance();
				String responseValidation = accountVoucherPostService.validateReversePostInput(bean);
				AccountVoucherEntryEntity response = null;
				if (responseValidation.isEmpty()) {
					response = accountVoucherPostService.voucherReversePosting(bean);
					if (response == null) {
						throw new IllegalArgumentException("Voucher Reverse Posting failed");
					}
				} else {
					LOGGER.error(session.getMessage("account.voucher.service.posting")
							+ session.getMessage("account.voucher.posting.improper.input") + responseValidation);
					throw new IllegalArgumentException(
							"Voucher Reverse Posting failed : proper data is not exist" + responseValidation);
				}
			}
		}
	}

	private void postChequeDishonorChargesEntry(final AccountChequeDishonourDTO tbAcChequeDishonourDTO,
			final Long orgId, final long fieldId, final int langId, final Long empId, String chequeDisRefNo,
			Date chequeDisDate) {

		final VoucherPostDTO postDto = new VoucherPostDTO();
		List<VoucherPostDTO> postDtoList = new ArrayList<>();
		final List<VoucherPostDetailDTO> voucherDetails = new ArrayList<>();
		VoucherPostDetailDTO postDetailDtoCr = null;
		VoucherPostDetailDTO postDetailDtoDr = null;

		postDto.setVoucherType(AccountConstants.PV.toString());
		final Long voucherSubTypeId = CommonMasterUtility.lookUpIdByLookUpCodeAndPrefix(
				PrefixConstants.ChequeDishonour.DHC, AccountPrefix.TDP.toString(), orgId);
		Organisation org = new Organisation();
		org.setOrgid(orgId);
		final LookUp payEntryMakerChecker = CommonMasterUtility.getLookUpFromPrefixLookUpValue(
				PrefixConstants.REV_SUB_CPD_VALUE, AccountPrefix.AUT.toString(), langId, org);
		if (payEntryMakerChecker.getLookUpCode() == null || payEntryMakerChecker.getLookUpCode().isEmpty()) {
			// dto.setVoucherDate(tbReceiptMasEntity.getRmDate());
			postDto.setPayEntryMakerFlag(MainetConstants.Y_FLAG);
		} else {
			// dto.setVoucherDate(tbReceiptMasEntity.getCreatedDate());
		}
		postDto.setVoucherDate(UtilityService.convertStringDateToDateFormat(tbAcChequeDishonourDTO.getDishonourDate()));
		postDto.setVoucherSubTypeId(voucherSubTypeId);
		final Long departmentId = departmentService.getDepartmentIdByDeptCode(AccountConstants.AC.toString());
		postDto.setDepartmentId(departmentId);
		postDto.setVoucherReferenceNo(chequeDisRefNo);
		postDto.setVoucherReferenceDate(chequeDisDate);

		postDto.setNarration(tbAcChequeDishonourDTO.getRemarks());
		postDto.setPayerOrPayee(MainetConstants.AccountChequeDishonour.PAYMENT_ENTRY);// Vendor name
		postDto.setFieldId(fieldId);
		postDto.setOrgId(orgId);
		postDto.setCreatedBy(tbAcChequeDishonourDTO.getUserId());
		postDto.setCreatedDate(tbAcChequeDishonourDTO.getLmoddate());
		postDto.setAuthFlag(MainetConstants.MENU.Y);
		postDto.setLangId(Integer.valueOf(langId));
		postDto.setLgIpMac(tbAcChequeDishonourDTO.getLgIpMac());
		// postDto.setTemplateType(AccountConstants.PN.toString());
		postDto.setEntryType(MainetConstants.BILL_MASTER_COMMON.ENTRY_TYPE);

		// Bank Credit side
		postDetailDtoCr = new VoucherPostDetailDTO();
		postDetailDtoCr.setVoucherAmount(new BigDecimal(tbAcChequeDishonourDTO.getDishonourAmount()));
		final Long crId = CommonMasterUtility.lookUpIdByLookUpCodeAndPrefix(AccountConstants.CR.toString(),
				PrefixConstants.DCR, orgId);
		postDetailDtoCr.setDrCrId(crId);
		String bankAcId = tbAcChequeDishonourDTO.getBankAccount().replace(",", "");
		final Long sacHeadIdCr = getSacHeadId(Long.valueOf(bankAcId), tbAcChequeDishonourDTO.getOrgid());
		if (sacHeadIdCr == null) {
			throw new NullPointerException("The given bank account id - account head code is not available : "
					+ bankAcId + " orgId is : " + tbAcChequeDishonourDTO.getOrgid());
		}
		postDetailDtoCr.setSacHeadId(sacHeadIdCr);
		voucherDetails.add(postDetailDtoCr);

		postDetailDtoDr = new VoucherPostDetailDTO();
		final LookUp chequeLookup = CommonMasterUtility.getLookUpFromPrefixLookUpValue("Q", AccountPrefix.PAY.name(),
				UserSession.getCurrent().getLanguageId(), UserSession.getCurrent().getOrganisation());
		postDetailDtoDr.setPayModeId(chequeLookup.getLookUpId());
		postDetailDtoDr.setVoucherAmount(new BigDecimal(tbAcChequeDishonourDTO.getDishonourAmount()));
		final Long drId = CommonMasterUtility.lookUpIdByLookUpCodeAndPrefix(AccountConstants.DR.toString(),
				PrefixConstants.DCR, orgId);
		postDetailDtoDr.setDrCrId(drId);

		voucherDetails.add(postDetailDtoDr);

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

	public Long getSacHeadId(final Long bankAccountId, final Long orgId) {
		return contraEntryVoucherJpaRepository.getSacHeadIdByBankAccountId(bankAccountId, orgId);
	}
	
	@Transactional
	private void sendSmsAndEmail(AccountChequeDishonourDTO dto, TbServiceReceiptMasEntity receiptMas) {
		final SMSAndEmailDTO smsdto = new SMSAndEmailDTO();
		Organisation org = new Organisation();
		org.setOrgid(dto.getOrgid());
		if (receiptMas != null) {
			smsdto.setMobnumber(receiptMas.getMobileNumber());
			smsdto.setEmail(receiptMas.getEmailId());
			smsdto.setReferenceNo(String.valueOf(dto.getChequeddno())); //cheque number
			smsdto.setOrganizationName(UserSession.getCurrent().getOrganisation().getONlsOrgname());//orgnization name
			smsdto.setDueDt(dto.getDishonourDate());  //Dishonor Date
			smsdto.setAmount(Double.valueOf(dto.getDishonourAmount()));//Dishonor Amount
			smsdto.setUserId(dto.getUserId());
			smsdto.setOrgId(dto.getOrgid());
			iSMSAndEmailService.sendEmailSMS(MainetConstants.DEPT_SHORT_NAME.ACCOUNT, "AccountChequeDishonour.html",
					PrefixConstants.SMS_EMAIL_ALERT_TYPE.TASK_NOTIFICATION, smsdto, org, dto.getLangId());
		}
	}
}
