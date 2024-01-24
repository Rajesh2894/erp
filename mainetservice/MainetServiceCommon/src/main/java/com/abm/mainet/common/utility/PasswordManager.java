package com.abm.mainet.common.utility;

import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.abm.mainet.common.constant.MainetConstants;
import com.abm.mainet.common.constant.MainetConstants.PasswordValidType;
import com.abm.mainet.common.domain.Employee;
import com.abm.mainet.common.domain.Organisation;
import javaxt.utils.Base64;


public class PasswordManager {
	
	private static final Logger LOG = LoggerFactory.getLogger(PasswordManager.class);

	/**
	 * Method to check LOCK_UNLOCK Flag In Employee Entity
	 * @param loggedEmployee
	 * @return true if LOCK_UNLOCK Flag is L
	 */
	public static boolean isAccountLocked(Employee loggedEmployee) {
		boolean status = false;
		if (null != loggedEmployee.getLockUnlock() && loggedEmployee.getLockUnlock().equals("L")) {
			status = true;
		}
		return status;
	}

	/**
	 * Method to Check Whether PassWord is Expired  or not by compiring current Date With 
	 * @param loggedEmployee
	 * @return
	 */
	public static boolean isPasswordExpired(Employee loggedEmployee) {
		boolean status = false;
		
		try {
			List<LookUp> lookup = CommonMasterUtility.getLookUps(MainetConstants.LookUpPrefix.PASSWORD_VALID_DAYS,
					UserSession.getCurrent().getOrganisation());
			if (null != lookup && (MainetConstants.IsLookUp.STATUS.YES).equals(lookup.get(0).getOtherField())) {
				if (null != loggedEmployee.getEmpexpiredt() && new Date().after(loggedEmployee.getEmpexpiredt())) {
					// TODO aparna - once expired date script is been executed
					// will
					// change status flag true;
					status = Boolean.TRUE; // true;
				}
			}
		} catch (Exception e) {
			LOG.error("PVD prefix not found");
		}
		return status;
	}
	/**
	 * Encrypt given password.
	 * @param encryptedPassword
	 * @return
	 */
	public static String decodePassword(String encryptedPassword){
		 return new String(Base64.decode(encryptedPassword));
	}
	
	/**
	 * Calculate Password Expired date
	 * password.expired.config.common = Y then all organization have same expired date which is define against "password.expired.config.days" property.
	 * password.expired.config.common = N than password calculated using "PVD" Prefix.
	 * Where Other Field vale is no of days required to Expired password
	 * @param loginType
	 * @return
	 */
	public static Date calculatePasswordExpiredDate(String loginType) {
		try{
		/*String commonPasswordConfig = ApplicationSession.getInstance().getMessage("password.expired.config.common");*/
		int expiredDays = 0;
		/*Organisation organisation = null;*/
		
		List<LookUp> lookup =CommonMasterUtility.getLookUps(MainetConstants.LookUpPrefix.PASSWORD_VALID_DAYS, 
				UserSession.getCurrent().getOrganisation());
		
		if (null != lookup && (MainetConstants.IsLookUp.STATUS.YES).equals(lookup.get(0).getOtherField())) {
			for (LookUp lookup1 : lookup) {
				if (MainetConstants.CommonConstants.NOD.equals(lookup1.getLookUpCode())) {
					if (null != lookup1.getOtherField() && !lookup1.getOtherField().isEmpty()
							&& !MainetConstants.CommonConstants.ZERO.equals(lookup1.getOtherField())) {
						String passwordExpiredDays = lookup1.getOtherField();
						expiredDays = StringUtils.isNumeric(passwordExpiredDays) ? Integer.valueOf(passwordExpiredDays)
								: 0;
					} else {
						String passwordExpiredDays = MainetConstants.CommonConstants.FORTY_FIVE;
						expiredDays = StringUtils.isNumeric(passwordExpiredDays) ? Integer.valueOf(passwordExpiredDays)
								: 0;
					}
					break;
				}
			}
		
		/*if (null != commonPasswordConfig && commonPasswordConfig.equals(MainetConstants.IsLookUp.STATUS.YES)) {
			String passwordExpiredDays = ApplicationSession.getInstance().getMessage("password.expired.config.days");
			expiredDays = StringUtils.isNumeric(passwordExpiredDays) ? Integer.valueOf(passwordExpiredDays) : 0;
		} else {
			if(null == loginType || loginType.isEmpty()) {
				return null;
			}
			
			if (loginType.equalsIgnoreCase(PasswordValidType.EMPLOYEE.getPrifixCode())) {
				organisation = ApplicationSession.getInstance().getSuperUserOrganization();
			} else {
				organisation = UserSession.getCurrent().getOrganisation();
			}
			LookUp prefixValue = CommonMasterUtility.getValueFromPrefixLookUp(loginType,
					MainetConstants.LookUpPrefix.PASSWORD_VALID_DAYS, organisation);
			if (null != prefixValue) {
				String days = prefixValue.getOtherField();
				expiredDays = StringUtils.isNumeric(days) ? Integer.valueOf(days) : 0;
			} else {
				LOG.warn("PVD prifix Configuration Error");
			}
		}*/
		if(expiredDays > 0) {
			Calendar cal = Calendar.getInstance();
			cal.add(Calendar.DATE, expiredDays);
			return cal.getTime();
		}else {
			return null;
		}
		}
		}catch(Exception e){
			LOG.error("PVD prifix Configuration Error");
		}
		return null;
	}

}
