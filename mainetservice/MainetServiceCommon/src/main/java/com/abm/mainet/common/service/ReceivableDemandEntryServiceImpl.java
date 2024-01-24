package com.abm.mainet.common.service;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.lang.reflect.Method;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Set;

import javax.annotation.Resource;
import javax.jws.WebMethod;
import javax.jws.WebService;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.hibernate.Hibernate;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.ClassUtils;
import org.springframework.util.ReflectionUtils;
import org.springframework.web.bind.annotation.RequestBody;

import com.abm.mainet.cfc.challan.dto.ChallanReceiptPrintDTO;
import com.abm.mainet.cfc.challan.service.IChallanService;
import com.abm.mainet.common.audit.service.AuditService;
import com.abm.mainet.common.constant.MainetConstants;
import com.abm.mainet.common.constant.PrefixConstants;
import com.abm.mainet.common.dao.IReceivableDemandEntryDao;
import com.abm.mainet.common.domain.CFCApplicationAddressEntity;
import com.abm.mainet.common.domain.Organisation;
import com.abm.mainet.common.domain.ReceivableDemandEntry;
import com.abm.mainet.common.domain.ReceivableDemandEntryDetailsHistory;
import com.abm.mainet.common.domain.ReceivableDemandEntryHistory;
import com.abm.mainet.common.domain.TbCfcApplicationMstEntity;
import com.abm.mainet.common.dto.CommonChallanDTO;
import com.abm.mainet.common.dto.ReceivableDemandEntryDTO;
import com.abm.mainet.common.dto.ReceivableDemandEntryDetailsDTO;
import com.abm.mainet.common.exception.FrameworkException;
import com.abm.mainet.common.integration.acccount.dto.TbServiceReceiptMasBean;
import com.abm.mainet.common.integration.acccount.dto.TbSrcptFeesDetBean;
import com.abm.mainet.common.integration.acccount.dto.VoucherPostDTO;
import com.abm.mainet.common.integration.acccount.dto.VoucherPostDetailDTO;
import com.abm.mainet.common.integration.dto.RequestDTO;
import com.abm.mainet.common.mapper.ReceivableDemandEntryMapper;
import com.abm.mainet.common.master.service.DepartmentService;
import com.abm.mainet.common.master.service.TbTaxMasService;
import com.abm.mainet.common.repository.ReceivableDemandEntryRepository;
import com.abm.mainet.common.utility.ApplicationContextProvider;
import com.abm.mainet.common.utility.ApplicationSession;
import com.abm.mainet.common.utility.CommonMasterUtility;
import com.abm.mainet.common.utility.Filepaths;
import com.abm.mainet.common.utility.LookUp;
import com.abm.mainet.common.utility.SeqGenFunctionUtility;
import com.abm.mainet.common.utility.UserSession;
import com.abm.mainet.common.utility.Utility;

import io.swagger.annotations.Api;

@WebService(endpointInterface = "com.abm.mainet.common.service.IReceivableDemandEntryService")
@Consumes({ MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML })
@Produces({ MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML })
@Api(value = "/receivableDemandEntryService")
@Path("/receivableDemandEntryService")
@Service
public class ReceivableDemandEntryServiceImpl implements IReceivableDemandEntryService {

    private static final Logger LOGGER = Logger.getLogger(ReceivableDemandEntryServiceImpl.class);

    @Autowired
    private ReceivableDemandEntryRepository receivableDemandEntryRepository;

    @Autowired
    private ApplicationService applicationService;

    @Autowired
    private AuditService auditService;

    @Autowired
    private ReceivableDemandEntryMapper receivableDemandEntryMapper;

    @Autowired
    private IReceivableDemandEntryDao receivableDemandEntryDao;

    @Autowired
    private SeqGenFunctionUtility seqGenFunctionUtility;

    @Resource
    private DepartmentService departmentService;

    @Autowired
    private MessageSource messageSource;

    @Autowired
    private TbTaxMasService tbTaxMasService;

    @Autowired
    private ICFCApplicationMasterService cFCApplicationMasterService;

    @Autowired
    private IChallanService challanService;

    @Autowired
    private IFinancialYearService financialYearService;
    
    @Autowired
    private ServiceMasterService serviceMaster;

