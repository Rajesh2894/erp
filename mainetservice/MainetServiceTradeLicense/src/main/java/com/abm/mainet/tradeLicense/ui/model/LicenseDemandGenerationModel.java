package com.abm.mainet.tradeLicense.ui.model;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.abm.mainet.common.constant.PrefixConstants;
import com.abm.mainet.common.master.dto.TbTaxMas;
import com.abm.mainet.common.ui.model.AbstractFormModel;
import com.abm.mainet.common.utility.UserSession;
import com.abm.mainet.tradeLicense.dto.TradeMasterDetailDTO;
import com.abm.mainet.tradeLicense.service.IRenewalLicenseApplicationService;
import com.abm.mainet.tradeLicense.service.ITradeLicenseApplicationService;

/**
 * @author Rahul.Yadav
 *
 */
@Component
@Scope("session")
public class LicenseDemandGenerationModel extends AbstractFormModel {

	private static final long serialVersionUID = 2640492035595639136L;

	private List<TbTaxMas> taxMas = null;

	private String demandRemarks;

	private Date demandDate;

	private Long demandFrequency;

	private int demandSize;

	private int errorSize;

	private int demandselectSize;
	private String demandCcnType;
	@Autowired
	ITradeLicenseApplicationService iTradeLicenseApplicationService;

	@Autowired
	private IRenewalLicenseApplicationService iRenewalLicenseApplicationService;
	private TradeMasterDetailDTO entity = null;

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

	/**
	 * @return
	 */
	public boolean searchWaterBillRecords() {

		return true;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.abm.mainet.common.ui.model.AbstractFormModel#saveForm()
	 */
	@Override
	public boolean saveForm() {
		TradeMasterDetailDTO dto = getEntity();

		List<TradeMasterDetailDTO> dtoList = (List<TradeMasterDetailDTO>) iTradeLicenseApplicationService
				.fetchAllLicenseDemand(UserSession.getCurrent().getOrganisation().getOrgid(), dto.getTrdLicno(),dto.getTrdWard1(),dto.getTrdWard2(),dto.getTrdWard3(),dto.getTrdWard4(),dto.getTrdWard5());

		String succMsg = iRenewalLicenseApplicationService.geneateDemandForLicense(dtoList);
		this.setSuccessMessage(succMsg);
		return true;

	}

	public List<TbTaxMas> getTaxMas() {
		return taxMas;
	}

	public void setTaxMas(final List<TbTaxMas> taxMas) {
		this.taxMas = taxMas;
	}

	public String getDemandRemarks() {
		return demandRemarks;
	}

	public Date getDemandDate() {
		return demandDate;
	}

	public Long getDemandFrequency() {
		return demandFrequency;
	}

	public int getDemandSize() {
		return demandSize;
	}

	public int getErrorSize() {
		return errorSize;
	}

	public int getDemandselectSize() {
		return demandselectSize;
	}

	public void setDemandRemarks(String demandRemarks) {
		this.demandRemarks = demandRemarks;
	}

	public void setDemandDate(Date demandDate) {
		this.demandDate = demandDate;
	}

	public void setDemandFrequency(Long demandFrequency) {
		this.demandFrequency = demandFrequency;
	}

	public void setDemandSize(int demandSize) {
		this.demandSize = demandSize;
	}

	public void setErrorSize(int errorSize) {
		this.errorSize = errorSize;
	}

	public void setDemandselectSize(int demandselectSize) {
		this.demandselectSize = demandselectSize;
	}

	public String getDemandCcnType() {
		return demandCcnType;
	}

	public void setDemandCcnType(String demandCcnType) {
		this.demandCcnType = demandCcnType;
	}

	public TradeMasterDetailDTO getEntity() {
		return entity;
	}

	public void setEntity(TradeMasterDetailDTO entity) {
		this.entity = entity;
	}

}
