package com.abm.mainet.buildingplan.ui.model;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.abm.mainet.buildingplan.domain.LicenseApplicationLandAcquisitionDetEntity;
import com.abm.mainet.buildingplan.domain.LicenseApplicationMaster;
import com.abm.mainet.buildingplan.dto.LandDetailsDTO;
import com.abm.mainet.buildingplan.dto.LicenseApplicationLandAcquisitionDetailDTO;
import com.abm.mainet.buildingplan.dto.LicenseApplicationMasterDTO;
import com.abm.mainet.buildingplan.service.INewLicenseFormService;
import com.abm.mainet.buildingplan.service.ISiteAffecService;
import com.abm.mainet.common.constant.MainetConstants;
import com.abm.mainet.common.domain.Employee;
import com.abm.mainet.common.integration.dms.service.IFileUploadService;
import com.abm.mainet.common.integration.dto.DocumentDetailsVO;
import com.abm.mainet.common.integration.dto.RequestDTO;
import com.abm.mainet.common.ui.model.AbstractFormModel;
import com.abm.mainet.common.utility.ApplicationContextProvider;
import com.abm.mainet.common.utility.UserSession;

@Component
@Scope("session")
public class LandDetailsModel extends AbstractFormModel {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Autowired
	ISiteAffecService siteAffecService;
	
	@Autowired
	INewLicenseFormService newLicenseFormService;

	private List<LandDetailsDTO> listLandDetailsDTO = new ArrayList<LandDetailsDTO>();
	
	private List<LicenseApplicationLandAcquisitionDetailDTO> listLandDetailDTO = new ArrayList<LicenseApplicationLandAcquisitionDetailDTO>();
	
	private LicenseApplicationMasterDTO licenseApplicationMasterDTO = new LicenseApplicationMasterDTO();

	private Long applicationId;

	private Long gmId;

	private Long slLabelId;

	private Long level;
	
	private String fieldFlag;
	
	private String scrnFlag;
	
	private String landRmark;
	
	private Long taskId;
	
	 private Map<String, List<LicenseApplicationLandAcquisitionDetailDTO>> landDetailsMap = new HashMap<>(0);

	@Override
	public boolean saveForm() {
		
		RequestDTO requestDTO = new RequestDTO();
		Long orgId = UserSession.getCurrent().getOrganisation().getOrgid();
		requestDTO.setOrgId(orgId);
		requestDTO.setStatus(MainetConstants.FlagA);
		requestDTO.setDepartmentName("ML");
		requestDTO.setServiceId(getServiceId());
		requestDTO.setIdfId(String.valueOf(applicationId+"L"));
		requestDTO.setApplicationId(applicationId);
		requestDTO.setReferenceId(applicationId+MainetConstants.WINDOWS_SLASH+fieldFlag+taskId);
		requestDTO.setUserId(UserSession.getCurrent().getEmployee().getEmpId());
		setCommonFileAttachment(ApplicationContextProvider.getApplicationContext().getBean(IFileUploadService.class)
				.setFileUploadMethod(new ArrayList<DocumentDetailsVO>()));
		if(!getCommonFileAttachment().isEmpty())
		getCommonFileAttachment().get(0).setDocDescription("Shajra Plan By Patwari Field PDF");
		ApplicationContextProvider.getApplicationContext().getBean(IFileUploadService.class)
				.doFileUpload(getCommonFileAttachment(), requestDTO);
		if(fieldFlag.equals("VF")) {
			newLicenseFormService.updateLoaRemark(applicationId,landRmark);
		}else{

		List<LicenseApplicationLandAcquisitionDetEntity> listLandDetailsEntity = new ArrayList<LicenseApplicationLandAcquisitionDetEntity>();
		final Employee emp = UserSession.getCurrent().getEmployee();
		List<LicenseApplicationLandAcquisitionDetailDTO> finalLandDetailDTO = new ArrayList<LicenseApplicationLandAcquisitionDetailDTO>();
		if(!fieldFlag.equals("LF")) {
		for (Map.Entry<String, List<LicenseApplicationLandAcquisitionDetailDTO>> entry : landDetailsMap.entrySet()) {
            String key = entry.getKey();
            finalLandDetailDTO .addAll(entry.getValue());
        }
		}else {
			finalLandDetailDTO.addAll(getLicenseApplicationMasterDTO().getLicenseApplicationLandAcquisitionDetDTOList());
		}
		for (LicenseApplicationLandAcquisitionDetailDTO landDetailsDTO : finalLandDetailDTO) {
			LicenseApplicationLandAcquisitionDetEntity landEntity = new LicenseApplicationLandAcquisitionDetEntity();
			landDetailsDTO.setGmId(gmId);
			landDetailsDTO.setLevel(level);
			landDetailsDTO.setSlLabelId(slLabelId);
			if (landDetailsDTO.getLicAcquDetId() != null) {
				landDetailsDTO.setModifiedBy(emp.getEmpId());
				landDetailsDTO.setModifiedDate(new Date());
			} else {
				landDetailsDTO.setCreatedBy(emp.getEmpId());
				landDetailsDTO.setCreateDate(new Date());
			}
			BeanUtils.copyProperties(landDetailsDTO, landEntity);
			LicenseApplicationMaster licenseApplicationMaster=new LicenseApplicationMaster();
				BeanUtils.copyProperties(landDetailsDTO.getLicenseApplicationMaster(), licenseApplicationMaster);
				landEntity.setLicenseApplicationMaster(licenseApplicationMaster);
				landEntity.setTaskId(taskId);
			    listLandDetailsEntity.add(landEntity);

		}
		
		listLandDetailsEntity=newLicenseFormService.saveLandDetailsData(listLandDetailsEntity,this.getScrnFlag());
	}
		setSuccessMessage("Records has been saved successfully");
		return true;
	}

