package com.abm.mainet.property.ui.controller;

import java.io.Serializable;
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicLong;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.abm.mainet.cfc.challan.dto.ChallanReceiptPrintDTO;
import com.abm.mainet.cfc.challan.dto.ChallanReportDTO;
import com.abm.mainet.cfc.checklist.service.IChecklistVerificationService;
import com.abm.mainet.cfc.objection.dto.ObjectionDetailsDto;
import com.abm.mainet.cfc.objection.service.NoticeMasterService;
import com.abm.mainet.common.constant.MainetConstants;
import com.abm.mainet.common.constant.PrefixConstants;
import com.abm.mainet.common.constant.ServiceEndpoints;
import com.abm.mainet.common.domain.FinancialYear;
import com.abm.mainet.common.domain.LocationMasEntity;
import com.abm.mainet.common.domain.Organisation;
import com.abm.mainet.common.integration.dto.TbBillDet;
import com.abm.mainet.common.integration.dto.TbBillMas;
import com.abm.mainet.common.integration.report.utility.ReportUtility;
import com.abm.mainet.common.master.service.DepartmentService;
import com.abm.mainet.common.service.IDuplicateReceiptService;
import com.abm.mainet.common.service.IFinancialYearService;
import com.abm.mainet.common.service.ILocationMasService;
import com.abm.mainet.common.service.ServiceMasterService;
import com.abm.mainet.common.ui.controller.AbstractFormController;
import com.abm.mainet.common.ui.model.JQGridResponse;
import com.abm.mainet.common.utility.ApplicationContextProvider;
import com.abm.mainet.common.utility.CommonMasterUtility;
import com.abm.mainet.common.utility.Filepaths;
import com.abm.mainet.common.utility.LookUp;
import com.abm.mainet.common.utility.UserSession;
import com.abm.mainet.common.utility.Utility;
import com.abm.mainet.common.workflow.dto.WorkflowRequest;
import com.abm.mainet.common.workflow.service.IWorkflowRequestService;
import com.abm.mainet.property.domain.AssesmentOwnerDtlEntity;
import com.abm.mainet.property.domain.PropertyMastEntity;
import com.abm.mainet.property.dto.ArrayOfDiversionPlotDetails;
import com.abm.mainet.property.dto.ArrayOfKhasraDetails;
import com.abm.mainet.property.dto.ArrayOfPlotDetails;
import com.abm.mainet.property.dto.LandTypeApiDetailRequestDto;
import com.abm.mainet.property.dto.NoticeGenSearchDto;
import com.abm.mainet.property.dto.ProperySearchDto;
import com.abm.mainet.property.dto.ProvisionalAssesmentFactorDtlDto;
import com.abm.mainet.property.dto.ProvisionalAssesmentMstDto;
import com.abm.mainet.property.dto.SpecialNoticeReportDto;
import com.abm.mainet.property.service.AssesmentMastService;
import com.abm.mainet.property.service.DataEntrySuiteService;
import com.abm.mainet.property.service.IProvisionalAssesmentMstService;
import com.abm.mainet.property.service.PrimaryPropertyService;
import com.abm.mainet.property.service.PropertyMainBillService;
import com.abm.mainet.property.service.PropertyNoticeService;
import com.abm.mainet.property.service.ViewPropertyDetailsService;
import com.abm.mainet.property.ui.model.ViewPropertyDetailModel;
import com.abm.mainet.smsemail.dto.SMSAndEmailDTO;
import com.abm.mainet.smsemail.service.ISMSAndEmailService;
import com.abm.mainet.validitymaster.service.IEmployeeWardZoneMappingService;

@Controller
@RequestMapping("/ViewPropertyDetail.html")
public class ViewPropertyDetailController extends AbstractFormController<ViewPropertyDetailModel> {
	
	 private static final Logger LOGGER = Logger.getLogger(ViewPropertyDetailController.class);

    @Autowired
    private ViewPropertyDetailsService viewPropertyDetailsService;

    @Autowired
    private ILocationMasService iLocationMasService;

    @Autowired
    private IFinancialYearService iFinancialYear;

    @Autowired
    private DepartmentService departmentService;

    @Autowired
    private DataEntrySuiteService dataEntrySuiteService;

    @Autowired
    private IDuplicateReceiptService iDuplicateReceiptService;
   
	@Autowired
	private ISMSAndEmailService smsAndEmailService;
	
	@Autowired
	private PropertyMainBillService mainBillService;
	
	@Autowired
	private ViewPropertyDetailsService viewDetailService;
	
	@Autowired
	private PrimaryPropertyService primaryPropertyService;
	
	@Autowired
    private IEmployeeWardZoneMappingService employeeWardZoneMappingService;
	
	@Autowired
    private IChecklistVerificationService iChecklistVerificationService;
	
	@Autowired
	private AssesmentMastService assesmentMastService;
	
	@Autowired
	private ServiceMasterService serviceMasterService;
	
	@Resource
    private IProvisionalAssesmentMstService provisionalAssesmentMstService;
	
	@Autowired
    private IWorkflowRequestService workflowReqService;
	
	@Autowired
    private NoticeMasterService noticeMasterService;
	
    @RequestMapping(method = { RequestMethod.POST, RequestMethod.GET })
    public ModelAndView index(HttpServletRequest request) throws Exception {
        sessionCleanup(request);
        getModel().bind(request);
        ViewPropertyDetailModel model = this.getModel();
        model.setCommonHelpDocs("ViewPropertyDetail.html");
        long orgid = UserSession.getCurrent().getOrganisation().getOrgid();
        model.setOrgId(orgid);
        Long deptId = departmentService.getDepartmentIdByDeptCode(
                MainetConstants.Property.PROP_DEPT_SHORT_CODE, MainetConstants.STATUS.ACTIVE);       
        model.getSearchDto().setOrgId(orgid);
        model.getSearchDto().setDeptId(deptId);
        List<LocationMasEntity> location = iLocationMasService.findWZMappedLocationByOrgIdAndDeptId(orgid,
                deptId);
        List<LookUp> locationList = new ArrayList<>(0);
        if (location != null && !location.isEmpty()) {
            location.forEach(loc -> {
                LookUp lookUp = new LookUp();
                lookUp.setLookUpId(loc.getLocId());
                lookUp.setDescLangFirst(loc.getLocNameEng());
                lookUp.setDescLangSecond(loc.getLocNameReg());
                locationList.add(lookUp);
            });
        }
        model.setLocation(locationList);
        model.setDeptId(deptId);
        model.setIntgrtionWithBP(CommonMasterUtility
                .getDefaultValueByOrg(MainetConstants.Property.IBP, UserSession.getCurrent().getOrganisation()).getLookUpCode());	
        return defaultResult();
    }