    @Override
    @POST
    @Path(value = "/saveSupplementaryBill")
    @Transactional
    public ReceivableDemandEntryDTO saveReceivableDemandEntry(@RequestBody ReceivableDemandEntryDTO receivableDemandEntryDto) {
        String refNumber = "";
        if (receivableDemandEntryDto.isNewCust() == true) {
            setCustomerMasterDetails(receivableDemandEntryDto);
            refNumber = receivableDemandEntryDto.getIdnNo();
        } else {
            if (org.apache.commons.lang3.StringUtils.isNotEmpty(receivableDemandEntryDto.getRefNumber())) {
                refNumber = receivableDemandEntryDto.getRefNumber();
                if (receivableDemandEntryDto.getCustomerDetails().getIdfId() != null) {
                    receivableDemandEntryDto.setRefNumber(receivableDemandEntryDto.getCustomerDetails().getIdfId()); // ccnId
                } else if (receivableDemandEntryDto.getApplicationId() != null) {
                    refNumber = receivableDemandEntryDto.getRefNumber();
                    if (receivableDemandEntryDto.getRefNumber() == "") {
                        receivableDemandEntryDto.setRefNumber(null);// if applicationId present
                    }
                }
            } else {
                LOGGER.error("Referance Number Should Not Be Null");
            }
        }
        ReceivableDemandEntry receivableDemandEntry = saveBill(receivableDemandEntryDto);
        ReceivableDemandEntryDTO rcvblDto = receivableDemandEntryMapper.mapEntityToDTO(receivableDemandEntry);
        rcvblDto.setRefNumber(refNumber);
        rcvblDto.setApplicationId(receivableDemandEntry.getApplicationId());
        return rcvblDto;

    }

    private ReceivableDemandEntryDTO setCustomerMasterDetails(ReceivableDemandEntryDTO receivableDemandEntryDto) {
        String ward = receivableDemandEntryDto.getWardIdnPattern();
        Long resetId = receivableDemandEntryDto.getOrgid();
        final Long sequenceNo = seqGenFunctionUtility.generateSequenceNo(MainetConstants.ReceivableDemandEntry.MODULE,
                MainetConstants.CommonMasterUi.TB_CFC_APP_MST,
                MainetConstants.SCRUTINY_COMMON_PARAM.APM_APPLICATION_ID, receivableDemandEntryDto.getOrgid(),
                MainetConstants.FlagC, resetId);
        String refNumber = ward + String.format("%07d", sequenceNo);
        receivableDemandEntryDto.getCustomerDetails().setReferenceId(refNumber);
        Long applicationId = applicationService.createApplication(receivableDemandEntryDto.getCustomerDetails());
        receivableDemandEntryDto.setApplicationId(applicationId);
        receivableDemandEntryDto.setIdnNo(refNumber);
        receivableDemandEntryDto.setRefNumber(null);// Created as new IDN
        return receivableDemandEntryDto;
    }

    @Transactional
    private ReceivableDemandEntry saveBill(ReceivableDemandEntryDTO receivableDemandEntryDto) {
        ReceivableDemandEntry receivableDemandEntry = mapped(receivableDemandEntryDto);
        String billNumber = generateBillNumber(receivableDemandEntryDto);
        receivableDemandEntry.setBillNo(billNumber);
        receivableDemandEntry = receivableDemandEntryRepository.save(receivableDemandEntry);

        try {

            ReceivableDemandEntryHistory receivableDemandEntryHistory = new ReceivableDemandEntryHistory();
            receivableDemandEntryHistory.sethStatus(MainetConstants.InsertMode.ADD.getStatus());
            auditService.createHistory(receivableDemandEntry, receivableDemandEntryHistory);
            List<Object> receivableDemandEntryHistoryList = new ArrayList<>();
            receivableDemandEntry.getRcvblDemandList().forEach(masDet -> {
                ReceivableDemandEntryDetailsHistory rDetailsHistory = new ReceivableDemandEntryDetailsHistory();
                BeanUtils.copyProperties(masDet, rDetailsHistory);
                rDetailsHistory.setBillId(masDet.getRcvblDemandDets().getBillId());
                rDetailsHistory.sethStatus(MainetConstants.InsertMode.ADD.getStatus());
                receivableDemandEntryHistoryList.add(rDetailsHistory);
            });
            auditService.createHistoryForListObj(receivableDemandEntryHistoryList);

        } catch (Exception e) {
            LOGGER.error("Could not make audit entry for " + receivableDemandEntry, e);
        }
        return receivableDemandEntry;

    }

