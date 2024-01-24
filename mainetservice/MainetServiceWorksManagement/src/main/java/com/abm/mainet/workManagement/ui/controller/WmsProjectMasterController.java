
package com.abm.mainet.workManagement.ui.controller;

import java.io.File;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.Map.Entry;
import java.util.stream.Collectors;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.abm.mainet.common.constant.MainetConstants;
import com.abm.mainet.common.constant.MainetConstants.AccountConstants;
import com.abm.mainet.common.constant.MainetConstants.BUG_HEAD_OPENING_BALANCE_MASTER;
import com.abm.mainet.common.constant.PrefixConstants.AccountPrefix;
import com.abm.mainet.common.domain.Organisation;
import com.abm.mainet.common.exception.FrameworkException;
import com.abm.mainet.common.integration.acccount.domain.AccountHeadSecondaryAccountCodeMasterEntity;
import com.abm.mainet.common.integration.acccount.dto.VendorBillApprovalDTO;
import com.abm.mainet.common.integration.acccount.service.AccountFieldMasterService;
import com.abm.mainet.common.integration.acccount.service.SecondaryheadMasterService;
import com.abm.mainet.common.integration.dms.domain.AttachDocs;
import com.abm.mainet.common.integration.dms.fileUpload.FileUploadUtility;
import com.abm.mainet.common.integration.dms.service.IAttachDocsService;
import com.abm.mainet.common.integration.dms.service.IFileUploadService;
import com.abm.mainet.common.integration.dto.DocumentDetailsVO;
import com.abm.mainet.common.integration.dto.RequestDTO;
import com.abm.mainet.common.master.dto.TbFinancialyear;
import com.abm.mainet.common.master.service.TbDepartmentService;
import com.abm.mainet.common.master.service.TbFinancialyearService;
import com.abm.mainet.common.ui.controller.AbstractFormController;
import com.abm.mainet.common.utility.ApplicationContextProvider;
import com.abm.mainet.common.utility.CommonMasterUtility;
import com.abm.mainet.common.utility.LookUp;
import com.abm.mainet.common.utility.UserSession;
import com.abm.mainet.common.utility.Utility;
import com.abm.mainet.workManagement.dto.TenderWorkDto;
import com.abm.mainet.workManagement.dto.WmsProjectMasterDto;
import com.abm.mainet.workManagement.dto.WorkDefinationYearDetDto;
import com.abm.mainet.workManagement.dto.WorkDefinitionDto;
import com.abm.mainet.workManagement.service.SchemeMasterService;
import com.abm.mainet.workManagement.service.TenderInitiationService;
import com.abm.mainet.workManagement.service.TenderInitiationServiceImpl;
import com.abm.mainet.workManagement.service.WmsProjectMasterService;
import com.abm.mainet.workManagement.service.WorkDefinitionService;
import com.abm.mainet.workManagement.ui.model.WmsProjectMasterModel;

@Controller
@RequestMapping("/WmsProjectMaster.html")
public class WmsProjectMasterController extends AbstractFormController<WmsProjectMasterModel> {

	private static final Logger LOGGER = LoggerFactory.getLogger(WmsProjectMasterController.class);
	
    @Autowired
    private WmsProjectMasterService iProjectMasterService;

    @Autowired
    private SchemeMasterService schemeMasterService;

    @Autowired
    private TbDepartmentService iTbDepartmentService;

    @Autowired
    private IFileUploadService fileUpload;
    
    @Autowired
    private WorkDefinitionService workDefinitionService;
    
    @Resource
	private AccountFieldMasterService tbAcFieldMasterService;

