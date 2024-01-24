package com.abm.mainet.water.ui.model;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.abm.mainet.common.constant.MainetConstants;
import com.abm.mainet.common.constant.PrefixConstants;
import com.abm.mainet.common.domain.Organisation;
import com.abm.mainet.common.dto.ApplicantDetailDTO;
import com.abm.mainet.common.integration.dto.TbBillDet;
import com.abm.mainet.common.integration.dto.TbBillMas;
import com.abm.mainet.common.master.dto.TbTaxMas;
import com.abm.mainet.common.service.IFinancialYearService;
import com.abm.mainet.common.ui.model.AbstractFormModel;
import com.abm.mainet.common.utility.ApplicationContextProvider;
import com.abm.mainet.common.utility.ApplicationSession;
import com.abm.mainet.common.utility.CommonMasterUtility;
import com.abm.mainet.common.utility.LookUp;
import com.abm.mainet.common.utility.UserSession;
import com.abm.mainet.common.utility.Utility;
import com.abm.mainet.water.domain.PlumberMaster;
import com.abm.mainet.water.dto.MeterDetailsEntryDTO;
import com.abm.mainet.water.dto.NewWaterConnectionReqDTO;
import com.abm.mainet.water.dto.TbCsmrInfoDTO;
import com.abm.mainet.water.dto.TbKLinkCcnDTO;
import com.abm.mainet.water.dto.TbMeterMas;
import com.abm.mainet.water.dto.TbWtBillSchedule;
import com.abm.mainet.water.dto.TbWtBillScheduleDetail;
import com.abm.mainet.water.dto.WaterDataEntrySearchDTO;
import com.abm.mainet.water.service.NewWaterConnectionService;
import com.abm.mainet.water.service.TbMeterMasService;
import com.abm.mainet.water.ui.validator.WaterDataEntrySuiteValidator;

@Component
@Scope("session")
public class WaterDataEntrySuiteModel extends AbstractFormModel {

	private static final long serialVersionUID = -1722059180783125139L;
	

	@Autowired
	private NewWaterConnectionService newWaterConnectionService;

	private MeterDetailsEntryDTO meterData = new MeterDetailsEntryDTO();

	private NewWaterConnectionReqDTO newConnectionDto = new NewWaterConnectionReqDTO();

	private TbCsmrInfoDTO csmrInfo = new TbCsmrInfoDTO();

	private List<LookUp> location = new ArrayList<>(0);

	private Long schduleId;// for data entry suit

	private List<TbBillMas> billMasList = new LinkedList<>();// Bill Generation List

	private List<TbBillMas> tbBillMas = new LinkedList<>();// Bill Generation List

	private List<LookUp> schedule = new ArrayList<>(0);

	private List<TbTaxMas> taxesMaster = new ArrayList<>(0);

	private Map<Integer, String> monthprefix = new HashMap<>(0);
	private Map<Long, String> financialYearMap = new HashMap<>(0);

	private Map<Long, List<String>> financialMap = new HashMap<>(0);

	private Long deptId;

	private Long serviceId;

	private List<TbWtBillScheduleDetail> billScheduleDetail = new ArrayList<>(0);

	private List<LookUp> sortedschedule = new ArrayList<>(0);

	private List<TbWtBillSchedule> sortedbillSchedule = new ArrayList<>(0);

	private String isBillingSame = MainetConstants.NewWaterServiceConstants.YES;

	private String isConsumerSame = MainetConstants.NewWaterServiceConstants.YES;

	private List<String> errorList = new ArrayList<>();

	private String modeType;

	private WaterDataEntrySearchDTO searchDTO = new WaterDataEntrySearchDTO();

	private List<WaterDataEntrySearchDTO> searchDTOResult = new ArrayList<>();
	
	private String gisValue;
	
   private String gISUri;
   
   private String propNoOptionalFlag;
   
   private List<PlumberMaster> plumberList = new ArrayList<>();

	
	@Override
	protected final String findPropertyPathPrefix(final String parentCode) {

		switch (parentCode) {

		case PrefixConstants.NewWaterServiceConstants.TRF:
			return "csmrInfo.trmGroup";

		case PrefixConstants.NewWaterServiceConstants.WWZ:
			return "csmrInfo.codDwzid";
		case PrefixConstants.WATERMODULEPREFIX.CSZ:
			return "csmrInfo.csCcnsize";

		case PrefixConstants.WATERMODULEPREFIX.CCG:
			return "csmrInfo.csCcncategory";

		}
		return null;
	}

