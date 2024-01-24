package com.abm.mainet.workManagement.roadcutting.ui.controller;

import java.io.File;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

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
import com.abm.mainet.common.constant.MainetConstants;
import com.abm.mainet.common.dto.ChargeDetailDTO;
import com.abm.mainet.common.dto.CheckListModel;
import com.abm.mainet.common.dto.DocumentDetailsVO;
import com.abm.mainet.common.exception.FrameworkException;
import com.abm.mainet.common.service.ICommonBRMSService;
import com.abm.mainet.common.ui.controller.AbstractFormController;
import com.abm.mainet.common.ui.view.JsonViewObject;
import com.abm.mainet.common.util.CommonMasterUtility;
import com.abm.mainet.common.util.UserSession;
import com.abm.mainet.dms.service.FileUploadServiceValidator;
import com.abm.mainet.dms.utility.FileUploadUtility;
import com.abm.mainet.integration.ws.JersyCall;
import com.abm.mainet.integration.ws.dto.WSRequestDTO;
import com.abm.mainet.integration.ws.dto.WSResponseDTO;
import com.abm.mainet.workManagement.roadcutting.datamodel.RoadCuttingRateMaster;
import com.abm.mainet.workManagement.roadcutting.service.IRoadCuttingService;
import com.abm.mainet.workManagement.roadcutting.ui.model.RoadCuttingModel;

/**
 * @author satish.rathore
 *
 */
@Controller
@RequestMapping(MainetConstants.RoadCuttingConstant.RAOD_CUTTING_URL)
public class RoadCuttingController extends AbstractFormController<RoadCuttingModel> {

	@Autowired
	private ICommonBRMSService brmsCommonService;
	@Autowired
	private IRoadCuttingService roadCuttingService;
	


	private static final Logger LOGGER = Logger.getLogger(RoadCuttingController.class);

	@SuppressWarnings("unchecked")
	@RequestMapping(method = { RequestMethod.GET, RequestMethod.POST })
	public ModelAndView index(final Model model, final HttpServletRequest httpServletRequest) {
		this.sessionCleanup(httpServletRequest);
		FileUploadServiceValidator.getCurrent().sessionCleanUpForFileUpload();
		final RoadCuttingModel roadModel = this.getModel();
		final Long orgIds = UserSession.getCurrent().getOrganisation().getOrgid();
		roadModel.setCommonHelpDocs("RoadCutting.html");
		roadModel.setListTEC(CommonMasterUtility.getLookUps("TEC", UserSession.getCurrent().getOrganisation()));
		roadModel.setListROT(CommonMasterUtility.getLookUps("ROT", UserSession.getCurrent().getOrganisation()));
		
		//Setting Applicant Default Info
		roadModel.getRoadCuttingDto().setPersonMobileNo2(new BigInteger( UserSession.getCurrent().getEmployee().getEmpmobno()));
		roadModel.getRoadCuttingDto().setPersonAddress2(UserSession.getCurrent().getEmployee().getEmpAddress1());
		roadModel.getRoadCuttingDto().setPersonEmailId2(UserSession.getCurrent().getEmployee().getEmpemail());
		
		try {
			final WSRequestDTO initRequestDto = new WSRequestDTO();
			initRequestDto.setModelName(MainetConstants.RoadCuttingConstant.CHECKLIST_ROADCUTTING_RATE_MASTER);
			WSResponseDTO response = brmsCommonService.initializeModel(initRequestDto);
			List<DocumentDetailsVO> checkListList = new ArrayList<>();
			if (response.getWsStatus() != null
					&& MainetConstants.WebServiceStatus.SUCCESS.equalsIgnoreCase(response.getWsStatus())) {
				List<Object> checklist = JersyCall.castResponse(response, CheckListModel.class, 0);
				CheckListModel checkListModel = (CheckListModel) checklist.get(0);
	            /* this is for charges and payment*/
				 final List<Object> roadCuttingRateMasterList = JersyCall.castResponse(response, RoadCuttingRateMaster.class, 1);
	             final RoadCuttingRateMaster roadCuttingRateMaster = (RoadCuttingRateMaster) roadCuttingRateMasterList.get(0);
				checkListModel.setOrgId(orgIds);
				checkListModel.setServiceCode(MainetConstants.RoadCuttingConstant.RCP);
				final WSRequestDTO checkRequestDto = new WSRequestDTO();
				checkRequestDto.setDataModel(checkListModel);
				 checkListList = brmsCommonService.getChecklist(checkListModel);				
					  if (checkListList != null && !checkListList.isEmpty()) {
		                    Long fileSerialNo = 1L;
		                    for (final DocumentDetailsVO docSr : checkListList) {
		                        docSr.setDocumentSerialNo(fileSerialNo);
		                        fileSerialNo++;
		                    }
		                    roadModel.setCheckList(checkListList);
		                 
		                }	
		           /*	payment and charges*/
					
					roadCuttingRateMaster.setOrgId(UserSession.getCurrent().getOrganisation().getOrgid());
					roadCuttingRateMaster.setServiceCode(MainetConstants.RoadCuttingConstant.RCP);
					roadCuttingRateMaster.setChargeApplicableAt(
							Long.toString(CommonMasterUtility.getValueFromPrefixLookUp(MainetConstants.RoadCuttingConstant.APL,
									MainetConstants.NewWaterServiceConstants.CAA).getLookUpId()));
					roadCuttingRateMaster.setOrganisationType(
							CommonMasterUtility.getCPDDescription(UserSession.getCurrent().getOrganisation().getOrgCpdId(),
									"E"));
					final WSRequestDTO taxRequestDto = new WSRequestDTO();
					taxRequestDto.setDataModel(roadCuttingRateMaster);
					 WSResponseDTO res = roadCuttingService.getApplicableTaxes(taxRequestDto);
					  if (MainetConstants.WebServiceStatus.SUCCESS.equalsIgnoreCase(res.getWsStatus())) {
	                        if (!res.isFree()) {
	                        //    final List<?> rates =  (List<?>) res.getResponseObj();
	                            final List<?> rates =  JersyCall.castResponse(res, RoadCuttingRateMaster.class);
	                            final List<RoadCuttingRateMaster> requiredCHarges = new ArrayList<>();
	                            for (final Object rate : rates) {
	                            	RoadCuttingRateMaster master1 = (RoadCuttingRateMaster) rate;
	                                master1 = populateChargeModel(roadModel, master1);
	                                requiredCHarges.add(master1);
	                            }
	                            WSRequestDTO chargeReqDto = new WSRequestDTO();
	                            chargeReqDto.setDataModel(requiredCHarges);
	                            WSResponseDTO applicableCharges = roadCuttingService.getApplicableCharges(chargeReqDto);
	                            final List<?> detailDTOsList = JersyCall.castResponse(applicableCharges, ChargeDetailDTO.class);
	                           List<ChargeDetailDTO> detailDTOs = (List<ChargeDetailDTO>)detailDTOsList;
	                            roadModel.setFree(MainetConstants.RoadCuttingConstant.NO);
	                            roadModel.getRoadCuttingDto().setFree(false);
	                            roadModel.setChargesInfo(detailDTOs);
	                            roadModel.setCharges((chargesToPay(detailDTOs)));
	                            setChargeMap(roadModel, detailDTOs);
	                            roadModel.getOfflineDTO().setAmountToShow(roadModel.getCharges());
	                            roadModel.setAmountToPaid(new BigDecimal(roadModel.getCharges()).setScale(2));    
	                        }
	                    }
				}
				else {
					return new ModelAndView(MainetConstants.DEFAULT_EXCEPTION_VIEW);
				}
		}	catch (FrameworkException e) {
					LOGGER.info(e.getErrMsg());
				 roadModel.setCheckList(null);
				return new ModelAndView(MainetConstants.DEFAULT_EXCEPTION_VIEW);
				}
		return new ModelAndView("RoadCutting", MainetConstants.FORM_NAME, roadModel);
	}

