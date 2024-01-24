package com.abm.mainet.property.ui.controller;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.stream.Collectors;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.collections.MapUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.abm.mainet.bill.service.BillMasterCommonService;
import com.abm.mainet.cfc.scrutiny.dto.ScrutinyLableValueDTO;
import com.abm.mainet.common.constant.MainetConstants;
import com.abm.mainet.common.domain.Department;
import com.abm.mainet.common.domain.Employee;
import com.abm.mainet.common.domain.FinancialYear;
import com.abm.mainet.common.domain.Organisation;
import com.abm.mainet.common.integration.dto.TbBillDet;
import com.abm.mainet.common.integration.dto.TbBillMas;
import com.abm.mainet.common.master.dto.TbTaxMas;
import com.abm.mainet.common.master.service.DepartmentService;
import com.abm.mainet.common.master.service.TbTaxMasService;
import com.abm.mainet.common.service.IFinancialYearService;
import com.abm.mainet.common.ui.controller.AbstractFormController;
import com.abm.mainet.common.utility.ApplicationSession;
import com.abm.mainet.common.utility.CommonMasterUtility;
import com.abm.mainet.common.utility.LookUp;
import com.abm.mainet.common.utility.UserSession;
import com.abm.mainet.common.utility.Utility;
import com.abm.mainet.property.dto.PropertyRoomDetailsDto;
import com.abm.mainet.property.dto.ProperySearchDto;
import com.abm.mainet.property.dto.ProvisionalAssesmentDetailDto;
import com.abm.mainet.property.dto.ProvisionalAssesmentMstDto;
import com.abm.mainet.property.service.IProvisionalAssesmentMstService;
import com.abm.mainet.property.service.IProvisionalBillService;
import com.abm.mainet.property.service.PropertyMainBillService;
import com.abm.mainet.property.ui.model.BillingMethodChangeModel;
import com.google.common.util.concurrent.AtomicDouble;

/**
 * @author Arun Shinde
 *
 */
@Controller
@RequestMapping("/BillingMethodAuthorization.html")
public class BillingMethodAuthorizationController extends AbstractFormController<BillingMethodChangeModel> {

	private static final Logger LOGGER = Logger.getLogger(BillingMethodAuthorizationController.class);

	@Resource
	private IProvisionalAssesmentMstService provisionalAssesmentMstService;

	@Autowired
	private DepartmentService departmentService;

	@Autowired
	private IFinancialYearService iFinancialYear;

	@Autowired
	private TbTaxMasService tbTaxMasService;

	@Autowired
	private BillMasterCommonService billMasterCommonService;

	@Autowired
	private IProvisionalBillService provisionalBillService;

	@Autowired
	private IProvisionalAssesmentMstService iProvisionalAssesmentMstService;

	@Autowired
	private PropertyMainBillService propertyMainBillService;

	@RequestMapping(params = "showDetails", method = RequestMethod.POST)
	public ModelAndView defaultLoad(@RequestParam("applId") final long applicationId,
			@RequestParam("serviceId") final long serviceId, final HttpServletRequest httpServletRequest)
			throws Exception {
		sessionCleanup(httpServletRequest);
		Organisation org = UserSession.getCurrent().getOrganisation();
		BillingMethodChangeModel model = this.getModel();
		final ScrutinyLableValueDTO lableDTO = model.getLableValueDTO();
		final Long lableId = Long.valueOf(httpServletRequest.getParameter("labelId"));
		final String lableValue = httpServletRequest.getParameter("labelVal");
		final Long level = Long.valueOf(httpServletRequest.getParameter("level"));
		lableDTO.setApplicationId(applicationId);
		lableDTO.setLableId(lableId);
		lableDTO.setLableValue(lableValue);
		lableDTO.setLevel(level);
		getModel().setLableValueDTO(lableDTO);
		getModel().setServiceId(serviceId);
		model.getWorkflowActionDto().setApplicationId(applicationId);
		model.getWorkflowActionDto().setServiceId(serviceId);
		List<ProvisionalAssesmentMstDto> provAssDtoList = provisionalAssesmentMstService
				.getAssesmentMstDtoListByAppId(applicationId, org.getOrgid());
		ProvisionalAssesmentMstDto assMst = provAssDtoList.get(provAssDtoList.size() - 1);
		if (assMst != null) {
			LookUp ownerType = CommonMasterUtility.getNonHierarchicalLookUpObject(assMst.getAssOwnerType(), org);
			model.setOwnershipPrefix(ownerType.getLookUpCode());
			final List<FinancialYear> finYearList = iFinancialYear.getAllFinincialYear();
			String financialYear = null;
			for (final FinancialYear finYearTemp : finYearList) {
				financialYear = Utility.getFinancialYearFromDate(finYearTemp.getFaFromDate());
				getModel().getFinancialYearMap().put(finYearTemp.getFaYear(), financialYear);
			}
			model.setProvisionalAssesmentMstDto(assMst);
			model.setDropDownValues(org);
		}
		return new ModelAndView("BillingMethodAuthorization", MainetConstants.FORM_NAME, this.getModel());
	}

