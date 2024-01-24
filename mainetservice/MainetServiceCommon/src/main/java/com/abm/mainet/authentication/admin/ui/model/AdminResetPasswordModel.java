package com.abm.mainet.authentication.admin.ui.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.abm.mainet.common.constant.MainetConstants;
import com.abm.mainet.common.constant.PrefixConstants;
import com.abm.mainet.common.domain.Employee;
import com.abm.mainet.common.domain.Organisation;
import com.abm.mainet.common.service.IEmployeeService;
import com.abm.mainet.common.ui.model.AbstractFormModel;
import com.abm.mainet.common.utility.Utility;
import com.abm.mainet.common.utility.UtilityService;
import com.abm.mainet.smsemail.dto.SMSAndEmailDTO;
import com.abm.mainet.smsemail.service.ISMSAndEmailService;

@Component
@Scope(value = "request")
public class AdminResetPasswordModel extends AbstractFormModel implements Serializable {
	private static final long serialVersionUID = -301987374919892976L;
	private String mobileNumber;
	private String oneTimePassword;
	private String confirmedPassword;
	private String newPassword;
	private String oldPassword;
	private boolean accountLock;
	private boolean isTemplateAvailable = true;
	private String result;
	private String captchaSessionLoginValue;

	@Autowired
	private IEmployeeService iEmployeeService;

	private static final Logger logger = Logger.getLogger(AdminResetPasswordModel.class);

	@Autowired
	private ISMSAndEmailService ismsAndEmailService;

	public boolean ifRegisteredEmployeeThenSendOTP(final String mobileNo) {
		boolean result = false;
//		final Organisation organisation = getUserSession().getOrganisation();
		final List<Employee> employeeList = iEmployeeService.getEmployeeByEmpMobileNo(mobileNo, null,
				MainetConstants.IsDeleted.ZERO);
		if (employeeList != null && !employeeList.isEmpty()) {
			final String otpNo = UtilityService.generateRandomNumericCode(MainetConstants.OTP_PRASSWORD_LENGTH);
			int count = 0;
			for (final Employee employee : employeeList) {
				if (employee.getEmplType() == null) {
					if (employee.getLockUnlock() == null || employee.getLockUnlock().equals("")) {
						// this.oneTimePasswordStep2(employee);
						if (employee.getAutMob().equals(MainetConstants.Common_Constant.YES)) {
							employee.setAutMob(MainetConstants.Common_Constant.NO);
						}

						// save OTP mobile no OTP to OTP column instead of password column
						// employee.setEmppassword(Utility.encryptPassword(employee.getEmploginname(),
						// otpNo));
						employee.setMobNoOtp(Utility.encryptPassword(employee.getEmploginname(), otpNo));
						iEmployeeService.updateEmployeeDetails(employee, getUserSession().getEmployee());
						if (count == 0) {
							if (!sendSMSandEMail(employee, otpNo)) {
								setTemplateAvailable(false);
								return false;
							}
							count++;
						}
						result = true;
					} else {
						setAccountLock(true);
					}
				}
			}

		}

		return result;
	}

	public boolean oneTimePasswordStep2(final List<Employee> employeeList) {
		boolean result = false;
		int count = 0;
		final String otp = UtilityService.generateRandomNumericCode(MainetConstants.OTP_PRASSWORD_LENGTH);
		for (Employee employee : employeeList) {
			if (employee.getAutMob().equals("Y")) {
				employee.setAutMob(MainetConstants.IsDeleted.NOT_DELETE);
			}
			// employee.setEmppassword(Utility.encryptPassword(employee.getEmploginname(),
			// otp));
			employee.setMobNoOtp(Utility.encryptPassword(employee.getEmploginname(), otp));
			iEmployeeService.updateEmployeeDetails(employee, getUserSession().getEmployee());
			if (count == 0) {
				sendSMSandEMail(employee, otp);
				count++;
			}
			result = true;
		}

		return result;
	}

	/**
	 * Send OTP via SMS and Email.
	 * 
	 * @param employee
	 * @param otp
	 */
	private boolean sendSMSandEMail(final Employee employee, final String otp) {
		final SMSAndEmailDTO dto = new SMSAndEmailDTO();
		dto.setEmail(employee.getEmpemail());
		dto.setMobnumber(employee.getEmpmobno());
		dto.setServDur(employee.getEmpname());
		dto.setEmpName(employee.getEmpname());
		dto.setOrgId(employee.getOrganisation().getOrgid());
		dto.setOneTimePassword(otp);
		// Added Changes As per told by Rajesh Sir For Sms and Email
		dto.setUserId(employee.getEmpId());
		if (employee.getOrganisation() != null)
			dto.setOrganizationName(employee.getOrganisation().getONlsOrgname());
		int langId = Utility.getDefaultLanguageId(employee.getOrganisation());
		dto.setLangId(langId);
		return ismsAndEmailService.sendEmailSMS(MainetConstants.DEPT_SHORT_NAME.CFC_CENTER,
				MainetConstants.SMS_EMAIL_URL.REGISTRATION, PrefixConstants.SMS_EMAIL_ALERT_TYPE.OTP_MSG, dto,
				employee.getOrganisation(), langId);
	}

