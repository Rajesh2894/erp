
package com.abm.mainet.property.ui.controller;

import java.io.File;
import java.io.IOException;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.DoubleStream;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.codec.binary.Base64;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.collections.MapUtils;
import org.apache.commons.io.FileUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.abm.mainet.bill.service.BillMasterCommonService;
import com.abm.mainet.cfc.challan.service.IChallanService;
import com.abm.mainet.cfc.challan.ui.validator.CommonOfflineMasterValidator;
import com.abm.mainet.common.constant.MainetConstants;
import com.abm.mainet.common.constant.PrefixConstants;
import com.abm.mainet.common.domain.Organisation;
import com.abm.mainet.common.domain.ServiceMaster;
import com.abm.mainet.common.dto.BillReceiptPostingDTO;
import com.abm.mainet.common.dto.CommonChallanDTO;
import com.abm.mainet.common.integration.acccount.domain.TbServiceReceiptMasEntity;
import com.abm.mainet.common.integration.acccount.dto.TbServiceReceiptMasBean;
import com.abm.mainet.common.integration.dms.fileUpload.FileUploadUtility;
import com.abm.mainet.common.integration.dms.service.IFileUploadService;
import com.abm.mainet.common.integration.dto.DocumentDetailsVO;
import com.abm.mainet.common.integration.dto.RequestDTO;
import com.abm.mainet.common.integration.dto.TbBillDet;
import com.abm.mainet.common.integration.dto.TbBillMas;
import com.abm.mainet.common.master.dto.TbTaxMas;
import com.abm.mainet.common.master.service.DepartmentService;
import com.abm.mainet.common.master.service.ICommonReversalEntry;
import com.abm.mainet.common.master.service.TbTaxMasService;
import com.abm.mainet.common.repository.ReceiptRepository;
import com.abm.mainet.common.service.IFinancialYearService;
import com.abm.mainet.common.service.IReceiptEntryService;
import com.abm.mainet.common.service.ServiceMasterService;
import com.abm.mainet.common.ui.controller.AbstractFormController;
import com.abm.mainet.common.utility.ApplicationContextProvider;
import com.abm.mainet.common.utility.CommonMasterUtility;
import com.abm.mainet.common.utility.LookUp;
import com.abm.mainet.common.utility.UserSession;
import com.abm.mainet.common.utility.Utility;
import com.abm.mainet.property.dto.BillDisplayDto;
import com.abm.mainet.property.dto.BillPaymentDetailDto;
import com.abm.mainet.property.dto.ProvisionalAssesmentMstDto;
import com.abm.mainet.property.service.AsExecssAmtService;
import com.abm.mainet.property.service.AssesmentMastService;
import com.abm.mainet.property.service.PropertyBRMSService;
import com.abm.mainet.property.service.PropertyBillPaymentService;
import com.abm.mainet.property.service.PropertyMainBillService;
import com.abm.mainet.property.service.PropertyService;
import com.abm.mainet.property.ui.model.SelfAssesmentNewModel;
import com.google.common.util.concurrent.AtomicDouble;

/**
 * @author cherupelli.srikanth
 * @since 16 May 2023
 */

@Controller
@RequestMapping("/PreviousYearManualReceiptWithBillChange.html")
public class PreviousYearManualReceiptWithBillChangeController extends AbstractFormController<SelfAssesmentNewModel> {

	private static final Logger LOGGER = Logger.getLogger(PreviousYearManualReceiptWithBillChangeController.class);

	@Autowired
	private AssesmentMastService assesmentMastService;

	@Autowired
	private PropertyMainBillService propertyMainBillService;

	@Autowired
	private PropertyBillPaymentService propertyBillPaymentService;

	@Autowired
	private IFinancialYearService financialYearService;

	@Autowired
	private BillMasterCommonService billMasterCommonService;

	@Autowired
	private ServiceMasterService serviceMaster;

	@Autowired
	private IReceiptEntryService receiptEntryService;

	@Autowired
	private TbTaxMasService tbTaxMasService;
	
	@Autowired
    private IReceiptEntryService iReceiptEntryService;
	
	@Autowired
    private IFileUploadService fileUpload;
	
	@Autowired
    private PropertyBRMSService propertyBRMSService;
	
	@Autowired
	private ReceiptRepository receiptRepository;
	
	@Autowired
	private PropertyService propertyService;
	
