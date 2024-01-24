package com.abm.mainet.property.ui.controller;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.ListIterator;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.stream.Collectors;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.abm.mainet.common.constant.MainetConstants;
import com.abm.mainet.common.constant.PrefixConstants;
import com.abm.mainet.common.domain.LocationMasEntity;
import com.abm.mainet.common.domain.Organisation;
import com.abm.mainet.common.domain.ServiceMaster;
import com.abm.mainet.common.integration.dms.service.IFileUploadService;
import com.abm.mainet.common.integration.dto.DocumentDetailsVO;
import com.abm.mainet.common.integration.dto.TbBillMas;
import com.abm.mainet.common.service.ILocationMasService;
import com.abm.mainet.common.service.IOrganisationService;
import com.abm.mainet.common.service.ServiceMasterService;
import com.abm.mainet.common.ui.controller.AbstractFormController;
import com.abm.mainet.common.utility.ApplicationContextProvider;
import com.abm.mainet.common.utility.ApplicationSession;
import com.abm.mainet.common.utility.CommonMasterUtility;
import com.abm.mainet.common.utility.LookUp;
import com.abm.mainet.common.utility.UserSession;
import com.abm.mainet.common.utility.Utility;
import com.abm.mainet.common.workflow.dto.WorkflowRequest;
import com.abm.mainet.common.workflow.service.IWorkflowRequestService;
import com.abm.mainet.property.dto.ArrayOfDiversionPlotDetails;
import com.abm.mainet.property.dto.ArrayOfKhasraDetails;
import com.abm.mainet.property.dto.ArrayOfPlotDetails;
import com.abm.mainet.property.dto.LandTypeApiDetailRequestDto;
import com.abm.mainet.property.dto.PropertyTransferMasterDto;
import com.abm.mainet.property.dto.PropertyTransferOwnerDto;
import com.abm.mainet.property.dto.ProvisionalAssesmentDetailDto;
import com.abm.mainet.property.dto.ProvisionalAssesmentMstDto;
import com.abm.mainet.property.dto.ProvisionalAssesmentOwnerDtlDto;
import com.abm.mainet.property.service.AssesmentMastService;
import com.abm.mainet.property.service.DataEntrySuiteService;
import com.abm.mainet.property.service.IProvisionalAssesmentMstService;
import com.abm.mainet.property.service.MutationService;
import com.abm.mainet.property.service.PrimaryPropertyService;
import com.abm.mainet.property.service.SelfAssessmentService;
import com.abm.mainet.property.ui.model.MutationModel;

@Controller
@RequestMapping("/MutationForm.html")
public class MutationFormController extends AbstractFormController<MutationModel> {

    private static final Logger LOGGER = Logger.getLogger(MutationFormController.class);
    @Resource
    private ILocationMasService iLocationMasService;

    @Autowired
    private ServiceMasterService serviceMaster;

    @Resource
    private SelfAssessmentService selfAssessmentService;

    @Autowired
    private MutationService mutationService;

    @Autowired
    private IFileUploadService fileUpload;

    @Autowired
    private DataEntrySuiteService dataEntrySuiteService;

    @Autowired
    private AssesmentMastService assesmentMastService;
       
    @Autowired
    private PrimaryPropertyService primaryPropertyService;
    
    @Autowired
    private IWorkflowRequestService workflowReqService;
    
    @Autowired
    private IProvisionalAssesmentMstService iProvisionalAssesmentMstService;

    @RequestMapping(method = { RequestMethod.POST, RequestMethod.GET })
    public ModelAndView index(HttpServletRequest request) {
        this.sessionCleanup(request);
        fileUpload.sessionCleanUpForFileUpload();
        getModel().bind(request);
        MutationModel model = this.getModel();
        model.setMutationLevelFlag("M");
        Long orgId = UserSession.getCurrent().getOrganisation().getOrgid();
        ServiceMaster service = serviceMaster.getServiceByShortName(MainetConstants.Property.MUT, orgId);
        getModel().setServiceMast(service);
        getModel().setOrgId(orgId);
        getModel().setDeptId(service.getTbDepartment().getDpDeptid());
        getModel().setDeptName(service.getTbDepartment().getDpDeptdesc());
        getModel().setServiceName(service.getSmServiceName());
        getModel().setCommonHelpDocs("MutationForm.html");
        return new ModelAndView("MutationForm", MainetConstants.FORM_NAME, model);
    }

