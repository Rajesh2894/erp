package com.abm.mainet.mobile.service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Base64;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.regex.Pattern;

import javax.annotation.Resource;

import org.apache.commons.collections.MapUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.json.JSONObject;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.abm.mainet.common.constant.MainetConstants;
import com.abm.mainet.common.constant.MainetConstants.PasswordValidType;
import com.abm.mainet.common.constant.PrefixConstants.Prefix;
import com.abm.mainet.common.constant.ServiceEndpoints;
import com.abm.mainet.common.domain.Department;
import com.abm.mainet.common.domain.Designation;
import com.abm.mainet.common.domain.Employee;
import com.abm.mainet.common.domain.GroupMaster;
import com.abm.mainet.common.domain.Organisation;
import com.abm.mainet.common.exception.FrameworkException;
import com.abm.mainet.common.service.IEmployeeService;
import com.abm.mainet.common.service.IOrganisationService;
import com.abm.mainet.common.util.ApplicationSession;
import com.abm.mainet.common.util.CommonMasterUtility;
import com.abm.mainet.common.util.EncryptionAndDecryption;
import com.abm.mainet.common.util.LookUp;
import com.abm.mainet.common.util.PasswordManager;
import com.abm.mainet.common.util.Utility;
import com.abm.mainet.common.util.UtilityService;
import com.abm.mainet.common.utility.ApplicationContextProvider;
import com.abm.mainet.integration.ws.JersyCall;
import com.abm.mainet.integration.ws.dto.PropertyDetailDto;
import com.abm.mainet.mobile.dao.RegistrationDAO;
import com.abm.mainet.mobile.dto.CommonAppResponseDTO;
import com.abm.mainet.mobile.dto.EmployeeOTPReqDTO;
import com.abm.mainet.mobile.dto.EmployeeRequestDTO;
import com.abm.mainet.mobile.dto.EmployeeResponseDTO;
import com.abm.mainet.mobile.dto.LoginRequestVO;
import com.abm.mainet.mobile.dto.LoginResponseVO;
import com.abm.mainet.mobile.repository.EmployeeRepository;
import com.abm.mainet.mobile.repository.RegistrationServiceRepository;
import com.abm.mainet.smsemail.dto.SMSAndEmailDTO;
import com.abm.mainet.smsemail.service.ISMSAndEmailService;
import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

/**
 * @author umashanker.kanaujiya
 *
 */
@Service
public class RegistrationServiceImpl implements RegistrationService {

    private static final Logger LOG = Logger.getLogger(RegistrationServiceImpl.class);

    @Resource
    private RegistrationServiceRepository serviceRepository;

    @Resource
    private RegistrationDAO registrationDAO;

    @Resource
    EmployeeRepository employeeRepository;

    @Autowired
    private IEmployeeService iEmployeeService;

    @Autowired
    private ISMSAndEmailService ismsAndEmailService;
    
    @Autowired
	private IOrganisationService iOrganisationService;

    @Override
    @Transactional
    public Employee saveEditedUserProfileForMobile(LoginResponseVO loginResponseVO) {
        Employee entity = null;
        try {
            entity = employeeRepository.getEmployee(loginResponseVO.getUserId());
            if (entity.getEmpId() != null) {

                final Date date = new Date();
                entity.setEmpemail(loginResponseVO.getEmailId());
                entity.setTitle(loginResponseVO.getTitleId());
                entity.setEmpGender(loginResponseVO.getGender());
                entity.setEmpdob(loginResponseVO.getDob());
                entity.setEmpname(loginResponseVO.getFirstName());
                entity.setEmpLName(loginResponseVO.getLastName());
                entity.setEmpMName(loginResponseVO.getMiddleName());
                entity.setPincode(loginResponseVO.getPincode().toString());
                entity.setEmpAddress(loginResponseVO.getAddress());
                // entity.setEmpAddress1(loginResponseVO.getCorrAddress());
                entity.setEmpCorAddress1(loginResponseVO.getCorrAddress());
                // entity.setEmpCorAddress2(loginResponseVO.getCorrAddress());
                entity.setUpdatedDate(date);
                entity = employeeRepository.save(entity);
            }
        } catch (final Exception exception) {
            LOG.error("Exception occur in updating user profile", exception);
        }
        return entity;
    }