    @RequestMapping(params = "searchData", method = RequestMethod.POST)
    public ModelAndView search(HttpServletRequest request) {
    	ViewPropertyDetailModel model = this.getModel();
        model.bind(request);
        model.setSearchDtoResult(new ArrayList<>(0));
        ProperySearchDto dto = model.getSearchDto();
        if ((dto.getProertyNo() == null || dto.getProertyNo().isEmpty()) && (dto.getOldPid() == null || dto.getOldPid().isEmpty())
                && (dto.getMobileno() == null || dto.getMobileno().isEmpty())
                && (dto.getOwnerName() == null || dto.getOwnerName().isEmpty())
                && (dto.getAssWard1() == null || dto.getAssWard1() <= 0)
                && (dto.getAssParshadWard1() == null || dto.getAssParshadWard1() <= 0)
                && (dto.getLocId() == null || dto.getLocId() <= 0)
                && (dto.getPropLvlRoadType() == null || dto.getPropLvlRoadType() <= 0)
                && (dto.getAssdUsagetype1() == null || dto.getAssdUsagetype1() <= 0)
                && (dto.getAssdConstruType() == null || dto.getAssdConstruType() <= 0)
                && (dto.getFlatNo()== null || dto.getFlatNo().isEmpty())
				&& (dto.getParentGrp1() == null || dto.getParentGrp1() <= 0)
				&& (dto.getParentGrp2() == null || dto.getParentGrp2() <= 0)
				&& (StringUtils.isBlank(dto.getHouseNo()))
				&& (StringUtils.isBlank(dto.getNewHouseNo()))) {
            model.addValidationError("In order to search it is mandatory to enter any one of the below search detail");
        }
        
        
        
        if (Utility.isEnvPrefixAvailable(UserSession.getCurrent().getOrganisation(), MainetConstants.ENV_PSCL) || (StringUtils.isNotBlank(dto.getProertyNo()) || StringUtils.isNotBlank(dto.getOldPid()) && Utility.isEnvPrefixAvailable(UserSession.getCurrent().getOrganisation(), MainetConstants.ENV_ASCL) && Utility.isEnvPrefixAvailable(UserSession.getCurrent().getOrganisation(), "BPA"))) {
        	List<ProvisionalAssesmentMstDto> provAssesMastList = new ArrayList<>();
			if (!StringUtils.isEmpty(dto.getProertyNo())) {
				provAssesMastList = provisionalAssesmentMstService.getPropDetailByPropNoOnly(dto.getProertyNo());
			} else {
				if (StringUtils.isNotBlank(dto.getOldPid())) {
					String propNo = provisionalAssesmentMstService.getPropNoByOldPropNo(dto.getOldPid(),UserSession.getCurrent().getOrganisation().getOrgid());
					if (StringUtils.isNotBlank(propNo)) {
						provAssesMastList = provisionalAssesmentMstService.getPropDetailByPropNoOnly(propNo);
					}
				}
			}
			if(CollectionUtils.isNotEmpty(provAssesMastList)) {
				ProvisionalAssesmentMstDto provAssesMstDto = provAssesMastList.get(provAssesMastList.size() - 1);
				if(provAssesMstDto!=null && provAssesMstDto.getApmApplicationId() != null) {
					WorkflowRequest workflowRequest = workflowReqService
							.getWorkflowRequestByAppIdOrRefId(provAssesMstDto.getApmApplicationId(), null, UserSession.getCurrent().getOrganisation().getOrgid());
					if (workflowRequest != null && MainetConstants.WorkFlow.Status.PENDING.equalsIgnoreCase(workflowRequest.getStatus())) {
						if(StringUtils.isNotBlank(dto.getProertyNo())) {
							getModel().addValidationError("Change In Assessment against property number " + dto.getProertyNo()
							+ " is already in progress ");
						}else {
							getModel().addValidationError("Change In Assessment against property number " + dto.getOldPid()
							+ " is already in progress ");
						}
					}
				}
			}
        	
		}
        /*List<String> checkActiveFlagList = assesmentMastService.checkActiveFlag(dto.getProertyNo(), dto.getOrgId());
        String checkActiveFlag = null;
        if (CollectionUtils.isNotEmpty(checkActiveFlagList)) {
            checkActiveFlag = checkActiveFlagList.get(checkActiveFlagList.size() - 1);
        }
        if (StringUtils.isNotBlank(checkActiveFlag) && StringUtils.equals(checkActiveFlag, MainetConstants.STATUS.INACTIVE)) {
            model.addValidationError(
                    getApplicationSession().getMessage("property.inactive.through.amalgamation.or.bifurcation.service"));
        }*/
        return defaultMyResult();
    }

