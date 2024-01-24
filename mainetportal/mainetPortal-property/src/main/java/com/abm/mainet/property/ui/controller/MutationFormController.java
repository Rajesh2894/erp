package com.abm.mainet.property.ui.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.ListIterator;

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
import com.abm.mainet.common.dto.DocumentDetailsVO;
import com.abm.mainet.common.service.IPortalServiceMasterService;
import com.abm.mainet.common.ui.controller.AbstractFormController;
import com.abm.mainet.common.util.ApplicationSession;
import com.abm.mainet.common.util.CommonMasterUtility;
import com.abm.mainet.common.util.LookUp;
import com.abm.mainet.common.util.UserSession;
import com.abm.mainet.common.util.Utility;
import com.abm.mainet.dms.service.FileUploadServiceValidator;
import com.abm.mainet.property.dto.ArrayOfDiversionPlotDetails;
import com.abm.mainet.property.dto.ArrayOfKhasraDetails;
import com.abm.mainet.property.dto.ArrayOfPlotDetails;
import com.abm.mainet.property.dto.LandTypeApiDetailRequestDto;
import com.abm.mainet.property.dto.PropertyTransferMasterDto;
import com.abm.mainet.property.dto.PropertyTransferOwnerDto;
import com.abm.mainet.property.dto.ProvisionalAssesmentDetailDto;
import com.abm.mainet.property.dto.ProvisionalAssesmentMstDto;
import com.abm.mainet.property.dto.ProvisionalAssesmentOwnerDtlDto;
import com.abm.mainet.property.service.MutationService;
import com.abm.mainet.property.service.SelfAssessmentService;
import com.abm.mainet.property.ui.model.MutationModel;

@Controller
@RequestMapping("/MutationForm.html")
public class MutationFormController extends AbstractFormController<MutationModel> {
	
	private static final Logger LOGGER = Logger.getLogger(MutationFormController.class);

    @Autowired
    private IPortalServiceMasterService serviceMaster;

    @Resource
    private SelfAssessmentService selfAssessmentService;

    @Autowired
    private MutationService mutationService;

    @RequestMapping(method = { RequestMethod.POST, RequestMethod.GET })
    public ModelAndView index(HttpServletRequest request) {
        this.sessionCleanup(request);
        FileUploadServiceValidator.getCurrent().sessionCleanUpForFileUpload();
        getModel().bind(request);
        MutationModel model = this.getModel();
        model.setMutationLevelFlag("M");
        Long orgId = UserSession.getCurrent().getOrganisation().getOrgid();
        getModel().setOrgId(orgId);
        final Long serviceId = serviceMaster.getServiceId(MainetConstants.Property.MUT, orgId);
        final Long deptId = serviceMaster.getServiceDeptIdId(serviceId);
        getModel().setDeptId(deptId);
        getModel().setServiceId(serviceId);
        getModel().setCommonHelpDocs("MutationForm.html");
        return new ModelAndView("MutationForm", MainetConstants.FORM_NAME, this.getModel());
    }

    @RequestMapping(params = "getMutationDetail", method = RequestMethod.POST)
    public ModelAndView getMutationDetail(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse,
            @RequestParam(value = "propNo") String propNo, @RequestParam(value = "oldPropNo") String oldPropNo) {
        this.getModel().bind(httpServletRequest);
		return getMutationData(propNo, oldPropNo, null);
    }
    
	@RequestMapping(params = "getMutationDetailWithFlat", method = RequestMethod.POST)
	public ModelAndView getMutationDetailWithFlat(HttpServletRequest httpServletRequest,
			HttpServletResponse httpServletResponse, @RequestParam(value = "propNo") String propNo,
			@RequestParam(value = "oldPropNo") String oldPropNo, @RequestParam(value = "flatNo") String flatNo, @RequestParam(value = "flatOrParent") String flatOrParent) {
		this.getModel().bind(httpServletRequest);
		this.getModel().setFlatOrParentLevelChange(flatOrParent);
		this.getModel().getPropTransferDto().setFlatOrParentLevelChange(flatOrParent);
		return getMutationData(propNo, oldPropNo, flatNo);
	}
    
