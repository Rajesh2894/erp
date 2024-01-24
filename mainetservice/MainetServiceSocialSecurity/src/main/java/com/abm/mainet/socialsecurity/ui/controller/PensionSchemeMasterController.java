/**
 * 
 */
package com.abm.mainet.socialsecurity.ui.controller;

import java.io.File;
import java.io.IOException;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.codec.binary.Base64;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.io.FileUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.json.MappingJackson2JsonView;

import com.abm.mainet.common.constant.MainetConstants;
import com.abm.mainet.common.constant.PrefixConstants;
import com.abm.mainet.common.domain.BankMasterEntity;
import com.abm.mainet.common.domain.Organisation;
import com.abm.mainet.common.dto.JsonViewObject;
import com.abm.mainet.common.exception.FrameworkException;
import com.abm.mainet.common.integration.dms.fileUpload.FileUploadUtility;
import com.abm.mainet.common.integration.dms.service.IFileUploadService;
import com.abm.mainet.common.integration.dto.DocumentDetailsVO;
import com.abm.mainet.common.master.dto.TbFinancialyear;
import com.abm.mainet.common.master.service.BankMasterService;
import com.abm.mainet.common.master.service.TbDepartmentService;
import com.abm.mainet.common.master.service.TbFinancialyearService;
import com.abm.mainet.common.service.IFinancialYearService;
import com.abm.mainet.common.service.IOrganisationService;
import com.abm.mainet.common.service.ServiceMasterService;
import com.abm.mainet.common.ui.controller.AbstractFormController;
import com.abm.mainet.common.utility.ApplicationContextProvider;
import com.abm.mainet.common.utility.ApplicationSession;
import com.abm.mainet.common.utility.CommonMasterUtility;
import com.abm.mainet.common.utility.LookUp;
import com.abm.mainet.common.utility.SeqGenFunctionUtility;
import com.abm.mainet.common.utility.UserSession;
import com.abm.mainet.common.utility.Utility;
import com.abm.mainet.socialsecurity.service.ConfigurationMasterService;
import com.abm.mainet.socialsecurity.service.IPensionSchemeMasterService;
import com.abm.mainet.socialsecurity.service.ISchemeApplicationFormService;
import com.abm.mainet.socialsecurity.ui.dto.PensionEligibilityCriteriaDto;
import com.abm.mainet.socialsecurity.ui.dto.PensionSourceOfFundDto;
import com.abm.mainet.socialsecurity.ui.dto.SubSchemeDetailsDto;
import com.abm.mainet.socialsecurity.ui.dto.ViewDtoList;
import com.abm.mainet.socialsecurity.ui.model.PensionSchemeMasterModel;

/**
 * @author satish.rathore
 *
 */
@Controller
@RequestMapping(MainetConstants.SocialSecurity.PENSION_SCHEME_MASTER_URL)
public class PensionSchemeMasterController extends AbstractFormController<PensionSchemeMasterModel> {

	@Autowired
	ServiceMasterService serviceMasterService;
	@Autowired
	TbDepartmentService tbDepartmentService;
	@Autowired
	private SeqGenFunctionUtility seqGenFunctionUtility;
	@Autowired
	private IPensionSchemeMasterService pensionSchemeMasterService;
	@Resource
	IFileUploadService fileUpload;
	
	@Autowired
	private ConfigurationMasterService configurationMasterService;
	
	@Autowired
	private ISchemeApplicationFormService schemeApplicationFormService;
	
	@Resource
	ServiceMasterService serviceMaster;
	
	 @Autowired
	 private IOrganisationService iOrganisationService;
	 
	 @Autowired
	 private BankMasterService bankMasterService;

	private static final Logger LOGGER = Logger.getLogger(PensionSchemeMasterController.class);

	private static final String ELIGIBILITY_CRITERIA = "EligibilityCriteria";

	@RequestMapping(method = { RequestMethod.GET, RequestMethod.POST })
	public ModelAndView index(final Model model, final HttpServletRequest httpServletRequest) {
		sessionCleanup(httpServletRequest);
		PensionSchemeMasterModel pSmodel = this.getModel();
		Organisation org = UserSession.getCurrent().getOrganisation();
		Long orgId = UserSession.getCurrent().getOrganisation().getOrgid();
		int langId = UserSession.getCurrent().getLanguageId();
		Long depId = tbDepartmentService.getDepartmentIdByDeptCode(MainetConstants.SocialSecurity.DEPARTMENT_SORT_CODE);
		final LookUp lookUpFieldStatus = CommonMasterUtility.getLookUpFromPrefixLookUpValue(MainetConstants.MASTER.A,
				PrefixConstants.ACN, langId, org);
		final Long activeStatusId = lookUpFieldStatus.getLookUpId();
		pSmodel.setViewList(pensionSchemeMasterService.getAllData(orgId, depId, activeStatusId));
		pSmodel.setServiceList(
				serviceMasterService.findAllActiveServicesWhichIsNotActual(orgId, depId, activeStatusId, "N"));
		if (Utility.isEnvPrefixAvailable(org, MainetConstants.ENV_TSCL))
			pSmodel.setEnvFlag(MainetConstants.FlagY);
		else
			pSmodel.setEnvFlag(MainetConstants.FlagN);
		
		 final List<TbFinancialyear> finYearList = ApplicationContextProvider.getApplicationContext()
	                .getBean(TbFinancialyearService.class)
	                .findAllFinancialYearByOrgId(org);
		 pSmodel.setFaYears(finYearList);
		return index();
	}

