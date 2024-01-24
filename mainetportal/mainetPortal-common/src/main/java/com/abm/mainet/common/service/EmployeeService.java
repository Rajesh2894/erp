package com.abm.mainet.common.service;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.ListIterator;
import java.util.Map;
import java.util.Set;

import javax.annotation.Resource;

import org.apache.commons.io.FileUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.abm.mainet.common.audit.service.AuditService;
import com.abm.mainet.common.authentication.ldap.IAuthenticationManager;
import com.abm.mainet.common.authentication.ldap.ILDAPManager;
import com.abm.mainet.common.authentication.ldap.UserProfile;
import com.abm.mainet.common.constant.MainetConstants;
import com.abm.mainet.common.constant.MainetConstants.PasswordValidType;
import com.abm.mainet.common.constant.PrefixConstants;
import com.abm.mainet.common.dao.EmployeeJpaRepository;
import com.abm.mainet.common.dao.EmployeeSessionRepository;
import com.abm.mainet.common.dao.IEmployeeDAO;
import com.abm.mainet.common.domain.Department;
import com.abm.mainet.common.domain.Designation;
import com.abm.mainet.common.domain.Employee;
import com.abm.mainet.common.domain.EmployeeHistory;
import com.abm.mainet.common.domain.EmployeeSession;
import com.abm.mainet.common.domain.GroupMaster;
import com.abm.mainet.common.domain.Organisation;
import com.abm.mainet.common.dto.EmployeeBean;
import com.abm.mainet.common.dto.EmployeeSearchDTO;
import com.abm.mainet.common.master.mapper.EmployeeServiceMapper;
import com.abm.mainet.common.util.ApplicationSession;
import com.abm.mainet.common.util.CommonMasterUtility;
import com.abm.mainet.common.util.LookUp;
import com.abm.mainet.common.util.PasswordManager;
import com.abm.mainet.common.util.UserSession;
import com.abm.mainet.common.util.Utility;
import com.abm.mainet.dms.client.FileNetApplicationClient;
import com.abm.mainet.dms.utility.FileUploadUtility;

import com.abm.mainet.smsemail.dto.SMSAndEmailDTO;
import com.abm.mainet.smsemail.service.ISMSAndEmailService;

@Service
public class EmployeeService implements IEmployeeService {
	private static final Logger LOGGER = Logger.getLogger(EmployeeService.class);
	private static final String NOUSER = "NOUSER";

	@Autowired
	@Qualifier("ldapManager")
	private ILDAPManager ldapManager;

	@Autowired
	@Qualifier("authManager")
	private IAuthenticationManager authManager;

	@Autowired
	private IEmployeeDAO employeeDAO;

	@Resource
	private EmployeeJpaRepository employeeJpaRepository;

	@Resource
	private EmployeeServiceMapper employeeServiceMapper;

	@Autowired
	private AuditService auditService;

	@Autowired
	private ISMSAndEmailService ismsAndEmailService;

	@Autowired
	private IGroupMasterService groupMasterService;

	@Autowired
	private EmployeeSessionRepository employeeSessionRepository;

	@Override
	@Transactional
	public Employee getEmployeeById(final Long empId, final Organisation organisation, final String isdeleted) {
		return employeeDAO.getEmployeeById(empId, organisation, isdeleted);
	}

	@Override
	@Transactional
	public List<Employee> getEmployeeByEmpEMail(final String empEMail, final Organisation organisation,
			final String isDeleted) {
		return employeeDAO.getEmployeeByEmailId(empEMail, organisation, isDeleted);
	}

	@Override
	@Transactional
	public Employee getAuthenticatedEmployee(final String emploginname, final String emppassword, final Long emplType,
			final Organisation organisation, final String isdeleted, final String type) {
		return employeeDAO.getAuthenticatedEmployee(emploginname, Utility.encryptPassword(emploginname, emppassword),
				emplType, organisation, isdeleted, type);
	}

	@Override
	@Transactional
	public Employee getAuthenticatedEmployee(final String emploginname, final String emppassword, final Long emplType,
			final Organisation organisation, final String isdeleted) {
		return employeeDAO.getAuthenticatedEmployee(emploginname, Utility.encryptPassword(emploginname, emppassword),
				emplType, organisation, isdeleted);
	}

	@Override
	@Transactional
	public List<Employee> getEmployeeByEmpEMailAndType(final String empEMail, final Long empType,
			final Organisation organisation, final String isDeleted, final boolean isAgency) {
		return employeeDAO.getEmployeeByEmailIdAndEmpType(empEMail, empType, organisation, isDeleted, isAgency);
	}

	@Override
	@Transactional
	public List<Employee> getEmployeeByEmpMobileNo(final String empMobileNo, final Organisation organisation,
			final String isDeleted, Long emplType) {
		return employeeDAO.getEmployeeByEmpMobileNo(empMobileNo, organisation, isDeleted, emplType);

	}

	@Override
	@Transactional
	public List<Employee> getEmployeeByEmpMobileNoAndType(final String empMobileNo, final Long empType,
			final Organisation organisation, final String isDeleted, final boolean isAgency) {
		final List<Employee> employeeList = employeeDAO.getEmployeeByEmpMobileNoAndEmpType(empMobileNo, empType,
				organisation, isDeleted, isAgency);
		return employeeList;
	}

