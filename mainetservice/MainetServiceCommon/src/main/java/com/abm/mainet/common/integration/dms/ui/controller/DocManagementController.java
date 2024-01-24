package com.abm.mainet.common.integration.dms.ui.controller;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.abm.mainet.common.constant.MainetConstants;
import com.abm.mainet.common.integration.dms.ui.model.DmsManagementModel;
import com.abm.mainet.common.ui.controller.AbstractEntryFormController;
import com.abm.mainet.common.utility.CommonMasterUtility;
import com.abm.mainet.common.utility.LookUp;
import com.abm.mainet.common.utility.UserSession;

@Controller
@RequestMapping(value = "/DocManagement.html")
public class DocManagementController extends AbstractEntryFormController<DmsManagementModel> {
	private static final Logger log = LoggerFactory.getLogger(DocManagementController.class);
	@RequestMapping(method = { RequestMethod.POST, RequestMethod.GET })
	public ModelAndView index(HttpServletRequest request, Model model) {
		sessionCleanup(request);
		List<LookUp> prefixListNew = new ArrayList<LookUp>();
		List<LookUp> prefixList = new ArrayList<LookUp>();
		String dept = UserSession.getCurrent().getEmployee().getTbDepartment().getDpDeptcode();
		try {
			prefixList = CommonMasterUtility.getLevelData(MainetConstants.Dms.MTD, MainetConstants.NUMBERS.ONE,
				UserSession.getCurrent().getOrganisation());
		}catch(Exception e) {
			log.error("Please configure MTD prefix");
		}
		prefixList.forEach(prefix -> {
			if (prefix.getLookUpCode().equals(dept)) {
				prefixListNew.add(prefix);
			}
		});
		getModel().setDepartmentList(prefixListNew);
		return new ModelAndView("DocManagement", MainetConstants.FORM_NAME, getModel());
	}
	
	@RequestMapping(params = "getMetadata", method = RequestMethod.POST)
	public ModelAndView getMetadata(@RequestParam("deptId") Long deptId, HttpServletRequest request, Model model) {
		getModel().bind(request);
		getModel().setDeptId(deptId);
		List<LookUp> prefixListNew = new ArrayList<LookUp>();
		List<LookUp> prefixListNewDoc = new ArrayList<LookUp>();
		List<LookUp> prefixList = new ArrayList<LookUp>();
		String dept = UserSession.getCurrent().getEmployee().getTbDepartment().getDpDeptcode();
		List<LookUp> metadataList = CommonMasterUtility.getChildLookUpsFromParentId(deptId);
		try {
			prefixList = CommonMasterUtility.getLevelData(MainetConstants.Dms.MTD, MainetConstants.NUMBERS.ONE,
				UserSession.getCurrent().getOrganisation());
		}catch(Exception e) {
			log.error("Please configure MTD prefix");
		}
		LookUp lookUp=CommonMasterUtility.getHierarchicalLookUp(deptId,UserSession.getCurrent().getOrganisation().getOrgid());
		if(lookUp!=null)
			getModel().setDeptCode(lookUp.getLookUpCode());
		String prefixList1 = CommonMasterUtility.getHierarchicalLookUp(deptId,UserSession.getCurrent().getOrganisation().getOrgid()).getLookUpCode();
		List<LookUp> atta = CommonMasterUtility.getListLookup(MainetConstants.Dms.DCT,
				UserSession.getCurrent().getOrganisation());
		atta.forEach(prefix -> {
			if (prefix.getOtherField().equals(prefixList1)) {
				prefixListNewDoc.add(prefix);
			}
		});
		prefixList.forEach(prefix -> {
			if (prefix.getLookUpCode().equals(dept)) {
				prefixListNew.add(prefix);
			}
		});
		getModel().setDepartmentList(prefixListNew);
		getModel().setMetadataList(metadataList);
		return new ModelAndView("DocManagementValidn", MainetConstants.FORM_NAME, this.getModel());
	}
}