	@RequestMapping(params = "showPensionSchemeForm", method = { RequestMethod.POST })
	public ModelAndView shemeMasterForm(@RequestParam(value = "type", required = false) String type, final Model model,
			final HttpServletRequest httpServletRequest) {
		sessionCleanup(httpServletRequest);
		fileUpload.sessionCleanUpForFileUpload();
		Organisation org = UserSession.getCurrent().getOrganisation();
		Long orgId = UserSession.getCurrent().getOrganisation().getOrgid();
		int langId = UserSession.getCurrent().getLanguageId();
		PensionSchemeMasterModel pSmodel = this.getModel();
		pSmodel.setModeType(type);
		pSmodel.setSourceLookUps(CommonMasterUtility.getNextLevelData(MainetConstants.SocialSecurity.FTR, 1, orgId));
		pSmodel.setSecondLevellookUps(
				CommonMasterUtility.getNextLevelData(MainetConstants.SocialSecurity.FTR, 2, orgId));
		List<LookUp> paymentList = CommonMasterUtility.getLookUps(MainetConstants.SocialSecurity.PAYMENT_PREFIX, org);
		Long depId = tbDepartmentService.getDepartmentIdByDeptCode(MainetConstants.SocialSecurity.DEPARTMENT_SORT_CODE);
		final LookUp lookUpFieldStatus = CommonMasterUtility.getLookUpFromPrefixLookUpValue(MainetConstants.MASTER.A,
				PrefixConstants.ACN, langId, org);
		final Long activeStatusId = lookUpFieldStatus.getLookUpId();
        //D#143073
		pSmodel.setServiceList(
				pensionSchemeMasterService.findAllActiveServicesNotAddedInSchemeMaster(orgId, depId, activeStatusId, "N"));
		pSmodel.setSponcerByList(CommonMasterUtility.getLookUps(MainetConstants.SocialSecurity.SBY, org));
		pSmodel.setPaymentList(paymentList);

		pSmodel.getPensionSchmDto().getPensionSourceFundList().add(new PensionSourceOfFundDto());
		pSmodel.getPensionSchmDto().getSubSchemeDetailsDtoList().add(new SubSchemeDetailsDto());
		System.out.println(pSmodel.getPensionSchmDto().getPensioneligibilityList().size());
		this.getModel().setCommonHelpDocs("PensionSchemeMaster.html");
		if (Utility.isEnvPrefixAvailable(org, MainetConstants.ENV_TSCL))
			pSmodel.setEnvFlag(MainetConstants.FlagY);
		else
			pSmodel.setEnvFlag(MainetConstants.FlagN);
		final List<TbFinancialyear> finYearList = ApplicationContextProvider.getApplicationContext()
                .getBean(TbFinancialyearService.class)
                .findAllFinancialYearByOrgId(org);
	// pSmodel.setFaYears(finYearList);
		pSmodel.getFaYears().clear();
	 if (finYearList != null && !finYearList.isEmpty()) {
         finYearList.forEach(finYearTemp -> {
             try {
                 finYearTemp.setFaYearFromTo(Utility.getFinancialYearFromDate(finYearTemp.getFaFromDate()));
                 pSmodel.getFaYears().add(finYearTemp);
             } catch (Exception ex) {
                 throw new FrameworkException( ex);
             }
         });
         Comparator<TbFinancialyear> comparing = Comparator.comparing(TbFinancialyear::getFaYearFromTo,
                 Comparator.reverseOrder());
         Collections.sort(pSmodel.getFaYears(), comparing);
     }
	 
		/*
		 * if (Utility.isEnvPrefixAvailable(org, MainetConstants.ENV_TSCL)) {
		 * List<LookUp> sourceLookUp =
		 * CommonMasterUtility.getNextLevelData(MainetConstants.SocialSecurity.FTR,
		 * 1,UserSession.getCurrent().getOrganisation().getOrgid()); List<LookUp> list =
		 * (schemeApplicationFormService.FindSecondLevelPrefixByFirstLevelPxCode(
		 * UserSession.getCurrent().getOrganisation().getOrgid(), "FTR",
		 * sourceLookUp.stream().filter(k -> k.getLookUpCode().equalsIgnoreCase("SS"))
		 * .collect(Collectors.toList()).get(0).getLookUpId(), 2L));
		 * 
		 * this.getModel().setSubTypeList(list); }
		 */
		return new ModelAndView("PensionSchemeMasterForm", MainetConstants.FORM_NAME, pSmodel);
	}

