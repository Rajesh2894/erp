package com.abm.mainet.account.service;

import java.lang.reflect.InvocationTargetException;
import java.math.BigDecimal;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;

import javax.annotation.Resource;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.abm.mainet.account.domain.AccountVoucherEntryDetailsEntity;
import com.abm.mainet.account.domain.AccountVoucherEntryDetailsHistEntity;
import com.abm.mainet.account.domain.AccountVoucherEntryEntity;
import com.abm.mainet.account.domain.AccountVoucherEntryHistEntity;
//import com.abm.mainet.account.domain.AccountVoucherExceptionMasterEntity;
import com.abm.mainet.account.domain.VoucherTemplateMasterEntity;
import com.abm.mainet.account.dto.AccountJournalVoucherEntryBean;
import com.abm.mainet.account.dto.AccountJournalVoucherEntryDetailsBean;
import com.abm.mainet.account.dto.AccountVoucherCommPostingDetailDto;
import com.abm.mainet.account.dto.AccountVoucherCommPostingMasterDto;
import com.abm.mainet.account.dto.VoucherReversePostDTO;
import com.abm.mainet.account.dto.VoucherReversePostDetailDTO;
import com.abm.mainet.account.repository.AccountVoucherEntryRepository;
//import com.abm.mainet.account.repository.AccountVoucherExceptionJpaRepository;
import com.abm.mainet.account.repository.BudgetHeadRepository;
import com.abm.mainet.account.repository.VoucherTemplateRepository;
import com.abm.mainet.common.audit.service.AuditService;
import com.abm.mainet.common.constant.MainetConstants;
import com.abm.mainet.common.constant.MainetConstants.AccountConstants;
import com.abm.mainet.common.constant.PrefixConstants;
import com.abm.mainet.common.constant.PrefixConstants.AccountPrefix;
import com.abm.mainet.common.domain.Organisation;
import com.abm.mainet.common.integration.acccount.domain.AccountBudgetCodeEntity;
import com.abm.mainet.common.integration.acccount.domain.AccountHeadSecondaryAccountCodeMasterEntity;
import com.abm.mainet.common.integration.acccount.dto.VoucherPostDTO;
import com.abm.mainet.common.integration.acccount.dto.VoucherPostDetailDTO;
import com.abm.mainet.common.integration.acccount.dto.VoucherPostDetailExternalDTO;
import com.abm.mainet.common.integration.acccount.dto.VoucherPostExternalDTO;
import com.abm.mainet.common.integration.acccount.repository.SecondaryheadMasterJpaRepository;
import com.abm.mainet.common.integration.acccount.service.AccountFieldMasterService;
import com.abm.mainet.common.integration.acccount.service.SecondaryheadMasterService;
import com.abm.mainet.common.master.dto.TbComparamDet;
import com.abm.mainet.common.master.service.DepartmentService;
import com.abm.mainet.common.master.service.TbFinancialyearService;
import com.abm.mainet.common.service.IFinancialYearService;
import com.abm.mainet.common.service.IOrganisationService;
import com.abm.mainet.common.service.TbComparamDetService;
import com.abm.mainet.common.utility.ApplicationSession;
import com.abm.mainet.common.utility.CommonMasterUtility;
import com.abm.mainet.common.utility.LookUp;
import com.abm.mainet.common.utility.SeqGenFunctionUtility;
import com.abm.mainet.common.utility.Utility;

/**
 *
 * @author Vivek.Kumar
 * @since 06 Feb 2017
 */
@Service
public class AccountVoucherPostServiceImpl implements AccountVoucherPostService {

    @Resource
    private VoucherTemplateRepository voucherTemplateRepository;

    @Resource
    private BudgetHeadRepository budgetHeadRepository;

    @Resource
    private SeqGenFunctionUtility seqGenFunctionUtility;

    @Resource
    private AccountVoucherEntryRepository accountVoucherEntryRepository;

    @Resource
    private SecondaryheadMasterJpaRepository secondaryheadMasterJpaRepository;

    @Resource
    private TbFinancialyearService tbFinancialyearService;

    @Resource
    private DepartmentService departmentService;

    @Resource
    private AccountJournalVoucherService accountJournalVoucherService;

    @Resource
    private IFinancialYearService iFinancialYearService;

    @Resource
    private AuditService auditService;

    @Autowired
    private IOrganisationService iOrganisationService;

    @Resource
    private AccountFieldMasterService tbAcFieldMasterService;
    @Resource
    private SecondaryheadMasterService secondaryheadMasterService;
    
	/*
	 * @Resource AccountVoucherExceptionJpaRepository
	 * accountVoucherExceptionJpaRepository;
	 */
	
	  @Autowired
	    private TbComparamDetService comparamDetService;

    private static final Logger LOGGER = Logger.getLogger(AccountVoucherPostServiceImpl.class);

    private static final String PREFIX_VOT = ",prefix=VOT]";
    private static final String VOUCHER_DATE = "voucherDate, ";
    private static final String VOUCHER_SUB_TYPE_ID = "voucherSubTypeId, ";
    private static final String DEPARTMENT_ID = "departmentId, ";
    private static final String NARRATION = "narration, ";
    private static final String FIELD_ID = "fieldId, ";
    private static final String ORGID = "orgId, ";
    private static final String CREATED_BY = "createdBy, ";
    private static final String LG_IP_MAC = "lgIpMac, ";
    private static final String VOUCHER_REF_NO = "voucherReferenceNo, ";
    private static final String VOUCHER_REF_DATE = "voucherReferenceDate, ";
    private static final String FIN_YEAR_ID = "financialYearId, ";
    private static final String PAY_MODE_ID = "payModeId, ";
    private static final String CORRECT_INPUT = "Correct Input";
    private static final String CANNOT_BE_NULL = " } cannot be null, empty or zero.";
    private static final String ENTRY_TYPE = "entryType, ";
    private static final String DR_CR_CANNOT_BE_NULL = "drCrId cannot be null for ";
    private static final String FI_YEAR_DATE_MAP = "Financial year Status is missing in the given financial year Date : ";
    private static final String FI_MONTH_DATE_MAP = "Financial month Status is missing in the given financial year Date : ";
    private static final String ORGID_IS = " and orgid is : ";
    private static final String FI_YEAR_STATUS_CLOSED = "This Financial year status is already closed";
    private static final String ATLEAST_ONE_DRCR = " At least one DR/CR is required!";
    private static final String SUM_OF_DEBIT_CREDIT = "Sum of Debit and Credit should be same.";
    private static final String ACCOUNT_TYPE_NOT_CONFIGURED = "account type is not configured properly, account type is :";
    private static final String VOUCHER_ENTRY_DETAILS = "getVoucherReversePostDetail, ";
    private static final String VOU_REV_DET_DETAILS = "getDetails[";
    private static final String ACCOUNT_HEAD = "].sacHeadId, ";
    private static final String VOU_DET_AMT = "].voudetAmt ";
    private static final String DR_CR_CPDID = "].drcrCpdId ";
    private static final String GENERATE_VOUCHER_NO_FIN_YEAR_ID = "Voucher sequence number generation, The financial year id is getting null value";
    private static final String GENERATE_VOUCHER_NO_VOU_TYPE_ID = "Voucher sequence number generation, The voucher type id is getting null value";
    private static final String VOUCHER_TEMPLATE_NOT_FOUNT = "VoucherTemplate not found";
    private static final String VALID_AMOUNT_NOT_FOUND = "amount can not be 0 or negative so please check it again";

    @SuppressWarnings("null")
    @Override
    @Transactional(rollbackFor = Exception.class)
    public AccountVoucherEntryEntity voucherPosting(List<VoucherPostDTO> voucherPostDTO) {
        LOGGER.info("Provided input for Voucher Posting :" + voucherPostDTO);

        StringBuilder finalResult = new StringBuilder();
        AccountVoucherEntryEntity finalReturnEntity = null;
        TbComparamDet detData = new TbComparamDet();
        detData = comparamDetService.findById(voucherPostDTO.get(0).getVoucherSubTypeId());
        Organisation org = iOrganisationService.getOrganisationById(voucherPostDTO.get(0).getOrgId());
        AccountVoucherEntryEntity finalEntity = null;
        
        for (VoucherPostDTO dto : voucherPostDTO) {

            // only for rest web services voucher posting integration in this logic is required...
            if (dto.getDepartmentId() != null && dto.getEntryType().equals("INS")) {
            	
                String deptCode = departmentService.getDeptCode(dto.getDepartmentId());
				if (!detData.getCpdValue().equalsIgnoreCase(MainetConstants.AccountConstants.BI.getValue()))
				{
					if (!deptCode.equals(MainetConstants.RECEIPT_MASTER.Module)) {
						List<VoucherPostDetailDTO> dtoList = new ArrayList<>();
						BigDecimal sumOfModeAmount = BigDecimal.ZERO;
						if (dto.getVoucherDetails() != null) {
							for (VoucherPostDetailDTO tempDetVoucherData : dto.getVoucherDetails()) {
								VoucherPostDetailDTO dtoListFee = new VoucherPostDetailDTO();
								if (tempDetVoucherData.getSacHeadId() != null) {
									dtoListFee.setSacHeadId(tempDetVoucherData.getSacHeadId());
								}
								if (tempDetVoucherData.getDemandTypeId() != null) {
									dtoListFee.setDemandTypeId(tempDetVoucherData.getDemandTypeId());
								}
								if (tempDetVoucherData.getYearId() != null) {
									dtoListFee.setYearId(tempDetVoucherData.getYearId());
								}
								if (tempDetVoucherData.getAccountHeadFlag() != null
										&& !tempDetVoucherData.getAccountHeadFlag().isEmpty()) {
									dtoListFee.setAccountHeadFlag(tempDetVoucherData.getAccountHeadFlag());
								}
								if (Utility.isEnvPrefixAvailable(org,MainetConstants.ENV_TSCL)) {
									if (tempDetVoucherData.getPayModeId() != null) {
										dtoListFee.setPayModeId(tempDetVoucherData.getPayModeId());
									}
								}
								dtoListFee.setVoucherAmount(tempDetVoucherData.getVoucherAmount());
								sumOfModeAmount = sumOfModeAmount.add(tempDetVoucherData.getVoucherAmount());
								dtoList.add(dtoListFee);
							}
						}
						VoucherPostDetailDTO dtoListMode = new VoucherPostDetailDTO();
						if (dto.getPayModeId() != null) {
							dtoListMode.setPayModeId(dto.getPayModeId());
							dtoListMode.setVoucherAmount(sumOfModeAmount);
							dtoList.add(dtoListMode);
						}
						dto.setVoucherDetails(dtoList);
					}
				}
            }
            // only for property tax (GRP) integration this logic is required...
            /*
             * if (dto.getPropertyIntFlag() != null && !dto.getPropertyIntFlag().isEmpty()) { if
             * (dto.getPropertyIntFlag().equals(MainetConstants.Y_FLAG)) { List<VoucherPostDetailDTO> dtoList = new ArrayList<>();
             * BigDecimal sumOfModeAmount = BigDecimal.ZERO; if (dto.getVoucherDetails() != null) { for (VoucherPostDetailDTO
             * tempDetVoucherData : dto.getVoucherDetails()) { VoucherPostDetailDTO dtoListFee = new VoucherPostDetailDTO();
             * dtoListFee.setSacHeadId(tempDetVoucherData.getSacHeadId());
             * dtoListFee.setVoucherAmount(tempDetVoucherData.getVoucherAmount()); sumOfModeAmount =
             * sumOfModeAmount.add(tempDetVoucherData.getVoucherAmount()); dtoList.add(dtoListFee); } } VoucherPostDetailDTO
             * dtoListMode = new VoucherPostDetailDTO(); dtoListMode.setPayModeId(dto.getPayModeId());
             * dtoListMode.setVoucherAmount(sumOfModeAmount); dtoList.add(dtoListMode); dto.setVoucherDetails(dtoList); } }
             */
            final Long status = CommonMasterUtility.lookUpIdByLookUpCodeAndPrefix(
                    MainetConstants.CommonConstants.ACTIVE, AccountPrefix.ACN.toString(), dto.getOrgId());
            String tempateForValue = null;
            if (dto.getVoucherSubTypeId() != null) {
                tempateForValue = CommonMasterUtility.findLookUpCode(MainetConstants.AccountBillEntry.TDP, dto.getOrgId(),
                        dto.getVoucherSubTypeId());
            }
            VoucherTemplateMasterEntity template = null;
            if (tempateForValue.equals("RV") || tempateForValue.equals("BI")) {
                Long accountDeptId = departmentService.getDepartmentIdByDeptCode(MainetConstants.RECEIPT_MASTER.Module);
                template = voucherTemplateRepository.queryDefinedTemplate(
                        dto.getVoucherSubTypeId(), accountDeptId, dto.getOrgId(), status, dto.getFinancialYearId());
            } else {
                template = voucherTemplateRepository.queryDefinedTemplate(
                        dto.getVoucherSubTypeId(), dto.getDepartmentId(), dto.getOrgId(), status, dto.getFinancialYearId());
            }
            /*
             * final VoucherTemplateMasterEntity template = voucherTemplateRepository.queryDefinedTemplate(
             * dto.getVoucherSubTypeId(), dto.getDepartmentId(), dto.getOrgId(), status, dto.getFinancialYearId());
             */
           
            if (template != null) {
                AccountVoucherEntryHistEntity accountVoucherEntryHistEntity = null;
                AccountVoucherEntryDetailsHistEntity accountVoucherEntryDetailsHistEntity = null;

                validatePaymentMode(template.getVoucherType(), dto);
                final AccountVoucherEntryEntity entity = mapDtoToEntity(dto, template);
                finalEntity = accountVoucherEntryRepository.save(entity);

                accountVoucherEntryHistEntity = new AccountVoucherEntryHistEntity();
                accountVoucherEntryHistEntity.sethStatus(MainetConstants.CommonConstants.CHAR_C);
                try {
                    auditService.createHistory(finalEntity, accountVoucherEntryHistEntity);
                } catch (Exception ex) {
                    LOGGER.error("Could not make audit entry for " + finalEntity, ex);
                }
                for (AccountVoucherEntryDetailsEntity accountVoucherEntryDetailsEntity : finalEntity.getDetails()) {
                    accountVoucherEntryDetailsHistEntity = new AccountVoucherEntryDetailsHistEntity();
                    accountVoucherEntryDetailsHistEntity.setMaster(finalEntity.getVouId());
                    if (accountVoucherEntryDetailsEntity.getBudgetCode() != null
                            && accountVoucherEntryDetailsEntity.getBudgetCode().getPrBudgetCodeid() != null) {
                        accountVoucherEntryDetailsHistEntity
                                .setBudgetCode(accountVoucherEntryDetailsEntity.getBudgetCode().getprBudgetCodeid());
                    }
                    accountVoucherEntryDetailsHistEntity.sethStatus(MainetConstants.CommonConstants.CHAR_C);
                    try {
                        auditService.createHistory(accountVoucherEntryDetailsEntity,
                                accountVoucherEntryDetailsHistEntity);
                    } catch (Exception ex) {
                        LOGGER.error("Could not make audit entry for " + accountVoucherEntryDetailsEntity, ex);
                    }
                }

                LOGGER.error("Account Voucher Posting done successfully in loop");
            } else {
                LOGGER.error("VoucherTemplate not found in loop");
                finalResult.append("VoucherTemplate not found in loop");
            }
        }
        if (!finalResult.toString().isEmpty()) {
            LOGGER.error("Account Voucher Posting failed : VoucherTemplate not found");
            throw new NullPointerException("Account Voucher Posting failed : VoucherTemplate not found");
            // return finalReturnEntity;
        } else {
            LOGGER.error("Account Voucher Posting done successfully");
            finalReturnEntity = new AccountVoucherEntryEntity();
            finalReturnEntity.setVouNo(finalEntity.getVouNo());
            return finalReturnEntity;
        }
    }

