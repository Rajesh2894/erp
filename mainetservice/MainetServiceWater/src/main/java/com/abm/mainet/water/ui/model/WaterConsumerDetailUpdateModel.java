package com.abm.mainet.water.ui.model;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.abm.mainet.common.constant.MainetConstants;
import com.abm.mainet.common.constant.PrefixConstants;
import com.abm.mainet.common.domain.Organisation;
import com.abm.mainet.common.dto.ApplicantDetailDTO;
import com.abm.mainet.common.integration.dms.domain.CFCAttachment;
import com.abm.mainet.common.integration.dms.dto.FileUploadDTO;
import com.abm.mainet.common.integration.dms.service.IFileUploadService;
import com.abm.mainet.common.integration.dto.DocumentDetailsVO;
import com.abm.mainet.common.integration.dto.TbBillDet;
import com.abm.mainet.common.integration.dto.TbBillMas;
import com.abm.mainet.common.service.IFinancialYearService;
import com.abm.mainet.common.ui.model.AbstractFormModel;
import com.abm.mainet.common.utility.ApplicationContextProvider;
import com.abm.mainet.common.utility.ApplicationSession;
import com.abm.mainet.common.utility.CommonMasterUtility;
import com.abm.mainet.common.utility.LookUp;
import com.abm.mainet.common.utility.UserSession;
import com.abm.mainet.water.dto.MeterDetailsEntryDTO;
import com.abm.mainet.water.dto.NewWaterConnectionReqDTO;
import com.abm.mainet.water.dto.TbCsmrInfoDTO;
import com.abm.mainet.water.dto.TbKLinkCcnDTO;
import com.abm.mainet.water.dto.WaterDataEntrySearchDTO;
import com.abm.mainet.water.service.NewWaterConnectionService;
import com.abm.mainet.water.ui.validator.WaterDataEntrySuiteValidator;

@Component
@Scope("session")
public class WaterConsumerDetailUpdateModel extends AbstractFormModel{

	private static final long serialVersionUID = 1065735217179929202L;
	

	@Autowired
	private NewWaterConnectionService newWaterConnectionService;
	

    @Autowired
    private IFileUploadService fileUpload;


	private WaterDataEntrySearchDTO searchDTO = new WaterDataEntrySearchDTO();
	
	private TbCsmrInfoDTO csmrInfo = new TbCsmrInfoDTO();

	private NewWaterConnectionReqDTO newConnectionDto = new NewWaterConnectionReqDTO();
	
	private List<DocumentDetailsVO> attachments = new ArrayList<>();

	private List<CFCAttachment> attachDocsList = new ArrayList<>();
	private String detShowFlag;
	
	private MeterDetailsEntryDTO meterData = new MeterDetailsEntryDTO();
	private List<TbBillMas> billMasList = new LinkedList<>();
	
	private String isConsumerSame = MainetConstants.NewWaterServiceConstants.YES;
	
	private String isBillingSame = MainetConstants.NewWaterServiceConstants.YES;
	
	
	@Override
	public boolean saveForm() {
		 if (!checkDocumentList()) {
             return false;
         }
		UserSession session = UserSession.getCurrent();
		getNewConnectionDto().setUserId(session.getEmployee().getEmpId());
		getNewConnectionDto().setOrgId(session.getOrganisation().getOrgid());
		getNewConnectionDto().setLangId((long) session.getLanguageId());
		getNewConnectionDto().setLgIpMac(session.getEmployee().getEmppiservername());
		getCsmrInfo().setCsEntryFlag("D");
		getNewConnectionDto().setCsmrInfo(getCsmrInfo());
		getNewConnectionDto().setApplicationId(getCsmrInfo().getApplicationNo());
		//saveWaterDataEntryWithoutArrears();
		validateBean(this, WaterDataEntrySuiteValidator.class);

		if (hasValidationErrors()) {
			return false;
		}
		  ApplicationContextProvider.getApplicationContext().getBean(IFileUploadService.class).prepareFileUpload(attachments);
		  getNewConnectionDto().setUploadDocument(attachments);
		  if ((getNewConnectionDto().getUploadDocument() != null) && !getNewConnectionDto().getUploadDocument().isEmpty()) {
				final boolean isUploaded = fileUpload.doFileUpload(getNewConnectionDto().getUploadDocument(),
						getNewConnectionDto());
		  }
			newWaterConnectionService.updateWaterDataEntry(getNewConnectionDto(), getMeterData(), getBillMasList());
			setSuccessMessage(getAppSession().getMessage("water.dataentry.update",
					new Object[] { getNewConnectionDto().getCsmrInfo().getCsCcn() }));
		
		return true;
	}
	