	@SuppressWarnings("unlikely-arg-type")
	@Override
	public boolean saveForm() {
		setCustomViewName("WaterBillDetailsEntry");
		UserSession session = UserSession.getCurrent();
		getNewConnectionDto().setUserId(session.getEmployee().getEmpId());
		getNewConnectionDto().setOrgId(session.getOrganisation().getOrgid());
		getNewConnectionDto().setLangId((long) session.getLanguageId());
		getNewConnectionDto().setLgIpMac(session.getEmployee().getEmppiservername());
		getCsmrInfo().setCsEntryFlag("D");

		 Organisation org =new Organisation();
	        org.setOrgid(session.getOrganisation().getOrgid());
	     
	        boolean asclEnv =Utility.isEnvPrefixAvailable(org, MainetConstants.ENV_ASCL);
		/*
		 * if (getSchduleId() != null) { if (getBillMasList() != null &&
		 * !getBillMasList().isEmpty()) { getBillMasList().forEach(data -> {
		 * data.setWtN1(new BigDecimal(getSchduleId())); }); }
		 * 
		 * }
		 */
		
	        
	        
	        
	        LookUp demandLookUp = CommonMasterUtility.getHieLookupByLookupCode("N",
	                PrefixConstants.LookUpPrefix.TAC, 1, session.getOrganisation().getOrgid());
	        getBillMasList().forEach(mas ->{
	        	List<TbBillDet> tbWtBillDetList = new ArrayList<TbBillDet>();
	        	mas.getTbWtBillDet().forEach(det ->{
	        		if(!det.getTaxCategory().equals(demandLookUp.getLookUpId())) {
	        			tbWtBillDetList.add(det);
	        		}else {
	        			if(det.getTaxCategory().equals(demandLookUp.getLookUpId()) && ((det.getBdCsmp() != null) && (det.getBdCsmp().compareTo(BigDecimal.ZERO) > 0))) {
	        				tbWtBillDetList.add(det);
		        		}
	        		}
	        	});
	        	
	        	mas.getTbWtBillDet().clear();
	        	mas.getTbWtBillDet().addAll(tbWtBillDetList);
	        });
	        
	        
	        
	        
	        
	        
	        
		 LookUp billScheduleEntry = null;
		 List<TbBillMas> previousYearBill = new ArrayList<TbBillMas>();
		 Date finStartDate  = null;
			try {
				billScheduleEntry = CommonMasterUtility.getValueFromPrefixLookUp("BSC", "BDE", UserSession.getCurrent().getOrganisation());
			}catch (Exception exception) {
			}
		
		if((billScheduleEntry == null) || (billScheduleEntry != null && StringUtils.isBlank(billScheduleEntry.getOtherField()))){
			for (TbBillMas prevBillMas : getBillMasList()) {
				
				if(asclEnv) {
					List<TbBillDet> billDetListWithZero = prevBillMas.getTbWtBillDet().stream().filter(det -> (det.getTaxCategory().equals(demandLookUp.getLookUpId()) && ((det.getBdCsmp() == null) ||(det.getBdCsmp().compareTo(BigDecimal.ZERO) == 0)))).collect(Collectors.toList());
				}else {
				List<TbBillDet> billDetListWithZero = prevBillMas.getTbWtBillDet().stream().filter(det -> (det.getTaxCategory().equals(demandLookUp.getLookUpId()) && ((det.getBdCsmp() == null) ||(det.getBdCsmp().compareTo(BigDecimal.ZERO) == 0)))).collect(Collectors.toList());
				
				
				if(CollectionUtils.isNotEmpty(billDetListWithZero)) {
					
					addValidationError(ApplicationSession.getInstance().getMessage("Demand tax of any bill cannot be zero"));
					setCustomViewName("WaterBillDetailsEntry");
					return false;
				}
				}
			}

		}
			if(billScheduleEntry != null && StringUtils.isNotBlank(billScheduleEntry.getOtherField())) {
				DateTimeFormatter dateFormat = DateTimeFormatter.ofPattern( "dd/MM/yyyy" );
            	LocalDate localDate = LocalDate.parse(billScheduleEntry.getOtherField() , dateFormat );
            	 finStartDate = Date.from(localDate.atStartOfDay(ZoneId.systemDefault()).toInstant());
			}
			if(finStartDate != null) {
				Long scheduleChangeYearId = ApplicationContextProvider.getApplicationContext().getBean(IFinancialYearService.class).getFinanceYearId(finStartDate);
				 previousYearBill = getBillMasList().stream()
							.filter(billMasDto -> (billMasDto.getBmYear().equals(scheduleChangeYearId) && billMasDto.getBmIdno() == 0))
							.collect(Collectors.toList());
			}
		
		boolean previousFlag = false;
		if(CollectionUtils.isNotEmpty(previousYearBill)) {
			if(previousYearBill.size() >1) {
				for(int i=0; i<previousYearBill.size();i++) {
					TbBillMas currBillMas = previousYearBill.get(i);
					if(i != (previousYearBill.size() - 1)) {
						for (TbBillDet billDet : currBillMas.getTbWtBillDet()) {
							if(billDet.getBdCsmp() != null && billDet.getBdCsmp().doubleValue() > 0) {
								previousFlag = true;
							}
						}
					}
					if(previousFlag) {
						break;
					}
				}
			}
		}
		
		if(previousFlag) {
			addValidationError(ApplicationSession.getInstance().getMessage("Please put total year consolidate amount in the last month of the year 19-20"));
			setCustomViewName("WaterBillDetailsEntry");
			return false;
		}
		getNewConnectionDto().setCsmrInfo(getCsmrInfo());
		saveWaterDataEntryWithoutArrears();

		validateBean(this, WaterDataEntrySuiteValidator.class);

		if (hasValidationErrors()) {
			setCustomViewName("WaterDataEntrySuiteCreate");
			return false;
		}

		if (getCustomViewName().equalsIgnoreCase("WaterBillDetailsEntry")) {
			Boolean scheduleFlag = ((getSchduleId() == null) || (getSchduleId() <= 0));
			Boolean billMasflag = (getBillMasList() == null || getBillMasList().isEmpty());
			if (scheduleFlag && billMasflag) {
				addValidationError(ApplicationSession.getInstance().getMessage("water.dataentry.arrears.selection"));
				return false;
			}
		}
		
			boolean arrearsStatus = validateArrears();
			if (arrearsStatus) {
				String errorString = "";
				if (getErrorList() != null && !getErrorList().isEmpty()) {
					errorString = StringUtils.join(getErrorList(), ',');
				}
				addValidationError(ApplicationSession.getInstance()
						.getMessage("water.dataentry.validation.bill.schedule.arrears.not.available") + errorString);
				return false;
		}
		 
		if (getModeType().equalsIgnoreCase("E")) {
			newWaterConnectionService.updateWaterDataEntry(getNewConnectionDto(), getMeterData(), getBillMasList());
			setSuccessMessage(getAppSession().getMessage("water.dataentry.update",
					new Object[] { getNewConnectionDto().getCsmrInfo().getCsCcn() }));
		} else {
			newWaterConnectionService.saveWaterDataEntry(getNewConnectionDto(), getMeterData(), getBillMasList());
			setSuccessMessage(
					getAppSession().getMessage("waterData.success") + getNewConnectionDto().getCsmrInfo().getCsCcn());
		}
		return true;
	}

