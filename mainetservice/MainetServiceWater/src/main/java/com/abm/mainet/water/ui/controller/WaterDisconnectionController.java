package com.abm.mainet.water.ui.controller;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.json.MappingJackson2JsonView;

import com.abm.mainet.common.constant.MainetConstants;
import com.abm.mainet.common.constant.MainetConstants.WaterServiceShortCode;
import com.abm.mainet.common.domain.Designation;
import com.abm.mainet.common.domain.ServiceMaster;
import com.abm.mainet.common.dto.JsonViewObject;
import com.abm.mainet.common.exception.FrameworkException;
import com.abm.mainet.common.integration.dms.service.IFileUploadService;
import com.abm.mainet.common.integration.dto.TbBillMas;
import com.abm.mainet.common.master.service.DesignationService;
import com.abm.mainet.common.service.ApplicationService;
import com.abm.mainet.common.service.IEmployeeService;
import com.abm.mainet.common.service.IFinancialYearService;
import com.abm.mainet.common.service.ServiceMasterService;
import com.abm.mainet.common.ui.controller.AbstractFormController;
import com.abm.mainet.common.utility.ApplicationContextProvider;
import com.abm.mainet.common.utility.ApplicationSession;
import com.abm.mainet.common.utility.UserSession;
import com.abm.mainet.common.utility.Utility;
import com.abm.mainet.water.domain.PlumberMaster;
import com.abm.mainet.water.domain.TbWtBillMasEntity;
import com.abm.mainet.water.dto.CustomerInfoDTO;
import com.abm.mainet.water.dto.NoDuesCertificateReqDTO;
import com.abm.mainet.water.dto.NoDuesCertificateRespDTO;
import com.abm.mainet.water.dto.TBWaterDisconnectionDTO;
import com.abm.mainet.water.dto.TbCsmrInfoDTO;
import com.abm.mainet.water.dto.WaterDeconnectionRequestDTO;
import com.abm.mainet.water.dto.WaterDisconnectionResponseDTO;
import com.abm.mainet.water.repository.TbWtBillMasJpaRepository;
import com.abm.mainet.water.service.BillMasterService;
import com.abm.mainet.water.service.NewWaterConnectionService;
import com.abm.mainet.water.service.TbWtBillMasService;
import com.abm.mainet.water.service.WaterCommonService;
import com.abm.mainet.water.service.WaterDisconnectionService;
import com.abm.mainet.water.service.WaterNoDuesCertificateService;
import com.abm.mainet.water.ui.model.WaterDisconnectionModel;

@Controller
@RequestMapping("/WaterDisconnectionForm.html")
public class WaterDisconnectionController extends AbstractFormController<WaterDisconnectionModel> {

	private static Logger log = Logger.getLogger(WaterDisconnectionController.class);

	@Autowired
	private ServiceMasterService serviceMaster;

	@Resource
	private WaterDisconnectionService waterDisconnectionService;

	@Resource
	private IFileUploadService fileUploadService;

	@Autowired
	DesignationService designationService;

	@Autowired
	IEmployeeService employeeService;

	@Autowired
	WaterCommonService waterCommonService;
	
	@Autowired
	private WaterNoDuesCertificateService waterNoDuesCertificateService;
	
	@Autowired
	private BillMasterService billMasterService;
	
	@Autowired
	private TbWtBillMasService billMasService;
	
	@Autowired
	private TbWtBillMasJpaRepository tbWtBillMasJpaRepository;
	
	@Autowired
	private IFinancialYearService iFinancialYearService;

	private static final String SEARCH_CONNECTION_DETAILS = "searchConnectionDetails";

	@RequestMapping(method = { RequestMethod.POST, RequestMethod.GET })
	public ModelAndView index(final HttpServletRequest httpServletRequest) {
		sessionCleanup(httpServletRequest);
		fileUploadService.sessionCleanUpForFileUpload();
		final WaterDisconnectionModel model = getModel();
		model.setCommonHelpDocs("WaterDisconnectionForm.html");
		try {
			getModel().initializeApplicantDetail();
			final long orgId = UserSession.getCurrent().getOrganisation().getOrgid();
			final ServiceMaster smServiceMaster = serviceMaster
					.getServiceByShortName(WaterServiceShortCode.WATER_DISCONNECTION, orgId);
			final Long serviceId = smServiceMaster.getSmServiceId();
			getModel().setUlbPlumber(MainetConstants.FlagU);
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
			model.setServiceId(serviceId);
			model.setServiceName(smServiceMaster.getSmServiceName());
			model.setDeptId(smServiceMaster.getTbDepartment().getDpDeptid());
		} catch (final Exception exception) {
			log.error("Exception found : ", exception);
			ModelAndView modelAndView;
			modelAndView = new ModelAndView(MainetConstants.DEFAULT_EXCEPTION_VIEW, MainetConstants.FORM_NAME,
					getModel());
			return modelAndView;
		}
		return super.index();
	}

