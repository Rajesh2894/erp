package com.abm.mainet.common.master.ui.controller;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.abm.mainet.common.constant.MainetConstants;
import com.abm.mainet.common.constant.PrefixConstants;
import com.abm.mainet.common.domain.Department;
import com.abm.mainet.common.domain.Organisation;
import com.abm.mainet.common.domain.ServiceMaster;
import com.abm.mainet.common.dto.JsonViewObject;
import com.abm.mainet.common.dto.TbAcVendormaster;
import com.abm.mainet.common.dto.TbApprejMas;
import com.abm.mainet.common.exception.FrameworkException;
import com.abm.mainet.common.integration.dms.domain.AttachDocs;
import com.abm.mainet.common.integration.dms.service.IAttachDocsService;
import com.abm.mainet.common.integration.dms.service.IFileUploadService;
import com.abm.mainet.common.master.dto.ContractAgreementSummaryDTO;
import com.abm.mainet.common.master.dto.ContractMastDTO;
import com.abm.mainet.common.master.dto.ContractPart2DetailDTO;
import com.abm.mainet.common.master.dto.TbDepartment;
import com.abm.mainet.common.master.service.DepartmentService;
import com.abm.mainet.common.master.service.IContractAgreementService;
import com.abm.mainet.common.master.service.TbDepartmentService;
import com.abm.mainet.common.master.service.TbServicesMstService;
import com.abm.mainet.common.master.service.TbTaxMasService;
import com.abm.mainet.common.master.ui.model.ContractAgreementModel;
import com.abm.mainet.common.service.IEmployeeService;
import com.abm.mainet.common.service.ServiceMasterService;
import com.abm.mainet.common.service.TbApprejMasService;
import com.abm.mainet.common.service.TbBankmasterService;
import com.abm.mainet.common.ui.controller.AbstractFormController;
import com.abm.mainet.common.utility.ApplicationSession;
import com.abm.mainet.common.utility.CommonMasterUtility;
import com.abm.mainet.common.utility.LookUp;
import com.abm.mainet.common.utility.UserSession;
import com.abm.mainet.common.utility.Utility;

/**
 * @author apurva.salgaonkar
 *
 */
@Controller
@RequestMapping("/ContractAgreement.html")
public class ContractAgreementController extends AbstractFormController<ContractAgreementModel> {

	@Resource
	private TbApprejMasService tbApprejMasService;

	@Resource
	private TbServicesMstService tbServicesMstService;

	private List<TbApprejMas> tbApprejList;

	@Resource
	private TbBankmasterService banksMasterService;

	@Autowired
	private IEmployeeService iEmployeeService;

	@Autowired
	private IContractAgreementService contractAgreementService;

	@Resource
	private TbTaxMasService tbTaxMasService;

	@Resource
	IFileUploadService fileUpload;

	@Resource
	private IAttachDocsService attachDocsService;

	@Resource
	TbDepartmentService departmentService;
	
	@Resource
	private DepartmentService deptService;
	
	@Autowired
	private ServiceMasterService serviceMaster;

	@RequestMapping(method = { RequestMethod.POST, RequestMethod.GET })
	public String index(final Model model, final HttpServletRequest httpServletRequest) {
		sessionCleanup(httpServletRequest);
		fileUpload.sessionCleanUpForFileUpload();
		getModel().bind(httpServletRequest);
		final ServiceMaster service = serviceMaster.getServiceMasterByShortCode("CBP", UserSession.getCurrent().getOrganisation().getOrgid());
		final Long deptId = service.getTbDepartment().getDpDeptid();
		TbDepartment deptDetails = departmentService.findById(deptId);
		model.addAttribute("departmentShortCode", deptDetails.getDpDeptcode());
		Long empdeptid = UserSession.getCurrent().getEmployee().getTbDepartment().getDpDeptid();
		String deptCode = deptService.getDeptCode(empdeptid);
		model.addAttribute("deptCode", deptCode);
		this.getModel().setCommonHelpDocs("ContractAgreement.html");
		model.addAttribute(MainetConstants.ADVNC_DEPOSIT_MAPP_ENTRY.DEPT_LIST, getDepartmentList());
		model.addAttribute(MainetConstants.ContractAgreement.VENDER_lIST, getVenderList());
		LookUp agreementDeptWise = null;

		try {
			agreementDeptWise = CommonMasterUtility.getLookUpFromPrefixLookUpValue(PrefixConstants.ENV.AE,
					MainetConstants.ENV, UserSession.getCurrent().getLanguageId(), UserSession.getCurrent().getOrganisation());
		}catch(Exception e) {

		}

			try {
			if (Utility.isEnvPrefixAvailable(UserSession.getCurrent().getOrganisation(), MainetConstants.ENV_TSCL) && deptCode.equalsIgnoreCase("RL")) {
				model.addAttribute("estateMasters", getEstateList());
				this.getModel().setSummaryDTOList(contractAgreementService.getRLContractAgreementSummaryData(
						UserSession.getCurrent().getOrganisation().getOrgid(), null, null, deptId, null,null,null,null,UserSession.getCurrent().getLanguageId()));
			}
			else if(agreementDeptWise!=null && MainetConstants.FlagY.equalsIgnoreCase(agreementDeptWise.getOtherField())){
				this.getModel().setSummaryDTOList(contractAgreementService.getContractAgreementSummaryData(
						UserSession.getCurrent().getOrganisation().getOrgid(), null, null, empdeptid, null, null,null));
			}else {
				this.getModel().setSummaryDTOList(contractAgreementService.getContractAgreementSummaryData(
						UserSession.getCurrent().getOrganisation().getOrgid(), null, null, deptId, null, null,null));
			}
			
		} catch (Exception ex) {

			throw new FrameworkException("Exception while Contract Summary Data : " + ex.getMessage());
		}
		return MainetConstants.ContractAgreement.CONTRACT_AGREEMENT_SUMMARY;
	}

