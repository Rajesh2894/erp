package com.abm.mainet.rti.ui.controller;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.abm.mainet.cfc.loi.dto.TbLoiMas;
import com.abm.mainet.cfc.loi.service.TbLoiMasService;
import com.abm.mainet.common.constant.MainetConstants;
import com.abm.mainet.common.constant.PrefixConstants;
import com.abm.mainet.common.domain.Department;
import com.abm.mainet.common.domain.Employee;
import com.abm.mainet.common.domain.ServiceMaster;
import com.abm.mainet.common.domain.TbCfcApplicationMstEntity;

import com.abm.mainet.common.master.service.TbDepartmentService;
import com.abm.mainet.common.service.ICFCApplicationMasterService;
import com.abm.mainet.common.service.IEmployeeService;
import com.abm.mainet.common.service.ServiceMasterService;
import com.abm.mainet.common.ui.controller.AbstractFormController;
import com.abm.mainet.common.utility.UserSession;
import com.abm.mainet.common.utility.Utility;
import com.abm.mainet.common.workflow.domain.WorkflowMas;
import com.abm.mainet.common.workflow.dto.TaskAssignment;
import com.abm.mainet.common.workflow.dto.TaskAssignmentRequest;
import com.abm.mainet.common.workflow.service.ITaskAssignmentService;
import com.abm.mainet.common.workflow.service.IWorkflowTyepResolverService;
import com.abm.mainet.rti.dto.RtiApplicationFormDetailsReqDTO;
import com.abm.mainet.rti.dto.RtiMediaListDTO;
import com.abm.mainet.rti.service.IRtiApplicationDetailService;
import com.abm.mainet.rti.ui.model.RtiLoiPrintingFormModel;
import com.abm.mainet.rti.utility.RtiUtility;

@Controller
@RequestMapping("/LoiPrintingReport.html")

public class RtiLoiPrintingFormController extends AbstractFormController<RtiLoiPrintingFormModel> {

	@Autowired
	private ICFCApplicationMasterService cfcService;

	@Autowired
	private TbLoiMasService tbloiMasService;

	@Autowired
	private ServiceMasterService serviceMasterService;

	@Autowired
	private IRtiApplicationDetailService rtiApplicationDetailService;

	@Autowired
	private TbDepartmentService departmentService;

	@Autowired
	private RtiUtility rtiUtility;

	@Autowired
	private IWorkflowTyepResolverService workflowTyepResolverService;

	@Autowired
	private ITaskAssignmentService taskAssignmentService;

	@Autowired
	private IEmployeeService employeeService;

	@RequestMapping(method = { RequestMethod.POST, RequestMethod.GET })
	public ModelAndView index(final HttpServletRequest httpServletRequest, final Model models) {
		sessionCleanup(httpServletRequest);
		Long currentOrgId = UserSession.getCurrent().getOrganisation().getOrgid();
		RtiLoiPrintingFormModel model = this.getModel();
		ServiceMaster sm = serviceMasterService.getServiceMasterByShortCode("RAF", currentOrgId);
		model.setServiceMaster(sm);
		List<TbLoiMas> loidata = tbloiMasService.getloiByLoiNo(sm.getSmServiceId(), currentOrgId);
		model.setLoidata(loidata);
		return index();
	}

