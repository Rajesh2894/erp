
package com.abm.mainet.common.master.ui.controller;

import java.io.File;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.abm.mainet.common.constant.MainetConstants;
import com.abm.mainet.common.constant.PrefixConstants;
import com.abm.mainet.common.constant.MainetConstants.AccountConstants;
import com.abm.mainet.common.constant.MainetConstants.BUG_HEAD_OPENING_BALANCE_MASTER;
import com.abm.mainet.common.constant.PrefixConstants.AccountPrefix;
import com.abm.mainet.common.domain.LocationMasEntity;
import com.abm.mainet.common.domain.Organisation;
import com.abm.mainet.common.dto.ReadExcelData;
import com.abm.mainet.common.exception.FrameworkException;
import com.abm.mainet.common.integration.acccount.dto.VendorBillApprovalDTO;
import com.abm.mainet.common.integration.acccount.service.AccountFieldMasterService;
import com.abm.mainet.common.integration.dms.client.FileNetApplicationClient;
import com.abm.mainet.common.integration.dms.domain.AttachDocs;
import com.abm.mainet.common.integration.dms.fileUpload.FileUploadUtility;
import com.abm.mainet.common.integration.dms.service.IFileUploadService;
import com.abm.mainet.common.master.dto.LocElectrolWZMappingDto;
import com.abm.mainet.common.master.dto.LocOperationWZMappingDto;
import com.abm.mainet.common.master.dto.LocRevenueWZMappingDto;
import com.abm.mainet.common.master.dto.LocationMasterUploadDto;
import com.abm.mainet.common.master.dto.TbDepartment;
import com.abm.mainet.common.master.dto.TbFinancialyear;
import com.abm.mainet.common.master.dto.TbLocationMas;
import com.abm.mainet.common.master.dto.TbOrganisation;
import com.abm.mainet.common.master.mapper.TbOrganisationServiceMapper;
import com.abm.mainet.common.master.service.DeptLocationService;
import com.abm.mainet.common.master.service.TbDepartmentService;
import com.abm.mainet.common.master.service.TbFinancialyearService;
import com.abm.mainet.common.master.service.TbOrganisationService;
import com.abm.mainet.common.master.ui.model.LocationMasModel;
import com.abm.mainet.common.master.ui.validator.LocationMasterValidator;
import com.abm.mainet.common.service.IEmployeeService;
import com.abm.mainet.common.service.ILocationMasService;
import com.abm.mainet.common.ui.controller.AbstractFormController;
import com.abm.mainet.common.ui.model.JQGridResponse;
import com.abm.mainet.common.upload.excel.WriteExcelData;
import com.abm.mainet.common.utility.ApplicationContextProvider;
import com.abm.mainet.common.utility.ApplicationSession;
import com.abm.mainet.common.utility.CommonMasterUtility;
import com.abm.mainet.common.utility.HttpHelper;
import com.abm.mainet.common.utility.LookUp;
import com.abm.mainet.common.utility.Message;
import com.abm.mainet.common.utility.MessageHelper;
import com.abm.mainet.common.utility.MessageType;
import com.abm.mainet.common.utility.UserSession;
import com.abm.mainet.common.utility.Utility;

@Controller
@RequestMapping("/LocationMas.html")
public class LocationMasController extends AbstractFormController<LocationMasModel> {

	private static final String JSP_EXCELUPLOAD = "locationMas/ExcelUpload";

	@Resource
	private ILocationMasService iLocationMasService;

	@Resource
	private TbDepartmentService tbDepartmentService;

	@Autowired
	private AccountFieldMasterService accountFieldMasterService;

	@Autowired
	private TbOrganisationService organisationService;

	@Autowired
	private TbOrganisationServiceMapper tbOrganisationServiceMapper;

	@Autowired
	private IEmployeeService iEmployeeService;

	@Resource
	private DeptLocationService tbDeptLocationService;

	@Resource
	private IFileUploadService fileUpload;

