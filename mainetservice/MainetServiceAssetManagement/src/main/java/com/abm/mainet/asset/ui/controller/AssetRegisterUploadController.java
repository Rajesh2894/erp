/**
 * 
 */
package com.abm.mainet.asset.ui.controller;

import java.io.File;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.abm.mainet.asset.service.IAssetRegisterUploadService;
import com.abm.mainet.asset.ui.dto.AssetRegisterUploadDto;
import com.abm.mainet.asset.ui.dto.AssetUploadErrorDetailDto;
import com.abm.mainet.asset.ui.dto.ITAssetRegisterUploadDto;
import com.abm.mainet.asset.ui.dto.ReadExcelData;
import com.abm.mainet.asset.ui.model.AssetRegisterUploadModel;
import com.abm.mainet.common.constant.MainetConstants;
import com.abm.mainet.common.exception.FrameworkException;
import com.abm.mainet.common.integration.dms.fileUpload.FileUploadUtility;
import com.abm.mainet.common.integration.dms.service.IFileUploadService;
import com.abm.mainet.common.ui.controller.AbstractFormController;
import com.abm.mainet.common.upload.excel.WriteExcelData;
import com.abm.mainet.common.utility.UserSession;
import com.abm.mainet.common.utility.Utility;

/**
 * @author satish.rathore
 *
 */
/**
 * @author satish.rathore
 *
 */
@Controller
@RequestMapping(value = { MainetConstants.AssetManagement.ASSET_REGISTER_UPLOAD, "ITAssetRegisterUpload.html" })
public class AssetRegisterUploadController extends AbstractFormController<AssetRegisterUploadModel> {
    private static final String ASSET_EXPORT_EXCEPTION = "Exception while exporting Schedule of rate Data : ";
    private static final String ASSET_IMPORT_EXCEPTION = "Exception while importing Schedule of rate Data : ";

    @Autowired
    private IFileUploadService fileUpload;
    @Autowired
    private IAssetRegisterUploadService assetRegisterUploadService;

    @RequestMapping(method = { RequestMethod.POST, RequestMethod.GET })
    public ModelAndView index(final Model model, final HttpServletRequest httpServletRequest) {
        sessionCleanup(httpServletRequest);
        fileUpload.sessionCleanUpForFileUpload();
        this.getModel().setCommonHelpDocs("AssetRegisterUpload.html");
        return index();
    }

    @RequestMapping(params = MainetConstants.AssetManagement.EXCEL_TEMPLATE, method = { RequestMethod.GET })
    public void exportAssetExcelData(final HttpServletResponse response, final HttpServletRequest request) {
        AssetRegisterUploadModel model = this.getModel();
        model.bind(request);
        try {
        	if(UserSession.getCurrent().getModuleDeptCode().equals(MainetConstants.AssetManagement.ASSET_MANAGEMENT)) {
        		WriteExcelData<AssetRegisterUploadDto> data = new WriteExcelData<>(
                        MainetConstants.AssetManagement.ASSET_REGISTER_UPLOAD_DTO + MainetConstants.XLSX_EXT,
                        request, response);
                data.getExpotedExcelSheet(this.getModel().getAstRegUploadDtoList(), AssetRegisterUploadDto.class);
        	}else {
        		WriteExcelData<ITAssetRegisterUploadDto> data = new WriteExcelData<>(
                        "ITAssetRegisterUpload" + MainetConstants.XLSX_EXT,
                        request, response);
                data.getExpotedExcelSheet(this.getModel().getItAssetRegisterUploadDto(), ITAssetRegisterUploadDto.class);
        	}
        } catch (Exception ex) {
            throw new FrameworkException(ASSET_EXPORT_EXCEPTION, ex);
        }
    }
    