	@RequestMapping(params = "PrintingReport", method = { RequestMethod.POST, RequestMethod.GET })
	public ModelAndView printLoiReport(HttpServletRequest httpServletRequest, @RequestParam Long loiNumber) {
		getModel().bind(httpServletRequest);
		Long loiApplicationId = 0l;
		RtiLoiPrintingFormModel model = this.getModel();
		Long currentOrgId = UserSession.getCurrent().getOrganisation().getOrgid();

		String amountToPay = "";
		for (TbLoiMas loiData : model.getLoidata()) {
			if (loiData.getLoiNo().equals(String.valueOf(loiNumber))) {
				loiApplicationId = loiData.getLoiApplicationId();
				RtiApplicationFormDetailsReqDTO rtiLoidto = model.getRtiInformationApplicantDto();

				rtiLoidto.setAmountToPay(String.valueOf(loiData.getLoiAmount()));
				// rtiLoidto.setEmail(email);
				amountToPay = String.valueOf(loiData.getLoiAmount());
				final String amountInWords = Utility.convertBiggerNumberToWord(loiData.getLoiAmount());
				model.setAmountInWords(amountInWords);
				model.setRtiInformationApplicantDto(rtiLoidto);
				break;
			}

		}

		TbCfcApplicationMstEntity cfcApplicationMstEntity = cfcService.getCFCApplicationByApplicationId(loiApplicationId, currentOrgId);
		model.setCfcEntity(cfcApplicationMstEntity);
		model.setCfcAddressEntity(cfcService.getApplicantsDetails(loiApplicationId));
		RtiApplicationFormDetailsReqDTO rtiDto = rtiApplicationDetailService.fetchRtiApplicationInformationById(loiApplicationId, currentOrgId);
		rtiDto.setAmountToPay(amountToPay);
		model.setRtiInformationApplicantDto(rtiDto);
		List<RtiMediaListDTO> mediDto = rtiApplicationDetailService.getMediaList(Long.valueOf(rtiDto.getRtiId()),currentOrgId);
		model.setMediaList(mediDto);

		if (cfcApplicationMstEntity.getApmApplicationDate() != null) {
			model.setDateDescription(Utility.dateToString(cfcApplicationMstEntity.getApmApplicationDate()));
		}

		model.setPageQuantity1(0l);
		model.setPageQuantity2(0l);
		model.setPageQuantity3(0l);
		for (RtiMediaListDTO dto : mediDto) {
			dto.setMediaTypeDesc(
					rtiUtility.getPrefixDesc(PrefixConstants.MEDIA_TYPE, Long.valueOf(dto.getMediaType())));

			if (dto.getMediaTypeDesc().equals("A-3") && dto.getMediaTypeDesc().equals("A-4")
					|| dto.getMediaTypeDesc().equals("A-3") || dto.getMediaTypeDesc().equals("A-4")) {
				if (dto.getQuantity() != null) {
					model.setA3a4Quantity(dto.getQuantity() + model.getA3a4Quantity());
					model.setPageQuantity1(model.getA3a4Quantity() * 2);
				}else {
					model.setPageQuantity1(0l);
				}
			}

			if (dto.getMediaTypeDesc().equals("Large Size")) {
				if (dto.getQuantity() != null) {
					model.setLargeCopy(dto.getQuantity());
					model.setPageQuantity2(model.getLargeCopy() * 3);
				}

			}
			

			if (dto.getMediaTypeDesc().equals("Floppy") && dto.getMediaTypeDesc().equals("CD")
					|| dto.getMediaTypeDesc().equals("Floppy") || dto.getMediaTypeDesc().equals("CD")) {
				if (dto.getQuantity() != null) {
					model.setFlopCopy(dto.getQuantity() + model.getFlopCopy());
					model.setPageQuantity3(model.getFlopCopy() * 50);

				} 

			}

			/*
			 * if (dto.getMediaTypeDesc().equals("A-3") &&
			 * dto.getMediaTypeDesc().equals("A-4")) { if(dto.getQuantity()!=null) {
			 * model.setA3a4Quantity(dto.getQuantity()+model.getA3a4Quantity());
			 * model.setPageQuantity1(model.getA3a4Quantity()*2); } }
			 */

			/*
			 * if(dto.getMediaTypeDesc().equals("Photo")){ Long picture=
			 * dto.getQuantity()*10; model.setA3a4Quantity(picture.toString());
			 * 
			 * }
			 */

			/*
			 * if (dto.getMediaTypeDesc().equals("A-3") &&
			 * dto.getMediaTypeDesc().equals("A-4") || dto.getMediaTypeDesc().equals("A-3")
			 * || dto.getMediaTypeDesc().equals("A-4")) { if (dto.getQuantity() != null) {
			 * model.setA3a4Quantity((dto.getQuantity() + model.getA3a4Quantity()));
			 * model.setPageQuantity1(model.getA3a4Quantity() * 2); } }
			 */

			if (dto.getMediaTypeDesc().equals("Model/Sample")) {
				if (dto.getQuantity() != null) {
					model.setInspection(dto.getQuantity());
					Long inspect = model.getInspection() * 500;
					model.setPageQuantity4(model.getInspection() * 500);

				}
			}else {
				model.setPageQuantity4(0l);
			}

			
			  Long total = 0l;
			  
			  
			  if(model.getPageQuantity1() != null) { 
				  total=model.getPageQuantity1(); 
				  }
			  if(model.getPageQuantity2() != null) { 
				  total=total+model.getPageQuantity2();
			  }
			  if(model.getPageQuantity3() != null) {
			  total=total+model.getPageQuantity3(); 
			  } 
			  if(model.getPageQuantity4() != null)
			  { total=total+model.getPageQuantity4(); 
			  } 
			  model.setGrandTotal(total) ;
			 

			/*
			 * model.setGrandTotal(model.getPageQuantity1()+model.getPageQuantity2()+model.
			 * getPageQuantity3());
			 */
		}

		Department dept = departmentService.findDepartmentByCode("RTI");
		model.setDepartment(dept);

		WorkflowMas workflowMas = workflowTyepResolverService.resolveServiceWorkflowType(currentOrgId,
				dept.getDpDeptid(), model.getServiceMaster().getSmServiceId(), null, null, null, null, null);

		Long group = 1l;

		TaskAssignmentRequest taskDto = new TaskAssignmentRequest();
		taskDto.setWorkflowTypeId(workflowMas.getWfId());
		taskDto.setServiceEventName("PIO Response");
		LinkedHashMap<String, LinkedHashMap<String, TaskAssignment>> hashmap = taskAssignmentService
				.getEventLevelGroupsByWorkflowTypeAndEventName(taskDto);

		LinkedHashMap<String, TaskAssignment> grp1 = hashmap
				.get(MainetConstants.GROUP + MainetConstants.operator.UNDER_SCORE + group);

		for (int j = 1; j <= grp1.size(); j++) {
			TaskAssignment ta = grp1.get(MainetConstants.LEVEL + MainetConstants.operator.UNDER_SCORE + j);

			List<Long> actorIdList = new ArrayList<>();
			String[] empIds = ta.getActorId().split(MainetConstants.operator.COMMA);
			for (String s : empIds) {
				actorIdList.add(Long.valueOf(s));
			}
			if (!actorIdList.isEmpty()) {
				String emp = MainetConstants.BLANK;
				List<Employee> empList = employeeService.getEmpDetailListByEmpIdList(actorIdList, currentOrgId);

				for (Employee employee : empList) {
					emp += employee.getEmpname() + MainetConstants.WHITE_SPACE + employee.getEmpmname()
							+ MainetConstants.WHITE_SPACE + employee.getEmplname();
				}
			 	model.setPioName(emp);
			}
		}

		return new ModelAndView("RtiLoiPrintingFormSheet", MainetConstants.FORM_NAME, getModel());

	}
}
