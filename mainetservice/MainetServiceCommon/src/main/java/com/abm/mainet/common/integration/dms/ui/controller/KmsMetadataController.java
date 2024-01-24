package com.abm.mainet.common.integration.dms.ui.controller;

import java.io.File;
import java.util.ArrayList;
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
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.servlet.ModelAndView;

import com.abm.mainet.common.constant.MainetConstants;
import com.abm.mainet.common.dto.JsonViewObject;
import com.abm.mainet.common.integration.dms.fileUpload.FileUploadUtility;
import com.abm.mainet.common.integration.dms.service.IFileUploadService;
import com.abm.mainet.common.integration.dms.ui.model.DmsMetadataModel;
import com.abm.mainet.common.integration.dto.DocumentDetailsVO;
import com.abm.mainet.common.master.dto.EmployeeBean;
import com.abm.mainet.common.master.dto.TbDepartment;
import com.abm.mainet.common.master.service.TbDepartmentService;
import com.abm.mainet.common.service.IEmployeeService;
import com.abm.mainet.common.ui.controller.AbstractEntryFormController;
import com.abm.mainet.common.utility.CommonMasterUtility;
import com.abm.mainet.common.utility.LookUp;
import com.abm.mainet.common.utility.UserSession;

@Controller
@RequestMapping(value = "/KmsMetadata.html")
public class KmsMetadataController extends AbstractEntryFormController<DmsMetadataModel> {

	@Autowired
	private IFileUploadService fileUploadService;

	@Autowired
	private IEmployeeService employeeService;

	@Autowired
	private TbDepartmentService tbDepartmentService;

	@RequestMapping(method = { RequestMethod.GET, RequestMethod.POST })
	public ModelAndView index(HttpServletRequest req, Model model) {
		this.sessionCleanUp(req);
		fileUploadService.sessionCleanUpForFileUpload();
		List<LookUp> prefixListNew = new ArrayList<LookUp>();
		String dept = UserSession.getCurrent().getEmployee().getTbDepartment().getDpDeptcode();
		List<LookUp> prefixList = CommonMasterUtility.getLevelData(MainetConstants.Dms.KTD, MainetConstants.NUMBERS.ONE,
				UserSession.getCurrent().getOrganisation());
		prefixList.forEach(prefix -> {
			if (prefix.getLookUpCode().equals(dept)) {
				prefixListNew.add(prefix);
			}
		});
		getModel().setDepartmentList(prefixListNew);
		Map<Long, String> roleList = employeeService
				.getGroupList(UserSession.getCurrent().getOrganisation().getOrgid());
		getModel().setRoleList(roleList);
		List<EmployeeBean> empList = employeeService
				.getAllEmployee(UserSession.getCurrent().getOrganisation().getOrgid());
		getModel().setEmployeeList(empList);
		List<TbDepartment> assignedDepartmentlist = tbDepartmentService
				.findMappedDepartments(UserSession.getCurrent().getOrganisation().getOrgid());
		getModel().setAssignedDeptList(assignedDepartmentlist);
		return new ModelAndView("KmsMetadata", MainetConstants.FORM_NAME, this.getModel());
	}

	@RequestMapping(params = "getKmsMetadata", method = RequestMethod.POST)
	public ModelAndView getKmsMetadata(@RequestParam("deptId") Long deptId, HttpServletRequest request, Model model) {
		sessionCleanup(request);
		fileUploadService.sessionCleanUpForFileUpload();
		getModel().setDeptId(deptId);
		List<LookUp> prefixListNew = new ArrayList<LookUp>();

		String dept = UserSession.getCurrent().getEmployee().getTbDepartment().getDpDeptcode();
		List<LookUp> metadataList = CommonMasterUtility.getChildLookUpsFromParentId(deptId);
		List<LookUp> prefixList = CommonMasterUtility.getLevelData(MainetConstants.Dms.KTD, MainetConstants.NUMBERS.ONE,
				UserSession.getCurrent().getOrganisation());

		List<LookUp> atta = CommonMasterUtility.getListLookup(MainetConstants.Dms.KDT,
				UserSession.getCurrent().getOrganisation());

		getModel().setDocTypeList(atta);
		prefixList.forEach(prefix -> {
			if (prefix.getLookUpCode().equals(dept)) {
				prefixListNew.add(prefix);
			}
		});
		List<TbDepartment> assignedDepartmentlist = tbDepartmentService
				.findMappedDepartments(UserSession.getCurrent().getOrganisation().getOrgid());
		getModel().setMetadataList(metadataList);
		getModel().setDepartmentList(prefixListNew);
		getModel().setAssignedDeptList(assignedDepartmentlist);
		Map<Long, String> roleList = employeeService
				.getGroupList(UserSession.getCurrent().getOrganisation().getOrgid());
		getModel().setRoleList(roleList);
		List<EmployeeBean> empList = employeeService
				.getAllEmployee(UserSession.getCurrent().getOrganisation().getOrgid());
		getModel().setEmployeeList(empList);

		return new ModelAndView("KmsMetadataValidn", MainetConstants.FORM_NAME, this.getModel());
	}

	@RequestMapping(method = RequestMethod.POST, params = "fileCountUpload")
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
		return new ModelAndView("MetadataFileUpload", MainetConstants.FORM_NAME, this.getModel());
	}

	@RequestMapping(method = RequestMethod.POST, params = "doFileUpload")
	public @ResponseBody JsonViewObject uploadDocument(final HttpServletRequest httpServletRequest,
			final HttpServletResponse response, final String fileCode, @RequestParam final String browserType) {
		UserSession.getCurrent().setBrowserType(browserType);
		final MultipartHttpServletRequest request = (MultipartHttpServletRequest) httpServletRequest;
		final JsonViewObject jsonViewObject = FileUploadUtility.getCurrent().doDmsFileUpload(request, fileCode,
				browserType);
		return jsonViewObject;
	}

}