	@RequestMapping(params = "savePensionScheme", method = RequestMethod.POST)
	public ModelAndView savePensionScheme(final Model model, final HttpServletRequest httpServletRequest) {

		bindModel(httpServletRequest);
		ModelAndView modelAndView = null;
		Long count=0l;
       
		Long orgId = UserSession.getCurrent().getOrganisation().getOrgid();
		Long squenceNo = seqGenFunctionUtility.generateSequenceNo(MainetConstants.SocialSecurity.DEPARTMENT_SORT_CODE,
				"TB_SWD_SCHEME_MAST", "SDSCH_ID", orgId, MainetConstants.FlagC, null);
		final PensionSchemeMasterModel pensionModel = this.getModel();
	
		//pensionModel.getSaveDataList().clear();
		final List<PensionEligibilityCriteriaDto> viewList = new ArrayList<>();

		pensionModel.getPensionSchmDto().getPensioneligibilityList().stream().forEach(h -> {
			if (h.getCheckBox()) {
				PensionEligibilityCriteriaDto pecdto = new PensionEligibilityCriteriaDto();
                //D#143073
				pecdto.setAmt(pensionModel.getPensionSchmDto().getAmt());
				if (h.getAmt() != null) {
					pecdto.setAmtq(h.getAmt());
					pecdto.setAmtq(pecdto.getAmtq());

					httpServletRequest.getSession().setAttribute("Amount", h.getAmt());
				}
				pecdto.setBatchId(h.getBatchId());
				pecdto.setCheckBox(h.getCheckBox());
				pecdto.setCriteriaDesc(h.getCriteriaDesc());
				pecdto.setCriteriaId(h.getCriteriaId());
				pecdto.setFactorApplicableDesc(h.getFactorApplicableDesc());
				pecdto.setFactorApplicableId(h.getFactorApplicableId());
				pecdto.setPaySchedule(pensionModel.getPensionSchmDto().getPaySchedule());
				pecdto.setRangeFrom(h.getRangeFrom());
				pecdto.setRangeTo(h.getRangeTo());
				pecdto.setBatchId(squenceNo);
				viewList.add(pecdto);

			}
		});
		//BigDecimal amount = BigDecimal.ZERO;
		if (Utility.isEnvPrefixAvailable(UserSession.getCurrent().getOrganisation(), MainetConstants.ENV_TSCL)) {
		long sum = pensionModel.getPensionSchmDto().getSubSchemeDetailsDtoList().stream().mapToLong(SubSchemeDetailsDto::getTotalAmount).sum();
		pensionModel.setBeneficiaryAmt(BigDecimal.valueOf(sum));
		long counts = pensionModel.getPensionSchmDto().getSubSchemeDetailsDtoList().stream().mapToLong(SubSchemeDetailsDto::getNoBeneficiary).sum();
		pensionModel.setBeneficiaryCount(Long.valueOf(counts));
		}
		List<LookUp> sourceLookUp = CommonMasterUtility.getNextLevelData(MainetConstants.SocialSecurity.FTR, 1, orgId);
		
		/*
		 * if (Utility.isEnvPrefixAvailable(UserSession.getCurrent().getOrganisation(),
		 * MainetConstants.ENV_TSCL)) { LookUp
		 * look=CommonMasterUtility.getHieLookupByLookupCode("SS",
		 * MainetConstants.SocialSecurity.FTR, 1, orgId);
		 * 
		 * for(int i=0;i<pensionModel.getSaveDataList().size();i++) { for(int j=0;j<
		 * pensionModel.getSaveDataList().get(i).size();j++) {
		 * if(pensionModel.getSaveDataList().get(i).get(j).getFactorApplicableId()==look
		 * .getLookUpId()) { for(int k=0;k< viewList.size();k++) {
		 * if(viewList.get(k).getFactorApplicableId()==look.getLookUpId()) {
		 * if(viewList.get(k).getCriteriaId().equals(pensionModel.getSaveDataList().get(
		 * i).get(j).getCriteriaId())) { count++; break; } } } } } }
		 * 
		 * if(count!=0) {
		 * this.getModel().addValidationError(ApplicationSession.getInstance().
		 * getMessage("social.subScheme.duplicate")); modelAndView = new
		 * ModelAndView("EligibilityCriteriaValidn", MainetConstants.FORM_NAME,
		 * this.getModel()); modelAndView.addObject( BindingResult.MODEL_KEY_PREFIX +
		 * ApplicationSession.getInstance().getMessage(MainetConstants.FORM_NAME),
		 * getModel().getBindingResult()); return modelAndView; }
		 * 
		 * if (look != null) { viewList.stream().forEach(l -> { if (look.getLookUpId()
		 * == l.getFactorApplicableId()) { BigDecimal amtq = l.getAmt(); BigDecimal
		 * amtq1 = new BigDecimal(l.getRangeTo()); BigDecimal totalAmount =
		 * (amtq.multiply(amtq1)); pensionModel.setSubSchemeAmt(totalAmount);
		 * if(pensionModel.getBeneficiaryCount()!=null)
		 * pensionModel.setBeneficiaryCount(pensionModel.getBeneficiaryCount()+(Long.
		 * valueOf(l.getRangeTo()))); else {
		 * pensionModel.setBeneficiaryCount((Long.valueOf(l.getRangeTo()))); }
		 * 
		 * } }); } if (pensionModel.getSubSchemeAmt() != null)
		 * viewList.stream().forEach(l -> {
		 * l.setSubSchemeAmt(pensionModel.getSubSchemeAmt()); });
		 * if(pensionModel.getBeneficiaryAmt()!=null)
		 * pensionModel.setBeneficiaryAmt(pensionModel.getBeneficiaryAmt().add(
		 * pensionModel.getPensionSchmDto().getAmt())); else {
		 * pensionModel.setBeneficiaryAmt(pensionModel.getPensionSchmDto().getAmt()); }
		 * 
		 * }
		 */
		List<PensionEligibilityCriteriaDto> l2 = new ArrayList<PensionEligibilityCriteriaDto>();
		List<Integer> da = new ArrayList<Integer>();
		List<Integer> l3 = new ArrayList<Integer>();

		viewList.stream().forEach(l -> {

			pensionModel.getPensionEligCriteriaDto().setFactorApplicableId(l.getFactorApplicableId());
			pensionModel.getPensionEligCriteriaDto().setCriteriaId(l.getCriteriaId());
			pensionModel.getPensionEligCriteriaDto().setRangeFrom(l.getRangeFrom());
			pensionModel.getPensionEligCriteriaDto().setRangeTo(l.getRangeTo());
			pensionModel.getPensionEligCriteriaDto().setAmt(l.getAmt());

			l2.add(pensionModel.getPensionEligCriteriaDto());

		});

		/*
		 * l2.stream().forEach(l -> { if (l.getFactorApplicableId() == 6332) { int Data
		 * = pensionSchemeMasterService.findfactorApplicable(l.getFactorApplicableId(),
		 * l.getCriteriaId(), l.getRangeFrom(), l.getRangeTo(),
		 * BigDecimal.valueOf(httpServletRequest.getSession().getAttribute("Amount")),
		 * orgId); da.add(Data); } else { int Data =
		 * pensionSchemeMasterService.findfactorApplicablewithoutamt(l.
		 * getFactorApplicableId(), l.getCriteriaId(), l.getRangeFrom(), l.getRangeTo(),
		 * orgId); da.add(Data); } });
		 */

		l2.stream().forEach(l -> {
			int Data = pensionSchemeMasterService.findfactorApplicable(l.getFactorApplicableId(), l.getCriteriaId(),
					l.getRangeFrom(), l.getRangeTo(), l.getAmt(), orgId);
			da.add(Data);
		});

		da.stream().forEach(l ->

		{
			if (l > 0)
				l3.add(l);
		});
		
		if (Utility.isEnvPrefixAvailable(UserSession.getCurrent().getOrganisation(), MainetConstants.ENV_TSCL))
			pensionModel.setEnvFlag(MainetConstants.FlagY);
		else
			pensionModel.setEnvFlag(MainetConstants.FlagN);

		
		if (da.size() != l3.size()) {
			pensionModel.getSaveDataList().add(viewList);
			pensionModel.setSourceLookUps(sourceLookUp);
			pensionModel.getUpdateBatchIdSet().add(squenceNo);
			pensionModel.getPensionSchmDto().setPensioneligibilityList(new ArrayList<>());
			modelAndView = new ModelAndView(ELIGIBILITY_CRITERIA, MainetConstants.FORM_NAME, pensionModel);
		}

		else {
			/* this.getModel().addValidationError("Duplicate Data"); */
			this.getModel().addValidationError(ApplicationSession.getInstance().getMessage("social.sec.duplicate"));
			modelAndView = new ModelAndView("EligibilityCriteriaValidn", MainetConstants.FORM_NAME, this.getModel());
			modelAndView.addObject(
					BindingResult.MODEL_KEY_PREFIX
							+ ApplicationSession.getInstance().getMessage(MainetConstants.FORM_NAME),
					getModel().getBindingResult());
		}
		return modelAndView;

	}

