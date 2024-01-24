package com.abm.mainet.water.ui.controller;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.CollectionUtils;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.abm.mainet.cfc.loi.dto.TbLoiMas;
import com.abm.mainet.cfc.loi.service.TbLoiMasService;
import com.abm.mainet.common.constant.MainetConstants;
import com.abm.mainet.common.constant.ServiceEndpoints;
import com.abm.mainet.common.domain.FinancialYear;
import com.abm.mainet.common.domain.ServiceMaster;
import com.abm.mainet.common.domain.TbCfcApplicationMstEntity;
import com.abm.mainet.common.integration.dms.dto.FileUploadDTO;
import com.abm.mainet.common.integration.dms.service.IFileUploadService;
import com.abm.mainet.common.integration.dto.DocumentDetailsVO;
import com.abm.mainet.common.master.service.TbFinancialyearService;
import com.abm.mainet.common.master.ui.validator.FileUploadServiceValidator;
import com.abm.mainet.common.service.ICFCApplicationMasterService;
import com.abm.mainet.common.service.ServiceMasterService;
import com.abm.mainet.common.ui.controller.AbstractFormController;
import com.abm.mainet.common.utility.ApplicationContextProvider;
import com.abm.mainet.common.utility.ApplicationSession;
import com.abm.mainet.common.utility.CommonMasterUtility;
import com.abm.mainet.common.utility.SeqGenFunctionUtility;
import com.abm.mainet.common.utility.UserSession;
import com.abm.mainet.common.utility.Utility;
import com.abm.mainet.water.domain.PlumberMaster;
import com.abm.mainet.water.dto.PlumberLicenseRequestDTO;
import com.abm.mainet.water.service.PlumberLicenseService;
import com.abm.mainet.water.ui.model.PlumberLicenseFormModel;

@Controller
@RequestMapping("/PlumberLicenseGenerator.html")
public class PlumberLicenceGeneratorController extends AbstractFormController<PlumberLicenseFormModel> {

    @Resource
    private SeqGenFunctionUtility seqGenFunctionUtility;

    @Autowired
    PlumberLicenseService plumberLicenseService;

    @Autowired
    ServiceMasterService iServiceMasterService;

    @Autowired
    private ICFCApplicationMasterService icfcApplicationMasterService;

    @Autowired
    private IFileUploadService fileUpload;
    
    @Autowired
	private TbLoiMasService tbLoiMasService;

    @RequestMapping(method = { RequestMethod.GET, RequestMethod.POST })
    public ModelAndView index(final HttpServletRequest httpServletRequest) {

        sessionCleanup(httpServletRequest);
        final PlumberLicenseFormModel model = getModel();
        model.getPlumberLicenseReqDTO().setFlag(MainetConstants.FlagY);
        FileUploadServiceValidator.getCurrent().sessionCleanUpForFileUpload();
        Long orgId = UserSession.getCurrent().getOrganisation().getOrgid();
        final ServiceMaster service = iServiceMasterService
                .getServiceMasterByShortCode(MainetConstants.WaterServiceShortCode.PlUMBER_LICENSE, orgId);
        model.setServiceId(service.getSmServiceId());
        model.setServiceCode(service.getSmShortdesc());
        model.setServiceName(service.getSmServiceName());
        model.setDeptName(service.getTbDepartment().getDpDeptdesc());
        model.setDeptId(service.getTbDepartment().getDpDeptid());
        this.getModel()
                .setLookUpList(CommonMasterUtility.getListLookup("ECN", UserSession.getCurrent().getOrganisation()));
        ModelAndView mv = null;
        mv = new ModelAndView("PlumberLicenceGenerator", MainetConstants.FORM_NAME, getModel());
        mv.addObject(MainetConstants.CommonConstants.COMMAND, getModel());
        return mv;
    }