    // US#96773 KDMC
    @Override
    @RequestMapping(params = "PrintReport")
    public ModelAndView printRTIStatusReport(HttpServletRequest request) {
        Organisation org = UserSession.getCurrent().getOrganisation();
        if (Utility.isEnvPrefixAvailable(org, MainetConstants.Property.MUT)) {
        	//D#147248
        	Date date = new Date();
    		this.getModel().setDate(Utility.dateToString(date, MainetConstants.DATE_FORMAT));
    		String currentYear = null;
			try {
				currentYear = Utility.getFinancialYearFromDate(date);
			} catch (Exception e) {
				e.printStackTrace();
			}
    		getModel().setFinYear(currentYear);
    		this.getModel().setDueDate(Utility.dateToString(this.getModel().dueDate(date), MainetConstants.DATE_FORMAT));
    		SimpleDateFormat localDateFormat = new SimpleDateFormat("HH:mm");
    		this.getModel().setTime(localDateFormat.format(new Date()));
    		return new ModelAndView("mutationAcknowledgement", MainetConstants.FORM_NAME, this.getModel());
        }
        return super.printRTIStatusReport(request);
    }

    @RequestMapping(params = "getMutationDetail", method = RequestMethod.POST)
    public ModelAndView getMutationDetail(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse,
            @RequestParam(value = "propNo") String propNo, @RequestParam(value = "oldPropNo") String oldPropNo) {
        this.getModel().bind(httpServletRequest);
		return getMutationData(propNo, oldPropNo,null);
    }
    
	@RequestMapping(params = "getMutationDetailWithFlat", method = RequestMethod.POST)
	public ModelAndView getMutationDetailWithFlat(HttpServletRequest httpServletRequest,
			HttpServletResponse httpServletResponse, @RequestParam(value = "propNo") String propNo,
			@RequestParam(value = "oldPropNo") String oldPropNo, @RequestParam(value = "flatNo") String flatNo) {
		this.getModel().bind(httpServletRequest);
		return getMutationData(propNo, oldPropNo,flatNo);
	}
    
