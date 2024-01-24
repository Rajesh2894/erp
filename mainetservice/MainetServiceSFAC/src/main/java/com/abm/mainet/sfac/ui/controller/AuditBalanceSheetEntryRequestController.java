package com.abm.mainet.sfac.ui.controller;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.abm.mainet.common.constant.MainetConstants;
import com.abm.mainet.common.integration.dms.service.IFileUploadService;
import com.abm.mainet.common.ui.controller.AbstractFormController;
import com.abm.mainet.common.utility.CommonMasterUtility;
import com.abm.mainet.common.utility.LookUp;
import com.abm.mainet.common.utility.UserSession;
import com.abm.mainet.sfac.dto.AuditBalanceSheetKeyParameterDto;
import com.abm.mainet.sfac.dto.AuditBalanceSheetMasterDto;
import com.abm.mainet.sfac.dto.AuditBalanceSheetSubParameterDetailDto;
import com.abm.mainet.sfac.dto.AuditBalanceSheetSubParameterDto;
import com.abm.mainet.sfac.service.AuditBalanceSheetEntryService;
import com.abm.mainet.sfac.ui.model.ABSEntryFormModel;

@Controller
@RequestMapping(MainetConstants.Sfac.ABS_ENTRY_REQ_FORM_HTML)
public class AuditBalanceSheetEntryRequestController extends AbstractFormController<ABSEntryFormModel>{


	@Autowired AuditBalanceSheetEntryService auditBalanceSheetEntryService;

	@Autowired
	private IFileUploadService fileUpload;

	@RequestMapping(method = { RequestMethod.GET, RequestMethod.POST })
	public ModelAndView index(HttpServletRequest httpServletRequest) {
		sessionCleanup(httpServletRequest);
		populateModel();
		return new ModelAndView(MainetConstants.Sfac.ABS_ENTRY_REQ_SUMMARY,MainetConstants.FORM_NAME,getModel());
	}

	private void populateModel() {

		AuditBalanceSheetMasterDto auditBalanceSheetMasterDto = auditBalanceSheetEntryService.findFPODetails(UserSession.getCurrent().getEmployee().getMasId());
		this.getModel().setDto(auditBalanceSheetMasterDto);

	}

	@RequestMapping(params = "formForCreate", method = { RequestMethod.POST })
	public ModelAndView formForCreate(HttpServletRequest request, Model model) {
		sessionCleanup(request);
		fileUpload.sessionCleanUpForFileUpload();
		populateModel();
		List<AuditBalanceSheetKeyParameterDto> auditBalanceSheetKeyParameterDtos = new ArrayList<>();
		Long orgId = UserSession.getCurrent().getOrganisation().getOrgid();
		List<LookUp> lookUplist = CommonMasterUtility.getNextLevelData("FRE", 1, orgId);
		lookUplist.forEach(look -> {
			List<LookUp> subList = CommonMasterUtility.getChildLookUpsFromParentId(look.getLookUpId());
			List<AuditBalanceSheetSubParameterDto> auditBalanceSheetSubParameterDtos = new ArrayList<>();
			for (LookUp lookUp : subList) {
				List<AuditBalanceSheetSubParameterDetailDto> auditBalanceSheetSubParameterDetailDtos = new ArrayList<>();
				AuditBalanceSheetSubParameterDto detDto = new AuditBalanceSheetSubParameterDto();
				detDto.setSubParameter(lookUp.getLookUpId());
				detDto.setSubParameterDesc(lookUp.getDescLangFirst());

				auditBalanceSheetSubParameterDtos.add(detDto);
				List<LookUp> subDetList = CommonMasterUtility.getChildLookUpsFromParentId(lookUp.getLookUpId());
				for (LookUp subDet : subDetList) {
					AuditBalanceSheetSubParameterDetailDto subDetDto = new AuditBalanceSheetSubParameterDetailDto();
					subDetDto.setCondition(subDet.getLookUpId());
					subDetDto.setConditionDesc(subDet.getDescLangFirst());

					auditBalanceSheetSubParameterDetailDtos.add(subDetDto);
				}
				detDto.setAuditBalanceSheetSubParameterDetailDtos(auditBalanceSheetSubParameterDetailDtos);
			}
			AuditBalanceSheetKeyParameterDto dto = new AuditBalanceSheetKeyParameterDto();
			dto.setAuditBalanceSheetSubParameterDtos(auditBalanceSheetSubParameterDtos);
			dto.setKeyParameter(look.getLookUpId());
			dto.setKeyParameterDesc(look.getDescLangFirst());

			auditBalanceSheetKeyParameterDtos.add(dto);

		});
		/*List<AuditBalanceSheetKeyParameterDto> sortedList = auditBalanceSheetKeyParameterDtos.stream()
				.sorted(Comparator.comparing(AssessmentKeyParameterDto::getKeyParameterDesc))
				.collect(Collectors.toList());
		this.getModel().setAssementKeyParamDtoList(sortedList);*/
		this.getModel().getDto().setAuditBalanceSheetKeyParameterDtos(auditBalanceSheetKeyParameterDtos);
		this.getModel().setViewMode(MainetConstants.FlagA);


		return new ModelAndView(MainetConstants.Sfac.ABS_ENTRY_REQ_FORM, MainetConstants.FORM_NAME, getModel());
	}

	@RequestMapping(method = { RequestMethod.POST }, params = "searchForm")
	public ModelAndView searchForm(Long fpoId, Long status ,final HttpServletRequest httpServletRequest) {


		/*List<AuditBalanceSheetMasterDto> auditBalanceSheetMasterDtos = circularNotificationService.getCircularNotification(circularTitle, circularNo);
		this.getModel().setCircularNotificationMasterDtos(circularNotificationMasterDtos);*/

		return new ModelAndView(MainetConstants.Sfac.ABS_ENTRY_REQ_SUMMARY_VALIDN, MainetConstants.FORM_NAME, getModel());
	}


	@RequestMapping(method = { RequestMethod.POST }, params = MainetConstants.CommonConstants.EDIT)
	public ModelAndView editOrView(Long absId,
			@RequestParam(value = MainetConstants.REQUIRED_PG_PARAM.MODE, required = true) String mode,
			final HttpServletRequest httpServletRequest) {
		getModel().bind(httpServletRequest);
		this.getModel().setViewMode(mode);
		AuditBalanceSheetMasterDto dto = auditBalanceSheetEntryService.fetchABSDetails(Long.valueOf(absId));
		this.getModel().setDto(dto);

		return new ModelAndView(MainetConstants.Sfac.CBBO_ASS_ENTRY, MainetConstants.FORM_NAME, getModel());
	}

}
