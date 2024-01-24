/**
 * 
 */
package com.abm.mainet.tradeLicense.ui.model;

import java.io.File;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.abm.mainet.common.constant.MainetConstants;
import com.abm.mainet.common.dto.ApplicantDetailDTO;
import com.abm.mainet.common.integration.dms.domain.AttachDocs;
import com.abm.mainet.common.integration.dms.fileUpload.FileUploadUtility;
import com.abm.mainet.common.integration.dms.service.IAttachDocsService;
import com.abm.mainet.common.integration.dms.service.IFileUploadService;
import com.abm.mainet.common.integration.dto.DocumentDetailsVO;
import com.abm.mainet.common.integration.dto.RequestDTO;
import com.abm.mainet.common.ui.model.AbstractFormModel;
import com.abm.mainet.common.utility.ApplicationContextProvider;
import com.abm.mainet.common.utility.ApplicationSession;
import com.abm.mainet.common.utility.CommonMasterUtility;
import com.abm.mainet.common.utility.LookUp;
import com.abm.mainet.common.utility.UserSession;
import com.abm.mainet.common.utility.Utility;
import com.abm.mainet.tradeLicense.dto.TradeMasterDetailDTO;
import com.abm.mainet.tradeLicense.service.ITradeLicenseApplicationService;

/**
 * @author Saiprasad.Vengurekar
 *
 */

@Component
@Scope("session")
public class DataEntrySuiteModel extends AbstractFormModel {

    @Autowired
    IFileUploadService fileUpload;

    private static final long serialVersionUID = 823516077594771567L;

    private ApplicantDetailDTO applicantDetailDto = new ApplicantDetailDTO();
    private TradeMasterDetailDTO tradeMasterDetailDTO = new TradeMasterDetailDTO();

    private String saveMode;
    private List<DocumentDetailsVO> attachments = new ArrayList<>();
    private List<AttachDocs> attachDocsList = new ArrayList<>();
    private String removeCommonFileById;
    private String removedIds;
    private String ownershipPrefix;
    private List<TradeMasterDetailDTO> masList = new ArrayList<>();
    private String removedOwnIds;
    private String propertyActive;
    private String viewMode;
    private String gisValue;
    private String printable;
    public String getSaveMode() {
        return saveMode;
    }

    public void setSaveMode(String saveMode) {
        this.saveMode = saveMode;
    }

    public ApplicantDetailDTO getApplicantDetailDto() {
        return applicantDetailDto;
    }

    public void setApplicantDetailDto(ApplicantDetailDTO applicantDetailDto) {
        this.applicantDetailDto = applicantDetailDto;
    }

    public TradeMasterDetailDTO getTradeMasterDetailDTO() {
        return tradeMasterDetailDTO;
    }

    public void setTradeMasterDetailDTO(TradeMasterDetailDTO tradeMasterDetailDTO) {
        this.tradeMasterDetailDTO = tradeMasterDetailDTO;
    }

    public List<DocumentDetailsVO> getAttachments() {
        return attachments;
    }

    public void setAttachments(List<DocumentDetailsVO> attachments) {
        this.attachments = attachments;
    }

    public List<AttachDocs> getAttachDocsList() {
        return attachDocsList;
    }

    public void setAttachDocsList(List<AttachDocs> attachDocsList) {
        this.attachDocsList = attachDocsList;
    }

    public String getRemoveCommonFileById() {
        return removeCommonFileById;
    }

    public void setRemoveCommonFileById(String removeCommonFileById) {
        this.removeCommonFileById = removeCommonFileById;
    }

    public String getRemovedIds() {
        return removedIds;
    }

    public void setRemovedIds(String removedIds) {
        this.removedIds = removedIds;
    }
    

    public String getGisValue() {
		return gisValue;
	}

	public void setGisValue(String gisValue) {
		this.gisValue = gisValue;
	}

