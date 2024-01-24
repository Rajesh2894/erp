package com.abm.mainet.common.workflow.ui.controller;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.abm.mainet.common.constant.MainetConstants;
import com.abm.mainet.common.ui.controller.AbstractFormController;
import com.abm.mainet.common.workflow.domain.BpmBrmDeploymentMaster;
import com.abm.mainet.common.workflow.service.BpmBrmDeploymentService;
import com.abm.mainet.common.workflow.ui.model.BpmBrmDeploymentMasterModel;

/**
 * 
 * @author rahul.maurya
 * @see BpmBrmDeploymentMaster
 *
 */
@Controller
@RequestMapping("/BpmBrmDeploymentMaster.html")
public class BpmBrmDeploymentMasterController extends AbstractFormController<BpmBrmDeploymentMasterModel> {

    @Autowired
    private BpmBrmDeploymentService bpmBrmDeploymentService;

    /**
     * It will return default Home Page of BpmBrmDeployment Master
     * @param httpServletRequest
     * @return ModelAndView
     */

    @RequestMapping(method = { RequestMethod.GET, RequestMethod.POST })
    public ModelAndView index(final HttpServletRequest httpServletRequest) {
        sessionCleanup(httpServletRequest);
        this.getModel().setBpmBrmDeploymentMasterDtos(bpmBrmDeploymentService.getAllBpmBrmDeploymentMaster());
        return defaultResult();
    }

    @RequestMapping(method = { RequestMethod.POST,
            RequestMethod.GET }, params = MainetConstants.WorkFlow.BpmBrmDeployment.ADD_BPMBRMDEPLOYMENT_MASTER)
    public ModelAndView addJudgeMaster(final HttpServletRequest request) {
        sessionCleanup(request);
        this.getModel().setSaveMode(MainetConstants.WorkFlow.BpmBrmDeployment.SaveMode.CREATE);
        return new ModelAndView(MainetConstants.WorkFlow.BpmBrmDeployment.BPMBRMDEPLOYMENT_MASTER_FORM, MainetConstants.FORM_NAME,
                this.getModel());
    }

    /**
     * This will set mode as View and render view on BpmBrmDeploymentMaster Form as Read only
     * 
     * @param mode String Mode(C:CREATE, E:EDIT, V:VIEW, D:DELETE)
     * @param id Long BpmBrmDeployment Id
     * @param httpServletRequest
     * @return ModelAndView
     */

    @RequestMapping(method = {
            RequestMethod.POST }, params = MainetConstants.WorkFlow.BpmBrmDeployment.VIEW_BPMBRMDEPLOYMENT_MASTER)
    public ModelAndView viewJudgeMaster(@RequestParam(MainetConstants.REQUIRED_PG_PARAM.MODE) String mode,
            @RequestParam(MainetConstants.Common_Constant.ID) Long id,
            final HttpServletRequest httpServletRequest) {
        this.getModel().setSaveMode(MainetConstants.WorkFlow.BpmBrmDeployment.SaveMode.VIEW);
        this.getModel().setBpmBrmDeploymentMasterDto(bpmBrmDeploymentService.getBpmBrmDeploymentMasterrById(id));
        return new ModelAndView(MainetConstants.WorkFlow.BpmBrmDeployment.BPMBRMDEPLOYMENT_MASTER_FORM, MainetConstants.FORM_NAME,
                this.getModel());
    }

    /**
     * This will set mode as Edit and render view on BpmBrmDeploymentMaster Form as an Edit Mode
     * 
     * @param mode String Mode(C:CREATE, E:EDIT, V:VIEW, D:DELETE)
     * @param id Long BpmBrmDeployment Id
     * @param httpServletRequest
     * @return ModelAndView
     */

    @RequestMapping(method = {
            RequestMethod.POST }, params = MainetConstants.WorkFlow.BpmBrmDeployment.EDIT_BPMBRMDEPLOYMENT_MASTER)
    public ModelAndView editJudgeMaster(@RequestParam(MainetConstants.REQUIRED_PG_PARAM.MODE) String mode,
            @RequestParam(MainetConstants.Common_Constant.ID) Long id,
            final HttpServletRequest httpServletRequest) {
        this.getModel().setSaveMode(MainetConstants.WorkFlow.BpmBrmDeployment.SaveMode.EDIT);
        this.getModel().setBpmBrmDeploymentMasterDto(bpmBrmDeploymentService.getBpmBrmDeploymentMasterrById(id));
        return new ModelAndView(MainetConstants.WorkFlow.BpmBrmDeployment.BPMBRMDEPLOYMENT_MASTER_FORM, MainetConstants.FORM_NAME,
                this.getModel());
    }

}
