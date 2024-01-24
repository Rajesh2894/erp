package com.abm.mainet.water.ui.model;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import javax.annotation.Resource;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.abm.mainet.common.constant.MainetConstants;
import com.abm.mainet.common.constant.PrefixConstants;
import com.abm.mainet.common.domain.Organisation;
import com.abm.mainet.common.integration.acccount.domain.TbServiceReceiptMasEntity;
import com.abm.mainet.common.integration.dto.TbBillDet;
import com.abm.mainet.common.integration.dto.TbBillMas;
import com.abm.mainet.common.master.dto.TbTaxMas;
import com.abm.mainet.common.master.service.DepartmentService;
import com.abm.mainet.common.master.service.TbTaxMasService;
import com.abm.mainet.common.service.IFinancialYearService;
import com.abm.mainet.common.service.IOrganisationService;
import com.abm.mainet.common.service.IReceiptEntryService;
import com.abm.mainet.common.ui.model.AbstractFormModel;
import com.abm.mainet.common.utility.CommonMasterUtility;
import com.abm.mainet.common.utility.LookUp;
import com.abm.mainet.common.utility.UserSession;
import com.abm.mainet.water.domain.TbWtBillMasEntity;
import com.abm.mainet.water.dto.TbCsmrInfoDTO;
import com.abm.mainet.water.dto.WaterBillPrintSkdclDTO;
import com.abm.mainet.water.dto.WaterBillTaxDTO;
import com.abm.mainet.water.dto.WaterDataEntrySearchDTO;
import com.abm.mainet.water.dto.WaterPenaltyDto;
import com.abm.mainet.water.repository.TbWtBillMasJpaRepository;
import com.abm.mainet.water.service.BillMasterService;
import com.abm.mainet.water.service.IWaterPenaltyService;
import com.abm.mainet.water.service.NewWaterConnectionService;
import com.abm.mainet.water.service.TbWtBillMasService;
import com.abm.mainet.water.ui.controller.WaterDuesCheckController;
import com.google.common.util.concurrent.AtomicDouble;

@Component
@Scope("session")
public class WaterDuesCheckModel extends AbstractFormModel {

	private static final long serialVersionUID = 4347978343068192540L;
	
	private static final Logger LOGGER = LoggerFactory.getLogger(WaterDuesCheckController.class);

	@Autowired
	private BillMasterService billGenerationService;

	@Autowired
	private TbWtBillMasService wtBillMasService;

	@Autowired
	private NewWaterConnectionService newWaterConnectionService;

	@Autowired
	private TbWtBillMasJpaRepository billMasterJpaRepository;

    @Autowired
    private IReceiptEntryService iReceiptEntryService;
    
    @Resource
    private TbWtBillMasService tbWtBillMasService;
    
    @Resource
    private TbTaxMasService tbTaxMasService;
    
    @Autowired
    private DepartmentService departmentService;
    
    @Autowired
    IOrganisationService organisationService;
    
    @Autowired
    private IFinancialYearService financialYearService;
    
    @Autowired
    IWaterPenaltyService waterPenaltyService;

	private WaterDataEntrySearchDTO entrySearchDto = new WaterDataEntrySearchDTO();

	private List<WaterDataEntrySearchDTO> entrySearchDtoList = new ArrayList<>();
	

	private double balanceExcessAmount = 0d;

	private double rebateAmount = 0d;

	private double excessAmount = 0d;

	private double surchargeAmount = 0d;

	private double curSurchargeAmount = 0d;

	private String ccnNumber;

	private double adjustmentEntry;

	private String skdclEnv;

	private String showForm;

	
	private double currentBalAmt = 0d;

	private double totalPaybale = 0d;

	private double totalArrearsAmt = 0d;
	
	private double totalPenalty = 0d;
	
	