    /**
     * @param AccountVoucherEntryEntity entity
     * @throws InvocationTargetException
     * @throws IllegalAccessException
     * 
     */
    @Override
    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    // @Transactional(rollbackFor = Exception.class)
    public AccountVoucherEntryEntity voucherReversePosting(final VoucherReversePostDTO dto)
            throws IllegalAccessException, InvocationTargetException {
        // LOGGER.info("Provided input for Voucher Reverse Posting :" + dto);
        AccountVoucherEntryEntity finalEntity = null;
        AccountVoucherEntryHistEntity accountVoucherEntryHistEntity = null;
        AccountVoucherEntryDetailsHistEntity accountVoucherEntryDetailsHistEntity = null;

        final AccountVoucherEntryEntity entity = mapReverseDtoToEntity(dto);
        finalEntity = accountVoucherEntryRepository.save(entity);

        accountVoucherEntryHistEntity = new AccountVoucherEntryHistEntity();
        accountVoucherEntryHistEntity.sethStatus(MainetConstants.CommonConstants.CHAR_C);
        try {
            auditService.createHistory(finalEntity, accountVoucherEntryHistEntity);
        } catch (Exception ex) {
            LOGGER.error("Could not make audit entry for " + finalEntity, ex);
        }

        for (AccountVoucherEntryDetailsEntity accountVoucherEntryDetailsEntity : finalEntity.getDetails()) {
            accountVoucherEntryDetailsHistEntity = new AccountVoucherEntryDetailsHistEntity();
            accountVoucherEntryDetailsHistEntity.setMaster(finalEntity.getVouId());
            if (accountVoucherEntryDetailsEntity.getBudgetCode() != null
                    && accountVoucherEntryDetailsEntity.getBudgetCode().getPrBudgetCodeid() != null) {
                accountVoucherEntryDetailsHistEntity
                        .setBudgetCode(accountVoucherEntryDetailsEntity.getBudgetCode().getprBudgetCodeid());
            }
            accountVoucherEntryDetailsHistEntity.sethStatus(MainetConstants.CommonConstants.CHAR_C);
            try {
                auditService.createHistory(accountVoucherEntryDetailsEntity, accountVoucherEntryDetailsHistEntity);
            } catch (Exception ex) {
                LOGGER.error("Could not make audit entry for " + accountVoucherEntryDetailsEntity, ex);
            }
        }

        LOGGER.error("Account Voucher Reverse Posting done successfully");
        return finalEntity;
    }

    private AccountVoucherEntryEntity mapReverseDtoToEntity(final VoucherReversePostDTO dto) {

        AccountVoucherEntryEntity masterEntity = null;
        masterEntity = prepareReverseMasterEntity(dto);
        List<AccountVoucherEntryDetailsEntity> details = null;
        details = prepareReverseDetailEntity(masterEntity, dto);
        masterEntity.setDetails(details);
        return masterEntity;
    }

    private AccountVoucherEntryEntity prepareReverseMasterEntity(final VoucherReversePostDTO dto) {

        final AccountVoucherEntryEntity masterEntity = new AccountVoucherEntryEntity();

        masterEntity.setVouSubtypeCpdId(dto.getVouSubtypeCpdId());
        masterEntity.setVouDate(dto.getVouDate());
        masterEntity.setVouReferenceNo(dto.getVouReferenceNo());
        masterEntity.setNarration(dto.getNarration());
        masterEntity.setOrg(dto.getOrg());
        masterEntity.setCreatedBy(dto.getCreatedBy());
        masterEntity.setLmodDate(dto.getLmodDate());
        masterEntity.setLgIpMac(dto.getLgIpMac());
        masterEntity.setEntryType(dto.getEntryType());
        masterEntity.setAuthoDate(dto.getAuthoDate());
        masterEntity.setLmodDate(dto.getLmodDate());
        masterEntity.setUpdatedDate(dto.getUpdatedDate());
        masterEntity.setVouPostingDate(dto.getVouPostingDate());
        masterEntity.setVouReferenceNoDate(dto.getVouReferenceNoDate());
        masterEntity.setAuthoFlg(MainetConstants.MENU.Y);
        masterEntity.setAuthoId(dto.getAuthoId());
        masterEntity.setAuthRemark(dto.getAuthRemark());
        masterEntity.setPayerPayee(dto.getPayerPayee());
        masterEntity.setUpdatedby(dto.getUpdatedby());
        if (dto.getVouNo() == null) {
            String vouNo = generateVoucherNo(PrefixConstants.ContraVoucherEntry.CV, dto.getOrg(), dto.getVouDate());
            masterEntity.setVouNo(vouNo);
        } else {
            masterEntity.setVouNo(dto.getVouNo());
        }
        masterEntity.setVouTypeCpdId(dto.getVouTypeCpdId());
        masterEntity.setDpDeptid(dto.getDpDeptid());
        masterEntity.setFieldId(dto.getFieldId());

        // Autho Flag
        if (isCheckerMakerApplicable(dto.getVouTypeCpdId(), dto.getOrg())) {
            masterEntity.setAuthoFlg(AccountConstants.N.getValue());
            masterEntity.setVouPostingDate(null);
        } else {
            masterEntity.setAuthoFlg(dto.getAuthoFlg());
            masterEntity.setAuthoId(dto.getAuthoId());
            masterEntity.setAuthoDate(dto.getAuthoDate());
            masterEntity.setVouNo(dto.getVouNo());
        }
        // Entry Type
        masterEntity.setEntryType(dto.getEntryType());
        return masterEntity;
    }

    private List<AccountVoucherEntryDetailsEntity> prepareReverseDetailEntity(
            final AccountVoucherEntryEntity masterEntity, final VoucherReversePostDTO dto) {

        final List<AccountVoucherEntryDetailsEntity> details = new ArrayList<>();
        AccountVoucherEntryDetailsEntity detailEntity = null;
        for (final VoucherReversePostDetailDTO detail : dto.getDetails()) {
            detailEntity = new AccountVoucherEntryDetailsEntity();
            // detailEntity.setVoudetId(entity.getVoudetId());

            detailEntity.setCreatedBy(detail.getCreatedBy());
            detailEntity.setDrcrCpdId(detail.getDrcrCpdId());
            detailEntity.setOrgId(detail.getOrgId());
            detailEntity.setSacHeadId(detail.getSacHeadId());
            detailEntity.setUpdatedBy(detail.getUpdatedBy());
            if (detail.getBudgetCode() != null) {
                AccountBudgetCodeEntity bugCodeEntity = new AccountBudgetCodeEntity();
                bugCodeEntity.setPrBudgetCodeid(detail.getBudgetCode());
                detailEntity.setBudgetCode(bugCodeEntity);
            }
            // detailEntity.setFi04D1(entity.getFi04D1());
            detailEntity.setFieldCode(detail.getFieldCode());
            detailEntity.setFunctionCode(detail.getFunctionCode());
            detailEntity.setFundCode(detail.getFundCode());
            detailEntity.setLgIpMac(detail.getLgIpMac());
            detailEntity.setLgIpMacUpd(detail.getLgIpMacUpd());
            detailEntity.setLmodDate(detail.getLmodDate());
            // detailEntity.setMaster(entity.getMaster());
            detailEntity.setMaster(masterEntity);
            detailEntity.setPrimaryHeadCode(detail.getPrimaryHeadCode());
            detailEntity.setSecondaryHeadCode(detail.getSecondaryHeadCode());
            detailEntity.setUpdatedDate(detail.getUpdatedDate());
            detailEntity.setVoudetAmt(detail.getVoudetAmt());
            details.add(detailEntity);
        }
        return details;
    }

    @Override
    public String validateReversePostInput(VoucherReversePostDTO dto) {

        StringBuilder builder = new StringBuilder();
        if (dto.getVouDate() == null) {
            builder.append(VOUCHER_DATE);
        }
        if (dto.getEntryType() == null) {
            builder.append(ENTRY_TYPE);
        }
        if ((dto.getVouSubtypeCpdId() == null) || (dto.getVouSubtypeCpdId() == 0l)) {
            builder.append(VOUCHER_SUB_TYPE_ID);
        }
        if ((dto.getDpDeptid() == null) || (dto.getDpDeptid() == 0l)) {
            builder.append(DEPARTMENT_ID);
        }
        if ((dto.getNarration() == null) || dto.getNarration().isEmpty()) {
            builder.append(NARRATION);
        }
        if ((dto.getFieldId() == null) || (dto.getFieldId() == 0l)) {
            builder.append(FIELD_ID);
        }
        if ((dto.getOrg() == null) || (dto.getOrg() == 0l)) {
            builder.append(ORGID);
        }
        if ((dto.getCreatedBy() == null) || (dto.getCreatedBy() == 0l)) {
            builder.append(CREATED_BY);
        }
        if ((dto.getLgIpMac() == null) || dto.getLgIpMac().isEmpty()) {
            builder.append(LG_IP_MAC);
        }
        if ((dto.getDetails() == null) || dto.getDetails().isEmpty()) {
            builder.append(VOUCHER_ENTRY_DETAILS);
        } else {
            int count = 0;
            List<VoucherReversePostDetailDTO> detDto = dto.getDetails();
            for (final VoucherReversePostDetailDTO detail : detDto) {
                if ((detail.getSacHeadId() == null) || (detail.getSacHeadId() == 0l)) {
                    builder.append(VOU_REV_DET_DETAILS + count).append(ACCOUNT_HEAD);
                }
                if ((detail.getVoudetAmt() == null)) {
                    builder.append(VOU_REV_DET_DETAILS + count).append(VOU_DET_AMT);
                }
                if ((detail.getDrcrCpdId() == null) || (detail.getDrcrCpdId() == 0l)) {
                    builder.append(VOU_REV_DET_DETAILS + count).append(DR_CR_CPDID);
                }
                if ((detail.getOrgId() == null) || (detail.getOrgId() == 0l)) {
                    builder.append(VOU_REV_DET_DETAILS + count).append(ORGID);
                }
                count++;
            }
        }
        if (!builder.toString().isEmpty()) {
            builder.append(CANNOT_BE_NULL);
        }

        return builder.toString();
    }

    /**
     * PaymentModeId is mandatory if voucher type is RV, PV,CV
     * 
     * @param voucherTypeId
     * @param orgId
     * @param paymentModeId
     */
    private void validatePaymentMode(final Long voucherTypeId, final VoucherPostDTO dto) {
        final String voucherType = CommonMasterUtility.findLookUpCode(AccountPrefix.VOT.toString(), dto.getOrgId(),
                voucherTypeId);
        dto.setVoucherType(voucherType);
        if (AccountConstants.RV.getValue().equalsIgnoreCase(voucherType)
                || AccountConstants.PV.getValue().equalsIgnoreCase(voucherType)
                || AccountConstants.CV.getValue().equalsIgnoreCase(voucherType)) {
            int count = 0;
            for (final VoucherPostDetailDTO detail : dto.getVoucherDetails()) {
                if ((detail.getPayModeId() != null) && (detail.getPayModeId() != 0l)) {
                    count++;
                }
            }
            if (count == 0) {
                throw new NullPointerException(
                        ApplicationSession.getInstance().getMessage("account.voucher.service.paymentmode.id"));
            }
        }
    }

     //Do Posting
    private AccountVoucherEntryEntity mapDtoToEntity(final VoucherPostDTO dto,
            final VoucherTemplateMasterEntity template) {
        AccountVoucherEntryEntity masterEntity = null;
        masterEntity = prepareMasterEntity(dto, template);
        Organisation org = new Organisation();
        org.setOrgid(dto.getOrgId());
        List<AccountVoucherEntryDetailsEntity> details = null;
        details = prepareDetailEntity(masterEntity, dto, template);
        int crCount = 0;
        int drCount = 0;
        String drCrCode = "";
        if (details != null && !details.isEmpty()) {
            for (AccountVoucherEntryDetailsEntity accountVoucherEntryDetailsEntity : details) {
                drCrCode = CommonMasterUtility
                        .getNonHierarchicalLookUpObject(accountVoucherEntryDetailsEntity.getDrcrCpdId(), org)
                        .getLookUpCode();
                if (drCrCode != null && !drCrCode.isEmpty()) {
                    if ("DR".equals(drCrCode)) {
                        ++drCount;
                    } else if ("CR".equals(drCrCode)) {
                        ++crCount;
                    }
                }
            }
        }
        if (drCount == 0 || crCount == 0) {
            throw new NullPointerException(ATLEAST_ONE_DRCR);
        }
        masterEntity.setDetails(details);

        return masterEntity;

    }

    //do Posting
    private AccountVoucherEntryEntity prepareMasterEntity(final VoucherPostDTO dto,
            final VoucherTemplateMasterEntity template) {
        final AccountVoucherEntryEntity masterEntity = new AccountVoucherEntryEntity();

        masterEntity.setVouDate(dto.getVoucherDate());
        boolean fiYearHeadClosed = false;
        if (dto.getVoucherDate() != null) {
            Date hardClosedFiYearDate = dto.getVoucherDate();
            Long finYeadStatus = tbFinancialyearService.checkHardClosedFinYearDateExists(hardClosedFiYearDate,
                    dto.getOrgId());
            if (finYeadStatus == null) {
                throw new NullPointerException(FI_YEAR_DATE_MAP + hardClosedFiYearDate + ORGID_IS + dto.getOrgId());
            } else {
                Long finYeadMonthStatus = tbFinancialyearService.checkSoftClosedFinYearDateExists(hardClosedFiYearDate,
                        dto.getOrgId());
                Organisation org = new Organisation();
                org.setOrgid(dto.getOrgId());
                String fiYearStatusCode = CommonMasterUtility.getNonHierarchicalLookUpObject(finYeadStatus, org)
                        .getLookUpCode();
                String fiYearMonthStatusCode = "";
                if (finYeadMonthStatus != null) {
                    fiYearMonthStatusCode = CommonMasterUtility.getNonHierarchicalLookUpObject(finYeadMonthStatus, org)
                            .getLookUpCode();
                }
                /*if ((fiYearStatusCode != null && !fiYearStatusCode.isEmpty())
                        && (fiYearMonthStatusCode == null || fiYearMonthStatusCode.isEmpty())) {
                    if (fiYearStatusCode.equals(PrefixConstants.LookUp.OPN)) {
                        fiYearHeadClosed = true;
                    }
                }*/
                if ((fiYearStatusCode != null && !fiYearStatusCode.isEmpty())
                        && (fiYearMonthStatusCode !=null && !fiYearMonthStatusCode.isEmpty())) {
                    if (fiYearStatusCode.equals(PrefixConstants.LookUp.OPN) || fiYearMonthStatusCode.equals(PrefixConstants.LookUp.OPN) ) {
                        fiYearHeadClosed = true;
                    }
                }
                
            }
        }
        if (fiYearHeadClosed == false) {
            throw new NullPointerException(FI_YEAR_STATUS_CLOSED);
        }
        if (dto.getBillVouPostingDate() != null) {
            masterEntity.setVouPostingDate(dto.getBillVouPostingDate());
        } else if (dto.getPayEntryMakerFlag() != null) {
            masterEntity.setVouPostingDate(dto.getVoucherDate());
        } else {
            masterEntity.setVouPostingDate(dto.getVoucherDate());
        }
        masterEntity.setVouTypeCpdId(template.getVoucherType());
        masterEntity.setVouSubtypeCpdId(dto.getVoucherSubTypeId());
        masterEntity.setDpDeptid(dto.getDepartmentId());
        masterEntity.setVouReferenceNo(dto.getVoucherReferenceNo());
        masterEntity.setVouReferenceNoDate(dto.getVoucherReferenceDate());
        masterEntity.setNarration(dto.getNarration());
        masterEntity.setPayerPayee(dto.getPayerOrPayee());
        masterEntity.setFieldId(dto.getFieldId());
        masterEntity.setOrg(dto.getOrgId());
        masterEntity.setCreatedBy(dto.getCreatedBy());
        masterEntity.setLmodDate(new Date());
        masterEntity.setLgIpMac(dto.getLgIpMac());

        /*
         * if (dto.getPropertyIntFlag() != null && !dto.getPropertyIntFlag().isEmpty()) {
         * masterEntity.setFi04Lo1(dto.getPropertyIntFlag()); }
         */

        // Autho Flag
        if (isCheckerMakerApplicable(template.getVoucherType(), dto.getOrgId())) {
            masterEntity.setAuthoFlg(AccountConstants.N.getValue());
            masterEntity.setVouPostingDate(null);
        } else {
            masterEntity.setAuthoFlg(AccountConstants.Y.getValue());
            masterEntity.setAuthoId(dto.getCreatedBy());
            if (dto.getBillVouPostingDate() != null) {
                masterEntity.setAuthoDate(dto.getBillVouPostingDate());
            } else if (dto.getPayEntryMakerFlag() != null) {
                masterEntity.setAuthoDate(dto.getVoucherDate());
            } else {
                masterEntity.setAuthoDate(dto.getVoucherDate());
            }
            masterEntity.setVouNo(generateVoucherNo(dto.getVoucherType(), dto.getOrgId(), dto.getVoucherDate()));
        }
        // Entry Type
        masterEntity.setEntryType(entryType(dto.getEntryType(), dto.getOrgId()));
        return masterEntity;
    }

