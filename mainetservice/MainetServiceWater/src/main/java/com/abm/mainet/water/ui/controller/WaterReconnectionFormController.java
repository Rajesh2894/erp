package com.abm.mainet.water.ui.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

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
import com.abm.mainet.common.domain.Designation;
import com.abm.mainet.common.domain.ServiceMaster;
import com.abm.mainet.common.dto.JsonViewObject;
import com.abm.mainet.common.master.service.DesignationService;
import com.abm.mainet.common.master.ui.validator.FileUploadServiceValidator;
import com.abm.mainet.common.service.IEmployeeService;
import com.abm.mainet.common.service.ServiceMasterService;
import com.abm.mainet.common.ui.controller.AbstractFormController;
import com.abm.mainet.common.utility.CommonMasterUtility;
import com.abm.mainet.common.utility.LookUp;
import com.abm.mainet.common.utility.UserSession;
import com.abm.mainet.common.utility.Utility;
import com.abm.mainet.water.domain.PlumberMaster;
import com.abm.mainet.water.dto.NoDuesCertificateReqDTO;
import com.abm.mainet.water.dto.NoDuesCertificateRespDTO;
import com.abm.mainet.water.dto.WaterReconnectionRequestDTO;
import com.abm.mainet.water.dto.WaterReconnectionResponseDTO;
import com.abm.mainet.water.service.WaterCommonService;
import com.abm.mainet.water.service.WaterNoDuesCertificateService;
import com.abm.mainet.water.service.WaterReconnectionService;
import com.abm.mainet.water.ui.model.ChangeOfOwnerShipModel;
import com.abm.mainet.water.ui.model.WaterReconnectionFormModel;

/**
 * @author Arun.Chavda
 *
 */

@Controller
@RequestMapping("/WaterReconnectionForm.html")
public class WaterReconnectionFormController extends AbstractFormController<WaterReconnectionFormModel> {

	@Autowired
	WaterCommonService waterCommonService;

	@Autowired
	DesignationService designationService;

	@Autowired
	IEmployeeService employeeService;

	@Autowired
	WaterReconnectionService waterReconnection;

	@Autowired
	private ServiceMasterService serviceMaster;

	private static Logger log = Logger.getLogger(WaterReconnectionFormController.class);

	@Autowired
    private WaterNoDuesCertificateService waterNoDuesCertificateService;
	
	@RequestMapping(method = { RequestMethod.GET, RequestMethod.POST })
	public ModelAndView index(final HttpServletRequest httpServletRequest) {
		sessionCleanup(httpServletRequest);
		bindModel(httpServletRequest);
		try {
			sessionCleanup(httpServletRequest);
			FileUploadServiceValidator.getCurrent().sessionCleanUpForFileUpload();
			final WaterReconnectionFormModel model = getModel();
			final Long orgId = UserSession.getCurrent().getOrganisation().getOrgid();
			model.setPlumberList(waterCommonService.listofplumber(orgId));
			Designation dsg = designationService.findByShortname("PLM");
			List<PlumberMaster> plumberList = new ArrayList<>();
			PlumberMaster master = null;
			if (dsg != null) {

				List<Object[]> empList = employeeService.getAllEmpByDesignation(dsg.getDsgid(), orgId);
				if (!empList.isEmpty()) {
					for (final Object empObj[] : empList) {
						master = new PlumberMaster();
						master.setPlumId(Long.valueOf(empObj[0].toString()));
						master.setPlumFname(empObj[1].toString());
						master.setPlumMname(empObj[2].toString());
						master.setPlumLname(empObj[3].toString());
						plumberList.add(master);
					}

				}
			}
			model.setUlbPlumberList(plumberList);
			final ServiceMaster service = serviceMaster.getServiceByShortName("WRC",
					UserSession.getCurrent().getOrganisation().getOrgid());
			getModel().setServiceId(service.getSmServiceId());
			getModel().setServiceName(service.getSmServiceName());
			getModel().setDeptId(service.getTbDepartment().getDpDeptid());
		} catch (final Exception ex) {
			log.error("Error Occurred while rendering Form:", ex);
			return defaultExceptionView();
		}
		ModelAndView mv = null;
		mv = new ModelAndView("WaterReconnectionForm", MainetConstants.FORM_NAME, getModel());
		return mv;
	}

	@RequestMapping(method = { RequestMethod.POST }, params = "getDisconnectionDetail")
	public ModelAndView getConnectionRecords(final HttpServletRequest httpServletRequest) {
		bindModel(httpServletRequest);

		ModelAndView mv = null;
		mv = new ModelAndView("DisconnectionDetailScrutiny", MainetConstants.FORM_NAME, getModel());
		mv.addObject(MainetConstants.CommonConstants.COMMAND, getModel());

		return mv;

	}

