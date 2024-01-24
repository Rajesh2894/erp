package com.abm.mainet.buildingplan.ui.controller;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import com.abm.mainet.buildingplan.service.ISiteAffecService;
import com.abm.mainet.buildingplan.ui.model.SiteAffectedModel;
import com.abm.mainet.cfc.scrutiny.service.ScrutinyService;
import com.abm.mainet.common.constant.MainetConstants;
import com.abm.mainet.common.dto.JsonViewObject;
import com.abm.mainet.common.integration.dms.service.IFileUploadService;
import com.abm.mainet.common.ui.controller.AbstractFormController;
import com.abm.mainet.common.utility.UserSession;

@Controller
@RequestMapping("SiteAffected.html")
public class SiteAffectedController extends AbstractFormController<SiteAffectedModel> {

	@Autowired
	ISiteAffecService siteAffecService;
	
	@Autowired
	private IFileUploadService fileUpload;
	
	@Resource
	private ScrutinyService scrutinyServiceImpl;

	@RequestMapping(method = RequestMethod.POST, params = "gasPipeLine")
	public ModelAndView getGasPipeLine(final HttpServletRequest httpServletRequest) {
		sessionCleanup(httpServletRequest);
		this.getModel().setFlag("G");
		final Long appId = Long.valueOf(httpServletRequest.getParameter("applId"));
		final Long lableId = Long.valueOf(httpServletRequest.getParameter("labelId"));
		final Long level = Long.valueOf(httpServletRequest.getParameter("level"));
		final Long gmId = Long.valueOf(httpServletRequest.getParameter("gmId"));
		final Long taskId = Long.valueOf(httpServletRequest.getParameter("taskId"));
		this.getModel().setApplicationId(appId);
		this.getModel().setLevel(level);
		this.getModel().setSlLabelId(lableId);
		this.getModel().setGmId(gmId);
		this.getModel().setTaskId(taskId);
		this.getModel()
				.setListSiteAffDto(siteAffecService.getSiteDetailsByApplicationId(appId, this.getModel().getFlag()));
		return new ModelAndView("SiteAffected", MainetConstants.FORM_NAME, this.getModel());
	}

	@RequestMapping(method = RequestMethod.POST, params = "waterPipeLine")
	public ModelAndView getWaterPipeLine(final HttpServletRequest httpServletRequest) {
		sessionCleanup(httpServletRequest);
		this.getModel().setFlag("W");
		final Long appId = Long.valueOf(httpServletRequest.getParameter("applId"));
		final Long lableId = Long.valueOf(httpServletRequest.getParameter("labelId"));
		final Long level = Long.valueOf(httpServletRequest.getParameter("level"));
		final Long gmId = Long.valueOf(httpServletRequest.getParameter("gmId"));
		final Long taskId = Long.valueOf(httpServletRequest.getParameter("taskId"));
		this.getModel().setApplicationId(appId);
		this.getModel().setLevel(level);
		this.getModel().setSlLabelId(lableId);
		this.getModel().setGmId(gmId);
		this.getModel().setTaskId(taskId);
		
		this.getModel()
				.setListSiteAffDto(siteAffecService.getSiteDetailsByApplicationId(appId, this.getModel().getFlag()));
		return new ModelAndView("SiteAffected", MainetConstants.FORM_NAME, this.getModel());
	}

	@RequestMapping(method = RequestMethod.POST, params = "htLine")
	public ModelAndView getHtLine(final HttpServletRequest httpServletRequest) {
		sessionCleanup(httpServletRequest);
		this.getModel().setFlag("H");
		final Long appId = Long.valueOf(httpServletRequest.getParameter("applId"));
		final Long lableId = Long.valueOf(httpServletRequest.getParameter("labelId"));
		final Long level = Long.valueOf(httpServletRequest.getParameter("level"));
		final Long gmId = Long.valueOf(httpServletRequest.getParameter("gmId"));
		final Long taskId = Long.valueOf(httpServletRequest.getParameter("taskId"));
		this.getModel().setApplicationId(appId);
		this.getModel().setLevel(level);
		this.getModel().setSlLabelId(lableId);
		this.getModel().setGmId(gmId);
		this.getModel().setTaskId(taskId);
		this.getModel()
				.setListSiteAffDto(siteAffecService.getSiteDetailsByApplicationId(appId, this.getModel().getFlag()));
		return new ModelAndView("SiteAffected", MainetConstants.FORM_NAME, this.getModel());
	}