    //doPosting
    private List<AccountVoucherEntryDetailsEntity> prepareDetailEntity(final AccountVoucherEntryEntity masterEntity,
            final VoucherPostDTO dto, final VoucherTemplateMasterEntity template) {
        final ApplicationSession session = ApplicationSession.getInstance();
        final List<AccountVoucherEntryDetailsEntity> details = new ArrayList<>();
        AccountVoucherEntryDetailsEntity detailEntity = null;
        for (final VoucherPostDetailDTO detail : dto.getVoucherDetails()) {
            detailEntity = new AccountVoucherEntryDetailsEntity();
            detailEntity.setMaster(masterEntity);
            if (detail.getVoucherAmount() != null) {
                detailEntity.setVoudetAmt(detail.getVoucherAmount());
            }
            Long drCrId;
            // AccountBudgetCodeEntity entity;
            AccountHeadSecondaryAccountCodeMasterEntity sacEntity;
            final String transferEntryType = CommonMasterUtility.findLookUpCode(AccountPrefix.TDP.toString(),
                    dto.getOrgId(), dto.getVoucherSubTypeId());
            // this is used for III case - particularly asset management module.
            if (dto.getEntryFlag() != null && !dto.getEntryFlag().isEmpty()) {
                if (dto.getEntryFlag().equals(MainetConstants.Y_FLAG)) {
                    if (detail.getAccountHeadFlag() == null) {
                        throw new NullPointerException(
                                session.getMessage("account.voucher.post.service.transfer.entry") + transferEntryType
                                        + MainetConstants.WHITE_SPACE + detail + detail.getSacHeadId());
                    }
                    if (detail.getSacHeadId() == null) {
                        throw new NullPointerException(
                                session.getMessage("account.voucher.post.service.transfer.entry") + transferEntryType
                                        + MainetConstants.WHITE_SPACE + detail + detail.getSacHeadId());
                    }
                    if (detail.getAccountHeadFlag() != null && !detail.getAccountHeadFlag().isEmpty()) {
                        if (detail.getAccountHeadFlag().equals("R")) {
                            final Long crValue = CommonMasterUtility.lookUpIdByLookUpCodeAndPrefix("CR", PrefixConstants.DCR,
                                    dto.getOrgId());
                            detailEntity.setDrcrCpdId(crValue);
                            // detailEntity.setVoudetAmt(detail.getVoucherAmount());
                            if (detail.getSacHeadId() == null) {
                                throw new NullPointerException(
                                        session.getMessage("account.voucher.post.service.transfer.entry") + transferEntryType
                                                + MainetConstants.WHITE_SPACE + detail + detail.getSacHeadId());
                            }
                            if (detail.getSacHeadId() != null) {
                                AccountHeadSecondaryAccountCodeMasterEntity sacEntityR = secondaryheadMasterJpaRepository
                                        .findOne(detail.getSacHeadId());
                                if (sacEntityR == null) {
                                    throw new NullPointerException(
                                            session.getMessage("account.voucher.post.service.record.sacheadid") + detail
                                                    + sacEntityR);
                                }
                                if (sacEntityR.getTbAcPrimaryheadMaster() == null) {
                                    throw new NullPointerException(
                                            session.getMessage("account.voucher.post.service.pacheadid") + sacEntityR
                                                    + sacEntityR.getTbAcPrimaryheadMaster());
                                }
                                if (sacEntityR.getTbAcPrimaryheadMaster().getCpdIdAcHeadTypes() == null) {
                                    throw new NullPointerException(
                                            session.getMessage("account.voucher.service.cpdid.type") + detail.getSacHeadId()
                                                    + sacEntityR.getTbAcPrimaryheadMaster().getCpdIdAcHeadTypes());
                                }
                                if (sacEntityR.getTbAcPrimaryheadMaster().getCpdIdAcHeadTypes() != null) {
                                    Long accountHeadTypeDrCrExist = voucherTemplateRepository.getAccountHeadTypeAndDrCrExists(
                                            template.getTemplateId(), sacEntityR.getTbAcPrimaryheadMaster().getCpdIdAcHeadTypes(),
                                            crValue, dto.getOrgId());
                                    if (accountHeadTypeDrCrExist == null || accountHeadTypeDrCrExist == 0) {
                                        throw new NullPointerException(
                                                "cpdIdAcHeadType is not configured in VoucherTemplate or Dc/Cr Type is not matched:"
                                                        + "[" + detail + ",cpdIdAcHeadType="
                                                        + sacEntityR.getTbAcPrimaryheadMaster().getCpdIdAcHeadTypes()
                                                        + ", drcrId=" + crValue + ", orgid=" + dto.getOrgId());
                                    }
                                }
                                detailEntity.setSacHeadId(detail.getSacHeadId());
                                detailEntity.setOrgId(dto.getOrgId());
                                detailEntity.setCreatedBy(dto.getCreatedBy());
                                detailEntity.setLmodDate(new Date());
                                detailEntity.setLgIpMac(dto.getLgIpMac());
                                detailEntity.setSecondaryHeadCode(sacEntityR.getSacHeadCode());
                                detailEntity
                                        .setBudgetCode(findBudgetHeadIdBySacHeadId(sacEntityR.getSacHeadId(), dto.getOrgId()));
                                if (sacEntityR.getTbAcPrimaryheadMaster() != null) {
                                    detailEntity.setPrimaryHeadCode(sacEntityR.getTbAcPrimaryheadMaster().getPrimaryAcHeadCode());
                                }
                                if (sacEntityR.getTbAcFunctionMaster() != null) {
                                    detailEntity.setFunctionCode(sacEntityR.getTbAcFunctionMaster().getFunctionCode());
                                }
                                if (sacEntityR.getTbAcFieldMaster() != null) {
                                    detailEntity.setFieldCode(sacEntityR.getTbAcFieldMaster().getFieldCode());
                                }
                                if (sacEntityR.getTbAcFundMaster() != null) {
                                    detailEntity.setFundCode(sacEntityR.getTbAcFundMaster().getFundCode());
                                }
                            }
                            // CR
                            // get account type id from sachead master table
                            // query passing to voucher template table to pass (orgid,templateid,accounttypeid,cRdRid) - count()
                        } else if (detail.getAccountHeadFlag().equals("P")) {
                            final Long drValue = CommonMasterUtility.lookUpIdByLookUpCodeAndPrefix("DR", PrefixConstants.DCR,
                                    dto.getOrgId());
                            detailEntity.setDrcrCpdId(drValue);
                            // detailEntity.setVoudetAmt(detail.getVoucherAmount());
                            if (detail.getSacHeadId() == null) {
                                throw new NullPointerException(
                                        session.getMessage("account.voucher.post.service.transfer.entry") + transferEntryType
                                                + MainetConstants.WHITE_SPACE + detail + detail.getSacHeadId());
                            }
                            if (detail.getSacHeadId() != null) {
                                AccountHeadSecondaryAccountCodeMasterEntity sacEntityP = secondaryheadMasterJpaRepository
                                        .findOne(detail.getSacHeadId());
                                if (sacEntityP == null) {
                                    throw new NullPointerException(
                                            session.getMessage("account.voucher.post.service.record.sacheadid") + detail
                                                    + sacEntityP);
                                }
                                if (sacEntityP.getTbAcPrimaryheadMaster() == null) {
                                    throw new NullPointerException(
                                            session.getMessage("account.voucher.post.service.pacheadid") + sacEntityP
                                                    + sacEntityP.getTbAcPrimaryheadMaster());
                                }
                                if (sacEntityP.getTbAcPrimaryheadMaster().getCpdIdAcHeadTypes() == null) {
                                    throw new NullPointerException(
                                            session.getMessage("account.voucher.service.cpdid.type") + detail.getSacHeadId()
                                                    + sacEntityP.getTbAcPrimaryheadMaster().getCpdIdAcHeadTypes());
                                }
                                if (sacEntityP.getTbAcPrimaryheadMaster().getCpdIdAcHeadTypes() != null) {
                                    Long accountHeadTypeDrCrExist = voucherTemplateRepository.getAccountHeadTypeAndDrCrExists(
                                            template.getTemplateId(), sacEntityP.getTbAcPrimaryheadMaster().getCpdIdAcHeadTypes(),
                                            drValue, dto.getOrgId());
                                    if (accountHeadTypeDrCrExist == null || accountHeadTypeDrCrExist == 0) {
                                        throw new NullPointerException(
                                                "cpdIdAcHeadType is not configured in VoucherTemplate or Dc/Cr Type is not matched:"
                                                        + "[" + detail + ",cpdIdAcHeadType="
                                                        + sacEntityP.getTbAcPrimaryheadMaster().getCpdIdAcHeadTypes()
                                                        + ", drcrId=" + drValue + ", orgid=" + dto.getOrgId());
                                    }
                                }
                                detailEntity.setSacHeadId(detail.getSacHeadId());
                                detailEntity.setOrgId(dto.getOrgId());
                                detailEntity.setCreatedBy(dto.getCreatedBy());
                                detailEntity.setLmodDate(new Date());
                                detailEntity.setLgIpMac(dto.getLgIpMac());
                                detailEntity.setSecondaryHeadCode(sacEntityP.getSacHeadCode());
                                detailEntity
                                        .setBudgetCode(findBudgetHeadIdBySacHeadId(sacEntityP.getSacHeadId(), dto.getOrgId()));
                                if (sacEntityP.getTbAcPrimaryheadMaster() != null) {
                                    detailEntity.setPrimaryHeadCode(sacEntityP.getTbAcPrimaryheadMaster().getPrimaryAcHeadCode());
                                }
                                if (sacEntityP.getTbAcFunctionMaster() != null) {
                                    detailEntity.setFunctionCode(sacEntityP.getTbAcFunctionMaster().getFunctionCode());
                                }
                                if (sacEntityP.getTbAcFieldMaster() != null) {
                                    detailEntity.setFieldCode(sacEntityP.getTbAcFieldMaster().getFieldCode());
                                }
                                if (sacEntityP.getTbAcFundMaster() != null) {
                                    detailEntity.setFundCode(sacEntityP.getTbAcFundMaster().getFundCode());
                                }
                            }
                            // DR
                            // get account type id from sachead master table
                            // query passing to voucher template table to pass (orgid,templateid,accounttypeid,cRdRid) - count()
                        }
                    }
                }
            } else {
                if (AccountConstants.BTE.getValue().equalsIgnoreCase(transferEntryType)
                        || AccountConstants.PVE.getValue().equalsIgnoreCase(transferEntryType)
                        || AccountConstants.BI.getValue().equalsIgnoreCase(transferEntryType)) {
                    drCrId = detail.getDrCrId();
                    Objects.requireNonNull(drCrId, DR_CR_CANNOT_BE_NULL + detail);
                    Objects.requireNonNull(detail.getSacHeadId(),
                            session.getMessage("account.voucher.post.service.transfer.entry") + transferEntryType
                                    + MainetConstants.WHITE_SPACE + detail);
                    detailEntity.setSacHeadId(detail.getSacHeadId());
                    sacEntity = secondaryheadMasterJpaRepository.findOne(detail.getSacHeadId());

                } else {
                    if (detail.getSacHeadId() != null && detail.getDemandTypeId() == null) {

                        sacEntity = secondaryheadMasterJpaRepository.findOne(detail.getSacHeadId());
                        detailEntity.setSacHeadId(detail.getSacHeadId());
                        Objects.requireNonNull(sacEntity,
                                session.getMessage("account.voucher.post.service.record.sacheadid") + detail);
                        Objects.requireNonNull(sacEntity.getTbAcPrimaryheadMaster(),
                                session.getMessage("account.voucher.post.service.pacheadid") + sacEntity);
                        Objects.requireNonNull(sacEntity.getTbAcPrimaryheadMaster().getCpdIdAcHeadTypes(),
                                session.getMessage("account.voucher.service.cpdid.type") + detail.getSacHeadId());
                        final Object[] drCrIdArr = decideDrCrSacHeadId(detail, template, dto,
                                sacEntity.getTbAcPrimaryheadMaster().getCpdIdAcHeadTypes());
                        if ((drCrIdArr == null) || (drCrIdArr.length <= 0)) {
                            throw new NullPointerException(
                                    "cpdIdAcHeadType is not configured in VoucherTemplate or Dc/Cr Type is not matched:"
                                            + "[" + detail + ",cpdIdAcHeadType="
                                            + sacEntity.getTbAcPrimaryheadMaster().getCpdIdAcHeadTypes());
                        }
                        if (drCrIdArr[0] == null) {
                            throw new NullPointerException(
                                    "cpdIdAcHeadType is not configured in VoucherTemplate or Dc/Cr Type is not matched:"
                                            + "[" + detail + ",cpdIdAcHeadType="
                                            + sacEntity.getTbAcPrimaryheadMaster().getCpdIdAcHeadTypes());
                        }
                        drCrId = (long) drCrIdArr[0];
                    } else if (detail.getPayModeId() != null) {
                               
                        final Object[] drCrIdArr = decideDrCrModeId(detail, template, dto);
                        Objects.requireNonNull(drCrIdArr[0],
                                session.getMessage("account.voucher.service.paymode") + detail + template + dto);
                        Objects.requireNonNull(drCrIdArr[1],
                                session.getMessage("account.voucher.service.head") + detail + template + dto);
                        drCrId = (long) drCrIdArr[0];
                        final long sacHeadId = (long) drCrIdArr[1];
                        sacEntity = secondaryheadMasterJpaRepository.findOne(sacHeadId);
                        detailEntity.setSacHeadId(sacHeadId);
                        Objects.requireNonNull(sacEntity, session.getMessage("account.voucher.service.sacheadid")
                                + sacHeadId + MainetConstants.VOUCHER_POST_DETAIL_DTO + detail);
                        Objects.requireNonNull(sacEntity.getTbAcPrimaryheadMaster().getCpdIdAcHeadTypes(),
                                session.getMessage("account.voucher.service.cpdid.type"));
                    } else {
                                             
                        final Object[] drCrIdArr = decideDrCrDemandTypeId(detail, template, dto);
                        Objects.requireNonNull(drCrIdArr[0],
                                session.getMessage("account.voucher.service.demandtype") + detail + template + dto);
                        Objects.requireNonNull(drCrIdArr[1],
                                session.getMessage("account.voucher.service.head") + detail + template + dto);
                        Long accountTypeId = Long.valueOf(drCrIdArr[0].toString());
                        final long sacHeadId = Long.valueOf(drCrIdArr[1].toString());
                        sacEntity = secondaryheadMasterJpaRepository.findOne(sacHeadId);
                        detailEntity.setSacHeadId(sacHeadId);
                        Objects.requireNonNull(sacEntity, session.getMessage("account.voucher.service.sacheadid")
                                + sacHeadId + MainetConstants.VOUCHER_POST_DETAIL_DTO + detail);
                        Objects.requireNonNull(sacEntity.getTbAcPrimaryheadMaster().getCpdIdAcHeadTypes(),
                                session.getMessage("account.voucher.service.cpdid.type"));
                        final Object[] drCrIdArrRDC = decideDrCrSacHeadId(detail, template, dto,
                                accountTypeId);
                        if ((drCrIdArrRDC == null) || (drCrIdArrRDC.length <= 0)) {
                            throw new NullPointerException(
                                    "cpdIdAcHeadType is not configured in VoucherTemplate or Dc/Cr Type is not matched:"
                                            + "[" + detail + ",cpdIdAcHeadType="
                                            + sacEntity.getTbAcPrimaryheadMaster().getCpdIdAcHeadTypes());
                        }
                        if (drCrIdArrRDC[0] == null) {
                            throw new NullPointerException(
                                    "cpdIdAcHeadType is not configured in VoucherTemplate or Dc/Cr Type is not matched:"
                                            + "[" + detail + ",cpdIdAcHeadType="
                                            + sacEntity.getTbAcPrimaryheadMaster().getCpdIdAcHeadTypes());
                        }
                        drCrId = Long.valueOf(drCrIdArrRDC[0].toString());
                    }
                }
                if (drCrId == 0) {
                    throw new IllegalArgumentException(
                            ApplicationSession.getInstance().getMessage("account.voucher.service.standard.template")
                                    + detail);
                }
                detailEntity.setDrcrCpdId(drCrId);
                detailEntity.setOrgId(dto.getOrgId());
                detailEntity.setCreatedBy(dto.getCreatedBy());
                detailEntity.setLmodDate(new Date());
                detailEntity.setLgIpMac(dto.getLgIpMac());
                detailEntity.setSecondaryHeadCode(sacEntity.getSacHeadCode());
                detailEntity.setBudgetCode(findBudgetHeadIdBySacHeadId(sacEntity.getSacHeadId(), dto.getOrgId()));
                if (sacEntity.getTbAcPrimaryheadMaster() != null) {
                    detailEntity.setPrimaryHeadCode(sacEntity.getTbAcPrimaryheadMaster().getPrimaryAcHeadCode());
                }
                if (sacEntity.getTbAcFunctionMaster() != null) {
                    detailEntity.setFunctionCode(sacEntity.getTbAcFunctionMaster().getFunctionCode());
                }
                if (sacEntity.getTbAcFieldMaster() != null) {
                    detailEntity.setFieldCode(sacEntity.getTbAcFieldMaster().getFieldCode());
                }
                if (sacEntity.getTbAcFundMaster() != null) {
                    detailEntity.setFundCode(sacEntity.getTbAcFundMaster().getFundCode());
                }
            }
            details.add(detailEntity);
        }
        return details;
    }

    private Object[] decideDrCrSacHeadId(VoucherPostDetailDTO detail, VoucherTemplateMasterEntity template,
            VoucherPostDTO dto, Long cpdIdAcHeadTypes) {

        final Long status = CommonMasterUtility.lookUpIdByLookUpCodeAndPrefix(
                MainetConstants.CommonConstants.ACTIVE, AccountPrefix.ACN.toString(), dto.getOrgId());
        final Object[] objArr = new Object[2];
        Long drCrIdId = voucherTemplateRepository.getDrCrIdByPassingSacHeadId(template.getTemplateId(), cpdIdAcHeadTypes,
                dto.getOrgId(), status);
        if (drCrIdId != null) {
            objArr[0] = drCrIdId;
            objArr[1] = 0;
        } else {
            throw new IllegalArgumentException(ACCOUNT_TYPE_NOT_CONFIGURED + cpdIdAcHeadTypes + ORGID + dto.getOrgId());
        }
        return objArr;
    }

