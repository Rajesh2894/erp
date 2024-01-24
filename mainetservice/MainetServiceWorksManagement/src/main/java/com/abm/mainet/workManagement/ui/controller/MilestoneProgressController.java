/**
 * 
 */
package com.abm.mainet.workManagement.ui.controller;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.abm.mainet.common.constant.MainetConstants;
import com.abm.mainet.common.integration.dms.client.FileNetApplicationClient;
import com.abm.mainet.common.integration.dms.domain.AttachDocs;
import com.abm.mainet.common.integration.dms.fileUpload.FileUploadUtility;
import com.abm.mainet.common.integration.dms.service.IAttachDocsService;
import com.abm.mainet.common.integration.dms.service.IFileUploadService;
import com.abm.mainet.common.integration.dto.DocumentDetailsVO;
import com.abm.mainet.common.ui.controller.AbstractFormController;
import com.abm.mainet.common.utility.ApplicationContextProvider;
import com.abm.mainet.common.utility.UserSession;
import com.abm.mainet.workManagement.dto.MileStoneDTO;
import com.abm.mainet.workManagement.dto.MilestoneDetDto;
import com.abm.mainet.workManagement.dto.WorkDefinitionDto;
import com.abm.mainet.workManagement.service.MileStoneService;
import com.abm.mainet.workManagement.service.WmsProjectMasterService;
import com.abm.mainet.workManagement.service.WorkDefinitionService;
import com.abm.mainet.workManagement.ui.model.MilestoneProgressModel;

/**
 * @author Saiprasad.Vengurlekar
 *
 */

@Controller
@RequestMapping("/MilestoneProgress.html")
public class MilestoneProgressController extends AbstractFormController<MilestoneProgressModel> {

    @Autowired
    private WmsProjectMasterService projectMasterService;

    @Autowired
    private WorkDefinitionService workDefinationService;

    @Autowired
    private MileStoneService mileStoneService;

    /**
     * Used to default Milestone Progress Summary page
     * 
     * @param httpServletRequest
     * @return defaultResult
     * @throws Exception
     */