	// Save data in provisional tables
	@RequestMapping(params = "saveDataInProviosionals", method = RequestMethod.POST)
	public @ResponseBody boolean saveDataInProviosionals(HttpServletRequest httpServletRequest,
			HttpServletResponse httpServletResponse) {
		LOGGER.info("saveDataInProviosionals method starts");
		getModel().bind(httpServletRequest);
		try {
			Organisation org = UserSession.getCurrent().getOrganisation();
			Employee emp = UserSession.getCurrent().getEmployee();
			ProvisionalAssesmentMstDto provAssessDto = getModel().getProvisionalAssesmentMstDto();
			List<TbBillMas> newBillMasList = new ArrayList<>();
			List<TbBillMas> tempBillMasList;
			Map<String, List<TbBillMas>> flatWiseBillMap = getModel().getFlatWiseBillmap();
			
			if (!flatWiseBillMap.isEmpty()) {				
				for (Entry<String, List<TbBillMas>> map : flatWiseBillMap.entrySet()) {
					tempBillMasList = new ArrayList<>();
					tempBillMasList = billMasterCommonService.generateBillForDataEntry(map.getValue(), org);
					tempBillMasList.forEach(tempBill -> {
						tempBill.setPropNo(
								provAssessDto.getAssNo() + MainetConstants.operator.UNDER_SCORE + map.getKey());
					});
					newBillMasList.addAll(tempBillMasList);
				}
				provisionalBillService.saveAndUpdateProvBillForBillChange(newBillMasList, org.getOrgid(),
						emp.getEmpId(), emp.getEmppiservername());
				provisionalAssesmentMstService.updateBillMethodChangeFlag(provAssessDto.getAssNo(),
						MainetConstants.FlagY, org.getOrgid());
			}
		} catch (Exception e) {
			LOGGER.info("Exception occurred while saving data into proviosional tables" + e.getMessage());
			return false;
		}
		LOGGER.info("saveDataInProviosionals method ends");
		return true;
	}