	@Resource
	private MessageHelper messageHelper;

	ApplicationSession appSession = ApplicationSession.getInstance();
	private static Logger logger = Logger.getLogger(LocationMasController.class);

	@RequestMapping(params = "form", method = RequestMethod.POST)
	public ModelAndView formForCreate(@RequestParam(value = "locId", required = false) final Long locId,
			@RequestParam(value = "type", required = false) final String modeType) {
		fileUpload.sessionCleanUpForFileUpload();
		final LocationMasModel locationMasModel = getModel();
		Organisation sessionOrg = UserSession.getCurrent().getOrganisation();

		final List<TbDepartment> deptList = tbDepartmentService.findDepartmentWithPrefix(sessionOrg.getOrgid());
		populateModel(locId, locationMasModel, modeType);
		locationMasModel.setDeptList(deptList);
		final Map<Long, String> map = accountFieldMasterService
				.getFieldMasterLastLevelsForLocation(sessionOrg.getOrgid());
		locationMasModel.setMapRenewalList(map);

		final List<TbLocationMas> locationDatalist = iLocationMasService.fillAllLocationMasterDetails(sessionOrg);
		final List<String> locNameList = new ArrayList<>();
		final List<String> locAreaList = new ArrayList<>();
		for (final TbLocationMas location : locationDatalist) {
			locNameList.add(location.getLocNameEng());
			locAreaList.add(location.getLocArea());
		}
		locationMasModel.setLocationNameList(locNameList);
		locationMasModel.setLocationAreaList(locAreaList);
		final List<TbFinancialyear> finYearList = ApplicationContextProvider.getApplicationContext()
                .getBean(TbFinancialyearService.class)
                .findAllFinancialYearByOrgId(UserSession.getCurrent().getOrganisation());
        this.getModel().getFaYears().clear();
        if (finYearList != null && !finYearList.isEmpty()) {
            finYearList.forEach(finYearTemp -> {
                try {
                    finYearTemp.setFaYearFromTo(Utility.getFinancialYearFromDate(finYearTemp.getFaFromDate()));
                    this.getModel().getFaYears().add(finYearTemp);
                } catch (Exception ex) {
                    //throw new FrameworkException(EXCEPTION_IN_FINANCIAL_YEAR_DETAIL + ex);
                }
            });
            Comparator<TbFinancialyear> comparing = Comparator.comparing(TbFinancialyear::getFaYearFromTo,
                    Comparator.reverseOrder());
            Collections.sort(this.getModel().getFaYears(), comparing);
        }
        LookUp defaultVal = CommonMasterUtility.getDefaultValue(BUG_HEAD_OPENING_BALANCE_MASTER.SLI_PREFIX_VALUE);
        if (defaultVal != null) {
        	this.getModel().setCpdMode(CommonMasterUtility.getDefaultValue(BUG_HEAD_OPENING_BALANCE_MASTER.SLI_PREFIX_VALUE)
                    .getLookUpCode());
        }
		return new ModelAndView("locationMas/form", MainetConstants.FORM_NAME, locationMasModel);
	}