	@RequestMapping(params = "form", method = { RequestMethod.POST, RequestMethod.GET })
	public ModelAndView formForCreate(final HttpServletRequest request,
			@RequestParam(value = "contId", required = false) final Long contId,
			@RequestParam(value = "type", required = false) final String modeType,
			@RequestParam(value = "showForm", required = false) final String showForm) {
		bindModel(request);
		final ContractAgreementModel contractAgreementModel = getModel();
		populateModel(contId, contractAgreementModel, modeType, showForm, request);
		return new ModelAndView(MainetConstants.ContractAgreement.CONTRACT_AGREEMENT, MainetConstants.FORM_NAME,
				contractAgreementModel);
	}

	private void populateModel(final Long contId, final ContractAgreementModel contractAgreementModel,
			final String modeType, final String showForm, HttpServletRequest request) {
		Long orgId = UserSession.getCurrent().getOrganisation().getOrgid();
		final ServiceMaster service = serviceMaster.getServiceMasterByShortCode("CBP", UserSession.getCurrent().getOrganisation().getOrgid());
		final Long deptId = service.getTbDepartment().getDpDeptid();
		TbDepartment deptDetails = departmentService.findById(deptId);
		this.getModel().setDeptShortCode(deptDetails.getDpDeptcode());
		if (contId == null) {
			contractAgreementModel.setContractMastDTO(new ContractMastDTO());
			contractAgreementModel.setModeType(MainetConstants.RnLCommon.MODE_CREATE);
			if(Utility.isEnvPrefixAvailable(UserSession.getCurrent().getOrganisation(), MainetConstants.ENV_TSCL)) {
				Long contDept = UserSession.getCurrent().getEmployee().getTbDepartment().getDpDeptid();
				contractAgreementModel.getContractMastDTO().setContDept(contDept);
			}
			
		} else {
			contractAgreementModel.setContractMastDTO(
					contractAgreementService.findById(contId, UserSession.getCurrent().getOrganisation().getOrgid()));

			contractAgreementModel.getContractMastDTO()
					.setContractType(CommonMasterUtility.findLookUpCode(PrefixConstants.CONTRACT_TYPE,
							UserSession.getCurrent().getOrganisation().getOrgid(),
							contractAgreementModel.getContractMastDTO().getContType()));
			if (contractAgreementModel.getContractMastDTO().getContractDetailList().get(0).getContSecAmount() != null) {
				contractAgreementModel.setContBGamount(contractAgreementModel.getContractMastDTO()
						.getContractDetailList().get(0).getContSecAmount().toString());
			}

			if (contractAgreementModel.getContractMastDTO().getContractDetailList().get(0).getContAmount() != null)
				contractAgreementModel
						.getContractMastDTO().getContractDetailList().get(
								0)
						.setContractAmt(BigDecimal.valueOf(contractAgreementModel.getContractMastDTO()
								.getContractDetailList().get(0).getContAmount()).setScale(2, BigDecimal.ROUND_UP));
			// Defect #34141-->To get contractor details form tender
			if (contractAgreementModel.getContractMastDTO().getLoaNo() != null) {
				ContractMastDTO mastDto = contractAgreementService.getLoaDetailsByLoaNumber(orgId,
						contractAgreementModel.getContractMastDTO().getLoaNo(),
						departmentService.findDepartmentShortCodeByDeptId(
								contractAgreementModel.getContractMastDTO().getContDept(), orgId));
				if (mastDto != null) {
					contractAgreementModel.setContractMastDTO(contractAgreementService.findById(contId,
							UserSession.getCurrent().getOrganisation().getOrgid()));
					if (contractAgreementModel.getContractMastDTO().getContractDetailList().get(0)
							.getContAmount() != null)
						contractAgreementModel.getContractMastDTO().getContractDetailList().get(0)
								.setContractAmt(
										BigDecimal
												.valueOf(contractAgreementModel.getContractMastDTO()
														.getContractDetailList().get(0).getContAmount())
												.setScale(2, BigDecimal.ROUND_UP));
					if (this.getModel().getContractMastDTO().getContractPart2List().isEmpty()) {
						this.getModel().setTndVendorId(mastDto.getContractPart2List().get(0).getVmVendorid());
					}
				}

			}

			final List<AttachDocs> attachDocs = attachDocsService.findByCode(
					UserSession.getCurrent().getOrganisation().getOrgid(),
					MainetConstants.CONTRACT + MainetConstants.WINDOWS_SLASH
							+ contractAgreementModel.getContractMastDTO().getContId());
			this.getModel().setAttachDocsList(attachDocs);
			if (MainetConstants.RnLCommon.MODE_VIEW.equals(modeType)) {
				contractAgreementModel.setModeType(MainetConstants.RnLCommon.MODE_VIEW);
				contractAgreementModel.setShowForm(showForm);

			}
			if (MainetConstants.RnLCommon.MODE_EDIT.equals(modeType)) {
				contractAgreementModel.setModeType(MainetConstants.RnLCommon.MODE_EDIT);
				contractAgreementModel.setShowForm(showForm);

			}
		}
		// To get the bank details
		final Map<Long, String> bankMap = new HashMap<>();
		final List<Object[]> blist = banksMasterService.findActiveBankList();
		for (final Object[] obj : blist) {
			bankMap.put((Long) obj[0],
					obj[1] + MainetConstants.SEPARATOR + obj[2] + MainetConstants.SEPARATOR + obj[3]);
		}
		this.getModel().setBankMapList(bankMap);
		
		//D76342
		if (modeType != null && modeType.equals(MainetConstants.FlagB)) {
			this.getModel().setContMapFlag(MainetConstants.FlagB);

			this.getModel().setModeType(MainetConstants.FlagV);

		}

		LookUp lookUp = CommonMasterUtility.getLookUpFromPrefixLookUpValue("CAT", "REM",
				UserSession.getCurrent().getLanguageId());
		if(contractAgreementModel.getModeType()!= null || modeType.equals(MainetConstants.FlagB)) {
			tbApprejList = tbApprejMasService.findByRemarkTyped(lookUp.getLookUpId(), orgId);
			List<String> termsList = new ArrayList<>();
			if (tbApprejList != null && !tbApprejList.isEmpty()) {
				this.getModel().setTermsFlag("Y");
				for (TbApprejMas apprejMas : tbApprejList) {
					termsList.add(apprejMas.getArtRemarks());
				}
			}
			this.getModel().setTermsList(termsList);
		}
		//#71812
		if (tbApprejList != null && !tbApprejList.isEmpty()) {
			if(contractAgreementModel.getModeType()!= null && !contractAgreementModel.getModeType().equals(MainetConstants.FlagC)
					|| (modeType != null && modeType.equals(MainetConstants.FlagB))) {
				if(!contractAgreementModel.getContractMastDTO().getContractTermsDetailList().isEmpty() 
						&& contractAgreementModel.getContractMastDTO().getContractTermsDetailList().get(0).getConttDescription().contains("EnCrYpTed")) {
					this.getModel().setTermsFlag(MainetConstants.FlagY);
					String[] reg=contractAgreementModel.getContractMastDTO().getContractTermsDetailList().get(0).getConttDescription().split("EnCrYpTed");
					contractAgreementModel.getContractMastDTO().getContractTermsDetailList().get(0).setConttDescription(reg[0]);
				}else {
					this.getModel().setTermsFlag(MainetConstants.FlagN);
				}
			}else {
				if(this.getModel().getTermsFlag().equals(MainetConstants.FlagY) && !contractAgreementModel.getContractMastDTO().getContractTermsDetailList().isEmpty() 
						&& contractAgreementModel.getContractMastDTO().getContractTermsDetailList().get(0).getConttDescription().contains("EnCrYpTed")) {
					String[] reg=contractAgreementModel.getContractMastDTO().getContractTermsDetailList().get(0).getConttDescription().split("EnCrYpTed");
					contractAgreementModel.getContractMastDTO().getContractTermsDetailList().get(0).setConttDescription(reg[0]);
				}
			}
		}else {
			if(!contractAgreementModel.getContractMastDTO().getContractTermsDetailList().isEmpty() 
						&& contractAgreementModel.getContractMastDTO().getContractTermsDetailList().get(0).getConttDescription().contains("EnCrYpTed")) {
				String[] reg=contractAgreementModel.getContractMastDTO().getContractTermsDetailList().get(0).getConttDescription().split("EnCrYpTed");
				contractAgreementModel.getContractMastDTO().getContractTermsDetailList().get(0).setConttDescription(reg[0]);
			}
		}
		
	}