    @RequestMapping(method = { RequestMethod.POST, RequestMethod.GET })
    public ModelAndView index(final HttpServletRequest httpServletRequest) {
        sessionCleanup(httpServletRequest);

        ApplicationContextProvider.getApplicationContext().getBean(IFileUploadService.class).sessionCleanUpForFileUpload();
        Long currentOrgId = UserSession.getCurrent().getOrganisation().getOrgid();
        this.getModel().setProjectMasterList(projectMasterService.getActiveProjectMasterListByOrgId(currentOrgId));
        this.getModel().setCommonHelpDocs("MilestoneProgress.html");
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
     * @param projId
     * @param workId
     * @param mileStoneFlag
     * @return projectDtoList
     */

    @ResponseBody
    @RequestMapping(params = MainetConstants.WorksManagement.GET_MILESTONES, method = RequestMethod.POST)
    public List<MileStoneDTO> physicalMilestoneList(@RequestParam(MainetConstants.WorksManagement.PROJ_ID) Long projId,
            @RequestParam(MainetConstants.WorksManagement.WORK_ID) Long workId) {
        List<MileStoneDTO> dtoList = null;
        Long currentOrgid = UserSession.getCurrent().getOrganisation().getOrgid();

        if (workId != null) {
            dtoList = mileStoneService.milestoneDtosWithWorkId(projId, workId, currentOrgid, MainetConstants.FlagP);

        } else {
            dtoList = mileStoneService.milestoneDtosByProjId(projId, currentOrgid, MainetConstants.FlagP);

        }
        this.getModel().setMilestoneList(dtoList);
        return dtoList;
    }

    @RequestMapping(method = RequestMethod.POST, params = MainetConstants.WorksManagement.MILESTONES_PROGRESS)
    public ModelAndView physicalMilestoneCreate(
            @RequestParam(name = MainetConstants.WorksManagement.MILESTONE_ID, required = false) Long mileId,
            final HttpServletRequest request) {
        bindModel(request);
        MilestoneProgressModel model = this.getModel();
        final List<AttachDocs> attachDocs;
        model.setMileStoneDTO(mileStoneService.getMileStoneDetail(mileId));
        Long projId = model.getMileStoneDTO().getProjId();
        Long workId = model.getMileStoneDTO().getWorkId();
        Long orgId = UserSession.getCurrent().getOrganisation().getOrgid();
        if (workId != null) {
            attachDocs = ApplicationContextProvider.getApplicationContext().getBean(IAttachDocsService.class).findByCode(orgId,
                    workDefinationService.findAllWorkDefinitionById(workId).getWorkcode()
                            + MainetConstants.WINDOWS_SLASH + model.getMileStoneDTO().getMileId());
        } else {
            attachDocs = ApplicationContextProvider.getApplicationContext().getBean(IAttachDocsService.class).findByCode(orgId,
                    projectMasterService.getProjectMasterByProjId(projId).getProjCode() + MainetConstants.WINDOWS_SLASH
                            + model.getMileStoneDTO().getMileId());
        }
        model.setProgressList(
                mileStoneService.getMilestoneDetListByMilestoneId(model.getMileStoneDTO().getMileId(), orgId));
        for (int i = 0; i <= model.getProgressList().size() - 1; i++) {
            model.getAttachments().add(new DocumentDetailsVO());
        }
        model.setAttachDocsList(attachDocs);
        if (!attachDocs.isEmpty()) {
            FileUploadUtility.getCurrent().setFileMap(mileStoneService.getUploadedFileList(attachDocs,
                    model.getProgressList(), FileNetApplicationClient.getInstance()));
        }
        model.getdataOfUploadedImage();
        return new ModelAndView(MainetConstants.WorksManagement.PROGRESS_FORM, MainetConstants.FORM_NAME,
                this.getModel());
    }

    @RequestMapping(method = RequestMethod.POST, params = MainetConstants.WorksManagement.FILE_COUNT_UPLOAD)
    public ModelAndView fileCountUpload(final HttpServletRequest request) {
        bindModel(request);
        FileUploadUtility.getCurrent().getFileMap().entrySet();
        List<DocumentDetailsVO> attachments = new ArrayList<>();
        List<MilestoneDetDto> progress = new ArrayList<>();
        for (int i = 0; i <= this.getModel().getLength(); i++) {
            attachments.add(new DocumentDetailsVO());
            progress.add(new MilestoneDetDto());
        }
        int count = 0;
        for (MilestoneDetDto mileStoneDTO : this.getModel().getProgressList()) {
            BeanUtils.copyProperties(mileStoneDTO, progress.get(count));
            count++;
        }
        this.getModel().setAttachments(attachments);
        this.getModel().setProgressList(progress);
        this.getModel().getdataOfUploadedImage();
        return new ModelAndView(MainetConstants.WorksManagement.ADD_MILESTONES_PROGRESS, MainetConstants.FORM_NAME,
                this.getModel());
    }

    @RequestMapping(method = RequestMethod.POST, params = MainetConstants.WorksManagement.ENTRY_DELETE)
    public void doEntryDeletion(@RequestParam(name = MainetConstants.WorksManagement.ID, required = false) int id,
            final HttpServletRequest request) {
        bindModel(request);
        MilestoneProgressModel model = this.getModel();
        MilestoneDetDto detDto = model.getProgressList().get(id);
        mileStoneService.updateMilestoneDetailStatus(detDto.getMiledId(), detDto.getOrgId());
        model.getProgressList().remove(id);
        List<AttachDocs> removedDocs = new ArrayList<>();
        if ((FileUploadUtility.getCurrent().getFileMap() != null)
                && !FileUploadUtility.getCurrent().getFileMap().isEmpty()) {
            for (final Map.Entry<Long, Set<File>> entry : FileUploadUtility.getCurrent().getFileMap().entrySet()) {
                if (id == entry.getKey().intValue()) {
                    FileUploadUtility.getCurrent().getFileMap().remove((long) id);
                }
            }

        }
        for (AttachDocs attachDocs : model.getAttachDocsList()) {
            if (detDto.getPhyPercent().longValue() == attachDocs.getSerialNo().longValue()) {
                removedDocs.add(attachDocs);
            }
        }
        if (!removedDocs.isEmpty()) {
            mileStoneService.DeleteProgressDocuments(removedDocs);
        }
    }

    @RequestMapping(method = RequestMethod.POST, params = MainetConstants.WorksManagement.getUploadedImage)
    public ModelAndView getUploadedImage(final HttpServletRequest request) {
        bindModel(request);
        this.getModel().getdataOfUploadedImage();
        return new ModelAndView(MainetConstants.WorksManagement.ADD_MILESTONES_PROGRESS, MainetConstants.FORM_NAME,
                this.getModel());
    }

}