	/**
	 * Populates the Spring MVC model with the given entity and eventually other
	 * useful data
	 */
	private void populateModel(final Long locId, final LocationMasModel locationMasModel, final String modeType) {

		if (locId == null) {
			locationMasModel.setModeType(MainetConstants.MODE_CREATE);
			locationMasModel.getTbLocationMas().setHiddeValue(MainetConstants.MODE_CREATE);
		} else {
			final TbLocationMas tbLocationMas = iLocationMasService.findById(locId);
			AttachDocs docs = iLocationMasService
					.fetchLocationImage(UserSession.getCurrent().getOrganisation().getOrgid(), locId);
			if (docs != null) {
				final String outputPath = MainetConstants.DirectoryTree.DEFAULT_CACHE_FOLDER
						+ MainetConstants.FILE_PATH_SEPARATOR + MainetConstants.CommonMasterUi.SHOW_DOCS;
				final String filePath = Utility.downloadedFileUrl(
						docs.getAttPath() + MainetConstants.FILE_PATH_SEPARATOR + docs.getAttFname(), outputPath,
						FileNetApplicationClient.getInstance());
				locationMasModel.setFilePath(filePath);
			}

			// List<LocAdminWZMappingDto> locAdminWZMappingDto =
			// tbLocationMas.getLocAdminWZMappingDto();
			final List<LocRevenueWZMappingDto> locRevenueWZMappingDto = tbLocationMas.getLocRevenueWZMappingDto();
			final List<LocElectrolWZMappingDto> locElectrolWZMappingDto = tbLocationMas.getLocElectrolWZMappingDto();
			final List<LocOperationWZMappingDto> locOperationWZMappingDtos = tbLocationMas
					.getLocOperationWZMappingDto();
			/*
			 * if(!locAdminWZMappingDto.isEmpty()){
			 * locAdminWZMappingDto.get(0).setAdministrativeChkBox(true); }
			 */
			if (!locRevenueWZMappingDto.isEmpty()) {
				locRevenueWZMappingDto.get(0).setRevenueChkBox(true);
			}
			if (!locElectrolWZMappingDto.isEmpty()) {
				locElectrolWZMappingDto.get(0).setElectoralChkBox(true);
			}
			if (!locOperationWZMappingDtos.isEmpty()) {
				locOperationWZMappingDtos.removeAll(locOperationWZMappingDtos);
				locOperationWZMappingDtos.add(new LocOperationWZMappingDto());
				locOperationWZMappingDtos.get(0).setOpertionalChkBox(true);
			}
			if (!tbLocationMas.getYearDtos().isEmpty()) {
				Map<Long, String> budgetMap = new HashMap<>();
		     	Organisation org = UserSession.getCurrent().getOrganisation();
		     	VendorBillApprovalDTO budgetHeadDTO = new VendorBillApprovalDTO();
		        budgetHeadDTO.setOrgId(org.getOrgid());
		        budgetHeadDTO.setFieldId(locRevenueWZMappingDto.get(0).getCodIdRevLevel1());
		        budgetMap=iLocationMasService.getBudgetExpenditure(budgetHeadDTO);
		        locationMasModel.setBudgetMap(budgetMap);
			}
			locationMasModel.setTbLocationMas(tbLocationMas);
			if (MainetConstants.MODE_VIEW.equals(modeType)) {
				locationMasModel.setModeType(MainetConstants.MODE_VIEW);
				locationMasModel.getTbLocationMas().setHiddeValue(MainetConstants.MODE_VIEW);
			} else {
				locationMasModel.setModeType(MainetConstants.MODE_EDIT);
				locationMasModel.getTbLocationMas().setHiddeValue(MainetConstants.MODE_EDIT);
			}

		}
	}

	@RequestMapping(method = RequestMethod.POST)
	public ModelAndView index(final HttpServletRequest httpServletRequest) {
		sessionCleanup(httpServletRequest);
		fileUpload.sessionCleanUpForFileUpload();
		this.getModel().setCommonHelpDocs("LocationMas.html");
		return new ModelAndView("locationMas", MainetConstants.FORM_NAME, getModel());
	}

	@RequestMapping(params = "locationMasGridData", produces = "application/json", method = RequestMethod.POST)
	public @ResponseBody JQGridResponse<TbLocationMas> getLocationMasGridData(
			final HttpServletRequest httpServletRequest, @RequestParam final String page,
			@RequestParam final String rows) {
		final List<TbLocationMas> locationDatalist = iLocationMasService
				.fillAllLocationMasterDetails(UserSession.getCurrent().getOrganisation());
		return getModel().paginate(httpServletRequest, page, rows, locationDatalist);
	}

