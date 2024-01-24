/**
 * 
 */
package com.abm.mainet.workManagement.ui.controller;

import java.io.File;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.LinkedHashMap;
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
import com.abm.mainet.common.integration.dto.DocumentDetailsVO;
import com.abm.mainet.common.master.dto.EmployeeBean;
import com.abm.mainet.common.master.ui.validator.FileUploadServiceValidator;
import com.abm.mainet.common.ui.controller.AbstractFormController;
import com.abm.mainet.common.utility.ApplicationContextProvider;
import com.abm.mainet.common.utility.UserSession;
import com.abm.mainet.workManagement.dto.MeasurementBookMasterDto;
import com.abm.mainet.workManagement.dto.WorkOrderDto;
import com.abm.mainet.workManagement.service.MeasurementBookService;
import com.abm.mainet.workManagement.service.WorkOrderService;
import com.abm.mainet.workManagement.ui.model.LegacyMeasurementBookModel;

/**
 * @author Saiprasad.Vengurlekar
 *
 */

@Controller
@RequestMapping("/LegacyMeasurementBook.html")
public class LegacyMeasurementBookController extends AbstractFormController<LegacyMeasurementBookModel> {
    @Autowired
    MeasurementBookService mbService;

    @Autowired
    WorkOrderService workOrderService;

    /**
     * Used to default LegacyMeasurementBook Summary page
     * 
     * @param httpServletRequest
     * @return defaultResult
     * @throws Exception
     */

    @RequestMapping(method = { RequestMethod.POST, RequestMethod.GET })
    public ModelAndView index(final HttpServletRequest httpServletRequest) throws Exception {
        sessionCleanup(httpServletRequest);
        Long orgId = UserSession.getCurrent().getOrganisation().getOrgid();
        LegacyMeasurementBookModel model = this.getModel();
        FileUploadServiceValidator.getCurrent().sessionCleanUpForFileUpload();
        this.getModel().setCommonHelpDocs("LegacyMeasurementBook.html");
        model.setWorkOrderDtoList(workOrderService.getAllLegacyWorkOrder(MainetConstants.FlagY, orgId));
        return defaultResult();
    }

    /**
     * this method is used to Create new Measurement Book details
     * 
     * @param request
     * @return create Measurement Book details form.
     */
    @RequestMapping(params = MainetConstants.WorksManagement.CreateMb, method = RequestMethod.POST)
    public ModelAndView createMb(final HttpServletRequest request) {
        LegacyMeasurementBookModel model = this.getModel();
        model.setSaveMode(MainetConstants.FlagC);
        return new ModelAndView("LegacyMeasurementBookForm", MainetConstants.FORM_NAME, this.getModel());
    }

