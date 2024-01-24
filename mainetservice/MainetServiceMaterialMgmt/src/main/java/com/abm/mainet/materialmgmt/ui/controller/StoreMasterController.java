package com.abm.mainet.materialmgmt.ui.controller;

import java.io.File;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.abm.mainet.common.constant.MainetConstants;
import com.abm.mainet.common.dto.ReadExcelData;
import com.abm.mainet.common.integration.dms.fileUpload.FileUploadUtility;
import com.abm.mainet.common.integration.dms.service.IFileUploadService;
import com.abm.mainet.common.service.IEmployeeService;
import com.abm.mainet.common.service.ILocationMasService;
import com.abm.mainet.common.ui.controller.AbstractFormController;
import com.abm.mainet.common.utility.UserSession;
import com.abm.mainet.common.utility.Utility;
import com.abm.mainet.materialmgmt.dto.StoreGroupMappingDto;
import com.abm.mainet.materialmgmt.dto.StoreMasterDTO;
import com.abm.mainet.materialmgmt.dto.WriteExcelData;
import com.abm.mainet.materialmgmt.service.IStoreMasterService;
import com.abm.mainet.materialmgmt.ui.model.StoreMasterModel;
import com.abm.mainet.materialmgmt.utility.StoreMasterUtility;

@Controller
@RequestMapping("/StoreMaster.html")
public class StoreMasterController extends AbstractFormController<StoreMasterModel> {

	@Autowired
	private ILocationMasService iLocationMasService;

	@Autowired
	private IEmployeeService employeeService;

	@Autowired
	private IStoreMasterService storeMasterservice;

	@Autowired
	private IFileUploadService fileUpload;

	@Autowired
	private IStoreMasterService storeMasterService;

	@RequestMapping(method = { RequestMethod.POST })
	public ModelAndView index(final HttpServletRequest httpServletRequest, Model model) {
		sessionCleanup(httpServletRequest);
		loadLocationData(httpServletRequest);
		Long orgId = UserSession.getCurrent().getOrganisation().getOrgid();
		this.getModel().setStoreMasterSummmaryDataList(storeMasterservice.serchStoreMasterDataByOrgid(orgId));
		loadStoreMasterSummaryData(orgId);
		fileUpload.sessionCleanUpForFileUpload();
		this.getModel().setCommonHelpDocs("StoreMaster.html");
		return index();
	}

	private void loadStoreMasterSummaryData(Long orgId) {		
		List<Object[]> employeeObjectList = employeeService.findAllEmpIntialInfoByOrg(orgId);
		Map<Long, String> employeeObjectMap = new HashMap<>();
		employeeObjectList.forEach(employeeObject -> {
			employeeObjectMap.put(Long.parseLong(employeeObject[3].toString()),
					replaceNull(employeeObject[0]) + MainetConstants.WHITE_SPACE + replaceNull(employeeObject[2]));
		});
		
		List<Object[]> locationObjectList = iLocationMasService.getLocationNameByOrgId(orgId);
		Map<Long, String> locationObjectMap = new HashMap<>();
		locationObjectList.forEach(locationObject -> {
			locationObjectMap.put(Long.parseLong(locationObject[0].toString()), locationObject[1].toString());
		});
		
		this.getModel().getStoreMasterSummmaryDataList().forEach(storeMasterData -> {
			storeMasterData.setStoreInchargeName(employeeObjectMap.get(storeMasterData.getStoreIncharge()));
			storeMasterData.setLocationName(locationObjectMap.get(storeMasterData.getLocation()));
		});
	}

	private void loadLocationData(final HttpServletRequest httpServletRequest) {
		this.getModel().setLocList(iLocationMasService
				.fillAllActiveLocationByOrgId(UserSession.getCurrent().getOrganisation().getOrgid()));
	}