    @Override
    @Transactional
    public CommonAppResponseDTO doRegistrationService(final EmployeeRequestDTO employeeDTO) {
        long methodStart = System.currentTimeMillis();
        long start = System.currentTimeMillis();
        final Organisation organisations = serviceRepository.getOrganisation(employeeDTO.getOrgId(),
                MainetConstants.STATUS.ACTIVE);
        final List<String> errorList = new ArrayList<>();
        // LOG.info("Inside Service Impl doRegistrationService -->start --> "+(start -System.currentTimeMillis()));
        LOG.info("Inside Service Impl doRegistrationService -->start --> " + getDuration(methodStart));
        final EmployeeResponseDTO responseDTO = new EmployeeResponseDTO();
        try {
            final Employee entity = new Employee();
            start = System.currentTimeMillis();

            LOG.info("Inside Service Impl  --registrationDAO.getEmployee --start--> " + getDuration(start));
            // final boolean flag = registrationDAO.getEmployee(employeeDTO);
            final boolean mobflag = iEmployeeService.isUniqueMobileNumber(employeeDTO.getMobileNo().toString(), null,
                    organisations);
            if (!mobflag) {
            if(employeeDTO !=null && employeeDTO.getLangId() ==1) {
                errorList.add(ApplicationSession.getInstance().getMessage("app.regisration.form.reg.mob.fail.eng"));
            }else {
            	 errorList.add(ApplicationSession.getInstance().getMessage("app.regisration.form.reg.mob.fail.reg"));	
            }
            }
            final boolean emailflag = iEmployeeService.isUniqueEmailAddress(employeeDTO.getEmailId(), null,
                    organisations);
            if (!emailflag) {
				if (employeeDTO !=null && employeeDTO.getLangId() == 1) {
					 errorList.add(ApplicationSession.getInstance().getMessage("app.regisration.form.reg.email.fail.eng"));
				} else {
					 errorList.add(ApplicationSession.getInstance().getMessage("app.regisration.form.reg.email.fail.reg"));
				}
            }
               
            
            LOG.info("Inside Service Impl  --Unique mobile number and email validation check ended--> " + getDuration(start));

            if (mobflag && emailflag) {
                start = System.currentTimeMillis();

                LOG.info("Inside Service Impl  --serviceRepository.getOrganisation --start--> " + getDuration(start));
                final Organisation organisation = serviceRepository.getOrganisation(employeeDTO.getOrgId(),
                        MainetConstants.STATUS.ACTIVE);

                LOG.info("Inside Service Impl  --serviceRepository.getOrganisation --end--> " + getDuration(start));
                start = System.currentTimeMillis();

                LOG.info("Inside Service Impl  --CommonMasterUtility.getLookUps(Prefix.NEC --start--> " + getDuration(start));
                final List<LookUp> list = CommonMasterUtility.getLookUps(Prefix.NEC, organisation);
                if ((list != null) && !list.isEmpty()) {
                    for (final LookUp d : list) {
                        if (d.getLookUpCode().equals(Prefix.CITZEN)) {
                            entity.setEmplType(d.getLookUpId());
                            break;
                        }
                    }

                }
                // start = System.currentTimeMillis();

                LOG.info("Inside Service Impl  --CommonMasterUtility.getLookUps(Prefix.NEC --end--> " + getDuration(start));
                start = System.currentTimeMillis();

                LOG.info("Inside Service Impl  --CommonMasterUtility.getLookUps(Prefix.TITLE --start--> " + getDuration(start));
                if (employeeDTO.getTitle() != null) {
                    final List<LookUp> tlist = CommonMasterUtility.getLookUps(Prefix.TITLE, organisation);

                    if ((tlist != null) && !tlist.isEmpty()) {
                        for (final LookUp d : tlist) {
                            if (d.getLookUpCode().equalsIgnoreCase(employeeDTO.getTitle())) {
                                entity.setTitle(d.getLookUpId());
                                break;
                            }
                        }

                    }
                }

                LOG.info("Inside Service Impl  --CommonMasterUtility.getLookUps(Prefix.TITLE --end--> " + getDuration(start));
                start = System.currentTimeMillis();

                LOG.info("Inside Service Impl  -- CommonMasterUtility.getLookUps(Prefix.GENDER, organisation); --start--> "
                        + getDuration(start));
                final List<LookUp> Glist = CommonMasterUtility.getLookUps(Prefix.GENDER, organisation);

                if ((Glist != null) && !Glist.isEmpty()) {
                    for (final LookUp d : Glist) {
                        if (d.getLookUpCode().equalsIgnoreCase(employeeDTO.getGender())) {
                            entity.setEmpGender(d.getLookUpCode());
                            break;
                        }
                    }

                }

                LOG.info("Inside Service Impl  -- CommonMasterUtility.getLookUps(Prefix.GENDER, organisation); --end--> "
                        + getDuration(start));
                start = System.currentTimeMillis();

                LOG.info("Inside Service Impl  -- serviceRepository.findGpId if--start--> " + getDuration(start));
                final GroupMaster master = serviceRepository.findGpId(
                        MainetConstants.MENU.ORG_CITIZEN.toUpperCase() + employeeDTO.getOrgId(), employeeDTO.getOrgId());
                LOG.info("Inside Service Impl  -- serviceRepository.findGpId if--end--> " + getDuration(start));
                if (master != null) {
                    entity.setGmid(master.getGmId());
                } else {
                    start = System.currentTimeMillis();

                    LOG.info("Inside Service Impl  -- serviceRepository.findGpId -else--start--> " + getDuration(start));
                    final GroupMaster mastert = serviceRepository.findGpId(MainetConstants.MENU.DEFAULT_CITIZEN.toUpperCase(),
                            employeeDTO.getOrgId());
                    LOG.info("Inside Service Impl  -- serviceRepository.findGpId -else---end--> " + getDuration(start));
                    if (mastert != null) {
                        entity.setGmid(mastert.getGmId());
                    }

                }
                start = System.currentTimeMillis();

                LOG.info("Inside Service Impl  -- serviceRepository.getDepartment-----start--> " + getDuration(start));
                final Department department = serviceRepository.getDepartment(MainetConstants.DEPARTMENT.ONLINE_SERVICES_CODE,
                        MainetConstants.STATUS.ACTIVE);

                LOG.info("Inside Service Impl  -- serviceRepository.getDepartment-----ends--> " + getDuration(start));
                start = System.currentTimeMillis();

                LOG.info("Inside Service Impl  -- serviceRepository.getAllDesignationByDesgName-----start--> "
                        + getDuration(start));
                final List<Designation> designationList = serviceRepository
                        .getAllDesignationByDesgName(MainetConstants.AGENCY.NAME.CITIZEN.toUpperCase());

                LOG.info("Inside Service Impl  -- serviceRepository.getAllDesignationByDesgName-----ends--> "
                        + getDuration(start));
                long entitySetupstart = System.currentTimeMillis();

                LOG.info("Inside Service Impl  -- setting up the entity-----starts--> " + getDuration(entitySetupstart));
                if ((designationList != null) && (department != null)) {
                    final Designation designation = designationList.get(0);
                    final Date date = new Date();
                    start = System.currentTimeMillis();

                    LOG.info(
                            "Inside Service Impl  -- UtilityService.generateRandomNumericCode(MainetConstants.OTP_PRASSWORD_LENGTH)-----starts--> "
                                    + getDuration(start));
                    entity.setEmppassword(UtilityService.generateRandomNumericCode(MainetConstants.OTP_PRASSWORD_LENGTH));
                    start = System.currentTimeMillis();

                    LOG.info(
                            "Inside Service Impl  -- UtilityService.generateRandomNumericCode(MainetConstants.OTP_PRASSWORD_LENGTH)-----ends--> "
                                    + getDuration(start));
                    start = System.currentTimeMillis();

                    LOG.info(
                            "Inside Service Impl  -- setMobNoOtp UtilityService.generateRandomNumericCode(MainetConstants.OTP_PRASSWORD_LENGTH)-----starts--> "
                                    + getDuration(start));

                    entity.setMobNoOtp(UtilityService.generateRandomNumericCode(MainetConstants.OTP_PRASSWORD_LENGTH));
                    start = System.currentTimeMillis();

                    LOG.info(
                            "Inside Service Impl  -- setMobNoOtp UtilityService.generateRandomNumericCode(MainetConstants.OTP_PRASSWORD_LENGTH)-----ends--> "
                                    + getDuration(start));

                    entity.setEmpemail(employeeDTO.getEmailId());
                    entity.setEmpdob(employeeDTO.getDob());
                    entity.setEmploginname(String.valueOf(employeeDTO.getMobileNo()));
                    entity.setEmpLName(employeeDTO.getLastName());
                    entity.setEmpmobno(String.valueOf(employeeDTO.getMobileNo()));
                    entity.setEmpname(employeeDTO.getFirstName());
                    entity.setEmpphoneno(String.valueOf(employeeDTO.getMobileNo()));
                    entity.setLmodDate(date);
                    String loginName = ApplicationSession.getInstance().getMessage("citizen.noUser.loginName");

                    start = System.currentTimeMillis();
                    LOG.info("Inside Service Impl  -- iEmployeeService.getEmployeeListByLoginName-----starts--> "
                            + getDuration(start));

                    /*
                     * List<Employee> empList =iEmployeeService.getEmployeeListByLoginName(loginName, organisation, "0");
                     * if(empList != null && !empList.isEmpty()) { entity.setUserId(empList.get(0).getUserId()); }
                     */
                    Employee emp = ApplicationContextProvider.getApplicationContext().getBean(IEmployeeService.class)
                            .getEmployeeByLoginName(loginName, organisation, "0");
                    entity.setUserId(emp.getUserId());

                    LOG.info("Inside Service Impl  -- iEmployeeService.getEmployeeListByLoginName-----ends--> "
                            + getDuration(start));
                    // entity.setUserId(UserSession.getCurrent().getEmployee().getUserId());
                    entity.setOrganisation(organisation);
                    entity.setAutMob(MainetConstants.IsDeleted.NOT_DELETE);
                    entity.setLangId(employeeDTO.getLangId().intValue());
                    entity.setDesignation(designation);
                    entity.setIsUploaded(MainetConstants.IsUploaded.NOT_UPLOADED);
                    entity.setOndate(date);
                    entity.setTbDepartment(department);
                    entity.setIsdeleted(MainetConstants.IsDeleted.ZERO);
                    entity.setAutEmail(MainetConstants.IsDeleted.NOT_DELETE);
                    entity.setEmpMName(employeeDTO.getMiddleName());

                    start = System.currentTimeMillis();

                    LOG.info("Inside Service Impl  -- save----starts--> " + getDuration(start));
                    employeeRepository.save(entity);

                    LOG.info("Inside Service Impl  -- save----ends--> " + getDuration(start));

                    LOG.info("Inside Service Impl  -- setting up the entity-----ends--> " + getDuration(entitySetupstart));
                    if (entity.getEmpId() != null) {
                        responseDTO.setUserId(entity.getEmpId());
                        responseDTO.setOrgId(employeeDTO.getOrgId());
                        responseDTO
                                .setResponseMsg(ApplicationSession.getInstance().getMessage("app.regisration.form.reg.success"));
                        responseDTO.setStatus(MainetConstants.SUCCESS);
                        if (employeeDTO.getMobileNo() != null) {
                            long smsstart = System.currentTimeMillis();
                            LOG.info("Inside Service Impl  -- sendSMSandEMail----start--> " + getDuration(smsstart));
                            sendSMSandEMail(entity, entity.getMobNoOtp(), employeeDTO.getLangId().intValue(), organisation);
                            LOG.info("Inside Service Impl  -- sendSMSandEMail----ends--> " + getDuration(smsstart));
                        }
                    } else {
                        responseDTO.setResponseMsg(ApplicationSession.getInstance().getMessage("app.regisration.form.reg.fail"));
                        responseDTO.setStatus(MainetConstants.FAIL);
                    }
                }
            } else {
                responseDTO.setResponseMsg(String.join(",", errorList));
                responseDTO.setStatus("mobileAlreadyReg");
            }

        } catch (final Exception exception) {
            LOG.error("Exception occur in doRegistrationService", exception);
            responseDTO.setResponseMsg(ApplicationSession.getInstance().getMessage("app.regisration.form.reg.fail"));
            responseDTO.setStatus(MainetConstants.FAIL);
            responseDTO.setErrorMsg(exception.getMessage());
        }
        start = System.currentTimeMillis();

        LOG.info("Inside Service Impl doRegistrationService --end--> " + getDuration(methodStart));
        return responseDTO;
    }