	private List<Object[]> getDepartmentList() {
		return contractAgreementService
				.getDepartmentsMappedWithContractParti1(UserSession.getCurrent().getOrganisation().getOrgid());
	}

	private List<Object[]> getVenderList() {
		return contractAgreementService.getVenderList(UserSession.getCurrent().getOrganisation().getOrgid(), null,
				null);
	}

	@RequestMapping(params = "getEmpBasedOnDesgnation", method = RequestMethod.POST)
	public @ResponseBody List<LookUp> getEmpBasedOnDesgnation(@RequestParam("degID") final Long desId) {
		List<Object[]> list = null;
		List<LookUp> emplookupList = null;
		list = iEmployeeService.getAllEmpByDesignation(desId, UserSession.getCurrent().getOrganisation().getOrgid());
		emplookupList = new ArrayList<>();
		LookUp look = null;
		for (final Object[] oneEmp : list) {
			look = new LookUp();
			String name = null;
			look.setLookUpId(Long.parseLong(oneEmp[0].toString()));
			if (oneEmp[1] != null) {
				name = oneEmp[1].toString();
			}
			if (oneEmp[3] != null) {
				name = name + " " + oneEmp[3].toString();
			}
			look.setDescLangFirst(name);
			emplookupList.add(look);
		}
		return emplookupList;
	}
	
