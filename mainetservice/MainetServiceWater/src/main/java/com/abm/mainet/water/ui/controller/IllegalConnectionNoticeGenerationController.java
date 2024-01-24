/**
 * 
 */
package com.abm.mainet.water.ui.controller;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.json.MappingJackson2JsonView;

import com.abm.mainet.common.constant.MainetConstants;
import com.abm.mainet.common.domain.Designation;
import com.abm.mainet.common.dto.ApplicantDetailDTO;
import com.abm.mainet.common.master.service.DesignationService;
import com.abm.mainet.common.master.ui.validator.FileUploadServiceValidator;
import com.abm.mainet.common.service.IEmployeeService;
import com.abm.mainet.common.ui.controller.AbstractFormController;
import com.abm.mainet.common.utility.ApplicationSession;
import com.abm.mainet.common.utility.CommonMasterUtility;
import com.abm.mainet.common.utility.UserSession;
import com.abm.mainet.water.domain.PlumberMaster;
import com.abm.mainet.water.dto.NewWaterConnectionReqDTO;
import com.abm.mainet.water.dto.TbCsmrInfoDTO;
import com.abm.mainet.water.service.NewWaterConnectionService;
import com.abm.mainet.water.service.WaterCommonService;
import com.abm.mainet.water.ui.model.IllegalConnectionNoticeGenerationModel;

/**
 * @author Saiprasad.Vengurlekar
 *
 */

