package com.abm.mainet.swm.ui.model;

import java.io.File;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
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
import com.abm.mainet.common.master.dto.EmployeeBean;
import com.abm.mainet.common.ui.model.AbstractFormModel;
import com.abm.mainet.common.utility.UserSession;
import com.abm.mainet.swm.dto.BeatMasterDTO;
import com.abm.mainet.swm.dto.MRFMasterDto;
import com.abm.mainet.swm.dto.TripSheetDTO;
import com.abm.mainet.swm.dto.VehicleMasterDTO;
import com.abm.mainet.swm.service.ITripSheetService;
import com.abm.mainet.swm.ui.validator.TripSheetMasterValidator;

/**
 * @author Ajay.Kumar
 *
 */
@Component
@Scope("session")
public class TripSheetMasterModel extends AbstractFormModel {

    private static final long serialVersionUID = 1L;

    @Autowired
    private ITripSheetService tripSheetService;
    @Resource
    private IFileUploadService fileUpload;

    private TripSheetDTO tripSheetDto;
    private String saveMode;

    private List<TripSheetDTO> tripSheetDtos = new ArrayList<>();

    private List<VehicleMasterDTO> vehicleMasterList = new ArrayList<>();

    private List<BeatMasterDTO> routelist = new ArrayList<>();

    private List<MRFMasterDto> mrfMasterList = new ArrayList<>();

    private List<DocumentDetailsVO> documents = new ArrayList<>();

    private List<AttachDocs> attachDocsList = new ArrayList<>();

    List<EmployeeBean> employeeList = new ArrayList<>();

    @Override
    public boolean saveForm() {
        tripSheetDto.setTripSheetDTO(tripSheetDtos);
        validateBean(tripSheetDto, TripSheetMasterValidator.class);

        if (hasValidationErrors()) {
            return false;
        }

        RequestDTO requestDTO = new RequestDTO();
        requestDTO.setOrgId(
                UserSession.getCurrent().getOrganisation().getOrgid());
        requestDTO.setStatus(MainetConstants.FlagA);
        // requestDTO.setIdfId(getTripSheetDto().getTripId().toString());
        requestDTO.setDepartmentName(MainetConstants.SolidWasteManagement.SHORT_CODE);
        requestDTO.setUserId(UserSession.getCurrent().getEmployee().getEmpId());
        List<DocumentDetailsVO> dto = getDocuments();

        setDocuments(fileUpload.setFileUploadMethod(getDocuments()));

        int i = 0;
        for (final Map.Entry<Long, Set<File>> entry : FileUploadUtility
                .getCurrent().getFileMap().entrySet()) {
            getDocuments().get(i).setDoc_DESC_ENGL(
                    dto.get(entry.getKey().intValue()).getDoc_DESC_ENGL());
            i++;
        }

        tripSheetDto.getTbSwTripsheetGdets().forEach(garbageDet -> {
            garbageDet.setTbSwTripsheet(tripSheetDto);
            if (garbageDet.getTripdId() == null) {
                garbageDet.setOrgid(
                        UserSession.getCurrent().getOrganisation().getOrgid());
                garbageDet.setCreatedBy(
                        UserSession.getCurrent().getEmployee().getEmpId());
                garbageDet.setCreatedDate(new Date());
                garbageDet.setLgIpMac(UserSession.getCurrent().getEmployee()
                        .getEmppiservername());
            } else {
                garbageDet.setUpdatedBy(
                        UserSession.getCurrent().getEmployee().getEmpId());
                garbageDet.setUpdatedDate(new Date());
                garbageDet.setLgIpMacUpd(UserSession.getCurrent().getEmployee()
                        .getEmppiservername());
            }
        });

        prepareTriphSheet(getTripSheetDto(), requestDTO);
        return true;
    }