	@Autowired
	ICommonReversalEntry iCommonReversalEntry;

	@RequestMapping(method = { RequestMethod.POST, RequestMethod.GET })
	public ModelAndView index(HttpServletRequest request) {
		sessionCleanup(request);
		this.getModel().setApprovalFlag(MainetConstants.FlagN);
		fileUpload.sessionCleanUpForFileUpload();
		return index();
	}

	@RequestMapping(params = "searchPropertyDetails", method = RequestMethod.POST)
	public ModelAndView searchPropertyDetails(HttpServletRequest request) {
		this.bindModel(request);
		SelfAssesmentNewModel model = this.getModel();
		ModelAndView mv = null;
		model.setApprovalFlag(MainetConstants.FlagY);
		ProvisionalAssesmentMstDto proAssMas = model.getProvisionalAssesmentMstDto();
		if(StringUtils.isBlank(proAssMas.getAssNo()) && StringUtils.isBlank(proAssMas.getAssOldpropno())) {
			model.addValidationError("Please Enter Property Number or Old Property Number");
			mv = new ModelAndView("PreviousYearManualReceiptWithBillChangeValidn", MainetConstants.FORM_NAME,
					this.getModel());
			mv.addObject(BindingResult.MODEL_KEY_PREFIX + MainetConstants.FORM_NAME, getModel().getBindingResult());
			return mv;
		}
		ProvisionalAssesmentMstDto assMst = assesmentMastService.getPropDetailByAssNoOrOldPropNo(
				UserSession.getCurrent().getOrganisation().getOrgid(), proAssMas.getAssNo(),
				proAssMas.getAssOldpropno(), MainetConstants.STATUS.ACTIVE, null);
		if (assMst == null) {
			model.addValidationError("Please enter valid Property Number / Old Property Number");
			mv = new ModelAndView("PreviousYearManualReceiptWithBillChangeValidn", MainetConstants.FORM_NAME,
					this.getModel());
			mv.addObject(BindingResult.MODEL_KEY_PREFIX + MainetConstants.FORM_NAME, getModel().getBindingResult());
			return mv;
		}
		BillPaymentDetailDto billPayDto = propertyBillPaymentService.getBillPaymentDetail(proAssMas.getAssOldpropno(),
				assMst.getAssNo(), UserSession.getCurrent().getOrganisation().getOrgid(),
				UserSession.getCurrent().getEmployee().getEmpId(), model.getManualReeiptDate(), null, null);
		this.getModel().setApprovalFlag(MainetConstants.FlagY);
		// List<TbBillMas> billMasData =
		// propertyMainBillService.fetchNotPaidBillForAssessment(assMst.getAssNo(),
		// UserSession.getCurrent().getOrganisation().getOrgid());
		model.setProvisionalAssesmentMstDto(assMst);
		model.setBillPayDto(billPayDto);
		return new ModelAndView("PreviousYearManualReceiptWithBillChangeForm", MainetConstants.FORM_NAME, model);
	}