	@Override
	@Transactional
	public Employee saveEmployeeDetails(final Employee newEmployee, final Organisation organisation,
			final Designation designation, final Department department, final Employee userId) {
		setEmployeeCreateParams(newEmployee, organisation, designation, department, userId);
		final Employee savedEmployee = employeeDAO.saveEmployee(newEmployee);
		return savedEmployee;
	}

	@Override
	@Transactional
	public Employee saveAgencyEmployeeDetails(final Employee newEmployee, final Organisation organisation,
			final Designation designation, final Department department, final Long userId) {
		final Employee savedEmployee = employeeDAO.saveAgencyEmployeeDetails(newEmployee);
		return savedEmployee;
	}

	@Override
	@Transactional
	public Employee updateEmployeeDetails(final Employee updateEmployee, final Employee userId) {
		setEmployeeUpdateParams(updateEmployee, userId);
		final Employee employeeUpdated = employeeDAO.saveEmployee(updateEmployee);

		final UserProfile uProfile = new UserProfile();
		uProfile.setFirstName(employeeUpdated.getEmpname());
		uProfile.setLastName(employeeUpdated.getEmpLName());
		uProfile.setPwd(employeeUpdated.getEmppassword());
		uProfile.setRole(employeeUpdated.getGmid().toString());
		uProfile.setuID(employeeUpdated.getEmploginname());
		uProfile.setUserType(MainetConstants.NEC.CITIZEN);

		ldapManager.updateUser(uProfile);

		return employeeUpdated;
	}

	@Override
	@Transactional
	public Employee updateEmployeePassword(final Employee updateEmployee, final String newPassword,
			final Employee userId) {

		setEmployeeUpdateParams(updateEmployee, userId);
		updateEmployee.setMobNoOtp(Utility.encryptPassword(updateEmployee.getEmploginname(), newPassword));
		final Employee employeeUpdated = employeeDAO.saveEmployee(updateEmployee);

		final UserProfile uProfile = new UserProfile();
		uProfile.setFirstName(employeeUpdated.getEmpname());
		uProfile.setLastName(employeeUpdated.getEmpLName());
		uProfile.setPwd(employeeUpdated.getEmppassword());
		uProfile.setRole(employeeUpdated.getGmid().toString());
		uProfile.setuID(employeeUpdated.getEmploginname());
		uProfile.setUserType(MainetConstants.NEC.CITIZEN);

		ldapManager.updateUser(uProfile);

		return employeeUpdated;
	}

	@Override
	@Transactional
	public Employee setEmployeePassword(final Employee updateEmployee, final String newPassword,
			final Employee userId) {
		if (updateEmployee.getAutMob().equals(MainetConstants.IsDeleted.NOT_DELETE)) {
			updateEmployee.setAutMob(MainetConstants.IsDeleted.DELETE);
		}
		setEmployeeUpdateParams(updateEmployee, userId);
		updateEmployee.setEmppassword(Utility.encryptPassword(updateEmployee.getEmploginname(), newPassword));
		final Employee employeeUpdated = employeeDAO.saveEmployee(updateEmployee);
		if (!userId.getEmploginname().equalsIgnoreCase(NOUSER)
				&& userId.getLoggedIn().equalsIgnoreCase(MainetConstants.IsDeleted.DELETE)) {
			userId.setEmppassword(employeeUpdated.getEmppassword());
		}
		final UserProfile uProfile = new UserProfile();
		uProfile.setFirstName(employeeUpdated.getEmpname());
		uProfile.setLastName(employeeUpdated.getEmpLName());
		uProfile.setPwd(employeeUpdated.getEmppassword());
		if (employeeUpdated.getGmid() != null) {
			uProfile.setRole(employeeUpdated.getGmid().toString());
		}
		uProfile.setuID(employeeUpdated.getEmploginname());
		uProfile.setUserType(MainetConstants.NEC.CITIZEN);

		ldapManager.updateUser(uProfile);

		return employeeUpdated;
	}

	/**
	 * @Method private method to set {@link Employee} creation time parameter.
	 * @param newEmployee        {@link Employee}
	 * @param organisation       {@link Organisation}
	 * @param departmentLocation {@link DepartmentLocation}
	 * @param designation        {@link Designation}
	 * @param department         {@link Department}
	 * @param userId             {@link Employee}
	 */
	private void setEmployeeCreateParams(final Employee newEmployee, final Organisation organisation,
			final Designation designation, final Department department, final Employee userId) {
		new SimpleDateFormat(MainetConstants.COMMOM_TIME_FORMAT);
		final Date date = new Date();

		newEmployee.setOrganisation(organisation);
		newEmployee.setOndate(date);
		newEmployee.setIsdeleted(MainetConstants.IsDeleted.ZERO);
		// Default_value_is_"N"
		newEmployee.setAutEmail(MainetConstants.IsDeleted.NOT_DELETE);// Default_value_is_"N"
		/*
		 * if ((UserSession.getCurrent().getAapleTrackId() != null) &&
		 * !UserSession.getCurrent().getAapleTrackId().isEmpty()) {
		 * newEmployee.setAutMob(MainetConstants.IsDeleted.DELETE);
		 * newEmployee.setEmppassword(newEmployee.getEmppassword()); } else {
		 */
		// }
		newEmployee.setAutMob(MainetConstants.IsDeleted.NOT_DELETE);
		newEmployee.setMobNoOtp(Utility.encryptPassword(newEmployee.getEmploginname(), newEmployee.getMobNoOtp()));
		newEmployee.setDesignation(designation);
		newEmployee.setTbDepartment(department);
		if(userId!=null)
		newEmployee.setUserId(userId.getUserId());
		newEmployee.setLangId(UserSession.getCurrent().getLanguageId());
		newEmployee.setIsUploaded(MainetConstants.IsUploaded.NOT_UPLOADED);
	}

