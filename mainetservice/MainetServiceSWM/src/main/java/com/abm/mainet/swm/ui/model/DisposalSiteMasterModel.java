package com.abm.mainet.swm.ui.model;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.abm.mainet.common.constant.MainetConstants;
import com.abm.mainet.common.domain.Employee;
import com.abm.mainet.common.integration.dms.domain.AttachDocs;
import com.abm.mainet.common.integration.dms.service.IFileUploadService;
import com.abm.mainet.common.integration.dto.DocumentDetailsVO;
import com.abm.mainet.common.integration.dto.RequestDTO;
import com.abm.mainet.common.master.dto.TbLocationMas;
import com.abm.mainet.common.master.service.DepartmentService;
import com.abm.mainet.common.ui.model.AbstractFormModel;
import com.abm.mainet.common.utility.ApplicationSession;
import com.abm.mainet.common.utility.CommonMasterUtility;
import com.abm.mainet.common.utility.UserSession;
import com.abm.mainet.common.utility.Utility;
import com.abm.mainet.swm.dto.DesposalDetailDTO;
import com.abm.mainet.swm.dto.DisposalMasterDTO;
import com.abm.mainet.swm.service.IDisposalMasterService;

/**
 * @author Ajay.Kumar
 *
 */
@Component
@Scope("session")
public class DisposalSiteMasterModel extends AbstractFormModel {
    private static final long serialVersionUID = -7405314460750591462L;

    @Autowired
    private IDisposalMasterService disposalMasterService;

    @Autowired
    private DepartmentService departmentService;

    @Resource
    private IFileUploadService fileUpload;

    DisposalMasterDTO disposalMasterDTO = new DisposalMasterDTO();
    List<DisposalMasterDTO> disposalMasterList;
    private List<DocumentDetailsVO> documents = new ArrayList<>();
    private List<AttachDocs> attachDocsList = new ArrayList<>();
    private List<TbLocationMas> locList = new ArrayList<>();
    private Long locationCat;
    List<Employee> employeList = new ArrayList<>();
    private String saveMode;