    @ResponseBody
    @RequestMapping(params = "ITAssetLoadExcelData", method = RequestMethod.POST)
    public ModelAndView itAssetLoadValidateAndLoadExcelData(final HttpServletRequest request) {

        Long orgId = UserSession.getCurrent().getOrganisation().getOrgid();
        Long empId = UserSession.getCurrent().getEmployee().getEmpId();
        int langId = UserSession.getCurrent().getLanguageId();
        AssetRegisterUploadModel model = this.getModel();
        this.getModel().bind(request);
        final String filePath = getUploadedFinePath();
        boolean assetFlag = UserSession.getCurrent().getModuleDeptCode().equals(MainetConstants.AssetManagement.ASSET_MANAGEMENT);
        try {
            List<AssetUploadErrorDetailDto> errorDetails = new ArrayList<>();
            
            ReadExcelData<ITAssetRegisterUploadDto> data = new ReadExcelData<>(filePath, ITAssetRegisterUploadDto.class);
            data.parseExcelList();
            List<String> errlist = data.getErrorList();
            AssetUploadErrorDetailDto errListDetails = null;
            if (!errlist.isEmpty()) {
                for (String string : errlist) {
                    errListDetails = new AssetUploadErrorDetailDto();
                    errListDetails.setFileName(model.getUploadFileName());
                    errListDetails.setErrDescription(MainetConstants.WorksManagement.DATA_TYPE);
                    errListDetails.setErrData(string);
                    errListDetails.setOrgId(orgId);
                    errListDetails.setCreatedBy(empId);
                    errListDetails.setCreationDate(new Date());
                    errListDetails.setLgIpMac(Utility.getClientIpAddress(request));
                    errorDetails.add(errListDetails);
                }
            }
            final List<ITAssetRegisterUploadDto> dtos = data.getParseData();
            List<ITAssetRegisterUploadDto> astList = model.itAssetprepareDto(dtos, orgId, Long.valueOf(langId), errorDetails);
            if (errorDetails.isEmpty()) {
                errorDetails = model.iTAssetvalidateDto(astList, model, orgId, empId, errorDetails, request);
            }
            if (errorDetails.isEmpty()) {
                Set<ITAssetRegisterUploadDto> errorDtos = dtos.stream().filter(dto -> Collections.frequency(dtos, dto) > 1)
                        .collect(Collectors.toSet());
                if (!errorDtos.isEmpty()) {
                    for (ITAssetRegisterUploadDto errorDto : errorDtos) {
                        AssetUploadErrorDetailDto dupliCombError = new AssetUploadErrorDetailDto();
                        dupliCombError.setAssetType(UserSession.getCurrent().getModuleDeptCode());
                        dupliCombError.setFileName(model.getUploadFileName());
                        dupliCombError.setErrDescription("Found Duplicate Combination");
                        dupliCombError.setErrData(
                                "Duplicate Combination :" + errorDto.getSerialNo());
                        dupliCombError.setOrgId(orgId);
                        dupliCombError.setCreatedBy(empId);
                        dupliCombError.setCreationDate(new Date());
                        dupliCombError.setLgIpMac(Utility.getClientIpAddress(request));
                        errorDetails.add(dupliCombError);
                    }
                }
            }
            List<String> errorList = new ArrayList<>();
            if (!errorDetails.isEmpty()) {
                errorList.addAll(errlist);
                model.addValidationError(
                        "Error While Uploading Excel: " + model.getUploadFileName() + ": For More Details Check Error Log.");
                assetRegisterUploadService.saveAndDeleteErrorDetails(errorDetails, orgId,
                        UserSession.getCurrent().getModuleDeptCode());
                model.setErrDetails(errorDetails);
                getModel().setSuccessFlag(MainetConstants.MASTER.E);

            } else {
                model.setItAstUploadDto(astList);
                model.setIpMacAddress(Utility.getClientIpAddress(request));
                if (model.saveForm()) {
                    model.setErrDetails(new ArrayList<AssetUploadErrorDetailDto>());
                    assetRegisterUploadService.saveAndDeleteErrorDetails(new ArrayList<AssetUploadErrorDetailDto>(),
                            Long.valueOf((long) orgId),
                            UserSession.getCurrent().getModuleDeptCode());
                    getModel().setSuccessFlag(getApplicationSession().getMessage("ast.upload.success"));
                }
            }

            fileUpload.sessionCleanUpForFileUpload();
            return defaultMyResult();
        } catch (Exception ex) {
            throw new FrameworkException(ASSET_IMPORT_EXCEPTION, ex);
        }
    
    }

