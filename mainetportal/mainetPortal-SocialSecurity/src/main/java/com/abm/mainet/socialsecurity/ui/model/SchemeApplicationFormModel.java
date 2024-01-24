/**
 * 
 */
package com.abm.mainet.socialsecurity.ui.model;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;
import org.springframework.web.context.WebApplicationContext;
import com.abm.mainet.common.constant.MainetConstants;
import com.abm.mainet.common.domain.Organisation;
import com.abm.mainet.common.domain.PortalService;
import com.abm.mainet.common.dto.BankMasterDTO;
import com.abm.mainet.common.dto.DocumentDetailsVO;
import com.abm.mainet.common.exception.FrameworkException;
import com.abm.mainet.common.service.IFileUploadService;
import com.abm.mainet.common.service.IPortalServiceMasterService;
import com.abm.mainet.common.ui.model.AbstractFormModel;
import com.abm.mainet.common.util.CommonMasterUtility;
import com.abm.mainet.common.util.LookUp;
import com.abm.mainet.common.util.UserSession;
import com.abm.mainet.common.utility.ApplicationContextProvider;
import com.abm.mainet.smsemail.dto.SMSAndEmailDTO;
import com.abm.mainet.smsemail.service.ISMSAndEmailService;
import com.abm.mainet.socialsecurity.service.ISchemeApplicationFormService;
import com.abm.mainet.socialsecurity.ui.dto.ApplicationFormDto;
import com.abm.mainet.socialsecurity.ui.dto.ViewDtoList;

/**
 * @author priti.singh
 *
 */
@Component
@Scope(value = WebApplicationContext.SCOPE_SESSION)
public class SchemeApplicationFormModel extends AbstractFormModel {

	private static final Logger LOGGER = Logger.getLogger(SchemeApplicationFormModel.class);

	private static final long serialVersionUID = 1387449222271618470L;
	private ApplicationFormDto applicationformdto = new ApplicationFormDto();
	private List<Object[]> serviceList = new ArrayList<>();
	private List<LookUp> genderList = new ArrayList<>();
	private List<LookUp> educationList = new ArrayList<>();
	private List<LookUp> maritalstatusList = new ArrayList<>();
	private List<LookUp> categoryList = new ArrayList<>();
	private List<LookUp> typeofdisabilityList = new ArrayList<>();
	private List<BankMasterDTO> bankList = new ArrayList<>();
	private List<DocumentDetailsVO> checkList = new ArrayList<>();
	private PortalService serviceMaster = new PortalService();

	private List<LookUp> bplList = new ArrayList<>();

	private List<ViewDtoList> viewList = new ArrayList<>();

	@Autowired
	private ISchemeApplicationFormService schemeApplicationFormService;
	@Autowired
	private IPortalServiceMasterService portalServiceMasterService;
	
	@Autowired
	private ISMSAndEmailService ismsAndEmailService;

	/*
	 * @Autowired private BankMasterService bankMasterService;
	 */

	@Autowired
	private IFileUploadService fileUpload;

	/**
	 * @return the applicationformdto
	 */
	public ApplicationFormDto getApplicationformdto() {
		return applicationformdto;
	}

	/**
	 * @param applicationformdto the applicationformdto to set
	 */
	public void setApplicationformdto(ApplicationFormDto applicationformdto) {
		this.applicationformdto = applicationformdto;
	}

	/**
	 * @return the serviceList
	 */
	public List<Object[]> getServiceList() {
		return serviceList;
	}

	/**
	 * @param serviceList the serviceList to set
	 */
	public void setServiceList(List<Object[]> serviceList) {
		this.serviceList = serviceList;
	}

	/**
	 * @return the educationList
	 */
	public List<LookUp> getEducationList() {
		return educationList;
	}

	/**
	 * @param educationList the educationList to set
	 */
	public void setEducationList(List<LookUp> educationList) {
		this.educationList = educationList;
	}

	/**
	 * @return the maritalstatusList
	 */
	public List<LookUp> getMaritalstatusList() {
		return maritalstatusList;
	}

	/**
	 * @param maritalstatusList the maritalstatusList to set
	 */
	public void setMaritalstatusList(List<LookUp> maritalstatusList) {
		this.maritalstatusList = maritalstatusList;
	}

	/**
	 * @return the categoryList
	 */
	public List<LookUp> getCategoryList() {
		return categoryList;
	}

	/**
	 * @param categoryList the categoryList to set
	 */
	public void setCategoryList(List<LookUp> categoryList) {
		this.categoryList = categoryList;
	}

	/**
	 * @return the typeofdisabilityList
	 */
	public List<LookUp> getTypeofdisabilityList() {
		return typeofdisabilityList;
	}

	/**
	 * @param typeofdisabilityList the typeofdisabilityList to set
	 */
	public void setTypeofdisabilityList(List<LookUp> typeofdisabilityList) {
		this.typeofdisabilityList = typeofdisabilityList;
	}

	/**
	 * @return the bplList
	 */
	public List<LookUp> getBplList() {
		return bplList;
	}

	/**
	 * @param bplList the bplList to set
	 */
	public void setBplList(List<LookUp> bplList) {
		this.bplList = bplList;
	}

	/**
	 * @return the serialversionuid
	 */
	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	/**
	 * @return the genderList
	 */
	public List<LookUp> getGenderList() {
		return genderList;
	}

