/**
 *
 */
package com.abm.mainet.cfc.challan.ui.model;

import java.lang.reflect.Method;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;
import org.springframework.util.ClassUtils;
import org.springframework.util.ReflectionUtils;

import com.abm.mainet.cfc.challan.dto.ChallanDetailsDTO;
import com.abm.mainet.cfc.challan.dto.ChallanReceiptPrintDTO;
import com.abm.mainet.cfc.challan.dto.ChallanRegenerateDTO;
import com.abm.mainet.cfc.challan.service.IChallanAtULBCounterService;
import com.abm.mainet.cfc.challan.service.IChallanService;
import com.abm.mainet.cfc.challan.ui.validator.CommonOfflineMasterValidator;
import com.abm.mainet.common.constant.MainetConstants;
import com.abm.mainet.common.domain.CFCApplicationAddressEntity;
import com.abm.mainet.common.domain.Employee;
import com.abm.mainet.common.dto.CommonChallanDTO;
import com.abm.mainet.common.integration.acccount.domain.TbServiceReceiptMasEntity;
import com.abm.mainet.common.master.dto.TbServicesMst;
import com.abm.mainet.common.master.service.DepartmentService;
import com.abm.mainet.common.master.service.TbServicesMstService;
import com.abm.mainet.common.service.ICFCApplicationAddressService;
import com.abm.mainet.common.ui.model.AbstractFormModel;
import com.abm.mainet.common.utility.ApplicationContextProvider;
import com.abm.mainet.common.utility.UserSession;

/**
 * @author Lalit.Prusti
 *
 */
