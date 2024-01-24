package com.abm.mainet.sfac.ui.model;

import java.io.File;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.annotation.Resource;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.abm.mainet.common.constant.MainetConstants;
import com.abm.mainet.common.domain.Employee;
import com.abm.mainet.common.integration.dms.client.FileNetApplicationClient;
import com.abm.mainet.common.integration.dms.domain.AttachDocs;
import com.abm.mainet.common.integration.dms.fileUpload.FileUploadUtility;
import com.abm.mainet.common.integration.dms.service.IFileUploadService;
import com.abm.mainet.common.integration.dto.DocumentDetailsVO;
import com.abm.mainet.common.integration.dto.RequestDTO;
import com.abm.mainet.common.ui.model.AbstractFormModel;
import com.abm.mainet.common.utility.ApplicationContextProvider;
import com.abm.mainet.common.utility.Filepaths;
import com.abm.mainet.common.utility.UserSession;
import com.abm.mainet.common.utility.Utility;
import com.abm.mainet.sfac.dto.FarmerMasterDto;
import com.abm.mainet.sfac.service.FarmerMasterService;
import com.abm.mainet.sfac.ui.validator.FarmerMasterValidator;

/**
 * @author pooja.maske
 *
 */

@Component
@Scope("session")
public class FarmerMasterModel extends AbstractFormModel {

	private static final long serialVersionUID = -739951024332709292L;

	@Autowired
	private FarmerMasterService farmerMasterService;

	@Resource
	IFileUploadService fileUpload;

	private FarmerMasterDto farmerMasterDto = new FarmerMasterDto();

	private String viewMode;

	private List<FarmerMasterDto> farmerMasterDtoList = new ArrayList<>();

	private List<FarmerMasterDto> masterDtoList = new ArrayList<>();
	private Map<Integer, List<AttachDocs>> docMap = new HashMap<>();
	private List<Long> fileCountUpload;
	private Long length;
	private List<DocumentDetailsVO> attachments = new ArrayList<>();
	private List<AttachDocs> attachDocsList = new ArrayList<>();
	private Map<Long, List<String>> fileNames = new HashMap<>();
	
	private String downloadMode;

	/**
	 * @return the farmerMasterDto
	 */
	public FarmerMasterDto getFarmerMasterDto() {
		return farmerMasterDto;
	}

	/**
	 * @param farmerMasterDto the farmerMasterDto to set
	 */
	public void setFarmerMasterDto(FarmerMasterDto farmerMasterDto) {
		this.farmerMasterDto = farmerMasterDto;
	}

	/**
	 * @return the farmerMasterDtoList
	 */
	public List<FarmerMasterDto> getFarmerMasterDtoList() {
		return farmerMasterDtoList;
	}

	/**
	 * @param farmerMasterDtoList the farmerMasterDtoList to set
	 */
	public void setFarmerMasterDtoList(List<FarmerMasterDto> farmerMasterDtoList) {
		this.farmerMasterDtoList = farmerMasterDtoList;
	}

	/**
	 * @return the viewMode
	 */
	public String getViewMode() {
		return viewMode;
	}

	/**
	 * @param viewMode the viewMode to set
	 */
	public void setViewMode(String viewMode) {
		this.viewMode = viewMode;
	}

	/**
	 * @return the masterDtoList
	 */
	public List<FarmerMasterDto> getMasterDtoList() {
		return masterDtoList;
	}

	/**
	 * @param masterDtoList the masterDtoList to set
	 */
	public void setMasterDtoList(List<FarmerMasterDto> masterDtoList) {
		this.masterDtoList = masterDtoList;
	}

	/**
	 * @return the docMap
	 */
	public Map<Integer, List<AttachDocs>> getDocMap() {
		return docMap;
	}

	/**
	 * @param docMap the docMap to set
	 */
	public void setDocMap(Map<Integer, List<AttachDocs>> docMap) {
		this.docMap = docMap;
	}

	/**
	 * @return the fileCountUpload
	 */
	public List<Long> getFileCountUpload() {
		return fileCountUpload;
	}

	/**
	 * @param fileCountUpload the fileCountUpload to set
	 */
	public void setFileCountUpload(List<Long> fileCountUpload) {
		this.fileCountUpload = fileCountUpload;
	}

	/**
	 * @return the length
	 */
	public Long getLength() {
		return length;
	}

	/**
	 * @param length the length to set
	 */
	public void setLength(Long length) {
		this.length = length;
	}

