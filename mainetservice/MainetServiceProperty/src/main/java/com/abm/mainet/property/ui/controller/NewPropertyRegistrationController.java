package com.abm.mainet.property.ui.controller;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.abm.mainet.common.constant.MainetConstants;
import com.abm.mainet.common.domain.FinancialYear;
import com.abm.mainet.common.domain.LocationMasEntity;
import com.abm.mainet.common.domain.Organisation;
import com.abm.mainet.common.domain.ServiceMaster;
import com.abm.mainet.common.integration.dms.domain.CFCAttachment;
import com.abm.mainet.common.integration.dms.service.IFileUploadService;
import com.abm.mainet.common.integration.dto.DocumentDetailsVO;
import com.abm.mainet.common.service.IFinancialYearService;
import com.abm.mainet.common.service.ILocationMasService;
import com.abm.mainet.common.service.ServiceMasterService;
import com.abm.mainet.common.ui.controller.AbstractFormController;
import com.abm.mainet.common.utility.CommonMasterUtility;
import com.abm.mainet.common.utility.LookUp;
import com.abm.mainet.common.utility.UserSession;
import com.abm.mainet.common.utility.Utility;
import com.abm.mainet.property.domain.BillingScheduleDetailEntity;
import com.abm.mainet.property.dto.ProvisionalAssesmentDetailDto;
import com.abm.mainet.property.dto.ProvisionalAssesmentFactorDtlDto;
import com.abm.mainet.property.dto.ProvisionalAssesmentMstDto;
import com.abm.mainet.property.dto.ProvisionalAssesmentOwnerDtlDto;
import com.abm.mainet.property.service.BillingScheduleService;
import com.abm.mainet.property.service.SelfAssessmentService;
import com.abm.mainet.property.ui.model.NewPropertyRegistrationModel;
import com.abm.mainet.property.ui.validator.NewPropertyRegistrationValidator;

@Controller
@RequestMapping("/NewPropertyRegistration.html")
public class NewPropertyRegistrationController extends AbstractFormController<NewPropertyRegistrationModel> {
    @Resource
    private SelfAssessmentService selfAssessmentService;

    @Autowired
    private IFinancialYearService iFinancialYear;

    @Autowired
    private BillingScheduleService billingScheduleService;

    @Autowired
    private ServiceMasterService serviceMaster;

    @Autowired
    private IFileUploadService fileUpload;

    @Resource
    private ILocationMasService iLocationMasService;

    @RequestMapping(method = { RequestMethod.POST })
    public ModelAndView index(HttpServletRequest request) throws Exception {
        this.sessionCleanup(request);
        fileUpload.sessionCleanUpForFileUpload();
        NewPropertyRegistrationModel model = this.getModel();
        model.setCommonHelpDocs("NewPropertyRegistration.html");
        setCommonFields(getModel());
        List<LookUp> locList = getModel().getLocation();
        Organisation org = UserSession.getCurrent().getOrganisation();
        List<LocationMasEntity> location = iLocationMasService.findWZMappedLocationByOrgIdAndDeptId(org.getOrgid(),
                getModel().getDeptId());
        if (location != null && !location.isEmpty()) {
            location.forEach(loc -> {
                LookUp lookUp = new LookUp();
                lookUp.setLookUpId(loc.getLocId());
                lookUp.setDescLangFirst(loc.getLocNameEng());
                lookUp.setDescLangSecond(loc.getLocNameReg());
                locList.add(lookUp);
            });
        }
        getModel().setSchedule(selfAssessmentService.getAllBillscheduleWithoutCurrentScheduleByOrgid(org));
        getModel().setAssType(MainetConstants.Property.NEW_PROP_REG);
        getModel().setSelfAss(MainetConstants.Y_FLAG);
        final List<FinancialYear> finYearList = iFinancialYear.getAllFinincialYear();
        String financialYear = null;
        for (final FinancialYear finYearTemp : finYearList) {
            financialYear = Utility.getFinancialYearFromDate(finYearTemp.getFaFromDate());
            getModel().getFinancialYearMap().put(finYearTemp.getFaYear(), financialYear);
        }
        return defaultResult();
    }