	@RequestMapping(params = "addStoreMaster", method = RequestMethod.POST)
	public ModelAndView addStoreMaster(final HttpServletRequest request) {
		this.getModel().setSaveMode(MainetConstants.FlagA);
		loadLocationData(request);
		this.getModel().setLookupList(StoreMasterUtility.getLookUpDesc("ITG", 1, UserSession.getCurrent().getOrganisation().getOrgid()));
		return new ModelAndView("storeMasterForm", MainetConstants.FORM_NAME, this.getModel());
	}
	
	
	@RequestMapping(params = "getStoreInchargeList", method = { RequestMethod.POST }, produces = "application/json")
	@ResponseBody
	public List<Object[]> getStoreInchargeList(@RequestParam("locId") Long locId, final HttpServletRequest httpServletRequest) {
		List<Object[]> empList = employeeService.findAllActiveEmployeeByLocation(locId);
		for (Object[] objects : empList) {
			String fullName = replaceNull(objects[0]) + MainetConstants.WHITE_SPACE + replaceNull(objects[1])
					+ MainetConstants.WHITE_SPACE + replaceNull(objects[2]);
			objects[0] = fullName;
		}
		this.getModel().setInchargeList(empList);		
		return this.getModel().getInchargeList();
	}
	
	private String replaceNull(Object name) {
		if (name == null)
			name = MainetConstants.BLANK;
		return name.toString();
	}

	@ResponseBody
	@RequestMapping(params = "searchStoreMaster", method = RequestMethod.POST)
	public ModelAndView searchStoreMaster(final HttpServletRequest request,
			@RequestParam(required = false) Long locationId, @RequestParam(required = false) String storeName) {
		sessionCleanup(request);
		Long orgId = UserSession.getCurrent().getOrganisation().getOrgid();
		this.getModel().getStoreMasterDTO().setLocation(locationId);
		this.getModel().getStoreMasterDTO().setStoreName(storeName);
		this.getModel().setStoreMasterSummmaryDataList(storeMasterservice.serchStoreByLocAndStoreName(locationId, storeName, orgId));
		loadStoreMasterSummaryData(orgId);
		loadLocationData(request);
		return new ModelAndView("StoreMasterSearch", MainetConstants.FORM_NAME, this.getModel());
	}

	@ResponseBody
	@RequestMapping(params = "viewStoreMaster", method = RequestMethod.POST)
	public ModelAndView viewtRouteMaster(final HttpServletRequest request, @RequestParam Long storeId) {
		Long orgId = UserSession.getCurrent().getOrganisation().getOrgid();
		this.getModel().setStoreMasterDTO(storeMasterservice.getStoreMasterByStoreId(storeId));
		loadLocationData(request);
		this.getModel().setLookupList(StoreMasterUtility.getLookUpDesc("ITG", 1, orgId));
		this.getModel().setSaveMode(MainetConstants.FlagV);
		getStoreInchargeList(this.getModel().getStoreMasterDTO().getLocation(), request);
		return new ModelAndView("storeMasterForm", MainetConstants.FORM_NAME, this.getModel());
	}

	@ResponseBody
	@RequestMapping(params = "editStoreMaster", method = RequestMethod.POST)
	public ModelAndView edittRouteMaster(final HttpServletRequest request, @RequestParam Long storeId) {
		Long orgId = UserSession.getCurrent().getOrganisation().getOrgid();
		this.getModel().setStoreMasterDTO(storeMasterservice.getStoreMasterByStoreId(storeId));
		loadLocationData(request);
		this.getModel().setLookupList(StoreMasterUtility.getLookUpDesc("ITG", 1, orgId));
		this.getModel().setSaveMode(MainetConstants.FlagE);
		getStoreInchargeList(this.getModel().getStoreMasterDTO().getLocation(), request);
		return new ModelAndView("storeMasterForm", MainetConstants.FORM_NAME, this.getModel());
	}

	@ResponseBody
	@RequestMapping(params = "excelUploadMaster", method = RequestMethod.POST)
	public ModelAndView ExcelUploadForm(final HttpServletRequest request) {
		fileUpload.sessionCleanUpForFileUpload();
		return new ModelAndView("excelUploadStoreMaster", MainetConstants.FORM_NAME, this.getModel());
	}