    private Object[] decideDrCrModeId(VoucherPostDetailDTO detailDTO, VoucherTemplateMasterEntity template,
            VoucherPostDTO dto) {

        final Long status = CommonMasterUtility.lookUpIdByLookUpCodeAndPrefix(
                MainetConstants.CommonConstants.ACTIVE, AccountPrefix.ACN.toString(), dto.getOrgId());
        final Object[] objArr = new Object[2];
        List<Object[]> drCrSacHeadId = voucherTemplateRepository.getDrCrIdByPassingModeId(template.getTemplateId(),
                detailDTO.getPayModeId(), dto.getOrgId(), status);
        for (Object[] objects : drCrSacHeadId) {
            if (objects[0] != null) {
                objArr[0] = objects[0];
            } else {
                throw new IllegalArgumentException(ApplicationSession.getInstance()
                        .getMessage("account.voucher.service.drcrid.paymode")
                        + detailDTO.getPayModeId());
            }
            if (objects[1] != null) {
                objArr[1] = objects[1];
            } else {
                throw new IllegalArgumentException(ApplicationSession.getInstance()
                        .getMessage("account.voucher.service.cpdid.paymode")
                        + detailDTO.getPayModeId());
            }
        }
        return objArr;
    }
//Do Posting
    private Object[] decideDrCrDemandTypeId(VoucherPostDetailDTO detailDTO, VoucherTemplateMasterEntity template,
            VoucherPostDTO dto) {

        final Long status = CommonMasterUtility.lookUpIdByLookUpCodeAndPrefix(
                MainetConstants.CommonConstants.ACTIVE, AccountPrefix.ACN.toString(), dto.getOrgId());
        final Object[] objArr = new Object[2];
        Long finYearId = null;
        if (detailDTO.getYearId() == null || detailDTO.getYearId() == 0L) {
            finYearId = 0L;
        } else {
            finYearId = detailDTO.getYearId();
        }
        List<Object[]> drCrSacHeadId = voucherTemplateRepository.getDrCrIdByPassingDemandTypeId(detailDTO.getDemandTypeId(),
                finYearId, dto.getDepartmentId(), status, dto.getOrgId());
        for (Object[] objects : drCrSacHeadId) {
            if (objects[0] != null) {
                objArr[0] = objects[0];
            } else {
                throw new IllegalArgumentException(ApplicationSession.getInstance()
                        .getMessage("account.voucher.service.drcrid.demandtype")
                        + detailDTO.getPayModeId());
            }
            if (objects[1] != null) {
                objArr[1] = objects[1];
            } else {
                throw new IllegalArgumentException(ApplicationSession.getInstance()
                        .getMessage("account.voucher.service.cpdid.demandtype")
                        + detailDTO.getPayModeId());
            }
        }
        return objArr;
    }

    private AccountBudgetCodeEntity findBudgetHeadIdBySacHeadId(final long sacHeadId, final long orgId) {
        return budgetHeadRepository.findBudgetHeadIdBySacHeadId(sacHeadId, orgId);
    }

    /*
     * private Object[] decideDrCr(final VoucherPostDetailDTO detailDTO, final VoucherTemplateMasterEntity template, final
     * VoucherPostDTO dto, final Long cpdIdAcHeadType) { final Long templateFor =
     * CommonMasterUtility.lookUpIdByLookUpCodeAndPrefix("CWE", AccountPrefix.TDP.toString(), dto.getOrgId()); final Long
     * depTemplateFor = CommonMasterUtility.lookUpIdByLookUpCodeAndPrefix("DS", AccountPrefix.TDP.toString(), dto.getOrgId());
     * final Long contraDepTemplateFor = CommonMasterUtility.lookUpIdByLookUpCodeAndPrefix("PCD", AccountPrefix.TDP.toString(),
     * dto.getOrgId()); final Long chequeDishonortemplateFor = CommonMasterUtility.lookUpIdByLookUpCodeAndPrefix("DHC",
     * AccountPrefix.TDP.toString(), dto.getOrgId()); final Long cbPropertyreceiptTemplateFor =
     * CommonMasterUtility.lookUpIdByLookUpCodeAndPrefix("RDC", AccountPrefix.TDP.toString(), dto.getOrgId()); final Long
     * cbRebatereceiptTemplateFor = CommonMasterUtility.lookUpIdByLookUpCodeAndPrefix("RBT", AccountPrefix.TDP.toString(),
     * dto.getOrgId()); final Long drValue = CommonMasterUtility.lookUpIdByLookUpCodeAndPrefix("DR", PrefixConstants.DCR,
     * dto.getOrgId()); final Long crValue = CommonMasterUtility.lookUpIdByLookUpCodeAndPrefix("CR", PrefixConstants.DCR,
     * dto.getOrgId()); final Long commonDeprTemplateFor = CommonMasterUtility.lookUpIdByLookUpCodeAndPrefix("ADE",
     * AccountPrefix.TDP.toString(), dto.getOrgId()); long drCrId = 0l; final Object[] objArr = new Object[2]; for (final
     * VoucherTemplateDetailEntity detailTemplate : template.getTemplateDetailEntities()) { if
     * (commonDeprTemplateFor.equals(template.getTemplateFor())) { if (detailDTO.getSacHeadId() != null) { drCrId = crValue;
     * objArr[0] = drCrId; objArr[1] = 0; break; } else { if ((detailTemplate.getCpdIdPayMode() != null) &&
     * (detailDTO.getPayModeId() != null)) { if (detailTemplate.getCpdIdPayMode().longValue() ==
     * detailDTO.getPayModeId().longValue()) { drCrId = drValue; objArr[0] = drCrId; if ((detailTemplate.getSacHeadId() == null)
     * || (detailTemplate.getSacHeadId().longValue() == 0l)) { throw new
     * IllegalArgumentException(ApplicationSession.getInstance().getMessage( "account.voucher.service.cpdid.paymode") +
     * detailTemplate.getCpdIdPayMode()); } objArr[1] = detailTemplate.getSacHeadId(); break; } } } } else if
     * (templateFor.equals(template.getTemplateFor())) { if (detailDTO.getSacHeadId() != null) { drCrId = crValue; objArr[0] =
     * drCrId; objArr[1] = 0; break; } else { if ((detailTemplate.getCpdIdPayMode() != null) && (detailDTO.getPayModeId() !=
     * null)) { if (detailTemplate.getCpdIdPayMode().longValue() == detailDTO.getPayModeId().longValue()) { drCrId = drValue;
     * objArr[0] = drCrId; if ((detailTemplate.getSacHeadId() == null) || (detailTemplate.getSacHeadId().longValue() == 0l)) {
     * throw new IllegalArgumentException(ApplicationSession.getInstance().getMessage( "account.voucher.service.cpdid.paymode") +
     * detailTemplate.getCpdIdPayMode()); } objArr[1] = detailTemplate.getSacHeadId(); break; } } } } else if
     * (chequeDishonortemplateFor.equals(template.getTemplateFor())) { if (detailDTO.getSacHeadId() != null) { drCrId = crValue;
     * objArr[0] = drCrId; objArr[1] = 0; break; } else { if ((detailTemplate.getCpdIdPayMode() != null) &&
     * (detailDTO.getPayModeId() != null)) { if (detailTemplate.getCpdIdPayMode().longValue() ==
     * detailDTO.getPayModeId().longValue()) { drCrId = drValue; objArr[0] = drCrId; if ((detailTemplate.getSacHeadId() == null)
     * || (detailTemplate.getSacHeadId().longValue() == 0l)) { throw new
     * IllegalArgumentException(ApplicationSession.getInstance().getMessage( "account.voucher.service.cpdid.paymode") +
     * detailTemplate.getCpdIdPayMode()); } objArr[1] = detailTemplate.getSacHeadId(); break; } } } } else if
     * ((depTemplateFor.equals(template.getTemplateFor())) || (contraDepTemplateFor.equals(template.getTemplateFor()))) { if
     * (detailDTO.getSacHeadId() != null) { drCrId = drValue; objArr[0] = drCrId; objArr[1] = 0; break; } else { if
     * ((detailTemplate.getCpdIdPayMode() != null) && (detailDTO.getPayModeId() != null)) { if
     * (detailTemplate.getCpdIdPayMode().longValue() == detailDTO.getPayModeId().longValue()) { drCrId = crValue; objArr[0] =
     * drCrId; if ((detailTemplate.getSacHeadId() == null) || (detailTemplate.getSacHeadId().longValue() == 0l)) { throw new
     * IllegalArgumentException(ApplicationSession.getInstance().getMessage( "account.voucher.service.cpdid.paymode") +
     * detailTemplate.getCpdIdPayMode()); } objArr[1] = detailTemplate.getSacHeadId(); break; } } } } else if
     * (cbPropertyreceiptTemplateFor.equals(template.getTemplateFor())) { if (detailDTO.getSacHeadId() != null) { drCrId =
     * crValue; objArr[0] = drCrId; objArr[1] = 0; break; } else { if ((detailTemplate.getCpdIdPayMode() != null) &&
     * (detailDTO.getPayModeId() != null)) { if (detailTemplate.getCpdIdPayMode().longValue() ==
     * detailDTO.getPayModeId().longValue()) { drCrId = drValue; objArr[0] = drCrId; if ((detailTemplate.getSacHeadId() == null)
     * || (detailTemplate.getSacHeadId().longValue() == 0l)) { throw new
     * IllegalArgumentException(ApplicationSession.getInstance().getMessage( "account.voucher.service.cpdid.paymode") +
     * detailTemplate.getCpdIdPayMode()); } objArr[1] = detailTemplate.getSacHeadId(); break; } } else if
     * (detailDTO.getDemandTypeId() != null) { final Long status = CommonMasterUtility.lookUpIdByLookUpCodeAndPrefix(
     * MainetConstants.CommonConstants.ACTIVE, AccountPrefix.ACN.toString(), dto.getOrgId()); final VoucherTemplateMasterEntity
     * templatetype = voucherTemplateRepository.queryDefinedTemplate( detailDTO.getDemandTypeId(), dto.getDepartmentId(),
     * dto.getOrgId(), status, detailDTO.getYearId()); for (final VoucherTemplateDetailEntity detTemplate :
     * templatetype.getTemplateDetailEntities()) { if (detTemplate.getSacHeadId() != null && detTemplate.getSacHeadId() > 0d) {
     * drCrId = crValue; objArr[0] = drCrId; objArr[1] = detTemplate.getSacHeadId(); } } break; } } } else if
     * (cbRebatereceiptTemplateFor.equals(template.getTemplateFor())) { if (detailDTO.getSacHeadId() != null) { drCrId = crValue;
     * objArr[0] = drCrId; objArr[1] = 0; break; } else { if ((detailTemplate.getCpdIdPayMode() != null) &&
     * (detailDTO.getPayModeId() != null)) { if (detailTemplate.getCpdIdPayMode().longValue() ==
     * detailDTO.getPayModeId().longValue()) { drCrId = drValue; objArr[0] = drCrId; if ((detailTemplate.getSacHeadId() == null)
     * || (detailTemplate.getSacHeadId().longValue() == 0l)) { throw new
     * IllegalArgumentException(ApplicationSession.getInstance().getMessage( "account.voucher.service.cpdid.paymode") +
     * detailTemplate.getCpdIdPayMode()); } objArr[1] = detailTemplate.getSacHeadId(); break; } } } } else { if ((cpdIdAcHeadType
     * != null) && (cpdIdAcHeadType == detailTemplate.getAccountType().longValue())) { // if ((cpdIdAcHeadType != null) &&
     * (cpdIdAcHeadType == // detailTemplate.getAccountType().longValue()) && detailTemplate.getSacHeadId() // == null) { drCrId =
     * detailTemplate.getCpdIdDrcr(); objArr[0] = drCrId; objArr[1] = 0; break; } else if ((detailTemplate.getCpdIdPayMode() !=
     * null) && (detailDTO.getPayModeId() != null)) { if (detailTemplate.getCpdIdPayMode().longValue() ==
     * detailDTO.getPayModeId().longValue()) { drCrId = detailTemplate.getCpdIdDrcr(); objArr[0] = drCrId; if
     * ((detailTemplate.getSacHeadId() == null) || (detailTemplate.getSacHeadId().longValue() == 0l)) { throw new
     * IllegalArgumentException( ApplicationSession.getInstance().getMessage("account.voucher.service.cpdid.paymode") +
     * detailTemplate.getCpdIdPayMode()); } objArr[1] = detailTemplate.getSacHeadId(); break; } } } } return objArr; }
     */

    private boolean isCheckerMakerApplicable(final Long voucherTypeId, final Long orgId) {
        final String voucherType = CommonMasterUtility.findLookUpCode(AccountPrefix.VOT.toString(), orgId,
                voucherTypeId);
        final long available = CommonMasterUtility.lookUpIdByLookUpCodeAndPrefix(voucherType,
                AccountPrefix.AUT.toString(), orgId);
        return available == 0 ? false : true;
    }

    private long entryType(final String entryType, final Long orgId) {
        long entryTypeId = CommonMasterUtility.lookUpIdByLookUpCodeAndPrefix(entryType, AccountPrefix.VET.toString(),
                orgId);
        if (entryTypeId == 0l) {
            throw new NullPointerException(
                    "entryType id not found for lookUpCode[MAN/INS] from VET Prefix in voucher posting.");
            /*
             * entryTypeId = CommonMasterUtility.lookUpIdByLookUpCodeAndPrefix(AccountConstants.EXS.getValue(),
             * AccountPrefix.VET.toString(), orgId);
             */
        }
        return entryTypeId;
    }

    private String generateVoucherNo(final String voucherType, final long orgId, Date vouDate) {
        final Long voucherTypeId = voucherTypeId(voucherType, orgId);
        if (voucherTypeId == null) {
            throw new NullPointerException(GENERATE_VOUCHER_NO_VOU_TYPE_ID);
        }
        Long finYearId = tbFinancialyearService.getFinanciaYearIdByFromDate(vouDate);
        if (finYearId == null) {
            throw new NullPointerException(GENERATE_VOUCHER_NO_FIN_YEAR_ID);
        }
        String resetIdValue = voucherTypeId.toString() + finYearId.toString();
        Long resetId = Long.valueOf(resetIdValue);
        final Object sequenceNo = seqGenFunctionUtility.generateSequenceNo(AccountConstants.AC.getValue(),
                AccountConstants.TB_AC_VOUCHER.getValue(), AccountConstants.VOU_NO.getValue(), orgId,
                MainetConstants.SECONDARY_MASTER.SECONDARY_SEQ_CONTINUE_VALUE, resetId);
        if (sequenceNo == null) {
            throw new NullPointerException(MainetConstants.SEQUENCE_NO_ERROR + orgId);
        }

        return voucherType.substring(0, 2) + MainetConstants.SEQUENCE_NO.substring(sequenceNo.toString().length())
                + sequenceNo.toString();
    }

    private Long voucherTypeId(final String voucherType, final long orgId) {

        final Long voucherTypeId = CommonMasterUtility.lookUpIdByLookUpCodeAndPrefix(voucherType,
                AccountPrefix.VOT.toString(), orgId);
        if (voucherTypeId == 0l) {
            throw new IllegalArgumentException(
                    ApplicationSession.getInstance().getMessage("account.voucher.service.templatetype.id") + voucherType
                            + MainetConstants.ORG_ID + orgId + PREFIX_VOT);
        }
        return voucherTypeId;
    }

