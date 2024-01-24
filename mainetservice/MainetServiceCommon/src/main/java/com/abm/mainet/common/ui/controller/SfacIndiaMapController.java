/**
 * 
 */
package com.abm.mainet.common.ui.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.abm.mainet.common.constant.MainetConstants;
import com.abm.mainet.common.dto.DistrictLocationDto;
import com.abm.mainet.common.service.IndiaMapService;
import com.abm.mainet.common.ui.model.SfacIndiaMapModel;
import com.abm.mainet.common.utility.CommonMasterUtility;
import com.abm.mainet.common.utility.LookUp;
import com.abm.mainet.common.utility.UserSession;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

/**
 * @author pooja.maske
 *
 */
@Controller
@RequestMapping("SfacIndiaMap.html")
public class SfacIndiaMapController extends AbstractFormController<SfacIndiaMapModel>{

	@Autowired IndiaMapService indiaMapService;

	private static final Logger LOG = LoggerFactory.getLogger(SfacIndiaMapController.class);

	@RequestMapping(method = { RequestMethod.POST, RequestMethod.GET })
	@ResponseBody
	public ModelAndView index(final Model uiModel, final HttpServletRequest httpServletRequest) {
		sessionCleanup(httpServletRequest);
		this.getModel().setCommonHelpDocs("SfacIndiaMap.html");
		final List<LookUp> stateList = CommonMasterUtility.getLevelData("SDB", 1,
				UserSession.getCurrent().getOrganisation());
		populateModel();
		this.getModel().setViewMode("VACANT");


		this.getModel().setStateList(stateList);
		return new ModelAndView("SfacIndiaMap", MainetConstants.FORM_NAME, getModel());
	}

