
package com.abm.mainet.account.service;

import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.annotation.Resource;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import com.abm.mainet.account.dao.AccountReceiptEntryDao;
import com.abm.mainet.account.domain.AccountVoucherEntryEntity;
import com.abm.mainet.account.domain.VoucherTemplateDetailEntity;
import com.abm.mainet.account.domain.VoucherTemplateMasterEntity;
import com.abm.mainet.account.dto.AccountChequeOrCashDepositeBean;
import com.abm.mainet.account.dto.AccountDepositBean;
import com.abm.mainet.account.dto.AccountVoucherCommPostingDetailDto;
import com.abm.mainet.account.dto.AccountVoucherCommPostingMasterDto;
import com.abm.mainet.account.dto.AdvanceEntryDTO;
import com.abm.mainet.account.dto.BankMasterDto;
import com.abm.mainet.account.dto.VoucherReversalDTO;
import com.abm.mainet.account.dto.VoucherTemplateDTO;
import com.abm.mainet.account.mapper.AccountReceiptEntryServiceMapper;
import com.abm.mainet.account.repository.AccountReceiptEntryJpaRepository;
import com.abm.mainet.account.repository.AccountReceiptHeadPostingJpaRepository;
import com.abm.mainet.account.repository.AdvanceEntryRepository;
import com.abm.mainet.account.repository.BudgetHeadRepository;
import com.abm.mainet.account.repository.VoucherTemplateRepository;
import com.abm.mainet.common.constant.MainetConstants;
import com.abm.mainet.common.constant.MainetConstants.AccountConstants;
import com.abm.mainet.common.constant.MainetConstants.AccountReceiptEntry;
import com.abm.mainet.common.constant.PrefixConstants;
import com.abm.mainet.common.constant.PrefixConstants.AccountPrefix;
import com.abm.mainet.common.dao.IDepartmentDAO;
import com.abm.mainet.common.domain.BankAccountMasterEntity;
import com.abm.mainet.common.domain.Employee;
import com.abm.mainet.common.domain.Organisation;
import com.abm.mainet.common.dto.CFCSchedulingCounterDet;
import com.abm.mainet.common.dto.TbAcVendormaster;
import com.abm.mainet.common.integration.acccount.domain.TbServiceReceiptMasEntity;
import com.abm.mainet.common.integration.acccount.domain.TbSrcptFeesDetEntity;
import com.abm.mainet.common.integration.acccount.domain.TbSrcptModesDetEntity;
import com.abm.mainet.common.integration.acccount.dto.AccountBudgetCodeBean;
import com.abm.mainet.common.integration.acccount.dto.AccountReceiptDTO;
import com.abm.mainet.common.integration.acccount.dto.AccountReceiptExternalDto;
import com.abm.mainet.common.integration.acccount.dto.AccountReceiptFeesExternalDto;
import com.abm.mainet.common.integration.acccount.dto.BankAccountMasterDto;
import com.abm.mainet.common.integration.acccount.dto.SecondaryheadMaster;
import com.abm.mainet.common.integration.acccount.dto.SrcptFeesDetDTO;
import com.abm.mainet.common.integration.acccount.dto.SrcptModesDetDTO;
import com.abm.mainet.common.integration.acccount.dto.TbServiceReceiptMasBean;
import com.abm.mainet.common.integration.acccount.dto.TbSrcptFeesDetBean;
import com.abm.mainet.common.integration.acccount.dto.TbSrcptModesDetBean;
import com.abm.mainet.common.integration.acccount.dto.VoucherPostDTO;
import com.abm.mainet.common.integration.acccount.dto.VoucherPostDetailDTO;
import com.abm.mainet.common.integration.acccount.service.AccountFieldMasterService;
import com.abm.mainet.common.integration.acccount.service.SecondaryheadMasterService;
import com.abm.mainet.common.master.dto.BankMasterDTO;
import com.abm.mainet.common.master.repository.TbFinancialyearJpaRepository;
import com.abm.mainet.common.master.repository.TbTaxMasJpaRepository;
import com.abm.mainet.common.master.service.BankMasterService;
import com.abm.mainet.common.master.service.DepartmentService;
import com.abm.mainet.common.master.service.TbAcVendormasterService;
import com.abm.mainet.common.master.service.TbCfcApplicationMstService;
import com.abm.mainet.common.repository.TbAcVendormasterJpaRepository;
import com.abm.mainet.common.service.IEmployeeService;
import com.abm.mainet.common.service.IOrganisationService;
import com.abm.mainet.common.service.IReceiptEntryService;
import com.abm.mainet.common.service.TbBankmasterService;
import com.abm.mainet.common.utility.ApplicationContextProvider;
import com.abm.mainet.common.utility.ApplicationSession;
import com.abm.mainet.common.utility.CommonMasterUtility;
import com.abm.mainet.common.utility.LookUp;
import com.abm.mainet.common.utility.SeqGenFunctionUtility;
import com.abm.mainet.common.utility.UserSession;
import com.abm.mainet.common.utility.Utility;
import com.google.common.util.concurrent.AtomicDouble;

/**
 * @author dharmendra.chouhan
 *
 */
@Component
public class AccountReceiptEntryServiceImpl implements AccountReceiptEntryService {

	@Resource
	private SeqGenFunctionUtility seqGenFunctionUtility;

	@Resource
	private AccountReceiptEntryJpaRepository accountReceiptEntrRrepository;

	@Resource
	private AccountReceiptEntryDao accountReceiptEntryDao;

	@Resource
	private AccountVoucherPostService voucherPostService;

	@Resource
	private AccountReceiptEntryServiceMapper accountReceiptEntryServiceMapper;

	@Resource
	private DepartmentService departmentService;

	@Resource
	private TbTaxMasJpaRepository tbTaxMasJpaRepository;

	@Resource
	private AccountVoucherPostService accountVoucherPostService;

	@Resource
	private TbAcVendormasterService tbAcVendormasterService;
	@Resource
	private BankAccountService bankAccountService;
	@Resource
	private TbFinancialyearJpaRepository financialYearJpaRepository;

	@Resource
	private AccountContraVoucherEntryService accountContraVoucherEntryService;
	@Resource
	private VoucherTemplateService voucherTemplateService;
	@Resource
	AccountReceiptHeadPostingJpaRepository accountReceiptHeadJpaRepository;
	@Resource
	private TbAcVendormasterJpaRepository tbAcVendormasterJpaRepository;

	private static final Logger LOGGER = Logger.getLogger(AccountVoucherPostServiceImpl.class);

	@Resource
	private BudgetHeadRepository budgetHeadRepository;

	@Resource
	private AccountDepositService depositService;
	@Resource
	private AdvanceEntryRepository advanceEntryRepository;
	@Resource
	private AccountChequeOrCashDepositeService chequeOrCashService;
	@Resource
	private TbBankmasterService tbbankAccountService;

	@Autowired
	private IDepartmentDAO departmentDAO;
	@Resource
	private VoucherTemplateRepository voucherTemplateRepository;
	@Resource
	BudgetCodeService budgetCodeService;
	
	@Resource
	private IReceiptEntryService iReceiptEntryService;

	@Autowired
	private TbCfcApplicationMstService tbCfcservice;
	
	@Autowired
    private IOrganisationService iOrganisationService;

	private static final String FOR_DEP_REC_VED_CODE_MANDATORY = "For Deposit receipt entry vendor selection is mandatory!";
	private static final String GENERATE_RECEIPT_NO_FIN_YEAR_ID = "Receipt sequence number generation, The financial year id is getting null value";

	@Override
	@Transactional(readOnly = true)
	public List<TbServiceReceiptMasBean> findAll(final Long orgId, final BigDecimal rmAmount, final Long rmRcptno,
			final String rmReceivedfrom, final Date rmDate) {

		final Iterable<TbServiceReceiptMasEntity> entities = accountReceiptEntryDao.getReceiptDetail(orgId, rmAmount,
				rmRcptno, rmReceivedfrom, rmDate);

		List<TbServiceReceiptMasBean> beans = null;
		if (entities != null) {
			beans = new ArrayList<>();
			for (final TbServiceReceiptMasEntity tbServiceReceiptMasEntity : entities) {
				//Defect #86097
				//Defect #146865
				if(!tbServiceReceiptMasEntity.getReceiptFeeDetail().isEmpty() &&tbServiceReceiptMasEntity.getReceiptFeeDetail().get(0).getSacHeadId() != null) {
				//if ((tbServiceReceiptMasEntity.getReceiptDelFlag() == null
					//	|| tbServiceReceiptMasEntity.getReceiptDelFlag().isEmpty())) {
					if ((tbServiceReceiptMasEntity.getReceiptTypeFlag() != null
							&& !tbServiceReceiptMasEntity.getReceiptTypeFlag().isEmpty()))
						if (tbServiceReceiptMasEntity.getReceiptTypeFlag().trim().equals("M")
								|| tbServiceReceiptMasEntity.getReceiptTypeFlag().trim().equals("R")
								|| tbServiceReceiptMasEntity.getReceiptTypeFlag().trim().equals("A")
								|| tbServiceReceiptMasEntity.getReceiptTypeFlag().trim().equals("P")
								|| tbServiceReceiptMasEntity.getReceiptTypeFlag().trim().equals("LNR")
								|| tbServiceReceiptMasEntity.getReceiptTypeFlag().trim().equals("INV")
								|| tbServiceReceiptMasEntity.getReceiptTypeFlag().trim().equals("GRT")
								) {
							
							TbServiceReceiptMasBean receptMasBean = accountReceiptEntryServiceMapper
							.mapTbServiceReceiptMasEntityToTbServiceReceiptMasBean(tbServiceReceiptMasEntity);
							 TbSrcptModesDetBean modeBean = new TbSrcptModesDetBean();
							 BeanUtils.copyProperties(tbServiceReceiptMasEntity.getReceiptModeDetail().get(0),modeBean);
							 receptMasBean.setReceiptModeDetailList(modeBean);
							 
							beans.add(receptMasBean);
//R A P M ?
						//}
				}
			}
          }
		}
		return beans;
	}

	@Override
	@Transactional
	public TbServiceReceiptMasBean create(final TbServiceReceiptMasBean tbServiceReceiptMasBean, final long fieldId)
			throws Exception {
		final TbServiceReceiptMasEntity receiptMasEntity = new TbServiceReceiptMasEntity();
		AccountChequeOrCashDepositeBean bean = null;
		if (tbServiceReceiptMasBean != null) {
			final Date curDate = new Date();

			final long orgid = tbServiceReceiptMasBean.getOrgId();

			tbServiceReceiptMasBean.setRmDate(new Date());

			final Long empId = tbServiceReceiptMasBean.getCreatedBy();
			final String macAddress = tbServiceReceiptMasBean.getLgIpMac();
			tbServiceReceiptMasBean.getLangId();

			accountReceiptEntryServiceMapper
					.mapTbServiceReceiptMasBeanToTbServiceReceiptMasEntity(tbServiceReceiptMasBean, receiptMasEntity);
			receiptMasEntity.setRmReceiptcategoryId(tbServiceReceiptMasBean.getRecCategoryTypeId());
			List<TbSrcptModesDetEntity> modeList = new ArrayList<>();
			TbSrcptModesDetEntity dto=new TbSrcptModesDetEntity();
			dto.setCpdFeemode(tbServiceReceiptMasBean.getReceiptModeDetailList().getCpdFeemode());
			dto.setRdAmount(tbServiceReceiptMasBean.getReceiptModeDetailList().getRdAmount());
			dto.setBaAccountid(tbServiceReceiptMasBean.getReceiptModeDetailList().getBaAccountid());
			dto.setRdChequedddate(tbServiceReceiptMasBean.getReceiptModeDetailList().getRdChequedddate());
			dto.setTranRefNumber(tbServiceReceiptMasBean.getReceiptModeDetailList().getTranRefNumber());
			dto.setRdChequeddno(tbServiceReceiptMasBean.getReceiptModeDetailList().getRdChequeddno());
			dto.setCbBankid(tbServiceReceiptMasBean.getReceiptModeDetailList().getCbBankid());
			try {
			LookUp lkp=CommonMasterUtility.getNonHierarchicalLookUpObject(tbServiceReceiptMasBean.getReceiptModeDetailList().getCpdFeemode());
			if(lkp!=null && (lkp.getLookUpCode()!=null && (lkp.getLookUpCode().equalsIgnoreCase(MainetConstants.FlagB)||lkp.getLookUpCode().equalsIgnoreCase(MainetConstants.MENU.POS)||lkp.getLookUpCode().equalsIgnoreCase(MainetConstants.PAYMODE.WEB)))) {
				List<Object[]> objList=tbbankAccountService.getBankAccountPayment(orgid, tbServiceReceiptMasBean.getReceiptModeDetailList().getBaAccountid());
				if(CollectionUtils.isNotEmpty(objList)&&objList.get(0)[6]!=null) {
					dto.setCbBankid(Long.valueOf(objList.get(0)[6].toString()));
					tbServiceReceiptMasBean.getReceiptModeDetailList().setCbBankid(dto.getCbBankid());
				}
			}}
			catch (Exception e) {
				LOGGER.error("Error at the time of fetching ULB bank Data");
			}
			if (StringUtils.isNotBlank(tbServiceReceiptMasBean.getReceiptModeDetailList().getTranRefNumber())
					&& StringUtils.isNumeric(tbServiceReceiptMasBean.getReceiptModeDetailList().getTranRefNumber())) {
				dto.setRdChequeddno(
						Long.valueOf(tbServiceReceiptMasBean.getReceiptModeDetailList().getTranRefNumber()));
			}
			//#149536
			if (tbServiceReceiptMasBean.getReceiptModeDetailList() !=null && StringUtils.isNotEmpty(tbServiceReceiptMasBean.getReceiptModeDetailList().getBaAccountidDesc()))
			dto.setRdActNo(tbServiceReceiptMasBean.getReceiptModeDetailList().getBaAccountidDesc());
			//BeanUtils.copyProperties(tbServiceReceiptMasBean.getReceiptModeDetailList(), dto);
			//dto.setCpdFeemode(cpdFeemode);
			modeList.add(dto);
			
			receiptMasEntity.setReceiptModeDetail(modeList);
			receiptMasEntity.getReceiptModeDetail().get(0).setRmRcptid(receiptMasEntity);
			receiptMasEntity.getReceiptModeDetail().get(0).setOrgId(orgid);
			receiptMasEntity.getReceiptModeDetail().get(0).setCreatedBy(empId);
			receiptMasEntity.getReceiptModeDetail().get(0).setLgIpMac(macAddress);
			receiptMasEntity.getReceiptModeDetail().get(0).setCreatedDate(new Date());

			List<LookUp> lookUpList = CommonMasterUtility.lookUpListByPrefix(
					PrefixConstants.LookUpPrefix.PAY_AT_COUNTER, tbServiceReceiptMasBean.getOrgId());

			List<LookUp> lookUpLists = lookUpList.stream()
					.filter(lookUps -> lookUps.getLookUpCode() != null
							&& lookUps.getLookUpCode().equals(MainetConstants.CommonConstants.Q)
							|| lookUps.getLookUpCode().equals(MainetConstants.MENU.D))
					.collect(Collectors.toList());

			LookUp lookUp = CommonMasterUtility.getValueFromPrefixLookUp(PrefixConstants.LookUpPrefix.RND,
					PrefixConstants.LookUpPrefix.CLR, new Organisation(tbServiceReceiptMasBean.getOrgId()));

			if (lookUpLists != null && !lookUpLists.isEmpty()) {
				if ((Long.valueOf(lookUpLists.get(0).getLookUpId()))
						.equals(receiptMasEntity.getReceiptModeDetail().get(0).getCpdFeemode())
						|| (Long.valueOf(lookUpLists.get(1).getLookUpId()))
								.equals(receiptMasEntity.getReceiptModeDetail().get(0).getCpdFeemode())) {
					if (lookUp != null) {
						receiptMasEntity.getReceiptModeDetail().get(0).setCheckStatus(lookUp.getLookUpId());
						if (lookUp.getOtherField() != null && !lookUp.getOtherField().isEmpty()) {
							receiptMasEntity.getReceiptModeDetail().get(0).setRdSrChkDis(lookUp.getOtherField());
						}
					}
				}
			}

			final Date rdchequedddate = Utility
					.stringToDate(tbServiceReceiptMasBean.getReceiptModeDetailList().getRdChequedddatetemp());
			receiptMasEntity.getReceiptModeDetail().get(0).setRdChequedddate(rdchequedddate);

			final Date tranrefdate = Utility
					.stringToDate(tbServiceReceiptMasBean.getReceiptModeDetailList().getTranRefDatetemp());
			receiptMasEntity.getReceiptModeDetail().get(0).setTranRefDate(tranrefdate);
			receiptMasEntity.setFieldId(fieldId);
			receiptMasEntity.setRefId(tbServiceReceiptMasBean.getPrAdvEntryId());
			// receiptMasEntity.setFieldId(fieldId);
			Objects.requireNonNull(tbServiceReceiptMasBean.getTransactionDate(),
					ApplicationSession.getInstance().getMessage("account.bill.entry.service.transactiondate")
							+ tbServiceReceiptMasBean);
			receiptMasEntity.setRmDate(Utility.stringToDate(tbServiceReceiptMasBean.getTransactionDate()));

			String recCategoryType = CommonMasterUtility.findLookUpCode(
					MainetConstants.BILL_MASTER_COMMON.PREFIX_CODE_RV,
					UserSession.getCurrent().getOrganisation().getOrgid(),
					tbServiceReceiptMasBean.getRecCategoryTypeId());
			if (recCategoryType != null && !recCategoryType.isEmpty()) {
				receiptMasEntity.setReceiptTypeFlag(recCategoryType);
			} else {
				receiptMasEntity.setReceiptTypeFlag(MainetConstants.FlagM);
			}
 
			// receipt voucher posting validate to given VoucherPostDTO
			receiptVoucherPostingValidation(receiptMasEntity, tbServiceReceiptMasBean.getLangId(), fieldId);

			tbServiceReceiptMasBean.getReceiptModeDetailList()
					.setCpdFeemodeCode(CommonMasterUtility.findLookUpCode(PrefixConstants.LookUpPrefix.PAY_AT_COUNTER,
							tbServiceReceiptMasBean.getOrgId(),
							tbServiceReceiptMasBean.getReceiptModeDetailList().getCpdFeemode()));
			if (tbServiceReceiptMasBean.getReceiptModeDetailList().getCpdFeemodeCode()
					.equals(PrefixConstants.WATERMODULEPREFIX.RT)
					|| tbServiceReceiptMasBean.getReceiptModeDetailList().getCpdFeemodeCode().equals(MainetConstants.MENU.N)
					|| tbServiceReceiptMasBean.getReceiptModeDetailList().getCpdFeemodeCode()
							.equals(PrefixConstants.PaymentMode.BANK)) {
				// bank deposit slip receipt integration voucher posting validate to given
				// VoucherPostDTO
				bankDepositSlipVoucherPostingValidation(tbServiceReceiptMasBean, fieldId);
			}
			final Long receiptNumber = getReceiptNumber(orgid, tbServiceReceiptMasBean.getTransactionDate());
			tbServiceReceiptMasBean.setRmRcptno(receiptNumber);
			
			 
			
			for (final TbSrcptFeesDetEntity receiptDetail : receiptMasEntity.getReceiptFeeDetail()) {
				receiptDetail.setRmRcptid(receiptMasEntity);
				receiptDetail.setOrgId(orgid);
				receiptDetail.setCreatedDate(curDate);
				receiptDetail.setLgIpMac(macAddress);
				receiptDetail.setCreatedBy(empId);
				// depositEntry(tbServiceReceiptMasBean, receiptDetail);
			}
			CFCSchedulingCounterDet  det=tbCfcservice.getCounterDetByEmpId(empId, orgid);
			if(det!=null) {
				tbCfcservice.setRecieptCfcAndCounterCount(det);
				receiptMasEntity.setCfcColCenterNo(det.getCollcntrno());
				receiptMasEntity.setCfcColCounterNo(det.getCounterno());
				receiptMasEntity.setRmCFfcCntrNo(det.getDwzId1());
			}
			receiptMasEntity.setRmRcptno(receiptNumber);
			TbServiceReceiptMasEntity tbServiceReceiptMasSave = accountReceiptEntrRrepository.save(receiptMasEntity);
			for (final TbSrcptFeesDetEntity receiptDetail : receiptMasEntity.getReceiptFeeDetail()) {
				receiptDetail.setRmRcptid(tbServiceReceiptMasSave);
				depositEntry(tbServiceReceiptMasBean, receiptDetail);
			}
			// voucher posting
			voucherPosting(tbServiceReceiptMasSave, tbServiceReceiptMasBean.getLangId(), fieldId);
			tbServiceReceiptMasBean.getReceiptModeDetailList()
					.setCpdFeemodeCode(CommonMasterUtility.findLookUpCode(PrefixConstants.LookUpPrefix.PAY_AT_COUNTER,
							tbServiceReceiptMasBean.getOrgId(),
							tbServiceReceiptMasBean.getReceiptModeDetailList().getCpdFeemode()));
			if (tbServiceReceiptMasBean.getReceiptModeDetailList().getCpdFeemodeCode()
					.equals(PrefixConstants.WATERMODULEPREFIX.RT)
					|| tbServiceReceiptMasBean.getReceiptModeDetailList().getCpdFeemodeCode().equals(MainetConstants.MENU.N)
					|| tbServiceReceiptMasBean.getReceiptModeDetailList().getCpdFeemodeCode()
							.equals(PrefixConstants.PaymentMode.BANK)) {
				bean = bankDepositEntry(tbServiceReceiptMasBean, fieldId);
				accountReceiptHeadJpaRepository.updateReceiptWiseDepositId(tbServiceReceiptMasSave.getRmRcptid(),
						bean.getDepositeSlipId());
			}
			if ((tbServiceReceiptMasBean.getAdvanceFlag() != null)
					&& !tbServiceReceiptMasBean.getAdvanceFlag().isEmpty()) {
				if (tbServiceReceiptMasBean.getAdvanceFlag().equals(MainetConstants.MENU.Y)) {
					final Long advId = tbServiceReceiptMasBean.getPrAdvEntryId();
					BigDecimal balanceAmount = null;
					for (final TbSrcptFeesDetBean receiptDetail : tbServiceReceiptMasBean.getReceiptFeeDetail()) {
						final BigDecimal feeAmount = receiptDetail.getRfFeeamount();
						final BigDecimal balAmt = new BigDecimal(tbServiceReceiptMasBean.getBalanceAmount());
						balanceAmount = balAmt.subtract(feeAmount);
					}

					advanceEntryRepository.updateAdvanceBalanceAmountInAdvanceTable(advId, balanceAmount, orgid);
					// User Story #6673 9. After receipt entry against advance updated date ,
					// updated by and IP addresss not
					// updated in advance master table.
					AdvanceEntryDTO advEntryDto = new AdvanceEntryDTO();
					advEntryDto.setPrAdvEntryId(advId);
					advEntryDto.setUpdatedBy(empId);
					advEntryDto.setLgIpMacUpd(macAddress);
					advEntryDto.setUpdatedDate(curDate);
					advanceEntryRepository.updateAdvanceEntryAuditDetails(advEntryDto, orgid);
				}
			}
			 TbServiceReceiptMasBean receptMasBean = accountReceiptEntryServiceMapper
					.mapTbServiceReceiptMasEntityToTbServiceReceiptMasBean(tbServiceReceiptMasSave);
			 TbSrcptModesDetBean modeBean = new TbSrcptModesDetBean();
			 BeanUtils.copyProperties(tbServiceReceiptMasSave.getReceiptModeDetail().get(0),modeBean);
			 receptMasBean.setReceiptModeDetailList(modeBean);
			 
			 if (Utility.isEnvPrefixAvailable(UserSession.getCurrent().getOrganisation(),MainetConstants.ENV_SKDCL)){
				 receptMasBean.setRmReceiptNo(iReceiptEntryService.getCustomReceiptNo(tbServiceReceiptMasBean.getDpDeptId(), receiptNumber));
               //#149536
				 if (receiptMasEntity.getReceiptModeDetail() != null && receiptMasEntity.getReceiptModeDetail().get(0).getRdActNo() != null)
				 receptMasBean.getReceiptModeDetailList().setBaAccountidDesc(receiptMasEntity.getReceiptModeDetail().get(0).getRdActNo());
			 }
			 return receptMasBean;
		}
		return tbServiceReceiptMasBean;
	}

