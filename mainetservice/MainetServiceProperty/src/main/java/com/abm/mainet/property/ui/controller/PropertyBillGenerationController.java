package com.abm.mainet.property.ui.controller;

import java.util.ArrayList;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.abm.mainet.common.constant.MainetConstants;
import com.abm.mainet.common.constant.PrefixConstants;
import com.abm.mainet.common.domain.FinancialYear;
import com.abm.mainet.common.domain.LocationMasEntity;
import com.abm.mainet.common.domain.Organisation;
import com.abm.mainet.common.master.service.DepartmentService;
import com.abm.mainet.common.service.IFinancialYearService;
import com.abm.mainet.common.service.ILocationMasService;
import com.abm.mainet.common.ui.controller.AbstractFormController;
import com.abm.mainet.common.utility.ApplicationSession;
import com.abm.mainet.common.utility.CommonMasterUtility;
import com.abm.mainet.common.utility.LookUp;
import com.abm.mainet.common.utility.UserSession;
import com.abm.mainet.common.utility.Utility;
import com.abm.mainet.property.dto.NoticeGenSearchDto;
import com.abm.mainet.property.service.PrimaryPropertyService;
import com.abm.mainet.property.service.PropertyBillGenerationService;
import com.abm.mainet.property.service.SelfAssessmentService;
import com.abm.mainet.property.ui.model.PropertyBillGenerationModel;

@Controller
@RequestMapping({"/PropertyBillGeneration.html","/PropertyBillProvisionalDemandGen.html"})
public class PropertyBillGenerationController extends AbstractFormController<PropertyBillGenerationModel> {

	private static final Logger LOGGER = Logger.getLogger(PropertyBillGenerationController.class);

    @Resource
    private ILocationMasService iLocationMasService;

    @Autowired
    private DepartmentService departmentService;

    @Autowired
    private PropertyBillGenerationService propertyBillGenerationService;
    
    @Autowired
    private IFinancialYearService iFinancialYearService;
    
    @Autowired
    private PrimaryPropertyService primaryPropertyService;
    
    @Autowired
    private SelfAssessmentService selfAssessmentService;

    @RequestMapping(method = { RequestMethod.POST, RequestMethod.GET })
    public String index(HttpServletRequest request) {
    	String INDEX_PAGE = null;
        this.sessionCleanup(request);
        PropertyBillGenerationModel model = this.getModel();
    	LookUp skdclEnv  = null;
    	try {
    		skdclEnv = CommonMasterUtility.getValueFromPrefixLookUp("SKDCL", "ENV", UserSession.getCurrent().getOrganisation());
    	}catch (Exception e) {
		}
    	if (skdclEnv != null && StringUtils.isNotBlank(skdclEnv.getOtherField())
				&& StringUtils.equals(skdclEnv.getOtherField(), MainetConstants.FlagY)) {
    		 model.getSpecialNotGenSearchDto().setSpecNotSearchType("S");
    	}else {
    		 model.getSpecialNotGenSearchDto().setSpecNotSearchType("SM");
    	}
        model.bind(request);
        //Checking Is this for Aligarh/SUDA or SKDLC
        model.setSearchGridHide(MainetConstants.FlagN);
        if(Utility.isEnvPrefixAvailable(UserSession.getCurrent().getOrganisation(), "SGH")) {
        	model.setSearchGridHide(MainetConstants.FlagY);
        }
        List<LookUp> locList = getModel().getLocation();
        Organisation org = UserSession.getCurrent().getOrganisation();
        Long deptId = departmentService.getDepartmentIdByDeptCode(MainetConstants.Property.PROP_DEPT_SHORT_CODE);
        List<LocationMasEntity> location = iLocationMasService.findWZMappedLocationByOrgIdAndDeptId(org.getOrgid(),
                deptId);
        if (location != null && !location.isEmpty()) {
            location.forEach(loc -> {
                LookUp lookUp = new LookUp();
                lookUp.setLookUpId(loc.getLocId());
                lookUp.setDescLangFirst(loc.getLocNameEng());
                lookUp.setDescLangSecond(loc.getLocNameReg());
                locList.add(lookUp);
            });
        }
		try {
			List<LookUp> parentPropLookupList = CommonMasterUtility.getLevelData(PrefixConstants.GPI,
					MainetConstants.NUMBER_ONE, org);
			this.getModel().setParentPropLookupList(parentPropLookupList);
		} catch (Exception e) {
			LOGGER.error("Prefix GPI is not configured in prefix master ");
		}
		/*List<String> parentPropNameList = selfAssessmentService.fetchAssessmentByParentPropName(org.getOrgid());
		this.getModel().setParentPropNameList(parentPropNameList);*/		
        if (request.getRequestURI().contains("PropertyBillGeneration")) {
        	 model.setCommonHelpDocs("PropertyBillGeneration.html");
        	 INDEX_PAGE = "PropertyBillGeneration";
        }else {
        	 model.setCommonHelpDocs("PropertyBillProvisionalDemandGen.html");
        	 INDEX_PAGE = "PropertyBillProvisionalDemandGen";
        }
        return INDEX_PAGE;
    }