    private void setCommonFields(NewPropertyRegistrationModel model) {
        Long orgId = UserSession.getCurrent().getOrganisation().getOrgid();
        ServiceMaster service = serviceMaster.getServiceByShortName(MainetConstants.Property.NPR, orgId);
        model.setOrgId(orgId);
        model.setLeastFinYear(iFinancialYear.getMinFinanceYear(orgId));
        model.setServiceMaster(service);
        model.setDeptId(service.getTbDepartment().getDpDeptid());
        model.setServiceId(service.getSmServiceId());
        model.setIntgrtionWithBP(CommonMasterUtility
                .getDefaultValueByOrg(MainetConstants.Property.IBP, UserSession.getCurrent().getOrganisation()).getLookUpCode());

    }

    @RequestMapping(params = "getCheckList", method = RequestMethod.POST)
    public ModelAndView getCheckList(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse) {
        getModel().bind(httpServletRequest);
        NewPropertyRegistrationModel model = this.getModel();
        ProvisionalAssesmentMstDto dto = model.getProvisionalAssesmentMstDto();
        List<ProvisionalAssesmentFactorDtlDto> factDto= model.getProvAsseFactDtlDto();
        dto.setOrgId(model.getOrgId());
        dto.setSmServiceId(model.getServiceId());
        List<DocumentDetailsVO> docs = selfAssessmentService.fetchCheckList(dto,factDto);
        model.setCheckList(docs);
        if (docs.isEmpty()) {
            model.setDropDownValues();
            selfAssessmentService.setFactorMappingToAssDto(model.getProvAsseFactDtlDto(), dto);
            return new ModelAndView("NewPropertyRegistrationView", MainetConstants.FORM_NAME, this.getModel());
        }
        // propertyService.setWardZoneDetailByLocId(dto, model.getDeptId(), dto.getLocId());
        selfAssessmentService.setFactorMappingToAssDto(model.getProvAsseFactDtlDto(), dto);
        List<ProvisionalAssesmentDetailDto> detailDto = model.getProvisionalAssesmentMstDto()
                .getProvisionalAssesmentDetailDtoList();
        Long max = 0l;
        if (detailDto != null && !detailDto.isEmpty()) {
            for (ProvisionalAssesmentDetailDto det : detailDto) {
                if (det.getAssdUnitNo() > max) {
                    max = det.getAssdUnitNo();
                }
            }
        }
        model.setMaxUnit(max);
        getModel().setSchedule(selfAssessmentService
                .getAllBillscheduleWithoutCurrentScheduleByOrgid(UserSession.getCurrent().getOrganisation()));
        selfAssessmentService.setFactorMappingToAssDto(model.getProvAsseFactDtlDto(), dto);
        return defaultMyResult();
    }

    @RequestMapping(params = "proceed", method = RequestMethod.POST)
    public ModelAndView confirmToProceed(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse) {
        getModel().bind(httpServletRequest);
        NewPropertyRegistrationModel model = this.getModel();
        ProvisionalAssesmentMstDto assMstDto = model.getProvisionalAssesmentMstDto();
        selfAssessmentService.setFactorMappingToAssDto(model.getProvAsseFactDtlDto(), assMstDto);
        List<DocumentDetailsVO> docs = model.getCheckList();
        docs = fileUpload.prepareFileUpload(docs);
        assMstDto.setDocs(docs);
        model.setDocumentList(new ArrayList<>());
        List<CFCAttachment> documentList = selfAssessmentService.preparePreviewOfFileUpload(model.getDocumentList(),
                model.getCheckList());
        model.setDocumentList(documentList);
        model.validateBean(assMstDto, NewPropertyRegistrationValidator.class);
        if (model.hasValidationErrors()) {
            return defaultMyResult();
        }
        model.setDropDownValues();
        return new ModelAndView("NewPropertyRegistrationView", MainetConstants.FORM_NAME, this.getModel());
    }