	@RequestMapping(params = "saveWaterForm", method = RequestMethod.POST)
	public ModelAndView searchAllDefaulter(final HttpServletRequest httpServletRequest) {
		bindModel(httpServletRequest);

		final WaterDisconnectionModel model = getModel();
		try {
			if (model.saveWaterForm()) {
				if(Utility.isEnvPrefixAvailable(UserSession.getCurrent().getOrganisation(), MainetConstants.ENV_SKDCL)) {
					return new ModelAndView("NewWaterConnectionApplicationFormPrint", MainetConstants.FORM_NAME, getModel());
				}
				return jsonResult(JsonViewObject.successResult(model.getSuccessMessage()));
			}
		} catch (final Exception ex) {
			logger.error(ex);
			return jsonResult(JsonViewObject.failureResult(ex));
		}

		return defaultMyResult();
	}

	@RequestMapping(params = SEARCH_CONNECTION_DETAILS, method = RequestMethod.POST)
	public ModelAndView getConnectionDetails(final HttpServletRequest httpServletRequest) {
		bindModel(httpServletRequest);
		ModelAndView mv;
		final WaterDisconnectionModel model = getModel();
		
		if(Utility.isEnvPrefixAvailable(UserSession.getCurrent().getOrganisation(), MainetConstants.ENV_SKDCL)) {
			model.validateConnectionDetail();
		}
		else {
			model.validateApplicantDetail();
		}
		
		if (!model.hasValidationErrors()) {
			final WaterDeconnectionRequestDTO waterRequestDto = new WaterDeconnectionRequestDTO();
			waterRequestDto.setOrgId(UserSession.getCurrent().getOrganisation().getOrgid());
			waterRequestDto.setUserId(UserSession.getCurrent().getEmployee().getEmpId());
			waterRequestDto.setConnectionNo(getModel().getConnectionNo());

			 WaterDisconnectionResponseDTO waterResponseDto = waterDisconnectionService
					.getConnectionsAvailableForDisConnection(waterRequestDto);
			String paymentStatusFlagByApplNo = null;
			if (waterResponseDto.getConnectionList().size() == 0) {
				TBWaterDisconnectionDTO disconnectionAppDetailsByCsIdn = null;
				TbCsmrInfoDTO consumerInfo = ApplicationContextProvider.getApplicationContext()
						.getBean(NewWaterConnectionService.class).fetchConnectionDetailsByConnNo(
								getModel().getConnectionNo(), UserSession.getCurrent().getOrganisation().getOrgid());
				if (consumerInfo != null && consumerInfo.getCsIdn()!=0) {
					 disconnectionAppDetailsByCsIdn = waterDisconnectionService.getDisconnectionAppDetailsByCsIdn(
							UserSession.getCurrent().getOrganisation().getOrgid(), consumerInfo.getCsIdn());
					 if(disconnectionAppDetailsByCsIdn != null) {
						 paymentStatusFlagByApplNo = ApplicationContextProvider.getApplicationContext().getBean(ApplicationService.class)
								.getPaymentStatusFlagByApplNo(disconnectionAppDetailsByCsIdn.getApmApplicationId(),
										UserSession.getCurrent().getOrganisation().getOrgid());
						 if(StringUtils.isBlank(paymentStatusFlagByApplNo)) {
							 waterResponseDto = waterDisconnectionService
										.getConnectionsForDisConnection(waterRequestDto);
						 }
						 
					 }
				}
			}
			if (waterResponseDto.getConnectionList().size() == 1) {
				String respMsg = "";
				if (waterResponseDto.getConnectionList().get(0).getApplicationNo()!=null) {
                    String status = waterDisconnectionService.getWorkflowRequestByAppId(waterResponseDto.getConnectionList().get(0).getApplicationNo(), waterRequestDto.getOrgId());
                    if (MainetConstants.TASK_STATUS_PENDING.equalsIgnoreCase(status)) {
                        respMsg = ApplicationSession.getInstance().getMessage(
                                "water.disconnection.search.error");
                        //model.setResultFound(false);
                        getModel().addValidationError(ApplicationSession.getInstance().getMessage("water.disconnection.search.error",
        						new Object[] { getModel().getConnectionNo() }));
                       // return new ModelAndView(new MappingJackson2JsonView(), MainetConstants.ERR_MSG, respMsg);
                        
                        mv = new ModelAndView("WaterDisconnectionValidn", MainetConstants.FORM_NAME, getModel());
                		mv.addObject(BindingResult.MODEL_KEY_PREFIX + MainetConstants.FORM_NAME, getModel().getBindingResult());
                    }
                }
				
				
				final CustomerInfoDTO customerInfoDTO = waterResponseDto.getConnectionList().get(0);
				getModel().setConnectionNo(customerInfoDTO.getCsCcn());
				getModel().setConsumerName(customerInfoDTO.getConsumerName());
				getModel().setAreaName(customerInfoDTO.getCsAdd());
				getModel().setConnectionList(waterResponseDto.getConnectionList());
				getModel().setCsOldCcn(customerInfoDTO.getCsOldccn());
				checkBillGenAndDuesCheck(customerInfoDTO);
			} else {

				getModel().addValidationError(ApplicationSession.getInstance().getMessage("water.discon.norecod",
						new Object[] { getModel().getConnectionNo() }));
			}
				
		}
		mv = new ModelAndView("WaterDisconnectionValidn", MainetConstants.FORM_NAME, getModel());
		mv.addObject(BindingResult.MODEL_KEY_PREFIX + MainetConstants.FORM_NAME, getModel().getBindingResult());

		return mv;
	}

	
	@RequestMapping(method = { RequestMethod.POST }, params = "getCheckListAndCharges")
	public ModelAndView doGetApplicableCheckListAndCharges(final HttpServletRequest request) {
		bindModel(request);
		final WaterDisconnectionModel model = getModel();
		model.validateApplicantDetail();
		model.validateConnectionDetail();
		if (!model.hasValidationErrors()) {

			try {

				final long orgId = UserSession.getCurrent().getOrganisation().getOrgid();
				model.findApplicableCheckListAndCharges(model.getServiceId(), orgId);
				model.setEnableSubmit(true);
			} catch (final Exception ex) {
				throw new FrameworkException(ex);
			}
		}

		return defaultMyResult();
	}

