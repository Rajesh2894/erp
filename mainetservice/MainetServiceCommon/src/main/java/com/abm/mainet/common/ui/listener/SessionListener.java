package com.abm.mainet.common.ui.listener;

import java.util.Date;

import javax.servlet.annotation.WebListener;
import javax.servlet.http.HttpSessionEvent;
import javax.servlet.http.HttpSessionListener;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.abm.mainet.common.constant.MainetConstants;
import com.abm.mainet.common.domain.EmployeeSession;
import com.abm.mainet.common.service.IEmployeeService;
import com.abm.mainet.common.utility.ApplicationContextProvider;
import com.abm.mainet.common.utility.UserSession;

@WebListener
public class SessionListener implements HttpSessionListener {

	private static final Logger LOG = LoggerFactory.getLogger(SessionListener.class);

	@Override
	public void sessionCreated(final HttpSessionEvent event) {
	}

	@Override
	public void sessionDestroyed(final HttpSessionEvent event) {
		String sessionId = event.getSession().getId();
		if (sessionId != null) {
			IEmployeeService iEmployeeService = ApplicationContextProvider.getApplicationContext()
					.getBean(IEmployeeService.class);
			iEmployeeService.updateEmployeeLoggedInFlag(MainetConstants.Common_Constant.NO, sessionId);

			// User Story #108649

			try {
				Long empId = iEmployeeService.getEmpIdByEMpSession(sessionId);
				if (empId != null) {
					EmployeeSession empSession = iEmployeeService
							.getEmployeeSessionDataByEmpId(empId);
					if (empSession != null) {
						empSession.setLogOutDate(new Date());
						boolean flag = iEmployeeService.saveEmployeeSession(empSession);
						if (flag) {
							LOG.info("EMployee session save succsessfully at the time of logout ");
						}
					} else {
						LOG.info("EMployee session not  save  at the time of logout ");

					}
				}
			} catch (Exception e) {
				LOG.error("Exception occured at the time of saving  Employee session data in  sessionDestroyed");

			}

		}
	}
}