	@ResponseBody
	@RequestMapping(params = "deleteLocation", method = RequestMethod.POST)
	public boolean deActiveLocationId(@RequestParam("locId") final Long locId) {
		iLocationMasService.delete(MainetConstants.N_FLAG, UserSession.getCurrent().getEmployee().getEmpId(), locId);
		return true;
	}

	@RequestMapping(params = "validateLocationNameAndArea", method = RequestMethod.POST)
	public @ResponseBody int validateLocation(@RequestParam("locationName") final String locationName,
			@RequestParam("locaArea") final String locaArea) {
		final Long orgId = UserSession.getCurrent().getOrganisation().getOrgid();
		int count = iLocationMasService.validateLocationNameAndArea(locationName, locaArea, orgId);
		return count;
	}

	/**
	 * Operational Ward Zone is different from department to
	 * department.Administrative,Electoral and Revenue Ward Zone are common across
	 * all department.
	 */
	@RequestMapping(params = "areaMapping", method = RequestMethod.POST)
	public ModelAndView getOperationalWardZonePrefixName(@RequestParam("deptId") final Long deptId) {
		final String prefixName = tbDepartmentService.findDepartmentPrefixName(deptId,
				UserSession.getCurrent().getOrganisation().getOrgid());
		getModel().setPrefixName(prefixName);
		return new ModelAndView("areaMappingWardZone", MainetConstants.CommonConstants.COMMAND, getModel());
	}

	/**
	 * Operational Ward Zone is different from department to
	 * department.Administrative,Electoral and Revenue Ward Zone are common across
	 * all department.
	 */
	@RequestMapping(params = "areaMappingEdit", method = RequestMethod.POST)
	public ModelAndView getOperationalWardZonePrefixNameEdit(@RequestParam("locId") final Long locId,
			@RequestParam("deptId") final Long deptId) {
		final String prefixName = tbDepartmentService.findDepartmentPrefixName(deptId,
				UserSession.getCurrent().getOrganisation().getOrgid());
		getModel().setPrefixName(prefixName);
		getModel().initializeModelEdit(prefixName);
		final LocOperationWZMappingDto locOperationWZMappingDto = iLocationMasService.findOperLocationAndDeptId(locId,
				deptId);
		getModel().getTbLocationMas().getLocOperationWZMappingDto()
				.removeAll(getModel().getTbLocationMas().getLocOperationWZMappingDto());
		if (locOperationWZMappingDto != null) {
			getModel().getTbLocationMas().getLocOperationWZMappingDto().add(locOperationWZMappingDto);
		} else {
			getModel().getTbLocationMas().getLocOperationWZMappingDto().add(new LocOperationWZMappingDto());
		}
		return new ModelAndView("areaMappingWardZone", MainetConstants.CommonConstants.COMMAND, getModel());
	}

	@ExceptionHandler(Exception.class)
	public ModelAndView handleError(final HttpServletRequest request, final Exception exception) {
		logger.error("Exception found : ", exception);
		final boolean asyncRequest = HttpHelper.isAjaxRequest(request);
		if (asyncRequest) {
			return new ModelAndView("defaultExceptionFormView");
		} else {
			return new ModelAndView("defaultExceptionView");
		}
	}

	/**
	 * Returns the locationlist for organisation
	 */
	@RequestMapping(params = "locationList", method = RequestMethod.POST)
	public @ResponseBody List<LocationMasEntity> getLocationList(@RequestParam("orgId") final Long orgId) {
		final List<LocationMasEntity> locationList = iLocationMasService.getlocationByOrgId(orgId);
		// Setting the session organisation on change of location from Login page
		final TbOrganisation orgBean = organisationService.findById(orgId);
		final Organisation orgEntity = new Organisation();
		tbOrganisationServiceMapper.mapTbOrganisationToTbOrganisationEntity(orgBean, orgEntity);
		UserSession.getCurrent().setOrganisation(orgEntity);
		return locationList;
	}