	/**
	 * @Method private method to set {@link Employee} update time parameter.
	 * @param newEmployee {@link Employee}
	 * @param userId      {@link Employee}
	 */
	private void setEmployeeUpdateParams(final Employee updateEmployee, final Employee userId) {
		final Date date = new Date();
		updateEmployee.setUpdatedDate(date);
		updateEmployee.setUpdatedBy(userId.getUserId());
		updateEmployee.setLangId(UserSession.getCurrent().getLanguageId());
	}

	@Override
	@Transactional
	public Employee getEmployeeByLoginName(final String emploginname, final Organisation organisation,
			final String isDeleted) {
		return employeeDAO.getEmployeeByLoginName(emploginname, organisation, isDeleted);
	}

	@Override
	@Transactional
	public List<Employee> getEmployeeListByLoginName(final String emploginname, final Organisation organisation,
			final String isDeleted) {
		return employeeDAO.getEmployeeListByLoginName(emploginname, organisation, isDeleted);
	}

	@Override
	@Transactional
	public boolean updateEmpDetails(final Employee emp) {
		final boolean retVal = employeeDAO.updateEmpDetails(emp);

		if (retVal) {
			final UserProfile uProfile = new UserProfile();
			uProfile.setFirstName(emp.getEmpname());
			uProfile.setLastName(emp.getEmpLName());
			uProfile.setPwd(emp.getEmppassword());
			if (emp.getGmid() != null) {
				uProfile.setRole(emp.getGmid().toString());
			}
			uProfile.setuID(emp.getEmploginname());
			uProfile.setUserType(MainetConstants.NEC.CITIZEN);
			ldapManager.updateUser(uProfile);
		}

		return retVal;
	}

	@Override
	@Transactional
	public Employee saveEmployee(final Employee employee) {
		final Employee emp = employeeDAO.saveEmployee(employee);
		final UserProfile uProfile = new UserProfile();
		uProfile.setFirstName(emp.getEmpname());
		uProfile.setLastName(emp.getEmpLName());
		uProfile.setPwd(emp.getEmppassword());
		uProfile.setRole(emp.getGmid().toString());
		uProfile.setuID(emp.getEmploginname());
		uProfile.setUserType(MainetConstants.NEC.CITIZEN);
		ldapManager.updateUser(uProfile);
		return emp;
	}

	@Override
	@Transactional
	public List<Employee> getAllAgencyList() {
		return employeeDAO.getAllAgencyList(UserSession.getCurrent().getOrganisation());
	}

	@Override
	@Transactional
	public Employee getAgencyByEmplTypeAndMobile(final String mobile, final Long agencyType,
			final Organisation organisation, final String isDeleted) {

		return employeeDAO.getAgencyByEmplTypeAndMobile(mobile, agencyType, organisation, isDeleted);
	}

	@Override
	@Transactional
	public Employee getAgencyByEmplTypeAndEmail(final String email, final Long agencyType,
			final Organisation organisation, final String isDeleted) {

		return employeeDAO.getAgencyByEmplTypeAndEmail(email, agencyType, organisation, isDeleted);
	}

	@Override
	@Transactional
	public Employee getAgencyByEmplTypeAndEmpLoginName(final String empLoginName, final Long agencyType,
			final Organisation organisation, final String isDeleted) {

		return employeeDAO.getAgencyByEmplTypeAndEmploginName(empLoginName, agencyType, organisation, isDeleted);
	}

	@Override
	@Transactional
	public void saveEditProfileInfo(final Employee modifiedEmployee) {
		modifiedEmployee.updateAuditFields();
		employeeDAO.persistModifiedCitizenInfo(modifiedEmployee);

		final UserProfile uProfile = new UserProfile();
		uProfile.setFirstName(modifiedEmployee.getEmpname());
		uProfile.setLastName(modifiedEmployee.getEmpLName());
		uProfile.setPwd(modifiedEmployee.getEmppassword());
		uProfile.setRole(modifiedEmployee.getGmid().toString());
		uProfile.setuID(modifiedEmployee.getEmploginname());
		uProfile.setUserType(MainetConstants.NEC.CITIZEN);
		ldapManager.updateUser(uProfile);
	}

	@Override
	@Transactional
	public void setEmployeeLoggedInFlag(final Employee currentLoggedInEmployee) {
		currentLoggedInEmployee.setLoggedIn(MainetConstants.IsDeleted.DELETE);
		currentLoggedInEmployee.setLastLoggedIn(new Date());
		employeeDAO.setEmployeeLoggedInFlag(currentLoggedInEmployee);
	}

	@Override
	@Transactional
	public void resetEmployeeLoggedInFlag(final Employee loggingOutEmployee) {
		if (loggingOutEmployee.getEmpId() != null) {
			employeeDAO.resetEmployeeLoggedInFlag(MainetConstants.IsLookUp.STATUS.NO, loggingOutEmployee.getEmpId());
		}
	}