	private void receiptVoucherPostingValidation(TbServiceReceiptMasEntity tbReceiptMasEntity, int langId,
			long fieldId) {
		LOGGER.info("Process for account receipt voucher posting validation:" + tbReceiptMasEntity + langId + fieldId);

		final VoucherPostDTO dto = new VoucherPostDTO();
		List<VoucherPostDTO> postDtoList = new ArrayList<>();
		dto.setCreatedBy(tbReceiptMasEntity.getCreatedBy());
		dto.setCreatedDate(tbReceiptMasEntity.getCreatedDate());
		dto.setDepartmentId(tbReceiptMasEntity.getDpDeptId());
		dto.setFieldId(tbReceiptMasEntity.getFieldId());
		dto.setLgIpMac(tbReceiptMasEntity.getLgIpMac());
		dto.setNarration(tbReceiptMasEntity.getRmNarration());
		dto.setOrgId(tbReceiptMasEntity.getOrgId());
		dto.setPayerOrPayee(tbReceiptMasEntity.getRmReceivedfrom());
		Organisation org = new Organisation();
		org.setOrgid(tbReceiptMasEntity.getOrgId());
		final LookUp payEntryMakerChecker = CommonMasterUtility.getLookUpFromPrefixLookUpValue(
				PrefixConstants.REV_SUB_CPD_VALUE, AccountPrefix.AUT.toString(), langId, org);
		if (payEntryMakerChecker.getLookUpCode() == null || payEntryMakerChecker.getLookUpCode().isEmpty()) {
			// dto.setVoucherDate(tbReceiptMasEntity.getRmDate());
			dto.setPayEntryMakerFlag(MainetConstants.Y_FLAG);
		} else {
			// dto.setVoucherDate(tbReceiptMasEntity.getCreatedDate());
		}
		dto.setVoucherDate(tbReceiptMasEntity.getRmDate());
		// dto.setVoucherReferenceNo(tbReceiptMasEntity.getRmRcptno().toString());
		dto.setFieldId(fieldId);
		Organisation organ = iOrganisationService.getOrganisationById(dto.getOrgId());
		dto.setEntryType(MainetConstants.BILL_MASTER_COMMON.ENTRY_TYPE);
		if(Utility.isEnvPrefixAvailable(organ, MainetConstants.ENV_SUDA))
			dto.setEntryType(MainetConstants.AGENCY.AUT);	
		dto.setVoucherSubTypeId(CommonMasterUtility.lookUpIdByLookUpCodeAndPrefix(PrefixConstants.REV_SUB_CPD_VALUE,
				PrefixConstants.REV_TYPE_CPD_VALUE, tbReceiptMasEntity.getOrgId()));
		final List<VoucherPostDetailDTO> dtoListCr = new ArrayList<>();
		for (final TbSrcptFeesDetEntity tbServiceReceiptMasEntityList : tbReceiptMasEntity.getReceiptFeeDetail()) {
			final VoucherPostDetailDTO dtoListFee = new VoucherPostDetailDTO();
			dtoListFee.setSacHeadId(tbServiceReceiptMasEntityList.getSacHeadId());
			dtoListFee.setVoucherAmount(tbServiceReceiptMasEntityList.getRfFeeamount());
			dtoListCr.add(dtoListFee);
		}
		final VoucherPostDetailDTO dtoListMode = new VoucherPostDetailDTO();
		dtoListMode.setPayModeId(tbReceiptMasEntity.getReceiptModeDetail().get(0).getCpdFeemode());
		dtoListMode.setVoucherAmount(tbReceiptMasEntity.getReceiptModeDetail().get(0).getRdAmount());
		dtoListCr.add(dtoListMode);
		dto.setVoucherDetails(dtoListCr);

		  if (Utility.isEnvPrefixAvailable(organ, MainetConstants.ENV_TSCL)) {
		dto.setDepartmentId(departmentService.getDepartment(AccountConstants.AC.getValue(),
				MainetConstants.CommonConstants.ACTIVE).getDpDeptid());
		  }
		// set all data
		postDtoList.add(dto);
		List<String> responseValidation = accountVoucherPostService.checkVoucherPostValidateInput(postDtoList);
		if (responseValidation.size() > 0) {
			throw new NullPointerException(
					"improper input parameter for VoucherPostDTO in receipt entry -> " + responseValidation);
		}
	}

	private void bankDepositSlipVoucherPostingValidation(TbServiceReceiptMasBean tbServiceReceiptMasBean, long fieldId)
			throws ParseException {
		LOGGER.info("Process for bank deposit slip receipt integration account voucher posting validation:"
				+ tbServiceReceiptMasBean + fieldId);

		final VoucherPostDTO postDto = new VoucherPostDTO();
		List<VoucherPostDTO> postDtoList = new ArrayList<>();
		final List<VoucherPostDetailDTO> voucherDetails = new ArrayList<>();
		VoucherPostDetailDTO postDetailDtoCr = null;
		VoucherPostDetailDTO postDetailDtoDr = null;

		Organisation org = new Organisation();
		org.setOrgid(tbServiceReceiptMasBean.getOrgId());
		final LookUp payEntryMakerChecker = CommonMasterUtility.getLookUpFromPrefixLookUpValue(
				PrefixConstants.ContraVoucherEntry.CV, AccountPrefix.AUT.toString(),
				tbServiceReceiptMasBean.getLangId(), org);
		if (payEntryMakerChecker.getLookUpCode() == null || payEntryMakerChecker.getLookUpCode().isEmpty()) {
			// postDto.setVoucherDate(Utility.stringToDate(bean.getDepositeSlipDate()));
			postDto.setPayEntryMakerFlag(MainetConstants.Y_FLAG);
		} else {
			// postDto.setVoucherDate(bean.getClearingDate());
		}

		// postDto.setVoucherDate(new Date());
		String chkDate = "";
		if (tbServiceReceiptMasBean.getRecProperyTaxFlag() != null
				&& !tbServiceReceiptMasBean.getRecProperyTaxFlag().isEmpty()) {
			chkDate = Utility.dateToString(tbServiceReceiptMasBean.getRmDate());
		} else {
			chkDate = tbServiceReceiptMasBean.getTransactionDate();
		}
		postDto.setVoucherDate(Utility.stringToDate(chkDate));

		final Long voucherSubTypeId = CommonMasterUtility.lookUpIdByLookUpCodeAndPrefix(
				PrefixConstants.ChequeDishonour.DS, PrefixConstants.REV_TYPE_CPD_VALUE,
				tbServiceReceiptMasBean.getOrgId());
		postDto.setVoucherSubTypeId(voucherSubTypeId);

		postDto.setDepartmentId(departmentDAO.getDepartmentIdByDeptCode(MainetConstants.RECEIPT_MASTER.Module,
				PrefixConstants.STATUS_ACTIVE_PREFIX));

		// postDto.setVoucherReferenceNo(bean.getDepositeSlipNo());

		postDto.setNarration(MainetConstants.AccountChequeOrCash.DEPOSIT_SLIP_ENTRY);
		postDto.setFieldId(fieldId);
		postDto.setOrgId(tbServiceReceiptMasBean.getOrgId());
		postDto.setCreatedBy(tbServiceReceiptMasBean.getCreatedBy());
		postDto.setCreatedDate(new Date());
		postDto.setLangId(tbServiceReceiptMasBean.getLangId());
		postDto.setLgIpMac(Utility.getMacAddress());
		postDto.setEntryType(MainetConstants.BILL_MASTER_COMMON.ENTRY_TYPE);

		// Credit side
		postDetailDtoCr = new VoucherPostDetailDTO();
		postDetailDtoCr.setVoucherAmount(tbServiceReceiptMasBean.getReceiptModeDetailList().getRdAmount());
		final Long depositTypeId = CommonMasterUtility.lookUpIdByLookUpCodeAndPrefix(
				tbServiceReceiptMasBean.getReceiptModeDetailList().getCpdFeemodeCode()
						.replace(MainetConstants.operator.COMMA, MainetConstants.WHITE_SPACE),
				MainetConstants.CHEQUE_DISHONOUR.PAY, tbServiceReceiptMasBean.getOrgId());
		LOGGER.info("getting PaymentMode Data -:" + depositTypeId + tbServiceReceiptMasBean.getOrgId());
		postDetailDtoCr.setPayModeId(depositTypeId); // paymentMode
		voucherDetails.add(postDetailDtoCr);

		// Debit side
		postDetailDtoDr = new VoucherPostDetailDTO();
		postDetailDtoDr.setVoucherAmount(tbServiceReceiptMasBean.getReceiptModeDetailList().getRdAmount());
		LOGGER.info("getting budgetCodeIdDr Data - budgetCodeService getBankBudgetCodeIdByAccountId :"
				+ tbServiceReceiptMasBean.getReceiptModeDetailList().getBaAccountid() + tbServiceReceiptMasBean.getOrgId());
		final Long budgetCodeIdDr = budgetCodeService.getBankBudgetCodeIdByAccountId(
				tbServiceReceiptMasBean.getReceiptModeDetailList().getBaAccountid(), tbServiceReceiptMasBean.getOrgId());
		postDetailDtoDr.setSacHeadId(budgetCodeIdDr); // Budget Head

		voucherDetails.add(postDetailDtoDr);
		postDto.setVoucherDetails(voucherDetails);

		LOGGER.info("voucherPostingService - voucherPosting validation :" + postDto);
		postDtoList.add(postDto);
		List<String> responseValidation = accountVoucherPostService.checkVoucherPostValidateInput(postDtoList);
		if (responseValidation.size() > 0) {
			throw new NullPointerException(
					"improper input parameter for VoucherPostDTO in bank depost slip entry - receipt integration -> "
							+ responseValidation);
		}
	}