	public boolean saveFormWithOutArrears() {
		setCustomViewName("WaterDataEntrySuiteCreate");
		UserSession session = UserSession.getCurrent();
		getNewConnectionDto().setUserId(session.getEmployee().getEmpId());
		getNewConnectionDto().setOrgId(session.getOrganisation().getOrgid());
		getNewConnectionDto().setLangId((long) session.getLanguageId());
		getNewConnectionDto().setLgIpMac(session.getEmployee().getEmppiservername());
		getCsmrInfo().setCsEntryFlag("D");
		getNewConnectionDto().setCsmrInfo(getCsmrInfo());
		saveWaterDataEntryWithoutArrears();

		validateBean(this, WaterDataEntrySuiteValidator.class);

		if (hasValidationErrors()) {
			return false;
		}

		if (getSchduleId() == null || getSchduleId() <= 0) {
			setBillMasList(null);
		} else {
			boolean arrearsStatus = validateArrears();
			if (arrearsStatus) {
				String errorString = "";
				if (getErrorList() != null && !getErrorList().isEmpty()) {
					errorString = StringUtils.join(getErrorList(), ',');
				}
				addValidationError(ApplicationSession.getInstance()
						.getMessage("water.dataentry.validation.bill.schedule.arrears.not.available") + errorString);
				return false;
			}
		}
		if (getModeType().equalsIgnoreCase("E")) {
			newWaterConnectionService.updateWaterDataEntry(getNewConnectionDto(), getMeterData(), getBillMasList());
			setSuccessMessage(getAppSession().getMessage("water.dataentry.update",
					new Object[] { getNewConnectionDto().getCsmrInfo().getCsCcn() }));
		} else {
			newWaterConnectionService.saveWaterDataEntry(getNewConnectionDto(), getMeterData(), getBillMasList());
			setSuccessMessage(
					getAppSession().getMessage("waterData.success") + getNewConnectionDto().getCsmrInfo().getCsCcn());
		}
		return true;
	}

