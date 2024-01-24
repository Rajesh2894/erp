package com.abm.mainet.common.service;

import java.io.File;
import java.io.Serializable;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.ListIterator;
import java.util.Map;
import java.util.Random;
import java.util.Set;
import java.util.regex.Pattern;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import javax.annotation.Resource;
import javax.jws.WebMethod;
import javax.jws.WebService;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;

import org.apache.commons.io.FileUtils;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.ClassUtils;
import org.springframework.util.ReflectionUtils;

import com.abm.mainet.authentication.ldap.ILDAPManager;
import com.abm.mainet.authentication.ldap.UserProfile;
import com.abm.mainet.common.audit.service.AuditService;
import com.abm.mainet.common.constant.MainetConstants;
import com.abm.mainet.common.constant.MainetConstants.PasswordValidType;
import com.abm.mainet.common.constant.PrefixConstants;
import com.abm.mainet.common.domain.Department;
import com.abm.mainet.common.domain.Designation;
import com.abm.mainet.common.domain.Employee;
import com.abm.mainet.common.domain.EmployeeHistory;
import com.abm.mainet.common.domain.EmployeeSession;
import com.abm.mainet.common.domain.EmployeeWardZoneMapping;
import com.abm.mainet.common.domain.GroupMaster;
import com.abm.mainet.common.domain.LocationMasEntity;
import com.abm.mainet.common.domain.Organisation;
import com.abm.mainet.common.dto.CommonMasterDto;
import com.abm.mainet.common.dto.EmployeeDTO;
import com.abm.mainet.common.dto.EmployeeSearchDTO;
import com.abm.mainet.common.exception.FrameworkException;
import com.abm.mainet.common.integration.dms.client.FileNetApplicationClient;
import com.abm.mainet.common.integration.dms.fileUpload.FileUploadUtility;
import com.abm.mainet.common.master.dao.IEmployeeDAO;
import com.abm.mainet.common.master.dto.EmployeeBean;
import com.abm.mainet.common.master.mapper.EmployeeServiceMapper;
import com.abm.mainet.common.master.repository.EmployeeHistoryRepository;
import com.abm.mainet.common.master.repository.EmployeeJpaRepository;
import com.abm.mainet.common.master.repository.EmployeeSessionRepository;
import com.abm.mainet.common.master.repository.EmployeeWardZoneMapRepository;
import com.abm.mainet.common.master.service.GroupMasterService;
import com.abm.mainet.common.master.service.TbDepartmentService;
import com.abm.mainet.common.utility.ApplicationContextProvider;
import com.abm.mainet.common.utility.ApplicationSession;
import com.abm.mainet.common.utility.LookUp;
import com.abm.mainet.common.utility.PasswordManager;
import com.abm.mainet.common.utility.UserSession;
import com.abm.mainet.common.utility.Utility;
import com.abm.mainet.common.utility.UtilityService;
import com.abm.mainet.smsemail.dto.SMSAndEmailDTO;
import com.abm.mainet.smsemail.service.ISMSAndEmailService;

@Service
@WebService(endpointInterface = "com.abm.mainet.common.service.IEmployeeService")
@Path("/employeeService")
public class EmployeeService implements IEmployeeService, Serializable {
	
	 private static final Logger LOGGER = LoggerFactory.getLogger(EmployeeService.class);

    private static final long serialVersionUID = 1L;

    @Autowired
    @Qualifier("ldapManager")
    private ILDAPManager ldapManager;

    @Autowired
    private IEmployeeDAO employeeDAO;

    @Autowired
    private AuditService auditService;

    @Resource
    private EmployeeServiceMapper employeeServiceMapper;

    @Resource
    private EmployeeJpaRepository employeeJpaRepository;

    @Autowired
    private ISMSAndEmailService ismsAndEmailService;

    @Autowired
    private EmployeeProvisionService employeeProvisionService;

    @Autowired
    private GroupMasterService groupMasterService;
    
    @Resource
    private EmployeeSessionRepository employeeSessionRepository;

	@Autowired
	private IOrganisationService iOrganisationService;
	
	@Autowired
	private EmployeeHistoryRepository employeeHistoryRepository;
	
	@Autowired
	private TbDepartmentService departmentService;
	
	@Autowired EmployeeWardZoneMapRepository empWardZoneRepository;
	
    @Override
    @Transactional
    @WebMethod(exclude = true)
    public EmployeeDTO getEmployeeById(final Long empId, final Organisation organisation, final String isdeleted) {
        EmployeeDTO employeeDTO = new EmployeeDTO();
        final Employee emp = employeeDAO.getEmployeeById(empId, organisation, isdeleted);
        employeeDTO = employeeServiceMapper.mapEmployeeTOEmployeeDTO(emp);
        employeeDTO.setDesignationId(emp.getDesignation().getDsgid());
        employeeDTO.setLocaDeptId(emp.getTbLocationMas().getLocId());
        employeeDTO.setTitle(emp.getCpdTtlId());
        employeeDTO.setUpdatedDate(new Date());
        employeeDTO.setUpdatedBy(emp.getEmpId());
        return employeeDTO;
    }

    @Override
    @Transactional
    @WebMethod(exclude = true)
    public Employee getAuthenticatedEmployee(final String emploginname, final String emppassword, final Long emplType,
            final Organisation organisation, final String isdeleted) {
        return employeeDAO.getAuthenticatedEmployee(emploginname, Utility.encryptPassword(emploginname, emppassword),
                emplType, organisation, isdeleted);
    }

    @Override
    @Transactional
    @WebMethod(exclude = true)
    public List<Employee> getEmployeeByEmpEMailAndType(final String empEMail, final Long empType,
            final Organisation organisation, final String isDeleted, final boolean isAgency) {
        return employeeDAO.getEmployeeByEmailIdAndEmpType(empEMail, empType, organisation, isDeleted, isAgency);
    }

    @Override
    @Transactional
    @WebMethod(exclude = true)
    public List<Employee> getEmployeeByEmpMobileNo(final String empMobileNo, final Organisation organisation,
            final String isDeleted) {
        return employeeDAO.getEmployeeByEmpMobileNo(empMobileNo, organisation, isDeleted);

    }

    @Override
    @Transactional
    @WebMethod(exclude = true)
    public List<Employee> getEmployeeByEmpMobileNoAndType(final String empMobileNo, final Long empType,
            final Organisation organisation, final String isDeleted, final boolean isAgency) {
        final List<Employee> employeeList = employeeDAO.getEmployeeByEmpMobileNoAndEmpType(empMobileNo, empType,
                organisation, isDeleted, isAgency);
        return employeeList;
    }

    @Override
    @Transactional
    @WebMethod(exclude = true)
    public Employee saveEmployeeDetails(final Employee newEmployee, final Organisation organisation,
            final LocationMasEntity departmentLocation, final Designation designation, final Department department,
            final Employee userId) {
        setEmployeeCreateParams(newEmployee, organisation, departmentLocation, designation, department, userId);
        final Employee savedEmployee = employeeDAO.saveEmployee(newEmployee);

        final UserProfile uProfile = new UserProfile();
        uProfile.setFirstName(savedEmployee.getEmpname());
        uProfile.setLastName(savedEmployee.getEmplname());
        uProfile.setPwd(savedEmployee.getEmppassword());
        uProfile.setRole(savedEmployee.getGmid().toString());
        uProfile.setuID(savedEmployee.getEmploginname());
        uProfile.setUserType(MainetConstants.Common_Constant.DELETE_FLAG);

        ldapManager.updateUser(uProfile);

        return savedEmployee;
    }

    @Override
    @Transactional
    @WebMethod(exclude = true)
    public Employee updateEmployeeDetails(final Employee updateEmployee, final Employee userId) {
        setEmployeeUpdateParams(updateEmployee, userId);
        final Employee employeeUpdated = employeeDAO.saveEmployee(updateEmployee);

        final UserProfile uProfile = new UserProfile();
        uProfile.setFirstName(employeeUpdated.getEmpname());
        uProfile.setLastName(employeeUpdated.getEmplname());
        uProfile.setPwd(employeeUpdated.getEmppassword());
        if (employeeUpdated.getGmid() != null) {
            uProfile.setRole(employeeUpdated.getGmid().toString());
        }
        uProfile.setuID(employeeUpdated.getEmploginname());
        uProfile.setUserType(MainetConstants.Common_Constant.DELETE_FLAG);

        ldapManager.updateUser(uProfile);

        return employeeUpdated;
    }

    @Override
    @Transactional
    @WebMethod(exclude = true)
    public Employee updateEmployeePassword(final Employee updateEmployee, final String newPassword,
            final Employee userId) {
        setEmployeeUpdateParams(updateEmployee, userId);
        updateEmployee.setEmppassword(Utility.encryptPassword(updateEmployee.getEmploginname(), newPassword));
        final Employee employeeUpdated = employeeDAO.saveEmployee(updateEmployee);

        final UserProfile uProfile = new UserProfile();
        uProfile.setFirstName(employeeUpdated.getEmpname());
        uProfile.setLastName(employeeUpdated.getEmplname());
        uProfile.setPwd(employeeUpdated.getEmppassword());
        uProfile.setRole(employeeUpdated.getGmid().toString());
        uProfile.setuID(employeeUpdated.getEmploginname());
        uProfile.setUserType(MainetConstants.Common_Constant.DELETE_FLAG);

        ldapManager.updateUser(uProfile);

        return employeeUpdated;
    }

    @Override
    @Transactional
    @WebMethod(exclude = true)
    public Employee setEmployeePassword(final Employee updateEmployee, final String newPassword,
            final Employee userId) {
        if (updateEmployee.getAutMob().equals(MainetConstants.Common_Constant.NO)) {
            updateEmployee.setAutMob(MainetConstants.IsDeleted.DELETE);
        }
        setEmployeeUpdateParams(updateEmployee, userId);
        updateEmployee.setEmppassword(Utility.encryptPassword(updateEmployee.getEmploginname(), newPassword));
        updateEmployee.setEmpexpiredt(
                PasswordManager.calculatePasswordExpiredDate(PasswordValidType.EMPLOYEE.getPrifixCode()));
        updateEmployee.setLoggedIn(MainetConstants.FlagN);
        final Employee employeeUpdated = employeeDAO.saveEmployee(updateEmployee);
        if (userId!=null && userId.getEmploginname() != null && !userId.getEmploginname().equalsIgnoreCase("NOUSER")
         && userId.getLoggedIn().equalsIgnoreCase("Y") ) {
            userId.setEmppassword(employeeUpdated.getEmppassword());
        }
        final UserProfile uProfile = new UserProfile();
        uProfile.setFirstName(employeeUpdated.getEmpname());
        uProfile.setLastName(employeeUpdated.getEmplname());
        uProfile.setPwd(employeeUpdated.getEmppassword());
        uProfile.setRole(employeeUpdated.getGmid().toString());
        uProfile.setuID(employeeUpdated.getEmploginname());
        uProfile.setUserType(MainetConstants.Common_Constant.DELETE_FLAG);
        ldapManager.updateUser(uProfile);
        return employeeUpdated;
    }