	@ResponseBody
	@RequestMapping(params = "updateRevisedBill", method = RequestMethod.POST)
	public Map<String, Object> updateManualReceiptUpdateBill(HttpServletRequest request) {
		this.getModel().bind(request);
		Organisation organisation = UserSession.getCurrent().getOrganisation();
		Map<String, Object> object = new LinkedHashMap<String, Object>();
		SelfAssesmentNewModel model = this.getModel();
		Date manualReeiptDate = model.getManualReeiptDate();
		List<TbBillMas> billsToKnockOff = new ArrayList<TbBillMas>();
		double excessAmount = 0.00;
		List<DocumentDetailsVO> docs  = new ArrayList<>(0);
        if ((FileUploadUtility.getCurrent().getFileMap() != null)
                && !FileUploadUtility.getCurrent().getFileMap().isEmpty()) {
            for (final Map.Entry<Long, Set<File>> entry : FileUploadUtility.getCurrent().getFileMap().entrySet()) {
                final List<File> list = new ArrayList<>(entry.getValue());
                for (final File file : list) {
                    try {
                        DocumentDetailsVO d = new DocumentDetailsVO();
                        final Base64 base64 = new Base64();
                        final String bytestring = base64.encodeToString(FileUtils.readFileToByteArray(file));
                        d.setDocumentByteCode(bytestring);
                        d.setDocumentName(file.getName());
                        docs.add(d);
                    } catch (final IOException e) {
                    }
                }
            }
		}else  if(Utility.isEnvPrefixAvailable(UserSession.getCurrent().getOrganisation(), MainetConstants.ENV_ASCL)) {
			object.put("document", "Please upload document"); 
			return object;
		}
        final CommonChallanDTO offline = model.getOfflineDTO();
        if (model.getManualRecptArrearPaidAmnt() > 0.0) {
			offline.setAmountToPay(Double.toString(model.getManualRecptArrearPaidAmnt()));
		}
        model.validateBean(offline, CommonOfflineMasterValidator.class);
        if(model.hasValidationErrors()) {
        	object.put("payerror", model.getBindingResult().getAllErrors());
        	return object;
        }
		Long manualYear = financialYearService.getFinanceYearId(manualReeiptDate);
		TbServiceReceiptMasEntity receipt = iReceiptEntryService.getLatestReceiptDetailByAddRefNo(UserSession.getCurrent().getOrganisation().getOrgid(),
				model.getProvisionalAssesmentMstDto().getAssNo());
		  List<TbBillMas> billMasData = propertyMainBillService.fetchAllBillByPropNo(
					model.getProvisionalAssesmentMstDto().getAssNo(),
					UserSession.getCurrent().getOrganisation().getOrgid());
		  List<TbServiceReceiptMasBean> inactiveReceiptList = new ArrayList<TbServiceReceiptMasBean>();
		  Date lastReceiptDate = null;
		if ((receipt != null && (receipt.getRmDate().after(manualReeiptDate)))) {
			List<TbServiceReceiptMasBean> collectionDetails = iReceiptEntryService.getAllReceiptsByOrgIdAndPropNo(model.getProvisionalAssesmentMstDto().getAssNo(), receipt.getDpDeptId(), UserSession.getCurrent().getOrganisation().getOrgid());
			if(CollectionUtils.isNotEmpty(collectionDetails)) {
				for (TbServiceReceiptMasBean receiptMas : collectionDetails) {
					Long receiptYear = financialYearService.getFinanceYearId(receipt.getRmDate());
					if(receiptYear > manualYear) {
						List<TbServiceReceiptMasBean> revereseReceipt = new ArrayList<TbServiceReceiptMasBean>();
						List<BillReceiptPostingDTO> billPosDtoList = new ArrayList<>();
						revereseReceipt.add(receiptMas);
						Double receiptAmount = receipt.getReceiptFeeDetail().stream().mapToDouble(det -> det.getRfFeeamount().doubleValue()).sum();
						excessAmount = excessAmount+ receiptAmount; 
						lastReceiptDate = receipt.getRmDate();
						inactiveReceiptList.add(receiptMas);
						propertyService.rvertBill(revereseReceipt, billPosDtoList, billMasData, organisation, UserSession.getCurrent().getEmployee().getEmpId(), this.getModel().getClientIpAddress());
					}
				}
			}
			billMasterCommonService.updateArrearInCurrentBills(billMasData);
			/*
			 * object.put("error",
			 * "Manual receipt date cannot be less than previous receipt Date"); return
			 * object;
			 */
		}
		 
		
		
			 
        
		/*
		 * List<TbBillMas> billMasData =
		 * propertyMainBillService.fetchNotPaidBillForAssessment(
		 * model.getProvisionalAssesmentMstDto().getAssNo(),
		 * UserSession.getCurrent().getOrganisation().getOrgid());
		 */
		double amount = model.getManualRecptArrearPaidAmnt();
		billMasData.forEach(billMas -> {
			if ((billMas.getBmYear() <= manualYear) && StringUtils.equals(MainetConstants.FlagN, billMas.getBmPaidFlag())) {
				billsToKnockOff.add(billMas);
			}
		});
		
		
		
		
		
		
		
		boolean rebateAppl = false;
		String rebateCaseType = "NR";
		Map<Long, Double> taxWiseRebate = new HashMap<Long, Double>();
		Map<Long, Long> rebateTaxId = new HashMap<Long, Long>();
		Long deptId = ApplicationContextProvider.getApplicationContext().getBean(DepartmentService.class)
				.getDepartmentIdByDeptCode("AS");
		List<BillDisplayDto> rebateDtoList = propertyBRMSService.fetchEarlyPayRebateRateForBackPosted(billsToKnockOff,
                UserSession.getCurrent().getOrganisation(), deptId, manualReeiptDate, null);
		double rebateAmount = 0.0;
		for (BillDisplayDto billDisplayDto : rebateDtoList) {
			taxWiseRebate.put(billDisplayDto.getParentTaxId(), billDisplayDto.getCurrentYearTaxAmt().doubleValue());
			rebateTaxId.put(billDisplayDto.getParentTaxId(), billDisplayDto.getTaxId());
			rebateAmount = rebateAmount + billDisplayDto.getTotalTaxAmt().doubleValue();
		}
		rebateAmount = Math.round(rebateAmount);
		
		AtomicDouble totalBal = new AtomicDouble(0);
		billsToKnockOff.forEach(billMas ->{
			billMas.getTbWtBillDet().forEach(det ->{
				totalBal.addAndGet(det.getBdCurBalTaxamt());
			});
		});
		if(CollectionUtils.isNotEmpty(billsToKnockOff)) {
		
		List<TbServiceReceiptMasEntity> currentYearBillPaidList = ApplicationContextProvider
                .getApplicationContext().getBean(ReceiptRepository.class)
                .getbillPaidBetweenCurFinYear(model.getProvisionalAssesmentMstDto().getAssNo(),
                		billsToKnockOff.get(billsToKnockOff.size() -1).getBmBilldt(), billsToKnockOff.get(billsToKnockOff.size() -1).getBmTodt(), UserSession.getCurrent().getOrganisation().getOrgid());
		double bmTotalOutStandingAmt = totalBal.doubleValue() - rebateAmount;
		if(CollectionUtils.isEmpty(currentYearBillPaidList)) {
			if(model.getManualRecptArrearPaidAmnt() >= (bmTotalOutStandingAmt)) {
				rebateAppl = true;
			}
		}else {
			List<TbServiceReceiptMasEntity> demandReceipts = currentYearBillPaidList.stream().filter(receiptMas -> StringUtils.equals(receiptMas.getReceiptTypeFlag(), "R")).collect(Collectors.toList());
			if(demandReceipts.size() <= 1 && rebateAmount >= totalBal.doubleValue()) {
				rebateAppl = true;
				rebateCaseType = "CR";
			}
		}
		
		Long lastBmNo = billsToKnockOff.get(billsToKnockOff.size() -1).getBmIdno();
		TbServiceReceiptMasEntity createAndSetRebate = null;
		TbServiceReceiptMasEntity receiptMasEntityRebate = null;
		if(rebateAppl && StringUtils.equals(rebateCaseType, "NR") && MapUtils.isNotEmpty(taxWiseRebate)) {
			 createAndSetRebate = createAndSetRebate(model, manualReeiptDate, billsToKnockOff, manualYear, amount,
					taxWiseRebate, rebateTaxId, deptId, rebateAmount, lastBmNo);
		}
		
		
		
		
			final Map<Long, Double> details = new HashMap<>(0);
			final Map<Long, Long> billDetails = new HashMap<>(0);
			final List<Map<Long, List<Double>>> rebateDetails = new ArrayList<>();
			List<BillReceiptPostingDTO> result = new ArrayList<>();
			
				
			if (Utility.isEnvPrefixAvailable(UserSession.getCurrent().getOrganisation(), "KIF")) {

				List<BillReceiptPostingDTO> interestResult = new ArrayList<>();
				interestResult = billMasterCommonService.updateBillDataForInterest(billsToKnockOff,
						model.getManualRecptArrearPaidAmnt(), details, billDetails,
						UserSession.getCurrent().getOrganisation(), rebateDetails, model.getManualReeiptDate());
				result = billMasterCommonService.updateBillDataWithoutInterest(billsToKnockOff,
						billsToKnockOff.get(billsToKnockOff.size() - 1).getExcessAmount(), details, billDetails,
						UserSession.getCurrent().getOrganisation(), rebateDetails, model.getManualReeiptDate());
				result.addAll(interestResult);
				billMasterCommonService.updateArrearInCurrentBills(billsToKnockOff);
			}
			if(billsToKnockOff.get(billsToKnockOff.size() - 1).getExcessAmount() > 0) {
				excessAmount = excessAmount + billsToKnockOff.get(billsToKnockOff.size() - 1).getExcessAmount();
			}
			
			CommonChallanDTO offlineDto = createDtoForReceiptInsertion(null, deptId, details, billDetails,
					model.getBillPayDto(), amount, manualReeiptDate, model.getAssesmentManualNo(),model.getProvisionalAssesmentMstDto().getAssNo(),model.getOfflineDTO(),null);
			offlineDto.setFaYearId(String.valueOf(manualYear));

			if(rebateAppl && StringUtils.equals(rebateCaseType, "CR")) {
				offlineDto.setPaymentCategory("RB");
			}
			TbServiceReceiptMasEntity rcptMastEntity = receiptEntryService.insertInReceiptMaster(offlineDto, result);
			ApplicationContextProvider.getApplicationContext().getBean(IChallanService.class)
					.setReceiptDtoAndSaveDuplicateService(rcptMastEntity, offlineDto);

			
			if(rebateAppl && StringUtils.equals(rebateCaseType, "NR") && MapUtils.isNotEmpty(taxWiseRebate) && createAndSetRebate != null) {
				createAndSetRebate.setCoddwzId1(rcptMastEntity.getCoddwzId1());
				createAndSetRebate.setCoddwzId2(rcptMastEntity.getCoddwzId2());
				createAndSetRebate.setCoddwzId3(rcptMastEntity.getCoddwzId3());
				createAndSetRebate.setCoddwzId4(rcptMastEntity.getCoddwzId4());
				createAndSetRebate.setCoddwzId5(rcptMastEntity.getCoddwzId5());
				receiptRepository.save(createAndSetRebate);
			}
			
			billMasData.forEach(bill -> {
				if (bill.getBmYear() > manualYear) {
					TbBillMas lastBill = billsToKnockOff.get(billsToKnockOff.size() - 1);
					bill.getTbWtBillDet().forEach(billDet -> {
						lastBill.getTbWtBillDet().forEach(lastBillDet -> {
							if (billDet.getTaxId().equals(lastBillDet.getTaxId())) {
								billDet.setBdPrvArramt(lastBillDet.getBdCurBalTaxamt() + lastBillDet.getBdPrvBalArramt());
								billDet.setBdPrvBalArramt(
										lastBillDet.getBdCurBalTaxamt() + lastBillDet.getBdPrvBalArramt());
							}
						});
					});

					billsToKnockOff.add(bill);
				}
			});

			billsToKnockOff.forEach(intBill -> {
				if (intBill.getBmYear() > manualYear) {
					intBill.getTbWtBillDet().forEach(billDet -> {
						final String taxCode = CommonMasterUtility
								.getHierarchicalLookUp(billDet.getTaxCategory(), UserSession.getCurrent().getOrganisation())
								.getLookUpCode();

						if (taxCode.equals(PrefixConstants.TAX_CATEGORY.INTERST)) {
							TbTaxMas intersetTaxMas = tbTaxMasService.findById(billDet.getTaxId(),
									UserSession.getCurrent().getOrganisation().getOrgid());

							List<TbBillDet> interestDependentTax = intBill.getTbWtBillDet().stream()
									.filter(detail -> detail.getTaxId().equals(intersetTaxMas.getParentCode()))
									.collect(Collectors.toList());
							if (CollectionUtils.isNotEmpty(interestDependentTax)) {
								double balanceAmnt = interestDependentTax.get(0).getBdPrvArramt();
								double interest = Math.round(balanceAmnt * 0.12);
								billDet.setBdCurTaxamt(interest);
								billDet.setBdCurBalTaxamt(interest);
							}
						}
					});
				}
			});
			if(excessAmount > 0) {
				if (Utility.isEnvPrefixAvailable(UserSession.getCurrent().getOrganisation(), "KIF")) {

					List<BillReceiptPostingDTO> interestResult = new ArrayList<>();
					interestResult = billMasterCommonService.updateBillDataForInterest(billsToKnockOff,
							excessAmount, details, billDetails,
							UserSession.getCurrent().getOrganisation(), rebateDetails, model.getManualReeiptDate());
					result = billMasterCommonService.updateBillDataWithoutInterest(billsToKnockOff,
							billsToKnockOff.get(billsToKnockOff.size() - 1).getExcessAmount(), details, billDetails,
							UserSession.getCurrent().getOrganisation(), rebateDetails, model.getManualReeiptDate());
					result.addAll(interestResult);
					billMasterCommonService.updateArrearInCurrentBills(billsToKnockOff);
				}
				
				CommonChallanDTO offlineDto1 = createDtoForReceiptInsertion(null, deptId, details, billDetails,
						model.getBillPayDto(), excessAmount, billsToKnockOff.get(billsToKnockOff.size() -1).getBmBilldt(), model.getAssesmentManualNo(),model.getProvisionalAssesmentMstDto().getAssNo(),model.getOfflineDTO(),lastReceiptDate);
				offlineDto.setFaYearId(String.valueOf(manualYear));

				TbServiceReceiptMasEntity rcptMastEntity1 = receiptEntryService.insertInReceiptMaster(offlineDto1, result);
				ApplicationContextProvider.getApplicationContext().getBean(IChallanService.class)
						.setReceiptDtoAndSaveDuplicateService(rcptMastEntity1, offlineDto1);
			}
			
			if(billsToKnockOff.get(billsToKnockOff.size() - 1).getExcessAmount() > 0) {
		        
		         ApplicationContextProvider.getApplicationContext().getBean(AsExecssAmtService.class)
		 		.addEntryInExcessAmt(UserSession.getCurrent().getOrganisation().getOrgid(),
		 				model.getProvisionalAssesmentMstDto().getAssNo(),
		 				billsToKnockOff.get(billsToKnockOff.size() - 1).getExcessAmount(), null,
		 				UserSession.getCurrent().getEmployee().getEmpId());
	       }
			
			billMasterCommonService.updateArrearInCurrentBills(billsToKnockOff);
			propertyMainBillService.saveAndUpdateMainBill(billsToKnockOff,
					UserSession.getCurrent().getOrganisation().getOrgid(),
					UserSession.getCurrent().getEmployee().getEmpId(), MainetConstants.Property.AuthStatus.AUTH,
					model.getClientIpAddress());
			if(CollectionUtils.isNotEmpty(inactiveReceiptList)) {
				inactiveReceiptList.forEach(receiptMaster ->{
					receiptMaster.setReceiptDelRemark("Inactive at the time of previous year manual posting");
					iCommonReversalEntry.updateReceipt(receiptMaster, organisation.getOrgid(), UserSession.getCurrent().getEmployee().getEmpId());
					
				});
			}
			
			}
			if(CollectionUtils.isNotEmpty(docs)) {
				 RequestDTO dto = new RequestDTO();
		         dto.setDeptId(model.getDeptId());
		         dto.setServiceId(model.getProvisionalAssesmentMstDto().getSmServiceId());
		         dto.setReferenceId(model.getProvisionalAssesmentMstDto().getAssNo());
		         dto.setOrgId(UserSession.getCurrent().getOrganisation().getOrgid());
		         dto.setUserId(UserSession.getCurrent().getEmployee().getEmpId());
		         dto.setLangId(Long.valueOf(UserSession.getCurrent().getLanguageId()));
		         fileUpload.doFileUpload(docs, dto);
			}
			object.put("successFlag", MainetConstants.FlagY);
			return object;
		
		
		
		
		
		
	}

