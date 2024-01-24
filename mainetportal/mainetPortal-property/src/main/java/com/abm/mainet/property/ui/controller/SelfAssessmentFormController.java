package com.abm.mainet.property.ui.controller;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicLong;
import java.util.stream.Collectors;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.abm.mainet.common.constant.MainetConstants;
import com.abm.mainet.common.domain.Organisation;
import com.abm.mainet.common.dto.TbBillMas;
import com.abm.mainet.common.exception.FrameworkException;
import com.abm.mainet.common.service.IPortalServiceMasterService;
import com.abm.mainet.common.ui.controller.AbstractFormController;
import com.abm.mainet.common.util.CommonMasterUtility;
import com.abm.mainet.common.util.LookUp;
import com.abm.mainet.common.util.UserSession;
import com.abm.mainet.common.util.Utility;
import com.abm.mainet.dms.service.FileUploadServiceValidator;
import com.abm.mainet.property.dto.ArrayOfDiversionPlotDetails;
import com.abm.mainet.property.dto.ArrayOfKhasraDetails;
import com.abm.mainet.property.dto.ArrayOfPlotDetails;
import com.abm.mainet.property.dto.AssessmentChargeCalDTO;
import com.abm.mainet.property.dto.BillDisplayDto;
import com.abm.mainet.property.dto.ExcelDownloadDTO;
import com.abm.mainet.property.dto.ExcelSheetDto;
import com.abm.mainet.property.dto.LandTypeApiDetailRequestDto;
import com.abm.mainet.property.dto.NoDuesCertificateDto;
import com.abm.mainet.property.dto.PropertyRoomDetailsDto;
import com.abm.mainet.property.dto.ProvisionalAssesmentDetailDto;
import com.abm.mainet.property.dto.ProvisionalAssesmentFactorDtlDto;
import com.abm.mainet.property.dto.ProvisionalAssesmentMstDto;
import com.abm.mainet.property.dto.ProvisionalAssesmentOwnerDtlDto;
import com.abm.mainet.property.dto.SelfAssessmentDeaultValueDTO;
import com.abm.mainet.property.dto.SelfAssessmentFinancialDTO;
import com.abm.mainet.property.dto.WriteExcelData;
import com.abm.mainet.property.service.PropertyNoDuesCertificateService;
import com.abm.mainet.property.service.SelfAssessmentService;
import com.abm.mainet.property.ui.model.PropertyNoDuesCertificateModel;
import com.abm.mainet.property.ui.model.SelfAssesmentNewModel;
import com.abm.mainet.property.ui.validator.RuleErrorValidator;
import com.fasterxml.jackson.databind.ObjectMapper;

@Controller
@RequestMapping("/SelfAssessmentForm.html")
public class SelfAssessmentFormController extends AbstractFormController<SelfAssesmentNewModel> {
    @Autowired
    private SelfAssessmentService selfAssessmentService;

    @Autowired
    private IPortalServiceMasterService serviceMaster;
    
    @Autowired
	private PropertyNoDuesCertificateService propertyNoDuesCertificate;

    @RequestMapping(method = { RequestMethod.POST, RequestMethod.GET })
    public ModelAndView index(HttpServletRequest request) throws Exception {
        this.sessionCleanup(request);
        if(Utility.isEnvPrefixAvailable(UserSession.getCurrent().getOrganisation(), MainetConstants.APP_NAME.SKDCL)) {
        	setCommonFeildsSkdcl();
        	this.getModel().setAssType(MainetConstants.Property.NEW_ASESS);
        	getModel().setConstructFlag(MainetConstants.FlagY);
        	getModel().setRoomTypeList(UserSession.getCurrent().getOrganisation());
            getModel().setRoomTypeJson(new ObjectMapper().writeValueAsString(getModel().getRoomTypeMap()));
        	return new ModelAndView("SelfAssessmentForm", MainetConstants.FORM_NAME, this.getModel());
        }
        return new ModelAndView("SelfAssessmentTermCondition", MainetConstants.FORM_NAME, this.getModel());
    }

    @RequestMapping(params = "accept", method = RequestMethod.POST)
    public ModelAndView conditionAccept(HttpServletRequest request) throws Exception {
        this.sessionCleanup(request);
        FileUploadServiceValidator.getCurrent().sessionCleanUpForFileUpload();
        SelfAssesmentNewModel model = getModel();
        setCommonFields(model);
        Organisation org = UserSession.getCurrent().getOrganisation();

        SelfAssessmentDeaultValueDTO data = selfAssessmentService.setAllDefaultFields(org.getOrgid(), getModel().getDeptId());
        data.setOrgId(org.getOrgid());
        data.setDeptId(getModel().getDeptId());
        model.setLeastFinYear(data.getLeastFinYear());
        model.setLocation(data.getLocation());
        model.setSchedule(data.getSchedule());
        model.setAssType(MainetConstants.Property.NEW_ASESS);
        model.setSelfAss(MainetConstants.Property.Y);
        model.setFinancialYearMap(data.getFinancialYearMap());
        model.setDefaultData(data);
        model.setFinYearList(data.getFinYearList());
        return defaultMyResult();
    }

