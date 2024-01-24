package com.abm.mainet.sfac.ui.controller;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.abm.mainet.common.ui.controller.AbstractFormController;
import com.abm.mainet.common.utility.ApplicationSession;
import com.abm.mainet.common.utility.UserSession;
import com.abm.mainet.sfac.ui.model.DashboardModel;

@Controller
@RequestMapping("Dashboard.html")
public class DashboardController extends AbstractFormController<DashboardModel>{
	
	
	
	@RequestMapping(method = { RequestMethod.POST,RequestMethod.GET})
	public void index(HttpServletRequest req, HttpServletResponse res) {
		// TODO Auto-generated method stub
		try {
			String url = ApplicationSession.getInstance().getMessage("sfac.dashboard.url");
			res.sendRedirect(url+UserSession.getCurrent().getEmployee().getMasId());
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
