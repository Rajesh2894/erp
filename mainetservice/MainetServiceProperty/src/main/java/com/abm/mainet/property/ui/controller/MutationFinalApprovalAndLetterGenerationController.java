
package com.abm.mainet.property.ui.controller;

import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.abm.mainet.common.constant.MainetConstants;
import com.abm.mainet.common.constant.PrefixConstants;
import com.abm.mainet.common.domain.CFCApplicationAddressEntity;
import com.abm.mainet.common.domain.Organisation;
import com.abm.mainet.common.domain.ServiceMaster;
import com.abm.mainet.common.domain.TbCfcApplicationMstEntity;
import com.abm.mainet.common.dto.ApplicantDetailDTO;
import com.abm.mainet.common.dto.JsonViewObject;
import com.abm.mainet.common.service.ICFCApplicationMasterService;
import com.abm.mainet.common.service.ILocationMasService;
import com.abm.mainet.common.ui.controller.AbstractFormController;
import com.abm.mainet.common.utility.ApplicationSession;
import com.abm.mainet.common.utility.CommonMasterUtility;
import com.abm.mainet.common.utility.LookUp;
import com.abm.mainet.common.utility.UserSession;
import com.abm.mainet.common.utility.Utility;
import com.abm.mainet.common.workflow.dto.WorkflowTaskAction;
import com.abm.mainet.common.workflow.ui.validator.CheckerActionValidator;
import com.abm.mainet.property.dto.PropertyTransferMasterDto;
import com.abm.mainet.property.dto.PropertyTransferOwnerDto;
import com.abm.mainet.property.dto.ProvisionalAssesmentMstDto;
import com.abm.mainet.property.dto.ProvisionalAssesmentOwnerDtlDto;
import com.abm.mainet.property.repository.PropertyTransferRepository;
import com.abm.mainet.property.service.AssesmentMastService;
import com.abm.mainet.property.service.MutationService;
import com.abm.mainet.property.service.PropertyTransferService;
import com.abm.mainet.property.ui.model.MutationModel;
import com.abm.mainet.smsemail.dto.SMSAndEmailDTO;

/**
 * @author cherupelli.srikanth
 * @since 18/05/2022
 */

@Controller
@RequestMapping("/MutationFinalApprovalAndLetterGeneration.html")
public class MutationFinalApprovalAndLetterGenerationController extends AbstractFormController<MutationModel>{
	
	@Autowired
    private PropertyTransferService propertyTransferService;
	
	@Autowired
    private ICFCApplicationMasterService applicationMasterService;
	
	@Autowired
    private MutationService mutationService;
	
	@Autowired
    private AssesmentMastService assesmentMastService;
	
	@Autowired
    private ILocationMasService iLocationMasService;
	
	  @RequestMapping(method = { RequestMethod.POST, RequestMethod.GET })
	    public ModelAndView index(HttpServletRequest request) {
	        this.sessionCleanup(request);
	        this.getModel().setSaveButFlag(MainetConstants.FlagN);
	        return index();
	    }
	  
