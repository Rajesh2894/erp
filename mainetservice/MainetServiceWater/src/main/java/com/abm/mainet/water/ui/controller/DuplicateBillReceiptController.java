/**
 * 
 */
package com.abm.mainet.water.ui.controller;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Optional;
import java.util.TreeMap;
import java.util.stream.Collectors;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.collections.CollectionUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.abm.mainet.cfc.challan.dto.ChallanReceiptPrintDTO;
import com.abm.mainet.common.constant.MainetConstants;
import com.abm.mainet.common.constant.ServiceEndpoints;
import com.abm.mainet.common.domain.Employee;
import com.abm.mainet.common.domain.Organisation;
import com.abm.mainet.common.domain.ServiceMaster;
import com.abm.mainet.common.dto.CFCSchedulingCounterDet;
import com.abm.mainet.common.dto.JsonViewObject;
import com.abm.mainet.common.integration.dto.ChargeDetailDTO;
import com.abm.mainet.common.integration.dto.TbBillMas;
import com.abm.mainet.common.integration.dto.WSRequestDTO;
import com.abm.mainet.common.integration.dto.WSResponseDTO;
import com.abm.mainet.common.integration.report.utility.ReportUtility;
import com.abm.mainet.common.master.dto.TbTaxMas;
import com.abm.mainet.common.master.service.TbCfcApplicationMstService;
import com.abm.mainet.common.master.service.TbDepartmentService;
import com.abm.mainet.common.master.service.TbTaxMasService;
import com.abm.mainet.common.service.IEmployeeService;
import com.abm.mainet.common.service.ReceiptDetailService;
import com.abm.mainet.common.service.ServiceMasterService;
import com.abm.mainet.common.ui.controller.AbstractFormController;
import com.abm.mainet.common.utility.CommonMasterUtility;
import com.abm.mainet.common.utility.Filepaths;
import com.abm.mainet.common.utility.LookUp;
import com.abm.mainet.common.utility.RestClient;
import com.abm.mainet.common.utility.UserSession;
import com.abm.mainet.common.utility.Utility;
import com.abm.mainet.water.datamodel.WaterRateMaster;
import com.abm.mainet.water.domain.TbKCsmrInfoMH;
import com.abm.mainet.water.domain.TbWtBillMasEntity;
import com.abm.mainet.water.dto.WaterBillPrintSkdclDTO;
import com.abm.mainet.water.repository.BillMasterJpaRepository;
import com.abm.mainet.water.service.NewWaterConnectionService;
import com.abm.mainet.water.service.TbWtBillMasService;
import com.abm.mainet.water.ui.model.DuplicateBillReceiptModel;

/**
 * @author akshata.bhat
 *
 */
@Controller
@RequestMapping("/DuplicateBillReceipt.html")
public class DuplicateBillReceiptController extends AbstractFormController<DuplicateBillReceiptModel>{

	private static final Logger LOGGER = Logger.getLogger(DuplicateBillReceiptController.class);
	@Autowired
	TbDepartmentService departmentService;

	@Autowired
	private ReceiptDetailService receiptDetailService;

	@Autowired
	private TbTaxMasService tbTaxMasService;

	@Autowired
	ServiceMasterService serviceMasterService;
	
	@Autowired
    private  IEmployeeService employeeService; 
	
	@Autowired
    private TbCfcApplicationMstService tbCfcservice;
	    
	@Resource
	private BillMasterJpaRepository billMasterJpaRepository;
	
	@Autowired
	WaterBillPrintingController waterBillPrintingController;
	
	@Autowired
    private NewWaterConnectionService newWaterConnectionService;
	
	@Autowired
	private TbWtBillMasService tbWtBillMasService;
	
	private String selectedBillNo;
	
	@RequestMapping(method = { RequestMethod.GET, RequestMethod.POST })
	public ModelAndView index(HttpServletRequest request) {
		sessionCleanup(request);
		this.getModel().setDepartmentList(departmentService.findAll());
		this.getModel().setLangId(UserSession.getCurrent().getLanguageId());
        this.getModel().setFinYearList(Utility.getNoOfFinancialYearIncludingCurrent(20));
        this.getModel().setOrgId(UserSession.getCurrent().getOrganisation().getOrgid());
		LOGGER.info("Duplicate Bill print index method()");
		return new ModelAndView("DuplicateBillReceipt", MainetConstants.FORM_NAME, this.getModel());
	}
	
