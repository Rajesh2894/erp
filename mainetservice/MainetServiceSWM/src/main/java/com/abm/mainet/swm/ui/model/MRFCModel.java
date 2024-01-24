package com.abm.mainet.swm.ui.model;

import java.io.File;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.annotation.Resource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.abm.mainet.common.constant.MainetConstants;
import com.abm.mainet.common.integration.dms.domain.AttachDocs;
import com.abm.mainet.common.integration.dms.fileUpload.FileUploadUtility;
import com.abm.mainet.common.integration.dms.service.IFileUploadService;
import com.abm.mainet.common.integration.dto.DocumentDetailsVO;
import com.abm.mainet.common.integration.dto.RequestDTO;
import com.abm.mainet.common.master.dto.DesignationBean;
import com.abm.mainet.common.master.dto.TbLocationMas;
import com.abm.mainet.common.master.service.DepartmentService;
import com.abm.mainet.common.ui.model.AbstractFormModel;
import com.abm.mainet.common.utility.ApplicationSession;
import com.abm.mainet.common.utility.UserSession;
import com.abm.mainet.common.utility.Utility;
import com.abm.mainet.swm.dto.MRFEmployeeDetailDto;
import com.abm.mainet.swm.dto.MRFMasterDto;
import com.abm.mainet.swm.dto.MRFVehicleDetailDto;
import com.abm.mainet.swm.service.IMRFMasterService;
import com.abm.mainet.swm.ui.validator.MRFMasterValidator;

/**
 * @author Ajay.Kumar
 *
 */
@Component
@Scope("session")
public class MRFCModel extends AbstractFormModel {
    private static final long serialVersionUID = -4663381546157454599L;

    @Autowired
    private IMRFMasterService mRFMasterService;

    @Autowired
    private DepartmentService departmentService;

    @Resource
    private IFileUploadService fileUpload;

    private String saveMode;

    MRFMasterDto mRFMasterDto = new MRFMasterDto();

    List<MRFMasterDto> mRFMasterDtoList;

    private List<DesignationBean> designationList = new ArrayList<>();

    private List<AttachDocs> attachDocsList = new ArrayList<>();

    private List<DocumentDetailsVO> documents = new ArrayList<>();

    private List<TbLocationMas> locList = new ArrayList<>();
    private String propertyActiveStatus;