	@RequestMapping(params = "getDesgBasedOnDept", method = RequestMethod.POST)
	public @ResponseBody List<LookUp> getDesgBasedOnDept(@RequestParam("deptId") final Long deptId) {
		List<LookUp> dsglookupList = contractAgreementService.getAllDesgBasedOnDept(deptId, UserSession.getCurrent().getOrganisation().getOrgid());
		return dsglookupList;
	}

	@RequestMapping(params = "getVenderNameOnVenderType", method = RequestMethod.POST)
	public @ResponseBody List<LookUp> getVenderNameOnVenderType(@RequestParam("venTypeId") final Long venTypeId) {
		List<Object[]> list = null;
		List<LookUp> venderlookupList = null;
		final Long statusId = CommonMasterUtility.getIdFromPrefixLookUpDesc(MainetConstants.Common_Constant.ACTIVE,
				MainetConstants.ContractAgreement.VSS, 1);
		list = contractAgreementService.getVenderList(UserSession.getCurrent().getOrganisation().getOrgid(), venTypeId,
				statusId);
		venderlookupList = new ArrayList<>();
		LookUp look = null;
		for (final Object[] oneVender : list) {
			look = new LookUp();
			look.setLookUpId(Long.parseLong(oneVender[0].toString()));
			look.setDescLangFirst(oneVender[1].toString());
			venderlookupList.add(look);
		}

		return venderlookupList;
	}

	@RequestMapping(params = "getAllTaxesBasedOnDept", method = RequestMethod.POST)
	public @ResponseBody List<LookUp> getAllTaxesBasedOnDept(@RequestParam("deptId") final Long deptId) {
		Organisation org = new Organisation();
		
		final LookUp chargeApplicableAt = CommonMasterUtility.getValueFromPrefixLookUp(
				PrefixConstants.NewWaterServiceConstants.BILL, PrefixConstants.NewWaterServiceConstants.CAA, org);
		Department department = departmentService.findDepartmentById(deptId);
		if (department.getDpDeptcode().equalsIgnoreCase("ADH")) {
			List<LookUp> findAllTaxesForBillPayment = tbTaxMasService.findAllTaxesBasedOnApplicableAt(
					UserSession.getCurrent().getOrganisation().getOrgid(), deptId, chargeApplicableAt.getLookUpId());
			return findAllTaxesForBillPayment;
		} else {
			return tbTaxMasService.getAllTaxesBasedOnDept(UserSession.getCurrent().getOrganisation().getOrgid(),
					UserSession.getCurrent().getLanguageId(),deptId);
		}
	}

	@RequestMapping(params = "save", method = RequestMethod.POST)
	public ModelAndView saveForm(final HttpServletRequest httpServletRequest,
			final HttpServletResponse httpServletResponse) {
		bindModel(httpServletRequest);
		if (getModel().saveForm()) {
			if (getModel().getModeType().equals(MainetConstants.CommonConstants.C)) {
				return jsonResult(JsonViewObject.successResult(
						ApplicationSession.getInstance().getMessage(MainetConstants.ContractAgreement.CONTRACT_CREATE)
								+ MainetConstants.WHITE_SPACE + getModel().getContractMastDTO().getContNo()));
			} else if (getModel().getModeType().equals(MainetConstants.CommonConstants.E)) {
				return jsonResult(JsonViewObject.successResult(ApplicationSession.getInstance()
						.getMessage(MainetConstants.ContractAgreement.CONTRACT_UPDATE) + MainetConstants.WHITE_SPACE
						+ getModel().getContractMastDTO().getContNo() + MainetConstants.WHITE_SPACE
						+ ApplicationSession.getInstance().getMessage(MainetConstants.ContractAgreement.CONTRACT_MSG)));
			}
		}

		return defaultMyResult();
	}