	public boolean getBillPaymentData() {

		Long orgId = UserSession.getCurrent().getOrganisation().getOrgid();
		final Organisation org = new Organisation();
		final Long deptId = departmentService.getDepartmentIdByDeptCode(MainetConstants.WATER_DEPARTMENT_CODE,
				MainetConstants.STATUS.ACTIVE);
		final LookUp chargeApplicableAtBillReceipt = CommonMasterUtility.getValueFromPrefixLookUp(
				PrefixConstants.NewWaterServiceConstants.BILL_RECEIPT, PrefixConstants.NewWaterServiceConstants.CAA,
				org);
		if (getCcnNumber() == null || getCcnNumber().isEmpty()) {
			addValidationError(getAppSession().getMessage("water.billPayment.search"));
		}
		if (hasValidationErrors()) {
			return false;
		}
		try {
			TbCsmrInfoDTO dto = newWaterConnectionService.fetchConnectionDetailsByConnNo(getEntrySearchDto().getCsCcn(),
					orgId);
			entrySearchDto.setCsName(dto.getCsName());
			List<TbWtBillMasEntity> billListByAscOrder = billMasterJpaRepository.fetchBillMasData(dto.getCsIdn(),
					orgId);
			List<Long> list = new ArrayList<>();
			Map<Long, WaterBillPrintSkdclDTO> billPrintSkdcl = new HashMap<>(0);
			if (CollectionUtils.isNotEmpty(billListByAscOrder)) {
				Long bmIdno = billListByAscOrder.get(billListByAscOrder.size() - 1).getBmIdno();
				list.add(bmIdno);
			}
			final List<Object[]> billMasList = wtBillMasService.getBillPrintingData(list,
					UserSession.getCurrent().getOrganisation());

			final List<TbBillDet> billDetList = wtBillMasService.getBillDetailEntity(list, orgId);
			WaterBillPrintSkdclDTO billDataInDTO = wtBillMasService.setBillDataInDTO(billMasList,
					UserSession.getCurrent().getOrganisation(), billPrintSkdcl, billDetList, null, null, null);
		

			if (billDataInDTO != null) {
			setCurrentBalAmt(billDataInDTO.getCurrentBillAmt());
			setTotalArrearsAmt(billDataInDTO.getArrearsAmt());
			
			setTotalPaybale(billDataInDTO.getOutstandingAmount());
			}
			
			WaterBillTaxDTO totalSurcharge = null;
			WaterPenaltyDto surcharge = null;
			WaterPenaltyDto surchargeDto = null;

			// Here fetching all taxes applicable at the time of payment
			List<TbTaxMas> taxList = tbTaxMasService.findAllTaxesForBillPayment(orgId, deptId,
					chargeApplicableAtBillReceipt.getLookUpId());
			Long finYearId = financialYearService.getFinanceYearId(new Date());
			List<TbBillMas> billList = billGenerationService.getBillMasterListByUniqueIdentifier(dto.getCsIdn(), orgId);
			
			  final int size = billList.size();
              final TbBillMas result = billList.get(size - 1);
           // final TbBillMas watertax = billMasList.get(size - 2);
              AtomicDouble totAmt = new AtomicDouble(0);
              if (billList != null && !billList.isEmpty()) {
            	  billList.get(billList.size() - 1).getTbWtBillDet().forEach(det -> {
                      totAmt.addAndGet(det.getBdCurBalTaxamt() + det.getBdPrvBalArramt());
                  });
              }
          	//to set adjustment amount for skdcl
  			Double totalAdjValue = 0.0;
      		totalAdjValue = billGenerationService.getAdjustmentAmountForWaterSkdcl(deptId, dto.getCsIdn(), orgId, billList);
              if (totalAdjValue != null)
  				setAdjustmentEntry(totalAdjValue);
              
			// Here calculating surcharge which is applicable at the time of payment
			boolean surchargeTaxActive = false;
			List<WaterPenaltyDto> surChargeList = new ArrayList<>();
			if (CollectionUtils.isNotEmpty(taxList)) {
				for (TbTaxMas tbTaxMas : taxList) {
					if (StringUtils.equalsIgnoreCase(tbTaxMas.getTaxActive(), MainetConstants.FlagY)) {

						LookUp lookUp = CommonMasterUtility.getHierarchicalLookUp(tbTaxMas.getTaxCategory2(), orgId);
						if (StringUtils.equalsIgnoreCase(lookUp.getLookUpCode(), MainetConstants.ReceiptForm.SC)) {

							surchargeTaxActive = true;
							LOGGER.info("fetch bill data  calculate surcharge for skdcl");
							TbServiceReceiptMasEntity receiptOfLatestPaidBill = iReceiptEntryService
									.getLatestReceiptDetailByAddRefNo(orgId, String.valueOf(dto.getCsIdn()));
							Date manualReceiptDate = receiptOfLatestPaidBill != null
									? receiptOfLatestPaidBill.getRmDate()
									: null;
							surChargeList = tbWtBillMasService.calculateSurcharge(
									organisationService.getOrganisationById(orgId), deptId, billList, tbTaxMas,
									finYearId, dto, getClientIpAddress(),
									UserSession.getCurrent().getEmployee().getEmpId(), "Y", manualReceiptDate,
									surChargeList);

		                    double arrearPenaltyAmount = 0;
		                    double currentPenaltyAmout = 0;
		                    double pendingAmount=0;
		                    double actualAmout=0;
		                    Long taxId=0l;
		                    
		                   // fetch bill wise surchage and return into a list
		                    if (!surchargeTaxActive) {
		                        if (CollectionUtils.isNotEmpty(billList)) {
		                            surChargeList = waterPenaltyService.getWaterPenaltyByBmNoIds(billList, orgId);
		                            
		                       }
		                   }
		                    
		                   if (CollectionUtils.isNotEmpty(surChargeList)) {
		                       for (WaterPenaltyDto surDto : surChargeList) {
		                           actualAmout=actualAmout+surDto.getActualAmount();
		                           pendingAmount=pendingAmount+ surDto.getPendingAmount(); 
		                           arrearPenaltyAmount=arrearPenaltyAmount+surDto.getArrearPenalty();
		                           currentPenaltyAmout=currentPenaltyAmout+surDto.getCurrentPenalty();
		                           taxId=surDto.getTaxId();
		                       }
		                       surchargeDto=new WaterPenaltyDto();
		                       surchargeDto.setPendingAmount(pendingAmount);
		                       surchargeDto.setArrearPenalty(arrearPenaltyAmount);
		                       surchargeDto.setCurrentPenalty(currentPenaltyAmout);
		                       surchargeDto.setTaxId(taxId);
		                       surchargeDto.setOrgId(orgId);
		                        totalSurcharge = new WaterBillTaxDTO();
		                       totalSurcharge.setArrearTaxAmount(arrearPenaltyAmount);
		                       totalSurcharge.setTotal(pendingAmount);
		                       totalSurcharge.setBalabceTaxAmount(pendingAmount);
		                        if (surchargeDto.getTaxId() != null) {
		                            totalSurcharge.setTaxdescription(tbTaxMasService.findTaxDescByTaxIdAndOrgId(surchargeDto.getTaxId(), orgId));
		                       
		                        }
		                   }
		                   // This code is added because edited interest as surcharge and we added this interest alias surcharge to actual
		                   // surcharge. Defect NO: 37598 by srikanth
		                   final LookUp chargeApplicableAt = CommonMasterUtility.getValueFromPrefixLookUp(
		                           PrefixConstants.NewWaterServiceConstants.BILL, PrefixConstants.NewWaterServiceConstants.CAA, org);

		                   LookUp taxCategoryLookUp = CommonMasterUtility.getHieLookupByLookupCode("P",
		                           PrefixConstants.LookUpPrefix.TAC, 1, orgId);
		                   LookUp taxSubCategoryLookUp = CommonMasterUtility.getHieLookupByLookupCode("SC",
		                           PrefixConstants.LookUpPrefix.TAC, 2, orgId);
		                   Long taxIds = tbTaxMasService.getTaxId(chargeApplicableAt.getLookUpId(), orgId, deptId,
		                           taxCategoryLookUp.getLookUpId(), taxSubCategoryLookUp.getLookUpId());

		                   Optional<TbBillDet> findFirst = result.getTbWtBillDet().stream().filter(detail -> detail.getTaxId().equals(taxIds))
		                           .findFirst();
		                   if (findFirst.isPresent()) {
		                       for (TbBillDet detail : result.getTbWtBillDet()) {
		                           final String taxCode = CommonMasterUtility
		                                   .getHierarchicalLookUp(detail.getTaxCategory(), org).getLookUpCode();
		                           if (StringUtils.equals(taxCode, "P")) {
		                               double curTaxAmnt = detail.getBdCurTaxamt();
		                               if (surcharge != null) {
		                                   detail.setBdCurTaxamt(detail.getBdCurTaxamt() + surchargeDto.getPendingAmount());
		                                   detail.setBdCurBalTaxamt(curTaxAmnt + surchargeDto.getPendingAmount());
		                               }
		                           }
		                       }

		                   } else {

		                       if (surchargeDto != null && surchargeDto.getArrearPenalty()>0.0) {
		                         
		                           TbBillDet det = new TbBillDet();
		                           det.setTaxId(surchargeDto.getTaxId());
		                           //det.setBdCurTaxamt(Math.round(surchargeDto.getPendingAmount()));
		                           //det.setBdCurBalTaxamt(Math.round(surchargeDto.getPendingAmount()));
		                           
		                           det.setBdPrvBalArramt(surchargeDto.getArrearPenalty());
		                           det.setBmIdno(result.getBmIdno());
		                           det.setOrgid(surchargeDto.getOrgId());
		                           TbTaxMas surchargeTaxMast = tbTaxMasService.findById(surchargeDto.getTaxId(),
		                                   org.getOrgid());
		                           det.setTaxCategory(surchargeTaxMast.getTaxCategory1());
		                           result.getTbWtBillDet().add(det);
		                           }
		                   }
		               
							setTotalPenalty(Math.round(totalSurcharge.getTotal()));
							setTotalPaybale(Math.round(totAmt.doubleValue() + surchargeDto.getCurrentPenalty()));
						}
					}
				}
			}
		}catch (Exception e) {
			LOGGER.error("Error occured while fetching water dues details in getBillPaymentData method");
		}

		return true;
	}