    @RequestMapping(method = RequestMethod.POST, params = "SearchPropNo")
    public ModelAndView search(HttpServletRequest request) {
        getModel().bind(request);
        PropertyBillGenerationModel model = this.getModel();
        model.setNotGenSearchDtoList(null);
        List<NoticeGenSearchDto> notGenShowList = null;
        model.getSpecialNotGenSearchDto().setOrgId(UserSession.getCurrent().getOrganisation().getOrgid());
        String searchType=model.getSpecialNotGenSearchDto().getSpecNotSearchType();
        Long orgId=UserSession.getCurrent().getOrganisation().getOrgid();
        NoticeGenSearchDto specialNotSearchDto = model.getSpecialNotGenSearchDto();
        if (searchType.equals("SM") || searchType.equals("GP")) {
            if (searchType.equals("SM") && (model.getSpecialNotGenSearchDto().getPropertyNo() == null
                    || model.getSpecialNotGenSearchDto().getPropertyNo().isEmpty())
                    && (model.getSpecialNotGenSearchDto().getOldPropertyNo() == null
                            || model.getSpecialNotGenSearchDto().getOldPropertyNo().isEmpty())) {
                model.addValidationError("Please enter valid property number or Old property number.");
			} else if (searchType.equals("GP") && StringUtils.isBlank(specialNotSearchDto.getParentPropNo())
					&& StringUtils.isBlank(specialNotSearchDto.getParentPropName())) {
				model.addValidationError(getApplicationSession().getMessage("property.validGroupname"));
			}
            else {
				notGenShowList = propertyBillGenerationService
						.getAllPropWithAuthChangeByPropNo(model.getSpecialNotGenSearchDto(), orgId);
				if (!searchType.equals("GP")) {
					notGenShowList = notGenShowList.stream()
							.filter(notGen -> !MainetConstants.FlagY.equals(notGen.getIsGroup()))
							.collect(Collectors.toList());
				}
                model.setNotGenSearchDtoList(notGenShowList);
                if (notGenShowList == null || notGenShowList.isEmpty()) {
					model.addValidationError("Please enter valid Property/ Old property number ");
                }               
            }
        } else if (searchType.equals("AL")) {
            if ((model.getSpecialNotGenSearchDto().getLocId() == null || model.getSpecialNotGenSearchDto().getLocId() <= 0)
                    && (model.getSpecialNotGenSearchDto().getAssWard1() == null
                            || model.getSpecialNotGenSearchDto().getAssWard1() <= 0)
                    && (model.getSpecialNotGenSearchDto().getAssdUsagetype1() == null
                            || model.getSpecialNotGenSearchDto().getAssdUsagetype1() <= 0)
                    &&(model.getSpecialNotGenSearchDto().getParshadAssWard1() == null
                    || model.getSpecialNotGenSearchDto().getParshadAssWard1() <= 0)) {
                model.addValidationError("Please select any mandatory search criteria.");
            } else {
				notGenShowList = propertyBillGenerationService.fetchAssDetailBySearchCriteriaForProduct(
						model.getSpecialNotGenSearchDto(), UserSession.getCurrent().getOrganisation().getOrgid());
                model.setNotGenSearchDtoList(notGenShowList);
                if (notGenShowList == null || notGenShowList.isEmpty()) {
                    model.addValidationError("No record found");
                }
				if (Utility.isEnvPrefixAvailable(UserSession.getCurrent().getOrganisation(), "PSCL")
						&& StringUtils.equals("SM", model.getSpecialNotGenSearchDto().getSpecNotSearchType())
						&& CollectionUtils.isNotEmpty(notGenShowList)
						&& StringUtils.equals(MainetConstants.FlagN, notGenShowList.get(0).getSplNotDueDatePass())) {
					model.addValidationError("Special notice due date is not completed");
				}
            }
        }		
        return defaultResult();
    }