	@RequestMapping(params = "findContractMapedOrNot", method = RequestMethod.POST)
	public @ResponseBody String findContractMapedOrNot(@RequestParam("contId") final Long contId) {
		return contractAgreementService.findContractMapedOrNot(UserSession.getCurrent().getOrganisation().getOrgid(),
				contId);
	}

	@RequestMapping(params = "fileUpload", method = RequestMethod.POST)
	public ModelAndView fileUpload(@RequestParam("uploadId") final Long uploadId,
			@RequestParam("uploadType") final String uploadType) {
		final String photoId = uploadId.toString() + "0";
		getModel().setPhotoId(Long.parseLong(photoId));
		final String thumbId = uploadId.toString() + "1";
		getModel().setThumbId(Long.parseLong(thumbId));
		getModel().setUploadType(uploadType);
		getModel().setViewuploadId(uploadId);
		getModel().setUploadedfile(getModel().getCachePathUpload(uploadType));
		return new ModelAndView(MainetConstants.ContractAgreement.CONTRACT_AGREEMENT_UPLOAD, MainetConstants.FORM_NAME,
				getModel());
	}

	@RequestMapping(params = "deleteCompleteRow", method = RequestMethod.POST)
	public @ResponseBody void deleteCompleteRow(@RequestParam("uploadId") final Long uploadId) {
		final String photoId = uploadId.toString() + "0";
		final String thumbId = uploadId.toString() + "1";
		getModel().deleteUploadedFile(Long.parseLong(photoId), Long.parseLong(thumbId));
	}

	@RequestMapping(params = "deleteSingleUpload", method = RequestMethod.POST)
	public @ResponseBody Map<Long, String> deleteSingleUpload(@RequestParam("deleteMapId") final Long deleteMapId,
			@RequestParam("deleteId") final Long deleteId) {
		return getModel().deleteSingleUpload(deleteMapId, deleteId);
	}

	@RequestMapping(params = "getUploadedImage", method = RequestMethod.POST)
	public @ResponseBody Map<Long, String> getUploadedImage(final HttpServletRequest httpServletRequest) {

		getModel().setUploadedfile(getModel().getCachePathUpload(getModel().getUploadType()));
		return getModel().getUploadedfile();
	}

	@RequestMapping(params = "deleteUploadedFile", method = RequestMethod.POST)
	public @ResponseBody void getUploadedImage(final HttpServletRequest httpServletRequest,
			@RequestParam("photoId") final Long photoId, @RequestParam("thumbId") final Long thumbId) {
		getModel().deleteUploadedFile(photoId, thumbId);
	}

	@RequestMapping(params = "getTenderDetails", method = RequestMethod.POST)
	@ResponseBody
	public ContractMastDTO getTenderDetails(@RequestParam(value = "tndDeptCode", required = false) final Long tndDeptId,
			@RequestParam(value = "loaNo", required = false) final String loaNo,
			@RequestParam(value = "agreementDate", required = false) final Date agreementDate) {
		final ContractAgreementModel model = this.getModel();
		ContractMastDTO mastDto = contractAgreementService.getLoaDetailsByLoaNumber(
				UserSession.getCurrent().getOrganisation().getOrgid(), loaNo,
				departmentService.findDepartmentShortCodeByDeptId(tndDeptId,
						UserSession.getCurrent().getOrganisation().getOrgid()));
		
		if (mastDto != null) {
			if(mastDto.getContDept().equals(tndDeptId)) {
			this.getModel().setContractMastDTO(mastDto);
			}
		}

		LookUp lookUp = CommonMasterUtility.getNonHierarchicalLookUpObjectByPrefix(
				mastDto.getContractDetailList().get(0).getContToPeriodUnit(),
				UserSession.getCurrent().getOrganisation().getOrgid(), "UTS");

		this.getModel().getContractMastDTO().setContToPeriodUnit(lookUp.getLookUpDesc());

		this.getModel().getContractMastDTO().setLoaNo(loaNo);
		this.getModel().getContractMastDTO().setContDate(agreementDate);
		this.getModel().getContractMastDTO().setContDept(tndDeptId);
		/* Defect #34141-->To get contractor details form tender */
		if (CollectionUtils.isNotEmpty(mastDto.getContractPart2List())) {
			this.getModel().setTndVendorId(mastDto.getContractPart2List().get(0).getVmVendorid());
		}
		model.setModeType(MainetConstants.RnLCommon.MODE_CREATE);
		model.setFormMode(MainetConstants.FlagY);
		return this.getModel().getContractMastDTO();
	}

