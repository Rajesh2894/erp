package com.abm.mainet.authentication.admin.ui.model;

import java.io.Serializable;
import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.abm.mainet.common.constant.MainetConstants;
import com.abm.mainet.common.constant.PrefixConstants;
import com.abm.mainet.common.domain.Employee;
import com.abm.mainet.common.service.IEmployeeService;
import com.abm.mainet.common.ui.model.AbstractFormModel;
import com.abm.mainet.common.utility.LookUp;
import com.abm.mainet.common.utility.Utility;
import com.abm.mainet.common.utility.UtilityService;
import com.abm.mainet.smsemail.dto.SMSAndEmailDTO;
import com.abm.mainet.smsemail.service.ISMSAndEmailService;

/**
 *
 * @author Vivek.Kumar
 *
 */

@Component
@Scope(value = "session")
public class AdminOTPVerificationModel extends AbstractFormModel implements Serializable {

	private static final long serialVersionUID = -1505022376994033832L;

	private String otpPassword;
	private String mobileNumber;
	private Long emplType;

	@Autowired
	private IEmployeeService iEmployeeService;

	@Autowired
	private ISMSAndEmailService ismsAndEmailService;

	private static Logger logger = Logger.getLogger(AdminOTPVerificationModel.class);

	/**
	 *
	 * @return {@code Employee} object by mobile no.
	 */
	public Employee getAdminByMobile() {
		Employee validateEmployee = null;
		final List<Employee> employeeList = iEmployeeService.getEmployeeByEmpMobileNoAndType(getMobileNumber(),
				getEmplType(), getUserSession().getOrganisation(), MainetConstants.IsDeleted.ZERO, false);

		if ((employeeList != null) && (employeeList.size() == 1)) {
			if (employeeList.get(0).getEmplType() == null) {
				validateEmployee = employeeList.get(0);
			}

		} else {
			for (final Employee employee : employeeList) {

				if (employee.getEmplType() == null) {
					validateEmployee = employee;
				}

			}
		}

		return validateEmployee;
	}

	/**
	 *
	 * @param emploginname
	 * @param emplType
	 * @return {@value true} if login id and otp matched
	 */
	public boolean validateMobileByOTP(final String emploginname, final Long emplType) {
		final Employee validAdmin = iEmployeeService.getAuthenticatedEmployee(emploginname, getOtpPassword(), emplType,
				getUserSession().getOrganisation(), MainetConstants.IsDeleted.ZERO);
		if (validAdmin != null) {
			return true;
		}
		return false;
	}

	/**
	 *
	 * @param adminEmployee
	 * @return {@value true} after sending otp as a mail and sms.
	 */
	public boolean resendOTP(final Employee adminEmployee) {

		boolean result = false;
		final List<Employee> employeeList = iEmployeeService.getEmployeeByEmpMobileNo(adminEmployee.getEmpmobno(), null,
				MainetConstants.IsDeleted.ZERO);
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

		/*
		 * boolean result = false; final String newAutoGeneratePwd =
		 * iEmployeeService.generateNewPassword(MainetConstants.OTP_PRASSWORD_LENGTH);
		 * final Employee updateEmployee =
		 * iEmployeeService.updateEmployeePassword(adminEmployee, newAutoGeneratePwd,
		 * getUserSession().getEmployee()); if (updateEmployee != null) { result = true;
		 * sendSMSandEMail(updateEmployee, newAutoGeneratePwd); } return result;
		 */}

	/**
	 *
	 * @param registeredEmployee
	 * @param newAutoGeneratePwd
	 */
	public void sendSMSandEMail(final Employee registeredEmployee, final String newAutoGeneratePwd) {
		final SMSAndEmailDTO dto = new SMSAndEmailDTO();
		dto.setEmail(registeredEmployee.getEmpemail());
		dto.setMobnumber(registeredEmployee.getEmpmobno());
		dto.setEmpName(registeredEmployee.getFullName());
		dto.setOneTimePassword(newAutoGeneratePwd);
		dto.setOrganizationName(registeredEmployee.getOrganisation().getONlsOrgname());
		int langId = Utility.getDefaultLanguageId(registeredEmployee.getOrganisation());
		// Added Changes As per told by Rajesh Sir For Sms and Email
		dto.setUserId(registeredEmployee.getEmpId());
		ismsAndEmailService.sendEmailSMS(MainetConstants.DEPT_SHORT_NAME.CFC_CENTER,
				MainetConstants.SMS_EMAIL_URL.REGISTRATION, PrefixConstants.SMS_EMAIL_ALERT_TYPE.OTP_MSG, dto,
				registeredEmployee.getOrganisation(), langId);

	}

	public LookUp getCitizenLooUp() {
		final List<LookUp> lookUpList = super.getLookUpList(PrefixConstants.NEC.PARENT);

		for (final LookUp lookUp : lookUpList) {
			if (lookUp.getLookUpCode().equalsIgnoreCase(PrefixConstants.NEC.CITIZEN)) {
				return lookUp;
			}
		}
		return null;
	}

	/**
	 * @return the otpPassword
	 */
	public String getOtpPassword() {
		return otpPassword;
	}

	/**
	 * @param otpPassword
	 *            the otpPassword to set
	 */
	public void setOtpPassword(final String otpPassword) {
		this.otpPassword = otpPassword;
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
	 * @return the emplType
	 */
	public Long getEmplType() {
		return emplType;
	}

	/**
	 * @param emplType
	 *            the emplType to set
	 */
	public void setEmplType(final Long emplType) {
		this.emplType = emplType;
	}

}
