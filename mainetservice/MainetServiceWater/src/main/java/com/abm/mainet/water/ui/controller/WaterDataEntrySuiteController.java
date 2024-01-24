package com.abm.mainet.water.ui.controller;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collector;
import java.util.stream.Collectors;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.CollectionUtils;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.json.MappingJackson2JsonView;

import com.abm.mainet.common.constant.MainetConstants;
import com.abm.mainet.common.constant.PrefixConstants;
import com.abm.mainet.common.constant.ServiceEndpoints;
import com.abm.mainet.common.domain.Department;
import com.abm.mainet.common.domain.FinancialYear;
import com.abm.mainet.common.domain.LocationMasEntity;
import com.abm.mainet.common.domain.Organisation;
import com.abm.mainet.common.dto.ApplicantDetailDTO;
import com.abm.mainet.common.dto.JsonViewObject;
import com.abm.mainet.common.integration.dto.TbBillDet;
import com.abm.mainet.common.integration.dto.TbBillMas;
import com.abm.mainet.common.master.dto.TbTaxMas;
import com.abm.mainet.common.master.service.DepartmentService;
import com.abm.mainet.common.master.service.TbTaxMasService;
import com.abm.mainet.common.service.IFinancialYearService;
import com.abm.mainet.common.service.ILocationMasService;
import com.abm.mainet.common.ui.controller.AbstractFormController;
import com.abm.mainet.common.ui.model.JQGridResponse;
import com.abm.mainet.common.utility.ApplicationSession;
import com.abm.mainet.common.utility.CommonMasterUtility;
import com.abm.mainet.common.utility.LookUp;
import com.abm.mainet.common.utility.UserSession;
import com.abm.mainet.common.utility.Utility;
import com.abm.mainet.common.utility.UtilityService;
import com.abm.mainet.water.domain.PlumberMaster;
import com.abm.mainet.water.dto.BillingScheduleDto;
import com.abm.mainet.water.dto.NewWaterConnectionReqDTO;
import com.abm.mainet.water.dto.TbCsmrInfoDTO;
import com.abm.mainet.water.dto.TbKLinkCcnDTO;
import com.abm.mainet.water.dto.TbWtBillSchedule;
import com.abm.mainet.water.dto.TbWtBillScheduleDetail;
import com.abm.mainet.water.dto.WaterDataEntrySearchDTO;
import com.abm.mainet.water.service.NewWaterConnectionService;
import com.abm.mainet.water.service.TbWtBillMasService;
import com.abm.mainet.water.service.TbWtBillScheduleService;
import com.abm.mainet.water.service.WaterCommonService;
import com.abm.mainet.water.ui.model.WaterDataEntrySuiteModel;

@Controller
@RequestMapping("WaterDataEntrySuite.html")
public class WaterDataEntrySuiteController extends AbstractFormController<WaterDataEntrySuiteModel> {

	private static final Logger LOGGER = LoggerFactory.getLogger(WaterDataEntrySuiteController.class);
    @Resource
    private ILocationMasService iLocationMasService;

    @Autowired
    private DepartmentService departmentService;

    @Autowired
    private TbWtBillScheduleService tbWtBillScheduleService;

    @Autowired
    private IFinancialYearService iFinancialYearService;

    @Autowired
    private TbTaxMasService tbTaxMasService;

    @Autowired
    private TbWtBillMasService tbWtBillMasService;

    @Autowired
    private NewWaterConnectionService newWaterConnectionService;
    
    @Autowired
	WaterCommonService waterCommonService;

    @RequestMapping(method = { RequestMethod.POST, RequestMethod.GET })
    public ModelAndView index(HttpServletRequest httpServletRequest) throws Exception {
        sessionCleanup(httpServletRequest);
        List<LookUp> locList = getModel().getLocation();
        Organisation org = UserSession.getCurrent().getOrganisation();
        final Department dept = departmentService.getDepartment(MainetConstants.WATER_DEPARTMENT_CODE,
                MainetConstants.STATUS.ACTIVE);
        List<LocationMasEntity> location = iLocationMasService.findWZMappedLocationByOrgIdAndDeptId(org.getOrgid(),
                dept.getDpDeptid());
        getModel().setDeptId(dept.getDpDeptid());
        getModel().setCommonHelpDocs("WaterDataEntrySuite.html");
        if (location != null && !location.isEmpty()) {
            location.forEach(loc -> {
                LookUp lookUp = new LookUp();
                lookUp.setLookUpId(loc.getLocId());
                lookUp.setDescLangFirst(loc.getLocNameEng());
                lookUp.setDescLangSecond(loc.getLocNameReg());
                locList.add(lookUp);
            });
        }

        final List<LookUp> monthlookup = CommonMasterUtility.getListLookup(PrefixConstants.WATERMODULEPREFIX.MONTH,
                UserSession.getCurrent().getOrganisation());

        for (final LookUp mon : monthlookup) {
            getModel().getMonthprefix().put(Integer.valueOf(mon.getLookUpCode()), mon.getLookUpDesc());
        }
        return defaultResult();
    }