	@RequestMapping(params = "deleteSchemeDetails", method = RequestMethod.POST)
	public ModelAndView deleteSchemeDetails(@RequestParam(value = "batchId", required = true) final Long batchId,
			final Model model, final HttpServletRequest httpServletRequest) {
		bindModel(httpServletRequest);
		final PensionSchemeMasterModel pensionModel = this.getModel();
		LookUp look = CommonMasterUtility.getHieLookupByLookupCode("SS", MainetConstants.SocialSecurity.FTR, 1, UserSession.getCurrent().getOrganisation().getOrgid());

		List<List<PensionEligibilityCriteriaDto>> saveDataList = pensionModel.getSaveDataList();

		Iterator<List<PensionEligibilityCriteriaDto>> outerIterator = saveDataList.iterator();
		while (outerIterator.hasNext()) {
			List<PensionEligibilityCriteriaDto> list = outerIterator.next();
			Iterator<PensionEligibilityCriteriaDto> iterator = list.iterator();
			while (iterator.hasNext()) {
				PensionEligibilityCriteriaDto dto = iterator.next();
				if (dto.getBatchId().equals(batchId)) {
					if (dto.getFactorApplicableId() == look.getLookUpId()) {
						String rangeToString = dto.getRangeTo();
						if (rangeToString != null && !rangeToString.isEmpty()) {
							try {
								Long rangeToValue = Long.parseLong(rangeToString);
								pensionModel.setBeneficiaryCount(pensionModel.getBeneficiaryCount() - rangeToValue);
							} catch (NumberFormatException e) {
								// Handle the case where the string cannot be parsed to Long
							}
						}
					}
					pensionModel.setBeneficiaryAmt(pensionModel.getBeneficiaryAmt().subtract(dto.getAmt()));
					iterator.remove(); // Remove the element from the inner list
				}
			}
			if (list.isEmpty()) {
				outerIterator.remove(); // Remove the inner list if it's empty
			}
		}

		pensionModel.getDeletedBatchIdSet().add(batchId);
		return new ModelAndView(ELIGIBILITY_CRITERIA, MainetConstants.FORM_NAME, pensionModel);
	}