@Controller
@RequestMapping("/IllegalConnectionNoticeGeneration.html")
public class IllegalConnectionNoticeGenerationController
		extends AbstractFormController<IllegalConnectionNoticeGenerationModel> {

	@Autowired
	WaterCommonService waterCommonService;

	@Autowired
	DesignationService designationService;

	@Autowired
	IEmployeeService employeeService;

	@Autowired
	private NewWaterConnectionService newWaterConnectionService;

	@RequestMapping(method = { RequestMethod.GET, RequestMethod.POST })
	public ModelAndView index(final HttpServletRequest httpServletRequest) {
		sessionCleanup(httpServletRequest);
		FileUploadServiceValidator.getCurrent().sessionCleanUpForFileUpload();
		IllegalConnectionNoticeGenerationModel model = this.getModel();
		final Long orgId = UserSession.getCurrent().getOrganisation().getOrgid();
		model.setCommonHelpDocs("IllegalConnectionNoticeGeneration.html");
		model.getSearchDTO().setOrgId(orgId);
		model.setNoticeList(newWaterConnectionService.getAllIllegalConnectionNotice(model.getSearchDTO()));
		model.setPlumberList(waterCommonService.listofplumber(orgId));
		Designation dsg = designationService.findByShortname("PLM");
		List<PlumberMaster> plumberList = new ArrayList<>();
		PlumberMaster master = null;
		if (dsg != null) {

			List<Object[]> empList = employeeService.getAllEmpByDesignation(dsg.getDsgid(), orgId);
			if (!empList.isEmpty()) {
				for (final Object empObj[] : empList) {
					master = new PlumberMaster();
					master.setPlumId(Long.valueOf(empObj[0].toString()));
					master.setPlumFname(empObj[1].toString());
					master.setPlumMname(empObj[2].toString());
					master.setPlumLname(empObj[3].toString());
					plumberList.add(master);
				}

			}
		}
		model.setUlbPlumberList(plumberList);
		return defaultResult();
	}

	@RequestMapping(method = RequestMethod.POST, params = "createNotice")
	public ModelAndView physicalMilestoneCreate(
			@RequestParam(name = MainetConstants.WorksManagement.MODE, required = false) String mode,
			final HttpServletRequest request) {
		IllegalConnectionNoticeGenerationModel model = this.getModel();
		model.setSaveMode(MainetConstants.MODE_CREATE);
		return new ModelAndView("IllegalNoticeForm", MainetConstants.FORM_NAME, model);
	}

	@RequestMapping(params = "getPropertyDetails", method = RequestMethod.POST)
	public ModelAndView getPropertyDetails(HttpServletRequest request) {
		bindModel(request);
		IllegalConnectionNoticeGenerationModel model = this.getModel();
		NewWaterConnectionReqDTO reqDTO = model.getReqDTO();
		reqDTO.setOrgId(UserSession.getCurrent().getOrganisation().getOrgid());
		reqDTO.setPropertyNo(model.getCsmrInfo().getPropertyNo());
		TbCsmrInfoDTO infoDTO = model.getCsmrInfo();
		TbCsmrInfoDTO propInfoDTO = model.getPropertyDetailsByPropertyNumber(reqDTO);
		String respMsg = "";
		if (propInfoDTO != null) {
			infoDTO.setCsOname(propInfoDTO.getCsOname());
			infoDTO.setCsOcontactno(propInfoDTO.getCsOcontactno());
			infoDTO.setCsOEmail(propInfoDTO.getCsOEmail());
			infoDTO.setOpincode(propInfoDTO.getOpincode());
			infoDTO.setCsOadd(propInfoDTO.getCsOadd());
			infoDTO.setPropertyUsageType(propInfoDTO.getPropertyUsageType());
			if (propInfoDTO.getCsOGender() != null && propInfoDTO.getCsOGender() != 0l) {
				infoDTO.setCsOGender(propInfoDTO.getCsOGender());
			}
			reqDTO.setApplicantDTO(new ApplicantDetailDTO());
			infoDTO.setPropertyNo(propInfoDTO.getPropertyNo());
			infoDTO.setTotalOutsatandingAmt(propInfoDTO.getTotalOutsatandingAmt());
			if (infoDTO.getTotalOutsatandingAmt() > 0) {
				model.setPropOutStanding(MainetConstants.FlagY);
			} else {
				model.setPropOutStanding(MainetConstants.FlagN);
			}
		} else {
			respMsg = ApplicationSession.getInstance().getMessage("water.dataentry.validation.property.not.found");
			return new ModelAndView(new MappingJackson2JsonView(), MainetConstants.ERR_MSG, respMsg);
		}
		return defaultMyResult();
	}

	@ResponseBody
	@RequestMapping(params = "searchNotices", method = RequestMethod.POST)
	public List<TbCsmrInfoDTO> getAllIllegalConnectionNotice(HttpServletRequest request) {
		bindModel(request);
		return newWaterConnectionService.getAllIllegalConnectionNotice(this.getModel().getSearchDTO());
	}

	@RequestMapping(params = "IllegalConnectionNoticePrint", method = RequestMethod.POST)
	public ModelAndView illegalConnectionNoticePrint(@RequestParam(name = "csId", required = false) Long csId,
			@RequestParam(name = MainetConstants.WorksManagement.MODE, required = false) String mode,
			final HttpServletRequest request) {
		bindModel(request);
		final IllegalConnectionNoticeGenerationModel model = getModel();
		TbCsmrInfoDTO csmrInfo = newWaterConnectionService.getConnectionDetailsById(csId);
		if (csmrInfo != null) {
			csmrInfo.setIllegalNoticeDate(
					(new SimpleDateFormat(MainetConstants.DATE_FORMAT).format(csmrInfo.getCsIllegalNoticeDate())));
			csmrInfo.setIllegalDate(
					(new SimpleDateFormat(MainetConstants.DATE_FORMAT).format(csmrInfo.getCsIllegalDate())));
			csmrInfo.setConnectionSize(Double.valueOf(CommonMasterUtility
					.getNonHierarchicalLookUpObject(csmrInfo.getCsCcnsize(), UserSession.getCurrent().getOrganisation())
					.getDescLangFirst()));
			model.setCsmrInfo(csmrInfo);
		}

		return new ModelAndView("IllegalConnectionNoticePrint", MainetConstants.FORM_NAME, model);
	}

	@RequestMapping(method = RequestMethod.POST, params = "editNotice")
	public ModelAndView editNotice(@RequestParam(name = "csId", required = false) Long csId,
			@RequestParam(name = MainetConstants.WorksManagement.MODE, required = false) String mode,
			final HttpServletRequest request) {
		IllegalConnectionNoticeGenerationModel model = this.getModel();
		model.setCsmrInfo(newWaterConnectionService.getConnectionDetailsById(csId));
		model.setSaveMode(mode);
		return new ModelAndView("IllegalNoticeForm", MainetConstants.FORM_NAME, model);
	}

}