    @RequestMapping(params = "getFactorValueDiv", method = RequestMethod.POST)
    public ModelAndView getFactorValueDiv(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse,
            @RequestParam(value = "factorCode") String factorCode) {
        this.getModel().bind(httpServletRequest);
        NewPropertyRegistrationModel model = this.getModel();
        LookUp lookup = model.getFactorLookup(factorCode);
        model.setFactorPrefix(lookup.getLookUpCode());
        model.setFactorId(lookup.getLookUpId());
        return new ModelAndView("NewPropRegUnitSpec", MainetConstants.FORM_NAME, model);
    }

    @RequestMapping(params = "getMappingFactor")
    public @ResponseBody Map<Long, String> getMappingFactor(@RequestParam("factorType") String factorType) {
        Organisation org = UserSession.getCurrent().getOrganisation();
        Map<Long, String> factorMap = new HashMap<>(0);
        List<LookUp> factorLookup = CommonMasterUtility.lookUpListByPrefix(factorType, org.getOrgid());
        for (LookUp lookUp : factorLookup) {
            factorMap.put(lookUp.getLookUpId(), lookUp.getDescLangFirst());
        }
        return factorMap;
    }

    @RequestMapping(params = "getOwnershipTypeDiv", method = RequestMethod.POST)
    public ModelAndView getOwnershipTypeDiv(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse,
            @RequestParam(value = "ownershipType") String ownershipType) {
        this.getModel().bind(httpServletRequest);
        NewPropertyRegistrationModel model = this.getModel();
        model.setOwnershipPrefix(ownershipType);
        LookUp lookup = CommonMasterUtility.getValueFromPrefixLookUp(ownershipType, MainetConstants.Property.propPref.OWT,
                UserSession.getCurrent().getOrganisation());
        model.setOwnershipTypeValue(lookup.getDescLangFirst());
        model.getProvisionalAssesmentMstDto().setAssOwnerType(lookup.getLookUpId());
        return new ModelAndView("NewPropRegOwner", MainetConstants.FORM_NAME, model);
    }

    @RequestMapping(params = "getFinancialYear", method = RequestMethod.POST)
    public @ResponseBody Long getyearOfAcquisition(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse,
            String yearOfAcq) {
        this.getModel().bind(httpServletRequest);
        NewPropertyRegistrationModel model = this.getModel();
        model.getProvisionalAssesmentMstDto().getProvisionalAssesmentDetailDtoList().clear();
        Date currentDate = Utility.stringToDate(yearOfAcq);
        model.getProvAsseFactDtlDto().clear();  // clear dto on year change
        return iFinancialYear.getFinanceYearId(currentDate);

    }

    @RequestMapping(params = "deleteUnitTableRow", method = RequestMethod.POST)
    public @ResponseBody void deleteUnitTableRow(HttpServletRequest httpServletRequest,
            HttpServletResponse httpServletResponse, @RequestParam(value = "deletedRowCount") int deletedRowCount) {
        this.getModel().bind(httpServletRequest);
        NewPropertyRegistrationModel model = this.getModel();
        if (!model.getProvisionalAssesmentMstDto().getProvisionalAssesmentDetailDtoList().isEmpty()
                && model.getProvisionalAssesmentMstDto().getProvisionalAssesmentDetailDtoList().size() > deletedRowCount) {
            ProvisionalAssesmentDetailDto detDto = model.getProvisionalAssesmentMstDto().getProvisionalAssesmentDetailDtoList()
                    .get(deletedRowCount);
            model.getProvisionalAssesmentMstDto().getProvisionalAssesmentDetailDtoList().remove(detDto);
        }
    }

    @RequestMapping(params = "deleteUnitSpecificInfo", method = RequestMethod.POST)
    public @ResponseBody void deleteUnitSpecificInfo(HttpServletRequest httpServletRequest,
            HttpServletResponse httpServletResponse, @RequestParam(value = "deletedUnitRow") Integer deletedUnitRow) {
        this.getModel().bind(httpServletRequest);
        NewPropertyRegistrationModel model = this.getModel();
        model.setUnitStatusCount(deletedUnitRow);
        for (ProvisionalAssesmentFactorDtlDto FactorDtlDto : model.getProvAsseFactDtlDto()) {
            if (model.getUnitStatusCount() == deletedUnitRow) {
                model.getProvAsseFactDtlDto().remove(FactorDtlDto);
            }
        }
    }