	private void depositEntry(final TbServiceReceiptMasBean tbServiceReceiptMasBean,
			final TbSrcptFeesDetEntity receiptDetail) throws ParseException {

		final Long accountTypeId = CommonMasterUtility.lookUpIdByLookUpCodeAndPrefix(
				PrefixConstants.AccountBillEntry.DP, PrefixConstants.TbAcVendormaster.SAM,
				tbServiceReceiptMasBean.getOrgId());
		final List<Long> objList = budgetHeadRepository.getMappingForDeposit(tbServiceReceiptMasBean.getOrgId(),
				accountTypeId, receiptDetail.getSacHeadId());
		if ((objList != null) && !objList.isEmpty()) {
			for (final Long obj : objList) {
				if (obj != null) {
					final AccountDepositBean depositBean = new AccountDepositBean();
					depositBean.setDepReceiptno(tbServiceReceiptMasBean.getRmRcptno().toString());
					depositBean.setDepEntryDate(tbServiceReceiptMasBean.getTransactionDate());
					depositBean.setDepReceiptdt(tbServiceReceiptMasBean.getRmDate());
					depositBean.setDepAmount(receiptDetail.getRfFeeamount());
					depositBean.setDepRefundBal(receiptDetail.getRfFeeamount());
					if (tbServiceReceiptMasBean.getVmVendorId() == null) {
						throw new NullPointerException(FOR_DEP_REC_VED_CODE_MANDATORY);
					}
					depositBean.setVmVendorid(tbServiceReceiptMasBean.getVmVendorId());
					depositBean.setDepNarration(tbServiceReceiptMasBean.getRmNarration());
					depositBean.setDepReceivedfrom(tbServiceReceiptMasBean.getRmReceivedfrom());
					depositBean.setDpDeptid(departmentDAO.getDepartmentIdByDeptCode(
							MainetConstants.RECEIPT_MASTER.Module, PrefixConstants.STATUS_ACTIVE_PREFIX));
					depositBean.setOrgid(tbServiceReceiptMasBean.getOrgId());
					final Long sourceTypeId = CommonMasterUtility.lookUpIdByLookUpCodeAndPrefix(
							PrefixConstants.AccountReceiptEntry.RP, PrefixConstants.AccountBillEntry.TOS,
							tbServiceReceiptMasBean.getOrgId());
					depositBean.setCpdSourceType(sourceTypeId);
					depositBean.setCpdStatus(
							CommonMasterUtility.lookUpIdByLookUpCodeAndPrefix(PrefixConstants.AccountBillEntry.DO,
									PrefixConstants.NewWaterServiceConstants.RDC, tbServiceReceiptMasBean.getOrgId()));
					depositBean.setCpdDepositType(Long.valueOf(obj));
					depositBean.setDmFlag(AccountConstants.S.toString());
					depositBean.setLangId((long) tbServiceReceiptMasBean.getLangId());
					depositBean.setCreatedBy(tbServiceReceiptMasBean.getCreatedBy());
					depositBean.setCreatedDate(new Date());
					depositBean.setLgIpMac(tbServiceReceiptMasBean.getLgIpMac());
					depositBean.setSacHeadId(receiptDetail.getSacHeadId());
					depositBean.setAdv_del_flag(MainetConstants.Y_FLAG);
					depositBean.setTbServiceReceiptMas(receiptDetail.getRmRcptid());
					depositService.create(depositBean);
				}
			}
		}
	}

	private AccountChequeOrCashDepositeBean bankDepositEntry(final TbServiceReceiptMasBean tbServiceReceiptMasBean,
			final long fieldId) throws Exception {

		AccountChequeOrCashDepositeBean bean = new AccountChequeOrCashDepositeBean();

		bean.setSfeeMode(tbServiceReceiptMasBean.getReceiptModeDetailList().getCpdFeemodeCode());
		bean.setDepositeType(bean.getSfeeMode().replace(MainetConstants.operator.COMMA, MainetConstants.WHITE_SPACE));
		bean.setDepartment(tbServiceReceiptMasBean.getDpDeptId());
		final List<TbServiceReceiptMasBean> list = new ArrayList<>();
		final TbServiceReceiptMasBean receiptBean = new TbServiceReceiptMasBean();

		String chkDate = "";
		if (tbServiceReceiptMasBean.getRecProperyTaxFlag() != null
				&& !tbServiceReceiptMasBean.getRecProperyTaxFlag().isEmpty()) {
			chkDate = Utility.dateToString(tbServiceReceiptMasBean.getRmDate());
		} else {
			chkDate = tbServiceReceiptMasBean.getTransactionDate();
		}
		bean.setDepositeSlipDate(chkDate);
		bean.setDepositeSlipId(tbServiceReceiptMasBean.getReceiptModeDetailList().getCpdFeemode());
		bean.setBaAccountid(tbServiceReceiptMasBean.getReceiptModeDetailList().getBaAccountid());
        if(tbServiceReceiptMasBean.getReceiptModeDetailList().getBaAccountid()!=null)
		receiptBean.setDrawnOnBank(tbServiceReceiptMasBean.getReceiptModeDetailList().getBaAccountid().toString());
		receiptBean.setPayOrderNo(tbServiceReceiptMasBean.getReceiptModeDetailList().getTranRefNumber());
		final SimpleDateFormat sdf = new SimpleDateFormat(MainetConstants.CommonConstants.D_FORMATE);
		receiptBean.setPayOrderDt(sdf.parse(tbServiceReceiptMasBean.getReceiptModeDetailList().getTranRefDatetemp()));
		receiptBean.setRmAmount(tbServiceReceiptMasBean.getReceiptModeDetailList().getRdAmount().toString());
		list.add(receiptBean);
		bean.setListOfReceiptDetails(list);

		bean.setTotal(tbServiceReceiptMasBean.getReceiptModeDetailList().getRdAmount());
		bean.setFieldId(Long.valueOf(fieldId));
		bean.setIsEmpty(MainetConstants.MENU.N);

		final Long statusId = CommonMasterUtility.lookUpIdByLookUpCodeAndPrefix(
				PrefixConstants.PaymentMode.USER_ADUSTMENT, PrefixConstants.BAS, tbServiceReceiptMasBean.getOrgId());
		final List<Object[]> accountsList = tbbankAccountService
				.getActiveBankAccountList(tbServiceReceiptMasBean.getOrgId(), statusId);
		for (final Object[] obj : accountsList) {
			bean.setFundId((Long) obj[4]);
		}
		bean.setRecptIntgFlagId(MainetConstants.MENU.Y);
		final VoucherPostDTO postDTO = new VoucherPostDTO();
		postDTO.setOrgId(tbServiceReceiptMasBean.getOrgId());
		postDTO.setVoucherSubTypeId(
				CommonMasterUtility.lookUpIdByLookUpCodeAndPrefix(PrefixConstants.ChequeDishonour.DS,
						PrefixConstants.REV_TYPE_CPD_VALUE, tbServiceReceiptMasBean.getOrgId()));
		postDTO.setDepartmentId(bean.getDepartment());

		final Long orgId = tbServiceReceiptMasBean.getOrgId();
		final int langId = tbServiceReceiptMasBean.getLangId();
		final Long empId = tbServiceReceiptMasBean.getCreatedBy();
		bean.setClearingDate(tbServiceReceiptMasBean.getCreatedDate());
		long depSlipTypeId=0l;
		 depSlipTypeId = CommonMasterUtility.lookUpIdByLookUpCodeAndPrefix("RWD",
				PrefixConstants.AccountPrefix.CFD.toString(), orgId);
		 if(depSlipTypeId==0) {
			 depSlipTypeId=CommonMasterUtility.lookUpIdByLookUpCodeAndPrefix("MWD",
						PrefixConstants.AccountPrefix.CFD.toString(), orgId);
		 }
		if (depSlipTypeId != 0) {
			bean.setDepositSlipType(depSlipTypeId);
		}

		bean = chequeOrCashService.saveRecords(bean, orgId, langId, empId, Utility.getMacAddress());
		return bean;
	}