    /**
     * This Method is used for filter Measurement Book Data on search criteria
     * 
     * @param workId
     * @return workOrder Details
     */
    @RequestMapping(params = MainetConstants.WorksManagement.FilterMeasurementBookData, method = RequestMethod.POST)
    public @ResponseBody List<WorkOrderDto> filterMeasurementBookData(final HttpServletRequest request,
            @RequestParam("workOrderId") final Long workOrderId) {
        List<WorkOrderDto> workOrderDtoList = new ArrayList<>();
        String workState = null;
        List<MeasurementBookMasterDto> bookMasDtos = mbService.getAllMbDeatilsBySearch(workOrderId, null, null, null,
                null, UserSession.getCurrent().getOrganisation().getOrgid());
        for (WorkOrderDto workOrderDto : this.getModel().getWorkOrderDtoList()) {
            if (workOrderDto.getWorkId().longValue() == workOrderId.longValue()) {
                workOrderDto.setContractNo(workOrderDto.getContractMastDTO().getContNo());
                List<Long> ids = new ArrayList<>();
                for (String id : workOrderDto.getMultiSelect()) {
                    ids.add(Long.parseLong(id));
                }
                if (!ids.isEmpty() && ids != null) {
                    String employee = MainetConstants.BLANK;
                    List<EmployeeBean> empList = mbService.getAssignedEmpDetailByEmpIdList(ids,
                            UserSession.getCurrent().getOrganisation().getOrgid());
                    for (EmployeeBean employeeBean : empList) {
                        employee += employeeBean.getEmpname() + MainetConstants.WHITE_SPACE + employeeBean.getEmpmname()
                                + MainetConstants.WHITE_SPACE + employeeBean.getEmplname()
                                + MainetConstants.WorksManagement.FW_ARROW + MainetConstants.WHITE_SPACE
                                + employeeBean.getDesignName() + MainetConstants.WHITE_SPACE
                                + MainetConstants.operator.COMMA + MainetConstants.WHITE_SPACE;
                    }

                    workOrderDto.setWorkAssigneeName(employee);
                }
                this.getModel().setWorkOrderDto(workOrderDto);
                break;
            }
        }
        if (!bookMasDtos.isEmpty()) {
            for (MeasurementBookMasterDto bookMasterDto : bookMasDtos) {
                WorkOrderDto orderDto = workOrderService.getWorkOredrByOrderId(bookMasterDto.getWorkOrId());
                if (bookMasterDto.getMbStatus().equals(MainetConstants.FlagP)) {
                    orderDto.setMbStatus(MainetConstants.TASK_STATUS_PENDING);
                } else if (bookMasterDto.getMbStatus().equals(MainetConstants.FlagD)) {
                    orderDto.setMbStatus(MainetConstants.TASK_STATUS_DRAFT);
                } else if (bookMasterDto.getMbStatus().equals(MainetConstants.FlagA)) {
                    orderDto.setMbStatus(MainetConstants.TASK_STATUS_APPROVED);
                } else if (bookMasterDto.getMbStatus().equals(MainetConstants.FlagR)) {
                    orderDto.setMbStatus(MainetConstants.TASK_STATUS_REJECTED);
                } else if (bookMasterDto.getMbStatus().equals(MainetConstants.WorksManagement.RATE_TYPE)) {
                    orderDto.setMbStatus(MainetConstants.WorksManagement.RABILL);
                }
                if (bookMasterDto.getMbTotalAmt() == null) {
                    orderDto.setMbTotalAmt(new BigDecimal(MainetConstants.ZERO));
                } else {
                    orderDto.setMbTotalAmt(bookMasterDto.getMbTotalAmt().setScale(2, BigDecimal.ROUND_UP));
                }
                orderDto.setMbId(bookMasterDto.getWorkMbId());
                orderDto.setMbNo(bookMasterDto.getWorkMbNo());
                if (workOrderId != null) {
                    orderDto.setWorkStatus(workState);
                }
                workOrderDtoList.add(orderDto);
            }
        }
        return workOrderDtoList;
    }

    @RequestMapping(method = RequestMethod.POST, params = MainetConstants.WorksManagement.FILE_COUNT_UPLOAD)
    public ModelAndView fileCountUpload(final HttpServletRequest request) {
        bindModel(request);
        FileUploadUtility.getCurrent().getFileMap().entrySet();
        List<DocumentDetailsVO> attachments = new ArrayList<>();
        List<MeasurementBookMasterDto> progress = new ArrayList<>();
        for (int i = 0; i <= this.getModel().getLength(); i++) {
            attachments.add(new DocumentDetailsVO());
            progress.add(new MeasurementBookMasterDto());
        }
        int count = 0;
        for (MeasurementBookMasterDto dto : this.getModel().getMbList()) {
            if (dto.getWorkMbTakenDate() != null) {
                BeanUtils.copyProperties(dto, progress.get(count));
                count++;
            }
        }
        this.getModel().setAttachments(attachments);
        this.getModel().setMbList(progress);
        Long count1 = 0l;
        Map<Long, Set<File>> fileMap1 = new LinkedHashMap<>();
        for (final Map.Entry<Long, Set<File>> entry : FileUploadUtility.getCurrent().getFileMap().entrySet()) {
            final List<File> list = new ArrayList<>(entry.getValue());
            if (!list.isEmpty()) {
                fileMap1.put(count1, entry.getValue());
                count1++;
            }
        }
        FileUploadUtility.getCurrent().setFileMap(fileMap1);
        return new ModelAndView("AddLegacyData", MainetConstants.FORM_NAME, this.getModel());
    }