	@RequestMapping(method = RequestMethod.POST, params = "saveSiteAffData")
	public ModelAndView saveSiteAffData(final HttpServletRequest httpServletRequest) {
		bindModel(httpServletRequest);
		final SiteAffectedModel model = getModel();
		if (model.saveForm()) {
			return jsonResult(JsonViewObject.successResult(model.getSuccessMessage()));
		} else {
			return defaultMyResult();
		}

	}

	@RequestMapping(method = RequestMethod.POST, params = "uploadDocument")
	public ModelAndView uploadDocument(final HttpServletRequest httpServletRequest) {
		sessionCleanup(httpServletRequest);
		fileUpload.sessionCleanUpForFileUpload();
		this.getModel().setFlag("D");
		final Long level = Long.valueOf(httpServletRequest.getParameter("level"));
		final Long gmId = Long.valueOf(httpServletRequest.getParameter("gmId"));
		final Long appId = Long.valueOf(httpServletRequest.getParameter("applId"));
		final Long taskId = Long.valueOf(httpServletRequest.getParameter("taskId"));
		this.getModel().setTaskId(taskId);
		this.getModel().setLevel(level);
		this.getModel().setApplicationId(appId);
		return new ModelAndView("uploadDocument", MainetConstants.FORM_NAME, this.getModel());
	}
	
	@RequestMapping(method = RequestMethod.POST, params = "uploadJEDocument")
	public ModelAndView uploadJEDocument(final HttpServletRequest httpServletRequest) {
		sessionCleanup(httpServletRequest);
		fileUpload.sessionCleanUpForFileUpload();
		this.getModel().setFlag("J");
		final Long level = Long.valueOf(httpServletRequest.getParameter("level"));
		final Long gmId = Long.valueOf(httpServletRequest.getParameter("gmId"));
		final Long appId = Long.valueOf(httpServletRequest.getParameter("applId"));
		final Long taskId = Long.valueOf(httpServletRequest.getParameter("taskId"));
		this.getModel().setTaskId(taskId);
		this.getModel().setApplicationId(appId);
		this.getModel().setLevel(level);
		return new ModelAndView("uploadDocument", MainetConstants.FORM_NAME, this.getModel());
	}

	@RequestMapping(method = RequestMethod.POST, params = "saveDocument")
	public ModelAndView saveDocument(final HttpServletRequest httpServletRequest) {
		bindModel(httpServletRequest);
		final SiteAffectedModel model = getModel();
		if (model.saveDocument()) {
			return jsonResult(JsonViewObject.successResult(model.getSuccessMessage()));
		} else {
			return defaultMyResult();
		}

	}
	
	 @RequestMapping(params = "getFieldDocument", method = RequestMethod.POST)
	   	public ModelAndView getFieldDocument(final HttpServletRequest httpServletRequest) {	
	       	getModel().bind(httpServletRequest);
	       	fileUpload.sessionCleanUpForFileUpload();
	       	final Long serviceId = Long.valueOf(httpServletRequest.getParameter("serviceId"));
	        final Long lableId = Long.valueOf(httpServletRequest.getParameter("labelId"));
	        final Long level = Long.valueOf(httpServletRequest.getParameter("level"));
	        final Long applicationId = Long.valueOf(httpServletRequest.getParameter("applId"));
	        String groupValue=	scrutinyServiceImpl.getGroupValue(level,lableId,serviceId,UserSession.getCurrent().getOrganisation().getOrgid());
	        if(groupValue!=null && !groupValue.isEmpty())
	       	this.getModel().setDownloadDocs(scrutinyServiceImpl.getDocumentUploadedListByGroupId(groupValue,applicationId));
	   		return new ModelAndView("ShowDocument", MainetConstants.FORM_NAME, this.getModel());
	   	}

}