	@RequestMapping(params = "validateLoaNo", method = RequestMethod.POST)
	public @ResponseBody String checkLoaNo(@RequestParam(value = "loaNo", required = false) final String loaNo) {
		String isValid = null;
		ContractMastDTO mastDto = contractAgreementService.getContractByLoaNo(loaNo,
				UserSession.getCurrent().getOrganisation().getOrgid());
		if (mastDto != null) {
			isValid = MainetConstants.Y_FLAG;
		}
		return isValid;
	}

	@RequestMapping(params = "AddPartyDetails", method = { RequestMethod.POST, RequestMethod.GET })
	public ModelAndView addPartyDetails(final HttpServletRequest request) {
		bindModel(request);
		this.getModel().bind(request);
		//D76342
		if (this.getModel().getContMapFlag()!=null && this.getModel().getContMapFlag().equals(MainetConstants.FlagB)) {
			this.getModel().setModeType(MainetConstants.MODE_EDIT);
		}
		final ContractAgreementModel contractAgreementModel = this.getModel();
		Long orgId = UserSession.getCurrent().getOrganisation().getOrgid();
		contractAgreementModel.getContractMastDTO().setHistFlag(this.getModel().getModeType());
		//#71812
		if(contractAgreementModel.getTermsFlag() != null && contractAgreementModel.getTermsFlag().equals(MainetConstants.FlagY)) {
			if(!contractAgreementModel.getContractMastDTO().getContractTermsDetailList().isEmpty()){
				StringBuffer stringBuffer=new StringBuffer();
				stringBuffer.append(contractAgreementModel.getContractMastDTO().getContractTermsDetailList().get(0).getConttDescription());
				stringBuffer.append("EnCrYpTed");
				contractAgreementModel.getContractMastDTO().getContractTermsDetailList().get(0).setConttDescription(stringBuffer.toString());
			}
		}
		
		ContractMastDTO contractMastDTO = contractAgreementModel
				.saveContractData(contractAgreementModel.getContractMastDTO());

		/* Defect #34141-->To get contractor details form tender */
		if (this.getModel().getTndVendorId() != null) {
			ContractPart2DetailDTO contractPart2Dto = new ContractPart2DetailDTO();
			contractPart2Dto.setVmVendorid(this.getModel().getTndVendorId());
			TbAcVendormaster venderMasterDto = contractAgreementService.getVenderTypeIdByVenderId(orgId,
					this.getModel().getTndVendorId());
			contractPart2Dto.setContp2vType(venderMasterDto.getCpdVendortype());
			final List<LookUp> vmVendoridList = getVenderNameOnVenderType(venderMasterDto.getCpdVendortype(), orgId);
			contractPart2Dto.setVmVendoridList(vmVendoridList);
			contractPart2Dto.setContp2Type("V");
			contractMastDTO.getContractPart2List().add(contractPart2Dto);
		}
		/*
		 * #36966-->To show Contractor detail which is coming from tender base on LOA no
		 */
		if (!contractMastDTO.getLoaNo().isEmpty()
				&& !contractAgreementModel.getModeType().equals(MainetConstants.MODE_EDIT)) {
			contractAgreementModel.setContracterNameFlag("Y");
		}
		if (contractMastDTO.getContId() != 0) {
			contractAgreementModel.setContractMastDTO(contractMastDTO);
			/*
			 * if (contractAgreementModel.getModeType().equals(MainetConstants.MODE_CREATE))
			 * { contractMastDTO.getContractPart1List().add(new ContractPart1DetailDTO());
			 * contractMastDTO.getContractPart1List().get(0).setDpDeptid(contractMastDTO.
			 * getContDept()); }
			 */
			fileUpload.sessionCleanUpForFileUpload();
			if (contractAgreementModel.getModeType().equals(MainetConstants.MODE_EDIT)) {
				final String guidRanDNum = Utility.getGUIDNumber();
				getModel().getAllUploadedFile(guidRanDNum);
				contractAgreementModel.setModeType(MainetConstants.MODE_EDIT);
			}
			//D76342
			if (this.getModel().getContMapFlag()!=null && this.getModel().getContMapFlag().equals(MainetConstants.FlagB)) {
				// this.getModel().setModeType(MainetConstants.VIEW_MODE);
				contractAgreementModel.setModeType(MainetConstants.FlagV);
			}
			return new ModelAndView("addPartyDetails", MainetConstants.FORM_NAME, contractAgreementModel);
		} else {
			return new ModelAndView(MainetConstants.ContractAgreement.CONTRACT_AGREEMENT, MainetConstants.FORM_NAME,
					contractAgreementModel);
		}
	}