	  @RequestMapping(params = "getMutationDetailForApproval", method = RequestMethod.POST)
	    public ModelAndView getMutationDetail(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse,
			@RequestParam(value = "applicationNo") Long applicationNo) {
		this.getModel().bind(httpServletRequest);
		MutationModel model = this.getModel();
		ModelAndView mv = null;
		PropertyTransferMasterDto dto = propertyTransferService
				.getPropTransferMstByAppId(UserSession.getCurrent().getOrganisation().getOrgid(), applicationNo);
		if(dto != null) {
			
			 try {
		            if (dto.getApmApplicationId() != null) {
		                String status = mutationService.getWorkflowRequestByAppId(dto.getApmApplicationId(),
		                        UserSession.getCurrent().getOrganisation().getOrgid());
		                if (MainetConstants.TASK_STATUS_PENDING.equalsIgnoreCase(status)) {
		                    getModel().addValidationError(
		                            ApplicationSession.getInstance().getMessage("property.validation.mutaion.process"));
		                    mv = new ModelAndView("MutationFormDisplay", MainetConstants.FORM_NAME,
		                            this.getModel());
		                    mv.addObject(BindingResult.MODEL_KEY_PREFIX + MainetConstants.FORM_NAME,
		                            getModel().getBindingResult());
		                    return mv;
		                }
		            }
		        } catch (Exception e) {
		            //LOGGER.error("Exception occured" + e);
		        }
			 
			 if(dto.getAutBy() != null && dto.getAutBy() > 0) {
				 getModel().addValidationError(
                         ApplicationSession.getInstance().getMessage("Procees have been completed for this application"));
                 mv = new ModelAndView("MutationFormDisplay", MainetConstants.FORM_NAME,
                         this.getModel());
                 mv.addObject(BindingResult.MODEL_KEY_PREFIX + MainetConstants.FORM_NAME,
                         getModel().getBindingResult());
                 return mv;
			 }
			 
			 Date afterAddingDaysToCreatedDate = null;
	            try {
	                String noOfDays = CommonMasterUtility.getDefaultValueByOrg(MainetConstants.Property.PNC,
	                        UserSession.getCurrent().getOrganisation()).getOtherField();
	                LocalDate convertCreateDateToLocalDate = dto.getCreatedDate().toInstant()
	                        .atZone(ZoneId.systemDefault()).toLocalDate();
	                afterAddingDaysToCreatedDate = Date.from(
	                        convertCreateDateToLocalDate.plusDays(Long.valueOf(noOfDays))
	                                .atStartOfDay(ZoneId.systemDefault()).toInstant());
	            } catch (Exception e) {
	                // TODO: handle exception
	            }
	            if (afterAddingDaysToCreatedDate != null && Utility.compareDate(new Date(), afterAddingDaysToCreatedDate)) {
	            	 getModel().addValidationError(
	                         ApplicationSession.getInstance().getMessage("Application is under public notice period"));
	                 mv = new ModelAndView("MutationFormDisplay", MainetConstants.FORM_NAME,
	                         this.getModel());
	                 mv.addObject(BindingResult.MODEL_KEY_PREFIX + MainetConstants.FORM_NAME,
	                         getModel().getBindingResult());
	                 return mv;
	            }
	            
	            
			 ProvisionalAssesmentMstDto assMst = null;
					assMst = assesmentMastService.fetchAssessmentMasterByPropNo(UserSession.getCurrent().getOrganisation().getOrgid(), dto.getProAssNo());
		        setDescriptionOfOwners(model, dto, assMst);
		        model.setDropDownValues(assMst, UserSession.getCurrent().getOrganisation());
		        model.setPropTransferDto(dto);
		        model.setMutationLevelFlag("A");
		        assMst.setLocationName(iLocationMasService.getLocationNameById(assMst.getLocId(), UserSession.getCurrent().getOrganisation().getOrgid()));
		        model.setProvisionalAssesmentMstDto(assMst);
			TbCfcApplicationMstEntity masterEntity = applicationMasterService
					.getCFCApplicationByRefNoOrAppNo(null, applicationNo, UserSession.getCurrent().getOrganisation().getOrgid());
			if(masterEntity != null) {
	    		CFCApplicationAddressEntity addressEntity = applicationMasterService
						.getApplicantsDetails(masterEntity.getApmApplicationId());
				ApplicantDetailDTO applicantDto = new ApplicantDetailDTO();
				BeanUtils.copyProperties(addressEntity, applicantDto);
				BeanUtils.copyProperties(masterEntity, applicantDto);
				if (masterEntity.getApmTitle() != null)
					applicantDto.setApplicantTitle(masterEntity.getApmTitle());
				applicantDto.setApplicantFirstName(masterEntity.getApmFname());
				applicantDto.setApplicantMiddleName(masterEntity.getApmMname());
				applicantDto.setApplicantLastName(masterEntity.getApmLname());
				if(addressEntity != null) {
					if(addressEntity.getApaPincode() != null) {
						applicantDto.setPinCode(String.valueOf(addressEntity.getApaPincode()));
					}
					applicantDto.setEmailId(addressEntity.getApaEmail());
					applicantDto.setMobileNo(addressEntity.getApaMobilno());
					applicantDto.setDwzid1(addressEntity.getApaZoneNo());
					applicantDto.setDwzid2(addressEntity.getApaWardNo());
					if(addressEntity.getApaBlockno() != null) {
						applicantDto.setDwzid3(Long.valueOf(addressEntity.getApaBlockno()));
					}
					applicantDto.setRoadName(addressEntity.getApaRoadnm());
					applicantDto.setAreaName(addressEntity.getApaCityName());
					applicantDto.setBuildingName(addressEntity.getApaBldgnm());
					applicantDto.setBlockName(addressEntity.getApaBlockName());
				}
				getModel().setApplicantDetailDto(applicantDto);
	    	}
			 model.setPropTransferDto(dto);
			this.getModel().setSaveButFlag(MainetConstants.FlagY);
			return new ModelAndView("MutationFormDisplay", MainetConstants.FORM_NAME,
					getModel());
		}else {
			 model.addValidationError("No record found");
             mv = new ModelAndView("MutationFormDisplay", MainetConstants.FORM_NAME, this.getModel());
             mv.addObject(BindingResult.MODEL_KEY_PREFIX + MainetConstants.FORM_NAME, getModel().getBindingResult());
             return mv;
		}
		
	}
	  
