package com.abm.mainet.authentication.admin.ui.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Base64;
import java.util.Date;
import java.util.List;
import java.util.regex.Pattern;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;
import org.springframework.validation.ObjectError;

import com.abm.mainet.authentication.ldap.IAuthenticationManager;
import com.abm.mainet.common.constant.MainetConstants;
import com.abm.mainet.common.constant.PrefixConstants;
import com.abm.mainet.common.domain.Employee;
import com.abm.mainet.common.domain.Organisation;
import com.abm.mainet.common.entitlement.service.IEntitlementService;
import com.abm.mainet.common.entitlement.service.MenuRoleEntitlement;
import com.abm.mainet.common.service.IEmployeeService;
import com.abm.mainet.common.service.IOrganisationService;
import com.abm.mainet.common.ui.model.AbstractFormModel;
import com.abm.mainet.common.utility.LookUp;
import com.abm.mainet.common.utility.UserSession;
import com.abm.mainet.common.utility.Utility;
import com.abm.mainet.smsemail.dto.SMSAndEmailDTO;
import com.abm.mainet.smsemail.service.ISMSAndEmailService;

@Component
@Scope(value = "session")
public class AdminUpdatePersonalDtlsModel extends AbstractFormModel implements Serializable {
	private static final long serialVersionUID = -5566262411904099203L;
	private String oldMobileNumber ;
	private String mobileNumber;
	private String empFName;
	private String empMName;
	private String empLName;

	private static Logger logger = Logger.getLogger(AdminUpdatePersonalDtlsModel.class);

	public String getEmpFName() {
		return empFName;
	}

	public void setEmpFName(String empFName) {
		this.empFName = empFName;
	}

	public String getEmpMName() {
		return empMName;
	}

	public void setEmpMName(String empMName) {
		this.empMName = empMName;
	}

	public String getEmpLName() {
		return empLName;
	}

	public void setEmpLName(String empLName) {
		this.empLName = empLName;
	}

	public String getMobileNumber() {
		return mobileNumber;
	}

	public void setMobileNumber(String mobileNumber) {
		this.mobileNumber = mobileNumber;
	}

	public String getOldMobileNumber() {
		return oldMobileNumber;
	}

	public void setOldMobileNumber(String oldMobileNumber) {
		this.oldMobileNumber = oldMobileNumber;
	}

}