    protected long getDuration(long start) {
        return System.currentTimeMillis() - start;
    }

    @Override
    @Transactional
    public CommonAppResponseDTO doOTPVerificationService(final EmployeeOTPReqDTO otpReqDTO) {
        final CommonAppResponseDTO responseDTO = new CommonAppResponseDTO();
        try {
            final Employee employee = employeeRepository.findOne(otpReqDTO.getUserId());
            Date valiDateTime = null;
            if (employee.getUpdatedDate() != null) {
                valiDateTime = employee.getUpdatedDate();
            } else {
                valiDateTime = employee.getOndate();
            }
            final boolean isValidPeriod = UtilityService.checkOTPValidityPeriod(valiDateTime,
                    MainetConstants.OTP_VALIDITITY_IN_MINS, new Date());

            if (isValidPeriod == true) {
                if ((employee != null) && employee.getMobNoOtp().equals(otpReqDTO.getOtpPass()) && (employee.getAutMob() != null)
                        &&
                        (employee.getAutMob().equalsIgnoreCase(MainetConstants.UNAUTH)
                                || otpReqDTO.getIsregistered().equalsIgnoreCase(MainetConstants.AUTH))) {
                    employee.setAutMob(MainetConstants.AUTH);
                    final Date date = new Date();
                    employee.setUpdatedDate(date);
                    employee.setUpdatedBy(employee.getUserId());
                    employee.setLangId(otpReqDTO.getLangId().intValue());
                    employee.setEmpmobno(String.valueOf(otpReqDTO.getMobileNo()));
                    employeeRepository.save(employee);

                    responseDTO.setStatus(MainetConstants.SUCCESS);
                    if (otpReqDTO.getLangId().intValue() == MainetConstants.NUMBERS.ONE) {
                        responseDTO
                                .setResponseMsg(ApplicationSession.getInstance().getMessage("app.regisration.form.otp.success"));
                    } else {

                        responseDTO
                                .setResponseMsg(ApplicationSession.getInstance().getMessage("app.regisration.form.otp.success"));
                    }
                } else {
                    if (employee != null && !employee.getEmpmobno().equalsIgnoreCase(String.valueOf(otpReqDTO.getMobileNo()))) {
                        responseDTO.setStatus(MainetConstants.FAIL);
                        if (otpReqDTO.getLangId().intValue() == MainetConstants.NUMBERS.ONE) {
                            responseDTO.setResponseMsg(
                                    ApplicationSession.getInstance().getMessage("app.regisration.form.mobile.fail"));
                            responseDTO
                                    .setErrorMsg(ApplicationSession.getInstance().getMessage("app.regisration.form.mobile.fail"));
                        } else {

                            responseDTO.setResponseMsg(
                                    ApplicationSession.getInstance().getMessage("app.regisration.form.mobile.fail"));
                            responseDTO
                                    .setErrorMsg(ApplicationSession.getInstance().getMessage("app.regisration.form.mobile.fail"));
                        }

                    } else if (!((employee != null) && employee.getMobNoOtp().equals(otpReqDTO.getOtpPass()))) {
                        responseDTO.setStatus(MainetConstants.FAIL);
                        if (otpReqDTO.getLangId().intValue() == MainetConstants.NUMBERS.ONE) {
                            responseDTO
                                    .setResponseMsg(ApplicationSession.getInstance().getMessage("app.regisration.form.otp.fail"));
                            responseDTO.setErrorMsg(ApplicationSession.getInstance().getMessage("app.regisration.form.otp.fail"));
                        } else {

                            responseDTO
                                    .setResponseMsg(ApplicationSession.getInstance().getMessage("app.regisration.form.otp.fail"));
                            responseDTO.setErrorMsg(ApplicationSession.getInstance().getMessage("app.regisration.form.otp.fail"));
                        }
                    }
                }
            } else {
                responseDTO.setStatus(MainetConstants.FAIL);
                if (otpReqDTO.getLangId().intValue() == MainetConstants.NUMBERS.ONE) {
                    responseDTO.setResponseMsg(ApplicationSession.getInstance().getMessage("app.regisration.form.otp.expired"));
                    responseDTO.setErrorMsg(ApplicationSession.getInstance().getMessage("app.regisration.form.otp.expired"));
                } else {

                    responseDTO.setResponseMsg(ApplicationSession.getInstance().getMessage("app.regisration.form.otp.expired"));
                    responseDTO.setErrorMsg(ApplicationSession.getInstance().getMessage("app.regisration.form.otp.expired"));
                }
            }
        } catch (final Exception exception) {
            LOG.error("Exception occur in doOTPVerificationService", exception);
            responseDTO.setStatus(MainetConstants.FAIL);
            responseDTO.setErrorMsg(exception.getMessage());
        }
        return responseDTO;

    }