	@ResponseBody
	@RequestMapping(params = "getBillList", method = RequestMethod.POST)
	public List<TbBillMas> getBillList(@Param("csCcn") String csCcn) {
		List<TbWtBillMasEntity> customCycleWiseBills = new LinkedList<>();
		Long orgId = UserSession.getCurrent().getOrganisation().getOrgid();
		try {
			TbKCsmrInfoMH csmrInfo = newWaterConnectionService.getConnectionByCsCcnAndOrgId(csCcn, orgId);
			if(csmrInfo!=null) {
				List<TbWtBillMasEntity> billList = billMasterJpaRepository.fetchAllBillByCsIdn(csmrInfo.getCsIdn(), orgId);
				if(CollectionUtils.isNotEmpty(billList)) {
					Map<Date, List<TbWtBillMasEntity>> genDateWiseBillList = billList.stream().collect(Collectors.groupingBy(bill-> bill.getBmBilldt()));
					
					Map<Date, List<TbWtBillMasEntity>> dateWiseSortedBill = new TreeMap<Date, List<TbWtBillMasEntity>>(Collections.reverseOrder());
					dateWiseSortedBill.putAll(genDateWiseBillList);
					Map<String, TbWtBillMasEntity> billNoWiseBillMap = new LinkedHashMap<>();
					LOGGER.info(dateWiseSortedBill);
					for (Entry<Date, List<TbWtBillMasEntity>> keyOfMap : dateWiseSortedBill.entrySet()) {
						if(keyOfMap.getValue()!=null && !keyOfMap.getValue().isEmpty() && keyOfMap.getValue().size()>1){
							Date billCycleFromDate = keyOfMap.getValue().get(0).getBmFromdt();
							Date billCycleToDate = keyOfMap.getValue().get(keyOfMap.getValue().size()-1).getBmTodt();
							String billNo = keyOfMap.getValue().get(keyOfMap.getValue().size()-1).getBmNo();
							TbWtBillMasEntity latestBillInTheCycle = keyOfMap.getValue().get(keyOfMap.getValue().size()-1);
							latestBillInTheCycle.setBmFromdt(billCycleFromDate);
							latestBillInTheCycle.setBmTodt(billCycleToDate);
							billNoWiseBillMap.put(billNo, latestBillInTheCycle);
						}else {
							billNoWiseBillMap.put(keyOfMap.getValue().get(0).getBmNo(), keyOfMap.getValue().get(0));
						}
					}
					customCycleWiseBills.addAll(billNoWiseBillMap.values());

					}
				}else {
					LOGGER.error("No bills present for connection number " + getModel().getConnectionNo());
				}
			}catch(Exception ex) {
				LOGGER.error("Invalid connection number " + getModel().getConnectionNo() +ex.getMessage());
			}
		getModel().setBillList(tbWtBillMasService.getUnpaidBillEntityToDto(customCycleWiseBills));
		getModel().setPaymentCheck(null);
		getModel().setConnectionNo(csCcn);
		return tbWtBillMasService.getUnpaidBillEntityToDto(customCycleWiseBills);
	}