    @RequestMapping(params = "SEARCH_GRID_RESULTS", produces = "application/json", method = RequestMethod.POST)
    public @ResponseBody JQGridResponse<? extends Serializable> getSearchResults(
            HttpServletRequest httpServletRequest) {
        List<ProperySearchDto> result = null;
        List<ProperySearchDto> finalListResult = new ArrayList<ProperySearchDto>();
        int count = 0;
        ViewPropertyDetailModel model = this.getModel();
        ProperySearchDto dto = model.getSearchDto();
        if ((dto.getProertyNo() == null || dto.getProertyNo().isEmpty()) && (dto.getOldPid() == null || dto.getOldPid().isEmpty())
                && (dto.getMobileno() == null || dto.getMobileno().isEmpty())
                && (dto.getOwnerName() == null || dto.getOwnerName().isEmpty())
                && (dto.getAssWard1() == null || dto.getAssWard1() <= 0)
                && (dto.getAssParshadWard1() == null || dto.getAssParshadWard1() <= 0)
                && (dto.getLocId() == null || dto.getLocId() <= 0)
                && (dto.getPropLvlRoadType() == null || dto.getPropLvlRoadType() <= 0)
                && (dto.getAssdUsagetype1() == null || dto.getAssdUsagetype1() <= 0)
                && (dto.getAssdConstruType() == null || dto.getAssdConstruType() <= 0)
                && (dto.getFlatNo()== null || dto.getFlatNo().isEmpty())
				&& (dto.getParentGrp1() == null || dto.getParentGrp1() <= 0)
				&& (dto.getParentGrp2() == null || dto.getParentGrp2() <= 0)
				&& (StringUtils.isBlank(dto.getHouseNo()))
				&& (StringUtils.isBlank(dto.getNewHouseNo()))) {
            model.addValidationError("In order to search it is mandatory to enter any one of the below search detail");
        }
        final String page = httpServletRequest.getParameter(MainetConstants.CommonConstants.PAGE);
        final String rows = httpServletRequest.getParameter(MainetConstants.CommonConstants.ROWS);
        
        
		if (!model.hasValidationErrors()) {
			 
			 
			model.getSearchDto().setStatusFlag(MainetConstants.FlagA);
			result = viewPropertyDetailsService.searchPropertyDetailsForAll(model.getSearchDto(),
					getModel().createPagingDTO(httpServletRequest), getModel().createGridSearchDTO(httpServletRequest),
					null, UserSession.getCurrent().getLoggedLocId());
			if(Utility.isEnvPrefixAvailable(UserSession.getCurrent().getOrganisation(), "EWZ") && CollectionUtils.isNotEmpty(result)) {
				for (ProperySearchDto properySearchDto : result) {
					if (employeeWardZoneMappingService.checkWardZoneMappingFlag(
							UserSession.getCurrent().getEmployee().getEmpId(),
							UserSession.getCurrent().getOrganisation().getOrgid(), properySearchDto.getAssWard1(),
							properySearchDto.getAssWard2(), properySearchDto.getAssWard3(),
							properySearchDto.getAssWard4(), properySearchDto.getAssWard5())) {
						finalListResult.add(properySearchDto);

					}
				}
			}else {
				finalListResult.addAll(result);
			}

			if(CollectionUtils.isNotEmpty(result) && CollectionUtils.isEmpty(finalListResult)) {
				 model.addValidationError("Employee cannot access the selected ward zone data");
			}
			
			if (!model.hasValidationErrors()) {
				if (finalListResult != null && !finalListResult.isEmpty()) {
					count = viewPropertyDetailsService.getTotalSearchCountForAll(model.getSearchDto(),
							getModel().createPagingDTO(httpServletRequest),
							getModel().createGridSearchDTO(httpServletRequest), null);
				}
			}
        }
        return this.getModel().paginate(httpServletRequest, page, rows, count,
        		finalListResult);
    }

    @RequestMapping(params = "edit", method = RequestMethod.POST)
    public ModelAndView editForm(@RequestParam  String rowId,
            final HttpServletRequest httpServletRequest) throws Exception {
        bindModel(httpServletRequest);       
        ViewPropertyDetailModel model = this.getModel();
        getModel().setAssType(MainetConstants.Property.VIW);
        String flatNo=null;       
        if(rowId.contains(MainetConstants.operator.UNDER_SCORE)) {
        	String[] splitStr = rowId.split(MainetConstants.operator.UNDER_SCORE);
        	rowId=splitStr[0];
        	flatNo=splitStr[1];        	
        }
        model.getSearchDto().setProertyNo(rowId);
        model.getSearchDto().setFlatNo(flatNo);
        model.getSearchDto().setOrgId(UserSession.getCurrent().getOrganisation().getOrgid());
        final ProvisionalAssesmentMstDto assMst = viewPropertyDetailsService
                .fetchPropertyByPropNo(model.getSearchDto());
		if (StringUtils.isNotBlank(flatNo)) {
			PropertyMastEntity propertyMast = primaryPropertyService.getPropertyDetailsByPropNoNFlatNo(rowId, flatNo,
					UserSession.getCurrent().getOrganisation().getOrgid());
			model.getSearchDto().setBillMethodChangeFlag(propertyMast.getBillMethodChngFlag());
		}
        LookUp ownerType = CommonMasterUtility.getNonHierarchicalLookUpObject(
                assMst.getAssOwnerType(), UserSession.getCurrent().getOrganisation());
        getModel().setOwnershipPrefix(ownerType.getLookUpCode());      
        model.setOwnershipTypeValue(ownerType.getDescLangFirst());
        List<ProvisionalAssesmentFactorDtlDto> factorMap = factorMappingForView(assMst,
                UserSession.getCurrent().getOrganisation());
        model.setProvAsseFactDtlDto(factorMap);
        List<FinancialYear> financialYearList = iFinancialYear
                .getFinanceYearListFromGivenDate(UserSession.getCurrent().getOrganisation().getOrgid(), assMst.getFaYearId(),
                        new Date());
        if (!financialYearList.isEmpty()) {
            String financialYear = null;
            for (final FinancialYear finYearTemp : financialYearList) {
                financialYear = Utility.getFinancialYearFromDate(finYearTemp.getFaFromDate());
                getModel().getFinancialYearMap().put(finYearTemp.getFaYear(), financialYear);
            }
        }
        if (assMst.getAssLandType() != null) {
            LookUp landTypePrefix = CommonMasterUtility.getNonHierarchicalLookUpObject(
                    assMst.getAssLandType(), UserSession.getCurrent().getOrganisation());
            getModel().setLandTypePrefix(landTypePrefix.getLookUpCode());
        }
        				
        this.getModel().setProvisionalAssesmentMstDto(assMst);		
        if (getModel().getLandTypePrefix() != null) {
            setlandTypeDetails(getModel());
        }
        model.setDropDownValues(UserSession.getCurrent().getOrganisation());
		if (StringUtils.isBlank(assMst.getParentPropNo())) {
			List<TbBillMas> viewData = viewPropertyDetailsService.getViewData(model.getSearchDto());
			viewData.sort(Comparator.comparing(TbBillMas::getBmIdno));
			model.setBillMasList(viewData);
			model.setCollectionDetails(viewPropertyDetailsService.getCollectionDetails(model.getSearchDto()));
		}
        model.setAppDocument(viewPropertyDetailsService.fetchApplicaionDocuments(model.getSearchDto()));
        model.setUpdateDataEntryDocs(
                viewPropertyDetailsService.fetchDESUploadedDocs(UserSession.getCurrent().getOrganisation().getOrgid(), rowId));
        model.setNoticeDetails(ApplicationContextProvider.getApplicationContext().getBean(NoticeMasterService.class)
                .getAllNoticeByRefNo(UserSession.getCurrent().getOrganisation().getOrgid(),
                        model.getSearchDto().getProertyNo(), model.getSearchDto().getFlatNo()));
        getModel().setDocumentList(iChecklistVerificationService.getDocumentUploadedByRefNo(assMst.getAssNo(), UserSession.getCurrent().getOrganisation().getOrgid()));
        AtomicLong increment = new AtomicLong(1);
        if(CollectionUtils.isNotEmpty(getModel().getDocumentList())) {
        	 getModel().getDocumentList().forEach(doc ->{
        		 doc.setClmSrNo(increment.longValue());
             	if(doc.getServiceId() != null) {
             		String serviceCode = serviceMasterService.fetchServiceShortCode(doc.getServiceId(), UserSession.getCurrent().getOrganisation().getOrgid());
             		
             		if(StringUtils.equals(serviceCode, "MTD")) {
             			doc.setClmDesc("Adjustment Entry");
             			doc.setClmDescEngl("Adjustment Entry");
             		}
             	}
             	increment.addAndGet(1);
             });
        }
        return new ModelAndView("ViewBillCollectionDetails", "command", model);
    }