	private void voucherPosting(final TbServiceReceiptMasEntity tbReceiptMasEntity, int longId, final long fieldId) {

		final VoucherPostDTO dto = new VoucherPostDTO();
		List<VoucherPostDTO> postDtoList = new ArrayList<>();
		dto.setCreatedBy(tbReceiptMasEntity.getCreatedBy());
		dto.setCreatedDate(tbReceiptMasEntity.getCreatedDate());
		Organisation organ = iOrganisationService.getOrganisationById(tbReceiptMasEntity.getOrgId());
		 if (Utility.isEnvPrefixAvailable(organ, MainetConstants.ENV_TSCL)) {
		dto.setDepartmentId(departmentService.getDepartment(AccountConstants.AC.getValue(),
				MainetConstants.CommonConstants.ACTIVE).getDpDeptid());
		 }
		 else {
			 dto.setDepartmentId(tbReceiptMasEntity.getDpDeptId());
		 }
		dto.setFieldId(tbReceiptMasEntity.getFieldId());
		dto.setLgIpMac(tbReceiptMasEntity.getLgIpMac());
		dto.setNarration(tbReceiptMasEntity.getRmNarration());
		dto.setOrgId(tbReceiptMasEntity.getOrgId());
		dto.setPayerOrPayee(tbReceiptMasEntity.getRmReceivedfrom());
		Organisation org = new Organisation();
		org.setOrgid(tbReceiptMasEntity.getOrgId());
		final LookUp payEntryMakerChecker = CommonMasterUtility.getLookUpFromPrefixLookUpValue(
				PrefixConstants.REV_SUB_CPD_VALUE, AccountPrefix.AUT.toString(), longId, org);
		if (payEntryMakerChecker.getLookUpCode() == null || payEntryMakerChecker.getLookUpCode().isEmpty()) {
			// dto.setVoucherDate(tbReceiptMasEntity.getRmDate());
			dto.setPayEntryMakerFlag(MainetConstants.Y_FLAG);
		} else {
			// dto.setVoucherDate(tbReceiptMasEntity.getCreatedDate());
		}

		dto.setVoucherDate(tbReceiptMasEntity.getRmDate());
		if (tbReceiptMasEntity.getRmRcptno() != null) {
			dto.setVoucherReferenceNo(tbReceiptMasEntity.getRmRcptno().toString());
		}
		dto.setFieldId(fieldId);
		dto.setEntryType(MainetConstants.BILL_MASTER_COMMON.ENTRY_TYPE);
		dto.setVoucherSubTypeId(CommonMasterUtility.lookUpIdByLookUpCodeAndPrefix(PrefixConstants.REV_SUB_CPD_VALUE,
				PrefixConstants.REV_TYPE_CPD_VALUE, tbReceiptMasEntity.getOrgId()));
		final List<VoucherPostDetailDTO> dtoListCr = new ArrayList<>();
		for (final TbSrcptFeesDetEntity tbServiceReceiptMasEntityList : tbReceiptMasEntity.getReceiptFeeDetail()) {

			final VoucherPostDetailDTO dtoListFee = new VoucherPostDetailDTO();
			dtoListFee.setSacHeadId(tbServiceReceiptMasEntityList.getSacHeadId());
			dtoListFee.setVoucherAmount(tbServiceReceiptMasEntityList.getRfFeeamount());
			dtoListCr.add(dtoListFee);

		}

		final VoucherPostDetailDTO dtoListMode = new VoucherPostDetailDTO();
		dtoListMode.setPayModeId(tbReceiptMasEntity.getReceiptModeDetail().get(0).getCpdFeemode());
		dtoListMode.setVoucherAmount(tbReceiptMasEntity.getReceiptModeDetail().get(0).getRdAmount());
		dtoListCr.add(dtoListMode);
		dto.setVoucherDetails(dtoListCr);
		// set all data
		ApplicationSession session = ApplicationSession.getInstance();
		postDtoList.add(dto);
		String responseValidation = accountVoucherPostService.validateInput(postDtoList);
		postDtoList.get(0).setDepartmentId(tbReceiptMasEntity.getDpDeptId());
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

	@Override
	@Transactional(readOnly = true)
	public TbServiceReceiptMasBean findById(final Long rmRcptid, final Long orgId) {
		final TbServiceReceiptMasEntity tbServiceReceiptMasEntity = accountReceiptEntrRrepository.findOne(rmRcptid);
		TbServiceReceiptMasBean receptMasBean=new TbServiceReceiptMasBean();
		if(tbServiceReceiptMasEntity!= null){
		 receptMasBean = accountReceiptEntryServiceMapper
				.mapTbServiceReceiptMasEntityToTbServiceReceiptMasBean(tbServiceReceiptMasEntity);
				 TbSrcptModesDetBean modeBean = new TbSrcptModesDetBean();
				 if(CollectionUtils.isNotEmpty(tbServiceReceiptMasEntity.getReceiptModeDetail())){
				 BeanUtils.copyProperties(tbServiceReceiptMasEntity.getReceiptModeDetail().get(0),modeBean);
				 modeBean.setBaAccountidDesc(tbServiceReceiptMasEntity.getReceiptModeDetail().get(0).getRdActNo());
				 }
				 receptMasBean.setReceiptModeDetailList(modeBean);
		}
		return receptMasBean;
	}

	@Override
	@Transactional(readOnly = true)
	public List<BankAccountMasterEntity> findBankacListReceipt(final long orgId) {

		final List<BankAccountMasterEntity> beans = new ArrayList<>(0);
		BankAccountMasterEntity account = null;
		final List<Object[]> receiptsAcList = accountReceiptEntrRrepository.getBankReceiptAcList(orgId);
		for (final Object obj[] : receiptsAcList) {
			account = new BankAccountMasterEntity();
			account.setBaAccountId((Long) obj[0]);
			account.setBaAccountNo(obj[1].toString());
			account.setBaAccountName(obj[2].toString());
			beans.add(account);
		}
		return beans;

	}

	@Override
	@Transactional
	public void findByIdEdit(final Long rmRcptid, final Long orgId, final String receiptDelRemark,
			final Date receiptDelDate, final String lgIpMacUpd, final Long updatedBy) {
		final Date updatedDate = new Date();
		final TbServiceReceiptMasEntity serviceReceiptMasEntity = accountReceiptEntrRrepository.findOne(rmRcptid);
		serviceReceiptMasEntity.setReceiptDelRemark(receiptDelRemark);
		serviceReceiptMasEntity.setReceiptDelFlag(MainetConstants.MENU.Y);
		serviceReceiptMasEntity.setUpdatedBy(updatedBy);
		serviceReceiptMasEntity.setUpdatedDate(updatedDate);
		serviceReceiptMasEntity.setLgIpMacUpd(lgIpMacUpd);
		accountReceiptEntrRrepository.save(serviceReceiptMasEntity);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.abm.mainet.account.service.AccountReceiptEntryService#
	 * deasEntryPosting(com.abm.mainetservice.core.bean.TbServiceReceiptMasBean)
	 */
	@Override
	@Transactional
	public TbServiceReceiptMasBean deasEntryPosting(final TbServiceReceiptMasBean tbServiceReceiptMasBean) {

		AccountVoucherCommPostingDetailDto accountVoucherCommPostingDetailDto = null;
		Long langId = null;
		if (tbServiceReceiptMasBean.getLangId() != 0) {
			langId = Long.valueOf(tbServiceReceiptMasBean.getLangId());
		}

		final List<AccountVoucherCommPostingDetailDto> accountVoucherCommPostingDetailDtoList = new ArrayList<>();
		accountVoucherCommPostingDetailDto = new AccountVoucherCommPostingDetailDto();

		accountVoucherCommPostingDetailDto
				.setDrcrValue(MainetConstants.BUG_HEAD_OPENING_BALANCE_MASTER.BUG_BAL_TYPE_LIABILITY);

		accountVoucherCommPostingDetailDto.setVoudetAmt(tbServiceReceiptMasBean.getReceiptModeDetailList().getRdAmount());
		accountVoucherCommPostingDetailDtoList.add(accountVoucherCommPostingDetailDto);
		for (final TbSrcptFeesDetBean tbSrcptFeesDetBeanList : tbServiceReceiptMasBean.getReceiptFeeDetail()) {
			accountVoucherCommPostingDetailDto = new AccountVoucherCommPostingDetailDto();
			accountVoucherCommPostingDetailDto
					.setDrcrValue(MainetConstants.BUG_HEAD_OPENING_BALANCE_MASTER.BUG_BAL_TYPE_ASSETS);
			accountVoucherCommPostingDetailDto.setVoudetAmt(tbSrcptFeesDetBeanList.getRfFeeamount());
			accountVoucherCommPostingDetailDtoList.add(accountVoucherCommPostingDetailDto);
		}

		final AccountVoucherCommPostingMasterDto accountVoucherCommPostingMasterDto = new AccountVoucherCommPostingMasterDto(
				tbServiceReceiptMasBean.getOrgId(), tbServiceReceiptMasBean.getRmDate(),
				tbServiceReceiptMasBean.getRmDate(), MainetConstants.BILL_MASTER_COMMON.PREFIX_CODE_RV,
				MainetConstants.AccountBudgetAdditionalSupplemental.REV, MainetConstants.RECEIPT_MASTER.Module,
				tbServiceReceiptMasBean.getRmRcptno().toString(), tbServiceReceiptMasBean.getRmDate(),
				tbServiceReceiptMasBean.getRmNarration(), null, null, null, tbServiceReceiptMasBean.getRmReceivedfrom(),
				tbServiceReceiptMasBean.getCreatedBy(), langId, tbServiceReceiptMasBean.getLgIpMac(),
				MainetConstants.MENU.Y, 1, tbServiceReceiptMasBean.getTbAcFieldMaster().getFieldId(),
				accountVoucherCommPostingDetailDtoList);

		voucherPostService.deasEntryCommPosting(accountVoucherCommPostingMasterDto);
		return null;
	}

	@Override
	@Transactional(readOnly = true)
	public List<String> getPayeeNames(final Long orgId) {

		final List<String> payeeList = new ArrayList<>(0);
		if ((orgId != null) && (orgId != 0L)) {
          //for fetching conditional query
			final List<String> payeeNewList = accountReceiptEntrRrepository.getPayeeNames(orgId,new Date());
			if ((payeeNewList != null) && !payeeNewList.isEmpty()) {
				for (final String payeeString : payeeNewList) {
					if ((payeeString != null) && !payeeString.isEmpty()) {
						payeeList.add(payeeString.replaceAll(MainetConstants.StandardAccountHeadMapping.NULL,
								MainetConstants.CommonConstants.BLANK));
					}
				}
			}
		}
		return payeeList;
	}

	@Override
	@Transactional(readOnly = true)
	public Long getBudgetCodeIdForReceitMode(final Long orgId, final Organisation org, final Long cpdFeemode) {

		final Long activePrefix = CommonMasterUtility
				.getValueFromPrefixLookUp(PrefixConstants.STATUS_ACTIVE_PREFIX, PrefixConstants.STATUS_PREFIX, org)
				.getLookUpId();
		final Long budgetCodeId = tbTaxMasJpaRepository.fetchBudgetCodeIdForReceiptMode(orgId, cpdFeemode,
				activePrefix);
		return budgetCodeId;
	}

	@Override
	@Transactional
	public AccountReceiptDTO findByRestId(final Long rmRcptid, final Long orgId) {
		final TbServiceReceiptMasEntity tbServiceReceiptMasEntity = accountReceiptEntrRrepository.findOne(rmRcptid);
		final AccountReceiptDTO accountReceiptDTO = new AccountReceiptDTO();
		
		TbServiceReceiptMasBean tbServiceReceiptMasBean = accountReceiptEntryServiceMapper
				.mapTbServiceReceiptMasEntityToTbServiceReceiptMasBean(tbServiceReceiptMasEntity);
				 TbSrcptModesDetBean modeBean = new TbSrcptModesDetBean();
				 BeanUtils.copyProperties(tbServiceReceiptMasEntity.getReceiptModeDetail().get(0),modeBean);
				 tbServiceReceiptMasBean.setReceiptModeDetailList(modeBean);

		accountReceiptDTO.setReceiptId(tbServiceReceiptMasBean.getRmRcptid());
		accountReceiptDTO.setReceiptNumber(tbServiceReceiptMasBean.getRmRcptno().toString());
		accountReceiptDTO.setReceiptAmount(tbServiceReceiptMasBean.getRmAmount().toString());
		accountReceiptDTO.setReceiptPayeeName(tbServiceReceiptMasBean.getRmReceivedfrom());
		accountReceiptDTO.setReceiptDate(tbServiceReceiptMasBean.getRmDate().toString());
		accountReceiptDTO.setRmReceiptAmt(new BigDecimal(tbServiceReceiptMasBean.getRmAmount()));
		accountReceiptDTO.setOrgId(tbServiceReceiptMasBean.getOrgId());

		final List<SrcptFeesDetDTO> srcptFeesDetDTOList = new ArrayList<>();
		final SrcptFeesDetDTO srcptFeesDetDTO = new SrcptFeesDetDTO();
		for (final TbSrcptFeesDetBean receiptFeeDetail : tbServiceReceiptMasBean.getReceiptFeeDetail()) {

			final AccountBudgetCodeBean budgetBean = receiptFeeDetail.getBudgetCode();
			srcptFeesDetDTO.setBudgetCodeid(budgetBean.getPrBudgetCodeid());
			srcptFeesDetDTO.setRfFeeamount(receiptFeeDetail.getRfFeeamount());
			srcptFeesDetDTO.setRfFeeid(receiptFeeDetail.getRfFeeid());
		}
		srcptFeesDetDTOList.add(srcptFeesDetDTO);

		accountReceiptDTO.setReceiptFeeDetail(srcptFeesDetDTOList);
		accountReceiptDTO.setVmVendorId(tbServiceReceiptMasBean.getVmVendorId());

		final SrcptModesDetDTO srcptModesDetDTO = new SrcptModesDetDTO();
		srcptModesDetDTO.setRdModesid(tbServiceReceiptMasBean.getReceiptModeDetailList().getRdModesid());
		srcptModesDetDTO.setCpdFeemode(tbServiceReceiptMasBean.getReceiptModeDetailList().getCpdFeemode());
		srcptModesDetDTO.setBaAccountid(tbServiceReceiptMasBean.getReceiptModeDetailList().getBaAccountid());
		srcptModesDetDTO.setTranRefNumber(tbServiceReceiptMasBean.getReceiptModeDetailList().getTranRefNumber());
		srcptModesDetDTO.setTranRefDatetemp(tbServiceReceiptMasBean.getReceiptModeDetailList().getTranRefDatetemp());
		srcptModesDetDTO.setRdAmount(tbServiceReceiptMasBean.getReceiptModeDetailList().getRdAmount());

		accountReceiptDTO.setReceiptModeDetail(srcptModesDetDTO);

		final String feeAmountStr = null;
		accountReceiptDTO.setFeeAmountStr(feeAmountStr);

		TbAcVendormaster tbAcVendormaster = null;
		if (accountReceiptDTO.getVmVendorId() == null) {
			tbAcVendormaster = new TbAcVendormaster();
		} else {
			tbAcVendormaster = tbAcVendormasterService.findById(accountReceiptDTO.getVmVendorId(), orgId);
			accountReceiptDTO.setVmVendorIdDesc(tbAcVendormaster.getVmVendorname());
		}
		if (!accountReceiptDTO.getReceiptModeDetail().getCpdFeemode().equals(MainetConstants.CommonConstants.BLANK)) {
			LookUp lookUp = new LookUp();
			final Organisation organisation = new Organisation();
			organisation.setOrgid(orgId);
			lookUp = CommonMasterUtility.getNonHierarchicalLookUpObject(
					accountReceiptDTO.getReceiptModeDetail().getCpdFeemode(), organisation);

			accountReceiptDTO.getReceiptModeDetail().setCpdFeemodeDesc(lookUp.getLookUpDesc());
			accountReceiptDTO.getReceiptModeDetail().setCpdFeemodeCode(lookUp.getLookUpCode());
		}

		BankAccountMasterDto bankAccountMasterDto = null;
		if (accountReceiptDTO.getReceiptModeDetail().getBaAccountid() == null) {
			bankAccountMasterDto = new BankAccountMasterDto();
		} else {
			bankAccountMasterDto = bankAccountService
					.findAccountByAccountId(accountReceiptDTO.getReceiptModeDetail().getBaAccountid());
			accountReceiptDTO.getReceiptModeDetail().setBaAccountidDesc(bankAccountMasterDto.getBaAccountNo()
					+ MainetConstants.HYPHEN + bankAccountMasterDto.getBaAccountName());

		}

		if (bankAccountService.getBankbyBranchId(accountReceiptDTO.getReceiptModeDetail().getCbBankid()) == null) {
			new BankMasterDto();
		} else {
			final BankMasterDto bank = bankAccountService
					.getBankbyBranchId(accountReceiptDTO.getReceiptModeDetail().getCbBankid());
			accountReceiptDTO.getReceiptModeDetail().setCbBankidDesc(bank.getBank());
		}

		final String chkDate = Utility.dateToString(accountReceiptDTO.getReceiptModeDetail().getRdChequedddate());
		accountReceiptDTO.getReceiptModeDetail().setRdChequedddatetemp(chkDate);

		final String tranRefDate = Utility.dateToString(accountReceiptDTO.getReceiptModeDetail().getTranRefDate());
		accountReceiptDTO.getReceiptModeDetail().setTranRefDatetemp(tranRefDate);

		return accountReceiptDTO;
	}

	@Override
	public String validateReceiptInput(final String receiptAmount, final String receiptNumber,
			final String receiptPayeeName, final String receiptDate) {
		final StringBuilder builder = new StringBuilder();
		if (((receiptAmount == null) || receiptAmount.isEmpty()) && ((receiptNumber == null) || receiptNumber.isEmpty())
				&& ((receiptPayeeName == null) || receiptPayeeName.isEmpty())
				&& ((receiptDate == null) || receiptDate.isEmpty())) {
			builder.append(ApplicationSession.getInstance().getMessage("accounts.receipt.receiptno"));
		}

		return builder.toString();
	}

	@Override
	@Transactional(readOnly = true)
	public List<AccountReceiptDTO> findAllReceiptRestData(final Long orgId, String receiptAmount, String receiptNumber,
			String receiptPayeeName, final String receiptDate) throws ParseException {

		if ((receiptNumber == null) || receiptNumber.isEmpty()) {
			receiptNumber = MainetConstants.ZERO;
		}
		if ((receiptAmount == null) || receiptAmount.isEmpty()) {
			receiptAmount = MainetConstants.ZERO;
		}
		if ((receiptPayeeName == null) || receiptPayeeName.isEmpty()) {
			receiptPayeeName = MainetConstants.CommonConstants.BLANK;
		}
		final String rmRcptno = receiptNumber;
		final long rmrcptno = Integer.parseInt(rmRcptno);
		BigDecimal rmmount = BigDecimal.ZERO;
		if (!receiptAmount.isEmpty()) {
			rmmount = new BigDecimal(receiptAmount);
		}
		Date rmDate = null;
		if ((receiptDate != null) && !receiptDate.isEmpty()) {
			final SimpleDateFormat sdf = new SimpleDateFormat(MainetConstants.CommonConstants.D_FORMATE);
			rmDate = sdf.parse(receiptDate);
		}
		final String receiptQuery = "select aRcpt from TbServiceReceiptMasEntity aRcpt,Department dPart where aRcpt.orgId = "
				+ orgId
				+ " and dPart.dpDeptid=aRcpt.dpDeptId and dPart.dpDeptcode='AC'  AND  aRcpt.receiptDelFlag IS NULL  ";

		final StringBuilder query = new StringBuilder(receiptQuery);
		final String rmReceivedfrom = receiptPayeeName;
		if ((rmrcptno != 0) || (rmmount != BigDecimal.ZERO) || ((rmReceivedfrom != null) && !rmReceivedfrom.isEmpty())
				|| (rmDate != null)) {
			query.append("and (");
		}
		if (rmrcptno != 0) {
			query.append("aRcpt.rmRcptno=:rmRcptno");

			if ((rmmount != BigDecimal.ZERO) || ((rmReceivedfrom != null) && !rmReceivedfrom.isEmpty())
					|| (rmDate != null)) {
				query.append("  and ");
			}
		}
		if ((rmmount != null) && (rmmount != BigDecimal.ZERO)) {
			query.append(" aRcpt.rmAmount=:rmAmount");

			if (((rmReceivedfrom != null) && !rmReceivedfrom.isEmpty()) || (rmDate != null)) {
				query.append("  and ");
			}
		}
		if ((rmReceivedfrom != null) && !rmReceivedfrom.isEmpty()) {
			query.append("aRcpt.rmReceivedfrom=:rmReceivedfrom");
			if (rmDate != null) {
				query.append(AccountReceiptEntry.APPEND_AND);
			}
		}
		if (rmDate != null) {
			query.append("aRcpt.rmDate=:rmDate");
		}

		if ((rmrcptno != 0) || (rmmount != BigDecimal.ZERO) || ((rmReceivedfrom != null) && !rmReceivedfrom.isEmpty())
				|| (rmDate != null)) {
			query.append(MainetConstants.FUND_MASTER.CLOSE_BRACKET);
		}
		query.append(" order by aRcpt.rmRcptid desc");
		final Iterable<TbServiceReceiptMasEntity> entities = null;// accountReceiptEntryDao.getReceiptDetail(query.toString(),
																	// rmmount, rmrcptno, rmReceivedfrom, rmDate);
		List<TbServiceReceiptMasBean> beans = null;
		if (entities != null) {
			beans = new ArrayList<>();
			for (final TbServiceReceiptMasEntity tbServiceReceiptMasEntity : entities) {
				
				TbServiceReceiptMasBean receptMasBean = accountReceiptEntryServiceMapper
						.mapTbServiceReceiptMasEntityToTbServiceReceiptMasBean(tbServiceReceiptMasEntity);
						 TbSrcptModesDetBean modeBean = new TbSrcptModesDetBean();
						 BeanUtils.copyProperties(tbServiceReceiptMasEntity.getReceiptModeDetail().get(0),modeBean);
						 receptMasBean.setReceiptModeDetailList(modeBean);
						 
				beans.add(receptMasBean);
			}
		}
		final List<AccountReceiptDTO> restDTO = new ArrayList<>();
		final AccountReceiptDTO dto = new AccountReceiptDTO();
		for (final TbServiceReceiptMasBean tbServiceReceiptMasBean : beans) {
			if (tbServiceReceiptMasBean != null) {
				dto.setReceiptId(tbServiceReceiptMasBean.getRmRcptid());
				dto.setReceiptNumber(tbServiceReceiptMasBean.getRmRcptno().toString());
				dto.setReceiptAmount(tbServiceReceiptMasBean.getRmAmount().toString());
				dto.setReceiptPayeeName(tbServiceReceiptMasBean.getRmReceivedfrom());
				dto.setReceiptDate(tbServiceReceiptMasBean.getRmDate().toString());
				dto.setRmReceiptAmt(new BigDecimal(tbServiceReceiptMasBean.getRmAmount()));
				dto.setOrgId(tbServiceReceiptMasBean.getOrgId());
			}
			restDTO.add(dto);
		}
		return restDTO;
	}

	@Override
	@Transactional(readOnly = true)
	public Long getFinanciaYearIds(final Date receiptEntryDate) {
		return financialYearJpaRepository.getFinanciaYearIds(receiptEntryDate);
	}

	@Override
	@Transactional(rollbackFor = Exception.class)
	public TbServiceReceiptMasEntity saveReceipt(final AccountReceiptDTO accountReceiptDTO, long fieldId)
			throws Exception {
		LOGGER.info("Provided input for saveReceipt :" + accountReceiptDTO);

		final Organisation org = new Organisation();
		org.setOrgid(accountReceiptDTO.getOrgId());

		Long receiptNo = null;
		TbServiceReceiptMasEntity tbServiceReceiptMasSave = null;

		final TbServiceReceiptMasEntity receiptMasEntity = new TbServiceReceiptMasEntity();
		TbServiceReceiptMasBean tbServiceReceiptMasBean = new TbServiceReceiptMasBean();

		final Long orgid = accountReceiptDTO.getOrgId();

		if (accountReceiptDTO.getDpDeptId() != null) {
			tbServiceReceiptMasBean.setDpDeptId(accountReceiptDTO.getDpDeptId());
		} else {
			tbServiceReceiptMasBean
					.setDpDeptId(departmentService.getDepartmentIdByDeptCode(AccountConstants.AC.toString()));
		}

		accountReceiptDTO.setReceiptDate(accountReceiptDTO.getReceiptDate());

		// tbServiceReceiptMasBean.setRmRcptid(accountReceiptDTO.getReceiptId());
		if ((accountReceiptDTO.getReceiptNo() != null) && !accountReceiptDTO.getReceiptNo().isEmpty()) {
			tbServiceReceiptMasBean.setRmRcptno(Long.valueOf(accountReceiptDTO.getReceiptNo()));
		}
		if ((accountReceiptDTO.getReceiptAmount() != null) && !accountReceiptDTO.getReceiptAmount().isEmpty()) {
			tbServiceReceiptMasBean.setRmAmount(accountReceiptDTO.getReceiptAmount());
		}
		tbServiceReceiptMasBean.setRmReceivedfrom(accountReceiptDTO.getReceiptPayeeName());
		final SimpleDateFormat sdf = new SimpleDateFormat(MainetConstants.CommonConstants.D_FORMATE);
		Date date = null;
		if ((accountReceiptDTO.getReceiptDate() != null) && !accountReceiptDTO.getReceiptDate().isEmpty()) {
			date = sdf.parse(accountReceiptDTO.getReceiptDate());
		} else {
			date = new Date();
		}
		tbServiceReceiptMasBean.setRmDate(date);
		tbServiceReceiptMasBean.setRmAmount(accountReceiptDTO.getRmReceiptAmt().toString());
		tbServiceReceiptMasBean.setOrgId(accountReceiptDTO.getOrgId());
		tbServiceReceiptMasBean.setCreatedBy(accountReceiptDTO.getCreatedBy());
		tbServiceReceiptMasBean.setLangId(accountReceiptDTO.getLangId());
		tbServiceReceiptMasBean.setCreatedDate(new Date());
		tbServiceReceiptMasBean.setLgIpMac(accountReceiptDTO.getLgIpMac());
		// tbServiceReceiptMasBean.getTbAcFieldMaster().setFieldId(fieldId);

		tbServiceReceiptMasBean.setVmVendorId(accountReceiptDTO.getVmVendorId());
		tbServiceReceiptMasBean.setMobileNumber(accountReceiptDTO.getMobileNumber());
		tbServiceReceiptMasBean.setEmailId(accountReceiptDTO.getEmailId());
		tbServiceReceiptMasBean.setRmNarration(accountReceiptDTO.getRmNarration());
		tbServiceReceiptMasBean.setRmReceivedfrom(accountReceiptDTO.getReceiptPayeeName());

		if (accountReceiptDTO.getDpDeptId() != null) {
			tbServiceReceiptMasBean.setDpDeptId(accountReceiptDTO.getDpDeptId());
		} else {
			tbServiceReceiptMasBean
					.setDpDeptId(departmentService.getDepartmentIdByDeptCode(AccountConstants.AC.toString()));
		}

		// check it receipt mode details
		final TbSrcptModesDetBean tbSrcptModesDetBean = new TbSrcptModesDetBean();
		// tbSrcptModesDetBean.setRdModesid(accountReceiptDTO.getReceiptModeDetail().getRdModesid());
		tbSrcptModesDetBean.setCpdFeemode(accountReceiptDTO.getReceiptModeDetail().getCpdFeemode());

		String payModeCode = CommonMasterUtility.findLookUpCode(PrefixConstants.PAY_PREFIX.PREFIX_VALUE,
				accountReceiptDTO.getOrgId(), accountReceiptDTO.getReceiptModeDetail().getCpdFeemode());

		if (accountReceiptDTO.getReceiptModeDetail() != null
				&& (accountReceiptDTO.getReceiptModeDetail().getCpdFeemodeCode() != null
						&& !accountReceiptDTO.getReceiptModeDetail().getCpdFeemodeCode().isEmpty())) {
			tbSrcptModesDetBean.setCpdFeemodeCode(accountReceiptDTO.getReceiptModeDetail().getCpdFeemodeCode());
		} else {
			if (payModeCode != null && !payModeCode.isEmpty()) {
				tbSrcptModesDetBean.setCpdFeemodeCode(payModeCode);
			}
		}

		if (!tbSrcptModesDetBean.getCpdFeemodeCode().isEmpty() && (tbSrcptModesDetBean.getCpdFeemodeCode() != null)) {
			if (tbSrcptModesDetBean.getCpdFeemodeCode().equals(MainetConstants.Complaint.MODE_CREATE)) {
			} else {
				if (tbSrcptModesDetBean.getCpdFeemodeCode().equals(PrefixConstants.WATERMODULEPREFIX.RT)
						|| tbSrcptModesDetBean.getCpdFeemodeCode().equals(MainetConstants.MENU.N)
						|| tbSrcptModesDetBean.getCpdFeemodeCode().equals(PrefixConstants.PaymentMode.BANK)) {
					tbSrcptModesDetBean.setBaAccountid(accountReceiptDTO.getReceiptModeDetail().getBaAccountid());
					tbSrcptModesDetBean.setTranRefNumber(accountReceiptDTO.getReceiptModeDetail().getTranRefNumber());
					tbSrcptModesDetBean
							.setTranRefDatetemp(accountReceiptDTO.getReceiptModeDetail().getTranRefDatetemp());
					tbSrcptModesDetBean.setRdChequedddate(
							sdf.parse(accountReceiptDTO.getReceiptModeDetail().getTranRefDatetemp()));
				} else {
					tbSrcptModesDetBean.setCbBankid(accountReceiptDTO.getReceiptModeDetail().getCbBankid());
					tbSrcptModesDetBean
							.setRdChequeddno(Long.valueOf(accountReceiptDTO.getReceiptModeDetail().getTranRefNumber()));
					tbSrcptModesDetBean.setRdChequedddate(
							sdf.parse(accountReceiptDTO.getReceiptModeDetail().getTranRefDatetemp()));
					tbSrcptModesDetBean
							.setTranRefDatetemp(accountReceiptDTO.getReceiptModeDetail().getTranRefDatetemp());
					tbSrcptModesDetBean
							.setRdChequedddatetemp(accountReceiptDTO.getReceiptModeDetail().getTranRefDatetemp());
					try {
						LookUp lookUp = CommonMasterUtility.getValueFromPrefixLookUp(PrefixConstants.LookUpPrefix.RND,
								PrefixConstants.LookUpPrefix.CLR, new Organisation(tbServiceReceiptMasBean.getOrgId()));
						tbSrcptModesDetBean.setCheckStatus(lookUp.getLookUpId());
						}catch(Exception ex) {
							LOGGER.error("Error while getting prefix value", ex);
						}
					try {
						LookUp depositNtIsuedLookUp = CommonMasterUtility.getValueFromPrefixLookUp("DNC", "CLR", org);
						if (depositNtIsuedLookUp != null
								&& StringUtils.isNotBlank(depositNtIsuedLookUp.getOtherField())) {
							tbSrcptModesDetBean.setRdSrChkDis(depositNtIsuedLookUp.getOtherField());
						}
					} catch (Exception ex) {
						LOGGER.error("Error while getting prefix value", ex);
					}
				}
			}
		}
		tbSrcptModesDetBean.setRdAmount(accountReceiptDTO.getReceiptModeDetail().getRdAmount());
		tbServiceReceiptMasBean.setReceiptModeDetailList(tbSrcptModesDetBean);
		// check it receipt collection details
		final List<TbSrcptFeesDetBean> receiptFeeDetail = new ArrayList<>();
		for (final SrcptFeesDetDTO dto : accountReceiptDTO.getReceiptFeeDetail()) {
			final TbSrcptFeesDetBean receiptFee = new TbSrcptFeesDetBean();

			receiptFee.setSacHeadId(dto.getBudgetCodeid());
			receiptFee.setRfFeeamount(dto.getRfFeeamount());
			receiptFee.setRfLo3(dto.getBillType());
			receiptFeeDetail.add(receiptFee);
		}
		tbServiceReceiptMasBean.setReceiptFeeDetail(receiptFeeDetail);

		final Date curDate = new Date();
		final Long empId = accountReceiptDTO.getCreatedBy();
		final String macAddress = accountReceiptDTO.getLgIpMac();
		accountReceiptDTO.getLangId();

		accountReceiptEntryServiceMapper.mapTbServiceReceiptMasBeanToTbServiceReceiptMasEntity(tbServiceReceiptMasBean,
				receiptMasEntity);
		
		receiptMasEntity.getReceiptModeDetail().get(0).setRmRcptid(receiptMasEntity);
		receiptMasEntity.getReceiptModeDetail().get(0).setOrgId(orgid);
		receiptMasEntity.getReceiptModeDetail().get(0).setCreatedBy(empId);
		receiptMasEntity.getReceiptModeDetail().get(0).setLgIpMac(macAddress);
		receiptMasEntity.getReceiptModeDetail().get(0).setCreatedDate(new Date());
		if (tbSrcptModesDetBean.getRdSrChkDis() != null && !tbSrcptModesDetBean.getRdSrChkDis().isEmpty()) {
			receiptMasEntity.getReceiptModeDetail().get(0).setRdSrChkDis(tbSrcptModesDetBean.getRdSrChkDis());
		}
		if (tbSrcptModesDetBean.getCheckStatus() != null) {
			receiptMasEntity.getReceiptModeDetail().get(0).setCheckStatus(tbSrcptModesDetBean.getCheckStatus());
		}
		receiptMasEntity.setRmAmount(tbSrcptModesDetBean.getRdAmount());
		org.setOrgid(orgid);
		// tbTaxMasJpaRepository.fetchBudgetCodeIdForReceiptMode(orgid,receiptMasEntity.getReceiptModeDetail().getCpdFeemode(),
		// activePrefix);

		final Date rdchequedddate = Utility
				.stringToDate(tbServiceReceiptMasBean.getReceiptModeDetailList().getRdChequedddatetemp());
		receiptMasEntity.getReceiptModeDetail().get(0).setRdChequedddate(rdchequedddate);

		final Date tranrefdate = Utility
				.stringToDate(tbServiceReceiptMasBean.getReceiptModeDetailList().getTranRefDatetemp());
		receiptMasEntity.getReceiptModeDetail().get(0).setTranRefDate(tranrefdate);
		// receiptMasEntity.setLgIpMac(macAddress);
		receiptMasEntity.setFieldId(fieldId);
		receiptMasEntity.setReceiptTypeFlag(MainetConstants.MENU.R);
		if (accountReceiptDTO.getRecProperyTaxFlag() != null && !accountReceiptDTO.getRecProperyTaxFlag().isEmpty()) {
			receiptMasEntity.setRmLo1(accountReceiptDTO.getRecProperyTaxFlag());
			tbServiceReceiptMasBean.setRecProperyTaxFlag(accountReceiptDTO.getRecProperyTaxFlag());
		}
		if (accountReceiptDTO.getReceiptNumber() != null && !accountReceiptDTO.getReceiptNumber().isEmpty()) {
			receiptMasEntity.setManualReceiptNo(accountReceiptDTO.getReceiptNumber());
		}
		if (accountReceiptDTO.getRecPropertytaxRefId() != null) {
			receiptMasEntity.setRefId(accountReceiptDTO.getRecPropertytaxRefId());
		}

		// receipt voucher property tax posting validate to given VoucherPostDTO
		receiptPropertyTaxVoucherPostingValidation(receiptMasEntity, accountReceiptDTO.getLangId(), fieldId,
				accountReceiptDTO.getFinancialYearId());

		if (tbServiceReceiptMasBean.getReceiptModeDetailList().getCpdFeemodeCode()
				.equals(PrefixConstants.WATERMODULEPREFIX.RT)
				|| tbServiceReceiptMasBean.getReceiptModeDetailList().getCpdFeemodeCode().equals(MainetConstants.MENU.N)
				|| tbServiceReceiptMasBean.getReceiptModeDetailList().getCpdFeemodeCode()
						.equals(PrefixConstants.PaymentMode.BANK)) {
			// bank deposit slip receipt integration voucher property tax posting validate
			// to given VoucherPostDTO
			bankDepositSlipVoucherPostingValidation(tbServiceReceiptMasBean, fieldId);
		}

		receiptNo = getReceiptNumber(orgid, accountReceiptDTO.getReceiptDate());
		accountReceiptDTO.setReceiptNo(receiptNo.toString());

		for (final TbSrcptFeesDetEntity receiptDetail : receiptMasEntity.getReceiptFeeDetail()) {
			receiptDetail.setRmRcptid(receiptMasEntity);
			receiptDetail.setOrgId(orgid);
			receiptDetail.setCreatedDate(curDate);
			receiptDetail.setLgIpMac(macAddress);
			receiptDetail.setCreatedBy(empId);
			depositEntry(tbServiceReceiptMasBean, receiptDetail);
		}
		receiptMasEntity.setRmRcptno(receiptNo);
		tbServiceReceiptMasSave = accountReceiptEntrRrepository.save(receiptMasEntity);
		cbPropertytaxReceiptVoucherPosting(tbServiceReceiptMasSave, accountReceiptDTO.getLangId(), fieldId,
				accountReceiptDTO.getFinancialYearId());
		if (tbServiceReceiptMasBean.getReceiptModeDetailList().getCpdFeemodeCode()
				.equals(PrefixConstants.WATERMODULEPREFIX.RT)
				|| tbServiceReceiptMasBean.getReceiptModeDetailList().getCpdFeemodeCode().equals(MainetConstants.MENU.N)
				|| tbServiceReceiptMasBean.getReceiptModeDetailList().getCpdFeemodeCode()
						.equals(PrefixConstants.PaymentMode.BANK)) {
			final AccountChequeOrCashDepositeBean bean = bankDepositEntry(tbServiceReceiptMasBean, fieldId);
			accountReceiptHeadJpaRepository.updateReceiptWiseDepositId(tbServiceReceiptMasSave.getRmRcptid(),
					bean.getDepositeSlipId());
		}
		tbServiceReceiptMasBean = accountReceiptEntryServiceMapper
				.mapTbServiceReceiptMasEntityToTbServiceReceiptMasBean(tbServiceReceiptMasSave);
				 TbSrcptModesDetBean modeBean = new TbSrcptModesDetBean();
				 BeanUtils.copyProperties(tbServiceReceiptMasSave.getReceiptModeDetail().get(0),modeBean);
				 tbServiceReceiptMasBean.setReceiptModeDetailList(modeBean);
		return tbServiceReceiptMasSave;
	}

	private void receiptPropertyTaxVoucherPostingValidation(TbServiceReceiptMasEntity tbReceiptMasEntity, int langId,
			long fieldId, Long financialYearId) {
		LOGGER.info("Process for account receipt property tax voucher posting validation:" + tbReceiptMasEntity + langId
				+ fieldId + financialYearId);

		final VoucherPostDTO dto = new VoucherPostDTO();
		List<VoucherPostDTO> postDtoList = new ArrayList<>();
		dto.setCreatedBy(tbReceiptMasEntity.getCreatedBy());
		dto.setCreatedDate(tbReceiptMasEntity.getCreatedDate());
		dto.setDepartmentId(tbReceiptMasEntity.getDpDeptId());
		dto.setFieldId(tbReceiptMasEntity.getFieldId());
		dto.setLgIpMac(tbReceiptMasEntity.getLgIpMac());
		dto.setNarration(tbReceiptMasEntity.getRmNarration());
		dto.setOrgId(tbReceiptMasEntity.getOrgId());
		dto.setPayerOrPayee(tbReceiptMasEntity.getRmReceivedfrom());
		Organisation org = new Organisation();
		org.setOrgid(tbReceiptMasEntity.getOrgId());
		final LookUp payEntryMakerChecker = CommonMasterUtility.getLookUpFromPrefixLookUpValue(
				PrefixConstants.REV_SUB_CPD_VALUE, AccountPrefix.AUT.toString(), langId, org);
		if (payEntryMakerChecker.getLookUpCode() == null || payEntryMakerChecker.getLookUpCode().isEmpty()) {
			// dto.setVoucherDate(tbReceiptMasEntity.getRmDate());
			dto.setPayEntryMakerFlag(MainetConstants.Y_FLAG);
		} else {
			// dto.setVoucherDate(tbReceiptMasEntity.getCreatedDate());
		}
		dto.setVoucherDate(tbReceiptMasEntity.getRmDate());
		// dto.setVoucherReferenceNo(tbReceiptMasEntity.getRmRcptno().toString());
		dto.setFieldId(fieldId);
		dto.setEntryType(MainetConstants.BILL_MASTER_COMMON.ENTRY_TYPE);
		dto.setVoucherSubTypeId(CommonMasterUtility.lookUpIdByLookUpCodeAndPrefix("RDC",
				PrefixConstants.REV_TYPE_CPD_VALUE, tbReceiptMasEntity.getOrgId()));

		final List<VoucherPostDetailDTO> dtoListCr = new ArrayList<>();
		if (tbReceiptMasEntity.getRmLo1() != null && !tbReceiptMasEntity.getRmLo1().isEmpty()) {
			if (tbReceiptMasEntity.getRmLo1().equals(MainetConstants.Y_FLAG)) {
				Long voucherSubTypeIdC = CommonMasterUtility.lookUpIdByLookUpCodeAndPrefix("DMD",
						PrefixConstants.REV_TYPE_CPD_VALUE, tbReceiptMasEntity.getOrgId());
				Long voucherSubTypeIdP = CommonMasterUtility.lookUpIdByLookUpCodeAndPrefix("DMP",
						PrefixConstants.REV_TYPE_CPD_VALUE, tbReceiptMasEntity.getOrgId());
				final Long status = CommonMasterUtility.lookUpIdByLookUpCodeAndPrefix(
						MainetConstants.CommonConstants.ACTIVE, AccountPrefix.ACN.toString(),
						tbReceiptMasEntity.getOrgId());
				Long dpDeptId = tbReceiptMasEntity.getDpDeptId();
				int countC = 0;
				int countP = 0;
				BigDecimal sumOfRecCurAmount = BigDecimal.ZERO;
				BigDecimal sumOfRecPreAmount = BigDecimal.ZERO;
				if (tbReceiptMasEntity.getReceiptFeeDetail() != null) {
					for (TbSrcptFeesDetEntity receiptFeeDetails : tbReceiptMasEntity.getReceiptFeeDetail()) {
						if (receiptFeeDetails.getRfLo3().equals("C")) {
							sumOfRecCurAmount = sumOfRecCurAmount.add(receiptFeeDetails.getRfFeeamount());
						}
						if (receiptFeeDetails.getRfLo3().equals("P")) {
							sumOfRecPreAmount = sumOfRecPreAmount.add(receiptFeeDetails.getRfFeeamount());
						}
					}
					for (TbSrcptFeesDetEntity receiptFeeDetails : tbReceiptMasEntity.getReceiptFeeDetail()) {
						Long sacHeadId = null;
						if (receiptFeeDetails.getRfLo3() != null && !receiptFeeDetails.getRfLo3().isEmpty()) {
							final VoucherPostDetailDTO dtoListFee = new VoucherPostDetailDTO();
							if (receiptFeeDetails.getRfLo3().equals("C") && (countC == 0)) {
								dtoListFee.setVoucherAmount(sumOfRecCurAmount);
								// PASSING FINANCIAL YEAR ID in query to getting voucher template, bill type is
								// 'C' only.
								VoucherTemplateMasterEntity template = voucherTemplateRepository.queryDefinedTemplate(
										voucherSubTypeIdC, dpDeptId, tbReceiptMasEntity.getOrgId(), status,
										financialYearId);
								List<VoucherTemplateDetailEntity> templateDetList = voucherTemplateRepository
										.queryDefinedTemplateDet((long) template.getTemplateId(),
												tbReceiptMasEntity.getOrgId());

								for (VoucherTemplateDetailEntity voucherTemplateDetailEntity : templateDetList) {
									if (voucherTemplateDetailEntity.getSacHeadId() != null) {
										sacHeadId = voucherTemplateDetailEntity.getSacHeadId();
										break;
									}
								}
								dtoListFee.setSacHeadId(sacHeadId);
								dtoListCr.add(dtoListFee);
								countC++;
							} else if (receiptFeeDetails.getRfLo3().equals("P") && (countP == 0)) {
								dtoListFee.setVoucherAmount(sumOfRecPreAmount);
								VoucherTemplateMasterEntity template = voucherTemplateRepository.queryDefinedTemplate(
										voucherSubTypeIdP, dpDeptId, tbReceiptMasEntity.getOrgId(), status, null);
								List<VoucherTemplateDetailEntity> templateDetList = voucherTemplateRepository
										.queryDefinedTemplateDet((long) template.getTemplateId(),
												tbReceiptMasEntity.getOrgId());
								for (VoucherTemplateDetailEntity voucherTemplateDetailEntity : templateDetList) {
									if (voucherTemplateDetailEntity.getSacHeadId() != null) {
										sacHeadId = voucherTemplateDetailEntity.getSacHeadId();
										break;
									}
								}
								dtoListFee.setSacHeadId(sacHeadId);
								dtoListCr.add(dtoListFee);
								countP++;
							} else if (receiptFeeDetails.getRfLo3().equals("A")) {
								dtoListFee.setVoucherAmount(receiptFeeDetails.getRfFeeamount());
								dtoListFee.setSacHeadId(receiptFeeDetails.getSacHeadId());
								dtoListCr.add(dtoListFee);
							}
						}
					}
				}
			}
		} else {
			for (final TbSrcptFeesDetEntity tbServiceReceiptMasEntityList : tbReceiptMasEntity.getReceiptFeeDetail()) {

				final VoucherPostDetailDTO dtoListFee = new VoucherPostDetailDTO();
				dtoListFee.setSacHeadId(tbServiceReceiptMasEntityList.getSacHeadId());
				dtoListFee.setVoucherAmount(tbServiceReceiptMasEntityList.getRfFeeamount());
				dtoListCr.add(dtoListFee);

			}
		}
		final VoucherPostDetailDTO dtoListMode = new VoucherPostDetailDTO();
		dtoListMode.setPayModeId(tbReceiptMasEntity.getReceiptModeDetail().get(0).getCpdFeemode());
		dtoListMode.setVoucherAmount(tbReceiptMasEntity.getReceiptModeDetail().get(0).getRdAmount());
		dtoListCr.add(dtoListMode);
		dto.setVoucherDetails(dtoListCr);
		// set all data
		postDtoList.add(dto);
		List<String> responseValidation = accountVoucherPostService.checkVoucherPostValidateInput(postDtoList);
		if (responseValidation.size() > 0) {
			throw new NullPointerException(
					"improper input parameter for VoucherPostDTO in receipt entry property tax -> "
							+ responseValidation);
		}
	}

	@Override
	public String validateInput(AccountReceiptDTO dto) {

		final StringBuilder builder = new StringBuilder();
		if ((dto.getReceiptPayeeName() == null) || dto.getReceiptPayeeName().isEmpty()) {
			builder.append(AccountReceiptEntry.PAYEE_NAME);
		}
		if ((dto.getRmNarration() == null) || dto.getRmNarration().isEmpty()) {
			builder.append(MainetConstants.AccountBillEntry.NARRATION);
		}
		if ((dto.getOrgId() == null) || (dto.getOrgId() == 0l)) {
			builder.append(MainetConstants.AccountBillEntry.ORG);
		}
		if ((dto.getCreatedBy() == null) || (dto.getCreatedBy() == 0l)) {
			builder.append(AccountReceiptEntry.CREATED_BY);
		}
		if (dto.getLangId() == 0) {
			builder.append(AccountReceiptEntry.LANG_ID);
		}
		if ((dto.getLgIpMac() == null) || dto.getLgIpMac().isEmpty()) {
			builder.append(AccountReceiptEntry.LG_IP_MAC);
		}

		// validating AccountRecieptDetailsDTO
		if ((dto.getReceiptFeeDetail() == null) || dto.getReceiptFeeDetail().isEmpty()) {
			builder.append(AccountReceiptEntry.RECEIPT_COLLECTION_DETAILS);
		} else {
			int count = 0;
			for (final SrcptFeesDetDTO detail : dto.getReceiptFeeDetail()) {
				if ((detail.getBudgetCodeid() == null) || (detail.getBudgetCodeid() == 0l)) {
					builder.append(AccountReceiptEntry.RECEIPTR_DETAILS + count)
							.append(AccountReceiptEntry.RECEIPT_HEAD);
				}
				if ((detail.getRfFeeamount() == null)) {
					builder.append(AccountReceiptEntry.VOUCHER_DETAILS + count).append(AccountReceiptEntry.FEEAMOUNT);
				}
				count++;
			}
		}
		// getting the receipt Mode code by passing paymodeid and orgid in PAY Prefix
		if (dto.getReceiptModeDetail() != null && dto.getReceiptModeDetail().getCpdFeemode() != null) {
			String recModeCode = CommonMasterUtility.findLookUpCode(PrefixConstants.LookUpPrefix.PAY_AT_COUNTER,
					dto.getOrgId(), dto.getReceiptModeDetail().getCpdFeemode());
			dto.getReceiptModeDetail().setCpdFeemodeCode(recModeCode);
		}
		// validating AccountRecieptModeDTO
		if (dto.getReceiptModeDetail() == null) {
			builder.append(AccountReceiptEntry.RECEIPT_COLLECTION_MODE_DETAILS);
		} else {
			if (dto.getReceiptModeDetail().getCpdFeemodeCode() == null) {
				builder.append(AccountReceiptEntry.MODE);
			} else {
				if (!dto.getReceiptModeDetail().getCpdFeemodeCode().equalsIgnoreCase(MainetConstants.MODE_CREATE)) {

					if (dto.getReceiptModeDetail().getBaAccountid() == null) {
						builder.append(AccountReceiptEntry.BANKNAME);
					}
					if (dto.getReceiptModeDetail().getTranRefNumber() == null
							|| dto.getReceiptModeDetail().getTranRefNumber().isEmpty()
							|| dto.getReceiptModeDetail().getTranRefNumber() == "") {
						builder.append(AccountReceiptEntry.TRN_NO);
					}
					if (dto.getReceiptModeDetail().getTranRefDatetemp() == null
							|| dto.getReceiptModeDetail().getTranRefDatetemp().isEmpty()
							|| dto.getReceiptModeDetail().getTranRefDatetemp() == "") {
						builder.append(AccountReceiptEntry.TRN_DATE);
					}
				}
			}
		}
		if (!builder.toString().isEmpty()) {
			builder.append(ApplicationSession.getInstance().getMessage("accounts.receipt.receiptmode.null"));
		}
		return builder.toString();
	}

	private String checkTemplate(final Long orgid) {

		final VoucherTemplateDTO postDTO = new VoucherTemplateDTO();
		String templateExistFlag = null;
		postDTO.setTemplateType(CommonMasterUtility.lookUpIdByLookUpCodeAndPrefix(PrefixConstants.ContraVoucherEntry.PN,
				PrefixConstants.ContraVoucherEntry.MTP, orgid));
		postDTO.setVoucherType(CommonMasterUtility.lookUpIdByLookUpCodeAndPrefix(
				MainetConstants.BILL_MASTER_COMMON.PREFIX_CODE_RV, PrefixConstants.ContraVoucherEntry.VOT, orgid));
		postDTO.setDepartment(departmentService.getDepartmentIdByDeptCode(AccountConstants.AC.toString()));
		postDTO.setTemplateFor(CommonMasterUtility.lookUpIdByLookUpCodeAndPrefix(
				MainetConstants.BILL_MASTER_COMMON.PREFIX_CODE_RV, PrefixConstants.REV_TYPE_CPD_VALUE, orgid));
		final boolean existTempalte = voucherTemplateService.isTemplateExist(postDTO, orgid);
		if (!existTempalte) {
			templateExistFlag = MainetConstants.MENU.N;
		} else {
			templateExistFlag = MainetConstants.MENU.Y;
		}
		return templateExistFlag;
	}

	public String checkBudgetCodeIdForFeeMode(final Long cpdFeemode, final Organisation org) {

		final Long orgid = org.getOrgid();
		String budgetCodeStatus = null;
		final Long activePrefix = CommonMasterUtility
				.getValueFromPrefixLookUp(PrefixConstants.STATUS_ACTIVE_PREFIX, PrefixConstants.STATUS_PREFIX, org)
				.getLookUpId();
		final Long budgetCodeId = tbTaxMasJpaRepository.fetchBudgetCodeIdForReceiptMode(orgid, cpdFeemode,
				activePrefix);
		if (budgetCodeId != null) {
			budgetCodeStatus = MainetConstants.MENU.Y;
		} else {
			budgetCodeStatus = MainetConstants.MENU.N;
		}
		return budgetCodeStatus;
	}

	public Long getReceiptNumber(final Long orgid, String transactionDate) {
		Long finYearId = financialYearJpaRepository.getFinanciaYearIdByFromDate(Utility.stringToDate(transactionDate));
		if (finYearId == null) {
			throw new NullPointerException(GENERATE_RECEIPT_NO_FIN_YEAR_ID);
		}
		final Long receiptNumber = seqGenFunctionUtility.generateSequenceNo(MainetConstants.RECEIPT_MASTER.Module,
				MainetConstants.RECEIPT_MASTER.Table, MainetConstants.RECEIPT_MASTER.Column, orgid,
				MainetConstants.SECONDARY_MASTER.SECONDARY_SEQ_CONTINUE_VALUE, finYearId);

		return receiptNumber;
	}

	@Override
	public List<VoucherReversalDTO> findRecordsForReversal(final String transactionType, final String transactionNo,
			final Date transactionDate, final String amount, final String transactionTypeId) {

		return null;
	}

	@Override
	@Transactional(readOnly = true)
	public Long getBudgetCodeIdForPrimaryReceitMode(final Long orgId, final Organisation org, final Long cpdFeemode) {

		final Long activePrefix = CommonMasterUtility
				.getValueFromPrefixLookUp(PrefixConstants.STATUS_ACTIVE_PREFIX, PrefixConstants.STATUS_PREFIX, org)
				.getLookUpId();
		final Long budgetCodeId = tbTaxMasJpaRepository.fetchBudgetCodeIdForPrimaryReceiptMode(orgId, cpdFeemode,
				activePrefix);
		return budgetCodeId;
	}

	private void cbPropertytaxReceiptVoucherPosting(final TbServiceReceiptMasEntity tbReceiptMasEntity, int longId,
			final long fieldId, Long financialYearId) {

		final VoucherPostDTO dto = new VoucherPostDTO();
		List<VoucherPostDTO> postDtoList = new ArrayList<>();
		dto.setCreatedBy(tbReceiptMasEntity.getCreatedBy());
		dto.setCreatedDate(tbReceiptMasEntity.getCreatedDate());
		dto.setDepartmentId(tbReceiptMasEntity.getDpDeptId());
		dto.setFieldId(tbReceiptMasEntity.getFieldId());
		dto.setLgIpMac(tbReceiptMasEntity.getLgIpMac());
		dto.setNarration(tbReceiptMasEntity.getRmNarration());
		dto.setOrgId(tbReceiptMasEntity.getOrgId());
		dto.setPayerOrPayee(tbReceiptMasEntity.getRmReceivedfrom());
		Organisation org = new Organisation();
		org.setOrgid(tbReceiptMasEntity.getOrgId());
		final LookUp payEntryMakerChecker = CommonMasterUtility.getLookUpFromPrefixLookUpValue(
				PrefixConstants.REV_SUB_CPD_VALUE, AccountPrefix.AUT.toString(), longId, org);
		if (payEntryMakerChecker.getLookUpCode() == null || payEntryMakerChecker.getLookUpCode().isEmpty()) {
			// dto.setVoucherDate(tbReceiptMasEntity.getRmDate());
			dto.setPayEntryMakerFlag(MainetConstants.Y_FLAG);
		} else {
			// dto.setVoucherDate(tbReceiptMasEntity.getCreatedDate());
		}

		dto.setVoucherDate(tbReceiptMasEntity.getRmDate());
		dto.setVoucherReferenceNo(tbReceiptMasEntity.getRmRcptno().toString());
		dto.setFieldId(fieldId);
		dto.setEntryType(MainetConstants.BILL_MASTER_COMMON.ENTRY_TYPE);
		dto.setVoucherSubTypeId(CommonMasterUtility.lookUpIdByLookUpCodeAndPrefix("RDC",
				PrefixConstants.REV_TYPE_CPD_VALUE, tbReceiptMasEntity.getOrgId()));
		final List<VoucherPostDetailDTO> dtoListCr = new ArrayList<>();
		if (tbReceiptMasEntity.getRmLo1() != null && !tbReceiptMasEntity.getRmLo1().isEmpty()) {
			if (tbReceiptMasEntity.getRmLo1().equals(MainetConstants.Y_FLAG)) {
				Long voucherSubTypeIdC = CommonMasterUtility.lookUpIdByLookUpCodeAndPrefix("DMD",
						PrefixConstants.REV_TYPE_CPD_VALUE, tbReceiptMasEntity.getOrgId());
				Long voucherSubTypeIdP = CommonMasterUtility.lookUpIdByLookUpCodeAndPrefix("DMP",
						PrefixConstants.REV_TYPE_CPD_VALUE, tbReceiptMasEntity.getOrgId());
				final Long status = CommonMasterUtility.lookUpIdByLookUpCodeAndPrefix(
						MainetConstants.CommonConstants.ACTIVE, AccountPrefix.ACN.toString(),
						tbReceiptMasEntity.getOrgId());
				Long dpDeptId = tbReceiptMasEntity.getDpDeptId();
				int countC = 0;
				int countP = 0;
				BigDecimal sumOfRecCurAmount = BigDecimal.ZERO;
				BigDecimal sumOfRecPreAmount = BigDecimal.ZERO;
				if (tbReceiptMasEntity.getReceiptFeeDetail() != null) {
					for (TbSrcptFeesDetEntity receiptFeeDetails : tbReceiptMasEntity.getReceiptFeeDetail()) {
						if (receiptFeeDetails.getRfLo3().equals("C")) {
							sumOfRecCurAmount = sumOfRecCurAmount.add(receiptFeeDetails.getRfFeeamount());
						}
						if (receiptFeeDetails.getRfLo3().equals("P")) {
							sumOfRecPreAmount = sumOfRecPreAmount.add(receiptFeeDetails.getRfFeeamount());
						}
					}
					for (TbSrcptFeesDetEntity receiptFeeDetails : tbReceiptMasEntity.getReceiptFeeDetail()) {
						Long sacHeadId = null;
						if (receiptFeeDetails.getRfLo3() != null && !receiptFeeDetails.getRfLo3().isEmpty()) {
							final VoucherPostDetailDTO dtoListFee = new VoucherPostDetailDTO();
							if (receiptFeeDetails.getRfLo3().equals("C") && (countC == 0)) {
								dtoListFee.setVoucherAmount(sumOfRecCurAmount);
								// PASSING FINANCIAL YEAR ID in query to getting voucher template, bill type is
								// 'C' only.
								VoucherTemplateMasterEntity template = voucherTemplateRepository.queryDefinedTemplate(
										voucherSubTypeIdC, dpDeptId, tbReceiptMasEntity.getOrgId(), status,
										financialYearId);
								List<VoucherTemplateDetailEntity> templateDetList = voucherTemplateRepository
										.queryDefinedTemplateDet((long) template.getTemplateId(),
												tbReceiptMasEntity.getOrgId());

								for (VoucherTemplateDetailEntity voucherTemplateDetailEntity : templateDetList) {
									if (voucherTemplateDetailEntity.getSacHeadId() != null) {
										sacHeadId = voucherTemplateDetailEntity.getSacHeadId();
										break;
									}
								}
								dtoListFee.setSacHeadId(sacHeadId);
								dtoListCr.add(dtoListFee);
								countC++;
							} else if (receiptFeeDetails.getRfLo3().equals("P") && (countP == 0)) {
								dtoListFee.setVoucherAmount(sumOfRecPreAmount);
								VoucherTemplateMasterEntity template = voucherTemplateRepository.queryDefinedTemplate(
										voucherSubTypeIdP, dpDeptId, tbReceiptMasEntity.getOrgId(), status, null);
								List<VoucherTemplateDetailEntity> templateDetList = voucherTemplateRepository
										.queryDefinedTemplateDet((long) template.getTemplateId(),
												tbReceiptMasEntity.getOrgId());
								for (VoucherTemplateDetailEntity voucherTemplateDetailEntity : templateDetList) {
									if (voucherTemplateDetailEntity.getSacHeadId() != null) {
										sacHeadId = voucherTemplateDetailEntity.getSacHeadId();
										break;
									}
								}
								dtoListFee.setSacHeadId(sacHeadId);
								dtoListCr.add(dtoListFee);
								countP++;
							} else if (receiptFeeDetails.getRfLo3().equals("A")) {
								dtoListFee.setVoucherAmount(receiptFeeDetails.getRfFeeamount());
								// srcptFeesDetDTO.setRfFeeid(receiptFeeDetails.getRfFeeid());
								// receiptFeeDetails.getRmRcptid();
								dtoListFee.setSacHeadId(receiptFeeDetails.getSacHeadId());
								dtoListCr.add(dtoListFee);
							}
						}
					}
				}
			}
		} else {
			for (final TbSrcptFeesDetEntity tbServiceReceiptMasEntityList : tbReceiptMasEntity.getReceiptFeeDetail()) {

				final VoucherPostDetailDTO dtoListFee = new VoucherPostDetailDTO();
				dtoListFee.setSacHeadId(tbServiceReceiptMasEntityList.getSacHeadId());
				dtoListFee.setVoucherAmount(tbServiceReceiptMasEntityList.getRfFeeamount());
				dtoListCr.add(dtoListFee);

			}
		}

		final VoucherPostDetailDTO dtoListMode = new VoucherPostDetailDTO();
		dtoListMode.setPayModeId(tbReceiptMasEntity.getReceiptModeDetail().get(0).getCpdFeemode());
		dtoListMode.setVoucherAmount(tbReceiptMasEntity.getReceiptModeDetail().get(0).getRdAmount());
		dtoListCr.add(dtoListMode);
		dto.setVoucherDetails(dtoListCr);
		// set all data
		ApplicationSession session = ApplicationSession.getInstance();
		postDtoList.add(dto);
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

	@Override
	public List<Object[]> findAllDataByReceiptId(Long rmRcptid, Long orgId) {
		// TODO Auto-generated method stub
		List<Object[]> list = accountReceiptEntrRrepository.findAllDataByReceiptId(rmRcptid, orgId);
		return list;
	}

	@Override
	public List<Object[]> findDataOfReceipt(String receiptTypeFlag, Long grantId, Long orgId) {
		// TODO Auto-generated method stub
		Assert.notNull(orgId, MainetConstants.ORGANISATION_ID_NOT_NULL);
		List<Object[]> list = accountReceiptEntrRrepository.findByOrgIdAndReceiptTypeFlag(grantId,receiptTypeFlag, orgId);
		return list;
	}


	@Override
	public BigDecimal getReceiptsAmount(Long refId, Long orgId,String receiptTypeFlag) {
		
		 
		Object amounts = accountReceiptEntrRrepository.getReceiptsAmount(refId, orgId,receiptTypeFlag);
		BigDecimal amt;
		if(amounts == null)
		{
			amt =BigDecimal.ZERO;
		}
	
		else 
		{
			String amountString = String.valueOf(amounts);
			Double d = Double.parseDouble(amountString);
			amt = BigDecimal.valueOf(d);
		}
		return amt;
	}
	@Transactional
	@Override
	public TbServiceReceiptMasBean findReceiptData(Long orgId, Long rmRcptno, Date rmDate, Long deptId) {
		TbServiceReceiptMasEntity receiptEntity = accountReceiptEntrRrepository.findReceiptData(rmRcptno, orgId, deptId, rmDate);
		TbServiceReceiptMasBean receptMasBean = accountReceiptEntryServiceMapper
				.mapTbServiceReceiptMasEntityToTbServiceReceiptMasBean(receiptEntity);
		if(receiptEntity!=null) {
		List<TbSrcptFeesDetEntity> detEntity = receiptEntity.getReceiptFeeDetail();
        for (TbSrcptFeesDetEntity detDTO : detEntity) {
            if (detDTO.getDepositeSlipId() != null) {
            	receptMasBean.setFlag(MainetConstants.TRUE);
            }
         }
		}
		return receptMasBean;
	}
	
	@Override
	@Transactional(rollbackFor = Exception.class)
	public TbServiceReceiptMasEntity saveExtReceipt(final AccountReceiptDTO accountReceiptDTO, long fieldId,String reciptCaragory)
			throws Exception {
		LOGGER.info("Provided input for saveReceipt :" + accountReceiptDTO);

		final Organisation org = new Organisation();
		org.setOrgid(accountReceiptDTO.getOrgId());

		Long receiptNo = null;
		TbServiceReceiptMasEntity tbServiceReceiptMasSave = null;

		final TbServiceReceiptMasEntity receiptMasEntity = new TbServiceReceiptMasEntity();
		TbServiceReceiptMasBean tbServiceReceiptMasBean = new TbServiceReceiptMasBean();

		final Long orgid = accountReceiptDTO.getOrgId();

		if (accountReceiptDTO.getDpDeptId() != null) {
			tbServiceReceiptMasBean.setDpDeptId(accountReceiptDTO.getDpDeptId());
		} else {
			tbServiceReceiptMasBean
					.setDpDeptId(departmentService.getDepartmentIdByDeptCode(AccountConstants.AC.toString()));
		}

		accountReceiptDTO.setReceiptDate(accountReceiptDTO.getReceiptDate());

		// tbServiceReceiptMasBean.setRmRcptid(accountReceiptDTO.getReceiptId());
		/*
		 * if ((accountReceiptDTO.getReceiptNo() != null) &&
		 * !accountReceiptDTO.getReceiptNo().isEmpty()) {
		 * tbServiceReceiptMasBean.setRmRcptno(Long.valueOf(accountReceiptDTO.
		 * getReceiptNo())); }
		 */
		if ((accountReceiptDTO.getReceiptAmount() != null) && !accountReceiptDTO.getReceiptAmount().isEmpty()) {
			tbServiceReceiptMasBean.setRmAmount(accountReceiptDTO.getReceiptAmount());
		}
		tbServiceReceiptMasBean.setRmReceivedfrom(accountReceiptDTO.getReceiptPayeeName());
		final SimpleDateFormat sdf = new SimpleDateFormat(MainetConstants.CommonConstants.D_FORMATE);
		Date date = null;
		if ((accountReceiptDTO.getReceiptDate() != null) && !accountReceiptDTO.getReceiptDate().isEmpty()) {
			date = sdf.parse(accountReceiptDTO.getReceiptDate());
		} else {
			date = new Date();
		}
		tbServiceReceiptMasBean.setRmDate(date);
		tbServiceReceiptMasBean.setTransactionDate(Utility.dateToString(date));
		tbServiceReceiptMasBean.setRmAmount(accountReceiptDTO.getRmReceiptAmt().toString());
		tbServiceReceiptMasBean.setOrgId(accountReceiptDTO.getOrgId());
		tbServiceReceiptMasBean.setCreatedBy(accountReceiptDTO.getCreatedBy());
		tbServiceReceiptMasBean.setLangId(accountReceiptDTO.getLangId());
		tbServiceReceiptMasBean.setCreatedDate(new Date());
		tbServiceReceiptMasBean.setLgIpMac(accountReceiptDTO.getLgIpMac());
		// tbServiceReceiptMasBean.getTbAcFieldMaster().setFieldId(fieldId);

		tbServiceReceiptMasBean.setVmVendorId(accountReceiptDTO.getVmVendorId());
		tbServiceReceiptMasBean.setMobileNumber(accountReceiptDTO.getMobileNumber());
		tbServiceReceiptMasBean.setEmailId(accountReceiptDTO.getEmailId());
		tbServiceReceiptMasBean.setRmNarration(accountReceiptDTO.getRmNarration());
		tbServiceReceiptMasBean.setRmReceivedfrom(accountReceiptDTO.getReceiptPayeeName());

		if (accountReceiptDTO.getDpDeptId() != null) {
			tbServiceReceiptMasBean.setDpDeptId(accountReceiptDTO.getDpDeptId());
		} else {
			tbServiceReceiptMasBean
					.setDpDeptId(departmentService.getDepartmentIdByDeptCode(AccountConstants.AC.toString()));
		}

		// check it receipt mode details
		final TbSrcptModesDetBean tbSrcptModesDetBean = new TbSrcptModesDetBean();
		// tbSrcptModesDetBean.setRdModesid(accountReceiptDTO.getReceiptModeDetail().getRdModesid());
		tbSrcptModesDetBean.setCpdFeemode(accountReceiptDTO.getReceiptModeDetail().getCpdFeemode());

		String payModeCode = CommonMasterUtility.findLookUpCode(PrefixConstants.PAY_PREFIX.PREFIX_VALUE,
				accountReceiptDTO.getOrgId(), accountReceiptDTO.getReceiptModeDetail().getCpdFeemode());

		if (accountReceiptDTO.getReceiptModeDetail() != null
				&& (accountReceiptDTO.getReceiptModeDetail().getCpdFeemodeCode() != null
						&& !accountReceiptDTO.getReceiptModeDetail().getCpdFeemodeCode().isEmpty())) {
			tbSrcptModesDetBean.setCpdFeemodeCode(accountReceiptDTO.getReceiptModeDetail().getCpdFeemodeCode());
		} else {
			if (payModeCode != null && !payModeCode.isEmpty()) {
				tbSrcptModesDetBean.setCpdFeemodeCode(payModeCode);
			}
		}
		if(accountReceiptDTO.getReceiptModeDetail().getCbBankidDesc()!=null)
		tbSrcptModesDetBean.setRdDrawnon(accountReceiptDTO.getReceiptModeDetail().getCbBankidDesc());

		if (!tbSrcptModesDetBean.getCpdFeemodeCode().isEmpty() && (tbSrcptModesDetBean.getCpdFeemodeCode() != null)) {
			if (tbSrcptModesDetBean.getCpdFeemodeCode().equals(MainetConstants.Complaint.MODE_CREATE)) {
			} else {
				if (tbSrcptModesDetBean.getCpdFeemodeCode().equals(PrefixConstants.WATERMODULEPREFIX.RT)
						|| tbSrcptModesDetBean.getCpdFeemodeCode().equals(MainetConstants.MENU.N)
						|| tbSrcptModesDetBean.getCpdFeemodeCode().equals(PrefixConstants.PaymentMode.BANK)) {
					tbSrcptModesDetBean.setBaAccountid(accountReceiptDTO.getReceiptModeDetail().getBaAccountid());
					tbSrcptModesDetBean.setTranRefNumber(accountReceiptDTO.getReceiptModeDetail().getTranRefNumber());
					tbSrcptModesDetBean
							.setTranRefDatetemp(accountReceiptDTO.getReceiptModeDetail().getTranRefDatetemp());
					tbSrcptModesDetBean.setRdChequedddate(
							sdf.parse(accountReceiptDTO.getReceiptModeDetail().getTranRefDatetemp()));
				} else {
					tbSrcptModesDetBean.setCbBankid(accountReceiptDTO.getReceiptModeDetail().getCbBankid());
					tbSrcptModesDetBean
							.setRdChequeddno(Long.valueOf(accountReceiptDTO.getReceiptModeDetail().getTranRefNumber()));
					tbSrcptModesDetBean.setRdChequedddate(
							sdf.parse(accountReceiptDTO.getReceiptModeDetail().getTranRefDatetemp()));
					tbSrcptModesDetBean
							.setTranRefDatetemp(accountReceiptDTO.getReceiptModeDetail().getTranRefDatetemp());
					tbSrcptModesDetBean
							.setRdChequedddatetemp(accountReceiptDTO.getReceiptModeDetail().getTranRefDatetemp());
					try {
						LookUp lookUp = CommonMasterUtility.getValueFromPrefixLookUp(PrefixConstants.LookUpPrefix.RND,
								PrefixConstants.LookUpPrefix.CLR, new Organisation(tbServiceReceiptMasBean.getOrgId()));
						tbSrcptModesDetBean.setCheckStatus(lookUp.getLookUpId());
						}catch(Exception ex) {
							LOGGER.error("Error while getting prefix value", ex);
						}
					tbSrcptModesDetBean.setRdSrChkDis("1");
				}
			}
		}
		tbSrcptModesDetBean.setRdAmount(accountReceiptDTO.getReceiptModeDetail().getRdAmount());
		tbServiceReceiptMasBean.setReceiptModeDetailList(tbSrcptModesDetBean);
		tbServiceReceiptMasBean.setReceiptModeList(Arrays.asList(tbSrcptModesDetBean));
		// check it receipt collection details
		final List<TbSrcptFeesDetBean> receiptFeeDetail = new ArrayList<>();
		for (final SrcptFeesDetDTO dto : accountReceiptDTO.getReceiptFeeDetail()) {
			final TbSrcptFeesDetBean receiptFee = new TbSrcptFeesDetBean();

			receiptFee.setSacHeadId(dto.getBudgetCodeid());
			receiptFee.setRfFeeamount(dto.getRfFeeamount());
			receiptFee.setRfLo3(dto.getBillType());
			receiptFeeDetail.add(receiptFee);
		}
		tbServiceReceiptMasBean.setReceiptFeeDetail(receiptFeeDetail);

		final Date curDate = new Date();
		final Long empId = accountReceiptDTO.getCreatedBy();
		final String macAddress = accountReceiptDTO.getLgIpMac();
		accountReceiptDTO.getLangId();

		accountReceiptEntryServiceMapper.mapTbServiceReceiptMasBeanToTbServiceReceiptMasEntity(tbServiceReceiptMasBean,
				receiptMasEntity);
		List<TbSrcptModesDetEntity> modeList = new ArrayList<>();
		TbSrcptModesDetEntity dto=new TbSrcptModesDetEntity();
		dto.setCpdFeemode(tbServiceReceiptMasBean.getReceiptModeDetailList().getCpdFeemode());
		dto.setRdAmount(tbServiceReceiptMasBean.getReceiptModeDetailList().getRdAmount());
		dto.setBaAccountid(tbServiceReceiptMasBean.getReceiptModeDetailList().getBaAccountid());
		dto.setRdChequedddate(tbServiceReceiptMasBean.getReceiptModeDetailList().getRdChequedddate());
		dto.setTranRefNumber(tbServiceReceiptMasBean.getReceiptModeDetailList().getTranRefNumber());
		dto.setRdChequeddno(tbServiceReceiptMasBean.getReceiptModeDetailList().getRdChequeddno());
		dto.setCbBankid(tbServiceReceiptMasBean.getReceiptModeDetailList().getCbBankid());
		dto.setRdDrawnon(tbServiceReceiptMasBean.getReceiptModeDetailList().getRdDrawnon());
		modeList.add(dto);
		receiptMasEntity.setReceiptModeDetail(modeList);
		receiptMasEntity.getReceiptModeDetail().get(0).setRmRcptid(receiptMasEntity);
		receiptMasEntity.getReceiptModeDetail().get(0).setOrgId(orgid);
		receiptMasEntity.getReceiptModeDetail().get(0).setCreatedBy(empId);
		receiptMasEntity.getReceiptModeDetail().get(0).setLgIpMac(macAddress);
		receiptMasEntity.getReceiptModeDetail().get(0).setCreatedDate(new Date());
		final List<LookUp> recieptVouType = CommonMasterUtility.getListLookup(
				MainetConstants.BILL_MASTER_COMMON.PREFIX_CODE_RV, new Organisation(orgid));
		 Optional<Long> receiptGatagory = recieptVouType.stream().filter(f->f.getLookUpCode().equalsIgnoreCase(reciptCaragory)).map(m->m.getLookUpId()).findFirst();
		 if(receiptGatagory.isPresent())
		 receiptMasEntity.setRmReceiptcategoryId(receiptGatagory.get());
		if (tbSrcptModesDetBean.getRdSrChkDis() != null && !tbSrcptModesDetBean.getRdSrChkDis().isEmpty()) {
			receiptMasEntity.getReceiptModeDetail().get(0).setRdSrChkDis(tbSrcptModesDetBean.getRdSrChkDis());
		}
		if (tbSrcptModesDetBean.getCheckStatus() != null) {
			receiptMasEntity.getReceiptModeDetail().get(0).setCheckStatus(tbSrcptModesDetBean.getCheckStatus());
		}
		receiptMasEntity.setRmAmount(tbSrcptModesDetBean.getRdAmount());
		org.setOrgid(orgid);
		// tbTaxMasJpaRepository.fetchBudgetCodeIdForReceiptMode(orgid,receiptMasEntity.getReceiptModeDetail().getCpdFeemode(),
		// activePrefix);

		final Date rdchequedddate = Utility
				.stringToDate(tbServiceReceiptMasBean.getReceiptModeDetailList().getRdChequedddatetemp());
		receiptMasEntity.getReceiptModeDetail().get(0).setRdChequedddate(rdchequedddate);

		final Date tranrefdate = Utility
				.stringToDate(tbServiceReceiptMasBean.getReceiptModeDetailList().getTranRefDatetemp());
		receiptMasEntity.getReceiptModeDetail().get(0).setTranRefDate(tranrefdate);
		// receiptMasEntity.setLgIpMac(macAddress);
		receiptMasEntity.setFieldId(fieldId);
		receiptMasEntity.setReceiptTypeFlag(reciptCaragory);
		if (accountReceiptDTO.getRecProperyTaxFlag() != null && !accountReceiptDTO.getRecProperyTaxFlag().isEmpty()) {
			receiptMasEntity.setRmLo1(accountReceiptDTO.getRecProperyTaxFlag());
			tbServiceReceiptMasBean.setRecProperyTaxFlag(accountReceiptDTO.getRecProperyTaxFlag());
		}
		if (accountReceiptDTO.getReceiptNumber() != null && !accountReceiptDTO.getReceiptNumber().isEmpty()) {
			receiptMasEntity.setManualReceiptNo(accountReceiptDTO.getReceiptNumber());
		}
		/*
		 * if (accountReceiptDTO.getReceiptNumber() != null &&
		 * !accountReceiptDTO.getReceiptNumber().isEmpty()) {
		 * receiptMasEntity.setRefId(Long.valueOf(accountReceiptDTO.getReceiptNumber()))
		 * ; }
		 */
		Long entryTypeId = CommonMasterUtility.lookUpIdByLookUpCodeAndPrefix(AccountConstants.EEC.getValue(),
				AccountPrefix.VET.toString(), orgid);
		if(entryTypeId!=null)
		receiptMasEntity.setRmN4(entryTypeId);
		if (accountReceiptDTO.getRecPropertytaxRefId() != null) {
			receiptMasEntity.setRefId(accountReceiptDTO.getRecPropertytaxRefId());
		}

		// receipt voucher property tax posting validate to given VoucherPostDTO
		receiptVoucherPostingValidation(receiptMasEntity, accountReceiptDTO.getLangId(), fieldId);

		if (tbServiceReceiptMasBean.getReceiptModeDetailList().getCpdFeemodeCode()
				.equals(PrefixConstants.WATERMODULEPREFIX.RT)
				|| tbServiceReceiptMasBean.getReceiptModeDetailList().getCpdFeemodeCode().equals(MainetConstants.MENU.N)) {
			// bank deposit slip receipt integration voucher property tax posting validate
			// to given VoucherPostDTO
			bankDepositSlipVoucherPostingValidation(tbServiceReceiptMasBean, fieldId);
		}

		receiptNo = getReceiptNumber(orgid, accountReceiptDTO.getReceiptDate());
		accountReceiptDTO.setReceiptNo(receiptNo.toString());

		for (final TbSrcptFeesDetEntity receiptDetail : receiptMasEntity.getReceiptFeeDetail()) {
			receiptDetail.setRmRcptid(receiptMasEntity);
			receiptDetail.setOrgId(orgid);
			receiptDetail.setCreatedDate(curDate);
			receiptDetail.setLgIpMac(macAddress);
			receiptDetail.setCreatedBy(empId);
			depositEntry(tbServiceReceiptMasBean, receiptDetail);
		}
		receiptMasEntity.setRmRcptno(receiptNo);
		tbServiceReceiptMasSave = accountReceiptEntrRrepository.save(receiptMasEntity);
		//cbPropertytaxReceiptVoucherPosting(tbServiceReceiptMasSave, accountReceiptDTO.getLangId(), fieldId,
			//	accountReceiptDTO.getFinancialYearId());
		voucherPosting(tbServiceReceiptMasSave, tbServiceReceiptMasBean.getLangId(), fieldId);
		if (tbServiceReceiptMasBean.getReceiptModeDetailList().getCpdFeemodeCode()
				.equals(PrefixConstants.WATERMODULEPREFIX.RT)
				|| tbServiceReceiptMasBean.getReceiptModeDetailList().getCpdFeemodeCode().equals(MainetConstants.MENU.N)) {
			final AccountChequeOrCashDepositeBean bean = bankDepositEntry(tbServiceReceiptMasBean, fieldId);
			accountReceiptHeadJpaRepository.updateReceiptWiseDepositId(tbServiceReceiptMasSave.getRmRcptid(),
					bean.getDepositeSlipId());
		}
		tbServiceReceiptMasBean = accountReceiptEntryServiceMapper
				.mapTbServiceReceiptMasEntityToTbServiceReceiptMasBean(tbServiceReceiptMasSave);
				 TbSrcptModesDetBean modeBean = new TbSrcptModesDetBean();
				 BeanUtils.copyProperties(tbServiceReceiptMasSave.getReceiptModeDetail().get(0),modeBean);
				 tbServiceReceiptMasBean.setReceiptModeDetailList(modeBean);
		return tbServiceReceiptMasSave;
	}
	
	
	
	@Override
	 public List<String> validateExternalSystemDTOInput(AccountReceiptExternalDto receiptEternalDto) {
	       
		     LOGGER.info("Provided input for External System Voucher Posting validation:" + receiptEternalDto);
	         List<String> errorMessageList = new ArrayList<>();
	         StringBuilder builder = new StringBuilder();
	         Organisation organisation=null;
	         try {
	          organisation = ApplicationContextProvider.getApplicationContext().getBean(IOrganisationService.class).getActiveOrgByUlbShortCode(receiptEternalDto.getUlbCode());
	         }catch(Exception e) {
	        	 builder.append("Invalid  ULB code"); 
	        	 errorMessageList.add(builder.toString());
	        	 LOGGER.info("ULb code :"+receiptEternalDto.getUlbCode());
	        	 return errorMessageList;
	         }
	         Long  orgId=organisation.getOrgid();
	         if(receiptEternalDto.getReceiptDate() == null) {
	        	builder.append("Receipt Date should not be empty, ");
	        }else {
	        	final LookUp lookUp = CommonMasterUtility.getValueFromPrefixLookUp(PrefixConstants.NEC.ADVOCATE,
						MainetConstants.BUG_HEAD_OPENING_BALANCE_MASTER.SLI_PREFIX_VALUE,
						organisation);
				String sliDate = lookUp.getOtherField();
				Date SLIDate = null;
				try {
					SLIDate = new SimpleDateFormat("dd/MM/yyyy").parse(sliDate);
				} catch (ParseException e) {
					e.printStackTrace();
				}
	        	if(Utility.compareDate(receiptEternalDto.getReceiptDate(),SLIDate)) {
	        		builder.append("Receipt Date can not be less than SLI date, ");
	        	}
	        	if(Utility.compareDate(new Date(),receiptEternalDto.getReceiptDate())) {
	        		builder.append("Receipt Date can not greater thane current date, ");
	        	}
	        	
	        }
	        
	        if(StringUtils.isBlank(receiptEternalDto.getReceiptCategory())) {
	        	builder.append("Receipt Category, ");
	        }
	        if(StringUtils.isBlank(receiptEternalDto.getReceivedFrom())) {
	        	builder.append("Received Name should not be empty, ");
	        }
	        if(StringUtils.isBlank(receiptEternalDto.getCheckSum())) {
	        	builder.append("Checksum should not be empty, ");
	        }
	        if(StringUtils.isBlank(receiptEternalDto.getDepartmentName())) {
	        	builder.append("Department Name should not be empty, ");
	        }else {
	        	Long departmentId = departmentService.getDepartmentIdByDeptCode(receiptEternalDto.getDepartmentName(),
						MainetConstants.STATUS.ACTIVE);
		        if(departmentId == null || departmentId <=0) {
		        	builder.append("We cant find department based on your Department Name. Invalid Department Name, ");
		        }
	        }
	        if(StringUtils.isBlank(receiptEternalDto.getNarration())) {
	        	builder.append("Narration should not be empty, ");
	        }
	        if(receiptEternalDto.getCreatedBy() == null) {
	        	builder.append("Created By should not be empty, ");
	        }else {
	        	 Employee employee = ApplicationContextProvider.getApplicationContext().getBean(IEmployeeService.class).findEmployeeById(receiptEternalDto.getCreatedBy());
	        	 if(employee==null) {
	        		 builder.append("Employee not found against creadted by id,");
	        	 }   
	        }
	       
	        if(receiptEternalDto.getReceiptCategory().equals("P")) {
	          if(StringUtils.isBlank(receiptEternalDto.getVendorName())) {
	        	 builder.append("vendor name should not be empty,");
	          }else {
	        	  final LookUp lookUpVendorStatus = CommonMasterUtility.getLookUpFromPrefixLookUpValue(
	      				AccountConstants.AC.getValue(), PrefixConstants.VSS, 1,
	      				organisation);
	      		final Long vendorStatus = lookUpVendorStatus.getLookUpId();
	        	  final List<TbAcVendormaster> list = tbAcVendormasterService.getActiveVendors(orgId, vendorStatus);
	        	  if(CollectionUtils.isEmpty(list.stream().filter(f->f.getVmVendorcode().equalsIgnoreCase(receiptEternalDto.getVendorName())).collect(Collectors.toList()))) {
	        		  builder.append("vendor name Wrong,");
	        	  }
	          }
	        }
	        
	        if(receiptEternalDto.getReceiptCategory().equals("R")) {
	        	for(AccountReceiptFeesExternalDto dto:receiptEternalDto.getReceiptFeeDetailList()) {
				/*
				 * if(dto.getDemColCode()!=null) { if(dto.getDemColCode().equals("DMD")) {
				 * if(dto.getFinancialYear()==null)
				 * builder.append("fianacial year for DMD type ,"); } }else {
				 * builder.append("demand code ,"); }
				 */
	        	}
	        }
	        
	        if(StringUtils.isBlank(receiptEternalDto.getUlbCode())) {
	        	builder.append("ULB Code should not be empty, ");
	        }else {
	 	        if(organisation == null) {
	 	        	builder.append("We cant find ULB based on your ULB Short Code. Invalid ULB Code, ");
	 	        }/*else {
	 	        	 if(StringUtils.isBlank(receiptEternalDto.getFieldCode())) {
	 		        	builder.append("Field Code, ");
	 		        }else {
	 		        	Long fieldIdByFieldCompositCode = ApplicationContextProvider.getApplicationContext().getBean(AccountFieldMasterService.class).getFieldIdByFieldCompositCode(receiptEternalDto.getFieldCode(), organisation.getOrgid());
	 		        	
	 		        	if(fieldIdByFieldCompositCode == null || fieldIdByFieldCompositCode <0) {
	 		        		builder.append("We cant find Field id based on your Field Short Code. Invalid Field Code, ");
	 		        	}
	 		        }
	 	        	
	 	        }*/
	        }
	        
	   
	   
	     if(StringUtils.isBlank(receiptEternalDto.getPayMode())) {
	        	builder.append("PayMode should not be empty, ");
		} else {
			if (!StringUtils.equals(receiptEternalDto.getPayMode(),"C")) {//cash
				if (StringUtils.isBlank(receiptEternalDto.getBankName())) {
					builder.append("Bank Name should not be empty, ");
				}/*else {
					Boolean isBank=false;
					 * if (receiptEternalDto.getPayMode().equalsIgnoreCase(MainetConstants.
					 * AccountConstants.CHEQUE.getValue())
					 * ||receiptEternalDto.getPayMode().equalsIgnoreCase(MainetConstants.
					 * AccountConstants.D.getValue())) { List<BankMasterDTO> bankmaster =
					 * ApplicationContextProvider.getApplicationContext().getBean(BankMasterService.
					 * class).getBankListDto(); for(BankMasterDTO dto:bankmaster) {
					 * if((dto.getBank()+" - "+dto.getBranch()+" - "+dto.getIfsc()).equalsIgnoreCase
					 * (receiptEternalDto.getBankName())) { isBank=true; } } }
					 */
					/*
					 * if (receiptEternalDto.getPayMode().equalsIgnoreCase(MainetConstants.
					 * AccountConstants.BANK.getValue())) { final Long statusId =
					 * CommonMasterUtility
					 * .lookUpIdByLookUpCodeAndPrefix(PrefixConstants.PaymentMode.USER_ADUSTMENT,
					 * PrefixConstants.BAS, organisation.getOrgid()); List<Object[]>
					 * bankAccountList; bankAccountList =
					 * tbbankAccountService.getActiveBankAccountList(organisation.getOrgid(),
					 * statusId); for (final Object[] bankAc : bankAccountList) {
					 * if((bankAc[3]+MainetConstants.SEPARATOR+(bankAc[1].toString().trim())+
					 * MainetConstants.SEPARATOR+(bankAc[2].toString().trim())).equalsIgnoreCase(
					 * receiptEternalDto.getBankName())) { isBank=true; } }
					 * 
					 * } if(isBank==false) { builder.append("Bank name not found,"); }}
					 */				
				if (StringUtils.isBlank(receiptEternalDto.getInstrumentNo())) {
					builder.append("Instrument Number should not be empty, ");
				}
				if (receiptEternalDto.getInstrumentDate() == null) {
					builder.append("Instrument Date should not be empty, ");
				}
			}
		}
	        
	     if(CollectionUtils.isNotEmpty(receiptEternalDto.getReceiptFeeDetailList())) {
	      receiptEternalDto.getReceiptFeeDetailList().forEach(feeDto ->{
	        	if(feeDto.getReceptAmount() == null || feeDto.getReceptAmount().compareTo(BigDecimal.ZERO) == 0) {
	        		builder.append("Receipt Amount should not be zero, ");
	        	}
	        	
	        	if(feeDto.getReceiptHead() == null) {
	        		builder.append("Receipt Head should no be empty, ");
	        	}
	        	 if(!"DMD".equals(feeDto.getDemColCode()) && ! ("DMP".equals(feeDto.getDemColCode())) || feeDto.getDemColCode()==null) {
				     List<SecondaryheadMaster> list = ApplicationContextProvider.getApplicationContext().getBean(SecondaryheadMasterService.class).findAll(orgId);
				     boolean isHeadExist=false;
				     for(SecondaryheadMaster dto:list) {
				    	 if((dto.getAcHeadCode().replaceAll(" ","").equals(feeDto.getReceiptHead().replaceAll(" ","")))) {
				    		 isHeadExist=true;
						  }
				    }
				     if(isHeadExist==false) {
				    	 builder.append("Receipt Head not found");
				     }
				 } 
	        });
	     }else {
	    	 builder.append("Receipt Head should no be empty,");
	     }
	    
         if (!builder.toString().isEmpty()) {
             errorMessageList.add(builder.toString());
         }
			return errorMessageList;
	 }
	 
	@Override
	 public AccountReceiptDTO convertExternalReceiptDtoToInternalReceiptDto(AccountReceiptExternalDto externalDto) {
		 LOGGER.info("Enter into the convertExternalReceiptDtoToInternalReceiptDto method");
		 AccountReceiptDTO internalReceiptDto = new AccountReceiptDTO();
		 LookUp payTypeLookUp  = null;
		 SrcptModesDetDTO modeTypeDto = new SrcptModesDetDTO();
         List<SrcptFeesDetDTO> feeDtoList = new ArrayList<>();
         BigDecimal receiptAmount = BigDecimal.ZERO;
		 Organisation organisation = ApplicationContextProvider.getApplicationContext().getBean(IOrganisationService.class).getActiveOrgByUlbShortCode(externalDto.getUlbCode());
		 if(organisation != null) {
				 payTypeLookUp = CommonMasterUtility.getValueFromPrefixLookUp(externalDto.getPayMode(), "PAY",
				organisation);
			 internalReceiptDto.setOrgId(organisation.getOrgid());
		 }
		 internalReceiptDto.setDpDeptId(departmentService.getDepartmentIdByDeptCode(externalDto.getDepartmentName(),
					MainetConstants.STATUS.ACTIVE));
		 internalReceiptDto.setReceiptDate( new SimpleDateFormat(MainetConstants.DATE_FORMAT)
		         .format(externalDto.getReceiptDate()));
		 //internalReceiptDto.setReceiptNo(externalDto.getReceiptNumber());
		 internalReceiptDto.setReceiptNumber(externalDto.getReceiptNumber());
		 internalReceiptDto.setReceiptPayeeName(externalDto.getReceivedFrom());
		 internalReceiptDto.setCreatedBy(externalDto.getCreatedBy());
		 //internalReceiptDto.setVmVendorId(externalDto.getVendorName());
		 internalReceiptDto.setMobileNumber(externalDto.getMobileNumber());
		 internalReceiptDto.setEmailId(externalDto.getEmailId());
		 internalReceiptDto.setRmNarration(externalDto.getNarration());
		 if(externalDto.getReceiptCategory().equalsIgnoreCase("P")) {
			 final LookUp lookUpVendorStatus = CommonMasterUtility.getLookUpFromPrefixLookUpValue(
	      				AccountConstants.AC.getValue(), PrefixConstants.VSS, 1,
	      				organisation);
	      		final Long vendorStatus = lookUpVendorStatus.getLookUpId();
	        final List<TbAcVendormaster> list = tbAcVendormasterService.getActiveVendors(organisation.getOrgid(), vendorStatus);
	        List<TbAcVendormaster> newList = list.stream().filter(f->f.getVmVendorcode().equalsIgnoreCase(externalDto.getVendorName())).collect(Collectors.toList());
	        internalReceiptDto.setVmVendorId(newList.get(0).getVmVendorid());
	        	  
		 }
		 /*final List<LookUp> recieptVouType = CommonMasterUtility.getListLookup(
					MainetConstants.BILL_MASTER_COMMON.PREFIX_CODE_RV, UserSession.getCurrent().getOrganisation());
		 List<LookUp> vouList = recieptVouType.stream().filter(r->r!=null && r.getLookUpCode().equalsIgnoreCase(externalDto.getReceiptCategory())).collect(Collectors.toList());
		 internalReceiptDto.setReceiptCategory(vouList.get(0).getLookUpId());*/
		 
		 internalReceiptDto.setLangId(1);
		 internalReceiptDto.setFieldId(ApplicationContextProvider.getApplicationContext().getBean(AccountFieldMasterService.class).getFieldIdByFieldCompositCode(externalDto.getFieldCode(), organisation.getOrgid()));
		 List<BankMasterDTO> bankmaster = ApplicationContextProvider.getApplicationContext().getBean(BankMasterService.class).getBankListDto();
		if (payTypeLookUp != null) {
			if (externalDto.getPayMode().equalsIgnoreCase(MainetConstants.AccountConstants.CHEQUE.getValue())||externalDto.getPayMode().equalsIgnoreCase(MainetConstants.AccountConstants.D.getValue())) {
				modeTypeDto.setCpdFeemodeCode(MainetConstants.AccountConstants.CHEQUE.getValue());
				modeTypeDto.setCpdFeemode(payTypeLookUp.getLookUpId());
				modeTypeDto.setTranRefNumber(externalDto.getInstrumentNo());
				modeTypeDto.setTranRefDate(externalDto.getInstrumentDate());
				modeTypeDto.setTranRefDatetemp(Utility.dateToString(externalDto.getInstrumentDate()));
				modeTypeDto.setCbBankidDesc(externalDto.getBankName());
				/*
				 * Optional<Long> bankId =
				 * bankmaster.stream().filter(dto->(dto.getBank()+MainetConstants.SEPARATOR+dto.
				 * getBranch()+MainetConstants.SEPARATOR+dto.getIfsc()).equalsIgnoreCase(
				 * externalDto.getBankName())).map(m->{ return m.getBankId(); }).findFirst();
				 */
			    // modeTypeDto.setCbBankid(bankId.get()); 
				SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
				modeTypeDto.setTranRefDatetemp(dateFormat.format(externalDto.getInstrumentDate()));
			} else if (externalDto.getPayMode().equalsIgnoreCase(MainetConstants.AccountConstants.BANK.getValue())) {
				modeTypeDto.setCpdFeemode(payTypeLookUp.getLookUpId());
				modeTypeDto.setCpdFeemodeCode(MainetConstants.AccountConstants.BANK.getValue());
				final Long statusId = CommonMasterUtility
						.lookUpIdByLookUpCodeAndPrefix(PrefixConstants.PaymentMode.USER_ADUSTMENT, PrefixConstants.BAS, organisation.getOrgid());
				/*
				 * List<Object[]> bankAccountList; bankAccountList =
				 * tbbankAccountService.getActiveBankAccountList(organisation.getOrgid(),
				 * statusId); for (final Object[] bankAc : bankAccountList) {
				 * if((bankAc[3]+MainetConstants.SEPARATOR+(bankAc[1].toString().trim())+
				 * MainetConstants.SEPARATOR+(bankAc[2].toString().trim())).equalsIgnoreCase(
				 * externalDto.getBankName())) { modeTypeDto.setBaAccountid((Long) bankAc[0]); }
				 * }
				 */
				modeTypeDto.setCbBankidDesc(externalDto.getBankName());
				modeTypeDto.setTranRefNumber(externalDto.getInstrumentNo());
				modeTypeDto.setTranRefDatetemp(Utility.dateToString(externalDto.getInstrumentDate()));
				modeTypeDto.setTranRefDate(externalDto.getInstrumentDate());
			}else {
				modeTypeDto.setCpdFeemodeCode(MainetConstants.AccountConstants.CASH.getValue());
				modeTypeDto.setCpdFeemode(payTypeLookUp.getLookUpId());
			}
		}
	  if(externalDto.getReceiptFeeDetailList()!=null) {
		 for (AccountReceiptFeesExternalDto externalFeeDto : externalDto.getReceiptFeeDetailList()) {
			 SrcptFeesDetDTO feeDto = new SrcptFeesDetDTO();
			 if(!"DMD".equals(externalFeeDto.getDemColCode()) && ! ("DMP".equals(externalFeeDto.getDemColCode())) || externalFeeDto.getDemColCode()==null) {
			     List<SecondaryheadMaster> list = ApplicationContextProvider.getApplicationContext().getBean(SecondaryheadMasterService.class).findAll(organisation.getOrgid());
			     list.forEach(dto->{
			    	 if(dto.getAcHeadCode().replaceAll(" ","").equalsIgnoreCase(externalFeeDto.getReceiptHead().replaceAll(" ",""))) {
						 feeDto.setBudgetCodeid(dto.getSacHeadId());
					  }
			    });
			 } 
			 feeDto.setRfFeeamount(externalFeeDto.getReceptAmount());
			 BigDecimal val = BigDecimal.ZERO;
			 val=externalFeeDto.getReceptAmount();
			 receiptAmount= receiptAmount.add(val);
			 if(externalFeeDto.getFinancialYear()!=null) {
			 internalReceiptDto.setFinancialYearId(financialYearJpaRepository.getFinanceYearIds(Utility.stringToDate("01/04/"+externalFeeDto.getFinancialYear().substring(0,4))));
			 }
			 feeDtoList.add(feeDto);
		}
	 }
		 modeTypeDto.setRdAmount(receiptAmount);
		 internalReceiptDto.setRmReceiptAmt(receiptAmount);
		 internalReceiptDto.setReceiptModeDetail(modeTypeDto);
		 internalReceiptDto.setReceiptFeeDetail(feeDtoList);
		 LOGGER.info("Leaving from convertExternalReceiptDtoToInternalReceiptDto method");
		 return internalReceiptDto;
	 }
}
