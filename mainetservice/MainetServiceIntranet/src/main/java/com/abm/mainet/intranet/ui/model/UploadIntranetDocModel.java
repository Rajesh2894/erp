package com.abm.mainet.intranet.ui.model;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;
import org.springframework.web.context.WebApplicationContext;

import com.abm.mainet.common.constant.MainetConstants;
import com.abm.mainet.common.integration.dms.domain.AttachDocs;
import com.abm.mainet.common.integration.dms.dto.FileUploadDTO;
import com.abm.mainet.common.integration.dms.service.IAttachDocsService;
import com.abm.mainet.common.integration.dms.service.IFileUploadService;
import com.abm.mainet.common.integration.dto.DocumentDetailsVO;
import com.abm.mainet.common.master.service.TbDepartmentService;
import com.abm.mainet.common.ui.model.AbstractFormModel;
import com.abm.mainet.common.utility.ApplicationContextProvider;
import com.abm.mainet.common.utility.UserSession;
import com.abm.mainet.intranet.domain.IntranetMaster;
import com.abm.mainet.intranet.dto.report.IntranetDTO;
import com.abm.mainet.intranet.service.IIntranetUploadService;

@Component
@Scope(value=WebApplicationContext.SCOPE_SESSION)
public class UploadIntranetDocModel extends AbstractFormModel {

	private static final long serialVersionUID = 1L;
	
	private IntranetDTO intranetDto = new IntranetDTO();
	
	private List<DocumentDetailsVO> uploadFileList = new ArrayList<>();
	
	private List<IntranetMaster> fetchIntranetListMas = new ArrayList<>();
	
	private List<IntranetDTO> fetchintranetDtoList = new ArrayList<>();
	
	private List<DocumentDetailsVO> attachments = new ArrayList<>();
	
	private List<AttachDocs> attachDocsList = new ArrayList<>();
	
	private String saveMode;
	
    @Autowired
	private IIntranetUploadService intranetUploadService; 
    
	@Resource
	IFileUploadService fileUpload;
	
    @Autowired
    private TbDepartmentService tbDepartmentService;
    
    @Autowired
    private IAttachDocsService iAttachDocsService;
	
	@Override
	public boolean saveForm() {
		
		Long langId = UserSession.getCurrent().getOrganisation().getOrgid();
		Long userId = UserSession.getCurrent().getEmployee().getEmpId();
		Long orgId = UserSession.getCurrent().getOrganisation().getOrgid();
		IntranetDTO dto = intranetUploadService.saveIntranetData(intranetDto, langId, userId, orgId, null);
		
		/*
		String DB_BACK_SLACE = "\\";
		RequestDTO requestDTO1 = new RequestDTO();
        requestDTO1.setOrgId(orgId);
        requestDTO1.setStatus(MainetConstants.FlagA);
        requestDTO1.setDepartmentName(tbDepartmentService.findDepartmentShortCodeByDeptId(dto.getDeptId(), orgId));
        requestDTO1.setIdfId("IntranetDoc" + DB_BACK_SLACE + dto.getInId());
        requestDTO1.setUserId(userId);
        requestDTO1.setLangId(langId);
        requestDTO1.setDeptId(dto.getDeptId());
		setUploadFileList(fileUpload.setFileUploadMethod(getUploadFileList()));
        fileUpload.doMasterFileUpload(getUploadFileList(), requestDTO1);
        */
		
		//
        FileUploadDTO requestDTO = new FileUploadDTO();
        requestDTO.setOrgId(orgId);
        requestDTO.setStatus(MainetConstants.FlagA);
		requestDTO.setDepartmentName(tbDepartmentService.findDepartmentShortCodeByDeptId(dto.getDeptId(), orgId));
		requestDTO.setUserId(userId);
		requestDTO.setIdfId(String.valueOf(dto.getInId()));
		setAttachments(ApplicationContextProvider.getApplicationContext().getBean(IFileUploadService.class).setFileUploadMethod(new ArrayList<DocumentDetailsVO>()));
		
		List<AttachDocs> ListAtt = iAttachDocsService.findByCode(requestDTO.getOrgId(), requestDTO.getIdfId());
		if(ListAtt.isEmpty()) {
			fileUpload.doMasterFileUpload(getAttachments(), requestDTO);
		}
		else {
			for(AttachDocs attDocs: ListAtt) {
				if(requestDTO.getIdfId().equals(attDocs.getIdfId())) {
					if (intranetDto.getSaveMode().equals("E")){
						if(!getAttachments().isEmpty()) {
							getAttachments().get(0).setAttachmentId(attDocs.getAttId());
							fileUpload.doMasterFileUpload(getAttachments(), requestDTO);
						}
					}
				}
			}
		}
		
        List<AttachDocs> attachDocs = iAttachDocsService.findByCode(orgId, requestDTO.getIdfId());
        intranetUploadService.saveIntranetData(intranetDto, langId, userId, orgId, attachDocs);
        
        this.setSuccessMessage(getAppSession().getMessage("intranet.intUpldFrmSave"));

        
		return true;
		
	}
	
	public IntranetDTO getIntranetDto() {
		return intranetDto;
	}

	public void setIntranetDto(IntranetDTO intranetDto) {
		this.intranetDto = intranetDto;
	}

	public List<DocumentDetailsVO> getUploadFileList() {
		return uploadFileList;
	}

	public void setUploadFileList(List<DocumentDetailsVO> uploadFileList) {
		this.uploadFileList = uploadFileList;
	}

	public List<IntranetMaster> getFetchIntranetListMas() {
		return fetchIntranetListMas;
	}

	public void setFetchIntranetListMas(List<IntranetMaster> fetchIntranetListMas) {
		this.fetchIntranetListMas = fetchIntranetListMas;
	}

	public String getSaveMode() {
		return saveMode;
	}

	public void setSaveMode(String saveMode) {
		this.saveMode = saveMode;
	}

	public List<IntranetDTO> getFetchintranetDtoList() {
		return fetchintranetDtoList;
	}

	public void setFetchintranetDtoList(List<IntranetDTO> fetchintranetDtoList) {
		this.fetchintranetDtoList = fetchintranetDtoList;
	}

	public List<DocumentDetailsVO> getAttachments() {
		return attachments;
	}

	public void setAttachments(List<DocumentDetailsVO> attachments) {
		this.attachments = attachments;
	}
	
	
}