    /**
     * @Method private method to set {@link Employee} creation time parameter.
     * @param newEmployee {@link Employee}
     * @param organisation {@link Organisation}
     * @param departmentLocation {@link DepartmentLocation}
     * @param designation {@link Designation}
     * @param department {@link Department}
     * @param userId {@link Employee}
     */
    private void setEmployeeCreateParams(final Employee newEmployee, final Organisation organisation,
            final LocationMasEntity departmentLocation, final Designation designation, final Department department,
            final Employee userId) {
        newEmployee.setOrganisation(organisation);
        newEmployee.setIsDeleted(MainetConstants.IsDeleted.ZERO);
        newEmployee.setAutMob(MainetConstants.IsDeleted.NOT_DELETE);
        newEmployee
                .setEmppassword(Utility.encryptPassword(newEmployee.getEmploginname(), newEmployee.getEmppassword()));
        newEmployee.setTbLocationMas(departmentLocation);
        newEmployee.setDesignation(designation);
        newEmployee.setTbDepartment(department);
        newEmployee.setUserId(userId.getEmpId());
        newEmployee.setIsuploaded(MainetConstants.IsUploaded.NOT_UPLOADED);
    }

    /**
     * @Method private method to set {@link Employee} update time parameter.
     * @param newEmployee {@link Employee}
     * @param userId {@link Employee}
     */
    private void setEmployeeUpdateParams(final Employee updateEmployee, final Employee userId) {
        final Date date = new Date();
        updateEmployee.setUpdatedDate(date);
        //Defect #31605: Handled case wherein User id will be null before login
        if (userId!=null) {
			updateEmployee.setUpdatedBy(userId.getUserId());
		}
    }

    @Override
    @Transactional
    @WebMethod(exclude = true)
    public Employee getEmployeeByLoginName(final String emploginname, final Organisation organisation,
            final String isDeleted) {
        return employeeDAO.getEmployeeByLoginName(emploginname, organisation, isDeleted);
    }

    @Override
    @Transactional
    @WebMethod(exclude = true)
    public List<Employee> getEmployeeListByLoginName(final String emploginname, final Organisation organisation,
            final String isDeleted) {
        return employeeDAO.getEmployeeListByLoginName(emploginname, organisation, isDeleted);
    }

    @Override
    @Transactional
    @WebMethod(exclude = true)
    public List<Employee> getAllListEmployeeByDeptId(final Organisation org, final long DeptId) {
        return employeeDAO.getAllEmployeeByDeptId(org, DeptId);
    }

    @Override
    @Transactional
    @WebMethod(exclude = true)
    public Employee saveEmployee(final Employee employee) {
        Employee savedEmployee = employeeDAO.saveEmployee(employee);
        final EmployeeBean empBean = employeeServiceMapper.mapEmployeeEntityToEmployee(savedEmployee);

        /**
         * used to push data to GRP environment if employee posting flag is enable. flag is maintained in
         * serviceConfiguration.properties file.
         */
        List<Object[]> descObj = employeeJpaRepository.getDescriptionDetailsByIds(
                savedEmployee.getTbDepartment().getDpDeptid(), savedEmployee.getDesignation().getDsgid(),
                savedEmployee.getTbLocationMas().getLocId(), savedEmployee.getOrganisation().getOrgid(),
                savedEmployee.getGmid());
        // set description details
        if (descObj != null && !descObj.isEmpty()) {
            empBean.setDeptName(descObj.get(0)[0].toString());
            empBean.setDesignName(descObj.get(0)[1].toString());
            empBean.setLocation(descObj.get(0)[2].toString());
            empBean.setOrgName(descObj.get(0)[3].toString());
            empBean.setGrCode(descObj.get(0)[4].toString());

        }

        employeeProvisionService.createEmployee(empBean);
        return savedEmployee;
    }

    @Override
    @Transactional
    @WebMethod(exclude = true)
    public List<Employee> getAllAgencyList() {
        return employeeDAO.getAllAgencyList(UserSession.getCurrent().getOrganisation());
    }

    @Override
    @Transactional
    @WebMethod(exclude = true)
    public List<EmployeeDTO> getEmployeeByAgencyTypeAndBySortOption(final Long agencyType, final String agencyName,
            final String sortValue, final Long orgId) {

        return employeeDAO.getEmployeeByAgencyTypeAndBySortOption(agencyType, agencyName, sortValue, orgId);
    }

    @Override
    @Transactional
    @WebMethod(exclude = true)
    public Employee getAgencyByEmplTypeAndEmail(final String email, final Long agencyType,
            final Organisation organisation, final String isDeleted) {

        return employeeDAO.getAgencyByEmplTypeAndEmail(email, agencyType, organisation, isDeleted);
    }

    @Override
    @Transactional
    @WebMethod(exclude = true)
    public void saveEditProfileInfo(final Employee modifiedEmployee) {
        employeeDAO.persistModifiedCitizenInfo(modifiedEmployee);

        final UserProfile uProfile = new UserProfile();
        uProfile.setFirstName(modifiedEmployee.getEmpname());
        uProfile.setLastName(modifiedEmployee.getEmplname());
        uProfile.setPwd(modifiedEmployee.getEmppassword());
        uProfile.setRole(modifiedEmployee.getGmid().toString());
        uProfile.setuID(modifiedEmployee.getEmploginname());
        uProfile.setUserType(MainetConstants.Common_Constant.DELETE_FLAG);

        ldapManager.updateUser(uProfile);
    }

    @Override
    @Transactional
    @WebMethod(exclude = true)
    public void setEmployeeLoggedInFlag(final Employee currentLoggedInEmployee) {
        currentLoggedInEmployee.setLoggedIn(MainetConstants.Common_Constant.YES);
        employeeDAO.setEmployeeLoggedInFlag(currentLoggedInEmployee);
    }

    @Override
    @Transactional
    @WebMethod(exclude = true)
    public void resetEmployeeLoggedInFlag(final Employee loggingOutEmployee) {
        if (loggingOutEmployee.getEmpId() != null) {
            employeeDAO.resetEmployeeLoggedInFlag(PrefixConstants.IsLookUp.STATUS.NO, loggingOutEmployee.getEmpId());
        }
    }

    @Transactional
    @Override
    @WebMethod(exclude = true)
    public List<EmployeeSearchDTO> getAllEmployeeInfoByOrgID(final String empName) {
        return employeeDAO.findEmployeeInfo(empName, UserSession.getCurrent().getOrganisation());
    }

    @Override
    @WebMethod(exclude = true)
    public Employee saveEmployeeForAgency(final Employee employee) {
        return employeeDAO.saveEmployeeForAgency(employee);
    }

    public List<LookUp> getAllEmployee(final List<Object[]> emp) {
        final List<LookUp> list = new ArrayList<>(0);

        final ListIterator<Object[]> listIterator = emp.listIterator();
        while (listIterator.hasNext()) {
            final Object[] obj = listIterator.next();

            final LookUp title = getEmpTitle(obj);
            final String fname = (String) obj[1];
            String lname = " ";
            if (obj[2] != null) {
                lname = (String) obj[2];
            }

            String fullName = " ";

            if (title.getLookUpDesc() != null) {
                fullName = title.getLookUpDesc() + " " + fname + " " + lname;
            } else {
                fullName = " " + fname + " " + lname;
            }
            final LookUp lookUp = new LookUp("", fullName);
            lookUp.setLookUpId((long) obj[4]);
            list.add(lookUp);
        }
        return list;
    }

    private LookUp getEmpTitle(final Object[] obj) {

        return ApplicationSession.getInstance()
                .getNonHierarchicalLookUp(UserSession.getCurrent().getOrganisation().getOrgid(), (Long) obj[0]);
    }

    @Override
    @WebMethod(exclude = true)
    public Map<Long, String> getEmployeeLookUp() {
        return employeeDAO.getEmployeeLookUp(UserSession.getCurrent().getOrganisation());
    }

    @Override
    @Transactional(readOnly = true)
    @WebMethod(exclude = true)
    public Employee getAdminEncryptAuthenticatedEmployee(final String emploginname, final String emppassword,
            final Long emplType, final Long empId, final Organisation organisation, final String isdeleted) {

        return employeeDAO.getAdminAuthenticatedEmployee(emploginname, emppassword, emplType, empId, organisation,
                isdeleted);
    }

    @Override
    @Transactional(readOnly = true)
    @WebMethod(exclude = true)
    public Employee getAuthenticatedEncryptEmployee(final String emploginname, final String emppassword,
            final Long emplType, final Organisation organisation, final String isdeleted) {

        return employeeDAO.getAuthenticatedEmployee(emploginname, emppassword, emplType, organisation, isdeleted);
    }

    @Override
    @Transactional(readOnly = true)
    @WebMethod(exclude = true)
    public int getCountOfGroup(final Long gmId, final Long orgId, final String isDeleted) {

        return employeeDAO.getCountOfGroup(gmId, orgId, isDeleted);
    }

    @Override
    @WebMethod(exclude = true)
    public EmployeeBean findById(final Long empId) {
        final Employee employeeEntity = employeeJpaRepository.findOne(empId);
        return employeeServiceMapper.mapEmployeeEntityToEmployee(employeeEntity);
    }

    @Override
    @WebMethod(exclude = true)
    public List<EmployeeBean> findAll() {
        final Iterable<Employee> entities = employeeJpaRepository.findAll();
        final List<EmployeeBean> beans = new ArrayList<>();
        for (final Employee employeeEntity : entities) {
            beans.add(employeeServiceMapper.mapEmployeeEntityToEmployee(employeeEntity));
        }
        return beans;
    }