    private String generateBillNumber(ReceivableDemandEntryDTO receivableDemandEntryDto) {
        String billNumber = "";
        Date start = null;
        Date end = null;
        final Object[] finData = financialYearService.getFinacialYearByDate(new Date());
        if ((finData != null) && (finData.length > 0)) {
            start = (Date) finData[1];
            end = (Date) finData[2];
        }
        String startYear = StringUtils.EMPTY;
        String endYaer = StringUtils.EMPTY;
        final SimpleDateFormat sdf1 = new SimpleDateFormat(MainetConstants.YEAR_FORMAT1);
        if (start != null) {
            startYear = sdf1.format(start);
        }
        final SimpleDateFormat sdf2 = new SimpleDateFormat(MainetConstants.YEAR_FORMAT1);
        if (end != null) {
            endYaer = sdf2.format(end);
        }

        Long resetId = Long.valueOf(startYear + endYaer);
        final Long sequenceNo = seqGenFunctionUtility.generateSequenceNo("AC", "TB_BILL_MAS", "BM_ID",
                receivableDemandEntryDto.getOrgid(), MainetConstants.FlagC, resetId);

        billNumber = startYear + endYaer + MainetConstants.ReceivableDemandEntry.BILL_NO_FORMAT
                + String.format("%08d", sequenceNo);
        return billNumber;
    }

    private ReceivableDemandEntry mapped(ReceivableDemandEntryDTO receivableDemandEntryDTO) {
        ReceivableDemandEntry master = receivableDemandEntryMapper
                .mapDTOToEntity(receivableDemandEntryDTO);
        return master;
    }

    @Transactional
    private Long setApplicationDetails(ReceivableDemandEntryDTO receivableDemandEntryDto) {
        Long applicationId = applicationService.createApplication(receivableDemandEntryDto.getCustomerDetails());
        return applicationId;
    }

    @Override
    @Transactional
    @WebMethod(exclude = true)
    public ReceivableDemandEntryDTO updateReceivableDemandEntry(ReceivableDemandEntryDTO receivableDemandEntryDto) {

        if (receivableDemandEntryDto.getNewIdn() != null && receivableDemandEntryDto.getNewIdn().equals(MainetConstants.ReceivableDemandEntry.NEW_IDN)) {
            applicationService.updateApplicationForSupplementaryBill(receivableDemandEntryDto.getCustomerDetails());
        }

        ReceivableDemandEntry receivableDemandEntry = mapped(receivableDemandEntryDto);
        receivableDemandEntry = receivableDemandEntryRepository.save(receivableDemandEntry);
        // Added For Update BillDispute Flag After Successfully Payment In Bill Master
        if (receivableDemandEntry.getBillDispId() != null && receivableDemandEntry.getReceiptId() != null) {
            updateBillDisputeFlagAfterPayment(receivableDemandEntryDto);
        }
                

        try {

            ReceivableDemandEntryHistory receivableDemandEntryHistory = new ReceivableDemandEntryHistory();
            receivableDemandEntryHistory.sethStatus(MainetConstants.InsertMode.UPDATE.getStatus());
            auditService.createHistory(receivableDemandEntry, receivableDemandEntryHistory);
            List<Object> receivableDemandEntryHistoryList = new ArrayList<>();
            receivableDemandEntry.getRcvblDemandList().forEach(masDet -> {
                ReceivableDemandEntryDetailsHistory rDetailsHistory = new ReceivableDemandEntryDetailsHistory();
                BeanUtils.copyProperties(masDet, rDetailsHistory);
                rDetailsHistory.setBillId(masDet.getRcvblDemandDets().getBillId());
                rDetailsHistory.sethStatus(MainetConstants.InsertMode.UPDATE.getStatus());
                receivableDemandEntryHistoryList.add(rDetailsHistory);
            });
            auditService.createHistoryForListObj(receivableDemandEntryHistoryList);

        } catch (Exception e) {
            LOGGER.error("Could not make audit entry for " + receivableDemandEntry, e);
        }
        return receivableDemandEntryMapper.mapEntityToDTO(receivableDemandEntry);
    }

    private void updateBillDisputeFlagAfterPayment(ReceivableDemandEntryDTO recvblDmndDto) {
        Class<?> clazz = null;
        String serviceClassName = null;
        String deptCode = null;
        Object dynamicServiceInstance = null;

        try {
            deptCode = departmentService.getDeptCode(recvblDmndDto.getDeptId());
            serviceClassName = messageSource.getMessage(ApplicationSession.getInstance().getMessage(MainetConstants.ReceivableDemandEntry.UPDATE_BILL_DISPUTE_FLAG)
                    + deptCode, new Object[] {}, StringUtils.EMPTY, Locale.ENGLISH);

            if (serviceClassName != null && !MainetConstants.BLANK.equals(serviceClassName)) {
                clazz = ClassUtils.forName(serviceClassName, ApplicationContextProvider.getApplicationContext().getClassLoader());
                dynamicServiceInstance = ApplicationContextProvider.getApplicationContext().getAutowireCapableBeanFactory().autowire(clazz, 2, false);
                final Method method = ReflectionUtils.findMethod(clazz, MainetConstants.ReceivableDemandEntry.UPDATE_BILL_DISPUTE_FLAG_BILL_MAS,
                        new Class[] { Long.class, Long.class });
                ReflectionUtils.invokeMethod(method, dynamicServiceInstance, new Object[] { recvblDmndDto.getBillDispId(), recvblDmndDto.getOrgid() });
            }

        } catch (Exception e) {
            LOGGER.error("Exception While Upadting Bill Dipsute Flag  :", e);
            throw new FrameworkException("Exception Occured While Upadting Bill Dipsute Flag of Supplementary Bill No:" + recvblDmndDto.getBillNo());
        }
    }

