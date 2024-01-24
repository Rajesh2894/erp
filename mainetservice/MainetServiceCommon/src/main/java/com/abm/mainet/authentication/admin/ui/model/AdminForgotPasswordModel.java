package com.abm.mainet.authentication.admin.ui.model;

import java.io.Serializable;
import java.util.Base64;
import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.abm.mainet.common.constant.MainetConstants;
import com.abm.mainet.common.constant.MainetConstants.PasswordValidType;
import com.abm.mainet.common.constant.PrefixConstants;
import com.abm.mainet.common.domain.Employee;
import com.abm.mainet.common.domain.Organisation;
import com.abm.mainet.common.service.IEmployeeService;
import com.abm.mainet.common.ui.model.AbstractFormModel;
import com.abm.mainet.common.utility.LookUp;
import com.abm.mainet.common.utility.PasswordManager;
import com.abm.mainet.common.utility.UserSession;
import com.abm.mainet.common.utility.Utility;
import com.abm.mainet.common.utility.UtilityService;
import com.abm.mainet.smsemail.dto.SMSAndEmailDTO;
import com.abm.mainet.smsemail.service.ISMSAndEmailService;

@Component
@Scope(value = "request")
public class AdminForgotPasswordModel extends AbstractFormModel implements Serializable {
	private static final long serialVersionUID = 9147514992549410053L;

	private String mobileNumber;
	private String oneTimePassword;
	private String confirmedPassword;
	private String newPassword;
	private boolean accountLock;
	@Autowired
	private IEmployeeService iEmployeeService;

	private static Logger logger = Logger.getLogger(AdminForgotPasswordModel.class);
	@Autowired
	private ISMSAndEmailService ismsAndEmailService;

	public boolean ifRegisteredAdminThenSendOTP(final String adminMobileNo) {
		final Organisation organisation = getUserSession().getOrganisation();
		final Long emplType = null;
		final List<Employee> employeeList = iEmployeeService.getEmployeeByEmpMobileNoAndType(adminMobileNo, emplType,
				organisation, MainetConstants.IsDeleted.ZERO, false);

		if ((employeeList == null) || (employeeList.size() == 0)) {
			return false;
		} else {

			for (final Employee employee : employeeList) {

				if (employee.getEmplType() == null) {
					if (employee.getLockUnlock() == null) {
						oneTimePasswordStep2(employee);
						return true;
					} else {
						setAccountLock(true);
					}
				}
			}
		}
		return false;
	}

	public boolean oneTimePasswordStep2(final Employee employee) {
		if (employee.getAutMob().equals("Y")) {
			employee.setAutMob(MainetConstants.IsDeleted.NOT_DELETE);
		}
		final String otp = UtilityService.generateRandomNumericCode(MainetConstants.OTP_PRASSWORD_LENGTH);
		employee.setEmppassword(Utility.encryptPassword(employee.getEmploginname(), otp));
		iEmployeeService.updateEmployeeDetails(employee, getUserSession().getEmployee());
		sendSMSandEMail(employee, otp);

		return true;
	}

	/**
	 * Send OTP via SMS and Email.
	 * 
	 * @param employee
	 * @param otp
	 */
	private void sendSMSandEMail(final Employee employee, final String otp) {

		final SMSAndEmailDTO dto = new SMSAndEmailDTO();
		dto.setEmail(employee.getEmpemail());
		dto.setMobnumber(employee.getEmpmobno());
		dto.setOneTimePassword(otp);
		dto.setEmpName(employee.getEmpname());
		//Added Changes As per told by Rajesh Sir For Sms and Email
		dto.setUserId(employee.getEmpId());
		if (employee.getOrganisation() != null)
			dto.setOrganizationName(employee.getOrganisation().getONlsOrgname());
		int langId = Utility.getDefaultLanguageId(employee.getOrganisation());
		ismsAndEmailService.sendEmailSMS(MainetConstants.DEPT_SHORT_NAME.CFC_CENTER,
				MainetConstants.SMS_EMAIL_URL.REGISTRATION, PrefixConstants.SMS_EMAIL_ALERT_TYPE.OTP_MSG, dto,
				employee.getOrganisation(), langId);

	}

	/**
	 * To check valid admin.
	 * 
	 * @param admin
	 * @return true/false
	 */
	private boolean isAdminEmployee(final Employee admin) {
		if ((admin.getEmplType() != null) && (admin.getEmplType() != 0l) && checkNECType(admin.getEmplType())) {
			return false;
		} else {
			return true;
		}
	}