	  private void setDescriptionOfOwners(MutationModel model, PropertyTransferMasterDto dto, ProvisionalAssesmentMstDto assMst) {
	        LookUp ownerType = CommonMasterUtility.getNonHierarchicalLookUpObject(
	                assMst.getAssOwnerType(), UserSession.getCurrent().getOrganisation());
	        model.setOwnershipPrefix(ownerType.getLookUpCode());
	        LookUp lookup = CommonMasterUtility.getValueFromPrefixLookUp(ownerType.getLookUpCode(),
	                MainetConstants.Property.propPref.OWT,
	                UserSession.getCurrent().getOrganisation());
	        model.setOwnershipTypeValue(lookup.getDescLangFirst());
	        assMst.setProAssOwnerTypeName(ownerType.getDescLangFirst());
	        LookUp ownerTypeNew = CommonMasterUtility.getNonHierarchicalLookUpObject(
	                dto.getOwnerType(), UserSession.getCurrent().getOrganisation());

	        if (MainetConstants.Property.SO.equals(ownerType.getLookUpCode())
	                || MainetConstants.Property.JO.equals(ownerType.getLookUpCode())) {
	            for (ProvisionalAssesmentOwnerDtlDto owner : assMst.getProvisionalAssesmentOwnerDtlDtoList()) {
	                if (owner.getGenderId() != null) {
	                    owner.setProAssGenderId(
	                            CommonMasterUtility
	                                    .getNonHierarchicalLookUpObject(owner.getGenderId(),
	                                            UserSession.getCurrent().getOrganisation())
	                                    .getDescLangFirst());
	                } else {
	                    owner.setProAssGenderId(MainetConstants.CommonConstants.NA);
	                }
	                if (owner.getRelationId() != null) {
	                    owner.setProAssRelationId(
	                            CommonMasterUtility.getNonHierarchicalLookUpObject(owner.getRelationId(),
	                                    UserSession.getCurrent().getOrganisation()).getDescLangFirst());
	                } else {
	                    owner.setProAssRelationId(MainetConstants.CommonConstants.NA);
	                }
	            }
	        }
	        if (MainetConstants.Property.SO.equals(ownerTypeNew.getLookUpCode())
	                || MainetConstants.Property.JO.equals(ownerTypeNew.getLookUpCode())) {
	            for (PropertyTransferOwnerDto ownerNew : dto.getPropTransferOwnerList()) {
	                if (ownerNew.getGenderId() != null) {
	                    ownerNew.setGenderIdDesc(
	                            CommonMasterUtility
	                                    .getNonHierarchicalLookUpObject(ownerNew.getGenderId(),
	                                            UserSession.getCurrent().getOrganisation())
	                                    .getDescLangFirst());
	                } else {
	                    ownerNew.setGenderIdDesc(MainetConstants.CommonConstants.NA);
	                }
	                if (ownerNew.getRelationId() != null) {
	                    ownerNew.setRelationIdDesc(
	                            CommonMasterUtility.getNonHierarchicalLookUpObject(ownerNew.getRelationId(),
	                                    UserSession.getCurrent().getOrganisation()).getDescLangFirst());
	                } else {
	                    ownerNew.setRelationIdDesc(MainetConstants.CommonConstants.NA);
	                }
	            }
	        }

	        model.setOwnershipPrefixNew(ownerTypeNew.getLookUpCode());
	        dto.setProAssOwnerTypeName(ownerTypeNew.getDescLangFirst());

	        
	        if(Utility.isEnvPrefixAvailable(UserSession.getCurrent().getOrganisation(), MainetConstants.ENV_ASCL)) {
	        	 Organisation organisation = UserSession.getCurrent().getOrganisation();
	             
	             StringBuilder ownerFullName = new StringBuilder();
	             int ownerSize = 1;
	             List<ProvisionalAssesmentOwnerDtlDto> ownerList = assMst.getProvisionalAssesmentOwnerDtlDtoList();
	             for (ProvisionalAssesmentOwnerDtlDto owner : ownerList) {
	                 if (owner.getAssoOType() != null && owner.getAssoOType().equals(MainetConstants.Property.PRIMARY_OWN)) {

	                     if (StringUtils.isEmpty(ownerFullName.toString())) {
	                         ownerFullName.append(owner.getAssoOwnerName());
	                         ownerFullName.append(MainetConstants.WHITE_SPACE);
	                         if (owner.getRelationId() != null && owner.getRelationId() > 0) {
	                             LookUp reltaionLookUp = CommonMasterUtility.getNonHierarchicalLookUpObject(owner.getRelationId(),
	                                     organisation);
	                             ownerFullName.append(reltaionLookUp.getDescLangFirst());
	                         } else {
	                             ownerFullName.append("Contact person - ");
	                         }
	                         if (StringUtils.isNotBlank(owner.getAssoGuardianName())) {
	                             ownerFullName.append(MainetConstants.WHITE_SPACE);
	                             ownerFullName.append(owner.getAssoGuardianName());
	                         }
	                     } else {
	                         ownerFullName.append(owner.getAssoOwnerName());
	                         ownerFullName.append(MainetConstants.WHITE_SPACE);
	                         if (owner.getRelationId() != null && owner.getRelationId() > 0) {
	                             LookUp reltaionLookUp = CommonMasterUtility.getNonHierarchicalLookUpObject(owner.getRelationId(),
	                                     organisation);
	                             ownerFullName.append(reltaionLookUp.getDescLangFirst());
	                         } else {
	                             ownerFullName.append("Contact person - ");
	                         }
	                         ownerFullName.append(MainetConstants.WHITE_SPACE);
	                         ownerFullName.append(owner.getAssoGuardianName());
	                     }
	                     if (ownerSize < ownerList.size()) {
	                         ownerFullName.append("," + " ");
	                     }
	                     ownerSize = ownerSize + 1;
	                 } else {
	                     ownerFullName.append(owner.getAssoOwnerName());
	                     ownerFullName.append(MainetConstants.WHITE_SPACE);
	                     if (owner.getRelationId() != null && owner.getRelationId() > 0) {
	                         LookUp reltaionLookUp = CommonMasterUtility.getNonHierarchicalLookUpObject(owner.getRelationId(),
	                                 organisation);
	                         ownerFullName.append(reltaionLookUp.getDescLangFirst());
	                     } else {
	                         ownerFullName.append("Contact person - ");
	                     }
	                     ownerFullName.append(MainetConstants.WHITE_SPACE);
	                     ownerFullName.append(owner.getAssoGuardianName());
	                     if (ownerSize < ownerList.size()) {
	                         ownerFullName.append("," + " ");
	                     }
	                 }
	             }
	             
	             assMst.setOwnerFullName(ownerFullName.toString());
	        }
	       
	    }

	  
	  
