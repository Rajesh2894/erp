package com.abm.mainet.swm.service;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.jws.WebMethod;
import javax.jws.WebService;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestBody;

import com.abm.mainet.common.audit.service.AuditService;
import com.abm.mainet.common.constant.MainetConstants;
import com.abm.mainet.common.constant.PrefixConstants;
import com.abm.mainet.common.domain.Organisation;
import com.abm.mainet.common.domain.ServiceMaster;
import com.abm.mainet.common.dto.CommonChallanDTO;
import com.abm.mainet.common.exception.FrameworkException;
import com.abm.mainet.common.integration.acccount.domain.TbServiceReceiptMasEntity;
import com.abm.mainet.common.integration.dms.service.IFileUploadService;
import com.abm.mainet.common.integration.dto.RequestDTO;
import com.abm.mainet.common.master.dto.TbTaxMas;
import com.abm.mainet.common.master.service.DepartmentService;
import com.abm.mainet.common.master.service.TbTaxMasService;
import com.abm.mainet.common.service.IFinancialYearService;
import com.abm.mainet.common.service.IReceiptEntryService;
import com.abm.mainet.common.service.ServiceMasterService;
import com.abm.mainet.common.utility.ApplicationContextProvider;
import com.abm.mainet.common.utility.CommonMasterUtility;
import com.abm.mainet.common.utility.LookUp;
import com.abm.mainet.common.utility.SeqGenFunctionUtility;
import com.abm.mainet.common.utility.UserSession;
import com.abm.mainet.common.utility.Utility;
import com.abm.mainet.smsemail.dto.SMSAndEmailDTO;
import com.abm.mainet.smsemail.service.ISMSAndEmailService;
import com.abm.mainet.swm.domain.SolidWasteBillMaster;
import com.abm.mainet.swm.domain.SolidWasteBillMasterHistory;
import com.abm.mainet.swm.domain.SolidWasteConsumerMaster;
import com.abm.mainet.swm.dto.SolidWasteBillMasterDTO;
import com.abm.mainet.swm.dto.UserChargeCollectionDTO;
import com.abm.mainet.swm.mapper.SolidWasteBillMasterMapper;
import com.abm.mainet.swm.repository.SolidWasteBillMasterRepository;

/**
 * The Class BillMasterServiceImpl.
 */
@Service
@Produces(value = { MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE })
@Consumes(value = { MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE })
@WebService(endpointInterface = "com.abm.mainet.swm.service.ISolidWasteBillMasterService")
@Path(value = "/solidwastebillservice")
public class SolidWasteBillMasterService implements ISolidWasteBillMasterService {

    private static final Logger LOG = LoggerFactory.getLogger(SolidWasteBillMasterService.class);

    /** The bill master repository. */
    @Autowired
    private SolidWasteBillMasterRepository billMasterRepository;

    /** The bill master mapper. */
    @Autowired
    private SolidWasteBillMasterMapper billMasterMapper;

    /** The audit service. */
    @Autowired
    private AuditService auditService;

    /**
     * The DepartmentService
     */
    @Autowired
    private DepartmentService departmentService;

    /**
     * The Service Master Service
     */
    @Autowired
    private ServiceMasterService serviceMasterService;

    /**
     * The TbTax Mas Service
     */
    @Autowired
    private TbTaxMasService taxMasService;

    /**
     * The IFinancial Year Service
     */
    @Autowired
    private IFinancialYearService financialYearService;

    /**
     * The Seq Gen Function Utility
     */
    @Autowired
    private SeqGenFunctionUtility seqGenFunctionUtility;

    /**
     * The IReceipt Entry Service
     */
    @Autowired
    private IReceiptEntryService receiptEntryService;

    /**
     * The IFile Upload Service
     */
    @Autowired
    private IFileUploadService fileUploadService;

    /**
     * The ISMSAndEmail Service
     */
    @Autowired
    private ISMSAndEmailService smsAndEmailService;    


    /*
     * (non-Javadoc)
     * @see com.abm.mainet.swm.service.BillMasterService#delete(java.lang.Long)
     */
    @Override
    @Transactional
    @WebMethod(exclude = true)
    public boolean delete(Long billMasterId, Long empId, String ipMacAdd) {
        SolidWasteBillMaster master = billMasterRepository.findOne(billMasterId);
        // TODO: set delete flag master.setDeActive(MainetConstants.IsDeleted.DELETE);
        master.setUpdatedBy(empId);
        master.setUpdatedDate(new Date());
        master.setLgIpMacUpd(ipMacAdd);
        billMasterRepository.save(master);
        saveBillMasterHistory(master, MainetConstants.Transaction.Mode.DELETE);

        return true;
    }

