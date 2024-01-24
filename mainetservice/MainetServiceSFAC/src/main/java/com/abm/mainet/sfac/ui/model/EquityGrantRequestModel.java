/**
 * 
 */
package com.abm.mainet.sfac.ui.model;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.List;

import org.codehaus.jackson.map.ObjectMapper;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;
import org.springframework.web.context.WebApplicationContext;

import com.abm.mainet.common.constant.MainetConstants;
import com.abm.mainet.common.domain.Organisation;
import com.abm.mainet.common.domain.ServiceMaster;
import com.abm.mainet.common.exception.FrameworkException;
import com.abm.mainet.common.integration.brms.datamodel.CheckListModel;
import com.abm.mainet.common.integration.brms.service.BRMSCommonService;
import com.abm.mainet.common.integration.dms.domain.AttachDocs;
import com.abm.mainet.common.integration.dms.domain.CFCAttachment;
import com.abm.mainet.common.integration.dms.service.IFileUploadService;
import com.abm.mainet.common.integration.dto.DocumentDetailsVO;
import com.abm.mainet.common.integration.dto.WSRequestDTO;
import com.abm.mainet.common.integration.dto.WSResponseDTO;
import com.abm.mainet.common.master.dto.DesignationBean;
import com.abm.mainet.common.master.service.TbDepartmentService;
import com.abm.mainet.common.service.IOrganisationService;
import com.abm.mainet.common.service.ServiceMasterService;
import com.abm.mainet.common.ui.model.AbstractFormModel;
import com.abm.mainet.common.utility.ApplicationContextProvider;
import com.abm.mainet.common.utility.LookUp;
import com.abm.mainet.common.utility.UserSession;
import com.abm.mainet.sfac.dto.EquityGrantMasterDto;
import com.abm.mainet.sfac.service.EquityGrantRequestMasterService;

/**
 * @author pooja.maske
 *
 */
@Component
@Scope(value = WebApplicationContext.SCOPE_SESSION)
public class EquityGrantRequestModel extends AbstractFormModel{

	private static final long serialVersionUID = -4476965005476354295L;

	@Autowired
	IFileUploadService fileUpload;

	@Autowired
	private BRMSCommonService brmsCommonService;
	
	private List<AttachDocs> attachDocsList = new ArrayList<>();

	private List<LookUp> stateList = new ArrayList<>();

	private List<LookUp> districtList = new ArrayList<>();

	private EquityGrantMasterDto dto = new EquityGrantMasterDto();

	private List<EquityGrantMasterDto> equityGrantMasterDtos = new ArrayList<>();

	private String bankName ;

	private String viewMode;


	private List<DocumentDetailsVO> checkList = new ArrayList<>();
	private ServiceMaster serviceMaster = new ServiceMaster();
	private List<CFCAttachment> documentList = new ArrayList<>();
	private String checklistFlag;
	private String checklistCheck;

	@Autowired
	private IOrganisationService orgService;

	@Autowired EquityGrantRequestMasterService equityGrantRequestMasterService;

	List<DesignationBean> designlist = new ArrayList<>();

	/**
	 * @return the stateList
	 */
	public List<LookUp> getStateList() {
		return stateList;
	}

	/**
	 * @param stateList the stateList to set
	 */
	public void setStateList(List<LookUp> stateList) {
		this.stateList = stateList;
	}

	/**
	 * @return the districtList
	 */
	public List<LookUp> getDistrictList() {
		return districtList;
	}

	/**
	 * @param districtList the districtList to set
	 */
	public void setDistrictList(List<LookUp> districtList) {
		this.districtList = districtList;
	}

	public EquityGrantMasterDto getDto() {
		return dto;
	}

	public void setDto(EquityGrantMasterDto dto) {
		this.dto = dto;
	}



	public String getBankName() {
		return bankName;
	}

	public void setBankName(String bankName) {
		this.bankName = bankName;
	}

	public List<DesignationBean> getDesignlist() {
		return designlist;
	}

	public void setDesignlist(List<DesignationBean> designlist) {
		this.designlist = designlist;
	}