	/* For populating RoadCuttingRateMaster Model for BRMS call */
	private RoadCuttingRateMaster populateChargeModel(RoadCuttingModel model, RoadCuttingRateMaster roadCuttingRateMaster) {
		roadCuttingRateMaster.setOrgId(UserSession.getCurrent().getOrganisation().getOrgid());
		roadCuttingRateMaster.setServiceCode(MainetConstants.RoadCuttingConstant.RCP);
		roadCuttingRateMaster.setDeptCode(MainetConstants.RoadCuttingConstant.WMS);
		roadCuttingRateMaster.setStartDate(new Date().getTime());
		return roadCuttingRateMaster;
	}
	/* end */
	
	 private void setChargeMap(final RoadCuttingModel model,
	            final List<ChargeDetailDTO> charges) {
	        final Map<Long, Double> chargesMap = new HashMap<>();
	        for (final ChargeDetailDTO dto : charges) {
	            chargesMap.put(dto.getChargeCode(), dto.getChargeAmount());
	        }
	        model.setChargesMap(chargesMap);
	    }
	 
		private double chargesToPay(List<ChargeDetailDTO> chargeDetailDTO) {
			double amountSum = 0.0;
			for (final ChargeDetailDTO charge : chargeDetailDTO) {
				amountSum = amountSum + charge.getChargeAmount();
			}
			return amountSum;
		}
		
		
		
		/* method to validate and Save Road Cutting Application */
		@RequestMapping(params = "saveRoadCutting", method = RequestMethod.POST)
		public @ResponseBody ModelAndView saveRoadCutting(HttpServletRequest httpServletRequest,
				HttpServletResponse httpServletResponse) {
			getModel().bind(httpServletRequest);
			RoadCuttingModel model = this.getModel();
			
			ModelAndView mv = null;
		//	fileUpload.validateUpload(model.getBindingResult());
			if (model.validateInputs())
			{
				if (model.saveRoadCuttingForm())
				{
					return jsonResult(JsonViewObject.successResult(model.getSuccessMessage()));

				} else
					return new ModelAndView(MainetConstants.DEFAULT_EXCEPTION_FORM_VIEW);
			}

			mv = new ModelAndView("RoadCuttingValidn", MainetConstants.FORM_NAME, this.getModel());
			mv.addObject(BindingResult.MODEL_KEY_PREFIX + MainetConstants.FORM_NAME, getModel().getBindingResult());
			return mv;

		}
		/* end */
		
		
	
		
		
	
		