	private void validateBean(WaterConsumerDetailUpdateModel waterConsumerDetailUpdateModel,
			Class<WaterDataEntrySuiteValidator> class1) {
		
		
	}

	private void saveWaterDataEntryWithoutArrears() {
		UserSession session = UserSession.getCurrent();
		NewWaterConnectionReqDTO connectionDTO = this.getNewConnectionDto();
		TbCsmrInfoDTO infoDTO = this.getCsmrInfo();
		ApplicantDetailDTO appDTO = connectionDTO.getApplicantDTO();
		Organisation organisation = new Organisation();
		organisation.setOrgid(UserSession.getCurrent().getOrganisation().getOrgid());

		connectionDTO.setUserId(session.getEmployee().getEmpId());
		connectionDTO.setOrgId(session.getOrganisation().getOrgid());
		connectionDTO.setLangId((long) session.getLanguageId());
		connectionDTO.setLgIpMac(session.getEmployee().getEmppiservername());

		if (this.getIsConsumerSame().equalsIgnoreCase("Y")) {
			connectionDTO.setIsConsumer("Y");
			infoDTO.setCsName(infoDTO.getCsOname());
			if (infoDTO.getCsOGender() != null && infoDTO.getCsOGender() != 0l) {
				infoDTO.setCsGender(infoDTO.getCsOGender());
			}
			infoDTO.setCsContactno(infoDTO.getCsOcontactno());
			infoDTO.setCsEmail(infoDTO.getCsOEmail());
			infoDTO.setCsAdd(infoDTO.getCsOadd());
			if (infoDTO.getOpincode() != null && !infoDTO.getOpincode().isEmpty()) {
				infoDTO.setCsCpinCode(Long.valueOf(infoDTO.getOpincode()));
			}
		}
	

		connectionDTO.setCityName(infoDTO.getCsAdd());
		connectionDTO.setFlatBuildingNo(infoDTO.getCsAdd());
		connectionDTO.setRoadName(infoDTO.getCsAdd());
		if (infoDTO.getCsCpinCode() != null) {
			connectionDTO.setPinCode(infoDTO.getCsCpinCode());
			connectionDTO.setPincodeNo(infoDTO.getCsCpinCode());
		}
		connectionDTO.setAreaName(infoDTO.getCsAdd());
		connectionDTO.setBldgName(infoDTO.getCsAdd());
		connectionDTO.setBlockName(infoDTO.getCsAdd());
		// connectionDTO.setBlockNo(infoDTO.getCsAdd());
		connectionDTO.setFlatBuildingNo(infoDTO.getCsAdd());
		if (infoDTO.getCsGender() != null && infoDTO.getCsGender() != 0l) {
			connectionDTO.setGender(String.valueOf(infoDTO.getCsGender()));
		}
		connectionDTO.setMobileNo(infoDTO.getCsContactno());
		connectionDTO.setEmail(infoDTO.getCsEmail());
		connectionDTO.setAadhaarNo(connectionDTO.getApplicantDTO().getAadharNo());

		appDTO.setApplicantFirstName(infoDTO.getCsName());
		appDTO.setAreaName(infoDTO.getCsAdd());
		appDTO.setRoadName(infoDTO.getCsAdd());
		appDTO.setMobileNo(infoDTO.getCsContactno());
		appDTO.setEmailId(infoDTO.getCsEmail());
		if (infoDTO.getCsCpinCode() != null) {
			appDTO.setPinCode(infoDTO.getCsCpinCode().toString());
		}
		final List<LookUp> lookUps = CommonMasterUtility.getLookUps(MainetConstants.GENDER, organisation);
		for (final LookUp lookUp : lookUps) {
			if ((infoDTO.getCsOGender() != null) && infoDTO.getCsOGender() != 0l) {
				if (lookUp.getLookUpId() == infoDTO.getCsOGender()) {
					appDTO.setGender(lookUp.getLookUpCode());
					break;
				}
			}

		}
		appDTO.setAreaName(infoDTO.getCsAdd());
		final List<TbKLinkCcnDTO> existingLinkDetails = getCsmrInfo().getLinkDetails();
		if (connectionDTO.getExistingConsumerNumber() != null) {
			if (connectionDTO.getExistingConsumerNumber().equals(MainetConstants.FlagY)) {
				if (existingLinkDetails != null && !existingLinkDetails.isEmpty()) {
					for (final TbKLinkCcnDTO link : existingLinkDetails) {

						if (link.getLcId() != 0) {
							link.setUserIds(UserSession.getCurrent().getEmployee().getEmpId());
							link.setLmodDate(new Date());
							link.setLgIpMac(UserSession.getCurrent().getEmployee().getEmppiservername());
						} else {
							link.setIsDeleted(PrefixConstants.NewWaterServiceConstants.NO);
							link.setUserIds(UserSession.getCurrent().getEmployee().getEmpId());
							link.setLmodDate(new Date());
							link.setLgIpMac(UserSession.getCurrent().getEmployee().getEmppiservername());
						}

					}
				}
			} else {
				if (existingLinkDetails != null && !existingLinkDetails.isEmpty()) {
					for (final TbKLinkCcnDTO link : existingLinkDetails) {
						link.setIsDeleted(PrefixConstants.NewWaterServiceConstants.YES);
					}
				}
			}
		}
		getCsmrInfo().setLinkDetails(existingLinkDetails);

		connectionDTO.setCsmrInfo(infoDTO);
	}

	
    // Validation for File Upload
    public boolean checkDocumentList() {
        boolean flag = true;
        final List<DocumentDetailsVO> docList = fileUpload.prepareFileUpload(getAttachments());
        if ((docList != null) && !docList.isEmpty()) {
            for (final DocumentDetailsVO doc : docList) {
                if ((doc.getDocumentByteCode() == null) || doc.getDocumentByteCode().isEmpty()) {
                    addValidationError(
                            ApplicationSession.getInstance().getMessage("council.proposal.validation.document"));
                    flag = false;
                }
            }
        }
        return flag;
    }
	public WaterDataEntrySearchDTO getSearchDTO() {
		return searchDTO;
	}