	@Override
	public boolean saveForm()  throws FrameworkException{

		Organisation org = orgService.getActiveOrgByUlbShortCode(MainetConstants.Sfac.FPO);
		Long createdBy = UserSession.getCurrent().getEmployee().getEmpId();
		String lgIp = UserSession.getCurrent().getEmployee().getEmppiservername();
		Date newDate = new Date();
		EquityGrantMasterDto mastDto = getDto();


		if (mastDto.getCreatedBy() == null) {
			mastDto.setCreatedBy(createdBy);
			mastDto.setCreatedDate(newDate);
			mastDto.setOrgId(org.getOrgid());
			mastDto.setLgIpMac(lgIp);
			mastDto.setAppStatus(MainetConstants.WorkFlow.Status.PENDING);
			mastDto.getEquityGrantDetailDto().forEach(detDto -> {
				detDto.setCreatedBy(createdBy);
				detDto.setCreatedDate(newDate);
				detDto.setOrgId(org.getOrgid());
				detDto.setLgIpMac(lgIp);
			});
			mastDto.getEquityGrantDetailDtoBOM().forEach(detDto -> {
				detDto.setCreatedBy(createdBy);
				detDto.setCreatedDate(newDate);
				detDto.setOrgId(org.getOrgid());
				detDto.setLgIpMac(lgIp);
			});
			mastDto.getEquityGrantShareHoldingDetailDtos().forEach(detDto -> {
				detDto.setCreatedBy(createdBy);
				detDto.setCreatedDate(newDate);
				detDto.setOrgId(org.getOrgid());
				detDto.setLgIpMac(lgIp);
			});
			mastDto.getEquityGrantFunctionalCommitteeDetailDtos().forEach(detDto -> {
				detDto.setCreatedBy(createdBy);
				detDto.setCreatedDate(newDate);
				detDto.setOrgId(org.getOrgid());
				detDto.setLgIpMac(lgIp);
			});



		} else {
			mastDto.setUpdatedBy(createdBy);
			mastDto.setUpdatedDate(newDate);
			mastDto.setOrgId(org.getOrgid());
			mastDto.setLgIpMac(UserSession.getCurrent().getEmployee().getEmppiservername());
			mastDto.getEquityGrantDetailDto().forEach(detailDto -> {
				if (detailDto.getCreatedBy() == null || detailDto.getCreatedBy() == 0) {
					detailDto.setCreatedBy(createdBy);
					detailDto.setCreatedDate(newDate);
					detailDto.setOrgId(org.getOrgid());
					detailDto.setLgIpMac(lgIp);
				} else {
					detailDto.setUpdatedBy(createdBy);
					detailDto.setUpdatedDate(newDate);
					detailDto.setOrgId(org.getOrgid());
					detailDto.setLgIpMac(lgIp);
				}
			});
			mastDto.getEquityGrantDetailDtoBOM().forEach(detailDto -> {
				if (detailDto.getCreatedBy() == null || detailDto.getCreatedBy() == 0) {
					detailDto.setCreatedBy(createdBy);
					detailDto.setCreatedDate(newDate);
					detailDto.setOrgId(org.getOrgid());
					detailDto.setLgIpMac(lgIp);
				} else {
					detailDto.setUpdatedBy(createdBy);
					detailDto.setUpdatedDate(newDate);
					detailDto.setOrgId(org.getOrgid());
					detailDto.setLgIpMac(lgIp);
				}
			});
			
			mastDto.getEquityGrantShareHoldingDetailDtos().forEach(detailDto -> {
				if (detailDto.getCreatedBy() == null || detailDto.getCreatedBy() == 0) {
					detailDto.setCreatedBy(createdBy);
					detailDto.setCreatedDate(newDate);
					detailDto.setOrgId(org.getOrgid());
					detailDto.setLgIpMac(lgIp);
				} else {
					detailDto.setUpdatedBy(createdBy);
					detailDto.setUpdatedDate(newDate);
					detailDto.setOrgId(org.getOrgid());
					detailDto.setLgIpMac(lgIp);
				}
			});
			
			mastDto.getEquityGrantFunctionalCommitteeDetailDtos().forEach(detailDto -> {
				if (detailDto.getCreatedBy() == null || detailDto.getCreatedBy() == 0) {
					detailDto.setCreatedBy(createdBy);
					detailDto.setCreatedDate(newDate);
					detailDto.setOrgId(org.getOrgid());
					detailDto.setLgIpMac(lgIp);
				} else {
					detailDto.setUpdatedBy(createdBy);
					detailDto.setUpdatedDate(newDate);
					detailDto.setOrgId(org.getOrgid());
					detailDto.setLgIpMac(lgIp);
				}
			});

		}
		 
		
		ServiceMaster service = ApplicationContextProvider.getApplicationContext().getBean(ServiceMasterService.class)
				.getServiceMasterByShortCode("EGR",
						UserSession.getCurrent().getOrganisation().getOrgid());
		this.setServiceMaster(service);
		
		mastDto.setDocumentList(this.getCheckList());

		if (this.viewMode.equals(MainetConstants.FlagA)) {
			mastDto = equityGrantRequestMasterService.saveAndUpdateApplication(mastDto);
			setDto(mastDto);
			this.setSuccessMessage(getAppSession().getMessage("sfac.equity.grant.save.msg") + " " + mastDto.getAppNumber());
			return true;
		}
		else {

			mastDto = equityGrantRequestMasterService.saveAndUpdateApplication(mastDto);
			setDto(mastDto);

			this.setSuccessMessage(getAppSession().getMessage("sfac.equity.grant.update.msg")+ " " + mastDto.getAppNumber());
			return true;
		}

	}