    @RequestMapping(method = RequestMethod.POST, params = "getReciptDet")
    public ModelAndView searchWaterRecords(final HttpServletRequest httpServletRequest) {
        bindModel(httpServletRequest);
        ModelAndView mv = null;
        final PlumberLicenseFormModel model = getModel();
        PlumberLicenseRequestDTO dto = model.getPlumberLicenseReqDTO();
        dto.setDeptId(model.getDeptId());
        if (dto.getApplicationId() == null && dto.getReceiptNo() == null) {
            getModel().addValidationError(ApplicationSession.getInstance().getMessage("wwater.enter.data"));
        } else {
            TbCfcApplicationMstEntity cfcEntity = null;
            if (dto.getApplicationId() != null) {
                cfcEntity = icfcApplicationMasterService.getCFCApplicationByApplicationId(dto.getApplicationId(),
                        UserSession.getCurrent().getOrganisation().getOrgid());
                if (cfcEntity == null || !StringUtils.contains(cfcEntity.getApmAppRejFlag(), MainetConstants.FlagA)) {
                    getModel().addValidationError(ApplicationSession.getInstance().getMessage("wwater.Norecord"));

                } else {
                    final ServiceMaster service = iServiceMasterService.getServiceMaster(
                            cfcEntity.getTbServicesMst().getSmServiceId(),
                            UserSession.getCurrent().getOrganisation().getOrgid());
                    model.setServiceId(service.getSmServiceId());
                    model.setServiceCode(service.getSmShortdesc());
                    if (StringUtils.contains(service.getSmScrutinyChargeFlag(), MainetConstants.FlagA)
                            && CollectionUtils.isEmpty(plumberLicenseService.getReceiptDetails(dto))) {
                        getModel().addValidationError(ApplicationSession.getInstance().getMessage("wwater.Norecord"));
                    }
                }
            } else if (dto.getReceiptNo() != null) {
                List<Object[]> result = plumberLicenseService.getReceiptDetails(dto);
                if (result != null) {
                    Object[] receiptDet = result.get(0);
                    dto.setApplicationId(Long.valueOf(receiptDet[2].toString()));
                    final ServiceMaster service = iServiceMasterService.getServiceMaster(
                            Long.valueOf(receiptDet[3].toString()),
                            UserSession.getCurrent().getOrganisation().getOrgid());
                    model.setServiceId(service.getSmServiceId());
                    model.setServiceCode(service.getSmShortdesc());
                    dto.setAmount(Double.valueOf(receiptDet[0].toString()));

                } else {
                    getModel().addValidationError(ApplicationSession.getInstance().getMessage("wwater.Norecord"));

                }
            }
            if (!getModel().hasValidationErrors()) {
            	TbLoiMas tbLoiMas = tbLoiMasService.findloiByApplicationIdAndOrgId(dto.getApplicationId(), UserSession.getCurrent().getOrganisation().getOrgid());
            	boolean isPsclEnv = Utility.isEnvPrefixAvailable(UserSession.getCurrent().getOrganisation(), MainetConstants.ENV_PSCL);
            	boolean isAsclEnv = Utility.isEnvPrefixAvailable(UserSession.getCurrent().getOrganisation(), MainetConstants.ENV_ASCL);            	
            	if((isPsclEnv == true || isAsclEnv == true) && (tbLoiMas.getLoiPaid() == null || !tbLoiMas.getLoiPaid().equalsIgnoreCase("Y"))) {
               	 	getModel().addValidationError(ApplicationSession.getInstance().getMessage("water.loi"));
            	}else {
            		 dto.setFlag(MainetConstants.FlagN);
                     model.populateApplicationData(dto.getApplicationId());
                     Date curentDate = new Date();
                     Calendar calendar = Calendar.getInstance();
                     Integer year = calendar.get(Calendar.YEAR);
                     Integer nextYear = year + 1;

                     String toDate = MainetConstants.Common_Constant.NUMBER.THREE
                             + MainetConstants.Common_Constant.NUMBER.ONE + MainetConstants.WINDOWS_SLASH
                             + MainetConstants.Common_Constant.NUMBER.ZERO_THREE + MainetConstants.WINDOWS_SLASH + year;

                     String nextToDate = MainetConstants.Common_Constant.NUMBER.THREE
                             + MainetConstants.Common_Constant.NUMBER.ONE + MainetConstants.WINDOWS_SLASH
                             + MainetConstants.Common_Constant.NUMBER.ZERO_THREE + MainetConstants.WINDOWS_SLASH + nextYear;

                     Date validToDate = Utility.stringToDate(toDate);
                     Date nextYrValidToDate = Utility.stringToDate(nextToDate);
                     dto.setPlumLicFromDate(new Date());
                     dto.setLicFromDate(new SimpleDateFormat(MainetConstants.DATE_FORMAT).format(new Date()));
                     if (curentDate.before(validToDate)) {
                         dto.setPlumLicToDate(validToDate);
                     } else if (curentDate.after(validToDate)) {
                         dto.setPlumLicToDate(nextYrValidToDate);
                     }
                     dto.setLicToDate(new SimpleDateFormat(MainetConstants.DATE_FORMAT).format(dto.getPlumLicToDate()));
                     
                     if(tbLoiMas!= null) {
     	                dto.setAmount(tbLoiMas.getLoiAmount().longValue());
     	            }
                     model.setPlumberLicenseReqDTO(dto);
            		
            	}
            }
        }
        mv = new ModelAndView("PlumberLicenceGeneratorValidn", MainetConstants.CommonConstants.COMMAND, model);
        if (model.getBindingResult() != null) {
            mv.addObject(BindingResult.MODEL_KEY_PREFIX + MainetConstants.FORM_NAME, getModel().getBindingResult());
        }
        return mv;
    }