    @RequestMapping(params = "resetFormData", method = RequestMethod.POST)
    public ModelAndView reset(HttpServletRequest request) {
        this.sessionCleanup(request);
        PropertyBillGenerationModel model = this.getModel();
        model.getSpecialNotGenSearchDto().setSpecNotSearchType("SM");
        model.bind(request);
        model.setCommonHelpDocs("PropertyBillGeneration.html");
        List<LookUp> locList = getModel().getLocation();
        Organisation org = UserSession.getCurrent().getOrganisation();
        Long deptId = departmentService.getDepartmentIdByDeptCode(MainetConstants.Property.PROP_DEPT_SHORT_CODE);
        List<LocationMasEntity> location = iLocationMasService.findWZMappedLocationByOrgIdAndDeptId(org.getOrgid(),
                deptId);
        if (location != null && !location.isEmpty()) {
            location.forEach(loc -> {
                LookUp lookUp = new LookUp();
                lookUp.setLookUpId(loc.getLocId());
                lookUp.setDescLangFirst(loc.getLocNameEng());
                lookUp.setDescLangSecond(loc.getLocNameReg());
                locList.add(lookUp);
            });
        }
		List<String> parentPropNameList = selfAssessmentService.fetchAssessmentByParentPropName(org.getOrgid());
		this.getModel().setParentPropNameList(parentPropNameList);	
        //Checking Is this for Aligarh/SUDA or SKDLC
        model.setSearchGridHide(MainetConstants.FlagN);
        if(Utility.isEnvPrefixAvailable(UserSession.getCurrent().getOrganisation(), "SGH")) {
        	model.setSearchGridHide(MainetConstants.FlagY);
        }
        return defaultMyResult();
    }
    
    @ResponseBody
    @RequestMapping(params = "getBillingMethod", method = { RequestMethod.POST })
    public List<String> getBillingMethodAndFlatList(@RequestParam("propNo") String propNo, HttpServletRequest request) {
    	this.getModel().bind(request);
    	PropertyBillGenerationModel model = this.getModel();
    	List<String> flatNoList = null;
    	String billingMethod = null;
    	Long billingMethodId = primaryPropertyService.getBillMethodIdByPropNo(propNo, UserSession.getCurrent().getOrganisation().getOrgid());
    	LookUp billingMethodLookUp  = null;
    	try {
    		 billingMethodLookUp = CommonMasterUtility.getNonHierarchicalLookUpObject(billingMethodId, UserSession.getCurrent().getOrganisation());
    	}catch (Exception e) {
			// TODO: handle exception
		}
    	if(billingMethodLookUp != null) {
    		billingMethod = billingMethodLookUp.getLookUpCode();
    	}
    	if(StringUtils.isNotBlank(billingMethod) && StringUtils.equals(billingMethod, MainetConstants.FlagI)) {
    		flatNoList = new ArrayList<String>();
    		flatNoList = primaryPropertyService.getFlatNoIdByPropNo(propNo, UserSession.getCurrent().getOrganisation().getOrgid());
    	}
    	model.setFlatNoList(flatNoList);
    	return flatNoList;
    }

