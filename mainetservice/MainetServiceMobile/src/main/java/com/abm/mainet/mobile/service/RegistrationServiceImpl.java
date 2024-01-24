package com.abm.mainet.mobile.service;

import java.util.List;
import java.util.regex.Pattern;

import javax.annotation.Resource;

import org.apache.log4j.Logger;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import com.abm.mainet.common.constant.MainetConstants;
import com.abm.mainet.common.constant.PrefixConstants;
import com.abm.mainet.common.domain.Designation;
import com.abm.mainet.common.domain.Employee;
import com.abm.mainet.common.domain.GroupMaster;
import com.abm.mainet.common.domain.LocationMasEntity;
import com.abm.mainet.common.domain.Organisation;
import com.abm.mainet.common.master.service.GroupMasterService;
import com.abm.mainet.common.utility.ApplicationSession;
import com.abm.mainet.common.utility.CommonMasterUtility;
import com.abm.mainet.common.utility.LookUp;
import com.abm.mainet.common.utility.Utility;
import com.abm.mainet.mobile.dto.LoginRequestVO;
import com.abm.mainet.mobile.dto.LoginResponseVO;
import com.abm.mainet.mobile.repository.EmployeeRepository;

/**
 * @author umashanker.kanaujiya
 *
 */
@Service
public class RegistrationServiceImpl implements RegistrationService {

    /*
     * (non-Javadoc)
     * @see
     * com.abm.mainetsource.rest.collection.service.RegistrationService#validateInput(com.abm.mainetsource.rest.collection.bean.
     * EmployeeDTO, java.lang.String)
     */
    private static final Logger LOG = Logger.getLogger(RegistrationServiceImpl.class);

    @Resource
    private EmployeeRepository employeeRepository;
    @Resource
    private GroupMasterService groupMasterService;