	/**
	 * @param genderList the genderList to set
	 */
	public void setGenderList(List<LookUp> genderList) {
		this.genderList = genderList;
	}

	public List<BankMasterDTO> getBankList() {
		return bankList;
	}

	public void setBankList(List<BankMasterDTO> bankList) {
		this.bankList = bankList;
	}

	public List<DocumentDetailsVO> getCheckList() {
		return checkList;
	}

	public void setCheckList(List<DocumentDetailsVO> checkList) {
		this.checkList = checkList;
	}

	public PortalService getServiceMaster() {
		return serviceMaster;
	}

	public void setServiceMaster(PortalService serviceMaster) {
		this.serviceMaster = serviceMaster;
	}

	@Override
	public boolean saveForm() {
		ApplicationFormDto statusDto = new ApplicationFormDto();
		try {
			statusDto = schemeApplicationFormService.saveApplicationDetails(getApplicationformdto());
			setSuccessMessage(getAppSession().getMessage("trade.successMsg") + statusDto.getMasterAppId());

			final SMSAndEmailDTO smsDto = new SMSAndEmailDTO();

			smsDto.setMobnumber(getApplicationformdto().getMobNum().toString());
			smsDto.setAppNo(statusDto.getMasterAppId().toString());
			// smsDto.setAppNo(getApplicationformdto().getApplicationId().toString());
			Long sm = ApplicationContextProvider.getApplicationContext().getBean(IPortalServiceMasterService.class)
					.getServiceId(MainetConstants.SocialSecurity.SERVICE_CODE,
							UserSession.getCurrent().getOrganisation().getOrgid());
			PortalService portalService = ApplicationContextProvider.getApplicationContext()
					.getBean(IPortalServiceMasterService.class).getPortalService(sm);
			setServiceMaster(portalService);
			smsDto.setServName(portalService.getServiceName());
			String url = "SchemeApplicationForm.html";
			smsDto.setOrgId(UserSession.getCurrent().getOrganisation().getOrgid());
			int langId = UserSession.getCurrent().getLanguageId();
			ismsAndEmailService.sendEmailSMS("CFC", url, MainetConstants.SocialSecurity.GENERAL_MSG, smsDto,
					UserSession.getCurrent().getOrganisation(), langId);

			statusDto.setStatusFlag(true);
		} catch (FrameworkException exp) {
		
			statusDto.setStatusFlag(false);
			throw new FrameworkException(exp);
		}
		return statusDto.getStatusFlag();
	}

	public void getAndSetPrefix(Long orgId, int langId, Organisation org) {
		List<LookUp> parentPxList = CommonMasterUtility.getLevelData(MainetConstants.SocialSecurity.FTR, 1, org);
		setGenderList(schemeApplicationFormService.FindSecondLevelPrefixByFirstLevelPxCode(orgId, "FTR",
				parentPxList.stream().filter(k -> k.getLookUpCode().equalsIgnoreCase("GNR"))
						.collect(Collectors.toList()).get(0).getLookUpId(),
				2L));
		setEducationList(schemeApplicationFormService.FindSecondLevelPrefixByFirstLevelPxCode(orgId, "FTR",
				parentPxList.stream().filter(k -> k.getLookUpCode().equalsIgnoreCase("EDU"))
						.collect(Collectors.toList()).get(0).getLookUpId(),
				2L));
		setMaritalstatusList(schemeApplicationFormService.FindSecondLevelPrefixByFirstLevelPxCode(orgId, "FTR",
				parentPxList.stream().filter(k -> k.getLookUpCode().equalsIgnoreCase("MLS"))
						.collect(Collectors.toList()).get(0).getLookUpId(),
				2L));
		setCategoryList(schemeApplicationFormService.FindSecondLevelPrefixByFirstLevelPxCode(orgId, "FTR",
				parentPxList.stream().filter(k -> k.getLookUpCode().equalsIgnoreCase("CTY"))
						.collect(Collectors.toList()).get(0).getLookUpId(),
				2L));
		setTypeofdisabilityList(schemeApplicationFormService.FindSecondLevelPrefixByFirstLevelPxCode(orgId, "FTR",
				parentPxList.stream().filter(k -> k.getLookUpCode().equalsIgnoreCase("DSY"))
						.collect(Collectors.toList()).get(0).getLookUpId(),
				2L));
		setBplList(schemeApplicationFormService.FindSecondLevelPrefixByFirstLevelPxCode(orgId, "FTR",
				parentPxList.stream().filter(k -> k.getLookUpCode().equalsIgnoreCase("BPL"))
						.collect(Collectors.toList()).get(0).getLookUpId(),
				2L));

		final Long serviceId = portalServiceMasterService.getServiceId("SAA", orgId);
		final PortalService service = portalServiceMasterService.getService(serviceId, orgId);
		final LookUp lookUpFieldStatus = CommonMasterUtility.getValueFromPrefixLookUp(MainetConstants.MASTER.A, "ACN");
		setServiceList(schemeApplicationFormService.findAllActiveServicesWhichIsNotActual(orgId,
				service.getPsmDpDeptid(), lookUpFieldStatus.getLookUpId(), "N"));

		setBankList(schemeApplicationFormService.getBankList(orgId));

	}

}