    private void setlandTypeDetails(ViewPropertyDetailModel model) {
        LandTypeApiDetailRequestDto dto = new LandTypeApiDetailRequestDto();
        dto.setLandType(model.getLandTypePrefix());
        dto.setDistrictId(model.getProvisionalAssesmentMstDto().getAssDistrict());
        dto.setTehsilId(model.getProvisionalAssesmentMstDto().getAssTahasil());
        dto.setVillageId(model.getProvisionalAssesmentMstDto().getTppVillageMauja());
        dto.setMohallaId(model.getProvisionalAssesmentMstDto().getMohalla());
        model.setDistrict(dataEntrySuiteService.findDistrictByLandType(dto));
        model.setTehsil(dataEntrySuiteService.getTehsilListByDistrict(dto));
        model.setVillage(dataEntrySuiteService.getVillageListByTehsil(dto));
        if (model.getLandTypePrefix().equals(MainetConstants.Property.LandType.NZL)
                || model.getLandTypePrefix().equals(MainetConstants.Property.LandType.DIV)) {
            getModel().setMohalla(dataEntrySuiteService.getMohallaListByVillageId(dto));
            getModel().setBlockStreet(dataEntrySuiteService.getStreetListByMohallaId(dto));
        }
    }

    @RequestMapping(params = "getViewPropertyDetails", method = { RequestMethod.POST, RequestMethod.GET })
    public ModelAndView getViewProprtyDeatils(@RequestParam("propNo") final String propNo,
            final HttpServletRequest httpServletRequest) throws Exception {

        bindModel(httpServletRequest);
        ViewPropertyDetailModel model = this.getModel();
        model.getSearchDto().setProertyNo(propNo);
        final ProvisionalAssesmentMstDto assMst = viewPropertyDetailsService
                .fetchPropertyByPropNo(model.getSearchDto());
        if (assMst == null) {
            this.getModel().setProvisionalAssesmentMstDto(null);
            model.addValidationError("Please enter valid property number");
            ModelAndView mv = new ModelAndView("ViewPropertyDetailsWithCss", MainetConstants.FORM_NAME, this.getModel());
            mv.addObject(BindingResult.MODEL_KEY_PREFIX + MainetConstants.FORM_NAME, getModel().getBindingResult());
            return mv;
        }

        Organisation org = new Organisation();
        org.setOrgid(assMst.getOrgId());
        model.getSearchDto().setOrgId(assMst.getOrgId());
        Long deptId = departmentService.getDepartmentIdByDeptCode(
                MainetConstants.Property.PROP_DEPT_SHORT_CODE, MainetConstants.STATUS.ACTIVE);
        model.getSearchDto().setDeptId(deptId);
        LookUp ownerType = CommonMasterUtility.getNonHierarchicalLookUpObject(
                assMst.getAssOwnerType(), org);
        getModel().setOwnershipPrefix(ownerType.getLookUpCode());
        model.setOwnershipTypeValue(ownerType.getDescLangFirst());
        List<ProvisionalAssesmentFactorDtlDto> factorMap = factorMappingForView(assMst, org);
        model.setProvAsseFactDtlDto(factorMap);
        List<FinancialYear> financialYearList = iFinancialYear
                .getFinanceYearListFromGivenDate(assMst.getOrgId(), assMst.getFaYearId(),
                        new Date());
        if (!financialYearList.isEmpty()) {
            String financialYear = null;
            for (final FinancialYear finYearTemp : financialYearList) {
                financialYear = Utility.getFinancialYearFromDate(finYearTemp.getFaFromDate());
                getModel().getFinancialYearMap().put(finYearTemp.getFaYear(), financialYear);
            }
        }
        if (assMst.getAssLandType() != null) {
            LookUp landTypePrefix = CommonMasterUtility.getNonHierarchicalLookUpObject(
                    assMst.getAssLandType(), org);
            getModel().setLandTypePrefix(landTypePrefix.getLookUpCode());
        }
        this.getModel().setProvisionalAssesmentMstDto(assMst);
        if (getModel().getLandTypePrefix() != null) {
            setlandTypeDetails(getModel());
        }

        model.setDropDownValues(org);
        model.setBillMasList(viewPropertyDetailsService.getViewData(model.getSearchDto()));
        model.setCollectionDetails(
                viewPropertyDetailsService.getCollectionDetails(model.getSearchDto()));
        model.setAppDocument(viewPropertyDetailsService.fetchApplicaionDocuments(model.getSearchDto()));

        return new ModelAndView("ViewPropertyDetailsWithCss", "command", model);
    }