    public ModelAndView getMutationData(String propNo,String oldPropNo,String flatNo){
        MutationModel model = this.getModel();
        this.getModel().getProvisionalAssesmentMstDto().setAssNo(propNo);
        this.getModel().getProvisionalAssesmentMstDto().setAssOldpropno(oldPropNo);
        final long orgId = UserSession.getCurrent().getOrganisation().getOrgid();
        List<TbBillMas> paidFlagList = null;
        List<String> checkActiveFlagList = null;
		if (StringUtils.isBlank(propNo)) {
			if (StringUtils.isNotBlank(flatNo)) {
				checkActiveFlagList = assesmentMastService.checkActiveFlagByOldPropNoNFlatNo(oldPropNo, flatNo, orgId);
			} else {
				checkActiveFlagList = assesmentMastService.checkActiveFlagByOldPropNo(oldPropNo, orgId);
			}
		} else {
			if (StringUtils.isNotBlank(flatNo)) {
				checkActiveFlagList = assesmentMastService.checkActiveFlagByPropnonFlatNo(propNo, flatNo, orgId);
			} else {
				checkActiveFlagList = assesmentMastService.checkActiveFlag(propNo, orgId);
			}
		}
        String checkActiveFlag = null;
        if (CollectionUtils.isNotEmpty(checkActiveFlagList)) {
            checkActiveFlag = checkActiveFlagList.get(checkActiveFlagList.size() - 1);
        }
        if (StringUtils.isNotBlank(checkActiveFlag) && StringUtils.equals(MainetConstants.STATUS.INACTIVE, checkActiveFlag)) {
            getModel().addValidationError(getApplicationSession().getMessage("property.inactive"));
            ModelAndView mv = new ModelAndView("MutationFormValidn", MainetConstants.FORM_NAME, this.getModel());
            mv.addObject(BindingResult.MODEL_KEY_PREFIX + MainetConstants.FORM_NAME, getModel().getBindingResult());
            return mv;
        }
		// To check any application is in progress
		ProvisionalAssesmentMstDto assessMst = null;
		List<ProvisionalAssesmentMstDto> assMstList = null;
		if (StringUtils.isBlank(propNo)) {
			if (StringUtils.isNotBlank(flatNo)) {
				assMstList = iProvisionalAssesmentMstService.getPropDetailFromProvAssByOldPropNoAndFlatNo(orgId,
						oldPropNo, flatNo, MainetConstants.FlagA);
				if (CollectionUtils.isNotEmpty(assMstList)) {
					assessMst = assMstList.get(0);
				}
			} else {
				assessMst = iProvisionalAssesmentMstService.fetchProvisionalDetailsByOldPropNo(oldPropNo, orgId);
			}
		} else {
			if (StringUtils.isNotBlank(flatNo)) {
				assMstList = iProvisionalAssesmentMstService.getDataEntryPropDetailFromProvAssByPropNoAndFlatNo(orgId,
						propNo, flatNo, MainetConstants.FlagA);
				if (CollectionUtils.isNotEmpty(assMstList)) {
					assessMst = assMstList.get(0);
				}
			} else {
				assessMst = iProvisionalAssesmentMstService.fetchProvisionalDetailsByPropNo(propNo, orgId);
			}
		}
		if (assessMst != null && assessMst.getApmApplicationId() != null) {
			WorkflowRequest workflowRequest = workflowReqService
					.getWorkflowRequestByAppIdOrRefId(assessMst.getApmApplicationId(), null, orgId);
			if (MainetConstants.WorkFlow.Status.PENDING.equalsIgnoreCase(workflowRequest.getStatus())) {
				getModel().setErrorMsg(MainetConstants.FlagY);
				getModel().addValidationError(
						ApplicationSession.getInstance().getMessage("property.applicationInProgress"));
				ModelAndView mv = new ModelAndView("MutationFormValidn", MainetConstants.FORM_NAME, this.getModel());
				mv.addObject(BindingResult.MODEL_KEY_PREFIX + MainetConstants.FORM_NAME, getModel().getBindingResult());
				return mv;
			}
		}			
		
        ServiceMaster service = serviceMaster.getServiceByShortName(MainetConstants.Property.MUT, orgId);
        // US#96773 KDMC
        LookUp kdmcFlag = null;
        try {
            kdmcFlag = CommonMasterUtility.getValueFromPrefixLookUp(MainetConstants.Property.MUT, MainetConstants.ENV,
                    UserSession.getCurrent().getOrganisation());
        } catch (Exception e) {
            LOGGER.error("No prefix found for ENV(MUT)" + e);
        }
        if (kdmcFlag != null
                && StringUtils.equals(kdmcFlag.getOtherField(), MainetConstants.FlagY)) {
            model.setKdmcMutFlag(kdmcFlag.getOtherField());
        }
        model.setServiceId(service.getSmServiceId());
        model.setServiceName(service.getSmServiceName());
        if (service.getSmFeesSchedule().equals(1l)) {
            model.setAppliChargeFlag(service.getSmAppliChargeFlag());
        } else {
            model.setAppliChargeFlag(MainetConstants.N_FLAG);
        }
        model.setDeptId(service.getTbDepartment().getDpDeptid());
        model.setCharges(null);
        model.setCheckList(null);
        model.setProvisionalAssesmentMstDto(null);        
        ProvisionalAssesmentMstDto assMst =null;
        PropertyTransferMasterDto transferDto=new PropertyTransferMasterDto();
        transferDto.setFlatNo(flatNo);
        model.setPropTransferDto(transferDto);
		if (StringUtils.isNotBlank(flatNo)) {
			assMst = mutationService.fetchMutationDetails(propNo, oldPropNo,flatNo, orgId);
		} else {
			assMst = mutationService.fetchDetailForMutataion(propNo, oldPropNo, orgId);
		}
        if (assMst != null) {

            // 109397 By Arun - To check mutation is already in progress or not
            try {
            	PropertyTransferMasterDto transferMasDto=null;
				if (StringUtils.isNotBlank(flatNo)) {
					transferMasDto = mutationService.getMutationByPropNonFlatNo(assMst.getAssNo(),flatNo, orgId);
				} else {
					transferMasDto = mutationService.getMutationByPropNo(assMst.getAssNo(), orgId);
				}
                if (transferMasDto.getApmApplicationId() != null) {
                    String status = mutationService.getWorkflowRequestByAppId(transferMasDto.getApmApplicationId(),
                            orgId);
                    if (MainetConstants.TASK_STATUS_PENDING.equalsIgnoreCase(status)) {
                    	getModel().setErrorMsg(MainetConstants.FlagY);
                        getModel().addValidationError(
                                "Mutation against property number " + assMst.getAssNo() + " is already in progress ");
                        ModelAndView mv = new ModelAndView("MutationFormValidn", MainetConstants.FORM_NAME,
                                this.getModel());
                        mv.addObject(BindingResult.MODEL_KEY_PREFIX + MainetConstants.FORM_NAME,
                                getModel().getBindingResult());
                        return mv;
                    }
                }
            } catch (Exception e) {
                LOGGER.error("Exception occured" + e);
            }
            // end
            if (!Utility.isEnvPrefixAvailable(UserSession.getCurrent().getOrganisation(), MainetConstants.Property.MUT)) {
            	getModel().setErrorMsg(MainetConstants.FlagY);
                if (StringUtils.equals(MainetConstants.N_FLAG, assMst.getAssAutStatus())) {
                    getModel().addValidationError(getApplicationSession().getMessage("under.mutation.auth.service"));
                    ModelAndView mv = new ModelAndView("MutationFormValidn", MainetConstants.FORM_NAME, this.getModel());
                    mv.addObject(BindingResult.MODEL_KEY_PREFIX + MainetConstants.FORM_NAME, getModel().getBindingResult());
                    return mv;
                }
            }
			if (!Utility.isEnvPrefixAvailable(UserSession.getCurrent().getOrganisation(), "SMV") && StringUtils.equalsIgnoreCase(MainetConstants.Property.DEMAND_GEN_PENDING, assMst.getDueStatus())) {
				getModel().setErrorMsg(MainetConstants.FlagY);
				if (StringUtils.isNotBlank(propNo)) {
					getModel().addValidationError(
							getApplicationSession().getMessage("property.demandNotGenerated") + " " + propNo);
				} else {
					getModel().addValidationError(
							getApplicationSession().getMessage("property.demandNotGenerated") + " " + oldPropNo);
				}
				ModelAndView mv = new ModelAndView("MutationFormValidn", MainetConstants.FORM_NAME, this.getModel());
				mv.addObject(BindingResult.MODEL_KEY_PREFIX + MainetConstants.FORM_NAME, getModel().getBindingResult());
				return mv;
			}
            if (!Utility.isEnvPrefixAvailable(UserSession.getCurrent().getOrganisation(), "SMV") && StringUtils.equalsIgnoreCase(MainetConstants.TASK_STATUS_PENDING, assMst.getDueStatus())) {
            	getModel().setErrorMsg(MainetConstants.FlagY);
                if (StringUtils.isNotBlank(propNo)) {
                    getModel().addValidationError(getApplicationSession().getMessage("dues.pending.propno") + " " + propNo);
                } else {
                    getModel()
                            .addValidationError(getApplicationSession().getMessage("dues.pending.old.propno") + " " + oldPropNo);
                }
                ModelAndView mv = new ModelAndView("MutationFormValidn", MainetConstants.FORM_NAME, this.getModel());
                mv.addObject(BindingResult.MODEL_KEY_PREFIX + MainetConstants.FORM_NAME, getModel().getBindingResult());
                return mv;
            }
            
            if (!Utility.isEnvPrefixAvailable(UserSession.getCurrent().getOrganisation(), PrefixConstants.ENV.MAS)
					&& !assMst.isAssesmentDoneForCurrentYear()) {
				getModel().setErrorMsg(MainetConstants.FlagY);
				getModel().addValidationError(getApplicationSession().getMessage("assesment.not.done"));
				ModelAndView mv = new ModelAndView("MutationFormValidn", MainetConstants.FORM_NAME, this.getModel());
				mv.addObject(BindingResult.MODEL_KEY_PREFIX + MainetConstants.FORM_NAME, getModel().getBindingResult());
				return mv;
			}
            
            getModel().setAssType(MainetConstants.Property.MUT);
            LookUp ownerType = CommonMasterUtility.getNonHierarchicalLookUpObject(
                    assMst.getAssOwnerType(), UserSession.getCurrent().getOrganisation());
            model.setOwnershipPrefix(ownerType.getLookUpCode());
            LookUp lookup = CommonMasterUtility.getValueFromPrefixLookUp(ownerType.getLookUpCode(),
                    MainetConstants.Property.propPref.OWT,
                    UserSession.getCurrent().getOrganisation());
            model.setOwnershipTypeValue(lookup.getDescLangFirst());
            model.setOwnerType(lookup.getLookUpId());
            List<LookUp> locList = getModel().getLocation();
            List<LocationMasEntity> location = iLocationMasService.findWZMappedLocationByOrgIdAndDeptId(orgId,
                    model.getDeptId());
            if (location != null && !location.isEmpty()) {
                location.forEach(loc -> {
                    LookUp lookUp = new LookUp();
                    lookUp.setLookUpId(loc.getLocId());
                    lookUp.setDescLangFirst(loc.getLocNameEng());
                    lookUp.setDescLangSecond(loc.getLocNameReg());
                    locList.add(lookUp);
                });
            }
            if (assMst.getAssLandType() != null && assMst.getAssLandType() > 0) {
                assMst.setAssLandTypeDesc(CommonMasterUtility
                        .getNonHierarchicalLookUpObject(assMst.getAssLandType(),
                                UserSession.getCurrent().getOrganisation())
                        .getDescLangFirst());
                LookUp landTypePrefix = CommonMasterUtility.getNonHierarchicalLookUpObject(
                        assMst.getAssLandType(), UserSession.getCurrent().getOrganisation());
                getModel().setLandTypePrefix(landTypePrefix.getLookUpCode());
            }
            // US#89348 Sum ALV/ARV of all floor.
            List<Double> totalARV = new ArrayList<Double>();
            List<ProvisionalAssesmentDetailDto> detailDtoList = assMst.getProvisionalAssesmentDetailDtoList();
            for (ProvisionalAssesmentDetailDto dto : detailDtoList) {
                if (dto.getAssdRv() != null) {
                    totalARV.add(dto.getAssdRv());
                }
            }
            if (CollectionUtils.isNotEmpty(totalARV)) {
                Double arv = totalARV.stream().mapToDouble(Double::doubleValue).sum();
                assMst.setTotalArv(arv);
            } else {
                assMst.setTotalArv(0.0);
            }// END of US#89348
            
            //To set flat level owner name in case of Individual property
			if (assMst.getBillMethod() != null && MainetConstants.FlagI.equals(CommonMasterUtility
					.getNonHierarchicalLookUpObject(assMst.getBillMethod(), UserSession.getCurrent().getOrganisation())
					.getLookUpCode())) {
				getModel().setOwnershipPrefix(MainetConstants.Property.SO);
				List<ProvisionalAssesmentOwnerDtlDto> ownerDtoList = new ArrayList<>();
				ProvisionalAssesmentOwnerDtlDto ownerDto = new ProvisionalAssesmentOwnerDtlDto();
				ProvisionalAssesmentDetailDto detailDto = assMst.getProvisionalAssesmentDetailDtoList()
						.get(assMst.getProvisionalAssesmentDetailDtoList().size() - 1);
				ownerDto.setAssoOwnerName(detailDto.getOccupierName());
				ownerDto.setAssoOwnerNameReg(detailDto.getOccupierNameReg());
				ownerDto.seteMail(detailDto.getOccupierEmail());
				ownerDto.setAssoMobileno(detailDto.getOccupierMobNo());
				ownerDtoList.add(ownerDto);
				assMst.setProvisionalAssesmentOwnerDtlDtoList(ownerDtoList);
			}
			
            model.setProvisionalAssesmentMstDto(assMst);
            if (getModel().getLandTypePrefix() != null) {
                setlandTypeDetails(getModel());
            }
            model.setDropDownValues(assMst, UserSession.getCurrent().getOrganisation());
            return defaultMyResult();
        }
        getModel().setErrorMsg(MainetConstants.FlagY);
        getModel().addValidationError("Enter valid Property No or Old Property No");
        ModelAndView mv = new ModelAndView("MutationFormValidn", MainetConstants.FORM_NAME, this.getModel());
        mv.addObject(BindingResult.MODEL_KEY_PREFIX + MainetConstants.FORM_NAME, getModel().getBindingResult());
        return mv;
    }
    