    @RequestMapping(params = "deleteFactorStatus", method = RequestMethod.POST)
    public @ResponseBody void deleteFactorStatus(HttpServletRequest httpServletRequest,
            HttpServletResponse httpServletResponse, @RequestParam(value = "factorCode") String factorCode) {
        this.getModel().bind(httpServletRequest);
        NewPropertyRegistrationModel model = this.getModel();
        int size = model.getProvAsseFactDtlDto().size();
        for (int i = size; i > 0; i--) {
            int j = i - 1;
            ProvisionalAssesmentFactorDtlDto factorDto = model.getProvAsseFactDtlDto().get(j);
            if (factorDto.getFactorValueCode() != null && factorDto.getFactorValueCode().equals(factorCode)) {
                model.getProvAsseFactDtlDto().remove(factorDto);
            }
        }
        // while deleting factor :-required to delete from detail list where factor already mapped
        model.getProvisionalAssesmentMstDto().getProvisionalAssesmentDetailDtoList().forEach(det -> {
            det.getProvisionalAssesmentFactorDtlDtoList().clear();
        });

    }

    @RequestMapping(params = "deleteOwnerTable", method = RequestMethod.POST)
    public @ResponseBody void deleteOwnerTable(HttpServletRequest httpServletRequest,
            HttpServletResponse httpServletResponse, @RequestParam(value = "deletedOwnerRowId") Integer deletedOwnerRowId) {
        this.getModel().bind(httpServletRequest);
        NewPropertyRegistrationModel model = this.getModel();
        if (!model.getProvisionalAssesmentMstDto().getProvisionalAssesmentOwnerDtlDtoList().isEmpty()
                && model.getProvisionalAssesmentMstDto().getProvisionalAssesmentOwnerDtlDtoList().size() > deletedOwnerRowId) {
            ProvisionalAssesmentOwnerDtlDto detDto = model.getProvisionalAssesmentMstDto()
                    .getProvisionalAssesmentOwnerDtlDtoList()
                    .get(deletedOwnerRowId);
            model.getProvisionalAssesmentMstDto().getProvisionalAssesmentOwnerDtlDtoList().remove(detDto);
        }
    }

    @RequestMapping(params = "getFinanceYearListFromGivenDate", method = RequestMethod.POST)
    public @ResponseBody List<Long> getFinanceYearListFromGivenDate(HttpServletRequest httpServletRequest,
            HttpServletResponse httpServletResponse, @RequestParam(value = "finYearId") Long finYearId) {
        List<Long> finYearList = null;
        List<FinancialYear> financialYearList = iFinancialYear
                .getFinanceYearListFromGivenDate(UserSession.getCurrent().getOrganisation().getOrgid(), finYearId, new Date());
        if (!financialYearList.isEmpty()) {
            finYearList = new ArrayList<>();
            for (FinancialYear financialYearEach : financialYearList) {
                Long finYear = iFinancialYear.getFinanceYearId(financialYearEach.getFaFromDate());
                finYearList.add(finYear);
            }
        }
        this.getModel().setFinYearList(finYearList);
        return finYearList;
    }

    @RequestMapping(params = "getNextScheduleFromLastPayDet", method = RequestMethod.POST)
    public @ResponseBody List<Object> getNextScheduleFromLastPayDet(HttpServletRequest httpServletRequest,
            HttpServletResponse httpServletResponse, @RequestParam(value = "schDetId") Long schDetId) {
        NewPropertyRegistrationModel model = this.getModel();
        List<Object> acqDateDetail = new ArrayList<>();
        List<BillingScheduleDetailEntity> nextSchedule = billingScheduleService
                .getNextScheduleFromLastPayDet(UserSession.getCurrent().getOrganisation().getOrgid(), schDetId);
        BillingScheduleDetailEntity billingScheduleDetailEntity = nextSchedule.get(0);
        BillingScheduleDetailEntity selectedSch = billingScheduleService.getSchDetailByScheduleId(schDetId,
                UserSession.getCurrent().getOrganisation().getOrgid());
        acqDateDetail.add(selectedSch.getBillToDate());
        acqDateDetail.add(iFinancialYear.getFinanceYearId(billingScheduleDetailEntity.getBillFromDate()));
        model.getProvAsseFactDtlDto().clear();  // clear dto on year change
        return acqDateDetail;
    }