	@RequestMapping(params = "checkIfDeptAndEmpMapped", method = RequestMethod.POST)
	public @ResponseBody String checkIfDeptAndEmpMapped(@RequestParam("locId") final Long locId) {
		int count = 0;
		String errorMsg = null;
		Long orgId = UserSession.getCurrent().getOrganisation().getOrgid();
		count = tbDeptLocationService.getDeptLocationCount(orgId, locId);
		if (count == 0) {
			count = checkIfEmpExists(locId);
			if (count > 0)
				errorMsg = appSession.getMessage("loc.error.empmapped");

		} else {
			errorMsg = appSession.getMessage("loc.error.deptmapped");
		}
		return errorMsg;
	}

	public int checkIfEmpExists(@RequestParam("locId") final Long locId) {
		int count = 0;
		Long orgId = UserSession.getCurrent().getOrganisation().getOrgid();
		count = iEmployeeService.getEmployeeByLocation(orgId, locId);
		return count;
	}

	@RequestMapping(params = "getCoordinates", method = RequestMethod.POST)
	public @ResponseBody double[] fetchCoordinate(HttpServletRequest httpRequest) {
		double[] coordinates = new double[2];
		if ((FileUploadUtility.getCurrent().getFileMap().entrySet() != null)
				&& !FileUploadUtility.getCurrent().getFileMap().entrySet().isEmpty()) {
			for (final Map.Entry<Long, Set<File>> entry : FileUploadUtility.getCurrent().getFileMap().entrySet()) {
				final Iterator<File> setFilesItr = entry.getValue().iterator();
				while (setFilesItr.hasNext()) {
					final File file = setFilesItr.next();
					javaxt.io.Image image = new javaxt.io.Image(file.getPath());
					double[] coord = image.getGPSCoordinate();
					if (coord != null) {
						coordinates[0] = coord[0];
						coordinates[1] = coord[1];
					}
				}
			}
		}
		return coordinates;
	}

	/*
	 * Excel import export related code starts for #73020
	 */

	@RequestMapping(params = "exportTemplateData", method = RequestMethod.POST)
	public ModelAndView exportImportExcelTemplate(final HttpServletRequest request) {
		logger.info("LocationMas-'exportImportExcelTemplate' : 'exportImportExcelTemplate'");

		fileUpload.sessionCleanUpForFileUpload();
		final LocationMasModel locationMasModel = getModel();
		locationMasModel.bind(request);
		Organisation sessionOrg = UserSession.getCurrent().getOrganisation();
		if (locationMasModel.getDeptList() == null) {
			List<TbDepartment> deptList = tbDepartmentService.findDepartmentWithPrefix(sessionOrg.getOrgid());
			locationMasModel.setDeptList(deptList);
		}

		if (locationMasModel.getMapRenewalList() == null) {
			Map<Long, String> map = accountFieldMasterService
					.getFieldMasterLastLevelsForLocation(sessionOrg.getOrgid());
			locationMasModel.setMapRenewalList(map);
		}

		if (locationMasModel.getLocationMasList() == null) {
			List<TbLocationMas> locationDatalist = iLocationMasService.fillAllLocationMasterDetails(sessionOrg);
			List<String> locNameList = new ArrayList<>();
			List<String> locAreaList = new ArrayList<>();
			for (TbLocationMas location : locationDatalist) {
				locNameList.add(location.getLocNameEng());
				locAreaList.add(location.getLocArea());
			}
			locationMasModel.setLocationMasList(locationDatalist);
			locationMasModel.setLocationNameList(locNameList);
			locationMasModel.setLocationAreaList(locAreaList);
		}
		populateModel(null, locationMasModel, null);
		return new ModelAndView(JSP_EXCELUPLOAD, MainetConstants.FORM_NAME, locationMasModel);
	}