    @Override
    @GET
    @Path(value = "/getByBillId/{billId}")
    @Transactional(readOnly = true)
    public ReceivableDemandEntryDTO getById(@PathParam("billId") Long billId) {
        ReceivableDemandEntryDTO recvDemandDto = receivableDemandEntryMapper
                .mapEntityToDTO(receivableDemandEntryRepository.findOne(billId));

        recvDemandDto.setActionMode(MainetConstants.ReceivableDemandEntry.GET_BY_ID);
        RequestDTO reqDto = new RequestDTO();

        reqDto = getCustomerDetailsByRefNo(recvDemandDto);
        if (StringUtils.isNotEmpty(reqDto.getfName())) {
            recvDemandDto.setCustomerDetails(reqDto);
        } else {
            reqDto = getApplicationDetailsByApplicatioNoOrRefNo(recvDemandDto);
            if (StringUtils.isNotEmpty(reqDto.getfName())) {
                recvDemandDto.setCustomerDetails(reqDto);
            }
        }
        return recvDemandDto;
    }

    @Override
    @Transactional(readOnly = true)
    @WebMethod(exclude = true)
    public RequestDTO getCustomerDetailsByRefNo(ReceivableDemandEntryDTO recvblDmndDto) {
        RequestDTO result = new RequestDTO();
        Class<?> clazz = null;
        String serviceClassName = null;
        String deptCode = null;
        Object dynamicServiceInstance = null;

        try {
            deptCode = departmentService.getDeptCode(recvblDmndDto.getDeptId());
            serviceClassName = messageSource.getMessage(
                    ApplicationSession.getInstance().getMessage(MainetConstants.ReceivableDemandEntry.RECEIVABELE_DEMAND_ENTRY)
                            + deptCode,
                    new Object[] {}, StringUtils.EMPTY,
                    Locale.ENGLISH);
            if (org.apache.commons.lang3.StringUtils.isNoneBlank(recvblDmndDto.getActionMode())) {

                if (serviceClassName != null && !MainetConstants.BLANK.equals(serviceClassName)) {
                    clazz = ClassUtils.forName(serviceClassName,
                            ApplicationContextProvider.getApplicationContext().getClassLoader());
                    dynamicServiceInstance = ApplicationContextProvider.getApplicationContext().getAutowireCapableBeanFactory()
                            .autowire(clazz, 2, false);
                    final Method method = ReflectionUtils.findMethod(clazz,
                            MainetConstants.ReceivableDemandEntry.GET_CONSUMER_DETAILS_BY_CCNID,
                            new Class[] { Long.class, Long.class });
                    result = (RequestDTO) ReflectionUtils.invokeMethod(method,
                            dynamicServiceInstance,
                            new Object[] { Long.valueOf(recvblDmndDto.getRefNumber()), recvblDmndDto.getOrgid() });
                }
            } else {

                if (serviceClassName != null && !MainetConstants.BLANK.equals(serviceClassName)) {
                    clazz = ClassUtils.forName(serviceClassName,
                            ApplicationContextProvider.getApplicationContext().getClassLoader());
                    dynamicServiceInstance = ApplicationContextProvider.getApplicationContext().getAutowireCapableBeanFactory()
                            .autowire(clazz, 4, false);
                    final Method method = ReflectionUtils.findMethod(clazz,
                            MainetConstants.ReceivableDemandEntry.GET_CONSUMER_DETAILS_BY_REF_NO,
                            new Class[] { String.class, Long.class, Long.class, Long.class });
                    result = (RequestDTO) ReflectionUtils.invokeMethod(method,
                            dynamicServiceInstance, new Object[] { recvblDmndDto.getRefNumber(), recvblDmndDto.getServiceId(),
                                    recvblDmndDto.getDeptId(), recvblDmndDto.getOrgid() });
                }
            }
        } catch (Exception e) {
            LOGGER.error("Exception while finding reference Number  :" + recvblDmndDto.getRefNumber(), e);
            return result;
        }
        return result;
    }