	@Transactional
	@Override
	public List<EmployeeSearchDTO> getAllEmployeeInfoByOrgID(final String empName) {
		return employeeDAO.findEmployeeInfo(empName, UserSession.getCurrent().getOrganisation());
	}

	@Override
	public Employee saveEmployeeForAgency(final Employee employee) {

		return employeeDAO.saveEmployeeForAgency(employee);
	}

	@Override
	@Transactional
	public Employee changeMobileNumber(final Employee emp, final String newMobNo) {

		return employeeDAO.changeMobileNumber(emp, newMobNo);
	}

	public List<LookUp> getAllEmployee(final List<Object[]> emp) {
		final List<LookUp> list = new ArrayList<>(0);

		final ListIterator<Object[]> listIterator = emp.listIterator();
		while (listIterator.hasNext()) {
			final Object[] obj = listIterator.next();

			final LookUp title = getEmpTitle(obj);
			final String fname = (String) obj[1];
			String lname = MainetConstants.WHITE_SPACE;
			if (obj[2] != null) {
				lname = (String) obj[2];
			}

			String fullName = MainetConstants.WHITE_SPACE;

			if (title.getLookUpDesc() != null) {
				fullName = title.getLookUpDesc() + MainetConstants.WHITE_SPACE + fname + MainetConstants.WHITE_SPACE
						+ lname;
			} else {
				fullName = MainetConstants.WHITE_SPACE + fname + MainetConstants.WHITE_SPACE + lname;
			}

			final LookUp lookUp = new LookUp(MainetConstants.BLANK, fullName);
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
	public Map<Long, String> getEmployeeLookUp() {

		return employeeDAO.getEmployeeLookUp(UserSession.getCurrent().getOrganisation());
	}

	@Override
	@Transactional(readOnly = true)
	public Employee getAdminEncryptAuthenticatedEmployee(final String emploginname, final String emppassword,
			final Long emplType, final Long empId, final Organisation organisation, final String isdeleted) {
		if (!authManager.authenticateUser(emploginname, emppassword)) {
			return null;
		}
		return employeeDAO.getAdminAuthenticatedEmployee(emploginname, emppassword, emplType, empId, organisation,
				isdeleted);
	}

	@Override
	public void setTpLicenseDataFromAuthorizer(final Employee entity) {
		if ((entity.getAuthStatus() != null) && entity.getAuthStatus().equals(MainetConstants.AuthStatus.APPROVED)) {
			employeeDAO.generateLicenseData(entity);
		}

	}

	@Override
	@Transactional(readOnly = true)
	public int getCountOfGroup(final Long gmId, final Long orgId, final String isDeleted) {

		return employeeDAO.getCountOfGroup(gmId, orgId, isDeleted);
	}

	@Override
	@Transactional(readOnly = true)
	public boolean isUniqueUserAlias(final String userAlias, final Organisation organisation) {
		return employeeDAO.isUniqueUserAlias(userAlias, organisation);
	}

	@Override
	@Transactional(readOnly = true)
	public Employee getEmployeeByUserAlias(final String userAlias, final Organisation organisation,
			final String isDeleted) {
		return employeeDAO.getEmployeeByUserAlias(userAlias, organisation, isDeleted);
	}

	private void setAgencyEmployeeUpdatedParams(final Employee updateEmployee) {
		final Date date = new Date();
		updateEmployee.setUpdatedDate(date);
		updateEmployee.setUpdatedBy(updateEmployee.getUserId());
		updateEmployee.setLangId(UserSession.getCurrent().getLanguageId());

	}

	@Override
	@Transactional
	public Employee updatedAgencyEmployeeDetails(final Employee employee) {
		setAgencyEmployeeUpdatedParams(employee);
		final Employee employeeUpdated = employeeDAO.saveUpdatedAgencyEmployeeDetails(employee);
		return employeeUpdated;
	}

	@Override
	@Transactional
	public Employee getAuthenticatedAgencyEmployee(final String emploginname, final String emppassword,
			final Long emplType, final Organisation organisation, final String isdeleted, final String type) {

		return employeeDAO.getAuthenticatedAgencyEmployee(emploginname,
				Utility.encryptPassword(emploginname, emppassword), emplType, organisation, isdeleted, type);

	}

	@Override
	@Transactional
	public Employee setAgencyEmployeePassword(final Employee updateEmployee, final String newPassword) {
		if (updateEmployee.getAutMob().equals(MainetConstants.IsDeleted.NOT_DELETE)) {
			updateEmployee.setAutMob(MainetConstants.IsDeleted.DELETE);
		}
		setAgencyEmployeeUpdatedParams(updateEmployee);
		updateEmployee.setEmppassword(Utility.encryptPassword(updateEmployee.getEmploginname(), newPassword));
		final Employee employeeUpdated = employeeDAO.saveUpdatedAgencyEmployeeDetails(updateEmployee);
		return employeeUpdated;
	}

	@Override
	@Transactional
	public List<Employee> getAgencyEmployeeByEmpMobileNo(final String empMobileNo, final Organisation organisation,
			final String isDeleted) {
		return employeeDAO.getAgencyEmployeeByEmpMobileNo(empMobileNo, organisation, isDeleted);
	}

	@Override
	@Transactional
	public Employee getAuthenticatedEncryptAgencyEmployee(final String emploginname, final String emppassword,
			final Long emplType, final Organisation organisation, final String isdeleted) {

		return employeeDAO.getAuthenticatedAgencyEmployee(emploginname, emppassword, emplType, organisation, isdeleted);
	}

	@Override
	@Transactional
	public void setAgencyEmployeeLoggedInFlag(final Employee currentLoggedInEmployee) {

		currentLoggedInEmployee.setLoggedIn(MainetConstants.IsDeleted.DELETE);
		currentLoggedInEmployee.setLastLoggedIn(new Date());
		employeeDAO.setAgencyEmployeeLoggedInFlag(currentLoggedInEmployee);

	}

	@Override
	@Transactional
	public Long findCountOfRegisteredEmployee() {
		return employeeDAO.findCountOfEmployee();
	}

	@Override
	@Transactional
	public Long findCountOfLoggedInUser() {
		return employeeDAO.findCountOfLoggedInUser();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.abm.mainetservice.web.masters.employee.business.service.EmployeeService#
	 * findAllEmpByDept(java.lang.Long, java.lang.Long)
	 */

	@Transactional(readOnly = true)
	public List<Employee> findAllEmployeeByDept(final Long orgId, final Long deptId) {
		return employeeDAO.findAllEmployeeByDept(orgId, deptId);
	}

	@Override
	@Transactional
	public Employee create(Employee employee) {
		return employeeDAO.create(employee);
	}

	@Override
	@Transactional(readOnly = true)
	public List<Object[]> getAllEmpByDesignation(final Long desgId, final Long orgId) {
		final List<Object[]> empList = employeeDAO.getAllEmpByDesignation(desgId, orgId);
		return empList;
	}

	@Override
	public Map<Long, String> getGroupList(final Long orgid) {
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
	public List<EmployeeBean> getAllEmployee(final Long orgId) {
		final List<Employee> entities = employeeJpaRepository.getAllEmployee(orgId);
		final List<EmployeeBean> beans = new ArrayList<>();
		for (final Employee employeeEntity : entities) {
			beans.add(employeeServiceMapper.mapEmployeeEntityToEmployee(employeeEntity));
		}
		return beans;
	}

	@Override
	public List<EmployeeBean> getEmployeeData(final Long deptId, final Long locId, final Long designId,
			final Long orgid) {
		List<Employee> employeeEntity = null;

		if ((locId != null) && (designId != null)) {
			employeeEntity = employeeJpaRepository.findEmployeeData(deptId, designId, orgid);
		} else if ((locId == null) && (designId == null)) {
			employeeEntity = employeeJpaRepository.findEmployeeData(deptId, orgid);
		} else if ((locId == null) && (designId != null)) {
			employeeEntity = employeeJpaRepository.findEmployeeDatabyDesign(deptId, designId, orgid);
		}

		final List<EmployeeBean> beans = new ArrayList<>();

		for (final Employee empEntity : employeeEntity) {

			beans.add(employeeServiceMapper.mapEmployeeEntityToEmployee(empEntity));

		}
		return beans;

	}

	@Override
	public EmployeeBean findById(final Long empId) {
		final Employee employeeEntity = employeeJpaRepository.findOne(empId);
		return employeeServiceMapper.mapEmployeeEntityToEmployee(employeeEntity);
	}

	@Override
	public int validateEmployee(final String emploginname, final Long orgid) {
		int empCounter = 0;
		final Long count = employeeJpaRepository.validateEmployee(emploginname, orgid);
		if (count != null) {
			empCounter = count.intValue();
		}
		return empCounter;
	}

	@Override
	@Transactional
	public EmployeeBean create(final EmployeeBean employee, final String directory,
			final FileNetApplicationClient fileNetApplicationClient) {
		final Employee employeeEntity = new Employee();

		employee.setOndate(new Date());
		employee.setOrgid(Long.valueOf(UserSession.getCurrent().getOrganisation().getOrgid()));
		employee.setUserId(UserSession.getCurrent().getEmployee());
		employee.setLgIpMac(Utility.getMacAddress());
		employee.setIsUploaded(MainetConstants.Common_Constant.YES);
		employee.setAutMob(MainetConstants.Common_Constant.YES);
		employee.setAutEmail(MainetConstants.Common_Constant.YES);
		employee.setAuthStatus(MainetConstants.Common_Constant.ACTIVE_FLAG);

		employee.setEmppassword(Utility.encryptPassword(employee.getEmploginname(), employee.getNewPassword()));
		/* Calculate Password Expired date */
		employee.setEmpexpiredt(
				PasswordManager.calculatePasswordExpiredDate(PasswordValidType.EMPLOYEE.getPrifixCode()));
		employee.setLoggedIn(MainetConstants.Common_Constant.FREE);
		List<File> list = null;
		for (final Map.Entry<Long, Set<File>> entry : FileUploadUtility.getCurrent().getFileMap().entrySet()) {
			list = new ArrayList<>(entry.getValue());
			final Iterator<File> setFilesItr = entry.getValue().iterator();
			String tempDirPath = MainetConstants.operator.EMPTY;
			while (setFilesItr.hasNext()) {
				final File file = setFilesItr.next();
				tempDirPath = directory + MainetConstants.FILE_PATH_SEPARATOR + entry.getKey().toString();

				if (entry.getKey().longValue() == 0) {
					employee.setEmpphotopath(tempDirPath + MainetConstants.FILE_PATH_SEPARATOR + file.getName());
				}
				if (entry.getKey().longValue() == 1) {
					employee.setScansignature(tempDirPath + MainetConstants.FILE_PATH_SEPARATOR + file.getName());
				}
				if (entry.getKey().longValue() == 2) {
					employee.setEmpuiddocpath(tempDirPath);
					employee.setEmpuiddocname(file.getName());
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
		employeeEntity.setOndate(new Date());
		employeeEntity.setIsdeleted(MainetConstants.IsDeleted.ZERO);
		employeeEntity.setEmpphotopath(employee.getEmpphotopath());
		employeeEntity.setEmpuiddocname(employee.getEmpuiddocname());
		employeeEntity.setEmpuiddocpath(employee.getEmpuiddocpath());
		final Employee employeeEntitySaved = employeeJpaRepository.save(employeeEntity);

		EmployeeHistory emp_hist = new EmployeeHistory();
		emp_hist.setStatus(MainetConstants.InsertMode.ADD.getStatus());
		auditService.createHistory(employeeEntitySaved, emp_hist);

		flushServerFolder();

		final EmployeeBean empBean = employeeServiceMapper.mapEmployeeEntityToEmployee(employeeEntitySaved);

		final UserProfile uProfile = new UserProfile();
		uProfile.setFirstName(empBean.getEmpname());
		uProfile.setLastName(empBean.getEmpLName());
		uProfile.setPwd(empBean.getEmppassword());
		uProfile.setRole(empBean.getGmid().toString());
		uProfile.setuID(empBean.getEmploginname());
		uProfile.setUserType(MainetConstants.MENU.D);

		ldapManager.createUser(uProfile);

		final SMSAndEmailDTO dto = new SMSAndEmailDTO();
		dto.setEmail(employee.getEmpemail());
		dto.setMobnumber(employee.getEmpmobno());
		dto.setUserName(employee.getEmploginname());
		dto.setAppName(employee.getEmpname() + " " + employee.getEmpLName());
		dto.setOrganizationName(employeeEntitySaved.getOrganisation().getONlsOrgname());
		dto.setPassword(employee.getNewPassword());
		ismsAndEmailService.sendEmailSMS(MainetConstants.AGENCY.NAME.CFC, MainetConstants.AGENCY.URL.REGISTRATION,
				PrefixConstants.SMS_EMAIL_ALERT_TYPE.APPROVAL, dto, employeeEntitySaved.getOrganisation(),
				MainetConstants.DEFAULT_LANGUAGE_ID);

		return empBean;
	}

	@Override
	@Transactional
	public EmployeeBean updateEmployee(final EmployeeBean employee, final String directry,
			final FileNetApplicationClient filenetClient) {
		final Employee employeeEntity = employeeJpaRepository.findOne(employee.getEmpId());

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
						employee.setEmpphotopath(tempDirPath + MainetConstants.FILE_PATH_SEPARATOR + file.getName());
					} else {
						if (employeeEntity.getEmpphotopath() != null)
							employee.setEmpphotopath(employeeEntity.getEmpphotopath());
					}
					if ((entry.getKey().longValue() == 1) && (file != null)) {
						employee.setScansignature(tempDirPath + MainetConstants.FILE_PATH_SEPARATOR + file.getName());
					} else {
						if (employeeEntity.getScansignature() != null)
							employee.setScansignature(employeeEntity.getScansignature());
					}
					if ((entry.getKey().longValue() == 2) && (file != null)) {
						employee.setEmpuiddocpath(tempDirPath);
						employee.setEmpuiddocname(file.getName());
					} else {
						if (employeeEntity.getEmpuiddocpath() != null && employeeEntity.getEmpuiddocname() != null) {
							employee.setEmpuiddocpath(employeeEntity.getEmpuiddocpath());
							employee.setEmpuiddocname(employeeEntity.getEmpuiddocname());
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

			employee.setEmpphotopath(employeeEntity.getEmpphotopath());
			employee.setScansignature(employeeEntity.getScansignature());
			employee.setEmpuiddocpath(employeeEntity.getEmpuiddocpath());
			employee.setEmpuiddocname(employeeEntity.getEmpuiddocname());
		}
		flushServerFolder();

		if (employeeEntity != null) {
			employee.setDsgid(null);
			if (employeeEntity.getTbDepartment() != null) {
				employee.setDpDeptid(Long.valueOf(employeeEntity.getTbDepartment().getDpDeptid()));
			}
			employee.setEmpisecuritykey(employeeEntity.getEmpisecuritykey());
			employee.setAddFlag(employeeEntity.getAddFlag());
			employee.setAutBy(employeeEntity.getAutBy());
			employee.setAutDate(employeeEntity.getAutDate());
			employee.setAuthStatus(employeeEntity.getAuthStatus());
			employee.setAutMob(employeeEntity.getAutMob());
			employee.setCentraleno(employeeEntity.getCentraleno());
			employee.setEmplType(employeeEntity.getEmplType());
			employee.setEmpCorAddress1(employeeEntity.getEmpCorAddress1());
			employee.setEmpCorAddress2(employeeEntity.getEmpCorAddress2());
			employee.setCorPincode(employeeEntity.getCorPincode());
			employee.setEmppayrollnumber(employeeEntity.getEmppayrollnumber());
			employee.setAddFlag(employeeEntity.getAddFlag());
			employee.setEmpnew(employeeEntity.getEmpnew());
			employee.setOndate(new Date());
			employee.setOrgid(Long.valueOf(employeeEntity.getOrganisation().getOrgid()));
			employee.setUpdatedBy(UserSession.getCurrent().getEmployee());
			employee.setUpdatedDate(new Date());
			employee.setLgIpMac(employeeEntity.getLgIpMac());
			employee.setLgIpMacUpd(Utility.getMacAddress());
			employee.setIsUploaded(employeeEntity.getIsUploaded());
			if (employee.getModeFlag().equals(MainetConstants.Common_Constant.YES)
					&& (employee.getUpdatelogin() != null)
					&& employee.getUpdatelogin().equals(MainetConstants.Common_Constant.YES)) {
				employee.setEmppassword(Utility.encryptPassword(employee.getEmploginname(), employee.getNewPassword()));
				employee.setEmpexpiredt(
						PasswordManager.calculatePasswordExpiredDate(PasswordValidType.EMPLOYEE.getPrifixCode()));

			} else {
				employee.setEmppassword(employeeEntity.getEmppassword());
			}
		}
		if (employee.getIsEmpPhotoDeleted().equals(MainetConstants.Common_Constant.YES)
				&& (FileUploadUtility.getCurrent().getFileMap().get(0) == null)) {
			employee.setEmpphotopath(null);
		}
		employeeServiceMapper.mapEmployeeToEmployeeEntity(employee, employeeEntity);
		if (Utility.isEnvPrefixAvailable(UserSession.getCurrent().getOrganisation(), MainetConstants.ENV_PSCL)) {
			
			if (employee.getLockUnlock()!=null && employee.getLockUnlock().equalsIgnoreCase(MainetConstants.Transaction.Mode.UPDATE)) {
			    employeeEntity.setLockUnlock(null);
			}
		


			employeeEntity.setIsdeleted(employeeEntity.getIsdeleted());
		} else {
			employeeEntity.setIsdeleted(MainetConstants.IsDeleted.ZERO);

		}
		
		final Employee employeeEntitySaved = employeeJpaRepository.save(employeeEntity);

		EmployeeHistory emp_hist = new EmployeeHistory();
		emp_hist.setStatus(MainetConstants.InsertMode.UPDATE.getStatus());
		auditService.createHistory(employeeEntitySaved, emp_hist);

		final EmployeeBean empBean = employeeServiceMapper.mapEmployeeEntityToEmployee(employeeEntitySaved);
		final UserProfile uProfile = new UserProfile();
		uProfile.setFirstName(empBean.getEmpname());
		uProfile.setLastName(empBean.getEmpLName());
		uProfile.setPwd(empBean.getEmppassword());
		uProfile.setRole(empBean.getGmid().toString());
		uProfile.setuID(empBean.getEmploginname());
		uProfile.setUserType(MainetConstants.Transaction.Mode.DELETE);

		ldapManager.updateUser(uProfile);

		final SMSAndEmailDTO dto = new SMSAndEmailDTO();
		dto.setEmail(employee.getEmpemail());
		dto.setMobnumber(employee.getEmpmobno());
		dto.setUserName(employee.getEmploginname());
		dto.setAppName(employee.getEmpname() + " " + employee.getEmpLName());
		dto.setOrganizationName(employeeEntitySaved.getOrganisation().getONlsOrgname());
		dto.setPassword(employee.getNewPassword());
		ismsAndEmailService.sendEmailSMS(MainetConstants.DEPARTMENT.CFC_CODE, MainetConstants.AGENCY.URL.REGISTRATION,
				PrefixConstants.SMS_EMAIL_ALERT_TYPE.APPROVAL, dto, employeeEntity.getOrganisation(),
				MainetConstants.DEFAULT_LANGUAGE_ID);

		return empBean;

	}

	@Override
	public void deleteEmployee(final Long empid, final Long orgId) {
		employeeJpaRepository.deleteEmployee(empid, orgId);
	}

	@Override
	public List<Employee> getEmployeeByMobileNo(final String mobileNo, final Long orgId) {
		final List<Employee> employee = employeeJpaRepository.validateEmpMobileNo(mobileNo, orgId);
		return employee;
	}

	@Override
	public List<Employee> getEmployeeByPancardNo(final String pancardNo, final Long orgId) {
		final List<Employee> employee = employeeJpaRepository.validateEmpPancardNo(pancardNo, orgId);
		return employee;
	}

	@Override
	public List<Employee> getEmployeeByUid(final String uid, final Long orgId) {
		final List<Employee> employee = employeeJpaRepository.validateEmpUid(uid, orgId);
		return employee;
	}

	public void flushServerFolder() {
		try {
			final String path = FileUploadUtility.getCurrent().getExistingFolderPath();
			if (path != null) {
				final File cacheFolderStructure = new File(FileUploadUtility.getCurrent().getExistingFolderPath());
				FileUtils.deleteDirectory(cacheFolderStructure);
			}
		} catch (final Throwable e) {
			e.printStackTrace();
		}
	}

	@Override
	public List<Employee> getEmployeeByGroupId(Long gmId, Long orgId) {

		return employeeJpaRepository.getByGmId(gmId);
	}

	@Override
	@Transactional
	public String findEmployeeSessionId(Long empId, String string) {
		return employeeDAO.findEmployeeSessionId(empId, string);
	}

	@Override
	@Transactional
	public void updateEmployeeLoggedInFlag(String no, String sessionId) {
		employeeDAO.updateEmployeeLoggedInFlag(no, sessionId);
	}

	@Override
	public List<Employee> getEmployeeByListOfGroupId(List<Long> helpDeskUserGroupId, Long orgId) {
		return employeeJpaRepository.getEmployeeByListOfGroupId(helpDeskUserGroupId, orgId);
	}

	@Override
	public List<EmployeeBean> getEmployeeByGroup(List<Long> helpDeskUserGroupId) {
		return employeeJpaRepository.getEmployeeByGroup(helpDeskUserGroupId);
	}

	@Override
	public Long findAllCitizenCount(Long orgId) {
		return employeeJpaRepository.findAllCitizenCount(orgId);

	}

	@Override
	public List<EmployeeBean> getAllEmployeeBasedOnRoleCode(Long orgId) {
		final List<Employee> entities = employeeJpaRepository.getAllEmployeeBasedOnRoleCode(orgId);
		final List<EmployeeBean> beans = new ArrayList<>();
		for (final Employee employeeEntity : entities) {
			beans.add(employeeServiceMapper.mapEmployeeEntityToEmployee(employeeEntity));
		}
		return beans;
	}

	@Override
	public List<EmployeeBean> getAllDeptEmployee(Long orgId) {
		final List<Employee> entities = employeeJpaRepository.getAllDeptEmployee(orgId);
		final List<EmployeeBean> beans = new ArrayList<>();
		for (final Employee employeeEntity : entities) {
			beans.add(employeeServiceMapper.mapEmployeeEntityToEmployee(employeeEntity));
		}
		return beans;
	}

	/**
	 *
	 * @param empMobileNo
	 * @return {@value true} if mobile no is not already registered
	 */
	@Override
	public boolean isUniqueMobileNumber(final String empMobileNo, final Long empType, final Organisation organisation) {
		boolean isUnique = false;

		final List<Employee> employeeList = getEmployeeByEmpMobileNoAndType(empMobileNo, empType, organisation,
				MainetConstants.IsDeleted.ZERO, false);
		final LookUp lookUp = getCitizenLooUp(organisation);
		if ((employeeList == null) || (employeeList.size() == 0)) {
			return true;
		} else {
			for (final Employee employee : employeeList) {
				if (employee.getEmplType() != null) {
					if (employee.getEmplType() == lookUp.getLookUpId()) {
						return false;
					} else {
						isUnique = true;
					}
				} else {
					isUnique = true;
				}
			}

			return isUnique;
		}

	}

	/**
	 *
	 * @param empEMail
	 * @return {@value true} if email id is not already registered
	 */
	@Override
	public boolean isUniqueEmailAddress(final String empEMail, final Long empType, final Organisation organisation) {
		boolean isUnique = false;

		final List<Employee> employeeList = getEmployeeByEmpEMailAndType(empEMail, empType, organisation,
				MainetConstants.IsDeleted.ZERO, false);

		final LookUp lookUp = getCitizenLooUp(organisation);
		if (lookUp != null) {
			if ((employeeList == null) || (employeeList.size() == 0)) {
				return true;
			} else {
				for (final Employee employee : employeeList) {
					if (employee.getEmplType() != null) {
						if (employee.getEmplType() == lookUp.getLookUpId()) {
							return false;
						} else {
							isUnique = true;
						}
					} else {
						isUnique = true;
					}
				}
				return isUnique;
			}
		}
		return isUnique;

	}

	/**
	 *
	 * @return {@code LookUp} of CItizen
	 */
	public LookUp getCitizenLooUp(final Organisation organisation) {
		final List<LookUp> lookUpList = CommonMasterUtility.getLookUps(MainetConstants.NEC.PARENT, organisation);

		for (final LookUp lookUp : lookUpList) {
			if (lookUp.getLookUpCode().equalsIgnoreCase(MainetConstants.NEC.CITIZEN)) {
				return lookUp;
			}
		}
		return null;
	}

	// User Story #108649
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
	public List<EmployeeBean> getAllEmployeeDetails() {
		final List<Employee> entities = employeeJpaRepository.getAllEmployeeDetails();
		final List<EmployeeBean> beans = new ArrayList<>();
		for (final Employee employeeEntity : entities) {
			beans.add(employeeServiceMapper.mapEmployeeEntityToEmployee(employeeEntity));
		}
		return beans;
	}

	@Override
	public long getHitCounterUser(Long orgId) {
		long count = employeeJpaRepository.getTotalUser(orgId);
		return count;

	}

	/*
	 * @Override public void updatePassword(String Password, Long empid) {
	 * LOGGER.info("Update Password start :"+Password);
	 * employeeJpaRepository.updateEmployee(Password, empid);
	 * LOGGER.info("Update Password start :"+Password);
	 * 
	 * }
	 * 
	 * @Override public List<EmployeeBean> getAllAdmin(Long orgId) { final
	 * List<Employee> entities = employeeJpaRepository.getAllAdmin(orgId); final
	 * List<EmployeeBean> beans = new ArrayList<>(); for (final Employee
	 * employeeEntity : entities) {
	 * beans.add(employeeServiceMapper.mapEmployeeEntityToEmployee(employeeEntity));
	 * } return beans; }
	 */

}
