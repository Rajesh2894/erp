package com.abm.mainet.property.ui.controller;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.abm.mainet.common.constant.MainetConstants;
import com.abm.mainet.common.domain.LocationMasEntity;
import com.abm.mainet.common.domain.Organisation;
import com.abm.mainet.common.master.service.DepartmentService;
import com.abm.mainet.common.service.ILocationMasService;
import com.abm.mainet.common.ui.controller.AbstractFormController;
import com.abm.mainet.common.utility.LookUp;
import com.abm.mainet.common.utility.UserSession;
import com.abm.mainet.property.dto.NoticeGenSearchDto;
import com.abm.mainet.property.service.PropertyNoticeService;
import com.abm.mainet.property.ui.model.PropertyDemandNoticeGenerationModel;

@Controller
@RequestMapping("PropertyDemandNoticeGeneration.html")
public class PropertyDemandNoticeGenerationController extends AbstractFormController<PropertyDemandNoticeGenerationModel> {

    @Resource
    private ILocationMasService iLocationMasService;

    @Autowired
    private DepartmentService departmentService;

    @Autowired
    private PropertyNoticeService propertyNoticeService;

    @RequestMapping(method = { RequestMethod.POST, RequestMethod.GET })
    public ModelAndView index(HttpServletRequest request) {
        this.sessionCleanup(request);
        getModel().setCommonHelpDocs("PropertyDemandNoticeGeneration.html");
        this.setData(request);        
        return defaultResult();
    }
    
    public void setData(HttpServletRequest request){
    	PropertyDemandNoticeGenerationModel model = this.getModel();
        model.getSpecialNotGenSearchDto().setSpecNotSearchType("SM");
        model.bind(request);
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
        List<String> notices = new ArrayList<>();
        notices.add("DN");
        notices.add("WN");
        // notices.add("WEN");
        List<LookUp> demandType = model.getLevelData("NTY");
        demandType.stream()
                .filter(notice -> notices.contains(notice.getLookUpCode()))
                .forEach(type -> {
                    model.getDemandType().add(type);
                });
    }

    @RequestMapping(method = RequestMethod.POST, params = "SearchPropNo")
    public ModelAndView search(HttpServletRequest request) {
        getModel().bind(request);
        PropertyDemandNoticeGenerationModel model = this.getModel();
        model.setNotGenSearchDtoList(null);
        if (model.getSpecialNotGenSearchDto().getNoticeType() == null || model.getSpecialNotGenSearchDto().getNoticeType() <= 0) {
            model.addValidationError("Please select notice type.");
        } else {
            if (model.getSpecialNotGenSearchDto().getSpecNotSearchType().equals("SM")) {
                if ((model.getSpecialNotGenSearchDto().getPropertyNo() == null
                        || model.getSpecialNotGenSearchDto().getPropertyNo().isEmpty())
                        && (model.getSpecialNotGenSearchDto().getOldPropertyNo() == null
                                || model.getSpecialNotGenSearchDto().getOldPropertyNo().isEmpty())) {
                    model.addValidationError("Please enter valid property number or Old property number.");
                } else {
                    fetchPropertyForDemandNotice(model);
                }
			} else if (model.getSpecialNotGenSearchDto().getSpecNotSearchType().equals("AL")) {
				if ((model.getSpecialNotGenSearchDto().getLocId() == null
						|| model.getSpecialNotGenSearchDto().getLocId() <= 0)
						&& (model.getSpecialNotGenSearchDto().getAssWard1() == null
								|| model.getSpecialNotGenSearchDto().getAssWard1() <= 0)
						&& (model.getSpecialNotGenSearchDto().getAssdUsagetype1() == null
								|| model.getSpecialNotGenSearchDto().getAssdUsagetype1() <= 0)
						&& (model.getSpecialNotGenSearchDto().getFromAmount() <= 0)
								&& (model.getSpecialNotGenSearchDto().getToAmount() <= 0)) {
                    model.addValidationError("Please select any mandatory search criteria.");
                } else {
                    fetchPropertyForDemandNotice(model);
                }
            }
        }
        
        return defaultResult();
    }

    private void fetchPropertyForDemandNotice(PropertyDemandNoticeGenerationModel model) {
        List<NoticeGenSearchDto> notGenShowList;
        notGenShowList = propertyNoticeService.fetchPropertyAfterDueDate(
                model.getSpecialNotGenSearchDto(),
                UserSession.getCurrent().getOrganisation().getOrgid());
        model.setNotGenSearchDtoList(notGenShowList);
        if (notGenShowList == null || notGenShowList.isEmpty()) {
            model.addValidationError("No record found");
        }
    }

    @RequestMapping(method = RequestMethod.POST, params = "resetForm")
    public ModelAndView resetFormData(HttpServletRequest request) {
    	this.sessionCleanup(request);
    	this.setData(request);
    	return new ModelAndView("PropertyDemandNoticeGenerationValidn", MainetConstants.FORM_NAME, this.getModel());
    }
   
    @ResponseBody
    @RequestMapping(params = "saveDemandNotice", method = RequestMethod.POST)
    public Map<String, Object> saveAgencyRegistration(HttpServletRequest httpServletRequest) {
	getModel().bind(httpServletRequest);
	Map<String, Object> object = new LinkedHashMap<String, Object>();
	if (this.getModel().saveForm()) {
	    object.put("applicationId", this.getModel().getApmApplicationId());
	} else {
	    object.put(MainetConstants.ERROR, this.getModel().getBindingResult().getAllErrors());
	}

	return object;

    }
}
