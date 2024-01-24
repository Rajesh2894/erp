package com.abm.mainet.cfc.challan.service;

import java.io.File;
import java.io.IOException;
import java.math.BigDecimal;
import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import java.util.TreeMap;
import java.util.stream.Collectors;

import javax.annotation.Resource;

import org.apache.commons.codec.binary.Base64;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.collections.MapUtils;
import org.apache.commons.io.FileUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.abm.mainet.bill.service.BillDetailsService;
import com.abm.mainet.bill.service.BillMasterCommonService;
import com.abm.mainet.bill.service.BillPaymentService;
import com.abm.mainet.cfc.challan.dao.IChallanDAO;
import com.abm.mainet.cfc.challan.domain.ChallanDetails;
import com.abm.mainet.cfc.challan.domain.ChallanMaster;
import com.abm.mainet.cfc.challan.dto.ChallanReceiptPrintDTO;
import com.abm.mainet.cfc.challan.dto.ChallanReportDTO;
import com.abm.mainet.cfc.loi.service.TbLoiMasService;
import com.abm.mainet.common.constant.MainetConstants;
import com.abm.mainet.common.constant.PrefixConstants;
import com.abm.mainet.common.dao.ICFCApplicationMasterDAO;
import com.abm.mainet.common.domain.BankMasterEntity;
import com.abm.mainet.common.domain.CFCApplicationAddressEntity;
import com.abm.mainet.common.domain.FinancialYear;
import com.abm.mainet.common.domain.LocationMasEntity;
import com.abm.mainet.common.domain.Organisation;
import com.abm.mainet.common.domain.ServiceMaster;
import com.abm.mainet.common.domain.TbCfcApplicationMstEntity;
import com.abm.mainet.common.domain.TbTaxMasEntity;
import com.abm.mainet.common.dto.BillReceiptPostingDTO;
import com.abm.mainet.common.dto.BillReceiptPrintingDTO;
import com.abm.mainet.common.dto.CFCSchedulingCounterDet;
import com.abm.mainet.common.dto.CommonChallanDTO;
import com.abm.mainet.common.dto.CommonChallanPayModeDTO;
import com.abm.mainet.common.dto.WardZoneBlockDTO;
import com.abm.mainet.common.exception.FrameworkException;
import com.abm.mainet.common.integration.acccount.domain.TbServiceReceiptMasEntity;
import com.abm.mainet.common.integration.acccount.domain.TbSrcptFeesDetEntity;
import com.abm.mainet.common.integration.dms.fileUpload.FileUploadUtility;
import com.abm.mainet.common.integration.dms.service.IFileUploadService;
import com.abm.mainet.common.integration.dto.ApplicationFormChallanDTO;
import com.abm.mainet.common.integration.dto.DocumentDetailsVO;
import com.abm.mainet.common.integration.dto.RequestDTO;
import com.abm.mainet.common.integration.payment.service.IPostPaymentService;
import com.abm.mainet.common.master.dto.BankMasterDTO;
import com.abm.mainet.common.master.dto.TbComparamMas;
import com.abm.mainet.common.master.dto.TbComparentMas;
import com.abm.mainet.common.master.dto.TbServicesMst;
import com.abm.mainet.common.master.dto.TbTaxMas;
import com.abm.mainet.common.master.repository.LocationMasJpaRepository;
import com.abm.mainet.common.master.repository.TbServicesMstJpaRepository;
import com.abm.mainet.common.master.repository.TbTaxMasJpaRepository;
import com.abm.mainet.common.master.service.BankMasterService;
import com.abm.mainet.common.master.service.DepartmentService;
import com.abm.mainet.common.master.service.TbCfcApplicationMstService;
import com.abm.mainet.common.master.service.TbFinancialyearService;
import com.abm.mainet.common.master.service.TbServicesMstService;
import com.abm.mainet.common.master.service.TbTaxMasService;
import com.abm.mainet.common.repository.ReceiptRepository;
import com.abm.mainet.common.service.CommonService;
import com.abm.mainet.common.service.ICFCApplicationAddressService;
import com.abm.mainet.common.service.IDuplicateReceiptService;
import com.abm.mainet.common.service.IFinancialYearService;
import com.abm.mainet.common.service.ILocationMasService;
import com.abm.mainet.common.service.IOrganisationService;
import com.abm.mainet.common.service.IReceiptEntryService;
import com.abm.mainet.common.service.ServiceMasterService;
import com.abm.mainet.common.service.TbComparamMasService;
import com.abm.mainet.common.service.TbComparentMasService;
import com.abm.mainet.common.utility.ApplicationContextProvider;
import com.abm.mainet.common.utility.ApplicationSession;
import com.abm.mainet.common.utility.CommonMasterUtility;
import com.abm.mainet.common.utility.LookUp;
import com.abm.mainet.common.utility.RestClient;
import com.abm.mainet.common.utility.SeqGenFunctionUtility;
import com.abm.mainet.common.utility.UserSession;
import com.abm.mainet.common.utility.Utility;
import com.abm.mainet.common.utility.UtilityService;
import com.abm.mainet.common.workflow.service.IWorkflowRequestService;
import com.abm.mainet.smsemail.dto.SMSAndEmailDTO;
import com.abm.mainet.smsemail.service.ISMSAndEmailService;
import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

/**
 *
 * @author Rahul.Yadav
 *
 */
@Service
public class ChallanService implements IChallanService {

    private static final Logger LOGGER = Logger.getLogger(ChallanService.class);

    IPostPaymentService IPostPaymentService;

    @Autowired
    private IChallanDAO challanDAO;

    @Autowired
    ISMSAndEmailService ismsAndEmailService;

    @Autowired
    private TbLoiMasService iTbLoiMasService;

    @Autowired
    private MessageSource messageSource;

    @Autowired
    private TbServicesMstService iTbServicesMstService;

    @Autowired
    private TbTaxMasService tbTaxMasService;

    @Autowired
    private ICFCApplicationAddressService iCFCApplicationAddressService;

    @Autowired
    private IWorkflowRequestService workflowRequestService;

    @Resource
    private DepartmentService departmentService;

    @Autowired
    private ICFCApplicationMasterDAO iCFCApplicationMasterDAO;

    @Resource
    private SeqGenFunctionUtility seqGenFunctionUtility;

    @Resource
    private CommonService commonService;

    @Resource
    private BillMasterCommonService billMasterCommonService;

    @Autowired
    private IReceiptEntryService iReceiptEntryService;

    @Autowired
    private IFileUploadService fileUpload;

    @Autowired
    private IDuplicateReceiptService iDuplicateReceiptService;

    @Autowired
    private IOrganisationService iOrganisationService;

    @Autowired
    private IFinancialYearService iFinancialYear;

    @Resource
    private TbServicesMstJpaRepository tbServicesMstJpaRepository;
    
    @Resource
	private BankMasterService bankMasterService;

    @Resource
    private TbCfcApplicationMstService tbCfcApplicationMstService;
    
	@Autowired
	private ServiceMasterService serviceMaster;
	
	@Autowired
    private TbTaxMasJpaRepository tbTaxMasJpaRepository;
	
	@Autowired
    private ReceiptRepository receiptRepository;
	
	@Autowired
	private ILocationMasService locationMasService;
	
	@Resource
    private LocationMasJpaRepository locationMasJpaRepository;
	
    @Override
    public ChallanMaster getChallanMasters(final Long challanNo, final Long applNo) {
        return challanDAO.getChallanMasters(challanNo, applNo);
    }

    @Override
    @Transactional(readOnly = true)
    public boolean getChallanMasterTransId(final String bankTransId) {
        return challanDAO.getChallanMasterTransId(bankTransId);
    }

    @Override
    public ChallanMaster getChallanMasterById(final Long challanId) {
        return challanDAO.getChallanMasterById(challanId);
    }

    @Transactional
    @Override
    public TbServiceReceiptMasEntity updateChallanDetails(final ChallanMaster challanMaster, Long taskId, Long empType,
            final Long empId, String empName) {

        final ChallanMaster challanMasterupdated = challanDAO.updateChallanDetails(challanMaster);
        TbServiceReceiptMasEntity receipt = null;
        List<BillReceiptPostingDTO> result = null;
        final CommonChallanDTO requestDto = new CommonChallanDTO();
        if (challanMasterupdated != null) {
            final long orgid = challanMasterupdated.getOrganisationId();

            final List<ChallanDetails> challanDetails = challanDAO
                    .getChallanDetails(challanMasterupdated.getChallanNo(), orgid);
            final CFCApplicationAddressEntity address = iCFCApplicationAddressService
                    .getApplicationAddressByAppId(challanMasterupdated.getApmApplicationId(), orgid);
            final TbCfcApplicationMstEntity applicationMaster = iCFCApplicationMasterDAO.getApplicationMaster(
                    challanMasterupdated.getApmApplicationId(), UserSession.getCurrent().getOrganisation());

            String userName = (applicationMaster.getApmFname() == null ? MainetConstants.BLANK
                    : applicationMaster.getApmFname() + MainetConstants.WHITE_SPACE);
            userName += applicationMaster.getApmMname() == null ? MainetConstants.BLANK
                    : applicationMaster.getApmMname() + MainetConstants.WHITE_SPACE;
            userName += applicationMaster.getApmLname() == null ? MainetConstants.BLANK
                    : applicationMaster.getApmLname();

            requestDto.setApplNo(challanMasterupdated.getApmApplicationId());
            requestDto.setOrgId(challanMasterupdated.getOrganisationId());
            requestDto.setDeptId(challanMasterupdated.getDpDeptid());
            requestDto.setAmountToPay(challanMasterupdated.getChallanAmount().toString());
            requestDto.setApplicantName(userName);
            requestDto.setServiceId(challanMasterupdated.getSmServiceId());
            requestDto.setFaYearId(UserSession.getCurrent().getFinYearId());
            requestDto.setUserId(empId);
            requestDto.setLangId(UserSession.getCurrent().getLanguageId());
            requestDto.setLgIpMac(challanMaster.getLgIpMacUpd());
            requestDto.setPayModeIn(CommonMasterUtility
                    .getValueFromPrefixLookUp(PrefixConstants.PAY_PREFIX.BANK, PrefixConstants.PAY_PREFIX.PREFIX_VALUE)
                    .getLookUpId());
            requestDto.setCbBankId(challanMasterupdated.getBmBankid());
            requestDto.setLoiNo(challanMasterupdated.getLoiNo());
            requestDto.setBankaAccId(challanMasterupdated.getBaAccountid());

            // in case of mixed if bill and workflow both require
            // revenue bill payment

            // if
            // ((MainetConstants.CHALLAN_RECEIPT_TYPE.REVENUE_BASED.equals(challanMasterupdated.getChallanServiceType())
            // ||
            // MainetConstants.CHALLAN_RECEIPT_TYPE.MIXED.equals(challanMasterupdated.getChallanServiceType()))
            // && challanMasterupdated.getUniquePrimaryId() != null) {

            if ((MainetConstants.CHALLAN_RECEIPT_TYPE.REVENUE_BASED
                    .equals(challanMasterupdated.getChallanServiceType()))
                    && challanMasterupdated.getUniquePrimaryId() != null) {

                result = updateBillMasterData(challanMasterupdated.getUniquePrimaryId(), orgid,
                        challanMasterupdated.getDpDeptid(), challanMasterupdated.getChallanAmount(), empId,
                        requestDto.getLgIpMac(),null,null,null);
                if (result != null && !result.isEmpty()) {
                    receipt = iReceiptEntryService.insertInReceiptMaster(requestDto, result);
                    final Long receiptId = receipt.getRmRcptid();
                    final Long advanceTaxId = billMasterCommonService.getAdvanceTaxId(orgid, null,
                            requestDto.getDeptId());
                    result.forEach(billReceiptPostingDTO -> {
                        if (billReceiptPostingDTO.getTaxId().equals(advanceTaxId)) {
                            saveAdvancePayment(orgid, challanMasterupdated.getUniquePrimaryId(), receiptId,
                                    challanMasterupdated.getDpDeptid(), empId, billReceiptPostingDTO.getTaxAmount());
                        }
                    });
                }
            }

            // application service payment
            if (MainetConstants.CHALLAN_RECEIPT_TYPE.NON_REVENUE_BASED
                    .equals(challanMasterupdated.getChallanServiceType())
                    || MainetConstants.CHALLAN_RECEIPT_TYPE.OBJECTION
                            .equals(challanMasterupdated.getChallanServiceType())

            ) {
                setChargeDetailMap(requestDto, challanDetails);
                receipt = iReceiptEntryService.insertInReceiptMaster(requestDto, result);

                iCFCApplicationMasterDAO.updateCFCApplicationMasterPaymentStatus(
                        challanMasterupdated.getApmApplicationId(), MainetConstants.Common_Constant.YES,
                        challanMasterupdated.getOrganisationId());
                if ((challanMasterupdated.getLoiNo() != null) && !challanMasterupdated.getLoiNo().isEmpty()) {
                    iTbLoiMasService.updateLoiPaidByLoiNo(challanMasterupdated.getLoiNo(),
                            challanMasterupdated.getOrganisationId());
                }

                workflowRequestService.updateWorkFlow(taskId, requestDto, empType, empId, empName);
            }

            // if
            // (MainetConstants.CHALLAN_RECEIPT_TYPE.OBJECTION.equals(challanMasterupdated.getChallanServiceType()))
            // {
            // setChargeDetailMap(requestDto, challanDetails);
            // receipt = iReceiptEntryService.insertInReceiptMaster(requestDto, result);
            // }

            // if
            // (MainetConstants.CHALLAN_RECEIPT_TYPE.NON_REVENUE_BASED.equals(challanMasterupdated.getChallanServiceType())
            // ||
            // MainetConstants.CHALLAN_RECEIPT_TYPE.MIXED.equals(challanMasterupdated.getChallanServiceType())
            // ||
            // MainetConstants.CHALLAN_RECEIPT_TYPE.OBJECTION.equals(challanMasterupdated.getChallanServiceType()))
            // {

            // iCFCApplicationMasterDAO.updateCFCApplicationMasterPaymentStatus(
            // challanMasterupdated.getApmApplicationId(),
            // MainetConstants.Common_Constant.YES,
            // challanMasterupdated.getOrganisationId());
            // if ((challanMasterupdated.getLoiNo() != null) &&
            // !challanMasterupdated.getLoiNo().isEmpty()) {
            // iTbLoiMasService.updateLoiPaidByLoiNo(challanMasterupdated.getLoiNo(),
            // challanMasterupdated.getOrganisationId());
            // }
            // updateWorkFlow(taskId, requestDto, empType, empId, empName);
            // }
            sendSmsAndEmail(challanMasterupdated, address, userName, empId);
        }
        return receipt;
    }

