package com.abm.mainet.water.ui.model;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import javax.annotation.Resource;

import org.apache.commons.collections.MapUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.abm.mainet.common.constant.MainetConstants;
import com.abm.mainet.common.constant.PrefixConstants;
import com.abm.mainet.common.domain.Organisation;
import com.abm.mainet.common.integration.dms.client.FileNetApplicationClient;
import com.abm.mainet.common.master.dto.TbTaxMas;
import com.abm.mainet.common.master.service.DepartmentService;
import com.abm.mainet.common.master.service.TbTaxMasService;
import com.abm.mainet.common.ui.model.AbstractFormModel;
import com.abm.mainet.common.utility.ApplicationContextProvider;
import com.abm.mainet.common.utility.CommonMasterUtility;
import com.abm.mainet.common.utility.LookUp;
import com.abm.mainet.common.utility.UserSession;
import com.abm.mainet.common.utility.Utility;
import com.abm.mainet.water.dto.TbCsmrInfoDTO;
import com.abm.mainet.water.dto.WaterBillPrintSkdclDTO;
import com.abm.mainet.water.dto.WaterBillPrintingDTO;
import com.abm.mainet.water.service.NewWaterConnectionService;
import com.abm.mainet.water.service.TbWtBillMasService;
import com.abm.mainet.water.ui.validator.WaterBillPrintingValidator;

/**
 * @author Rahul.Yadav
 *
 */
@Component
@Scope("session")
public class WaterBillPrintingModel extends AbstractFormModel {

	private static final long serialVersionUID = -5323730151643436533L;

	@Autowired
	private TbWtBillMasService tbWtBillMasService;

	@Autowired
	TbTaxMasService taxMasService;

	@Autowired
	private NewWaterConnectionService newWaterConnectionService;
	
	@Resource
    private TbTaxMasService tbTaxMasService;

	private TbCsmrInfoDTO entity = null;

	private String conType = null;

	private String meterType = null;

	private List<WaterBillPrintingDTO> billMas = new ArrayList<>(0);

	private List<TbCsmrInfoDTO> entityList = new ArrayList<>(0);

	private Map<Long, WaterBillPrintingDTO> billMasPrint = new HashMap<>(0);

	private List<Long> propIds;

	private String orgName;

	private String waterBillRule;

	private double totalYearlyBill;
	private double totalMonthlyBill;
	
	private Map<Long, WaterBillPrintSkdclDTO> billPrintSkdcl= new HashMap<>(0);

	@Override
	protected final String findPropertyPathPrefix(final String parentCode) {
		switch (parentCode) {

		case PrefixConstants.NewWaterServiceConstants.TRF:
			return "entity.trmGroup";

		case PrefixConstants.NewWaterServiceConstants.WWZ:
			return "entity.codDwzid";

		default:
			return null;

		}
	}

	public boolean searchWaterBillPrintingRecords() {
		if (getConType() == null) {
			addValidationError(getAppSession().getMessage("water.billGen.ccnType"));
		} else if (MainetConstants.SEARCH.equals(getConType())) {
			if ((getEntity().getCsCcn() == null) || getEntity().getCsCcn().isEmpty()) {
				addValidationError(getAppSession().getMessage("water.billGen.ccnNumber"));
			}
		} else {
			validateBean(getEntity(), WaterBillPrintingValidator.class);
		}
		if (hasValidationErrors()) {
			return false;
		}
		Long metertype = null;
		if (MainetConstants.NewWaterServiceConstants.METER.equals(getMeterType())) {
			metertype = CommonMasterUtility.getValueFromPrefixLookUp(MainetConstants.NewWaterServiceConstants.METER,
					MainetConstants.NewWaterServiceConstants.WMN).getLookUpId();
		} else {
			metertype = CommonMasterUtility.getValueFromPrefixLookUp(MainetConstants.NewWaterServiceConstants.NON_METER,
					MainetConstants.NewWaterServiceConstants.WMN).getLookUpId();
		}
		getEntity().setCsMeteredccn(metertype);
		getEntity().setOrgId(UserSession.getCurrent().getOrganisation().getOrgid());
		final List<WaterBillPrintingDTO> billMasList = newWaterConnectionService
				.getBillPrintingSearchDetail(getEntity(), getConType());
		setBillMas(billMasList);
		if ((billMasList == null) || billMasList.isEmpty()) {
			addValidationError(getAppSession().getMessage("water.invalidConnection"));
		} else if (MainetConstants.SEARCH.equals(getConType())) {
			getEntity().setCsMeteredccn(billMasList.get(0).getWaterMas().getCsMeteredccn());
		}
		return true;
	}