    @Override
    @Transactional(readOnly = true)
    @WebMethod(exclude = true)
    public RequestDTO getApplicationDetailsByApplicatioNoOrRefNo(ReceivableDemandEntryDTO recvblDmndDto) {
        RequestDTO reqDto = new RequestDTO();
        TbCfcApplicationMstEntity cfcMaster = null;
        CFCApplicationAddressEntity cfcAddress = null;
        try {
            cfcMaster = cFCApplicationMasterService.getCFCApplicationByRefNoOrAppNo(recvblDmndDto.getRefNumber(),
                    recvblDmndDto.getApplicationId(), recvblDmndDto.getOrgid());
            if (cfcMaster.getApmApplicationId() != null) {
                cfcAddress = cFCApplicationMasterService.getApplicantsDetails(cfcMaster.getApmApplicationId());
                reqDto.setfName(replaceNull(cfcMaster.getApmFname()) + MainetConstants.WHITE_SPACE
                        + replaceNull(cfcMaster.getApmMname()) + MainetConstants.WHITE_SPACE
                        + replaceNull(cfcMaster.getApmLname()));
                reqDto.setApplicationType(cfcMaster.getCcdApmType());
                reqDto.setApplicationId(cfcMaster.getApmApplicationId());
                reqDto.setApplicationDate(cfcMaster.getApmApplicationDate());
                if (org.apache.commons.lang3.StringUtils.isNoneBlank(cfcMaster.getRefNo())) {
                    reqDto.setCcnNumber(cfcMaster.getRefNo());
                }
                if (cfcAddress != null) {
                    reqDto.setBldgName(cfcAddress.getApaBldgnm());
                    reqDto.setRoadName(cfcAddress.getApaRoadnm());
                    reqDto.setAreaName(cfcAddress.getApaAreanm());
                    reqDto.setEmail(cfcAddress.getApaEmail());
                    reqDto.setMobileNo(cfcAddress.getApaMobilno());
                    reqDto.setCityName(cfcAddress.getApaCityName());
                    reqDto.setPincodeNo(cfcAddress.getApaPincode());
                    reqDto.setHouseComplexName(cfcAddress.getApaHsgCmplxnm());
                }
            }
            return reqDto;
        } catch (Exception e) {
            LOGGER.error("Application Number Not Found:" + recvblDmndDto.getRefNumber(), e);
            return reqDto;
        }
    }

    private String replaceNull(String name) {
        if (name == null) {
            name = MainetConstants.BLANK;
        }
        return name;
    }

    @Override
    @Transactional(readOnly = true)
    @WebMethod(exclude = true)
    public ReceivableDemandEntryDTO getByRefNoOrAppNo(ReceivableDemandEntryDTO recvblDmndDto) {
        RequestDTO reqDto = new RequestDTO();

        reqDto = getCustomerDetailsByRefNo(recvblDmndDto);
        if (StringUtils.isNotEmpty(reqDto.getfName())) {
            recvblDmndDto.setCustomerDetails(reqDto);
        } else {
            reqDto = getApplicationDetailsByApplicatioNoOrRefNo(recvblDmndDto);
            if (StringUtils.isNotEmpty(reqDto.getfName())) {
                recvblDmndDto.setCustomerDetails(reqDto);
                recvblDmndDto.setApplicationId(reqDto.getApplicationId());
            }
        }

        return recvblDmndDto;
    }

    @Override
    @Transactional(readOnly = true)
    @WebMethod(exclude = true)
    public List<ReceivableDemandEntryDTO> getBillInfoListByBillNoOrRefNo(String billNo, String refNumber, int dueDates,
            Long orgId) {
        List<ReceivableDemandEntryDTO> dtoList = new ArrayList<>();
        ReceivableDemandEntryDTO dto = null;
        List<ReceivableDemandEntry> receivableDemandEntryList = receivableDemandEntryRepository.getBillInfoBybillNoOrRefNo(orgId,
                billNo, refNumber, dueDates);
        Long getCustomerInfo = 1L;
        if (!receivableDemandEntryList.isEmpty()) {
            for (ReceivableDemandEntry entity : receivableDemandEntryList) {
                if (entity != null) {
                    dto = new ReceivableDemandEntryDTO();
                    Hibernate.initialize(entity.getRcvblDemandList());
                    dto = receivableDemandEntryMapper.mapEntityToDTO(entity);
                    if (getCustomerInfo.equals(1L)) { // get data of same ccn/idn only once
                        dto.setActionMode(MainetConstants.ReceivableDemandEntry.GET_BY_ID);
                        dto = getByRefNoOrAppNo(dto);
                        getCustomerInfo++;
                    }
                    dtoList.add(dto);

                }
            }
        }

        return dtoList;
    }