    /*
     * (non-Javadoc)
     * @see com.abm.mainet.swm.service.BillMasterService#getById(java.lang.Long)
     */
    @Override
    @Transactional(readOnly = true)
    @Path(value = "/get/{id}")
    @GET
    public SolidWasteBillMasterDTO getById(@PathParam("id") Long billMaster) {
        return billMasterMapper.mapBillMasterToBillMasterDTO(billMasterRepository.findOne(billMaster));
    }

    /**
     * Mapped.
     *
     * @param billMasterDetails the bill master details
     * @return the bill master
     */
    private SolidWasteBillMaster mapped(SolidWasteBillMasterDTO billMasterDetails) {
        SolidWasteBillMaster master = billMasterMapper
                .mapBillMasterDTOToBillMaster(billMasterDetails);
        return master;
    }

    /*
     * (non-Javadoc)
     * @see com.abm.mainet.swm.service.BillMasterService#save(com.abm.mainet.swm.dto.BillMasterDTO)
     */
    @Override
    @Transactional
    @POST
    @Path(value = "/saveSolidWasteBill")
    public SolidWasteBillMasterDTO save(@RequestBody SolidWasteBillMasterDTO billMasterDetails) {
        SolidWasteBillMaster master = mapped(billMasterDetails);
        master.setSwNewCustId(billMasterDetails.getSwNewCustId());
        // get financial by date
        Long financiaYear = financialYearService.getFinanceYearId(new Date());
        master.setFinYearid(financiaYear);

        if (null != master.getBillAmount() && master.getMonthlyCharges() != null) {

            BigDecimal billAmount = master.getBillAmount();

            if (master.getAdvanceAmount() != null) {
                master.setBillAmount(master.getBillAmount().add(master.getAdvanceAmount()));
                master.setAdvanceAmount(BigDecimal.ZERO);
            }

            if (master.getOutStandingAmount() != null) {
                master.setBillAmount(master.getBillAmount().subtract(master.getOutStandingAmount()));
                master.setOutStandingAmount(BigDecimal.ZERO);
            }

            BigDecimal balanceAmount = master.getMonthlyCharges().subtract(master.getBillAmount());

            if (balanceAmount.doubleValue() < 0D) {
                master.setAdvanceAmount(balanceAmount.abs());
            } else {
                master.setOutStandingAmount(balanceAmount);
            }
            master.setBillAmount(billAmount);

        }

        final Long sequence = seqGenFunctionUtility.generateSequenceNo(MainetConstants.SolidWasteManagement.SHORT_CODE,
                "TB_SW_REGISTRATION", "SW_NEW_CUST_ID", billMasterDetails.getOrgid(),
                MainetConstants.FlagC, financiaYear);

        master.setSwBmBillno(sequence);
        SolidWasteConsumerMaster consumer = new SolidWasteConsumerMaster();
        consumer.setRegistrationId(billMasterDetails.getRegistrationId());
        master.setTbSwNewRegistration(consumer);
        master = billMasterRepository.save(master);
        saveBillMasterHistory(master, MainetConstants.Transaction.Mode.ADD);

        Organisation org = new Organisation();
        org.setOrgid(master.getOrgid());        
     
        Long deptId = departmentService.getDepartmentIdByDeptCode(MainetConstants.SolidWasteManagement.SHORT_CODE);
        CommonChallanDTO receiptDTO = new CommonChallanDTO();
        receiptDTO.setDeptId(deptId);
        receiptDTO.setOrgId(master.getOrgid());
        if (master.getSwBmIdno() != null)
            receiptDTO.setUniquePrimaryId(master.getSwBmIdno().toString());
        receiptDTO.setUserId(master.getCreatedBy());
        ServiceMaster serviceMaster = serviceMasterService
                .getServiceMasterByShortCode(MainetConstants.SolidWasteManagement.SOLID_WASTE_USER_CHARGED, master.getOrgid());
        receiptDTO.setServiceId(serviceMaster.getSmServiceId());

        receiptDTO.setChallanServiceType(MainetConstants.CHALLAN_RECEIPT_TYPE.NON_REVENUE_BASED);
        receiptDTO.setApplicantName(billMasterDetails.getCustomerName());
        receiptDTO.setLgIpMac(billMasterDetails.getLgIpMac());

        // TODO : set tax and amount
        Map<Long, Double> feeId = new HashMap<>(0);
        List<TbTaxMas> taxList = null;

        final LookUp chargeApplicableAt = CommonMasterUtility.getValueFromPrefixLookUp(
                PrefixConstants.LookUpPrefix.RCPT, PrefixConstants.NewWaterServiceConstants.CAA, org);

      //To get single record of tax details for specific service after setting tax group and service in tax master configuration
      		if (StringUtils.isNotEmpty(billMasterDetails.getServiceShortCode())) {
      			ServiceMaster sm = ApplicationContextProvider.getApplicationContext().getBean(ServiceMasterService.class)
      					.getServiceMasterByShortCode(billMasterDetails.getServiceShortCode(), master.getOrgid());
      			if (sm.getSmServiceId() != null) {
      				taxList = taxMasService.findAllTaxesForBillGenerationByServiceId(master.getOrgid(), deptId,
      						chargeApplicableAt.getLookUpId(), sm.getSmServiceId());
      			}
      		} else
      			taxList = taxMasService.findAllTaxesForBillGeneration(master.getOrgid(), deptId,
      					chargeApplicableAt.getLookUpId(), null); // -1L for All

        feeId.put(taxList.get(0).getTaxId(), master.getBillAmount().doubleValue());
        receiptDTO.setFeeIds(feeId);
   
        final LookUp serviceReceipt = CommonMasterUtility.getValueFromPrefixLookUp(MainetConstants.FlagM,
                PrefixConstants.REV_SUB_CPD_VALUE, new Organisation(master.getOrgid()));
        receiptDTO.setPaymentCategory(serviceReceipt.getLookUpCode());
        receiptDTO.setAmountToPay(master.getBillAmount().toString());
        LookUp payLookUp = CommonMasterUtility
                .getValueFromPrefixLookUp(billMasterDetails.getPayMode(), PrefixConstants.PAY_PREFIX.PREFIX_VALUE,
                        org);
        receiptDTO.setPayModeIn(
                payLookUp
                        .getLookUpId());

        receiptDTO.setDeptId(deptId);
        TbServiceReceiptMasEntity serviceReceiptMaster = receiptEntryService.insertInReceiptMaster(receiptDTO, null);
        final Long receiptId = serviceReceiptMaster.getRmRcptid();
        if (receiptId != null) {
            master.setBillPaidFlg(MainetConstants.Y_FLAG);
        }
        SolidWasteBillMasterDTO response = billMasterMapper.mapBillMasterToBillMasterDTO(master);
        response.setReceiptNo(serviceReceiptMaster.getRmRcptno());
        if (CollectionUtils.isNotEmpty(billMasterDetails.getDocumentList())) {
            try {
                RequestDTO fileUploadDTO = new RequestDTO();

                fileUploadDTO.setDeptId(deptId);
                fileUploadDTO.setOrgId(billMasterDetails.getOrgid());
                fileUploadDTO.setIdfId("BILL/" + master.getSwBmBillno());
                fileUploadDTO.setStatus(MainetConstants.FlagA);
                fileUploadDTO.setUserId(billMasterDetails.getCreatedBy());
                fileUploadDTO.setDepartmentName(MainetConstants.SolidWasteManagement.SHORT_CODE);
                fileUploadService.doMasterFileUpload(billMasterDetails.getDocumentList(), fileUploadDTO);
            } catch (Exception e) {
                throw new FrameworkException("File Upload Error", e);
            }
        }

        SMSAndEmailDTO dto = new SMSAndEmailDTO();
        dto.setOrganizationName(org.getONlsOrgname());
        dto.setAppNo(master.getSwNewCustId());
        dto.setServName(serviceMaster.getSmServiceName());
        dto.setMobnumber(billMasterDetails.getCustomerMobile());
        dto.setUserName(billMasterDetails.getCustomerName());

        dto.setAmount(master.getMonthlyCharges().doubleValue());
        dto.setCurrentDate(Utility.dateToString(new Date()));
        dto.setChallanAmt(master.getBillAmount().toString());
        if (master.getAdvanceAmount() != null) {
            dto.setAppAmount(master.getAdvanceAmount().toString());
        }
        dto.setNoticeNo(serviceReceiptMaster.getRmRcptno().toString());
        dto.setNoticeDate(Utility.dateToString(serviceReceiptMaster.getRmDate()));
        dto.setDispMode(payLookUp.getDescLangFirst());

        smsAndEmailService.sendEmailSMS(MainetConstants.SolidWasteManagement.SHORT_CODE,
                MainetConstants.SolidWasteManagement.USER_CHARGE,
                PrefixConstants.SMS_EMAIL_ALERT_TYPE.GENERAL_MSG, dto, org,
                1);        
        return response;

    }

