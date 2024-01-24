package com.abm.mainet.workManagement.ui.controller;

import java.io.File;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.abm.mainet.common.constant.MainetConstants;
import com.abm.mainet.common.constant.MainetConstants.AccountConstants;
import com.abm.mainet.common.constant.MainetConstants.BUG_HEAD_OPENING_BALANCE_MASTER;
import com.abm.mainet.common.constant.PrefixConstants.AccountPrefix;
import com.abm.mainet.common.constant.ServiceEndpoints;
import com.abm.mainet.common.domain.Organisation;
import com.abm.mainet.common.exception.FrameworkException;
import com.abm.mainet.common.integration.acccount.domain.AccountHeadSecondaryAccountCodeMasterEntity;
import com.abm.mainet.common.integration.acccount.dto.VendorBillApprovalDTO;
import com.abm.mainet.common.integration.acccount.service.SecondaryheadMasterService;
import com.abm.mainet.common.integration.dms.domain.AttachDocs;
import com.abm.mainet.common.integration.dms.fileUpload.FileUploadUtility;
import com.abm.mainet.common.integration.dms.service.IAttachDocsService;
import com.abm.mainet.common.integration.dms.service.IFileUploadService;
import com.abm.mainet.common.integration.dto.CommonProposalDTO;
import com.abm.mainet.common.integration.dto.DocumentDetailsVO;
import com.abm.mainet.common.integration.dto.RequestDTO;
import com.abm.mainet.common.master.dto.TbDepartment;
import com.abm.mainet.common.master.dto.TbFinancialyear;
import com.abm.mainet.common.master.dto.TbLocationMas;
import com.abm.mainet.common.master.service.TbDepartmentService;
import com.abm.mainet.common.master.service.TbFinancialyearService;
import com.abm.mainet.common.service.ILocationMasService;
import com.abm.mainet.common.ui.controller.AbstractFormController;
import com.abm.mainet.common.utility.ApplicationContextProvider;
import com.abm.mainet.common.utility.CommonMasterUtility;
import com.abm.mainet.common.utility.LookUp;
import com.abm.mainet.common.utility.RestClient;
import com.abm.mainet.common.utility.UserSession;
import com.abm.mainet.common.utility.Utility;
import com.abm.mainet.common.workflow.service.IWorkFlowTypeService;
import com.abm.mainet.workManagement.dto.WmsProjectMasterDto;
import com.abm.mainet.workManagement.dto.WorkDefinationYearDetDto;
import com.abm.mainet.workManagement.dto.WorkDefinitionDto;
import com.abm.mainet.workManagement.service.WmsProjectMasterService;
import com.abm.mainet.workManagement.service.WorkDefinitionService;
import com.abm.mainet.workManagement.ui.model.WorkDefinitionModel;

/**
 * Object: this controller is used for work definition master.
 * 
 * @author hiren.poriya
 * @Since 08-Feb-2018
 */
@Controller
@RequestMapping(MainetConstants.WorkDefination.WORK_DEF_HTML)
public class WorkDefinationController extends AbstractFormController<WorkDefinitionModel> {
    private static final String EXCEPTION_IN_FINANCIAL_YEAR_DETAIL = "Exception while getting financial year Details :";

    private static final Logger LOGGER = Logger.getLogger(WorkDefinationController.class);

    @Autowired
    private WorkDefinitionService workDefinitionService;

    @Autowired
    private WmsProjectMasterService projectMasterService;

    @Autowired
    TbDepartmentService tbdepartmentservice;
    
    @Autowired
    private IFileUploadService fileUpload;
    
    @Autowired
    private ILocationMasService locationMasService;

    /**
     * Default index method for search, edit and add new work definition.
     * 
     * @param httpServletRequest
     * @return return home page of work definition master.
     */
    @RequestMapping(method = { RequestMethod.POST, RequestMethod.GET })
    public ModelAndView index(final HttpServletRequest httpServletRequest) {
        sessionCleanup(httpServletRequest);

        ApplicationContextProvider.getApplicationContext().getBean(IFileUploadService.class).sessionCleanUpForFileUpload();
        WorkDefinitionModel model = this.getModel();
        this.getModel().setCommonHelpDocs("WmsWorkDefinationMaster.html");

        model.setDefDtoList(workDefinitionService
                .findAllWorkDefinitionsByOrgId(UserSession.getCurrent().getOrganisation().getOrgid()));
        model.setProjectMasterList(ApplicationContextProvider.getApplicationContext().getBean(WmsProjectMasterService.class)
                .getActiveProjectMasterListByOrgId(UserSession.getCurrent().getOrganisation().getOrgid()));

        populateModel(model);
        return defaultResult();
    }