    /**
     * default summary pages
     * @param httpServletRequest
     * @return
     */
    @RequestMapping(method = { RequestMethod.POST, RequestMethod.GET })
    public ModelAndView index(final Model models,final HttpServletRequest httpServletRequest) {
        sessionCleanup(httpServletRequest);
        fileUpload.sessionCleanUpForFileUpload();
        WmsProjectMasterModel model = this.getModel();
        this.getModel().setCommonHelpDocs("WmsProjectMaster.html");
        Long orgId = UserSession.getCurrent().getOrganisation().getOrgid();
        model.setSchemeMasterDtoList(schemeMasterService.getSchemeMasterList(null, null, null, orgId));
        model.setProjectMasterList(iProjectMasterService.getProjectMasterList(null, null, null, null, orgId, null, null));
        model.setDepartmentsList(
                iTbDepartmentService.findMappedDepartments(orgId));
        model.prepareProjectMasterGridData(getModel().getProjectMasterList());
        model.setSourceLookUps(CommonMasterUtility.getNextLevelData(MainetConstants.WorksManagement.SSF, 1, orgId));
        model.setDepartmentsList(
                iTbDepartmentService.findMappedDepartments(orgId));
        Organisation organisation = new Organisation();
        organisation.setOrgid(orgId);
        List<LookUp> defaultStatusUAD = CommonMasterUtility.getLookUps(MainetConstants.WorksManagement.CSR, organisation).stream()
                .filter(c -> c.getDefaultVal().equals(MainetConstants.FlagY)).collect(Collectors.toList());

        if (defaultStatusUAD.get(0).getLookUpCode().equals(MainetConstants.YES)) {
            model.setUADstatusForProject(defaultStatusUAD.get(0).getLookUpCode());
        }
        models.addAttribute(MainetConstants.BUDGET_PROJECTED_EXPENDITURE_MASTER.FIELD_MASTER_ITEMS,
				tbAcFieldMasterService.getFieldMasterLastLevels(UserSession.getCurrent().getOrganisation().getOrgid()));
        return index();
    }

    @RequestMapping(method = RequestMethod.POST, params = MainetConstants.WorksManagement.SAVE_AND_UPDATE_PROJECTMASTER)
    public ModelAndView saveAndUpdateProjectMaster(final HttpServletRequest request) {

        bindModel(request);
        WmsProjectMasterModel masterModel = this.getModel();
        RequestDTO requestDTO = new RequestDTO();
        requestDTO.setOrgId(UserSession.getCurrent().getOrganisation().getOrgid());
        requestDTO.setStatus(MainetConstants.FlagA);
        requestDTO.setIdfId(masterModel.getWmsProjectMasterDto().getProjCode());
        requestDTO.setDepartmentName(MainetConstants.WorksManagement.WORKS_MANAGEMENT);
        List<DocumentDetailsVO> dto = this.getModel().getAttachments();

        masterModel.setAttachments(fileUpload.setFileUploadMethod(new ArrayList<DocumentDetailsVO>()));

        int i = 0;
        for (final Map.Entry<Long, Set<File>> entry : FileUploadUtility.getCurrent().getFileMap().entrySet()) {
            masterModel.getAttachments().get(i).setDoc_DESC_ENGL(dto.get(entry.getKey().intValue()).getDoc_DESC_ENGL());
            i++;
        }
        fileUpload.doMasterFileUpload(masterModel.getAttachments(), requestDTO);
        masterModel.prepareProjectMasterEntity(masterModel.getWmsProjectMasterDto());
        iProjectMasterService.saveUpdateProjectMaster(masterModel.getWmsProjectMasterDto(),
                masterModel.getRemoveFileByIdVal());
        return index();
    }