    private void setChargeDetailMap(final CommonChallanDTO requestDto, final List<ChallanDetails> challanDetails) {
        final Map<Long, Double> feeId = new HashMap<>(0);
        final Map<Long, Long> billdetId = new HashMap<>(0);
        for (final ChallanDetails details : challanDetails) {
            feeId.put(details.getTaxId(), details.getRfFeeamount());
            if (details.getBilldetId() != null) {
                billdetId.put(details.getTaxId(), details.getBilldetId());
            }
        }
        requestDto.setFeeIds(feeId);
        requestDto.setBillDetIds(billdetId);
    }

    /**
     * @param ChallanMasterupdated
     * @param address
     * @param userName
     */
    private void sendSmsAndEmail(final ChallanMaster ChallanMasterupdated, final CFCApplicationAddressEntity address,
            final String userName, Long empId) {
        final SMSAndEmailDTO dto = new SMSAndEmailDTO();

        final DecimalFormat format = new DecimalFormat("#.00");

        if (ChallanMasterupdated.getChallanAmount() != null) {

            final String decamount = format.format(ChallanMasterupdated.getChallanAmount());
            if ((decamount != null) && !decamount.isEmpty()) {
                dto.setChallanAmt(decamount);
            } else {
                dto.setChallanAmt(ChallanMasterupdated.getChallanAmount().toString());
            }

        } else {
            dto.setChallanAmt(MainetConstants.BLANK);
        }
        final String serviceName = MainetConstants.BLANK;
        dto.setEmail(address.getApaEmail());
        dto.setMobnumber(address.getApaMobilno());
        dto.setAppName(userName);
        if (serviceName != null) {
            dto.setServName(serviceName);
        } else {
            dto.setServName(ApplicationSession.getInstance().getMessage("challan.message.appliedService"));
        }
        dto.setAppNo(ChallanMasterupdated.getApmApplicationId().toString());
        Organisation org = new Organisation();
        org.setOrgid(ChallanMasterupdated.getOrganisationId());
        int langId = Utility.getDefaultLanguageId(org);
        // Added Changes As per told by Rajesh Sir For Sms and Email
        dto.setUserId(empId);
        ismsAndEmailService.sendEmailSMS(MainetConstants.DEPT_SHORT_NAME.CFC_CENTER,
                MainetConstants.SMS_EMAIL_URL.CHALLAN_UPDATE, PrefixConstants.SMS_EMAIL_ALERT_TYPE.APPROVAL, dto, org,
                langId);
    }

    /**
     * @param primaryKeyId
     * @param orgid
     * @param amount
     * @param empId
     * @param ipAddress
     * @param serviceId
     * @return
     */
    @Override
    public List<BillReceiptPostingDTO> updateBillMasterData(final String primaryKeyId, final long orgid,
            final Long deptId, final Double amount, Long empId, String ipAddress, Date manualReceptDate,
            String flatNo,String parentNo) {
        List<BillReceiptPostingDTO> result = null;
        try {
            BillPaymentService dynamicServiceInstance = null;
            String serviceClassName = null;
            final String deptCode = departmentService.getDeptCode(deptId);
            serviceClassName = messageSource.getMessage(MainetConstants.CHALLAN_BILL + deptCode, new Object[] {},
                    StringUtils.EMPTY, Locale.ENGLISH);
            dynamicServiceInstance = ApplicationContextProvider.getApplicationContext().getBean(serviceClassName,
                    BillPaymentService.class);
            if(StringUtils.isNotBlank(parentNo)) {
            	 result = dynamicServiceInstance.updateBillMasterAmountPaidForGroupProperty(primaryKeyId, amount, orgid, empId, ipAddress,
                       manualReceptDate, flatNo,parentNo);
            }else {
            	 result = dynamicServiceInstance.updateBillMasterAmountPaid(primaryKeyId, amount, orgid, empId, ipAddress,
                         manualReceptDate, flatNo);
            }
        } catch (LinkageError | Exception e) {
            throw new FrameworkException("Exception in UpdateBill for department Id :" + deptId, e);
        }
        return result;
    }

    @Override
    public CommonChallanDTO getDataRequiredforRevenueReceipt(CommonChallanDTO offlineMaster,
            final Organisation orgnisation) {
        CommonChallanDTO result = null;
        try {
            BillPaymentService dynamicServiceInstance = null;
            String serviceClassName = null;
            final String deptCode = departmentService.getDeptCode(offlineMaster.getDeptId());
            serviceClassName = messageSource.getMessage(MainetConstants.CHALLAN_BILL + deptCode, new Object[] {},
                    StringUtils.EMPTY, Locale.ENGLISH);
            dynamicServiceInstance = ApplicationContextProvider.getApplicationContext().getBean(serviceClassName,
                    BillPaymentService.class);
            result = dynamicServiceInstance.getDataRequiredforRevenueReceipt(offlineMaster, orgnisation);
        } catch (LinkageError | Exception e) {
            throw new FrameworkException(
                    "Exception in getDataRequiredforRevenueReceipt for department Id :" + offlineMaster.getDeptId(), e);
        }
        return result;
    }

    @Override
    @Transactional

    // find all reference where it is called and after challan generation workflow
    // to be initiated
    // this method is having problem if user does not come for payment unnessarily
    // workflow reamin in open state
    // there should be some schedular which will check all expire challan and close
    // these task
    public ChallanMaster InvokeGenerateChallan(final CommonChallanDTO requestDTO) {
        final ChallanMaster challan = new ChallanMaster();
        final int days = challanDAO.getdurationDays(requestDTO.getServiceId(), requestDTO.getOrgId());
        Organisation org = new Organisation();
        org.setOrgid(requestDTO.getOrgId());
        final Calendar c = Calendar.getInstance();
        c.setTime(new Date());
        c.add(Calendar.DATE, days);
        if ((requestDTO.getOfflinePaymentText() != null)
                && requestDTO.getOfflinePaymentText().equalsIgnoreCase(PrefixConstants.OFFLINE_TYPE.PAY_AT_BANK)) {
            final List<String> bankDetails = challanDAO.getBankDetailsList(requestDTO.getBankaAccId(),
                    requestDTO.getOrgId());
            if ((bankDetails != null) && !bankDetails.isEmpty()) {
                challan.setBmBankid(Long.valueOf(bankDetails.get(4)));
                challan.setBaAccountid(Long.valueOf(bankDetails.get(3)));
            }
        }
        final Long challanNumber = seqGenFunctionUtility.generateSequenceNo(MainetConstants.CHALLAN_NO_PARAM.Module,
                MainetConstants.CHALLAN_NO_PARAM.Table, MainetConstants.CHALLAN_NO_PARAM.Column, requestDTO.getOrgId(),
                MainetConstants.CHALLAN_NO_PARAM.Reset_Type, null);
        String startYear = StringUtils.EMPTY;
        String endYaer = StringUtils.EMPTY;
        final SimpleDateFormat sdf1 = new SimpleDateFormat(MainetConstants.YEAR_FORMAT1);
        if (requestDTO.getFinYearStartDate() != null) {
            startYear = sdf1.format(requestDTO.getFinYearStartDate());
        }

        final SimpleDateFormat sdf2 = new SimpleDateFormat(MainetConstants.YEAR_FORMAT1);
        if (requestDTO.getFinYearEndDate() != null) {
            endYaer = sdf2.format(requestDTO.getFinYearEndDate());
        }

        final String challanNo = startYear + endYaer + challanNumber.toString();
        challan.setChallanNo(challanNo);
        requestDTO.setChallanNo(challanNo);
        challan.setOrganisationId(requestDTO.getOrgId());
        challan.setUserempId(requestDTO.getUserId());
        if ((requestDTO.getAmountToPay() != null) && !requestDTO.getAmountToPay().isEmpty()) {
            challan.setChallanAmount(Double.valueOf(requestDTO.getAmountToPay()));
        }
        challan.setChallanDate(new Date());
        challan.setChallanValiDate(c.getTime());
        if (requestDTO.getFaYearId() != null) {
            challan.setFaYearid(Long.valueOf(requestDTO.getFaYearId()));
        }
        challan.setApmApplicationId(requestDTO.getApplNo());
        challan.setSmServiceId(requestDTO.getServiceId());
        challan.setDpDeptid(requestDTO.getDeptId());
        challan.setStatus(MainetConstants.FlagA);
        challan.setLangId(requestDTO.getLangId());
        challan.setLgIpMac(requestDTO.getLgIpMac());
        challan.setLmodDate(new Date());
        challan.setChallanServiceType(requestDTO.getChallanServiceType());
        challan.setOflPaymentMode(requestDTO.getOflPaymentMode());
        challan.setChallanRcvdFlag(MainetConstants.Common_Constant.NO);
        challan.setLoiNo(requestDTO.getLoiNo());
        challan.setUniquePrimaryId(requestDTO.getUniquePrimaryId());
        challan.setPaymentReceiptCategory(requestDTO.getPaymentCategory());
        final ChallanMaster challanMaster = challanDAO.InvokeGenerateChallan(challan);

        final List<ChallanDetails> challanDetails = new ArrayList<>(0);
        ChallanDetails details = null;
        if ((requestDTO.getFeeIds() != null) && (requestDTO.getFeeIds().keySet() != null)) {
            final Map<Long, Double> feeAmmount = requestDTO.getFeeIds();
            Long feeId = null;
            Double ammount = null;
            for (final Entry<Long, Double> entry : feeAmmount.entrySet()) {
                feeId = entry.getKey();
                ammount = entry.getValue();
                details = new ChallanDetails();
                details.setChallanNo(challanMaster.getChallanNo());
                details.setTaxId(feeId);
                details.setRfFeeamount(ammount);
                details.setOrgId(requestDTO.getOrgId());
                details.setUserId(requestDTO.getUserId());
                details.setLangId(requestDTO.getLangId());
                details.setLgIpMac(requestDTO.getLgIpMac());
                details.setLmodDate(new Date());
                if ((requestDTO.getBillDetIds() != null) && !requestDTO.getBillDetIds().isEmpty()) {
                    details.setBilldetId(requestDTO.getBillDetIds().get(feeId));
                }
                challanDetails.add(details);
            }
        }
        challanDAO.saveChallanDetails(challanDetails);
        if(Utility.isEnvPrefixAvailable(org, MainetConstants.ENV_TSCL)) {
        	if ((MainetConstants.CHALLAN_RECEIPT_TYPE.NON_REVENUE_BASED.equals(requestDTO.getChallanServiceType())
                    || MainetConstants.CHALLAN_RECEIPT_TYPE.MIXED.equals(requestDTO.getChallanServiceType())) && StringUtils.isEmpty(requestDTO.getWorkflowEnable())) {
                final WardZoneBlockDTO dwzDTO = commonService.getWordZoneBlockByApplicationId(requestDTO.getApplNo(),
                        requestDTO.getServiceId(), requestDTO.getOrgId());
                workflowRequestService.initiateAndUpdateWorkFlowProcess(requestDTO, dwzDTO);
            }
        }else {
        	if ((MainetConstants.CHALLAN_RECEIPT_TYPE.NON_REVENUE_BASED.equals(requestDTO.getChallanServiceType())
                    || MainetConstants.CHALLAN_RECEIPT_TYPE.MIXED.equals(requestDTO.getChallanServiceType()))&& !Utility.isEnvPrefixAvailable(org, MainetConstants.APP_NAME.TCP)) {
                final WardZoneBlockDTO dwzDTO = commonService.getWordZoneBlockByApplicationId(requestDTO.getApplNo(),
                        requestDTO.getServiceId(), requestDTO.getOrgId());

                workflowRequestService.initiateAndUpdateWorkFlowProcess(requestDTO, dwzDTO);
            }
        }
        	
        
        return challanMaster;
    }

    /*
     * (non-Javadoc)
     * @see com.abm.mainetservice.web.cfc.challan.service.IChallanService# getchallanOfflineType(long, long)
     */
    @Override
    @Transactional(readOnly = true)
    public ChallanMaster getchallanOfflineType(final long applicationId, final long orgid) {
        return challanDAO.getchallanOfflineType(applicationId, orgid);
    }

    /*
     * (non-Javadoc)
     * @see com.abm.mainetservice.web.cfc.challan.service.IChallanService# getBankDetailsList(java.lang.Long, long)
     */
    @Override
    @Transactional(readOnly = true)
    public List<String> getBankDetailsList(final Long bankAccID, final long organisation) {
        return challanDAO.getBankDetailsList(bankAccID, organisation);
    }