    @Override
    @Transactional
    public EmployeeResponseDTO setPasswordService(final EmployeeOTPReqDTO otpReqDTO) {
        final EmployeeResponseDTO responseDTO = new EmployeeResponseDTO();

        boolean uniqFlag = true;

        Employee employee = null;
        try {
            employee = employeeRepository.getEmployee(String.valueOf(otpReqDTO.getMobileNo()));
        } catch (Exception e) {
            // TODO: handle exception
            uniqFlag = false;
            LOG.error("Exception occur in doOTPVerificationService", e);
            responseDTO.setStatus(MainetConstants.FAIL);
            responseDTO.setErrorMsg(ApplicationSession.getInstance().getMessage("app.regisration.form.pass.duplicate.msg"));
        }
        if (uniqFlag == true) {
            try {
                if (employee != null) {
                    employee.setEmppassword(
                            Utility.encryptPassword(String.valueOf(employee.getEmploginname()), otpReqDTO.getOtpPass()));
                    employee.setEmpexpiredt(
                            PasswordManager.calculatePasswordExpiredDate(PasswordValidType.CITIZEN.getPrifixCode()));
                    final Employee updateEmployee = employeeRepository.save(employee);
                    if (updateEmployee != null) {
                        responseDTO.setStatus(MainetConstants.SUCCESS);
                        responseDTO.setResponseMsg(ApplicationSession.getInstance().getMessage("app.regisration.form.pass.msg"));
                        //D#150385
                        sendSMSAndEmailForResetPassword(otpReqDTO, updateEmployee);

                    } else {
                        responseDTO.setStatus(MainetConstants.FAIL);
                        responseDTO
                                .setResponseMsg(ApplicationSession.getInstance().getMessage("app.regisration.form.pass.failmsg"));
                    }
                } else {
                    responseDTO.setStatus(MainetConstants.FAIL);
                    responseDTO.setResponseMsg(ApplicationSession.getInstance().getMessage("app.regisration.form.pass.mobileno"));
                }
            } catch (final Exception exception) {
                LOG.error("Exception occur in doOTPVerificationService", exception);
                responseDTO.setStatus(MainetConstants.FAIL);
                responseDTO.setErrorMsg(exception.getMessage());
            }
        }
        return responseDTO;

    }

    @Override
    @Transactional
    public EmployeeResponseDTO reSendOTPService(final EmployeeOTPReqDTO otpReqDTO) {

        final EmployeeResponseDTO responseDTO = new EmployeeResponseDTO();
        try {
            final Employee employee = employeeRepository.getEmployee(String.valueOf(otpReqDTO.getMobileNo()));
            if (employee != null) {
                final Organisation organisation = serviceRepository.getOrganisation(otpReqDTO.getOrgId(),
                        MainetConstants.STATUS.ACTIVE);
                final String newAutoGeneratePwd = UtilityService.generateRandomNumericCode(MainetConstants.OTP_PRASSWORD_LENGTH);
                employee.setMobNoOtp(newAutoGeneratePwd);
                final Date date = new Date();
                employee.setUpdatedDate(date);
                employee.setOndate(date);
                employee.setUpdatedBy(employee.getUserId());
                // employee.setAutEmail(MainetConstants.IsDeleted.NOT_DELETE);
                // employee.setAutMob(MainetConstants.IsDeleted.NOT_DELETE);
                // employee.setEmppassword(Utility.encryptPassword(String.valueOf(employee.getEmploginname()),
                // newAutoGeneratePwd));
                employee.setEmpexpiredt(PasswordManager.calculatePasswordExpiredDate(PasswordValidType.CITIZEN.getPrifixCode()));
                final Employee updateEmployee = employeeRepository.save(employee);
                if (updateEmployee != null) {
                    if (otpReqDTO.getMobileNo() != null) {

                        sendSMSandEMail(updateEmployee, updateEmployee.getMobNoOtp(), otpReqDTO.getLangId().intValue(),
                                organisation);
                    }
                    responseDTO.setUserId(updateEmployee.getEmpId());
                    responseDTO.setOrgId(otpReqDTO.getOrgId());
                    responseDTO.setStatus(MainetConstants.SUCCESS);
                    responseDTO.setResponseMsg(
                            ApplicationSession.getInstance().getMessage("app.regisration.form.otp.resend.succssmsg"));

                } else {
                    responseDTO.setStatus(MainetConstants.FAIL);
                    responseDTO.setResponseMsg(
                            ApplicationSession.getInstance().getMessage("app.regisration.form.otp.resend.failmsg"));
                }
            } else {
                responseDTO.setStatus(MainetConstants.FAIL);
                responseDTO.setResponseMsg(ApplicationSession.getInstance().getMessage("app.regisration.form.otp.resend.mobmsg"));
            }
        } catch (final Exception exception) {
            LOG.error("Exception occur in doOTPVerificationService", exception);
            responseDTO.setStatus(MainetConstants.FAIL);
            responseDTO.setResponseMsg(MainetConstants.FAIL);
            responseDTO.setErrorMsg(exception.getMessage());
        }
        return responseDTO;
    }