    private void setCommonFields(SelfAssesmentNewModel model) {
        Long orgId = UserSession.getCurrent().getOrganisation().getOrgid();
        final Long serviceId = serviceMaster.getServiceId(MainetConstants.Property.SAS, orgId);
        final Long deptId = serviceMaster.getServiceDeptIdId(serviceId);
        model.setOrgId(orgId);
        model.setDeptId(deptId);
        model.setServiceId(serviceId);
        model.setIntgrtionWithBP(CommonMasterUtility
                .getDefaultValueByOrg(MainetConstants.Property.IBP, UserSession.getCurrent().getOrganisation()).getLookUpCode());
        String lookUpCode=CommonMasterUtility.getDefaultValueByOrg(MainetConstants.Property.APP, UserSession.getCurrent().getOrganisation()).getLookUpCode();
        model.getProvisionalAssesmentMstDto().setPartialAdvancePayStatus(lookUpCode);
    }

    private void setCommonFeildsSkdcl() {
		
		Long orgId = UserSession.getCurrent().getOrganisation().getOrgid();
		getModel().setOrgId(orgId);
		NoDuesCertificateDto noDueDto = new NoDuesCertificateDto();
		noDueDto.setOrgId(orgId);
		noDueDto.setSmShortCode("SAS");
		noDueDto = propertyNoDuesCertificate.getPropertyServiceData(noDueDto);
		getModel().setServiceId(noDueDto.getSmServiceId());
		getModel().setServiceShortCode((noDueDto.getSmShortCode()));
		getModel().setDeptId(noDueDto.getDeptId());
    }
    
    @RequestMapping(params = "getChecklist", method = RequestMethod.POST)
    public ModelAndView getChecklist(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse) {
        getModel().bind(httpServletRequest);
        SelfAssesmentNewModel model = this.getModel();
        this.getModel().setDropDownValues(UserSession.getCurrent().getOrganisation());
        if(Utility.isEnvPrefixAvailable(UserSession.getCurrent().getOrganisation(), MainetConstants.APP_NAME.SKDCL)) {
        	model.setMultipleYearAssessment(model.getProvisionalAssesmentMstDto());
        }
        getCheckListAndcharges(model);
        if(Utility.isEnvPrefixAvailable(UserSession.getCurrent().getOrganisation(), MainetConstants.APP_NAME.SKDCL)) {
        	model.showOnlySelectedFinYearAssessment(model.getProvisionalAssesmentMstDto());
        }
		model.validateBean(model.getProvisionalAssesmentMstDto(), RuleErrorValidator.class);
		if (model.hasValidationErrors()) {
			return defaultMyResult();
		}
        return new ModelAndView("SelfAssessmentView", MainetConstants.FORM_NAME, model);
    }
    
    @RequestMapping(params = "proceed", method = RequestMethod.POST)
    public ModelAndView confirmToProceed(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse) {
        getModel().bind(httpServletRequest);
        SelfAssesmentNewModel model = this.getModel();
        this.getModel().setDropDownValues(UserSession.getCurrent().getOrganisation());
        getCheckListAndcharges(model);
        // D#18545 Error MSG for Rule found or not
		model.validateBean(model.getProvisionalAssesmentMstDto(), RuleErrorValidator.class);
		if (model.hasValidationErrors()) {
			return defaultMyResult();
		}
        return new ModelAndView("SelfAssessmentView", MainetConstants.FORM_NAME, model);
    }

    private void getCheckListAndcharges(SelfAssesmentNewModel model) {
        ProvisionalAssesmentMstDto dto = model.getProvisionalAssesmentMstDto();
        
        dto.getProvisionalAssesmentDetailDtoList().forEach(detailList ->{
        	if(detailList.getFirstAssesmentDate() != null) {
        		detailList.setFirstAssesmentStringDate(new SimpleDateFormat(MainetConstants.DATE_FORMAT)
                        .format(detailList.getFirstAssesmentDate()));
        	}
        	detailList.setLastAssesmentDate(detailList.getFirstAssesmentDate());
        });
        dto.setOrgId(model.getOrgId());
        dto.setSmServiceId(model.getServiceId());
        model.getDefaultData().setProvisionalMas(dto);
        model.getDefaultData().setProvAsseFactDtlDto(model.getProvAsseFactDtlDto());
        model.getDefaultData().setFinYearList(model.getFinYearList());
        AssessmentChargeCalDTO checklistCharge = selfAssessmentService.fetchChecklistAndCharges(model.getDefaultData());
        model.setDemandLevelRebateList(checklistCharge.getDemandLevelRebateList());
        model.setCheckList(checklistCharge.getCheckList());
        checklistCharge.getProvisionalMas().setBillTotalAmt(checklistCharge.getBillTotalAmt());
        model.setBillMasList(checklistCharge.getBillMasList());
        Map<String, List<BillDisplayDto>> displayMap = selfAssessmentService.getTaxMapForDisplayCategoryWise(
                checklistCharge.getDisplayDto(),
                UserSession.getCurrent().getOrganisation(), model.getDeptId(), checklistCharge.getTaxCatList());
        model.setProvisionalAssesmentMstDto(checklistCharge.getProvisionalMas());
        model.setDisplayMap(displayMap);
    }

    @RequestMapping(params = "getFactorValueDiv", method = RequestMethod.POST)
    public ModelAndView getFactorValueDiv(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse,
            @RequestParam(value = "factorCode") String factorCode) {
        this.getModel().bind(httpServletRequest);
        SelfAssesmentNewModel model = this.getModel();
        LookUp lookup = model.getFactorLookup(factorCode);
        model.setFactorPrefix(lookup.getLookUpCode());
        model.setFactorId(lookup.getLookUpId());
        return new ModelAndView("SelfAssessmentUnitSpecificAdditionalInfo", MainetConstants.FORM_NAME, model);
    }