    @Override
    @Transactional
    @WebMethod(exclude = true)
    public EmployeeBean create(final EmployeeBean employee, final String directory,
            final FileNetApplicationClient fileNetApplicationClient, Long orgId, Long userId) {
        final Employee employeeEntity = new Employee();

        employee.setLmoddate(new Date());
		if (Utility.isEnvPrefixAvailable(UserSession.getCurrent().getOrganisation(), MainetConstants.ENV_SFAC))
			employee.setOrgid(employee.getOrgid());
		else
			employee.setOrgid(orgId);
        employee.setUserId(userId);
        employee.setIsuploaded(MainetConstants.Common_Constant.YES);
        employee.setAutMob(MainetConstants.Common_Constant.YES);
        employee.setAutEmail(MainetConstants.Common_Constant.YES);
        employee.setAuthStatus(MainetConstants.Common_Constant.ACTIVE_FLAG);
        //US#148113 for SKDCL
        Organisation org = iOrganisationService.getOrganisationById(orgId);
        if(Utility.isEnvPrefixAvailable(org, MainetConstants.ENV_SKDCL) ) {
        String pwd=generatePassword();
        employee.setEmppassword(pwd);
        employee.setNewPassword(pwd);
        }
		if (Utility.isEnvPrefixAvailable(org, MainetConstants.ENV_SFAC)) {
			 Organisation orgnisation = iOrganisationService.getOrganisationById(employee.getOrgid());
				Long deptId = departmentService.getDepartmentIdByDeptCode(orgnisation.getOrgShortNm());
				employee.setDpDeptid(deptId);
		}

        if (employee.getAlreadResisteredUser().equals(MainetConstants.Common_Constant.YES)) {
            employee.setEmpGender(employee.getRegiEmpGender());
            employee.setCpdTtlId(employee.getRegiCpdTtlId());
            employee.setEmpdob(UtilityService.convertStringDateToDateFormat(employee.getRegiEmpDOB()));
            /*
             * get active employee by mobile number to fetch its password from data base. not taken as a hidden parameter because
             * of security reason
             */
            Employee regiEmp = employeeJpaRepository.getActiveEmployeeByEmpMobileNo(employee.getEmpmobno().trim())
                    .get(0);
            employee.setEmppassword(regiEmp.getEmppassword());

            // set Employee Photo path
            employee.setEmpphotoPath(regiEmp.getEmpphotopath());

            // set UID Doc name and Doc Path
            employee.setEmpuiddocPath(regiEmp.getEmpuiddocpath());
            employee.setEmpUidDocName(regiEmp.getEmpUidDocName());
            // set scan signature path
            employee.setScansignature(regiEmp.getScansignature());
        } else {
        	if(StringUtils.isNotBlank(employee.getNewPassword())) {
        		employee.setEmppassword(Utility.encryptPassword(employee.getEmploginname(), employee.getNewPassword()));
            }
            if(StringUtils.isNotBlank(employee.getEmpDOB() )) {
            	employee.setEmpdob(UtilityService.convertStringDateToDateFormat(employee.getEmpDOB()));
            }
            employee.setLoggedIn(MainetConstants.Common_Constant.FIRST);
        }
        // Calculate Password Expired date
        employee.setEmpexpiredt(
                PasswordManager.calculatePasswordExpiredDate(PasswordValidType.EMPLOYEE.getPrifixCode()));
        List<File> list = null;
        for (final Map.Entry<Long, Set<File>> entry : FileUploadUtility.getCurrent().getFileMap().entrySet()) {
            list = new ArrayList<>(entry.getValue());
            final Iterator<File> setFilesItr = entry.getValue().iterator();
            String tempDirPath = MainetConstants.operator.EMPTY;
            while (setFilesItr.hasNext()) {
                final File file = setFilesItr.next();
                tempDirPath = directory + MainetConstants.FILE_PATH_SEPARATOR + entry.getKey().toString();

                if (entry.getKey().longValue() == 0) {
                    employee.setEmpphotoPath(tempDirPath + MainetConstants.FILE_PATH_SEPARATOR + file.getName());
                }
                if (entry.getKey().longValue() == 1) {
                    employee.setScansignature(tempDirPath + MainetConstants.FILE_PATH_SEPARATOR + file.getName());
                }
                if (entry.getKey().longValue() == 2) {
                    employee.setEmpuiddocPath(tempDirPath);
                    employee.setEmpUidDocName(file.getName());
                }

                try {
                    fileNetApplicationClient.uploadFileList(list, tempDirPath);
                } catch (final Exception e) {
                    e.printStackTrace();
                    return employee;
                }
            }
        }
        employeeServiceMapper.mapEmployeeToEmployeeEntity(employee, employeeEntity);
        employeeEntity.setEmpmobno(employee.getEmpmobno().trim());
        employeeEntity.setLmodDate(new Date());
        employeeEntity.setIsDeleted(MainetConstants.IsDeleted.ZERO); 
        employeeEntity.setEmpphotopath(employee.getEmpphotoPath());
        employeeEntity.setEmpUidDocName(employee.getEmpUidDocName());
        employeeEntity.setEmpuiddocpath(employee.getEmpuiddocPath());
        final Employee employeeEntitySaved = employeeJpaRepository.save(employeeEntity);

        EmployeeHistory emp_hist = new EmployeeHistory();
        emp_hist.setStatus(MainetConstants.InsertMode.ADD.getStatus());
        auditService.createHistory(employeeEntitySaved, emp_hist);

        flushServerFolder();

        final EmployeeBean empBean = employeeServiceMapper.mapEmployeeEntityToEmployee(employeeEntitySaved);

        final UserProfile uProfile = new UserProfile();
        uProfile.setFirstName(empBean.getEmpname());
        uProfile.setLastName(empBean.getEmplname());
        uProfile.setPwd(empBean.getEmppassword());
        if(empBean.getGmid() != null) {
        	uProfile.setRole(empBean.getGmid().toString());
        }
        uProfile.setuID(empBean.getEmploginname());
        uProfile.setUserType(MainetConstants.Common_Constant.DELETE_FLAG);

        ldapManager.createUser(uProfile);

        /**
         * used to push data to GRP environment if employee posting flag is enable. flag is maintained in
         * serviceConfiguration.properties file.
         */
        List<Object[]> descObj = employeeJpaRepository.getDescriptionDetailsByIds(empBean.getDpDeptid(),
                empBean.getDsgid(), empBean.getDepid(), empBean.getOrgid(), empBean.getGmid());
        // set description details
        if (descObj != null && !descObj.isEmpty()) {
            empBean.setDeptName(descObj.get(0)[0].toString());
            empBean.setDesignName(descObj.get(0)[1].toString());
            empBean.setLocation(descObj.get(0)[2].toString());
            empBean.setOrgName(descObj.get(0)[3].toString());
            empBean.setGrCode(descObj.get(0)[4].toString());
        }

        employeeProvisionService.createEmployee(empBean);

        final SMSAndEmailDTO dto = new SMSAndEmailDTO();
        dto.setEmail(employee.getEmpemail());
        dto.setMobnumber(employee.getEmpmobno().trim());
        dto.setUserName(employee.getEmploginname());
        dto.setAppName(employee.getEmpname() + " " + employee.getEmplname());
        dto.setOrganizationName(employeeEntitySaved.getOrganisation().getONlsOrgname());
        dto.setPassword(employee.getNewPassword());
        int langId = Utility.getDefaultLanguageId(employeeEntitySaved.getOrganisation());
        // Added Changes As per told by Rajesh Sir For Sms and Email
        dto.setUserId(employeeEntitySaved.getEmpId());
        ismsAndEmailService.sendEmailSMS(MainetConstants.DEPT_SHORT_NAME.CFC_CENTER,
                MainetConstants.SMS_EMAIL_URL.REGISTRATION, PrefixConstants.SMS_EMAIL_ALERT_TYPE.APPROVAL, dto,
                employeeEntitySaved.getOrganisation(), langId);
        return empBean;
    }

    @Override
    @Transactional
    @WebMethod(exclude = true)
    public EmployeeBean update(final EmployeeBean employee) {
        final Employee employeeEntity = employeeJpaRepository.findOne(employee.getEmpId());
        employeeServiceMapper.mapEmployeeToEmployeeEntity(employee, employeeEntity);
        final Employee employeeEntitySaved = employeeJpaRepository.save(employeeEntity);

        EmployeeHistory emp_hist = new EmployeeHistory();
        emp_hist.setStatus(MainetConstants.InsertMode.UPDATE.getStatus());
        auditService.createHistory(employeeEntitySaved, emp_hist);

        final EmployeeBean empBean = employeeServiceMapper.mapEmployeeEntityToEmployee(employeeEntitySaved);

        final UserProfile uProfile = new UserProfile();
        uProfile.setFirstName(empBean.getEmpname());
        uProfile.setLastName(empBean.getEmplname());
        uProfile.setPwd(empBean.getEmppassword());
        uProfile.setRole(empBean.getGmid().toString());
        uProfile.setuID(empBean.getEmploginname());
        uProfile.setUserType(MainetConstants.Common_Constant.DELETE_FLAG);

        ldapManager.updateUser(uProfile);

        return empBean;
    }

    public EmployeeJpaRepository getEmployeeJpaRepository() {
        return employeeJpaRepository;
    }

    public void setEmployeeJpaRepository(final EmployeeJpaRepository employeeJpaRepository) {
        this.employeeJpaRepository = employeeJpaRepository;
    }

    public EmployeeServiceMapper getEmployeeServiceMapper() {
        return employeeServiceMapper;
    }

    public void setEmployeeServiceMapper(final EmployeeServiceMapper employeeServiceMapper) {
        this.employeeServiceMapper = employeeServiceMapper;
    }