	/**
	 * From admin login only NEC type employee can able to login. Method will check
	 * login request is given by an NEC type employee.
	 * 
	 * @param emplTypeId
	 * @return true/false
	 */
	private boolean checkNECType(final long emplTypeId) {
		final List<LookUp> lookUpList = super.getLookUpList(PrefixConstants.NEC.PARENT);

		for (final LookUp lookUp : lookUpList) {
			if (lookUp.getLookUpId() == emplTypeId) {
				return true;
			}
		}
		return false;
	}

	public LookUp getEmployeeLookUp() {
		final List<LookUp> lookUpList = super.getLookUpList(PrefixConstants.NEC.PARENT);

		for (final LookUp lookUp : lookUpList) {
			if (lookUp.getLookUpCode().equalsIgnoreCase(PrefixConstants.NEC.EMPLOYEE)) {
				return lookUp;
			}
		}
		return null;
	}

	/**
	 * OTP verification.
	 */
	public String verficationOfOTP() {
		String result = getAppSession().getMessage("eip.admin.forgotPassword.otpVerificationFailed");

		if ((getOneTimePassword() != null) && (getOneTimePassword() != "")) {
			if ((getMobileNumber() != null) && !getMobileNumber().equalsIgnoreCase("")) {
				final Organisation organisation = getUserSession().getOrganisation();
				final List<Employee> employeeList = iEmployeeService.getEmployeeByEmpMobileNo(getMobileNumber(),
						organisation, MainetConstants.IsDeleted.ZERO);

				if ((employeeList != null) && (employeeList.size() != 0)) {
					for (final Employee employee : employeeList) {
						if (employee.getEmplType() == null) {
							final Employee authEmployee = iEmployeeService.getAuthenticatedEmployee(
									employee.getEmploginname(), getOneTimePassword(), employee.getEmplType(),
									null, MainetConstants.IsDeleted.ZERO);

							if (authEmployee != null) {
								result = "success";
							} else {
								result = getAppSession().getMessage("eip.admin.forgotPassword.invalidOTP");
							}
						}
					}
				}
			}
		} else {
			result = "OTP is mandatory";
		}
		return result;
	}

	public Employee getAdminByMobile() {
		final List<Employee> employeeList = iEmployeeService.getEmployeeByEmpMobileNo(getMobileNumber(),
				getUserSession().getOrganisation(), MainetConstants.IsDeleted.ZERO);

		if ((employeeList != null) && (employeeList.size() > 0)) {
			for (final Employee admin : employeeList) {
				if (isAdminEmployee(admin)) {
					return admin;
				}
			}
		}

		return null;
	}

	public boolean setPassword(final Employee admin) {
		boolean result = false;

		/*
		 * final Employee updateEmployee = iEmployeeService.setEmployeePassword(admin,
		 * getNewPassword(), getUserSession().getEmployee());
		 */
		String empPassword = new String(Base64.getDecoder().decode((getNewPassword())));
		admin.setEmpexpiredt(PasswordManager.calculatePasswordExpiredDate(PasswordValidType.EMPLOYEE.getPrifixCode()));
		Employee updateEmployee = iEmployeeService.setEmployeePassword(admin, empPassword,
				getUserSession().getEmployee());
		if (updateEmployee != null) {
			result = true;
			UserSession.getCurrent().setEmployee(updateEmployee);
		}
		return result;
	}

	/**
	 * @return the mobileNumber
	 */
	public String getMobileNumber() {
		return mobileNumber;
	}

	/**
	 * @param mobileNumber
	 *            the mobileNumber to set
	 */
	public void setMobileNumber(final String mobileNumber) {
		this.mobileNumber = mobileNumber;
	}

	/**
	 * @return the oneTimePassword
	 */
	public String getOneTimePassword() {
		return oneTimePassword;
	}

	/**
	 * @param oneTimePassword
	 *            the oneTimePassword to set
	 */
	public void setOneTimePassword(final String oneTimePassword) {
		this.oneTimePassword = oneTimePassword;
	}

	/**
	 * @return the confirmedPassword
	 */
	public String getConfirmedPassword() {
		return confirmedPassword;
	}

	/**
	 * @param confirmedPassword
	 *            the confirmedPassword to set
	 */
	public void setConfirmedPassword(final String confirmedPassword) {
		this.confirmedPassword = confirmedPassword;
	}

	/**
	 * @return the newPassword
	 */
	public String getNewPassword() {
		return newPassword;
	}

	/**
	 * @param newPassword
	 *            the newPassword to set
	 */
	public void setNewPassword(final String newPassword) {
		this.newPassword = newPassword;
	}

	public boolean isAccountLock() {
		return accountLock;
	}

	public void setAccountLock(boolean accountLock) {
		this.accountLock = accountLock;
	}

}