    @Override
    @WebMethod(exclude = true)
    public ChallanReceiptPrintDTO updateBillAfterPayment(List<ReceivableDemandEntryDTO> receivableDemandEntryDtolist,
            CommonChallanDTO offline) {
        BigDecimal zero = new BigDecimal("0");
        List<ReceivableDemandEntryDTO> rcvblDemandList = new ArrayList<>();
        List<ReceivableDemandEntryDetailsDTO> rcvblDemandDetList = null;

        double balancedAmount = Math.abs(Math.round(Double.valueOf(offline.getAmountToPay())));

        for (ReceivableDemandEntryDTO receivableDemandEntryDto : receivableDemandEntryDtolist) {
            rcvblDemandDetList = new ArrayList<>();
            for (final ReceivableDemandEntryDetailsDTO det : receivableDemandEntryDto.getRcvblDemandList()) {
                double taxAmount = det.getBillDetailsAmount().doubleValue();
                /*
                 * double paidTaxAmount = 0; double balancePayDetAmount = taxAmount - paidTaxAmount; if (taxAmount > 0d &&
                 * balancePayDetAmount > 0d) { if (balancedAmount > balancePayDetAmount) { balancedAmount = balancedAmount -
                 * balancePayDetAmount; det.setPaidBillDetAmount(BigDecimal.valueOf(balancePayDetAmount));
                 * det.setBalancePayDetAmount(zero); det.setCurrentPayDetAmount(BigDecimal.valueOf(balancePayDetAmount)); } else {
                 * balancePayDetAmount = balancePayDetAmount - balancedAmount;
                 * det.setPaidBillDetAmount(BigDecimal.valueOf(balancedAmount + paidTaxAmount));
                 * det.setBalancePayDetAmount(BigDecimal.valueOf(balancePayDetAmount));
                 * det.setCurrentPayDetAmount(BigDecimal.valueOf(balancedAmount)); balancedAmount = zero.doubleValue(); } }
                 */
                // balancedAmount = balancedAmount - taxAmount;
                rcvblDemandDetList.add(det);
                if (det.getIsDeleted().equals(MainetConstants.FlagN)) {
                    offline.getFeeIds().put(det.getTaxId().longValue(), taxAmount);
                    offline.getBillDetIds().put(det.getTaxId().longValue(), det.getBillDetId().longValue());
                    offline.getSupplimentryBillIdMap().put(det.getTaxId().longValue(), receivableDemandEntryDto.getBillId());
                }
            }
            receivableDemandEntryDto.setRcvblDemandList(rcvblDemandDetList);
            rcvblDemandList.add(receivableDemandEntryDto);
        }

        ChallanReceiptPrintDTO receiptDto = receiptPrintForPayAtUlb(offline);
        updateReceivableDemandEntryList(receiptDto, rcvblDemandList);
        return receiptDto;

    }

    private void updateReceivableDemandEntryList(ChallanReceiptPrintDTO receiptDto,
            List<ReceivableDemandEntryDTO> rcvblDemandList) {
        for (ReceivableDemandEntryDTO receivableDemandEntryDto : rcvblDemandList) {
            receivableDemandEntryDto.setReceiptId(receiptDto.getReceiptId());
            receivableDemandEntryDto = updateReceivableDemandEntry(receivableDemandEntryDto);
        }
    }

    @Transactional
    private ChallanReceiptPrintDTO receiptPrintForPayAtUlb(CommonChallanDTO offline) {
        try {
        	String serviceName= serviceMaster.fetchServiceShortCode(offline.getServiceId(), offline.getOrgId());
            ChallanReceiptPrintDTO printReceiptDto = challanService.savePayAtUlbCounter(offline,serviceName);
            return printReceiptDto;
        } catch (Exception e) {
            LOGGER.error("Exception Occured While Receipt Entry For Supplementary Against :" + offline.getUniquePrimaryId(), e);
            throw new FrameworkException(
                    "Exception Occured While Receipt Entry For Supplementary Against:" + offline.getUniquePrimaryId());
        }
    }

