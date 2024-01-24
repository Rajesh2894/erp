/**
 *
 */
package com.abm.mainet.common.ui.model;

import java.io.Serializable;
import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.abm.mainet.common.domain.EmployeeSession;
import com.abm.mainet.common.service.IEmployeeService;
import com.abm.mainet.common.util.UserSession;

/**
 * @author pabitra.raulo
 *
 */
@Component
@Scope(value = "session")
public class LogOutModel extends AbstractModel implements Serializable {
	private static final long serialVersionUID = -6153048961158163058L;

	@Autowired
	private IEmployeeService iEmployeeService;

	private String logoutMesg;

	public String getLogoutMesg() {
		return logoutMesg;
	}

	public void setLogoutMesg(final String logoutMesg) {
		this.logoutMesg = logoutMesg;
	}
	//User Story #108649
	public boolean saveEmployeeSessionInLogOut(Long empId) {
		EmployeeSession empSession = null;
		if (empId != null) {
			empSession = iEmployeeService.getEmployeeSessionDataByEmpId(empId);
		}
		
		if (empSession != null) {
			empSession.setLogOutDate(new Date());
			boolean flag = iEmployeeService.saveEmployeeSession(empSession);
			if (flag)
				return true;
		}
		return false;
	}
}