	private TbServiceReceiptMasEntity createAndSetRebate(SelfAssesmentNewModel model, Date manualReeiptDate,
			List<TbBillMas> billsToKnockOff, Long manualYear, double amount, Map<Long, Double> taxWiseRebate,
			Map<Long, Long> rebateTaxId, Long deptId, double rebateAmount, Long lastBmNo) {
		TbServiceReceiptMasEntity receiptMasEntityRebate;
		
		TbBillMas lastBill = billsToKnockOff.get(billsToKnockOff.size() -1);
		final List<Map<Long, List<Double>>> rebateDetails = new ArrayList<>();
		billsToKnockOff.forEach(bills ->{
			if(bills.getBmIdno() == lastBmNo) {
				
				bills.getTbWtBillDet().forEach(det ->{
					List<Double> taxvalueIdList = new ArrayList<Double>();
					Map<Long, List<Double>> rebateDet = new HashMap<>();
					Double rebateAmount1 = taxWiseRebate.get(det.getTaxId());
					if(rebateAmount1 != null) {
						taxvalueIdList.add(taxWiseRebate.get(det.getTaxId()));
						taxvalueIdList.add(Double.valueOf(rebateTaxId.get(det.getTaxId())));
						rebateDet.put(det.getBdBilldetid(), taxvalueIdList);
						det.setBdCurBalTaxamt(det.getBdCurBalTaxamt() - taxWiseRebate.get(det.getTaxId()));
						rebateDetails.add(rebateDet);
					}
					
				});
			}
		});
		
		final Map<Long, Double> details = new HashMap<>(0);
		final Map<Long, Long> billDetails = new HashMap<>(0);
		
		receiptMasEntityRebate = new TbServiceReceiptMasEntity();
		receiptMasEntityRebate.setRmDate(manualReeiptDate);
		Map<Long, Long> taxHeadId = new HashMap<>();
		CommonChallanDTO requestDTO = createDtoForReceiptInsertion(null, deptId, details, billDetails,
				model.getBillPayDto(), amount, manualReeiptDate, model.getAssesmentManualNo(),model.getProvisionalAssesmentMstDto().getAssNo(),model.getOfflineDTO(),null);
		requestDTO.setFaYearId(String.valueOf(manualYear));
		BillReceiptPostingDTO billDef = new BillReceiptPostingDTO();
		billDef.setBmIdNo(String.valueOf(lastBill.getBmIdno()));
		billDef.setRebateDetails(rebateDetails);
		billDef.setBillMasId(lastBill.getBmIdno());
		final Long currDemandId = CommonMasterUtility.getValueFromPrefixLookUp("CYR", "DMC", UserSession.getCurrent().getOrganisation()).getLookUpId();
		TbServiceReceiptMasEntity saveRebateReceipt = receiptEntryService.saveRebateReceipt(requestDTO, receiptMasEntityRebate, UserSession.getCurrent().getOrganisation(), taxHeadId, billDef, currDemandId, rebateAmount);
		return saveRebateReceipt;
	}

