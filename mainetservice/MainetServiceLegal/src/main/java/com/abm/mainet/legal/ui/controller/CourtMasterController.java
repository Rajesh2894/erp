package com.abm.mainet.legal.ui.controller;

import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.abm.mainet.common.constant.MainetConstants;
import com.abm.mainet.common.domain.Employee;
import com.abm.mainet.common.ui.controller.AbstractFormController;
import com.abm.mainet.common.utility.ApplicationSession;
import com.abm.mainet.common.utility.CommonMasterUtility;
import com.abm.mainet.common.utility.LookUp;
import com.abm.mainet.common.utility.UserSession;
import com.abm.mainet.legal.dto.CourtMasterDTO;
import com.abm.mainet.legal.service.ICourtMasterService;
import com.abm.mainet.legal.ui.model.CourtMasterModel;

/**
 * 
 * @author satish.kadu
 * @see ICourtMasterService
 *
 */
@Controller
@RequestMapping("/CourtMaster.html")
public class CourtMasterController extends AbstractFormController<CourtMasterModel> {

    @Autowired
    private ICourtMasterService courtMasterService;

    /**
     * It will return default Home Page of Court Master
     * @param httpServletRequest
     * @return ModelAndView
     */
    @RequestMapping(method = { RequestMethod.POST, RequestMethod.GET })
    public ModelAndView index(final HttpServletRequest httpServletRequest) {
        sessionCleanup(httpServletRequest);
        Long orgid = UserSession.getCurrent().getOrganisation().getOrgid();
        this.getModel().setCourtMasterDtos(courtMasterService.getAllCourtMaster(orgid));
        this.getModel().setCourtNameList(courtMasterService.getAllActiveCourtMaster(orgid));
        this.getModel().setCommonHelpDocs("CourtMaster.html");
        return defaultResult();
    }

    /**
     * It will set mode as Create and render view on CourtMaster Form
     * 
     * @param mode String Mode(C:CREATE, E:EDIT, V:VIEW, D:DELETE)
     * @param request
     * @return ModelAndView
     */
    @RequestMapping(method = { RequestMethod.POST,
            RequestMethod.GET }, params = MainetConstants.Legal.ADD_COURT_MASTER)
    public ModelAndView addCourtMaster(@RequestParam(MainetConstants.REQUIRED_PG_PARAM.MODE) String mode,
            final HttpServletRequest request) {
        this.getModel().setSaveMode(MainetConstants.Legal.SaveMode.CREATE);
         //120803
        List<LookUp> envLookUpList = CommonMasterUtility.lookUpListByPrefix(MainetConstants.ENV, UserSession.getCurrent().getOrganisation().getOrgid());
        boolean tscl = envLookUpList.stream()
                .anyMatch(env -> env.getLookUpCode().equals(MainetConstants.APP_NAME.TSCL) && StringUtils.equals(env.getOtherField(),
                        MainetConstants.FlagY));
        if (tscl) {
           this.getModel().setEnvFlag(MainetConstants.FlagY);
        }
        return new ModelAndView(MainetConstants.Legal.COURT_MASTER_FORM, MainetConstants.FORM_NAME,
                this.getModel());
    }

    /**
     * This will set mode as Edit and render view on CourtMaster Form as an Edit Mode
     * 
     * @param mode String Mode(C:CREATE, E:EDIT, V:VIEW, D:DELETE)
     * @param id Long Court Id
     * @param httpServletRequest
     * @return ModelAndView
     */
    @RequestMapping(method = { RequestMethod.POST }, params = MainetConstants.Legal.EDIT_COURT_MASTER)
    public ModelAndView editCourtMaster(@RequestParam(MainetConstants.REQUIRED_PG_PARAM.MODE) String mode,
            @RequestParam(MainetConstants.Common_Constant.ID) Long id,
            final HttpServletRequest httpServletRequest) {
        this.getModel().setSaveMode(MainetConstants.Legal.SaveMode.EDIT);
        this.getModel()
                .setCourtMasterDto(courtMasterService.getCourtMasterById(id));
        //120803
        List<LookUp> envLookUpList = CommonMasterUtility.lookUpListByPrefix(MainetConstants.ENV, UserSession.getCurrent().getOrganisation().getOrgid());
        boolean tscl = envLookUpList.stream()
                .anyMatch(env -> env.getLookUpCode().equals(MainetConstants.APP_NAME.TSCL) && StringUtils.equals(env.getOtherField(),
                        MainetConstants.FlagY));
        if (tscl) {
           this.getModel().setEnvFlag(MainetConstants.FlagY);
        }else {
        	this.getModel().setEnvFlag(MainetConstants.FlagN);
        }
        return new ModelAndView(MainetConstants.Legal.COURT_MASTER_FORM, MainetConstants.FORM_NAME, this.getModel());
    }