	  @RequestMapping(params = "saveFinalApprovalMutation", method = RequestMethod.POST)
	public ModelAndView saveMutationWithoutEdit(HttpServletRequest httpServletRequest,
			HttpServletResponse httpServletResponse) {
		getModel().bind(httpServletRequest);
		MutationModel model = getModel();
		final WorkflowTaskAction workFlowActionDto = model.getWorkflowActionDto();
		if (StringUtils.equals(workFlowActionDto.getDecision(), MainetConstants.WorkFlow.Decision.REJECTED)) {
			mutationService.updateMutationApproval(UserSession.getCurrent().getOrganisation().getOrgid(),UserSession.getCurrent().getEmployee().getEmpId(),model.getPropTransferDto().getApmApplicationId());
			return jsonResult(
					JsonViewObject.successResult(ApplicationSession.getInstance().getMessage("property.reject")));
		} else {
			mutationService.saveFinalAuthorization(UserSession.getCurrent().getOrganisation().getOrgid(),
					UserSession.getCurrent().getEmployee(), model.getPropTransferDto(), model.getClientIpAddress(),
					model.getPropTransferDto().getApmApplicationId());
			model.setApmApplicationId(model.getPropTransferDto().getApmApplicationId());
			return jsonResult(JsonViewObject
					.successResult(ApplicationSession.getInstance().getMessage("property.mutation.authSave")));

		}
	}
	  

}
