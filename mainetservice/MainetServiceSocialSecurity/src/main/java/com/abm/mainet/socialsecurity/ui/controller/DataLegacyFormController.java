/**
 * 
 */
package com.abm.mainet.socialsecurity.ui.controller;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import javax.servlet.http.HttpServletRequest;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.json.MappingJackson2JsonView;
import com.abm.mainet.common.constant.MainetConstants;
import com.abm.mainet.common.domain.Organisation;
import com.abm.mainet.common.dto.JsonViewObject;
import com.abm.mainet.common.service.IOrganisationService;
import com.abm.mainet.common.service.ServiceMasterService;
import com.abm.mainet.common.ui.controller.AbstractFormController;
import com.abm.mainet.common.utility.ApplicationContextProvider;
import com.abm.mainet.common.utility.ApplicationSession;
import com.abm.mainet.common.utility.CommonMasterUtility;
import com.abm.mainet.common.utility.LookUp;
import com.abm.mainet.common.utility.UserSession;
import com.abm.mainet.common.utility.Utility;
import com.abm.mainet.socialsecurity.service.IPensionSchemeMasterService;
import com.abm.mainet.socialsecurity.service.ISchemeApplicationFormService;
import com.abm.mainet.socialsecurity.ui.dto.ApplicationFormDto;
import com.abm.mainet.socialsecurity.ui.dto.CriteriaDto;
import com.abm.mainet.socialsecurity.ui.model.DataLegacyModel;
import com.abm.mainet.socialsecurity.ui.model.SchemeApplicationFormModel;
import com.abm.mainet.socialsecurity.ui.validator.ApplicationFormValidator;

/**
 * @author priti.singh
 *
 */
@Controller
@RequestMapping("DataLegacyForm.html")
public class DataLegacyFormController extends AbstractFormController<DataLegacyModel>{

	@Autowired
	private ISchemeApplicationFormService schemeApplicationFormService;
	
	 @Autowired
	   private IPensionSchemeMasterService pensionSchemeMasterService;
	 
		@Autowired
		private IOrganisationService iOrganisationService;
    
    private static final Logger LOGGER = Logger.getLogger(DataLegacyFormController.class);
    
    @RequestMapping(method = { RequestMethod.GET, RequestMethod.POST })
    public ModelAndView index(final Model model, final HttpServletRequest httpServletRequest) {
        sessionCleanup(httpServletRequest);
        DataLegacyModel dataLegacyModel = this.getModel();
        Organisation org = UserSession.getCurrent().getOrganisation();
        Long orgId = UserSession.getCurrent().getOrganisation().getOrgid();
        int langId = UserSession.getCurrent().getLanguageId();
       dataLegacyModel.getAndSetPrefix(orgId, langId, org);
        this.getModel().setCommonHelpDocs("DataLegacyForm.html");
        return index();

    }
    
    
    