    public ModelAndView getMutationData(String propNo, String oldPropNo, String flatNo) {
        MutationModel model = this.getModel();
        final long orgId = UserSession.getCurrent().getOrganisation().getOrgid();

        model.setCharges(null);
        model.setCheckList(null);
        //model.setProvisionalAssesmentMstDto(null);
        model.getProvisionalAssesmentMstDto().setAssNo(propNo);
        model.getProvisionalAssesmentMstDto().setAssOldpropno(oldPropNo);
        PropertyTransferMasterDto transferDto = new PropertyTransferMasterDto();
        transferDto.setFlatNo(flatNo);
        model.setPropTransferDto(transferDto);
        
        final ProvisionalAssesmentMstDto assMst = mutationService.fetchMutationDetails(propNo, oldPropNo, flatNo, orgId);
        ModelAndView mv = new ModelAndView("MutationFormValidn", MainetConstants.FORM_NAME, this.getModel());
        mv.addObject(BindingResult.MODEL_KEY_PREFIX + MainetConstants.FORM_NAME, getModel().getBindingResult());
        if (assMst != null) {
        	
        	// Defect #163143 To check clearance status
        	if(Utility.isEnvPrefixAvailable(UserSession.getCurrent().getOrganisation(), MainetConstants.APP_NAME.SKDCL)) {        		
        	if(!assMst.isChequeClearanceStatus()) {
        		getModel().addValidationError(ApplicationSession.getInstance().getMessage("prop.no.dues.cheque.not.clear"));
    			getModel().setErrorMsg(MainetConstants.FlagY);
    			return mv;
        	}
        	}
        	// To check property is active or inactive
    		Boolean propertyActiveOrNot = selfAssessmentService.checkWhetherPropertyIsActive(propNo, oldPropNo, orgId);
    		if (!propertyActiveOrNot) {
    			getModel().addValidationError(ApplicationSession.getInstance().getMessage("property.inactiveProperty"));
    			getModel().setErrorMsg(MainetConstants.FlagY);
    			return mv;
    		}
    		if(StringUtils.equalsIgnoreCase(MainetConstants.ApplicationMasterConstant.APPLICATION_STATUS_PENDING, assMst.getStatus())) {
    			getModel().addValidationError(ApplicationSession.getInstance().getMessage("property.applicationInProgress"));  	
    			getModel().setErrorMsg(MainetConstants.FlagY);
    			return mv;
    		}
    		else if (assMst.getOutstandingAmount() != null && assMst.getOutstandingAmount() <= 0) {
                LookUp ownerType = CommonMasterUtility.getNonHierarchicalLookUpObject(
                        assMst.getAssOwnerType(), UserSession.getCurrent().getOrganisation());
                model.setOwnershipPrefix(ownerType.getLookUpCode());
                LookUp lookup = CommonMasterUtility.getValueFromPrefixLookUp(ownerType.getLookUpCode(),
                        MainetConstants.Property.propPref.OWT);
                model.setOwnershipTypeValue(lookup.getDescLangFirst());
                if(getModel().getDeptId()!= null){
                List<LookUp> locList = mutationService.getLocationList(orgId, getModel().getDeptId());
                getModel().setLocation(locList);
                }
                if (assMst.getAssLandType() != null) {
                    assMst.setAssLandTypeDesc(CommonMasterUtility
                            .getNonHierarchicalLookUpObject(assMst.getAssLandType(),
                                    UserSession.getCurrent().getOrganisation())
                            .getDescLangFirst());
                    LookUp landTypePrefix = CommonMasterUtility.getNonHierarchicalLookUpObject(
                            assMst.getAssLandType(), UserSession.getCurrent().getOrganisation());
                    getModel().setLandTypePrefix(landTypePrefix.getLookUpCode());
                }

                // Sum ALV/ARV of all floor.
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
                }
                if (assMst.getBillMethod() != null && "I".equals(CommonMasterUtility
    					.getNonHierarchicalLookUpObject(assMst.getBillMethod(), UserSession.getCurrent().getOrganisation())
    					.getLookUpCode()) && StringUtils.equals("F", model.getFlatOrParentLevelChange())) {
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
    			//Defect #162870 The owner's name should be in capital by default.
    			ListIterator<ProvisionalAssesmentOwnerDtlDto> iterator = assMst.getProvisionalAssesmentOwnerDtlDtoList().listIterator();
    			while(iterator.hasNext()) {
    				ProvisionalAssesmentOwnerDtlDto ownerDtlDto = iterator.next();
    				if(ownerDtlDto != null && StringUtils.isNotBlank(ownerDtlDto.getAssoOwnerName())) {
    					ownerDtlDto.setAssoOwnerName(ownerDtlDto.getAssoOwnerName().toUpperCase());
    				}
    			}
                model.setProvisionalAssesmentMstDto(assMst);
                if (getModel().getLandTypePrefix() != null) {
                    setlandTypeDetails(getModel());
                }
                model.setDropDownValues(assMst, UserSession.getCurrent().getOrganisation());
                getModel().setAssType(MainetConstants.Property.MUT);
                return defaultMyResult();
            } else {
                if (StringUtils.isNotBlank(propNo)) {
                	getModel().addValidationError(ApplicationSession.getInstance().getMessage("property.mutationForm.duesPendingProperty") + propNo);
                } else {
                    getModel().addValidationError(ApplicationSession.getInstance().getMessage("property.mutationFor.oldDuesPending") + oldPropNo);
                }     
                getModel().setErrorMsg(MainetConstants.FlagY);
                return mv;
            }
        }
        getModel().addValidationError(ApplicationSession.getInstance().getMessage("property.noDues.propertySearchValid"));
        getModel().setErrorMsg(MainetConstants.FlagY);
        return mv;
    }

