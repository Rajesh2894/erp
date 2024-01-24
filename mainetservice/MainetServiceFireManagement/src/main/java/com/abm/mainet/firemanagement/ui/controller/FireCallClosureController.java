package com.abm.mainet.firemanagement.ui.controller;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.abm.mainet.common.constant.MainetConstants;
import com.abm.mainet.common.integration.dms.domain.AttachDocs;
import com.abm.mainet.common.integration.dms.service.IAttachDocsService;
import com.abm.mainet.common.integration.dms.service.IFileUploadService;
import com.abm.mainet.common.integration.dto.RequestDTO;
import com.abm.mainet.common.master.service.DepartmentService;
import com.abm.mainet.common.service.IEmployeeService;
import com.abm.mainet.common.ui.controller.AbstractFormController;
import com.abm.mainet.common.utility.UserSession;
import com.abm.mainet.firemanagement.Constants.Constants;
import com.abm.mainet.firemanagement.dto.FireCallRegisterDTO;
import com.abm.mainet.firemanagement.dto.OccuranceBookDTO;
import com.abm.mainet.firemanagement.service.IFireCallRegisterService;
import com.abm.mainet.firemanagement.ui.model.FireCallClosureModel;

@Controller
@RequestMapping(value = "/FireCallClosure.html")
public class FireCallClosureController extends AbstractFormController<FireCallClosureModel> {

	@Autowired
	private IFileUploadService fileUpload;

	@Autowired
	private IFireCallRegisterService fireCallRegisterService;
	
	@Autowired
	private IEmployeeService employeeService;
	
	@Autowired
	private DepartmentService departmentService;
	
	@Autowired
    private IAttachDocsService iAttachDocsService;

	@RequestMapping(method = { RequestMethod.POST, RequestMethod.GET })
	public ModelAndView index(final HttpServletRequest httpServletRequest) {
		this.sessionCleanup(httpServletRequest);
		fileUpload.sessionCleanUpForFileUpload();
		this.getModel().setCommonHelpDocs("FireCallClosure.html");
		return defaultResult();
	}

	@ResponseBody
	@RequestMapping(params = "searchFireCallRegister", method = RequestMethod.POST)
	public ModelAndView searchFireCallRegister(final HttpServletRequest request,
			@RequestParam(required = false) String complainNo, @RequestParam(required = false) String fireStation) {

		getModel().bind(request);

		ModelAndView mv = new ModelAndView("FireCallClosureSummary", MainetConstants.FORM_NAME, this.getModel());

			List<FireCallRegisterDTO> callRegisterList = fireCallRegisterService.searchFireCallRegister(complainNo, "O,SB,A,I", fireStation, UserSession.getCurrent().getOrganisation().getOrgid());
			if (CollectionUtils.isEmpty(callRegisterList)) {
				this.getModel().addValidationError(getApplicationSession().getMessage("FireCallRegisterDTO.call.norecord"));
				final BindingResult bindingResult = this.getModel().getBindingResult();
	
				if (bindingResult != null) {
					mv.addObject(BindingResult.MODEL_KEY_PREFIX + MainetConstants.FORM_NAME,
							this.getModel().getBindingResult());
				}
			}else {
			List<FireCallRegisterDTO> callRegisterList1=new ArrayList<FireCallRegisterDTO>();
			callRegisterList1.addAll(callRegisterList);
			request.setAttribute("callList", callRegisterList1);
			}		
	
		return mv;
}
	
	
	@ResponseBody
	@RequestMapping(params = "searchFireCallRegister1", method = RequestMethod.POST)
	public ModelAndView searchFireCallRegister1(final HttpServletRequest request,
			@RequestParam(required = false) String complainNo, @RequestParam(required = false) String fireStation) {

		getModel().bind(request);

		ModelAndView mv = new ModelAndView("FireCallClosureSummary", MainetConstants.FORM_NAME, this.getModel());

		List<FireCallRegisterDTO> callRegisterList = fireCallRegisterService.searchFireCallRegister(complainNo, "O,SB", fireStation, UserSession.getCurrent().getOrganisation().getOrgid());
		if (CollectionUtils.isEmpty(callRegisterList)) {
			final BindingResult bindingResult = this.getModel().getBindingResult();

			if (bindingResult != null) {
				mv.addObject(BindingResult.MODEL_KEY_PREFIX + MainetConstants.FORM_NAME,
						this.getModel().getBindingResult());
			}
		}
		request.setAttribute("callList", callRegisterList);

		return mv;
	}
	
	

	@RequestMapping(method = { RequestMethod.POST }, params = MainetConstants.CommonConstants.EDIT)
	public ModelAndView editOrViewCaseEntry(@RequestParam(MainetConstants.Common_Constant.ID) Long id,
			@RequestParam(value = MainetConstants.REQUIRED_PG_PARAM.MODE, required = true) String mode,
			final HttpServletRequest request) {
		getModel().bind(request);
		FireCallClosureModel model = getModel();
		model.setSaveMode(mode);
		model.setEntity(fireCallRegisterService.findOne(id));
		Long secuDeptId = departmentService.getDepartmentIdByDeptCode(Constants.FIRE_DRPT_CODE);
		request.setAttribute("secuDeptEmployee", employeeService.getEmployeeData(secuDeptId , null, null , UserSession.getCurrent().getOrganisation().getOrgid(),null));
		List<FireCallRegisterDTO> list1 = fireCallRegisterService.getAllVehiclesAssign(UserSession.getCurrent().getOrganisation().getOrgid(),secuDeptId);
		request.setAttribute("listVeh", list1);
		
		OccuranceBookDTO occurDto=fireCallRegisterService.findAlloccurrences(model.getEntity().getCmplntNo(),UserSession.getCurrent().getOrganisation().getOrgid());
		request.setAttribute("occurDto", occurDto);
		//fetch uploaded document start
		FireCallRegisterDTO fireEntity = fireCallRegisterService.findOne(id);
		RequestDTO requestDTO = new RequestDTO();
		requestDTO.setIdfId(Constants.FIRE_CALL_REG_TABLE + MainetConstants.WINDOWS_SLASH + fireEntity.getCmplntNo().toString());
		List<AttachDocs> attachDocs = iAttachDocsService.findByCode(UserSession.getCurrent().getOrganisation().getOrgid(), requestDTO.getIdfId());
		this.getModel().setAttachDocsList(attachDocs);	
		List<AttachDocs> attachDoc = iAttachDocsService.findByCode(UserSession.getCurrent().getOrganisation().getOrgid(),Constants.FIRE_CALL_CLOSURE + MainetConstants.WINDOWS_SLASH +fireEntity.getClosureId()+MainetConstants.WINDOWS_SLASH +UserSession.getCurrent().getOrganisation().getOrgid());
		this.getModel().setAttachDocs(attachDoc);
		//fetch uploaded document end
		
		ModelAndView mv = new ModelAndView("FireCallClosureForm", MainetConstants.FORM_NAME, model);
		return mv;
	}

}