	/**
	 * OTP verification.
	 */
	public String verficationOfOTP() {
		String result = getAppSession().getMessage("eip.citizen.forgotPassword.otpVerificationFailed");

		if ((getOneTimePassword() != null) && (getOneTimePassword() != "")) {
			if ((getMobileNumber() != null) && !getMobileNumber().equalsIgnoreCase("")) {
				final Organisation organisation = getUserSession().getOrganisation();
				final List<Employee> employeeList = iEmployeeService.getEmployeeByEmpMobileNo(getMobileNumber(),
						null, MainetConstants.IsDeleted.ZERO);

				if ((employeeList != null) && (!employeeList.isEmpty())) {
					for (final Employee employee : employeeList) {
						if (employee.getEmplType() == null) {
							final Employee authEmployee = iEmployeeService.getAuthenticatedEmployee(
									employee.getEmploginname(), getOneTimePassword(), employee.getEmplType(),
									null, MainetConstants.IsDeleted.ZERO);
							if (authEmployee != null) {
								result = "success";
							} else {
								result = getAppSession().getMessage("eip.admin.forgotPassword.invalidOTP");
								this.addValidationError(
										getAppSession().getMessage("eip.admin.forgotPassword.invalidOTP"));
							}
						}
					}
				}
			}
		} else {
			result = getAppSession().getMessage("citizen.login.otp.must.error");
			this.addValidationError(getAppSession().getMessage("citizen.login.otp.must.error"));

		}
		return result;
	}

	public List<Employee> getEmployeeByMobile() {
		final List<Employee> employeeList = iEmployeeService.getEmployeeByEmpMobileNo(getMobileNumber(), null,
				MainetConstants.IsDeleted.ZERO);;
		List<Employee> activeEmplList = new ArrayList<>();

		if ((employeeList != null && !employeeList.isEmpty())) {
			for (final Employee employee : employeeList) {
				if (employee.getEmplType() == null) {
					activeEmplList.add(employee);
					// return employee;
				}
			}
		}

		return activeEmplList;
	}

	public boolean setPassword(final List<Employee> employeeList) {
		boolean result = false;

		/*
		 * Employee updateEmployee = iEmployeeService.setEmployeePassword(employee,
		 * getNewPassword(), getUserSession().getEmployee());
		 */
		/*
		 * String empPassword = new
		 * String(Base64.getDecoder().decode(getNewPassword()));
		 */
		int cnt = 0;
		for (Employee employee : employeeList) {
			Employee updateEmployee = iEmployeeService.setEmployeePassword(employee, getNewPassword(),
					getUserSession().getEmployee());
			if (updateEmployee != null) {
				if (cnt == 0) {
					sendSuccessSMSandEMail(updateEmployee);
					cnt++;
				}
				result = true;
			}
		}

		return result;
	}

	/**
	 * Send Success message via SMS and Email.
	 * 
	 * @param employee
	 * @param otp
	 */
	private void sendSuccessSMSandEMail(final Employee employee) {
		final SMSAndEmailDTO dto = new SMSAndEmailDTO();
		dto.setEmail(employee.getEmpemail());
		dto.setMobnumber(employee.getEmpmobno());
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

	/**
	 * @return the oldPassword
	 */
	public String getOldPassword() {
		return oldPassword;
	}

	/**
	 * @param oldPassword
	 *            the oldPassword to set
	 */
	public void setOldPassword(final String oldPassword) {
		this.oldPassword = oldPassword;
	}

	public boolean isAccountLock() {
		return accountLock;
	}

	public boolean isTemplateAvailable() {
		return isTemplateAvailable;
	}

	public void setTemplateAvailable(boolean isTemplateAvailable) {
		this.isTemplateAvailable = isTemplateAvailable;
	}

	public void setAccountLock(boolean accountLock) {
		this.accountLock = accountLock;
	}

	public String getResult() {
		return result;
	}

	public void setResult(String result) {
		this.result = result;
	}

	public String getCaptchaSessionLoginValue() {
		return captchaSessionLoginValue;
	}

	public void setCaptchaSessionLoginValue(String captchaSessionLoginValue) {
		this.captchaSessionLoginValue = captchaSessionLoginValue;
	}

}