    @RequestMapping(params = "getMappingFactor")
    public @ResponseBody Map<Long, String> getMappingFactor(@RequestParam("factorType") String factorType) {
        Organisation org = UserSession.getCurrent().getOrganisation();
        Map<Long, String> factorMap = new HashMap<>(0);
        List<LookUp> factorLookup = CommonMasterUtility.getLookUps(factorType, org);
        for (LookUp lookUp : factorLookup) {
            factorMap.put(lookUp.getLookUpId(), lookUp.getLookUpDesc());
        }
        return factorMap;
    }

    @RequestMapping(params = "getOwnershipTypeDiv", method = RequestMethod.POST)
    public ModelAndView getOwnershipTypeDiv(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse,
            @RequestParam(value = "ownershipType") String ownershipType) {
        this.getModel().bind(httpServletRequest);
        SelfAssesmentNewModel model = this.getModel();
        model.setOwnershipPrefix(ownershipType);
        LookUp lookup = CommonMasterUtility.getValueFromPrefixLookUp(ownershipType, MainetConstants.Property.propPref.OWT);
        model.setOwnershipTypeValue(lookup.getDescLangFirst());
        model.getProvisionalAssesmentMstDto().setAssOwnerType(lookup.getLookUpId());
        return new ModelAndView("SelfAssessmentOwnershipDetailForm", MainetConstants.FORM_NAME, model);
    }

    @RequestMapping(params = "getFinancialYear", method = RequestMethod.POST)
    public @ResponseBody Long getyearOfAcquisition(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse,
            String yearOfAcq) {
        this.getModel().bind(httpServletRequest);
        SelfAssesmentNewModel model = this.getModel();
        model.getProvisionalAssesmentMstDto().getProvisionalAssesmentDetailDtoList().clear();
        Date currentDate = Utility.stringToDate(yearOfAcq);
        model.getDefaultData().setUiDate(currentDate);
        SelfAssessmentFinancialDTO dto = selfAssessmentService.getFinyearDate(model.getDefaultData());
        return dto.getFinYearId();
    }

    @RequestMapping(params = "deleteUnitTableRow", method = RequestMethod.POST)
    public @ResponseBody void deleteUnitTableRow(HttpServletRequest httpServletRequest,
            HttpServletResponse httpServletResponse, @RequestParam(value = "deletedRowCount") int deletedRowCount) {
        this.getModel().bind(httpServletRequest);
        SelfAssesmentNewModel model = this.getModel();
        if (!model.getProvisionalAssesmentMstDto().getProvisionalAssesmentDetailDtoList().isEmpty()
                && model.getProvisionalAssesmentMstDto().getProvisionalAssesmentDetailDtoList().size() > 1) {
        ProvisionalAssesmentDetailDto detDto = model.getProvisionalAssesmentMstDto().getProvisionalAssesmentDetailDtoList()
                .get(deletedRowCount);
        model.getProvisionalAssesmentMstDto().getProvisionalAssesmentDetailDtoList().remove(detDto);
        }
    }

    @RequestMapping(params = "deleteUnitSpecificInfo", method = RequestMethod.POST)
    public @ResponseBody void deleteUnitSpecificInfo(HttpServletRequest httpServletRequest,
            HttpServletResponse httpServletResponse, @RequestParam(value = "deletedUnitRow") Integer deletedUnitRow) {
        this.getModel().bind(httpServletRequest);
        SelfAssesmentNewModel model = this.getModel();
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
        SelfAssesmentNewModel model = this.getModel();
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
        SelfAssesmentNewModel model = this.getModel();
        model.setOwnerDetailTableCount(deletedOwnerRowId);
        for (ProvisionalAssesmentOwnerDtlDto dto : model.getProvisionalAssesmentMstDto()
                .getProvisionalAssesmentOwnerDtlDtoList()) {
            if (model.getOwnerDetailTableCount() == deletedOwnerRowId) {
                model.getProvisionalAssesmentMstDto().getProvisionalAssesmentOwnerDtlDtoList().remove(dto);
            }
        }
    }

    @RequestMapping(params = "getFinanceYearListFromGivenDate", method = RequestMethod.POST)
    public @ResponseBody List<Long> getFinanceYearListFromGivenDate(HttpServletRequest httpServletRequest,
            HttpServletResponse httpServletResponse, @RequestParam(value = "finYearId") Long finYearId) {
        getModel().getDefaultData().setGivenfinYearId(finYearId);
        SelfAssessmentFinancialDTO dto = selfAssessmentService.fetchFromGivenDate(getModel().getDefaultData());

        this.getModel().setFinYearList(dto.getFinYearList());

        return dto.getFinYearList();
    }

    @RequestMapping(params = "getNextScheduleFromLastPayDet", method = RequestMethod.POST)
    public @ResponseBody List<Object> getNextScheduleFromLastPayDet(HttpServletRequest httpServletRequest,
            HttpServletResponse httpServletResponse, @RequestParam(value = "schDetId") Long schDetId) {
        getModel().getDefaultData().setScheduleDetId(schDetId);
        SelfAssessmentFinancialDTO dto = selfAssessmentService.fetchScheduleDate(getModel().getDefaultData());
        return dto.getAcqDateDetail();
    }