    @RequestMapping(method = { RequestMethod.POST, RequestMethod.GET }, params = "proceedNext")
    public ModelAndView proceed(HttpServletRequest httpServletRequest) throws Exception {
        bindModel(httpServletRequest);

        WaterDataEntrySuiteModel suitModel = this.getModel();
        UserSession session = UserSession.getCurrent();
        NewWaterConnectionReqDTO connectionDTO = suitModel.getNewConnectionDto();
        TbCsmrInfoDTO infoDTO = suitModel.getCsmrInfo();
        ApplicantDetailDTO appDTO = connectionDTO.getApplicantDTO();
        Organisation organisation = new Organisation();
        organisation.setOrgid(UserSession.getCurrent().getOrganisation().getOrgid());
        final Department dept = departmentService.getDepartment(MainetConstants.WATER_DEPARTMENT_CODE,
                MainetConstants.STATUS.ACTIVE);
        getModel().setDeptId(dept.getDpDeptid());
        // suitModel.setBillMasList(tbWtBillMasService.getBillMasByConnectionId(suitModel.getCsmrInfo().getCsIdn()));
        if (suitModel.getBillMasList().isEmpty()) {
            getModel().setSchduleId(new Long(0));
        } else {
            if (suitModel.getBillMasList().get(0).getWtN1() != null)
                getModel().setSchduleId(suitModel.getBillMasList().get(0).getWtN1().longValue());
        }

        connectionDTO.setUserId(session.getEmployee().getEmpId());
        connectionDTO.setOrgId(session.getOrganisation().getOrgid());
        connectionDTO.setLangId((long) session.getLanguageId());
        connectionDTO.setLgIpMac(session.getEmployee().getEmppiservername());

        if (suitModel.getIsConsumerSame().equalsIgnoreCase("Y")) {
            connectionDTO.setIsConsumer("Y");
            infoDTO.setCsName(infoDTO.getCsOname());
            if (infoDTO.getCsOGender() != null && infoDTO.getCsOGender() != 0l) {
                infoDTO.setCsGender(infoDTO.getCsOGender());
            }
            infoDTO.setCsContactno(infoDTO.getCsOcontactno());
            infoDTO.setCsEmail(infoDTO.getCsOEmail());
            infoDTO.setCsAdd(infoDTO.getCsOadd());
            if (infoDTO.getOpincode() != null && !infoDTO.getOpincode().isEmpty()) {
                infoDTO.setCsCpinCode(Long.valueOf(infoDTO.getOpincode()));
            }
            if (appDTO.getAadharNo() != null && !appDTO.getAadharNo().isEmpty()) {
                connectionDTO.setAadhaarNo(appDTO.getAadharNo());
                infoDTO.setCsUid(Long.valueOf(appDTO.getAadharNo()));
            }
        }
        if (suitModel.getIsBillingSame().equalsIgnoreCase("Y")) {
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
        infoDTO.setPropertyNo(connectionDTO.getPropertyNo());
        connectionDTO.setCsmrInfo(infoDTO);

        if (getModel().validateData()) {
            final ModelAndView mv = new ModelAndView("WaterDataEntrySuiteCreate", MainetConstants.FORM_NAME, suitModel);
            if (suitModel.getBindingResult() != null) {
                mv.addObject(BindingResult.MODEL_KEY_PREFIX + MainetConstants.FORM_NAME, suitModel.getBindingResult());
            }
            return mv;
        }
        Organisation org = UserSession.getCurrent().getOrganisation();
        TbCsmrInfoDTO waterDTO = getModel().getCsmrInfo();
        getModel().setSchedule(new ArrayList<>());
        getModel().setSortedschedule(new ArrayList<>());
        List<String> years = Utility.getNoOfFinancialYearIncludingCurrent(7);

        String[] year = years.get(6).split("-");
        Calendar c = Calendar.getInstance();
        /*
         * c.setTime(new Date()); c.set(Calendar.YEAR, Integer.parseInt(year[0])); c.set(Calendar.MINUTE, 0);
         * c.set(Calendar.SECOND, 0); c.set(Calendar.HOUR_OF_DAY, 0);
         */
        c.set(Integer.parseInt(year[0]), Calendar.APRIL, 1, 0, 0, 0);

        Calendar pc = Calendar.getInstance();
        pc.setTime(waterDTO.getPcDate());
        pc.set(Calendar.MINUTE, 0);
        pc.set(Calendar.SECOND, 0);
        pc.set(Calendar.HOUR_OF_DAY, 0);

        Date scheduleDate = null;
		if (getModel().getBillMasList() != null && !getModel().getBillMasList().isEmpty()
				&& getModel().getModeType().equalsIgnoreCase(MainetConstants.FlagE)) {
			scheduleDate = getModel().getBillMasList().get(getModel().getBillMasList().size() - 1).getBmFromdt();

		}else {
			 if (UtilityService.compareDateField(c.getTime(), pc.getTime())) {
	                scheduleDate = pc.getTime();
	            } else {
	                scheduleDate = c.getTime();
	            }
		}

        String meterType = CommonMasterUtility.getNonHierarchicalLookUpObject(waterDTO.getCsMeteredccn(), org)
                .getLookUpCode();
        List<TbWtBillSchedule> tbWtBillScheduleEntity = tbWtBillScheduleService.getBillScheduleFromToYear(scheduleDate,
                org.getOrgid(), meterType, new Date());
        final List<LookUp> monthlookup = CommonMasterUtility.getListLookup(PrefixConstants.WATERMODULEPREFIX.MONTH,
                UserSession.getCurrent().getOrganisation());

        for (final LookUp mon : monthlookup) {
            getModel().getMonthprefix().put(Integer.valueOf(mon.getLookUpCode()), mon.getLookUpDesc());
        }
        if (getModel().getModeType().equalsIgnoreCase("C")) {
            getModel().setSortedschedule(new ArrayList<>());
            getModel().setBillScheduleDetail(new ArrayList<>());
            getModel().setSchduleId(new Long(0));
            getModel().getBillMasList().clear();
            getModel().setErrorList(new ArrayList<>());
        }
        setBillingSchedule(org, waterDTO, getModel().getMonthprefix(), tbWtBillScheduleEntity, "N");

        int differenceValue = 0;
        String previousValue = null;
        int counter = 0;
        for (LookUp lookup : getModel().getSortedschedule()) {
            String[] financialYear = lookup.getDescLangFirst().split("-");
            String currentValue = financialYear[0];
            if (previousValue == null) {
                previousValue = currentValue;
            }
            differenceValue = Integer.parseInt(currentValue) - Integer.parseInt(previousValue);
            if (differenceValue > 1) {
                counter = counter + 1;
            }
            previousValue = currentValue;
        }
        
        if(getModel().getBillMasList() != null && !getModel().getBillMasList().isEmpty()) {
        	addbillMasScheduleEdit(getModel().getBillMasList(), getModel().getCsmrInfo());
        }

        if (counter >= 1) {
            suitModel.getBindingResult().addError(new ObjectError(MainetConstants.BLANK, ApplicationSession
                    .getInstance().getMessage("water.dataentry.validation.bill.schedule.not.defined")));
            final ModelAndView mv = new ModelAndView("WaterDataEntrySuiteCreateValidn", MainetConstants.FORM_NAME,
                    getModel());
            mv.addObject(BindingResult.MODEL_KEY_PREFIX + MainetConstants.FORM_NAME, suitModel.getBindingResult());
            return mv;
        }

        return new ModelAndView("WaterBillDetailsEntry", MainetConstants.FORM_NAME, getModel());
    }

    private void setBillingSchedule(Organisation org, TbCsmrInfoDTO waterDTO, final Map<Integer, String> monthprefix,
            List<TbWtBillSchedule> tbWtBillScheduleEntity, String type) throws Exception {
        if ((tbWtBillScheduleEntity != null) && !tbWtBillScheduleEntity.isEmpty()) {
            long ward1 = -1;
            long ward2 = -1;
            long ward3 = -1;
            long ward4 = -1;
            long ward5 = -1;

            long Schward1 = -1;
            long Schward2 = -1;
            long Schward3 = -1;
            long Schward4 = -1;
            long Schward5 = -1;
            for (final TbWtBillSchedule billsch : tbWtBillScheduleEntity) {
                if (MainetConstants.Common_Constant.NUMBER.ONE.equals(billsch.getDependsOnType())) {
                    if ((billsch.getCodIdWwz1() != null) && (billsch.getCodIdWwz1() > -1)) {
                        if (waterDTO.getCodDwzid1() != null) {
                            ward1 = waterDTO.getCodDwzid1().longValue();
                        }
                        if ((billsch.getCodIdWwz2() != null) && (billsch.getCodIdWwz2() > -1)) {
                            if (waterDTO.getCodDwzid2() != null) {
                                ward2 = waterDTO.getCodDwzid2().longValue();
                            }
                            if ((billsch.getCodIdWwz3() != null) && (billsch.getCodIdWwz3() > -1)) {
                                if (waterDTO.getCodDwzid3() != null) {
                                    ward3 = waterDTO.getCodDwzid3().longValue();
                                }
                                if ((billsch.getCodIdWwz4() != null) && (billsch.getCodIdWwz4() > -1)) {
                                    if (waterDTO.getCodDwzid4() != null) {
                                        ward4 = waterDTO.getCodDwzid4().longValue();
                                    }
                                    if ((billsch.getCodIdWwz5() != null) && (billsch.getCodIdWwz5() > -1)) {
                                        if (waterDTO.getCodDwzid5() != null) {
                                            ward5 = waterDTO.getCodDwzid5().longValue();
                                        }
                                    }
                                }
                            }
                        }
                    }

                    if (billsch.getCodIdWwz1() != null) {
                        Schward1 = billsch.getCodIdWwz1().longValue();
                    }
                    if (billsch.getCodIdWwz2() != null) {
                        Schward2 = billsch.getCodIdWwz2().longValue();
                    }
                    if (billsch.getCodIdWwz3() != null) {
                        Schward3 = billsch.getCodIdWwz3().longValue();
                    }
                    if (billsch.getCodIdWwz4() != null) {
                        Schward4 = billsch.getCodIdWwz4().longValue();
                    }
                    if (billsch.getCodIdWwz5() != null) {
                        Schward5 = billsch.getCodIdWwz5().longValue();
                    }
                    if (((Schward1 == -1) || (ward1 == Schward1)) && ((Schward2 == -1) || (ward2 == Schward2))
                            && ((Schward3 == -1) || (ward3 == Schward3)) && ((Schward4 == -1) || (ward4 == Schward4))
                            && ((Schward5 == -1) || (ward5 == Schward5))) {
                        for (final TbWtBillScheduleDetail schDetail : billsch.getBillScheduleDetail()) {
                            setSchedule(org, monthprefix, billsch, schDetail, type, waterDTO);
                        }
                    }
                } else if (MainetConstants.Common_Constant.NUMBER.TWO.equals(billsch.getDependsOnType())) {
                    if ((waterDTO.getCsCcncategory1() != null)
                            && waterDTO.getCsCcncategory1().equals(billsch.getCnsCcgid1())) {
                        for (final TbWtBillScheduleDetail schDetail : billsch.getBillScheduleDetail()) {
                            setSchedule(org, monthprefix, billsch, schDetail, type, waterDTO);
                        }
                    }
                }
            }
        }

        final List<FinancialYear> finYearAll = iFinancialYearService.getAllFinincialYear();
        Collections.reverse(finYearAll);

        finYearAll.forEach(finyear -> {
            if ("N".equals(type)) {
                getModel().getSchedule().forEach(sch -> {
                    if (finyear.getFaYear() == sch.getLookUpParentId()) {
                        getModel().getSortedschedule().add(sch);
                    }
                });
            }

            /*
             * else { getModel().getBillScheduleDetail().forEach(sch1 -> { if (finyear.getFaYear() == sch1.getYearId()) {
             * getModel().getSortedbillSchedule().add(sch1); } }); }
             */
        });

        List<TbWtBillScheduleDetail> schedule = new ArrayList<>(0);
        getModel().getSortedschedule().forEach(detlookup -> {
            getModel().getBillScheduleDetail().forEach(detSche -> {
                if (detSche.getDetId().equals(detlookup.getLookUpId())) {
                    schedule.add(detSche);
                }
            });
        });

        getModel().setBillScheduleDetail(new ArrayList<>());
        getModel().getBillScheduleDetail().addAll(schedule);

    }

    private void setSchedule(Organisation org, final Map<Integer, String> monthprefix, final TbWtBillSchedule billsch,
            final TbWtBillScheduleDetail schDetail, String type, TbCsmrInfoDTO waterDTO) throws Exception {
        FinancialYear finYear = iFinancialYearService.getFinincialYearsById(billsch.getCnsYearid(), org.getOrgid());
        String financialYear = Utility.getFinancialYearFromDate(finYear.getFaFromDate());

        Calendar schDate = Calendar.getInstance();
        schDate.setTime(finYear.getFaFromDate());
        schDate.set(Calendar.MONTH, schDetail.getCnsToDate().intValue() - 1); // January = 0, December = 11

        int schDateValue = Utility.getFinancialFormatedDateFromDate(schDate.getTime(), "yyyyMM");
        int currentDateValue = Utility.getFormatedDateFromDate(Calendar.getInstance().getTime(), "yyyyMM");

        int csmrPcInstFinancialYearWithMonth = Utility.getFormatedDateFromDate(waterDTO.getPcDate(), "yyyyMM");
        final String billFrequency = CommonMasterUtility
                .getNonHierarchicalLookUpObject(billsch.getCnsCpdid(), org).getLookUpCode();
        String currentFinYear = Utility.getFinancialYearFromDate(new Date());
        LookUp currentBillOpenLookUp = null;
        try {
        	currentBillOpenLookUp = CommonMasterUtility.getValueFromPrefixLookUp("CBO", "WEV", UserSession.getCurrent().getOrganisation());
        }catch (Exception e) {
        	
		}
		if ((currentBillOpenLookUp != null && StringUtils.isNotBlank(currentBillOpenLookUp.getOtherField())
				&& StringUtils.equals(currentBillOpenLookUp.getOtherField(), MainetConstants.FlagY))
				&& (((StringUtils.equals(MainetConstants.NewWaterServiceConstants.YEARLY_BILL_FREQUENCY, billFrequency)
						|| (financialYear.equals(currentFinYear)) && (schDateValue <= currentDateValue))
						&& (csmrPcInstFinancialYearWithMonth <= schDateValue))
						|| ((!financialYear.equals(currentFinYear)) && (schDetail.getCnsFromDate() == 4))
						|| (!financialYear.equals(currentFinYear) && getModel().getModeType().equalsIgnoreCase("E")))) {

            if ((!financialYear.equals(currentFinYear)) && (schDetail.getCnsFromDate() == 4) && !getModel().getModeType().equalsIgnoreCase("E")) {
                schDetail.setCnsToDate(3l);
            }

            LookUp look = new LookUp();
            look.setLookUpId(schDetail.getDetId());
            look.setDescLangFirst(financialYear + "->" + monthprefix.get(schDetail.getCnsFromDate().intValue()) + " - "
                    + monthprefix.get(schDetail.getCnsToDate().intValue()));
            look.setLookUpParentId(finYear.getFaYear());
            schDetail.setYearId(billsch.getCnsYearid());
            getModel().getSchedule().add(look);
            getModel().getBillScheduleDetail().add(schDetail);
		} else if (((financialYear.equals(currentFinYear)) && (schDateValue < currentDateValue)
				&& (csmrPcInstFinancialYearWithMonth <= schDateValue))
				|| ((!financialYear.equals(currentFinYear)) && (schDetail.getCnsFromDate() == 4))
				|| (!financialYear.equals(currentFinYear) && getModel().getModeType().equalsIgnoreCase("E"))) {
			 if ((!financialYear.equals(currentFinYear)) && (schDetail.getCnsFromDate() == 4) && !getModel().getModeType().equalsIgnoreCase("E")) {
	                schDetail.setCnsToDate(3l);
	            }

	            LookUp look = new LookUp();
	            look.setLookUpId(schDetail.getDetId());
	            look.setDescLangFirst(financialYear + "->" + monthprefix.get(schDetail.getCnsFromDate().intValue()) + " - "
	                    + monthprefix.get(schDetail.getCnsToDate().intValue()));
	            look.setLookUpParentId(finYear.getFaYear());
	            schDetail.setYearId(billsch.getCnsYearid());
	            getModel().getSchedule().add(look);
	            getModel().getBillScheduleDetail().add(schDetail);
        }

    }

    @RequestMapping(params = "financialYear", method = RequestMethod.POST)
    public ModelAndView yearFrequancy(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse)
            throws Exception {
        bindModel(httpServletRequest);
        getModel().setFinancialYearMap(new HashMap<>());
        TbCsmrInfoDTO waterDTO = getModel().getCsmrInfo();
        Organisation org = UserSession.getCurrent().getOrganisation();
        this.getModel().getBillMasList().clear();
        if (getModel().getSchduleId() != null && getModel().getSchduleId() > 0) {

            String metertype = CommonMasterUtility.getNonHierarchicalLookUpObject(waterDTO.getCsMeteredccn(), org)
                    .getLookUpCode();
            Long taxSubCategoryId = setTaxSubCategoryForMeterAndNonMeter(org, metertype);
            final LookUp chargeApplicableAt = CommonMasterUtility.getValueFromPrefixLookUp(
                    PrefixConstants.NewWaterServiceConstants.BILL, PrefixConstants.NewWaterServiceConstants.CAA,
                    UserSession.getCurrent().getOrganisation());
            final List<TbTaxMas> taxMaster = new ArrayList<>(0);
            taxMaster.addAll(
                    tbTaxMasService.fetchAllIndependentTaxes(UserSession.getCurrent().getOrganisation().getOrgid(),
                            getModel().getDeptId(), chargeApplicableAt.getLookUpId(), taxSubCategoryId, 0L));
            taxMaster
                    .addAll(tbTaxMasService.fetchAllDepenentTaxes(UserSession.getCurrent().getOrganisation().getOrgid(),
                            getModel().getDeptId(), chargeApplicableAt.getLookUpId(), taxSubCategoryId, 0L));
            if(Utility.isEnvPrefixAvailable(UserSession.getCurrent().getOrganisation(), MainetConstants.ENV_PSCL)){
            final LookUp chargeApplicableAtBillRecpt = CommonMasterUtility.getValueFromPrefixLookUp(
                    PrefixConstants.NewWaterServiceConstants.BILL_RECEIPT, PrefixConstants.NewWaterServiceConstants.CAA,
                    UserSession.getCurrent().getOrganisation());
            taxMaster.addAll(
                    tbTaxMasService.fetchAllIndependentTaxes(UserSession.getCurrent().getOrganisation().getOrgid(),
                            getModel().getDeptId(), chargeApplicableAtBillRecpt.getLookUpId(), taxSubCategoryId, 0L));
            }
            if(Utility.isEnvPrefixAvailable(UserSession.getCurrent().getOrganisation(), MainetConstants.ENV_PSCL)){
                final LookUp chargeApplicableAtBillRecpt = CommonMasterUtility.getValueFromPrefixLookUp(
                        PrefixConstants.NewWaterServiceConstants.ARREAR, PrefixConstants.NewWaterServiceConstants.CAA,
                        UserSession.getCurrent().getOrganisation());
                taxMaster.addAll(
                        tbTaxMasService.fetchAllIndependentTaxes(UserSession.getCurrent().getOrganisation().getOrgid(),
                                getModel().getDeptId(), chargeApplicableAtBillRecpt.getLookUpId(), taxSubCategoryId, 0L));
                }
            List<TbTaxMas> taxMasterList = new ArrayList<>(0);
            taxMaster.forEach(tax -> {
                String taxCategory1 = CommonMasterUtility.getHierarchicalLookUp(tax.getTaxCategory1(), org)
                        .getLookUpCode();
                if (!PrefixConstants.TAX_CATEGORY.ADVANCE.equals(taxCategory1)
                        && !PrefixConstants.TAX_CATEGORY.REBATE.equals(taxCategory1)) {
                    taxMasterList.add(tax);
                }
            });

            this.getModel().setTaxesMaster(taxMasterList);

            List<TbBillMas> billMasList = new ArrayList<>();

            Long yearId = tbWtBillScheduleService.findScheduleByDet(getModel().getSchduleId(), org.getOrgid())
                    .getYearId();

            Long fromDate = tbWtBillScheduleService.findScheduleByDet(getModel().getSchduleId(), org.getOrgid())
                    .getCnsFromDate();

            Long toDate = tbWtBillScheduleService.findScheduleByDet(getModel().getSchduleId(), org.getOrgid())
                    .getCnsToDate();

            final String faYearStringModel = Utility.getCompleteFinancialYear(
                    iFinancialYearService.getFinincialYearsById(yearId, org.getOrgid()).getFaFromDate(),
                    iFinancialYearService.getFinincialYearsById(yearId, org.getOrgid()).getFaToDate());

            final Date startDateModel = Utility.dateFromMonth(faYearStringModel, fromDate.intValue(),
                    MainetConstants.FINYEAR_DATE.FIRST);

            final Date endDateModel = Utility.dateFromMonth(faYearStringModel, toDate.intValue(),
                    MainetConstants.FINYEAR_DATE.LAST);
            getModel().getBillScheduleDetail().forEach(shedule -> {

                final FinancialYear finYearData = iFinancialYearService.getFinincialYearsById(shedule.getYearId(),
                        org.getOrgid());

                final String faYearString = Utility.getCompleteFinancialYear(finYearData.getFaFromDate(),
                        finYearData.getFaToDate());

                final Date startDate = Utility.dateFromMonth(faYearString, shedule.getCnsFromDate().intValue(),
                        MainetConstants.FINYEAR_DATE.FIRST);
                final Date endDate = Utility.dateFromMonth(faYearString, shedule.getCnsToDate().intValue(),
                        MainetConstants.FINYEAR_DATE.LAST);

                if ((startDate.after(startDateModel)) || startDate.equals(startDateModel)) {

                    TbBillMas billMas = new TbBillMas();
                    List<TbBillDet> billDetList = new ArrayList<>();
                    taxMasterList.forEach(tax -> {
                        TbBillDet billDet = new TbBillDet();
                        billDet.setTaxId(tax.getTaxId());
                        billDet.setTaxCategory(tax.getTaxCategory1());
                        billDet.setCollSeq(tax.getCollSeq());
                        billDet.setBdCsmp(new BigDecimal(0).setScale(2, BigDecimal.ROUND_UP));
                        billDetList.add(billDet);
                        billMas.setTbWtBillDet(billDetList);
                    });

                    String value = faYearString + "->"
                            + getModel().getMonthprefix().get(shedule.getCnsFromDate().intValue()) + " - "
                            + getModel().getMonthprefix().get(shedule.getCnsToDate().intValue());
                    billMas.setBmYear(shedule.getYearId());
                    billMas.setBmFromdt(startDate);
                    billMas.setBmTodt(endDate);
                    billMas.setBmBilldt(startDate);
                    billMas.setBmGenDes(value);
                    billMas.setBmDuedate(getBillDueDate(org, metertype, endDate,startDate));
                    billMas.setWtN1(BigDecimal.valueOf(shedule.getDetId()));

                    billMasList.add(billMas);
                }
            });
            this.getModel().setBillMasList(billMasList);
        } else {
            this.getModel().setBillMasList(new ArrayList<>(0));

        }
        if (this.getModel().getBillMasList().isEmpty()) {
            this.getModel().getBindingResult().addError(new ObjectError(MainetConstants.BLANK,
                    ApplicationSession.getInstance().getMessage("Please Check the Defined Taxes and Bill scheduling")));
            final ModelAndView mv = new ModelAndView("WaterBillDetailsEntryValidn", MainetConstants.FORM_NAME,
                    getModel());
            mv.addObject(BindingResult.MODEL_KEY_PREFIX + MainetConstants.FORM_NAME,
                    this.getModel().getBindingResult());
            return mv;
        }
        return new ModelAndView("WaterBillDetailsEntry", MainetConstants.FORM_NAME, getModel());
    }

    /**
     * @param orgnisation
     * @param meterType
     * @param dueDate
     * @return
     */
    private Date getBillDueDate(final Organisation orgnisation, final String meterType, Date endDate,Date startDate) {
        List<LookUp> billingMethod = null;
        billingMethod = CommonMasterUtility.getListLookup(PrefixConstants.WATERMODULEPREFIX.DDM, orgnisation);
        LookUp lookUpVal = null;
        if ((billingMethod != null) && !billingMethod.isEmpty()) {
            for (final LookUp lookUp : billingMethod) {
                if (lookUp.getDefaultVal().equals(PrefixConstants.IsLookUp.STATUS.YES)) {
                    lookUpVal = lookUp;
                    break;
                }
            }
        }
        if ((lookUpVal != null)
                && MainetConstants.NewWaterServiceConstants.BILL_SCHEDULE_DATE.equals(lookUpVal.getLookUpCode())) {
            final Calendar cal = tbWtBillMasService.getBillingScheduleDueDate(meterType, orgnisation, endDate);
            return cal.getTime();
        }else if ((lookUpVal != null)
                && MainetConstants.NewWaterServiceConstants.BILL_SCHEDULE_START_DATE.equals(lookUpVal.getLookUpCode())) {
            final Calendar cal = tbWtBillMasService.getBillingScheduleDueDate(meterType, orgnisation, startDate);
            return cal.getTime();
        }else if ((lookUpVal != null)
                && MainetConstants.NewWaterServiceConstants.BILL_SCHEDULE_END_DATE.equals(lookUpVal.getLookUpCode())) {
            final Calendar cal = tbWtBillMasService.getBillingScheduleDueDate(meterType, orgnisation, endDate);
            return cal.getTime();
        }
        return null;
    }

    @RequestMapping(params = "back", method = RequestMethod.POST)
    public ModelAndView backToPropDet(HttpServletRequest request) {
        bindModel(request);
        LookUp propNoOptional = null;
		try {
			propNoOptional = CommonMasterUtility.getValueFromPrefixLookUp("PNO", "WEV", UserSession.getCurrent().getOrganisation());
		}catch (Exception exception) {
			LOGGER.error("No prefix found for WEV(PNO)");
		}
		if(propNoOptional != null) {
			getModel().setPropNoOptionalFlag(propNoOptional.getOtherField());
		}else {
			getModel().setPropNoOptionalFlag(MainetConstants.FlagN);
		}
        if (this.getModel().getModeType().equalsIgnoreCase("C")) {
            getModel().setSortedschedule(new ArrayList<>());
            getModel().setBillScheduleDetail(new ArrayList<>());
            getModel().setSchduleId(new Long(0));
            getModel().getBillMasList().clear();
            getModel().setErrorList(new ArrayList<>());
        }
        return new ModelAndView("WaterDataEntrySuiteCreate", MainetConstants.FORM_NAME, getModel());
    }

    private Long setTaxSubCategoryForMeterAndNonMeter(final Organisation orgnisation, final String meterType) {
        Long taxSubCategoryId = null;
        final List<LookUp> taxSubCategory = CommonMasterUtility.getNextLevelData(PrefixConstants.LookUpPrefix.TAC,
                MainetConstants.INDEX.TWO, orgnisation.getOrgid());
        if (MainetConstants.NewWaterServiceConstants.METER.equals(meterType)) {
            for (final LookUp taxSubCat : taxSubCategory) {
                if (PrefixConstants.TAX_SUBCATEGORY.WATER_TAX_NONMETER.equals(taxSubCat.getLookUpCode())) {
                    taxSubCategoryId = taxSubCat.getLookUpId();
                }
            }
        } else {
            for (final LookUp taxSubCat : taxSubCategory) {
                if (PrefixConstants.TAX_SUBCATEGORY.WATER_TAX_METER.equals(taxSubCat.getLookUpCode())) {
                    taxSubCategoryId = taxSubCat.getLookUpId();
                }
            }
        }
        return taxSubCategoryId;
    }

    @RequestMapping(params = "deletedLinkCCnRow", method = RequestMethod.POST)
    public @ResponseBody void deleteUnitTableRow(HttpServletRequest httpServletRequest,
            HttpServletResponse httpServletResponse, @RequestParam(value = "deletedRow") int deletedRowCount) {
        this.getModel().bind(httpServletRequest);
        TbKLinkCcnDTO detDto = this.getModel().getCsmrInfo().getLinkDetails().get(deletedRowCount);
        if (detDto != null) {
            detDto.setIsDeleted("Y");
            // this.getModel().getCsmrInfo().getLinkDetails().remove(detDto);
        }
    }

    @RequestMapping(params = "getPropertyDetails", method = RequestMethod.POST)
    public ModelAndView getPropertyDetails(HttpServletRequest request) {
        bindModel(request);
        WaterDataEntrySuiteModel model = this.getModel();
        NewWaterConnectionReqDTO reqDTO = model.getNewConnectionDto();
        reqDTO.setOrgId(UserSession.getCurrent().getOrganisation().getOrgid());
        TbCsmrInfoDTO infoDTO = model.getCsmrInfo();
        TbCsmrInfoDTO propInfoDTO = model.getPropertyDetailsByPropertyNumber(reqDTO);
        String respMsg = "";
        if (propInfoDTO != null && propInfoDTO.getPropertyNo() != null) {
            infoDTO.setCsOname(propInfoDTO.getCsOname());
            infoDTO.setCsOcontactno(propInfoDTO.getCsOcontactno());
            infoDTO.setCsOEmail(propInfoDTO.getCsOEmail());
            infoDTO.setOpincode(propInfoDTO.getOpincode());
            infoDTO.setCsOadd(propInfoDTO.getCsOadd());
            if (propInfoDTO.getCsOGender() != null && propInfoDTO.getCsOGender() != 0l) {
                infoDTO.setCsOGender(propInfoDTO.getCsOGender());
            }
            infoDTO.setPropertyUsageType(propInfoDTO.getPropertyUsageType());
            reqDTO.setApplicantDTO(new ApplicantDetailDTO());
            infoDTO.setPropertyNo(propInfoDTO.getPropertyNo());
        } else {
            respMsg = ApplicationSession.getInstance().getMessage("water.dataentry.validation.property.not.found");
            return new ModelAndView(new MappingJackson2JsonView(), MainetConstants.ERR_MSG, respMsg);
        }
        return new ModelAndView("WaterDataEntrySuiteCreate", MainetConstants.FORM_NAME, getModel());
    }

    @RequestMapping(params = "saveFormWithOutArrears", method = RequestMethod.POST)
    public ModelAndView saveFormWithOutArrears(HttpServletRequest httpServletRequest,
            HttpServletResponse httpServletResponse) {
        getModel().bind(httpServletRequest);
        WaterDataEntrySuiteModel model = this.getModel();

        if (model.saveFormWithOutArrears()) {
            return jsonResult(JsonViewObject.successResult(model.getSuccessMessage()));
        }
        return (model.getCustomViewName() == null) || model.getCustomViewName().isEmpty() ? defaultMyResult()
                : customDefaultMyResult(model.getCustomViewName());
    }

    @RequestMapping(params = "addDataEntryDetails", method = RequestMethod.POST)
    public @ResponseBody ModelAndView addDetails(HttpServletRequest httpServletRequest,
            HttpServletResponse httpServletResponse) throws Exception {
        this.sessionCleanup(httpServletRequest);
        getModel().bind(httpServletRequest);

        getModel().setModeType(MainetConstants.FlagC);
        List<LookUp> locList = getModel().getLocation();
        Organisation org = UserSession.getCurrent().getOrganisation();
        final Department dept = departmentService.getDepartment(MainetConstants.WATER_DEPARTMENT_CODE,
                MainetConstants.STATUS.ACTIVE);
        List<LocationMasEntity> location = iLocationMasService.findWZMappedLocationByOrgIdAndDeptId(org.getOrgid(),
                dept.getDpDeptid());
        getModel().setDeptId(dept.getDpDeptid());
        getModel().setCommonHelpDocs("WaterDataEntrySuite.html");
        if (location != null && !location.isEmpty()) {
            location.forEach(loc -> {
                LookUp lookUp = new LookUp();
                lookUp.setLookUpId(loc.getLocId());
                lookUp.setDescLangFirst(loc.getLocNameEng());
                lookUp.setDescLangSecond(loc.getLocNameReg());
                locList.add(lookUp);
            });
        }
        final List<LookUp> monthlookup = CommonMasterUtility.getListLookup(PrefixConstants.WATERMODULEPREFIX.MONTH,
                UserSession.getCurrent().getOrganisation());

        for (final LookUp mon : monthlookup) {
            getModel().getMonthprefix().put(Integer.valueOf(mon.getLookUpCode()), mon.getLookUpDesc());
        }
        return new ModelAndView("WaterDataEntrySuiteCreate", MainetConstants.FORM_NAME, getModel());
    }

    @RequestMapping(params = "edit", method = RequestMethod.POST)
    public ModelAndView editForm(@RequestParam final String rowId,
            @RequestParam(value = MainetConstants.AdvertisingAndHoarding.SAVE_MODE, required = true) String saveMode,
            final HttpServletRequest httpServletRequest)
            throws Exception {
        WaterDataEntrySuiteModel model = this.getModel();
        model.setModeType(saveMode);
        model.bind(httpServletRequest);
        LookUp propNoOptional = null;
		try {
			propNoOptional = CommonMasterUtility.getValueFromPrefixLookUp("PNO", "WEV", UserSession.getCurrent().getOrganisation());
		}catch (Exception exception) {
			LOGGER.error("No prefix found for WEV(PNO)");
		}
		if(propNoOptional != null) {
			getModel().setPropNoOptionalFlag(propNoOptional.getOtherField());
		}else {
			getModel().setPropNoOptionalFlag(MainetConstants.FlagN);
		}
		List<PlumberMaster> allPlumberList = new ArrayList<>();
		allPlumberList = waterCommonService.listofplumber(UserSession.getCurrent().getOrganisation().getOrgid());
		getModel().setPlumberList(allPlumberList);
        // Defect #32992
        String checkEntryFlag = newWaterConnectionService.checkEntryFlag(Long.valueOf(rowId),
                UserSession.getCurrent().getOrganisation().getOrgid());
        if (StringUtils.equals(checkEntryFlag, MainetConstants.MENU.S)
                && StringUtils.equals(saveMode, MainetConstants.MODE_EDIT)) {
            model.addValidationError(
                    "This connection is through new water service. So you are not eligible to edit, you can view only");
            String respMsg = "This connection is through new water service. So you are not eligible to edit, you can view only";
            return new ModelAndView(new MappingJackson2JsonView(), MainetConstants.ERR_MSG, respMsg);
        }

        final Department dept = departmentService.getDepartment(MainetConstants.WATER_DEPARTMENT_CODE,
                MainetConstants.STATUS.ACTIVE);
        getModel().setDeptId(dept.getDpDeptid());
        Organisation org = new Organisation(UserSession.getCurrent().getOrganisation().getOrgid());
        List<TbBillMas> billList = null;
        if (rowId != null && !rowId.isEmpty()) {
            Long csIdn = Long.valueOf(rowId);
            populateModel(model, csIdn, saveMode);
            billList = tbWtBillMasService.getBillMasByConnectionId(csIdn);
            model.setBillMasList(billList);
            BigDecimal totalAmount = BigDecimal.ZERO;
            for (TbBillMas billMas : billList) {
            	if(saveMode.equals(MainetConstants.FlagE) && (billMas.getWtN1() == null || billMas.getWtN1().doubleValue() <= 0)) {
            		 model.addValidationError("Bill is already generated through system for this Connection Number");
                     String respMsg = "Bill is already generated through system for this Connection Number";
                     return new ModelAndView(new MappingJackson2JsonView(), MainetConstants.ERR_MSG, respMsg);
            	}
                final String faYearString = Utility.getFinancialYearFromDate(billMas.getBmFromdt());
                Date fromDate = billMas.getBmFromdt();
                Calendar c = Calendar.getInstance();
                c.setTime(fromDate);
                int startMonth = c.get(Calendar.MONTH) + 1;
                Date endDate = billMas.getBmFromdt();
                c.setTime(endDate);
                int endMonth = c.get(Calendar.MONTH) + 1;
                String value = faYearString + "->" + getModel().getMonthprefix().get(startMonth) + " - "
                        + getModel().getMonthprefix().get(endMonth);
                billMas.setBmGenDes(value);
                BigDecimal arrearAmount = BigDecimal.valueOf(billMas.getBmLastRcptamt());
                totalAmount = totalAmount.add(arrearAmount);
            }
            if (saveMode.equals(MainetConstants.FlagE) && totalAmount.compareTo(BigDecimal.ZERO) > 0) {
                model.addValidationError("Bill is already Paid for this Connection Number");
                String respMsg = "Bill is already Paid for this Connection Number";
                return new ModelAndView(new MappingJackson2JsonView(), MainetConstants.ERR_MSG, respMsg);
            }
            String meterType = CommonMasterUtility
                    .getNonHierarchicalLookUpObject(getModel().getCsmrInfo().getCsMeteredccn(), org).getLookUpCode();
            NewWaterConnectionReqDTO connectionDTO = model.getNewConnectionDto();
            TbCsmrInfoDTO infoDTO = model.getCsmrInfo();
            connectionDTO.setCsmrInfo(infoDTO);

            TbCsmrInfoDTO waterDTO = getModel().getCsmrInfo();
            List<String> years = Utility.getNoOfFinancialYearIncludingCurrent(6);

            String[] year = years.get(5).split("-");
            Calendar c = Calendar.getInstance();
            c.setTime(new Date());
            c.set(Calendar.YEAR, Integer.parseInt(year[0]));
            c.set(Calendar.MINUTE, 0);
            c.set(Calendar.SECOND, 0);
            c.set(Calendar.HOUR_OF_DAY, 0);

            Calendar pc = Calendar.getInstance();
            pc.setTime(waterDTO.getPcDate());
            pc.set(Calendar.MINUTE, 0);
            pc.set(Calendar.SECOND, 0);
            pc.set(Calendar.HOUR_OF_DAY, 0);
            Date scheduleDate = null;
			if (getModel().getBillMasList() != null && !getModel().getBillMasList().isEmpty()
					&& getModel().getModeType().equalsIgnoreCase(MainetConstants.FlagE)) {
				scheduleDate = getModel().getBillMasList().get(getModel().getBillMasList().size() - 1).getBmFromdt();

			}else {
				 if (UtilityService.compareDateField(c.getTime(), pc.getTime())) {
		                scheduleDate = pc.getTime();
		            } else {
		                scheduleDate = c.getTime();
		            }
			}
            
           // Organisation org = UserSession.getCurrent().getOrganisation();
			LookUp gisFlag = CommonMasterUtility.getValueFromPrefixLookUp("GIS",
                    "MLI", org);
			if (gisFlag != null) {
				getModel().setGisValue(gisFlag.getOtherField());
				String GISURL=ServiceEndpoints.GisItegration.GIS_URI+ServiceEndpoints.GisItegration.WATER_GIS_LAYER_NAME;
				model.setgISUri(GISURL);
			}
            List<TbWtBillSchedule> tbWtBillScheduleEntity = tbWtBillScheduleService
                    .getBillScheduleFromToYear(scheduleDate, org.getOrgid(), meterType, new Date());
           // setScheduleAndBillMas(model, billList, meterType, tbWtBillScheduleEntity);
           // setScheduleAndBillMasEdit(model, billList, meterType, tbWtBillScheduleEntity);
        }

        return new ModelAndView("WaterDataEntrySuiteEdit", MainetConstants.FORM_NAME, getModel());
    }

    private void setScheduleAndBillMas(WaterDataEntrySuiteModel model, List<TbBillMas> billList, String meterType,
            List<TbWtBillSchedule> tbWtBillScheduleEntity) throws Exception {
        Map<Integer, String> monthprefix = getModel().getMonthprefix();
        Organisation org = new Organisation(UserSession.getCurrent().getOrganisation().getOrgid());

        getModel().setBillMasList(new ArrayList<>(0));
        getModel().setSchedule(new ArrayList<>(0));
        getModel().setSortedschedule(new ArrayList<>());
        TbCsmrInfoDTO waterDTO = getModel().getCsmrInfo();

        setBillingSchedule(org, waterDTO, monthprefix, tbWtBillScheduleEntity, "N");

        BillingScheduleDto scheduleDTO = new BillingScheduleDto();
        scheduleDTO.setCnsMn(meterType);
        scheduleDTO.setCodIdWwz1(waterDTO.getCodDwzid1());
        scheduleDTO.setCodIdWwz2(waterDTO.getCodDwzid2());
        scheduleDTO.setCodIdWwz3(waterDTO.getCodDwzid3());
        scheduleDTO.setCodIdWwz4(waterDTO.getCodDwzid4());
        scheduleDTO.setCodIdWwz5(waterDTO.getCodDwzid5());
        scheduleDTO.setOrgid(waterDTO.getOrgId());
        scheduleDTO.setCnsCcgid1(waterDTO.getCsCcncategory1());

        tbWtBillScheduleEntity = tbWtBillScheduleService.searchBillingDataWithScheduleDetails(scheduleDTO,
                waterDTO.getOrgId());

        if (billList != null && !billList.isEmpty()) {
            Long taxSubCategoryId = setTaxSubCategoryForMeterAndNonMeter(org, meterType);
            final LookUp chargeApplicableAt = CommonMasterUtility.getValueFromPrefixLookUp(
                    PrefixConstants.NewWaterServiceConstants.BILL, PrefixConstants.NewWaterServiceConstants.CAA,
                    UserSession.getCurrent().getOrganisation());
            final List<TbTaxMas> taxMaster = new ArrayList<>(0);
            // changes related to Defect #17462 For old connection numbers, inactive tax
            // names are not visible under arrears entry of data entry
            /*
             * taxMaster.addAll( tbTaxMasService.fetchAllIndependentTaxes(UserSession.getCurrent(). getOrganisation().getOrgid(),
             * getModel().getDeptId(), chargeApplicableAt.getLookUpId(), taxSubCategoryId)); taxMaster
             * .addAll(tbTaxMasService.fetchAllDepenentTaxes(UserSession.getCurrent(). getOrganisation().getOrgid(),
             * getModel().getDeptId(), chargeApplicableAt.getLookUpId(), taxSubCategoryId));
             */
            taxMaster.addAll(tbTaxMasService.findAllTaxesForBillPayment(org.getOrgid(), getModel().getDeptId(),
                    chargeApplicableAt.getLookUpId()));

            List<TbTaxMas> taxMasterList = new ArrayList<>(0);
            taxMaster.forEach(tax -> {
                String taxCategory1 = CommonMasterUtility.getHierarchicalLookUp(tax.getTaxCategory1(), org)
                        .getLookUpCode();
                if (!PrefixConstants.TAX_CATEGORY.ADVANCE.equals(taxCategory1)
                        && !PrefixConstants.TAX_CATEGORY.REBATE.equals(taxCategory1)) {
                    taxMasterList.add(tax);
                }
            });

            this.getModel().setTaxesMaster(taxMasterList);
            getModel().setBillMasList(billList);
            TbBillMas billMas = billList.get(0);
            Date billFromDate = billMas.getBmFromdt();
            Date billToDate = billMas.getBmTodt();
            boolean startDateflag = false;
            boolean endDateflag = false;
            FinancialYear finYear = iFinancialYearService.getFinincialYearsById(billMas.getBmYear(), org.getOrgid());
            final String faYearString = Utility.getCompleteFinancialYear(finYear.getFaFromDate(),
                    finYear.getFaToDate());
            for (TbWtBillSchedule billSchedule : tbWtBillScheduleEntity) {
                FinancialYear billFinYear = iFinancialYearService.getFinincialYearsById(billSchedule.getCnsYearid(),
                        org.getOrgid());
                final String billFaYearString = Utility.getCompleteFinancialYear(billFinYear.getFaFromDate(),
                        billFinYear.getFaToDate());
                if (billFaYearString.equals(faYearString)) {
                    for (TbWtBillScheduleDetail billScheduleDetail : billSchedule.getBillScheduleDetail()) {
                        final Date startDate = Utility.dateFromMonth(billFaYearString,
                                billScheduleDetail.getCnsFromDate().intValue(), MainetConstants.FINYEAR_DATE.FIRST);
                        final Date endDate = Utility.dateFromMonth(billFaYearString,
                                billScheduleDetail.getCnsToDate().intValue(), MainetConstants.FINYEAR_DATE.LAST);
                        startDateflag = Utility.comapreDates(billFromDate, startDate);
                        endDateflag = Utility.comapreDates(billToDate, endDate);
                        if (startDateflag && endDateflag) {
                            getModel().setSchduleId(billScheduleDetail.getDetId());
                        }
                    }
                }
            }
        } else {
            getModel().setBillMasList(new ArrayList<>(0));
            getModel().setSchedule(new ArrayList<>(0));
            getModel().setSortedschedule(new ArrayList<>(0));
            getModel().setBillScheduleDetail(new ArrayList<>(0));
            getModel().setSchduleId(0l);
        }
    }

    @RequestMapping(params = "searchData", method = RequestMethod.POST)
    public ModelAndView search(HttpServletRequest request) {
        WaterDataEntrySuiteModel model = this.getModel();
        model.bind(request);
        model.setSearchDTOResult(new ArrayList<>(0));
        WaterDataEntrySearchDTO dto = model.getSearchDTO();
        dto.setOrgId(UserSession.getCurrent().getOrganisation().getOrgid());
        if ((dto.getPropertyNo() == null || dto.getPropertyNo().isEmpty())
                && (dto.getCsContactno() == null || dto.getCsContactno().isEmpty())
                && (dto.getCsName() == null || dto.getCsName().isEmpty())
                && (dto.getCsCcn() == null || dto.getCsCcn().isEmpty())
                && (dto.getCodDwzid1() == null || dto.getCodDwzid1() <= 0l)
                && (dto.getLocId() == null || dto.getLocId() <= 0l)
                && (StringUtils.isBlank(dto.getCsOldccn()))) {
            model.addValidationError(getApplicationSession().getMessage("water.bill.validate"));
        }
        return defaultMyResult();
    }

    @RequestMapping(params = "backToSearch", method = RequestMethod.POST)
    public ModelAndView backToSearch(HttpServletRequest request) {
        WaterDataEntrySuiteModel model = this.getModel();
        model.bind(request);
        return defaultMyResult();
    }

    @RequestMapping(method = { RequestMethod.POST }, params = "resetSeachGrid")
    public ModelAndView reset(HttpServletRequest request) {
        sessionCleanup(request);
        getModel().bind(request);
        WaterDataEntrySuiteModel model = this.getModel();
        long orgId = UserSession.getCurrent().getOrganisation().getOrgid();
        model.setCommonHelpDocs("WaterDataEntrySuite.html");
        model.getSearchDTO().setOrgId(orgId);
        return defaultMyResult();
    }

    @RequestMapping(params = "SEARCH_GRID_RESULTS", produces = "application/json", method = RequestMethod.POST)
    public @ResponseBody JQGridResponse<? extends Serializable> getSearchResults(
            HttpServletRequest httpServletRequest) {
        List<WaterDataEntrySearchDTO> result = null;
        int count = 0;
        WaterDataEntrySuiteModel model = this.getModel();
        WaterDataEntrySearchDTO dto = model.getSearchDTO();
        if ((dto.getPropertyNo() == null || dto.getPropertyNo().isEmpty())
                && (dto.getCsContactno() == null || dto.getCsContactno().isEmpty())
                && (dto.getCsName() == null || dto.getCsName().isEmpty())
                && (dto.getCsCcn() == null || dto.getCsCcn().isEmpty())
                && (dto.getCodDwzid1() == null || dto.getCodDwzid1() <= 0l)
                && (dto.getLocId() == null || dto.getLocId() <= 0l)
                && (StringUtils.isBlank(dto.getCsOldccn()))) {
            model.addValidationError("In order to search it is mandatory to enter any one of the below search detail");
        }
        final String page = httpServletRequest.getParameter(MainetConstants.CommonConstants.PAGE);
        final String rows = httpServletRequest.getParameter(MainetConstants.CommonConstants.ROWS);
        if (!model.hasValidationErrors()) {
            result = newWaterConnectionService.searchConnectionDetails(model.getSearchDTO(),
                    getModel().createPagingDTO(httpServletRequest), getModel().createGridSearchDTO(httpServletRequest),
                    null);
            if (result != null && !result.isEmpty()) {
                count = newWaterConnectionService.getTotalSearchCount(model.getSearchDTO(),
                        getModel().createPagingDTO(httpServletRequest),
                        getModel().createGridSearchDTO(httpServletRequest));
            }
        }
        return this.getModel().paginate(httpServletRequest, page, rows, count, result);
    }

    @RequestMapping(params = MainetConstants.Common_Constant.FORM, method = RequestMethod.POST)
    public ModelAndView connectionForm(@RequestParam(value = "csIdn", required = false) Long csIdn,
            @RequestParam(value = MainetConstants.Common_Constant.TYPE, required = false) String type,
            HttpServletRequest request) {
        sessionCleanup(request);
        WaterDataEntrySuiteModel dataSuitmodel = this.getModel();
        dataSuitmodel.setCommonHelpDocs("WaterDataEntrySuite.html");
        LookUp propNoOptional = null;
		try {
			propNoOptional = CommonMasterUtility.getValueFromPrefixLookUp("PNO", "WEV", UserSession.getCurrent().getOrganisation());
		}catch (Exception exception) {
			LOGGER.error("No prefix found for WEV(PNO)");
		}
		if(propNoOptional != null) {
			getModel().setPropNoOptionalFlag(propNoOptional.getOtherField());
		}else {
			getModel().setPropNoOptionalFlag(MainetConstants.FlagN);
		}
        populateModel(dataSuitmodel, csIdn, type);
        List<PlumberMaster> allPlumberList = new ArrayList<>();
		allPlumberList = waterCommonService.listofplumber(UserSession.getCurrent().getOrganisation().getOrgid());
		getModel().setPlumberList(allPlumberList);
        switch (dataSuitmodel.getModeType()) {
        case "V":
            return new ModelAndView("WaterDataEntrySuiteEdit", MainetConstants.FORM_NAME, dataSuitmodel);
        case "E":
            return new ModelAndView("WaterDataEntrySuiteEdit", MainetConstants.FORM_NAME, dataSuitmodel);
        default:
        	if(Utility.isEnvPrefixAvailable(UserSession.getCurrent().getOrganisation(), MainetConstants.ENV_PSCL)){
        	dataSuitmodel.setIsConsumerSame("N");
        	dataSuitmodel.setIsBillingSame("N");
        	 }
            return new ModelAndView("WaterDataEntrySuiteCreate", MainetConstants.FORM_NAME, dataSuitmodel);
        }
    }

    private void populateModel(final WaterDataEntrySuiteModel dataSuitmodel, final Long csIdn, final String mode) {
        if (mode.equals(MainetConstants.MODE_CREATE)) {
            dataSuitmodel.setCsmrInfo(new TbCsmrInfoDTO());
            dataSuitmodel.setNewConnectionDto(new NewWaterConnectionReqDTO());
            dataSuitmodel.setModeType(MainetConstants.MODE_CREATE);
        } else {
            final NewWaterConnectionReqDTO connectionDTO = getConnectionDetails(csIdn);
            dataSuitmodel.setNewConnectionDto(connectionDTO);
            if (mode.equals(MainetConstants.MODE_EDIT)) {
                dataSuitmodel.setModeType(MainetConstants.MODE_EDIT);
            } else {
                dataSuitmodel.setModeType(MainetConstants.MODE_VIEW);
            }
        }
    }

    private NewWaterConnectionReqDTO getConnectionDetails(Long csIdn) {
        NewWaterConnectionReqDTO connectionDTO = this.getModel().getConnectionDetails(csIdn);
        return connectionDTO;
    }

    @RequestMapping(params = "validateWardZonePrefix", method = RequestMethod.POST)
    public @ResponseBody String validateWardZonePrefix() {
        String isValid = MainetConstants.FlagY;
        final List<LookUp> wardZonelookup = CommonMasterUtility.getListLookup(
                PrefixConstants.NewWaterServiceConstants.WWZ, UserSession.getCurrent().getOrganisation());

        if (CollectionUtils.isEmpty(wardZonelookup)) {
            isValid = MainetConstants.FlagN;
        }
        return isValid;
    }
    
    public void addbillMasScheduleEdit(List<TbBillMas> billList, TbCsmrInfoDTO csmrInfo) {
    	 getModel().setFinancialYearMap(new HashMap<>());
         TbCsmrInfoDTO waterDTO = getModel().getCsmrInfo();
         Organisation org = UserSession.getCurrent().getOrganisation();
         Date lastBillToDate = billList.get(billList.size() - 1).getBmTodt();
             String metertype = CommonMasterUtility.getNonHierarchicalLookUpObject(waterDTO.getCsMeteredccn(), org)
                     .getLookUpCode();
             Long taxSubCategoryId = setTaxSubCategoryForMeterAndNonMeter(org, metertype);
             final LookUp chargeApplicableAt = CommonMasterUtility.getValueFromPrefixLookUp(
                     PrefixConstants.NewWaterServiceConstants.BILL, PrefixConstants.NewWaterServiceConstants.CAA,
                     UserSession.getCurrent().getOrganisation());
             final List<TbTaxMas> taxMaster = new ArrayList<>(0);
             taxMaster.addAll(
                     tbTaxMasService.fetchAllIndependentTaxes(UserSession.getCurrent().getOrganisation().getOrgid(),
                             getModel().getDeptId(), chargeApplicableAt.getLookUpId(), taxSubCategoryId, 0L));
             taxMaster
                     .addAll(tbTaxMasService.fetchAllDepenentTaxes(UserSession.getCurrent().getOrganisation().getOrgid(),
                             getModel().getDeptId(), chargeApplicableAt.getLookUpId(), taxSubCategoryId, 0L));

             List<TbTaxMas> taxMasterList = new ArrayList<>(0);
             taxMaster.forEach(tax -> {
                 String taxCategory1 = CommonMasterUtility.getHierarchicalLookUp(tax.getTaxCategory1(), org)
                         .getLookUpCode();
                 if (!PrefixConstants.TAX_CATEGORY.ADVANCE.equals(taxCategory1)
                         && !PrefixConstants.TAX_CATEGORY.REBATE.equals(taxCategory1)) {
                     taxMasterList.add(tax);
                 }
             });

             this.getModel().setTaxesMaster(taxMasterList);

             List<TbBillMas> billMasList = new ArrayList<>();

            
             getModel().getBillScheduleDetail().forEach(shedule -> {
            	 
				 final FinancialYear finYearData = iFinancialYearService.getFinincialYearsById(shedule.getYearId(),
                             org.getOrgid());

                     final String faYearString = Utility.getCompleteFinancialYear(finYearData.getFaFromDate(),
                             finYearData.getFaToDate());

                     final Date startDate = Utility.dateFromMonth(faYearString, shedule.getCnsFromDate().intValue(),
                             MainetConstants.FINYEAR_DATE.FIRST);
                     final Date endDate = Utility.dateFromMonth(faYearString, shedule.getCnsToDate().intValue(),
                             MainetConstants.FINYEAR_DATE.LAST);

                     if(Utility.compareDate(lastBillToDate, startDate)) {
                     if ((startDate.after(billList.get(0).getBmFromdt())) || startDate.equals(billList.get(0).getBmFromdt())) {

                         TbBillMas billMas = new TbBillMas();
                         List<TbBillDet> billDetList = new ArrayList<>();
                         taxMasterList.forEach(tax -> {
                             TbBillDet billDet = new TbBillDet();
                             billDet.setTaxId(tax.getTaxId());
                             billDet.setTaxCategory(tax.getTaxCategory1());
                             billDet.setCollSeq(tax.getCollSeq());
                             billDet.setBdCsmp(new BigDecimal(0).setScale(2, BigDecimal.ROUND_UP));
                             billDetList.add(billDet);
                             billMas.setTbWtBillDet(billDetList);
                         });

                         String value = faYearString + "->"
                                 + getModel().getMonthprefix().get(shedule.getCnsFromDate().intValue()) + " - "
                                 + getModel().getMonthprefix().get(shedule.getCnsToDate().intValue());
                         billMas.setBmYear(shedule.getYearId());
                         billMas.setBmFromdt(startDate);
                         billMas.setBmTodt(endDate);
                         billMas.setBmBilldt(startDate);
                         billMas.setBmGenDes(value);
                         billMas.setBmDuedate(getBillDueDate(org, metertype, endDate,startDate));
                         billMas.setWtN1(BigDecimal.valueOf(shedule.getDetId()));

                         billMasList.add(billMas);
                     }
            	 }
            	 
                 
             });
             billList.addAll(billMasList);
             this.getModel().setBillMasList(billList);
		List<TbTaxMas> taxList = tbTaxMasService.findAllTaxesForBillPayment(org.getOrgid(), getModel().getDeptId(),
				chargeApplicableAt.getLookUpId());
		taxList.forEach(tax -> {
			String taxCategory1 = CommonMasterUtility.getHierarchicalLookUp(tax.getTaxCategory1(), org).getLookUpCode();
			if (!PrefixConstants.TAX_CATEGORY.ADVANCE.equals(taxCategory1)
					&& !PrefixConstants.TAX_CATEGORY.REBATE.equals(taxCategory1)) {
				Optional<TbTaxMas> findFirst = taxMasterList.stream()
						.filter(taxMas -> Long.valueOf(taxMas.getTaxId()).equals(tax.getTaxId())).findFirst();
				if (!findFirst.isPresent()) {
					taxMasterList.add(tax);
				}
			}
		});
       
        
    }
    
    private void setScheduleAndBillMasEdit(WaterDataEntrySuiteModel model, List<TbBillMas> billList, String meterType,
            List<TbWtBillSchedule> tbWtBillScheduleEntity) throws Exception {
        Organisation org = new Organisation(UserSession.getCurrent().getOrganisation().getOrgid());
        if (billList != null && !billList.isEmpty()) {
            getModel().setBillMasList(billList);
            TbBillMas billMas = billList.get(0);
            Date billFromDate = billMas.getBmFromdt();
            Date billToDate = billMas.getBmTodt();
            boolean startDateflag = false;
            boolean endDateflag = false;
            FinancialYear finYear = iFinancialYearService.getFinincialYearsById(billMas.getBmYear(), org.getOrgid());
            final String faYearString = Utility.getCompleteFinancialYear(finYear.getFaFromDate(),
                    finYear.getFaToDate());
            if(tbWtBillScheduleEntity != null) {
            for (TbWtBillSchedule billSchedule : tbWtBillScheduleEntity) {
                FinancialYear billFinYear = iFinancialYearService.getFinincialYearsById(billSchedule.getCnsYearid(),
                        org.getOrgid());
                final String billFaYearString = Utility.getCompleteFinancialYear(billFinYear.getFaFromDate(),
                        billFinYear.getFaToDate());
                if (billFaYearString.equals(faYearString)) {
                    for (TbWtBillScheduleDetail billScheduleDetail : billSchedule.getBillScheduleDetail()) {
                        final Date startDate = Utility.dateFromMonth(billFaYearString,
                                billScheduleDetail.getCnsFromDate().intValue(), MainetConstants.FINYEAR_DATE.FIRST);
                        final Date endDate = Utility.dateFromMonth(billFaYearString,
                                billScheduleDetail.getCnsToDate().intValue(), MainetConstants.FINYEAR_DATE.LAST);
                        startDateflag = Utility.comapreDates(billFromDate, startDate);
                        endDateflag = Utility.comapreDates(billToDate, endDate);
                        if (startDateflag && endDateflag) {
                            getModel().setSchduleId(billScheduleDetail.getDetId());
                        }
                    }
                }
            }
        }
        }
    }
    
    @RequestMapping(params = "view", method = RequestMethod.POST)
    public ModelAndView viewForm(@RequestParam final String rowId,
            @RequestParam(value = MainetConstants.AdvertisingAndHoarding.SAVE_MODE, required = true) String saveMode,
            final HttpServletRequest httpServletRequest)
            throws Exception {
        WaterDataEntrySuiteModel model = this.getModel();
        model.setModeType(saveMode);
        model.bind(httpServletRequest);
        LookUp propNoOptional = null;
		try {
			propNoOptional = CommonMasterUtility.getValueFromPrefixLookUp("PNO", "WEV", UserSession.getCurrent().getOrganisation());
		}catch (Exception exception) {
			LOGGER.error("No prefix found for WEV(PNO)");
		}
		if(propNoOptional != null) {
			getModel().setPropNoOptionalFlag(propNoOptional.getOtherField());
		}else {
			getModel().setPropNoOptionalFlag(MainetConstants.FlagN);
		}

        final Department dept = departmentService.getDepartment(MainetConstants.WATER_DEPARTMENT_CODE,
                MainetConstants.STATUS.ACTIVE);
        getModel().setDeptId(dept.getDpDeptid());
        Organisation org = new Organisation(UserSession.getCurrent().getOrganisation().getOrgid());
        List<TbBillMas> billList = null;
        if (rowId != null && !rowId.isEmpty()) {
            Long csIdn = Long.valueOf(rowId);
            populateModel(model, csIdn, saveMode);
            billList = tbWtBillMasService.getBillMasByConnectionId(csIdn);
            model.setBillMasList(billList);
            BigDecimal totalAmount = BigDecimal.ZERO;
            for (TbBillMas billMas : billList) {
                final String faYearString = Utility.getFinancialYearFromDate(billMas.getBmFromdt());
                Date fromDate = billMas.getBmFromdt();
                Calendar c = Calendar.getInstance();
                c.setTime(fromDate);
                int startMonth = c.get(Calendar.MONTH) + 1;
                Date endDate = billMas.getBmFromdt();
                c.setTime(endDate);
                int endMonth = c.get(Calendar.MONTH) + 1;
                String value = faYearString + "->" + getModel().getMonthprefix().get(startMonth) + " - "
                        + getModel().getMonthprefix().get(endMonth);
                billMas.setBmGenDes(value);
                BigDecimal arrearAmount = BigDecimal.valueOf(billMas.getBmLastRcptamt());
                totalAmount = totalAmount.add(arrearAmount);
            }
            String meterType = CommonMasterUtility
                    .getNonHierarchicalLookUpObject(getModel().getCsmrInfo().getCsMeteredccn(), org).getLookUpCode();
            NewWaterConnectionReqDTO connectionDTO = model.getNewConnectionDto();
            TbCsmrInfoDTO infoDTO = model.getCsmrInfo();
            connectionDTO.setCsmrInfo(infoDTO);

            TbCsmrInfoDTO waterDTO = getModel().getCsmrInfo();
            List<String> years = Utility.getNoOfFinancialYearIncludingCurrent(6);

            String[] year = years.get(5).split("-");
            Calendar c = Calendar.getInstance();
            c.setTime(new Date());
            c.set(Calendar.YEAR, Integer.parseInt(year[0]));
            c.set(Calendar.MINUTE, 0);
            c.set(Calendar.SECOND, 0);
            c.set(Calendar.HOUR_OF_DAY, 0);

            Calendar pc = Calendar.getInstance();
            pc.setTime(waterDTO.getPcDate());
            pc.set(Calendar.MINUTE, 0);
            pc.set(Calendar.SECOND, 0);
            pc.set(Calendar.HOUR_OF_DAY, 0);
            Date scheduleDate = null;
			if (getModel().getBillMasList() != null && !getModel().getBillMasList().isEmpty()
					&& getModel().getModeType().equalsIgnoreCase(MainetConstants.FlagE)) {
				scheduleDate = getModel().getBillMasList().get(getModel().getBillMasList().size() - 1).getBmFromdt();

			}else {
				 if (UtilityService.compareDateField(c.getTime(), pc.getTime())) {
		                scheduleDate = pc.getTime();
		            } else {
		                scheduleDate = c.getTime();
		            }
			}
            
           // Organisation org = UserSession.getCurrent().getOrganisation();
			LookUp gisFlag = CommonMasterUtility.getValueFromPrefixLookUp("GIS",
                    "MLI", org);
			if (gisFlag != null) {
				getModel().setGisValue(gisFlag.getOtherField());
				String GISURL=ServiceEndpoints.GisItegration.GIS_URI+ServiceEndpoints.GisItegration.WATER_GIS_LAYER_NAME;
				model.setgISUri(GISURL);
			}
            List<TbWtBillSchedule> tbWtBillScheduleEntity = tbWtBillScheduleService
                    .getBillScheduleFromToYear(scheduleDate, org.getOrgid(), meterType, new Date());
           // setScheduleAndBillMas(model, billList, meterType, tbWtBillScheduleEntity);
           // setScheduleAndBillMasEdit(model, billList, meterType, tbWtBillScheduleEntity);
        }

        return new ModelAndView("WaterDataEntrySuiteEdit", MainetConstants.FORM_NAME, getModel());
    }

}