	@Override
	@RequestMapping(params = "saveform", method = RequestMethod.POST)
	public ModelAndView saveform(final HttpServletRequest httpServletRequest) {

		bindModel(httpServletRequest);
		JsonViewObject respObj;
		Long orgId = UserSession.getCurrent().getOrganisation().getOrgid();
		Long empId = UserSession.getCurrent().getEmployee().getEmpId();
		String ipMacAddress = Utility.getClientIpAddress(httpServletRequest);
		this.getModel().getPensionSchmDto().setSaveDataList(this.getModel().getSaveDataList());
       //Defect #138181
		try {
			if (this.getModel().getPensionSchmDto().getResolutionDoc()!=null) {
				this.getModel().getPensionSchmDto().setResolutionDoc(
						prepareFileUploadForImg(this.getModel().getPensionSchmDto().getResolutionDoc()));
			}
		} catch (Exception e) {
			LOGGER.error("Exception has been occurred in file byte to string setting in resolution document");
		}

		int serviceId = pensionSchemeMasterService.findServiceId(this.getModel().getPensionSchmDto().getServiceId(),
				UserSession.getCurrent().getOrganisation().getOrgid());

		// Logic for duplicate scheme name
		if (serviceId == 0) {
			boolean statusFlag = pensionSchemeMasterService.savePensionDetails(orgId, empId, ipMacAddress,
					this.getModel().getPensionSchmDto());
			if (Utility.isEnvPrefixAvailable(UserSession.getCurrent().getOrganisation(), MainetConstants.ENV_TSCL) && this.getModel().getConfigurtionMasterDto() != null) {
				if (this.getModel().getConfigurtionMasterDto().getConfigurationId() == null) {
					this.getModel().getConfigurtionMasterDto().setOrgId(orgId);
					this.getModel().getConfigurtionMasterDto().setCreatedBy(empId);
					this.getModel().getConfigurtionMasterDto().setCreationDate(new Date());
					this.getModel().getConfigurtionMasterDto().setLgIpMac(ipMacAddress);
                       
					if (this.getModel().getPensionSchmDto().getServiceId() != null) {
					this.getModel().getConfigurtionMasterDto().setSchemeCode(serviceMaster
							.fetchServiceShortCode(this.getModel().getPensionSchmDto().getServiceId(),orgId));
					this.getModel().getConfigurtionMasterDto()
							.setSchemeName(serviceMaster.getServiceNameByServiceId(this.getModel().getPensionSchmDto().getServiceId()));
					this.getModel().getConfigurtionMasterDto().setSchemeMstId(this.getModel().getPensionSchmDto().getServiceId());
					}
					configurationMasterService.saveConfigurationMaster(this.getModel().getConfigurtionMasterDto());
					
				} else {
					this.getModel().getConfigurtionMasterDto().setOrgId(orgId);
					this.getModel().getConfigurtionMasterDto().setUpdatedBy(empId);
					this.getModel().getConfigurtionMasterDto().setUpdatedDate(new Date());
					this.getModel().getConfigurtionMasterDto().setLgIpMacUpd(ipMacAddress);
					this.getModel().getConfigurtionMasterDto().getBeneficiaryCount();
					this.getModel().getConfigurtionMasterDto().setSchemeMstId(this.getModel().getPensionSchmDto().getServiceId());
					configurationMasterService.updateConfigurationMaster(this.getModel().getConfigurtionMasterDto());
					
				}
			}

			if (statusFlag) {
				respObj = JsonViewObject
						.successResult(ApplicationSession.getInstance().getMessage("social.sec.save.success"));
				this.getModel().setSuccessMessage("Save successfully");
			} else {
				respObj = JsonViewObject
						.successResult(ApplicationSession.getInstance().getMessage("social.sec.notsave.success"));

			}
			return new ModelAndView(new MappingJackson2JsonView(), MainetConstants.FORM_NAME, respObj);

		} else {
			String year = null;
			try {
				year = Utility.getFinancialYearFromDate(this.getModel().getConfigurtionMasterDto().getToDate());
			} catch (Exception e) {
				LOGGER.info("Error occured while fetcing year");
			}
			if (year != null ) 
			getModel().addValidationError((ApplicationSession.getInstance().getMessage("social.sec.scheme.exists1")) + year + (ApplicationSession.getInstance().getMessage("social.sec.scheme.exists2")));
			else
			getModel().addValidationError((ApplicationSession.getInstance().getMessage("social.sec.scheme.exists1")) +" "+ (ApplicationSession.getInstance().getMessage("social.sec.scheme.exists2")));
		}
		return defaultMyResult();

	}