    /**
     * This will set mode as View and render view on CourtMaster Form as Read only
     * 
     * @param mode String Mode(C:CREATE, E:EDIT, V:VIEW, D:DELETE)
     * @param id Long Court Id
     * @param httpServletRequest
     * @return ModelAndView
     */
    @RequestMapping(method = { RequestMethod.POST }, params = MainetConstants.Legal.VIEW_COURT_MASTER)
    public ModelAndView viewCourtMaster(@RequestParam(MainetConstants.REQUIRED_PG_PARAM.MODE) String mode,
            @RequestParam(MainetConstants.Common_Constant.ID) Long id,
            final HttpServletRequest httpServletRequest) {
        this.getModel().setSaveMode(MainetConstants.Legal.SaveMode.VIEW);
        this.getModel()
                .setCourtMasterDto(courtMasterService.getCourtMasterById(id));
         //120803
        List<LookUp> envLookUpList = CommonMasterUtility.lookUpListByPrefix(MainetConstants.ENV, UserSession.getCurrent().getOrganisation().getOrgid());
        boolean tscl = envLookUpList.stream()
                .anyMatch(env -> env.getLookUpCode().equals(MainetConstants.APP_NAME.TSCL) && StringUtils.equals(env.getOtherField(),
                        MainetConstants.FlagY));
        if (tscl) {
           this.getModel().setEnvFlag(MainetConstants.FlagY);
        }
        return new ModelAndView(MainetConstants.Legal.COURT_MASTER_FORM, MainetConstants.FORM_NAME, this.getModel());
    }

    /**
     * This method will soft delete CourtMaster record of given id.
     * 
     * @param mode String Mode(C:CREATE, E:EDIT, V:VIEW, D:DELETE)
     * @param id Long Court Id
     * @param httpServletRequest
     * @return boolean
     */
    @RequestMapping(method = { RequestMethod.POST }, params = MainetConstants.Legal.DELETE_COURT_MASTER)
    @ResponseBody
    public boolean deleteCourtMaster(@RequestParam(MainetConstants.REQUIRED_PG_PARAM.MODE) String mode,
            @RequestParam(MainetConstants.Common_Constant.ID) Long id,
            final HttpServletRequest httpServletRequest) {

        this.getModel().setSaveMode(MainetConstants.Legal.SaveMode.DELETE);
        CourtMasterDTO courtMasterDto = new CourtMasterDTO();
        courtMasterDto.setId(id);
        courtMasterDto.setOrgId(UserSession.getCurrent().getOrganisation().getOrgid());
        Employee employee = UserSession.getCurrent().getEmployee();
        courtMasterDto.setUpdatedBy(employee.getEmpId());
        courtMasterDto.setUpdatedDate(new Date());
        courtMasterDto.setLgIpMacUpd(employee.getEmppiservername());
        return courtMasterService.deleteCourtMaster(courtMasterDto);
    }

    /**
     * This method will return curt address for requested curt id or blank otherwise.
     * 
     * @param id Long Court Id
     * @param httpServletRequest
     * @return String
     */
    @RequestMapping(method = { RequestMethod.POST }, params = MainetConstants.Legal.GET_COURT_BY_ID)
    @ResponseBody
    public String getCourtAddressById(@RequestParam(MainetConstants.Common_Constant.ID) Long id,
            final HttpServletRequest httpServletRequest) {
        String crtAddress = MainetConstants.BLANK;
        CourtMasterDTO court = courtMasterService.getCourtMasterById(id);
        if (court != null)
            crtAddress = court.getCrtAddress();
        return crtAddress;
    }
   
    //127193
    @ResponseBody
    @RequestMapping(params = "searchCourtData", method = RequestMethod.POST)
    public ModelAndView searchCourtData(final HttpServletRequest request,
    		 @RequestParam(required = false) Long crtId, @RequestParam(required = false) Long crtType) {
		getModel().bind(request);
		Long orgid = UserSession.getCurrent().getOrganisation().getOrgid();
		ModelAndView mv = null;
		List<CourtMasterDTO> masterDto = courtMasterService.getCourtMasterDetailByIds(crtId, crtType, orgid);
		if (CollectionUtils.isNotEmpty(masterDto)) {
			this.getModel().setCourtMasterDtos(masterDto);
			mv = new ModelAndView("CourtMasterFormValidn", MainetConstants.FORM_NAME, this.getModel());
		} else {
			mv = new ModelAndView("CourtMasterFormValidn", MainetConstants.FORM_NAME, this.getModel());
			mv.addObject(BindingResult.MODEL_KEY_PREFIX + MainetConstants.FORM_NAME, getModel().getBindingResult());
			this.getModel().addValidationError(ApplicationSession.getInstance().getMessage("lgl.nofoundmsg"));
		}
		this.getModel().getCourtMasterDto().setId(crtId);
		this.getModel().getCourtMasterDto().setCrtType(crtType);
		return mv;
    }
}
