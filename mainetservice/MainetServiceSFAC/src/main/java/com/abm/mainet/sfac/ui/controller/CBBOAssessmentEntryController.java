/**
 * 
 */
package com.abm.mainet.sfac.ui.controller;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
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
import com.abm.mainet.common.service.ServiceMasterService;
import com.abm.mainet.common.ui.controller.AbstractFormController;
import com.abm.mainet.common.utility.CommonMasterUtility;
import com.abm.mainet.common.utility.LookUp;
import com.abm.mainet.common.utility.UserSession;
import com.abm.mainet.sfac.dto.AssessmentKeyParameterDto;
import com.abm.mainet.sfac.dto.AssessmentMasterDto;
import com.abm.mainet.sfac.dto.AssessmentSubParamDetailDto;
import com.abm.mainet.sfac.dto.AssessmentSubParameterDto;
import com.abm.mainet.sfac.repository.FPOMasterRepository;
import com.abm.mainet.sfac.service.CBBOAssesementEntryService;
import com.abm.mainet.sfac.service.CBBOMasterService;
import com.abm.mainet.sfac.service.IAMasterService;
import com.abm.mainet.sfac.ui.model.CBBOAssessmentEntryModel;

/**
 * @author pooja.maske
 *
 */
@Controller
@RequestMapping(MainetConstants.Sfac.CBBO_ASSESEMENT_ENTRY_HTML)
public class CBBOAssessmentEntryController extends AbstractFormController<CBBOAssessmentEntryModel> {

	private static final Logger log = Logger.getLogger(CBBOAssessmentEntryController.class);

	@Autowired
	private CBBOMasterService cbboMasterService;

	@Autowired
	FPOMasterRepository fpoMasterRepository;

	@Autowired
	IOrganisationService iOrganisationService;

	@Autowired
	private IAMasterService iaMasterService;


	@Autowired
	private CBBOAssesementEntryService assessmentService;

	

	@RequestMapping(method = { RequestMethod.GET, RequestMethod.POST })
	public ModelAndView index(HttpServletRequest httpServletRequest) {
		sessionCleanup(httpServletRequest);
		this.getModel().setAssessmentDtoList(assessmentService.findAll());
		return new ModelAndView(MainetConstants.Sfac.CBBO_ASS_ENTRY_SUMMARY, MainetConstants.FORM_NAME, getModel());
	}