    @RequestMapping(params = "editSelfAssForm", method = RequestMethod.POST)
    public ModelAndView editForm(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse) {
        getModel().bind(httpServletRequest);
        FileUploadServiceValidator.getCurrent().sessionCleanUpForFileUpload();
        getModel().setSelfAss(null);
        SelfAssesmentNewModel model = this.getModel();
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
        return new ModelAndView("SelfAssessmentFormEdit", MainetConstants.FORM_NAME, model);
    }

    @RequestMapping(params = "compareDate", method = RequestMethod.POST)
    public @ResponseBody Date compareDate(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse,
            @RequestParam(value = "finId") Long finId) {

        AtomicLong lastYearDate = new AtomicLong(0);
        getModel().getDefaultData().getFinancialYearDate().forEach((fromDate, financYearId) -> {
            if (financYearId.equals(finId)) {
                lastYearDate.addAndGet(fromDate.getTime());
            }
        });
        return new Date(lastYearDate.get());
    }

    @RequestMapping(params = "displayYearListBasedOnDate", method = RequestMethod.POST)
    public @ResponseBody Map<Long, String> displayYearListBasedOnDate(HttpServletRequest httpServletRequest,
            @RequestParam(value = "finYearId") Long finYearId) throws Exception {
        getModel().getDefaultData().setGivenfinYearId(finYearId);
        SelfAssessmentFinancialDTO dto = selfAssessmentService.displayYearList(getModel().getDefaultData());
        this.getModel().setFinancialYearMap(dto.getYearMap());
        return dto.getYearMap();
    }

    @RequestMapping(params = "displayYear", method = RequestMethod.POST)
    public @ResponseBody String displayYear(HttpServletRequest httpServletRequest,
            @RequestParam(value = "finYearId") Long finYearId) throws Exception {
    	getModel().getDefaultData().setGivenfinYearId(finYearId);
        SelfAssessmentFinancialDTO dto = selfAssessmentService.displayYearList(getModel().getDefaultData());
        this.getModel().setFinYearList(dto.getFinYearList());
		return dto.getFinYear();
    }
    
    @SuppressWarnings({ "rawtypes", "unchecked" })
    @RequestMapping(params = "downloadSheet", method = RequestMethod.GET)
    public void exportRateExcelData(final HttpServletResponse response, final HttpServletRequest request) {
        try {
            getModel().bind(request);
            List<ExcelSheetDto> dtoList = new LinkedList<>();
            if (this.getModel().getBillMasList() != null && !this.getModel().getBillMasList().isEmpty()) {
                TbBillMas currBill = this.getModel().getBillMasList().get(this.getModel().getBillMasList().size() - 1);
                ExcelSheetDto excelHeadDto = new ExcelSheetDto();
                excelHeadDto.setTaxName(getApplicationSession().getMessage("propertyTax.TaxName"));
                excelHeadDto.setSrNo(getApplicationSession().getMessage("propertyTax.SrNo"));
                AtomicInteger count = new AtomicInteger(1);
                ExcelDownloadDTO data = new ExcelDownloadDTO();
                data.setBillMasList(this.getModel().getBillMasList());
                data.setOrgId(UserSession.getCurrent().getOrganisation().getOrgid());
                data = selfAssessmentService.getAllBillschedulByOrgid(data);

                excelHeadDto.setTaxAmount(data.getTaxAmountList());// Setting Schedule name as header
                dtoList.add(excelHeadDto);// Excel Sheet Header Row as Header is also Dynamic
                // Taking all taxes from current Bill as tax carry forward
                currBill.getTbWtBillDet().forEach(taxDet -> {// for each Tax row
                    ExcelSheetDto excelDto = new ExcelSheetDto();
                    List<String> taxAmount = new LinkedList<>();
                    excelDto.setTaxName(taxDet.getTaxDesc());// tax name
                    this.getModel().getBillMasList().forEach(mas -> {
                        boolean hasTax = mas.getTbWtBillDet().stream()
                                .anyMatch(billDetail -> billDetail.getTaxId().equals(taxDet.getTaxId()));
                        mas.getTbWtBillDet().parallelStream()
                                .filter(det -> taxDet.getTaxId().equals(det.getTaxId()))
                                .forEach(det -> {
                                    taxAmount.add(Double.toString(det.getBdCurTaxamt()));
                                });
                        if (!hasTax) {
                            taxAmount.add("0.0");
                        }
                    });
                    excelDto.setSrNo(count.toString());
                    count.incrementAndGet();
                    excelDto.setTaxAmount(taxAmount);
                    dtoList.add(excelDto);
                });

                ExcelSheetDto excelTotalDto = new ExcelSheetDto();
                excelTotalDto.setTaxName("Total");
                List<String> totAmount = new LinkedList<>();

                this.getModel().getBillMasList().forEach(mas -> {
                    totAmount.add(Double.toString(mas.getBmTotalOutstanding()));
                });
                excelTotalDto.setTaxAmount(totAmount);
                dtoList.add(excelTotalDto);// Last row of Excel Sheet
            }

            WriteExcelData data = new WriteExcelData("Tax_Wise_Details_Sheet" + ".xlsx",
                    request, response);
            data.getExpotedExcelSheet(dtoList, ExcelSheetDto.class);

        } catch (Exception e) {
        	throw new FrameworkException(e);
        }
    }
    
    
    @RequestMapping(params = "getLandTypeDetails", method = RequestMethod.POST)
    public ModelAndView getLandTypeDetailsDiv(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse,
            @RequestParam(value = "landType") String landType) {
        this.getModel().bind(httpServletRequest);
        SelfAssesmentNewModel model = this.getModel();
        model.setLandTypePrefix(landType);
        LandTypeApiDetailRequestDto dto= new LandTypeApiDetailRequestDto();
        dto.setLandType(landType);
        List<LookUp> districtList= selfAssessmentService.findDistrictByLandType(dto);
        getModel().setDistrict(districtList);
        return new ModelAndView("PropertyLandDetails", MainetConstants.FORM_NAME, model);
    }