    @ResponseBody
    @RequestMapping(params = MainetConstants.WorksManagement.EDIT_PROJECT_MASTERDATA, method = RequestMethod.POST)
    public ModelAndView editProjectMasterData(final Model model,
            @RequestParam(MainetConstants.WorksManagement.PROJ_ID) final Long projId) {
    	
    	List<AccountHeadSecondaryAccountCodeMasterEntity> listAdd = new ArrayList<>();

        this.getModel().setSaveMode(MainetConstants.WorksManagement.EDIT);
        WmsProjectMasterDto projectMasterDto = iProjectMasterService.getProjectMasterByProjId(projId);
        
        List<WorkDefinationYearDetDto> definationYearDetDto=iProjectMasterService.getYearDetailByProjectId(projectMasterDto, projectMasterDto.getOrgId());
        
        LookUp defaultVal = CommonMasterUtility.getDefaultValue(BUG_HEAD_OPENING_BALANCE_MASTER.SLI_PREFIX_VALUE);
        if (defaultVal != null) {
        	this.getModel().setCpdMode(CommonMasterUtility.getDefaultValue(BUG_HEAD_OPENING_BALANCE_MASTER.SLI_PREFIX_VALUE)
                    .getLookUpCode());
            if (this.getModel().getCpdMode().equals(MainetConstants.FlagL)) {
            	if(!definationYearDetDto.isEmpty()) {
            		definationYearDetDto.forEach(dto -> {     	
            		Map<Long, String> budgetMap = new HashMap<>();
            	    VendorBillApprovalDTO budgetHeadDTO = new VendorBillApprovalDTO();
            	    budgetHeadDTO.setOrgId(UserSession.getCurrent().getOrganisation().getOrgid());
            	    budgetHeadDTO.setFieldId(dto.getFieldId());
            	    budgetHeadDTO.setDepartmentId(projectMasterDto.getDpDeptId());
            	List<AccountHeadSecondaryAccountCodeMasterEntity> list = new ArrayList<>();
            	 budgetMap=iProjectMasterService.getBudgetExpenditure(budgetHeadDTO);
            	 for (Entry<Long, String> entry : budgetMap.entrySet()) {
            		 AccountHeadSecondaryAccountCodeMasterEntity dtos=new AccountHeadSecondaryAccountCodeMasterEntity();
            		 String a= String.valueOf(entry.getKey());
            		 dtos.setSacHeadId(Long.valueOf(a));
            		 dtos.setAcHeadCode(entry.getValue());
            		 list.add(dtos);
            	 }
            	//list=(ApplicationContextProvider.getApplicationContext().getBean(SecondaryheadMasterService.class)
                      //  .getSecondaryHeadcodesWithDeptField(UserSession.getCurrent().getOrganisation().getOrgid(),projectMasterDto.getDpDeptId(),dto.getFieldId()));
            	dto.setBudgetList(list);
            	listAdd.addAll(list);
            		   });
            		 this.getModel().setBudgetList(listAdd);
            	  }
            	else {
            		this.getModel().setBudgetList(ApplicationContextProvider.getApplicationContext().getBean(SecondaryheadMasterService.class)
                            .getSecondaryHeadcodesForWorks(UserSession.getCurrent().getOrganisation().getOrgid()));
            	}
            }
           
        } else {
        	this.getModel().setCpdMode(null);
        }
        if(!definationYearDetDto.isEmpty()) {
        	projectMasterDto.setYearDtos(definationYearDetDto);        	
        }
        
        this.getModel().setDepartmentsList(
                iTbDepartmentService.findMappedDepartments(UserSession.getCurrent().getOrganisation().getOrgid()));
        this.getModel().setWmsProjectMasterDto(projectMasterDto);

        final List<AttachDocs> attachDocs = ApplicationContextProvider.getApplicationContext().getBean(IAttachDocsService.class)
                .findByCode(UserSession.getCurrent().getOrganisation().getOrgid(), projectMasterDto.getProjCode());
        this.getModel().setAttachDocsList(attachDocs);
        
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
        
        
        model.addAttribute(MainetConstants.BUDGET_PROJECTED_EXPENDITURE_MASTER.FIELD_MASTER_ITEMS,
				tbAcFieldMasterService.getFieldMasterLastLevels(UserSession.getCurrent().getOrganisation().getOrgid()));

        return new ModelAndView(MainetConstants.WorksManagement.ADD_PROJECT_MASTER, MainetConstants.FORM_NAME,
                this.getModel());

    }