	@RequestMapping(params = "editForm", method = RequestMethod.POST)
	public ModelAndView cdmForm(@RequestParam(value = "id") Long id, @RequestParam(value = "orgId") Long orgId,
			@RequestParam(value = "type") String type, final Model model, final HttpServletRequest httpServletRequest) {
		sessionCleanup(httpServletRequest);
		fileUpload.sessionCleanUpForFileUpload();
		PensionSchemeMasterModel pSmodel = this.getModel();
		int langId = UserSession.getCurrent().getLanguageId();
		Organisation org = UserSession.getCurrent().getOrganisation();
		Long depId = tbDepartmentService.getDepartmentIdByDeptCode(MainetConstants.SocialSecurity.DEPARTMENT_SORT_CODE);
		final LookUp lookUpFieldStatus = CommonMasterUtility.getLookUpFromPrefixLookUpValue(MainetConstants.MASTER.A,
				PrefixConstants.ACN, langId, org);
		final Long activeStatusId = lookUpFieldStatus.getLookUpId();
		pSmodel.setServiceList(serviceMasterService.findAllActiveServicesByDepartment(orgId, depId, activeStatusId));
		pSmodel.setSponcerByList(CommonMasterUtility.getLookUps(MainetConstants.SocialSecurity.SBY, org));
		List<LookUp> paymentList = CommonMasterUtility.getLookUps(MainetConstants.SocialSecurity.PAYMENT_PREFIX, org);
		pSmodel.setPaymentList(paymentList);
		pSmodel.setSecondLevellookUps(
				CommonMasterUtility.getNextLevelData(MainetConstants.SocialSecurity.FTR, 2, orgId));

		/*
		 * // changes pSmodel.getPensionSchmDto().setSchmeMsId(id);
		 * pSmodel.getPensionSchmDto().getIsSchmeActive(); if
		 * (pSmodel.getPensionSchmDto().getIsSchmeActive().equals("N")) {
		 * pSmodel.setModeType("V"); } if
		 * (pSmodel.getPensionSchmDto().getIsSchmeActive().equals("Y")) {
		 * pSmodel.setModeType(type); }
		 */

		pSmodel.getOneDetails(id, orgId, type);
		/* setting schemeid to inactive scheme if checkbox clicked */
		pSmodel.getPensionSchmDto().setSchmeMsId(id);
		pSmodel.getPensionSchmDto().getIsSchmeActive();
		// if scheme is inactive the form will open in view mode

		if (pSmodel.getPensionSchmDto().getIsSchmeActive().equals("N")) {
			pSmodel.getPensionSchmDto().setCheckBox(false);
			pSmodel.setModeType("V");
		}
		if (pSmodel.getPensionSchmDto().getIsSchmeActive().equals("Y")) {
			pSmodel.getPensionSchmDto().setCheckBox(true);
			pSmodel.setModeType(type);
		}

		pSmodel.getModeType();

		List<LookUp> set = pensionSchemeMasterService.filterCriteria(
				CommonMasterUtility.getNextLevelData(MainetConstants.SocialSecurity.FTR, 1, orgId),
				pSmodel.getPensionSchmDto().getPensioneligibilityList());
		if (Utility.isEnvPrefixAvailable(UserSession.getCurrent().getOrganisation(), MainetConstants.ENV_TSCL))
		pSmodel.setSourceLookUps(CommonMasterUtility.getNextLevelData(MainetConstants.SocialSecurity.FTR, 1, orgId));
		else {
			pSmodel.getSourceLookUps().addAll(new HashSet<>(set));
			pSmodel.getSourceLookUps().sort((p1, p2) -> {
				return p1.getLookUpId() < p2.getLookUpId() ? -1 : p1.getLookUpId() == p2.getLookUpId() ? 0 : 1;
			});
		}
		
		// pSmodel.getPensionSchmDto().setPensioneligibilityList(new ArrayList<>());
		pSmodel.getPensionSchmDto().getPensioneligibilityList();
        //D#143073
		if (pSmodel.getPensionSchmDto().getPensioneligibilityList() != null) {
		pSmodel.getPensionSchmDto().setPaySchedule(pSmodel.getPensionSchmDto().getPensioneligibilityList().get(0).getPaySchedule());
		pSmodel.getPensionSchmDto().setAmt(pSmodel.getPensionSchmDto().getPensioneligibilityList().get(0).getAmt());
		}
		if (Utility.isEnvPrefixAvailable(UserSession.getCurrent().getOrganisation(), MainetConstants.ENV_TSCL)) {
			
			List<LookUp> sourceLookUp = CommonMasterUtility.getNextLevelData(MainetConstants.SocialSecurity.FTR, 1,UserSession.getCurrent().getOrganisation().getOrgid());			
	         List<LookUp> list = (schemeApplicationFormService.FindSecondLevelPrefixByFirstLevelPxCode(
						UserSession.getCurrent().getOrganisation().getOrgid(), "FTR",
						sourceLookUp.stream().filter(k -> k.getLookUpCode().equalsIgnoreCase("SS"))
								.collect(Collectors.toList()).get(0).getLookUpId(),
						2L));

				this.getModel().setSubTypeList(list);
				
			pSmodel.setConfigurtionMasterDto(configurationMasterService.getConfigMstDataBySchemeId(pSmodel.getPensionSchmDto().getServiceId(), orgId));
			pSmodel.setEnvFlag(MainetConstants.FlagY);
			boolean result = schemeApplicationFormService.checkAppPresentAgainstScheme(pSmodel.getPensionSchmDto().getServiceId(), orgId);
	    	if (result == true) 
			pSmodel.setApplValidFlag(MainetConstants.FlagY);
	    	else 
	    		pSmodel.setApplValidFlag(MainetConstants.FlagN);
	    	
	    	long sum = pSmodel.getPensionSchmDto().getSubSchemeDetailsDtoList().stream().mapToLong(SubSchemeDetailsDto::getTotalAmount).sum();
	    	pSmodel.setBeneficiaryAmt(BigDecimal.valueOf(sum));
	    	long counts = pSmodel.getPensionSchmDto().getSubSchemeDetailsDtoList().stream().mapToLong(SubSchemeDetailsDto::getNoBeneficiary).sum();
			pSmodel.setBeneficiaryCount(Long.valueOf(counts));
		}
		final List<TbFinancialyear> finYearList = ApplicationContextProvider.getApplicationContext()
                .getBean(TbFinancialyearService.class)
                .findAllFinancialYearByOrgId(org);
	// pSmodel.setFaYears(finYearList);
		pSmodel.getFaYears().clear();
	 if (finYearList != null && !finYearList.isEmpty()) {
         finYearList.forEach(finYearTemp -> {
             try {
                 finYearTemp.setFaYearFromTo(Utility.getFinancialYearFromDate(finYearTemp.getFaFromDate()));
                 pSmodel.getFaYears().add(finYearTemp);
             } catch (Exception ex) {
                 throw new FrameworkException( ex);
             }
         });
         Comparator<TbFinancialyear> comparing = Comparator.comparing(TbFinancialyear::getFaYearFromTo,
                 Comparator.reverseOrder());
         Collections.sort(pSmodel.getFaYears(), comparing);
     }
		return new ModelAndView("PensionSchemeMasterForm", MainetConstants.FORM_NAME, pSmodel);
	}

