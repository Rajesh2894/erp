package com.abm.mainet.swm.ui.model;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.abm.mainet.common.dto.TbAcVendormaster;
import com.abm.mainet.common.master.dto.EmployeeBean;
import com.abm.mainet.common.ui.model.AbstractFormModel;
import com.abm.mainet.common.utility.UserSession;
import com.abm.mainet.common.utility.Utility;
import com.abm.mainet.swm.dto.MRFMasterDto;
import com.abm.mainet.swm.dto.WastageSegregationDTO;
import com.abm.mainet.swm.service.IWastageSegregationService;

/**
 * @author Ajay.Kumar
 *
 */
@Component
@Scope("session")
public class SegregationModel extends AbstractFormModel {

    private static final long serialVersionUID = 1L;

    @Autowired
    private IWastageSegregationService wastageSegregationService;

    WastageSegregationDTO segregationDto;

    private String saveMode;
    private List<MRFMasterDto> mrfMasterList = new ArrayList<>();

    private List<WastageSegregationDTO> tbSwWastesegDets = new ArrayList<>();

    List<EmployeeBean> employeeList = new ArrayList<>();
    List<TbAcVendormaster> vendorList = new ArrayList<>();

    @Override
    public boolean saveForm() {

        segregationDto.setOrgid(UserSession.getCurrent().getOrganisation().getOrgid());

        segregationDto.getTbSwWastesegDets().forEach(segregationDet -> {
            segregationDet.setTbSwWasteseg(segregationDto);

            if (segregationDet.getGrdId() == null) {
                segregationDet.setOrgid(UserSession.getCurrent().getOrganisation().getOrgid());
                segregationDet.setCreatedBy(UserSession.getCurrent().getEmployee().getEmpId());
                segregationDet.setCreatedDate(new Date());
                segregationDet.setLgIpMac(Utility.getMacAddress());

            } else {

                segregationDet.setUpdatedBy(UserSession.getCurrent().getEmployee().getEmpId());
                segregationDet.setUpdatedDate(new Date());
                segregationDet.setLgIpMacUpd(Utility.getMacAddress());
            }
        });

        if (segregationDto.getGrId() == null) {
            segregationDto.setCreatedBy(UserSession.getCurrent().getEmployee().getEmpId());
            segregationDto.setCreatedDate(new Date());
            segregationDto.setLgIpMac(Utility.getMacAddress());
            wastageSegregationService.save(segregationDto);
            this.setSuccessMessage(getAppSession().getMessage("swm.saveSegregation"));

        } else {
            segregationDto.setUpdatedBy(UserSession.getCurrent().getEmployee().getEmpId());
            segregationDto.setUpdatedDate(new Date());
            segregationDto.setLgIpMacUpd(Utility.getMacAddress());
            wastageSegregationService.update(segregationDto);
            this.setSuccessMessage(getAppSession().getMessage("swm.editSegregation"));
        }

        return true;

    }

    @Override
    protected final String findPropertyPathPrefix(final String parentCode) {
        switch (parentCode) {

        case "WTY":
            return "segregationDto.tbSwWastesegDets[0].codWast";
        default:
            return null;

        }
    }

    public String getSaveMode() {
        return saveMode;
    }

    public void setSaveMode(String saveMode) {
        this.saveMode = saveMode;
    }

    public WastageSegregationDTO getSegregationDto() {
        return segregationDto;
    }

    public void setSegregationDto(WastageSegregationDTO segregationDto) {
        this.segregationDto = segregationDto;
    }

    public List<TbAcVendormaster> getVendorList() {
        return vendorList;
    }

    public void setVendorList(List<TbAcVendormaster> vendorList) {
        this.vendorList = vendorList;
    }

    public List<MRFMasterDto> getMrfMasterList() {
        return mrfMasterList;
    }

    public void setMrfMasterList(List<MRFMasterDto> mrfMasterList) {
        this.mrfMasterList = mrfMasterList;
    }

    public List<WastageSegregationDTO> getTbSwWastesegDets() {
        return tbSwWastesegDets;
    }

    public void setTbSwWastesegDets(List<WastageSegregationDTO> tbSwWastesegDets) {
        this.tbSwWastesegDets = tbSwWastesegDets;
    }

    public List<EmployeeBean> getEmployeeList() {
        return employeeList;
    }

    public void setEmployeeList(List<EmployeeBean> employeeList) {
        this.employeeList = employeeList;
    }

}