	private void populateModel() {
		HashMap<Integer, List<DistrictLocationDto>> vacant =  indiaMapService.getVacantBlockData();

	HashMap<Integer, List<DistrictLocationDto>> allocated = indiaMapService.getAllocatedBlackList();
	Integer vCount  =0;
	Integer aCount = 0;
	ObjectMapper om = new ObjectMapper();

	for (Map.Entry<Integer, List<DistrictLocationDto>> entry : vacant.entrySet()) {
		vCount = entry.getKey();
		this.getModel().setVacantBlockList(entry.getValue());
		try {
			this.getModel().setVacantJson(om.writeValueAsString(entry.getValue()));
		} catch (JsonProcessingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	for (Map.Entry<Integer, List<DistrictLocationDto>> entry : allocated.entrySet()) {
		aCount = entry.getKey();
		this.getModel().setAllocatedBlockList(entry.getValue());
		try {
			this.getModel().setAlocatedJson(om.writeValueAsString(entry.getValue()));
		} catch (JsonProcessingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	this.getModel().setTotalBlock(aCount+vCount);
	
	this.getModel().setAllocatedCount(aCount);
	this.getModel().setVacantCount(vCount);

	this.getModel().setVacantBlockMap(vacant);
	this.getModel().setAllocatedBlockMap(allocated);
	}

	@RequestMapping(params = "getDistrictList", method = { RequestMethod.POST })
	@ResponseBody
	public List<LookUp> getDistrictListByStateId(@RequestParam("stateId") Long stateId, HttpServletRequest request) {
		List<LookUp> lookUpList1 = new ArrayList<LookUp>();
		this.getModel().getDto().setStateId(stateId);
		try {
			List<LookUp> lookUpList = CommonMasterUtility.getLevelData("SDB", 2,
					UserSession.getCurrent().getOrganisation());
			lookUpList1 = lookUpList.stream().filter(lookUp -> lookUp.getLookUpParentId() == stateId)
					.collect(Collectors.toList());
			this.getModel().setDistrictList(lookUpList1);
			return lookUpList1;
		} catch (Exception e) {
			LOG.error("SDB Prefix not found");
			return lookUpList1;
		}
	}

	

	@RequestMapping(method = { RequestMethod.POST }, params = "districtDetail")
	public ModelAndView districtDetail(Long distId, final HttpServletRequest httpServletRequest) {

		//populateModel();
		Integer vCount  =0;
		Integer aCount = 0;
		ObjectMapper om = new ObjectMapper();
		HashMap<Integer, List<DistrictLocationDto>> vacant =  indiaMapService.getVacantList(distId);

		HashMap<Integer, List<DistrictLocationDto>> allocated = indiaMapService.getAllocatedList(distId);

		for (Map.Entry<Integer, List<DistrictLocationDto>> entry : vacant.entrySet()) {
			vCount = entry.getKey();
			this.getModel().setVacantBlockList(entry.getValue());
			try {
				this.getModel().setVacantJson(om.writeValueAsString(entry.getValue()));
			} catch (JsonProcessingException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		for (Map.Entry<Integer, List<DistrictLocationDto>> entry : allocated.entrySet()) {
			aCount = entry.getKey();
			this.getModel().setAllocatedBlockList(entry.getValue());
			try {
				this.getModel().setAlocatedJson(om.writeValueAsString(entry.getValue()));
			} catch (JsonProcessingException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		this.getModel().setAllocatedCount(aCount);
		this.getModel().setVacantCount(vCount);
		this.getModel().setTotalBlock(aCount+vCount);
		this.getModel().setIaList(indiaMapService.getIaList(distId));
		this.getModel().setCbboList(indiaMapService.getCbboList(distId));
		this.getModel().setFpoList(indiaMapService.getFpoList(distId));
		this.getModel().setVacantBlockMap(vacant);
		this.getModel().getDto().setDistId(distId);
		this.getModel().setAllocatedBlockMap(allocated);
		this.getModel().setTotalBlockDist(indiaMapService.getTotalBlock(distId));
		this.getModel().setViewMode("VACANT");
		
		List<LookUp> lookUpList = CommonMasterUtility.getLevelData("SDB", 3,
				UserSession.getCurrent().getOrganisation());
		List<LookUp> lookUpList1 = lookUpList.stream().filter(lookUp -> lookUp.getLookUpParentId() == distId)
				.collect(Collectors.toList());
		this.getModel().setBlockList(lookUpList1);

		return new ModelAndView("SfacIndiaMapValidIn", MainetConstants.FORM_NAME, getModel());
	}
	
	
	@RequestMapping(method = { RequestMethod.POST }, params = "blockDetail")
	public ModelAndView blockDetail(Long blockId, final HttpServletRequest httpServletRequest) {

		//populateModel();
		Integer vCount  =0;
		Integer aCount = 0;
		ObjectMapper om = new ObjectMapper();
		HashMap<Integer, List<DistrictLocationDto>> vacant =  indiaMapService.getVacantListByBlockId(blockId);

		HashMap<Integer, List<DistrictLocationDto>> allocated = indiaMapService.getAllocatedListByBlockId(blockId);

		for (Map.Entry<Integer, List<DistrictLocationDto>> entry : vacant.entrySet()) {
			vCount = entry.getKey();
			this.getModel().setVacantBlockList(entry.getValue());
			try {
				this.getModel().setVacantJson(om.writeValueAsString(entry.getValue()));
			} catch (JsonProcessingException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		for (Map.Entry<Integer, List<DistrictLocationDto>> entry : allocated.entrySet()) {
			aCount = entry.getKey();
			this.getModel().setAllocatedBlockList(entry.getValue());
			try {
				this.getModel().setAlocatedJson(om.writeValueAsString(entry.getValue()));
			} catch (JsonProcessingException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		this.getModel().setAllocatedCount(aCount);
		this.getModel().setVacantCount(vCount);
		this.getModel().setTotalBlock(aCount+vCount);
		this.getModel().setIaList(indiaMapService.getIaListByBlock(blockId));
		this.getModel().setCbboList(indiaMapService.getCbboListByBlock(blockId));
		this.getModel().setFpoList(indiaMapService.getFpoListByBlock(blockId));
		this.getModel().setVacantBlockMap(vacant);
		this.getModel().getDto().setBlockId(blockId);
		this.getModel().setAllocatedBlockMap(allocated);
		this.getModel().setTotalBlockDist(indiaMapService.getTotalBlockBlockId(blockId));
		this.getModel().setViewMode("VACANT");
		
		
		return new ModelAndView("SfacIndiaMapValidIn", MainetConstants.FORM_NAME, getModel());
	}


}