    @RequestMapping(method = RequestMethod.POST, params = "SearchPropNoForSkdcl")
    public ModelAndView searchForSkdcl(HttpServletRequest request) {
        getModel().bind(request);
        PropertyBillGenerationModel model = this.getModel();
        model.getSpecialNotGenSearchDto().setOrgId(UserSession.getCurrent().getOrganisation().getOrgid());
        model.setNotGenSearchDtoList(null);
        
		if(model.getFromDate() == null) {
			model.addValidationError(getApplicationSession().getMessage("property.AssessmentList.fromDate"));
		}
		if(model.getToDate() == null) {
			model.addValidationError(getApplicationSession().getMessage("property.AssessmentList.toDate"));
		}
		if(model.getFromDate() != null && model.getToDate() != null) {
			Object[] obj = null;
			obj = iFinancialYearService.getFinacialYearByDate(model.getFromDate());
			int comparision1 = model.getFromDate().compareTo((Date) obj[1]);
			int comparision2 = model.getToDate().compareTo((Date) obj[2]);
			if (comparision1 == -1 || comparision1 == 1 || comparision2 == -1 || comparision2 == 1) {
				model.addValidationError(getApplicationSession().getMessage("property.report.finYear.date"));
			}
		}
		if(!model.hasValidationErrors()) {
			 Long currFinId = iFinancialYearService.getFinanceYearId(model.getFromDate());
		  	  FinancialYear currFinYear = iFinancialYearService.getFinincialYearsById(currFinId, UserSession.getCurrent().getOrganisation().getOrgid());
		  	  FinancialYear prevFinYear = Utility.getPreviousFinYearByCurrYear(currFinYear);
		        List<NoticeGenSearchDto> notGenShowList = null;
		        if (model.getSpecialNotGenSearchDto().getSpecNotSearchType().equals("S")) {
		            if ((model.getSpecialNotGenSearchDto().getPropertyNo() == null
		                    || model.getSpecialNotGenSearchDto().getPropertyNo().isEmpty())
		                    && (model.getSpecialNotGenSearchDto().getOldPropertyNo() == null
		                            || model.getSpecialNotGenSearchDto().getOldPropertyNo().isEmpty())) {
		                model.addValidationError("Please enter valid property number or Old property number.");
		            } else {
		            	 notGenShowList = propertyBillGenerationService.fetchAssDetailBySearchCriteriaForSkdcl(
		                         model.getSpecialNotGenSearchDto(),currFinId,prevFinYear.getFaYear(),
		                         UserSession.getCurrent().getOrganisation().getOrgid());
		                model.setNotGenSearchDtoList(notGenShowList);
		                if (notGenShowList == null || notGenShowList.isEmpty()) {
		                    model.addValidationError("No record found");
		                }else if(CollectionUtils.isNotEmpty(notGenShowList) && StringUtils.equals(notGenShowList.get(0).getCheckStatus(), MainetConstants.FlagY)) {
		                	model.addValidationError("Bill has been already generated against selected financial year / No bill exist for previous fin year");
		                	notGenShowList.clear();
		                }
		            }
		        } else if (model.getSpecialNotGenSearchDto().getSpecNotSearchType().equals("M") || model.getSpecialNotGenSearchDto().getSpecNotSearchType().equals("A")) {
		            if ((model.getSpecialNotGenSearchDto().getLocId() == null || model.getSpecialNotGenSearchDto().getLocId() <= 0)
		                    && (model.getSpecialNotGenSearchDto().getAssWard1() == null
		                            || model.getSpecialNotGenSearchDto().getAssWard1() <= 0)
		                    && (model.getSpecialNotGenSearchDto().getAssdUsagetype1() == null
		                            || model.getSpecialNotGenSearchDto().getAssdUsagetype1() <= 0)) {
		                model.addValidationError("Please select any mandatory search criteria.");
		            } else {
		                notGenShowList = propertyBillGenerationService.fetchAssDetailBySearchCriteriaForSkdcl(
		                        model.getSpecialNotGenSearchDto(),currFinId,prevFinYear.getFaYear(),
		                        UserSession.getCurrent().getOrganisation().getOrgid());
		                model.setNotGenSearchDtoList(notGenShowList);
		                if (notGenShowList == null || notGenShowList.isEmpty()) {
		                    model.addValidationError("No record found");
		                }
		            }
		        }
		}
        return defaultResult();
    }
    
    @ResponseBody
    @RequestMapping(params = "saveDemandBillSpending", method = RequestMethod.POST)
    public Map<String, Object> saveDemandBillSpending(HttpServletRequest httpServletRequest) {
	getModel().bind(httpServletRequest);
	Map<String, Object> object = new LinkedHashMap<String, Object>();
	
	if (this.getModel().saveFormForMissingPropNos()) {
		 object.put("success", MainetConstants.FlagY);
	} else {
		object.put(MainetConstants.ERROR, this.getModel().getBindingResult().getAllErrors());
	}
	return object;

	
    }
    
    @RequestMapping(params = "getBillGenLog",method = { RequestMethod.POST, RequestMethod.GET })
    public ModelAndView getBillGenLog(HttpServletRequest request) {
        this.getModel().setPropertyBillGenerationMap(ApplicationSession.getInstance().getPropertyBillGenerationMap());
        return new ModelAndView("PropertyBillGenerationLog",
                MainetConstants.CommonConstants.COMMAND, getModel());
    }
}