	@ResponseBody
	@RequestMapping(method = RequestMethod.POST, params = "getCharges")
	public ModelAndView getCharges(final HttpServletRequest httpServletRequest) {
		this.getModel().bind(httpServletRequest);
		DuplicateBillReceiptModel model = this.getModel();
		Organisation organisation = UserSession.getCurrent().getOrganisation();
		String chargesAmount = "0.0";
		this.selectedBillNo = model.getBillNo();
		WSRequestDTO wsRequestDTO = new WSRequestDTO();
		wsRequestDTO.setModelName("WaterRateMaster");
		final WSResponseDTO response = RestClient.callBRMS(wsRequestDTO,
                ServiceEndpoints.BRMSMappingURL.INITIALIZE_MODEL_URL);
        if (response != null && MainetConstants.Req_Status.SUCCESS.equals(response.getWsStatus())) {
            List<Object> waterRate = RestClient.castResponse(response, WaterRateMaster.class, 0);
            WaterRateMaster rateMaster = (WaterRateMaster) waterRate.get(0);
				ServiceMaster serviceMaster = serviceMasterService.getServiceMasterByShortCode(MainetConstants.ServiceShortCode.DUPLICATE_BILL_PRINT,
						organisation.getOrgid());
				rateMaster.setOrgId(organisation.getOrgid());
				rateMaster.setNoOfCopies(model.getNoOfCopies());
				rateMaster.setServiceCode(MainetConstants.ServiceShortCode.DUPLICATE_BILL_PRINT);
	            rateMaster.setDeptCode(MainetConstants.WATER_DEPT);
	            TbTaxMas taxMas = tbTaxMasService.getTaxIdByServiceIdOrgIdDeptId(serviceMaster.getSmServiceId(), organisation.getOrgid(),
	            		serviceMaster.getTbDepartment().getDpDeptid());	
	            String taxType = CommonMasterUtility.getCPDDescription(Long.parseLong(taxMas.getTaxMethod()),
	                    MainetConstants.FlagE, organisation.getOrgid());
	            rateMaster.setTaxType(taxType);
	            rateMaster.setTaxCode(taxMas.getTaxCode());
                LookUp taxCategory1 = CommonMasterUtility.getHierarchicalLookUp(taxMas.getTaxCategory1(), organisation);
                rateMaster.setTaxCategory(taxCategory1.getDescLangFirst());
                final String taxCategory2 = CommonMasterUtility.getHierarchicalLookUp(taxMas.getTaxCategory2(), organisation)
                        .getDescLangFirst();
                rateMaster.setTaxSubCategory(taxCategory2);
                rateMaster.setRateStartDate(new Date().getTime());
                rateMaster.setChargeApplicableAt("Application");
	            wsRequestDTO.setDataModel(Arrays.asList(rateMaster));
				WSResponseDTO responseDTO =  getDuplicatePaymentCharges(wsRequestDTO);
		  		if (responseDTO != null && responseDTO.getWsStatus().equals(MainetConstants.WebServiceStatus.SUCCESS)) {
					final List<?> waterRateList = RestClient.castResponse(responseDTO, WaterRateMaster.class);
					LinkedHashMap<String, String> charges = null;
					List<ChargeDetailDTO> detailDTOs = new ArrayList<>();
					detailDTOs = (List<ChargeDetailDTO>) responseDTO.getResponseObj();
					for (final Object rate : detailDTOs) {
						charges = (LinkedHashMap<String, String>) rate;
						break;
					} 
					chargesAmount = String.valueOf(charges.get("flatRate"));
					model.setChargesAmount(chargesAmount);
	            	model.getOfflineDTO().setAmountToPay(chargesAmount);
	            	model.getOfflineDTO().setAmountToShow(Double.parseDouble(chargesAmount));
	            	model.getOfflineDTO().setAmountToPay(chargesAmount);
	            	model.setPaymentCheck(MainetConstants.Y_FLAG);
	            	Optional<TbBillMas> selectedBill = model.getBillList().stream().filter(bill->bill.getBmNo().equals(model.getBillNo())).findFirst();
	            	model.setBillNo(selectedBill!=null && selectedBill.isPresent() ? (selectedBill.get().getBmFromdt() + "-" +
            			selectedBill.get().getBmTodt() + "( " + selectedBill.get().getBmNo() + ")"): "");
	            	model.setFinYear(model.getFinYear());
	                model.setApplicationCharge(chargesAmount);
				}else {
					LOGGER.error("Cannot get duplicate receipt charges");
				}
	        }
		return new ModelAndView("DuplicateBillReceiptValidn", MainetConstants.FORM_NAME, this.getModel());

	}
	public WSResponseDTO getDuplicatePaymentCharges(@RequestBody WSRequestDTO wSRequestDTO) {
		LOGGER.info("brms getDuplicatePaymentCharges execution start..");
		WSResponseDTO responseDTO = null;
		try {
			responseDTO = RestClient.callBRMS(wSRequestDTO, ServiceEndpoints.BRMSMappingURL.WATER_SERVICE_CHARGE_URL);
		} catch (Exception ex) {
			LOGGER.error("UNBALE TO REQUEST FOR WATER RATE CHARGES"+ ex.getMessage());
		}
		LOGGER.info("brms getDuplicatePaymentCharges execution End..");
		return responseDTO;
	}	