    /*
     * (non-Javadoc)
     * @see com.abm.mainetservice.web.cfc.challan.service.IChallanService#getChallanData
     * (com.abm.mainetservice.rest.common.bean.CommonChallanDTO, com.abm.mainetservice.web.common.entity.Organisation)
     */
    @Override
    @Transactional
    public ApplicationFormChallanDTO getChallanData(final CommonChallanDTO challanDTO,
            final Organisation organisation) {
        final ApplicationFormChallanDTO rfc = new ApplicationFormChallanDTO();
        final ApplicationSession session = ApplicationSession.getInstance();
        List<String> bankParams = null;
        final String paymentMode = CommonMasterUtility.getNonHierarchicalLookUpObject(challanDTO.getOflPaymentMode())
                .getLookUpCode();
        if ((paymentMode != null) && paymentMode.equalsIgnoreCase(MainetConstants.PAYMENT_MODES.PCB_MODE)) {
            rfc.setLabelMun(session.getMessage("chn.labelBank"));
            rfc.setLabelBank(session.getMessage("chn.labelMun"));
            rfc.setLcd(session.getMessage("chn.lcd"));
            rfc.setLcdd(session.getMessage("chn.lcdd"));

            bankParams = getBankDetailsList(challanDTO.getBankaAccId(), challanDTO.getOrgId());
            if ((bankParams != null) && !bankParams.isEmpty()) {
                rfc.setBankName(bankParams.get(0));
                rfc.setBranch(bankParams.get(1));
                rfc.setBankAccId(bankParams.get(2));
            }
        }
        if ((paymentMode != null) && paymentMode.equalsIgnoreCase(MainetConstants.PAYMENT_MODES.PCU_MODE)) {
            rfc.setLabelMun(session.getMessage("chn.labelMun"));
            rfc.setLcd(session.getMessage("chn.lcd"));
            rfc.setLcdd(session.getMessage("chn.lcdd"));
        }
        if ((paymentMode != null) && paymentMode.equalsIgnoreCase(MainetConstants.PAYMENT_MODES.PPO_MODE)) {
            rfc.setLcd(session.getMessage("chn.pono"));
            rfc.setLcdd(session.getMessage("chn.poDate"));
            rfc.setDdOrPpDate(challanDTO.getPoDate());
            rfc.setDdOrPpnumber(challanDTO.getPoNo());
        }
        if ((paymentMode != null) && paymentMode.equalsIgnoreCase(MainetConstants.PAYMENT_MODES.PDD_MODE)) {
            rfc.setLcd(session.getMessage("chn.ddno"));
            rfc.setLcdd(session.getMessage("chn.ddDate"));
            rfc.setDdOrPpDate(challanDTO.getDdDate());
            rfc.setDdOrPpnumber(challanDTO.getDdNo());
        }

        rfc.setLabel_Municipality(session.getMessage("chn.labelMunicipality"));
        rfc.setLabelRtiNo(session.getMessage("chn.labelRtiNo"));
        rfc.setRtiNo(challanDTO.getApplNo());
        rfc.setLoiNo(challanDTO.getLoiNo());
        rfc.setLabelCitizen(session.getMessage("chn.labelCitizen"));
        rfc.setlDateOfDeposite(session.getMessage("chn.lDateOfDeposite"));
        rfc.setlDateOfFilling(session.getMessage("chn.lDateOfFilling"));
        rfc.setlChallanNo(session.getMessage("chn.lChallanNo"));

        if (UserSession.getCurrent().getLanguageId() == 1) {
            rfc.setOrgName(UserSession.getCurrent().getOrganisation().getONlsOrgname());
        }
        if (UserSession.getCurrent().getLanguageId() == 2) {
            rfc.setOrgName(UserSession.getCurrent().getOrganisation().getONlsOrgnameMar());
        }

        String service = null;
        String serviceName = session.getMessage("challan.message.serviceName");
        /*
         * service = iTbServicesMstService.findServiceNameById(challanDTO.getServiceId(), challanDTO.getOrgId()); if (service !=
         * null) { serviceName = service; }
         */
        ServiceMaster serviceMaster = tbServicesMstJpaRepository.findOne(challanDTO.getServiceId());
        if (serviceMaster != null && serviceMaster.getSmServiceName() != null) {
            serviceName = serviceMaster.getSmServiceName();
            if (serviceMaster.getTbDepartment().getDpDeptcode() != null
                    && serviceMaster.getSmShortdesc().equals(MainetConstants.RTISERVICE.RTIAPPLICATIONSERVICECODE)) {
                rfc.setlPaymentForRti((session.getMessage("chn.payment.for." + serviceMaster.getSmShortdesc())));
            } else {
                rfc.setlPaymentForRti(session.getMessage("challan.message.paymentFor") + serviceName);
            }
        } else {
            rfc.setlPaymentForRti(session.getMessage("challan.message.paymentFor") + serviceName);
        }
        rfc.setlName(session.getMessage("chn.lName"));
        rfc.setName(challanDTO.getApplicantName());
        rfc.setlAddress(session.getMessage("chn.lAddress"));
        rfc.setAddress(challanDTO.getApplicantAddress());
        rfc.setlContact(session.getMessage("chn.lContact"));
        rfc.setContact(challanDTO.getMobileNumber());
        rfc.setlEmail(session.getMessage("chn.lEmail"));
        rfc.setEmail(challanDTO.getEmailId());
        rfc.setlAmountPayable(session.getMessage("chn.lAmountPayable"));
        rfc.setlAmountInWords(session.getMessage("chn.lAmountInWords"));
        if (challanDTO.getAmountToPay() != null) {
            rfc.setAmount(Double.valueOf(challanDTO.getAmountToPay()));
            final String amountInWords = Utility.convertBigNumberToWord(new BigDecimal(challanDTO.getAmountToPay()));
            rfc.setAmountInWords(amountInWords);
        }

        rfc.setIpaymentMode(session.getMessage("chn.ipaymentMode"));
        rfc.setPaymentModeValue(session.getMessage("chn.paymentModeValue"));

        rfc.setIpaymentText(session.getMessage("chn.ipaymentText"));

        rfc.setlBankName(session.getMessage("chn.lBankName"));
        rfc.setlBranch(session.getMessage("chn.lBranch"));
        rfc.setlTransactionId(session.getMessage("chn.lTransactionId"));
        rfc.setlSignatureStamp(session.getMessage("chn.lSignatureStamp"));
        rfc.setlSignOfDepositer(session.getMessage("chn.lSignatureOfDepositer"));
        rfc.setlAR(session.getMessage("chn.lAR"));
        rfc.setTodate(new Date());
        if (challanDTO.getChallanNo() != null) {
            rfc.setChallanNo(Long.valueOf(challanDTO.getChallanNo()));
        }

        rfc.setLvalidDate(session.getMessage("chn.lvalidDate"));
        rfc.setLwebSiteLink(session.getMessage("chn.lwebSiteLink"));
        rfc.setValidDate(challanDTO.getChallanValidDate());
        rfc.setLbankaccId(session.getMessage("chn.lbankaccId"));

        final SMSAndEmailDTO dto = new SMSAndEmailDTO();
        dto.setChallanNo(challanDTO.getChallanNo());
        dto.setEmail(challanDTO.getEmailId());
        dto.setMobnumber(challanDTO.getMobileNumber());
        dto.setAppName(challanDTO.getApplicantName());
        dto.setServName(serviceName);
        if (challanDTO.getApplNo() != null) {
            dto.setAppNo(challanDTO.getApplNo().toString());
        }
        dto.setOrganizationName(organisation.getONlsOrgname());
        int langId;
        if (UserSession.getCurrent() != null) {
            langId = UserSession.getCurrent().getLanguageId();
        } else {
            langId = 1;
        }
        // Added Changes As per told by Rajesh Sir For Sms and Email
        dto.setUserId(challanDTO.getUserId());
        ismsAndEmailService.sendEmailSMS(MainetConstants.DEPT_SHORT_NAME.CFC_CENTER,
                MainetConstants.SMS_EMAIL_URL.CHALLAN_UPDATE, PrefixConstants.SMS_EMAIL_ALERT_TYPE.APPROVAL, dto,
                organisation, langId);

        return rfc;
    }

    /*
     * (non-Javadoc)
     * @see com.abm.mainetservice.web.cfc.challan.service.IChallanService# updateOnlinePaymentCFCStatus(java.lang.Long,
     * java.lang.Long)
     */
    @Override
    @Transactional
    public void updateOnlinePaymentCFCStatus(final Long applNo, final Long orgId) {
        iCFCApplicationMasterDAO.updateCFCApplicationMasterPaymentStatus(applNo, MainetConstants.PAY_STATUS.PAID,
                orgId);
    }

