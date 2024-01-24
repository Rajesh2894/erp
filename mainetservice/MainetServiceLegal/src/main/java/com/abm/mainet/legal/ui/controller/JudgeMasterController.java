package com.abm.mainet.legal.ui.controller;

import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.collections.CollectionUtils;
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
import com.abm.mainet.common.utility.UserSession;
import com.abm.mainet.legal.dto.JudgeMasterDTO;
import com.abm.mainet.legal.service.ICourtMasterService;
import com.abm.mainet.legal.service.IJudgeMasterService;
import com.abm.mainet.legal.ui.model.JudgeMasterModel;

/**
 * 
 * @author rahul.maurya
 * @see IJudgeMasterService
 *
 */
@Controller
@RequestMapping("/JudgeMaster.html")
public class JudgeMasterController extends AbstractFormController<JudgeMasterModel> {

    @Autowired
    private IJudgeMasterService judgeMasterService;

    @Autowired
	 private ICourtMasterService courtMasterService;
    /**
     * It will return default Home Page of Judge Master
     * @param httpServletRequest
     * @return ModelAndView
     */

    @RequestMapping(method = { RequestMethod.GET, RequestMethod.POST })
    public ModelAndView index(final HttpServletRequest httpServletRequest) {
        sessionCleanup(httpServletRequest);
        Long orgid = UserSession.getCurrent().getOrganisation().getOrgid();
        this.getModel().setJudgeMasterDtos(judgeMasterService.getAllJudgeMaster(orgid));
        this.getModel().setCommonHelpDocs("JudgeMaster.html");
        this.getModel().setCourtNameList(courtMasterService.getAllActiveCourtMaster(orgid));
        return defaultResult();
    }

    /**
     * It will set mode as Create and render view on JudgeMaster Form
     * 
     * @param mode String Mode(C:CREATE, E:EDIT, V:VIEW, D:DELETE)
     * @param request
     * @return ModelAndView
     */

    @RequestMapping(method = { RequestMethod.POST,
            RequestMethod.GET }, params = MainetConstants.Legal.ADD_JUDGE_MASTER)
    public ModelAndView addJudgeMaster(final HttpServletRequest request) {
        sessionCleanup(request);
        this.getModel().setSaveMode(MainetConstants.Legal.SaveMode.CREATE);
        return new ModelAndView(MainetConstants.Legal.JUDGE_MASTER_FORM, MainetConstants.FORM_NAME,
                this.getModel());
    }

    /**
     * This will set mode as View and render view on JudgeMaster Form as Read only
     * 
     * @param mode String Mode(C:CREATE, E:EDIT, V:VIEW, D:DELETE)
     * @param id Long Judge Id
     * @param httpServletRequest
     * @return ModelAndView
     */

    @RequestMapping(method = { RequestMethod.POST }, params = MainetConstants.Legal.VIEW_JUDGE_MASTER)
    public ModelAndView viewJudgeMaster(@RequestParam(MainetConstants.REQUIRED_PG_PARAM.MODE) String mode,
            @RequestParam(MainetConstants.Common_Constant.ID) Long id,
            final HttpServletRequest httpServletRequest) {
        this.getModel().setSaveMode(MainetConstants.Legal.SaveMode.VIEW);
        this.getModel()
                .setJudgeMasterDto(judgeMasterService.getJudgeMasterById(id));
        return new ModelAndView(MainetConstants.Legal.JUDGE_MASTER_FORM, MainetConstants.FORM_NAME, this.getModel());
    }

    /**
     * This will set mode as Edit and render view on JudgeMaster Form as an Edit Mode
     * 
     * @param mode String Mode(C:CREATE, E:EDIT, V:VIEW, D:DELETE)
     * @param id Long Judge Id
     * @param httpServletRequest
     * @return ModelAndView
     */