    @Override
    public LoginResponseVO getAuthenticationProcess(final LoginRequestVO loginRequestVO) {

        LOG.info("starts getAuthenticationProcess  in MainetLoginServiceImpl class");
        final LoginResponseVO loginResponseVO = new LoginResponseVO();
        try {
            Employee employeeEntity = null;
            if (Pattern.compile(MainetConstants.MOB_PATTERN).matcher(loginRequestVO.getUserName()).matches()) {
                employeeEntity = employeeRepository.getEmployeeMob(loginRequestVO.getUserName(), loginRequestVO.getOrgId());
            } else if (Pattern.compile(MainetConstants.EMAIL_PATTERN).matcher(loginRequestVO.getUserName()).matches()) {
                employeeEntity = employeeRepository.getEmployeeEmail(loginRequestVO.getUserName(), loginRequestVO.getOrgId());
            } else {
                employeeEntity = employeeRepository.getEmployeeUserName(loginRequestVO.getUserName(), loginRequestVO.getOrgId());
            }

            final String passWord = Utility.encryptPassword(loginRequestVO.getUserName(), loginRequestVO.getPassWord());

            if (employeeEntity != null) {
                final Employee validateEmployee = employeeRepository.getAuthentication(employeeEntity.getEmploginname(),
                        loginRequestVO.getOrgId(), passWord);

                if (validateEmployee != null) {
                    final Organisation organisation = new Organisation();
                    organisation.setOrgid(loginRequestVO.getOrgId());
                    GroupMaster groupMaster = groupMasterService.findByGmId(validateEmployee.getGmid(),
                            loginRequestVO.getOrgId());
                    final List<LookUp> list = CommonMasterUtility.getLookUps(PrefixConstants.MobilePreFix.NEC, organisation);
                    if ((list != null) && !list.isEmpty()) {
                        for (final LookUp d : list) {

                            if ((validateEmployee.getEmplType() != null)
                                    && (d.getLookUpId() == validateEmployee.getEmplType())) {
                                if (loginRequestVO.getLangId() == 1) {
                                    loginResponseVO.setUserType(d.getDescLangFirst());
                                } else {
                                    loginResponseVO.setUserType(d.getDescLangSecond());
                                }

                                break;
                            }
                        }

                    }
                    if (validateEmployee.getCpdTtlId() != null) {
                        final List<LookUp> tlist = CommonMasterUtility.getLookUps(PrefixConstants.MobilePreFix.TITLE,
                                organisation);

                        if ((tlist != null) && !tlist.isEmpty()) {
                            for (final LookUp d : tlist) {
                                if (d.getLookUpId() == validateEmployee.getCpdTtlId()) {
                                    ;
                                }
                                {

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
                        final List<LookUp> Glist = CommonMasterUtility.getLookUps(PrefixConstants.MobilePreFix.GENDER,
                                organisation);

                        if ((Glist != null) && !Glist.isEmpty()) {
                            for (final LookUp d : Glist) {
                                if (d.getLookUpCode().equalsIgnoreCase(validateEmployee.getEmpGender())) {
                                    ;
                                }
                                {
                                    // loginResponseVO.setGender(d.getLookUpCode());
                                    if (loginRequestVO.getLangId() == 1) {
                                        loginResponseVO.setGender(d.getDescLangFirst());
                                    } else {
                                        loginResponseVO.setGender(d.getDescLangSecond());
                                    }

                                    break;
                                }
                            }

                        }
                    }
                    Designation desg=validateEmployee.getDesignation();
                    if(desg!=null) {
                    	loginResponseVO.setDesgShortCode(desg.getDsgshortname());
                    }
                    LocationMasEntity locEnt=validateEmployee.getTbLocationMas();
                    if(locEnt!=null) {
                    	loginResponseVO.setLocationName(locEnt.getLocNameEng());
                    }
                    loginResponseVO.setAddress(validateEmployee.getEmpAddress());
                    loginResponseVO.setMobileNo(Long.valueOf(validateEmployee.getEmpmobno()));
                    loginResponseVO.setEmailId(validateEmployee.getEmpemail());
                    loginResponseVO.setDob(validateEmployee.getEmpdob());
                    loginResponseVO.setFirstName(validateEmployee.getEmpname());
                    loginResponseVO.setMiddleName(validateEmployee.getEmpmname());
                    loginResponseVO.setLastName(validateEmployee.getEmplname());
                    loginResponseVO.setUserName(validateEmployee.getEmploginname());
                    loginResponseVO.setStatus(MainetConstants.COMMON_STATUS.SUCCESS);
                    loginResponseVO.setOrgId(loginRequestVO.getOrgId());
                    loginResponseVO.setUserId(validateEmployee.getEmpId());

                    // below department details added for Mobile Authentication process
                    loginResponseVO.setDeptId(validateEmployee.getTbDepartment().getDpDeptid());
                    loginResponseVO.setDeptDesc(validateEmployee.getTbDepartment().getDpDeptdesc());
                    loginResponseVO.setDeptCode(validateEmployee.getTbDepartment().getDpDeptcode());
                    if (groupMaster != null) {
                        loginResponseVO.setGrCode(groupMaster.getGrCode());
                    }
                    if (validateEmployee.getIsDeleted().equalsIgnoreCase(MainetConstants.Common_Constant.Isdeleted)) {
                        loginResponseVO.setUserStatus(MainetConstants.Common_Constant.ACTIVE);
                    } else {
                        loginResponseVO.setUserStatus(MainetConstants.Common_Constant.INACTIVE);
                    }
                    loginResponseVO.setHttpstatus(HttpStatus.OK);
                    loginResponseVO.setResponseMsg(ApplicationSession.getInstance().getMessage("app.Login.success"));
                    loginResponseVO.setStatus(MainetConstants.COMMON_STATUS.SUCCESS);

                } else {
                    loginResponseVO.setHttpstatus(HttpStatus.BAD_REQUEST);
                    loginResponseVO.setResponseMsg(ApplicationSession.getInstance().getMessage("app.Login.fail.password"));
                    loginResponseVO.setStatus(MainetConstants.COMMON_STATUS.FAIL);

                }
            } else {
                loginResponseVO.setHttpstatus(HttpStatus.BAD_REQUEST);
                loginResponseVO.setResponseMsg(ApplicationSession.getInstance().getMessage("app.Login.fail.moibelno"));
                loginResponseVO.setStatus(MainetConstants.COMMON_STATUS.FAIL);
            }

        } catch (final Exception exception) {
            LOG.error("Exception occur in citizenAuthenticationProcess() ", exception);
            loginResponseVO.setHttpstatus(HttpStatus.NO_CONTENT);
            loginResponseVO.setErrorMsg(exception.getMessage());
            loginResponseVO.setResponseMsg(exception.getMessage().trim());
            loginResponseVO.setStatus(MainetConstants.COMMON_STATUS.FAIL);
        }

        return loginResponseVO;
    }

}
