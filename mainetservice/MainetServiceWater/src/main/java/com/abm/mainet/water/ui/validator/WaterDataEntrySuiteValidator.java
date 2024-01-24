package com.abm.mainet.water.ui.validator;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.concurrent.TimeUnit;

import javax.annotation.Resource;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.abm.mainet.common.constant.MainetConstants;
import com.abm.mainet.common.domain.Organisation;
import com.abm.mainet.common.exception.FrameworkException;
import com.abm.mainet.common.ui.validator.BaseEntityValidator;
import com.abm.mainet.common.ui.validator.EntityValidationContext;
import com.abm.mainet.common.utility.ApplicationSession;
import com.abm.mainet.common.utility.CommonMasterUtility;
import com.abm.mainet.common.utility.UserSession;
import com.abm.mainet.common.utility.Utility;
import com.abm.mainet.common.utility.UtilityService;
import com.abm.mainet.water.domain.TbWtBillScheduleEntity;
import com.abm.mainet.water.dto.TbCsmrInfoDTO;
import com.abm.mainet.water.dto.TbKLinkCcnDTO;
import com.abm.mainet.water.repository.TbWtBillMasJpaRepository;
import com.abm.mainet.water.repository.TbWtBillScheduleJpaRepository;
import com.abm.mainet.water.service.NewWaterConnectionService;
import com.abm.mainet.water.ui.model.WaterDataEntrySuiteModel;

@Component
public class WaterDataEntrySuiteValidator extends BaseEntityValidator<WaterDataEntrySuiteModel> {
    @Autowired
    NewWaterConnectionService waterService;

    @Resource
    TbWtBillScheduleJpaRepository tbWtBillScheduleJpaRepository;

    @Resource
    TbWtBillMasJpaRepository tbWtBillMasJpaRepository;