	@Override
    public boolean saveForm() {
        Long orgId = UserSession.getCurrent().getOrganisation().getOrgid();
        Long createdBy = UserSession.getCurrent().getEmployee().getEmpId();
        String lgIp = UserSession.getCurrent().getEmployee().getEmppiservername();
        LookUp lookUp = CommonMasterUtility.getValueFromPrefixLookUp("I", "LIS", UserSession.getCurrent().getOrganisation());
        List<Long> removeItemIds = new ArrayList<>();
        List<Long> removeOwnIds = new ArrayList<>();
        Date newDate = new Date();
        TradeMasterDetailDTO masDto = getTradeMasterDetailDTO();
        boolean  envFlag=Utility.isEnvPrefixAvailable(UserSession.getCurrent().getOrganisation(), MainetConstants.APP_NAME.TSCL);
        if (masDto.getCreatedBy() == null) {
            masDto.setTrdEty(MainetConstants.FlagD);
            masDto.setTrdStatus(lookUp.getLookUpId());
            masDto.setCreatedBy(createdBy);
            masDto.setCreatedDate(newDate);
            masDto.setOrgid(orgId);
            masDto.setLgIpMac(UserSession.getCurrent().getEmployee().getEmppiservername());
            masDto.getTradeLicenseOwnerdetailDTO().forEach(ownDto -> {
            //Defect #138350
            	if(envFlag) {
            		String ownwrName ="";
            		String ownwrFathName = "";
            		ownwrName=(ownDto.getOwnFname()!=null?ownDto.getOwnFname():"");
            		ownwrName+=(ownDto.getOwnMname()!=null?" "+ownDto.getOwnMname():"");
            		ownwrName+=(ownDto.getOwnLname()!=null?" "+ownDto.getOwnLname():"");
            		ownwrFathName=(ownDto.getGurdFname()!=null?ownDto.getGurdFname():"");
            		ownwrFathName+=(ownDto.getGurdMname()!=null?" "+ownDto.getGurdMname():"");
            		ownwrFathName+=(ownDto.getGurdLname()!=null?" "+ownDto.getGurdLname():"");
            		ownDto.setTroName(ownwrName);
            		ownDto.setTroMname(ownwrFathName);        		
            	}
                ownDto.setCreatedBy(createdBy);
                ownDto.setCreatedDate(newDate);
                ownDto.setOrgid(orgId);
                ownDto.setLgIpMac(lgIp);
                ownDto.setTroPr(MainetConstants.FlagA);
            });

            masDto.getTradeLicenseItemDetailDTO().forEach(itemDto -> {
                itemDto.setCreatedBy(createdBy);
                itemDto.setCreatedDate(newDate);
                itemDto.setOrgid(orgId);
                itemDto.setLgIpMac(lgIp);
                itemDto.setTriStatus(MainetConstants.FlagA);
            });

        } else {
            masDto.setTrdStatus(lookUp.getLookUpId());
            masDto.setTrdEty(MainetConstants.FlagD);
            masDto.setUpdatedBy(createdBy);
            masDto.setUpdatedDate(newDate);
            masDto.setOrgid(orgId);
            masDto.setLgIpMac(UserSession.getCurrent().getEmployee().getEmppiservername());
            masDto.getTradeLicenseOwnerdetailDTO().forEach(ownDto -> {
                if (ownDto.getCreatedBy() == null) {
                    ownDto.setCreatedBy(createdBy);
                    ownDto.setCreatedDate(newDate);
                    ownDto.setOrgid(orgId);
                    ownDto.setLgIpMac(lgIp);
                    ownDto.setTroPr(MainetConstants.FlagA);
                } else {
                    ownDto.setUpdatedBy(createdBy);
                    ownDto.setUpdatedDate(newDate);
                    ownDto.setOrgid(orgId);
                    ownDto.setLgIpMac(lgIp);
                }
            });

            masDto.getTradeLicenseItemDetailDTO().forEach(itemDto -> {
                if (itemDto.getCreatedBy() == null) {
                    itemDto.setCreatedBy(createdBy);
                    itemDto.setCreatedDate(newDate);
                    itemDto.setOrgid(orgId);
                    itemDto.setLgIpMac(lgIp);
                    itemDto.setTriStatus(MainetConstants.FlagA);
                } else {
                    itemDto.setUpdatedBy(createdBy);
                    itemDto.setUpdatedDate(newDate);
                    itemDto.setOrgid(orgId);
                    itemDto.setLgIpMac(lgIp);
                }
            });

        }

        String ids = getRemovedIds();
        if (ids != null && !ids.isEmpty()) {
            String array[] = ids.split(MainetConstants.operator.COMMA);
            for (String id : array) {
                removeItemIds.add(Long.valueOf(id));
            }
        }
        String ownIds = getRemovedOwnIds();
        if (ownIds != null && !ownIds.isEmpty()) {
            String array[] = ownIds.split(MainetConstants.operator.COMMA);
            for (String id : array) {
                removeOwnIds.add(Long.valueOf(id));
            }
        }
        masDto.setRemoveItemIds(removeItemIds);
        masDto.getOwnIds().addAll(removeOwnIds);
        masDto = ApplicationContextProvider.getApplicationContext().getBean(ITradeLicenseApplicationService.class)
                .saveTradeApplicationDataSuite(masDto);
        if (masDto.getTrdLicno() != null && !masDto.getTrdLicno().isEmpty()) {
            RequestDTO requestDTO = new RequestDTO();
            requestDTO.setOrgId(UserSession.getCurrent().getOrganisation().getOrgid());
            requestDTO.setStatus(MainetConstants.FlagA);
            requestDTO.setDepartmentName("TL");
            requestDTO.setIdfId(masDto.getTrdLicno());
            requestDTO.setUserId(UserSession.getCurrent().getEmployee().getEmpId());
            List<DocumentDetailsVO> dto = getCommonFileAttachment();

            setCommonFileAttachment(fileUpload.setFileUploadMethod(new ArrayList<DocumentDetailsVO>()));
            int i = 0;
            for (final Map.Entry<Long, Set<File>> entry : FileUploadUtility.getCurrent().getFileMap().entrySet()) {
                getCommonFileAttachment().get(i)
                        .setDoc_DESC_ENGL(dto.get(entry.getKey().intValue()).getDoc_DESC_ENGL());
                i++;
            }
            fileUpload.doMasterFileUpload(getCommonFileAttachment(), requestDTO);
            List<Long> enclosureRemoveById = null;
            String fileId = getRemoveCommonFileById();
            if (fileId != null && !fileId.isEmpty()) {
                enclosureRemoveById = new ArrayList<>();
                String fileArray[] = fileId.split(MainetConstants.operator.COMMA);
                for (String fields : fileArray) {
                    enclosureRemoveById.add(Long.valueOf(fields));
                }
            }
            if (enclosureRemoveById != null && !enclosureRemoveById.isEmpty()) {
                ApplicationContextProvider.getApplicationContext().getBean(IAttachDocsService.class).deleteDocFileById(
                        enclosureRemoveById,
                        UserSession.getCurrent().getEmployee().getEmpId());
            }
        }
        if (saveMode.equals(MainetConstants.MODE_CREATE)) {
            this.setSuccessMessage(getAppSession().getMessage("trade.dataEntry.successLicenseMsg") + masDto.getTrdLicno());

        } else {
            setSuccessMessage(
                    ApplicationSession.getInstance().getMessage("Trade License Data Entry Suite Updated Successfully"));
        }
        return true;
    }

    public String getOwnershipPrefix() {
        return ownershipPrefix;
    }

    public void setOwnershipPrefix(String ownershipPrefix) {
        this.ownershipPrefix = ownershipPrefix;
    }

    public List<TradeMasterDetailDTO> getMasList() {
        return masList;
    }

    public void setMasList(List<TradeMasterDetailDTO> masList) {
        this.masList = masList;
    }

    public String getRemovedOwnIds() {
        return removedOwnIds;
    }

    public void setRemovedOwnIds(String removedOwnIds) {
        this.removedOwnIds = removedOwnIds;
    }

    public String getPropertyActive() {
        return propertyActive;
    }

    public void setPropertyActive(String propertyActive) {
        this.propertyActive = propertyActive;
    }

    public String getViewMode() {
        return viewMode;
    }

    public void setViewMode(String viewMode) {
        this.viewMode = viewMode;
    }

	

	public String getPrintable() {
		return printable;
	}

	public void setPrintable(String printable) {
		this.printable = printable;
	}

}