	@RequestMapping(params = "updatePensionDetails", method = RequestMethod.POST)
	public ModelAndView updatePensionDetails(final Model model, final HttpServletRequest httpServletRequest) {
		bindModel(httpServletRequest);
		ModelAndView mv = null;
		BindingResult bindingResult = this.getModel().getBindingResult();
		Long orgId = UserSession.getCurrent().getOrganisation().getOrgid();
		Long empId = UserSession.getCurrent().getEmployee().getEmpId();
		String ipMacAddress = Utility.getClientIpAddress(httpServletRequest);
		this.getModel().getPensionSchmDto().setSaveDataList(this.getModel().getSaveDataList());
		boolean status = false;
		try {
			pensionSchemeMasterService.updatePensionDetails(orgId, empId, ipMacAddress,
					this.getModel().getPensionSchmDto(), this.getModel().getDeletedBatchIdSet(),
					this.getModel().getUpdateBatchIdSet());
			status = true;
			mv = updateDetails(httpServletRequest, status);
		} catch (FrameworkException ex) {
			model.addAttribute(BindingResult.MODEL_KEY_PREFIX + MainetConstants.FORM_NAME, bindingResult);
			throw new FrameworkException("while updating and deleting data problem occurs", ex);
		}

		return mv;
	}

	@RequestMapping(params = "filterServices", method = { RequestMethod.POST })
	public @ResponseBody List<ViewDtoList> filterSearchData(final HttpServletRequest request,
			@RequestParam(value = "serviceId", required = true) final Long serviceId) {
		final List<ViewDtoList> viewDtoList;
		try {
			viewDtoList = this.getModel().getViewList().stream().filter(l -> l.getServiceId().equals(serviceId))
					.collect(Collectors.toList());
		} catch (Exception ex) {
			throw new FrameworkException("services are  not found", ex);
		}
		return viewDtoList;
	}

	private ModelAndView updateDetails(final HttpServletRequest request, final boolean status) {
		if (status) {
			return jsonResult(JsonViewObject
					.successResult(getApplicationSession().getMessage("asset.maintainanceType.successMessage")));
		} else {
			return jsonResult(JsonViewObject
					.failureResult(getApplicationSession().getMessage("asset.maintainanceType.failureMessage")));
		}
	}

	@RequestMapping(params = "inactiveScheme", method = RequestMethod.POST)
	public ModelAndView inactiveScheme(final HttpServletRequest httpServletRequest) {
		Long orgId = UserSession.getCurrent().getOrganisation().getOrgid();
		PensionSchemeMasterModel pSmodel = this.getModel();
		pensionSchemeMasterService.inactiveScheme(pSmodel.getPensionSchmDto().getSchmeMsId(), orgId);

		return defaultMyResult();
	}