    @Override
    @WebMethod(exclude = true)
    public List<EmployeeBean> getEmployeeData(final Long deptId, final Long locId, final Long designId,
            final Long orgid, final Long gmid) {
        List<Employee> employeeEntity = null;

        
        if ((locId != null) && (designId != null) && (gmid != null) && (deptId != null)) {
        employeeEntity = employeeJpaRepository.findEmployeeDataByIds(deptId, locId, designId, orgid,gmid);
        }
        if ((locId != null) && (designId != null) && (gmid == null) &&  (deptId != null)) {
            employeeEntity = employeeJpaRepository.findEmployeeData(deptId, locId, designId, orgid);
        } else if ((deptId != null) && (locId == null) && (designId == null) && (gmid == null)) {
            employeeEntity = employeeJpaRepository.findEmployeeData(deptId, orgid);
        } else if ((locId == null) && (designId != null) &&  (deptId != null) && (gmid == null)) {
            employeeEntity = employeeJpaRepository.findEmployeeDatabyDesign(deptId, designId, orgid);
        } else if ((locId != null) &&  (deptId != null) &&  (designId == null) && (gmid == null)) {
            employeeEntity = employeeJpaRepository.findEmployeeDataByLocID(deptId, locId, orgid);
        }else if ((gmid != null) &&  (deptId != null) && (designId == null) && (locId == null)) {
        	employeeEntity = employeeJpaRepository.findEmployeeDataByGmId(deptId, gmid, orgid);
        }else if ((gmid != null) && (designId != null) && (locId == null) &&  (deptId != null)) {
        	employeeEntity = employeeJpaRepository.findEmployeeDatabyDesignAndGmId(deptId, designId, orgid,gmid);
        }else if ((gmid != null) && (designId == null) && (locId != null) &&  (deptId != null)) {
        	employeeEntity = employeeJpaRepository.findEmployeeDataByLocIDAndGmId(deptId, locId, orgid,gmid);
        }else if ((locId != null) && (designId == null) && (gmid == null) && (deptId == null)) {
        	employeeEntity = employeeJpaRepository.findEmployeeDataByLocId(locId, orgid);
        }
        else if ((designId != null) && (locId == null) && (gmid == null) && (deptId == null)) {
        	employeeEntity = employeeJpaRepository.findEmployeeDataByDesgId(designId, orgid);
        }
        else if ((gmid != null) &&  (locId == null) && (designId == null)  && (deptId == null)) {
        	employeeEntity = employeeJpaRepository.findEmployeeDataByGmId(gmid, orgid);
        }
        else if((designId != null) && (locId != null) && (gmid == null) && (deptId == null)) {
        	employeeEntity = employeeJpaRepository.findEmployeeDataByLocIdAndDesgId(designId,locId,orgid);
        } else if((designId == null) && (locId == null) && (gmid == null) && (deptId == null) && (orgid != null)){
        	employeeEntity = employeeJpaRepository.getAllEmployee(orgid);
        }

        final List<EmployeeBean> beans = new ArrayList<>();

        for (final Employee empEntity : employeeEntity) {

            beans.add(employeeServiceMapper.mapEmployeeEntityToEmployee(empEntity));

        }
        return beans;

    }

    @Override
    @Transactional
    @WebMethod(exclude = true)
    public EmployeeBean updateEmployee(final EmployeeBean employee, final String directry,
            final FileNetApplicationClient filenetClient) {
        final Employee employeeEntity = employeeJpaRepository.findOne(employee.getEmpId());
     //US#148174
        EmployeeHistory emp_hist = new EmployeeHistory();
        if(employeeEntity!=null) {
			
			  emp_hist.setPrevEmpname(employeeEntity.getEmpname());
			  emp_hist.setPrevEmpmname(employeeEntity.getEmpmname());
			  emp_hist.setPrevEmplname(employeeEntity.getEmplname());
			  emp_hist.setPrevTtlId(employeeEntity.getCpdTtlId());
			 
            }
        List<File> list = null;
        if ((FileUploadUtility.getCurrent().getFileMap().entrySet() != null)
                && !FileUploadUtility.getCurrent().getFileMap().entrySet().isEmpty()) {
            for (final Map.Entry<Long, Set<File>> entry : FileUploadUtility.getCurrent().getFileMap().entrySet()) {
                list = new ArrayList<>(entry.getValue());
                final Iterator<File> setFilesItr = entry.getValue().iterator();
                String tempDirPath = MainetConstants.operator.EMPTY;
                while (setFilesItr.hasNext()) {
                    final File file = setFilesItr.next();
                    tempDirPath = directry + MainetConstants.FILE_PATH_SEPARATOR + employee.getEmpId().toString()
                            + MainetConstants.FILE_PATH_SEPARATOR + entry.getKey().toString();

                    if ((entry.getKey().longValue() == 0) && (file != null)) {
                        employee.setEmpphotoPath(tempDirPath + MainetConstants.FILE_PATH_SEPARATOR + file.getName());
                    } else {
                        if (employeeEntity.getEmpphotopath() != null)
                            employee.setEmpphotoPath(employeeEntity.getEmpphotopath());
                    }
                    if ((entry.getKey().longValue() == 1) && (file != null)) {
                        employee.setScansignature(tempDirPath + MainetConstants.FILE_PATH_SEPARATOR + file.getName());
                    } else {
                        if (employeeEntity.getScansignature() != null)
                            employee.setScansignature(employeeEntity.getScansignature());
                    }
                    if ((entry.getKey().longValue() == 2) && (file != null)) {
                        employee.setEmpuiddocPath(tempDirPath);
                        employee.setEmpUidDocName(file.getName());
                    } else {
                        if (employeeEntity.getEmpuiddocpath() != null && employeeEntity.getEmpUidDocName() != null) {
                            employee.setEmpuiddocPath(employeeEntity.getEmpuiddocpath());
                            employee.setEmpUidDocName(employeeEntity.getEmpUidDocName());
                        }
                    }

                    try {
                        filenetClient.uploadFileList(list, tempDirPath);
                    } catch (final Exception e) {
                        e.printStackTrace();
                        return employee;
                    }
                }
            }
        } else {

            employee.setEmpphotoPath(employeeEntity.getEmpphotopath());
            employee.setScansignature(employeeEntity.getScansignature());
            employee.setEmpuiddocPath(employeeEntity.getEmpuiddocpath());
            employee.setEmpUidDocName(employeeEntity.getEmpUidDocName());
        }
        flushServerFolder();
        Organisation org = iOrganisationService.getOrganisationById(employeeEntity.getOrganisation().getOrgid());
        if (employeeEntity != null) {
        	//123332
           // employee.setDsgid(employeeEntity.getDesignation().getDsgid());
        	if(!Utility.isEnvPrefixAvailable(org, MainetConstants.ENV_TSCL))
            employee.setDpDeptid(Long.valueOf(employeeEntity.getTbDepartment().getDpDeptid()));
           // employee.setDepid(employeeEntity.getTbLocationMas().getLocId());
            employee.setEmpisecuritykey(employeeEntity.getEmpisecuritykey());
            employee.setAddFlag(employeeEntity.getAddFlag());
            employee.setAutBy(employeeEntity.getAutBy());
            employee.setAutDate(employeeEntity.getAutDate());
            employee.setAuthStatus(employeeEntity.getAuthStatus());
            employee.setAutMob(employeeEntity.getAutMob());
            employee.setCentraleno(employeeEntity.getCentraleno());
            employee.setEmplType(employeeEntity.getEmplType());
            employee.setEmpCorAdd1(employeeEntity.getEmpCorAdd1());
            employee.setEmpCorAdd2(employeeEntity.getEmpCorAdd2());
            employee.setEmpCorPincode(employeeEntity.getEmpCorPincode());
            employee.setEmppayrollnumber(employeeEntity.getEmppayrollnumber());
            employee.setAddFlag(employeeEntity.getAddFlag());
            employee.setEmpnew(employeeEntity.getEmpnew());
            employee.setLmoddate(new Date());
            employee.setOrgid(Long.valueOf(employeeEntity.getOrganisation().getOrgid()));
            employee.setUpdatedBy(Long.valueOf(UserSession.getCurrent().getEmployee().getEmpId()));
            employee.setUpdatedDate(new Date());
            employee.setLgIpMac(employeeEntity.getLgIpMac());

            employee.setIsuploaded(employeeEntity.getIsuploaded());
            if(null != employeeEntity && null != employee) {
				if(!employeeEntity.getEmpmobno().equals(employee.getEmpmobno())) {
					employee.setLoggedIn(MainetConstants.Common_Constant.FIRST);
				}
			}
            if (employee.getModeFlag().equals(MainetConstants.Common_Constant.YES)
                    && (employee.getUpdatelogin() != null)
                    && employee.getUpdatelogin().equals(MainetConstants.Common_Constant.YES)) {
                employee.setEmppassword(Utility.encryptPassword(employee.getEmploginname(), employee.getNewPassword()));
            } else {
                employee.setEmppassword(employeeEntity.getEmppassword());
            }
        }
        //US#148113 for SKDCL
       
        if(Utility.isEnvPrefixAvailable(org, MainetConstants.ENV_SKDCL) && employee.getIsDeleted().equals(MainetConstants.ZERO) ) {
            String pwd=generatePassword();
            employee.setEmppassword(Utility.encryptPassword(employee.getEmploginname(),pwd));
            employee.setNewPassword(pwd);
            }
        if (employee.getIsEmpPhotoDeleted().equals(MainetConstants.Common_Constant.YES)
                && (FileUploadUtility.getCurrent().getFileMap().get(0) == null)) {
            employee.setEmpphotoPath(null);
        }
        employeeServiceMapper.mapEmployeeToEmployeeEntity(employee, employeeEntity);
        /* employeeEntity.setIsDeleted(MainetConstants.IsDeleted.ZERO); */
        final Employee employeeEntitySaved = employeeJpaRepository.save(employeeEntity);
       
        emp_hist.setStatus(MainetConstants.InsertMode.UPDATE.getStatus());
      //US#148162
         employeeEntitySaved.setLmodDate(new Date());
         
         
        auditService.createHistory(employeeEntitySaved, emp_hist);

        final EmployeeBean empBean = employeeServiceMapper.mapEmployeeEntityToEmployee(employeeEntitySaved);

        final UserProfile uProfile = new UserProfile();
        uProfile.setFirstName(empBean.getEmpname());
        uProfile.setLastName(empBean.getEmplname());
        uProfile.setPwd(empBean.getEmppassword());
        uProfile.setRole(empBean.getGmid().toString());
        uProfile.setuID(empBean.getEmploginname());
        uProfile.setUserType(MainetConstants.Common_Constant.DELETE_FLAG);

        ldapManager.updateUser(uProfile);

        /**
         * it used to push data to GRP environment if employee posting flag is enable. flag is maintained in
         * serviceConfiguration.properties file.
         */

        // set description details
        empBean.setOrgName(employee.getOrgName());
        empBean.setLocation(employee.getLocation());
        empBean.setDesignName(employee.getDesignName());
        empBean.setDeptName(employee.getDeptName());

        // get group code
        GroupMaster groupMaster = groupMasterService.findByGmId(employee.getGmid(), employee.getOrgid());
        empBean.setGrCode(groupMaster.getGrCode());
        employeeProvisionService.updateEmployee(empBean);

        final SMSAndEmailDTO dto = new SMSAndEmailDTO();
        dto.setEmail(employee.getEmpemail());
        dto.setMobnumber(employee.getEmpmobno());
        dto.setUserName(employee.getEmploginname());
        dto.setAppName(employee.getEmpname() + " " + employee.getEmplname());
        dto.setPassword(employee.getNewPassword());
        dto.setOrganizationName(employeeEntitySaved.getOrganisation().getONlsOrgname());
        int langId = Utility.getDefaultLanguageId(employeeEntity.getOrganisation());
        // Added Changes As per told by Rajesh Sir For Sms and Email
        dto.setUserId(employee.getEmpId());
        ismsAndEmailService.sendEmailSMS(MainetConstants.DEPT_SHORT_NAME.CFC_CENTER,
                MainetConstants.SMS_EMAIL_URL.REGISTRATION, PrefixConstants.SMS_EMAIL_ALERT_TYPE.APPROVAL, dto,
                employeeEntity.getOrganisation(), langId);

        return empBean;

    }