    @ResponseBody
    @RequestMapping(params = MainetConstants.WorksManagement.LOAD_EXCEL_DATA, method = RequestMethod.POST)
    public ModelAndView loadValidateAndLoadExcelData(final HttpServletRequest request) {
        Long orgId = UserSession.getCurrent().getOrganisation().getOrgid();
        Long empId = UserSession.getCurrent().getEmployee().getEmpId();
        int langId = UserSession.getCurrent().getLanguageId();
        AssetRegisterUploadModel model = this.getModel();
        this.getModel().bind(request);
        final String filePath = getUploadedFinePath();
       try {
            List<AssetUploadErrorDetailDto> errorDetails = new ArrayList<>();
            
            ReadExcelData<AssetRegisterUploadDto> data = new ReadExcelData<>(filePath, AssetRegisterUploadDto.class);
            data.parseExcelList();
            List<String> errlist = data.getErrorList();
            AssetUploadErrorDetailDto errListDetails = null;
            if (!errlist.isEmpty()) {
                for (String string : errlist) {
                    errListDetails = new AssetUploadErrorDetailDto();
                    errListDetails.setFileName(model.getUploadFileName());
                    errListDetails.setErrDescription(MainetConstants.WorksManagement.DATA_TYPE);
                    errListDetails.setErrData(string);
                    errListDetails.setOrgId(orgId);
                    errListDetails.setCreatedBy(empId);
                    errListDetails.setCreationDate(new Date());
                    errListDetails.setLgIpMac(Utility.getClientIpAddress(request));
                    errorDetails.add(errListDetails);
                }
            }
            final List<AssetRegisterUploadDto> dtos = data.getParseData();
            List<AssetRegisterUploadDto> astList = model.prepareDto(dtos, orgId, Long.valueOf(langId), errorDetails);
            if (errorDetails.isEmpty()) {
                errorDetails = model.validateDto(astList, model, orgId, empId, errorDetails, request);
            }
            if (errorDetails.isEmpty()) {
                Set<AssetRegisterUploadDto> errorDtos = dtos.stream().filter(dto -> Collections.frequency(dtos, dto) > 1)
                        .collect(Collectors.toSet());
                if (!errorDtos.isEmpty()) {
                    for (AssetRegisterUploadDto errorDto : errorDtos) {
                        AssetUploadErrorDetailDto dupliCombError = new AssetUploadErrorDetailDto();
                        dupliCombError.setAssetType(UserSession.getCurrent().getModuleDeptCode());
                        dupliCombError.setFileName(model.getUploadFileName());
                        dupliCombError.setErrDescription("Found Duplicate Combination");
                        dupliCombError.setErrData(
                                "Duplicate Combination :" + errorDto.getSerialNo());
                        dupliCombError.setOrgId(orgId);
                        dupliCombError.setCreatedBy(empId);
                        dupliCombError.setCreationDate(new Date());
                        dupliCombError.setLgIpMac(Utility.getClientIpAddress(request));
                        errorDetails.add(dupliCombError);
                    }
                }
            }
            List<String> errorList = new ArrayList<>();
            if (!errorDetails.isEmpty()) {
                errorList.addAll(errlist);
                model.addValidationError(
                        "Error While Uploading Excel: " + model.getUploadFileName() + ": For More Details Check Error Log.");
                assetRegisterUploadService.saveAndDeleteErrorDetails(errorDetails, orgId,
                        UserSession.getCurrent().getModuleDeptCode());
                model.setErrDetails(errorDetails);
                getModel().setSuccessFlag(MainetConstants.MASTER.E);

            } else {
                model.setAstUploadDto(astList);
                model.setIpMacAddress(Utility.getClientIpAddress(request));
                if (model.saveForm()) {
                    model.setErrDetails(new ArrayList<AssetUploadErrorDetailDto>());
                    assetRegisterUploadService.saveAndDeleteErrorDetails(new ArrayList<AssetUploadErrorDetailDto>(),
                            Long.valueOf((long) orgId),
                            UserSession.getCurrent().getModuleDeptCode());
                    getModel().setSuccessFlag(getApplicationSession().getMessage("ast.upload.success"));
                }
            }

            fileUpload.sessionCleanUpForFileUpload();
            return defaultMyResult();
        } catch (Exception ex) {
            throw new FrameworkException(ASSET_IMPORT_EXCEPTION, ex);
        }
    }

    /**
     * @return this method is for find the real path of the file where it is present
     */
    private String getUploadedFinePath() {
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

}