    private void setlandTypeDetails(MutationModel model) {
        LandTypeApiDetailRequestDto dto = new LandTypeApiDetailRequestDto();
        dto.setLandType(model.getLandTypePrefix());
        dto.setDistrictId(model.getProvisionalAssesmentMstDto().getAssDistrict());
        dto.setTehsilId(model.getProvisionalAssesmentMstDto().getAssTahasil());
        dto.setVillageId(model.getProvisionalAssesmentMstDto().getTppVillageMauja());
        dto.setMohallaId(model.getProvisionalAssesmentMstDto().getMohalla());
        model.setDistrict(selfAssessmentService.findDistrictByLandType(dto));
        model.setTehsil(selfAssessmentService.getTehsilListByDistrict(dto));
        model.setVillage(selfAssessmentService.getVillageListByTehsil(dto));
        if (model.getLandTypePrefix().equals(MainetConstants.Property.NZL)
                || model.getLandTypePrefix().equals(MainetConstants.Property.DIV)) {
            getModel().setMohalla(selfAssessmentService.getMohallaListByVillageId(dto));
            getModel().setBlockStreet(selfAssessmentService.getStreetListByMohallaId(dto));
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
        tranDto.setProAssNo(prvDto.getAssNo());
        tranDto.setOrgId(UserSession.getCurrent().getOrganisation().getOrgid());
        tranDto.setSmServiceId(model.getServiceId());
        tranDto.setDeptId(model.getDeptId());
        tranDto.setAssesmentMstDto(prvDto);
        tranDto.setLocationId(model.getProvisionalAssesmentMstDto().getLocId());
        LOGGER.info("Before checklist call");
        List<DocumentDetailsVO> docs = mutationService.fetchCheckList(tranDto);
        model.setCheckList(docs);
        LOGGER.info("After checklist call");
        PropertyTransferMasterDto charges = mutationService.fetchChargesForMuatation(tranDto);
        if (charges != null) {
            model.getPropTransferDto().setCharges(charges.getCharges());
            model.getPropTransferDto().setBillTotalAmt(charges.getBillTotalAmt());
            model.setAppliChargeFlag(charges.getAppliChargeFlag());
        }else {
        	model.setAppliChargeFlag(MainetConstants.FlagN);
        }
        model.setSaveButFlag(MainetConstants.Common_Constant.YES);
        return defaultMyResult();
    }

    @RequestMapping(params = "getOwnershipTypeDiv", method = RequestMethod.POST)
    public ModelAndView getOwnershipTypeDiv(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse,
            @RequestParam(value = "ownershipType") String ownershipType) {
        this.getModel().bind(httpServletRequest);
        MutationModel model = this.getModel();
        model.setOwnershipPrefix(ownershipType);
        LookUp lookup = CommonMasterUtility.getValueFromPrefixLookUp(ownershipType, MainetConstants.Property.propPref.OWT);
        model.setOwnershipTypeValue(lookup.getDescLangFirst());
        // model.getProvisionalAssesmentMstDto().setAssOwnerType(lookup.getLookUpId());
        return new ModelAndView("PropTransferOwner", MainetConstants.FORM_NAME, model);
    }

    @RequestMapping(params = "deleteOwnerTable", method = RequestMethod.POST)
    public @ResponseBody void deleteOwnerTable(HttpServletRequest httpServletRequest,
            HttpServletResponse httpServletResponse, @RequestParam(value = "deletedOwnerRowId") Integer deletedOwnerRowId) {
        this.getModel().bind(httpServletRequest);
        MutationModel model = this.getModel();
        model.setOwnerDetailTableCount(deletedOwnerRowId);
        for (PropertyTransferOwnerDto dto : model.getPropTransferDto()
                .getPropTransferOwnerList()) {
            if (model.getOwnerDetailTableCount() == deletedOwnerRowId) {
                model.getPropTransferDto()
                        .getPropTransferOwnerList().remove(dto);
            }
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
        if (model.getLandTypePrefix().equals(MainetConstants.Property.KPK)) {
            ArrayOfKhasraDetails response = selfAssessmentService.getKhasraDetails(dto);
            getModel().setArrayOfKhasraDetails(response);
        } else if (model.getLandTypePrefix().equals(MainetConstants.Property.NZL)) {
            ArrayOfPlotDetails response = selfAssessmentService.getNajoolDetails(dto);
            model.setArrayOfPlotDetails(response);
        } else if (model.getLandTypePrefix().equals(MainetConstants.Property.DIV)) {
            ArrayOfDiversionPlotDetails response = selfAssessmentService.getDiversionDetails(dto);
            model.setArrayOfDiversionPlotDetails(response);
        }
        return new ModelAndView("showPropertyKhasaraDetails", MainetConstants.FORM_NAME, model);
    }
    
    @ResponseBody
	@RequestMapping(params = "getBillingMethod", method = { RequestMethod.POST })
	public List<String> getBillingMethodAndFlatList(@RequestParam("propNo") String propNo, HttpServletRequest request) {
		this.getModel().bind(request);
		MutationModel model = this.getModel();
		List<String> flatNoList = new ArrayList<>();
		String billingMethod = mutationService.getPropertyBillingMethod(propNo, UserSession.getCurrent().getOrganisation().getOrgid());
		
		this.getModel().setBillingMethod(billingMethod);
		if (StringUtils.isNotBlank(billingMethod) && StringUtils.equals(billingMethod, MainetConstants.FLAGI)) {
			flatNoList = new ArrayList<String>();
			flatNoList = mutationService.getPropertyFlatList(propNo,
					String.valueOf(UserSession.getCurrent().getOrganisation().getOrgid()));
		}
		if(flatNoList!= null)
		model.setFlatNoList(flatNoList);
		return flatNoList;
	}
}