    @RequestMapping(params = "viewPropDet", method = { RequestMethod.POST })
    public ModelAndView vieBillAndPropDetails(@RequestParam("bmIdNo") final long bmIdNo,
            final HttpServletRequest httpServletRequest) {
        bindModel(httpServletRequest);
        ViewPropertyDetailModel model = this.getModel();
        List<TbBillMas> billMasList = new ArrayList<>(0);
        model.getBillMasList().stream().filter(bill -> bill.getBmIdno() == bmIdNo)
                .forEach(bills -> {
                    billMasList.add(bills);
                });
        billMasList.forEach(billMas -> {
        	billMas.getTbWtBillDet().sort(
    				Comparator.comparing(TbBillDet::getDisplaySeq, Comparator.nullsLast(Comparator.naturalOrder())));
		});
        model.setAuthComBillList(billMasList);
        return new ModelAndView("ViewPropertyTaxDetails", "command", model);
    }

    @RequestMapping(params = "viewBillDet", method = { RequestMethod.POST })
    public ModelAndView vieBillDetails(@RequestParam("bmIdNo") final long bmIdNo,
            final HttpServletRequest httpServletRequest) {
        bindModel(httpServletRequest);
        ViewPropertyDetailModel model = this.getModel();
        List<TbBillMas> billMasList = new ArrayList<>(0);
        model.getBillMasList().stream().filter(bill -> bill.getBmIdno() == bmIdNo)
                .forEach(bills -> {
                    billMasList.add(bills);
                });
        model.setAuthComBillList(billMasList);
        return new ModelAndView("ViewBillDetailWithCss", "command", model);
    }

    private List<ProvisionalAssesmentFactorDtlDto> factorMappingForView(final ProvisionalAssesmentMstDto assMst,
            Organisation org) {
        List<ProvisionalAssesmentFactorDtlDto> factorMap = new ArrayList<>();
        assMst.getProvisionalAssesmentDetailDtoList().forEach(propDet -> {
            propDet.getProvisionalAssesmentFactorDtlDtoList().forEach(fact -> {
            	if(propDet.getAssdUnitNo() != null) {
            		fact.setUnitNoFact(propDet.getAssdUnitNo().toString());
            	}
                fact.setProAssfFactorIdDesc(CommonMasterUtility
                        .getNonHierarchicalLookUpObject(fact.getAssfFactorId(), org)
                        .getDescLangFirst());
                fact.setProAssfFactorValueDesc(CommonMasterUtility
                        .getNonHierarchicalLookUpObject(fact.getAssfFactorValueId(),
                                org)
                        .getDescLangFirst());
                fact.setFactorValueCode(CommonMasterUtility
                        .getNonHierarchicalLookUpObject(fact.getAssfFactorId(), org)
                        .getLookUpCode());
                factorMap.add(fact);
                assMst.getProAssfactor().add(MainetConstants.FlagY);
            });

        });
        return factorMap;
    }

    @RequestMapping(params = "backToPropDet", method = RequestMethod.POST)
    public ModelAndView backToPropDet(HttpServletRequest request) {
    	ViewPropertyDetailModel model = this.getModel();
        model.bind(request);
        return new ModelAndView("ViewBillCollectionDetails", "command", model);
    }

    @RequestMapping(params = "backToViewProprtyDeatils", method = RequestMethod.POST)
    public ModelAndView backToViewProprtyDeatils(HttpServletRequest request) {
    	ViewPropertyDetailModel model = this.getModel();
        model.bind(request);
        return new ModelAndView("ViewPropertyDetailsWithCss", "command", model);
    }

    @RequestMapping(params = "backToSearch", method = RequestMethod.POST)
    public ModelAndView backToSearch(HttpServletRequest request) {
    	ViewPropertyDetailModel model = this.getModel();
        model.bind(request);
        return defaultMyResult();
    }

    @RequestMapping(method = { RequestMethod.POST }, params = "resetViewProp")
    public ModelAndView reset(HttpServletRequest request) throws Exception {
        sessionCleanup(request);
        getModel().bind(request);
        ViewPropertyDetailModel model = this.getModel();
        long orgId = UserSession.getCurrent().getOrganisation().getOrgid();
        model.setCommonHelpDocs("ViewPropertyDetail.html");
        Long deptId = departmentService.getDepartmentIdByDeptCode(
                MainetConstants.Property.PROP_DEPT_SHORT_CODE, MainetConstants.STATUS.ACTIVE);
        model.getSearchDto().setOrgId(orgId);
        model.getSearchDto().setDeptId(deptId);
        List<LocationMasEntity> location = iLocationMasService.findWZMappedLocationByOrgIdAndDeptId(orgId,
                deptId);
        List<LookUp> locationList = new ArrayList<>(0);
        if (location != null && !location.isEmpty()) {
            location.forEach(loc -> {
                LookUp lookUp = new LookUp();
                lookUp.setLookUpId(loc.getLocId());
                lookUp.setDescLangFirst(loc.getLocNameEng());
                lookUp.setDescLangSecond(loc.getLocNameReg());
                locationList.add(lookUp);
            });
        }
        model.setLocation(locationList);
        model.setOrgId(orgId);
        model.setDeptId(deptId);

        return defaultMyResult();
    }

    @RequestMapping(params = "propertyBillDownload", method = { RequestMethod.POST })
    public @ResponseBody String printReport(@RequestParam("bmIdNo") Long bmIdNo, final HttpServletRequest httpServletRequest,
            final HttpServletResponse httpServletResponse) {
        bindModel(httpServletRequest);
        ProperySearchDto propDto = new ProperySearchDto();
        propDto.setBmIdNo(bmIdNo);
        propDto.setEmpId(UserSession.getCurrent().getEmployee().getEmpId());
        propDto.setOrg(UserSession.getCurrent().getOrganisation());
        ProperySearchDto properySearchDto = viewPropertyDetailsService.getAndGenearteJasperForBill(propDto);
        getModel().setFilePath(properySearchDto.getFilePath());
        return properySearchDto.getFilePath();
    }