	@RequestMapping(params = "proceed", method = { RequestMethod.POST })
	public ModelAndView proceedAfterEdit(HttpServletRequest request) {
		getModel().bind(request);
		return new ModelAndView("WaterDisconnectionViewAfterEdit", MainetConstants.CommonConstants.COMMAND,
				this.getModel());
	}


	  @RequestMapping(params = "getDuesForConnNoSKDCL", method = RequestMethod.POST) 
	    public @ResponseBody NoDuesCertificateReqDTO getDuesForConnNoSKDCL(final HttpServletRequest request) {
		  log.info("Start the getConnectionDetail()");
	        getModel().bind(request);
	        NoDuesCertificateReqDTO noDuesCertificateReqDTO = new NoDuesCertificateReqDTO();
	        final WaterDisconnectionModel model = getModel();
	        Long orgId = UserSession.getCurrent().getOrganisation().getOrgid();
	        model.getDisconnectionEntity().setOrgId(orgId);
	        final String connectionNo = model.getConnectionNo();
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
	  
	private void checkBillGenAndDuesCheck(CustomerInfoDTO customerInfoDTO) {
		if (Utility.isEnvPrefixAvailable(UserSession.getCurrent().getOrganisation(), MainetConstants.ENV_PSCL) || Utility.isEnvPrefixAvailable(UserSession.getCurrent().getOrganisation(), MainetConstants.ENV_ASCL)) {
			Long finYearId = iFinancialYearService.getFinanceYearId(new Date());
			List<TbWtBillMasEntity> currentBill = tbWtBillMasJpaRepository
					.getArrearsDeletionBills(customerInfoDTO.getCsIdn(), finYearId);
			if (CollectionUtils.isEmpty(currentBill)) {

				getModel().addValidationError(ApplicationSession.getInstance()
						.getMessage("Bill.generation.is.not.up.to.date.for.connection.no.")+" " + customerInfoDTO.getCsCcn()
								+" "+ ApplicationSession.getInstance()
								.getMessage("Please.generate.the.bills.and.pay.dues.to.proceed.with.water.disconnection"));
				
			} else {
				List<TbBillMas> billPendingList = billMasterService.getBillMasterListByUniqueIdentifier(
						customerInfoDTO.getCsIdn(), UserSession.getCurrent().getOrganisation().getOrgid());
				if (CollectionUtils.isNotEmpty(billPendingList)) {
					getModel().addValidationError(
							ApplicationSession.getInstance().getMessage("Dues.are.pending.please.clear.the.dues"));
				}else {
					getModel().setCheckerror("Y");
				}
			}
		}
	}
	
}