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
import com.abm.mainet.common.constant.PrefixConstants;
import com.abm.mainet.common.domain.BankMasterEntity;
import com.abm.mainet.common.domain.Organisation;
import com.abm.mainet.common.exception.FrameworkException;
import com.abm.mainet.common.integration.dto.DocumentDetailsVO;
import com.abm.mainet.common.master.service.BankMasterService;
import com.abm.mainet.common.master.service.TbDepartmentService;
import com.abm.mainet.common.service.ServiceMasterService;
import com.abm.mainet.common.ui.model.AbstractFormModel;
import com.abm.mainet.common.utility.CommonMasterUtility;
import com.abm.mainet.common.utility.LookUp;
import com.abm.mainet.socialsecurity.domain.SocialSecuritySchemeMaster;
import com.abm.mainet.socialsecurity.service.ISchemeApplicationFormService;
import com.abm.mainet.socialsecurity.ui.dto.ApplicationFormDto;

@Component
@Scope(value = WebApplicationContext.SCOPE_SESSION)
public class DataLegacyModel extends AbstractFormModel {

	/**
	 * 
	 * @author priti.singh
	 *
	 */

	private static final Logger LOGGER = Logger.getLogger(DataLegacyModel.class);
	private static final long serialVersionUID = 1387449222271618470L;

	private ApplicationFormDto applicationformdto = new ApplicationFormDto();
	private List<Object[]> serviceList = new ArrayList<>();
	private List<LookUp> genderList = new ArrayList<>();
	private List<LookUp> educationList = new ArrayList<>();
	private List<LookUp> maritalstatusList = new ArrayList<>();
	private List<LookUp> categoryList = new ArrayList<>();
	private List<LookUp> typeofdisabilityList = new ArrayList<>();
	private List<BankMasterEntity> bankList = new ArrayList<>();
	private List<DocumentDetailsVO> checkList = new ArrayList<>();
	private List<LookUp> subTypeList = new ArrayList<>();

	private List<LookUp> bplList = new ArrayList<>();

	@Autowired
	private ISchemeApplicationFormService schemeApplicationFormService;

	@Autowired
	private ServiceMasterService serviceMasterService;

	@Autowired
	private TbDepartmentService tbDepartmentService;

	@Autowired
	private BankMasterService bankMasterService;

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
	 * @return the bankList
	 */
	public List<BankMasterEntity> getBankList() {
		return bankList;
	}

	/**
	 * @param bankList the bankList to set
	 */
	public void setBankList(List<BankMasterEntity> bankList) {
		this.bankList = bankList;
	}

	/**
	 * @return the checkList
	 */
	public List<DocumentDetailsVO> getCheckList() {
		return checkList;
	}

	/**
	 * @param checkList the checkList to set
	 */
	public void setCheckList(List<DocumentDetailsVO> checkList) {
		this.checkList = checkList;
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
	 * @return the schemeApplicationFormService
	 */
	public ISchemeApplicationFormService getSchemeApplicationFormService() {
		return schemeApplicationFormService;
	}

	/**
	 * @param schemeApplicationFormService the schemeApplicationFormService to set
	 */
	public void setSchemeApplicationFormService(ISchemeApplicationFormService schemeApplicationFormService) {
		this.schemeApplicationFormService = schemeApplicationFormService;
	}

	/**
	 * @return the serviceMasterService
	 */
	public ServiceMasterService getServiceMasterService() {
		return serviceMasterService;
	}

	/**
	 * @param serviceMasterService the serviceMasterService to set
	 */
	public void setServiceMasterService(ServiceMasterService serviceMasterService) {
		this.serviceMasterService = serviceMasterService;
	}

	/**
	 * @return the tbDepartmentService
	 */
	public TbDepartmentService getTbDepartmentService() {
		return tbDepartmentService;
	}

	/**
	 * @param tbDepartmentService the tbDepartmentService to set
	 */
	public void setTbDepartmentService(TbDepartmentService tbDepartmentService) {
		this.tbDepartmentService = tbDepartmentService;
	}

	/**
	 * @return the bankMasterService
	 */
	public BankMasterService getBankMasterService() {
		return bankMasterService;
	}
	/**
	 * @return the Sub-Scheme
	 */
	public List<LookUp> getSubTypeList() {
		return subTypeList;
	}

	public void setSubTypeList(List<LookUp> subTypeList) {
		this.subTypeList = subTypeList;
	}

	/**
	 * @param bankMasterService the bankMasterService to set
	 */
	public void setBankMasterService(BankMasterService bankMasterService) {
		this.bankMasterService = bankMasterService;
	}

	public boolean saveDataLegacyDetails() {
		ApplicationFormDto statusDto = new ApplicationFormDto();
		try {
			statusDto = schemeApplicationFormService.saveDataLegacyFormDetails(getApplicationformdto());
			setSuccessMessage(getAppSession().getMessage("data.legacy.form.save") + statusDto.getBeneficiaryno());

		} catch (FrameworkException exp) {
			LOGGER.warn("Error occured while saving the Data Legacy details:", exp);
			statusDto.setStatusFlag(false);
		}
		return statusDto.getStatusFlag();
	}

	public void getAndSetPrefix(Long orgId, int langId, Organisation org) {
		List<LookUp> parentPxList = CommonMasterUtility.getNextLevelData(MainetConstants.SocialSecurity.FTR, 1, orgId);
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

		Long depId = tbDepartmentService.getDepartmentIdByDeptCode(MainetConstants.SocialSecurity.DEPARTMENT_SORT_CODE);
		final LookUp lookUpFieldStatus = CommonMasterUtility.getLookUpFromPrefixLookUpValue(MainetConstants.MASTER.A,
				PrefixConstants.ACN, langId, org);
		final Long activeStatusId = lookUpFieldStatus.getLookUpId();
		List<SocialSecuritySchemeMaster> schemeMaster = schemeApplicationFormService.getActiveScheme(orgId);
		// setServiceList(serviceMasterService.findAllActiveServicesWhichIsNotActual(orgId,
		// depId, activeStatusId, "N"));
		List<Object[]> schemeMastServiceList = serviceMasterService.findAllActiveServicesWhichIsNotActual(orgId, depId,
				activeStatusId, "N");
		List<Object[]> schemeservicelist1 = new ArrayList();
		schemeMaster.stream().forEach(k -> {
			schemeMastServiceList.stream().forEach(l -> {
				if (k.getSchemeNameId().equals(l[0])) {
					schemeservicelist1.add(l);
				}
			});
		});
		setServiceList(schemeservicelist1);

		// setServiceList(serviceMasterService.findAllActiveServicesWhichIsNotActual(orgId,
		// depId, activeStatusId, "N"));
		setBankList(bankMasterService.getBankList());
	}
}