	public MeterDetailsEntryDTO getMeterData() {
		return meterData;
	}

	public void setMeterData(MeterDetailsEntryDTO meterData) {
		this.meterData = meterData;
	}

	public NewWaterConnectionReqDTO getNewConnectionDto() {
		return newConnectionDto;
	}

	public void setNewConnectionDto(NewWaterConnectionReqDTO newConnectionDto) {
		this.newConnectionDto = newConnectionDto;
	}

	public TbCsmrInfoDTO getCsmrInfo() {
		return csmrInfo;
	}

	public void setCsmrInfo(TbCsmrInfoDTO csmrInfo) {
		this.csmrInfo = csmrInfo;
	}

	public List<LookUp> getLocation() {
		return location;
	}

	public void setLocation(List<LookUp> location) {
		this.location = location;
	}

	public Long getSchduleId() {
		return schduleId;
	}

	public void setSchduleId(Long schduleId) {
		this.schduleId = schduleId;
	}

	public List<TbBillMas> getBillMasList() {
		return billMasList;
	}

	public void setBillMasList(List<TbBillMas> billMasList) {
		this.billMasList = billMasList;
	}

	public List<LookUp> getSchedule() {
		return schedule;
	}

	public void setSchedule(List<LookUp> schedule) {
		this.schedule = schedule;
	}

	public List<TbTaxMas> getTaxesMaster() {
		return taxesMaster;
	}

	public void setTaxesMaster(List<TbTaxMas> taxesMaster) {
		this.taxesMaster = taxesMaster;
	}

	public Long getDeptId() {
		return deptId;
	}

	public void setDeptId(Long deptId) {
		this.deptId = deptId;
	}

	public Long getServiceId() {
		return serviceId;
	}

	public void setServiceId(Long serviceId) {
		this.serviceId = serviceId;
	}

	public Map<Integer, String> getMonthprefix() {
		return monthprefix;
	}

	public List<TbWtBillScheduleDetail> getBillScheduleDetail() {
		return billScheduleDetail;
	}

	public void setBillScheduleDetail(List<TbWtBillScheduleDetail> billScheduleDetail) {
		this.billScheduleDetail = billScheduleDetail;
	}

	public List<TbBillMas> getTbBillMas() {
		return tbBillMas;
	}

	public void setTbBillMas(List<TbBillMas> tbBillMas) {
		this.tbBillMas = tbBillMas;
	}

	public Map<Long, String> getFinancialYearMap() {
		return financialYearMap;
	}

	public void setFinancialYearMap(Map<Long, String> financialYearMap) {
		this.financialYearMap = financialYearMap;
	}

	public void setMonthprefix(Map<Integer, String> monthprefix) {
		this.monthprefix = monthprefix;
	}

	public boolean validateData() {
		validateBean(this, WaterDataEntrySuiteValidator.class);
		if (hasValidationErrors()) {
			return true;
		}
		return false;
	}

	public List<LookUp> getSortedschedule() {
		return sortedschedule;
	}

	public void setSortedschedule(List<LookUp> sortedschedule) {
		this.sortedschedule = sortedschedule;
	}

	public List<TbWtBillSchedule> getSortedbillSchedule() {
		return sortedbillSchedule;
	}

	public void setSortedbillSchedule(List<TbWtBillSchedule> sortedbillSchedule) {
		this.sortedbillSchedule = sortedbillSchedule;
	}

	