    public void flushServerFolder() {
        try {
            final String path = FileUploadUtility.getCurrent().getExistingFolderPath();
            if (path != null) {
                final File cacheFolderStructure = new File(FileUploadUtility.getCurrent().getExistingFolderPath());
                FileUtils.deleteDirectory(cacheFolderStructure);
            }
        } catch (final Throwable ex) {
            throw new FrameworkException("Exception while flush server folder :", ex);
        }
    }

    @Override
    @WebMethod(exclude = true)
    public void deleteEmployee(final Long empid, final Long orgId) {
        employeeJpaRepository.deleteEmployee(empid, orgId);
    }

    @Override
    @WebMethod(exclude = true)
    public List<EmployeeBean> getRegisterdMobile(final String empmobno, final Long orgId) {
        List<Employee> employeeEntity = null;
        employeeEntity = employeeJpaRepository.getRegisterdMobile(empmobno, orgId);
        final List<EmployeeBean> beans = new ArrayList<>();
        for (final Employee empEntity : employeeEntity) {
            beans.add(employeeServiceMapper.mapEmployeeEntityToEmployee(empEntity));
        }
        return beans;
    }

    @Override
    @WebMethod(exclude = true)
    public List<EmployeeBean> getRegisterdEmail(final String empemail, final Long orgId) {
        List<Employee> employeeEntity = null;
        employeeEntity = employeeJpaRepository.getRegisterdEmail(empemail, orgId);
        final List<EmployeeBean> beans = new ArrayList<>();
        for (final Employee empEntity : employeeEntity) {
            beans.add(employeeServiceMapper.mapEmployeeEntityToEmployee(empEntity));
        }
        return beans;
    }

    @Override
    @WebMethod(exclude = true)
    public List<EmployeeBean> getRegisterdLoginName(final String emploginname, final Long orgId) {
        List<Employee> employeeEntity = null;
        employeeEntity = employeeJpaRepository.getRegisterdLoginName(emploginname, orgId);
        final List<EmployeeBean> beans = new ArrayList<>();
        for (final Employee empEntity : employeeEntity) {
            beans.add(employeeServiceMapper.mapEmployeeEntityToEmployee(empEntity));
        }
        return beans;
    }

    /*
     * (non-Javadoc)
     * @see com.abm.mainetservice.web.masters.employee.business.service.EmployeeService #getGroupList(long)
     */
    @Override
    @WebMethod(exclude = true)
    public Map<Long, String> getGroupList(final long orgid) {
        List<GroupMaster> grpEntity = null;
        grpEntity = employeeJpaRepository.getGroupList(orgid);
        final Map<Long, String> list = new LinkedHashMap<>();

        if ((grpEntity != null) && !grpEntity.isEmpty()) {
            for (final GroupMaster mas : grpEntity) {
                list.put(mas.getGmId(), mas.getGrCode());
            }

        }
        return list;
    }

    @Override
    @WebMethod(exclude = true)
    public List<GroupMaster> getGroupDataList(final long orgid) {
        final List<GroupMaster> grpEntity = employeeJpaRepository.getGroupList(orgid);
        return grpEntity;
    }

    @Override
    @WebMethod(exclude = true)
    public List<EmployeeBean> getAllEmployee(final Long orgId) {
        final List<Employee> entities = employeeJpaRepository.getAllEmployee(orgId);
        final List<EmployeeBean> beans = new ArrayList<>();
        for (final Employee employeeEntity : entities) {
            beans.add(employeeServiceMapper.mapEmployeeEntityToEmployee(employeeEntity));
        }
        return beans;
    }

    @Override
    @WebMethod(exclude = true)
    public int validateEmployee(final String emploginname, final Long orgid) {
        int empCounter = 0;
        final Long count = employeeJpaRepository.validateEmployee(emploginname, orgid);
        if (count != null) {
            empCounter = count.intValue();
        }
        return empCounter;
    }

    @Override
    @WebMethod(exclude = true)
    public List<Object[]> getAllEmployeeNames(final Long orgId) {
        final List<Object[]> entities = employeeJpaRepository.getAllEmployeeNames(orgId);
        return entities;
    }

    /*
     * (non-Javadoc)
     * @see com.abm.mainetservice.web.masters.employee.business.service.EmployeeService #findAllEmpByDept(java.lang.Long,
     * java.lang.Long)
     */
    @Override
    @Transactional(readOnly = true)
    @GET
    @Produces(value = { MediaType.APPLICATION_JSON_VALUE })
    @Path(value = "/employee/orgId/{orgId}")
    public List<Object[]> findAllEmpByOrg(@PathParam("orgId") final Long orgId) {
        final List<Object[]> entities = employeeJpaRepository.findAllEmpByOrg(orgId);
        return entities;
    }

    /*
     * (non-Javadoc)
     * @see com.abm.mainetservice.web.masters.employee.business.service.EmployeeService
     * #findAllEmployeeByLocation(com.abm.mainetservice.web.common.entity. LocationMasEntity)
     */
    @Override
    @Transactional(readOnly = true)
    @WebMethod(exclude = true)
    public List<Employee> findAllEmployeeByLocation(final Long locId) {
        return employeeJpaRepository.findAllEmployeeByLocation(locId);
    }

    @Override
    @Transactional(readOnly = true)
    @WebMethod(exclude = true)
    public List<Long> findAllEmployeeByLocation(final Set<Long> locIds) {
        return employeeJpaRepository.findAllEmployeeByLocation(locIds);
    }

    @Override
    @Transactional(readOnly = true)
    @WebMethod(exclude = true)
    public List<Object[]> getAllEmpByDesignation(final Long desgId, final Long orgId) {
        final List<Object[]> empList = employeeJpaRepository.getAllEmpByDesignation(desgId, orgId);
        return empList;
    }

    @Override
    @Transactional(readOnly = true)
    @WebMethod(exclude = true)
    public List<Employee> findAllEmployeeByDept(final Long orgId, final Long deptId) {
        return employeeJpaRepository.findAllEmployeeByDept(orgId, deptId);
    }

    @Override
    @WebMethod(exclude = true)
    public LocationMasEntity findEmployeeLocation(final String empName, final Long orgId) {
        final Employee employeeEntity = employeeJpaRepository.findEmployeeByName(empName, orgId);
        if (employeeEntity != null) {
            return employeeEntity.getTbLocationMas();
        } else {
            return null;
        }
    }

    @Override
    @WebMethod(exclude = true)
    public List<EmployeeBean> getAllEmployeeWithLoggedInEmployeeNotPresent(final Long orgId, final Long empId) {
        final List<Employee> entities = employeeJpaRepository.getAllEmployeeWithLoggedInEmployeeNotPresent(orgId,
                empId);
        final List<EmployeeBean> beans = new ArrayList<>();
        for (final Employee employeeEntity : entities) {
            beans.add(employeeServiceMapper.mapEmployeeEntityToEmployee(employeeEntity));
        }
        return beans;
    }

    @Override
    @Transactional(readOnly = true)
    @WebMethod(exclude = true)
    public List<Employee> getByGmId(final Long gmId) {
        return employeeJpaRepository.getByGmId(gmId);
    }

    @Override
    @Transactional(readOnly = true)
    @WebMethod(exclude = true)
    public List<Employee> getByGmId(final List<Long> gmIds) {
        return employeeJpaRepository.getByGmId(gmIds);
    }

    @Override
    @WebMethod(exclude = true)
    public Employee findEmployeeById(final Long empId) {
        final Employee employeeEntity = employeeJpaRepository.findOne(empId);
        return employeeEntity;
    }

    @Override
    @Transactional(readOnly = true)
    @WebMethod(exclude = true)
    public List<Object[]> findAllEmpByLocation(final Long orgId, final Long deptId, final List<Long> locationId) {
        return employeeJpaRepository.findAllEmpByLocation(orgId, deptId, locationId);
    }

    @Override
    @Transactional(readOnly = true)
    @WebMethod(exclude = true)
    public List<Object[]> findAllRoleByLocation(final Long orgId, final Long deptId, final List<Long> locationId) {
        return employeeJpaRepository.findAllRoleByLocation(orgId, deptId, locationId);
    }

    @Override
    @WebMethod(exclude = true)
    public List<Object[]> findActiveEmployeeByDeptId(final long deptId, final long orgId) {
        return employeeJpaRepository.findActiveEmployeeByDeptId(deptId, orgId);
    }
    
    @Override
    @WebMethod(exclude = true)
    public List<Object[]> findActiveEmployeeAndDsgByDeptId(final long deptId, final long orgId) {
        return employeeJpaRepository.findActiveEmployeeAndDsgByDeptId(deptId, orgId);
    }

    @Override
    @WebMethod(exclude = true)
    public String generateNewPassword(final int newPasswordLength) {
        return UtilityService.generateRandomNumericCode(newPasswordLength);
    }

