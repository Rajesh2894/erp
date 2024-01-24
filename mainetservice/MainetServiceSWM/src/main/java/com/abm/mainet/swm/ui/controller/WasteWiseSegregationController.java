package com.abm.mainet.swm.ui.controller;

import java.util.Map;
import java.util.stream.Collectors;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.abm.mainet.common.constant.MainetConstants;
import com.abm.mainet.common.ui.controller.AbstractFormController;
import com.abm.mainet.common.utility.UserSession;
import com.abm.mainet.common.utility.Utility;
import com.abm.mainet.swm.dto.MRFMasterDto;
import com.abm.mainet.swm.dto.WastageSegregationDTO;
import com.abm.mainet.swm.dto.WastageSegregationDetailsDTO;
import com.abm.mainet.swm.service.IMRFMasterService;
import com.abm.mainet.swm.service.IWastageSegregationService;
import com.abm.mainet.swm.ui.model.WasteWiseSegregationModel;

/**
 * @author Ajay.Kumar
 *
 */
@Controller
@RequestMapping("/WasteWiseSegregation.html")
public class WasteWiseSegregationController extends AbstractFormController<WasteWiseSegregationModel> {
	/**
	 * IWastageSegregation Service
	 */
	@Autowired
	private IWastageSegregationService wastageSegregationService;

	/**
	 * IMRFMaster Service
	 */
	@Autowired
	private IMRFMasterService imRFMasterService;

	/**
	 * index
	 * @param httpServletRequest
	 * @return
	 */
	@RequestMapping(method = { RequestMethod.POST })
	public ModelAndView index(final HttpServletRequest httpServletRequest) {
		sessionCleanup(httpServletRequest);
		this.getModel().setCommonHelpDocs("WasteWiseSegregation.html");
		getMrfCenterName(httpServletRequest);
		return index();
	}

	private void loadMrfCenterName(final HttpServletRequest httpServletRequest) {
		this.getModel().setmRFMasterDtoList(imRFMasterService.serchMRFMasterByPlantIdAndPlantname(null, null,
				UserSession.getCurrent().getOrganisation().getOrgid()));
	}

	private void getMrfCenterName(final HttpServletRequest httpServletRequest) {
		loadMrfCenterName(httpServletRequest);
		Map<Long, String> mrfCenterNameMap = this.getModel().getmRFMasterDtoList().stream()
				.collect(Collectors.toMap(MRFMasterDto::getMrfId, MRFMasterDto::getMrfPlantName));
		this.getModel().getmRFMasterDtoList().forEach(master -> {
			master.setMrfPlantName(mrfCenterNameMap.get(master.getMrfId()));
		});
		this.getModel().getmRFMasterDtoList();
	}

	/**
	 * day Wise Dumping Summary
	 * @param request
	 * @param deId
	 * @param fromDate
	 * @param codWast1
	 * @param codWast2
	 * @param codWast3
	 * @param toDate
	 * @return
	 */
	@ResponseBody
	@RequestMapping(params = "report", method = RequestMethod.POST)
	public ModelAndView dayWiseDumpingSummary(final HttpServletRequest request, @RequestParam("deId") Long deId,
			@RequestParam("fromDate") String fromDate, @RequestParam("codWast1") Long codWast1,
			@RequestParam("codWast2") Long codWast2, @RequestParam("codWast3") Long codWast3,
			@RequestParam("toDate") String toDate) {
		//sessionCleanup(request);
		WasteWiseSegregationModel wWSModel = this.getModel();
		String redirectType = null;
		Long OrgId = UserSession.getCurrent().getOrganisation().getOrgid();
		WastageSegregationDTO wastageSegregationDTO = wastageSegregationService.findWastageSegregation(OrgId, deId, codWast1,
				codWast2, codWast3,
				Utility.stringToDate(fromDate), Utility.stringToDate(toDate));
		
		if (wastageSegregationDTO != null) {
			this.getModel().setWastageSegregationDto(wastageSegregationDTO);
			if (deId != 0) {
				if (this.getModel().getWastageSegregationDto().getWastageSegregationList() != null
						&& !this.getModel().getWastageSegregationDto().getWastageSegregationList().isEmpty()) {
					for (WastageSegregationDTO det : this.getModel().getWastageSegregationDto().getWastageSegregationList()) {
						wastageSegregationDTO.setdName(det.getdName());
						break;
					}
				}
			}

			wastageSegregationDTO.setFromDate(fromDate);
			wastageSegregationDTO.setToDate(toDate);
			wastageSegregationDTO.setFlagMsg("Y");
			this.getModel().setWastageSeg(wastageSegregationDTO);
			redirectType = "WasteWiseSegregationSummary";
		} else {
			WastageSegregationDTO wastageSegregationDto = this.getModel().getWastageSegregationDto();
			WastageSegregationDetailsDTO wastageSegregationDet = this.getModel().getWastageSegregationDet();
			wastageSegregationDto.setDeId(deId);
			wastageSegregationDto.setFromDate(fromDate);
			wastageSegregationDto.setToDate(toDate);
			wastageSegregationDet.setCodWast1(codWast1);
			wastageSegregationDet.setCodWast2(codWast2);
			wastageSegregationDet.setCodWast3(codWast3);
			this.getModel().setWastageSeg(wastageSegregationDto);
			this.getModel().setWastageSegregationDet(wastageSegregationDet);
			redirectType = "WasteWiseSegregationList";
		}
		return new ModelAndView(redirectType, MainetConstants.FORM_NAME, wWSModel);
	}
}