	public String getIsBillingSame() {
		return isBillingSame;
	}

	public void setIsBillingSame(String isBillingSame) {
		this.isBillingSame = isBillingSame;
	}

	public String getIsConsumerSame() {
		return isConsumerSame;
	}

	public void setIsConsumerSame(String isConsumerSame) {
		this.isConsumerSame = isConsumerSame;
	}

	public Map<Long, List<String>> getFinancialMap() {
		return financialMap;
	}

	public void setFinancialMap(Map<Long, List<String>> financialMap) {
		this.financialMap = financialMap;
	}

	public List<String> getErrorList() {
		return errorList;
	}

	public void setErrorList(List<String> errorList) {
		this.errorList = errorList;
	}

	public String getModeType() {
		return modeType;
	}

	public void setModeType(String modeType) {
		this.modeType = modeType;
	}

	public WaterDataEntrySearchDTO getSearchDTO() {
		return searchDTO;
	}

	public void setSearchDTO(WaterDataEntrySearchDTO searchDTO) {
		this.searchDTO = searchDTO;
	}

	public List<WaterDataEntrySearchDTO> getSearchDTOResult() {
		return searchDTOResult;
	}

	public void setSearchDTOResult(List<WaterDataEntrySearchDTO> searchDTOResult) {
		this.searchDTOResult = searchDTOResult;
	}
	

	public String getGisValue() {
		return gisValue;
	}

	public void setGisValue(String gisValue) {
		this.gisValue = gisValue;
	}

	public String getgISUri() {
		return gISUri;
	}

	public void setgISUri(String gISUri) {
		this.gISUri = gISUri;
	}

	public boolean validateArrears() {
		List<TbBillMas> billMasList = getBillMasList();
		int counter = 0;
		List<String> errors = new ArrayList<>();
		setErrorList(new ArrayList<>());
		BigDecimal amount = BigDecimal.ZERO;
		for (TbBillMas billMas : billMasList) {

			if (billMas.getBmYear() != null) {

				for (TbBillDet billDet : billMas.getTbWtBillDet()) {
					if (billDet.getBdCsmp() != null && billDet.getBdCsmp().compareTo(BigDecimal.ZERO) > 0) {
						amount = amount.add(billDet.getBdCsmp());
					} else {
						amount = amount.add(BigDecimal.ZERO);
					}
				}

			}

		}
		if (amount.compareTo(BigDecimal.ZERO) == 0) {
			counter = counter + 1;
			errors.add("Atleast one arrear entry must be entered");
		}
		if (counter >= 1) {
			getErrorList().addAll(errors);
			return true;
		}
		return false;
	}