    @Override
    public LoginResponseVO getAuthenticationProcess(final LoginRequestVO loginRequestVO) {

        final LoginResponseVO loginResponseVO = new LoginResponseVO();
        String uniqueKey  ="";
        Organisation org =null;
        try {

            Employee employeeEntity = null;

            Long orgId = ApplicationSession.getInstance().getSuperUserOrganization().getOrgid();
            final Organisation organisation = serviceRepository.getOrganisation(orgId,
                    MainetConstants.STATUS.ACTIVE);
            final List<LookUp> list = CommonMasterUtility.getLookUps(Prefix.NEC, organisation);
            Long emplType = null;
            if ((list != null) && !list.isEmpty()) {
                for (final LookUp d : list) {
                    if (d.getLookUpCode().equals(Prefix.CITZEN)) {
                        emplType = d.getLookUpId();
                        break;
                    }
                }

            }
            if (emplType == null) {
                loginResponseVO.setHttpstatus(HttpStatus.BAD_REQUEST);
                if (loginRequestVO.getLangId() == 1) {
                    loginResponseVO
                            .setResponseMsg(ApplicationSession.getInstance().getMessage("app.Login.fail.employeeType.eng"));
                } else {
                    loginResponseVO
                            .setResponseMsg(ApplicationSession.getInstance().getMessage("app.Login.fail.employeeType.reg"));
                }
                loginResponseVO.setStatus(MainetConstants.FAIL);
                return loginResponseVO;

			}
             org = iOrganisationService.getOrganisationById(loginRequestVO.getOrgId());
            
            if (Utility.isEnvPrefixAvailable(org,MainetConstants.ENVIRNMENT_VARIABLE.ENV_PSCL)){
            	if (StringUtils.isNotBlank(loginRequestVO.getUniqueKey())) {
    				byte[] decodedBytes = Base64.getDecoder().decode(loginRequestVO.getUniqueKey());
    				 uniqueKey  = new String(decodedBytes);
    			}
            	if (StringUtils.isNotBlank(loginRequestVO.getTimeStamp())) {
            		String timeStamp = EncryptionAndDecryption.decrypt(loginRequestVO.getTimeStamp(),uniqueKey);
    				loginResponseVO.setTimeStamp(timeStamp);
    			}
            	if (StringUtils.isNotBlank(loginRequestVO.getUserName())) {
    				String userName = EncryptionAndDecryption.decrypt(loginRequestVO.getUserName(),uniqueKey);
    				loginRequestVO.setUserName(userName);
    			}
    			if (StringUtils.isNotBlank(loginRequestVO.getPassWord())) {
    				String passWord = EncryptionAndDecryption.decrypt(loginRequestVO.getPassWord(),uniqueKey);
    				loginRequestVO.setPassWord(passWord);
    			}
            }
            if (Pattern.compile(MainetConstants.MOB_PATTERN).matcher(loginRequestVO.getUserName()).matches()) {
                employeeEntity = employeeRepository.getEmployeeMob(loginRequestVO.getUserName(), orgId, emplType);
            } else if (Pattern.compile(MainetConstants.EMAIL_PATTERN).matcher(loginRequestVO.getUserName()).matches()) {
                employeeEntity = employeeRepository.getEmployeeEmail(loginRequestVO.getUserName(), orgId, emplType);
            } else {
                employeeEntity = employeeRepository.getEmployeeUserName(loginRequestVO.getUserName(), orgId, emplType);
            }


            if (employeeEntity != null) {
               //Change code for Login With Mobile no and email
                final String passWord = Utility.encryptPassword(employeeEntity.getEmploginname(), loginRequestVO.getPassWord());
                final Employee validateEmployee = employeeRepository.getAuthentication(employeeEntity.getEmploginname(),
                        orgId, passWord, emplType);
                // Defect #134651 capital and small letters both are allowed to login.
                if (validateEmployee != null && passWord.equals(validateEmployee.getEmppassword())) {
                    if ((list != null) && !list.isEmpty()) {
                        for (final LookUp d : list) {
                            if (d.getLookUpId() == validateEmployee.getEmplType()) {
                                if (loginRequestVO.getLangId() == 1) {
                                    loginResponseVO.setUserType(d.getDescLangFirst());
                                } else {
                                    loginResponseVO.setUserType(d.getDescLangSecond());
                                }
                                break;
                            }
                        }

                    }
                    if (validateEmployee.getTitle() != null) {
                        loginResponseVO.setTitleId(validateEmployee.getTitle());
                        final List<LookUp> tlist = CommonMasterUtility.getLookUps(Prefix.TITLE, organisation);

                        if ((tlist != null) && !tlist.isEmpty()) {
                            for (final LookUp d : tlist) {
                                if (d.getLookUpId() == validateEmployee.getTitle()) {
                                    if (loginRequestVO.getLangId() == 1) {
                                        loginResponseVO.setTitle(d.getDescLangFirst());
                                    } else {
                                        loginResponseVO.setTitle(d.getDescLangSecond());
                                    }
                                    break;
                                }
                            }
                        }
                    }
                    if (validateEmployee.getEmpGender() != null) {
                        final List<LookUp> Glist = CommonMasterUtility.getLookUps(Prefix.GENDER, organisation);

                        if ((Glist != null) && !Glist.isEmpty()) {
                            for (final LookUp d : Glist) {
                                if (d.getLookUpCode().equalsIgnoreCase(validateEmployee.getEmpGender())) {
                                    loginResponseVO.setGender(d.getLookUpCode());
                                    loginResponseVO.setGenderId(d.getLookUpId());
                                    break;
                                }
                            }

                        }
                    }

                    if ((validateEmployee.getLockUnlock() != null)
                            && validateEmployee.getLockUnlock().equals(MainetConstants.NEC.ADVOCATE)) {

                        loginResponseVO.setHttpstatus(HttpStatus.BAD_REQUEST);
                        if (loginRequestVO.getLangId() == 1) {
                            loginResponseVO.setResponseMsg(
                                    ApplicationSession.getInstance().getMessage("app.Login.fail.account.lock.eng"));
                        } else {
                            loginResponseVO.setResponseMsg(
                                    ApplicationSession.getInstance().getMessage("app.Login.fail.account.lock.reg"));
                        }
                        loginResponseVO.setStatus(MainetConstants.FAIL);
                        return loginResponseVO;
                    }
                    if (validateEmployee.getEmpexpiredt() != null && new Date().after(validateEmployee.getEmpexpiredt())) {
                        loginResponseVO.setHttpstatus(HttpStatus.BAD_REQUEST);
                        if (loginRequestVO.getLangId() == 1) {
                            loginResponseVO.setResponseMsg(
                                    ApplicationSession.getInstance().getMessage("app.Login.fail.account.expired.eng"));
                        } else {
                            loginResponseVO.setResponseMsg(
                                    ApplicationSession.getInstance().getMessage("app.Login.fail.account.expired.reg"));
                        }
                        loginResponseVO.setStatus(MainetConstants.FAIL);
                        return loginResponseVO;
                    }
                    loginResponseVO.setAddress(validateEmployee.getEmpAddress());
                    loginResponseVO.setCorrAddress(validateEmployee.getEmpCorAddress1());
                    if (validateEmployee.getPincode() != null) {
                        loginResponseVO.setPincode(Long.valueOf(validateEmployee.getPincode()));
                    }
                    if (validateEmployee.getCorPincode() != null) {
                        loginResponseVO.setCorrPincode(Long.valueOf(validateEmployee.getCorPincode()));
                    }
                    loginResponseVO.setMobileNo(Long.valueOf(validateEmployee.getEmpmobno()));
                    loginResponseVO.setEmailId(validateEmployee.getEmpemail());
                    loginResponseVO.setDob(validateEmployee.getEmpdob());
                    loginResponseVO.setFirstName(validateEmployee.getEmpname());
                    loginResponseVO.setMiddleName(validateEmployee.getEmpMName());
                    loginResponseVO.setLastName(validateEmployee.getEmpLName());
                    loginResponseVO.setUserName(validateEmployee.getEmploginname());
                    loginResponseVO.setStatus(MainetConstants.SUCCESS);
                    loginResponseVO.setOrgId(orgId);
                    loginResponseVO.setUserId(validateEmployee.getEmpId());
                    if (validateEmployee.getIsdeleted().equalsIgnoreCase(MainetConstants.Isdeleted)) {
                        loginResponseVO.setUserStatus(MainetConstants.ACTIVE);
                    } else {
                        loginResponseVO.setUserStatus(MainetConstants.INACTIVE);
                    }
                    loginResponseVO.setHttpstatus(HttpStatus.OK);
                    if (loginRequestVO.getLangId() == 1) {
                        loginResponseVO.setResponseMsg(ApplicationSession.getInstance().getMessage("app.Login.success.eng"));
                    } else {
                        loginResponseVO.setResponseMsg(ApplicationSession.getInstance().getMessage("app.Login.success.reg"));
                    }
                    loginResponseVO.setStatus(MainetConstants.SUCCESS);

                } else {
                    // update the count for locking the account for three successful attempts --> starts

                    // if password not match increment login attempt
                    Integer maxLoginAttempts = null;
                    try {
                        maxLoginAttempts = Integer
                                .valueOf(ApplicationSession.getInstance().getMessage("citizen.max.attempts").trim());
                    } catch (final NumberFormatException e) {
                        LOG.error("Parsing error citizen.max.attempts", e);
                        maxLoginAttempts = 3;
                    }
                    if ((employeeEntity != null && employeeEntity.getLoggedInAttempt() == null)
                            || (employeeEntity != null && employeeEntity.getLoggedInAttempt() < maxLoginAttempts)) {
                        employeeEntity.setLoggedInAttempt(
                                employeeEntity.getLoggedInAttempt() != null ? employeeEntity.getLoggedInAttempt() + 1
                                        : 1);

                    } else {
                        // lock account if max attempted reached.
                        if ((employeeEntity != null) && employeeEntity.getLockUnlock() == null) {
                            employeeEntity.setLockUnlock("L");
                            employeeEntity.setLockDate(new Date());
                        }

                    }
                    iEmployeeService.updateEmpDetails(employeeEntity);

                    // update the count for locking the account for three successful attempts --> ends

                    loginResponseVO.setHttpstatus(HttpStatus.BAD_REQUEST);
                    if(employeeEntity!=null && employeeEntity.getLockUnlock()!=null && employeeEntity.getLockUnlock().equalsIgnoreCase(MainetConstants.FlagL)) {
                    	 if (loginRequestVO.getLangId() == 1) {
                             loginResponseVO
                                     .setResponseMsg(ApplicationSession.getInstance().getMessage("app.Login.fail.account.lock.eng"));
                         } else {
                             loginResponseVO
                                     .setResponseMsg(ApplicationSession.getInstance().getMessage("app.Login.fail.account.lock.reg"));
                         }	
                    }else {
                    if (loginRequestVO.getLangId() == 1) {
                        loginResponseVO
                                .setResponseMsg(ApplicationSession.getInstance().getMessage("app.Login.fail.password.eng"));
                    } else {
                        loginResponseVO
                                .setResponseMsg(ApplicationSession.getInstance().getMessage("app.Login.fail.password.reg"));
                    }
                    }
                    loginResponseVO.setStatus(MainetConstants.FAIL);

                }
            } else {
                loginResponseVO.setHttpstatus(HttpStatus.BAD_REQUEST);
                if (loginRequestVO.getLangId() == 1) {
                    loginResponseVO.setResponseMsg(ApplicationSession.getInstance().getMessage("app.Login.fail.moibelno.eng"));
                } else {
                    loginResponseVO.setResponseMsg(ApplicationSession.getInstance().getMessage("app.Login.fail.moibelno.reg"));
                }
                loginResponseVO.setStatus(MainetConstants.FAIL);
            }

        } catch (final Exception exception) {
            LOG.error("Exception occur in citizenAuthenticationProcess() ", exception);
            loginResponseVO.setHttpstatus(HttpStatus.NO_CONTENT);
            loginResponseVO.setErrorMsg(exception.getMessage().trim());
            loginResponseVO.setResponseMsg(MainetConstants.FAIL);
            loginResponseVO.setStatus(MainetConstants.FAIL);
        }
        
		if (org != null && Utility.isEnvPrefixAvailable(org, MainetConstants.ENVIRNMENT_VARIABLE.ENV_PSCL)) {

			ObjectMapper objectMapper = new ObjectMapper();
			String jsonString = null;
			try {
				jsonString = objectMapper.writeValueAsString(loginResponseVO);
			} catch (JsonProcessingException e) {
				e.printStackTrace();
			}
			String encryptedDTO = EncryptionAndDecryption.encrypt(jsonString, uniqueKey);
			final LoginResponseVO temp = new LoginResponseVO();
			temp.setUserStatus(encryptedDTO);
			return temp;
		}
        return loginResponseVO;
    }