	@RequestMapping(params = "addArrears", method = RequestMethod.POST)
	public ModelAndView setFinYearWiseFlatArrears(HttpServletRequest httpServletRequest,
			HttpServletResponse httpServletResponse) throws Exception {
		LOGGER.info("Begin " + this.getClass().getSimpleName() + " setFinYearWiseFlatArrears() method");
		getModel().bind(httpServletRequest);
		Organisation org = UserSession.getCurrent().getOrganisation();
		BillingMethodChangeModel model = this.getModel();
		ProvisionalAssesmentMstDto provDto = model.getProvisionalAssesmentMstDto();
		String flatNo = provDto.getProvisionalAssesmentDetailDtoList().get(model.getFlatNoOfRow().intValue())
				.getFlatNo();
		getModel().getProvisionalAssesmentMstDto().setFlatNo(flatNo);
		ProperySearchDto searchDto = model.getSearchDto();
		searchDto.setOrgId(org.getOrgid());
		searchDto.setProertyNo(provDto.getAssNo());
		Department dept = departmentService.getDepartment(MainetConstants.DEPT_SHORT_NAME.PROPERTY,
				MainetConstants.FlagA);
		searchDto.setDeptId(dept.getDpDeptid());
		List<FinancialYear> financialYearList = iFinancialYear.getAllFinincialYear();
		String financialYear = null;
		for (final FinancialYear finYearTemp : financialYearList) {
			financialYear = Utility.getFinancialYearFromDate(finYearTemp.getFaFromDate());
			getModel().getFinancialYearMapForTax().put(finYearTemp.getFaYear(), financialYear);
		}
		List<Long> finYearList = financialYearList.stream().map(FinancialYear::getFaYear).collect(Collectors.toList());
		this.getModel().setFinYearList(finYearList);

		Map<Long, String> taxMap = getTaxMastersList(dept.getDpDeptid(), org);
		this.getModel().setTaxMasterMap(taxMap);

		// To get bill after it is saved and it is moved in provisional table
		List<TbBillMas> provTempBillMasList = provisionalBillService.getProvisionalBillMasterList(provDto, flatNo,
				taxMap);
		if (CollectionUtils.isNotEmpty(provTempBillMasList)) {
			this.getModel().setBillMasList(provTempBillMasList);
			this.getModel().setProvBillPresent(MainetConstants.FlagY);
		} else {
			LOGGER.info("Trying to get Bill details");
			if (CollectionUtils.isEmpty(this.getModel().getBillMasList())) {
				List<TbBillMas> oldBillMasList = propertyMainBillService.getBillsForBillMethodChange(searchDto);
				this.getModel().setBillMasList(oldBillMasList);
				this.getModel().setOldBillMasList(getBillMasterList(oldBillMasList));
			} else {
				Map<String, List<TbBillMas>> flatWiseBillMap = getModel().getFlatWiseBillmap();
				if (flatWiseBillMap.containsKey(flatNo)) {
					this.getModel().setBillMasList(flatWiseBillMap.get(flatNo));
				} else {
					// Created tax wise map to later adjust amount to be transfered in latest
					// records
					Map<String, BigDecimal> taxWiseAmtMap = new LinkedHashMap<>();				
					for (Entry<String, List<TbBillMas>> map : flatWiseBillMap.entrySet()) {
						for (TbBillMas billMas : map.getValue()) {
							for (TbBillDet det : billMas.getTbWtBillDet()) {
								if (det.getBdCsmp() != null) {
									taxWiseAmtMap.put(
											billMas.getBmYear() + MainetConstants.operator.FORWARD_SLACE
													+ det.getTaxId(),
											taxWiseAmtMap.get(billMas.getBmYear()
													+ MainetConstants.operator.FORWARD_SLACE + det.getTaxId()) != null
															? (taxWiseAmtMap.get(billMas.getBmYear()
																	+ MainetConstants.operator.FORWARD_SLACE
																	+ det.getTaxId()).add(det.getBdCsmp()))
															: det.getBdCsmp());
								}
							}
						}
					}
					
					for (TbBillMas bill : this.getModel().getBillMasList()) {
						double totalBalanceAmount = 0;
						double totalAmtToTransfer = 0;
						for (TbBillDet detail : bill.getTbWtBillDet()) {
							BigDecimal amtToTransfer = taxWiseAmtMap
									.get(bill.getBmYear() + MainetConstants.operator.FORWARD_SLACE + detail.getTaxId());
							if (amtToTransfer != null) {
								detail.setBdBalAmtToTransfer(
										BigDecimal.valueOf(detail.getBdCurBalTaxamt()).subtract(amtToTransfer));
								totalAmtToTransfer += detail.getBdBalAmtToTransfer().doubleValue();
							}
							detail.setBdCsmp(null);
							totalBalanceAmount += detail.getBdCurBalTaxamt();
						}
						bill.setTotalBalanceAmount(totalBalanceAmount);
						bill.setTotalAmtToTransfer(totalAmtToTransfer);
					}
				}
			}
		}
		LOGGER.info("End " + this.getClass().getSimpleName() + " setFinYearWiseFlatArrears() method");
		return new ModelAndView("BillChangeArrarsForFlat", MainetConstants.FORM_NAME, this.getModel());
	}

