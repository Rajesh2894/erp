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

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.abm.mainet.common.constant.MainetConstants;
import com.abm.mainet.common.exception.FrameworkException;
import com.abm.mainet.common.integration.dms.domain.AttachDocs;
import com.abm.mainet.common.integration.dms.fileUpload.FileUploadUtility;
import com.abm.mainet.common.integration.dms.service.IAttachDocsService;
import com.abm.mainet.common.integration.dms.service.IFileUploadService;
import com.abm.mainet.common.integration.dto.DocumentDetailsVO;
import com.abm.mainet.common.master.dto.ContractAgreementSummaryDTO;
import com.abm.mainet.common.master.service.DepartmentService;
import com.abm.mainet.common.master.service.IContractAgreementService;
import com.abm.mainet.common.ui.controller.AbstractFormController;
import com.abm.mainet.common.utility.ApplicationContextProvider;
import com.abm.mainet.common.utility.UserSession;
import com.abm.mainet.workManagement.ui.model.ContractTimeVariationModel;

/**
 * @author Saiprasad.Vengurlekar
 *
 */

@Controller
@RequestMapping("/ContractTimeVariation.html")
public class ContractTimeVariationController extends AbstractFormController<ContractTimeVariationModel> {

    /**
     * Used to default Contract Time Variation page
     * 
     * @param httpServletRequest
     * @return defaultResult
     * @throws Exception
     */

    @RequestMapping(method = { RequestMethod.POST, RequestMethod.GET })
    public ModelAndView index(final HttpServletRequest httpServletRequest) {
        sessionCleanup(httpServletRequest);

        ApplicationContextProvider.getApplicationContext().getBean(IFileUploadService.class).sessionCleanUpForFileUpload();
        this.getModel().setCommonHelpDocs("ContractTimeVariation.html");

        try {
            this.getModel()
                    .setContractSummaryDTOList(ApplicationContextProvider.getApplicationContext()
                            .getBean(IContractAgreementService.class)
                            .getContractAgreementSummaryData(UserSession.getCurrent().getOrganisation().getOrgid(), null, null,
                                    ApplicationContextProvider.getApplicationContext().getBean(DepartmentService.class)
                                            .getDepartmentIdByDeptCode(
                                                    MainetConstants.WorksManagement.WORKS_MANAGEMENT),
                                    null, null, null));
        } catch (Exception e) {
            throw new FrameworkException(e);
        }
        return defaultResult();
    }

    /**
     * Used to get Contract Details By contId
     * 
     * @param contId
     * @return
     * @return ContractAgreementSummaryDTO
     */
    @ResponseBody
    @RequestMapping(params = MainetConstants.WorksManagement.GETCONTRACT_DETAILS, method = RequestMethod.POST)
    public String contractDetailsBycontId(@RequestParam(MainetConstants.WorksManagement.CONTRACT_ID) Long contId) {
        ContractTimeVariationModel model = this.getModel();
        for (ContractAgreementSummaryDTO contractAgreementSummaryDTO : model.getContractSummaryDTOList()) {
            if (contractAgreementSummaryDTO.getContId() == contId.longValue()) {
                model.setContractAgreementSummaryDTO(contractAgreementSummaryDTO);
                break;
            }
        }
        final List<AttachDocs> attachDocs = ApplicationContextProvider.getApplicationContext().getBean(IAttachDocsService.class)
                .findByCode(UserSession.getCurrent().getOrganisation().getOrgid(),
                        model.getContractAgreementSummaryDTO().getContNo());
        this.getModel().setAttachDocsList(attachDocs);
        return model.getContractAgreementSummaryDTO().getContFromDate() + MainetConstants.operator.COMMA
                + model.getContractAgreementSummaryDTO().getContToDate() + MainetConstants.operator.COMMA
                + model.getContractAgreementSummaryDTO().getContp2Name();

    }

    @RequestMapping(method = RequestMethod.POST, params = MainetConstants.WorksManagement.FILE_COUNT_UPLOAD)
    public ModelAndView fileCountUpload(final HttpServletRequest request) {
        bindModel(request);
        this.getModel().getFileCountUpload().clear();
        for (final Map.Entry<Long, Set<File>> entry : FileUploadUtility.getCurrent().getFileMap().entrySet()) {
            this.getModel().getFileCountUpload().add(entry.getKey());
        }
        int fileCount = (int) FileUploadUtility.getCurrent().getFileMap().entrySet().size();
        this.getModel().getFileCountUpload().add(fileCount + 1L);
        List<DocumentDetailsVO> attachments = new ArrayList<>();
        for (int i = 0; i <= this.getModel().getAttachments().size(); i++)
            attachments.add(new DocumentDetailsVO());
        for (final Map.Entry<Long, Set<File>> entry : FileUploadUtility.getCurrent().getFileMap().entrySet()) {

            attachments.get(entry.getKey().intValue()).setDoc_DESC_ENGL(
                    this.getModel().getAttachments().get(entry.getKey().intValue()).getDoc_DESC_ENGL());
        }
        if (attachments.get(this.getModel().getAttachments().size()).getDoc_DESC_ENGL() == null)
            attachments.get(this.getModel().getAttachments().size()).setDoc_DESC_ENGL(MainetConstants.BLANK);
        else {
            DocumentDetailsVO ob = new DocumentDetailsVO();
            ob.setDoc_DESC_ENGL(MainetConstants.BLANK);
            attachments.add(ob);
        }
        this.getModel().setAttachments(attachments);
        return new ModelAndView(MainetConstants.WorksManagement.CONTRACT_TIME, MainetConstants.FORM_NAME,
                this.getModel());
    }

    /**
     * Used to Update Contract Details With updated Variation in Time
     * 
     * @param contId
     */
    @RequestMapping(params = MainetConstants.WorksManagement.UPDATE_CONTRACT_PERIOD, method = RequestMethod.POST)
    public void updateContractTimeVariation(final HttpServletRequest request) {
        bindModel(request);
        this.getModel().updateContractTimeVariation();

    }

}