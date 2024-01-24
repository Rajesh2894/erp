package com.abm.mainet.rts.ui.model;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.abm.mainet.common.constant.MainetConstants;
import com.abm.mainet.common.constant.PrefixConstants;
import com.abm.mainet.common.domain.Organisation;
import com.abm.mainet.common.domain.PortalService;
import com.abm.mainet.common.domain.ServiceMaster;
import com.abm.mainet.common.dto.ChallanReceiptPrintDTO;
import com.abm.mainet.common.dto.CommonChallanDTO;
import com.abm.mainet.common.dto.DocumentDetailsVO;
import com.abm.mainet.common.dto.RequestDTO;
import com.abm.mainet.common.exception.FrameworkException;
import com.abm.mainet.common.service.IFileUploadService;
import com.abm.mainet.common.service.IPortalServiceMasterService;
import com.abm.mainet.common.service.ServiceMasterService;
import com.abm.mainet.common.ui.model.AbstractFormModel;
import com.abm.mainet.common.util.CommonMasterUtility;
import com.abm.mainet.common.util.UserSession;
import com.abm.mainet.common.util.Utility;
import com.abm.mainet.common.util.UtilityService;
import com.abm.mainet.common.utility.ApplicationContextProvider;
import com.abm.mainet.payment.dto.PaymentRequestDTO;
import com.abm.mainet.payment.service.IChallanService;
import com.abm.mainet.rts.dto.DrainageConnectionDto;
import com.abm.mainet.rts.service.DrainageConnectionService;
import com.abm.mainet.smsemail.dto.SMSAndEmailDTO;
import com.abm.mainet.smsemail.service.ISMSAndEmailService;

@Component
@Scope("session")
public class RtsServiceFormModel extends AbstractFormModel {

	/**
	 * 
	 */
	private static final long serialVersionUID = 4567171526510177167L;

	// @Autowired
	// private ServiceMasterService serviceMaster;

	/*
	 * @Autowired private CommonService commonService;
	 */

	@Autowired
	private IFileUploadService fileUploadService;

	@Autowired
	private ISMSAndEmailService ismsAndEmailService;

	@Autowired
	private IChallanService iChallanService;
	
	@Autowired
	private IPortalServiceMasterService iPortalService;

	@Autowired
	private DrainageConnectionService drainageConnectionService;

	private Map<Long, String> depList = null;
	private List<Object[]> serviceList = new ArrayList<>();
	private Map<String, String> serviceMap = new HashMap();
	private RequestDTO reqDTO = new RequestDTO();
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
	private String serviceCode;
	private String formName;
	private List<DocumentDetailsVO> documentList = new ArrayList<>();
	private String orgName;

	/*
	 * public ServiceMasterService getServiceMaster() { return serviceMaster; }
	 * public void setServiceMaster(ServiceMasterService serviceMaster) {
	 * this.serviceMaster = serviceMaster; }
	 */
	public IFileUploadService getFileUploadService() {
		return fileUploadService;
	}

	public void setFileUploadService(IFileUploadService fileUploadService) {
		this.fileUploadService = fileUploadService;
	}

	public ISMSAndEmailService getIsmsAndEmailService() {
		return ismsAndEmailService;
	}

	public void setIsmsAndEmailService(ISMSAndEmailService ismsAndEmailService) {
		this.ismsAndEmailService = ismsAndEmailService;
	}

	public IChallanService getiChallanService() {
		return iChallanService;
	}

	public void setiChallanService(IChallanService iChallanService) {
		this.iChallanService = iChallanService;
	}

	public Map<Long, String> getDepList() {
		return depList;
	}

	public void setDepList(Map<Long, String> depList) {
		this.depList = depList;
	}

	public List<Object[]> getServiceList() {
		return serviceList;
	}

	public void setServiceList(List<Object[]> serviceList) {
		this.serviceList = serviceList;
	}

	public void setReqDTO(RequestDTO reqDTO) {
		this.reqDTO = reqDTO;
	}

	public RequestDTO getReqDTO() {
		return reqDTO;
	}

	public void setRequestDTO(RequestDTO reqDTO) {
		this.reqDTO = reqDTO;
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

	public DrainageConnectionDto getDrainageConnectionDto() {
		return drainageConnectionDto;
	}

	public void setDrainageConnectionDto(DrainageConnectionDto drainageConnectionDto) {
		this.drainageConnectionDto = drainageConnectionDto;
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

	public String getServiceCode() {
		return serviceCode;
	}

	public void setServiceCode(String serviceCode) {
		this.serviceCode = serviceCode;
	}

	public Map<String, String> getServiceMap() {
		return serviceMap;
	}

	public void setServiceMap(Map<String, String> serviceMap) {
		this.serviceMap = serviceMap;
	}

	public String getFormName() {
		return formName;
	}

	public void setFormName(String formName) {
		this.formName = formName;
	}

	public List<DocumentDetailsVO> getDocumentList() {
		return documentList;
	}

	public void setDocumentList(List<DocumentDetailsVO> documentList) {
		this.documentList = documentList;
	}

	public String getOrgName() {
		return orgName;
	}

	public void setOrgName(String orgName) {
		this.orgName = orgName;
	}
	//method for Setting data for online payment Defect 
		@Override
		public void redirectToPayDetails(final HttpServletRequest httpServletRequest,
				final PaymentRequestDTO payURequestDTO) {
			this.bind(httpServletRequest);
			RtsServiceFormModel model = this;
			final PortalService portalServiceMaster = iPortalService.getService(model.getReqDTO().getServiceId(),
					UserSession.getCurrent().getOrganisation().getOrgid());
			payURequestDTO.setUdf3("CitizenHome.html");
			payURequestDTO.setUdf5(portalServiceMaster.getShortName());
			payURequestDTO.setUdf7(String.valueOf(model.getApmApplicationId()));
			String fullName = String.join(" ", Arrays.asList(model.getReqDTO().getfName(), model.getReqDTO().getmName(),
					model.getReqDTO().getlName()));
			payURequestDTO.setApplicantName(fullName);
			payURequestDTO.setServiceId(portalServiceMaster.getServiceId());
			payURequestDTO.setUdf2(String.valueOf(model.getApmApplicationId()));
			payURequestDTO.setMobNo(model.getReqDTO().getMobileNo());
			if (model.getReqDTO().getPayAmount()!= null) {
				payURequestDTO.setDueAmt(new BigDecimal(model.getReqDTO().getPayAmount()));
			}
			// payURequestDTO.setDueAmt(paymentAmount);
			payURequestDTO.setEmail(model.getReqDTO().getEmail());
			payURequestDTO.setApplicationId(payURequestDTO.getUdf2());
			if (portalServiceMaster != null) {
				payURequestDTO.setUdf10(portalServiceMaster.getPsmDpDeptid().toString());
				if (UserSession.getCurrent().getLanguageId() == MainetConstants.ENGLISH) {
					payURequestDTO.setServiceName(portalServiceMaster.getServiceName());
				} else {
					payURequestDTO.setServiceName(portalServiceMaster.getServiceNameReg());
				}
			}
		}

}