    private void sendSMSandEMail(final Employee registeredEmployee, final String newAutoGeneratePwd, final int langId,
            final Organisation organisation) {

        final SMSAndEmailDTO dto = new SMSAndEmailDTO();
        dto.setEmail(registeredEmployee.getEmpemail());
        dto.setMobnumber(registeredEmployee.getEmpmobno());
        dto.setAppNo(newAutoGeneratePwd);
        dto.setAppName(registeredEmployee.getFullName());
        if (langId == MainetConstants.ENGLISH) {
            dto.setV_muncipality_name(organisation.getONlsOrgname());
        } else {
            dto.setV_muncipality_name(organisation.getONlsOrgnameMar());
        }
        ismsAndEmailService.sendEmailSMS(MainetConstants.DEPT_SHORT_NAME.ONLINE_SERVICE, MainetConstants.EIP_CHKLST.CITIZEN,
                MainetConstants.SMS_EMAIL.OTP_MSG, dto, organisation, langId);

    }

    @Override
    @Transactional
    public EmployeeResponseDTO reSendOTPServiceForMobile(final EmployeeOTPReqDTO otpReqDTO) {

        final EmployeeResponseDTO responseDTO = new EmployeeResponseDTO();
        try {
            final Employee employee = employeeRepository.getEmployee(String.valueOf(otpReqDTO.getMobileNo()));
            if (employee != null) {
                final Organisation organisation = serviceRepository.getOrganisation(otpReqDTO.getOrgId(),
                        MainetConstants.STATUS.ACTIVE);
                final String newAutoGeneratePwd = UtilityService.generateRandomNumericCode(MainetConstants.OTP_PRASSWORD_LENGTH);
                employee.setMobNoOtp(newAutoGeneratePwd);
                final Date date = new Date();
                employee.setUpdatedDate(date);
                employee.setOndate(date);
                employee.setUpdatedBy(employee.getUserId());
                // employee.setAutEmail(MainetConstants.IsDeleted.NOT_DELETE);
                // employee.setAutMob(MainetConstants.IsDeleted.NOT_DELETE);
                // employee.setEmppassword(Utility.encryptPassword(String.valueOf(employee.getEmploginname()),
                // newAutoGeneratePwd));
                final Employee updateEmployee = employeeRepository.save(employee);
                if (updateEmployee != null) {
                    if (otpReqDTO.getMobileNo() != null) {

                        sendSMSandEMail(updateEmployee, updateEmployee.getMobNoOtp(), otpReqDTO.getLangId().intValue(),
                                organisation);
                    }
                    responseDTO.setUserId(updateEmployee.getEmpId());
                    responseDTO.setOrgId(otpReqDTO.getOrgId());
                    responseDTO.setStatus(MainetConstants.SUCCESS);
                    responseDTO.setResponseMsg(
                            ApplicationSession.getInstance().getMessage("app.regisration.form.otp.resend.succssmsg"));

                } else {
                    responseDTO.setStatus(MainetConstants.FAIL);
                    responseDTO.setResponseMsg(
                            ApplicationSession.getInstance().getMessage("app.regisration.form.otp.resend.failmsg"));
                }
            } else {
                responseDTO.setStatus(MainetConstants.FAIL);
                responseDTO.setResponseMsg(ApplicationSession.getInstance().getMessage("app.regisration.form.otp.resend.mobmsg"));
            }
        } catch (final Exception exception) {
            LOG.error("Exception occur in doOTPVerificationService", exception);
            responseDTO.setStatus(MainetConstants.FAIL);
            responseDTO.setResponseMsg(MainetConstants.FAIL);
            responseDTO.setErrorMsg(exception.getMessage());
        }
        return responseDTO;
    }