    @RequestMapping(params = "saveLicGenerator", method = RequestMethod.POST)
    public @ResponseBody Map<String, Object> saveLicGenerator(final HttpServletRequest httpServletRequest) {
        this.bindModel(httpServletRequest);
        Map<String, Object> object = new LinkedHashMap<String, Object>();
        PlumberLicenseFormModel model = this.getModel();
        PlumberLicenseRequestDTO dto = model.getPlumberLicenseReqDTO();
        Long orgId = UserSession.getCurrent().getOrganisation().getOrgid();
        if (!StringUtils.contains(model.getServiceCode(), "DPL")) {
            String licNo = seqGenFunctionUtility.generateSequenceNo(
                    MainetConstants.PlumberLicense.PLUM_MASTER_TABLE.MODULE,
                    MainetConstants.PlumberLicense.PLUM_MASTER_TABLE.TABLE,
                    MainetConstants.PlumberLicense.PLUM_MASTER_TABLE.COLUMN, orgId,
                    MainetConstants.RECEIPT_MASTER.Reset_Type, null).toString();

            if(Utility.isEnvPrefixAvailable(UserSession.getCurrent().getOrganisation(), MainetConstants.ENV_PSCL)){
        		   FinancialYear financiaYear = ApplicationContextProvider.getApplicationContext().getBean(TbFinancialyearService.class).getFinanciaYearByDate(new Date());
                   String finacialYear = Utility.getFinancialYear(financiaYear.getFaFromDate(), financiaYear.getFaToDate());
                   licNo = "FY" + MainetConstants.WINDOWS_SLASH + finacialYear + MainetConstants.WINDOWS_SLASH + String.format(MainetConstants.WorksManagement.FOUR_PERCENTILE,Long.valueOf(licNo));
        	}
         
            dto.setPlumberLicenceNo(licNo);
            if (dto.getApplicationId() != null) {
                PlumberMaster master = null;
                try{
                	master=plumberLicenseService.getPlumberDetailsByAppId(dto.getApplicationId(), orgId);
                	
                }catch (final Exception ex) {
                	master =null;
                	
                }
                if(Utility.isEnvPrefixAvailable(UserSession.getCurrent().getOrganisation(), MainetConstants.ENV_PSCL)){
                if(master == null) {
                	Long id=plumberLicenseService.getPlumberId(dto.getApplicationId(), orgId);
                	master=plumberLicenseService.getPlumberDetailsByPlumId(id, orgId);
                }
                }
                if (master.getPlumLicNo() != null && !master.getPlumLicNo().isEmpty()) {
                    object.put(MainetConstants.WorksManagement.CHECK_FLAG, MainetConstants.FlagD);
                    object.put("licNo", master.getPlumLicNo());
                    return object;
                }

            }
            

            boolean saveflag = plumberLicenseService.updateLicNumber(dto);
            if (saveflag) {
                model.getPlumberLicenseReqDTO().setPlumberLicenceNo(licNo);
                object.put(MainetConstants.WorksManagement.CHECK_FLAG, MainetConstants.FlagY);
                object.put("licNo", licNo);
                model.setApplicationId(dto.getApplicationId());
            } else {
                object.put(MainetConstants.WorksManagement.CHECK_FLAG, MainetConstants.FlagN);
            }

        } else {
            object.put(MainetConstants.WorksManagement.CHECK_FLAG, MainetConstants.FlagA);
            object.put("licNo", model.getPlumberLicenseReqDTO().getPlumberLicenceNo());
        }

        return object;
    }

    @RequestMapping(params = "printPlumberLicense", method = RequestMethod.POST)
    public ModelAndView printPlumberLicense(final HttpServletRequest httpServletRequest) {

        bindModel(httpServletRequest);
        final PlumberLicenseFormModel model = getModel();
        // #T89750
        if (MainetConstants.Common_Constant.YES
                .equals(ApplicationSession.getInstance().getMessage("dms.configure"))) {
            // push in DMS make BIRT/JASPER for PDF
            // make complete URL with application id and search in TB_WT_PLUM_MAS
            // for BIRT
            String URL = ServiceEndpoints.WATER_BIRT_REPORT_URL;
            String filePath = Utility.getFilePathForPdfUsingBirt(URL);
            if (filePath != null) {
                String idfId = "LICE" + MainetConstants.FILE_PATH_SEPARATOR + model.getApplicationId();
                FileUploadDTO uploadDTO = Utility.dataSetForDMS(filePath, idfId, MainetConstants.WATER_DEPT);
                model.setCommonFileAttachment(ApplicationContextProvider.getApplicationContext().getBean(IFileUploadService.class)
                        .setFileUploadMethod(new ArrayList<DocumentDetailsVO>()));
                fileUpload.doMasterFileUpload(model.getCommonFileAttachment(), uploadDTO);
            }
        }
        return new ModelAndView("PlumberLicenseLetter", MainetConstants.FORM_NAME, model);
    }

    @RequestMapping(params = "getduplicateLicense", method = RequestMethod.POST)
    public @ResponseBody Map<String, Object> getduplicateLicense(final HttpServletRequest httpServletRequest) {
        this.bindModel(httpServletRequest);
        Map<String, Object> object = new LinkedHashMap<String, Object>();
        Long orgId = UserSession.getCurrent().getOrganisation().getOrgid();
        PlumberLicenseFormModel model = this.getModel();
        if(model.getPlumberLicenseReqDTO().getPlumberLicenceNo()!=null){
	        object.put(MainetConstants.WorksManagement.CHECK_FLAG, MainetConstants.FlagA);
	        object.put("licNo", model.getPlumberLicenseReqDTO().getPlumberLicenceNo());
    	}else{
    		object.put(MainetConstants.WorksManagement.CHECK_FLAG, MainetConstants.FlagI);
    	}
        return object;
    }
}