    private void setlandTypeDetails(MutationModel model) {
        LandTypeApiDetailRequestDto dto = new LandTypeApiDetailRequestDto();
        dto.setLandType(model.getLandTypePrefix());
        dto.setDistrictId(model.getProvisionalAssesmentMstDto().getAssDistrict());
        dto.setTehsilId(model.getProvisionalAssesmentMstDto().getAssTahasil());
        dto.setVillageId(model.getProvisionalAssesmentMstDto().getTppVillageMauja());
        dto.setMohallaId(model.getProvisionalAssesmentMstDto().getMohalla());
        model.setDistrict(dataEntrySuiteService.findDistrictByLandType(dto));
        model.setTehsil(dataEntrySuiteService.getTehsilListByDistrict(dto));
        model.setVillage(dataEntrySuiteService.getVillageListByTehsil(dto));
        if (model.getLandTypePrefix().equals(MainetConstants.Property.LandType.NZL)
                || model.getLandTypePrefix().equals(MainetConstants.Property.LandType.DIV)) {
            getModel().setMohalla(dataEntrySuiteService.getMohallaListByVillageId(dto));
            getModel().setBlockStreet(dataEntrySuiteService.getStreetListByMohallaId(dto));
        }
    }

    @RequestMapping(params = "getCheckListAndCharges", method = RequestMethod.POST)
    public ModelAndView getCheckListAndCharges(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse) {
        this.getModel().bind(httpServletRequest);
        MutationModel model = this.getModel();
        PropertyTransferMasterDto tranDto = model.getPropTransferDto();
        ProvisionalAssesmentMstDto prvDto = model.getProvisionalAssesmentMstDto();
        LookUp ownerTypeNew = CommonMasterUtility.getNonHierarchicalLookUpObject(
                tranDto.getOwnerType(), UserSession.getCurrent().getOrganisation());
        model.setOwnershipPrefixNew(ownerTypeNew.getLookUpCode());
        model.setOwnerType(ownerTypeNew.getLookUpId());
        LookUp transferType = CommonMasterUtility.getNonHierarchicalLookUpObject(
                tranDto.getTransferType(), UserSession.getCurrent().getOrganisation());
        tranDto.setTransferTypeDesc(transferType.getLookUpDesc());
        tranDto.setProAssNo(prvDto.getAssNo());
        tranDto.setOrgId(UserSession.getCurrent().getOrganisation().getOrgid());
        tranDto.setSmServiceId(model.getServiceId());
        tranDto.setDeptId(model.getDeptId());
        
        AtomicBoolean dupOwnerAadharNo = new AtomicBoolean(false);
		  
		  if(tranDto.getPropTransferOwnerList().size() > 1) {
			  tranDto.getPropTransferOwnerList().forEach(ownerDto ->{
				List<PropertyTransferOwnerDto> ownerAadharList = tranDto.getPropTransferOwnerList()
						.stream().filter(owner -> owner.getAddharno().equals(ownerDto.getAddharno()))
						.collect(Collectors.toList());
				if(!dupOwnerAadharNo.get() && ownerAadharList.size() > 1) {
					model.addValidationError("Same aadhar no for multiple owner is not allowed");
					dupOwnerAadharNo.getAndSet(true);
				}
			  });
		  }
		  if (model.hasValidationErrors()) {
            return defaultMyResult();
        }
		LookUp checkListApplLookUp = null;
		List<DocumentDetailsVO> docs = null;
		ServiceMaster serviceMas = serviceMaster.getServiceByShortName(MainetConstants.Property.MUT, model.getOrgId());
		if (serviceMas != null) {
			checkListApplLookUp = CommonMasterUtility.getNonHierarchicalLookUpObject(serviceMas.getSmChklstVerify(),
					ApplicationContextProvider.getApplicationContext().getBean(IOrganisationService.class)
							.getOrganisationById(model.getOrgId()));
			if (StringUtils.equalsIgnoreCase(checkListApplLookUp.getLookUpCode(), MainetConstants.FlagA)) {
				docs = mutationService.fetchCheckList(tranDto);
			}
		}
		model.setCheckList(docs);
        if (MainetConstants.Y_FLAG.equals(model.getAppliChargeFlag())) {
            tranDto.setAppliChargeFlag("Y");
            mutationService.fetchChargesForMuatation(tranDto, prvDto);
        }
        model.setSaveButFlag(MainetConstants.Y_FLAG);
        if (Utility.isEnvPrefixAvailable(UserSession.getCurrent().getOrganisation(), MainetConstants.ENV_SKDCL)
        		&& getModel().isAuthLevel() && getModel().isHideUserAction()) {
        	getModel().setLoiChargeApplFlag(MainetConstants.FlagN);
        	getModel().setApprovalFlag(MainetConstants.FlagN);
        	return new ModelAndView("MutationAuthView", MainetConstants.FORM_NAME, getModel());
        }
        getModel().setFormType(MainetConstants.BLANK);
        model.getOfflineDTO().setAmountToShow(getModel().getPropTransferDto().getBillTotalAmt());
        return defaultMyResult();

    }

