package com.abm.mainet.property.ui.model;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;
import org.springframework.web.context.WebApplicationContext;

import com.abm.mainet.common.constant.MainetConstants;
import com.abm.mainet.common.domain.Organisation;
import com.abm.mainet.common.integration.dms.domain.AttachDocs;
import com.abm.mainet.common.integration.dms.fileUpload.FileUploadUtility;
import com.abm.mainet.common.integration.dms.service.IFileUploadService;
import com.abm.mainet.common.integration.dto.DocumentDetailsVO;
import com.abm.mainet.common.integration.dto.RequestDTO;
import com.abm.mainet.common.ui.model.AbstractFormModel;
import com.abm.mainet.common.utility.CommonMasterUtility;
import com.abm.mainet.common.utility.LookUp;
import com.abm.mainet.common.utility.UserSession;
import com.abm.mainet.property.dto.ProvisionalAssesmentMstDto;
import com.abm.mainet.property.dto.ProvisionalAssesmentOwnerDtlDto;
import com.abm.mainet.property.service.AssesmentMastService;

/**
 * 
 * @author anwarul.hassan
 *
 * @since 11-Sep-2020
 */
@Component
@Scope(value = WebApplicationContext.SCOPE_SESSION)
public class UpdateDataEntrySuiteModel extends AbstractFormModel {

    private static final long serialVersionUID = 1L;
    @Autowired
    private AssesmentMastService assesmentMastService;
    @Autowired
    private IFileUploadService fileUpload;
    private ProvisionalAssesmentMstDto provisionalAssesmentMstDto;
    private ProvisionalAssesmentOwnerDtlDto provisionalAssesmentOwnerDtlDto;
    private String searchFlag;
    private String ownershipPrefix;
    private String ownershipTypeValue;
    private List<LookUp> location = new ArrayList<>(0);
    private Long deptId;
    private String modeType;
    private String existOldPropNo;
    private List<AttachDocs> attachDocsList = new ArrayList<>();

    public void setDropDownValues(Organisation org) {
        this.getProvisionalAssesmentMstDto().setProAssOwnerTypeName(CommonMasterUtility
                .getNonHierarchicalLookUpObject(provisionalAssesmentMstDto.getAssOwnerType(), org).getDescLangFirst());
        String ownerTypeCode = CommonMasterUtility
                .getNonHierarchicalLookUpObject(getProvisionalAssesmentMstDto().getAssOwnerType(), org).getLookUpCode();
        if (MainetConstants.Property.SO.equals(ownerTypeCode) || MainetConstants.Property.JO.equals(ownerTypeCode)) {
            for (ProvisionalAssesmentOwnerDtlDto dto : getProvisionalAssesmentMstDto().getProvisionalAssesmentOwnerDtlDtoList()) {
                dto.setProAssGenderId(
                        CommonMasterUtility.getNonHierarchicalLookUpObject(dto.getGenderId(), org).getDescLangFirst());
                dto.setProAssRelationId(
                        CommonMasterUtility.getNonHierarchicalLookUpObject(dto.getRelationId(), org).getDescLangFirst());
            }
        }
        this.getLocation().forEach(loca -> {
            if (loca.getLookUpId() == provisionalAssesmentMstDto.getLocId()) {
                this.getProvisionalAssesmentMstDto().setLocationName(loca.getDescLangFirst());
            }
        });
    }

    public void prepareContractDocumentsData(ProvisionalAssesmentMstDto provisionalAssesmentMstDto) {
        RequestDTO requestDTO = new RequestDTO();
        requestDTO.setOrgId(UserSession.getCurrent().getOrganisation().getOrgid());
        requestDTO.setStatus(MainetConstants.FlagA);
        requestDTO.setDepartmentName(MainetConstants.Property.PROP_DEPT_SHORT_CODE);
        requestDTO.setIdfId(provisionalAssesmentMstDto.getAssNo());
        requestDTO.setUserId(UserSession.getCurrent().getEmployee().getEmpId());
        List<DocumentDetailsVO> dto = getCommonFileAttachment();
        setCommonFileAttachment(fileUpload.setFileUploadMethod(new ArrayList<DocumentDetailsVO>()));
        int i = 0;
        for (final Map.Entry<Long, Set<File>> entry : FileUploadUtility.getCurrent().getFileMap().entrySet()) {
            getCommonFileAttachment().get(i).setDoc_DESC_ENGL(dto.get(entry.getKey().intValue()).getDoc_DESC_ENGL());
            i++;
        }
        fileUpload.doMasterFileUpload(getCommonFileAttachment(), requestDTO);
    }

    public ProvisionalAssesmentMstDto getProvisionalAssesmentMstDto() {
        return provisionalAssesmentMstDto;
    }

    public void setProvisionalAssesmentMstDto(ProvisionalAssesmentMstDto provisionalAssesmentMstDto) {
        this.provisionalAssesmentMstDto = provisionalAssesmentMstDto;
    }

    public ProvisionalAssesmentOwnerDtlDto getProvisionalAssesmentOwnerDtlDto() {
        return provisionalAssesmentOwnerDtlDto;
    }

    public void setProvisionalAssesmentOwnerDtlDto(ProvisionalAssesmentOwnerDtlDto provisionalAssesmentOwnerDtlDto) {
        this.provisionalAssesmentOwnerDtlDto = provisionalAssesmentOwnerDtlDto;
    }

    public String getSearchFlag() {
        return searchFlag;
    }

    public void setSearchFlag(String searchFlag) {
        this.searchFlag = searchFlag;
    }

    public String getOwnershipPrefix() {
        return ownershipPrefix;
    }

    public void setOwnershipPrefix(String ownershipPrefix) {
        this.ownershipPrefix = ownershipPrefix;
    }

    public String getOwnershipTypeValue() {
        return ownershipTypeValue;
    }

    public void setOwnershipTypeValue(String ownershipTypeValue) {
        this.ownershipTypeValue = ownershipTypeValue;
    }

    public List<LookUp> getLocation() {
        return location;
    }

    public void setLocation(List<LookUp> location) {
        this.location = location;
    }

    public Long getDeptId() {
        return deptId;
    }

    public void setDeptId(Long deptId) {
        this.deptId = deptId;
    }

    public String getModeType() {
        return modeType;
    }

    public void setModeType(String modeType) {
        this.modeType = modeType;
    }

    public String getExistOldPropNo() {
		return existOldPropNo;
	}

	public void setExistOldPropNo(String existOldPropNo) {
		this.existOldPropNo = existOldPropNo;
	}

	public List<AttachDocs> getAttachDocsList() {
        return attachDocsList;
    }

    public void setAttachDocsList(List<AttachDocs> attachDocsList) {
        this.attachDocsList = attachDocsList;
    }

}