    @RequestMapping(params = "getTehsilListByDistrict", method = RequestMethod.POST)
    public @ResponseBody List<LookUp> getObjectionServiceByDept(final HttpServletRequest httpServletRequest,
            final HttpServletResponse httpServletResponse, @RequestParam("districtId") String districtId, @RequestParam(value = "landType") String landType) {
    	this.getModel().getTehsil().clear();
    	LandTypeApiDetailRequestDto dto= new LandTypeApiDetailRequestDto();
        dto.setLandType(landType);
        dto.setDistrictId(districtId);
    	List<LookUp> tehsilList = selfAssessmentService.getTehsilListByDistrict(dto);
        getModel().setTehsil(tehsilList);
        return tehsilList;
    }

    @RequestMapping(params = "getVillageListByTehsil", method = RequestMethod.POST)
    public @ResponseBody List<LookUp> getVillageListByTehsil(final HttpServletRequest httpServletRequest,
            final HttpServletResponse httpServletResponse, @RequestParam("tehsilId") String tehsilId,@RequestParam("districtId") String districtId, @RequestParam(value = "landType") String landType) {
    	this.getModel().getVillage().clear();
    	LandTypeApiDetailRequestDto dto= new LandTypeApiDetailRequestDto();
    	dto.setLandType(landType);
        dto.setDistrictId(districtId);
        dto.setTehsilId(tehsilId);
        List<LookUp> villageList = selfAssessmentService.getVillageListByTehsil(dto);
        getModel().setVillage(villageList);
        return villageList;
    }

    @RequestMapping(params = "getMohallaListByVillageId", method = RequestMethod.POST)
    public @ResponseBody List<LookUp> getMohallaListByVillageId(final HttpServletRequest httpServletRequest,
            final HttpServletResponse httpServletResponse, @RequestParam("villageId") String villageId,@RequestParam("tehsilId") String tehsilId,@RequestParam("districtId") String districtId, @RequestParam(value = "landType") String landType) {
    	this.getModel().getMohalla().clear();
    	LandTypeApiDetailRequestDto dto= new LandTypeApiDetailRequestDto();
    	dto.setLandType(landType);
        dto.setDistrictId(districtId);
        dto.setTehsilId(tehsilId);
        dto.setVillageId(villageId);
    	List<LookUp> mohallaList = selfAssessmentService.getMohallaListByVillageId(dto);
        getModel().setMohalla(mohallaList);
        return mohallaList;
    }

    @RequestMapping(params = "getStreetListByMohallaId", method = RequestMethod.POST)
    public @ResponseBody List<LookUp> getStreetListByMohallaId(final HttpServletRequest httpServletRequest,
            final HttpServletResponse httpServletResponse, @RequestParam("mohallaId") String mohallaId,@RequestParam("villageId") String villageId,@RequestParam("tehsilId") String tehsilId,@RequestParam("districtId") String districtId, @RequestParam(value = "landType") String landType) {
        this.getModel().getBlockStreet().clear();
        LandTypeApiDetailRequestDto dto= new LandTypeApiDetailRequestDto();
    	dto.setLandType(landType);
        dto.setDistrictId(districtId);
        dto.setTehsilId(tehsilId);
        dto.setVillageId(villageId);
        dto.setMohallaId(mohallaId);
        List<LookUp> streetList = selfAssessmentService.getStreetListByMohallaId(dto);       
        getModel().setBlockStreet(streetList);
        return streetList;
    }

    @RequestMapping(params = "getKhasaraDetails", method = RequestMethod.POST)
    public ModelAndView getKhasaraDetails(final HttpServletRequest httpServletRequest,
            final HttpServletResponse httpServletResponse, @RequestParam("landType") String landType,
            @RequestParam("khasara") String khasara, @RequestParam("districtId") String districtId,
            @RequestParam("TehsilId") String TehsilId, @RequestParam("villageId") String villageId) {
    	LandTypeApiDetailRequestDto dto= new LandTypeApiDetailRequestDto();
     	dto.setLandType(landType);
        dto.setDistrictId(districtId);
        dto.setTehsilId(TehsilId);
        dto.setVillageId(villageId);
    	dto.setKhasraNo(khasara);
    	ArrayOfKhasraDetails response = selfAssessmentService.getKhasraDetails(dto);
    	SelfAssesmentNewModel model = this.getModel();
        model.setArrayOfKhasraDetails(response);        
        return new ModelAndView("showPropertyKhasaraDetails", MainetConstants.FORM_NAME, model);
    }

