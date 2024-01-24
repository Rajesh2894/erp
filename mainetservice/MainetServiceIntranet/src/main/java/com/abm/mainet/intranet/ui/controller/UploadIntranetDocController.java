package com.abm.mainet.intranet.ui.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.abm.mainet.common.constant.MainetConstants;
import com.abm.mainet.common.domain.Department;
import com.abm.mainet.common.integration.dms.domain.AttachDocs;
import com.abm.mainet.common.integration.dms.service.IAttachDocsService;
import com.abm.mainet.common.integration.dms.service.IFileUploadService;
import com.abm.mainet.common.integration.dto.RequestDTO;
import com.abm.mainet.common.master.service.DepartmentService;
import com.abm.mainet.common.ui.controller.AbstractFormController;
import com.abm.mainet.common.utility.ApplicationContextProvider;
import com.abm.mainet.common.utility.CommonMasterUtility;
import com.abm.mainet.common.utility.UserSession;
import com.abm.mainet.intranet.domain.IntranetMaster;
import com.abm.mainet.intranet.dto.report.IntranetDTO;
import com.abm.mainet.intranet.service.IIntranetUploadService;
import com.abm.mainet.intranet.ui.model.UploadIntranetDocModel;

@Controller
@RequestMapping(value="/UploadIntranetDocSummary.html")
public class UploadIntranetDocController extends AbstractFormController<UploadIntranetDocModel> {
	
	@Autowired
    private IAttachDocsService iAttachDocsService;
    
    @Autowired
    private IIntranetUploadService iIntranetUploadService;
    
    @Autowired
    private IFileUploadService iFileUploadService;
	
	@RequestMapping(method = { RequestMethod.GET, RequestMethod.POST })
	public ModelAndView index(final Model model, final HttpServletRequest httpServletRequest) {
		sessionCleanup(httpServletRequest);
		Long orgId = UserSession.getCurrent().getOrganisation().getOrgid();
		UploadIntranetDocModel uploadIntranetDocModel = this .getModel();
		//List<IntranetMaster> intranetMasterDocList = intranetRepository.findAll();	//add orgid
		List<IntranetMaster> intranetMasterDocList = iIntranetUploadService.getAllIntranetData(orgId);
		//fetch dept desc and doc category type desc
		intranetMasterDocList.forEach(obj->{
			String intranetDeptDesc=null;
			if(obj.getDeptId()==0) {
				intranetDeptDesc="All";
			}else {
				intranetDeptDesc= iIntranetUploadService.getdeptdesc(obj.getDeptId(), obj.getOrgid());
			}
			String intranetDocCateDesc = CommonMasterUtility.getCPDDescription(obj.getDocCateType(), "IDC", obj.getOrgid()); 
			obj.setDeptDesc(intranetDeptDesc);
			obj.setDocCatDesc(intranetDocCateDesc);
		});
	  	uploadIntranetDocModel.setFetchIntranetListMas(intranetMasterDocList);
		model.addAttribute("intranetMasterDocList", intranetMasterDocList);	
		return new ModelAndView("UploadIntranetDocSummary", MainetConstants.FORM_NAME, uploadIntranetDocModel);
	}
	
	@RequestMapping(params = "UploadIntranetDoc", method = { RequestMethod.POST, RequestMethod.GET })
	public ModelAndView addUploadIntranetDoc(@RequestParam String mode, final HttpServletRequest request, Model model) {
		this.sessionCleanup(request);
		UploadIntranetDocModel intranetModel = this.getModel();
		intranetModel.setSaveMode(mode);
		model.addAttribute("departments", loadDepartmentList());
		return new ModelAndView("UploadIntranetDocValidn", MainetConstants.FORM_NAME, intranetModel);
	}
	
	@ResponseBody
	@RequestMapping(params = "searchIntranet", method = RequestMethod.POST)
	public List<IntranetDTO> findIntranetData(@RequestParam("docCateType") final Long docCateType, final HttpServletRequest request, final Model model) {
		UploadIntranetDocModel modelIntranet = this.getModel();
		getModel().bind(request);
		Long orgId = UserSession.getCurrent().getOrganisation().getOrgid();
		List<IntranetDTO> intranetDtoList = iIntranetUploadService.getIntranetData(orgId, docCateType);
		//fetch dept desc and doc category type desc
		intranetDtoList.forEach(obj->{
			String intranetDeptDesc = iIntranetUploadService.getdeptdesc(obj.getDeptId(), orgId);
			String intranetDocCateDesc = CommonMasterUtility.getCPDDescription(obj.getDocCateType(), "IDC", orgId); 
			obj.setDeptDesc(intranetDeptDesc);
			obj.setDocCatDesc(intranetDocCateDesc);
		});
		modelIntranet.setFetchintranetDtoList(intranetDtoList);
		return intranetDtoList; 	
	}
	
	@RequestMapping(params = "editIntranetForm", method = { RequestMethod.GET, RequestMethod.POST })
	public ModelAndView index1(@RequestParam(MainetConstants.Common_Constant.ID) Long inId, final Model model, final HttpServletRequest httpServletRequest) {
		sessionCleanup(httpServletRequest);
		iFileUploadService.sessionCleanUpForFileUpload();
		
		Long orgId = UserSession.getCurrent().getOrganisation().getOrgid();
		
		//String DB_BACK_SLACE = "\\";
		//requestDTO.setIdfId("IntranetDoc" + DB_BACK_SLACE + inId);
		
		RequestDTO requestDTO = new RequestDTO();
		requestDTO.setIdfId(inId.toString());
		List<AttachDocs> attachDocs = iAttachDocsService.findByCode(orgId, requestDTO.getIdfId());
				
		this.getModel().setIntranetDto(iIntranetUploadService.getIntranetDataByInId(inId, orgId));
		this.getModel().getIntranetDto().setAtdFname(attachDocs.get(0).getAttFname());
		this.getModel().getIntranetDto().setAtdPath(attachDocs.get(0).getAttPath());
		this.getModel().setSaveMode("E");
		
		model.addAttribute("departments", loadDepartmentList());
		return new ModelAndView("UploadIntranetDocValidn", MainetConstants.FORM_NAME, this.getModel());
	}
	
	private List<Department> loadDepartmentList() {
		DepartmentService departmentService = ApplicationContextProvider.getApplicationContext().getBean(DepartmentService.class);
		List<Department> departments = departmentService.getDepartments(UserSession.getCurrent().getOrganisation().getOrgid(), MainetConstants.FlagA);
		return departments;
	}

	
	
}