	private void saveWaterDataEntryWithoutArrears() {
		UserSession session = UserSession.getCurrent();
		NewWaterConnectionReqDTO connectionDTO = this.getNewConnectionDto();
		TbCsmrInfoDTO infoDTO = this.getCsmrInfo();
		ApplicantDetailDTO appDTO = connectionDTO.getApplicantDTO();
		Organisation organisation = new Organisation();
		organisation.setOrgid(UserSession.getCurrent().getOrganisation().getOrgid());

		connectionDTO.setUserId(session.getEmployee().getEmpId());
		connectionDTO.setOrgId(session.getOrganisation().getOrgid());
		connectionDTO.setLangId((long) session.getLanguageId());
		connectionDTO.setLgIpMac(session.getEmployee().getEmppiservername());

		if (this.getIsConsumerSame().equalsIgnoreCase("Y")) {
			connectionDTO.setIsConsumer("Y");
			infoDTO.setCsName(infoDTO.getCsOname());
			if (infoDTO.getCsOGender() != null && infoDTO.getCsOGender() != 0l) {
				infoDTO.setCsGender(infoDTO.getCsOGender());
			}
			if(Utility.isEnvPrefixAvailable(UserSession.getCurrent().getOrganisation(), MainetConstants.ENV_PSCL)){
				infoDTO.setCsContactno(infoDTO.getCsContactno());
				infoDTO.setCsAdd(infoDTO.getCsAdd());
			}else {
				infoDTO.setCsContactno(infoDTO.getCsOcontactno());
				infoDTO.setCsAdd(infoDTO.getCsOadd());
			}
			infoDTO.setCsEmail(infoDTO.getCsOEmail());
			if (infoDTO.getOpincode() != null && !infoDTO.getOpincode().isEmpty()) {
				infoDTO.setCsCpinCode(Long.valueOf(infoDTO.getOpincode()));
			}
		}
		if (this.getIsBillingSame().equalsIgnoreCase("Y")) {
			connectionDTO.setIsBillingAddressSame("Y");
			infoDTO.setCsBadd(infoDTO.getCsAdd());
			if (infoDTO.getCsCpinCode() != null) {
				infoDTO.setBpincode(infoDTO.getCsCpinCode().toString());
			}
		}

		connectionDTO.setCityName(infoDTO.getCsAdd());
		connectionDTO.setFlatBuildingNo(infoDTO.getCsAdd());
		connectionDTO.setRoadName(infoDTO.getCsAdd());
		if (infoDTO.getCsCpinCode() != null) {
			connectionDTO.setPinCode(infoDTO.getCsCpinCode());
			connectionDTO.setPincodeNo(infoDTO.getCsCpinCode());
		}
		connectionDTO.setAreaName(infoDTO.getCsAdd());
		connectionDTO.setBldgName(infoDTO.getCsAdd());
		connectionDTO.setBlockName(infoDTO.getCsAdd());
		// connectionDTO.setBlockNo(infoDTO.getCsAdd());
		connectionDTO.setFlatBuildingNo(infoDTO.getCsAdd());
		if (infoDTO.getCsGender() != null && infoDTO.getCsGender() != 0l) {
			connectionDTO.setGender(String.valueOf(infoDTO.getCsGender()));
		}
		connectionDTO.setMobileNo(infoDTO.getCsContactno());
		connectionDTO.setEmail(infoDTO.getCsEmail());
		connectionDTO.setAadhaarNo(connectionDTO.getApplicantDTO().getAadharNo());

		appDTO.setApplicantFirstName(infoDTO.getCsName());
		appDTO.setAreaName(infoDTO.getCsAdd());
		appDTO.setRoadName(infoDTO.getCsAdd());
		appDTO.setMobileNo(infoDTO.getCsContactno());
		appDTO.setEmailId(infoDTO.getCsEmail());
		if (infoDTO.getCsCpinCode() != null) {
			appDTO.setPinCode(infoDTO.getCsCpinCode().toString());
		}
		final List<LookUp> lookUps = CommonMasterUtility.getLookUps(MainetConstants.GENDER, organisation);
		for (final LookUp lookUp : lookUps) {
			if ((infoDTO.getCsOGender() != null) && infoDTO.getCsOGender() != 0l) {
				if (lookUp.getLookUpId() == infoDTO.getCsOGender()) {
					appDTO.setGender(lookUp.getLookUpCode());
					break;
				}
			}

		}
		appDTO.setAreaName(infoDTO.getCsAdd());
		final List<TbKLinkCcnDTO> existingLinkDetails = getCsmrInfo().getLinkDetails();
		if (connectionDTO.getExistingConsumerNumber() != null) {
			if (connectionDTO.getExistingConsumerNumber().equals(MainetConstants.FlagY)) {
				if (existingLinkDetails != null && !existingLinkDetails.isEmpty()) {
					for (final TbKLinkCcnDTO link : existingLinkDetails) {

						if (link.getLcId() != 0) {
							link.setUserIds(UserSession.getCurrent().getEmployee().getEmpId());
							link.setLmodDate(new Date());
							link.setLgIpMac(UserSession.getCurrent().getEmployee().getEmppiservername());
						} else {
							link.setIsDeleted(PrefixConstants.NewWaterServiceConstants.NO);
							link.setUserIds(UserSession.getCurrent().getEmployee().getEmpId());
							link.setLmodDate(new Date());
							link.setLgIpMac(UserSession.getCurrent().getEmployee().getEmppiservername());
						}

					}
				}
			} else {
				if (existingLinkDetails != null && !existingLinkDetails.isEmpty()) {
					for (final TbKLinkCcnDTO link : existingLinkDetails) {
						link.setIsDeleted(PrefixConstants.NewWaterServiceConstants.YES);
					}
				}
			}
		}
		getCsmrInfo().setLinkDetails(existingLinkDetails);

		connectionDTO.setCsmrInfo(infoDTO);
	}