    @RequestMapping(params = MainetConstants.WorksManagement.EditMb, method = { RequestMethod.POST, RequestMethod.GET })
    public ModelAndView editLegacyForm(final HttpServletRequest request,
            @RequestParam(name = MainetConstants.WorksManagement.MODE) final String mode,
            @RequestParam(MainetConstants.WorksManagement.WORK_ID) final Long workId) {
        bindModel(request);
        LegacyMeasurementBookModel model = this.getModel();
        MeasurementBookMasterDto mbMasDto = null;
        model.setWorkOrderDtoList(workOrderService.getAllLegacyWorkOrder(MainetConstants.FlagY,
                UserSession.getCurrent().getOrganisation().getOrgid()));
        model.setSaveMode(mode);
        mbMasDto = mbService.getMBById(workId);
        model.setMbMasDto(mbMasDto);
        model.getMbList().add(mbMasDto);
        model.setWorkOrderDto(workOrderService.getWorkOredrByOrderId(mbMasDto.getWorkOrId()));
        final List<AttachDocs> attachDocs = ApplicationContextProvider.getApplicationContext().getBean(IAttachDocsService.class)
                .findByCode(UserSession.getCurrent().getOrganisation().getOrgid(), mbMasDto.getWorkMbNo());
        if (!attachDocs.isEmpty()) {
            if (mode.equals(MainetConstants.FlagE))
                FileUploadUtility.getCurrent()
                        .setFileMap(model.getUploadedFileList(attachDocs, FileNetApplicationClient.getInstance()));
            model.setAttachDocsList(attachDocs);
        }
        return new ModelAndView("LegacyMeasurementBookForm", MainetConstants.FORM_NAME, this.getModel());

    }

    @RequestMapping(method = RequestMethod.POST, params = MainetConstants.WorksManagement.ENTRY_DELETE)
    public void doEntryDeletion(@RequestParam(name = MainetConstants.WorksManagement.ID, required = false) int id,
            final HttpServletRequest request) {
        bindModel(request);
        this.getModel().getMbList().remove(id);
        if ((FileUploadUtility.getCurrent().getFileMap() != null)
                && !FileUploadUtility.getCurrent().getFileMap().isEmpty()) {

            for (final Map.Entry<Long, Set<File>> entry : FileUploadUtility.getCurrent().getFileMap().entrySet()) {
                if (id == entry.getKey().intValue()) {
                    entry.getValue().clear();
                }

            }
            Long count1 = 0l;
            Map<Long, Set<File>> fileMap1 = new LinkedHashMap<>();
            for (final Map.Entry<Long, Set<File>> entry : FileUploadUtility.getCurrent().getFileMap().entrySet()) {
                final List<File> list = new ArrayList<>(entry.getValue());
                if (!list.isEmpty()) {
                    fileMap1.put(count1, entry.getValue());
                    count1++;
                }
            }
            FileUploadUtility.getCurrent().setFileMap(fileMap1);

        }

    }

    @ResponseBody
    @RequestMapping(params = MainetConstants.WorksManagement.updateRaBillStatus, method = RequestMethod.POST)
    public ModelAndView updateRABillStatus(@RequestParam(MainetConstants.WorksManagement.WORK_ID) final Long workId,
            @RequestParam(MainetConstants.WorksManagement.MODE) final String mode) {
        MeasurementBookMasterDto mbMasDto = mbService.getMBById(workId);
        mbService.updateMeasureMentMode(mbMasDto.getWorkMbId(), MainetConstants.WorksManagement.RATE_TYPE);
        return defaultResult();
    }

}
