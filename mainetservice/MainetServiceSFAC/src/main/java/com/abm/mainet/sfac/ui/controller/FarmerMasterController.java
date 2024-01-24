package com.abm.mainet.sfac.ui.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.abm.mainet.common.constant.MainetConstants;
import com.abm.mainet.common.integration.dms.client.FileNetApplicationClient;
import com.abm.mainet.common.integration.dms.domain.AttachDocs;
import com.abm.mainet.common.integration.dms.fileUpload.FileUploadUtility;
import com.abm.mainet.common.integration.dms.service.IAttachDocsService;
import com.abm.mainet.common.integration.dms.service.IFileUploadService;
import com.abm.mainet.common.ui.controller.AbstractFormController;
import com.abm.mainet.common.utility.ApplicationContextProvider;
import com.abm.mainet.common.utility.UserSession;
import com.abm.mainet.sfac.dto.FarmerMasterDto;
import com.abm.mainet.sfac.service.FarmerMasterService;
import com.abm.mainet.sfac.ui.model.FarmerMasterModel;


/**
 * @author pooja.maske
 * 
 */

@Controller
@RequestMapping(MainetConstants.Sfac.FARMER_MASTER_FORM_HTML)
public class FarmerMasterController extends AbstractFormController<FarmerMasterModel>{
	
	@Autowired
	private FarmerMasterService farmerMasterService;
	
	@Autowired
	IFileUploadService fileUpload;
	
	@RequestMapping(method = { RequestMethod.GET, RequestMethod.POST })
	public ModelAndView index(HttpServletRequest request) throws Exception {
		sessionCleanup(request);
		fileUpload.sessionCleanUpForFileUpload();
		this.getModel().setMasterDtoList(farmerMasterService.findAll());
		/*if (UserSession.getCurrent().getEmployee().getMasId() != null )
		this.getModel().setFarmerMasterDtoList(farmerMasterService.getAllDetailsById(UserSession.getCurrent().getEmployee().getMasId()));*/
		return new ModelAndView(MainetConstants.Sfac.FARMER_MASTER_SUMMARY_FORM, MainetConstants.FORM_NAME,
				getModel());
	}
	

	@RequestMapping(params = "formForCreate", method = { RequestMethod.POST })
	public ModelAndView formForCreate(HttpServletRequest request, Model model) {
		sessionCleanup(request);
		fileUpload.sessionCleanUpForFileUpload();
		this.getModel().setViewMode(MainetConstants.FlagA);
		this.getModel().setDownloadMode(MainetConstants.FlagN);
		return new ModelAndView(MainetConstants.Sfac.FARMER_MASTER_FORM, MainetConstants.FORM_NAME, getModel());
	}

	@RequestMapping(method = { RequestMethod.POST }, params = MainetConstants.CommonConstants.EDIT)
	public ModelAndView editOrView(Long frmId,
			@RequestParam(value = MainetConstants.REQUIRED_PG_PARAM.MODE, required = true) String mode,
			final HttpServletRequest httpServletRequest) {
		getModel().bind(httpServletRequest);
		this.getModel().setViewMode(mode);
		final List<AttachDocs> attachDocs;
		Long orgId = UserSession.getCurrent().getOrganisation().getOrgid();
		FarmerMasterDto dto = farmerMasterService.getDetailById(frmId);
		this.getModel().setFarmerMasterDto(dto);
		attachDocs = ApplicationContextProvider.getApplicationContext().getBean(IAttachDocsService.class).findByCode(orgId,
                "FRM"+ MainetConstants.WINDOWS_SLASH +this.getModel().getFarmerMasterDto().getFrmId());
		  this.getModel().setAttachDocsList(attachDocs);
		  if (!attachDocs.isEmpty()) {
	          FileUploadUtility.getCurrent().setFileMap(farmerMasterService.getUploadedFileList(attachDocs,FileNetApplicationClient.getInstance()));
	          this.getModel().setDownloadMode(MainetConstants.FlagY);
		  }else
			   this.getModel().setDownloadMode(MainetConstants.FlagN);
		  this.getModel().getdataOfUploadedImage();
		return new ModelAndView(MainetConstants.Sfac.FARMER_MASTER_FORM, MainetConstants.FORM_NAME, getModel());
	}

	@RequestMapping(method = { RequestMethod.POST }, params = "searchForm")
	public ModelAndView searchFarmerDetails(Long frmId, String  frmFPORegNo, final HttpServletRequest httpServletRequest) {
		List<FarmerMasterDto> DtoList = farmerMasterService.getFarmerDetailsByIds(frmId,frmFPORegNo);
		this.getModel().setFarmerMasterDtoList(DtoList);
		this.getModel().getFarmerMasterDto().setFrmId(frmId);
		this.getModel().getFarmerMasterDto().setFrmFPORegNo(frmFPORegNo);
		return new ModelAndView("FarmerMasterSummaryValidn", MainetConstants.FORM_NAME, getModel());
	}


    
    @RequestMapping(method = RequestMethod.POST, params = MainetConstants.WorksManagement.getUploadedImage)
    public ModelAndView getUploadedImage(final HttpServletRequest request) {
        bindModel(request);
        this.getModel().getdataOfUploadedImage();
        this.getModel().setDownloadMode(MainetConstants.FlagY);
        return new ModelAndView("FarmerMasterValidn", MainetConstants.FORM_NAME, getModel());
    }
}