    @RequestMapping(params = "receiptDownload", method = { RequestMethod.POST })
    public ModelAndView receiptDownload(@RequestParam("reciptId") Long reciptId, @RequestParam("receiptNo") Long receiptNo,
            final HttpServletRequest httpServletRequest,
            final HttpServletResponse httpServletResponse) {
        bindModel(httpServletRequest);
        ProperySearchDto propDto = new ProperySearchDto();
        ProvisionalAssesmentMstDto proMstDto = getModel().getProvisionalAssesmentMstDto();
        propDto.setRecptId(reciptId);
        propDto.setEmpId(UserSession.getCurrent().getEmployee().getEmpId());
        propDto.setOrg(UserSession.getCurrent().getOrganisation());
        propDto.setLangId(UserSession.getCurrent().getLanguageId());
		ChallanReceiptPrintDTO receiptDto = iDuplicateReceiptService.getRevenueReceiptDetails(reciptId, receiptNo,
				proMstDto.getAssNo(),UserSession.getCurrent().getOrganisation().getOrgid(),UserSession.getCurrent().getLanguageId());
		Boolean displaySeq = true;
		
        if (receiptDto != null) {
        	if(Utility.isEnvPrefixAvailable(UserSession.getCurrent().getOrganisation(), "ASCL") && StringUtils.isBlank(receiptDto.getParshadWard1())) {
        		 setParshadZone(proMstDto, receiptDto);
			}
        	for (ChallanReportDTO dto : receiptDto.getPaymentList()) {
    			if (dto.getDisplaySeq() == null) {
    				displaySeq = false;
    				break;
    			}
    		}
    		if (displaySeq)
    			receiptDto.getPaymentList().sort(Comparator.comparing(ChallanReportDTO::getDisplaySeq));
    		
    		receiptDto.setPropNo_connNo_estateNo_L("Property No");
    		receiptDto.setPropNo_connNo_estateN_V(proMstDto.getAssNo());
    		receiptDto.setDeptShortCode(MainetConstants.DEPT_SHORT_NAME.PROPERTY);

            getModel().setReceiptDTO(receiptDto);
            return new ModelAndView("revenueReceiptPrint",
                    MainetConstants.FORM_NAME, getModel());
        }
        return null;

    }

	private void setParshadZone(ProvisionalAssesmentMstDto proMstDto, ChallanReceiptPrintDTO receiptDto) {
		if ((proMstDto.getAssParshadWard1() != null) && (proMstDto.getAssParshadWard1() > 0)) {
			 receiptDto.setParshadWard1(CommonMasterUtility.getHierarchicalLookUp(proMstDto.getAssParshadWard1(), UserSession.getCurrent().getOrganisation())
		                .getLookUpDesc());
		    }
		    if ((proMstDto.getAssParshadWard2() != null) && (proMstDto.getAssParshadWard2() > 0)) {
		    	receiptDto.setParshadWard2(CommonMasterUtility.getHierarchicalLookUp(proMstDto.getAssParshadWard2(), UserSession.getCurrent().getOrganisation())
		                .getLookUpDesc());
		    }
		    if ((proMstDto.getAssParshadWard3() != null) && (proMstDto.getAssParshadWard3() > 0)) {
		    	receiptDto.setParshadWard3(CommonMasterUtility.getHierarchicalLookUp(proMstDto.getAssParshadWard3(), UserSession.getCurrent().getOrganisation())
		                .getLookUpDesc());
		    }
		    if ((proMstDto.getAssParshadWard4() != null) && (proMstDto.getAssParshadWard4() > 0)) {
		    	receiptDto.setParshadWard4(CommonMasterUtility.getHierarchicalLookUp(proMstDto.getAssParshadWard4(), UserSession.getCurrent().getOrganisation())
		                .getLookUpDesc());
		    }
		    if ((proMstDto.getAssParshadWard5() != null) && (proMstDto.getAssParshadWard5() > 0)) {
		    	receiptDto.setParshadWard5(CommonMasterUtility.getHierarchicalLookUp(proMstDto.getAssParshadWard5(), UserSession.getCurrent().getOrganisation())
		                .getLookUpDesc());
		    }
	}

    @RequestMapping(params = "getLandTypeApiDetails", method = RequestMethod.POST)
    public ModelAndView getLandTypeApiDetails(final HttpServletRequest httpServletRequest,
            final HttpServletResponse httpServletResponse) {
    	ViewPropertyDetailModel model = this.getModel();
        LandTypeApiDetailRequestDto dto = new LandTypeApiDetailRequestDto();
        dto.setLandType(model.getLandTypePrefix());
        dto.setDistrictId(model.getProvisionalAssesmentMstDto().getAssDistrict());
        dto.setTehsilId(model.getProvisionalAssesmentMstDto().getAssTahasil());
        dto.setVillageId(model.getProvisionalAssesmentMstDto().getTppVillageMauja());
        dto.setMohallaId(model.getProvisionalAssesmentMstDto().getMohalla());
        dto.setStreetNo(model.getProvisionalAssesmentMstDto().getAssStreetNo());
        dto.setKhasraNo(model.getProvisionalAssesmentMstDto().getTppPlotNoCs());
        dto.setPlotNo(model.getProvisionalAssesmentMstDto().getTppPlotNo());
        if (model.getLandTypePrefix().equals(MainetConstants.Property.LandType.KPK)) {
            ArrayOfKhasraDetails response = dataEntrySuiteService.getKhasraDetails(dto);
            getModel().setArrayOfKhasraDetails(response);
        }

        else if (model.getLandTypePrefix().equals(MainetConstants.Property.LandType.NZL)) {
            ArrayOfPlotDetails response = dataEntrySuiteService.getNajoolDetails(dto);
            model.setArrayOfPlotDetails(response);
        } else if (model.getLandTypePrefix().equals(MainetConstants.Property.LandType.DIV)) {
            ArrayOfDiversionPlotDetails response = dataEntrySuiteService.getDiversionDetails(dto);
            model.setArrayOfDiversionPlotDetails(response);
        }
        return new ModelAndView("showPropertyKhasaraDetails", MainetConstants.FORM_NAME, model);
    }