    /**
     * this method is used to Create new work definition or Edit existing work definition details
     * 
     * @param request
     * @param workId
     * @return work definition create for or work definition edit form.
     */
    @SuppressWarnings("unchecked")
    @RequestMapping(params = MainetConstants.Common_Constant.FORM, method = RequestMethod.POST)
    public ModelAndView workDefinationCreate(final HttpServletRequest request,
            @RequestParam(value = MainetConstants.WorkDefination.WORK_ID, required = false) Long workId) {
        WorkDefinitionModel model = this.getModel();
        CommonProposalDTO prosalDto = new CommonProposalDTO();
        prosalDto.setOrgId(UserSession.getCurrent().getOrganisation().getOrgid());
        List<WorkDefinitionDto> workDefDto = workDefinitionService
                .findAllWorkDefinitionsByOrgId(UserSession.getCurrent().getOrganisation().getOrgid());
        if (!workDefDto.isEmpty()) {
            Long workid = workDefDto.get(0).getWorkId();
            WorkDefinitionDto workDef = workDefinitionService.findAllWorkDefinitionById(workid);
            Long deptId = workDef.getDeptId();
            prosalDto.setProposalDepId(deptId);
            List<CommonProposalDTO> councilList = new ArrayList<>();
            ResponseEntity<?> response = null;
            try {
                 response = RestClient.callRestTemplateClient(prosalDto, ServiceEndpoints.COUNCIL_PROPOSAL_DETAILS);//#159581

                if ((response != null) && (response.getStatusCode() == HttpStatus.OK)) {
                    LOGGER.info("Council Proposal Details done successfully ::");
                    councilList = (List<CommonProposalDTO>) response.getBody();
                    model.setCommonproposalList(councilList);
                } else {
                    LOGGER.error("get Council proposal Details failed due to :"
                            + (response != null ? response.getBody() : MainetConstants.BLANK));

                }
            } catch (Exception ex) {
                LOGGER.error("Exception occured while fetching Council Proposal Details : " + ex);
               // throw new FrameworkException(ex);
            }
        }

        this.getModel().setFormMode(MainetConstants.MODE_CREATE);
        populateModel(model);
        return new ModelAndView(MainetConstants.WorkDefination.WORK_DEF_FORM, MainetConstants.FORM_NAME, model);
    }