    private TripSheetDTO prepareTriphSheet(TripSheetDTO tripDto,
            RequestDTO requestDTO) {
        getTripSheetDto().setOrgid(
                UserSession.getCurrent().getOrganisation().getOrgid());
        if (tripDto.getTripId() == null) {
            tripDto.setCreatedBy(
                    UserSession.getCurrent().getEmployee().getEmpId());
            tripDto.setCreatedDate(new Date());
            tripDto.setLgIpMac(UserSession.getCurrent().getEmployee()
                    .getEmppiservername());
            tripDto.setTripIntime(
                    stringToTimeConvert(tripDto.getTripIntimeDesc()));
            tripDto.setTripOuttime(
                    stringToTimeConvert(tripDto.getTripOuttimeDesc()));
            if (tripDto.getTripDate() == null) {
                tripDto.setTripDate(new Date());
            }
            tripDto.setTripData("N");
            TripSheetDTO dto = tripSheetService.save(tripDto);
            requestDTO.setIdfId("SWM_Tripsheet" + dto.getTripId().toString());
            fileUpload.doMasterFileUpload(getDocuments(), requestDTO);
            this.setSuccessMessage(getAppSession().getMessage("swm.tripsheet.save"));
        } else {
            tripDto.setUpdatedBy(
                    UserSession.getCurrent().getEmployee().getEmpId());
            tripDto.setUpdatedDate(new Date());
            tripDto.setLgIpMacUpd(UserSession.getCurrent().getEmployee()
                    .getEmppiservername());
            tripDto.setTripIntime(
                    stringToTimeConvert(tripDto.getTripIntimeDesc()));
            tripDto.setTripOuttime(
                    stringToTimeConvert(tripDto.getTripOuttimeDesc()));

            TripSheetDTO dto = tripSheetService.update(tripDto);
            if (attachDocsList.isEmpty()) {
                requestDTO.setIdfId("SWM_Tripsheet" + dto.getTripId().toString());
                fileUpload.doMasterFileUpload(getDocuments(), requestDTO);
            }
            this.setSuccessMessage(getAppSession().getMessage("swm.tripsheet.edit"));
        }
        return tripDto;

    }

    public List<MRFMasterDto> getMrfMasterList() {
        return mrfMasterList;
    }

    public void setMrfMasterList(List<MRFMasterDto> mrfMasterList) {
        this.mrfMasterList = mrfMasterList;
    }

    public List<VehicleMasterDTO> getVehicleMasterList() {
        return vehicleMasterList;
    }

    public void setVehicleMasterList(List<VehicleMasterDTO> vehicleMasterList) {
        this.vehicleMasterList = vehicleMasterList;
    }

    public TripSheetDTO getTripSheetDto() {
        return tripSheetDto;
    }

    public List<DocumentDetailsVO> getDocuments() {
        return documents;
    }

    public void setDocuments(List<DocumentDetailsVO> documents) {
        this.documents = documents;
    }

    public void setTripSheetDto(TripSheetDTO tripSheetDto) {
        this.tripSheetDto = tripSheetDto;
    }

    public String getSaveMode() {
        return saveMode;
    }

    public List<EmployeeBean> getEmployeeList() {
        return employeeList;
    }

    public void setEmployeeList(List<EmployeeBean> employeeList) {
        this.employeeList = employeeList;
    }

    public void setSaveMode(String saveMode) {
        this.saveMode = saveMode;
    }

    public List<TripSheetDTO> getTripSheetDtos() {
        return tripSheetDtos;
    }

    public void setTripSheetDtos(List<TripSheetDTO> tripSheetDtos) {
        this.tripSheetDtos = tripSheetDtos;
    }

    public List<AttachDocs> getAttachDocsList() {
        return attachDocsList;
    }

    public void setAttachDocsList(List<AttachDocs> attachDocsList) {
        this.attachDocsList = attachDocsList;
    }

    public List<BeatMasterDTO> getRoutelist() {
        return routelist;
    }

    public void setRoutelist(List<BeatMasterDTO> routelist) {
        this.routelist = routelist;
    }

    public Date stringToTimeConvert(String time) {

        DateFormat formatter = new SimpleDateFormat("HH:mm");
        Date timeValue = null;

        if (time != null) {
            try {
                timeValue = new Date(formatter.parse(time).getTime());
            } catch (ParseException e) {
                e.printStackTrace();
            }
        }
        return timeValue;
    }

    public String TimeToStringConvert(Date date) {
        String dateString = null;
        DateFormat sdf = new SimpleDateFormat("HH:mm");
        dateString = sdf.format(date);
        return dateString;
    }

}