		@RequestMapping(params = "uploadEndPoints", method = RequestMethod.POST)
		public ModelAndView uploadEndPoints(HttpServletRequest httpServletRequest,
				HttpServletResponse httpServletResponse,@RequestParam Long index,@RequestParam String mode) {
			getModel().bind(httpServletRequest);
			ModelAndView mv =  new ModelAndView("RoadCuttingUpload", MainetConstants.FORM_NAME, this.getModel());
			mv.addObject(BindingResult.MODEL_KEY_PREFIX + MainetConstants.FORM_NAME, getModel().getBindingResult());
			mv.addObject("index", index);
			mv.addObject("mode", mode);
			return mv;

		}
		
		@RequestMapping(params = "deleteEndPoints", method = RequestMethod.POST)
		public ModelAndView deleteEndPoints(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse,
				@RequestParam Long index) {
	
			if ((FileUploadUtility.getCurrent().getFileMap().entrySet() != null)
					&& !FileUploadUtility.getCurrent().getFileMap().entrySet().isEmpty()) {
	
				long lKey = 100 + index;
				long rKey = 200 + index;
				if (FileUploadUtility.getCurrent().getFileMap().containsKey(lKey)) {
					FileUploadUtility.getCurrent().getFileMap().remove(lKey);
				}
	
				if (FileUploadUtility.getCurrent().getFileMap().containsKey(rKey)) {
					FileUploadUtility.getCurrent().getFileMap().remove(rKey);
				}
				getModel().getRoadCuttingDto().getRoadList().clear();
				getModel().bind(httpServletRequest);
			}
	
			ModelAndView mv = new ModelAndView("RoadCuttingValidn", MainetConstants.FORM_NAME, this.getModel());
			mv.addObject(BindingResult.MODEL_KEY_PREFIX + MainetConstants.FORM_NAME, getModel().getBindingResult());
			mv.addObject("index", index);
			return mv;
	
		}
		
		
		@RequestMapping(params = "bindData", method = RequestMethod.POST)
		public ModelAndView bindData(HttpServletRequest httpServletRequest,
				HttpServletResponse httpServletResponse) {
			getModel().bind(httpServletRequest);
			ModelAndView mv =  new ModelAndView("RoadCuttingValidn", MainetConstants.FORM_NAME, this.getModel());
			mv.addObject(BindingResult.MODEL_KEY_PREFIX + MainetConstants.FORM_NAME, getModel().getBindingResult());
			return mv;

		}
		
		
		@RequestMapping(params = "back", method = RequestMethod.POST)
		public ModelAndView back(HttpServletRequest httpServletRequest,
				HttpServletResponse httpServletResponse) {
			getModel().bind(httpServletRequest);
			ModelAndView mv = new ModelAndView("RoadCuttingValidn", MainetConstants.FORM_NAME, this.getModel());
			mv.addObject(BindingResult.MODEL_KEY_PREFIX + MainetConstants.FORM_NAME, getModel().getBindingResult());
			return mv;

		}
		
		@RequestMapping(params = "scrBack", method = RequestMethod.POST)
		public ModelAndView scrBack(HttpServletRequest httpServletRequest,
				HttpServletResponse httpServletResponse) {
			getModel().bind(httpServletRequest);
			ModelAndView mv = new ModelAndView("RoadCuttingView", MainetConstants.FORM_NAME, this.getModel());
			mv.addObject(BindingResult.MODEL_KEY_PREFIX + MainetConstants.FORM_NAME, getModel().getBindingResult());
			return mv;

		}
		
		
	    @RequestMapping(params = "getCoordinates", method = RequestMethod.POST)
	    public @ResponseBody double[] fetchCoordinate(HttpServletRequest httpRequest,@RequestParam Long fileIndex) {
	        double[] coordinates = new double[2];
	        if ((FileUploadUtility.getCurrent().getFileMap().entrySet() != null)
	                && !FileUploadUtility.getCurrent().getFileMap().entrySet().isEmpty()) {
	            for (final Map.Entry<Long, Set<File>> entry : FileUploadUtility.getCurrent().getFileMap().entrySet()) {
	                final Iterator<File> setFilesItr = entry.getValue().iterator();
	                while (setFilesItr.hasNext()) {
	                    final File file = setFilesItr.next();
	                    javaxt.io.Image image = new javaxt.io.Image(file.getPath());
	                    double[] coord = image.getGPSCoordinate();
	                    if (coord != null) {
	                        coordinates[0] = coord[1];
	                        coordinates[1] = coord[0];
	                    }
	                }
	            }
	        }
	        return coordinates;
	    }
	
}
		
		