	/**
	 * @param billIds
	 */
	public void generateBillandPrint(final List<String> billIds) {
		setOrgName(UserSession.getCurrent().getOrganisation().getONlsOrgname());
		final Organisation orgid = UserSession.getCurrent().getOrganisation();
		Long orgTypeId = UserSession.getCurrent().getOrganisation().getOrgCpdId();
		LookUp orgTypeLookUp = CommonMasterUtility.getNonHierarchicalLookUpObject(orgTypeId,
				UserSession.getCurrent().getOrganisation());
		if (StringUtils.equalsIgnoreCase(orgTypeLookUp.getLookUpCode(), PrefixConstants.OrgnisationType.CORPORATION)) {
			setWaterBillRule(getAppSession().getMessage("water.bill.corporation.rule"));

		} else if (StringUtils.equalsIgnoreCase(orgTypeLookUp.getLookUpCode(), PrefixConstants.OrgnisationType.COUNCIL)
				|| StringUtils.equalsIgnoreCase(orgTypeLookUp.getLookUpCode(),
						PrefixConstants.OrgnisationType.PANCHAYAT)) {
			setWaterBillRule(getAppSession().getMessage("water.bill.nagar.panchayat.rule"));
		}

		if (MainetConstants.SEARCH.equals(getConType())) {
			final String meter = CommonMasterUtility.getNonHierarchicalLookUpObject(getEntity().getCsMeteredccn())
					.getLookUpCode();
			setMeterType(meter);
		}

		final Long deptId = ApplicationContextProvider.getApplicationContext().getBean(DepartmentService.class)
				.getDepartmentIdByDeptCode(MainetConstants.WATER_DEPARTMENT_CODE, MainetConstants.STATUS.ACTIVE);
		final LookUp chargeApplicableAt = CommonMasterUtility.getValueFromPrefixLookUp(
				PrefixConstants.NewWaterServiceConstants.BILL, PrefixConstants.NewWaterServiceConstants.CAA, UserSession.getCurrent().getOrganisation());
		final LookUp chargeApplicableAtBillReceipt = CommonMasterUtility.getValueFromPrefixLookUp(
				PrefixConstants.NewWaterServiceConstants.BILL_RECEIPT, PrefixConstants.NewWaterServiceConstants.CAA,
				UserSession.getCurrent().getOrganisation());
		final List<TbTaxMas> taxesMaster = tbTaxMasService.findAllTaxesForBillPayment(UserSession.getCurrent().getOrganisation().getOrgid(), deptId,
				chargeApplicableAt.getLookUpId());
		final List<TbTaxMas> taxesMasterBillReceipt = tbTaxMasService.findAllTaxesForBillPayment(UserSession.getCurrent().getOrganisation().getOrgid(),
				deptId, chargeApplicableAtBillReceipt.getLookUpId());
		taxesMaster.addAll(taxesMasterBillReceipt);
		
		LookUp taxCategoryLookUp = CommonMasterUtility.getHieLookupByLookupCode("P",
				PrefixConstants.LookUpPrefix.TAC, 1, UserSession.getCurrent().getOrganisation().getOrgid());
		LookUp taxSubCategoryLookUp = CommonMasterUtility.getHieLookupByLookupCode("SC",
				PrefixConstants.LookUpPrefix.TAC, 2, UserSession.getCurrent().getOrganisation().getOrgid());
		Long surchargeTaxId = tbTaxMasService.getTaxId(chargeApplicableAt.getLookUpId(), UserSession.getCurrent().getOrganisation().getOrgid(),
				deptId, taxCategoryLookUp.getLookUpId(), taxSubCategoryLookUp.getLookUpId());
		Map<Long, WaterBillPrintingDTO> prinBillData = tbWtBillMasService.prinBillData(getBillMasPrint(), orgid,
				billIds, getMeterType(), getFileNetClient(),taxesMaster,null,null,surchargeTaxId);

		// This code is only as per suda requirement. Here Setting total Yearly bill
		// based on bill schedule.
		if (MapUtils.isNotEmpty(prinBillData)) {
			for (Entry<Long, WaterBillPrintingDTO> printingDto : prinBillData.entrySet()) {
				WaterBillPrintingDTO billPrintingdto = printingDto.getValue();
				int billFrequency = getMonthsDifference(billPrintingdto.getBmFromdt(), billPrintingdto.getBmTodt());

				billPrintingdto.getTbWtBillDet().forEach(det -> {
					LookUp lookUp = null;
					TbTaxMas taxMas = taxMasService.findTaxByTaxIdAndOrgId(det.getTaxId(), det.getOrgid());
					if (taxMas != null) {
						lookUp = CommonMasterUtility.getNonHierarchicalLookUpObject(taxMas.getTaxDescId(), orgid);
					}
					if (StringUtils.equalsIgnoreCase(lookUp.getLookUpCode(), MainetConstants.WATER_DEPT)) {
						if (billFrequency == 0 || billFrequency < 2) {
							billPrintingdto.setTotalYearlyBill(det.getBdCurTaxamt() * 12);
							billPrintingdto.setTotalMonthlyBill(det.getBdCurTaxamt());
							billPrintingdto.setConnectionType("Monthly");
						} else if (billFrequency == 3 && billFrequency < 12) {
							billPrintingdto.setTotalYearlyBill(det.getBdCurTaxamt() * 4);
							billPrintingdto.setTotalMonthlyBill(det.getBdCurTaxamt() / 4);
							billPrintingdto.setConnectionType("Quarterly");
						} else if (billFrequency == 6 && billFrequency < 12) {
							billPrintingdto.setTotalYearlyBill(det.getBdCurTaxamt() * 2);
							billPrintingdto.setTotalMonthlyBill(det.getBdCurTaxamt() / 6);
							billPrintingdto.setConnectionType("Half-Yearly");
						} else if (billFrequency == 2 && billFrequency < 12) {
							billPrintingdto.setTotalYearlyBill(det.getBdCurTaxamt() * 6);
							billPrintingdto.setTotalMonthlyBill(det.getBdCurTaxamt() / 2);
							billPrintingdto.setConnectionType("Bi-Monthly");
						} else {
							billPrintingdto.setTotalYearlyBill(det.getBdCurTaxamt());
							billPrintingdto.setTotalMonthlyBill(det.getBdCurTaxamt() / 12);
							billPrintingdto.setConnectionType("Yearly");
						}
					}
				});
			}
		}
	}

