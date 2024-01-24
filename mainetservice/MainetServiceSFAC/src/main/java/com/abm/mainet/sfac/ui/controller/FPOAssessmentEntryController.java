/**
 * 
 */
package com.abm.mainet.sfac.ui.controller;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.abm.mainet.common.constant.MainetConstants;
import com.abm.mainet.common.domain.Organisation;
import com.abm.mainet.common.service.IOrganisationService;
import com.abm.mainet.common.ui.controller.AbstractFormController;
import com.abm.mainet.common.utility.CommonMasterUtility;
import com.abm.mainet.common.utility.LookUp;
import com.abm.mainet.common.utility.UserSession;
import com.abm.mainet.sfac.dto.FpoAssKeyParameterDto;
import com.abm.mainet.sfac.dto.FpoAssSubParamDetailDto;
import com.abm.mainet.sfac.dto.FpoAssSubParameterDto;
import com.abm.mainet.sfac.dto.FpoAssessmentMasterDto;
import com.abm.mainet.sfac.repository.FPOMasterRepository;
import com.abm.mainet.sfac.service.FPOAssessmentService;
import com.abm.mainet.sfac.service.FPOMasterService;
import com.abm.mainet.sfac.service.IAMasterService;
import com.abm.mainet.sfac.ui.model.FPOAssessmentEntryModel;

/**
 * @author pooja.maske
 *
 */
@Controller
@RequestMapping(MainetConstants.Sfac.FPO_ASSESSMENT_ENTRY)
public class FPOAssessmentEntryController extends AbstractFormController<FPOAssessmentEntryModel> {

	@Autowired
	private FPOMasterService fpoMasterService;

	@Autowired
	private IAMasterService iaMasterService;

	@Autowired
	private FPOMasterRepository fpoMasterRepository;

	@Autowired
	private IOrganisationService iOrganisationService;

	@Autowired
	private FPOAssessmentService assessmentService;

	@RequestMapping(method = { RequestMethod.GET, RequestMethod.POST })
	public ModelAndView index(HttpServletRequest request) throws Exception {
		sessionCleanup(request);
		this.getModel().setAssessmentDtoList(assessmentService.findAll());
		return new ModelAndView(MainetConstants.Sfac.FPO_ASSESSMENT_SUMMARY_FORM, MainetConstants.FORM_NAME,
				getModel());
	}

	@RequestMapping(params = "formForCreate", method = { RequestMethod.POST })
	public ModelAndView formForCreate(HttpServletRequest request, Model model) {
		sessionCleanup(request);
		bindModel(request);
		Long orgId = UserSession.getCurrent().getOrganisation().getOrgid();
		this.getModel().setSaveMode(MainetConstants.FlagA);
		this.getModel()
				.setFpoMastDtoList(fpoMasterService.findByIaId(UserSession.getCurrent().getEmployee().getMasId()));
		this.getModel().setFaYears(iaMasterService.getfinancialYearList(UserSession.getCurrent().getOrganisation()));
		List<FpoAssKeyParameterDto> assKeyParameterDtoList = new ArrayList<>();
		List<LookUp> lookUplist = CommonMasterUtility.getNextLevelData("FAP", 1, orgId);
		for (LookUp lookUp : lookUplist) {
			List<LookUp> subList = CommonMasterUtility.getChildLookUpsFromParentId(lookUp.getLookUpId());
			List<FpoAssSubParameterDto> assementSubParamDtoList = new ArrayList<>();
			for (LookUp look : subList) {
				List<FpoAssSubParamDetailDto> assSubParamDetailDtoList = new ArrayList<>();
				FpoAssSubParameterDto subParamDto = new FpoAssSubParameterDto();
				subParamDto.setSubParameter(look.getLookUpId());
				subParamDto.setSubParameterDesc(look.getDescLangFirst());
				assementSubParamDtoList.add(subParamDto);

				List<LookUp> subDetList = CommonMasterUtility.getChildLookUpsFromParentId(look.getLookUpId());
				for (LookUp subDet : subDetList) {
					FpoAssSubParamDetailDto subDetDto = new FpoAssSubParamDetailDto();
					subDetDto.setCondition(subDet.getLookUpId());
					subDetDto.setConditionDesc(subDet.getDescLangFirst());
					String otherVal = getPrefixOtherValue(subDet.getLookUpId());
					if (StringUtils.isNotEmpty(otherVal))
						subDetDto.setSubWeightage(new BigDecimal(otherVal));
					assSubParamDetailDtoList.add(subDetDto);
				}
				subParamDto.setFpoSubParamDetailDtoList(assSubParamDetailDtoList);
			}
			FpoAssKeyParameterDto dto = new FpoAssKeyParameterDto();
			dto.setFpoSubParamDtoList(assementSubParamDtoList);
			dto.setKeyParameter(lookUp.getLookUpId());
			dto.setKeyParameterDesc(lookUp.getDescLangFirst());
			String otherVal = getPrefixOtherValue(lookUp.getLookUpId());
			if (StringUtils.isNotEmpty(otherVal))
				dto.setWeightage(new BigDecimal(otherVal));
			assKeyParameterDtoList.add(dto);
		}
		List<FpoAssKeyParameterDto> sortedList = assKeyParameterDtoList.stream()
				.sorted(Comparator.comparing(FpoAssKeyParameterDto::getKeyParameterDesc)).collect(Collectors.toList());
		this.getModel().setKeyParamDtoList(sortedList);
		this.getModel().getAssementMasterDto().setAssKeyDtoList(sortedList);
		return new ModelAndView(MainetConstants.Sfac.FPO_ASSESSMENT_FORM, MainetConstants.FORM_NAME, getModel());
	}

