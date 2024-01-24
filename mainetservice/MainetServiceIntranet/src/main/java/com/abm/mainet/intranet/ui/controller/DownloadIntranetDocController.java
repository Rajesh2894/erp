package com.abm.mainet.intranet.ui.controller;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import com.abm.mainet.common.constant.MainetConstants;
import com.abm.mainet.common.ui.controller.AbstractFormController;
import com.abm.mainet.common.utility.CommonMasterUtility;
import com.abm.mainet.common.utility.UserSession;
import com.abm.mainet.intranet.domain.IntranetMaster;
import com.abm.mainet.intranet.repository.IntranetRepository;
import com.abm.mainet.intranet.service.IIntranetUploadService;
import com.abm.mainet.intranet.ui.model.UploadIntranetDocModel;

@Controller
@RequestMapping(value="/DownloadIntranetDoc.html")
public class DownloadIntranetDocController extends AbstractFormController<UploadIntranetDocModel> {
	
    @Autowired
    private IntranetRepository intranetRepository;
    
    @Autowired
    private IIntranetUploadService iIntranetUploadService;
	
	@RequestMapping(method = { RequestMethod.GET, RequestMethod.POST })
	public ModelAndView index(final Model model, final HttpServletRequest httpServletRequest) {
		UploadIntranetDocModel uploadIntranetDocModel = this .getModel();
		Long orgId = UserSession.getCurrent().getOrganisation().getOrgid();
		Date docToDate = new Date(); 
		Long deptId=UserSession.getCurrent().getEmployee().getTbDepartment().getDpDeptid();
		//List<IntranetMaster> intranetMasterDocList = intranetRepository.findDocs(docToDate, orgId);
		List<IntranetMaster> intranetMasterDocList = intranetRepository.findDocs(orgId, docToDate,deptId);
		//fetch dept desc and doc category type desc
		intranetMasterDocList.forEach(obj->{
			String intranetDeptDesc=null;
			if(obj.getDeptId()==0) {
				intranetDeptDesc="All";
			}else {
				intranetDeptDesc= iIntranetUploadService.getdeptdesc(obj.getDeptId(), orgId);
			}
			String intranetDocCateDesc = CommonMasterUtility.getCPDDescription(obj.getDocCateType(), "IDC", orgId); 
			obj.setDeptDesc(intranetDeptDesc);
			obj.setDocCatDesc(intranetDocCateDesc);
		});
		uploadIntranetDocModel.setFetchIntranetListMas(intranetMasterDocList);
		return new ModelAndView("DownloadIntranetDoc", MainetConstants.FORM_NAME, uploadIntranetDocModel);
	}
	
	

}