    @Override
    @Transactional
    @WebMethod(exclude = true)
    public Employee setAdminEmployeePassword(final String mobile, final String newPassword, final Employee userId) {
        Employee updateEmployee = null;
        final List<Employee> employeeList = employeeDAO.getEmployeeByEmpMobileNo(mobile,
                UserSession.getCurrent().getOrganisation(), MainetConstants.IsDeleted.ZERO);
        if (employeeList != null) {
            final Date date = new Date();
            for (final Employee employee : employeeList) {
                if (employee.getEmplType() == null) {
                    employee.setAutMob(MainetConstants.Common_Constant.YES);
                    employee.setUpdatedDate(date);
                    employee.setUpdatedBy(userId.getUserId());
                    employee.setEmppassword(Utility.encryptPassword(employee.getEmploginname(), newPassword));
                    updateEmployee = employeeDAO.saveEmployee(employee);
                }
            }
        } else {
            return updateEmployee;
        }
        return updateEmployee;
    }

    @Override
    @WebMethod(exclude = true)
    public List<Employee> getEmployeeByMobileNo(final String mobileNo, final Long orgId) {
        final List<Employee> employee = employeeJpaRepository.validateEmpMobileNo(mobileNo, orgId);
        return employee;
    }

    @Override
    @WebMethod(exclude = true)
    public List<Employee> getEmployeeByUid(final String uid, final Long orgId) {
        final List<Employee> employee = employeeJpaRepository.validateEmpUid(uid, orgId);
        return employee;
    }

    @Override
    @WebMethod(exclude = true)
    public List<Employee> getEmployeeByPancardNo(final String pancardNo, final Long orgId) {
        final List<Employee> employee = employeeJpaRepository.validateEmpPancardNo(pancardNo, orgId);
        return employee;
    }

    @Override
    @Transactional
    @WebMethod(exclude = true)
    public int getEmployeeByLocation(Long orgId, Long locId) {
        return employeeJpaRepository.getEmpByLocation(orgId, locId);
    }

    @Override
    @WebMethod(exclude = true)
    public List<Employee> findEmpList(final long orgId) {
        return employeeJpaRepository.getEmpList(orgId);
    }

    @Override
    @Transactional
    @WebMethod(exclude = true)
    public List<Employee> findMappedEmployeeLevel1(final Long empId, final Long orgId, final Long dpDeptid) {
        return employeeDAO.findMappedEmployeeLevel1(empId, orgId, dpDeptid);
    }

    @Override
    @WebMethod(exclude = true)
    public int getTotalEmployeeCountByRoles(List<Long> roleIds, Long orgId) {
        return employeeDAO.getTotalEmployeeCountByRoles(roleIds, orgId);
    }

    @Override
    @Transactional
    @WebMethod(exclude = true)
    public boolean updateEmpDetails(Employee emp) {
        return employeeDAO.updateEmpDetails(emp);
    }

    @Override
    @WebMethod(exclude = true)
    public List<Employee> getEmpDetailListByEmpIdList(List<Long> empIds, Long orgId) {
        return employeeJpaRepository.getEmpDetailListByEmpIdList(orgId, empIds);
    }

    @Override
    @WebMethod(exclude = true)
    public List<EmployeeBean> getAllActiveEmployee() {
        final List<Employee> entities = employeeJpaRepository.getAllActiveEmployee();
        final List<EmployeeBean> beans = new ArrayList<>();
        for (final Employee employeeEntity : entities) {
            beans.add(employeeServiceMapper.mapEmployeeEntityToEmployee(employeeEntity));
        }
        return beans;
    }

    @Override
    @WebMethod(exclude = true)
    public List<EmployeeBean> getActiveEmployeeByEmpMobileNo(String empMobileNo) {
        final List<Employee> entities = employeeJpaRepository.getActiveEmployeeByEmpMobileNo(empMobileNo);
        final List<EmployeeBean> beans = new ArrayList<>();
        for (final Employee employeeEntity : entities) {
            beans.add(employeeServiceMapper.mapEmployeeEntityToEmployee(employeeEntity));
        }
        return beans;
    }

    @Override
    @WebMethod(exclude = true)
    public Employee findEmployeeByIdAndOrgId(Long empId, Long orgId) {
        return employeeJpaRepository.findEmployeeByIdAndOrgId(empId, orgId);
    }

    @Override
    @Transactional
    public String findEmployeeSessionId(Long empId, String flag) {
        return employeeDAO.findEmployeeSessionId(empId, flag);
    }

    @Override
    @WebMethod(exclude = true)
    public List<GroupMaster> getGroupDataList(final long orgid, final long deptId) {
        final List<GroupMaster> grpEntity = employeeJpaRepository.getGroupList(orgid, deptId);
        return grpEntity;
    }

    @Override
    @Transactional(rollbackForClassName = { "SQLException" })
    public void updateEmployeeLoggedInFlag(String no, String sessionId) {
        employeeDAO.updateEmployeeLoggedInFlag(no, sessionId);
    }
    
	@Override
	@Transactional
	public int getEmpCountByGmIdAndOrgId( Long orgId, Long gmid) {
		return employeeJpaRepository.getEmpCountByGmIdAndOrgId(orgId, gmid);
	}
	/**
	 * This method validates logged in employee. Username and password are authenticated. Also user is locked after max allowed failed attempts.
	 * @param empLoginString - employee username
	 * @param empPassword - employee password
	 * @param orgId - employee organisation id
	 * @response  Employee - logged in employee. null value is returned in case of failed attempt
	 */
	@Transactional
	public Employee validateLoggedInEmployee(String empLoginString, String empPassword, String orgId) {
		final IOrganisationService iOrganisationService = ApplicationContextProvider.getApplicationContext()
				.getBean(IOrganisationService.class);
		final Organisation organisation = iOrganisationService.getOrganisationById(new Long(orgId));

		Employee validateEmployee = null;
		
		
		if (Pattern.compile(MainetConstants.EMAIL_PATTERN).matcher(empLoginString).matches()) {
			final List<Employee> employeeListByEmpEMail = getEmployeeByEmpEMailAndType(empLoginString,
					null,organisation,
					MainetConstants.IsDeleted.ZERO, false);
			if (employeeListByEmpEMail.size() == 1) {
				validateEmployee = employeeListByEmpEMail.get(0);
			} else {
				for (final Employee employee : employeeListByEmpEMail) {
					if (employee.getEmplType() == null) {
						validateEmployee = employee;
					}

				}
			}

		} else if (Pattern.compile(MainetConstants.MOB_PATTERN).matcher(empLoginString).matches()) {

			final List<Employee> employeeListByEmpMob = getEmployeeByEmpMobileNoAndType(empLoginString,
					null, organisation,
					MainetConstants.IsDeleted.ZERO, false);
			if (employeeListByEmpMob.size() == 1) {
				validateEmployee = employeeListByEmpMob.get(0);
			} else {
				for (final Employee employee : employeeListByEmpMob) {

					if (employee.getEmplType() == null) {
						validateEmployee = employee;
					}
				}
			}

		}

		else {

			final List<Employee> empListByLoginName = getEmployeeListByLoginName(empLoginString,
					organisation, MainetConstants.IsDeleted.ZERO);
			if (empListByLoginName.size() == 1) {
				validateEmployee = empListByLoginName.get(0);
			} else {
				for (final Employee employee : empListByLoginName) {
					if (employee.getEmplType() == null) {
						validateEmployee = employee;
					}

				}
			}

		}
		

		if (validateEmployee != null) {
			final Long emplType = validateEmployee.getEmplType();
			final String empLoginName = validateEmployee.getEmploginname();
			boolean loginStatus = false;
			if (validateEmployee.getAutMob().equals("Y")) {
				if (empLoginName != null) {
					// Query to get user details
					validateEmployee = getAdminEncryptAuthenticatedEmployee(empLoginName, null, emplType,
							validateEmployee.getEmpId(), organisation, MainetConstants.IsDeleted.ZERO);
					// if user password got matches with entered encrypted password
					if (validateEmployee.getEmppassword().equals(empPassword)) {
						validateEmployee.setLoggedInAttempt(null);
						loginStatus = true;

					} else {
						// if password not match increment login attempt
						Integer maxLoginAttempts = null;
						try {
							maxLoginAttempts = Integer
									.valueOf(ApplicationSession.getInstance().getMessage("admin.max.attempts").trim());
						} catch (NumberFormatException e) {
							maxLoginAttempts = 3;
						}
						if (null == validateEmployee.getLoggedInAttempt()
								|| validateEmployee.getLoggedInAttempt() < maxLoginAttempts) {
							validateEmployee.setLoggedInAttempt(validateEmployee.getLoggedInAttempt() != null
									? validateEmployee.getLoggedInAttempt() + 1
									: 1);
							loginStatus = false;
						} else {
							// lock account if max attempted reached.
							if (null == validateEmployee.getLockUnlock()) {
								validateEmployee.setLockUnlock("L");
								validateEmployee.setLockDate(new Date());
							}
							loginStatus = true;
						}

					}

					// UPdate Employee attempt count and lock unlock status
					updateEmpDetails(validateEmployee);

					// Login status is false return null as expected in case of failed attempted
					if (loginStatus == false) {
						validateEmployee = null;
					}
				}
			}
		}
		return validateEmployee;

	}
	
	@Override
    @Transactional(readOnly = true)
    @WebMethod(exclude = true)
    public List<Employee> findAllEmployeeByDesgId(final Long orgid, final Long designId) {
        return employeeJpaRepository.findEmployeeDataByDesgId(designId,orgid);
    }
	
	@Override
    @Transactional(readOnly = true)
    @WebMethod(exclude = true)
    public List<Employee> findAllEmployeeByDesgIdDept(final Long orgid, final Long designId,final long deptId) {
        return employeeJpaRepository.findEmployeeDataByDesgIdDept(designId,orgid,deptId);
    }
	
	@Override
	@Transactional
	public boolean saveEmployeeSession(EmployeeSession empSession) {
		EmployeeSession empSessionDate = employeeSessionRepository.save(empSession);
		if (empSessionDate != null)
			return true;
		else
			return false;
	}
	@Override
	@Transactional
	public EmployeeSession getEmployeeSessionDataByEmpId(Long empId) {
		EmployeeSession empSessionDate = employeeSessionRepository.getEmployeeSessionDataBy(empId);
		if (empSessionDate != null)
			return empSessionDate;
		else
			return null;
	}
	
	@Override
	@Transactional
	public Long getEmpIdByEMpSession(String empSession) {
		Long empId = employeeJpaRepository.getEmpIdByEMpSession(empSession);
		if (empId != null)
			return empId;
		else
			return null;
	}

	@Override
	public String getEmpNameByEmpId(Long empId) {
		
		return employeeJpaRepository.getEmpNameByEmpId(empId);
	}
	