    @RequestMapping(params = "getNajoolAndDiversionDetails", method = RequestMethod.POST)
    public ModelAndView getNajoolAndDiversionDetails(final HttpServletRequest httpServletRequest,
            final HttpServletResponse httpServletResponse, @RequestParam("landType") String landType,
            @RequestParam("plotNo") String plotNo, @RequestParam("districtId") String districtId,
            @RequestParam("TehsilId") String TehsilId, @RequestParam("villageId") String villageId,
            @RequestParam("mohallaId") String mohallaId, @RequestParam("streetNo") String streetNo) {
    	SelfAssesmentNewModel model = this.getModel();
        LandTypeApiDetailRequestDto dto= new LandTypeApiDetailRequestDto();
     	dto.setLandType(landType);
        dto.setDistrictId(districtId);
        dto.setTehsilId(TehsilId);
        dto.setVillageId(villageId);
        dto.setMohallaId(mohallaId);
        dto.setStreetNo(streetNo);
    	dto.setPlotNo(plotNo);
    	dto.setLandTypePrefix(landType);
        if(model.getLandTypePrefix().equals(MainetConstants.Property.NZL)) {
        ArrayOfPlotDetails response = selfAssessmentService.getNajoolDetails(dto); 
        model.setArrayOfPlotDetails(response);
        }
        if(model.getLandTypePrefix().equals(MainetConstants.Property.DIV)) {
        ArrayOfDiversionPlotDetails response = selfAssessmentService.getDiversionDetails(dto);        	
        model.setArrayOfDiversionPlotDetails(response);
        }
        
        return new ModelAndView("showPropertyKhasaraDetails", MainetConstants.FORM_NAME, model);
    }
    
    @RequestMapping(params = "getLandTypeApiDetails", method = RequestMethod.POST)
    public ModelAndView getLandTypeApiDetails(final HttpServletRequest httpServletRequest,
            final HttpServletResponse httpServletResponse) {
    	SelfAssesmentNewModel model = this.getModel();
        LandTypeApiDetailRequestDto dto = new LandTypeApiDetailRequestDto();
        if(model.getLandTypePrefix().equals(MainetConstants.Property.KPK)) {
        	ArrayOfKhasraDetails response = selfAssessmentService.getKhasraDetails(dto);
            getModel().setArrayOfKhasraDetails(response);
       }            
        else if(model.getLandTypePrefix().equals(MainetConstants.Property.NZL)) {        
        	ArrayOfPlotDetails response = selfAssessmentService.getNajoolDetails(dto); 
            model.setArrayOfPlotDetails(response);
        }
        else if( model.getLandTypePrefix().equals(MainetConstants.Property.DIV)) {
        	ArrayOfDiversionPlotDetails response = selfAssessmentService.getDiversionDetails(dto);        	
        	model.setArrayOfDiversionPlotDetails(response);  
        }           
        return new ModelAndView("showPropertyKhasaraDetails", MainetConstants.FORM_NAME, model);
    }
    
    @RequestMapping(params = "getKhasraNoList", method = { RequestMethod.POST, RequestMethod.GET })
    public @ResponseBody List<LookUp> getKhasraNoList(final HttpServletRequest httpServletRequest,
            final HttpServletResponse httpServletResponse, @RequestParam("villageId") String villageId,
            @RequestParam("tehsilId") String tehsilId, @RequestParam("districtId") String districtId,
            @RequestParam(value = "landType") String landType, @RequestParam("khasara") String khasara) {
    	ProvisionalAssesmentMstDto assDto=getModel().getProvisionalAssesmentMstDto();
    	LandTypeApiDetailRequestDto dto = new LandTypeApiDetailRequestDto();
        dto.setLandType(landType);
        dto.setDistrictId(districtId);
        dto.setTehsilId(tehsilId);
        dto.setVillageId(villageId);
        dto.setKhasraNo(khasara);
        List<LookUp> khasraNoList = selfAssessmentService.getKhasraNoList(dto);
        LandTypeApiDetailRequestDto requestDto=selfAssessmentService.getVsrNo(dto);        
        assDto.setAssVsrNo(requestDto.getVillageCode());
        getModel().setKhasraNo(khasraNoList);
        return khasraNoList;
    }
    
    @RequestMapping(params = "getNajoolPlotList", method = { RequestMethod.POST, RequestMethod.GET })
    public @ResponseBody List<LookUp> getNajoolPlotList(final HttpServletRequest httpServletRequest,
            final HttpServletResponse httpServletResponse, @RequestParam("landType") String landType,
            @RequestParam("districtId") String districtId,
            @RequestParam("TehsilId") String TehsilId, @RequestParam("villageId") String villageId,
            @RequestParam("mohallaId") String mohallaId, @RequestParam("streetNo") String streetNo,
            @RequestParam("plotNo") String plotNo) {
        LandTypeApiDetailRequestDto dto = new LandTypeApiDetailRequestDto();
        dto.setLandType(landType);
        dto.setDistrictId(districtId);
        dto.setTehsilId(TehsilId);
        dto.setVillageId(villageId);
        dto.setMohallaId(mohallaId);
        dto.setStreetNo(streetNo);
        dto.setPlotNo(plotNo);
        List<LookUp> najoolPlotList = selfAssessmentService.getNajoolPlotList(dto);
        getModel().setPlotNo(najoolPlotList);
        return najoolPlotList;
    }