	@Override
	@RequestMapping(params = "saveform", method = RequestMethod.POST)
	public ModelAndView saveform(final HttpServletRequest httpServletRequest) {
		bindModel(httpServletRequest);
		final WaterReconnectionFormModel model = getModel();

		try {
			if (model.saveForm()) {
				return jsonResult(JsonViewObject.successResult(getApplicationSession().getMessage("water.save")));
			}
		} catch (final Exception ex) {
			log.error("Error Occured During Save Data", ex);
			return jsonResult(JsonViewObject.failureResult(ex));
		}
		return defaultMyResult();
	}

	@RequestMapping(method = { RequestMethod.POST }, params = "getReconnectionDetails")
	public @ResponseBody WaterReconnectionResponseDTO getConnectionRecords(
			@RequestParam("connectionNo") final String connectionNo, final HttpServletRequest httpServletRequest) {
		bindModel(httpServletRequest);
		final WaterReconnectionFormModel model = getModel();
		final WaterReconnectionResponseDTO responseDto = new WaterReconnectionResponseDTO();
		try {
			final WaterReconnectionRequestDTO requestDTO = model.getReconnRequestDTO();
			requestDTO.setOrgId(UserSession.getCurrent().getOrganisation().getOrgid());
			requestDTO.setUserId(UserSession.getCurrent().getEmployee().getEmpId());
			requestDTO.setConnectionNo(connectionNo);
			requestDTO.getResponseDTOs().clear();

			List<WaterReconnectionResponseDTO> responseDTO = waterReconnection
					.getDisconnectionDetailsForReConnection(requestDTO);
			/*
			 * if (!responseDTO.isEmpty()) { responseDTO.get(0)
			 * .setAlreadyApplied(waterReconnection.isAlreadyAppliedForReConn(responseDTO.
			 * get(0).getCsIdn())); }
			 */
			requestDTO.setResponseDTOs(responseDTO);
			model.setReconnRequestDTO(requestDTO);
			String connSize = MainetConstants.BLANK;
			String disType = MainetConstants.BLANK;
			String disMethod = MainetConstants.BLANK;
			String tarrifCate = null;
			String preType = null;
			String applicantType = null;
			String meterType = null;
			if (model.getReconnRequestDTO() != null) {
				if (!model.getReconnRequestDTO().getResponseDTOs().isEmpty()) {
					
					//for skdcl permenant disconnected should give error
					if(Utility.isEnvPrefixAvailable(UserSession.getCurrent().getOrganisation(), MainetConstants.ENV_SKDCL)) {
						//Permanent Disconnection
						LookUp lookup = CommonMasterUtility.getNonHierarchicalLookUpObject(model.getReconnRequestDTO().getResponseDTOs().get(0).getDiscType(),
								UserSession.getCurrent().getOrganisation());
						if("P".equals(lookup.getLookUpCode())) {
							final WaterReconnectionResponseDTO response = new WaterReconnectionResponseDTO();
							response.setPermenantDisconnection(true);
							response.setConnectionNo(connectionNo);
							return response;
						}
						
					}
					
					getModel().getReconnRequestDTO()
							.setDiscMethodId(model.getReconnRequestDTO().getResponseDTOs().get(0).getDiscMethodId());
					getModel().getReconnRequestDTO()
							.setDiscType(model.getReconnRequestDTO().getResponseDTOs().get(0).getDiscType());
					getModel().getReconnRequestDTO()
							.setConsumerIdNo(model.getReconnRequestDTO().getResponseDTOs().get(0).getConsumerIdNo());
					getModel().getReconnRequestDTO()
							.setDiscAppDate(model.getReconnRequestDTO().getResponseDTOs().get(0).getDiscAppDate());
					final int langId = UserSession.getCurrent().getLanguageId();

					if (model.getReconnRequestDTO().getResponseDTOs().get(0).getApplicantTypeId() != null) {
						applicantType = CommonMasterUtility
								.getNonHierarchicalLookUpObject(
										model.getReconnRequestDTO().getResponseDTOs().get(0).getApplicantTypeId())
								.getLookUpDesc();
					}
					if (model.getReconnRequestDTO().getResponseDTOs().get(0).getMeterTypeId() != null) {
						meterType = CommonMasterUtility.getNonHierarchicalLookUpObject(
								model.getReconnRequestDTO().getResponseDTOs().get(0).getMeterTypeId(),
								UserSession.getCurrent().getOrganisation()).getLookUpDesc();
					}
					final List<LookUp> categorylist = CommonMasterUtility.getLevelData("TRF", MainetConstants.ENGLISH,
							UserSession.getCurrent().getOrganisation());
					for (final LookUp lookup : categorylist) {
						if (lookup.getLookUpId() == model.getReconnRequestDTO().getResponseDTOs().get(0)
								.getTarrifCategoryId()) {
							tarrifCate = lookup.getLookUpDesc();
						}
					}
					final List<LookUp> premiselist = getSubAlphanumericSortInfo(MainetConstants.BLANK,
							model.getReconnRequestDTO().getResponseDTOs().get(0).getTarrifCategoryId(),
							MainetConstants.NewWaterServiceConstants.YES);
					for (final LookUp lookup : premiselist) {
						if (lookup.getLookUpId() == model.getReconnRequestDTO().getResponseDTOs().get(0)
								.getPremiseTypeId()) {
							preType = lookup.getLookUpDesc();
						}
					}
					if (langId == MainetConstants.DEFAULT_LANGUAGE_ID) {
						connSize = CommonMasterUtility.getCPDDescription(
								model.getReconnRequestDTO().getResponseDTOs().get(0).getConnectionSize().longValue(),
								MainetConstants.FlagE);
						disType = CommonMasterUtility.getCPDDescription(
								model.getReconnRequestDTO().getResponseDTOs().get(0).getDiscType(),
								MainetConstants.FlagE);
						disMethod = CommonMasterUtility.getCPDDescription(
								model.getReconnRequestDTO().getResponseDTOs().get(0).getDiscMethodId(),
								MainetConstants.FlagE);

					} else {
						connSize = CommonMasterUtility.getCPDDescription(
								model.getReconnRequestDTO().getResponseDTOs().get(0).getConnectionSize().longValue(),
								MainetConstants.FlagR);
						disType = CommonMasterUtility.getCPDDescription(
								model.getReconnRequestDTO().getResponseDTOs().get(0).getDiscType(),
								MainetConstants.FlagR);
						disMethod = CommonMasterUtility.getCPDDescription(
								model.getReconnRequestDTO().getResponseDTOs().get(0).getDiscMethodId(),
								MainetConstants.FlagR);
					}
				}
			}
			responseDto.setConnectionSize(model.getReconnRequestDTO().getResponseDTOs().get(0).getConnectionSize());
			responseDto.setTarrifCategory(tarrifCate);
			responseDto.setPremiseType(preType);
			responseDto.setDisconnectionType(disType);
			responseDto.setDiscMethod(disMethod);
			responseDto.setApplicantType(applicantType);
			responseDto.setConnectionNo(connectionNo);
			responseDto.setMeterType(meterType);
			responseDto.setAlreadyApplied(model.getReconnRequestDTO().getResponseDTOs().get(0).isAlreadyApplied());
			responseDto.setConsumerName(model.getReconnRequestDTO().getResponseDTOs().get(0).getConsumerName());
			model.setConnectionSize(model.getReconnRequestDTO().getResponseDTOs().get(0).getConnectionSize());
			model.getReconnRequestDTO().setTarrifCategory(tarrifCate);
			model.getReconnRequestDTO().setPremiseType(preType);
			model.getReconnRequestDTO().setDisconnectionType(disType);
			model.getReconnRequestDTO().setDiscMethod(disMethod);
			model.getReconnResponseDTO().setApplicantType(applicantType);
			model.getReconnResponseDTO().setMeterType(meterType);
			model.getReconnResponseDTO()
					.setConnectionSize(model.getReconnRequestDTO().getResponseDTOs().get(0).getConnectionSize());
			model.getReconnResponseDTO().setTarrifCategory(tarrifCate);
			model.getReconnResponseDTO().setPremiseType(preType);
			model.getReconnResponseDTO().setDisconnectionType(disType);
			model.getReconnResponseDTO().setDiscMethod(disMethod);
			responseDto.setDiscDate(
					Utility.dateToString(model.getReconnRequestDTO().getResponseDTOs().get(0).getDiscAppDate()));

		}

		catch (final Exception e) {
			log.error("Exception while fetching the old connection Record for Water Reconnection", e);
		}

		return responseDto;

	}