	public WaterDataEntrySearchDTO getEntrySearchDto() {
		return entrySearchDto;
	}

	public void setEntrySearchDto(WaterDataEntrySearchDTO entrySearchDto) {
		this.entrySearchDto = entrySearchDto;
	}

	public List<WaterDataEntrySearchDTO> getEntrySearchDtoList() {
		return entrySearchDtoList;
	}

	public void setEntrySearchDtoList(List<WaterDataEntrySearchDTO> entrySearchDtoList) {
		this.entrySearchDtoList = entrySearchDtoList;
	}

	public String getShowForm() {
		return showForm;
	}

	public void setShowForm(String showForm) {
		this.showForm = showForm;
	}

	public BillMasterService getBillGenerationService() {
		return billGenerationService;
	}

	public void setBillGenerationService(BillMasterService billGenerationService) {
		this.billGenerationService = billGenerationService;
	}

	

	public double getRebateAmount() {
		return rebateAmount;
	}

	public void setRebateAmount(double rebateAmount) {
		this.rebateAmount = rebateAmount;
	}

	public double getExcessAmount() {
		return excessAmount;
	}

	public void setExcessAmount(double excessAmount) {
		this.excessAmount = excessAmount;
	}

	public double getSurchargeAmount() {
		return surchargeAmount;
	}