	@RequestMapping(params = "resetForm", method = RequestMethod.POST)
	public ModelAndView resetForm(@RequestParam(value = "id") Long id, @RequestParam(value = "orgId") Long orgId,
			@RequestParam(value = "type") String type, final Model model, final HttpServletRequest httpServletRequest) {
		sessionCleanup(httpServletRequest);
		PensionSchemeMasterModel pSmodel = this.getModel();
		int langId = UserSession.getCurrent().getLanguageId();
		Organisation org = UserSession.getCurrent().getOrganisation();
		Long depId = tbDepartmentService.getDepartmentIdByDeptCode(MainetConstants.SocialSecurity.DEPARTMENT_SORT_CODE);
		final LookUp lookUpFieldStatus = CommonMasterUtility.getLookUpFromPrefixLookUpValue(MainetConstants.MASTER.A,
				PrefixConstants.ACN, langId, org);
		final Long activeStatusId = lookUpFieldStatus.getLookUpId();
		pSmodel.setServiceList(serviceMasterService.findAllActiveServicesByDepartment(orgId, depId, activeStatusId));
		pSmodel.setSponcerByList(CommonMasterUtility.getLookUps(MainetConstants.SocialSecurity.SBY, org));
		List<LookUp> paymentList = CommonMasterUtility.getLookUps(MainetConstants.SocialSecurity.PAYMENT_PREFIX, org);
		pSmodel.setPaymentList(paymentList);
		pSmodel.setSecondLevellookUps(
				CommonMasterUtility.getNextLevelData(MainetConstants.SocialSecurity.FTR, 2, orgId));

		pSmodel.getOneDetails(id, orgId, type);
		/* setting schemeid to inactive scheme if checkbox clicked */
		pSmodel.getPensionSchmDto().setSchmeMsId(id);
		// if scheme is inactive the form will open in view mode

		if (pSmodel.getPensionSchmDto().getIsSchmeActive().equals("N")) {
			pSmodel.getPensionSchmDto().setCheckBox(false);
			pSmodel.setModeType("V");
		}
		if (pSmodel.getPensionSchmDto().getIsSchmeActive().equals("Y")) {
			pSmodel.getPensionSchmDto().setCheckBox(true);
			pSmodel.setModeType(type);
		}

		pSmodel.setSourceLookUps(CommonMasterUtility.getNextLevelData(MainetConstants.SocialSecurity.FTR, 1, orgId));
		// Reset existing eligibility criteria list
		pSmodel.getPensionSchmDto().getPensioneligibilityList().clear();
		pSmodel.getSaveDataList().clear();
		return new ModelAndView("PensionSchemeMasterForm", MainetConstants.FORM_NAME, pSmodel);
	}

	public List<DocumentDetailsVO> prepareFileUploadForImg(List<DocumentDetailsVO> document) throws IOException {

		final Map<Long, String> listOfString = new HashMap<>();
		final Map<Long, String> fileName = new HashMap<>();
		if ((FileUploadUtility.getCurrent().getFileMap() != null)
				&& !FileUploadUtility.getCurrent().getFileMap().isEmpty()) {
			for (final Map.Entry<Long, Set<File>> entry : FileUploadUtility.getCurrent().getFileMap().entrySet()) {
				final List<File> list = new ArrayList<>(entry.getValue());
				for (final File file : list) {
					try {
						final Base64 base64 = new Base64();
						final String bytestring = base64.encodeToString(FileUtils.readFileToByteArray(file));
						fileName.put(entry.getKey(), file.getName());
						listOfString.put(entry.getKey(), bytestring);
					} catch (final IOException e) {
						LOGGER.error("Exception has been occurred in file byte to string conversions", e);
					}
				}
			}
		}

		List<DocumentDetailsVO> document1=new ArrayList<DocumentDetailsVO>();
		if ( !listOfString.isEmpty()) {
			long count = 500;
				if (listOfString.containsKey(count) && fileName.containsKey(count)) {
					DocumentDetailsVO vo=new DocumentDetailsVO();
					vo.setDocumentByteCode(listOfString.get(count));
					vo.setDocumentName(fileName.get(count));
					document1.add(vo);
				}
				
			
		}

		return document1;
	}
	
	@RequestMapping(params = "getSubScheme", method = RequestMethod.POST)
	public ModelAndView getSubScheme(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse,
			@RequestParam(value = "serviceId") Long serviceId) {
		this.getModel().bind(httpServletRequest);
		PensionSchemeMasterModel pSmodel = this.getModel();
		Long orgId = UserSession.getCurrent().getOrganisation().getOrgid();
		List<LookUp> list = new ArrayList<>();
		List<LookUp> subList = new ArrayList<>();
		String shortCode = ApplicationContextProvider.getApplicationContext().getBean(ServiceMasterService.class)
				.fetchServiceShortCode(serviceId, orgId);
		LookUp look = CommonMasterUtility.getHieLookupByLookupCode("SS", MainetConstants.SocialSecurity.FTR, 1, orgId);
		List<LookUp> secondLevelData = CommonMasterUtility.getNextLevelData(MainetConstants.SocialSecurity.FTR, 2,orgId);
		Organisation orgid = iOrganisationService.getSuperUserOrganisation();
		for (int i = 0; i < secondLevelData.size(); i++) {
			if (look.getLookUpId() == secondLevelData.get(i).getLookUpParentId()) {
				String id = pensionSchemeMasterService.getPrefixOtherValue(secondLevelData.get(i).getLookUpId(),
						orgid.getOrgid());
				if (id != null && id != "" && id.equals(shortCode)) {
					list.add(secondLevelData.get(i));
				subList.add(secondLevelData.get(i));}
			} else {
				list.add(secondLevelData.get(i));
			}
		}
		pSmodel.setSubTypeList(subList);
		pSmodel.setSecondLevellookUps(list);
		pSmodel.getPensionSchmDto().setServiceId(serviceId);
		pSmodel.getPensionSchmDto().setOrgId(orgId);
		return new ModelAndView("PensionSchemeMasterForm", MainetConstants.FORM_NAME, pSmodel);
	}
	
	@RequestMapping(params = "bankCode", method = RequestMethod.POST)
	public @ResponseBody List<BankMasterEntity> getBankCode(@RequestParam("banknameId") final String banknameId) {

		final List<BankMasterEntity> details = bankMasterService.getBankListByName(banknameId);

		return details;
	}
	
}