	@RequestMapping(params = "saveAdjustedArrears", method = RequestMethod.POST)
	public ModelAndView saveAdjustedArrears(HttpServletRequest httpServletRequest) {
		LOGGER.info("Begin " + this.getClass().getSimpleName() + " saveAdjustedArrears() method");
		getModel().bind(httpServletRequest);
		ProvisionalAssesmentMstDto provDto = getModel().getProvisionalAssesmentMstDto();
		// Map is created to separate out flat wise bills
		Map<String, List<TbBillMas>> flatWiseBillmap = null;
		if (getModel().getFlatWiseBillmap().isEmpty()) {
			flatWiseBillmap = new LinkedHashMap<>();
		} else {
			flatWiseBillmap = getModel().getFlatWiseBillmap();
		}
		// To check if amount entered in Arrears should not be greater than balance
		// amount of that tax
		if (MapUtils.isNotEmpty(flatWiseBillmap)) {
			Map<String, BigDecimal> balTaxWiseAmtMap = new LinkedHashMap<>();
			Map<String, BigDecimal> origTaxWiseAmtMap = new LinkedHashMap<>();
			for (Entry<String, List<TbBillMas>> map : flatWiseBillmap.entrySet()) {
				for (TbBillMas billMas : map.getValue()) {
					for (TbBillDet det : billMas.getTbWtBillDet()) {
						if (det.getBdCsmp() != null) {
							balTaxWiseAmtMap.put(
									billMas.getBmYear() + MainetConstants.operator.FORWARD_SLACE + det.getTaxId(),
									balTaxWiseAmtMap.get(billMas.getBmYear() + MainetConstants.operator.FORWARD_SLACE
											+ det.getTaxId()) != null
													? (balTaxWiseAmtMap.get(billMas.getBmYear()
															+ MainetConstants.operator.FORWARD_SLACE + det.getTaxId())
															.add(det.getBdCsmp()))
													: det.getBdCsmp());
						}
						if (det.getBdCurBalTaxamt() != 0) {
							origTaxWiseAmtMap.put(
									billMas.getBmYear() + MainetConstants.operator.FORWARD_SLACE + det.getTaxId(),
									BigDecimal.valueOf(det.getBdCurBalTaxamt()));
						}
					}
				}
			}

			for (Map.Entry<String, BigDecimal> map : balTaxWiseAmtMap.entrySet()) {
				if (map.getValue().compareTo(origTaxWiseAmtMap.get(map.getKey())) == 1) {
					getModel().addValidationError(
							ApplicationSession.getInstance().getMessage("property.validateArrearsAmount"));
					ModelAndView mv = new ModelAndView("BillChangeArrarsForFlat", MainetConstants.FORM_NAME,
							this.getModel());
					mv.addObject(BindingResult.MODEL_KEY_PREFIX + MainetConstants.FORM_NAME,
							getModel().getBindingResult());
					return mv;
				}
			}
		}

		List<TbBillMas> billMasList = getModel().getBillMasList();
		List<TbBillMas> firstbillMasList = new ArrayList<>();
		TbBillMas tbBill;
		TbBillDet tbBillDet;
		List<TbBillDet> tbWtBillDet;
		for (TbBillMas tbBillMas : billMasList) {
			tbBill = new TbBillMas();
			tbBillMas.setFlatNo(provDto.getFlatNo());
			BeanUtils.copyProperties(tbBillMas, tbBill, "bmIdno", "updatedBy", "updatedDate", "lgIpMacUpd");
			tbBill.setPropNo(provDto.getAssNo());
			tbWtBillDet = new ArrayList<>(0);
			for (TbBillDet det : tbBillMas.getTbWtBillDet()) {
				tbBillDet = new TbBillDet();
				BeanUtils.copyProperties(det, tbBillDet, "bdBilldetid", "updatedBy", "updatedDate", "lgIpMacUpd");				
				tbWtBillDet.add(tbBillDet);
			}
			tbBill.setTbWtBillDet(tbWtBillDet);
			firstbillMasList.add(tbBill);
		}
		flatWiseBillmap.put(provDto.getFlatNo(), firstbillMasList);
		getModel().setFlatWiseBillmap(flatWiseBillmap);
		LOGGER.info("End " + this.getClass().getSimpleName() + " saveAdjustedArrears() method");

		return new ModelAndView("BillingMethodAuthorizationValidn", MainetConstants.FORM_NAME, this.getModel());
	}