    /**
     * this method is used to update
     * 
     * @param request
     * @param workId
     * @return work definition create for or work definition edit form.
     */
    @RequestMapping(params = MainetConstants.WorksManagement.FORM_FOR_UPDATE, method = RequestMethod.POST)
    public ModelAndView workDefinationUpdate(final HttpServletRequest request,
            @RequestParam(value = MainetConstants.WorkDefination.WORK_ID) Long workId,
            @RequestParam(value = MainetConstants.Common_Constant.TYPE, required = false) String type,
            @RequestParam(value = MainetConstants.WorkDefination.WORK_COME_FROM, required = false) String comeFrom) {
        WorkDefinitionModel model = this.getModel();
        model.setFormMode(type);
        if(Utility.isEnvPrefixAvailable(UserSession.getCurrent().getOrganisation(), MainetConstants.ENV_ASCL)){
        	if(null!=comeFrom)
        		this.getModel().setWorkBackHandle(comeFrom);
        }
        	

        WorkDefinitionDto defDetails = workDefinitionService.findAllWorkDefinitionById(workId);
        this.getModel().setWorkStatus(defDetails.getWorkStatus());
        List<WorkDefinationYearDetDto> definationYearDetDto=workDefinitionService.getYearDetailByWorkId(defDetails, defDetails.getOrgId());
        if(!definationYearDetDto.isEmpty()) {
        	defDetails.setYearDtos(definationYearDetDto);        	
        }
        final List<AttachDocs> attachDocs = ApplicationContextProvider.getApplicationContext().getBean(IAttachDocsService.class)
                .findByCode(UserSession.getCurrent().getOrganisation().getOrgid(), defDetails.getWorkcode());
        model.setWmsDto(defDetails);
        model.setAttachDocsList(attachDocs);
        populateModel(model);
        Organisation org = UserSession.getCurrent().getOrganisation();
        LookUp gisFlag = CommonMasterUtility.getValueFromPrefixLookUp("GIS",
                "MLI", org);
        getModel().setGisValue(gisFlag.getOtherField());
        String GISURL=ServiceEndpoints.GisItegration.GIS_URI+ServiceEndpoints.GisItegration.LAYER_NAME_POINT;
        String appendableUrl=null;
        String newUri=null;
        List<LookUp> lookup = model.getLevelData("ACL");
        if(lookup!=null && !lookup.isEmpty()) {
          for(LookUp look:lookup) {
        	if(look.getOtherField().equals("IMO")) {
        	  if(look.getLookUpId()==defDetails.getWorkCategory()) {
        		  if(look.getLookUpCode().equals(MainetConstants.WorkDefination.WORK_CATAGORY_BUILDING))
        		     appendableUrl=MainetConstants.WorkDefination.GIS_LAYER_NAME_POLYGONE;
        		  if(look.getLookUpCode().equals(MainetConstants.WorkDefination.WORK_CATAGORY_LAND))
            		 appendableUrl=MainetConstants.WorkDefination.GIS_LAYER_NAME_POINT;
        		  if(look.getLookUpCode().equals(MainetConstants.WorkDefination.WORK_CATAGORY_ROAD) || look.getLookUpCode().equals(MainetConstants.WorkDefination.WORK_CATAGORY_BRIDGE)
    				  || look.getLookUpCode().equals(MainetConstants.WorkDefination.WORK_CATAGORY_WATER) || look.getLookUpCode().equals(MainetConstants.WorkDefination.WORK_CATAGORY_DRAINAGE))//road
            		 appendableUrl=MainetConstants.WorkDefination.GIS_LAYER_NAME_LINE;
         	  }
        	} 
           }
        }
        if(GISURL!=null && gisFlag.getOtherField().equals(MainetConstants.Common_Constant.YES)) {
      	  newUri=GISURL.replaceAll(MainetConstants.WorkDefination.GIS_LAYER_NAME_POINT, appendableUrl);
      	  model.setgISUri(newUri);
        }
        if (Utility.isEnvPrefixAvailable(UserSession.getCurrent().getOrganisation(), MainetConstants.ENV_TSCL)) {
        Long codId1=(model.getWmsDto().getWardZoneDto().get(0).getCodId1());
    	model.getWmsDto().setProjId(model.getWmsDto().getProjId());
        Long fieldId=locationMasService.getFieldIdWithWard(model.getWmsDto().getDeptId(), codId1, UserSession.getCurrent().getOrganisation().getOrgid());
        WmsProjectMasterDto projectMasterDto = projectMasterService.getProjectMasterByProjId(model.getWmsDto().getProjId());
        List<WorkDefinationYearDetDto> definationYearDetDto1=projectMasterService.getYearDetailByProjectId(projectMasterDto, UserSession.getCurrent().getOrganisation().getOrgid());
           if(fieldId!=null) {
        	   if(!definationYearDetDto1.isEmpty()) {
            List<TbFinancialyear> list2=new ArrayList<>();
            model.getFaYears().forEach(dept -> {
            	list2.add(dept);
            });
            List<AccountHeadSecondaryAccountCodeMasterEntity> bugList=new ArrayList<>();
            model.getBudgetList().forEach(bug -> {
            	bugList.add(bug);
            });
				model.getFaYears().clear();
				model.getBudgetList().clear();
				if (list2 != null && !list2.isEmpty()) {
					definationYearDetDto1.forEach(list -> {
						list2.forEach(finYearTemp -> {
							if (list.getFaYearId().equals(finYearTemp.getFaYear()) && list.getFieldId() !=null && list.getFieldId().equals(fieldId)) {
								if(!(model.getFaYears().contains(finYearTemp)))
								model.getFaYears().add(finYearTemp);
							}
						});
						bugList.forEach(bugTemp -> {
							if (list.getSacHeadId().equals(bugTemp.getSacHeadId())  && list.getFieldId() !=null && list.getFieldId().equals(fieldId)) {
								if(!(model.getBudgetList().contains(bugTemp)))
								model.getBudgetList().add(bugTemp);
							}
						});
					});
				}
        }
            else {
            	LookUp lookupNew=null;
             	int langId = UserSession.getCurrent().getLanguageId();
             	Map<Long, String> budgetMap = new HashMap<>();
             	List<AccountHeadSecondaryAccountCodeMasterEntity> budgetList = new ArrayList<>();
            	 VendorBillApprovalDTO budgetHeadDTO = new VendorBillApprovalDTO();
                 budgetHeadDTO.setOrgId(org.getOrgid());
                 budgetHeadDTO.setFieldId(fieldId);
                 try {
                	 lookupNew = CommonMasterUtility.getLookUpFromPrefixLookUpValue(AccountConstants.ASD.getValue(),
                             AccountPrefix.AIC.toString(),langId,org);
                 }catch(Exception e) {
                 	return null;
                 }
                 if(lookupNew!=null && lookupNew.getOtherField().equals("Y")) {
                	 budgetHeadDTO.setDepartmentId(model.getWmsDto().getDeptId());
                	 budgetMap=projectMasterService.getBudgetExpenditure(budgetHeadDTO);
                	 for (Map.Entry<Long, String> entry : budgetMap.entrySet()) {
                		 AccountHeadSecondaryAccountCodeMasterEntity dto=new AccountHeadSecondaryAccountCodeMasterEntity();
                		 String a= String.valueOf(entry.getKey());
                		 dto.setSacHeadId(Long.valueOf(a));
                		 dto.setAcHeadCode(entry.getValue());
                		 budgetList.add(dto);
                	 }
                 }
                 else {
                	 budgetMap=projectMasterService.getBudgetExpenditure(budgetHeadDTO);
                	 for (Map.Entry<Long, String> entry : budgetMap.entrySet()) {
                		 AccountHeadSecondaryAccountCodeMasterEntity dto=new AccountHeadSecondaryAccountCodeMasterEntity();
                		 String a= String.valueOf(entry.getKey());
                		 dto.setSacHeadId(Long.valueOf(a));
                		 dto.setAcHeadCode(entry.getValue());
                		 budgetList.add(dto);
                	 }
                 }
                 this.getModel().setBudgetList(budgetList);
            }
          } 
        }
        return new ModelAndView(MainetConstants.WorkDefination.WORK_DEF_FORM, MainetConstants.FORM_NAME, model);
    }