	/**
	 * Show charge details Page
	 */
	@RequestMapping(method = { RequestMethod.GET }, params = "showChargeDetails")
	public ModelAndView showChargesDetails(final HttpServletRequest httpServletRequest) {
		return new ModelAndView("ChargesDetail", MainetConstants.CommonConstants.COMMAND, getModel());
	}

	@ResponseBody
	@RequestMapping(params = "printBill")
	public ModelAndView printReceiptData(final HttpServletRequest httpServletRequest, final HttpServletResponse httpServletResponse,
			@RequestParam("csCcn") final String csCcn) {
		bindModel(httpServletRequest);
		ModelAndView generateBillAndPrint = null;
		long orgid = UserSession.getCurrent().getOrganisation().getOrgid();
		TbKCsmrInfoMH csmrInfo = billMasterJpaRepository.fetchCsIdnByConnectionNumber(csCcn, orgid);
		try {
			TbWtBillMasEntity billDataByBillNo = billMasterJpaRepository.getBillDataByBillNo(csmrInfo.getCsIdn(), selectedBillNo, orgid);
			if(billDataByBillNo!=null) {
				Map<String, WaterBillPrintSkdclDTO> printDuplicateBill = tbWtBillMasService.printDuplicateBill(UserSession.getCurrent().getOrganisation(),
					csmrInfo.getCsIdn(), billDataByBillNo.getBmIdno(), selectedBillNo);
				printJasperReport(httpServletRequest, httpServletResponse, printDuplicateBill);
			}else{
				LOGGER.error("Bill is not present for this connection number " + csCcn);
			}
		}catch(Exception ex) {
			LOGGER.error("Exception while fetching bill for ccn " + csCcn + " "+ ex.getMessage());
		}
    	return generateBillAndPrint;
	}

	public ModelAndView printJasperReport(final HttpServletRequest httpServletRequest, final HttpServletResponse httpServletResponse,
		Map<String, WaterBillPrintSkdclDTO> printDuplicateBill) {
		
        LOGGER.info("In printJasperReport SKDCL ");
        if (printDuplicateBill!=null && Utility.isEnvPrefixAvailable( UserSession.getCurrent().getOrganisation(), MainetConstants.ENV_SKDCL)) {
        	final Map<String, Object> map = new HashMap<>();
            final String jrxmlName = "WaterMeterBillPrintingReport.jrxml";
            final String jrxmlFileLocation = Filepaths.getfilepath() + "jasperReport" + MainetConstants.FILE_PATH_SEPARATOR
            	+ jrxmlName;
            List<WaterBillPrintSkdclDTO> billDtoValues = printDuplicateBill.values().stream().collect(Collectors.toList());
            LOGGER.info("Duplicate Bill values: "+ billDtoValues.get(0));
            LOGGER.info("Searching for jasper file in location: " + jrxmlFileLocation);
            String fileName = ReportUtility.generateReportFromCollectionUtility(httpServletRequest, httpServletResponse,
            	jrxmlFileLocation, map, billDtoValues, UserSession.getCurrent().getEmployee().getEmpId());
            LOGGER.info("FileName is" + fileName);
            if (!fileName.equals(MainetConstants.SERVER_ERROR)) {
            	getModel().setFilePath(fileName);
                if (fileName.contains(MainetConstants.DOUBLE_BACK_SLACE)) {
    				fileName = fileName.replace(MainetConstants.DOUBLE_BACK_SLACE, MainetConstants.QUAD_BACK_SLACE);
    			} else if (fileName.contains(MainetConstants.DOUBLE_FORWARD_SLACE)) {
    				fileName = fileName.replace(MainetConstants.DOUBLE_FORWARD_SLACE, MainetConstants.QUAD_FORWARD_SLACE);
    			}
    			getModel().setFilePath(fileName);
            } else {
            	getModel().addValidationError("Jasper report not found in specified location: " + fileName);
            	LOGGER.error("Error while calling generateReportFromCollectionUtility(): " + fileName);
            }
		  }       
    	return new ModelAndView(MainetConstants.URL_EVENT.OPEN_NEXT_TAB, MainetConstants.FORM_NAME, getModel());
	}