	@RequestMapping(method = { RequestMethod.POST }, params = "searchForm")
	public ModelAndView searchAssessmentDetails(Long fpoId, String assStatus,
			final HttpServletRequest httpServletRequest) {
		this.getModel().setAssessmentDtoList(assessmentService.findByFpoIdAndAssStatus(fpoId, assStatus));
		this.getModel().getAssementMasterDto().setFpoId(fpoId);
		this.getModel().getAssementMasterDto().setAssStatus(assStatus);
		return new ModelAndView("fpoAssessmentSummaryValidn", MainetConstants.FORM_NAME, getModel());
	}

	public String getPrefixOtherValue(Long lookUpId) {
		Organisation org = iOrganisationService.getActiveOrgByUlbShortCode(MainetConstants.Sfac.NPMA);
		return fpoMasterRepository.getPrefixOtherValue(lookUpId, org.getOrgid());
	}

	@RequestMapping(method = { RequestMethod.POST }, params = MainetConstants.CommonConstants.EDIT)
	public ModelAndView editOrView(Long assId,
			@RequestParam(value = MainetConstants.REQUIRED_PG_PARAM.MODE, required = true) String mode,
			final HttpServletRequest httpServletRequest) {
		getModel().bind(httpServletRequest);
		this.getModel().setSaveMode(mode);
		FpoAssessmentMasterDto dto = assessmentService.fetchAssessmentDetByAssId(Long.valueOf(assId));
		this.getModel().setAssementMasterDto(dto);
		List<FpoAssKeyParameterDto> sortedList = dto.getAssKeyDtoList().stream()
				.sorted(Comparator.comparing(FpoAssKeyParameterDto::getKeyParameterDesc))
				.collect(Collectors.toList());
		this.getModel().setKeyParamDtoList(sortedList);
		this.getModel().getAssementMasterDto().setAssKeyDtoList(sortedList);
		this.getModel().setFaYears(iaMasterService.getfinancialYearList(UserSession.getCurrent().getOrganisation()));
		return new ModelAndView(MainetConstants.Sfac.FPO_ASSESSMENT_FORM, MainetConstants.FORM_NAME, getModel());
	}
	
	@RequestMapping(method = { RequestMethod.POST }, params = MainetConstants.Sfac.PRINT_ASSESSMENT_DETAILS)
	public ModelAndView printAssessMentDetails(Long assId,
			@RequestParam(value = MainetConstants.REQUIRED_PG_PARAM.MODE, required = true) String mode,
			final HttpServletRequest httpServletRequest) {
		getModel().bind(httpServletRequest);
		FpoAssessmentMasterDto dto = assessmentService.fetchAssessmentDetByAssId(Long.valueOf(assId));
		this.getModel().setAssYear(dto.getAlcYearDesc());
		List<FpoAssKeyParameterDto> sortedList = dto.getAssKeyDtoList().stream()
				.sorted(Comparator.comparing(FpoAssKeyParameterDto::getKeyParameterDesc))
				.collect(Collectors.toList());
		this.getModel().setKeyParamDtoList(sortedList);
		this.getModel().getAssementMasterDto().setAssKeyDtoList(sortedList);
		this.getModel().setAssementMasterDto(dto);
		return new ModelAndView("FpoAssessmentDetailsPrint", MainetConstants.FORM_NAME, getModel());
	}
}