    @ResponseBody
    @RequestMapping(params = MainetConstants.WorkDefination.FILTER_RECORDS, method = RequestMethod.POST)
    public List<WorkDefinitionDto> filterWorkDefRecords(final HttpServletRequest httpServletRequest) {
        WorkDefinitionModel workDefModel = this.getModel();
        workDefModel.bind(httpServletRequest);
        return workDefinitionService.filterWorkDefRecords(UserSession.getCurrent().getOrganisation().getOrgid(),
                workDefModel.getWmsDto());
    }

    /**
     * populate common form details for Work definition form
     * 
     * @param model
     */
    private void populateModel(WorkDefinitionModel model) {
        ResponseEntity<?> responseEntity = null;
        Organisation org = UserSession.getCurrent().getOrganisation();
        Long orgId = UserSession.getCurrent().getOrganisation().getOrgid();

        model.setDepartmentsList(ApplicationContextProvider.getApplicationContext().getBean(TbDepartmentService.class)
                .findMappedDepartments(orgId));

        List<TbDepartment> departMent = ApplicationContextProvider.getApplicationContext().getBean(IWorkFlowTypeService.class)
                .getAllWorkflowDefinedDepartmentsByOrgId(
                        UserSession.getCurrent().getOrganisation().getOrgid(), MainetConstants.WorksManagement.WOA);
        if (!departMent.isEmpty()) {
            departMent.forEach(dept -> {
                if (UserSession.getCurrent().getLanguageId() == 1) {
                    dept.setDpDeptdesc(dept.getDpDeptdesc());
                } else if ((UserSession.getCurrent().getLanguageId() == 2)) {
                    dept.setDpDeptdesc(dept.getDpNameMar());
                }
            });
        }
        model.setSanctionDeptsList(departMent);

        model.setLocList(
                ApplicationContextProvider.getApplicationContext().getBean(ILocationMasService.class)
                        .fillAllActiveLocationByOrgId(org.getOrgid()));
        final List<TbFinancialyear> finYearList = ApplicationContextProvider.getApplicationContext()
                .getBean(TbFinancialyearService.class)
                .findAllFinancialYearByOrgId(org);
        model.getFaYears().clear();
        if (finYearList != null && !finYearList.isEmpty()) {
            finYearList.forEach(finYearTemp -> {
                try {
                    finYearTemp.setFaYearFromTo(Utility.getFinancialYearFromDate(finYearTemp.getFaFromDate()));
                    model.getFaYears().add(finYearTemp);
                } catch (Exception ex) {
                    throw new FrameworkException(EXCEPTION_IN_FINANCIAL_YEAR_DETAIL + ex);
                }
            });
            Comparator<TbFinancialyear> comparing = Comparator.comparing(TbFinancialyear::getFaYearFromTo,
                    Comparator.reverseOrder());
            Collections.sort(model.getFaYears(), comparing);
        }

        LookUp defaultVal = CommonMasterUtility.getDefaultValue(BUG_HEAD_OPENING_BALANCE_MASTER.SLI_PREFIX_VALUE);
        if (defaultVal != null) {
            model.setCpdMode(CommonMasterUtility.getDefaultValue(BUG_HEAD_OPENING_BALANCE_MASTER.SLI_PREFIX_VALUE)
                    .getLookUpCode());
            if (model.getCpdMode().equals(MainetConstants.FlagL)) {
                model.setBudgetList(ApplicationContextProvider.getApplicationContext().getBean(SecondaryheadMasterService.class)
                        .getSecondaryHeadcodesForWorks(org.getOrgid()));
            }
        } else {
            model.setCpdMode(null);
        }

    }

