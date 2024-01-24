package com.abm.mainet.rts.ui.model;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.abm.mainet.cfc.challan.domain.ChallanMaster;
import com.abm.mainet.cfc.challan.dto.ChallanReceiptPrintDTO;
import com.abm.mainet.cfc.challan.service.IChallanService;
import com.abm.mainet.cfc.checklist.service.IChecklistVerificationService;
import com.abm.mainet.common.constant.MainetConstants;
import com.abm.mainet.common.constant.PrefixConstants;
import com.abm.mainet.common.domain.Employee;
import com.abm.mainet.common.domain.Organisation;
import com.abm.mainet.common.domain.ServiceMaster;
import com.abm.mainet.common.dto.CommonChallanDTO;
import com.abm.mainet.common.exception.FrameworkException;
import com.abm.mainet.common.integration.dms.domain.AttachDocs;
import com.abm.mainet.common.integration.dms.service.IFileUploadService;
import com.abm.mainet.common.integration.dto.DocumentDetailsVO;
import com.abm.mainet.common.integration.dto.RequestDTO;
import com.abm.mainet.common.service.CommonService;
import com.abm.mainet.common.service.ServiceMasterService;
import com.abm.mainet.common.ui.model.AbstractFormModel;
import com.abm.mainet.common.utility.ApplicationContextProvider;
import com.abm.mainet.common.utility.ApplicationSession;
import com.abm.mainet.common.utility.CommonMasterUtility;
import com.abm.mainet.common.utility.UserSession;
import com.abm.mainet.common.utility.Utility;
import com.abm.mainet.common.utility.UtilityService;
import com.abm.mainet.common.workflow.dto.ApplicationMetadata;
import com.abm.mainet.common.workflow.dto.WorkflowTaskAction;
import com.abm.mainet.rts.dto.DrainageConnectionDto;
import com.abm.mainet.rts.service.DrainageConnectionService;
import com.abm.mainet.smsemail.dto.SMSAndEmailDTO;
import com.abm.mainet.smsemail.service.ISMSAndEmailService;

@Component
@Scope("session")
public class RtsServiceFormModel extends AbstractFormModel {

	/**
	 * @author rahul.chaubey
	 * @since 06 March 2020
	 */
	private static final long serialVersionUID = 268147895593525919L;

	@Autowired
	private DrainageConnectionService drainageConnectionService;

	@Autowired
	private ServiceMasterService serviceMaster;

	@Autowired
	private CommonService commonService;

	@Autowired
	private IFileUploadService fileUploadService;

	@Autowired
	private ISMSAndEmailService ismsAndEmailService;

	@Autowired
	private IChallanService iChallanService;

	private Map<Long, String> depList = null;
	private Map<Long, String> serviceList = null;
	private RequestDTO requestDTO = new RequestDTO();
	private Map<Long, String> wardList = null;
	private List<DocumentDetailsVO> checkList = new ArrayList<>();
	private List<Long> applicationNo = new ArrayList<Long>();
	private List<RequestDTO> requestDtoList = new ArrayList<>();
	private DrainageConnectionDto drainageConnectionDto = new DrainageConnectionDto();
	private String saveMode;
	private boolean noCheckListFound;
	private String errorMessage;
	private Map<Long, Double> chargesMap = new HashMap<>();
	private String isFree;
	private Double charges = 0.0d;
	private ServiceMaster serviceMasterData = new ServiceMaster();
	private Long parentOrgId;
	private Long serviceId;
	private Long deptId;
	private CommonChallanDTO offlineDTO = new CommonChallanDTO();
	private List<AttachDocs> attachDocsList = new ArrayList<>();

	
	public List<AttachDocs> getAttachDocsList() {
		return attachDocsList;
	}

	public void setAttachDocsList(List<AttachDocs> attachDocsList) {
		this.attachDocsList = attachDocsList;
	}

	public Map<Long, String> getDepList() {
		return depList;
	}

	public void setDepList(Map<Long, String> depList) {
		this.depList = depList;
	}

	public Map<Long, String> getServiceList() {
		return serviceList;
	}

	public void setServiceList(Map<Long, String> serviceList) {
		this.serviceList = serviceList;
	}

	public RequestDTO getRequestDTO() {
		return requestDTO;
	}

	public void setRequestDTO(RequestDTO requestDTO) {
		this.requestDTO = requestDTO;
	}

	public DrainageConnectionDto getDrainageConnectionDto() {
		return drainageConnectionDto;
	}

	public void setDrainageConnectionDto(DrainageConnectionDto drainageConnectionDto) {
		this.drainageConnectionDto = drainageConnectionDto;
	}

	public Map<Long, String> getWardList() {
		return wardList;
	}

	public void setWardList(Map<Long, String> wardList) {
		this.wardList = wardList;
	}

	public List<DocumentDetailsVO> getCheckList() {
		return checkList;
	}

	public void setCheckList(List<DocumentDetailsVO> checkList) {
		this.checkList = checkList;
	}

	public String getSaveMode() {
		return saveMode;
	}

	public void setSaveMode(String saveMode) {
		this.saveMode = saveMode;
	}

	public boolean isNoCheckListFound() {
		return noCheckListFound;
	}

	public void setNoCheckListFound(boolean noCheckListFound) {
		this.noCheckListFound = noCheckListFound;
	}

	public String getErrorMessage() {
		return errorMessage;
	}

	public void setErrorMessage(String errorMessage) {
		this.errorMessage = errorMessage;
	}

	public List<Long> getApplicationNo() {
		return applicationNo;
	}

	public void setApplicationNo(List<Long> applicationNo) {
		this.applicationNo = applicationNo;
	}

	public List<RequestDTO> getRequestDtoList() {
		return requestDtoList;
	}

	public void setRequestDtoList(List<RequestDTO> requestDtoList) {
		this.requestDtoList = requestDtoList;
	}

	public Map<Long, Double> getChargesMap() {
		return chargesMap;
	}

	public void setChargesMap(Map<Long, Double> chargesMap) {
		this.chargesMap = chargesMap;
	}

	public String getIsFree() {
		return isFree;
	}

	public void setIsFree(String isFree) {
		this.isFree = isFree;
	}

	public Double getCharges() {
		return charges;
	}

	public void setCharges(Double charges) {
		this.charges = charges;
	}

	public ServiceMaster getServiceMasterData() {
		return serviceMasterData;
	}

	public void setServiceMasterData(ServiceMaster serviceMasterData) {
		this.serviceMasterData = serviceMasterData;
	}

	public Long getParentOrgId() {
		return parentOrgId;
	}

	public void setParentOrgId(Long parentOrgId) {
		this.parentOrgId = parentOrgId;
	}

	public Long getServiceId() {
		return serviceId;
	}

	public void setServiceId(Long serviceId) {
		this.serviceId = serviceId;
	}

	public Long getDeptId() {
		return deptId;
	}

	public void setDeptId(Long deptId) {
		this.deptId = deptId;
	}

	public CommonChallanDTO getOfflineDTO() {
		return offlineDTO;
	}

	public void setOfflineDTO(CommonChallanDTO offlineDTO) {
		this.offlineDTO = offlineDTO;
	}

}