@Component
@Scope(value = "session")
public class ChallanAtULBCounterModel extends
        AbstractFormModel {

    private static final long serialVersionUID = -4698420347491208550L;

    @Autowired
    private IChallanAtULBCounterService iChallanAtULBCounterService;

    @Autowired
    private IChallanService iChallanService;

    @Autowired
    private ICFCApplicationAddressService iCFCApplicationAddressService;
    

    @Autowired
    private DepartmentService departmentService;
    
    @Autowired
    private TbServicesMstService iTbServicesMstService;

    
    @Autowired
    private MessageSource messageSource;
    
    private Long applicationNo;
    private Long challanNo;
    private ChallanDetailsDTO challanDetails;
    private CommonChallanDTO offline;

    private String pageUrlFlag;

    private boolean expiredFlag;

    @Override
    public boolean saveForm() {
        boolean result = false;
        final CommonChallanDTO offlineMaster = getOffline();
        Employee emp = UserSession.getCurrent().getEmployee();
        offlineMaster
                .setOnlineOfflineCheck(MainetConstants.PAYMENT.PAY_AT_ULB_COUNTER);
        validateBean(getOffline(),
                CommonOfflineMasterValidator.class);
        if (!hasValidationErrors()) {
            offlineMaster.setLgIpMac(UserSession.getCurrent().getEmployee().getEmppiservername());
            final TbServiceReceiptMasEntity serviceReceiptMaster = iChallanAtULBCounterService
                    .updateChallanDetails(
                            challanDetails,
                            offlineMaster, UserSession
                                    .getCurrent()
                                    .getOrganisation(),
                            this.getTaskId(), emp.getEmplType(), emp.getEmpId(), emp.getEmpname());
            
            /*change by Suhel Chaudhry
             *  receipt genrated for muncipale Property base on servcie Code Defect #34415
             *  code review By Saurab
             * */
            TbServicesMst serviceMst=iTbServicesMstService.findById(offline.getServiceId());
            String serviceCode=serviceMst.getSmShortdesc();
            String deptCode = departmentService.getDeptCode(serviceMst.getTbDepartment().getDpDeptid());
            if (serviceCode.equals(MainetConstants.RNL_ESTATE_BOOKING)) {
                Long appId = serviceReceiptMaster.getApmApplicationId();
                long orgid = serviceReceiptMaster.getOrgId();
                Class<?> clazz = null;
                Object dynamicServiceInstance = null;
                CommonChallanDTO dto = null;
                try {
                    String serviceClassName = null;
                    serviceClassName = "com.abm.mainet.rnl.service.EstateBookingServiceImpl";// messageSource.getMessage(MainetConstants.CHALLAN_BILL
                                                                                             // + deptCode, new Object[] {},
                    // StringUtils.EMPTY, Locale.ENGLISH);
                    if (serviceClassName != null && !MainetConstants.BLANK.equals(serviceClassName)) {
                        clazz = ClassUtils.forName(serviceClassName,
                                ApplicationContextProvider.getApplicationContext().getClassLoader());
                        dynamicServiceInstance = ApplicationContextProvider.getApplicationContext()
                                .getAutowireCapableBeanFactory()
                                .autowire(clazz, 2, false);

                        final Method method = ReflectionUtils.findMethod(clazz,
                                MainetConstants.CHALLAN_VERIFICATION,
                                new Class[] { Long.class, Long.class });

                        dto = (CommonChallanDTO) ReflectionUtils.invokeMethod(method,
                                dynamicServiceInstance, new Object[] { orgid, appId });
                        Date toDate = dto.getFromedate();
                        Date fromdate = dto.getToDate();
                        // second Reflection call for property name
                        Class<?> clazz1 = null;
                        clazz1 = ClassUtils.forName(serviceClassName,
                                ApplicationContextProvider.getApplicationContext().getClassLoader());
                        Object dynamicServiceInstanceNew = ApplicationContextProvider.getApplicationContext()
                                .getAutowireCapableBeanFactory()
                                .autowire(clazz1, 2, false);

                        final Method method1 = ReflectionUtils.findMethod(clazz1,
                                MainetConstants.NARATION_PROPERTY,
                                new Class[] { Long.class, Long.class });

                        CommonChallanDTO dto1 = (CommonChallanDTO) ReflectionUtils.invokeMethod(method1,
                                dynamicServiceInstanceNew, new Object[] { appId, orgid });
                        String propName = dto1.getPropName();
                        offline.setFromedate(toDate);
                        offline.setToDate(fromdate);
                        offline.setFaYearId(UserSession.getCurrent().getFinYearId());
                        offline.setServiceName(propName);
                        offline.setApplicantAddress(challanDetails.getAddress());
                        final ChallanReceiptPrintDTO printDTO = iChallanService.setValuesAndPrintReport(serviceReceiptMaster,
                                offline);
                        setReceiptDTO(printDTO);
                        setSuccessMessage(getAppSession().getMessage("challan.rcpt.print"));
                        result = true;
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            } //123943 Added to get License details on receipt 
               else if (deptCode.equals(MainetConstants.TradeLicense.MARKET_LICENSE)) {
            	Class<?> clazz = null;
            	Object dynamicServiceInstance = null;
            	CommonChallanDTO dto = null;
				Long  applicationId = serviceReceiptMaster.getApmApplicationId();
				Long orgId = serviceReceiptMaster.getOrgId();
            	final String serviceName = "com.abm.mainet.tradeLicense.service.TradeLicenseApplicationServiceImpl";
            	//Defect #111802
            	if (!StringUtils.isEmpty(serviceName)) {
            		try {
            		clazz = ClassUtils.forName(serviceName,
            				ApplicationContextProvider.getApplicationContext().getClassLoader());
            		dynamicServiceInstance = ApplicationContextProvider.getApplicationContext().getAutowireCapableBeanFactory()
            				.autowire(clazz, 2, false);
            		final Method method = ReflectionUtils.findMethod(clazz,
            				"getLicenseDetails",
            				new Class[] { Long.class, Long.class });
            		dto = (CommonChallanDTO) ReflectionUtils.invokeMethod(method,
                            dynamicServiceInstance, new Object[] {applicationId , orgId });
            		if (dto != null)
            		offline.setLicNo(dto.getLicNo());
            		offline.setFromedate(dto.getFromedate());
                    offline.setToDate(dto.getToDate());
                    offline.setApplicantAddress(challanDetails.getAddress());
            		final ChallanReceiptPrintDTO printDTO = iChallanService.setValuesAndPrintReport(serviceReceiptMaster,
                            offline);
                    setReceiptDTO(printDTO);
                    setSuccessMessage(getAppSession().getMessage("challan.rcpt.print"));
                    result = true;
            	 } catch (Exception e) {
                     e.printStackTrace();
                 }
            	}
       
            } else if (serviceReceiptMaster != null) {
            	offline.setFaYearId(UserSession.getCurrent().getFinYearId());
                final ChallanReceiptPrintDTO printDTO = iChallanService.setValuesAndPrintReport(serviceReceiptMaster, offline);
                setReceiptDTO(printDTO);
                setSuccessMessage(getAppSession().getMessage("challan.rcpt.print"));
                result = true;
            }
        }
        return result;
    }

    public void searchChallanDetails(String payStatus) {
        if ((null == applicationNo) && (null == challanNo)) {
            addValidationError(getAppSession().getMessage(
                    "search.criteria"));
        } else {
            final long orgId = UserSession.getCurrent()
                    .getOrganisation().getOrgid();
            final long langId = UserSession.getCurrent()
                    .getLanguageId();
            final ChallanDetailsDTO challan = iChallanAtULBCounterService
                    .getChallanDetails(challanNo,
                            applicationNo,payStatus, orgId, langId);
            if (null != challan) {
                setChallanDetails(challan);
                final SimpleDateFormat dateFormatter = new SimpleDateFormat(MainetConstants.COMMON_DATE_FORMAT);
                Date today = null;
                Date challanExpDate = null;
                String challanExpDateStr = null;
                try {
                    today = dateFormatter.parse(dateFormatter.format(new Date()));
                    challanExpDateStr = dateFormatter.format(challan.getChallanExpierdDate());
                    challanExpDate = dateFormatter.parse(challanExpDateStr);
                } catch (final ParseException e) {
                    e.printStackTrace();
                }
                if (today.after(challanExpDate)) {
                    setExpiredFlag(true);
                    addValidationError(getAppSession().getMessage("Challan has expired on date: " + challanExpDateStr));
                }
            } else {
                addValidationError(getAppSession().getMessage("challan.noRecord"));
            }
        }
    }

    public Long getApplicationNo() {
        return applicationNo;
    }

    public void setApplicationNo(final Long applicationNo) {
        this.applicationNo = applicationNo;
    }

    public Long getChallanNo() {
        return challanNo;
    }

    public void setChallanNo(final Long challanNo) {
        this.challanNo = challanNo;
    }

    public ChallanDetailsDTO getChallanDetails() {
        return challanDetails;
    }

    public void setChallanDetails(
            final ChallanDetailsDTO challanDetails) {
        this.challanDetails = challanDetails;
    }

    public CommonChallanDTO getOffline() {
        return offline;
    }

    public void setOffline(final CommonChallanDTO offline) {
        this.offline = offline;
    }

    public String getPageUrlFlag() {
        return pageUrlFlag;
    }

    public void setPageUrlFlag(final String pageUrlFlag) {
        this.pageUrlFlag = pageUrlFlag;
    }

    public boolean isExpiredFlag() {
        return expiredFlag;
    }

    public void setExpiredFlag(final boolean expiredFlag) {
        this.expiredFlag = expiredFlag;
    }

    /**
     * @param applicationNo
     * @param challanNo
     * @return
     * @throws LinkageError
     * @throws ClassNotFoundException
     */
    public boolean regenerateChallan() throws ClassNotFoundException, LinkageError {
        boolean result = false;
        final ChallanRegenerateDTO dto = iChallanAtULBCounterService.calculateChallanAmount(getChallanDetails());
        if (dto != null) {
            final CommonChallanDTO challanData = iChallanAtULBCounterService.regenerateChallanAndInactiveOld(getChallanDetails(),
                    dto);
            final CFCApplicationAddressEntity address = iCFCApplicationAddressService
                    .getApplicationAddressByAppId(challanData.getApplNo(), UserSession.getCurrent().getOrganisation().getOrgid());
            challanData.setEmailId(address.getApaEmail());
            challanData.setMobileNumber(address.getApaMobilno());
            setApplicationNo(challanData.getApplNo());
            setChallanNo(Long.valueOf(challanData.getChallanNo()));
            setOfflineDTO(challanData);
            result = true;
        }
        return result;
    }

}