    /*
     * (non-Javadoc)
     * @see com.abm.mainetservice.web.cfc.challan.service.IChallanService#
     * setValuesAndPrintReport(com.abm.mainetservice.core.entity. TbServiceReceiptMasEntity,
     * com.abm.mainetservice.web.cfc.challan.dto.ChallanDetailsDTO)
     */
    @Override
    public ChallanReceiptPrintDTO setValuesAndPrintReport(final TbServiceReceiptMasEntity serviceReceiptMaster,
            final CommonChallanDTO offline) {
        Map<Long, ChallanReportDTO> taxdto = new HashMap<>();
        Map<String, String> billPeriodDetails = new LinkedHashMap<String, String>();
        Organisation org = iOrganisationService.getOrganisationById(offline.getOrgId());
        final ChallanReceiptPrintDTO printDTO = new ChallanReceiptPrintDTO();
        printDTO.setOrgName(org.getONlsOrgname());
        printDTO.setOrgNameMar(org.getONlsOrgnameMar());
        
        FinancialYear financialYear = ApplicationContextProvider.getApplicationContext()
                .getBean(TbFinancialyearService.class).getFinanciaYearByDate(new Date());
        String licFinYear = null;
        if (financialYear != null) {
            licFinYear = Utility.getFinancialYear(financialYear.getFaFromDate(), financialYear.getFaToDate());
        }
     
      
        StringBuilder receiptNumberRnl = new StringBuilder();
        String seperator = "/";
        
        receiptNumberRnl.append("TMC").append(seperator);
        if(serviceReceiptMaster.getFieldId() !=null && offline.getOrgId() != null) {
        	printDTO.setFieldId(serviceReceiptMaster.getFieldId());
            String locationNameById = locationMasService.getLocationNameById(serviceReceiptMaster.getFieldId(),offline.getOrgId());
           
            final LocationMasEntity tbLocationMasEntity = locationMasJpaRepository.findByLocationName(locationNameById, offline.getOrgId());
           
            
        
        if(tbLocationMasEntity!=null && tbLocationMasEntity.getLocCode()!=null && !tbLocationMasEntity.getLocCode().isEmpty()) {
        	 receiptNumberRnl.append(tbLocationMasEntity.getLocCode()).append(seperator);
        }else if(locationNameById !=null && !locationNameById.isEmpty()) {
        	receiptNumberRnl.append(locationNameById).append(seperator);
        }
        }
      
        receiptNumberRnl.append("EST").append(seperator);
        if(serviceReceiptMaster.getRmRcptno()!=null) {
        receiptNumberRnl.append(String.format("%06d", serviceReceiptMaster.getRmRcptno())).append(seperator);
        }
        receiptNumberRnl.append(licFinYear);
        if(Utility.isEnvPrefixAvailable(org, MainetConstants.ENV_TSCL)&& offline.getDeptCode() !=null && !offline.getDeptCode().isEmpty() && offline.getDeptCode().equals("RL") ) {
        	if(serviceReceiptMaster.getRmRcptno()!=null) {
                printDTO.setReceiptNo(receiptNumberRnl.toString());
                }
        	
        }else {
        	if(serviceReceiptMaster.getRmRcptno()!=null) {
                printDTO.setReceiptNo(serviceReceiptMaster.getRmRcptno().toString());
                }
        }
        
        printDTO.setReceiptId(serviceReceiptMaster.getRmRcptid());
        printDTO.setManualReceiptDate(Utility.dateToString(offline.getManualReeiptDate()));
        printDTO.setManualReceiptNo(offline.getManualReceiptNo());
        printDTO.setCfcCenter(offline.getCfcCenter());
        printDTO.setCfcCounterNo(offline.getCfcCounterNo());
        printDTO.setPayeeName(offline.getPayeeName());
        printDTO.setNewHouseNo(offline.getNewHouseNo());
        printDTO.setMobileNumber(offline.getMobileNumber());
        if(serviceReceiptMaster.getSmServiceId()!=null)
        	printDTO.setServiceId(serviceReceiptMaster.getSmServiceId());
       
        if (serviceReceiptMaster.getRmDate() != null) {
            printDTO.setRcptDate(
                    new SimpleDateFormat(MainetConstants.DATE_FORMAT).format(serviceReceiptMaster.getRmDate()));
            printDTO.setRmDate(serviceReceiptMaster.getRmDate());
        }
        final String date = Utility.dateToString(new Date());
        
        if (Utility.isEnvPrefixAvailable(org, MainetConstants.ENV_TSCL)  && offline.getDeptCode() !=null && !offline.getDeptCode().isEmpty() && offline.getDeptCode().equals("RL")  ) {
        printDTO.setReceiptDate(Utility.dateToString(offline.getManualReeiptDate()));
        }else {
        	 printDTO.setReceiptDate(date);
        }
        
        final SimpleDateFormat sd = new SimpleDateFormat("dd-MMM-yyyy");
        String cfcDate = sd.format(new Date());
        printDTO.setCfcDate(cfcDate);
        final SimpleDateFormat localDateFormat = new SimpleDateFormat("HH:mm");
        final String time = localDateFormat.format(new Date());
        printDTO.setReceiptTime(time);
        LookUp roundOffNotReq = null;
        try {
            roundOffNotReq = CommonMasterUtility.getValueFromPrefixLookUp("RNR", "ENV", org);

        } catch (Exception e) {
            LOGGER.error("No prefix found for ENV(RNR)");
        }

        LookUp receiptForSkdcl = null;
        try {
            receiptForSkdcl = CommonMasterUtility.getValueFromPrefixLookUp("RPF", "ENV", org);

        } catch (Exception e) {
            LOGGER.error("No prefix found for ENV(RPF)");
        }

        if (offline.getFaYearId() != null && !offline.getFaYearId().isEmpty()) {
            FinancialYear finYear = iFinancialYear.getFinincialYearsById(Long.valueOf(offline.getFaYearId()),
                    offline.getOrgId());
            if (finYear != null) {
                final SimpleDateFormat sdf1 = new SimpleDateFormat(MainetConstants.YEAR_FORMAT);
                final String startYear = sdf1.format(finYear.getFaFromDate());
                final SimpleDateFormat sdf2 = new SimpleDateFormat(MainetConstants.YEAR_FORMAT1);
                final String endYaer = sdf2.format(finYear.getFaToDate());
                printDTO.setFinYear(startYear + MainetConstants.HYPHEN + endYaer);
            }
        }

        // TbComparamMasService
        String deptCode = departmentService.getDeptCode(serviceReceiptMaster.getDpDeptId());
        printDTO.setDeptShortCode(deptCode);
        String wardZonePrefixValue = null;
        if (StringUtils.isNotBlank(deptCode) && StringUtils.equals(deptCode, "WT")) {
            wardZonePrefixValue = "WWZ";
        } else if (StringUtils.isNotBlank(deptCode) && StringUtils.equals(deptCode, "AS")) {
            wardZonePrefixValue = "WZB";
        }

        if (StringUtils.isNotBlank(wardZonePrefixValue)) {
            TbComparamMas comparamMas = ApplicationContextProvider.getApplicationContext()
                    .getBean(TbComparamMasService.class).findComparamDetDataByCpmId(wardZonePrefixValue);
            List<TbComparentMas> comparentMasList = ApplicationContextProvider.getApplicationContext()
                    .getBean(TbComparentMasService.class)
                    .findComparentMasDataById(comparamMas.getCpmId(), offline.getOrgId());
            if (CollectionUtils.isNotEmpty(comparentMasList)) {

                comparentMasList.sort(Comparator.comparing(TbComparentMas::getComLevel));

                comparentMasList.forEach(comparentMas -> {
                    Long coddwzId = null;
                    String prefixDesc = null;
                    if (comparentMas.getComLevel() == 1) {
                        coddwzId = serviceReceiptMaster.getCoddwzId1();
                    } else if (comparentMas.getComLevel() == 2) {
                        coddwzId = serviceReceiptMaster.getCoddwzId2();
                    } else if (comparentMas.getComLevel() == 3) {
                        coddwzId = serviceReceiptMaster.getCoddwzId3();
                    } else if (comparentMas.getComLevel() == 4) {
                        coddwzId = serviceReceiptMaster.getCoddwzId4();
                    } else if (comparentMas.getComLevel() == 5) {
                        coddwzId = serviceReceiptMaster.getCoddwzId5();
                    }
                    if ((coddwzId != null) && (coddwzId > 0)) {
                        prefixDesc = CommonMasterUtility.getHierarchicalLookUp(coddwzId, org).getLookUpDesc();
                        String[] wardZone = new String[2];
                        wardZone[0] = comparentMas.getComDesc();
                        wardZone[1] = prefixDesc;
                        printDTO.getWardZoneList().add(wardZone);
                    }
                });
            }
        }

        if ((serviceReceiptMaster.getCoddwzId1() != null) && (serviceReceiptMaster.getCoddwzId1() > 0)) {
            printDTO.setDwz1(CommonMasterUtility.getHierarchicalLookUp(serviceReceiptMaster.getCoddwzId1(), org)
                    .getLookUpDesc());
        }
        if ((serviceReceiptMaster.getCoddwzId2() != null) && (serviceReceiptMaster.getCoddwzId2() > 0)) {
            printDTO.setDwz2(CommonMasterUtility.getHierarchicalLookUp(serviceReceiptMaster.getCoddwzId2(), org)
                    .getLookUpDesc());
        }
        if ((serviceReceiptMaster.getCoddwzId3() != null) && (serviceReceiptMaster.getCoddwzId3() > 0)) {
            printDTO.setDwz3(CommonMasterUtility.getHierarchicalLookUp(serviceReceiptMaster.getCoddwzId3(), org)
                    .getLookUpDesc());
        }
        if ((serviceReceiptMaster.getCoddwzId4() != null) && (serviceReceiptMaster.getCoddwzId4() > 0)) {
            printDTO.setDwz4(CommonMasterUtility.getHierarchicalLookUp(serviceReceiptMaster.getCoddwzId4(), org)
                    .getLookUpDesc());
        }
        if ((serviceReceiptMaster.getCoddwzId5() != null) && (serviceReceiptMaster.getCoddwzId5() > 0)) {
            printDTO.setDwz5(CommonMasterUtility.getHierarchicalLookUp(serviceReceiptMaster.getCoddwzId5(), org)
                    .getLookUpDesc());
        }
        
        printDTO.setParshadWard1(offline.getParshadWard1());
        printDTO.setParshadWard2(offline.getParshadWard2());
        printDTO.setParshadWard3(offline.getParshadWard3());
        printDTO.setParshadWard4(offline.getParshadWard4());
        printDTO.setParshadWard5(offline.getParshadWard5());
        
        printDTO.setPaymentText(ApplicationSession.getInstance().getMessage("receipt.label.offline"));
        String departDesc = null;
        if (offline.getLangId() > 0) {
            departDesc = departmentService.fetchDepartmentDescEngById(serviceReceiptMaster.getDpDeptId(),
                    offline.getLangId());
        } else {
            departDesc = departmentService.fetchDepartmentDescById(serviceReceiptMaster.getDpDeptId());
        }
        printDTO.setDeptName(departDesc);
        printDTO.setReceivedFrom(offline.getApplicantName());
        printDTO.setOwnerFullName(offline.getApplicantFullName());
        printDTO.setTransferType(offline.getTransferType()); // #92282
        String serviceName = null;
        if (offline.getLangId() > 0) {
            serviceName = iTbServicesMstService.getServiceNameByServiceIdLangId(offline.getServiceId(),
                    offline.getLangId());
        } else {
            serviceName = iTbServicesMstService.getServiceNameByServiceId(offline.getServiceId());
        }
        TbServicesMst serviceMst = iTbServicesMstService.findById(offline.getServiceId());
        String serviceCode = serviceMst.getSmShortdesc();
        
        if(serviceMst!=null)
        printDTO.setServiceCode(serviceMst.getSmShortdesc());

        /*
         * compare proper Service code Told By Saurab Sir this if for estateBooking property Add By Suhel Chaudhry
         */
        if (serviceCode.equals(MainetConstants.RNL_ESTATE_BOOKING)) {
            String changeServiceName = offline.getServiceName();
            SimpleDateFormat formatter = new SimpleDateFormat("dd MMMM yyyy");
            String fromDate = "", toDate = "";
            if (offline.getFromedate() != null && offline.getToDate() != null) {
                fromDate = formatter.format(offline.getFromedate());
                toDate = formatter.format(offline.getToDate());
            }

            String fromDateToDate = fromDate + " to " + toDate;
            String addMuncipaleProperty = serviceName + MainetConstants.HYPHEN + MainetConstants.WHITE_SPACE
                    + changeServiceName + MainetConstants.HYPHEN + "Booking from " + MainetConstants.HYPHEN
                    + fromDateToDate;
            printDTO.setSubject(ApplicationSession.getInstance().getMessage("receipt.label.receipt.subject")
                    + MainetConstants.WHITE_SPACE + addMuncipaleProperty);
            offline.setServiceName(offline.getServiceName());
            // Defect #112686
        } else if (serviceCode.equals(MainetConstants.TradeLicense.RENEWAL_SERVICE_SHORT_CODE)) {
            SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
            String fromDate = "", toDate = "";
            // #130728 -to share from year to year on skdcl env
            if (offline.getFromedate() != null && offline.getToDate() != null) {
                if (Utility.isEnvPrefixAvailable(org, MainetConstants.ENV_SKDCL)|| Utility.isEnvPrefixAvailable(org, MainetConstants.ENV_ASCL)) {
                    //Defect #139300
                    DateFormat yearFormater = new SimpleDateFormat(MainetConstants.DATE_FORMAT_UPLOAD);
                    fromDate = yearFormater.format(offline.getFromedate());
                    toDate = yearFormater.format(offline.getToDate());
                } else {
                    fromDate = formatter.format(offline.getFromedate());
                    toDate = formatter.format(offline.getToDate());
                }

            }

            String fromDateToDate = fromDate + MainetConstants.WHITE_SPACE
                    + ApplicationSession.getInstance().getMessage("receipt.label.receipt.to")
                    + MainetConstants.WHITE_SPACE + toDate + MainetConstants.WHITE_SPACE;
            String subjectformat = ApplicationSession.getInstance().getMessage("receipt.label.receipt.subject")
                    + MainetConstants.WHITE_SPACE + serviceName + MainetConstants.WHITE_SPACE
                    + ApplicationSession.getInstance().getMessage("receipt.label.receipt.from")
                    + MainetConstants.WHITE_SPACE + fromDateToDate
                    + ApplicationSession.getInstance().getMessage("receipt.label.receipt.licno")
                    + MainetConstants.WHITE_SPACE + offline.getLicNo();
            printDTO.setSubject(subjectformat);
        } else if (serviceCode.equals(MainetConstants.TradeLicense.DUPLICATE_SERVICE_SHORT_CODE)
                || serviceCode.equals(MainetConstants.TradeLicense.TRANSFER_SERVICE_SHORT_CODE)
                || serviceCode.equals(MainetConstants.TradeLicense.CHANGE_BUSINESS_NAME_SHORT_CODE)
                || serviceCode.equals(MainetConstants.TradeLicense.CANCELLATION_SHORT_CODE)) {
            String subjectformat = ApplicationSession.getInstance().getMessage("receipt.label.receipt.subject")
                    + MainetConstants.WHITE_SPACE + serviceName + MainetConstants.WHITE_SPACE
                    + ApplicationSession.getInstance().getMessage("receipt.label.receipt.licno")
                    + MainetConstants.WHITE_SPACE + offline.getLicNo();
            printDTO.setSubject(subjectformat);
        } else if (serviceName != null && !StringUtils.isEmpty(serviceName)) {
            printDTO.setSubject(ApplicationSession.getInstance().getMessage("receipt.label.receipt.subject")
                    + MainetConstants.WHITE_SPACE + serviceName);
            offline.setServiceName(serviceName);
        } else {
            printDTO.setSubject(ApplicationSession.getInstance().getMessage("receipt.label.receipt.subject")
                    + MainetConstants.WHITE_SPACE + "Payment");
        }
        printDTO.setAddress(offline.getApplicantAddress());
        printDTO.setApplicationNumber(serviceReceiptMaster.getApmApplicationId());
        printDTO.setPaymentMode(CommonMasterUtility
                .getNonHierarchicalLookUpObject(serviceReceiptMaster.getReceiptModeDetail().get(0).getCpdFeemode(), org)
                .getLookUpDesc());
        printDTO.setChkPmtMode(CommonMasterUtility
                .getNonHierarchicalLookUpObject(serviceReceiptMaster.getReceiptModeDetail().get(0).getCpdFeemode(), org)
                .getDescLangFirst());
        printDTO.setAmount(Double.valueOf(serviceReceiptMaster.getRmAmount().toString()));
		if(Utility.isEnvPrefixAvailable(org, MainetConstants.ENV_SKDCL) && MainetConstants.ServiceShortCode.DUPLICATE_BILL_PRINT.equals(serviceCode)) {
			printDTO.setAmountPayable(printDTO.getAmount());
	        printDTO.setTotalReceivedAmount(printDTO.getAmount());				
		}
        printDTO.setDdOrPpnumber(serviceReceiptMaster.getReceiptModeDetail().get(0).getRdChequeddno());
        if (serviceReceiptMaster.getReceiptModeDetail().get(0).getRdChequedddate() != null) {
            printDTO.setDdOrPpDate(UtilityService
                    .convertDateToDDMMYYYY(serviceReceiptMaster.getReceiptModeDetail().get(0).getRdChequedddate()));
        }
        if (serviceReceiptMaster.getReceiptModeDetail() != null 
        		&& serviceReceiptMaster.getReceiptModeDetail().get(0) != null
        		&& serviceReceiptMaster.getReceiptModeDetail().get(0).getCbBankid() != null) {
			// #134943-to get newly added bank name also
			BankMasterDTO dto = new BankMasterDTO();
			
			LOGGER.info("CB Bank ID:" +  serviceReceiptMaster.getReceiptModeDetail().get(0).getCbBankid());
			
			dto.setBankId(serviceReceiptMaster.getReceiptModeDetail().get(0).getCbBankid());
			dto = bankMasterService.getDetailsUsingBankId(dto);
			if(null != dto)
			{
				if (StringUtils.isNotEmpty(dto.getBank()) && StringUtils.isNotEmpty(dto.getBranch())) {
					final String bankName = dto.getBank() + " :: " + dto.getBranch();
					printDTO.setBankName(bankName);
				}
			}
			else
			{
				LOGGER.info("BankMasterDTO is null");
			}
        }

        printDTO.setReferenceNo(serviceReceiptMaster.getRmLoiNo());
        
        if (Utility.isEnvPrefixAvailable(org, MainetConstants.ENV_TSCL) && offline.getDeptCode() !=null && !offline.getDeptCode().isEmpty()&& offline.getDeptCode().equals("RL") ) {
            printDTO.setDate(Utility.dateToString(offline.getManualReeiptDate()));
            }else {
            	 printDTO.setDate(UtilityService.convertDateToDDMMYYYY(new Date()));
            }
        
        
        // #129193 - to get account no on receipt
        if (serviceReceiptMaster.getReceiptModeDetail().get(0).getRdActNo() != null) {
            printDTO.setBankAccountId(serviceReceiptMaster.getReceiptModeDetail().get(0).getRdActNo());
        }
        ChallanReportDTO challanReport = null;
        String taxDesc = null;
        double totalPayable = 0d;
        double totalReceived = 0d;
        double totalPayableArrear = 0d;
        double totalPayableCurrent = 0d;
        double totalReceivedArrear = 0d;
        double totalreceivedCurrent = 0d;
        double earlyPaymentDiscount = 0d;
        Long earlyPaymentTaxId = 0L;
        String lastKey = null;
        if (offline != null) {
            printDTO.setUsageType1_V(offline.getUsageType());
            printDTO.setRebateAmount(offline.getDemandLevelRebate());
            printDTO.setOld_propNo_connNo_V(offline.getReferenceNo());
            printDTO.setPropNo_connNo_estateN_V(offline.getPropNoConnNoEstateNoV());
            if(StringUtils.isNotBlank(offline.getFlatNo())) {
            	StringBuilder propertyNo = new StringBuilder();
            	propertyNo.append(offline.getPropNoConnNoEstateNoV());
            	propertyNo.append(MainetConstants.WHITE_SPACE);
            	propertyNo.append(MainetConstants.WINDOWS_SLASH_WITH_SPACE);
            	propertyNo.append(MainetConstants.WHITE_SPACE);
            	propertyNo.append(offline.getFlatNo());
            	printDTO.setPropNo_connNo_estateN_V(propertyNo.toString());
            	printDTO.setOccupierName(offline.getOccupierName());
            }
            printDTO.setPropNo_connNo_estateNo_L(offline.getPropNoConnNoEstateNoL());
            printDTO.setBillDetails(offline.getBillDetails());
            if (StringUtils.isNotBlank(deptCode) && StringUtils.equals(deptCode, "WT") && serviceCode.equals("BPW")
                    && MapUtils.isNotEmpty(offline.getBillDetails())) {
                TreeMap<String, String> billDetails = new TreeMap<String, String>();
                billDetails.putAll(offline.getBillDetails());
                lastKey = billDetails.lastKey();
                String lastValue = billDetails.get(lastKey);
                Map<String, String> lastBillDetails = new LinkedHashMap<>();
                lastBillDetails.put(lastKey, lastValue);
                printDTO.setBillDetails(lastBillDetails);
            } else if (receiptForSkdcl != null
                    && StringUtils.equals(MainetConstants.FlagY, receiptForSkdcl.getOtherField())
                    && MapUtils.isNotEmpty(offline.getBillDetails())) {
                TreeMap<String, String> billDetails = new TreeMap<String, String>();
                billDetails.putAll(offline.getBillDetails());
                lastKey = billDetails.lastKey();
            }
            printDTO.setPdRv(offline.getPdRv());
            printDTO.setPlotNo(offline.getPlotNo());
            if ((MainetConstants.CHALLAN_RECEIPT_TYPE.MIXED.equals(offline.getChallanServiceType())
                    || MainetConstants.CHALLAN_RECEIPT_TYPE.REVENUE_BASED.equals(offline.getChallanServiceType()))
                    && offline.getPrintDtoList() != null && !offline.getPrintDtoList().isEmpty()) {

                List<String> taxesNotAppForRec = Arrays.asList(PrefixConstants.TAX_CATEGORY.REBATE,
                        PrefixConstants.TAX_CATEGORY.EXEMPTION, PrefixConstants.TAX_CATEGORY.ADVANCE);

                Map<String, String> billNoWiseDate = new LinkedHashMap<>();

                for (final BillReceiptPrintingDTO billDto : offline.getPrintDtoList()) {
                		  TbTaxMas taxMaster = tbTaxMasService.findById(billDto.getTaxId(), offline.getOrgId());
                          LookUp earlyPaymentLookUp = CommonMasterUtility.getHierarchicalLookUp(taxMaster.getTaxCategory2(),
                                  taxMaster.getOrgid());
                          if (earlyPaymentLookUp != null && !StringUtils.equals(earlyPaymentLookUp.getLookUpCode(), "EPD")) {
                              if (billDto.getTaxCategoryCode() == null
                                      || !taxesNotAppForRec.contains(billDto.getTaxCategoryCode())) {
                                  challanReport = taxdto.get(billDto.getTaxId());
                                  if (challanReport == null) {
                                      challanReport = new ChallanReportDTO();
                                  }
                                  if (billDto.getTotalDetAmount() != null) {
                                      if (billDto.getArrearAmount() != null) {
                                          if (roundOffNotReq != null && StringUtils.isNotBlank(roundOffNotReq.getOtherField())
                                                  && StringUtils.equals(roundOffNotReq.getOtherField(),
                                                          MainetConstants.FlagY)) {
                                              challanReport.setAmountPayableArrear(
                                                      challanReport.getAmountPayableArrear() + billDto.getTotalDetAmount());
                                          } else {
                                              challanReport.setAmountPayableArrear(Math.round(
                                                      challanReport.getAmountPayableArrear() + billDto.getTotalDetAmount()));
                                          }
                                          totalPayableArrear += billDto.getTotalDetAmount();
                                          challanReport.setAmountPayablArrearCurr(
                                                  challanReport.getAmountPayablArrearCurr() + billDto.getTotalDetAmount());
                                          challanReport.setDisplaySeq(taxMaster.getTaxDisplaySeq());
                                      } else {
                                          if (roundOffNotReq != null && StringUtils.isNotBlank(roundOffNotReq.getOtherField())
                                                  && StringUtils.equals(roundOffNotReq.getOtherField(),
                                                          MainetConstants.FlagY)) {
                                              challanReport.setAmountPayableCurrent(
                                                      challanReport.getAmountPayableCurrent() + billDto.getTotalDetAmount());
                                          } else {
                                              challanReport.setAmountPayableCurrent(Math.round(
                                                      challanReport.getAmountPayableCurrent() + billDto.getTotalDetAmount()));
                                          }
                                          totalPayableCurrent += billDto.getTotalDetAmount();
                                          challanReport.setAmountPayablArrearCurr(
                                                  challanReport.getAmountPayablArrearCurr() + billDto.getTotalDetAmount());
                                      }
                                      totalPayable += billDto.getTotalDetAmount();
                                      challanReport.setAmountPayable(
                                              challanReport.getAmountPayable() + billDto.getTotalDetAmount());
                                      challanReport.setDisplaySeq(taxMaster.getTaxDisplaySeq());
                                  } else {
                                      challanReport.setAmountPayable(0d);
                                  }
                                  if (billDto.getArrearAmount() != null) {
                                      if (roundOffNotReq != null && StringUtils.isNotBlank(roundOffNotReq.getOtherField())
                                              && StringUtils.equals(roundOffNotReq.getOtherField(), MainetConstants.FlagY)) {
                                          challanReport.setAmountReceivedArrear(
                                                  challanReport.getAmountReceivedArrear() + billDto.getTaxAmount());
                                      } else {
                                          challanReport.setAmountReceivedArrear(Math
                                                  .round(challanReport.getAmountReceivedArrear() + billDto.getTaxAmount()));
                                      }

                                      totalReceivedArrear += billDto.getTaxAmount();
                                      challanReport.setAmountReceivedArrearCurr(
                                              challanReport.getAmountReceivedArrearCurr() + billDto.getTaxAmount());
                                      challanReport.setDisplaySeq(taxMaster.getTaxDisplaySeq());
                                  } else {
                                      if (roundOffNotReq != null && StringUtils.isNotBlank(roundOffNotReq.getOtherField())
                                              && StringUtils.equals(roundOffNotReq.getOtherField(), MainetConstants.FlagY)) {
                                          challanReport.setAmountReceivedCurrent(
                                                  challanReport.getAmountReceivedCurrent() + billDto.getTaxAmount());
                                      } else {
                                          challanReport.setAmountReceivedCurrent(Math
                                                  .round(challanReport.getAmountReceivedCurrent() + billDto.getTaxAmount()));
                                      }
                                      totalreceivedCurrent += billDto.getTaxAmount();
                                      challanReport.setAmountReceivedArrearCurr(
                                              challanReport.getAmountReceivedArrearCurr() + billDto.getTaxAmount());
                                      challanReport.setDisplaySeq(taxMaster.getTaxDisplaySeq());
                                  }
                                  challanReport.setAmountReceived(challanReport.getAmountReceived() + billDto.getTaxAmount());
                                  totalReceived += billDto.getTaxAmount();
                                  if (offline.getLangId() > 0 && offline.getLangId() == MainetConstants.REGIONAL_LANGUAGE_ID) {
                                      taxDesc = tbTaxMasService.findTaxDescRegByTaxIdAndOrgId(billDto.getTaxId(),
                                              offline.getOrgId());
                                  } else
                                      taxDesc = tbTaxMasService.findTaxDescByTaxIdAndOrgId(billDto.getTaxId(), offline.getOrgId());
                                  challanReport.setDetails(taxDesc);
                                  taxdto.put(billDto.getTaxId(), challanReport);
                              }
                              if (billDto.getTaxCategoryCode() != null
                                      && billDto.getTaxCategoryCode().equals(PrefixConstants.TAX_CATEGORY.ADVANCE)) {
                                  if (billDto.getTaxAmount() > 1) {
                                      printDTO.setAdvOrExcessAmt(
                                              Math.round(printDTO.getAdvOrExcessAmt() + billDto.getTaxAmount()));
                                      if (receiptForSkdcl != null
                                              && StringUtils.equals(MainetConstants.FlagY, receiptForSkdcl.getOtherField())) {
                                          challanReport = new ChallanReportDTO();
                                          if (offline.getLangId() > 0 && offline.getLangId() == MainetConstants.REGIONAL_LANGUAGE_ID) {
                                              taxDesc = tbTaxMasService.findTaxDescRegByTaxIdAndOrgId(billDto.getTaxId(),
                                                      offline.getOrgId());
                                          } else
                                              taxDesc = tbTaxMasService.findTaxDescByTaxIdAndOrgId(billDto.getTaxId(), offline.getOrgId());
                                          challanReport.setDetails(taxDesc);
                                          challanReport.setAmountPayablArrearCurr(0);
                                          challanReport.setAmountReceivedArrearCurr(billDto.getTaxAmount());
                                          challanReport.setDisplaySeq(taxMaster.getTaxDisplaySeq());
                                          taxdto.put(billDto.getTaxId(), challanReport);
                                      }
                                  }
                              }
                              if (billDto.getTaxCategoryCode() != null
                                      && (billDto.getTaxCategoryCode().equals(PrefixConstants.TAX_CATEGORY.REBATE)
                                              || billDto.getTaxCategoryCode().equals(PrefixConstants.TAX_CATEGORY.EXEMPTION))
                                      && billDto.getTotalDetAmount() != null) {
                                  printDTO.setRebateAmount(printDTO.getRebateAmount() + billDto.getTotalDetAmount());
                              }
                          }
                          if (earlyPaymentLookUp != null && StringUtils.equals(earlyPaymentLookUp.getLookUpCode(), "EPD")) {
                              earlyPaymentDiscount = earlyPaymentDiscount + billDto.getTaxAmount();
                              earlyPaymentTaxId = billDto.getTaxId();
                          }
                          
					if (Utility.isEnvPrefixAvailable(org, MainetConstants.ENV_SKDCL) && billDto.getYearId() != null && billDto.getYearId() > 0
							&& billDto.getBillFromDate() != null && billDto.getBillToDate() != null) {
	                	  
		            	  StringBuilder billPeriod = new StringBuilder();
		
		                  String fromDate = new SimpleDateFormat(MainetConstants.DATE_FORMAT).format(billDto.getBillFromDate());
		                  String toDate = new SimpleDateFormat(MainetConstants.DATE_FORMAT).format(billDto.getBillToDate());
		                  billPeriod.append(fromDate);
		                  billPeriod.append(" ");
		                  billPeriod.append("to");
		                  billPeriod.append(" ");
		                  billPeriod.append(toDate);
		                  if(MainetConstants.WATER_DEPT.equals(deptCode) || MainetConstants.DEPT_SHORT_NAME.PROPERTY.equals(deptCode)) {
			            	  billPeriodDetails.put(String.valueOf(billDto.getBmIdNo()), billPeriod.toString());
			            	  billNoWiseDate.put(billDto.getBmIdNo(), new SimpleDateFormat(MainetConstants.DATE_FORMAT).format(billDto.getBillDate()));

		                  }else {
			            	  billPeriodDetails.put(String.valueOf(billDto.getYearId()), billPeriod.toString());
		                  }
						}
                	}
                if(Utility.isEnvPrefixAvailable(org, MainetConstants.ENV_SKDCL) && MainetConstants.WATER_DEPT.equals(deptCode)) {
                	 printDTO.setBillDetails(sortMaps(billNoWiseDate));
                     printDTO.setBillYearDetails(sortBillPeriodWiseData(billNoWiseDate, billPeriodDetails, offline.getPrintDtoList()));
                }else if(Utility.isEnvPrefixAvailable(org, MainetConstants.ENV_SKDCL)){
                	  printDTO.setBillDetails(billNoWiseDate);
	            	  printDTO.setBillYearDetails(billPeriodDetails);
                }
               
                if (earlyPaymentDiscount > 0 && receiptForSkdcl != null
                        && StringUtils.equals(MainetConstants.FlagY, receiptForSkdcl.getOtherField())) {
                    challanReport = new ChallanReportDTO();
                    challanReport
                            .setDetails(ApplicationSession.getInstance().getMessage("property.bill.receipt.rebate.note")
                                    + MainetConstants.WHITE_SPACE + lastKey + MainetConstants.WHITE_SPACE
                                    + printDTO.getFinYear() + MainetConstants.WHITE_SPACE + MainetConstants.WHITE_SPACE
                                    + Math.ceil(earlyPaymentDiscount) + MainetConstants.WHITE_SPACE + "/-");

                    taxdto.put(earlyPaymentTaxId, challanReport);

                }
            } else {
                for (final TbSrcptFeesDetEntity feedet : serviceReceiptMaster.getReceiptFeeDetail()) {
                	TbTaxMas taxMaster = tbTaxMasService.findById(feedet.getTaxId(), offline.getOrgId());
                    challanReport = taxdto.get(feedet.getTaxId());
                    if (challanReport == null) {
                        challanReport = new ChallanReportDTO();
                    }
                    if (feedet.getBalAmount() != null) {
                        challanReport.setAmountPayable(Math.round(
                                challanReport.getAmountPayable() + Double.valueOf(feedet.getBalAmount().toString())));
                        totalPayable += feedet.getBalAmount().doubleValue();
                    } else {
                        challanReport.setAmountPayable(0d);
                    }
                    challanReport.setAmountReceived(Math.round(
                            challanReport.getAmountReceived() + Double.valueOf(feedet.getRfFeeamount().toString())));
                    totalReceived += feedet.getRfFeeamount().doubleValue();
                    // #130728 -> tax desc in reg
                    if (offline.getLangId() > 0 && offline.getLangId() == MainetConstants.REGIONAL_LANGUAGE_ID) {
                        taxDesc = tbTaxMasService.findTaxDescRegByTaxIdAndOrgId(feedet.getTaxId(),
                                offline.getOrgId());
                    } else
                        taxDesc = tbTaxMasService.findTaxDescByTaxIdAndOrgId(feedet.getTaxId(), offline.getOrgId());
                    challanReport.setDetails(taxDesc);
                    challanReport.setTaxId(feedet.getTaxId());
                    challanReport.setDisplaySeq(taxMaster.getTaxDisplaySeq());
                    taxdto.put(feedet.getTaxId(), challanReport);
                }
                
                if (Utility.isEnvPrefixAvailable(org, MainetConstants.ENV_ASCL)
        				&& StringUtils.equals(offline.getServiceCode(), "MUT")) {
                	List<TbServiceReceiptMasEntity> receiptDetailsByAppId = receiptRepository.getReceiptDetailsByAppIdList(offline.getApplNo(), org.getOrgid());
            		if(CollectionUtils.isNotEmpty(receiptDetailsByAppId)) {
            			for (TbServiceReceiptMasEntity receiptMas : receiptDetailsByAppId) {
            			for (TbSrcptFeesDetEntity receiptDet : receiptMas.getReceiptFeeDetail()) {
                			Long taxDescId = tbTaxMasJpaRepository.getTaxDescIdByTaxId(receiptDet.getTaxId());
                			LookUp lookUp = CommonMasterUtility.getNonHierarchicalLookUpObject(taxDescId, UserSession.getCurrent().getOrganisation());
                			if(lookUp != null && StringUtils.equals(lookUp.getLookUpCode(), "APC")) {
                                 printDTO.setApplicationFee(receiptDet.getRfFeeamount().doubleValue());
                			}
        				}
            		}
            		}
                }
            }
        }
        if (serviceReceiptMaster.getRefId() != null) {
            TbServiceReceiptMasEntity receiptMaster = iReceiptEntryService
                    .findByRmRcptidAndOrgId(serviceReceiptMaster.getRefId(), org.getOrgid());
            for (TbSrcptFeesDetEntity rebateReceipt : receiptMaster.getReceiptFeeDetail()) {
            		 TbTaxMas taxMaster = tbTaxMasService.findById(rebateReceipt.getTaxId(), offline.getOrgId());
                     LookUp earlyPaymentLookUp = CommonMasterUtility.getHierarchicalLookUp(taxMaster.getTaxCategory2(),
                             taxMaster.getOrgid());
                     if (earlyPaymentLookUp != null && !StringUtils.equals(earlyPaymentLookUp.getLookUpCode(), "EPD")) {
                         challanReport = taxdto.get(rebateReceipt.getTaxId());
                         if (challanReport == null) {
                             challanReport = new ChallanReportDTO();
                         }
                         challanReport.setAmountPayable(0d);
                         challanReport.setAmountReceived(Math.round(challanReport.getAmountReceived()
                                 + Double.valueOf(rebateReceipt.getRfFeeamount().toString())));
                         totalReceived += rebateReceipt.getRfFeeamount().doubleValue();
                         taxDesc = tbTaxMasService.findTaxDescByTaxIdAndOrgId(rebateReceipt.getTaxId(), offline.getOrgId());
                         challanReport.setDetails(taxDesc);
                         taxdto.put(rebateReceipt.getTaxId(), challanReport);
                     }
            	}
        	}
        if(Utility.isEnvPrefixAvailable(org, MainetConstants.ENV_SKDCL) && 
        		(MainetConstants.Dms.COMMON_DEPT.equals(deptCode)) || (MainetConstants.WATER_DEPT.equals(deptCode))){
            	mapTaxesByName(printDTO, taxdto, org);
        }else {
            printDTO.getPaymentList().addAll(taxdto.values().stream().collect(Collectors.toList()));
        }
        printDTO.setTotalAmountPayable(Math.round(totalPayable));
        printDTO.setTotalReceivedAmount(Math.round(totalReceived));
        printDTO.setTotalPayableArrear(Math.round(totalPayableArrear));
        printDTO.setTotalPayableCurrent(Math.round(totalPayableCurrent));
        printDTO.setTotalReceivedArrear(Math.round(totalReceivedArrear));
        printDTO.setTotalreceivedCurrent(Math.round(totalreceivedCurrent));
        printDTO.setEarlyPaymentDiscount(Math.round(earlyPaymentDiscount));
        printDTO.setAmountPayable(printDTO.getTotalAmountPayable() + printDTO.getRebateAmount());

        final String amountInWords = Utility.convertBigNumberToWord(serviceReceiptMaster.getRmAmount());
        printDTO.setAmountInWords(amountInWords);
        //D#141370
        if (Utility.isEnvPrefixAvailable(org, MainetConstants.ENV_SKDCL)) {
        	if(StringUtils.isNotEmpty(serviceCode)&&(StringUtils.isNotBlank(deptCode)&& deptCode.equals(MainetConstants.TradeLicense.MARKET_LICENSE))) {
        	printDTO.setAccountHead(ApplicationSession.getInstance().getMessage("skdcl.receipt.accounthead"+"."+serviceCode));
        	//Defect #141737
				Map<Long, ChallanReportDTO> taxdtos = setTaxdataInsequenceOrder(taxdto, offline.getOrgId(),
						offline.getServiceId());
				printDTO.setPaymentList(new ArrayList<ChallanReportDTO>());
				printDTO.getPaymentList().addAll(taxdtos.values().stream().collect(Collectors.toList()));
            
        	}
        	if(MainetConstants.ServiceShortCode.DUPLICATE_BILL_PRINT.equals(serviceCode)) {
        		printDTO.setAmount(Double.valueOf(serviceReceiptMaster.getRmAmount().toString()));
    	        printDTO.setAmountPayable(printDTO.getAmount());
        	    printDTO.setTotalReceivedAmount(printDTO.getAmount());
    	        printDTO.setTotalAmountPayable(printDTO.getAmount());
    	        printDTO.setTotalPayableCurrent(printDTO.getAmount());
    	        printDTO.setTotalreceivedCurrent(printDTO.getAmount());
        	}
        	//#149731
        	  if (serviceReceiptMaster.getSmServiceId() != null)
           	   printDTO.setServiceCodeflag(MainetConstants.FlagN);
              else
           	   printDTO.setServiceCodeflag(MainetConstants.FlagY);
        }
       //#148057- to get counter no. and center no. details with count
        if (StringUtils.isNotEmpty(serviceReceiptMaster.getCfcColCenterNo()))
        printDTO.setCfcColCenterNo(serviceReceiptMaster.getCfcColCenterNo());
        if (StringUtils.isNotEmpty(serviceReceiptMaster.getCfcColCounterNo()))
        printDTO.setCfcColCounterNo(serviceReceiptMaster.getCfcColCounterNo());
    return printDTO;
    }