	private CommonChallanDTO createDtoForReceiptInsertion(TbServiceReceiptMasEntity receiptDetail, Long deptId,
			final Map<Long, Double> details, final Map<Long, Long> billDetails, BillPaymentDetailDto billPayDto,
			Double Amnt, Date manualRecDate, String manualReceiptNo,String propertyNo,CommonChallanDTO offline,Date lastReceiptDate) {

		LOGGER.info("Begin--> " + this.getClass().getSimpleName() + " setChallanDToandSaveChallanData() method");
		final UserSession session = UserSession.getCurrent();

		LookUp printBillPaymentDescOnBill = null;
		try {
			printBillPaymentDescOnBill = CommonMasterUtility.getValueFromPrefixLookUp("PPB", "ENV",
					UserSession.getCurrent().getOrganisation());
		} catch (Exception e) {
			LOGGER.error("No prefix found for ENV(PPB)");
		}
		LookUp printManualReceiptYear = null;
		try {
			printManualReceiptYear = CommonMasterUtility.getValueFromPrefixLookUp("PMY", "ENV",
					UserSession.getCurrent().getOrganisation());
		} catch (Exception e) {
			LOGGER.error("No prefix found for ENV(PMY)");
		}

		if (receiptDetail != null && receiptDetail.getRmAmount() != null) {
			offline.setAmountToPay(Double.toString(receiptDetail.getRmAmount().doubleValue()));
		} else if (Amnt != null) {
			offline.setAmountToPay(Double.toString(Amnt));
		}
		offline.setUserId(session.getEmployee().getEmpId());
		offline.setOrgId(session.getOrganisation().getOrgid());
		offline.setLangId(session.getLanguageId());
		offline.setLgIpMac(session.getEmployee().getEmppiservername());
		if ((details != null) && !details.isEmpty()) {
			offline.setFeeIds(details);
		}
		if ((billDetails != null) && !billDetails.isEmpty()) {
			offline.setBillDetIds(billDetails);
		}
		offline.setPropNoConnNoEstateNoL("Property No.");
		offline.setPropNoConnNoEstateNoV(propertyNo);
		offline.setFaYearId(UserSession.getCurrent().getFinYearId());
		if (receiptDetail != null) {
			Long manualYearId = ApplicationContextProvider.getApplicationContext().getBean(IFinancialYearService.class)
					.getFinanceYearId(receiptDetail.getRmDate());
			offline.setFaYearId(String.valueOf(manualYearId));
		} else if (manualRecDate != null) {
			Long manualYearId = ApplicationContextProvider.getApplicationContext().getBean(IFinancialYearService.class)
					.getFinanceYearId(manualRecDate);
			offline.setFaYearId(String.valueOf(manualYearId));
			if(lastReceiptDate != null) {
				Long lastManualYearId = ApplicationContextProvider.getApplicationContext().getBean(IFinancialYearService.class)
						.getFinanceYearId(lastReceiptDate);
				offline.setFaYearId(String.valueOf(lastManualYearId));
			}
		}
		LookUp billReceipt = CommonMasterUtility.getValueFromPrefixLookUp("R", "RV",
				UserSession.getCurrent().getOrganisation());

		offline.setPaymentCategory(billReceipt.getLookUpCode());
		offline.setFinYearStartDate(UserSession.getCurrent().getFinStartDate());
		offline.setFinYearEndDate(UserSession.getCurrent().getFinEndDate());
		// offline.setEmailId(billPayDto.get);
		offline.setApplicantName(billPayDto.getOwnerFullName());
		offline.setApplicantFullName(billPayDto.getOwnerFullName());
		offline.setPayeeName(billPayDto.getOwnerFullName());
		offline.setApplNo(billPayDto.getApplNo());
		offline.setApplicantAddress(billPayDto.getAddress());
		offline.setUniquePrimaryId(propertyNo);
		offline.setMobileNumber(billPayDto.getPrimaryOwnerMobNo());
		offline.setServiceId(billPayDto.getServiceId());
		if (printBillPaymentDescOnBill != null && org.apache.commons.lang.StringUtils.equals(MainetConstants.FlagY,
				printBillPaymentDescOnBill.getOtherField())) {
			try {
				final ServiceMaster service = serviceMaster.getServiceByShortName("PBP",
						UserSession.getCurrent().getOrganisation().getOrgid());
				if (service != null) {
					offline.setServiceId(service.getSmServiceId());
				}
			} catch (Exception exception) {
				LOGGER.error("No service available for service short code PBP");
			}
		}
		offline.setDeptId(billPayDto.getDeptId());
		offline.setDeptId(deptId);
		offline.setChallanServiceType(MainetConstants.CHALLAN_RECEIPT_TYPE.REVENUE_BASED);
		offline.setDocumentUploaded(false);
		offline.setEmpType(UserSession.getCurrent().getEmployee().getEmplType());
		offline.setPlotNo(billPayDto.getPlotNo());
		offline.setOfflinePaymentText(CommonMasterUtility
				.getNonHierarchicalLookUpObject(offline.getOflPaymentMode(), UserSession.getCurrent().getOrganisation())
				.getLookUpCode());
		offline.setManualReceiptNo(null);
		offline.setManualReeiptDate(new Date());
		if (receiptDetail != null) {
			offline.setManualReeiptDate(receiptDetail.getRmDate());
		} else if (manualRecDate != null) {
			offline.setManualReeiptDate(manualRecDate);
			if(lastReceiptDate != null) {
				offline.setManualReeiptDate(lastReceiptDate);
			}
		}
		if (org.apache.commons.lang.StringUtils.isNotBlank(manualReceiptNo)) {
			offline.setManualReceiptNo(manualReceiptNo);
		}
		offline.setUsageType(billPayDto.getUsageType1());
		if (billPayDto.getWard1() != null) {
			offline.getDwzDTO().setAreaDivision1(billPayDto.getWard1());
		}
		if (billPayDto.getWard2() != null) {
			offline.getDwzDTO().setAreaDivision2(billPayDto.getWard2());
		}
		if (billPayDto.getWard3() != null) {
			offline.getDwzDTO().setAreaDivision3(billPayDto.getWard3());
		}
		if (billPayDto.getWard4() != null) {
			offline.getDwzDTO().setAreaDivision4(billPayDto.getWard4());
		}
		if (billPayDto.getWard5() != null) {
			offline.getDwzDTO().setAreaDivision5(billPayDto.getWard5());
		}
		// offline.setBillDetails(billPayDto.getBillDetails());
		offline.setReferenceNo(billPayDto.getOldpropno());
		offline.setPdRv(billPayDto.getPdRv());
		if(StringUtils.isBlank(offline.getNarration())) {
			//offline.setNarration("Inserted through previous manual entry");
		}
			
		
		offline.setDeptCode(MainetConstants.Property.PROP_DEPT_SHORT_CODE);
		return offline;
	}
}