    @Override
    public String validateInput(LoginResponseVO vo) {
        final StringBuilder builder = new StringBuilder();
        if ((vo.getTitle() == null) || vo.getTitle().isEmpty()) {
            builder.append("Title");
        }
        if ((vo.getFirstName() == null) || vo.getFirstName().isEmpty()) {
            builder.append("First Name");
        }
        if ((vo.getLastName() == null) || vo.getLastName().isEmpty()) {
            builder.append("Last Name");
        }
        if ((vo.getGender() == null) || vo.getGender().isEmpty()) {
            builder.append("Gender");
        }
        if ((vo.getMobileNo() == null) || vo.getMobileNo() == 0l) {
            builder.append("Mobile");
        }
        if ((vo.getAddress() == null) || vo.getAddress().isEmpty()) {
            builder.append("Address");
        }
        if ((vo.getPincode() == null) || vo.getPincode() == 0l) {
            builder.append("Pincode");
        }
        if (!builder.toString().isEmpty()) {
            builder.append(ApplicationSession.getInstance().getMessage("accounts.receipt.receiptmode.null"));
        }
        return builder.toString();
    }

    @SuppressWarnings("unchecked")
    @Override
    public PropertyDetailDto updateOTPServiceForAssessment(String mobileNo) {

        final LinkedHashMap<Long, Object> responseVo = (LinkedHashMap<Long, Object>) JersyCall.callRestTemplateClient(mobileNo,
                ServiceEndpoints.PROPERTY_URL.PROPERTY_UPDATE_OTP_BY_MOBILE + mobileNo);
        if (MapUtils.isNotEmpty(responseVo)) {
            final String d = new JSONObject(responseVo).toString();

            try {
                return new ObjectMapper().readValue(d,
                        PropertyDetailDto.class);
            } catch (JsonParseException e) {
                throw new FrameworkException(e);
            } catch (JsonMappingException e) {
                throw new FrameworkException(e);
            } catch (IOException e) {
                throw new FrameworkException(e);
            }
        }
        return null;
    }