	@RequestMapping(method = { RequestMethod.POST }, params = "getCheckListAndCharges")
	public ModelAndView doGetApplicableCheckListAndCharges(final HttpServletRequest httpServletRequest) {
		bindModel(httpServletRequest);
		final long orgId = UserSession.getCurrent().getOrganisation().getOrgid();
		ModelAndView modelAndView = null;
		try {
			getModel().findApplicableCheckListAndCharges("WRC", orgId);
			modelAndView = new ModelAndView("WaterReconnectionFormValidn", MainetConstants.FORM_NAME, getModel());
			if (getModel().getBindingResult() != null) {
				modelAndView.addObject(BindingResult.MODEL_KEY_PREFIX + MainetConstants.FORM_NAME,
						getModel().getBindingResult());
			}
		} catch (final Exception ex) {
			log.error("Error Occurred while getCheckListAndCharges:", ex);
			modelAndView = defaultExceptionFormView();
		}
		return modelAndView;
	}

	@RequestMapping(params = "save", method = RequestMethod.POST)
	public ModelAndView saveWaterReconnectionform(final HttpServletRequest httpServletRequest) {
		bindModel(httpServletRequest);
		final WaterReconnectionFormModel model = getModel();

		try {
			if (model.saveWaterReconnectionform()) {
				
				if(Utility.isEnvPrefixAvailable(UserSession.getCurrent().getOrganisation(), MainetConstants.ENV_SKDCL)) {
					return new ModelAndView("NewWaterConnectionApplicationFormPrint", MainetConstants.FORM_NAME, getModel());
				}
				
				return jsonResult(JsonViewObject.successResult(getApplicationSession().getMessage(
						"Your Application for Water Re-Connection has been saved successfully.Your Application No. is "
								+ " " + model.getReconnRequestDTO().getApplicationId())));
			}
		} catch (final Exception ex) {
			log.error("Error Occured During Save Data", ex);
			return jsonResult(JsonViewObject.failureResult(ex));
		}
		return defaultMyResult();
	}