    @Override
    public String validateInput(final List<VoucherPostDTO> voucherPostDTO) {
        final StringBuilder builder = new StringBuilder();
        TbComparamDet detData = new TbComparamDet();
        for (VoucherPostDTO dto : voucherPostDTO) {
            if (dto.getVoucherDate() == null) {
                builder.append(VOUCHER_DATE);
            }
            if ((dto.getEntryType() == null) || dto.getEntryType().isEmpty()) {
                builder.append(ENTRY_TYPE);
            }
            
            if ((dto.getVoucherSubTypeId() == null) || (dto.getVoucherSubTypeId() == 0l)) {
                builder.append(VOUCHER_SUB_TYPE_ID);
            }
            else 
            {
            	   detData = comparamDetService.findById(dto.getVoucherSubTypeId());
            }
       
            if ((dto.getDepartmentId() == null) || (dto.getDepartmentId() == 0l)) {
                builder.append(DEPARTMENT_ID);
            }
            if ((dto.getNarration() == null) || dto.getNarration().isEmpty()) {
                builder.append(NARRATION);
            }
            if ((dto.getFieldId() == null) || (dto.getFieldId() == 0l)) {
                builder.append(FIELD_ID);
            }
            if ((dto.getOrgId() == null) || (dto.getOrgId() == 0l)) {
                builder.append(ORGID);
            }
            if ((dto.getCreatedBy() == null) || (dto.getCreatedBy() == 0l)) {
                builder.append(CREATED_BY);
            }
            if ((dto.getLgIpMac() == null) || dto.getLgIpMac().isEmpty()) {
                builder.append(LG_IP_MAC);
            }
            if (dto.getDepartmentId() != null) {
                String deptCode = departmentService.getDeptCode(dto.getDepartmentId());
              
               //code modified by rahul.chaubey as per the requirment given by samadhan sir 
				// below code will be skipped in the case of Bill/Invoice authorization
				if (!detData.getCpdValue().equalsIgnoreCase(MainetConstants.AccountConstants.BI.getValue())) 
				{
            	   if (!deptCode.equals(MainetConstants.RECEIPT_MASTER.Module)) {
                    if ((dto.getVoucherReferenceNo() == null) || dto.getVoucherReferenceNo().isEmpty()) {
                        builder.append(VOUCHER_REF_NO);
                    }
                    if (dto.getVoucherReferenceDate() == null) {
                        builder.append(VOUCHER_REF_DATE);
                    }
                    if (dto.getEntryFlag() == null || dto.getEntryFlag().isEmpty()) {
                        if (dto.getPayModeId() == null) {
                            builder.append(PAY_MODE_ID);
                        }
                    }
                }
            }
        }
            //end
            if (dto.getVoucherSubTypeId() != null && dto.getOrgId() != null) {
                String vouSubTypeCode = CommonMasterUtility.findLookUpCode(AccountPrefix.TDP.toString(),
                        dto.getOrgId(), dto.getVoucherSubTypeId());
                if (vouSubTypeCode.equals("DMD") || vouSubTypeCode.equals("BDL") /*|| vouSubTypeCode.equals("RBT")*/
                        || vouSubTypeCode.equals("DWO") || vouSubTypeCode.equals("RBR")) {
                    if (dto.getFinancialYearId() == null) {
                        builder.append(FIN_YEAR_ID);
                    }
                }
            }
            dto.getVoucherDetails().parallelStream().forEach(dtos -> {
                if (dtos.getVoucherAmount().compareTo(BigDecimal.ZERO) == 0 || dtos.getVoucherAmount().signum() == -1) {
                    builder.append(VALID_AMOUNT_NOT_FOUND);
                }

            });
        }
        if (!builder.toString().isEmpty()) {
            builder.append(CANNOT_BE_NULL);
        }
        return builder.toString();
    }

    @Override
    public AccountVoucherCommPostingMasterDto deasEntryCommPosting(
            final AccountVoucherCommPostingMasterDto accountVoucherCommPostingMasterDto) {

        final AccountJournalVoucherEntryBean accountJournalVoucherEntryBean = new AccountJournalVoucherEntryBean();
        AccountJournalVoucherEntryDetailsBean accountJournalVoucherEntryDetailsBean = new AccountJournalVoucherEntryDetailsBean();
        final Date curDate = new Date();
        final Organisation organisation = new Organisation();
        organisation.setOrgid(accountVoucherCommPostingMasterDto.getOrgid());

        final LookUp n_cpdid = CommonMasterUtility.getValueFromPrefixLookUp(
                accountVoucherCommPostingMasterDto.getVou_type_cpd_id_value(), PrefixConstants.ContraVoucherEntry.VOT,
                organisation);
        accountJournalVoucherEntryBean.setVouTypeCpdId(n_cpdid.getLookUpId());
        final LookUp n_cpdidsubtype = CommonMasterUtility.getValueFromPrefixLookUp(
                accountVoucherCommPostingMasterDto.getVou_subtype_cpd_id_value(), PrefixConstants.REV_SUB_CPD_VALUE,
                organisation);

        if (departmentService.getDepartmentIdByDeptCode(accountVoucherCommPostingMasterDto.getDp_deptid_code(),
                MainetConstants.MENU.A) != null) {
            accountJournalVoucherEntryBean.setDpDeptid(departmentService.getDepartmentIdByDeptCode(
                    accountVoucherCommPostingMasterDto.getDp_deptid_code(), MainetConstants.MENU.A));
        }

        if ((MainetConstants.MENU.Y).equals(accountVoucherCommPostingMasterDto.getAutho_flg())) {
            final String voucherNumber = findAuthVoucherNumber(
                    accountVoucherCommPostingMasterDto.getVou_type_cpd_id_value(),
                    accountVoucherCommPostingMasterDto.getVou_date(), accountVoucherCommPostingMasterDto.getOrgid());
            accountJournalVoucherEntryBean.setVouNo(voucherNumber);
            accountJournalVoucherEntryBean.setAuthoId(accountVoucherCommPostingMasterDto.getCreated_by());
            accountJournalVoucherEntryBean.setAuthoDate(curDate);
        }

        accountJournalVoucherEntryBean.setVouSubtypeCpdId(n_cpdidsubtype.getLookUpId());
        accountJournalVoucherEntryBean.setVouDate(accountVoucherCommPostingMasterDto.getVou_date());
        accountJournalVoucherEntryBean.setVouPostingDate(accountVoucherCommPostingMasterDto.getVou_posting_date());
        accountJournalVoucherEntryBean.setOrg(accountVoucherCommPostingMasterDto.getOrgid());
        accountJournalVoucherEntryBean.setCreatedBy(accountVoucherCommPostingMasterDto.getCreated_by());
        accountJournalVoucherEntryBean.setLgIpMac(accountVoucherCommPostingMasterDto.getLg_ip_mac());
        accountJournalVoucherEntryBean.setLangId(accountVoucherCommPostingMasterDto.getLang_id().intValue());
        accountJournalVoucherEntryBean.setLmodDate(curDate);
        accountJournalVoucherEntryBean.setNarration(accountVoucherCommPostingMasterDto.getNarration());
        accountJournalVoucherEntryBean.setVouReferenceNo(accountVoucherCommPostingMasterDto.getVou_reference_no());
        accountJournalVoucherEntryBean
                .setVouReferenceNoDate(accountVoucherCommPostingMasterDto.getVou_reference_no_date());
        accountJournalVoucherEntryBean.setAuthoFlg(accountVoucherCommPostingMasterDto.getAutho_flg());
        accountJournalVoucherEntryBean.setEntryType(Long.valueOf(accountVoucherCommPostingMasterDto.getEntry_type()));
        if (accountVoucherCommPostingMasterDto.getFieldId() != null) {
            accountJournalVoucherEntryBean.setFieldId(accountVoucherCommPostingMasterDto.getFieldId());
        }

        final List<AccountJournalVoucherEntryDetailsBean> VoucherdetailsList = new ArrayList<>(0);
        if (accountVoucherCommPostingMasterDto.getAccountVoucherCommPostingDetailDto() != null) {

            for (final AccountVoucherCommPostingDetailDto drcrGroupHeadList : accountVoucherCommPostingMasterDto
                    .getAccountVoucherCommPostingDetailDto()) {
                accountJournalVoucherEntryDetailsBean = new AccountJournalVoucherEntryDetailsBean();
                final LookUp debitCreditCpdId = CommonMasterUtility
                        .getValueFromPrefixLookUp(drcrGroupHeadList.getDrcrValue(), PrefixConstants.DCR);
                accountJournalVoucherEntryDetailsBean.setDrcrCpdId(debitCreditCpdId.getLookUpId());
                accountJournalVoucherEntryDetailsBean.setMaster(accountJournalVoucherEntryBean);

                if (drcrGroupHeadList.getFundId() != null) {
                    accountJournalVoucherEntryDetailsBean.setFundId(drcrGroupHeadList.getFundId());
                }

                if (drcrGroupHeadList.getFunctionId() != null) {
                    accountJournalVoucherEntryDetailsBean.setFunctionId(drcrGroupHeadList.getFunctionId());
                }

                if (drcrGroupHeadList.getSacHeadId() != null) {
                    accountJournalVoucherEntryDetailsBean.setSacHeadId(drcrGroupHeadList.getSacHeadId());
                }

                if (drcrGroupHeadList.getVoudetAmt() != null) {
                    accountJournalVoucherEntryDetailsBean.setVoudetAmt(drcrGroupHeadList.getVoudetAmt());
                }

                accountJournalVoucherEntryDetailsBean.setOrgId(accountVoucherCommPostingMasterDto.getOrgid());
                accountJournalVoucherEntryDetailsBean.setCreatedBy(accountVoucherCommPostingMasterDto.getCreated_by());
                accountJournalVoucherEntryDetailsBean.setLgIpMac(accountVoucherCommPostingMasterDto.getLg_ip_mac());
                accountJournalVoucherEntryDetailsBean
                        .setLangId(accountVoucherCommPostingMasterDto.getLang_id().intValue());
                accountJournalVoucherEntryDetailsBean.setLmodDate(curDate);
                VoucherdetailsList.add(accountJournalVoucherEntryDetailsBean);
                accountJournalVoucherEntryBean.setDetails(VoucherdetailsList);
            }

        }

        accountJournalVoucherService.saveAccountJournalVoucherEntry(accountJournalVoucherEntryBean);
        return null;
    }

    private String findAuthVoucherNumber(final String voucherTypeRvPVACvJv, final Date voucherDate, final Long OrgId) {
        Long voucherType = null;
        long faYear = 0;
        final Object[] finData = iFinancialYearService.getFinacialYearByDate(voucherDate);
        if ((finData != null) && (finData.length > 0)) {
            faYear = (long) finData[0];
        }

        if ((MainetConstants.BILL_MASTER_COMMON.PREFIX_CODE_RV).equals(voucherTypeRvPVACvJv)) {
            voucherType = 1 + faYear;
        } else if ((MainetConstants.AccountJournalVoucherEntry.PV).equals(voucherTypeRvPVACvJv)) {
            voucherType = 2 + faYear;
        } else if ((MainetConstants.AccountJournalVoucherEntry.CV).equals(voucherTypeRvPVACvJv)) {
            voucherType = 3 + faYear;
        } else if ((MainetConstants.AccountJournalVoucherEntry.JV).equals(voucherTypeRvPVACvJv)) {
            voucherType = 4 + faYear;
        }

        final Long vouNoyear = seqGenFunctionUtility.generateSequenceNo(MainetConstants.RECEIPT_MASTER.Module,
                AccountConstants.VOUCHERTABLE.getValue(), AccountConstants.VOUCHERCOLUNVOU_NO.getValue(), OrgId,
                AccountConstants.POSTRESET_TYPE.getValue(), voucherType);
        final String voucherNumber = voucherTypeRvPVACvJv.substring(0, 2)
                + MainetConstants.RECEIPT_MASTER.VOUCHER_NO.substring(vouNoyear.toString().length())
                + vouNoyear.toString();
        return voucherNumber;
    }

    @Override
    @Transactional
    public String contraVoucherPosting(final VoucherPostDTO dto) {

        LOGGER.info("Provided input for contra Voucher Posting :" + dto);
        String voucherNo = "";
        final Long status = CommonMasterUtility.lookUpIdByLookUpCodeAndPrefix(MainetConstants.CommonConstants.ACTIVE,
                AccountPrefix.ACN.toString(), dto.getOrgId());
        final VoucherTemplateMasterEntity template = voucherTemplateRepository
                .queryDefinedTemplate(dto.getVoucherSubTypeId(), dto.getDepartmentId(), dto.getOrgId(), status, null);
        if (template != null) {

            AccountVoucherEntryHistEntity accountVoucherEntryHistEntity = null;
            AccountVoucherEntryDetailsHistEntity accountVoucherEntryDetailsHistEntity = null;

            validatePaymentMode(template.getVoucherType(), dto);
            AccountVoucherEntryEntity entity = mapDtoToEntity(dto, template);
            AccountVoucherEntryEntity finalEntity = accountVoucherEntryRepository.save(entity);

            accountVoucherEntryHistEntity = new AccountVoucherEntryHistEntity();
            accountVoucherEntryHistEntity.sethStatus(MainetConstants.CommonConstants.CHAR_C);
            try {
                auditService.createHistory(finalEntity, accountVoucherEntryHistEntity);
            } catch (Exception ex) {
                LOGGER.error("Could not make audit entry for " + finalEntity, ex);
            }

            for (AccountVoucherEntryDetailsEntity accountVoucherEntryDetailsEntity : finalEntity.getDetails()) {
                accountVoucherEntryDetailsHistEntity = new AccountVoucherEntryDetailsHistEntity();
                accountVoucherEntryDetailsHistEntity.setMaster(finalEntity.getVouId());
                if (accountVoucherEntryDetailsEntity.getBudgetCode() != null
                        && accountVoucherEntryDetailsEntity.getBudgetCode().getPrBudgetCodeid() != null) {
                    accountVoucherEntryDetailsHistEntity
                            .setBudgetCode(accountVoucherEntryDetailsEntity.getBudgetCode().getprBudgetCodeid());
                }
                accountVoucherEntryDetailsHistEntity.sethStatus(MainetConstants.CommonConstants.CHAR_C);
                try {
                    auditService.createHistory(accountVoucherEntryDetailsEntity, accountVoucherEntryDetailsHistEntity);
                } catch (Exception ex) {
                    LOGGER.error("Could not make audit entry for " + accountVoucherEntryDetailsEntity, ex);
                }
            }

            voucherNo = entity.getVouNo();
        }
        return voucherNo;
    }

