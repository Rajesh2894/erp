package com.abm.mainet.swm.ui.model;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.abm.mainet.common.ui.model.AbstractFormModel;
import com.abm.mainet.common.utility.ApplicationSession;
import com.abm.mainet.common.utility.UserSession;
import com.abm.mainet.common.utility.Utility;
import com.abm.mainet.swm.dto.BeatMasterDTO;
import com.abm.mainet.swm.dto.SanitationStaffTargetDTO;
import com.abm.mainet.swm.dto.SanitationStaffTargetDetDTO;
import com.abm.mainet.swm.dto.VehicleMasterDTO;
import com.abm.mainet.swm.service.ISanitationStaffTargetService;
import com.abm.mainet.swm.ui.validator.SanitationStaffTargetValidator;

/**
 * @author Ajay.Kumar
 *
 */
@Component
@Scope("session")
public class SanitationStaffTargetModel extends AbstractFormModel {

    private static final long serialVersionUID = 1L;

    @Autowired
    private ISanitationStaffTargetService sanitationStaffTargetService;

    private String saveMode;
    private SanitationStaffTargetDTO sanitationStaffTargetDto;
    private Long targetType;
    private String srNo;

    private List<SanitationStaffTargetDTO> sanitationStaffTargetDets = new ArrayList<>();
    private List<BeatMasterDTO> routelist = new ArrayList<>();
    private List<VehicleMasterDTO> vehicleMasterList = new ArrayList<>();
    private SanitationStaffTargetDetDTO sanitationStaffTargetDET = new SanitationStaffTargetDetDTO();
    private static boolean status;

    @SuppressWarnings("deprecation")
    @Override
    public boolean saveForm() {
        status = true;
        sanitationStaffTargetDto.setTargetDto(sanitationStaffTargetDets);
        validateBean(sanitationStaffTargetDto, SanitationStaffTargetValidator.class);

        if (hasValidationErrors()) {
            return false;
        }
        sanitationStaffTargetDto.setOrgid(UserSession.getCurrent().getOrganisation().getOrgid());

        sanitationStaffTargetDto.getSanitationStaffTargetDet().forEach(santargetDet -> {
            santargetDet.setSanitationStaffTarget(sanitationStaffTargetDto);

            if (santargetDet.getSanId() == null) {
                santargetDet.setOrgid(UserSession.getCurrent().getOrganisation().getOrgid());
                santargetDet.setCreatedBy(UserSession.getCurrent().getEmployee().getEmpId());
                santargetDet.setCreatedDate(new Date());
                santargetDet.setLgIpMac(Utility.getMacAddress());
            } else {
                santargetDet.setUpdatedBy(UserSession.getCurrent().getEmployee().getEmpId());
                santargetDet.setUpdatedDate(new Date());
                santargetDet.setLgIpMacUpd(Utility.getMacAddress());
            }
        });

        if (sanitationStaffTargetDto.getSanId() == null) {
            sanitationStaffTargetDto.setCreatedBy(UserSession.getCurrent().getEmployee().getEmpId());
            sanitationStaffTargetDto.setCreatedDate(new Date());
            sanitationStaffTargetDto.setLgIpMac(Utility.getMacAddress());
            sanitationStaffTargetDto.getSanitationStaffTargetDet().forEach(tar -> {
                // status = sanitationStaffTargetService.validateVehicleTarget(tar.getVehicleId(), tar.getRoId(),
                // sanitationStaffTargetDto.getSanTgfromdt(), sanitationStaffTargetDto.getSanTgtodt(),
                // UserSession.getCurrent().getOrganisation().getOrgid());
                /*
                 * if(status == false) {
                 * addValidationError(ApplicationSession.getInstance().getMessage("Already Have Same Target Period For Vehicle"));
                 * }
                 */
            });
            /* if(status==true) { */
            sanitationStaffTargetService.save(sanitationStaffTargetDto);
            /* } */
            this.setSuccessMessage(getAppSession().getMessage("swm.target.save"));
        } else {
            sanitationStaffTargetDto.setUpdatedBy(UserSession.getCurrent().getEmployee().getEmpId());
            sanitationStaffTargetDto.setUpdatedDate(new Date());
            sanitationStaffTargetDto.setLgIpMacUpd(Utility.getMacAddress());
            sanitationStaffTargetDto.getSanitationStaffTargetDet().forEach(tar -> {
                if (tar.getSandId() == sanitationStaffTargetDET.getSandId()) {
                    tar.setSandVolume(sanitationStaffTargetDET.getSandVolume());
                    tar.setVehicleId(sanitationStaffTargetDET.getVehicleId());
                    tar.setRoId(sanitationStaffTargetDET.getRoId());
                    status = sanitationStaffTargetService.validateVehicleTarget(tar.getVehicleId(), tar.getRoId(),
                            tar.getSandId(), sanitationStaffTargetDto.getSanTgfromdt(), sanitationStaffTargetDto.getSanTgtodt(),
                            UserSession.getCurrent().getOrganisation().getOrgid());
                    if (status == false) {
                        addValidationError(ApplicationSession.getInstance().getMessage("swm.vehicle.target.validation"));
                    }
                }
            });
            if (status == true) {
                sanitationStaffTargetService.update(sanitationStaffTargetDto);
            }
            this.setSuccessMessage(getAppSession().getMessage("swm.target.edit"));
        }

        return status;

    }

    public Long getTargetType() {
        return targetType;
    }

    public void setTargetType(long targetTyp) {
        this.targetType = targetTyp;
    }

    public ISanitationStaffTargetService getSanitationStaffTargetService() {
        return sanitationStaffTargetService;
    }

    public void setSanitationStaffTargetService(ISanitationStaffTargetService sanitationStaffTargetService) {
        this.sanitationStaffTargetService = sanitationStaffTargetService;
    }

    public String getSaveMode() {
        return saveMode;
    }

    public void setSaveMode(String saveMode) {
        this.saveMode = saveMode;
    }

    public SanitationStaffTargetDTO getSanitationStaffTargetDto() {
        return sanitationStaffTargetDto;
    }

    public void setSanitationStaffTargetDto(SanitationStaffTargetDTO sanitationStaffTargetDto) {
        this.sanitationStaffTargetDto = sanitationStaffTargetDto;
    }

    public List<SanitationStaffTargetDTO> getSanitationStaffTargetDets() {
        return sanitationStaffTargetDets;
    }

    public void setSanitationStaffTargetDets(List<SanitationStaffTargetDTO> sanitationStaffTargetDets) {
        this.sanitationStaffTargetDets = sanitationStaffTargetDets;
    }

    public List<BeatMasterDTO> getRoutelist() {
        return routelist;
    }

    public void setRoutelist(List<BeatMasterDTO> routelist) {
        this.routelist = routelist;
    }

    public List<VehicleMasterDTO> getVehicleMasterList() {
        return vehicleMasterList;
    }

    public void setVehicleMasterList(List<VehicleMasterDTO> vehicleMasterList) {
        this.vehicleMasterList = vehicleMasterList;
    }

    public String getSrNo() {
        return srNo;
    }

    public void setSrNo(String srNo) {
        this.srNo = srNo;
    }

    public SanitationStaffTargetDetDTO getSanitationStaffTargetDET() {
        return sanitationStaffTargetDET;
    }

    public void setSanitationStaffTargetDET(SanitationStaffTargetDetDTO sanitationStaffTargetDET) {
        this.sanitationStaffTargetDET = sanitationStaffTargetDET;
    }

    public static boolean isStatus() {
        return status;
    }

    public static void setStatus(boolean status) {
        SanitationStaffTargetModel.status = status;
    }

}