	  @RequestMapping(params = "getDuesForConnNoSKDCL", method = RequestMethod.POST) 
	    public @ResponseBody NoDuesCertificateReqDTO getDuesForConnNoSKDCL(final HttpServletRequest request) {
		  log.info("Start the getConnectionDetail()");
	        getModel().bind(request);
	        NoDuesCertificateReqDTO noDuesCertificateReqDTO = new NoDuesCertificateReqDTO();
	        final WaterReconnectionFormModel model = getModel();
	        Long orgId = UserSession.getCurrent().getOrganisation().getOrgid();
	        model.setOrgId(orgId);
	        final String connectionNo = model.getReconnRequestDTO().getConnectionNo();
	        try {
	            if ((connectionNo != null) && !connectionNo.isEmpty() && (UserSession.getCurrent() != null)) {
	            	final NoDuesCertificateReqDTO requestDTO = new NoDuesCertificateReqDTO();
	            	requestDTO.setConsumerNo(connectionNo);
		            requestDTO.setOrgId(orgId);
		            requestDTO.setServiceId(model.getServiceId());
		            requestDTO.setUserId(UserSession.getCurrent().getEmployee().getEmpId());
		            requestDTO.setEmpId(UserSession.getCurrent().getEmployee().getEmpId());
		           
		            NoDuesCertificateRespDTO resDTO = null;
					resDTO = waterNoDuesCertificateService.getWaterDuesByPropNoNConnNo(requestDTO);
					
					if (resDTO != null) {
		                noDuesCertificateReqDTO.setConsumerName(resDTO.getConsumerName());
		                if (resDTO.getConsumerAddress() != null) {
		                    noDuesCertificateReqDTO.setConsumerAddress(resDTO.getConsumerAddress());
		                } else {
		                    noDuesCertificateReqDTO.setConsumerAddress(resDTO.getCsAdd());
		                }
		                for (final Map.Entry<String, Double> map : resDTO.getDuesList().entrySet()) {
		                	if(MainetConstants.NoDuesCertificate.PROPERTYDUES.equals(map.getKey())) {
		                		noDuesCertificateReqDTO.setPropDueAmt(map.getValue());
		                	}
		                    noDuesCertificateReqDTO.setWaterDues(map.getKey());
		                    noDuesCertificateReqDTO.setDuesAmount(map.getValue());
		                }
		                noDuesCertificateReqDTO.setDues(resDTO.isDues());
		                noDuesCertificateReqDTO.setConsumerNo(connectionNo);
		                noDuesCertificateReqDTO.setOrgId(model.getOrgId());
		                noDuesCertificateReqDTO.setServiceId(model.getServiceId());
		                noDuesCertificateReqDTO.setUserId(UserSession.getCurrent().getEmployee().getEmpId());
		                noDuesCertificateReqDTO.setEmpId(UserSession.getCurrent().getEmployee().getEmpId());
		                noDuesCertificateReqDTO.setMobileNo(resDTO.getCsContactno());
		                noDuesCertificateReqDTO.setEmail(resDTO.getEmail());
		                noDuesCertificateReqDTO.setCcnDuesList(resDTO.getCcnDuesList());
		                if (resDTO.isBillGenerated()) {
		                    noDuesCertificateReqDTO.setBillGenerated(true);
		                } else {
		                    noDuesCertificateReqDTO.setBillGenerated(false);
		                }
					}
	            }
	        } catch (final Exception exception) {
	        	log.error("Error occured during call to no dues certificate " + exception.getMessage());           
	        }
	        return  noDuesCertificateReqDTO;
	    }
	
}