	/**
	 * export Rate Excel Data
	 * 
	 * @param response
	 * @param request
	 */
	@SuppressWarnings({ "rawtypes", "unchecked" })
	@RequestMapping(params = "exportStoreExcelData", method = { RequestMethod.GET })
	public void exportStoreExcelData(final HttpServletResponse response, final HttpServletRequest request) {
		List<StoreMasterDTO> dtos = new ArrayList<>();
		try {
			WriteExcelData data = new WriteExcelData("SoteMaster.xlsx", request, response);
			data.getExpotedExcelSheet(dtos, StoreMasterDTO.class);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@SuppressWarnings({ "rawtypes", "unchecked", "deprecation" })
	@ResponseBody
	@RequestMapping(params = MainetConstants.WorksManagement.LOAD_EXCEL_DATA, method = RequestMethod.POST)
	public ModelAndView loadValidateAndLoadExcelData(final HttpServletRequest request) {
		this.getModel().bind(request);
		String filePath = null;
		for (final Map.Entry<Long, Set<File>> entry : FileUploadUtility.getCurrent().getFileMap().entrySet()) {
			Set<File> list = entry.getValue();
			for (final File file : list) {
				filePath = file.toString();
				break;
			}
		}
		try {
			ReadExcelData data = new ReadExcelData<>(filePath, StoreMasterDTO.class);
			data.parseExcelList();
			int i = 1;
			List<String> errlist = data.getErrorList();
			if (errlist.isEmpty()) {
				List<StoreMasterDTO> dtoList = data.getParseData();
				List<StoreMasterDTO> detList = new ArrayList<>();
				List<StoreGroupMappingDto> storeDet = new ArrayList<>();
				List<StoreGroupMappingDto> storeDetail = new ArrayList<>();
				StoreGroupMappingDto storeGroupMappingDto = null;
				for (StoreMasterDTO storeMasterDTO : dtoList) {
					storeGroupMappingDto = new StoreGroupMappingDto();
					if (storeMasterDTO.getStoreName() == null) {
						this.getModel().addValidationError("StoreName " + i + "/ Enter Proper StoreName");
					}
					if (storeMasterDTO.getLocation() == null) {

						this.getModel().addValidationError("Location " + i + "/ Enter Proper Location");
					}

					if (storeMasterDTO.getAddress() == null) {
						this.getModel().addValidationError("Address " + i + " / Enter Proper Address");
					}

					if (storeMasterDTO.getStoreIncharge() == null) {
						this.getModel().addValidationError("StoreIncharge " + i + " / Enter Proper StoreIncharge");
					}
					storeGroupMappingDto.setItemGroupId(storeMasterDTO.getItemGroupId());
					storeDetail.add(storeGroupMappingDto);
					storeMasterDTO.setStoreGrMappingDtoList(storeDetail);
					storeMasterDTO.setOrgId(UserSession.getCurrent().getOrganisation().getOrgid());
					storeMasterDTO.setUserId(UserSession.getCurrent().getEmployee().getEmpId());
					storeMasterDTO.setLmoDate(new Date());
					storeMasterDTO.setLgIpMac(Utility.getMacAddress());
					storeMasterDTO.setLangId(UserSession.getCurrent().getOrganisation().getOrgid());
					if (storeMasterDTO.getStoreGrMappingDtoList() != null) {
						for (StoreGroupMappingDto storeDetails : storeMasterDTO.getStoreGrMappingDtoList()) {
							storeGroupMappingDto.setOrgId(UserSession.getCurrent().getOrganisation().getOrgid());
							storeGroupMappingDto.setUserId(UserSession.getCurrent().getEmployee().getEmpId());
							storeGroupMappingDto.setUpdatedBy(UserSession.getCurrent().getEmployee().getEmpId());
							storeGroupMappingDto.setUpdatedDate(new Date());
							storeGroupMappingDto.setLgIpMac(Utility.getMacAddress());
							storeGroupMappingDto.setLangId(UserSession.getCurrent().getOrganisation().getOrgid());
							storeGroupMappingDto.setItemGroupId(storeDetails.getItemGroupId());
							storeDet.add(storeGroupMappingDto);
						}
					}
					detList.add(storeMasterDTO);
					i++;
				}
				if (!this.getModel().hasValidationErrors()) {
					storeMasterService.saveStoreExcelData(detList);
				}
			} else {
				this.getModel().addValidationError("Check the Content of Excel Sheet");
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
		fileUpload.sessionCleanUpForFileUpload();
		return customResult("excelUploadStoreMaster");
	}

}