	@RequestMapping(params = "ExcelTemplateData")
	public void exportLocationMasterExcelData(final HttpServletResponse response,
			final HttpServletRequest request) {

		Organisation org = UserSession.getCurrent().getOrganisation();
		try {
			WriteExcelData<LocationMasterUploadDto> data = new WriteExcelData<>(
					MainetConstants.LOCATIONMASTERUPLOADDTO + MainetConstants.XLSX_EXT, request, response);
			final List<LocationMasterUploadDto> locationMasterUploadDtos = iLocationMasService.exportAllLocationsForOrg(org);
			data.getExpotedExcelSheet(locationMasterUploadDtos, LocationMasterUploadDto.class);
		} catch (Exception ex) {
			throw new FrameworkException(ex.getMessage());
		}
	}

	@RequestMapping(params = "loadExcelData", method = RequestMethod.POST)
	public ModelAndView validateAndLoadExcelData(final Model model, final HttpServletRequest httpServletRequest,
			final RedirectAttributes redirectAttributes) throws Exception {

		logger.info("Action 'loadExcelData' of Location Master called");

		final LocationMasModel locationMasModel = getModel();
		locationMasModel.bind(httpServletRequest);
		BindingResult bindingResult = locationMasModel.getBindingResult();
		final boolean isDafaultOrgExist = organisationService.defaultexist(MainetConstants.MASTER.Y);
		Organisation defaultOrg = null;
		if (isDafaultOrgExist) {
			defaultOrg = ApplicationSession.getInstance().getSuperUserOrganization();
		} else {
			defaultOrg = UserSession.getCurrent().getOrganisation();
		}
		Organisation org = UserSession.getCurrent().getOrganisation();
		TbLocationMas tbLocationMas = locationMasModel.getTbLocationMas();
		final String filePath = getUploadedFilePath();
		ReadExcelData<LocationMasterUploadDto> data = new ReadExcelData<>(filePath, LocationMasterUploadDto.class);
		data.parseExcelList();
		List<String> errlist = data.getErrorList();
		if (!errlist.isEmpty()) {
			for (String error : errlist) {
				if (error != null && !error.isEmpty()) {
					if (error.contains("Template Can Not Be Empty")) {
						bindingResult.addError(new org.springframework.validation.FieldError(LocationMasterValidator.LOCATIONMASTER,
								MainetConstants.BLANK, null, false, new String[] { MainetConstants.ERRORS }, null,
								ApplicationSession.getInstance().getMessage("locationMas.empty.excel")));
					}
					if (error.contains("Formate") && error.contains("GIS Number")) {
						String row = StringUtils.substring(error, StringUtils.ordinalIndexOf(error, "[", 2) + 1, StringUtils.ordinalIndexOf(error, "[", 2) + 2);
						bindingResult.addError(new org.springframework.validation.FieldError(LocationMasterValidator.LOCATIONMASTER,
								MainetConstants.BLANK, null, false, new String[] { MainetConstants.ERRORS }, null,
								row + ApplicationSession.getInstance().getMessage("locationMas.excel.gisNo")));
					}
					if (error.contains("Formate") && error.contains("Pin Code")) {
						String row = StringUtils.substring(error, StringUtils.ordinalIndexOf(error, "[", 2) + 1, StringUtils.ordinalIndexOf(error, "[", 2) + 2);
						bindingResult.addError(new org.springframework.validation.FieldError(LocationMasterValidator.LOCATIONMASTER,
								MainetConstants.BLANK, null, false, new String[] { MainetConstants.ERRORS }, null,
								row + ApplicationSession.getInstance().getMessage("locationMas.excel.pincode")));
					}
						
				}
			}
			
			
		} else {
			final List<LocationMasterUploadDto> locationMasterUploadDtos = data.getParseData();
			List<LookUp> locCategory = CommonMasterUtility.getLookUps(MainetConstants.fieldName.LCT, defaultOrg);
			Map<Long, String> codIdRevLevel1 = locationMasModel.getMapRenewalList();
			List<TbDepartment> deptList = locationMasModel.getDeptList();
			
			Map<Integer, List<LookUp>> elecLookupsMap = new HashMap<>();
			for(int i=1; i<=5; i++) {
				try {
					List<LookUp> lookUps = CommonMasterUtility.getLevelData(PrefixConstants.prefixName.ElectrolWZ, i, org);
					elecLookupsMap.put(i, lookUps);
				} catch (Exception e) {
					
				}
			}
			
			if (!bindingResult.hasErrors()) {
				LocationMasterValidator validator = new LocationMasterValidator();
				List<LocationMasterUploadDto> locationMasterUploadDtoList = validator.validateExcel(
						locationMasterUploadDtos, bindingResult, locCategory, elecLookupsMap,
						codIdRevLevel1, deptList, org);
				if (!bindingResult.hasErrors()) {
					validateExcelData(locationMasterUploadDtos);
					if (!bindingResult.hasErrors()) {

						iLocationMasService.saveLocationMasterExcelData(locationMasterUploadDtoList, tbLocationMas,
								org);
						model.addAttribute(MainetConstants.AccountBudgetAdditionalSupplemental.KEY_TEST,
								ApplicationSession.getInstance().getMessage("locationMas.success.excel"));

					}
				}
			}
		}

		model.addAttribute(BindingResult.MODEL_KEY_PREFIX + MainetConstants.FORM_NAME, bindingResult);
		messageHelper.addMessage(redirectAttributes, new Message(MessageType.SUCCESS, MainetConstants.SAVE));
		return new ModelAndView(JSP_EXCELUPLOAD, MainetConstants.FORM_NAME, locationMasModel);
	}