    // T#101661
    @RequestMapping(params = "propertySpecialNoticeDownload", method = { RequestMethod.POST })
    public ModelAndView printSpecialNoticeReport(@RequestParam(value = "noticeNo", required = false) Long noticeNo,
            final HttpServletRequest httpServletRequest,
            final HttpServletResponse httpServletResponse) throws  Exception {
        bindModel(httpServletRequest);

        List<NoticeGenSearchDto> notGenSearchDtoList = new ArrayList<>();
        NoticeGenSearchDto notGenSearchDto = new NoticeGenSearchDto();
        notGenSearchDto.setGenNotCheck("Y");
        notGenSearchDto.setNoticeEntry("Y");
        notGenSearchDto.setNoticeNo(noticeNo);
        notGenSearchDto.setPropertyNo(getModel().getProvisionalAssesmentMstDto().getAssNo());
        notGenSearchDto.setLocationName(
                iLocationMasService.getLocationNameById(getModel().getProvisionalAssesmentMstDto().getLocId(),
                        UserSession.getCurrent().getOrganisation().getOrgid()));
        notGenSearchDtoList.add(notGenSearchDto);

        final List<SpecialNoticeReportDto> specNotDtoList = ApplicationContextProvider.getApplicationContext()
                .getBean(PropertyNoticeService.class)
                .setDtoForSpecialNotPrinting(notGenSearchDtoList, UserSession.getCurrent().getOrganisation());

        final Map<String, Object> map = new HashMap<>();
        final String subReportSource = Filepaths.getfilepath() + MainetConstants.JASPER_REPORT_NAME
                + MainetConstants.FILE_PATH_SEPARATOR;
        map.put("SUBREPORT_DIR", subReportSource);
        String imgpath = Filepaths.getfilepath() + MainetConstants.IMAGES + MainetConstants.FILE_PATH_SEPARATOR;	
		map.put("imgPath", imgpath);

        String jrxmlName = "PropertyTaxSpecialNotice.jrxml";
        final String jrxmlFileLocation = Filepaths.getfilepath() + "jasperReport" + MainetConstants.FILE_PATH_SEPARATOR
                + jrxmlName;
        String fileName = ReportUtility.generateReportFromCollectionUtility(httpServletRequest, httpServletResponse,
                jrxmlFileLocation, map, specNotDtoList, UserSession.getCurrent().getEmployee().getEmpId());

        if (!fileName.equals(MainetConstants.SERVER_ERROR)) {
            if (fileName.contains(MainetConstants.DOUBLE_BACK_SLACE)) {
                fileName = fileName.replace(MainetConstants.DOUBLE_BACK_SLACE, MainetConstants.QUAD_BACK_SLACE);
            } else if (fileName.contains(MainetConstants.DOUBLE_FORWARD_SLACE)) {
                fileName = fileName.replace(MainetConstants.DOUBLE_FORWARD_SLACE, MainetConstants.QUAD_FORWARD_SLACE);
            }
            getModel().setFilePath(fileName);
        }
        //Defect #147343
        getModel().setRedirectURL("ViewPropertyDetail.html");
        return new ModelAndView(MainetConstants.URL_EVENT.OPEN_NEXT_TAB, MainetConstants.FORM_NAME, getModel());
    }
    
	@RequestMapping(params = "sendNotification", method = { RequestMethod.POST })
	public @ResponseBody Boolean sendNotificationToProperty(@RequestParam("assNo") final String assNo,
			final HttpServletRequest httpServletRequest) {
		LOGGER.info("Start --> " + this.getClass().getSimpleName() + " sendNotificationToProperty() method");
		getModel().bind(httpServletRequest);
		Organisation org = UserSession.getCurrent().getOrganisation();
		Long empId = UserSession.getCurrent().getEmployee().getEmpId();
		ProperySearchDto searchDto = getModel().getSearchDto();
		searchDto.setProertyNo(assNo);
		searchDto.setOrgId(org.getOrgid());
		String orgLink = this.getModel().getAppSession().getMessage("property.orgLink");
		try {
			List<ProperySearchDto> results = viewDetailService.searchPropertyDetailsForAll(searchDto, null, null, null,
					null);
			LOGGER.info(" <----------------------- Details fetched from Property tables -------------------->");
			if (CollectionUtils.isNotEmpty(results)) {
				// To calculate rebate percentage slab wise in case of ASCL
				String percentage = "";
				List<LookUp> lookUps = CommonMasterUtility.getLookUps(PrefixConstants.RDS, org);
				int month = Utility.getMonthByDate(new Date()) + 1;
				int monthForEndDate = 0;

				for (LookUp lookUp : lookUps) {
					String[] str = lookUp.getLookUpCode().split(MainetConstants.HYPHEN);
					if ((Integer.parseInt(str[0]) <= month) && (month <= Integer.parseInt(str[1]))) {
						percentage = lookUp.getOtherField() + MainetConstants.operator.PERCENTILE; // To set rebate
																									// percentage
						monthForEndDate = Integer.parseInt(str[1]);
						break;
					}
				}

				String financialYear = "";
				financialYear = Utility.getFinancialYearFromDate(new Date());
				Date[] dates = Utility.getFromAndToDate(financialYear, monthForEndDate); // Will give month start and
																							// end
																							// date
				LocalDate date = dates[1].toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
				DateTimeFormatter formatter = DateTimeFormatter.ofPattern(MainetConstants.COMMON_DATE_FORMAT);
				String dueDateStr = date.format(formatter); // To set due date
				//

				for (ProperySearchDto result : results) {
					SMSAndEmailDTO dto = new SMSAndEmailDTO();
					dto.setMobnumber(result.getMobileno());
					dto.setEmail(result.geteMail());
					dto.setUserId(empId);
					dto.setPropertyNo(
							StringUtils.isNotBlank(result.getOldPid()) ? result.getOldPid() : MainetConstants.HYPHEN);
					dto.setRegNo(
							StringUtils.isNotBlank(result.getHouseNo()) ? result.getHouseNo() : MainetConstants.HYPHEN);

					LOGGER.info(" <----------------------- Getting bill details -------------------->");
					List<TbBillMas> mainBillList = mainBillService.fetchAllBillByPropNo(result.getProertyNo(),
							org.getOrgid());
					Double amount = 0.0;
					String billNo = "";
					if (CollectionUtils.isNotEmpty(mainBillList)) {
						TbBillMas mainBill = mainBillList.get(mainBillList.size() - 1);
						for (TbBillDet det : mainBill.getTbWtBillDet()) {
							amount += det.getBdCurBalTaxamt() + det.getBdPrvBalArramt();
						}
						billNo = mainBill.getBmNo();
					}
					dto.setBillNo(billNo);
					dto.setAmount(amount);
					dto.setDueDt(dueDateStr);
					dto.setAppAmount(percentage);
					dto.setOrgName(orgLink);
					dto.setServiceUrl(MainetConstants.Property.PROP_NOT_URL);
					LOGGER.info(" Sending SMS to property no : " + result.getProertyNo());
					smsAndEmailService.sendEmailSMS(MainetConstants.Property.PROP_DEPT_SHORT_CODE,
							"PropertyNotification.html", PrefixConstants.SMS_EMAIL_ALERT_TYPE.SUBMITTED, dto, org,
							UserSession.getCurrent().getLanguageId());
				}
			}
		} catch (Exception e) {
			return false;
		}
		LOGGER.info("End --> " + this.getClass().getSimpleName() + " sendNotificationToProperty() method");
		return true;
	}		
	
	
	@RequestMapping(params = "downloadBillBirtReport", method = RequestMethod.POST)
	public @ResponseBody String downloadDetailBill(@RequestParam("bmIdNo") Long bmIdno, @RequestParam("finYearId") Long finYearId, @RequestParam("propNo") String propNo) {
		Long orgId = UserSession.getCurrent().getOrganisation().getOrgid();
		return ServiceEndpoints.PROPERTY_BIRT_REPORT_URL + "=RP_PropertyBillViewReport_ASCL.rptdesign&Orgid=" + orgId
				+ "&bmIdNo=" + bmIdno + "&finYear=" + finYearId + "&propertyNo=" + propNo;
	}
	
	
	@RequestMapping(params = "downloadSpecialNotice", method = RequestMethod.POST)
	public @ResponseBody String downloadSpecialNoticeBirt(HttpServletRequest request) {
		this.getModel().bind(request);
		Long  notId = noticeMasterService.getNoticeIdByApplicationId(this.getModel().getProvisionalAssesmentMstDto().getApmApplicationId());
		return ServiceEndpoints.PROPERTY_BIRT_REPORT_URL + "=newProperty_registeration.rptdesign&P_NIT_NO=" + notId;
	}
	