    @RequestMapping(params = "getOwnershipTypeDiv", method = RequestMethod.POST)
    public ModelAndView getOwnershipTypeDiv(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse,
            @RequestParam(value = "ownershipType") String ownershipType) {
        this.getModel().bind(httpServletRequest);
        MutationModel model = this.getModel();
        model.setOwnershipPrefix(ownershipType);
        LookUp lookup = CommonMasterUtility.getValueFromPrefixLookUp(ownershipType, MainetConstants.Property.propPref.OWT,
                UserSession.getCurrent().getOrganisation());
        model.setOwnershipTypeValue(lookup.getDescLangFirst());
        // model.getProvisionalAssesmentMstDto().setAssOwnerType(lookup.getLookUpId());
        //US#137279 START
        if(Utility.isEnvPrefixAvailable(UserSession.getCurrent().getOrganisation(), MainetConstants.ENV_ASCL) && 
        		StringUtils.equals(MainetConstants.Property.JO, ownershipType)) {
        List<PropertyTransferOwnerDto> ownerDtoList = new ArrayList<>();
        PropertyTransferMasterDto transferMasterDto = new PropertyTransferMasterDto();
        if(model.getPropTransferDto().getPropTransferOwnerList().isEmpty()) {
        	model.getProvisionalAssesmentMstDto().getProvisionalAssesmentOwnerDtlDtoList().forEach(ownerDto ->{
            	PropertyTransferOwnerDto transferOwnerDto = new PropertyTransferOwnerDto();
            	transferOwnerDto.setOwnerName(ownerDto.getAssoOwnerName());
            	transferOwnerDto.setGenderId(ownerDto.getGenderId());
            	transferOwnerDto.setRelationId(ownerDto.getRelationId());
            	transferOwnerDto.setGuardianName(ownerDto.getAssoGuardianName());
            	transferOwnerDto.setPropertyShare(ownerDto.getPropertyShare());
            	transferOwnerDto.setMobileno(ownerDto.getAssoMobileno());
            	transferOwnerDto.seteMail(ownerDto.geteMail());
            	transferOwnerDto.setAddharno(ownerDto.getAssoAddharno());
            	transferOwnerDto.setPanno(ownerDto.getAssoPanno());
            	ownerDtoList.add(transferOwnerDto);
            });
            transferMasterDto.setPropTransferOwnerList(ownerDtoList);
            transferMasterDto.setOwnerType(lookup.getLookUpId());
            model.setPropTransferDto(transferMasterDto);
			} else {
				ListIterator<PropertyTransferOwnerDto> iter = model.getPropTransferDto().getPropTransferOwnerList().listIterator();
		        while(iter.hasNext()){
		        	if (iter.next().getOwnerName() == null) {
		        		iter.remove();
		          }
		        }
			}
		} //US#137279 END
        return new ModelAndView("PropTransferOwner", MainetConstants.FORM_NAME, model);
    }