	@RequestMapping(params = "confirmToProceed", method = RequestMethod.POST)
	public ModelAndView confirmToProceed(HttpServletRequest httpServletRequest,
			HttpServletResponse httpServletResponse) {
		LOGGER.info("Begin " + this.getClass().getSimpleName() + " confirmToProceed() method");
		getModel().bind(httpServletRequest);
		long orgId = UserSession.getCurrent().getOrganisation().getOrgid();
		Map<Long, Double> oldFinAndAmtMap = new LinkedHashMap<>();
		Map<Long, Double> newFinAndAmtMap = new LinkedHashMap<>();
		List<TbBillMas> oldBillMasList = this.getModel().getOldBillMasList();
		oldBillMasList.forEach(oldBill -> {
			oldFinAndAmtMap.put(oldBill.getBmYear(), oldBill.getBmTotalOutstanding());
		});
		List<TbBillMas> billMasList = new ArrayList<>(0);
		TbBillMas b;
		TbBillDet det;
		List<TbBillDet> tbBillDet;
		Map<String, List<TbBillMas>> flatWiseBillMap = getModel().getFlatWiseBillmap();
		SimpleDateFormat formatter = new SimpleDateFormat(MainetConstants.DATE_FRMAT);
		for (Entry<String, List<TbBillMas>> map : flatWiseBillMap.entrySet()) {
			Boolean flatHide = false;
			for (TbBillMas bill : map.getValue()) {
				b = new TbBillMas();
				BeanUtils.copyProperties(bill, b);
				if (flatHide) {
					b.setFlatNo(MainetConstants.BLANK);
				}
				if (b.getBmYear() != null) {
					FinancialYear financYear = iFinancialYear.getFinincialYearsById(b.getBmYear(), orgId);
					b.setBillDistrDateString(String.valueOf(Utility.getYearByDate(financYear.getFaFromDate())));
				}
				if (b.getBmBilldt() != null) {
					b.setBmBilldtString(formatter.format(b.getBmBilldt()));
				}
				b.setBmCcnOwner(new SimpleDateFormat("dd/MM/yyyy").format(b.getBmFromdt()) + "  -  "
						+ new SimpleDateFormat("dd/MM/yyyy").format(b.getBmTodt()));

				tbBillDet = new ArrayList<>(0);
				AtomicDouble bmTotalOutstanding = new AtomicDouble();
				for (TbBillDet billdet : bill.getTbWtBillDet()) {
					det = new TbBillDet();
					BeanUtils.copyProperties(billdet, det);
					if (billdet.getBdCsmp() == null) {
						det.setBdCsmp(BigDecimal.ZERO);
					}	
					tbBillDet.add(det);
					if (det.getBdCsmp() != null) {
						bmTotalOutstanding.addAndGet(det.getBdCsmp().doubleValue());
					}													 
				}
				b.setTbWtBillDet(tbBillDet);
				b.setBmTotalOutstanding(bmTotalOutstanding.doubleValue());
				billMasList.add(b);
				flatHide = true;
				if (MapUtils.isNotEmpty(newFinAndAmtMap) && newFinAndAmtMap.get(bill.getBmYear()) != null) {
					newFinAndAmtMap.put(bill.getBmYear(),
							newFinAndAmtMap.get(bill.getBmYear()) + bmTotalOutstanding.doubleValue());
				} else {
					newFinAndAmtMap.put(bill.getBmYear(), bmTotalOutstanding.doubleValue());
				}
			}
		}
		// To check amount after adjusting arrears is matching or not
		ModelAndView modelview = new ModelAndView("BillingMethodAuthorizationValidn", MainetConstants.FORM_NAME,
				this.getModel());
		modelview.addObject(BindingResult.MODEL_KEY_PREFIX + MainetConstants.FORM_NAME, getModel().getBindingResult());
		if (MapUtils.isNotEmpty(oldFinAndAmtMap) && MapUtils.isNotEmpty(newFinAndAmtMap)) {
			for (Map.Entry<Long, Double> oldFinNdAmtMap : oldFinAndAmtMap.entrySet()) {
				if (!oldFinNdAmtMap.getValue().equals(newFinAndAmtMap.get(oldFinNdAmtMap.getKey()))) {
					getModel().addValidationError(
							ApplicationSession.getInstance().getMessage("property.amountNotMatching"));
					return modelview;
				}
			}
		}
		getModel().setNewBillMasList(billMasList);
		LOGGER.info("End " + this.getClass().getSimpleName() + " confirmToProceed() method");
		return new ModelAndView("BillingMethodChangeSummary", MainetConstants.FORM_NAME, this.getModel());
	}