    @Override
    public boolean saveForm() {
        boolean status = false;
        if (hasValidationErrors()) {
            return false;
        } else {

            RequestDTO requestDTO = new RequestDTO();
            requestDTO.setOrgId(
                    UserSession.getCurrent().getOrganisation().getOrgid());
            requestDTO.setStatus(MainetConstants.FlagA);
            // requestDTO.setIdfId(getDisposalMasterDTO().getDeId().toString());
            requestDTO.setDepartmentName(MainetConstants.SolidWasteManagement.SHORT_CODE);
            requestDTO.setUserId(UserSession.getCurrent().getEmployee().getEmpId());
            requestDTO.setDeptId(departmentService.getDepartmentIdByDeptCode(MainetConstants.SolidWasteManagement.SHORT_CODE));
            List<DocumentDetailsVO> dto = getDocuments();
            setDocuments(fileUpload.setFileUploadMethod(getDocuments()));

            List<DesposalDetailDTO> reomveList = new ArrayList<>();
            List<DesposalDetailDTO> desposalDetList = disposalMasterDTO
                    .getTbSwDesposalDets();
            desposalDetList.forEach(det -> {
                if (det.getDedId() == null) {
                    if (det.getDeActive() != null) {

                        det.setOrgid(UserSession.getCurrent().getOrganisation()
                                .getOrgid());
                        det.setCreatedBy(
                                UserSession.getCurrent().getEmployee().getEmpId());
                        det.setCreatedDate(new Date());
                        det.setLgIpMac(Utility.getMacAddress());
                        det.setTbSwDesposalMast(disposalMasterDTO);

                    } else {
                        reomveList.add(det);
                    }

                } else {

                    det.setUpdatedBy(
                            UserSession.getCurrent().getEmployee().getEmpId());
                    det.setUpdatedDate(new Date());
                    det.setLgIpMacUpd(Utility.getMacAddress());
                    det.setTbSwDesposalMast(disposalMasterDTO);
                    if (null == det.getDeActive()) {
                        det.setDeActive(MainetConstants.Common_Constant.NO);
                    }
                    /* disposalMasterDTO.setDeActive(MainetConstants.IsDeleted.NOT_DELETE); */

                }
            });

            desposalDetList.removeAll(reomveList);

            if (disposalMasterDTO.getDeId() == null) {
                disposalMasterDTO.setOrgid(
                        UserSession.getCurrent().getOrganisation().getOrgid());
                disposalMasterDTO.setCreatedBy(
                        UserSession.getCurrent().getEmployee().getEmpId());
                disposalMasterDTO.setCreatedDate(new Date());
                disposalMasterDTO.setLgIpMac(Utility.getMacAddress());
                disposalMasterDTO.setDeActive(MainetConstants.FlagY);
                DisposalMasterDTO disp;

                status = disposalMasterService.validateDisposalMaster(disposalMasterDTO);

                if (status) {
                    disp = disposalMasterService.saveDisposalSite(disposalMasterDTO);
                    status = true;
                } else {
                    addValidationError(ApplicationSession.getInstance().getMessage("disposal.site.validation.duplicate"));
                    status = false;
                    disp = null;
                }

                // DisposalMasterDTO disp = disposalMasterService.saveDisposalSite(disposalMasterDTO);

                if (attachDocsList.isEmpty() && disp != null) {
                    requestDTO.setIdfId(MainetConstants.SolidWasteManagement.DISPOSAL_SITE + disp.getDeId());
                    fileUpload.doMasterFileUpload(getDocuments(), requestDTO);
                }
                setSuccessMessage(ApplicationSession.getInstance()
                        .getMessage("disposal.site.save"));

            } else {

                disposalMasterDTO.setUpdatedBy(
                        UserSession.getCurrent().getEmployee().getEmpId());
                disposalMasterDTO.setUpdatedDate(new Date());
                disposalMasterDTO.setLgIpMacUpd(Utility.getMacAddress());
                DisposalMasterDTO disp;

                status = disposalMasterService.validateDisposalMaster(disposalMasterDTO);

                if (status) {
                    disp = disposalMasterService.updateDisposalSite(disposalMasterDTO);
                    status = true;
                } else {
                    addValidationError(ApplicationSession.getInstance().getMessage("disposal.site.validation.duplicate"));
                    status = false;
                    disp = null;
                }

                if (attachDocsList.isEmpty() && disp != null) {
                    requestDTO.setIdfId(MainetConstants.SolidWasteManagement.DISPOSAL_SITE + disp.getDeId());
                    fileUpload.doMasterFileUpload(getDocuments(), requestDTO);
                }
                setSuccessMessage(ApplicationSession.getInstance()
                        .getMessage("disposal.site.edit"));

            }
        }
        return status;
    }

    public String getSaveMode() {
        return saveMode;
    }

    public void setSaveMode(String saveMode) {
        this.saveMode = saveMode;
    }

    public DisposalMasterDTO getDisposalMasterDTO() {
        return disposalMasterDTO;
    }

    public List<DocumentDetailsVO> getDocuments() {
        return documents;
    }

    public void setDocuments(List<DocumentDetailsVO> documents) {
        this.documents = documents;
    }

    public List<AttachDocs> getAttachDocsList() {
        return attachDocsList;
    }

    public void setAttachDocsList(List<AttachDocs> attachDocsList) {
        this.attachDocsList = attachDocsList;
    }

    public void setDisposalMasterDTO(DisposalMasterDTO disposalMasterDTO) {
        this.disposalMasterDTO = disposalMasterDTO;
    }

    public List<DisposalMasterDTO> getDisposalMasterList() {
        return disposalMasterList;
    }

    public List<TbLocationMas> getLocList() {
        return locList;
    }

    public void setLocList(List<TbLocationMas> locList) {
        this.locList = locList;
    }

    public void setDisposalMasterList(
            List<DisposalMasterDTO> disposalMasterList) {
        this.disposalMasterList = disposalMasterList;
    }

    public Long getLocationCat() {
        return CommonMasterUtility.lookUpIdByLookUpCodeAndPrefix("GCC", "LCT",
                UserSession.getCurrent().getOrganisation().getOrgid());
    }

    public void setLocationCat(Long locationCat) {
        this.locationCat = locationCat;
    }

    public List<Employee> getEmployeList() {
        return employeList;
    }

    public void setEmployeList(List<Employee> employeList) {
        this.employeList = employeList;
    }

}