	@RequestMapping(params = "formForCreate", method = { RequestMethod.POST })
	public ModelAndView formForCreate(HttpServletRequest request, Model model) {
		sessionCleanup(request);
		bindModel(request);
		Long orgId = UserSession.getCurrent().getOrganisation().getOrgid();
		this.getModel().setSaveMode(MainetConstants.FlagA);
		this.getModel().setCbboMasterDtoList(
				cbboMasterService.findCbboById(UserSession.getCurrent().getEmployee().getMasId()));
		// this.getModel().setIaNameList(iaMasterService.findAllIA());
		this.getModel().setFaYears(iaMasterService.getfinancialYearList(UserSession.getCurrent().getOrganisation()));
		List<AssessmentKeyParameterDto> assKeyParameterDtoList = new ArrayList<>();
		this.getModel().setShowParamTables(MainetConstants.FlagY);
		List<LookUp> lookUplist = CommonMasterUtility.getNextLevelData("CAP", 1, orgId);
		List<LookUp> meanOfVerList = CommonMasterUtility.lookUpListByPrefix("MOV", orgId);
		lookUplist.forEach(look -> {
			List<LookUp> subList = CommonMasterUtility.getChildLookUpsFromParentId(look.getLookUpId());
			List<AssessmentSubParameterDto> assementSubParamDtoList = new ArrayList<>();
			for (LookUp lookUp : subList) {
				List<AssessmentSubParamDetailDto> assSubParamDetailDtoList = new ArrayList<>();
				AssessmentSubParameterDto detDto = new AssessmentSubParameterDto();
				detDto.setSubParameter(lookUp.getLookUpId());
				detDto.setSubParameterDesc(lookUp.getDescLangFirst());
				for (LookUp means : meanOfVerList) {
					if (means.getLookUpCode().equals(lookUp.getLookUpCode())) {
						detDto.setMeansOfVerification(means.getLookUpId());
						if (StringUtils.isEmpty(means.getDescLangFirst()))
							detDto.setMeansOfVerificationDesc("");
						else
							detDto.setMeansOfVerificationDesc(means.getDescLangFirst());
						break;
					}
				}
				assementSubParamDtoList.add(detDto);
				List<LookUp> subDetList = CommonMasterUtility.getChildLookUpsFromParentId(lookUp.getLookUpId());
				for (LookUp subDet : subDetList) {
					AssessmentSubParamDetailDto subDetDto = new AssessmentSubParamDetailDto();
					subDetDto.setCondition(subDet.getLookUpId());
					subDetDto.setConditionDesc(subDet.getDescLangFirst());
					String otherVal = getPrefixOtherValue(subDet.getLookUpId());
					if (StringUtils.isNotEmpty(otherVal))
						subDetDto.setSubWeightage(new BigDecimal(otherVal));
					assSubParamDetailDtoList.add(subDetDto);
				}
				detDto.setAssSubParamDetailDtoList(assSubParamDetailDtoList);
			}
			AssessmentKeyParameterDto dto = new AssessmentKeyParameterDto();
			dto.setAssSubParamDtoList(assementSubParamDtoList);
			dto.setKeyParameter(look.getLookUpId());
			dto.setKeyParameterDesc(look.getDescLangFirst());
			String otherVal = getPrefixOtherValue(look.getLookUpId());
			if (StringUtils.isNotEmpty(otherVal))
				dto.setWeightage(new BigDecimal(otherVal));
			assKeyParameterDtoList.add(dto);
			this.getModel().setAssementSubParamDtoList(assementSubParamDtoList);
		});
		List<AssessmentKeyParameterDto> sortedList = assKeyParameterDtoList.stream()
				.sorted(Comparator.comparing(AssessmentKeyParameterDto::getKeyParameterDesc))
				.collect(Collectors.toList());
		this.getModel().setAssementKeyParamDtoList(sortedList);
		this.getModel().getAssementMasterDto().setAssementKeyParamDtoList(sortedList);
		assKeyParameterDtoList.forEach(System.out::println);
		return new ModelAndView(MainetConstants.Sfac.CBBO_ASS_ENTRY, MainetConstants.FORM_NAME, getModel());
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
		AssessmentMasterDto dto = assessmentService.fetchAssessmentDetByAssId(Long.valueOf(assId));
		this.getModel().setAssementMasterDto(dto);
		List<AssessmentKeyParameterDto> sortedList = dto.getAssementKeyParamDtoList().stream()
				.sorted(Comparator.comparing(AssessmentKeyParameterDto::getKeyParameterDesc))
				.collect(Collectors.toList());
		this.getModel().setAssementKeyParamDtoList(sortedList);
		this.getModel().getAssementMasterDto().setAssementKeyParamDtoList(sortedList);
	
		this.getModel().setFaYears(iaMasterService.getfinancialYearList(UserSession.getCurrent().getOrganisation()));
		return new ModelAndView(MainetConstants.Sfac.CBBO_ASS_ENTRY, MainetConstants.FORM_NAME, getModel());
	}

	@RequestMapping(method = { RequestMethod.POST }, params = MainetConstants.Sfac.PRINT_ASSESSMENT_DETAILS)
	public ModelAndView printAssessMentDetails(Long assId,
			@RequestParam(value = MainetConstants.REQUIRED_PG_PARAM.MODE, required = true) String mode,
			final HttpServletRequest httpServletRequest) {
		getModel().bind(httpServletRequest);
		AssessmentMasterDto dto = assessmentService.fetchAssessmentDetByAssId(Long.valueOf(assId));
		this.getModel().setAssYear(dto.getAlcYearDesc());
		List<AssessmentKeyParameterDto> sortedList = dto.getAssementKeyParamDtoList().stream()
				.sorted(Comparator.comparing(AssessmentKeyParameterDto::getKeyParameterDesc))
				.collect(Collectors.toList());
		this.getModel().setAssementKeyParamDtoList(sortedList);
		this.getModel().getAssementMasterDto().setAssementKeyParamDtoList(sortedList);
		this.getModel().setAssementMasterDto(dto);
		return new ModelAndView("AssessmentDetailsPrint", MainetConstants.FORM_NAME, getModel());
	}

	@RequestMapping(method = { RequestMethod.POST }, params = "searchForm")
	public ModelAndView searchAssessmentDetails(Long cbboId, String assStatus, Date assDate,
			final HttpServletRequest httpServletRequest) {
		this.getModel().setAssessmentDtoList(assessmentService.findByIds(cbboId, assStatus, assDate));
		this.getModel().getAssementMasterDto().setCbboId(cbboId);
		this.getModel().getAssementMasterDto().setAssDate(assDate);
		this.getModel().getAssementMasterDto().setAssStatus(assStatus);
		return new ModelAndView("CBBOAssessmentSummaryValidn", MainetConstants.FORM_NAME, getModel());
	}

}