    @Override
    public CommonAppResponseDTO doOTPVerificationForAssessment(EmployeeOTPReqDTO otpReqDTO) {
        final EmployeeResponseDTO responseDTO = new EmployeeResponseDTO();
        try {
            PropertyDetailDto response = checkForValidOTPNo(otpReqDTO.getMobileNo().toString());
            if (response != null && MainetConstants.SUCCESS.equalsIgnoreCase(response.getStatus())) {
                Date valiDateTime = null;
                if (response.getUpdatedDate() != null) {
                    valiDateTime = response.getUpdatedDate();
                }
                final boolean isValidPeriod = UtilityService.checkOTPValidityPeriod(valiDateTime,
                        MainetConstants.OTP_VALIDITITY_IN_MINS, new Date());
                if (isValidPeriod == true) {
                    if (StringUtils.equals(response.getOtp(), otpReqDTO.getOtpPass())) {
                        responseDTO.setOrgId(otpReqDTO.getOrgId());
                        responseDTO.setStatus(MainetConstants.SUCCESS);
                        responseDTO.setResponseMsg(ApplicationSession.getInstance().getMessage("otp.resend.succssmsg"));
                    } else {
                        responseDTO.setStatus(MainetConstants.FAIL);
                        responseDTO.setResponseMsg(ApplicationSession.getInstance().getMessage("otp.resend.failmsg"));
                    }
                } else {
                    responseDTO.setStatus(MainetConstants.FAIL);
                    responseDTO.setResponseMsg(ApplicationSession.getInstance().getMessage("otp.password.expired"));
                }
            } else {
                responseDTO.setStatus(MainetConstants.FAIL);
                responseDTO.setErrorMsg(ApplicationSession.getInstance().getMessage("otp.resend.fail"));
                responseDTO.setResponseMsg(ApplicationSession.getInstance().getMessage("otp.resend.fail"));
            }
        } catch (final Exception exception) {
            LOG.error("Exception occur in doOTPVerificationServiceForMobile", exception);
            responseDTO.setStatus(MainetConstants.FAIL);
            responseDTO.setResponseMsg(MainetConstants.FAIL);
            responseDTO.setErrorMsg(exception.getMessage());
        }
        return responseDTO;
    }

    @Override
    @Transactional
    public EmployeeResponseDTO sendOTPServiceForAssessment(final EmployeeOTPReqDTO otpReqDTO) {

        final EmployeeResponseDTO responseDTO = new EmployeeResponseDTO();
        try {
            final Organisation organisation = serviceRepository.getOrganisation(otpReqDTO.getOrgId(),
                    MainetConstants.STATUS.ACTIVE);
            PropertyDetailDto response = updateOTPServiceForAssessment(otpReqDTO.getMobileNo().toString());
            if (response != null && MainetConstants.SUCCESS.equalsIgnoreCase(response.getStatus())) {
                if (response.getOtp() != null) {
                    LOG.info("OTP for mobile " + otpReqDTO.getMobileNo() + " : " + response.getOtp());
                    sendOTPEmailAndSMS(response, response.getOtp(), organisation,
                            otpReqDTO.getLangId().intValue());
                }
                responseDTO.setOrgId(otpReqDTO.getOrgId());
                responseDTO.setStatus(MainetConstants.SUCCESS);
                responseDTO.setResponseMsg(ApplicationSession.getInstance().getMessage("otp.send.succss"));
            } else {
                responseDTO.setStatus(MainetConstants.FAIL);
                responseDTO.setErrorMsg(ApplicationSession.getInstance().getMessage("mobie.not.registered.or.invalid"));
                responseDTO.setResponseMsg(ApplicationSession.getInstance().getMessage("otp.send.fail"));
            }
        } catch (final Exception exception) {
            LOG.error("Exception occur in sendOTPServiceForAssessment", exception);
            responseDTO.setStatus(MainetConstants.FAIL);
            responseDTO.setResponseMsg(MainetConstants.FAIL);
            responseDTO.setErrorMsg(exception.getMessage());
        }
        return responseDTO;
    }

    private void sendOTPEmailAndSMS(PropertyDetailDto detailDto, final String otp, Organisation organisation, final int langId) {
        final SMSAndEmailDTO dto = new SMSAndEmailDTO();
        if (detailDto.getOwnerEmail() != null) {
            dto.setEmail(detailDto.getOwnerEmail());
        }
        dto.setMobnumber(detailDto.getPrimaryOwnerMobNo());
        dto.setAppName(detailDto.getPrimaryOwnerName());
        dto.setOneTimePassword(otp);
        if (langId == MainetConstants.ENGLISH) {
            dto.setV_muncipality_name(organisation.getONlsOrgname());
        } else {
            dto.setV_muncipality_name(organisation.getONlsOrgnameMar());
        }

        ismsAndEmailService.sendEmailSMS(MainetConstants.DEPT_SHORT_NAME.ONLINE_SERVICE, "NoChangeInAssessment.html",
                MainetConstants.SMS_EMAIL.OTP_MSG, dto, organisation, langId);
    }

    @SuppressWarnings("unchecked")
    @Override
    public PropertyDetailDto checkForValidOTPNo(String mobileNo) {
        final LinkedHashMap<Long, Object> responseVo = (LinkedHashMap<Long, Object>) JersyCall.callRestTemplateClient(mobileNo,
                ServiceEndpoints.PROPERTY_URL.PROPERTY_FETCH_OTP_BY_MOBILE + mobileNo);
        if (MapUtils.isNotEmpty(responseVo)) {
            final String d = new JSONObject(responseVo).toString();
            try {
                return new ObjectMapper().readValue(d,
                        PropertyDetailDto.class);
            } catch (JsonParseException e) {
                throw new FrameworkException(e);
            } catch (JsonMappingException e) {
                throw new FrameworkException(e);
            } catch (IOException e) {
                throw new FrameworkException(e);
            }
        }
        return null;
    }
    private void sendSMSAndEmailForResetPassword(EmployeeOTPReqDTO otpReqDTO, Employee emp) {
		final SMSAndEmailDTO dto = new SMSAndEmailDTO();
		int langId=1;
		final Organisation organisation = serviceRepository.getOrganisation(otpReqDTO.getOrgId(),
				MainetConstants.STATUS.ACTIVE);
		if (emp.getEmpemail() != null) {
			dto.setEmail(emp.getEmpemail());
		}
		if (otpReqDTO.getMobileNo() != null)
			dto.setMobnumber(otpReqDTO.getMobileNo().toString());
		if(otpReqDTO.getLangId()!=null)
			langId=	otpReqDTO.getLangId().intValue();
		dto.setPassword(otpReqDTO.getOtpPass());
		ismsAndEmailService.sendEmailSMS(MainetConstants.DEPT_SHORT_NAME.ONLINE_SERVICE, "CitizenResetPassword.html",
				MainetConstants.SMS_EMAIL.GENERAL_MSG, dto, organisation, otpReqDTO.getLangId().intValue());
	}
}