	private Map<Long, String> getTaxMastersList(Long dpDeptid, Organisation org) {
		Map<Long, String> taxMap = new LinkedHashMap<>(0);
		final LookUp chargeApplicableAt = CommonMasterUtility.getValueFromPrefixLookUp(
				MainetConstants.Property.propPref.BILL, MainetConstants.Property.propPref.CAA, org);
		final LookUp chargeApplicableAtBillRec = CommonMasterUtility.getValueFromPrefixLookUp(
				MainetConstants.Property.propPref.BILL_RECEIPT, MainetConstants.Property.propPref.CAA, org);
		List<TbTaxMas> taxes = tbTaxMasService.findAllTaxesForBillGeneration(org.getOrgid(), dpDeptid,
				chargeApplicableAt.getLookUpId(), null);
		List<TbTaxMas> taxesBillRece = tbTaxMasService.findAllTaxesForBillGeneration(org.getOrgid(), dpDeptid,
				chargeApplicableAtBillRec.getLookUpId(), null);
		List<TbTaxMas> notActiveTaxes = tbTaxMasService.findAllNotActiveTaxesForBillGeneration(org.getOrgid(), dpDeptid,
				chargeApplicableAt.getLookUpId(), null);
		if (taxes != null && !taxes.isEmpty()) {
			taxes.forEach(t -> {
				taxMap.put(t.getTaxId(), t.getTaxDesc());
			});
		}
		if ((notActiveTaxes != null && !notActiveTaxes.isEmpty())) {
			notActiveTaxes.forEach(t -> {
				taxMap.put(t.getTaxId(), t.getTaxDesc());
			});
		}
		if (taxesBillRece != null && !taxesBillRece.isEmpty()) {
			taxesBillRece.forEach(t -> {
				taxMap.put(t.getTaxId(), t.getTaxDesc());
			});
		}
		return taxMap;
	}

	private List<TbBillMas> getBillMasterList(List<TbBillMas> originalbillMasList) {
		List<TbBillMas> updatedBillMasList = new ArrayList<>(0);
		TbBillMas tbBill;
		TbBillDet tbBillDet;
		List<TbBillDet> tbWtBillDet;
		for (TbBillMas tbBillMas : originalbillMasList) {
			double bmTotalDemand = 0;
			double bmTotalBalance = 0;
			tbBill = new TbBillMas();
			BeanUtils.copyProperties(tbBillMas, tbBill);
			tbWtBillDet = new ArrayList<>(0);
			for (TbBillDet det : tbBillMas.getTbWtBillDet()) {
				tbBillDet = new TbBillDet();
				BeanUtils.copyProperties(det, tbBillDet);
				tbBillDet.setBmIdno(tbBillMas.getBmIdno());// was not getting set automatically
				tbWtBillDet.add(tbBillDet);
				bmTotalDemand += det.getBdCurTaxamt();
				bmTotalBalance += det.getBdCurBalTaxamt();
			}
			tbBill.setTbWtBillDet(tbWtBillDet);
			tbBill.setBmTotalAmount(bmTotalDemand);
			tbBill.setBmTotalOutstanding(bmTotalBalance);
			updatedBillMasList.add(tbBill);
		}
		return updatedBillMasList;
	}

	@RequestMapping(params = "backToEditForm", method = RequestMethod.POST)
	public ModelAndView backToSearch(HttpServletRequest httpServletRequest) {
		getModel().bind(httpServletRequest);
		return new ModelAndView("BillingMethodAuthorizationValidn", MainetConstants.FORM_NAME, this.getModel());
	}

