package com.abm.mainet.cms.ui.listener;

import java.util.Date;

import javax.servlet.annotation.WebListener;
import javax.servlet.http.HttpSessionEvent;
import javax.servlet.http.HttpSessionListener;

import org.apache.log4j.Logger;

import com.abm.mainet.common.constant.MainetConstants;
import com.abm.mainet.common.domain.EmployeeSession;
import com.abm.mainet.common.service.IEmployeeService;
import com.abm.mainet.common.util.UserSession;
import com.abm.mainet.common.utility.ApplicationContextProvider;


@WebListener
public class SessionListener implements HttpSessionListener {

	private static final Logger LOG = Logger.getLogger(SessionListener.class);

	private static int activeSessions;

	public static int getActiveSessions() {
		return activeSessions;
	}

	public static void setActiveSessions(final int activeSessions) {
		SessionListener.activeSessions = activeSessions;
	}

	@Override
	public void sessionCreated(final HttpSessionEvent event) {
		activeSessions++;
		LOG.info("session created - total active sessions: " + activeSessions);
	}

	@Override
	public void sessionDestroyed(final HttpSessionEvent event) {
		String sessionId = event.getSession().getId();
		if (sessionId != null) {
			IEmployeeService iEmployeeService = ApplicationContextProvider.getApplicationContext()
					.getBean(IEmployeeService.class);
			iEmployeeService.updateEmployeeLoggedInFlag(MainetConstants.IsLookUp.STATUS.NO, sessionId);
			//User Story #108649
			try {
				if (UserSession.getCurrent().getEmployee() != null
						&& UserSession.getCurrent().getEmployee().getEmpId() != null) {
					EmployeeSession empSession = iEmployeeService
							.getEmployeeSessionDataByEmpId(UserSession.getCurrent().getEmployee().getEmpId());
					if (empSession != null) {
						empSession.setLogOutDate(new Date());
						boolean flag = iEmployeeService.saveEmployeeSession(empSession);
						if (flag) {
							LOG.info("EMployee session save succsessfully at the time of logout " + activeSessions);
						}
					} else {
						LOG.info("EMployee session not  save  at the time of logout " + activeSessions);
					}
				}
			} catch (Exception e) {
				LOG.error("Exception occured at the time of saving  Employee session data in  sessionDestroyed");
			}
		}
		activeSessions--;
		LOG.info("session destroyed - total active sessions: " + activeSessions);
	}
}