    @Override
    @RequestMapping(params = "saveform", method = RequestMethod.POST)
    public ModelAndView saveform(final HttpServletRequest httpServletRequest) {
        bindModel(httpServletRequest);
        JsonViewObject respObj;
        Long orgId = UserSession.getCurrent().getOrganisation().getOrgid();
        Long empId = UserSession.getCurrent().getEmployee().getEmpId();
        Long langId = (long) UserSession.getCurrent().getLanguageId();
        String ipMacAddress = Utility.getClientIpAddress(httpServletRequest);
        String ulbName = UserSession.getCurrent().getOrganisation().getOrgShortNm();
        DataLegacyModel dataLegacyModel = this.getModel();
        
        ApplicationFormDto dto = dataLegacyModel.getApplicationformdto();
        setGridId(dataLegacyModel);
       List<Object[]> list=dataLegacyModel.getServiceList().parallelStream().filter(s->s[0].equals(dto.getSelectSchemeName())).collect(Collectors.toList());
       list.parallelStream().forEach(l->{
           dto.setServiceCode(l[2].toString());
       });
        dto.setOrgId(orgId);
        dto.setCreatedBy(empId);
        dto.setCreatedDate(new Date());
        dto.setLgIpMac(ipMacAddress);
        dto.setLangId(langId);
        dto.setUlbName(ulbName);
        
      //  dataLegacyModel.validateBean(dto, ApplicationFormValidator.class);
        
        if (dataLegacyModel.saveDataLegacyDetails()) {
        
        respObj = JsonViewObject.successResult(ApplicationSession.getInstance().getMessage(dataLegacyModel.getSuccessMessage()));
            
        } else {
            respObj = JsonViewObject
                    .successResult(ApplicationSession.getInstance().getMessage("social.sec.notsave.success"));

        }
        return new ModelAndView(new MappingJackson2JsonView(), MainetConstants.FORM_NAME, respObj);

    }
    //rajesh.das add for getting Grid id 
	public void setGridId(DataLegacyModel appModel) {
		CriteriaDto dto = new CriteriaDto();
		List<Long> factorsId = new ArrayList<Long>();
		List<Long> criteriaId = new ArrayList<Long>();
		Long ageCriteria = 0l;
		List<LookUp> lookups = CommonMasterUtility.getNextLevelData(MainetConstants.SocialSecurity.FTR, 1,
				UserSession.getCurrent().getOrganisation().getOrgid());
		Long orgId = UserSession.getCurrent().getOrganisation().getOrgid();
		// set the factors values
		Long categoryFactorId = appModel.getCategoryList().get(0).getLookUpParentId();
		Long bplFactorId = appModel.getBplList().get(0).getLookUpParentId();
		Long educationFactorId = appModel.getEducationList().get(0).getLookUpParentId();
		Long genderFactorId = appModel.getGenderList().get(0).getLookUpParentId();
		Long maritalFactorId = appModel.getMaritalstatusList().get(0).getLookUpParentId();
		Long disabilityFactorId = appModel.getTypeofdisabilityList().get(0).getLookUpParentId();
		Long ageFactorId = lookups.get(0).getLookUpId();
		// adding factors to a list
		factorsId.addAll(Arrays.asList(categoryFactorId, bplFactorId, educationFactorId, genderFactorId,
				maritalFactorId, disabilityFactorId, ageFactorId));
		dto.setFactrors(factorsId);

		// set the criteria values
		Long categoryId = appModel.getApplicationformdto().getCategoryId() != null
				? appModel.getApplicationformdto().getCategoryId()
				: 0l;
		Long bplId = appModel.getApplicationformdto().getBplid() != null ? appModel.getApplicationformdto().getBplid()
				: 0l;
		Long genderId = appModel.getApplicationformdto().getGenderId() != null
				? appModel.getApplicationformdto().getGenderId()
				: 0l;
		Long maritalId = appModel.getApplicationformdto().getMaritalStatusId() != null
				? appModel.getApplicationformdto().getMaritalStatusId()
				: 0l;
		Long disabilityId = appModel.getApplicationformdto().getTypeofDisId() != null
				? appModel.getApplicationformdto().getTypeofDisId()
				: 0l;
		Long educationId = appModel.getApplicationformdto().getEducationId() != null
				? appModel.getApplicationformdto().getEducationId()
				: 0l;
		// adding criteria to a list
		List<LookUp> lookupsCriteria = CommonMasterUtility.getNextLevelData(MainetConstants.SocialSecurity.FTR, 2,
				UserSession.getCurrent().getOrganisation().getOrgid());
		for (LookUp l : lookupsCriteria) {
			if (l.getLookUpParentId() == ageFactorId) {

				if (l.getLookUpCode().equalsIgnoreCase("MAE") && appModel.getApplicationformdto().getAgeason() >= 1
						&& appModel.getApplicationformdto().getAgeason() <= 17) {
					ageCriteria = l.getLookUpId();
					break;
				} else if (l.getLookUpCode().equalsIgnoreCase("ADT")
						&& appModel.getApplicationformdto().getAgeason() >= 18
						&& appModel.getApplicationformdto().getAgeason() <= 57) {
					ageCriteria = l.getLookUpId();
					break;
				} else if (l.getLookUpCode().equalsIgnoreCase("ODA")
						&& appModel.getApplicationformdto().getAgeason() > 57) {
					ageCriteria = l.getLookUpId();
					break;
				}

			}

		}

		criteriaId
				.addAll(Arrays.asList(categoryId, bplId, genderId, maritalId, disabilityId, educationId, ageCriteria));
		dto.setCriterias(criteriaId);
		dto.setOrgId(orgId);
		dto.setServiceId(appModel.getApplicationformdto().getSelectSchemeName());

		Long gridId = schemeApplicationFormService.getCriteriaGridId(dto);
		appModel.getApplicationformdto().setGridId(gridId);

	}

	@RequestMapping(params = "checkDate", method = RequestMethod.POST)
	public ModelAndView checkDate(final HttpServletRequest httpServletRequest) {
		ModelAndView modelAndView = null;
		this.getModel().bind(httpServletRequest);
		List<LookUp> sourceLookUp = CommonMasterUtility.getNextLevelData(MainetConstants.SocialSecurity.FTR, 1, UserSession.getCurrent().getOrganisation().getOrgid());
		Long id=null;
		for (LookUp l : sourceLookUp) {
			if (l.getLookUpCode().equalsIgnoreCase("ICE")) {
				id = l.getLookUpId();
			}
		}
		String shortCode = ApplicationContextProvider.getApplicationContext().getBean(ServiceMasterService.class)
				.fetchServiceShortCode(this.getModel().getApplicationformdto().getSelectSchemeName(), UserSession.getCurrent().getOrganisation().getOrgid());
		Organisation orgid = iOrganisationService.getSuperUserOrganisation();
		List<LookUp> subSchemelist = new ArrayList<>();
		for (LookUp l : sourceLookUp) {
			if (l.getLookUpCode().equalsIgnoreCase("SS")) {
				List<LookUp> list = (schemeApplicationFormService.FindSecondLevelPrefixByFirstLevelPxCode(
						UserSession.getCurrent().getOrganisation().getOrgid(), "FTR",
						sourceLookUp.stream().filter(k -> k.getLookUpCode().equalsIgnoreCase("SS"))
								.collect(Collectors.toList()).get(0).getLookUpId(),
						2L));
				for (int i = 0; i < list.size(); i++) {
					String ids = pensionSchemeMasterService.getPrefixOtherValue(list.get(i).getLookUpId(),orgid.getOrgid());
					if (ids != null && ids != "" && ids.equals(shortCode))
						subSchemelist.add(list.get(i));
				}
				this.getModel().setSubTypeList(subSchemelist);
			}
		} 
		return new ModelAndView("DataLegacyValidn", MainetConstants.FORM_NAME, this.getModel());
	} 

}
