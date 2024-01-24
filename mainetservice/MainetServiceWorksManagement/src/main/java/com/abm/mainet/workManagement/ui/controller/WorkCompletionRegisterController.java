package com.abm.mainet.workManagement.ui.controller;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.abm.mainet.common.constant.MainetConstants;
import com.abm.mainet.common.ui.controller.AbstractFormController;
import com.abm.mainet.common.utility.ApplicationContextProvider;
import com.abm.mainet.common.utility.UserSession;
import com.abm.mainet.workManagement.dto.WorkDefinitionDto;
import com.abm.mainet.workManagement.dto.WorksRABillDto;
import com.abm.mainet.workManagement.service.WmsProjectMasterService;
import com.abm.mainet.workManagement.service.WorkDefinitionService;
import com.abm.mainet.workManagement.service.WorksRABillService;
import com.abm.mainet.workManagement.ui.model.WorkCompletionRegisterModel;

/**
 * @author Saiprasad.Vengurlekar
 *
 */

@Controller
@RequestMapping("/workCompletionRegister.html")
public class WorkCompletionRegisterController extends AbstractFormController<WorkCompletionRegisterModel> {

    @Autowired
    private WorkDefinitionService workDefinationService;

    /**
     * Used to default Work Completion Register Summary page
     * 
     * @param httpServletRequest
     * @return defaultResult
     * @throws Exception
     */

    @RequestMapping(method = { RequestMethod.POST, RequestMethod.GET })
    public ModelAndView index(final HttpServletRequest httpServletRequest) {
        sessionCleanup(httpServletRequest);
        Long currentOrgId = UserSession.getCurrent().getOrganisation().getOrgid();
        this.getModel()
                .setProjectMasterList(ApplicationContextProvider.getApplicationContext().getBean(WmsProjectMasterService.class)
                        .getActiveProjectMasterListByOrgId(currentOrgId));
        return defaultResult();
    }

    /**
     * Used to get All Active WorksName By ProjectId
     * 
     * @param orgId
     * @param projId
     * @return workDefinationDto
     */
    @ResponseBody
    @RequestMapping(params = MainetConstants.WorksManagement.WORKS_NAME, method = RequestMethod.POST)
    public List<WorkDefinitionDto> getAllActiveWorksNameByProjectId(
            @RequestParam(MainetConstants.WorksManagement.PROJ_ID) Long projId) {
        return workDefinationService
                .findAllWorkDefinationByProjId(UserSession.getCurrent().getOrganisation().getOrgid(), projId);

    }

    /**
     * this method is used to Get Work Completion Register Entry
     * 
     * @param request
     * @return ModelAndView
     */
    @RequestMapping(params = MainetConstants.WorksManagement.GET_REGISTER, method = RequestMethod.POST)
    public ModelAndView getRegister(final HttpServletRequest request,
            @RequestParam(MainetConstants.WorksManagement.PROJ_ID) Long projId,
            @RequestParam(MainetConstants.WorksManagement.WORK_ID) Long workId) {

        Long orgId = UserSession.getCurrent().getOrganisation().getOrgid();
        List<WorkDefinitionDto> defDtoList = new ArrayList<>();

        List<WorksRABillDto> raBills = ApplicationContextProvider.getApplicationContext().getBean(WorksRABillService.class)
                .getRaBillListByProjAndWorkNumber(projId, workId, orgId);

        if (!raBills.isEmpty()) {
            for (WorksRABillDto billDto : raBills) {
                WorkDefinitionDto defDto = workDefinationService.getWorkRegisterByWorkId(workId, billDto.getRaCode(),
                        orgId);
                if (defDto != null) {
                    defDto.setRaCode(billDto.getRaCode());
                    defDto.setRegDtos(
                            workDefinationService.getWorkRegisterDetailsByWorkId(workId, billDto.getRaCode(), orgId));
                    defDto.setWorkEstAmt(defDto.getRegDtos().get(0).getWorkEstAmt());
                    defDtoList.add(defDto);
                }
            }
        }
        this.getModel().setWorkDefDtos(defDtoList);
        return new ModelAndView(MainetConstants.WorksManagement.WORK_REG_ENTRY, MainetConstants.FORM_NAME,
                this.getModel());
    }

    /**
     * this method is used to print Work Completion Register
     * 
     * @param request
     * 
     */
    @RequestMapping(params = MainetConstants.WorksManagement.PRINT_WORK_REG, method = RequestMethod.POST)
    public ModelAndView printWorkCompletionRegister(final HttpServletRequest request) {

        return new ModelAndView(MainetConstants.WorksManagement.WORK_REG_ENTRY, MainetConstants.FORM_NAME,
                this.getModel());
    }

}