	public List<LandDetailsDTO> getListLandDetailsDTO() {
		return listLandDetailsDTO;
	}

	public void setListLandDetailsDTO(List<LandDetailsDTO> listLandDetailsDTO) {
		this.listLandDetailsDTO = listLandDetailsDTO;
	}

	public Long getApplicationId() {
		return applicationId;
	}

	public void setApplicationId(Long applicationId) {
		this.applicationId = applicationId;
	}

	public Long getGmId() {
		return gmId;
	}

	public void setGmId(Long gmId) {
		this.gmId = gmId;
	}

	public Long getSlLabelId() {
		return slLabelId;
	}

	public void setSlLabelId(Long slLabelId) {
		this.slLabelId = slLabelId;
	}

	public Long getLevel() {
		return level;
	}

	public void setLevel(Long level) {
		this.level = level;
	}

	public List<LicenseApplicationLandAcquisitionDetailDTO> getListLandDetailDTO() {
		return listLandDetailDTO;
	}

	public void setListLandDetailDTO(List<LicenseApplicationLandAcquisitionDetailDTO> listLandDetailDTO) {
		this.listLandDetailDTO = listLandDetailDTO;
	}

	public Map<String, List<LicenseApplicationLandAcquisitionDetailDTO>> getLandDetailsMap() {
		return landDetailsMap;
	}

	public void setLandDetailsMap(Map<String, List<LicenseApplicationLandAcquisitionDetailDTO>> landDetailsMap) {
		this.landDetailsMap = landDetailsMap;
	}

	public String getFieldFlag() {
		return fieldFlag;
	}

	public void setFieldFlag(String fieldFlag) {
		this.fieldFlag = fieldFlag;
	}

	public LicenseApplicationMasterDTO getLicenseApplicationMasterDTO() {
		return licenseApplicationMasterDTO;
	}

	public void setLicenseApplicationMasterDTO(LicenseApplicationMasterDTO licenseApplicationMasterDTO) {
		this.licenseApplicationMasterDTO = licenseApplicationMasterDTO;
	}

	public String getLandRmark() {
		return landRmark;
	}

	public void setLandRmark(String landRmark) {
		this.landRmark = landRmark;
	}

	public Long getTaskId() {
		return taskId;
	}

	public void setTaskId(Long taskId) {
		this.taskId = taskId;
	}

	public String getScrnFlag() {
		return scrnFlag;
	}

	public void setScrnFlag(String scrnFlag) {
		this.scrnFlag = scrnFlag;
	}

}