	@ResponseBody
	@RequestMapping(params = "printNewBill")
	public ModelAndView printNewReceiptData(final HttpServletRequest request) {
		bindModel(request);
		Long orgId = UserSession.getCurrent().getOrganisation().getOrgid();
		int langId = UserSession.getCurrent().getLanguageId();
		try {
			ChallanReceiptPrintDTO printDTO = receiptDetailService
					.setValuesAndPrintReport(this.getModel().getReceiptDTO().getReceiptId(), orgId, langId);
			LookUp lookUp = CommonMasterUtility.getValueFromPrefixLookUp("CST", MainetConstants.ENV,
					UserSession.getCurrent().getOrganisation());
			CFCSchedulingCounterDet counterDet = null;
			if (lookUp != null && lookUp.getOtherField().equals(MainetConstants.FlagY)) {
				counterDet = tbCfcservice.getCounterDetByEmpId(UserSession.getCurrent().getEmployee().getEmpId(),
						UserSession.getCurrent().getOrganisation().getOrgid());
				if (counterDet != null && counterDet.getEmpId() != null) {
					Employee empDto = employeeService.findEmployeeById(counterDet.getEmpId());
					if (empDto != null) {
						String empName = empDto.getEmpname().concat(" ").concat(empDto.getEmpmname()).concat(" ")
								.concat(empDto.getEmplname());
						printDTO.setUserName(empName);
					}
				}
			}
			this.getModel().setCfcSchedulingCounterDet(counterDet);
			this.getModel().setReceiptDTO(printDTO);
		} catch (Exception e) {
			LOGGER.info("Exception occure while Fetching receipt details in printNewReceiptData:" + e);
		}
		return new ModelAndView(MainetConstants.CHALLAN_AT_ULB_RECEIPT_PRINT, MainetConstants.FORM_NAME,
				this.getModel());
	}

	@RequestMapping(params = MainetConstants.TradeLicense.GENERATE_CHALLAN_AND_PAYMENT, method = RequestMethod.POST)
	public ModelAndView generateChallanAndPayement(HttpServletRequest httpServletRequest,
			HttpServletResponse httpServletResponse) {
		getModel().bind(httpServletRequest);
		DuplicateBillReceiptModel model = this.getModel();
		if (model.validateInputs()) {
			if(true) {
				if (model.saveForm()) {
					return jsonResult(JsonViewObject.successResult(model.getSuccessMessage()));
				} else
					return new ModelAndView(MainetConstants.DEFAULT_EXCEPTION_FORM_VIEW);
			}
		}
		return defaultMyResult();
	}

	@ResponseBody
	@RequestMapping(params = "checkIfBillExists", method = RequestMethod.POST)
	public Boolean checkIfBillExists(@Param("bmIdNo") Long bmIdNo, @Param("orgid") Long orgid) {
		TbWtBillMasEntity billPaymentDataByBmno = null;
		orgid = orgid != null? orgid : UserSession.getCurrent().getOrganisation().getOrgid();

		try{
			billPaymentDataByBmno = billMasterJpaRepository.getBillPaymentDataByBmno(bmIdNo, orgid);
		}catch(Exception ex) {
			LOGGER.error("Error while checking for bill " + ex.getMessage());
		}
		LOGGER.info("checkReceiptExists method called");
		return billPaymentDataByBmno!=null? true: false;
	}

	@ResponseBody
	@RequestMapping(params = "getBillListForFinancialYear", method = RequestMethod.POST)
	public List<TbWtBillMasEntity> getBillListForFinancialYear(@Param("csIdn") Long csIdn,  @Param("orgid") Long orgid, @Param("bmYear") Long bmYear) {
		List<TbWtBillMasEntity> billListByCsidnForFinYear = null;
		orgid = orgid != null? orgid : UserSession.getCurrent().getOrganisation().getOrgid();
		try {
			//get list of bills by descending order
			billListByCsidnForFinYear = billMasterJpaRepository.getBillListByCsidnForFinYear(csIdn, orgid, bmYear);
		}catch(Exception ex) {
			LOGGER.error("Error while fetching bills for csIdn " + csIdn + " : " + ex.getMessage());
		}
		LOGGER.info("getBillListForFinancialYear() method called");
		return billListByCsidnForFinYear;
	}
	
}