	public String getViewMode() {
		return viewMode;
	}

	public void setViewMode(String viewMode) {
		this.viewMode = viewMode;
	}

	public List<EquityGrantMasterDto> getEquityGrantMasterDtos() {
		return equityGrantMasterDtos;
	}

	public void setEquityGrantMasterDtos(List<EquityGrantMasterDto> equityGrantMasterDtos) {
		this.equityGrantMasterDtos = equityGrantMasterDtos;
	}

	public List<DocumentDetailsVO> getCheckList() {
		return checkList;
	}

	public void setCheckList(List<DocumentDetailsVO> checkList) {
		this.checkList = checkList;
	}

	public ServiceMaster getServiceMaster() {
		return serviceMaster;
	}

	public void setServiceMaster(ServiceMaster serviceMaster) {
		this.serviceMaster = serviceMaster;
	}

	public List<CFCAttachment> getDocumentList() {
		return documentList;
	}

	public void setDocumentList(List<CFCAttachment> documentList) {
		this.documentList = documentList;
	}

	public String getChecklistFlag() {
		return checklistFlag;
	}

	public void setChecklistFlag(String checklistFlag) {
		this.checklistFlag = checklistFlag;
	}


	@SuppressWarnings("unchecked")
	public void getCheckListFromBrms() {

		final WSRequestDTO requestDTO = new WSRequestDTO();
		requestDTO.setModelName(MainetConstants.TradeLicense.CHECK_LIST_MODEL);
		WSResponseDTO response = brmsCommonService.initializeModel(requestDTO);
		if (MainetConstants.WebServiceStatus.SUCCESS.equalsIgnoreCase(response.getWsStatus())) {
			final List<Object> checklistModel = this.castResponse(response, CheckListModel.class, 0);
			final CheckListModel checkListModel2 = (CheckListModel) checklistModel.get(0);
			checkListModel2.setOrgId(UserSession.getCurrent().getOrganisation().getOrgid());
			checkListModel2.setServiceCode("EGR");

			WSRequestDTO checklistReqDto = new WSRequestDTO();
			checklistReqDto.setDataModel(checkListModel2);
			WSResponseDTO checklistRespDto = brmsCommonService.getChecklist(checklistReqDto);
			if (MainetConstants.WebServiceStatus.SUCCESS.equalsIgnoreCase(checklistRespDto.getWsStatus())
					|| MainetConstants.CommonConstants.NA.equalsIgnoreCase(checklistRespDto.getWsStatus())) {

				if (!MainetConstants.CommonConstants.NA.equalsIgnoreCase(checklistRespDto.getWsStatus())) {
					List<DocumentDetailsVO> checkListList = Collections.emptyList();
					checkListList = (List<DocumentDetailsVO>) checklistRespDto.getResponseObj();
					long cnt = 1;
					for (final DocumentDetailsVO doc : checkListList) {
						doc.setDocumentSerialNo(cnt);
						cnt++;
					}
					if ((checkListList != null) && !checkListList.isEmpty()) {
						setCheckList(checkListList);
					}
				}
			}
		}
	}
	
	@SuppressWarnings("unchecked")
	public List<Object> castResponse(final WSResponseDTO response, final Class<?> clazz, final int position) {

		Object dataModel = null;
		LinkedHashMap<Long, Object> responseMap = null;
		final List<Object> dataModelList = new ArrayList<>();
		try {
			if (MainetConstants.SUCCESS_MSG.equalsIgnoreCase(response.getWsStatus())) {
				final List<?> list = (List<?>) response.getResponseObj();
				final Object object = list.get(position);
				responseMap = (LinkedHashMap<Long, Object>) object;
				final String jsonString = new JSONObject(responseMap).toString();
				dataModel = new ObjectMapper().readValue(jsonString, clazz);
				dataModelList.add(dataModel);
			}

		} catch (final IOException e) {
			logger.error("Error Occurred during cast response object while BRMS call is success!", e);
		}

		return dataModelList;

	}

	public String getChecklistCheck() {
		return checklistCheck;
	}

	public void setChecklistCheck(String checklistCheck) {
		this.checklistCheck = checklistCheck;
	}

	public List<AttachDocs> getAttachDocsList() {
		return attachDocsList;
	}

	public void setAttachDocsList(List<AttachDocs> attachDocsList) {
		this.attachDocsList = attachDocsList;
	}

	
}