    @RequestMapping(params = "editSelfAssForm", method = RequestMethod.POST)
    public ModelAndView editForm(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse) {
        getModel().bind(httpServletRequest);
        getModel().setSelfAss(null);
        NewPropertyRegistrationModel model = this.getModel();
        getModel().setSchedule(selfAssessmentService
                .getAllBillscheduleWithoutCurrentScheduleByOrgid(UserSession.getCurrent().getOrganisation()));
        Long max = 0l;
        List<ProvisionalAssesmentDetailDto> detailDto = model.getProvisionalAssesmentMstDto()
                .getProvisionalAssesmentDetailDtoList();
        if (detailDto != null && !detailDto.isEmpty()) {
            for (ProvisionalAssesmentDetailDto dto : detailDto) {
                if (dto.getAssdUnitNo() > max) {
                    max = dto.getAssdUnitNo();
                }
            }
        }
        model.setMaxUnit(max);
        if (model.getAssType().equals(MainetConstants.Property.ASESS_AUT)) {
            loadDateForAuthorization(model);
        }
        return new ModelAndView("NewPropertyRegistrationEdit", MainetConstants.FORM_NAME, model);
    }

    @RequestMapping(params = "compareDate", method = RequestMethod.POST)
    public @ResponseBody Date compareDate(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse,
            @RequestParam(value = "finId") Long finId) {
        return iFinancialYear.getFinincialYearsById(finId, UserSession.getCurrent().getOrganisation().getOrgid()).getFaFromDate();
    }

    @RequestMapping(params = "displayYearListBasedOnDate", method = RequestMethod.POST)
    public @ResponseBody Map<Long, String> displayYearListBasedOnDate(HttpServletRequest httpServletRequest,
            @RequestParam(value = "finYearId") Long finYearId) throws Exception {

        List<FinancialYear> financialYearList = iFinancialYear
                .getFinanceYearListFromGivenDate(UserSession.getCurrent().getOrganisation().getOrgid(), finYearId, new Date());
        String financialYear = null;
        Map<Long, String> yearMap = new HashMap<>(0);
        for (final FinancialYear finYearTemp : financialYearList) {
            financialYear = Utility.getFinancialYearFromDate(finYearTemp.getFaFromDate());
            yearMap.put(finYearTemp.getFaYear(), financialYear);

        }
        this.getModel().setFinancialYearMap(yearMap);
        return yearMap;
    }

    private void loadDateForAuthorization(NewPropertyRegistrationModel model) {
        List<LookUp> locList = getModel().getLocation();
        List<LocationMasEntity> location = iLocationMasService.findWZMappedLocationByOrgIdAndDeptId(
                UserSession.getCurrent().getOrganisation().getOrgid(),
                getModel().getDeptId());
        if (location != null && !location.isEmpty()) {
            location.forEach(loc -> {
                LookUp lookUp = new LookUp();
                lookUp.setLookUpId(loc.getLocId());
                lookUp.setDescLangFirst(loc.getLocNameEng());
                lookUp.setDescLangSecond(loc.getLocNameReg());
                locList.add(lookUp);
            });
        }
        /*
         * TbBillMas billMas = model.getAuthComBillList().get(model.getAuthComBillList().size() - 1); List<BillDisplayDto>
         * billDisDtoList = propertyService.getTaxListForDisplay(billMas, UserSession.getCurrent().getOrganisation(),
         * model.getDeptId()); getModel().setDisplayMapAuthComp(propertyService.getTaxMapForDisplayCategoryWise(billDisDtoList,
         * UserSession.getCurrent().getOrganisation(), model.getDeptId()));
         */
        /*
         * getModel().setAuthCompBeforeDto(
         * iProvisionalAssesmentMstService.copyProvDtoDataToOtherDto(model.getProvisionalAssesmentMstDto()));
         */

    }

}