	private void mapTaxesByName(ChallanReceiptPrintDTO printDTO, Map<Long, ChallanReportDTO> taxdto, Organisation org) {
        Map<String, ChallanReportDTO> taxesByTaxName = new HashMap<>();

		for (Entry<Long, ChallanReportDTO> tax : taxdto.entrySet()) {
			String taxDesc = tbTaxMasService.findTaxDescByTaxIdAndOrgId(tax.getKey(), org.getOrgid());
			if(!taxesByTaxName.containsKey(taxDesc)) {
				taxesByTaxName.put(taxDesc, tax.getValue());
			}else {
				ChallanReportDTO challanReportDTO = taxesByTaxName.get(taxDesc);
				challanReportDTO.setAmountPayable(Math.round(challanReportDTO.getAmountPayable() + tax.getValue().getAmountPayable()));
				challanReportDTO.setAmountPayableCurrent(Math.round(challanReportDTO.getAmountPayablArrearCurr() + 
						tax.getValue().getAmountPayableCurrent()));
				challanReportDTO.setAmountPayableArrear(Math.round(challanReportDTO.getAmountPayableArrear() +
						tax.getValue().getAmountPayableArrear()));
				challanReportDTO.setAmountPayablArrearCurr(Math.round(challanReportDTO.getAmountPayablArrearCurr() +
						tax.getValue().getAmountPayablArrearCurr()));
				challanReportDTO.setAmountReceivedArrearCurr(challanReportDTO.getAmountReceivedArrearCurr() +
						tax.getValue().getAmountReceivedArrearCurr());
				taxesByTaxName.put(taxDesc, challanReportDTO);

			}
		}

        printDTO.getPaymentList().addAll(taxesByTaxName.values().stream().collect(Collectors.toList()));

	}