    @Override
    @Transactional(readOnly = true)
    public List<String> checkVoucherPostValidateInput(List<VoucherPostDTO> voucherPostDTO) {
        LOGGER.info("Provided input for Voucher Posting validation:" + voucherPostDTO);

        List<String> errorMessageList = new ArrayList<>();
        StringBuilder builder = null;
        for (VoucherPostDTO dto : voucherPostDTO) {

            // only for rest web services voucher posting integration in this logic is required...
            if (dto.getDepartmentId() != null) {
                String deptCode = departmentService.getDeptCode(dto.getDepartmentId());
                if (!deptCode.equals(MainetConstants.RECEIPT_MASTER.Module)) {
                    List<VoucherPostDetailDTO> dtoList = new ArrayList<>();
                    BigDecimal sumOfModeAmount = BigDecimal.ZERO;
                    BigDecimal sumOfDMDAmount = BigDecimal.ZERO;
                    BigDecimal sumOfDMPAmount = BigDecimal.ZERO;
                    if (dto.getVoucherDetails() != null) {

                        for (VoucherPostDetailDTO tempDetVoucherRDCData : dto.getVoucherDetails()) {
                            if (tempDetVoucherRDCData.getDemandTypeId() != null) {
                                String demandTypeCode = CommonMasterUtility.findLookUpCode(AccountPrefix.TDP.toString(),
                                        dto.getOrgId(), tempDetVoucherRDCData.getDemandTypeId());
                                if (demandTypeCode.equals("DMD")) {
                                    sumOfDMDAmount = sumOfDMDAmount.add(tempDetVoucherRDCData.getVoucherAmount());
                                }
                                if (demandTypeCode.equals("DMP")) {
                                    sumOfDMPAmount = sumOfDMPAmount.add(tempDetVoucherRDCData.getVoucherAmount());
                                }
                            }
                        }
                        int countDMD = 0;
                        int countDMP = 0;
                        for (VoucherPostDetailDTO tempDetVoucherData : dto.getVoucherDetails()) {
                            VoucherPostDetailDTO dtoListFee = null;
                            if (tempDetVoucherData.getDemandTypeId() != null) {
                                String demandTypeCode = CommonMasterUtility.findLookUpCode(AccountPrefix.TDP.toString(),
                                        dto.getOrgId(), tempDetVoucherData.getDemandTypeId());
                                if (demandTypeCode.equals("DMD")) {
                                    if (countDMD == 0) {
                                        dtoListFee = new VoucherPostDetailDTO();
                                        dtoListFee.setDemandTypeId(tempDetVoucherData.getDemandTypeId());
                                        dtoListFee.setYearId(tempDetVoucherData.getYearId());
                                        dtoListFee.setVoucherAmount(sumOfDMDAmount);
                                        sumOfModeAmount = sumOfModeAmount.add(sumOfDMDAmount);
                                        dtoList.add(dtoListFee);
                                        countDMD++;
                                    }
                                }
                                if (demandTypeCode.equals("DMP")) {
                                    if (countDMP == 0) {
                                        dtoListFee = new VoucherPostDetailDTO();
                                        dtoListFee.setDemandTypeId(tempDetVoucherData.getDemandTypeId());
                                       /* dtoListFee.setYearId(tempDetVoucherData.getYearId());*/
                                        dtoListFee.setVoucherAmount(sumOfDMPAmount);
                                        sumOfModeAmount = sumOfModeAmount.add(sumOfDMPAmount);
                                        dtoList.add(dtoListFee);
                                        countDMP++;
                                    }
                                }
                            } else {
                                dtoListFee = new VoucherPostDetailDTO();
                                if (tempDetVoucherData.getSacHeadId() != null) {
                                    dtoListFee.setSacHeadId(tempDetVoucherData.getSacHeadId());
                                }
                                if (tempDetVoucherData.getYearId() != null) {
                                    dtoListFee.setYearId(tempDetVoucherData.getYearId());
                                }
                                if (tempDetVoucherData.getAccountHeadFlag() != null
                                        && !tempDetVoucherData.getAccountHeadFlag().isEmpty()) {
                                    dtoListFee.setAccountHeadFlag(tempDetVoucherData.getAccountHeadFlag());
                                }
                                dtoListFee.setVoucherAmount(tempDetVoucherData.getVoucherAmount());
                                sumOfModeAmount = sumOfModeAmount.add(tempDetVoucherData.getVoucherAmount());
                                dtoList.add(dtoListFee);
                            }
                        }
                    }
                    VoucherPostDetailDTO dtoListMode = new VoucherPostDetailDTO();
                    if (dto.getPayModeId() != null) {
                        dtoListMode.setPayModeId(dto.getPayModeId());
                        dtoListMode.setVoucherAmount(sumOfModeAmount);
                        dtoList.add(dtoListMode);
                    }
                    dto.setVoucherDetails(dtoList);
                }
            }

            builder = new StringBuilder();
            if (dto.getVoucherDate() == null) {
                builder.append(VOUCHER_DATE);
            }
            if ((dto.getEntryType() == null) || dto.getEntryType().isEmpty()) {
                builder.append(ENTRY_TYPE);
            }
            if ((dto.getVoucherSubTypeId() == null) || (dto.getVoucherSubTypeId() == 0l)) {
                builder.append(VOUCHER_SUB_TYPE_ID);
            }
            if ((dto.getDepartmentId() == null) || (dto.getDepartmentId() == 0l)) {
                builder.append(DEPARTMENT_ID);
            }
            if ((dto.getNarration() == null) || dto.getNarration().isEmpty()) {
                builder.append(NARRATION);
            }
            if ((dto.getFieldId() == null) || (dto.getFieldId() == 0l)) {
                builder.append(FIELD_ID);
            }
            if ((dto.getOrgId() == null) || (dto.getOrgId() == 0l)) {
                builder.append(ORGID);
            }
            if ((dto.getCreatedBy() == null) || (dto.getCreatedBy() == 0l)) {
                builder.append(CREATED_BY);
            }
            if ((dto.getLgIpMac() == null) || dto.getLgIpMac().isEmpty()) {
                builder.append(LG_IP_MAC);
            }
            if (dto.getDepartmentId() != null) {
                String deptCode = departmentService.getDeptCode(dto.getDepartmentId());
                if (!deptCode.equals(MainetConstants.RECEIPT_MASTER.Module)) {
                    if ((dto.getVoucherReferenceNo() == null) || dto.getVoucherReferenceNo().isEmpty()) {
                        builder.append(VOUCHER_REF_NO);
                    }
                    if (dto.getVoucherReferenceDate() == null) {
                        builder.append(VOUCHER_REF_DATE);
                    }
                    if (dto.getEntryFlag() == null || dto.getEntryFlag().isEmpty()) {
                        if (dto.getPayModeId() == null) {
                            builder.append(PAY_MODE_ID);
                        }
                    }
                }
            }
            if (dto.getVoucherSubTypeId() != null && dto.getOrgId() != null) {
                String vouSubTypeCode = CommonMasterUtility.findLookUpCode(AccountPrefix.TDP.toString(),
                        dto.getOrgId(), dto.getVoucherSubTypeId());
                if (vouSubTypeCode.equals("DMD") || vouSubTypeCode.equals("BDL") /*|| vouSubTypeCode.equals("RBT")*/
                        || vouSubTypeCode.equals("DWO") || vouSubTypeCode.equals("RBR")) {
                    if (dto.getFinancialYearId() == null) {
                         builder.append(FIN_YEAR_ID);
                    }
                }
            }
            if (!builder.toString().isEmpty()) {
                builder.append(CANNOT_BE_NULL);
            }

            boolean fiYearHeadClosed = false;
            if (dto.getVoucherDate() != null) {
                Date hardClosedFiYearDate = dto.getVoucherDate();
                Long finYeadStatus = tbFinancialyearService.checkHardClosedFinYearDateExists(hardClosedFiYearDate,
                        dto.getOrgId());
                if (finYeadStatus == null) {
                    builder.append(FI_YEAR_DATE_MAP + hardClosedFiYearDate + ORGID_IS + dto.getOrgId());
                } else {
                    Long finYeadMonthStatus = tbFinancialyearService
                            .checkSoftClosedFinYearDateExists(hardClosedFiYearDate, dto.getOrgId());
                    Organisation org = new Organisation();
                    org.setOrgid(dto.getOrgId());
                    String fiYearStatusCode = CommonMasterUtility.getNonHierarchicalLookUpObject(finYeadStatus, org)
                            .getLookUpCode();
                    String fiYearMonthStatusCode = "";
                    if (finYeadMonthStatus != null) {
                        fiYearMonthStatusCode = CommonMasterUtility
                                .getNonHierarchicalLookUpObject(finYeadMonthStatus, org).getLookUpCode();
                    }
                    if ((fiYearStatusCode != null && !fiYearStatusCode.isEmpty())
                            && (fiYearMonthStatusCode !=null && !fiYearMonthStatusCode.isEmpty())) {
                        if (fiYearStatusCode.equals(PrefixConstants.LookUp.OPN) || fiYearMonthStatusCode.equals(PrefixConstants.LookUp.OPN) ) {
                            fiYearHeadClosed = true;
                        }
                    }
                }
            }
            if (fiYearHeadClosed == false) {
                builder.append(FI_YEAR_STATUS_CLOSED);
            }

            // SLI Prefix date validation.
            Organisation org = new Organisation();
            org.setOrgid(dto.getOrgId());
            final LookUp lookUp = CommonMasterUtility.getValueFromPrefixLookUp(PrefixConstants.NEC.ADVOCATE,
                    MainetConstants.BUG_HEAD_OPENING_BALANCE_MASTER.SLI_PREFIX_VALUE, org);
            if (lookUp == null) {
                builder.append(ApplicationSession.getInstance().getMessage("account.receipt.sli"));
            }

            final Long status = CommonMasterUtility.lookUpIdByLookUpCodeAndPrefix(
                    MainetConstants.CommonConstants.ACTIVE, AccountPrefix.ACN.toString(), dto.getOrgId());
            String tempateForValue = null;
            if (dto.getVoucherSubTypeId() != null) {
                tempateForValue = CommonMasterUtility.findLookUpCode(MainetConstants.AccountBillEntry.TDP, dto.getOrgId(),
                        dto.getVoucherSubTypeId());
            }
            VoucherTemplateMasterEntity template = null;
            
            //AccontVoucher Posting
            if (tempateForValue.equals("RV")) {
                Long accountDeptId = departmentService.getDepartmentIdByDeptCode(MainetConstants.RECEIPT_MASTER.Module);
                template = voucherTemplateRepository.queryDefinedTemplate(
                        dto.getVoucherSubTypeId(), accountDeptId, dto.getOrgId(), status, dto.getFinancialYearId());
            } else {
                template = voucherTemplateRepository.queryDefinedTemplate(
                        dto.getVoucherSubTypeId(), dto.getDepartmentId(), dto.getOrgId(), status, dto.getFinancialYearId());
            }
            /*
             * final VoucherTemplateMasterEntity template = voucherTemplateRepository.queryDefinedTemplate(
             * dto.getVoucherSubTypeId(), dto.getDepartmentId(), dto.getOrgId(), status, dto.getFinancialYearId());
             */
            if (template == null) {
                builder.append(VOUCHER_TEMPLATE_NOT_FOUNT);
            }
            prepareDetailEntityValidation(dto, template, builder);
            if (!builder.toString().isEmpty()) {
                errorMessageList.add(builder.toString());
            }
        }
        return errorMessageList;
    }

    //Voucher Posting
    private void prepareDetailEntityValidation(final VoucherPostDTO dto, final VoucherTemplateMasterEntity template,
            StringBuilder builder) {

        final ApplicationSession session = ApplicationSession.getInstance();
        // AccountVoucherEntryDetailsEntity detailEntity = null;
        int crCount = 0;
        int drCount = 0;
        BigDecimal drTotalAmount = new BigDecimal(0.00);
        BigDecimal crTotalAmount = new BigDecimal(0.00);
        for (final VoucherPostDetailDTO detail : dto.getVoucherDetails()) {
            // detailEntity = new AccountVoucherEntryDetailsEntity();
            Long drCrId = null;
            AccountHeadSecondaryAccountCodeMasterEntity sacEntity;
            final String transferEntryType = CommonMasterUtility.findLookUpCode(AccountPrefix.TDP.toString(),
                    dto.getOrgId(), dto.getVoucherSubTypeId());

            if (dto.getEntryFlag() != null && !dto.getEntryFlag().isEmpty()) {
                if (dto.getEntryFlag().equals(MainetConstants.Y_FLAG)) {
                    if (detail.getAccountHeadFlag() == null) {
                        builder.append(session.getMessage("account.voucher.post.service.transfer.entry") + transferEntryType
                                + MainetConstants.WHITE_SPACE + detail + detail.getSacHeadId());
                    }
                    if (detail.getSacHeadId() == null) {
                        builder.append(session.getMessage("account.voucher.post.service.transfer.entry") + transferEntryType
                                + MainetConstants.WHITE_SPACE + detail + detail.getSacHeadId());
                    }
                    if (detail.getAccountHeadFlag() != null && !detail.getAccountHeadFlag().isEmpty()) {
                        if (detail.getAccountHeadFlag().equals("R")) {
                            final Long crValue = CommonMasterUtility.lookUpIdByLookUpCodeAndPrefix("CR", PrefixConstants.DCR,
                                    dto.getOrgId());
                            drCrId = crValue;
                            if (detail.getSacHeadId() == null) {
                                builder.append(
                                        session.getMessage("account.voucher.post.service.transfer.entry") + transferEntryType
                                                + MainetConstants.WHITE_SPACE + detail + detail.getSacHeadId());
                            }
                            if (detail.getSacHeadId() != null) {
                                AccountHeadSecondaryAccountCodeMasterEntity sacEntityR = secondaryheadMasterJpaRepository
                                        .findOne(detail.getSacHeadId());
                                if (sacEntityR == null) {
                                    builder.append(session.getMessage("account.voucher.post.service.record.sacheadid") + detail
                                            + sacEntityR);
                                }
                                if (sacEntityR.getTbAcPrimaryheadMaster() == null) {
                                    builder.append(session.getMessage("account.voucher.post.service.pacheadid") + sacEntityR
                                            + sacEntityR.getTbAcPrimaryheadMaster());
                                }
                                if (sacEntityR.getTbAcPrimaryheadMaster().getCpdIdAcHeadTypes() == null) {
                                    builder.append(
                                            session.getMessage("account.voucher.service.cpdid.type") + detail.getSacHeadId()
                                                    + sacEntityR.getTbAcPrimaryheadMaster().getCpdIdAcHeadTypes());
                                }
                                if (sacEntityR.getTbAcPrimaryheadMaster().getCpdIdAcHeadTypes() != null) {
                                    Long accountHeadTypeDrCrExist = voucherTemplateRepository.getAccountHeadTypeAndDrCrExists(
                                            template.getTemplateId(), sacEntityR.getTbAcPrimaryheadMaster().getCpdIdAcHeadTypes(),
                                            crValue, dto.getOrgId());
                                    if (accountHeadTypeDrCrExist == null || accountHeadTypeDrCrExist == 0) {
                                        builder.append(
                                                "cpdIdAcHeadType is not configured in VoucherTemplate or Dc/Cr Type is not matched:"
                                                        + "[" + detail + ",cpdIdAcHeadType="
                                                        + sacEntityR.getTbAcPrimaryheadMaster().getCpdIdAcHeadTypes()
                                                        + ", drcrId=" + crValue + ", orgid=" + dto.getOrgId());
                                    }
                                }
                            }
                            // CR
                            // get account type id from sachead master table
                            // query passing to voucher template table to pass (orgid,templateid,accounttypeid,cRdRid) - count()
                        } else if (detail.getAccountHeadFlag().equals("P")) {
                            final Long drValue = CommonMasterUtility.lookUpIdByLookUpCodeAndPrefix("DR", PrefixConstants.DCR,
                                    dto.getOrgId());
                            drCrId = drValue;
                            if (detail.getSacHeadId() == null) {
                                builder.append(
                                        session.getMessage("account.voucher.post.service.transfer.entry") + transferEntryType
                                                + MainetConstants.WHITE_SPACE + detail + detail.getSacHeadId());
                            }
                            if (detail.getSacHeadId() != null) {
                                AccountHeadSecondaryAccountCodeMasterEntity sacEntityR = secondaryheadMasterJpaRepository
                                        .findOne(detail.getSacHeadId());
                                if (sacEntityR == null) {
                                    builder.append(session.getMessage("account.voucher.post.service.record.sacheadid") + detail
                                            + sacEntityR);
                                }
                                if (sacEntityR.getTbAcPrimaryheadMaster() == null) {
                                    builder.append(session.getMessage("account.voucher.post.service.pacheadid") + sacEntityR
                                            + sacEntityR.getTbAcPrimaryheadMaster());
                                }
                                if (sacEntityR.getTbAcPrimaryheadMaster().getCpdIdAcHeadTypes() == null) {
                                    builder.append(
                                            session.getMessage("account.voucher.service.cpdid.type") + detail.getSacHeadId()
                                                    + sacEntityR.getTbAcPrimaryheadMaster().getCpdIdAcHeadTypes());
                                }
                                if (sacEntityR.getTbAcPrimaryheadMaster().getCpdIdAcHeadTypes() != null) {
                                    Long accountHeadTypeDrCrExist = voucherTemplateRepository.getAccountHeadTypeAndDrCrExists(
                                            template.getTemplateId(), sacEntityR.getTbAcPrimaryheadMaster().getCpdIdAcHeadTypes(),
                                            drValue, dto.getOrgId());
                                    if (accountHeadTypeDrCrExist == null || accountHeadTypeDrCrExist == 0) {
                                        builder.append(
                                                "cpdIdAcHeadType is not configured in VoucherTemplate or Dc/Cr Type is not matched:"
                                                        + "[" + detail + ",cpdIdAcHeadType="
                                                        + sacEntityR.getTbAcPrimaryheadMaster().getCpdIdAcHeadTypes()
                                                        + ", drcrId=" + drValue + ", orgid=" + dto.getOrgId());
                                    }
                                }
                            }
                            // DR
                            // get account type id from sachead master table
                            // query passing to voucher template table to pass (orgid,templateid,accounttypeid,cRdRid) - count()
                        }
                    }
                }
            } else {
                if (AccountConstants.BTE.getValue().equalsIgnoreCase(transferEntryType)
                        || AccountConstants.PVE.getValue().equalsIgnoreCase(transferEntryType)
                        || AccountConstants.BI.getValue().equalsIgnoreCase(transferEntryType)) {
                    drCrId = detail.getDrCrId();
                    if (drCrId == null) {
                        builder.append(DR_CR_CANNOT_BE_NULL + detail + drCrId);
                    }
                    if (detail.getSacHeadId() == null) {
                        builder.append(session.getMessage("account.voucher.post.service.transfer.entry") + transferEntryType
                                + MainetConstants.WHITE_SPACE + detail + detail.getSacHeadId());
                    }
                    if (detail.getSacHeadId() != null) {
                        // detailEntity.setSacHeadId(detail.getSacHeadId());
                        sacEntity = secondaryheadMasterJpaRepository.findOne(detail.getSacHeadId());
                    }
                } else {
                    if (detail.getSacHeadId() != null && detail.getDemandTypeId() == null) {

                        sacEntity = secondaryheadMasterJpaRepository.findOne(detail.getSacHeadId());
                        // detailEntity.setSacHeadId(detail.getSacHeadId());
                        if (sacEntity == null) {
                            builder.append(session.getMessage("account.voucher.post.service.record.sacheadid") + detail
                                    + sacEntity);
                        }
                        if (sacEntity.getTbAcPrimaryheadMaster() == null) {
                            builder.append(session.getMessage("account.voucher.post.service.pacheadid") + sacEntity
                                    + sacEntity.getTbAcPrimaryheadMaster());
                        }
                        if (sacEntity.getTbAcPrimaryheadMaster().getCpdIdAcHeadTypes() == null) {
                            builder.append(session.getMessage("account.voucher.service.cpdid.type") + detail.getSacHeadId()
                                    + sacEntity.getTbAcPrimaryheadMaster().getCpdIdAcHeadTypes());
                        }
                        final Object[] drCrIdArr = decideDrCrValidateSacHeadIdSide(detail, template, dto,
                                sacEntity.getTbAcPrimaryheadMaster().getCpdIdAcHeadTypes(), builder);
                        if ((drCrIdArr == null) || (drCrIdArr.length <= 0)) {
                            builder.append(
                                    "cpdIdAcHeadType is not configured in VoucherTemplate or Dc/Cr Type is not matched:"
                                            + "[" + detail + ",cpdIdAcHeadType="
                                            + sacEntity.getTbAcPrimaryheadMaster().getCpdIdAcHeadTypes());
                        }
                        if (drCrIdArr[0] == null) {
                            builder.append(
                                    "cpdIdAcHeadType is not configured in VoucherTemplate or Dc/Cr Type is not matched:"
                                            + "[" + detail + ",cpdIdAcHeadType="
                                            + sacEntity.getTbAcPrimaryheadMaster().getCpdIdAcHeadTypes());
                        }
                        if (drCrIdArr[0] != null) {
                            drCrId = (long) drCrIdArr[0];
                        }
                    } else if (detail.getPayModeId() != null) {//mode is available

                        final Object[] drCrIdArr = decideDrCrValidateModeSide(detail, template, dto, builder);
                        if (drCrIdArr[0] == null) {
                            builder.append(session.getMessage("account.voucher.service.paymode") + detail + template + dto
                                    + drCrIdArr[0]);
                        }
                        if (drCrIdArr[1] == null) {
                            builder.append(session.getMessage("account.voucher.service.head") + detail + template + dto
                                    + drCrIdArr[1]);
                        }
                        if (drCrIdArr[0] != null) {
                            drCrId = (long) drCrIdArr[0];
                        }
                        Long sacHeadId = null;
                        if (drCrIdArr[1] != null) {
                            sacHeadId = (long) drCrIdArr[1];
                        }
                        sacEntity = secondaryheadMasterJpaRepository.findOne(sacHeadId);
                        // detailEntity.setSacHeadId(sacHeadId);
                        if (sacEntity == null) {
                            builder.append(session.getMessage("account.voucher.service.sacheadid") + sacHeadId
                                    + MainetConstants.VOUCHER_POST_DETAIL_DTO + detail + sacEntity);
                        } else {
                            if (sacEntity.getTbAcPrimaryheadMaster() == null) {
                                builder.append(session.getMessage("account.voucher.post.service.pacheadid") + sacEntity
                                        + sacEntity.getTbAcPrimaryheadMaster());
                            }
                            if (sacEntity.getTbAcPrimaryheadMaster().getCpdIdAcHeadTypes() == null) {
                                builder.append(
                                        session.getMessage("account.voucher.service.cpdid.type")
                                                + sacEntity.getTbAcPrimaryheadMaster().getCpdIdAcHeadTypes());
                            }
                        }
                    } else { // demand type side required business logic.//3 call voucher Posting //getting Null
                        final Object[] drCrIdArr = decideDrCrValidateDemandTypeSide(detail, template, dto, builder);
                        if (drCrIdArr[0] == null) {
                            builder.append(session.getMessage("account.voucher.service.demandtype") + detail + template + dto
                                    + drCrIdArr[0]);
                        }
                        if (drCrIdArr[1] == null) {
                            builder.append(session.getMessage("account.voucher.service.head") + detail + template + dto
                                    + drCrIdArr[1]);
                        }
                        Long accountTypeId = Long.valueOf(drCrIdArr[0].toString());
                        final long sacHeadId = Long.valueOf(drCrIdArr[1].toString());
                        sacEntity = secondaryheadMasterJpaRepository.findOne(sacHeadId);
                        if (sacEntity == null) {
                            builder.append(session.getMessage("account.voucher.service.sacheadid")
                                    + sacHeadId + MainetConstants.VOUCHER_POST_DETAIL_DTO + detail + sacEntity);
                        } else {
                            if (sacEntity.getTbAcPrimaryheadMaster() == null) {
                                builder.append(session.getMessage("account.voucher.post.service.pacheadid") + sacEntity
                                        + sacEntity.getTbAcPrimaryheadMaster());
                            }
                            if (sacEntity.getTbAcPrimaryheadMaster().getCpdIdAcHeadTypes() == null) {
                                builder.append(session.getMessage("account.voucher.service.cpdid.type")
                                        + sacEntity.getTbAcPrimaryheadMaster().getCpdIdAcHeadTypes());
                            }
                        }
                        //Getting null
                        final Object[] drCrIdArrRDC = decideDrCrValidateSacHeadIdSide(detail, template, dto,
                                accountTypeId, builder);
                        if ((drCrIdArrRDC == null) || (drCrIdArrRDC.length <= 0)) {
                            builder.append("cpdIdAcHeadType is not configured in VoucherTemplate or Dc/Cr Type is not matched:"
                                    + "[" + detail + ",cpdIdAcHeadType="
                                    + sacEntity.getTbAcPrimaryheadMaster().getCpdIdAcHeadTypes());
                        }
                        if (drCrIdArrRDC[0] == null) {
                            builder.append("cpdIdAcHeadType is not configured in VoucherTemplate or Dc/Cr Type is not matched:"
                                    + "[" + detail + ",cpdIdAcHeadType="
                                    + sacEntity.getTbAcPrimaryheadMaster().getCpdIdAcHeadTypes());
                        }
                        drCrId = Long.valueOf(drCrIdArrRDC[0].toString());
                    }
                }
            }
            if (drCrId == null) {
                builder.append(ApplicationSession.getInstance().getMessage("account.voucher.service.standard.template")
                        + detail);
            }
            if (drCrId != null) {
                // detailEntity.setDrcrCpdId(drCrId);
                Organisation org = new Organisation();
                org.setOrgid(dto.getOrgId());
                String drCrCode = CommonMasterUtility.getNonHierarchicalLookUpObject(drCrId, org).getLookUpCode();
                if (drCrCode != null && !drCrCode.isEmpty()) {
                    if ("DR".equals(drCrCode)) {
                        drTotalAmount = drTotalAmount.add(detail.getVoucherAmount());
                        ++drCount;
                    } else if ("CR".equals(drCrCode)) {
                        crTotalAmount = crTotalAmount.add(detail.getVoucherAmount());
                        ++crCount;
                    }
                }
            }
            // details.add(detailEntity);
        }
        if (drCount == 0 || crCount == 0) {
            builder.append(ATLEAST_ONE_DRCR);
        }
        if (!drTotalAmount.equals(crTotalAmount)) {
            builder.append(SUM_OF_DEBIT_CREDIT);
        }
    }