    @SuppressWarnings({ "null", "deprecation" })
    @Override
    public boolean saveForm() {
        boolean status = false;
        validateBean(mRFMasterDto, MRFMasterValidator.class);

        if (hasValidationErrors()) {
            return false;
        }
        String mode = null;
        RequestDTO requestDTO = new RequestDTO();
        requestDTO.setOrgId(UserSession.getCurrent().getOrganisation().getOrgid());
        requestDTO.setStatus(MainetConstants.FlagA);
        requestDTO.setDepartmentName(MainetConstants.SolidWasteManagement.SHORT_CODE);
        requestDTO.setUserId(UserSession.getCurrent().getEmployee().getEmpId());
        requestDTO.setDeptId(departmentService.getDepartmentIdByDeptCode(MainetConstants.SolidWasteManagement.SHORT_CODE));

        List<DocumentDetailsVO> dto = getDocuments();
        setDocuments(fileUpload.setFileUploadMethod(getDocuments()));
        int i = 0;
        for (final Map.Entry<Long, Set<File>> entry : FileUploadUtility.getCurrent().getFileMap().entrySet()) {
            getDocuments().get(i).setDoc_DESC_ENGL(dto.get(entry.getKey().intValue()).getDoc_DESC_ENGL());
            i++;
        }

        if (mRFMasterDto.getMrfId() == null) {
            mRFMasterDto.setOrgId(UserSession.getCurrent().getOrganisation().getOrgid());
            mRFMasterDto.setCreatedBy(UserSession.getCurrent().getEmployee().getEmpId());
            mRFMasterDto.setCreatedDate(new Date());
            mRFMasterDto.setLgIpMac(Utility.getMacAddress());
            status = true;
            mode = "save";
        } else {
            mRFMasterDto.setUpdatedBy(UserSession.getCurrent().getEmployee().getEmpId());
            mRFMasterDto.setUpdatedDate(new Date());
            mRFMasterDto.setLgIpMacUpd(Utility.getMacAddress());
            status = true;
            mode = "update";
        }
        for (MRFVehicleDetailDto mRFVehicleDetailDto : mRFMasterDto.getTbSwMrfVechicleDet()) {
            if (mRFVehicleDetailDto.getMrfvId() == null) {
                mRFVehicleDetailDto.setOrgId(UserSession.getCurrent().getOrganisation().getOrgid());
                mRFVehicleDetailDto.setCreatedBy(UserSession.getCurrent().getEmployee().getEmpId());
                mRFVehicleDetailDto.setCreatedDate(new Date());
                mRFVehicleDetailDto.setLgIpMac(Utility.getMacAddress());
                mRFVehicleDetailDto.setMrfVEId(mRFMasterDto);
                status = true;
                mode = "save";
            } else {
                mRFVehicleDetailDto.setUpdatedBy(UserSession.getCurrent().getEmployee().getEmpId());
                mRFVehicleDetailDto.setUpdatedDate(new Date());
                mRFVehicleDetailDto.setLgIpMacUpd(Utility.getMacAddress());
                mRFVehicleDetailDto.setMrfVEId(mRFMasterDto);
                status = true;
                mode = "update";
            }
        }
        for (MRFEmployeeDetailDto MRFEmployeeDetailDto : mRFMasterDto.getTbSwMrfEmployeeDet()) {
            if (MRFEmployeeDetailDto.getMrfEId() == null) {
                MRFEmployeeDetailDto.setOrgId(UserSession.getCurrent().getOrganisation().getOrgid());
                MRFEmployeeDetailDto.setCreatedBy(UserSession.getCurrent().getEmployee().getEmpId());
                MRFEmployeeDetailDto.setCreatedDate(new Date());
                MRFEmployeeDetailDto.setLgIpMac(Utility.getMacAddress());
                MRFEmployeeDetailDto.setMrfEMPId(mRFMasterDto);
                status = true;
                mode = "save";
            } else {
                MRFEmployeeDetailDto.setUpdatedBy(UserSession.getCurrent().getEmployee().getEmpId());
                MRFEmployeeDetailDto.setUpdatedDate(new Date());
                MRFEmployeeDetailDto.setLgIpMacUpd(Utility.getMacAddress());
                MRFEmployeeDetailDto.setMrfEMPId(mRFMasterDto);
                status = true;
                mode = "update";
            }
        }
        if (status && mode.equals("save")) {
            MRFMasterDto mrfDto = mRFMasterService.save(mRFMasterDto);
            requestDTO.setIdfId("SWM_MRF_" + mrfDto.getMrfId().toString());
            fileUpload.doMasterFileUpload(getDocuments(), requestDTO);
            setSuccessMessage(ApplicationSession.getInstance().getMessage("material.site.save"));
        } else if (status && mode.equals("update")) {
            MRFMasterDto mrfDto = mRFMasterService.update(mRFMasterDto);
            if (attachDocsList.isEmpty()) {
                requestDTO.setIdfId("SWM_MRF_" + mrfDto.getMrfId().toString());
                fileUpload.doMasterFileUpload(getDocuments(), requestDTO);
            }
            setSuccessMessage(ApplicationSession.getInstance().getMessage("material.site.edit"));
            status = true;
        } else {
            addValidationError(ApplicationSession.getInstance().getMessage("material.site.validation.duplicate"));
            status = false;
        }
        return status;
    }

    public String getSaveMode() {
        return saveMode;
    }

    public void setSaveMode(String saveMode) {
        this.saveMode = saveMode;
    }

    public MRFMasterDto getmRFMasterDto() {
        return mRFMasterDto;
    }

    public List<DocumentDetailsVO> getDocuments() {
        return documents;
    }

    public void setDocuments(List<DocumentDetailsVO> documents) {
        this.documents = documents;
    }

    public void setmRFMasterDto(MRFMasterDto mRFMasterDto) {
        this.mRFMasterDto = mRFMasterDto;
    }

    public List<MRFMasterDto> getmRFMasterDtoList() {
        return mRFMasterDtoList;
    }

    public void setmRFMasterDtoList(List<MRFMasterDto> mRFMasterDtoList) {
        this.mRFMasterDtoList = mRFMasterDtoList;
    }

    public List<TbLocationMas> getLocList() {
        return locList;
    }

    public List<AttachDocs> getAttachDocsList() {
        return attachDocsList;
    }

    public void setAttachDocsList(List<AttachDocs> attachDocsList) {
        this.attachDocsList = attachDocsList;
    }

    public void setLocList(List<TbLocationMas> locList) {
        this.locList = locList;
    }

    public List<DesignationBean> getDesignationList() {
        return designationList;
    }

    public void setDesignationList(List<DesignationBean> designationList) {
        this.designationList = designationList;
    }
    public String getPropertyActiveStatus() {
        return propertyActiveStatus;
    }

    public void setPropertyActiveStatus(String propertyActiveStatus) {
        this.propertyActiveStatus = propertyActiveStatus;
    }

}