	@RequestMapping(params = "editPropertyDetail", method = RequestMethod.POST)
	public ModelAndView editPropertyDetail(@RequestParam("applId") final long applicationId,
			@RequestParam("serviceId") final long serviceId, final HttpServletRequest httpServletRequest) {
		getModel().bind(httpServletRequest);
		Organisation org = UserSession.getCurrent().getOrganisation();
		BillingMethodChangeModel model = this.getModel();
		Long applId = Long.valueOf(httpServletRequest.getParameter("applId"));
		final ScrutinyLableValueDTO lableDTO = model.getLableValueDTO();
		final Long lableId = Long.valueOf(httpServletRequest.getParameter("labelId"));
		final String lableValue = httpServletRequest.getParameter("labelVal");
		final Long level = Long.valueOf(httpServletRequest.getParameter("level"));
		lableDTO.setApplicationId(applicationId);
		lableDTO.setLableId(lableId);
		lableDTO.setLableValue(lableValue);
		lableDTO.setLevel(level);
		getModel().setLableValueDTO(lableDTO);
		getModel().setServiceId(serviceId);
		List<ProvisionalAssesmentMstDto> provAssDtoList = provisionalAssesmentMstService
				.getAssesmentMstDtoListByAppId(applId, org.getOrgid());
		ProvisionalAssesmentMstDto provisionalAssesmentMstDto = provAssDtoList.get(provAssDtoList.size() - 1);
		this.getModel().setProvisionalAssesmentMstDto(provisionalAssesmentMstDto);
		// To compare it later if new flat is added.In that case build up area should
		// remain same.
		Map<Long, Double> buildAreaAndUsageMap = new LinkedHashMap<>();
		provisionalAssesmentMstDto.getProvisionalAssesmentDetailDtoList().forEach(detail -> {
			if (buildAreaAndUsageMap.containsKey(detail.getAssdUsagetype1())) {
				buildAreaAndUsageMap.put(detail.getAssdUsagetype1(),
						buildAreaAndUsageMap.get(detail.getAssdUsagetype1()) + detail.getAssdBuildupArea());
			} else {
				buildAreaAndUsageMap.put(detail.getAssdUsagetype1(), detail.getAssdBuildupArea());
			}
		});
		getModel().setBuildAreaAndUsageMap(buildAreaAndUsageMap);
		getModel().setNoOfDetailRows(
				Long.valueOf(provisionalAssesmentMstDto.getProvisionalAssesmentDetailDtoList().size() - 1));

		final List<FinancialYear> finYearList = iFinancialYear.getAllFinincialYear();
		String financialYear = null;
		for (final FinancialYear finYearTemp : finYearList) {
			try {
				financialYear = Utility.getFinancialYearFromDate(finYearTemp.getFaFromDate());
				getModel().getFinancialYearMap().put(finYearTemp.getFaYear(), financialYear);
			} catch (Exception e) {
				LOGGER.error("Exception occurred while fetching financial year " + e.getMessage());
			}
		}
		return new ModelAndView("BillingMethodAddFlats", MainetConstants.FORM_NAME, this.getModel());
	}

	@RequestMapping(params = "addRoomDetails", method = RequestMethod.POST)
	public ModelAndView addRoomDetails(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse) {
		getModel().bind(httpServletRequest);
		List<ProvisionalAssesmentDetailDto> detailList = getModel().getProvisionalAssesmentMstDto()
				.getProvisionalAssesmentDetailDtoList();
		if (CollectionUtils.isNotEmpty(detailList)) {
			httpServletRequest.setAttribute("carpetArea",
					detailList.get(Integer.parseInt(getModel().getCountOfRow())).getCarpetArea());
		}
		return new ModelAndView("BillMethodChangeAddRoom", MainetConstants.FORM_NAME, this.getModel());
	}

	@RequestMapping(params = "removeRoomDetails", method = RequestMethod.POST)
	public @ResponseBody Boolean removeRoomDetails(HttpServletRequest httpServletRequest,
			HttpServletResponse httpServletResponse, @RequestParam("countOfRow") Long countOfRow,
			@RequestParam("index") Long index) {
		this.getModel().bind(httpServletRequest);
		BillingMethodChangeModel model = this.getModel();
		List<ProvisionalAssesmentDetailDto> detailDtoList = model.getProvisionalAssesmentMstDto()
				.getProvisionalAssesmentDetailDtoList();
		if ((detailDtoList != null && !detailDtoList.isEmpty()) && detailDtoList.get(countOfRow.intValue()) != null
				&& detailDtoList.get(countOfRow.intValue()).getRoomDetailsDtoList().size() >= (index.intValue())
				&& detailDtoList.get(countOfRow.intValue()).getRoomDetailsDtoList().get(index.intValue() - 1) != null) {
			PropertyRoomDetailsDto roomDetDto = model.getProvisionalAssesmentMstDto()
					.getProvisionalAssesmentDetailDtoList().get(countOfRow.intValue()).getRoomDetailsDtoList()
					.get(index.intValue() - 1);
			model.getProvisionalAssesmentMstDto().getProvisionalAssesmentDetailDtoList().get(countOfRow.intValue())
					.getRoomDetailsDtoList().remove(roomDetDto);
		}
		return true;
	}