    //Voucher Posting
    private Object[] decideDrCrValidateDemandTypeSide(VoucherPostDetailDTO detailDTO, VoucherTemplateMasterEntity template,
            VoucherPostDTO dto, StringBuilder builder) {
        final Long status = CommonMasterUtility.lookUpIdByLookUpCodeAndPrefix(
                MainetConstants.CommonConstants.ACTIVE, AccountPrefix.ACN.toString(), dto.getOrgId());
        final Object[] objArr = new Object[2];
        Long finYearId = null;
        if (detailDTO.getYearId() == null || detailDTO.getYearId() == 0L) {
            finYearId = 0L;
        } else {
            finYearId = detailDTO.getYearId();
        }
        //fyear required
        
        
        List<Object[]> drCrSacHeadId = voucherTemplateRepository.getDrCrIdByPassingDemandTypeId(detailDTO.getDemandTypeId(),
                finYearId, dto.getDepartmentId(), status, dto.getOrgId());
        
        /*List<Object[]> drCrSacHeadId = voucherTemplateRepository.getDrCrIdByPassingDemandTypeId(dto.getVoucherSubTypeId(),
                finYearId, dto.getDepartmentId(), status, dto.getOrgId());*/
        for (Object[] objects : drCrSacHeadId) {
            if (objects[0] != null) {
                objArr[0] = objects[0];
            } else {
                builder.append(ApplicationSession.getInstance()
                        .getMessage("account.voucher.service.drcrid.demandtype")
                        + detailDTO.getPayModeId());
            }
            if (objects[1] != null) {
                objArr[1] = objects[1];
            } else {
                builder.append(ApplicationSession.getInstance()
                        .getMessage("account.voucher.service.cpdid.demandtype")
                        + detailDTO.getPayModeId());
            }
        }
        return objArr;
    }

    //Voucher Posting
    private Object[] decideDrCrValidateSacHeadIdSide(final VoucherPostDetailDTO detailDTO,
            final VoucherTemplateMasterEntity template, final VoucherPostDTO dto, final Long cpdIdAcHeadType,
            StringBuilder builder) {

        final Long status = CommonMasterUtility.lookUpIdByLookUpCodeAndPrefix(
                MainetConstants.CommonConstants.ACTIVE, AccountPrefix.ACN.toString(), dto.getOrgId());
        final Object[] objArr = new Object[2];
        Long drCrIdId = voucherTemplateRepository.getDrCrIdByPassingSacHeadId(template.getTemplateId(), cpdIdAcHeadType,
                dto.getOrgId(), status);
        if (drCrIdId != null) {
            objArr[0] = drCrIdId;
            objArr[1] = 0;
        } else {
            builder.append(ACCOUNT_TYPE_NOT_CONFIGURED + cpdIdAcHeadType + ORGID + dto.getOrgId());
        }
        return objArr;
    }

    private Object[] decideDrCrValidateModeSide(final VoucherPostDetailDTO detailDTO,
            final VoucherTemplateMasterEntity template, final VoucherPostDTO dto, StringBuilder builder) {

        final Long status = CommonMasterUtility.lookUpIdByLookUpCodeAndPrefix(
                MainetConstants.CommonConstants.ACTIVE, AccountPrefix.ACN.toString(), dto.getOrgId());
        final Object[] objArr = new Object[2];
        List<Object[]> drCrSacHeadId = voucherTemplateRepository.getDrCrIdByPassingModeId(template.getTemplateId(),
                detailDTO.getPayModeId(), dto.getOrgId(), status);
        for (Object[] objects : drCrSacHeadId) {
            if (objects[0] != null) {
                objArr[0] = objects[0];
            } else {
                builder.append(ApplicationSession.getInstance()
                        .getMessage("account.voucher.service.drcrid.paymode")
                        + detailDTO.getPayModeId());
            }
            if (objects[1] != null) {
                objArr[1] = objects[1];
            } else {
                builder.append(ApplicationSession.getInstance()
                        .getMessage("account.voucher.service.cpdid.paymode")
                        + detailDTO.getPayModeId());
            }
        }
        return objArr;
    }

    @Override
    public List<String> validateExternalSystemDTOInput(List<VoucherPostExternalDTO> voucherExternalPostDTO) {
        LOGGER.info("Provided input for External System Voucher Posting validation:" + voucherExternalPostDTO);

        List<String> errorMessageList = new ArrayList<>();
        StringBuilder builder = null;
        for (VoucherPostExternalDTO dto : voucherExternalPostDTO) {

            VoucherPostDTO masterDTO = new VoucherPostDTO();
            builder = new StringBuilder();
            if (StringUtils.isBlank(dto.getVoucherDate())) {
                builder.append(VOUCHER_DATE);
            } else {
                Date vouDate = null;
                final DateFormat formatter = new SimpleDateFormat(MainetConstants.DATE_FORMAT);
                try {
                    vouDate = formatter.parse(dto.getVoucherDate());
                } catch (final ParseException e) {
                    builder.append("Kindly set voucher date (DD/MM/YYYY) format : " + dto.getVoucherDate() + e);
                }
            }
            if (StringUtils.isBlank(dto.getUlbCode())) {
                builder.append("ulbCode, ");
            } else {
                List<Organisation> orgList = iOrganisationService.findAllActiveOrganization("A");
                Long orgid = orgList.stream().filter(list -> list != null && list.getOrgShortNm().equals(dto.getUlbCode()))
                        .collect(Collectors.toList()).get(0).getOrgid();
                if (orgid == null || orgid == 0L) {
                    builder.append("orgid not exist based on given ulb code : " + dto.getUlbCode());
                } else {
                    masterDTO.setOrgId(orgid);
                }
            }
            if (StringUtils.isBlank(dto.getEntryCode())) {
                builder.append("entryTypeCode, ");
            } else {
                if (!dto.getEntryCode().equals("EXS")) {
                    builder.append("entryTypeCode is required 'EXS' - in Prefix -> 'VET', ");
                }
            }
            if (StringUtils.isBlank(dto.getVousubTypeCode())) {
                builder.append("vousubTypeCode, ");
            } else {
                if (masterDTO.getOrgId() != null && masterDTO.getOrgId() != 0L) {
                    Organisation organisation = new Organisation();
                    organisation.setOrgid(masterDTO.getOrgId());
                    List<LookUp> lookUpList = CommonMasterUtility.getLookUps(PrefixConstants.AccountPrefix.TDP.toString(),
                            organisation);
                    Long vouSubTypeId = lookUpList.stream()
                            .filter(list -> list != null && list.getLookUpCode().equals(dto.getVousubTypeCode()))
                            .collect(Collectors.toList()).get(0).getLookUpId();
                    if (vouSubTypeId == null || vouSubTypeId == 0L) {
                        builder.append("vouSubTypeId is not exist, based on given 'TDP' - Prefix lookup voucherSubTypeCode : "
                                + dto.getVousubTypeCode());
                    }
                }
            }
            if (StringUtils.isBlank(dto.getDepartmentCode())) {
                builder.append("departmentCode, ");
            } else {
                Long departmentId = departmentService.getDepartmentIdByDeptCode(dto.getDepartmentCode());
                if (departmentId == null || departmentId == 0L) {
                    builder.append("departmentId not exist based on given department code : " + dto.getUlbCode());
                }
            }
            if (StringUtils.isBlank(dto.getNarration())) {
                builder.append(NARRATION);
            }
            if (StringUtils.isBlank(dto.getLocationDescription())) {
                builder.append("locationDescription, ");
            } else {
                int locCount = 0;
                if (masterDTO.getOrgId() != null && masterDTO.getOrgId() != 0L) {
                    Map<Long, String> fieldMapList = tbAcFieldMasterService
                            .getFieldMasterLastLevelsCompositeCodeByOrgId(masterDTO.getOrgId());
                    for (Map.Entry<Long, String> fieldMap : fieldMapList.entrySet()) {
                        if (fieldMap.getValue() != null) {
                            if (fieldMap.getValue().equals(dto.getLocationDescription())) {
                                locCount++;
                                break;
                            }
                        }
                    }
                    if (locCount == 0) {
                        builder.append("fieldId is not exist, based on given location code and ulb code : "
                                + dto.getLocationDescription() + " - " + dto.getUlbCode());
                    }
                }
            }
            if (StringUtils.isBlank(dto.getCreatedBy())) {
                builder.append(CREATED_BY);
            }
            /*
             * if (StringUtils.isBlank(dto.getLgIpMac())) { builder.append(LG_IP_MAC); }
             */
            if (StringUtils.isBlank(dto.getVoucherReferenceNo())) {
                builder.append(VOUCHER_REF_NO);
            }
            if (StringUtils.isBlank(dto.getVoucherReferenceDate())) {
                builder.append(VOUCHER_REF_DATE);
            } else {
                Date vouReferenceDate = null;
                final DateFormat formatter = new SimpleDateFormat(MainetConstants.DATE_FORMAT);
                try {
                    vouReferenceDate = formatter.parse(dto.getVoucherReferenceDate());
                } catch (final ParseException e) {
                    builder.append(
                            "Kindly set voucher reference date (DD/MM/YYYY) format : " + dto.getVoucherReferenceDate() + e);
                }
            }
            if (StringUtils.isBlank(dto.getEntryFlag())) {
                if (dto.getPayModeCode() == null) {
                    builder.append(PAY_MODE_ID);
                } else {
                    if (masterDTO.getOrgId() != null && masterDTO.getOrgId() != 0L) {
                        Organisation organisation = new Organisation();
                        organisation.setOrgid(masterDTO.getOrgId());
                        List<LookUp> lookUpList = CommonMasterUtility.getLookUps(PrefixConstants.PAY_PREFIX.PREFIX_VALUE,
                                organisation);
                        Long payModeId = lookUpList.stream()
                                .filter(list -> list != null && list.getLookUpCode().equals(dto.getPayModeCode()))
                                .collect(Collectors.toList()).get(0).getLookUpId();
                        if (payModeId == null || payModeId == 0L) {
                            builder.append("payModeId is not exist, based on given 'PAY' - Prefix payModeCode : "
                                    + dto.getPayModeCode());
                        }
                    }
                }
            }
            if (dto.getVousubTypeCode() != null && !dto.getVousubTypeCode().isEmpty()) {
                if (dto.getVousubTypeCode().equals("DMD") || dto.getVousubTypeCode().equals("BDL")
                        || dto.getVousubTypeCode().equals("RBT") || dto.getVousubTypeCode().equals("DWO")
                        || dto.getVousubTypeCode().equals("RBR")) {
                    if (dto.getFinancialYearId() == null) {
                        // builder.append(FIN_YEAR_ID);
                    }
                }
            }

            List<VoucherPostDetailExternalDTO> detailsExternalList = dto.getVoucherExtDetails();
            if ((detailsExternalList == null) || detailsExternalList.size() == 0) {
                builder.append(VOU_REV_DET_DETAILS);
            } else {
                int count = 0;
                for (final VoucherPostDetailExternalDTO detailExternalDto : detailsExternalList) {

                    if (StringUtils.isBlank(detailExternalDto.getDemandTypeCode())) {
                        if (StringUtils.isBlank(detailExternalDto.getAcHeadCode())) {
                            builder.append("getDetails[" + count).append("acHeadCode, ");
                        } else {
                            if (masterDTO.getOrgId() != null && masterDTO.getOrgId() != 0L) {
                                Organisation organisation = new Organisation();
                                organisation.setOrgid(masterDTO.getOrgId());
                                LookUp lookUpStatus = CommonMasterUtility.getValueFromPrefixLookUp(MainetConstants.MASTER.A,
                                        PrefixConstants.ACN, organisation);
                                Long activeStatusId = lookUpStatus.getLookUpId();
                                int sacHeadCodeCount = 0;
                                List<Object[]> acHeadList = secondaryheadMasterJpaRepository.findAccountHeadsByOrgIdAndStatusId(
                                        organisation.getOrgid(),
                                        activeStatusId);
                                for (Object[] objects : acHeadList) {
                                    if (objects[1] != null) {
                                        if (detailExternalDto.getAcHeadCode().equals(objects[1].toString())) {
                                            sacHeadCodeCount++;
                                            break;
                                        }
                                    }
                                }
                                if (sacHeadCodeCount == 0) {
                                    builder.append("account head code is not exist, based on given acHeadCode : "
                                            + detailExternalDto.getAcHeadCode());
                                }
                            }
                        }
                    } else {
                        if ((masterDTO.getOrgId() != null && masterDTO.getOrgId() != 0L)
                                && (detailExternalDto.getDemandTypeCode() != null
                                        && !detailExternalDto.getDemandTypeCode().isEmpty())) {
                            Organisation organisation = new Organisation();
                            organisation.setOrgid(masterDTO.getOrgId());
                            List<LookUp> lookUpList = CommonMasterUtility.getLookUps(PrefixConstants.AccountPrefix.TDP.toString(),
                                    organisation);
                            Long demandTypeId = lookUpList.stream()
                                    .filter(list -> list != null
                                            && list.getLookUpCode().equals(detailExternalDto.getDemandTypeCode()))
                                    .collect(Collectors.toList()).get(0).getLookUpId();
                            if (demandTypeId == null || demandTypeId == 0L) {
                                builder.append("demandTypeId is not exist, based on given 'TDP' - Prefix demandTypeCode : "
                                        + detailExternalDto.getDemandTypeCode());
                            }
                        } else {
                            builder.append("getDetails[" + count).append("DemandTypeCode, ");
                        }
                    }
                    if (detailExternalDto.getVoucherAmount() == null
                            || detailExternalDto.getVoucherAmount().compareTo(BigDecimal.ZERO) == 0
                            || detailExternalDto.getVoucherAmount().signum() == -1) {
                        builder.append("getDetails[" + count).append("voucherAmount ");
                    }
                    /*
                     * if(dto.getVousubTypeCode() != null && !dto.getVousubTypeCode().isEmpty()) {
                     * if(dto.getVousubTypeCode().equals("DMD") || dto.getVousubTypeCode().equals("BDL") ||
                     * dto.getVousubTypeCode().equals("RBT") || dto.getVousubTypeCode().equals("DWO") ||
                     * dto.getVousubTypeCode().equals("RBR")) { if (detailExternalDto.getYearId() == null) {
                     * builder.append("getDetails[" + count).append(FIN_YEAR_ID); } } }
                     */
                    count++;
                }
            }
            if (!builder.toString().isEmpty()) {
                builder.append(CANNOT_BE_NULL);
            }
            if (!builder.toString().isEmpty()) {
                errorMessageList.add(builder.toString());
            }
        }
        return errorMessageList;
    }