	/**
	 * @return the attachments
	 */
	public List<DocumentDetailsVO> getAttachments() {
		return attachments;
	}

	/**
	 * @param attachments the attachments to set
	 */
	public void setAttachments(List<DocumentDetailsVO> attachments) {
		this.attachments = attachments;
	}

	/**
	 * @return the attachDocsList
	 */
	public List<AttachDocs> getAttachDocsList() {
		return attachDocsList;
	}

	/**
	 * @param attachDocsList the attachDocsList to set
	 */
	public void setAttachDocsList(List<AttachDocs> attachDocsList) {
		this.attachDocsList = attachDocsList;
	}

	/**
	 * @return the fileNames
	 */
	public Map<Long, List<String>> getFileNames() {
		return fileNames;
	}

	/**
	 * @param fileNames the fileNames to set
	 */
	public void setFileNames(Map<Long, List<String>> fileNames) {
		this.fileNames = fileNames;
	}
	
	

	/**
	 * @return the downloadMode
	 */
	public String getDownloadMode() {
		return downloadMode;
	}

	/**
	 * @param downloadMode the downloadMode to set
	 */
	public void setDownloadMode(String downloadMode) {
		this.downloadMode = downloadMode;
	}

	@Override
	public boolean saveForm() {
		validateBean(farmerMasterDto, FarmerMasterValidator.class);
		if (hasValidationErrors()) {
			return false;
		} else {
			Employee employee = getUserSession().getEmployee();
			int langId = UserSession.getCurrent().getLanguageId();
			farmerMasterDto.setCreatedBy(employee.getEmpId());
			farmerMasterDto.setCreatedDate(new Date());
			farmerMasterDto.setLgIpMac(employee.getEmppiservername());
			farmerMasterDto.setLgIpMacUpd(employee.getEmppiservername());
			farmerMasterDto.setOrgId(employee.getOrganisation().getOrgid());
			farmerMasterDto.setLangId(langId);
			setAttachments(fileUpload.setFileUploadMethod(new ArrayList<DocumentDetailsVO>()));

			
			RequestDTO requestDTO = new RequestDTO();
			requestDTO.setOrgId(UserSession.getCurrent().getOrganisation().getOrgid());
			requestDTO.setStatus(MainetConstants.FlagA);
			requestDTO.setDepartmentName(MainetConstants.Sfac.NPMA);
			requestDTO.setUserId(UserSession.getCurrent().getEmployee().getEmpId());
	        
	     /*   int i = 0;
	        for (final Map.Entry<Long, Set<File>> entry : FileUploadUtility.getCurrent().getFileMap().entrySet()) {
	            for (int value = 0; value <= entry.getValue().size() - 1; value++) {
	                getAttachments().get(i)
	                        .setDocumentSerialNo((progressList.get(entry.getKey().intValue()).getPhyPercent().longValue()));
	                i++;
	            }
	        }*/
			farmerMasterService.saveFarmerDetails(farmerMasterDto, this, getAttachments(), requestDTO);
		}
		this.setSuccessMessage(getAppSession().getMessage("sfac.frm.master.form.save"));
		return true;
	}

	 public String getDirectry() {
	        return UserSession.getCurrent().getOrganisation().getOrgid() + File.separator + "FARMER_MASTER"
	                + File.separator + Utility.getTimestamp();
	    }
	 
	public void getdataOfUploadedImage() {
		getFileNames().clear();
		List<String> fileNameList = null;
		Long count = 0L;
		Map<Long, List<String>> fileNames = new HashMap<>();
		if ((FileUploadUtility.getCurrent().getFileMap() != null)
				&& !FileUploadUtility.getCurrent().getFileMap().isEmpty()) {
			for (final Map.Entry<Long, Set<File>> entry : FileUploadUtility.getCurrent().getFileMap().entrySet()) {
				fileNameList = new ArrayList<>();
				for (final File file : entry.getValue()) {
					String fileName = null;

					try {
						final String path = file.getPath().replace(MainetConstants.DOUBLE_BACK_SLACE,
								MainetConstants.operator.FORWARD_SLACE);
						fileName = path.replace(Filepaths.getfilepath(), StringUtils.EMPTY);
					} catch (final Exception e) {
						e.printStackTrace();
					}

					fileNameList.add(fileName);
				}
				fileNames.put(count, fileNameList);
				count++;
			}
		}
		setFileNames(fileNames);
	}

	
}