	public NewWaterConnectionReqDTO getConnectionDetails(Long csIdn) {
		TbCsmrInfoDTO csmrDTO = newWaterConnectionService.getConnectionDetailsById(csIdn);
		setCsmrInfo(csmrDTO);
		if(csmrDTO.getHouseNumber() == null ) {
			csmrDTO.setHouseNumber(csmrDTO.getCsOflatno());
			
		}
		NewWaterConnectionReqDTO connectionDTO = new NewWaterConnectionReqDTO();
		connectionDTO.setCsmrInfo(csmrDTO);
		
		connectionDTO.setPropertyNo(csmrDTO.getPropertyNo());
		connectionDTO.setOrgId(csmrDTO.getOrgId());
		
		TbCsmrInfoDTO propDTO = null;
		
		if(null != csmrDTO.getPropertyNo())
			propDTO = getPropertyDetailsByPropertyNumber(connectionDTO);
		
		if (propDTO != null) {
			getCsmrInfo().setPropertyUsageType(propDTO.getPropertyUsageType());
		}
		List<Long> conIds = new ArrayList<>(0);
		conIds.add(csIdn);
		Map<Long, TbMeterMas> meterMasMap = ApplicationContextProvider.getApplicationContext()
				.getBean(TbMeterMasService.class).findMeterMasListBycnsId(conIds, csmrDTO.getOrgId());
		if (meterMasMap != null && !meterMasMap.isEmpty()) {
			TbMeterMas meterMas = meterMasMap.get(csIdn);
			MeterDetailsEntryDTO meterEntryDTO = new MeterDetailsEntryDTO();
			if (meterMas != null) {
				meterEntryDTO.setMeterMake(meterMas.getMmMtrmake());
				meterEntryDTO.setMeterNumber(meterMas.getMmMtrno());
				meterEntryDTO.setInitialMeterReading(Long.valueOf(meterMas.getMmInitialReading()));
				
				if(meterMas.getMaxMeterRead() != null)
				{
					meterEntryDTO.setMeterMaxReading(meterMas.getMaxMeterRead().longValue());
				}
				if (meterMas.getMmMtrcost() != null) {
					meterEntryDTO.setMeterCost(meterMas.getMmMtrcost().doubleValue());
				}
				meterEntryDTO.setMeterOwnerShip(meterMas.getMmOwnership());
				meterEntryDTO.setMeterInstallationDate(meterMas.getMmInstallDate());
				setMeterData(meterEntryDTO);
			}
		}
		
		if(null !=csmrDTO.getCsName()  && null != csmrDTO.getCsOname())
			isConsumerSame = (csmrDTO.getCsName().equals(csmrDTO.getCsOname())) ? "Y" : "N";
		else
			isConsumerSame = MainetConstants.NewWaterServiceConstants.NO;
		
		if(null !=csmrDTO.getCsAdd()  && null != csmrDTO.getCsBadd())
			isBillingSame = (csmrDTO.getCsAdd().equals(csmrDTO.getCsBadd())) ? "Y" : "N";
		else
			isBillingSame = MainetConstants.NewWaterServiceConstants.NO;

		if (csmrDTO.getLinkDetails() != null && !csmrDTO.getLinkDetails().isEmpty()) {
			TbKLinkCcnDTO linkDTO = csmrDTO.getLinkDetails().get(0);
			String existingConnectionNumber = (linkDTO.getLcOldccn() != null && !linkDTO.getLcOldccn().isEmpty()) ? "Y"
					: "N";
			connectionDTO.setExistingConsumerNumber(existingConnectionNumber);
		} else {
			connectionDTO.setExistingConsumerNumber("N");
		}
		return connectionDTO;
	}

	public TbCsmrInfoDTO getPropertyDetailsByPropertyNumber(NewWaterConnectionReqDTO requestDTO) {
		return newWaterConnectionService.getPropertyDetailsByPropertyNumber(requestDTO);
	}

	public String getPropNoOptionalFlag() {
		return propNoOptionalFlag;
	}

	public void setPropNoOptionalFlag(String propNoOptionalFlag) {
		this.propNoOptionalFlag = propNoOptionalFlag;
	}
	
	public List<PlumberMaster> getPlumberList() {
		return plumberList;
	}

	public void setPlumberList(List<PlumberMaster> plumberList) {
		this.plumberList = plumberList;
	}

	
}