    /**
     * Save bill master history.
     *
     * @param master the master
     * @param status the status
     */
    private void saveBillMasterHistory(SolidWasteBillMaster master, String status) {
        SolidWasteBillMasterHistory masterHistory = new SolidWasteBillMasterHistory();
        masterHistory.sethStatus(status);
        auditService.createHistory(master, masterHistory);
    }

    /*
     * (non-Javadoc)
     * @see com.abm.mainet.swm.service.BillMasterService#update(com.abm.mainet.swm.dto.BillMasterDTO)
     */
    @Override
    @Transactional
    @WebMethod(exclude = true)
    public SolidWasteBillMasterDTO update(SolidWasteBillMasterDTO billMasterDetails) {
        SolidWasteBillMaster master = mapped(billMasterDetails);
        master = billMasterRepository.save(master);
        saveBillMasterHistory(master, MainetConstants.Transaction.Mode.UPDATE);
        return billMasterMapper.mapBillMasterToBillMasterDTO(master);
    }

    /* (non-Javadoc)
     * @see com.abm.mainet.swm.service.ISolidWasteBillMasterService#getUserChargeDetails(java.lang.Long)
     */
    @Override
    @WebMethod(exclude = true)
    @Transactional(readOnly = true)
    public UserChargeCollectionDTO getUserChargeDetails(Long MonthNo) {
        List<Object[]> userChargeDetails = billMasterRepository.getUserChargeCollectionData(MonthNo,UserSession.getCurrent().getOrganisation().getOrgid());
        UserChargeCollectionDTO userChargeCollectionDto = null;
        UserChargeCollectionDTO userChargeCollectionsDTO = null;
        BigDecimal sum = new BigDecimal("0");
        List<UserChargeCollectionDTO> usercollections = new ArrayList<>();
        if (userChargeDetails != null && !userChargeDetails.isEmpty()) {
            userChargeCollectionsDTO = new UserChargeCollectionDTO();
            for (Object[] UserChargeCollectionDTO : userChargeDetails) {
                userChargeCollectionDto = new UserChargeCollectionDTO();
                if(UserChargeCollectionDTO[0]!=null)
                userChargeCollectionDto.setBillDate((Date) UserChargeCollectionDTO[0]);
                if(UserChargeCollectionDTO[4]!=null)
                userChargeCollectionDto.setChargesNameEng(UserChargeCollectionDTO[4].toString());
                if(UserChargeCollectionDTO[5]!=null)
                userChargeCollectionDto.setChargesNameReg(UserChargeCollectionDTO[5].toString());
                if(UserChargeCollectionDTO[3]!=null)
                userChargeCollectionDto.setConsumerAddress(UserChargeCollectionDTO[3].toString());
                if(UserChargeCollectionDTO[1]!=null)
                userChargeCollectionDto.setConsumerName(UserChargeCollectionDTO[1].toString());
                BigInteger mobile = new BigInteger("0");                
                mobile = (BigInteger)UserChargeCollectionDTO[2];
                if(mobile!=null)
                userChargeCollectionDto.setConsumerMobNo(mobile.longValue());
                if(UserChargeCollectionDTO[9]!=null)
                userChargeCollectionDto.setMonthlyCharges((BigDecimal)UserChargeCollectionDTO[9]);
                if(UserChargeCollectionDTO[8]!=null)
                userChargeCollectionDto.setManualReceiptNo(UserChargeCollectionDTO[8].toString());
                if(UserChargeCollectionDTO[11]!=null)
                userChargeCollectionDto.setReceiptNo(UserChargeCollectionDTO[11].toString());
                if(UserChargeCollectionDTO[12]!=null)
                    userChargeCollectionDto.setReceiptBookNo(UserChargeCollectionDTO[12].toString());
                if(UserChargeCollectionDTO[10]!=null) {
                userChargeCollectionDto.setTotalAmount((BigDecimal)UserChargeCollectionDTO[10]);
                sum= sum.add((BigDecimal)UserChargeCollectionDTO[10]);
                }
                if(UserChargeCollectionDTO[6]!=null)
                userChargeCollectionDto.setWardEng(UserChargeCollectionDTO[6].toString());
                if(UserChargeCollectionDTO[7]!=null)
                userChargeCollectionDto.setWardReg(UserChargeCollectionDTO[7].toString());           
                usercollections.add(userChargeCollectionDto);
            }
            userChargeCollectionsDTO.setUsercollections(usercollections);
            userChargeCollectionsDTO.setSumAmount(sum);
            
        }
        return userChargeCollectionsDTO;
    }

}