	 private static String generatePassword() {
	      String capitalCaseLetters = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
	      String lowerCaseLetters = "abcdefghijklmnopqrstuvwxyz";
	      String specialCharacters = "!@#$";
	      String numbers = "1234567890";
	      String combinedChars = capitalCaseLetters + lowerCaseLetters + specialCharacters + numbers;
	      Random random = new Random();
	      char[] password = new char[8];

	      password[0] = lowerCaseLetters.charAt(random.nextInt(lowerCaseLetters.length()));
	      password[1] = capitalCaseLetters.charAt(random.nextInt(capitalCaseLetters.length()));
	      password[2] = specialCharacters.charAt(random.nextInt(specialCharacters.length()));
	      password[3] = numbers.charAt(random.nextInt(numbers.length()));
	   
	      for(int i = 4; i< 8 ; i++) {
	         password[i] = combinedChars.charAt(random.nextInt(combinedChars.length()));
	      }
	      return String.valueOf(password);
	   }
	 @Override
	 public boolean isEmployeeUpdated(Long empId, long orgid, Date date) {
		 Employee emp= employeeJpaRepository.findEmployeeByIdAndOrgId(empId, orgid);
		 if(emp!=null && emp.getUpdatedDate()!=null) {
			 int count=employeeJpaRepository.isEmployeeDataUpdated(empId,orgid,date) ;
			 if(count>0){
				 return true;
			 }
		 }
		return false; 
	 }
	 
	 @Override
	 public Map<String,String>  latestUpdatedEmployeeDet(Long empId, Long orgid,
			String applicationId, String taskId, Date date, String empName) {
		List<Object[]> empHistList = employeeHistoryRepository.findEmployeeHistData(empId, orgid, applicationId,
				taskId);
		Map<String, String> map=new HashMap<String, String>();
		List<EmployeeDTO> dtoList = new ArrayList<EmployeeDTO>();
		StringBuilder builder = new StringBuilder();
		StringBuilder empFrm = new StringBuilder();
		StringBuilder lastEmpName = new StringBuilder();
		Date onDate = null;
		int count = empHistList.size();
		int i = 0;
		for (Object[] empHist : empHistList) {
			if(builder.length()>0){
				builder.append("Task transfer to   ");
			}
			if (empHist != null) {

				if (empFrm != null && !empFrm.toString().isEmpty() && i <= empHistList.size() - 1) {
					if (empHistList.get(i)[2] != null) 
						builder.append("from  " + empHistList.get(i)[2].toString()+" ");
					
					
					if (empHistList.get(i)[3] != null) 
						builder.append(empHistList.get(i)[3].toString()+" ");
						
					if (empHistList.get(i)[4] != null) 
						builder.append( empHistList.get(i)[4].toString()+" ");
					
				}
				if (onDate != null)
					builder.append(" on Date " + onDate + ". ");
				if (count > 1) {
					if (empFrm != null && !empFrm.toString().isEmpty())
						builder.append(" And to " );
					// &&(empHist.getEmpmobno()!=null && (!empHist.getEmpmobno().equals(mobNo)))

					if (empHist[2] != null) {
						builder.append(empHist[2].toString() + " ");
						empFrm.append(empHist[2].toString() + " ");
					}
					if (empHist[3] != null) {
						builder.append(empHist[3].toString() + " ");
						empFrm.append(empHist[3].toString() + " ");
					}
					if (empHist[4] != null) {
						builder.append(empHist[4].toString() + " ");
						empFrm.append(empHist[4].toString() + " ");
					}
					if (empHist[5] != null)
						onDate = (Date) empHist[5];

				}
				else {
					if (empHistList.get(i)[2] != null) 
						lastEmpName.append(empHistList.get(i)[2].toString()+" ");
					
					if (empHistList.get(i)[3] != null)
						lastEmpName.append(empHistList.get(i)[3].toString()+" ");
					
					if (empHistList.get(i)[4] != null) 
						lastEmpName.append(empHistList.get(i)[4].toString()+" ");
					
					map.put("empName", lastEmpName.toString());
					if(empHistList.get(i)[7] != null) {
						map.put("loginName", empHistList.get(i)[7].toString());
					}
				}
			}
			count--;
			i++;
		}
		map.putIfAbsent("FORWARD_TO_EMPLOYEE", builder.toString());
		return map;
	}
	 
	 @Override
	    @WebMethod(exclude = true)
	    public List<EmployeeBean> getAllActiveEmployee(final Long orgId) {
	        final List<Employee> entities = employeeJpaRepository.getAllActiveEmployee(orgId);
	        final List<EmployeeBean> beans = new ArrayList<>();
	        for (final Employee employeeEntity : entities) {
	            beans.add(employeeServiceMapper.mapEmployeeEntityToEmployee(employeeEntity));
	        }
	        return beans;
	    }
	 
	   @Override
	   @WebMethod(exclude = true)
	   public List<Employee> getActiveEmployeeList(final Long orgId) {
		 List<Employee> entities = new ArrayList<>();
		 entities = employeeJpaRepository.getAllActiveEmployee(orgId);
		 return entities;
	   }

	@Override
	public Date getEmployeeUpdatedDateByEmpId(Long empId, Long orgId) {
		return employeeJpaRepository.getEmployeeUpdatedDate(empId, orgId);
	}

	@Override
	@Transactional(readOnly=true)
	public List<Object[]> findAllEmpIntialInfoByOrg(Long orgId) {
		return employeeJpaRepository.findAllEmpIntialInfoByOrg(orgId);
	}

	


	@Override
	@Transactional
	public List<CommonMasterDto> getMasterDetail(Long orgid) {
		List<CommonMasterDto> masterList = new ArrayList<>();
		Class<?> clazz = null;
		Object dynamicServiceInstance = null;
		String serviceClassName = null;
		try {
	       	serviceClassName = "com.abm.mainet.sfac.service.IAMasterServiceImpl";
            clazz = ClassUtils.forName(serviceClassName, ApplicationContextProvider.getApplicationContext()
                    .getClassLoader());

			clazz = ClassUtils.forName(serviceClassName,
					ApplicationContextProvider.getApplicationContext().getClassLoader());

			dynamicServiceInstance = ApplicationContextProvider.getApplicationContext().getAutowireCapableBeanFactory()
					.autowire(clazz, 4, false);
			final Method method = ReflectionUtils.findMethod(clazz,"getMasterDetail",	
					new Class[] {Long.class});
			
			masterList = (List<CommonMasterDto>) ReflectionUtils.invokeMethod(method, dynamicServiceInstance,
					new Object[] { orgid });
		} catch (LinkageError | Exception e) {
			LOGGER.error(e.getMessage());
			throw new FrameworkException("Exception in fetching master list for application id : " + orgid, e);
		}
		return masterList;
	}

	@Override
	@Transactional
	public String getMasterName(Long orgid, Long mastId) {
		String result = null;
		try {
			Class<?> clazz = null;
			Object dynamicServiceInstance = null;
			String serviceClassName = "com.abm.mainet.sfac.service.IAMasterServiceImpl";

			clazz = ClassUtils.forName(serviceClassName,
					ApplicationContextProvider.getApplicationContext().getClassLoader());
			final Method method = ReflectionUtils.findMethod(clazz, "fetchNameById",
					new Class[] { Long.class, Long.class });
			dynamicServiceInstance = ApplicationContextProvider.getApplicationContext().getAutowireCapableBeanFactory()
					.autowire(clazz, 4, false);
			result = (String) ReflectionUtils.invokeMethod(method, dynamicServiceInstance,
					new Object[] { orgid, mastId });
		} catch (LinkageError | Exception e) {
			throw new FrameworkException(
					"Exception in fetching master name in getMasterName method by orgid and masid " + e);
		}
		return result;
	}

	/* (non-Javadoc)
	 * @see com.abm.mainet.common.service.IEmployeeService#getEmployeeDataForSFAC(java.lang.Long, java.lang.Long, java.lang.Long, java.lang.Long, java.lang.Long, java.lang.Long)
	 */
	@Override
	@WebMethod(exclude = true)
	public List<EmployeeBean> getEmployeeDataForSFAC(Long deptId, Long locId, Long designId, Long orgid, Long gmid,
			Long masId) {
        List<Employee> employeeEntity = null;
        employeeEntity = employeeDAO.findEmployeeDataByIdsSFAC(deptId, locId, designId, orgid,gmid,masId);

        final List<EmployeeBean> beans = new ArrayList<>();

        for (final Employee empEntity : employeeEntity) {

            beans.add(employeeServiceMapper.mapEmployeeEntityToEmployee(empEntity));

        }
        return beans;

    }

	/* (non-Javadoc)
	 * @see com.abm.mainet.common.service.IEmployeeService#fetchAllgroupList()
	 */
	@Override
	public Map<Long, String> fetchAllgroupList() {
        List<GroupMaster> grpEntity = null;
        grpEntity = employeeJpaRepository.fetchAllgroupList();
        final Map<Long, String> list = new LinkedHashMap<>();

        if ((grpEntity != null) && !grpEntity.isEmpty()) {
            for (final GroupMaster mas : grpEntity) {
                list.put(mas.getGmId(), mas.getGrCode());
            }

        }
        return list;
    }

	/* (non-Javadoc)
	 * @see com.abm.mainet.common.service.IEmployeeService#findAllEmpForReporting()
	 */
	@Override
	public List<Object[]> findAllEmpForReporting() {
        final List<Object[]> entities = employeeJpaRepository.findAllEmpForReporting();
        return entities;
    
	}

	/**
	 * @param activeInactiveStatus
	 * @param cbboId
	 */
	@Transactional
	@Override
	public void updateIsDeletedFlagByMasId(String status, Long masId,Long userId,String uniqueId,Long orgId) {
		try {
			employeeJpaRepository.updateIsDeletedFlagByMasId(status,masId,userId,uniqueId,orgId);
		} catch (Exception e) {
			LOGGER.error(e.getMessage()+masId);
		}
	}
	
		
	@Override
    public List<Object[]> findAllActiveEmployeeByLocation(Long locId) {
        final List<Object[]> entities = employeeJpaRepository.findAllActiveEmployeeByLocation(locId);
        return entities;
    }
    
    
	@Override
	public String getEmpFullNameById(Long empId) {
		final Object[] empObject = employeeJpaRepository.getEmpFullNameByEmpId(empId);
		if (empObject.length == 0) {
			return MainetConstants.BLANK;
		} else {
			Object[] employee = (Object[]) empObject[0];
			String fName = employee[0].toString();
			String lName = employee[1].toString();

			if (employee[0].toString() == null)
				fName = MainetConstants.BLANK;
			if (employee[1].toString() == null)
				fName = MainetConstants.BLANK;

			return fName + MainetConstants.WHITE_SPACE + lName;
		}

	}
    
