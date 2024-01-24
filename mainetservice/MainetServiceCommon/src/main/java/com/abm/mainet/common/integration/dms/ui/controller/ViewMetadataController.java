package com.abm.mainet.common.integration.dms.ui.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.abm.mainet.common.constant.MainetConstants;
import com.abm.mainet.common.integration.dms.dto.DmsDocsMetadataDto;
import com.abm.mainet.common.integration.dms.service.IViewMetadataService;
import com.abm.mainet.common.integration.dms.ui.model.ViewMetadataModel;
import com.abm.mainet.common.ui.controller.AbstractFormController;
import com.abm.mainet.common.utility.CommonMasterUtility;
import com.abm.mainet.common.utility.LookUp;
import com.abm.mainet.common.utility.UserSession;

@Controller
@RequestMapping(value = "ViewMetadataDetails.html")
public class ViewMetadataController extends AbstractFormController<ViewMetadataModel> {

	@Autowired
	private IViewMetadataService viewMetadataService;

	@RequestMapping(method = { RequestMethod.POST, RequestMethod.GET })
	public ModelAndView index(HttpServletRequest request, Model model) {
		sessionCleanup(request);
		return new ModelAndView("ViewMetadataDetails", MainetConstants.FORM_NAME, getModel());
	}

	@RequestMapping(params = "searchDetails", method = RequestMethod.POST)
	public ModelAndView searchMetadataDetails(@RequestParam("level1") Long deptId,
			@RequestParam("level2") Long metadataId, @RequestParam("metadataValue") String metadataValue,
			@RequestParam("ward1") Long zone, @RequestParam("ward2") Long ward, @RequestParam("ward3") Long mohalla,
			HttpServletRequest request) {

		ModelAndView mv = null;
		getModel().bind(request);
		getModel().setErrorMsg(null);
		String callType = MainetConstants.Dms.DMS;
		List<DmsDocsMetadataDto> metadataList = viewMetadataService.getMetadataDetails(String.valueOf(deptId),
				String.valueOf(metadataId), metadataValue, UserSession.getCurrent().getEmployee().getGmid(),
				UserSession.getCurrent().getOrganisation().getOrgid(), zone, ward, mohalla, null, callType,
				this.getModel(), null,null,null);

		getModel().setDmsDocsMetadataDto(metadataList);
		getModel().getMetadatDto().setLevel1(deptId);
		getModel().getMetadatDto().setLevel2(metadataId);
		getModel().setMetadataValue(metadataValue);
		getModel().setWard1(zone);
		getModel().setWard2(ward);
		getModel().setWard3(mohalla);

		if (CollectionUtils.isEmpty(metadataList) || (getModel().getErrorMsg() != null && getModel().getErrorMsg().equals(MainetConstants.FlagY))) {
			mv = new ModelAndView("ViewMetadataDetailsValidn", MainetConstants.FORM_NAME, this.getModel());
			this.getModel().addValidationError(getApplicationSession().getMessage("dms.noRecordInService"));
			mv.addObject(BindingResult.MODEL_KEY_PREFIX + MainetConstants.FORM_NAME, getModel().getBindingResult());
			return mv;
		} else {
			return new ModelAndView("ViewMetadataDetailsValidn", MainetConstants.FORM_NAME, getModel());
		}

	}

	@RequestMapping(params = "getWardZone", method = RequestMethod.POST)
	public ModelAndView getWardZone(@RequestParam("deptId") Long deptId, HttpServletRequest request) {
		sessionCleanup(request);
		@SuppressWarnings("deprecation")
		LookUp lookUp = CommonMasterUtility.getHierarchicalLookUp(deptId);
		getModel().getMetadatDto().setLevel1(deptId);
		if (lookUp != null)
			getModel().setDeptCode(lookUp.getLookUpCode());
		return new ModelAndView("ViewMetadataDetailsValidn", MainetConstants.FORM_NAME, getModel());
	}

}