	@RequestMapping(params = "filterData", method = RequestMethod.POST)
	public @ResponseBody List<ContractAgreementSummaryDTO> getGridFilterData(
			@RequestParam(value = "contractNo") final String contractNo,
			@RequestParam(value = "contractDate") final String contractDate,
			@RequestParam(value = "viewClosedCon") final String viewClosedCon,
			@RequestParam(value = "estateId",required = false)  Long estateId,
			@RequestParam(value = "propId",required = false)  Long propId,
			@RequestParam(value = "deptCode",required = false)  String deptCode) {
		List<ContractAgreementSummaryDTO> contractAgreementSummaryDTO = new ArrayList<>();

		try {
			if (!viewClosedCon.equals(MainetConstants.FlagD)) {
				if (Utility.isEnvPrefixAvailable(UserSession.getCurrent().getOrganisation(),MainetConstants.ENV_TSCL) && deptCode.equalsIgnoreCase("RL")) {
					if(estateId == null || estateId == 0L) {
						estateId = null;
					}
					if(propId == null || propId == 0L) {
						propId = null;
					}
					contractAgreementSummaryDTO = contractAgreementService.getRLContractAgreementSummaryData(
							UserSession.getCurrent().getOrganisation().getOrgid(),
							contractNo.isEmpty() ? null : contractNo, contractDate.isEmpty() ? null : contractDate,
							null, viewClosedCon.isEmpty() ? null : viewClosedCon, null,
							estateId,propId,UserSession.getCurrent().getLanguageId());
				} else {
					contractAgreementSummaryDTO = contractAgreementService.getContractAgreementSummaryData(
							UserSession.getCurrent().getOrganisation().getOrgid(), contractNo.isEmpty() ? null : contractNo,
							contractDate.isEmpty() ? null : contractDate,null, null, 
							viewClosedCon.isEmpty() ? null : viewClosedCon, null);
				}

			} else {

				contractAgreementSummaryDTO = contractAgreementService.getContractAgreementFilterData(
						UserSession.getCurrent().getOrganisation().getOrgid(), contractNo, contractDate, viewClosedCon);

			}
		} catch (Exception e) {
			throw new FrameworkException("Problem in getGridData", e);
		}

		return contractAgreementSummaryDTO;
	}

	@RequestMapping(params = "viewPartyDetails", method = { RequestMethod.POST, RequestMethod.GET })
	public ModelAndView viewPartyDetails(final HttpServletRequest request) {
		bindModel(request);
		final ContractAgreementModel contractAgreementModel = this.getModel();
		contractAgreementModel.setContractMastDTO(
				contractAgreementService.findById(contractAgreementModel.getContractMastDTO().getContId(),
						UserSession.getCurrent().getOrganisation().getOrgid()));
		return new ModelAndView("addPartyDetails", MainetConstants.FORM_NAME, contractAgreementModel);

	}