    @RequestMapping(params = "deleteOwnerTable", method = RequestMethod.POST)
    public @ResponseBody void deleteOwnerTable(HttpServletRequest httpServletRequest,
            HttpServletResponse httpServletResponse, @RequestParam(value = "deletedOwnerRowId") Integer deletedOwnerRowId) {
        this.getModel().bind(httpServletRequest);
        MutationModel model = this.getModel();
        
        if(!model.getPropTransferDto().getPropTransferOwnerList().isEmpty()
        	&& model.getPropTransferDto().getPropTransferOwnerList().size() > deletedOwnerRowId) {
        	PropertyTransferOwnerDto ownerDto = model.getPropTransferDto().getPropTransferOwnerList().get(deletedOwnerRowId);
        	model.getPropTransferDto().getPropTransferOwnerList().remove(ownerDto);
        	model.getPropTransferDto().setPropTransferOwnerList(model.getPropTransferDto().getPropTransferOwnerList());
        }
    }

    @RequestMapping(params = "getLandTypeApiDetails", method = RequestMethod.POST)
    public ModelAndView getLandTypeApiDetails(final HttpServletRequest httpServletRequest,
            final HttpServletResponse httpServletResponse) {
        MutationModel model = this.getModel();
        LandTypeApiDetailRequestDto dto = new LandTypeApiDetailRequestDto();
        dto.setLandType(model.getLandTypePrefix());
        dto.setDistrictId(model.getProvisionalAssesmentMstDto().getAssDistrict());
        dto.setTehsilId(model.getProvisionalAssesmentMstDto().getAssTahasil());
        dto.setVillageId(model.getProvisionalAssesmentMstDto().getTppVillageMauja());
        dto.setMohallaId(model.getProvisionalAssesmentMstDto().getMohalla());
        dto.setStreetNo(model.getProvisionalAssesmentMstDto().getAssStreetNo());
        dto.setKhasraNo(model.getProvisionalAssesmentMstDto().getTppPlotNoCs());
        dto.setPlotNo(model.getProvisionalAssesmentMstDto().getTppPlotNo());
        if (model.getLandTypePrefix().equals(MainetConstants.Property.LandType.KPK)) {
            ArrayOfKhasraDetails response = dataEntrySuiteService.getKhasraDetails(dto);
            getModel().setArrayOfKhasraDetails(response);
        }

        else if (model.getLandTypePrefix().equals(MainetConstants.Property.LandType.NZL)) {
            ArrayOfPlotDetails response = dataEntrySuiteService.getNajoolDetails(dto);
            model.setArrayOfPlotDetails(response);
        } else if (model.getLandTypePrefix().equals(MainetConstants.Property.LandType.DIV)) {
            ArrayOfDiversionPlotDetails response = dataEntrySuiteService.getDiversionDetails(dto);
            model.setArrayOfDiversionPlotDetails(response);
        }
        return new ModelAndView("showPropertyKhasaraDetails", MainetConstants.FORM_NAME, model);
    }