    @RequestMapping(params = "getDiversionPlotList", method = { RequestMethod.POST, RequestMethod.GET })
    public @ResponseBody List<LookUp> getDiversionPlotList(final HttpServletRequest httpServletRequest,
            final HttpServletResponse httpServletResponse, @RequestParam("landType") String landType,
            @RequestParam("districtId") String districtId,
            @RequestParam("TehsilId") String TehsilId, @RequestParam("villageId") String villageId,
            @RequestParam("mohallaId") String mohallaId, @RequestParam("streetNo") String streetNo,
            @RequestParam("plotNo") String plotNo) {
        LandTypeApiDetailRequestDto dto = new LandTypeApiDetailRequestDto();
        dto.setLandType(landType);
        dto.setDistrictId(districtId);
        dto.setTehsilId(TehsilId);
        dto.setVillageId(villageId);
        dto.setMohallaId(mohallaId);
        dto.setStreetNo(streetNo);
        dto.setPlotNo(plotNo);
        List<LookUp> diversionPlotList = selfAssessmentService.getDiversionPlotList(dto);
        getModel().setPlotNo(diversionPlotList);
        return diversionPlotList;
    }
    
    @RequestMapping(params = "deleteLandEntry", method = RequestMethod.POST)
    public @ResponseBody void deleteLandEntry(HttpServletRequest httpServletRequest,
            HttpServletResponse httpServletResponse) {
        this.getModel().bind(httpServletRequest);
        SelfAssesmentNewModel model = this.getModel();
        model.getProvisionalAssesmentMstDto().setAssLandType(null);
        model.getProvisionalAssesmentMstDto().setAssLandTypeDesc(null);
        model.setLandTypePrefix(null);
        model.getProvisionalAssesmentMstDto().setAssDistrict(null);
        model.getProvisionalAssesmentMstDto().setAssDistrictDesc(null);
        model.getProvisionalAssesmentMstDto().setAssTahasil(null);
        model.getProvisionalAssesmentMstDto().setAssTahasilDesc(null);
        model.getProvisionalAssesmentMstDto().setTppVillageMauja(null);
        model.getProvisionalAssesmentMstDto().setTppVillageMaujaDesc(null);
        model.getProvisionalAssesmentMstDto().setMohalla(null);
        model.getProvisionalAssesmentMstDto().setMohallaDesc(null);
        model.getProvisionalAssesmentMstDto().setAssStreetNo(null);
        model.getProvisionalAssesmentMstDto().setAssStreetNoDesc(null);
        model.getProvisionalAssesmentMstDto().setTppPlotNo(null);
        model.getProvisionalAssesmentMstDto().setTppPlotNoCs(null);
        model.setArrayOfKhasraDetails(null);
        model.setArrayOfDiversionPlotDetails(null);
        model.setArrayOfPlotDetails(null);
    }
    
    @RequestMapping(params = "saveFormOnDraft", method = RequestMethod.POST)
    public void saveFormOnDraft(HttpServletRequest request, @RequestParam("draftFlag") String draftFlag) {
        getModel().bind(request);
        SelfAssesmentNewModel model = this.getModel();
        model.setDraftModeFlag(draftFlag);        
         
    }

    @RequestMapping(params = "addRoomDetails", method = RequestMethod.POST)
	public ModelAndView addRoomDetails(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse) {
		getModel().bind(httpServletRequest);
		List<ProvisionalAssesmentDetailDto> detailList = getModel().getProvisionalAssesmentMstDto()
				.getProvisionalAssesmentDetailDtoList();
		if (CollectionUtils.isNotEmpty(detailList)) {
			if(StringUtils.isNotBlank(getModel().getCountOfRow())) {
				if((Integer.parseInt(getModel().getCountOfRow()) < detailList.size())) {
					httpServletRequest.setAttribute("carpetArea",
							detailList.get(Integer.parseInt(getModel().getCountOfRow())).getCarpetArea());				
				}				
			}
		}
		return new ModelAndView("SelfAssessRoomDetails", MainetConstants.FORM_NAME, this.getModel());
	}

    @RequestMapping(params = "saveRoomDetails", method = RequestMethod.POST)
	public ModelAndView saveRoomDetails(HttpServletRequest httpServletRequest,
			HttpServletResponse httpServletResponse) {
		getModel().bind(httpServletRequest);
		getModel().setCopyOfRow(null);
		ProvisionalAssesmentDetailDto detailDto=this.getModel().getProvisionalAssesmentMstDto().getProvisionalAssesmentDetailDtoList()
				.get(Integer.parseInt(getModel().getCountOfRow()));
		List<Double> lengthList=new ArrayList<>();
		List<Double> widthList=new ArrayList<>();
		List<Double> areaList=new ArrayList<>();
		for(PropertyRoomDetailsDto room:detailDto.getRoomDetailsDtoList()) {
			if(room.getRoomLength() != null && room.getRoomWidth() != null) {
				lengthList.add(room.getRoomLength());
				widthList.add(room.getRoomWidth());
			}
		}
		if(CollectionUtils.isNotEmpty(lengthList) && CollectionUtils.isNotEmpty(widthList)) {
			List<Long> roomTypeList = detailDto.getRoomDetailsDtoList().stream().map(PropertyRoomDetailsDto::getRoomType).collect(Collectors.toList());
			for (int i = 0; i < lengthList.size(); i++) {
				Double area = lengthList.get(i) * widthList.get(i);
				Double calArea=0.0;
				if(MainetConstants.Property.ZERO.equals(getModel().getRoomTypeMap().get(roomTypeList.get(i)))){
					calArea = area * 0.0;
				}else if(MainetConstants.Property.HALF.equals(getModel().getRoomTypeMap().get(roomTypeList.get(i)))){
					calArea = area / 2;
				}else {
					calArea = area;
				}
				areaList.add(calArea);
			}
			detailDto.setAssdBuildupArea(Utility.round(areaList.stream().mapToDouble(Double::doubleValue).sum(), 2));
			detailDto.setCarpetArea(Utility.round(detailDto.getRoomDetailsDtoList().stream().mapToDouble(PropertyRoomDetailsDto::getRoomArea).sum(), 2));
			detailDto.setRoomTotalArea(detailDto.getRoomDetailsDtoList().stream().mapToDouble(PropertyRoomDetailsDto::getRoomArea).sum());
			this.getModel().getProvisionalAssesmentMstDto().getProvisionalAssesmentDetailDtoList().set(Integer.parseInt(getModel().getCountOfRow()), detailDto);
		}
		return new ModelAndView("SelfAssessmentFormValidn", MainetConstants.FORM_NAME,  this.getModel());
	}
    