	/*
     * (non-Javadoc)
     * @see com.abm.mainetservice.web.cfc.challan.service.IChallanService#
     * savePayAtUlbCounter(com.abm.mainetservice.rest.common.bean. CommonChallanDTO)
     */

    // this method is called all over the counter payment via CFC cash , card (POS)
    // , cheque and other instrument
    @Override
    @Transactional
    public ChallanReceiptPrintDTO savePayAtUlbCounter(final CommonChallanDTO offline, final String serviceName) {
        ChallanReceiptPrintDTO receiptDto = updateDataAfterPayment(offline);

        return receiptDto;
    }

    // This method is called from online transaction after sucessful method
    // from paymentservice implementaion
    @SuppressWarnings("deprecation")
	@Override
    @Transactional
    public ChallanReceiptPrintDTO updateDataAfterPayment(final CommonChallanDTO offline) {
        TbServiceReceiptMasEntity receiptMaster = new TbServiceReceiptMasEntity();
        Organisation org = new Organisation();
        org.setOrgid(offline.getOrgId());
        //Defect #137746
      //User Story #147721
      		try {
      			if(!offline.isPosPayApplicable()) {
      			LookUp lookUp = CommonMasterUtility.getNonHierarchicalLookUpObject(offline.getPayModeIn());
      			if (lookUp != null && lookUp.getLookUpCode().equals("POS")) {
      				CommonChallanDTO dto=callPushToPayApiForPayment(offline);
      				if(dto!=null && dto.getPushToPayErrMsg()!=null) {
      					ChallanReceiptPrintDTO challDto=new ChallanReceiptPrintDTO();
      					challDto.setPushToPayErrMsg(dto.getPushToPayErrMsg());
      					return challDto;
      				}
      			}}
      		} catch (Exception e) {
      			// TODO: handle exception
      		}
        try {
        	List<DocumentDetailsVO> docList=prepareFileUpload(offline.getPostalCardDocList());
        	offline.setPostalCardDocList(docList);
        }
        catch (Exception e) {
        	LOGGER.error("Exception has been occurred at the time of Postal document upload", e);
		}
        //List<CommonChallanPayModeDTO> multiModeList = new ArrayList<>(offline.getMultiModeList());

        List<BillReceiptPostingDTO> result = null;
        if (MainetConstants.CHALLAN_RECEIPT_TYPE.NON_REVENUE_BASED.equals(offline.getChallanServiceType())) {
            receiptMaster = serviceReceipt(offline, org, result);
        }
        // removed mixed type check
        else if ((MainetConstants.CHALLAN_RECEIPT_TYPE.REVENUE_BASED.equals(offline.getChallanServiceType()))) {
            receiptMaster = billAndMixedReceipt(offline, receiptMaster, org);
        } else if (MainetConstants.CHALLAN_RECEIPT_TYPE.OBJECTION.equals(offline.getChallanServiceType())) {
            receiptMaster = serviceReceiptForObjection(offline, org, result);
            // objection event trigger
        }

        // post payment sucess implemttaion has to be written by all individual module
        // where after payment cascading impact needs
        // to be provide in module
        // postPaymentSuccess(offline,receiptMaster);
        uploadManaulReceiptDoc(offline, receiptMaster);
        //Defect #137746
        uploadPostalCardtDoc(offline, receiptMaster);

        if(Utility.isEnvPrefixAvailable(org, MainetConstants.ENV_SKDCL)) {
        	String serviceShortCode = serviceMaster.fetchServiceShortCode(offline.getServiceId(), 
        			org.getOrgid());
			if(serviceShortCode.equals(MainetConstants.ServiceShortCode.DUPLICATE_BILL_PRINT)) {
				BigDecimal rmAmount = new BigDecimal(offline.getAmountToPay());
				receiptMaster.setRmAmount(rmAmount);
				receiptMaster.setAdditionalRefNo(offline.getReferenceNo());;
			}

        }
        // set print Dto and make entry in duplicate receipt
        ChallanReceiptPrintDTO receiptDto = setReceiptDtoAndSaveDuplicateService(receiptMaster, offline);

        // for multimode report dto set for-SKDCL
		/*
		 * if (Utility.isEnvPrefixAvailable(UserSession.getCurrent().getOrganisation(),
		 * "RPF")) { SetDtoForMultiModeReport(org, multiModeList, receiptDto); }
		 */
        sendSMSAndEmail(offline, offline.getServiceName(), receiptDto);
        return receiptDto;
    }