    @Override
    @WebMethod(exclude = true)
    public List<ReceivableDemandEntryDTO> searchSupplementaryBillInfo(String refNumber, String billNo, Long orgId, String wardCode, Long locID) {

        List<Object[]> detailsList = receivableDemandEntryDao.searchDemandEntry(refNumber, billNo, orgId, wardCode, locID);

        List<ReceivableDemandEntryDTO> rcvlDemandList = new ArrayList<>();
        ReceivableDemandEntryDTO rcvlDmndDTO = null;
        for (Object[] billData : detailsList) {
            rcvlDmndDTO = new ReceivableDemandEntryDTO();
            if (billData[5] != null) {
                rcvlDmndDTO.setCreatedDate((Date) billData[5]);
            }
            if (billData[4] != null) {
                rcvlDmndDTO.setBillNo(billData[4].toString());
            }
            if (billData[7] != null) {
                rcvlDmndDTO.setReceiptNo(billData[7].toString());
            }
            if (billData[8] != null) {
                rcvlDmndDTO.setReceiptDate((Date) billData[8]);
            }
            if (billData[6] != null) {
                rcvlDmndDTO.setBillAmount(new BigDecimal(billData[6].toString()));
            }
            if (billData[0] != null) {
                rcvlDmndDTO.setBillId(Long.valueOf(billData[0].toString()));
            }
            rcvlDemandList.add(rcvlDmndDTO);
        }
        return rcvlDemandList;
    }

    @Override
    @Transactional(readOnly = true)
    @WebMethod(exclude = true)
    public ReceivableDemandEntryDTO getBillInfoByBillNo(String billNo, Long orgId) {
        ReceivableDemandEntryDTO dto = new ReceivableDemandEntryDTO();
        ReceivableDemandEntry receivableDemandEntry = receivableDemandEntryRepository.getBillInfoByBillNo(orgId, billNo);
        if (receivableDemandEntry != null) {
            Hibernate.initialize(receivableDemandEntry.getRcvblDemandList());
            dto = receivableDemandEntryMapper.mapEntityToDTO(receivableDemandEntry);
            dto.setActionMode(MainetConstants.ReceivableDemandEntry.GET_BY_ID);
            dto = getByRefNoOrAppNo(dto);
        }

        return dto;
    }

    @SuppressWarnings({ "unchecked", "rawtypes" })
    @Override
    @WebMethod(exclude = true)
    public String printSupplementryBill(Long receiptId, String type) {
        Map oParms = new HashMap();
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        final String jrxmlFileLocation = Filepaths.getfilepath() + MainetConstants.JASPER_REPORT_NAME + MainetConstants.WINDOWS_SLASH;
        String fileName = null;
        if (MainetConstants.ReceivableDemandEntry.IDN.compareTo(type) == 0) {
            fileName = MainetConstants.SupplementaryBillPrintFiles.SUPPLEMENTARY_BILL_PRINT_IDN.getColDescription();
            oParms.put("transactionId", receiptId.toString());
        } else if (MainetConstants.ReceivableDemandEntry.CCN.compareTo(type) == 0) {
            fileName = MainetConstants.SupplementaryBillPrintFiles.SUPPLEMENTARY_BILL_PRINT_CCN.getColDescription();
            oParms.put("transactionId", receiptId.toString());
        }
        final String imgPath = jrxmlFileLocation + MainetConstants.IMAGES + MainetConstants.WINDOWS_SLASH;

        oParms.put("SUBREPORT_DIR", jrxmlFileLocation);
        oParms.put("sReportSourcePath", jrxmlFileLocation);
        oParms.put("imgPath", imgPath);
        return generateJasperReportPDF(type, outputStream, oParms, fileName);
    }

    @SuppressWarnings("rawtypes")
    @Transactional
    @WebMethod(exclude = true)
    public String generateJasperReportPDF(String type, ByteArrayOutputStream outputStream, Map oParms, String fileName) {
        String filePath = "";
        String fileNames = "";
        FileOutputStream fos = null;
        File someFile = null;
        String pdfNameGenarated = null;
        byte[] bytes = receivableDemandEntryDao.generateJasperReportPDF(outputStream, oParms, fileName);
        try {
            if (bytes.length > 1) {
                String genFilName = fileName.substring(0, fileName.length() - 6);
                genFilName = genFilName.replace(MainetConstants.WHITE_SPACE, MainetConstants.operator.UNDER_SCORE);
                fileNames = genFilName + MainetConstants.operator.UNDER_SCORE + type + MainetConstants.operator.UNDER_SCORE + Utility.getTimestamp() + MainetConstants.PDF_EXTENSION;
                filePath = MainetConstants.DirectoryTree.DEFAULT_CACHE_FOLDER + MainetConstants.FILE_PATH_SEPARATOR + fileNames;
                pdfNameGenarated = Filepaths.getfilepath() + filePath;
                someFile = new File(pdfNameGenarated);
                fos = new FileOutputStream(someFile);
                fos.write(bytes);
                fos.flush();
                fos.close();
            }
        } catch (FileNotFoundException e) {
        	  LOGGER.error("File Not Found Exception" , e);    
			throw new FrameworkException("Could not generate report",e);
        } catch (IOException e) {
        	  LOGGER.error("IO Exception" , e);  
 			throw new FrameworkException("Could not generate report",e);
        } finally {
            if (fos != null) {
                try {
                    fos.close();
                } catch (IOException e) {
                	 LOGGER.error("IO Exception while closing FileOutputStream" , e);
                }
            }
        }
        filePath = filePath.replace(MainetConstants.FILE_PATH_SEPARATOR, MainetConstants.operator.FORWARD_SLACE);
        return filePath;
    }