    /**
     * used to file upload and deletion
     * 
     * @param request
     * @return file upload JSP with added or edited documents
     */
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
        return new ModelAndView(MainetConstants.WorkDefination.FILE_UPLOAD_COUNT, MainetConstants.FORM_NAME,
                this.getModel());
    }

    /**
     * SLI Prefix Check Live Mode or Setting Mode
     * @param httpServletRequest
     * @return default Value
     */
    @ResponseBody
    @RequestMapping(params = MainetConstants.WorkDefination.DEFAUL_SLI_CHECK, method = RequestMethod.POST)
    public String checkForDefaultSLI(final HttpServletRequest httpServletRequest) {
        String defaultVal = MainetConstants.Common_Constant.NO;
        LookUp lookUp = CommonMasterUtility.getDefaultValue(BUG_HEAD_OPENING_BALANCE_MASTER.SLI_PREFIX_VALUE);
        if (lookUp != null)
            defaultVal = MainetConstants.Common_Constant.YES;
        return defaultVal;
    }

    /**
     * getMapData
     * 
     * @return
     */
    @RequestMapping(params = "getMapData", method = { RequestMethod.POST }, produces = "application/json")
    @ResponseBody
    public List<Object> getMapData(final HttpServletRequest request, @RequestParam(required = true) Long locIdSt,
            @RequestParam(required = true) Long locIdEn) {
    	getModel().bind(request);
    	List<Object> data = new ArrayList<Object>();
    	if(getModel().getWmsDto().getLatitude()!=null && getModel().getWmsDto().getLongitude()!=null ) {
    		final String[] mapData = new String[] { getModel().getWmsDto().getWorkName(), getModel().getWmsDto().getLatitude(),
                    getModel().getWmsDto().getLongitude() };
            data.add(mapData);
    	}else {
        TbLocationMas locList;
        List<Long> ids = new ArrayList<>();
        ids.add(locIdSt);
        ids.add(locIdEn);

        for (Long id : ids) {
            locList = ApplicationContextProvider.getApplicationContext().getBean(ILocationMasService.class).findById(id);
            if (locList.getLatitude() != null && locList.getLongitude() != null) {
                final String[] mapData = new String[] { locList.getLocNameEng(), locList.getLatitude(),
                        locList.getLongitude() };
                data.add(mapData);
            }
        }
        }
        return data;
    }

    @ResponseBody
    @RequestMapping(params = "getBudgetHeadDetails", method = RequestMethod.POST)
    public VendorBillApprovalDTO checkBudgetHeadDetails(@RequestParam("sacHeadId") final Long sacHeadId,
            @RequestParam("projId") final Long projId, @RequestParam("faYearId") final Long faYearId,
            @RequestParam("yeBugAmount") final BigDecimal yeBugAmount,
            @RequestParam(value ="fieldId",required = false ) Long fieldId,
            @RequestParam("dpDeptId") final Long dpDeptId) {
        Long orgId = UserSession.getCurrent().getOrganisation().getOrgid();
        VendorBillApprovalDTO budgetHeadDTO = new VendorBillApprovalDTO();
        budgetHeadDTO.setBillAmount(yeBugAmount);
        budgetHeadDTO.setDepartmentId(dpDeptId);
        budgetHeadDTO.setFaYearid(faYearId);
        budgetHeadDTO.setBudgetCodeId(sacHeadId);
        budgetHeadDTO.setOrgId(orgId);
        Long fieldId1=null;
        if(fieldId!=null) {
        fieldId1=locationMasService.getFieldIdWithWard(dpDeptId, fieldId, UserSession.getCurrent().getOrganisation().getOrgid());
        budgetHeadDTO.setFieldId(fieldId1);
        }
        
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

    @ResponseBody
    @RequestMapping(params = MainetConstants.WorksManagement.SAVE_WORK_DEFINATION, method = RequestMethod.POST)
    public Map<String, Object> saveAndRedirectToWorkEsmt(HttpServletRequest httpServletRequest) {
        getModel().bind(httpServletRequest);
        this.getModel().saveForm();

        Map<String, Object> object = new LinkedHashMap<String, Object>();
        object.put("messageText", this.getModel().getSuccessMessage());
        object.put(MainetConstants.ERROR, this.getModel().getBindingResult().getAllErrors());

        object.put(MainetConstants.WorksManagement.WORK_ID, this.getModel().getWorkDefinitionDto().getWorkId());
        object.put(MainetConstants.WorksManagement.PROJ_ID, this.getModel().getWmsDto().getProjId());

        return object;

    }

    /**
     * check Financial year compulsory or not
     * @param projID
     * @return YES or NO
     */
    @RequestMapping(params = "checkSSFCode", method = RequestMethod.POST)
    public @ResponseBody String checkProjectToSchemeSourceCode(@RequestParam("projID") Long projID) {
        String success = MainetConstants.FlagN;
        WmsProjectMasterDto masterDto = projectMasterService.getProjectMasterByProjId(projID);
        if (masterDto != null) {
            LookUp lookUp = CommonMasterUtility.getHierarchicalLookUp(masterDto.getSchmSourceCode(),
                    UserSession.getCurrent().getOrganisation().getOrgid());
            if (lookUp.getLookUpCode().equals(MainetConstants.FlagU)) {
                success = MainetConstants.FlagY;
            }
        }
        return success;
    }
    
    @RequestMapping(params = "getLocationLatLong", method = { RequestMethod.POST }, produces = "application/json")
    @ResponseBody
    public String getLocationLatLong(final HttpServletRequest request, @RequestParam(required = true) Long locIdSt,
            @RequestParam(required = true) Long locIdEn) {
        StringBuilder error=new StringBuilder();
        TbLocationMas locList;
        List<Long> ids = new ArrayList<>();
        ids.add(locIdSt);
        ids.add(locIdEn);
        int count=0;

        for (Long id : ids) {
            locList = ApplicationContextProvider.getApplicationContext().getBean(ILocationMasService.class).findById(id);
            if (locList.getLatitude().isEmpty() && locList.getLongitude().isEmpty()) {
				if (count == 0) {
					error.append(getApplicationSession().getMessage("work.loc.start"));
					error.append(locList.getLocNameEng());
					count++;
				} else {
					error.append(" ");
					error.append(getApplicationSession().getMessage("work.loc.and"));
					error.append(locList.getLocNameEng());
				}
            }
        }
		if (count != 0) {
			error.append(" ");
			error.append(getApplicationSession().getMessage("work.loc.end"));
			return error.toString();
		}
		return error.toString();
    }
     
    
    @RequestMapping(params = "getBudgetHead", method = RequestMethod.POST)
    public ModelAndView getBudgetHead(final HttpServletRequest request) {
    	getModel().bind(request);
    	WorkDefinitionModel model = this.getModel();
    	Long codId1=(model.getWmsDto().getWardZoneDto().get(0).getCodId1());
    	model.getWmsDto().setProjId(model.getWmsDto().getProjId());
        WmsProjectMasterDto projectMasterDto = projectMasterService.getProjectMasterByProjId(model.getWmsDto().getProjId());
        Long fieldId=locationMasService.getFieldIdWithWard(model.getWmsDto().getDeptId(), codId1, UserSession.getCurrent().getOrganisation().getOrgid());
        if (projectMasterDto != null && fieldId!= null  ) {
            List<WorkDefinationYearDetDto> definationYearDetDto=projectMasterService.getYearDetailByProjectId(projectMasterDto, projectMasterDto.getOrgId());
            if(!definationYearDetDto.isEmpty()) {
            	 List<AccountHeadSecondaryAccountCodeMasterEntity> bugList1= new ArrayList<>();
            	definationYearDetDto.forEach(code -> {
            	AccountHeadSecondaryAccountCodeMasterEntity budget=(ApplicationContextProvider.getApplicationContext().getBean(SecondaryheadMasterService.class).findOne(UserSession.getCurrent().getOrganisation().getOrgid(),code.getSacHeadId()));
            	bugList1.add(budget);
            	 });
        		
            List<TbFinancialyear> list2=new ArrayList<>();
            final List<TbFinancialyear> finYearList = ApplicationContextProvider.getApplicationContext()
                    .getBean(TbFinancialyearService.class)
                    .findAllFinancialYearByOrgId(UserSession.getCurrent().getOrganisation());
            model.getFaYears().clear();
            if (finYearList != null && !finYearList.isEmpty()) {
                finYearList.forEach(finYearTemp -> {
                    try {
                        finYearTemp.setFaYearFromTo(Utility.getFinancialYearFromDate(finYearTemp.getFaFromDate()));
                        list2.add(finYearTemp);
                    } catch (Exception ex) {
                        throw new FrameworkException(EXCEPTION_IN_FINANCIAL_YEAR_DETAIL + ex);
                    }
                });
            }
				model.getFaYears().clear();
				model.getBudgetList().clear();
				if (list2 != null && !list2.isEmpty()) {
					definationYearDetDto.forEach(list -> {
						list2.forEach(finYearTemp -> {
							if (list.getFaYearId().equals(finYearTemp.getFaYear()) && list.getFieldId()!=null && list.getFieldId().equals(fieldId)) {
								if(!(model.getFaYears().contains(finYearTemp)))
								model.getFaYears().add(finYearTemp);
							}
						});
						
						bugList1.forEach(bugTemp -> {
							if (list.getFieldId()!=null && (list.getFieldId().equals(fieldId))) {
								if(!(model.getBudgetList().contains(bugTemp)))
								model.getBudgetList().add(bugTemp);
							}
						});
					});
				}
        }  else {
            	LookUp lookup=null;
             	Organisation org = UserSession.getCurrent().getOrganisation();
             	Map<Long, String> budgetMap = new HashMap<>();
             	List<AccountHeadSecondaryAccountCodeMasterEntity> budgetList = new ArrayList<>();
            	 VendorBillApprovalDTO budgetHeadDTO = new VendorBillApprovalDTO();
                 budgetHeadDTO.setOrgId(org.getOrgid());
                 budgetHeadDTO.setFieldId(fieldId);
             	int langId = UserSession.getCurrent().getLanguageId();
                 try {
                 	 lookup = CommonMasterUtility.getLookUpFromPrefixLookUpValue(AccountConstants.ASD.getValue(),
                             AccountPrefix.AIC.toString(),langId,org);
                 }catch(Exception e) {
                 	return null;
                 }
                 if(lookup!=null && lookup.getOtherField().equals("Y")) {
                	 budgetHeadDTO.setDepartmentId(model.getWmsDto().getDeptId());
                	 budgetMap=projectMasterService.getBudgetExpenditure(budgetHeadDTO);
                	 for (Map.Entry<Long, String> entry : budgetMap.entrySet()) {
                		 AccountHeadSecondaryAccountCodeMasterEntity dto=new AccountHeadSecondaryAccountCodeMasterEntity();
                		 String a= String.valueOf(entry.getKey());
                		 dto.setSacHeadId(Long.valueOf(a));
                		 dto.setAcHeadCode(entry.getValue());
                		 budgetList.add(dto);
                	 }
                 }
                 else {
                	 budgetMap=projectMasterService.getBudgetExpenditure(budgetHeadDTO);
                	 for (Map.Entry<Long, String> entry : budgetMap.entrySet()) {
                		 AccountHeadSecondaryAccountCodeMasterEntity dto=new AccountHeadSecondaryAccountCodeMasterEntity();
                		 String a= String.valueOf(entry.getKey());
                		 dto.setSacHeadId(Long.valueOf(a));
                		 dto.setAcHeadCode(entry.getValue());
                		 budgetList.add(dto);
                	 }
                 }
                 this.getModel().setBudgetList(budgetList);
            }
        }
        return new ModelAndView("WorkDefinationValidn", MainetConstants.FORM_NAME, model);
    }
    
    
    @RequestMapping(params = "LatLong", method = { RequestMethod.POST, RequestMethod.GET })
	public ModelAndView printCertificater(final HttpServletRequest httpServletRequest) {
		sessionCleanup(httpServletRequest);
		 fileUpload.sessionCleanUpForFileUpload();
		 WorkDefinitionModel model = this.getModel();
		 model.setProjectMasterList(ApplicationContextProvider.getApplicationContext().getBean(WmsProjectMasterService.class)
	                .getActiveProjectMasterListByOrgId(UserSession.getCurrent().getOrganisation().getOrgid()));
		
		ModelAndView mv = null;
		mv = new ModelAndView("LatLong", MainetConstants.FORM_NAME, getModel());
		return mv;

	}
    
    @ResponseBody
    @RequestMapping(params = "saveLatLong", method = RequestMethod.POST)
    public Map<String, Object> saveLatLong(HttpServletRequest httpServletRequest) {
        getModel().bind(httpServletRequest);
        
        List<DocumentDetailsVO> dto = getModel().getAttachments();
        getModel().setAttachments(ApplicationContextProvider.getApplicationContext().getBean(IFileUploadService.class)
                .setFileUploadMethod(new ArrayList<DocumentDetailsVO>()));
        int i = 0;
        for (final Map.Entry<Long, Set<File>> entry : FileUploadUtility.getCurrent().getFileMap().entrySet()) {
        	getModel().getAttachments().get(i).setDoc_DESC_ENGL(dto.get(entry.getKey().intValue()).getDoc_DESC_ENGL());
            i++;
        }
        RequestDTO requestDTO = new RequestDTO();
        requestDTO.setOrgId(UserSession.getCurrent().getOrganisation().getOrgid());
        requestDTO.setStatus(MainetConstants.FlagA);
        requestDTO.setDepartmentName(MainetConstants.WorksManagement.WORKS_MANAGEMENT);
        requestDTO.setUserId(UserSession.getCurrent().getEmployee().getEmpId());
        
        //this.getModel().saveForm();
		 ApplicationContextProvider.getApplicationContext().getBean(WorkDefinitionService.class).updateWorkDefinitionLatLong(getModel().getWmsDto(),
				 getModel().getAttachments(), requestDTO);

        Map<String, Object> object = new LinkedHashMap<String, Object>();
        object.put("messageText", "Data Saved Successfully");
        object.put(MainetConstants.ERROR, this.getModel().getBindingResult().getAllErrors());

        object.put(MainetConstants.WorksManagement.WORK_ID, this.getModel().getWorkDefinitionDto().getWorkId());
        object.put(MainetConstants.WorksManagement.PROJ_ID, this.getModel().getWmsDto().getProjId());

        return object;

    }
}