	public void setSearchDTO(WaterDataEntrySearchDTO searchDTO) {
		this.searchDTO = searchDTO;
	}

	public TbCsmrInfoDTO getCsmrInfo() {
		return csmrInfo;
	}

	public void setCsmrInfo(TbCsmrInfoDTO csmrInfo) {
		this.csmrInfo = csmrInfo;
	}


	public NewWaterConnectionReqDTO getNewConnectionDto() {
		return newConnectionDto;
	}

	public void setNewConnectionDto(NewWaterConnectionReqDTO newConnectionDto) {
		this.newConnectionDto = newConnectionDto;
	}

	public List<DocumentDetailsVO> getAttachments() {
		return attachments;
	}

	public void setAttachments(List<DocumentDetailsVO> attachments) {
		this.attachments = attachments;
	}

	public List<CFCAttachment> getAttachDocsList() {
		return attachDocsList;
	}

	public void setAttachDocsList(List<CFCAttachment> attachDocsList) {
		this.attachDocsList = attachDocsList;
	}

	public String getDetShowFlag() {
		return detShowFlag;
	}

	public void setDetShowFlag(String detShowFlag) {
		this.detShowFlag = detShowFlag;
	}

	public MeterDetailsEntryDTO getMeterData() {
		return meterData;
	}

	public void setMeterData(MeterDetailsEntryDTO meterData) {
		this.meterData = meterData;
	}

	public List<TbBillMas> getBillMasList() {
		return billMasList;
	}

	public void setBillMasList(List<TbBillMas> billMasList) {
		this.billMasList = billMasList;
	}

	public String getIsConsumerSame() {
		return isConsumerSame;
	}

	public void setIsConsumerSame(String isConsumerSame) {
		this.isConsumerSame = isConsumerSame;
	}

	public String getIsBillingSame() {
		return isBillingSame;
	}

	public void setIsBillingSame(String isBillingSame) {
		this.isBillingSame = isBillingSame;
	}


	
}