	@RequestMapping(params = "backToContractDetails", method = { RequestMethod.POST, RequestMethod.GET })
	public ModelAndView backToContractDetails(final HttpServletRequest request) {
		bindModel(request);
		this.getModel().bind(request);
		fileUpload.sessionCleanUpForFileUpload();
		final ContractAgreementModel contractAgreementModel = this.getModel();
		contractAgreementModel.getCommonFileAttachment().clear();
		Long taxId = null;
		if(contractAgreementModel.getContractMastDTO().getTaxId() != null){
			taxId = contractAgreementModel.getContractMastDTO().getTaxId();
		}
		contractAgreementModel.setContractMastDTO(
				contractAgreementService.findById(contractAgreementModel.getContractMastDTO().getContId(),
						UserSession.getCurrent().getOrganisation().getOrgid()));
		if(taxId != null){
			contractAgreementModel.getContractMastDTO().setTaxId(taxId);
		}
		contractAgreementModel.getContractMastDTO()
				.setContractType(CommonMasterUtility.findLookUpCode(PrefixConstants.CONTRACT_TYPE,
						UserSession.getCurrent().getOrganisation().getOrgid(),
						contractAgreementModel.getContractMastDTO().getContType()));
		if (contractAgreementModel.getContractMastDTO().getContractDetailList().get(0).getContAmount() != null)
			contractAgreementModel.getContractMastDTO().getContractDetailList().get(0)
					.setContractAmt(BigDecimal.valueOf(
							contractAgreementModel.getContractMastDTO().getContractDetailList().get(0).getContAmount())
							.setScale(2, BigDecimal.ROUND_UP));
		this.getModel()
				.setAttachDocsList(attachDocsService.findByCode(UserSession.getCurrent().getOrganisation().getOrgid(),
						MainetConstants.CONTRACT + MainetConstants.DOUBLE_BACK_SLACE
								+ contractAgreementModel.getContractMastDTO().getContId()));
		//#71812
		if(!this.getModel().getTermsList().isEmpty()) {
			if(contractAgreementModel.getModeType()!= null && !contractAgreementModel.getModeType().equals(MainetConstants.FlagC)
					|| (contractAgreementModel.getModeType() != null && contractAgreementModel.getModeType().equals(MainetConstants.FlagB))) {
				if(!contractAgreementModel.getContractMastDTO().getContractTermsDetailList().isEmpty() 
						&& contractAgreementModel.getContractMastDTO().getContractTermsDetailList().get(0).getConttDescription().contains("EnCrYpTed")) {
					this.getModel().setTermsFlag(MainetConstants.FlagY);
					String[] reg=contractAgreementModel.getContractMastDTO().getContractTermsDetailList().get(0).getConttDescription().split("EnCrYpTed");
					contractAgreementModel.getContractMastDTO().getContractTermsDetailList().get(0).setConttDescription(reg[0]);
				}else {
					this.getModel().setTermsFlag(MainetConstants.FlagN);
				}
			}else {
				if(this.getModel().getTermsFlag().equals(MainetConstants.FlagY) && !contractAgreementModel.getContractMastDTO().getContractTermsDetailList().isEmpty() 
						&& contractAgreementModel.getContractMastDTO().getContractTermsDetailList().get(0).getConttDescription().contains("EnCrYpTed")) {
					String[] reg=contractAgreementModel.getContractMastDTO().getContractTermsDetailList().get(0).getConttDescription().split("EnCrYpTed");
					contractAgreementModel.getContractMastDTO().getContractTermsDetailList().get(0).setConttDescription(reg[0]);
				}
			}
		}else {
			if(!contractAgreementModel.getContractMastDTO().getContractTermsDetailList().isEmpty() 
					&& contractAgreementModel.getContractMastDTO().getContractTermsDetailList().get(0).getConttDescription().contains("EnCrYpTed")) {
				String[] reg=contractAgreementModel.getContractMastDTO().getContractTermsDetailList().get(0).getConttDescription().split("EnCrYpTed");
				contractAgreementModel.getContractMastDTO().getContractTermsDetailList().get(0).setConttDescription(reg[0]);
			}
		}
		
		
		return new ModelAndView(MainetConstants.ContractAgreement.CONTRACT_AGREEMENT, MainetConstants.FORM_NAME,
				contractAgreementModel);

	}

	public List<LookUp> getVenderNameOnVenderType(final Long venTypeId, final Long orgId) {
		List<Object[]> list = null;
		List<LookUp> venderlookupList = null;
		final Long statusId = CommonMasterUtility.getIdFromPrefixLookUpDesc("Active", "VSS", 1);
		list = contractAgreementService.getVenderList(orgId, venTypeId, statusId);
		venderlookupList = new ArrayList<>();
		LookUp look = null;
		for (final Object[] oneVender : list) {
			look = new LookUp();
			look.setLookUpId(Long.parseLong((oneVender[0].toString())));
			look.setDescLangFirst(oneVender[1].toString());
			venderlookupList.add(look);
		}
		return venderlookupList;
	}
	
	private List<Object[]> getEstateList() {
		return contractAgreementService.getEstateList(UserSession.getCurrent().getOrganisation().getOrgid());
	}
	
	@ResponseBody
	@RequestMapping(params = "propList", method = RequestMethod.POST)
	public List<Object[]> getPropListByEstateId(@RequestParam("esId") final Long esId) {
		return contractAgreementService.getPropListByEstateId(UserSession.getCurrent().getOrganisation().getOrgid(), esId);
	}
	
	
	@RequestMapping(params = "getTenderLoaDetails", method = RequestMethod.POST)
	@ResponseBody
	public ContractMastDTO getTenderLoaDetails(@RequestParam(value = "tndDeptCode", required = false) final Long tndDeptId,
			@RequestParam(value = "loaNo", required = false) final String loaNo,
			@RequestParam(value = "agreementDate", required = false) final Date agreementDate) {
		final ContractAgreementModel model = this.getModel();
		ContractMastDTO mastDto = contractAgreementService.getLoaDetailsByLoaNumber(
				UserSession.getCurrent().getOrganisation().getOrgid(), loaNo,
				departmentService.findDepartmentShortCodeByDeptId(tndDeptId,
						UserSession.getCurrent().getOrganisation().getOrgid()));

		if (mastDto.getContDept() != null  ) {
			if(mastDto.getContDept().equals(tndDeptId)) {
				this.getModel().setContractMastDTO(mastDto);
			}
		}
		if (mastDto.getContDept() != null  ) {
		return this.getModel().getContractMastDTO();
		}
		else {
			return mastDto;
		}
		
	}
}