    @Override
    public List<VoucherPostDTO> convertExternalSystemDTOIntoVouPostDTO(
            List<VoucherPostExternalDTO> voucherExternalPostDTO, String clientIPAddress) {
        LOGGER.info("Provided input to convert form External System DTO to Voucher Posting DTO :" + voucherExternalPostDTO);

        List<VoucherPostDTO> postDTOList = new ArrayList<>();
        for (VoucherPostExternalDTO dto : voucherExternalPostDTO) {

            VoucherPostDTO masterDTO = new VoucherPostDTO();
            if (dto.getVoucherDate() != null && !dto.getVoucherDate().isEmpty()) {
                masterDTO.setVoucherDate(Utility.stringToDate(dto.getVoucherDate()));
            }
            if ((dto.getUlbCode() != null) && (!dto.getUlbCode().isEmpty())) {
                List<Organisation> orgList = iOrganisationService.findAllActiveOrganization("A");
                Long orgid = orgList.stream().filter(list -> list != null && list.getOrgShortNm().equals(dto.getUlbCode()))
                        .collect(Collectors.toList()).get(0).getOrgid();
                masterDTO.setOrgId(orgid);
            }
            if ((dto.getEntryCode() != null) && (!dto.getEntryCode().isEmpty())) {
                masterDTO.setEntryType(dto.getEntryCode());
            }
            if ((dto.getVousubTypeCode() != null) && (!dto.getVousubTypeCode().isEmpty())) {
                if (masterDTO.getOrgId() != null && masterDTO.getOrgId() != 0L) {
                    Organisation organisation = new Organisation();
                    organisation.setOrgid(masterDTO.getOrgId());
                    List<LookUp> lookUpList = CommonMasterUtility.getLookUps(PrefixConstants.AccountPrefix.TDP.toString(),
                            organisation);
                    Long vouSubTypeId = lookUpList.stream()
                            .filter(list -> list != null && list.getLookUpCode().equals(dto.getVousubTypeCode()))
                            .collect(Collectors.toList()).get(0).getLookUpId();
                    masterDTO.setVoucherSubTypeId(vouSubTypeId);
                }
            }
            if ((dto.getDepartmentCode() != null) && (!dto.getDepartmentCode().isEmpty())) {
                Long departmentId = departmentService.getDepartmentIdByDeptCode(dto.getDepartmentCode());
                masterDTO.setDepartmentId(departmentId);
            }
            if ((dto.getNarration() != null) && (!dto.getNarration().isEmpty())) {
                masterDTO.setNarration(dto.getNarration());
            }
            if ((dto.getLocationDescription() != null) && (!dto.getLocationDescription().isEmpty())) {
                if (masterDTO.getOrgId() != null && masterDTO.getOrgId() != 0L) {
                    // List<AccountFieldMasterBean> fieldList = tbAcFieldMasterService.findAll(masterDTO.getOrgId());
                    // Long fieldId = fieldList.stream().filter(list->list != null && (list.getFieldCompcode() != null &&
                    // !list.getFieldCompcode().isEmpty()) &&
                    // list.getFieldCompcode().equals(dto.getLocationDescription())).collect(Collectors.toList()).get(0).getFieldId();
                    // masterDTO.setFieldId(fieldId);
                    Map<Long, String> fieldMapList = tbAcFieldMasterService
                            .getFieldMasterLastLevelsCompositeCodeByOrgId(masterDTO.getOrgId());
                    for (Map.Entry<Long, String> fieldMap : fieldMapList.entrySet()) {
                        if (fieldMap.getValue() != null) {
                            if (fieldMap.getValue().equals(dto.getLocationDescription())) {
                                masterDTO.setFieldId(fieldMap.getKey());
                                break;
                            }
                        }
                    }
                }
            }
            if ((dto.getCreatedBy() != null) && (!dto.getCreatedBy().isEmpty())) {
                masterDTO.setCreatedBy(Long.valueOf(dto.getCreatedBy()));
            }
            if (clientIPAddress != null && !clientIPAddress.isEmpty()) {
                masterDTO.setLgIpMac(clientIPAddress);
            }
            if ((dto.getVoucherReferenceNo() != null) && (!dto.getVoucherReferenceNo().isEmpty())) {
                masterDTO.setVoucherReferenceNo(dto.getVoucherReferenceNo());
            }
            if (dto.getVoucherReferenceDate() != null && !dto.getVoucherReferenceDate().isEmpty()) {
                masterDTO.setVoucherReferenceDate(Utility.stringToDate(dto.getVoucherReferenceDate()));
            }
            if (dto.getEntryFlag() == null || dto.getEntryFlag().isEmpty()) {
                if (masterDTO.getOrgId() != null && masterDTO.getOrgId() != 0L) {
                    Organisation organisation = new Organisation();
                    organisation.setOrgid(masterDTO.getOrgId());
                    List<LookUp> lookUpList = CommonMasterUtility.getLookUps(PrefixConstants.PAY_PREFIX.PREFIX_VALUE,
                            organisation);
                    Long payModeId = lookUpList.stream()
                            .filter(list -> list != null && list.getLookUpCode().equals(dto.getPayModeCode()))
                            .collect(Collectors.toList()).get(0).getLookUpId();
                    masterDTO.setPayModeId(payModeId);
                }
            } else {
                masterDTO.setEntryFlag(dto.getEntryFlag());
            }
            if (dto.getPayerOrPayee() != null && !dto.getPayerOrPayee().isEmpty()) {
                masterDTO.setPayerOrPayee(dto.getPayerOrPayee());
            }
            if (dto.getVousubTypeCode() != null && !dto.getVousubTypeCode().isEmpty()) {
                if (dto.getVousubTypeCode().equals("DMD") || dto.getVousubTypeCode().equals("BDL")
                        || dto.getVousubTypeCode().equals("RBT") || dto.getVousubTypeCode().equals("DWO")
                        || dto.getVousubTypeCode().equals("RBR")) {
                    /*
                     * // i put langId 0 because there is no use of langId inside that method Task #4701 Map<Long, String>
                     * financialYear = secondaryheadMasterService.getAllFinincialYear(masterDTO.getOrgId(), 0); String result =
                     * financialYear.entrySet().parallelStream() .filter(x ->
                     * x.getValue().trim().equalsIgnoreCase(dto.getFinancialYear().trim())) .map(x -> x.getKey().toString())
                     * .collect(Collectors.joining());
                     */
                    // masterDTO.setFinancialYearId(dto.getFinancialYearId());
                }
            }

            List<VoucherPostDetailDTO> detailslList = new ArrayList<>();
            List<VoucherPostDetailExternalDTO> detailsExternalList = dto.getVoucherExtDetails();
            for (final VoucherPostDetailExternalDTO detailExternalDto : detailsExternalList) {
                VoucherPostDetailDTO detailsDTO = new VoucherPostDetailDTO();

                if (detailExternalDto.getDemandTypeCode() == null || detailExternalDto.getDemandTypeCode().isEmpty()) {
                    if (masterDTO.getOrgId() != null && masterDTO.getOrgId() != 0L) {
                        Organisation organisation = new Organisation();
                        organisation.setOrgid(masterDTO.getOrgId());
                        LookUp lookUpStatus = CommonMasterUtility.getValueFromPrefixLookUp(MainetConstants.MASTER.A,
                                PrefixConstants.ACN, organisation);
                        Long activeStatusId = lookUpStatus.getLookUpId();
                        List<Object[]> acHeadList = secondaryheadMasterJpaRepository.findAccountHeadsByOrgIdAndStatusId(
                                organisation.getOrgid(),
                                activeStatusId);
                        String head = acHeadList.parallelStream()
                                .filter(l -> l[1] != null && l[1].toString().equalsIgnoreCase(detailExternalDto.getAcHeadCode()))
                                .map(l -> l[0].toString()).collect(Collectors.joining());
                        detailsDTO.setSacHeadId(Long.valueOf(head));

                    }
                } else {
                    if ((masterDTO.getOrgId() != null && masterDTO.getOrgId() != 0L)
                            && (detailExternalDto.getDemandTypeCode() != null
                                    && !detailExternalDto.getDemandTypeCode().isEmpty())) {
                        Organisation organisation = new Organisation();
                        organisation.setOrgid(masterDTO.getOrgId());
                        List<LookUp> lookUpList1 = CommonMasterUtility.getLookUps(PrefixConstants.AccountPrefix.TDP.toString(),
                                organisation);
                        Long demandTypeId = lookUpList1.stream()
                                .filter(list -> list != null
                                        && list.getLookUpCode().equals(detailExternalDto.getDemandTypeCode()))
                                .collect(Collectors.toList()).get(0).getLookUpId();
                        detailsDTO.setDemandTypeId(demandTypeId);
                      
                    }
                }
                if (detailExternalDto.getVoucherAmount() != null) {
                    detailsDTO.setVoucherAmount(detailExternalDto.getVoucherAmount());
                }
                /*
                 * if(dto.getVousubTypeCode() != null && !dto.getVousubTypeCode().isEmpty()) {
                 * if(dto.getVousubTypeCode().equals("DMD") || dto.getVousubTypeCode().equals("BDL") ||
                 * dto.getVousubTypeCode().equals("RBT") || dto.getVousubTypeCode().equals("DWO") ||
                 * dto.getVousubTypeCode().equals("RBR")) { detailsDTO.setYearId(detailExternalDto.getYearId()); } }
                 */
                /*
                 * if(detailExternalDto.getYearId() != null) { detailsDTO.setYearId(detailExternalDto.getYearId()); }
                 */
                if (detailExternalDto.getAccountHeadFlag() != null) {
                    detailsDTO.setAccountHeadFlag(detailExternalDto.getAccountHeadFlag());
                }
                detailslList.add(detailsDTO);
            }
            masterDTO.setVoucherDetails(detailslList);
            postDTOList.add(masterDTO);
        }
        return postDTOList;
    }
//
//	//code added by rahul.chaubey #28583 Start
//	public boolean exceptionHandling(VoucherPostDTO voucherPostDTO, StringBuilder message) {
//		// creating object of VoucherException Entity
//		AccountVoucherExceptionMasterEntity exceptionObject = new AccountVoucherExceptionMasterEntity();
//		
//		// setting the values of dto into the entity object
//		if(voucherPostDTO.getAuthFlag()== null)
//		{
//			exceptionObject.setAuthoFlag('\u0000');
//		}
//		else {
//			exceptionObject.setAuthoFlag(voucherPostDTO.getAuthFlag().charAt(0));
//		}
//		exceptionObject.setBillVoucherPostingDate(voucherPostDTO.getBillVouPostingDate());
//		exceptionObject.setCreatedBy(voucherPostDTO.getCreatedBy());
//		exceptionObject.setDeptId(voucherPostDTO.getDepartmentId());
//		//exceptionObject.setEntryFlag('A');
//		
//		if (voucherPostDTO.getEntryType() != null && voucherPostDTO.getOrgId() != null) {
//			//exceptionObject.setEntryType(entryType(voucherPostDTO.getEntryType(), voucherPostDTO.getOrgId()));
//			exceptionObject.setEntryType(entryType(voucherPostDTO.getEntryType(), voucherPostDTO.getOrgId()));
//		}
//		else 
//		{
//			exceptionObject.setEntryType(null);
//		}
//
//		exceptionObject.setFaYearId(voucherPostDTO.getFinancialYearId());
//		exceptionObject.setFieldId(voucherPostDTO.getFieldId());
//		// set Exception Handling Message
//		exceptionObject.setExceptionDetails(message.toString());
//		exceptionObject.setLangId(Long.valueOf(voucherPostDTO.getLangId()));
//		exceptionObject.setLgIpMac(voucherPostDTO.getLgIpMac());
//		exceptionObject.setNarration(voucherPostDTO.getNarration());
//		exceptionObject.setOrgId(voucherPostDTO.getOrgId());
//		exceptionObject.setPayerPayee(voucherPostDTO.getPayerOrPayee());
//		exceptionObject.setPayModeidId(voucherPostDTO.getPayModeId());
//
//		if (voucherPostDTO.getTemplateType() != null) 
//		{
//			exceptionObject.setTemplateType(Long.valueOf(voucherPostDTO.getTemplateType()));
//		}
//		else 
//		{
//			exceptionObject.setTemplateType(null);
//		}
//		exceptionObject.setVouceherRefenceNoDate(voucherPostDTO.getVoucherReferenceDate());
//		exceptionObject.setVoucherDate(voucherPostDTO.getVoucherDate());
//		//exceptionObject.setVoucherId();
//		exceptionObject.setVoucherReferenceNo(voucherPostDTO.getVoucherReferenceNo());
//		exceptionObject.setVoucherSubTypeCpdId(voucherPostDTO.getVoucherSubTypeId());
//		if(voucherPostDTO.getVoucherSubTypeId()!=null)
//		{
//			exceptionObject.setVoucherSubTypeCpdId(Long.valueOf(voucherPostDTO.getVoucherSubTypeId()));
//		}
//		else 
//		{
//			exceptionObject.setVoucherSubTypeCpdId(null);
//		}
//		 //setting voucherTypeCpdId instead of VoucherType
//		 	DateTimeFormatter dtf = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss");
//		 	LocalDateTime now = LocalDateTime.now();
//		   	exceptionObject.setCreatedDate(Utility.stringToDate(dtf.format(now), "dd/MM/yyyy HH:mm:ss"));
//		   	accountVoucherExceptionJpaRepository.save(exceptionObject);
//
//		return false;
//	}
//	//code added by rahul.chaubey #28583 End
//	
}