	@ResponseBody
	@RequestMapping(params = "getPropDetails", method = { RequestMethod.POST })
	public ObjectionDetailsDto getPropertyDetails(@RequestParam("refNo") String refNo,
			@RequestParam("serviceId") Long serviceId, @RequestParam("objectionDeptId") Long objectionDeptId,
			HttpServletRequest request) {
		ObjectionDetailsDto objectionDet = new ObjectionDetailsDto();
		List<AssesmentOwnerDtlEntity> ownerDetailList = assesmentMastService.getPrimaryOwnerDetailByPropertyNo(UserSession.getCurrent().getOrganisation().getOrgid(), refNo);
		if(CollectionUtils.isNotEmpty(ownerDetailList)) {
			AssesmentOwnerDtlEntity ownerDetail = ownerDetailList.get(0);
			objectionDet.setGender(ownerDetail.getGenderId());
			objectionDet.setfName(ownerDetail.getAssoOwnerName());
			objectionDet.setMobileNo(ownerDetail.getAssoMobileno());
			objectionDet.seteMail(ownerDetail.geteMail());
			objectionDet.setUid(ownerDetail.getAssoAddharno());
			
		}
		return objectionDet;
	}
	
	 @RequestMapping(params = "downloadSpecialNoticeReceipt", method = { RequestMethod.POST })
	    public @ResponseBody String  downloadSpecialNoticeReceiptReport(@RequestParam(value = "noticeNo", required = false) Long noticeNo,
	            final HttpServletRequest httpServletRequest,
	            final HttpServletResponse httpServletResponse) throws  Exception {
	        bindModel(httpServletRequest);

	        List<NoticeGenSearchDto> notGenSearchDtoList = new ArrayList<>();
	        NoticeGenSearchDto notGenSearchDto = new NoticeGenSearchDto();
	        notGenSearchDto.setGenNotCheck("Y");
	        notGenSearchDto.setNoticeEntry("Y");
	        notGenSearchDto.setNoticeNo(noticeNo);
	        notGenSearchDto.setPropertyNo(getModel().getProvisionalAssesmentMstDto().getAssNo());
	        notGenSearchDto.setLocationName(
	                iLocationMasService.getLocationNameById(getModel().getProvisionalAssesmentMstDto().getLocId(),
	                        UserSession.getCurrent().getOrganisation().getOrgid()));
	        notGenSearchDtoList.add(notGenSearchDto);

	        final List<SpecialNoticeReportDto> specNotDtoList = ApplicationContextProvider.getApplicationContext()
	                .getBean(PropertyNoticeService.class)
	                .setDtoForSpecialNotPrinting(notGenSearchDtoList, UserSession.getCurrent().getOrganisation());

	        final Map<String, Object> map = new HashMap<>();
	        final String subReportSource = Filepaths.getfilepath() + MainetConstants.JASPER_REPORT_NAME
	                + MainetConstants.FILE_PATH_SEPARATOR;
	        map.put("SUBREPORT_DIR", subReportSource);
	        String imgpath = Filepaths.getfilepath() + MainetConstants.IMAGES + MainetConstants.FILE_PATH_SEPARATOR;	
			map.put("imgPath", imgpath);

	        String jrxmlName = "PropertyTaxSpecialNotice.jrxml";
	        final String jrxmlFileLocation = Filepaths.getfilepath() + "jasperReport" + MainetConstants.FILE_PATH_SEPARATOR
	                + jrxmlName;
	        String fileName = ReportUtility.generateReportFromCollectionUtility(httpServletRequest, httpServletResponse,
	                jrxmlFileLocation, map, specNotDtoList, UserSession.getCurrent().getEmployee().getEmpId());

	        if (!fileName.equals(MainetConstants.SERVER_ERROR)) {
	            if (fileName.contains(MainetConstants.DOUBLE_BACK_SLACE)) {
	                fileName = fileName.replace(MainetConstants.DOUBLE_BACK_SLACE, MainetConstants.QUAD_BACK_SLACE);
	            } else if (fileName.contains(MainetConstants.DOUBLE_FORWARD_SLACE)) {
	                fileName = fileName.replace(MainetConstants.DOUBLE_FORWARD_SLACE, MainetConstants.QUAD_FORWARD_SLACE);
	            }
	            getModel().setFilePath(fileName);
	        }
	        //Defect #147343
//	        getModel().setRedirectURL("ViewPropertyDetail.html");
//	        return new ModelAndView(MainetConstants.URL_EVENT.OPEN_NEXT_TAB, MainetConstants.FORM_NAME, getModel());
	        return getModel().getFilePath();
	    }
}