    @Override
    @Transactional(readOnly = true)
    @WebMethod(exclude = true)
    public Object[] getSupplimentryBillDetailsByBillandReceiptId(Long billId, Long receiptId, Long orgId) {
        Object[] billDetails = (Object[]) receivableDemandEntryRepository.getSupplimentryBillDetailsByBillandReceiptId(orgId, receiptId, billId);
        return billDetails;
    }

    @Override
    @WebMethod(exclude = true)
    public VoucherPostDTO getAccountPostingDtoForSupplimentaryBillReversal(TbServiceReceiptMasBean receiptMaster,final Long orgId,final Long userId,final String ipAddress) {

        updateSupplimentaryBillReversal(receiptMaster, orgId, userId, ipAddress);
        VoucherPostDTO voucherPostDTO = new VoucherPostDTO();
        List<VoucherPostDetailDTO> voucherPostdetails = new ArrayList<>();
        Organisation org = new Organisation();
        org.setOrgid(orgId);

        final LookUp dmd = CommonMasterUtility.getValueFromPrefixLookUp(MainetConstants.BILL_MASTER_COMMON.DMD_VALUE, MainetConstants.BILL_MASTER_COMMON.ACCOUNT_TEMPLATE_FOR_PREFIX, org);

        for (TbSrcptFeesDetBean billDet : receiptMaster.getReceiptFeeDetail()) {
            VoucherPostDetailDTO voucherPostDetailDTO = new VoucherPostDetailDTO();
            Long applicableAtId = CommonMasterUtility.getValueFromPrefixLookUp(MainetConstants.ReceivableDemandEntry.SUPPLEMENTRY_BILL,
                    PrefixConstants.LookUp.CHARGE_MASTER_CAA, UserSession.getCurrent().getOrganisation()).getLookUpId();
            Long sacHeadId = tbTaxMasService.fetchSacHeadIdForSupplementryBill(orgId, billDet.getTaxId(), applicableAtId);
            // voucherPostDetailDTO.setDemandTypeId(dmd.getLookUpId());
            voucherPostDetailDTO.setVoucherAmount(billDet.getRfFeeamount());
            voucherPostDetailDTO.setSacHeadId(sacHeadId);
            // voucherPostDetail.setYearId();
            voucherPostdetails.add(voucherPostDetailDTO);
        }
        voucherPostDTO.setVoucherDetails(voucherPostdetails);

        return voucherPostDTO;

    }

    //update Supplementary bill for receipt reversal
    @Transactional
    private void updateSupplimentaryBillReversal(TbServiceReceiptMasBean receiptMaster,final Long orgId,final Long userId,final String ipAddress) {
        Set<Long> billIds = new HashSet<>();
        for (TbSrcptFeesDetBean billDet : receiptMaster.getReceiptFeeDetail()) {
            billIds.add(billDet.getBmIdNo());
        }

        for (Long billId : billIds) {
            ReceivableDemandEntryDTO recvDemandDto = receivableDemandEntryMapper.mapEntityToDTO(receivableDemandEntryRepository.findOne(billId));
            recvDemandDto.setBillPostingDate(null);
            recvDemandDto.setReceiptId(null);
            recvDemandDto.setUpdatedDate(new Date());
            recvDemandDto.setUpdatedBy(userId);
            recvDemandDto.setLgIpMacUpd(ipAddress);
            updateReceivableDemandEntry(recvDemandDto);
        }

    }

    // Updating the Refund and adjustment flag after Decision
    @Override
    @Transactional
    public void updateDepositRefundAdjustmentFlag(List<Long> billIds, final String refundFlag, final String AdjustmentFlag) {
        receivableDemandEntryRepository.updateDepositRefundAdjustmentFlag(billIds, refundFlag, AdjustmentFlag);
    }

}