    @Override
    protected void performValidations(final WaterDataEntrySuiteModel model,
	    final EntityValidationContext<WaterDataEntrySuiteModel> entityValidationContext) {

	final ApplicationSession session = ApplicationSession.getInstance();

	if ((model.getCsmrInfo().getTypeOfApplication() == null)
		|| model.getCsmrInfo().getTypeOfApplication().isEmpty()) {
	    entityValidationContext.addOptionConstraint(session.getMessage("water.validation.typeOfApplication"));
	} else {
	    if ((model.getCsmrInfo().getTypeOfApplication() != null) && model.getCsmrInfo().getTypeOfApplication()
		    .equals(MainetConstants.NewWaterServiceConstants.APPLICATION_TYPE)) {
		if (model.getCsmrInfo().getFromDate() != null && model.getCsmrInfo().getToDate() != null) {
		    entityValidationContext.rejectCompareDate(model.getCsmrInfo().getFromDate(), "fromDate",
			    model.getCsmrInfo().getToDate(), "toDate");
		} else {
		    if (model.getCsmrInfo().getFromDate() == null) {
			entityValidationContext
				.addOptionConstraint(session.getMessage("water.dataentry.select.from.period"));
		    }
		    if (model.getCsmrInfo().getToDate() == null) {
			entityValidationContext
				.addOptionConstraint(session.getMessage("water.dataentry.select.to.period"));
		    }
		}

		try {
		    if (model.getCsmrInfo().getFromDate() != null && model.getCsmrInfo().getToDate() != null) {
			waterService.findNoOfDaysCalculation(model.getCsmrInfo(),
				UserSession.getCurrent().getOrganisation());
			if (checkDate(model.getCsmrInfo().getFromDate(), model.getCsmrInfo().getToDate(),
				model.getCsmrInfo().getMaxNumberOfDay()))
			    entityValidationContext
				    .addOptionConstraint(session.getMessage("water.dataentry.temp.connec.period "
					    + model.getCsmrInfo().getMaxNumberOfDay() + "water.dataentry.days"));
		    }
		} catch (FrameworkException exp) {
		    entityValidationContext
			    .addOptionConstraint(session.getMessage("water.validation.prefix.not.found.noOfDays"));
		}

	    }
	}

	if(StringUtils.isNotBlank(model.getPropNoOptionalFlag()) && StringUtils.equals(model.getPropNoOptionalFlag(), MainetConstants.FlagY)) {
		
	}else {
		if (model.getCsmrInfo().getPropertyNo() == null || model.getCsmrInfo().getPropertyNo().isEmpty()) {
		    entityValidationContext.addOptionConstraint(session.getMessage("water.dataentry.validate.property.num"));
		}
	}

	if (model.getCsmrInfo().getCsName() == null || model.getCsmrInfo().getCsName().isEmpty()) {
	    entityValidationContext.addOptionConstraint(session.getMessage("water.enter.consumer.name"));
	}

	if ((model.getCsmrInfo().getBplFlag() == null || model.getCsmrInfo().getBplFlag().isEmpty())) {
	    entityValidationContext.addOptionConstraint(session.getMessage("water.dataentry.validation.bhagirathi"));
	}
	performGroupValidation("csmrInfo.codDwzid");
	performGroupValidation("csmrInfo.csCcncategory");
	// performGroupValidation("csmrInfo.trmGroup");

	if ((model.getCsmrInfo().getCsAdd() == null || model.getCsmrInfo().getCsAdd().isEmpty())) {
	    entityValidationContext.addOptionConstraint(session.getMessage("water.enter.consumer.adddress"));
	}
	if ((model.getCsmrInfo().getCsCpinCode() == null)) {
	    entityValidationContext.addOptionConstraint(session.getMessage("water.validation.OpinCode"));
	}

	if ((model.getCsmrInfo().getCsContactno() == null || model.getCsmrInfo().getCsContactno().isEmpty())) {
	    entityValidationContext.addOptionConstraint(session.getMessage("water.validation.Applicantentermobileno"));
	}

	if ((model.getCsmrInfo().getCsCcnsize() != null) && (model.getCsmrInfo().getCsCcnsize() == 0L)) {
	    entityValidationContext.addOptionConstraint(session.getMessage("water.validation.connSize"));
	}
	if (model.getCsmrInfo().getCsMeteredccn() == null || model.getCsmrInfo().getCsMeteredccn() <= 0) {
	    entityValidationContext.addOptionConstraint(session.getMessage("water.validation.connMeterType"));
	}
	if (model.getCsmrInfo().getCsCcnstatus() == null || model.getCsmrInfo().getCsCcnstatus() <= 0) {
	    entityValidationContext.addOptionConstraint(session.getMessage("water.validation.connCCnStatus"));
	}
	if (model.getCsmrInfo().getPcDate() == null) {
	    entityValidationContext.addOptionConstraint(session.getMessage("water.validation.pcInsDate"));

	}

	if (model.getCsmrInfo().getPcDate() != null && model.getCsmrInfo().getPcDate().after(new Date())) {
	    entityValidationContext.addOptionConstraint(session.getMessage("water.validation.current.pcDate"));

	}

	if (model.getIsBillingSame().equalsIgnoreCase("N")) {
	    if ((model.getCsmrInfo().getCsBadd() == null || model.getCsmrInfo().getCsBadd().isEmpty())) {
		entityValidationContext.addOptionConstraint(session.getMessage("water.validation.areaName"));
	    }
	    if ((model.getCsmrInfo().getBpincode() == null) || model.getCsmrInfo().getBpincode().isEmpty()) {
		entityValidationContext.addOptionConstraint(session.getMessage("water.validation.OpinCode"));
	    }
	}

	String metertype = CommonMasterUtility.getNonHierarchicalLookUpObject(model.getCsmrInfo().getCsMeteredccn(),
		UserSession.getCurrent().getOrganisation()).getLookUpCode();
	if ("MTR".equals(metertype)) {
	    if ((model.getMeterData().getMeterNumber() == null) || model.getMeterData().getMeterNumber().isEmpty()) {
		entityValidationContext.addOptionConstraint(session.getMessage("water.valid.meterNumber"));
	    }
	    if (model.getMeterData().getInitialMeterReading() == null) {
		entityValidationContext.addOptionConstraint(session.getMessage("water.valid.meterRead"));
	    }
	    if (model.getMeterData().getMeterMaxReading() == null) {
		entityValidationContext.addOptionConstraint(session.getMessage("water.valid.metMaxRead"));
	    }
	    /*
	     * if (model.getMeterData().getMeterInstallationDate() == null) {
	     * entityValidationContext.addOptionConstraint(session.getMessage(
	     * "water.valid.metInsDt")); } else { if (model.getCsmrInfo().getPcDate() !=
	     * null) { final Calendar appDate = Calendar.getInstance();
	     * appDate.setTime(model.getCsmrInfo().getPcDate());
	     * appDate.set(Calendar.HOUR_OF_DAY, 0); appDate.set(Calendar.MINUTE, 0);
	     * appDate.set(Calendar.SECOND, 0); if
	     * (!UtilityService.compareDate(appDate.getTime(),
	     * model.getMeterData().getMeterInstallationDate())) {
	     * entityValidationContext.addOptionConstraint(session.getMessage(
	     * "meter.date.installation")); } else if
	     * (!UtilityService.compareDate(model.getMeterData().getMeterInstallationDate(),
	     * new Date())) {
	     * entityValidationContext.addOptionConstraint(session.getMessage(
	     * "meter.install.date")); } }
	     * 
	     * }
	     */

	    if (model.getMeterData().getMeterInstallationDate() == null) {
		entityValidationContext.addOptionConstraint(session.getMessage("meter.valid.meterInstDate"));
	    }

	    if (model.getMeterData().getMeterInstallationDate() != null
		    && model.getMeterData().getMeterInstallationDate().after(new Date())) {
		entityValidationContext.addOptionConstraint(session.getMessage("meter.validation.current.mtrDate"));

	    }

	    if (model.getCsmrInfo().getPcDate() != null && model.getMeterData().getMeterInstallationDate() != null) {
		if (model.getCsmrInfo().getPcDate().after(model.getMeterData().getMeterInstallationDate())) {
		    entityValidationContext.addOptionConstraint(session.getMessage("meter.valid.conInst.mtrInstDate"));
		}
	    }
	    if (model.getMeterData().getMeterOwnerShip() == null) {
		entityValidationContext.addOptionConstraint(session.getMessage("water.valid.ownShip"));
	    }
	}

	if ((model.getCsmrInfo().getTrmGroup1() == null) || (model.getCsmrInfo().getTrmGroup1() == 0L)) {
	    entityValidationContext
		    .addOptionConstraint(session.getMessage("water.dataentry.validation.tariif.category"));
	}

	if ((model.getCsmrInfo().getDepositAmount() == null)&& !Utility.isEnvPrefixAvailable(UserSession.getCurrent().getOrganisation(), "PSCL")) {
	    entityValidationContext
		    .addOptionConstraint(session.getMessage("water.dataentry.validation.security.deposit"));
	}

	String panCheck = model.getCsmrInfo().getCsTaxPayerFlag();

	if ("Y".equalsIgnoreCase(panCheck)) {
	    /*
	     * if (model.getCsmrInfo().getCsPanNo() == null ||
	     * model.getCsmrInfo().getCsPanNo().isEmpty()) { entityValidationContext
	     * .addOptionConstraint(session.getMessage(
	     * "water.dataentry.validation.pan.number")); }
	     */
	}
	if (model.getCsmrInfo().getPcDate() != null && model.getMeterData().getMeterInstallationDate() != null) {
	    Organisation org = UserSession.getCurrent().getOrganisation();
	    TbCsmrInfoDTO waterDTO = model.getCsmrInfo();
	    List<String> years = Utility.getNoOfFinancialYearIncludingCurrent(7);

        String[] year = years.get(6).split("-");
        Calendar c = Calendar.getInstance();
        c.set(Integer.parseInt(year[0]), Calendar.APRIL, 1, 0, 0, 0);
	    Calendar currentDate = Calendar.getInstance();

	    Date scheduleDate = null;
	    if (UtilityService.compareDateField(c.getTime(), waterDTO.getPcDate())) {
		scheduleDate = waterDTO.getPcDate();
	    } else {
	    	scheduleDate = c.getTime();
	    }

	    String meterType = CommonMasterUtility.getNonHierarchicalLookUpObject(waterDTO.getCsMeteredccn(), org)
		    .getLookUpCode();
	    List<TbWtBillScheduleEntity> tbWtBillScheduleEntity = tbWtBillScheduleJpaRepository
		    .getBillScheduleFromToYear(scheduleDate, org.getOrgid(), meterType, new Date());
	    if (tbWtBillScheduleEntity == null || tbWtBillScheduleEntity.isEmpty()) {
		entityValidationContext.addOptionConstraint(session.getMessage("water.validation.schedule.bill"));
	    }
	}

	String existingConsumer = model.getNewConnectionDto().getExistingConsumerNumber();
	if ("Y".equalsIgnoreCase(existingConsumer)) {
	    List<String> ccnList = new ArrayList<>();
	    List<TbKLinkCcnDTO> ccnDTOList = model.getCsmrInfo().getLinkDetails();
	    for (TbKLinkCcnDTO ccnDTO : ccnDTOList) {
		ccnList.add(ccnDTO.getLcOldccn());
		if (ccnDTO.getLcOldccn() != null || !ccnDTO.getLcOldccn().isEmpty()) {
		    Organisation org = UserSession.getCurrent().getOrganisation();
		    long orgId = org.getOrgid();
		    TbCsmrInfoDTO ccnObj = waterService.fetchConnectionDetailsByConnNo(ccnDTO.getLcOldccn(), orgId);
		    if (ccnObj.getCsIdn() == 0) {
			entityValidationContext.addOptionConstraint(
				session.getMessage("water.valid.check.connection.number") + " " + ccnDTO.getLcOldccn());
		    }

		} else {
		    entityValidationContext.addOptionConstraint(session.getMessage("water.valid.connection.number"));
		}

	    }
	    boolean duplicateFlag = false;
	    Set<String> ccnSet = new HashSet<>();
	    for (String ccn : ccnList) {
		if (ccnSet.add(ccn) == false) {
		    duplicateFlag = true;
		}
	    }
	    if (duplicateFlag) {
		entityValidationContext
			.addOptionConstraint(session.getMessage("water.dataentry.duplicate.existing.connection"));
	    }

	}

	String tariffCategory = CommonMasterUtility.getNonHierarchicalLookUpObject(model.getCsmrInfo().getTrmGroup1(),
		UserSession.getCurrent().getOrganisation()).getLookUpCode();
	if ((model.getCsmrInfo().getCsNoofusers() == null) || (model.getCsmrInfo().getCsNoofusers() == 0L)) {
	    if ("H".equalsIgnoreCase(tariffCategory)) {
		entityValidationContext.addOptionConstraint(session.getMessage("water.dataentry.validation.noRoom"));
	    }
	    if ("RFC".equalsIgnoreCase(tariffCategory)) {
		entityValidationContext.addOptionConstraint(session.getMessage("water.dataentry.validation.noTable"));
	    }
	}
    }

    public boolean checkDate(Date fromDate, Date toDate, Long noOfDays) {
	boolean flag = false;
	SimpleDateFormat df = new SimpleDateFormat("dd/MM/yyyy");
	String fromD = df.format(fromDate);
	String toD = df.format(toDate);
	long diff;
	try {
	    diff = df.parse(toD).getTime() - df.parse(fromD).getTime();
	    Long days = TimeUnit.DAYS.convert(diff, TimeUnit.MILLISECONDS);
	    long range = TimeUnit.DAYS.toDays(days);
	    if (range > noOfDays)
		flag = true;
	} catch (ParseException e) {
	    throw new FrameworkException("Error in Parsing Date in checkDate()", e);
	}

	return flag;
    }
}