    /**
     * @param org
     * @param multiModeList
     * @param receiptDto
     */
    private void SetDtoForMultiModeReport(Organisation org, List<CommonChallanPayModeDTO> multiModeList,
            ChallanReceiptPrintDTO receiptDto) {
        List<CommonChallanPayModeDTO> multiPayModeList = new ArrayList<>();
        multiModeList.forEach(multiModeDto -> {
            CommonChallanPayModeDTO commonChallanPayModeDTO = null;
            if (multiModeDto != null) {
                commonChallanPayModeDTO = new CommonChallanPayModeDTO();
                BeanUtils.copyProperties(multiModeDto, commonChallanPayModeDTO);
                if (multiModeDto.getPayModeIn() != null) {
                    commonChallanPayModeDTO.setPayModeDesc(CommonMasterUtility
                            .getNonHierarchicalLookUpObject(multiModeDto.getPayModeIn(), org).getLookUpDesc());
                }
                if (multiModeDto.getCbBankid() != null) {
                    final String rdDrawnon = ApplicationSession.getInstance().getCustomerBanks()
                            .get(multiModeDto.getCbBankid());
                    commonChallanPayModeDTO.setBmDrawOn(rdDrawnon);
                }
                if (multiModeDto.getBmChqDDDate() != null) {
                    commonChallanPayModeDTO
                            .setBmChqDDDateTemp(UtilityService.convertDateToDDMMYYYY(multiModeDto.getBmChqDDDate()));
                }
                final String bankName = ApplicationSession.getInstance().getBanks().stream()
                        .filter(x -> multiModeDto.getCbBankid() != null
                                && multiModeDto.getCbBankid().equals(x.getBankId()))
                        .map(BankMasterEntity::getBank).findAny().orElse("");
                if (bankName != null && !bankName.isEmpty()) {
                    commonChallanPayModeDTO.setBankName(bankName);
                }
                multiPayModeList.add(commonChallanPayModeDTO);
            }
        });
        receiptDto.setMultiPayModeList(multiPayModeList);
    }

    @Override
    public ChallanReceiptPrintDTO setReceiptDtoAndSaveDuplicateService(TbServiceReceiptMasEntity receiptMaster,
            CommonChallanDTO offline) {
        final ChallanReceiptPrintDTO receiptDto = setValuesAndPrintReport(receiptMaster, offline);
        if(StringUtils.isNotBlank(offline.getNarration())) {
        	receiptDto.setNarration(offline.getNarration());
        }
        new Thread(() -> iDuplicateReceiptService.saveDuplicateReceipt(receiptDto, receiptMaster, offline)).start();
        return receiptDto;
    }

    private TbServiceReceiptMasEntity serviceReceiptForObjection(final CommonChallanDTO offline, Organisation org,
            List<BillReceiptPostingDTO> result) {
        TbServiceReceiptMasEntity receiptMaster;

        updateOnlinePaymentCFCStatus(offline.getApplNo(), offline.getOrgId());
        final LookUp serviceReceipt = CommonMasterUtility.getValueFromPrefixLookUp("M", "RV", org);
        offline.setPaymentCategory(serviceReceipt.getLookUpCode());
        receiptMaster = iReceiptEntryService.insertInReceiptMaster(offline, result);
        WardZoneBlockDTO dwzDTO = new WardZoneBlockDTO();
        dwzDTO.setAreaDivision1(receiptMaster.getCoddwzId1());
        dwzDTO.setAreaDivision2(receiptMaster.getCoddwzId2());
        dwzDTO.setAreaDivision3(receiptMaster.getCoddwzId3());
        dwzDTO.setAreaDivision4(receiptMaster.getCoddwzId4());
        dwzDTO.setAreaDivision5(receiptMaster.getCoddwzId5());
        // in case of objection signal workflow initiate
        workflowRequestService.signalWorkFlow(offline);
        return receiptMaster;
    }

    private TbServiceReceiptMasEntity serviceReceipt(final CommonChallanDTO offline, Organisation org,
            List<BillReceiptPostingDTO> result) {
        TbServiceReceiptMasEntity receiptMaster;
        if (offline.getLoiNo() != null && !offline.getLoiNo().isEmpty()) {
            iTbLoiMasService.updateLoiPaidByLoiNo(offline.getLoiNo(), offline.getOrgId());
        }
        if (offline.getApplNo() != null)
            updateOnlinePaymentCFCStatus(offline.getApplNo(), offline.getOrgId());
        final LookUp serviceReceipt = CommonMasterUtility.getValueFromPrefixLookUp("M", "RV", org);
        offline.setPaymentCategory(serviceReceipt.getLookUpCode());
        // This will call from Mobile #116953
        if (MainetConstants.DEPT_SHORT_NAME.ADH.equals(departmentService.getDeptCode(offline.getDeptId()))
                && offline.getOnlineOfflineCheck() == null) {
            result = updateBillMasterData(offline.getUniquePrimaryId(), offline.getOrgId(), offline.getDeptId(),
                    Double.valueOf(offline.getAmountToPay()), offline.getUserId(), offline.getLgIpMac(),null,null,null);
        }
        //Defect #156332
        if(offline.getLoggedLocId()==null) {
        	if(UserSession.getCurrent()!=null && UserSession.getCurrent().getLoggedLocId()!=null)
        		offline.setLoggedLocId(UserSession.getCurrent().getLoggedLocId());
        }
        // this will move to post payment method
        receiptMaster = iReceiptEntryService.insertInReceiptMaster(offline, result);

        WardZoneBlockDTO dwzDTO = new WardZoneBlockDTO();
        dwzDTO.setAreaDivision1(receiptMaster.getCoddwzId1());
        dwzDTO.setAreaDivision2(receiptMaster.getCoddwzId2());
        dwzDTO.setAreaDivision3(receiptMaster.getCoddwzId3());
        dwzDTO.setAreaDivision4(receiptMaster.getCoddwzId4());
        dwzDTO.setAreaDivision5(receiptMaster.getCoddwzId5());
        
        if(offline.getDeptId() != null && offline.getDeptId() > 0) {
        	String deptCode = departmentService.getDeptCode(offline.getDeptId());
			if (StringUtils.equals(MainetConstants.DEPT_SHORT_NAME.PROPERTY, deptCode)
					&& Utility.isEnvPrefixAvailable(org, MainetConstants.ENV_ASCL) && Utility.isEnvPrefixAvailable(org, "PWZ")) {
				if(offline.getAssParshadWard1() != null) {
					dwzDTO.setAreaDivision1(offline.getAssParshadWard1());	
				}
				if(offline.getAssParshadWard2() != null) {
					dwzDTO.setAreaDivision2(offline.getAssParshadWard2());	
				}
				if(offline.getAssParshadWard3() != null) {
					dwzDTO.setAreaDivision3(offline.getAssParshadWard3());	
				}
				if(offline.getAssParshadWard4() != null) {
					dwzDTO.setAreaDivision4(offline.getAssParshadWard4());	
				}
				if(offline.getAssParshadWard5() != null) {
					dwzDTO.setAreaDivision5(offline.getAssParshadWard5());	
				}
			}
        }

        // this works needs to be done in postPaymentSuccess(offline);
        String serviceCode = serviceMaster.fetchServiceShortCode(offline.getServiceId(), 
     			org.getOrgid());
        if(Utility.isEnvPrefixAvailable(org, MainetConstants.ENV_SKDCL) && 
        		MainetConstants.ServiceShortCode.DUPLICATE_BILL_PRINT.equals(serviceCode)){
        	// do not call workflow process
        }else {
	        if (StringUtils.isEmpty(offline.getWorkflowEnable())) { // User Story #105145
	        	// workflow initiate
	            workflowRequestService.initiateAndUpdateWorkFlowProcess(offline, dwzDTO); // in case of objection signal
	        } 
        }
        return receiptMaster;
    }

    private TbServiceReceiptMasEntity billAndMixedReceipt(final CommonChallanDTO offline,
            TbServiceReceiptMasEntity receiptMaster, Organisation org) {
        List<BillReceiptPostingDTO> result;
        double totalPayAmount = Double.valueOf(offline.getAmountToPay());
        result = updateBillMasterData(offline.getUniquePrimaryId(), offline.getOrgId(), offline.getDeptId(),
                totalPayAmount, offline.getUserId(), offline.getLgIpMac(), offline.getManualReeiptDate(),
                offline.getFlatNo(),offline.getParentPropNo());
        if (result != null && !result.isEmpty()) {
            LookUp billReceipt = null;
            double advanceTaxPayment = 0;
            final Long advanceTaxId = billMasterCommonService.getAdvanceTaxId(offline.getOrgId(), null,
                    offline.getDeptId());
            if (advanceTaxId != null) {
                advanceTaxPayment = result.stream().filter(bill -> bill.getTaxId().equals(advanceTaxId))
                        .collect(Collectors.toList()).stream().mapToDouble(i -> i.getTaxAmount()).sum();
            }
            if (advanceTaxPayment > 0d && advanceTaxPayment == totalPayAmount) {
                billReceipt = CommonMasterUtility.getValueFromPrefixLookUp("A", "RV", org);
            } else {
                billReceipt = CommonMasterUtility.getValueFromPrefixLookUp("R", "RV", org);
            }
            offline.setPaymentCategory(billReceipt.getLookUpCode());
            receiptMaster = iReceiptEntryService.insertInReceiptMaster(offline, result);
            if (offline.getApplNo() != null) {
                updateOnlinePaymentCFCStatus(offline.getApplNo(), offline.getOrgId());
            }
            final Long receiptId = receiptMaster.getRmRcptid();
            if (advanceTaxPayment > 0d) {
                saveAdvancePayment(offline.getOrgId(), offline.getUniquePrimaryId(), receiptId, offline.getDeptId(),
                        offline.getUserId(), advanceTaxPayment);
            }
        }

        // this is specially written or property module and this code should be written
        // in property module before bill payment at
        // the time of assessment

        // this to be moved after PostPaymentSucessful
        // if
        // (MainetConstants.CHALLAN_RECEIPT_TYPE.MIXED.equals(offline.getChallanServiceType()))
        // {
        // updateOnlinePaymentCFCStatus(offline.getApplNo(), offline.getOrgId());
        /*
         * WardZoneBlockDTO dwzDTO = new WardZoneBlockDTO(); dwzDTO.setAreaDivision1(receiptMaster.getCoddwzId1());
         * dwzDTO.setAreaDivision2(receiptMaster.getCoddwzId2()); dwzDTO.setAreaDivision3(receiptMaster.getCoddwzId3());
         * dwzDTO.setAreaDivision4(receiptMaster.getCoddwzId4()); dwzDTO.setAreaDivision5(receiptMaster.getCoddwzId5());
         * initiateAndUpdateWorkFlowProcess(offline, dwzDTO);
         */
        // }
        return receiptMaster;
    }

    private void uploadManaulReceiptDoc(final CommonChallanDTO offline, TbServiceReceiptMasEntity receiptMaster) {
        if (offline.getDocumentList() != null && !offline.getDocumentList().isEmpty()) {
            RequestDTO dto = new RequestDTO();
            dto.setDeptId(offline.getDeptId());
            dto.setServiceId(offline.getServiceId());
            dto.setReferenceId(Long.valueOf(receiptMaster.getRmRcptid()).toString());
            dto.setOrgId(offline.getOrgId());
            dto.setUserId(offline.getUserId());
            dto.setLangId(Long.valueOf(offline.getLangId()));
            dto.setApplicationId(offline.getApplNo());
            fileUpload.doFileUpload(offline.getDocumentList(), dto);
        }
    }
    //Defect #137746
    private void uploadPostalCardtDoc(final CommonChallanDTO offline, TbServiceReceiptMasEntity receiptMaster) {
        if (CollectionUtils.isNotEmpty(offline.getPostalCardDocList())) {
            RequestDTO dto = new RequestDTO();
            dto.setDeptId(offline.getDeptId());
            dto.setServiceId(offline.getServiceId());
            dto.setReferenceId(Long.valueOf(receiptMaster.getRmRcptid()).toString());
            dto.setOrgId(offline.getOrgId());
            dto.setUserId(offline.getUserId());
            dto.setLangId(Long.valueOf(offline.getLangId()));
            dto.setApplicationId(Long.valueOf(receiptMaster.getRmRcptid()));
            fileUpload.doFileUpload(offline.getPostalCardDocList(), dto);
        }
    }

    /**
     * this method is used for send SMS and Email to applicant for service payment
     * 
     * @param receiptMaster
     * @param offline
     * @param serviceName
     */
    private void sendSMSAndEmail(CommonChallanDTO offline, String serviceName, ChallanReceiptPrintDTO receiptDto) {
        String paymentUrl = MainetConstants.SMS_EMAIL_URL.CHALLAN_AT_ULB_COUNTER;
        final SMSAndEmailDTO dto = new SMSAndEmailDTO();
        dto.setAppName(offline.getApplicantName());
        dto.setMobnumber(offline.getMobileNumber());
        dto.setEmail(offline.getEmailId());
        if (offline.getApplNo() != null) {
            dto.setAppNo(offline.getApplNo().toString());
        }
        if (offline.getLoiNo() != null && !offline.getLoiNo().isEmpty()) {
            dto.setLoiNo(offline.getLoiNo());
            paymentUrl = MainetConstants.SMS_EMAIL_URL.LOI_PAYMENT;
        }
        dto.setChallanAmt(offline.getAmountToPay());
        dto.setServName(serviceName);
        // 126164
        dto.setUserId(offline.getUserId());
        Organisation org = iOrganisationService.getOrganisationById(offline.getOrgId());
        org.setOrgid(offline.getOrgId());
        // to sent smsAndEmail in both language English and regional
        int langId;
        if (offline.getLangId() > 0) {
            langId = offline.getLangId();
        } else {
            langId = Utility.getDefaultLanguageId(org);
        }
        if (Utility.isEnvPrefixAvailable(org, "RPF")) {
            if (receiptDto != null && receiptDto.getMultiPayModeList() != null
                    && !receiptDto.getMultiPayModeList().isEmpty()) {
                for (CommonChallanPayModeDTO printDto : receiptDto.getMultiPayModeList()) {
                    dto.setAmount(printDto.getAmount());
                    dto.setDispMode(printDto.getPayModeDesc());
                    if (printDto.getBmChqDDNo() != null) {
                        dto.setP1(printDto.getBmChqDDNo().toString());
                    }
                    dto.setCurrentDate(Utility.dateToString(new Date()));
                    dto.setReferenceNo(receiptDto.getReceiptNo());
                    dto.setConNo(receiptDto.getPropNo_connNo_estateN_V());
                    ismsAndEmailService.sendEmailSMS(MainetConstants.DEPT_SHORT_NAME.CFC_CENTER, paymentUrl,
                            PrefixConstants.SMS_EMAIL_ALERT_TYPE.COMPLETED_MESSAGE, dto, org, langId);
                }
            }
        } else {
            ismsAndEmailService.sendEmailSMS(MainetConstants.DEPT_SHORT_NAME.CFC_CENTER, paymentUrl,
                    PrefixConstants.SMS_EMAIL_ALERT_TYPE.APPROVAL, dto, org, langId);
        }

    }