    @RequestMapping(method = { RequestMethod.POST }, params = MainetConstants.Legal.EDIT_JUDGE_MASTER)
    public ModelAndView editJudgeMaster(@RequestParam(MainetConstants.REQUIRED_PG_PARAM.MODE) String mode,
            @RequestParam(MainetConstants.Common_Constant.ID) Long id,
            final HttpServletRequest httpServletRequest) {
        this.getModel().setSaveMode(MainetConstants.Legal.SaveMode.EDIT);
        this.getModel().setJudgeMasterDto(judgeMasterService.getJudgeMasterById(id));

        return new ModelAndView(MainetConstants.Legal.JUDGE_MASTER_FORM, MainetConstants.FORM_NAME, this.getModel());
    }

    /**
     * This method will soft delete JudgeMaster record of given id.
     * 
     * @param mode String Mode(C:CREATE, E:EDIT, V:VIEW, D:DELETE)
     * @param id Long Judge Id
     * @param httpServletRequest
     * @return boolean
     */

    @RequestMapping(method = { RequestMethod.POST }, params = MainetConstants.Legal.DELETE_COURT_MASTER)
    @ResponseBody
    public boolean deleteJudgeMaster(@RequestParam(MainetConstants.REQUIRED_PG_PARAM.MODE) String mode,
            @RequestParam(MainetConstants.Common_Constant.ID) Long id,
            final HttpServletRequest httpServletRequest) {

        this.getModel().setSaveMode(MainetConstants.Legal.SaveMode.DELETE);
        JudgeMasterDTO judgeMasterDto = new JudgeMasterDTO();
        judgeMasterDto.setId(id);
        judgeMasterDto.setOrgId(UserSession.getCurrent().getOrganisation().getOrgid());
        Employee employee = UserSession.getCurrent().getEmployee();
        judgeMasterDto.setUpdatedBy(employee.getEmpId());
        judgeMasterDto.setUpdatedDate(new Date());
        judgeMasterDto.setLgIpMacUpd(employee.getEmppiservername());
        return judgeMasterService.deleteJudgeMaster(judgeMasterDto);
    }

    
    /**
     * code added by rahul.chaubey
     * For Adding Judge details from case hearing details page 
     */

    @RequestMapping(params = "addJudge", method = RequestMethod.POST )
    private ModelAndView judgeMasterForm(final HttpServletRequest httpServletRequest, @RequestParam("id") String id)
    {
    	this.getModel().setCaseId(Long.valueOf(id));
    	this.getModel().setSaveMode(MainetConstants.Legal.SaveMode.CREATE);
    	return  new ModelAndView("JudgeMaster/addJudgeFORM", MainetConstants.FORM_NAME, this.getModel());
    }
    

     //127193
    @ResponseBody
    @RequestMapping(params = "searchJugdgeData", method = RequestMethod.POST)
    public ModelAndView searchJugdgeData(final HttpServletRequest request,
			@RequestParam(required = false) Long crtId, @RequestParam(required = false) String judgeStatus,
			@RequestParam(required = false) String judgeBenchName) {
		getModel().bind(request);
		Long orgid = UserSession.getCurrent().getOrganisation().getOrgid();
		ModelAndView mv = null;
		List<JudgeMasterDTO> judgeMasterDto = judgeMasterService.getjudgeDetails(crtId, judgeStatus, judgeBenchName,
				orgid);
		if (CollectionUtils.isNotEmpty(judgeMasterDto)) {
			this.getModel().setJudgeMasterDtos(judgeMasterDto);
			mv = new ModelAndView("JudgeMasterFormValidn", MainetConstants.FORM_NAME, this.getModel());
		} else {
			mv = new ModelAndView("JudgeMasterFormValidn", MainetConstants.FORM_NAME, this.getModel());
			mv.addObject(BindingResult.MODEL_KEY_PREFIX + MainetConstants.FORM_NAME, getModel().getBindingResult());
			this.getModel().addValidationError(ApplicationSession.getInstance().getMessage("lgl.nofoundmsg"));

		}
		this.getModel().getJudgeMasterDto().getJudgeDetails().get(0).setJudgeStatus(judgeStatus);
		this.getModel().getJudgeMasterDto().getJudgeDetails().get(0).setCrtId(crtId);
		this.getModel().getJudgeMasterDto().setJudgeBenchName(judgeBenchName);
		return mv;
	}
    
}