	@RequestMapping(params = "removeRoomDetails", method = RequestMethod.POST)
	public @ResponseBody Boolean removeRoomDetails(HttpServletRequest httpServletRequest,
			HttpServletResponse httpServletResponse, @RequestParam("countOfRow") Long countOfRow,
			@RequestParam("index") Long index) {
		this.getModel().bind(httpServletRequest);
		SelfAssesmentNewModel model = this.getModel();
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
	
	@ResponseBody
	@RequestMapping(params = "copyRoomDetails", method = RequestMethod.POST)
	public Boolean copyRoomDetails(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse,
			@RequestParam("countOfRow") Long countOfRow, @RequestParam("index") Long index) {
        this.getModel().bind(httpServletRequest);
        SelfAssesmentNewModel model = this.getModel();
        if(countOfRow <= index) {
        	List<ProvisionalAssesmentDetailDto> detailDtoList = model.getProvisionalAssesmentMstDto().getProvisionalAssesmentDetailDtoList();
            ProvisionalAssesmentDetailDto detailDto = model.getProvisionalAssesmentMstDto()
                    .getProvisionalAssesmentDetailDtoList().get(countOfRow.intValue());
            if(detailDto != null) {
                List<PropertyRoomDetailsDto> roomDetDtoList = detailDto.getRoomDetailsDtoList();
                if(CollectionUtils.isNotEmpty(roomDetDtoList)) {
                    ProvisionalAssesmentDetailDto assesmentDetailDto = new ProvisionalAssesmentDetailDto();
                    List<PropertyRoomDetailsDto> roomDetailsDtoList = new ArrayList<>();
                      BeanUtils.copyProperties(detailDto, assesmentDetailDto);
                      
                      assesmentDetailDto.setTempUsePrimKey(null);
                      
                      for(PropertyRoomDetailsDto room : roomDetDtoList) {
                    	  PropertyRoomDetailsDto roomDetail = new PropertyRoomDetailsDto();
                    	  BeanUtils.copyProperties(room, roomDetail);
                    	  
                    	  roomDetailsDtoList.add(roomDetail);
                      }
                      assesmentDetailDto.setRoomDetailsDtoList(roomDetailsDtoList);
                      
                      List<ProvisionalAssesmentFactorDtlDto> assesmentFactorDtlDtoList = new ArrayList<>();
                      assesmentDetailDto.setProvisionalAssesmentFactorDtlDtoList(assesmentFactorDtlDtoList);
                    
                    detailDtoList.add(assesmentDetailDto);
                    this.getModel().getProvisionalAssesmentMstDto().getProvisionalAssesmentDetailDtoList().set(detailDtoList.size()-1, assesmentDetailDto);
                    return true;
                }
            }
        }
		return false;
    }
	
	@RequestMapping(params = "viewRoomDetails", method = RequestMethod.POST)
	public ModelAndView addRoomDetails(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse,
			@RequestParam(value = "countOfRow") int countOfRow) {
		getModel().bind(httpServletRequest);
		ProvisionalAssesmentDetailDto detailDto = getModel().getProvisionalAssesmentMstDto().getProvisionalAssesmentDetailDtoList()
				.get(countOfRow);
		List<PropertyRoomDetailsDto> roomList = new ArrayList<>();
		for(PropertyRoomDetailsDto room : detailDto.getRoomDetailsDtoList()) {
			room.setRoomTypeDesc(CommonMasterUtility.getNonHierarchicalLookUpObject(room.getRoomType(),
                    UserSession.getCurrent().getOrganisation()).getDescLangFirst());
			roomList.add(room);
		}
		detailDto.setRoomTotalArea(detailDto.getRoomDetailsDtoList().stream().mapToDouble(PropertyRoomDetailsDto::getRoomArea).sum());
		detailDto.setRoomDetailsDtoList(roomList);
		getModel().getProvisionalAssesmentMstDto().getProvisionalAssesmentDetailDtoList().set(countOfRow, detailDto);
		return new ModelAndView("viewRoomDetails", MainetConstants.FORM_NAME, getModel());
	}
	
	@RequestMapping(params = "backToAssessmentView", method = RequestMethod.POST)
    public ModelAndView backToAssessmentView(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse) {
        getModel().bind(httpServletRequest);
        return new ModelAndView("SelfAssessmentView", MainetConstants.FORM_NAME, getModel());
    }
	
	@RequestMapping(params = "validateRefPropNo", method = RequestMethod.POST)
    public @ResponseBody boolean validateRefPropNo(@RequestParam(value = "propNo") String propNo) {
    	if(StringUtils.isBlank(propNo)) {
    		return true;
    	}
    	return selfAssessmentService.checkPropertyExistByPropNo(propNo, UserSession.getCurrent().getOrganisation().getOrgid());
    }
}