    @Override
    public boolean saveAdvancePayment(final Long orgId, final String uniquePrimaryId, final Long receiptId, Long deptId,
            Long userId, Double amount) {
        boolean result = false;
        try {
            BillPaymentService dynamicServiceInstance = null;
            String serviceClassName = null;
            final String dept = departmentService.getDeptCode(deptId);
            serviceClassName = messageSource.getMessage(
                    ApplicationSession.getInstance().getMessage("bill.lbl.Bills") + dept, new Object[] {},
                    StringUtils.EMPTY, Locale.ENGLISH);
            dynamicServiceInstance = ApplicationContextProvider.getApplicationContext().getBean(serviceClassName,
                    BillPaymentService.class);
            result = dynamicServiceInstance.saveAdvancePayment(orgId, amount, uniquePrimaryId, userId, receiptId);
        } catch (LinkageError | Exception e) {
            throw new FrameworkException("Exception in UpdateBill for advance payment for department Id :" + deptId, e);
        }
        return result;
    }

    @Transactional
    @Override
    public CommonChallanDTO getBillDetails(CommonChallanDTO commonChallanDTO, String deptCode) {
        CommonChallanDTO result = null;
        try {
            BillDetailsService billDetailsService = null;
            switch (deptCode) {
            case MainetConstants.DEPT_SHORT_NAME.PROPERTY:
                billDetailsService = ApplicationContextProvider.getApplicationContext()
                        .getBean(MainetConstants.PAYMENT_POSTING_API.PROPERTY_BILL_PAYMENT, BillDetailsService.class);
                break;
            case MainetConstants.DEPT_SHORT_NAME.WATER:
                billDetailsService = ApplicationContextProvider.getApplicationContext()
                        .getBean(MainetConstants.PAYMENT_POSTING_API.WATER_BILL_SERVICE, BillDetailsService.class);
                break;

            case MainetConstants.DEPT_SHORT_NAME.RNL:
                billDetailsService = ApplicationContextProvider.getApplicationContext()
                        .getBean(MainetConstants.PAYMENT_POSTING_API.RNL_BILL_PAYMENT, BillDetailsService.class);
                break;

            case MainetConstants.DEPT_SHORT_NAME.RTI:
                billDetailsService = ApplicationContextProvider.getApplicationContext()
                        .getBean(MainetConstants.PAYMENT_POSTING_API.RTI_BILL_PAYMENT, BillDetailsService.class);
                break;

            case MainetConstants.DEPT_SHORT_NAME.ADH:
                billDetailsService = ApplicationContextProvider.getApplicationContext()
                        .getBean(MainetConstants.PAYMENT_POSTING_API.ADH_BILL_PAYMENT, BillDetailsService.class);
                break;

            }

            result = billDetailsService.getBillDetails(commonChallanDTO);
        } catch (LinkageError | Exception e) {
            throw new FrameworkException(
                    "Exception in getBillDetails of challan service for department Id :" + commonChallanDTO.getDeptId(),
                    e);
        }
        return result;
    }
	public List<DocumentDetailsVO> prepareFileUpload(List<DocumentDetailsVO> doc) throws IOException {

		final Map<Long, String> listOfString = new HashMap<>();
		final Map<Long, String> fileName = new HashMap<>();
		if ((FileUploadUtility.getCurrent().getFileMap() != null)
				&& !FileUploadUtility.getCurrent().getFileMap().isEmpty()) {
			for (final Map.Entry<Long, Set<File>> entry : FileUploadUtility.getCurrent().getFileMap().entrySet()) {
				final List<File> list = new ArrayList<>(entry.getValue());
				for (final File file : list) {
					try {
						final Base64 base64 = new Base64();
						final String bytestring = base64.encodeToString(FileUtils.readFileToByteArray(file));
						fileName.put(entry.getKey(), file.getName());
						listOfString.put(entry.getKey(), bytestring);
					} catch (final IOException e) {
						 LOGGER.error("Exception has been occurred in file byte to string conversions", e);
					}
				}
			}
		}
		if (doc != null && !doc.isEmpty() && !listOfString.isEmpty()) {
			long count = 111;
			for (final DocumentDetailsVO d : doc) {
				if (d.getDocumentSerialNo() != null) {
					count = d.getDocumentSerialNo() - 1;
				}
				if (listOfString.containsKey(count) && fileName.containsKey(count)) {
					d.setDocumentByteCode(listOfString.get(count));
					d.setDocumentName(fileName.get(count));

				}
				count++;
			}
		}

		return doc;
	}
	
	private Map<Long, ChallanReportDTO> setTaxdataInsequenceOrder(Map<Long, ChallanReportDTO> taxdto, Long orgId,
			Long serviceId) {

		Long taxId = null;
		List<TbTaxMasEntity> taxMasList = null;
		Map<Long, ChallanReportDTO> taxdtos = new LinkedHashMap<>();
		if (taxdto != null) {
			taxId = taxdto.entrySet().stream().findFirst().get().getKey();
		}
		try {
			if (taxId != null) {
				TbTaxMas taxMas = tbTaxMasService.findTaxByTaxIdAndOrgId(taxId, orgId);
				if (taxMas != null) {
					taxMasList = tbTaxMasService.fetchAllApplicableServiceCharge(serviceId, orgId,
							taxMas.getTaxApplicable());
					if (CollectionUtils.isNotEmpty(taxMasList)) {
						for (TbTaxMasEntity txMas : taxMasList) {
							ChallanReportDTO dto = new ChallanReportDTO();
							dto = taxdto.get(txMas.getTaxId());
							taxdtos.put(txMas.getTaxId(), dto);
						}
					}
				}
			}

			return taxdtos;
		} catch (Exception e) {
			LOGGER.error("Exception has been occurred at the time of setTaxdataInsequenceOrder ", e);
		}
		return taxdtos;
	}

	private Map<String, String> sortMaps(Map<String, String> inputMap){
		Map<String, Date> tempMap = new LinkedHashMap<String, Date>();
		for (Entry<String, String> entry : inputMap.entrySet()) {
			Date date;
			try {
				date = new SimpleDateFormat(MainetConstants.DATE_FORMAT)
				        .parse(entry.getValue());
				tempMap.put(entry.getKey(), date);

			} catch (ParseException e) {
				LOGGER.error("Problem while parsing date : " + entry.getValue() + " " + e.getMessage());
			}
		}
		Map<String, Date> sortedMap = new LinkedHashMap<String, Date>();
		sortedMap = tempMap.entrySet().stream().sorted(Collections.reverseOrder(Map.Entry.comparingByValue())).
		collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue, (e1, e2)->e2, LinkedHashMap::new));
		inputMap.clear();
		for (Entry<String, Date> entry : sortedMap.entrySet()) {
			String dateInString = new SimpleDateFormat(MainetConstants.DATE_FORMAT).format(entry.getValue());
			inputMap.put(entry.getKey(), dateInString);
		}
		return inputMap;
	}
	
	 private Map<String, String> sortBillPeriodWiseData(Map<String, String> billNoWiseDate, Map<String, String> billPeriodDetails,
			 List<BillReceiptPrintingDTO> list) {
    	Map<String, String> tempSortedByBillPeriod = new LinkedHashMap<>();
	  	for(Entry<String, String> billDate : billNoWiseDate.entrySet()) {
  			String period = billPeriodDetails.get(billDate.getKey());
  			tempSortedByBillPeriod.put(billDate.getKey(), period);
		}
		return tempSortedByBillPeriod;		
	}
	//User Story #147721
	 @Override
		public CommonChallanDTO callPushToPayApiForPayment(CommonChallanDTO offline) throws IOException, InterruptedException {
			CommonChallanDTO commonDto=new CommonChallanDTO();
			String startUrl=ApplicationSession.getInstance().getMessage("challan.easyTap.start.url");
			String statusUrl=ApplicationSession.getInstance().getMessage("challan.easyTap.status.url");
			String userName=ApplicationSession.getInstance().getMessage("challan.easyTap.cfc.username");
			String appKey=ApplicationSession.getInstance().getMessage("challan.easyTap.key");
			long sleepTime=Long.valueOf(ApplicationSession.getInstance().getMessage("challan.easyTap.sleepTime"));
			long timeOut=Long.valueOf(ApplicationSession.getInstance().getMessage("challan.easyTap.timeOut"));
			String deviceId=null;
			String seq = seqGenFunctionUtility.generateSequenceNo(MainetConstants.Dms.COMMON_DEPT, "tb_collectionmaster",
					"cm_collnid", offline.getOrgId(), MainetConstants.FlagC, offline.getDeptId()).toString();
				try {
			CFCSchedulingCounterDet cfcDto =tbCfcApplicationMstService.getCounterDetByEmpId(offline.getUserId(),offline.getOrgId());
			if (cfcDto == null || cfcDto.getDeviceId() == null) {
				commonDto.setPushToPayErrMsg(ApplicationSession.getInstance().getMessage("challan.easyTap.cfc.deviceId"));
				return commonDto;
			} else {
				deviceId = cfcDto.getDeviceId() + "|ezetap_android";
			}

			LOGGER.info("Cfc Scheduling Details  " + cfcDto);
		} catch (Exception e) {
			commonDto.setPushToPayErrMsg(ApplicationSession.getInstance().getMessage("challan.easyTap.cfc.deviceId"));
			return commonDto;
		}
			PushToPayRequestDto dto = new PushToPayRequestDto();
			Map<String, String> map = new HashMap<String, String>();
			map.put("deviceId", deviceId);
			 dto.setAppKey(appKey);
			dto.setExternalRefNumber(seq);
			dto.setPushTo(map);
			dto.setAmount(offline.getAmountToPay());
			dto.setUsername(userName);
			ResponseEntity obj=null;
			String requstKey=null;
			 Map<String, String> jsonMap=new HashMap<String, String>();
			try {
			 obj = RestClient.callRestTemplateClient(dto, startUrl);
			 requstKey = new ObjectMapper().writeValueAsString(obj.getBody());
			 jsonMap = new ObjectMapper().readValue(requstKey,
					new TypeReference<Map<String, String>>() {
					}); // converts JSON to Map
			 LOGGER.info("Response from start api "+jsonMap);
			}
			
			catch (Exception e) {
				LOGGER.info("Exception at Start API call");
				commonDto.setPushToPayErrMsg(ApplicationSession.getInstance().getMessage("challan.easyTap.error"));
				return commonDto;
			}
			boolean flag = false;
			long sTime=System.currentTimeMillis();
			if (jsonMap != null) {
				String requestId = jsonMap.get("success");
				if (requestId != null && requestId.equals("true")) {
					while(!flag) {
						Thread.currentThread().sleep(sleepTime);
	                   
						EasyToPayStatusDto etpDto = new EasyToPayStatusDto();
						etpDto.setAppKey(appKey);
						etpDto.setOrigP2pRequestId(jsonMap.get("p2pRequestId"));
			
						
						etpDto.setUsername(userName);
						ResponseEntity etpObj = RestClient.callRestTemplateClient(etpDto,
								statusUrl);
						String etpRequstKey=null;
						try {
							
							
								
							 etpRequstKey = new ObjectMapper().writeValueAsString(etpObj.getBody());
						} catch (JsonProcessingException e1) {
							// TODO Auto-generated catch block
							e1.printStackTrace();
						}
						Map<String, Object> etpJsonMap = new HashMap<String, Object>();
						try {
							etpJsonMap = new ObjectMapper().readValue(etpRequstKey,
									new TypeReference<Map<String, Object>>() {
									});
							if (etpJsonMap != null && (etpJsonMap.get("success").toString().equals("true"))) {
								if (etpJsonMap.get("messageCode")
										.equals(MainetConstants.PAYMENT_STATUS.P2P_DEVICE_TXN_DONE)) {
									flag = true;
									commonDto.setPushToPayErrMsg(null);
									if(etpJsonMap.get("paymentMode")!=null)
										commonDto.setPosPayMode(etpJsonMap.get("paymentMode").toString());	
									if(etpJsonMap.get("txnId")!=null)
										commonDto.setPosTxnId(etpJsonMap.get("txnId").toString());	
					
									return commonDto;
								}else if(etpJsonMap.get("messageCode")
										.equals(MainetConstants.PAYMENT_STATUS.P2P_DEVICE_CANCELED)) {
									commonDto.setPushToPayErrMsg(etpJsonMap.get("message").toString());
									return commonDto;
								}
							}
							else {
								commonDto.setPushToPayErrMsg(etpJsonMap.get("message").toString());	
							}
						long lTime=System.currentTimeMillis();
						if((lTime-sTime)>timeOut) { 
							commonDto.setPushToPayErrMsg(ApplicationSession.getInstance().getMessage("challan.easyTap.timeoutMsg"));	
							flag=true;
						}
						} catch (JsonParseException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
					}

	
				}
				else {
					commonDto.setPushToPayErrMsg(jsonMap.get("errorMessage"));
				}
			}
			return commonDto;
		}
}