	@SuppressWarnings("deprecation")
	public static final int getMonthsDifference(Date date1, Date date2) {
		int m1 = date1.getYear() * 12 + date1.getMonth();
		int m2 = date2.getYear() * 12 + date2.getMonth();
		return m2 - m1 + 1;
	}

	public TbCsmrInfoDTO getEntity() {
		return entity;
	}

	public void setEntity(final TbCsmrInfoDTO entity) {
		this.entity = entity;
	}

	public String getConType() {
		return conType;
	}

	public void setConType(final String conType) {
		this.conType = conType;
	}

	public String getMeterType() {
		return meterType;
	}

	public void setMeterType(final String meterType) {
		this.meterType = meterType;
	}

	public List<WaterBillPrintingDTO> getBillMas() {
		return billMas;
	}

	public void setBillMas(final List<WaterBillPrintingDTO> billMas) {
		this.billMas = billMas;
	}

	public Map<Long, WaterBillPrintingDTO> getBillMasPrint() {
		return billMasPrint;
	}

	public void setBillMasPrint(final Map<Long, WaterBillPrintingDTO> billMasPrint) {
		this.billMasPrint = billMasPrint;
	}

	public List<TbCsmrInfoDTO> getEntityList() {
		return entityList;
	}

	public void setEntityList(final List<TbCsmrInfoDTO> entityList) {
		this.entityList = entityList;
	}

	public List<Long> getPropIds() {
		return propIds;
	}

	public void setPropIds(final List<Long> propIds) {
		this.propIds = propIds;
	}

	public String getOrgName() {
		return orgName;
	}

	public void setOrgName(final String orgName) {
		this.orgName = orgName;
	}

	public String getWaterBillRule() {
		return waterBillRule;
	}

	public void setWaterBillRule(String waterBillRule) {
		this.waterBillRule = waterBillRule;
	}

	public double getTotalYearlyBill() {
		return totalYearlyBill;
	}

	public void setTotalYearlyBill(double totalYearlyBill) {
		this.totalYearlyBill = totalYearlyBill;
	}

	public double getTotalMonthlyBill() {
		return totalMonthlyBill;
	}