	@ResponseBody
	@RequestMapping(params = "getBillingMethod", method = { RequestMethod.POST })
	public List<String> getBillingMethodAndFlatList(@RequestParam("propNo") String propNo, HttpServletRequest request) {
		this.getModel().bind(request);
		MutationModel model = this.getModel();
		List<String> flatNoList = null;
		String billingMethod = null;
		Long billingMethodId = primaryPropertyService.getBillMethodIdByPropNo(propNo,
				UserSession.getCurrent().getOrganisation().getOrgid());
		try {
			billingMethod = CommonMasterUtility
					.getNonHierarchicalLookUpObject(billingMethodId, UserSession.getCurrent().getOrganisation())
					.getLookUpCode();
		} catch (Exception e) {
			
		}
		this.getModel().setBillingMethod(billingMethod);
		if (StringUtils.isNotBlank(billingMethod) && StringUtils.equals(billingMethod, MainetConstants.FlagI)) {
			flatNoList = new ArrayList<String>();
			flatNoList = primaryPropertyService.getFlatNoIdByPropNo(propNo,
					UserSession.getCurrent().getOrganisation().getOrgid());
		}
		model.setFlatNoList(flatNoList);
		return flatNoList;
	}
	
	@RequestMapping(params = "editMutationForm", method = RequestMethod.POST)
    public ModelAndView editMutationForm(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse) {
        getModel().bind(httpServletRequest);
        final long orgId = UserSession.getCurrent().getOrganisation().getOrgid();
        if(CollectionUtils.isNotEmpty(getModel().getPropTransferDto().getCharges())) {
        	getModel().getPropTransferDto().getCharges().clear();        	
        }
        getModel().setFormType(MainetConstants.FlagE);
        List<LookUp> locList = getModel().getLocation();
        List<LocationMasEntity> location = iLocationMasService.findWZMappedLocationByOrgIdAndDeptId(orgId,
                getModel().getDeptId());
        if (location != null && !location.isEmpty()) {
            location.forEach(loc -> {
                LookUp lookUp = new LookUp();
                lookUp.setLookUpId(loc.getLocId());
                lookUp.setDescLangFirst(loc.getLocNameEng());
                lookUp.setDescLangSecond(loc.getLocNameReg());
                locList.add(lookUp);
            });
        }
        return new ModelAndView("MutationFormValidn", MainetConstants.FORM_NAME, getModel());
    }
}