	 @Override
	    @Transactional(readOnly = true)
	    @GET
	    @Produces(value = { MediaType.APPLICATION_JSON_VALUE })
	    @Path(value = "/findemployeeLatLongAndPhoto/empId/{empId}")
	    public Map<String, String> findEmployeeLocLatLongAndPhoto(@PathParam("orgId") final Long orgId,@PathParam("empId") final Long empId) {
		 Employee emp=  employeeJpaRepository.findOne(empId);
		 Map<String, String> empMap=new HashMap<>();
		 if(emp!=null ) {
			 if(StringUtils.isNotBlank(emp.getEmpphotopath())) {
				 String byteCode=null;
				 String filename=emp.getEmpphotopath().substring(
						 emp.getEmpphotopath().lastIndexOf(File.separator) + 1, emp.getEmpphotopath().length());
					String filePath=emp.getEmpphotopath().substring(0,emp.getEmpphotopath().lastIndexOf(File.separator));
					 try {
						byteCode=Utility.convertInByteCode(filename, filePath);
					} catch (Exception e) {
						LOGGER.error("Exception occure at the time of converting image to byte code"+e);
					}
					empMap.put("photoByteCode", byteCode);
			 }
			 LocationMasEntity loc= emp.getTbLocationMas();
			 if(loc!=null) {
				 empMap.put("lattitude", loc.getLatitude()); 
				 empMap.put("longitude", loc.getLongitude());
			 }
		 }
	        return empMap;
	    }
	 
	 
	@Override
	public List<Object[]> getEmployeesForVehicleDriverMas(Long orgId, String desgDriver) {
		return employeeJpaRepository.getEmployeesForVehicleDriverMas(orgId, desgDriver);
	}
	
	@Override
	@WebMethod(exclude = true)
	public List<EmployeeBean> getAllEmployeeWithRole(Long orgId, Map<Long, String> groupLookup,String roleCode) {
		List<Employee> entities = new ArrayList<>();
		
		entities = employeeJpaRepository.getAllEmployee(orgId);
		final List<EmployeeBean> beans = new ArrayList<>();
		for (final Employee employeeEntity : entities) {
			beans.add(employeeServiceMapper.mapEmployeeEntityToEmployeeWithRole(employeeEntity, groupLookup));
		}
		return beans;
	}
	
	@Override
	public boolean saveEmpGrid(Long empid, String name, String mob, String oldNo) {
		// TODO Auto-generated method stub
		List<Employee> employeeCheck = new ArrayList<>();
		if(!mob.equalsIgnoreCase(oldNo)) {
			employeeCheck = employeeJpaRepository.findDistinctByEmpmobno(mob);
		}
		if(employeeCheck.size()>0) {

			return false;

		}else {
			Employee employee = employeeJpaRepository.findOne(empid);
			StringBuilder sb = new StringBuilder();
			employee.setEmpmobno(mob);
			String[] words = name.split("\\s+");
			if(words[0]!=null)
				employee.setEmpname(words[0].toString());
			if(words.length>2) {
				employee.setEmpmname(words[1].toString());
				if(words.length>3) {
					for(int i =3; i <= words.length; i ++) {
						sb.append(words[i-1].toString());
						sb.append(" ");

					}
					employee.setEmplname(sb.toString());
				}else {
					employee.setEmplname(words[2].toString());

				}

			}else {
				if(words.length==2) {
					employee.setEmplname(words[1].toString());
				}else {
					employee.setEmplname("");
				}
				employee.setEmpmname("");
			}
			
			

			EmployeeHistory emp_hist = new EmployeeHistory();
			employee = employeeJpaRepository.save(employee);
			emp_hist.setStatus(MainetConstants.InsertMode.UPDATE.getStatus());
			//US#148162
			employee.setLmodDate(new Date());
			auditService.createHistory(employee, emp_hist);
			return true;
		}


	}
	
	@Override
	@Transactional
	public boolean saveEmpGridWithWardZone(Long empid, String name, String mob, String oldNo, String zone,
			String ward, String sectorTehsil, String sector, Organisation org, String oldZone, String oldWard, String olsectorTehsil, String oldSector) {
		// TODO Auto-generated method stub
		String lgIp = UserSession.getCurrent().getEmployee().getEmppiservername();
		

    	
		
		EmployeeWardZoneMapping employeeWardZoneMapping = empWardZoneRepository.findByEmpId(empid);
		if(employeeWardZoneMapping!=null) {
			employeeWardZoneMapping.setZones(zone);
			employeeWardZoneMapping.setWards(ward);
			employeeWardZoneMapping.setLoc1(zone);
			employeeWardZoneMapping.setLoc2(ward);
			employeeWardZoneMapping.setLoc3(sectorTehsil);
			employeeWardZoneMapping.setLoc4(sector);
			employeeWardZoneMapping.setLgIpMacUpd(lgIp);
			employeeWardZoneMapping.setUpdatedBy(org.getOrgid());
			employeeWardZoneMapping.setUpdatedDate(new Date());
			
			/*
			 * List<Long> oldWards = Stream.of(oldZone.split(",")) .map(Long::parseLong)
			 * .collect(Collectors.toList());
			 * 
			 * List<Long> oldWardsAgain = Stream.of(oldZone.split(","))
			 * .map(Long::parseLong) .collect(Collectors.toList());
			 * 
			 * 
			 * List<Long> newWards = Stream.of(zone.split(",")) .map(Long::parseLong)
			 * .collect(Collectors.toList());
			 */
	    	
			/*
			 * oldZones.removeAll(newZards);
			 * 
			 * newWards.removeAll(oldZardsAgain);
			 */
	    	
			/*
			 * if(oldWards.size()>0) iWorkFlowTypeService.removeWardsInWorkflow(empid,
			 * oldWards);
			 * 
			 * if(newWards.size()>0) iWorkFlowTypeService.addWardsInWorkflow(empid,
			 * newWards);
			 */
	    	
			
			
		}else {

			employeeWardZoneMapping = new EmployeeWardZoneMapping();

			employeeWardZoneMapping.setEmpId(empid);
			employeeWardZoneMapping.setCreateDate(new Date());
			employeeWardZoneMapping.setCreatedBy(org.getOrgid());
			employeeWardZoneMapping.setOrgId(org);
			employeeWardZoneMapping.setZones(zone);
			employeeWardZoneMapping.setWards(ward);
			employeeWardZoneMapping.setLoc1(zone);
			employeeWardZoneMapping.setLoc2(ward);
			employeeWardZoneMapping.setLoc3(sectorTehsil);
			employeeWardZoneMapping.setLoc4(sector);
			employeeWardZoneMapping.setLgIpMac(lgIp);
			
			
			

		}

		empWardZoneRepository.saveAndFlush(employeeWardZoneMapping);


		return saveEmpGrid(empid, name, mob, oldNo);
	}
	
	@Override
	@WebMethod(exclude = true)
	public List<EmployeeBean> getEmployeeDataWithRole(Long deptId, Long locId, Long designId, Long orgid, Long gmid,
			Map<Long, String> groupLookup, Long zone, Long empId,  String roleCode) {
		List<Employee> employeeEntity = null;
		
		if(zone!=null){
			employeeEntity = employeeJpaRepository.findAllEmpWithAllCond(deptId, locId, designId, orgid, gmid, empId, zone.toString(), zone.toString()+",");
		}
		else {
		employeeEntity = employeeJpaRepository.findAllEmpWithAllCondNoWard(deptId, locId, designId, orgid, gmid, empId);
		}
		

		final List<EmployeeBean> beans = new ArrayList<>();

		for (final Employee empEntity : employeeEntity) {

			beans.add(employeeServiceMapper.mapEmployeeEntityToEmployeeWithRole(empEntity, groupLookup));

		}
		return beans;

	}
	
	@Override
    @WebMethod(exclude = true)
    public List<Employee> getActiveEmployeeByMobileNo(String empMobileNo) {
        final List<Employee> entities = employeeJpaRepository.getActiveEmployeeByEmpMobileNo(empMobileNo);
        return entities;
    }

	@Override
	public List<Employee> getEmployeeByEmpName(String empName) {

		 List<Employee> entities = new ArrayList<>();
		 entities = employeeJpaRepository.getEmployeeByEmpName(empName);
		 return entities;
	   
	}
	
	@Override
    public List<Employee> getAllListEmployeeByGmId(final long orgId, final long roleId) {
        return employeeDAO.getAllEmployeeByGmId(orgId, roleId);
    }
	
	@Override
    @Transactional
    public void updateEmployeeName(final String empName, final Long empId) {     
		employeeJpaRepository.updateEmpNameByEmpId(empName, empId, new Date());
    }
	
	@Override
    @Transactional
    public Long getGroupIdByGroupCode(Long orgId, String grCode) {     
		return employeeJpaRepository.getGroupIdByGroupCode(orgId, grCode);
	}
	
	@Override
	public List<Object[]> validateIsAuthUsersMobilesAndEmails(List<String> empmobnoList,  List<String> emailList) {
		 return employeeJpaRepository.validateIsAuthUsersMobilesAndEmails(empmobnoList, emailList);
} 
	@Override
    @Transactional(readOnly = true)
    @WebMethod(exclude = true)
    public List<Employee> getByGmIdAndWardZone(final List<Long> gmIds,final String loc1,final String loc2,final String loc3,final String loc4,final String loc5) {
        return employeeJpaRepository.getByGmIdAndWardZone(gmIds,loc1,loc2,loc3,loc4,loc5);
    }
	
	@Override
	public List<Object[]> findAllRolesForDept(Long orgId, String status,Long deptId) {
		final List<Object[]> entities = employeeJpaRepository.findAllRolesForDept(orgId,status,deptId);
		return entities;
	}
	
	@Override
    @WebMethod(exclude = true)
    public List<Employee> getActiveEmployeeByEmpMobileNoAndLoginName(String empMobileNo,String empLogName) {
        final List<Employee> entities = employeeJpaRepository.getActiveEmployeeByEmpMobileNoAndLoginName(empMobileNo,empLogName);
        return entities;
    }
} 