	public void setTotalMonthlyBill(double totalMonthlyBill) {
		this.totalMonthlyBill = totalMonthlyBill;
	}

	public Map<Long, WaterBillPrintSkdclDTO> getBillPrintSkdcl() {
		return billPrintSkdcl;
	}

	public void setBillPrintSkdcl(Map<Long, WaterBillPrintSkdclDTO> billPrintSkdcl) {
		this.billPrintSkdcl = billPrintSkdcl;
	}

	public Map<Long, WaterBillPrintSkdclDTO> printBillForSkdcl(List<String> billIds) {
		setOrgName(UserSession.getCurrent().getOrganisation().getONlsOrgname());
		final Organisation orgid = UserSession.getCurrent().getOrganisation();
		Long orgTypeId = UserSession.getCurrent().getOrganisation().getOrgCpdId();
		LookUp orgTypeLookUp = CommonMasterUtility.getNonHierarchicalLookUpObject(orgTypeId,
				UserSession.getCurrent().getOrganisation());
		if (StringUtils.equalsIgnoreCase(orgTypeLookUp.getLookUpCode(), PrefixConstants.OrgnisationType.CORPORATION)) {
			setWaterBillRule(getAppSession().getMessage("water.bill.corporation.rule"));

		} else if (StringUtils.equalsIgnoreCase(orgTypeLookUp.getLookUpCode(), PrefixConstants.OrgnisationType.COUNCIL)
				|| StringUtils.equalsIgnoreCase(orgTypeLookUp.getLookUpCode(),
						PrefixConstants.OrgnisationType.PANCHAYAT)) {
			setWaterBillRule(getAppSession().getMessage("water.bill.nagar.panchayat.rule"));
		}

		if (MainetConstants.SEARCH.equals(getConType())) {
			final String meter = CommonMasterUtility.getNonHierarchicalLookUpObject(getEntity().getCsMeteredccn())
					.getLookUpCode();
			setMeterType(meter);
		}

		final Long deptId = ApplicationContextProvider.getApplicationContext().getBean(DepartmentService.class)
				.getDepartmentIdByDeptCode(MainetConstants.WATER_DEPARTMENT_CODE, MainetConstants.STATUS.ACTIVE);
		final LookUp chargeApplicableAt = CommonMasterUtility.getValueFromPrefixLookUp(
				PrefixConstants.NewWaterServiceConstants.BILL, PrefixConstants.NewWaterServiceConstants.CAA, UserSession.getCurrent().getOrganisation());
		final LookUp chargeApplicableAtBillReceipt = CommonMasterUtility.getValueFromPrefixLookUp(
				PrefixConstants.NewWaterServiceConstants.BILL_RECEIPT, PrefixConstants.NewWaterServiceConstants.CAA,
				UserSession.getCurrent().getOrganisation());
		final List<TbTaxMas> taxesMaster = tbTaxMasService.findAllTaxesForBillPayment(UserSession.getCurrent().getOrganisation().getOrgid(), deptId,
				chargeApplicableAt.getLookUpId());
		final List<TbTaxMas> taxesMasterBillReceipt = tbTaxMasService.findAllTaxesForBillPayment(UserSession.getCurrent().getOrganisation().getOrgid(),
				deptId, chargeApplicableAtBillReceipt.getLookUpId());
		taxesMaster.addAll(taxesMasterBillReceipt);
		
		LookUp taxCategoryLookUp = CommonMasterUtility.getHieLookupByLookupCode("P",
				PrefixConstants.LookUpPrefix.TAC, 1, UserSession.getCurrent().getOrganisation().getOrgid());
		LookUp taxSubCategoryLookUp = CommonMasterUtility.getHieLookupByLookupCode("SC",
				PrefixConstants.LookUpPrefix.TAC, 2, UserSession.getCurrent().getOrganisation().getOrgid());
		Long surchargeTaxId = tbTaxMasService.getTaxId(chargeApplicableAt.getLookUpId(), UserSession.getCurrent().getOrganisation().getOrgid(),
				deptId, taxCategoryLookUp.getLookUpId(), taxSubCategoryLookUp.getLookUpId());
				
		Map<Long, WaterBillPrintSkdclDTO> skdclBill = tbWtBillMasService.printBillSkdcl(getBillPrintSkdcl(), orgid, billIds, getMeterType(), 
        		FileNetApplicationClient.getInstance(), taxesMaster,surchargeTaxId);
	
		return skdclBill;
	}

}