    @RequestMapping(method = RequestMethod.POST, params = MainetConstants.WorksManagement.ADD_PROJECT_MASTER)
    public ModelAndView editProjectMasterData(final Model model,final HttpServletRequest request) {
        bindModel(request);
        this.getModel().setSchemeMasterDtoList(schemeMasterService.getSchemeMasterList(null, null, null,
                UserSession.getCurrent().getOrganisation().getOrgid()));
        if (this.getModel().getSaveMode() == null)
            this.getModel().setSaveMode(MainetConstants.WorksManagement.ADD);
        this.getModel().setDepartmentsList(
                iTbDepartmentService.findMappedDepartments(UserSession.getCurrent().getOrganisation().getOrgid()));
        
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
            if (this.getModel().getCpdMode().equals(MainetConstants.FlagL)) {
            	 LookUp lookup=null;
              	Organisation org = UserSession.getCurrent().getOrganisation();
              	int langId = UserSession.getCurrent().getLanguageId();
                  try {
                  	 lookup = CommonMasterUtility.getLookUpFromPrefixLookUpValue(AccountConstants.ASD.getValue(),
                              AccountPrefix.AIC.toString(),langId,org);
                  }catch(Exception e) {
                	  LOGGER.info("Prefix not found------------------------------------------"+e);
                  }
				if (lookup != null && lookup.getOtherField().equals("Y")) {
					//this.getModel().setBudgetList(ApplicationContextProvider.getApplicationContext().getBean(SecondaryheadMasterService.class).getSecondaryHeadcodesWithDeptField(
											//UserSession.getCurrent().getOrganisation().getOrgid(),this.getModel().getWmsProjectMasterDto().getDpDeptId()));
				} else {
					this.getModel().setBudgetList(ApplicationContextProvider.getApplicationContext().getBean(SecondaryheadMasterService.class).getSecondaryHeadcodesForWorks(
											UserSession.getCurrent().getOrganisation().getOrgid()));
				}
            }
        } else {
        	this.getModel().setCpdMode(null);
        }
        model.addAttribute(MainetConstants.BUDGET_PROJECTED_EXPENDITURE_MASTER.FIELD_MASTER_ITEMS,
				tbAcFieldMasterService.getFieldMasterLastLevels(UserSession.getCurrent().getOrganisation().getOrgid()));
        return new ModelAndView(MainetConstants.WorksManagement.ADD_PROJECT_MASTER, MainetConstants.FORM_NAME,
                this.getModel());
    }

    @RequestMapping(method = RequestMethod.POST, params = MainetConstants.WorksManagement.OPEN_SCHEME_MASTER)
    public ModelAndView openSchemeMasterForm(final HttpServletRequest request) {
        bindModel(request);

        request.getSession().setAttribute(MainetConstants.WorksManagement.SAVEMODE, MainetConstants.MENU.P);

        return new ModelAndView(MainetConstants.WorksManagement.REDIRECT_SCHEME);
    }

    @ResponseBody
    @RequestMapping(params = MainetConstants.WorksManagement.DELETE_PROJECT_MASTER, method = RequestMethod.POST)
    public boolean deleteProjectMasterData(@RequestParam(MainetConstants.WorksManagement.PROJ_ID) final Long projId) {

        boolean isdeleted = true; // to check whether project is associated to any Works or Not

        boolean status = iProjectMasterService.checkProjectWorkAssociation(projId,
                UserSession.getCurrent().getOrganisation().getOrgid());
        if (status == false) {
            iProjectMasterService.deleteProjectByProjId(projId);
            for (WmsProjectMasterDto wmsProjectMasterDto : this.getModel().getProjectMasterList()) {
                if (wmsProjectMasterDto.getProjId().longValue() == projId.longValue()) {
                    this.getModel().getProjectMasterList().remove(wmsProjectMasterDto);
                    break;
                }
            }
        } else {
            isdeleted = false;
        }

        return isdeleted;
    }

    @ResponseBody
    @RequestMapping(params = MainetConstants.WorksManagement.GET_PROJECT_MASTER_GRID_DATA, method = RequestMethod.POST)
    public List<WmsProjectMasterDto> getProjectMasterList(
            @RequestParam(MainetConstants.WorksManagement.SOURCE_CODE) final Long sourceCode,
            @RequestParam(MainetConstants.WorksManagement.SOURCE_NAME) final Long sourceName,
            @RequestParam(MainetConstants.WorksManagement.DEPT_ID) final Long dpDeptId,
            @RequestParam(MainetConstants.WorksManagement.PROJ_STATUS) final Long projStatus,
            @RequestParam(MainetConstants.WorksManagement.PROJ_CODE) final String projCode,
            @RequestParam(MainetConstants.WorksManagement.PROJECT_NAME) final String projectName) {

        List<WmsProjectMasterDto> projectMasterDtos = iProjectMasterService.getProjectMasterList(sourceCode, sourceName,
                projectName, projCode, UserSession.getCurrent().getOrganisation().getOrgid(), dpDeptId, projStatus);
        this.getModel().prepareProjectMasterGridData(projectMasterDtos);
        return projectMasterDtos;
    }

    @RequestMapping(method = RequestMethod.POST, params = MainetConstants.WorksManagement.FILE_COUNT_UPLOAD)
    public ModelAndView fileCountUpload(final HttpServletRequest request) {
        bindModel(request);
        this.getModel().getFileCountUpload().clear();

        for (final Map.Entry<Long, Set<File>> entry : FileUploadUtility.getCurrent().getFileMap().entrySet()) {
            this.getModel().getFileCountUpload().add(entry.getKey());
        }

        int fileCount = FileUploadUtility.getCurrent().getFileMap().entrySet().size();
        this.getModel().getFileCountUpload().add(fileCount + 1L);

        List<DocumentDetailsVO> attachments = new ArrayList<>();
        for (int i = 0; i <= this.getModel().getAttachments().size(); i++)
            attachments.add(new DocumentDetailsVO());

        for (final Map.Entry<Long, Set<File>> entry : FileUploadUtility.getCurrent().getFileMap().entrySet()) {

            attachments.get(entry.getKey().intValue()).setDoc_DESC_ENGL(
                    this.getModel().getAttachments().get(entry.getKey().intValue()).getDoc_DESC_ENGL());
        }

        if (attachments.get(this.getModel().getAttachments().size()).getDoc_DESC_ENGL() == null)
            attachments.get(this.getModel().getAttachments().size()).setDoc_DESC_ENGL(MainetConstants.BLANK);
        else {
            DocumentDetailsVO ob = new DocumentDetailsVO();
            ob.setDoc_DESC_ENGL(MainetConstants.BLANK);
            attachments.add(ob);
        }

        this.getModel().setAttachments(attachments);

        return new ModelAndView(MainetConstants.WorksManagement.PROJECT_FILE_UPLOAD, MainetConstants.FORM_NAME,
                this.getModel());
    }

    /**
     * method is used for view Project Master Data
     * 
     * @param projId
     * @param request
     * @return ModelAndView
     */
    @ResponseBody
    @RequestMapping(params = MainetConstants.WorksManagement.VIEW_PROJMASDATA, method = { RequestMethod.POST,
            RequestMethod.GET })
    public ModelAndView viewProjectMasterData(final Model model,@RequestParam(MainetConstants.WorksManagement.PROJ_ID) final Long projId,
            final HttpServletRequest request) {
        Long orgId = UserSession.getCurrent().getOrganisation().getOrgid();

        if (request.getSession().getAttribute(MainetConstants.WorksManagement.SAVEMODE) == null
                || !request.getSession().getAttribute(MainetConstants.WorksManagement.SAVEMODE).toString()
                        .equals(MainetConstants.WorksManagement.SM)) {
            this.getModel().setSaveMode(MainetConstants.WorksManagement.VIEW);
        } else {
            this.getModel().setSaveMode(MainetConstants.WorksManagement.SM);
            request.getSession().removeAttribute(MainetConstants.WorksManagement.SAVEMODE);
        }

        WmsProjectMasterDto projectMasterDto = iProjectMasterService.getProjectMasterByProjId(projId);
        if (projectMasterDto.getProjStartDate()!=null && StringUtils.isNotEmpty(projectMasterDto.getProjStartDate().toString()) ) {
            projectMasterDto.setStartDateDesc(
                    new SimpleDateFormat(MainetConstants.DATE_FORMAT).format(projectMasterDto.getProjStartDate()));
        }
        if (projectMasterDto.getProjEndDate()!=null && StringUtils.isNotEmpty(projectMasterDto.getProjEndDate().toString()) ) {
            projectMasterDto.setEndDateDesc(
                    new SimpleDateFormat(MainetConstants.DATE_FORMAT).format(projectMasterDto.getProjEndDate()));
        }
        if (projectMasterDto.getRsoDate()!=null && StringUtils.isNotEmpty(projectMasterDto.getRsoDate().toString())) {
            projectMasterDto.setRsoDateDesc(
                    new SimpleDateFormat(MainetConstants.DATE_FORMAT).format(projectMasterDto.getRsoDate()));
        }
        if (projectMasterDto != null) {
            List<WorkDefinationYearDetDto> definationYearDetDto=iProjectMasterService.getYearDetailByProjectId(projectMasterDto, projectMasterDto.getOrgId());
            if(!definationYearDetDto.isEmpty()) {
            	projectMasterDto.setYearDtos(definationYearDetDto);  
            }
        }
        
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
            if (this.getModel().getCpdMode().equals(MainetConstants.FlagL)) {
            	this.getModel().setBudgetList(ApplicationContextProvider.getApplicationContext().getBean(SecondaryheadMasterService.class)
                        .getSecondaryHeadcodesForWorks(UserSession.getCurrent().getOrganisation().getOrgid()));
            }
        } else {
        	this.getModel().setCpdMode(null);
        }
        this.getModel().setDepartmentsList(
                iTbDepartmentService.findMappedDepartments(UserSession.getCurrent().getOrganisation().getOrgid()));
        this.getModel().setWmsProjectMasterDto(projectMasterDto);

        /*
         * final List<AttachDocs> attachDocs = attachDocsService
         * .findByCode(UserSession.getCurrent().getOrganisation().getOrgid(), projectMasterDto.getProjCode());
         * this.getModel().setAttachDocsList(attachDocs);
         */

        List<WorkDefinitionDto> definitionDtoList = ApplicationContextProvider.getApplicationContext()
                .getBean(WorkDefinitionService.class).findAllWorkDefinationByProjId(orgId, projId);
        if (definitionDtoList != null) {
            for (WorkDefinitionDto dto : definitionDtoList) {
                if (StringUtils.isNotEmpty(dto.getWorkStatus())) {
                    if (dto.getWorkStatus().equals(MainetConstants.FlagA)) {
                        dto.setWorkStatus(MainetConstants.TASK_STATUS_APPROVED);
                    }
                    if (dto.getWorkStatus().equals(MainetConstants.FlagP)) {
                        dto.setWorkStatus(MainetConstants.TASK_STATUS_PENDING);
                    }
                    if (dto.getWorkStatus().equals(MainetConstants.FlagI)) {
                        dto.setWorkStatus(MainetConstants.TASK_STATUS_TENDER_INITIATED);
                    }
                    if (dto.getWorkStatus().equals(MainetConstants.FlagD)) {
                        dto.setWorkStatus(MainetConstants.TASK_STATUS_DRAFT);
                    }
                    if (dto.getWorkStatus().equals(MainetConstants.FlagR)) {
                        dto.setWorkStatus(MainetConstants.TASK_STATUS_REJECTED);
                    }
                    if (dto.getWorkStatus().equals(MainetConstants.FlagC)) {
                        dto.setWorkStatus(MainetConstants.TASK_STATUS_COMPLETED);
                    }
                    if (dto.getWorkStatus().equals(MainetConstants.WorksManagement.FLAG_T)) {
                        dto.setWorkStatus(MainetConstants.WorksManagement.TENDER_GENERATED);
                    }
                    if (dto.getWorkStatus().equals(MainetConstants.WorksManagement.TECH_SANCTION)) {
                    	dto.setWorkStatus(MainetConstants.WorksManagement.TECH_SANC_APPROVE);
                    }
                    if (dto.getWorkStatus().equals(MainetConstants.WorksManagement.ADMIN_SANCTION)) {
                    	dto.setWorkStatus(MainetConstants.WorksManagement.ADMIN_SANC_APPROVE);
                    }

                }
            }
        }

        this.getModel().setWorkDefDtoList(definitionDtoList);
        model.addAttribute(MainetConstants.BUDGET_PROJECTED_EXPENDITURE_MASTER.FIELD_MASTER_ITEMS,
				tbAcFieldMasterService.getFieldMasterLastLevels(UserSession.getCurrent().getOrganisation().getOrgid()));

        return new ModelAndView(MainetConstants.WorksManagement.VIEW_PROJ, MainetConstants.FORM_NAME, this.getModel());

    }

    /**
     * method is used for get Contract Id With WorkId
     * 
     * @param workId
     * @return contractId
     */
    @ResponseBody
    @RequestMapping(params = MainetConstants.WorksManagement.GET_CONTID, method = RequestMethod.POST)
    public Long getContractIdWithWorkId(@RequestParam(MainetConstants.WorksManagement.WORK_ID) final Long workId) {
        Long contractId = 0L;

        TenderWorkDto tenderWorkDto = ApplicationContextProvider.getApplicationContext().getBean(TenderInitiationService.class)
                .findContractByWorkId(workId);
        if (tenderWorkDto != null) {
            if (tenderWorkDto.getContractId() != null && tenderWorkDto.getContractId() != 0) {
                contractId = tenderWorkDto.getContractId();
            }
        }

        return contractId;
    }

    /**
     * method is used for show Current page
     * 
     * @param httpServletRequest
     * @return ModelAndView
     */
    @RequestMapping(params = MainetConstants.WorksManagement.SHOW_CURRENTPROJ, method = { RequestMethod.POST,
            RequestMethod.GET })
    public ModelAndView showCurrentpage(HttpServletRequest httpServletRequest) {
        getModel().bind(httpServletRequest);
        final WmsProjectMasterModel projectModel = this.getModel();
        return new ModelAndView(MainetConstants.WorksManagement.VIEW_PROJ, MainetConstants.FORM_NAME, projectModel);
    }

    /**
     * Used to get Scheme Name and Codes By Scheme Fund Source
     * 
     * @param sourceName
     * @param projId
     * @return List<LookUp>
     */
    @ResponseBody
    @RequestMapping(params = MainetConstants.WorksManagement.GET_SCHEMEFUND, method = RequestMethod.POST)
    public List<LookUp> getSchemeNames(
            @RequestParam(MainetConstants.WorksManagement.SOURCE_ID) Long sourceId) {
        List<LookUp> sourceNames = new ArrayList<>();
        List<LookUp> obj = CommonMasterUtility.getNextLevelData(MainetConstants.WorksManagement.SSF, 2,
                UserSession.getCurrent().getOrganisation().getOrgid());
        for (LookUp lookUp : obj) {
            if (lookUp.getLookUpParentId() == sourceId.longValue()) {
                sourceNames.add(lookUp);
            }
        }
        return sourceNames;

    }
    
    @ResponseBody
    @RequestMapping(params = "getBudgetHeadDetails", method = RequestMethod.POST)
    public VendorBillApprovalDTO checkBudgetHeadDetails(@RequestParam("sacHeadId") final Long sacHeadId,
            @RequestParam("dpDeptId") final Long dpDeptId, @RequestParam("faYearId") final Long faYearId,
            @RequestParam("yeBugAmount") final BigDecimal yeBugAmount,@RequestParam("fieldId") final Long fieldId) {
        Long orgId = UserSession.getCurrent().getOrganisation().getOrgid();
        VendorBillApprovalDTO budgetHeadDTO = new VendorBillApprovalDTO();
        budgetHeadDTO.setBillAmount(yeBugAmount);
        budgetHeadDTO.setDepartmentId(dpDeptId);
        budgetHeadDTO.setFaYearid(faYearId);
        budgetHeadDTO.setBudgetCodeId(sacHeadId);
        budgetHeadDTO.setOrgId(orgId);
        budgetHeadDTO.setFieldId(fieldId);
        VendorBillApprovalDTO dto = workDefinitionService.getBudgetExpenditureDetails(budgetHeadDTO);
        if (dto != null) {
            dto.setBillAmount(yeBugAmount.setScale(2, RoundingMode.UP));
            dto.setAuthorizationStatus(MainetConstants.FlagY);
            if (dto.getInvoiceAmount().subtract(dto.getSanctionedAmount()).compareTo(yeBugAmount) < 0) {
                dto.setDisallowedRemark(MainetConstants.FlagY);
            }
        } else {
            dto = new VendorBillApprovalDTO();
            dto.setAuthorizationStatus(MainetConstants.FlagN);
        }

        return dto;
    }
    
    
    @RequestMapping(method = RequestMethod.POST, params = "getBudgetHead")
    public @ResponseBody Map<Long, String> getBudget(@RequestParam("dpDeptId") final Long dpDeptId,@RequestParam("fieldId") final Long fieldId,
    		final HttpServletRequest request) {
        bindModel(request);
        Map<Long, String> budgetMap = new HashMap<>();
        LookUp lookup=null;
     	Organisation org = UserSession.getCurrent().getOrganisation();
     	VendorBillApprovalDTO budgetHeadDTO = new VendorBillApprovalDTO();
        budgetHeadDTO.setOrgId(org.getOrgid());
        budgetHeadDTO.setFieldId(fieldId);
     	int langId = UserSession.getCurrent().getLanguageId();
         try {
         	 lookup = CommonMasterUtility.getLookUpFromPrefixLookUpValue(AccountConstants.ASD.getValue(),
                     AccountPrefix.AIC.toString(),langId,org);
         }catch(Exception e) {
         	return budgetMap;
         }
         if(lookup!=null && lookup.getOtherField().equals("Y")) {
        	 budgetHeadDTO.setDepartmentId(dpDeptId);
        	 budgetMap=iProjectMasterService.getBudgetExpenditure(budgetHeadDTO);
			} 
         else {
        	 budgetMap=iProjectMasterService.getBudgetExpenditure(budgetHeadDTO);
         }
       return budgetMap;
        
    }

}