	public void setSurchargeAmount(double surchargeAmount) {
		this.surchargeAmount = surchargeAmount;
	}

	public String getCcnNumber() {
		return ccnNumber;
	}

	public void setCcnNumber(String ccnNumber) {
		this.ccnNumber = ccnNumber;
	}

	

	

	public double getAdjustmentEntry() {
		return adjustmentEntry;
	}

	public void setAdjustmentEntry(double adjustmentEntry) {
		this.adjustmentEntry = adjustmentEntry;
	}

	public String getSkdclEnv() {
		return skdclEnv;
	}

	public void setSkdclEnv(String skdclEnv) {
		this.skdclEnv = skdclEnv;
	}

	

	

	public double getBalanceExcessAmount() {
		return balanceExcessAmount;
	}

	public void setBalanceExcessAmount(double balanceExcessAmount) {
		this.balanceExcessAmount = balanceExcessAmount;
	}

	
	public double getCurSurchargeAmount() {
		return curSurchargeAmount;
	}

	public void setCurSurchargeAmount(double curSurchargeAmount) {
		this.curSurchargeAmount = curSurchargeAmount;
	}

	
	public double getCurrentBalAmt() {
		return currentBalAmt;
	}

	public void setCurrentBalAmt(double currentBalAmt) {
		this.currentBalAmt = currentBalAmt;
	}

	public double getTotalPaybale() {
		return totalPaybale;
	}

	public void setTotalPaybale(double totalPaybale) {
		this.totalPaybale = totalPaybale;
	}

	public double getTotalArrearsAmt() {
		return totalArrearsAmt;
	}

	public void setTotalArrearsAmt(double totalArrearsAmt) {
		this.totalArrearsAmt = totalArrearsAmt;
	}

	public double getTotalPenalty() {
		return totalPenalty;
	}

	public void setTotalPenalty(double totalPenalty) {
		this.totalPenalty = totalPenalty;
	}

}