	private void validateExcelData(final List<LocationMasterUploadDto> locationMasterUploadDtos) {

		final ApplicationSession session = ApplicationSession.getInstance();
		BindingResult bindingResult = getModel().getBindingResult();

		List<TbLocationMas> locMasterDtos = getModel().getLocationMasList();
		int rowNo = 0;
		for (LocationMasterUploadDto locationMasterUploadDto : locationMasterUploadDtos) {
			rowNo++;

			for (TbLocationMas locMasterDto : locMasterDtos) {
				if (StringUtils.isNotBlank(locMasterDto.getLocNameEng())
						&& StringUtils.isNotBlank(locationMasterUploadDto.getLocNameEng())) {
					if (locMasterDto.getLocNameEng().equalsIgnoreCase(locationMasterUploadDto.getLocNameEng())) {
						if (StringUtils.isNotBlank(locMasterDto.getLocArea())
								&& StringUtils.isNotBlank(locationMasterUploadDto.getLocArea())) {
							if (locMasterDto.getLocArea().equalsIgnoreCase(locationMasterUploadDto.getLocArea())) {
								bindingResult.addError(new ObjectError("locationName",
										rowNo + session.getMessage("locationMas.excel.dupLocName")));
							}
						}
					}
				}
			}
		}

	}

	private String getUploadedFilePath() {
		String filePath = null;
		for (final Map.Entry<Long, Set<File>> entry : FileUploadUtility.getCurrent().getFileMap().entrySet()) {
			Set<File> list = entry.getValue();
			for (final File file : list) {
				filePath = file.toString();
				break;
			}
		}
		return filePath;
	}
	
	// Excel import export related code ends for #73020
	
	 @RequestMapping(method = RequestMethod.POST, params = "getBudgetHead")
	    public @ResponseBody Map<Long, String> getBudget(@RequestParam("fieldId") final Long fieldId,
	    		final HttpServletRequest request) {
	        bindModel(request);
	        Map<Long, String> budgetMap = new HashMap<>();
	     	Organisation org = UserSession.getCurrent().getOrganisation();
	     	VendorBillApprovalDTO budgetHeadDTO = new VendorBillApprovalDTO();
	        budgetHeadDTO.setOrgId(org.getOrgid());
	        budgetHeadDTO.setFieldId(fieldId);
	        budgetMap=iLocationMasService.getBudgetExpenditure(budgetHeadDTO);
	        
	       return budgetMap;
	        
	    }

}