	@RequestMapping(params = "saveRoomDetails", method = RequestMethod.POST)
	public ModelAndView saveRoomDetails(HttpServletRequest httpServletRequest,
			HttpServletResponse httpServletResponse) {
		getModel().bind(httpServletRequest);
		return new ModelAndView("BillingMethodAddFlatsValidn", MainetConstants.FORM_NAME, this.getModel());
	}

	@RequestMapping(params = "deleteUnitTableRow", method = RequestMethod.POST)
	public @ResponseBody void deleteUnitTableRow(HttpServletRequest httpServletRequest,
			HttpServletResponse httpServletResponse, @RequestParam(value = "deletedRowCount") int deletedRowCount) {
		this.getModel().bind(httpServletRequest);
		BillingMethodChangeModel model = this.getModel();
		if (!model.getProvisionalAssesmentMstDto().getProvisionalAssesmentDetailDtoList().isEmpty() && model
				.getProvisionalAssesmentMstDto().getProvisionalAssesmentDetailDtoList().size() > deletedRowCount) {
			ProvisionalAssesmentDetailDto detDto = model.getProvisionalAssesmentMstDto()
					.getProvisionalAssesmentDetailDtoList().get(deletedRowCount);
			model.getProvisionalAssesmentMstDto().getProvisionalAssesmentDetailDtoList().remove(detDto);
		}
	}

	@RequestMapping(params = "updateFloorDetails", method = RequestMethod.POST)
	public @ResponseBody String updateFloorDetails(HttpServletRequest httpServletRequest,
			HttpServletResponse httpServletResponse) {
		LOGGER.info("updateFloorDetails method starts");
		getModel().bind(httpServletRequest);
		String msg = "";
		try {
			Organisation org = UserSession.getCurrent().getOrganisation();
			Employee emp = UserSession.getCurrent().getEmployee();
			List<Long> finYearList = new ArrayList<>();
			ProvisionalAssesmentMstDto provAssessDto = getModel().getProvisionalAssesmentMstDto();
			finYearList.add(provAssessDto.getFaYearId());
			// Buildup area should remain same after adding flat
			Map<Long, Double> newBuildAreaAndUsageMap = new LinkedHashMap<>();
			provAssessDto.getProvisionalAssesmentDetailDtoList().forEach(detail -> {
				if (newBuildAreaAndUsageMap.containsKey(detail.getAssdUsagetype1())) {
					newBuildAreaAndUsageMap.put(detail.getAssdUsagetype1(),
							newBuildAreaAndUsageMap.get(detail.getAssdUsagetype1()) + detail.getAssdBuildupArea());
				} else {
					newBuildAreaAndUsageMap.put(detail.getAssdUsagetype1(), detail.getAssdBuildupArea());
				}
			});
			Map<Long, Double> oldBuildAreaAndUsageMap = new LinkedHashMap<>();
			oldBuildAreaAndUsageMap.putAll(getModel().getBuildAreaAndUsageMap());
			for (Map.Entry<Long, Double> newMap : newBuildAreaAndUsageMap.entrySet()) {
				if (oldBuildAreaAndUsageMap.containsKey(newMap.getKey())) {
					if (!oldBuildAreaAndUsageMap.get(newMap.getKey()).equals(newMap.getValue())) {
						msg = ApplicationSession.getInstance().getMessage("property.invalidBuildUpArea");
						return msg;
					}
				}
			}
			// Add additional flat and save
			iProvisionalAssesmentMstService.saveProvisionalAssessment(provAssessDto, org.getOrgid(), emp.getEmpId(),
					finYearList, provAssessDto.getApmApplicationId());
		} catch (Exception e) {
			LOGGER.info("Exception occurred while saving data into proviosional tables" + e.getMessage());
			return ApplicationSession.getInstance().getMessage("property.saveWorkflowError");
		}
		LOGGER.info("updateFloorDetails method ends");
		return msg;
	}

}
