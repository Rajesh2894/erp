package com.abm.mainet.cms.ui.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.abm.mainet.cms.dto.ItmsUpdateDTO;
import com.abm.mainet.cms.dto.MDDAApiResponseDto;
import com.abm.mainet.cms.dto.NewUPDCLEleConDTO;
import com.abm.mainet.cms.dto.TraficUpdateDTO;
import com.abm.mainet.cms.ui.model.DoonIntegrationModel;
import com.abm.mainet.common.constant.MainetConstants;
import com.abm.mainet.common.ui.controller.AbstractFormController;
import com.abm.mainet.dms.service.FileUploadServiceValidator;


@Controller
@RequestMapping("/DoonIntegration.html")
public class DoonIntegrationController extends AbstractFormController<DoonIntegrationModel> {
	private static final Logger LOG = Logger.getLogger(DoonIntegrationController.class);

	@RequestMapping(params = "getEventSensorData")
	public @ResponseBody Map<String, Object> getServiceList(@RequestParam("sensorLocCode") final String sensorLocCode) {
		Map<String, Object> response = null;
		response = getModel().getEnvSensorUpdate(sensorLocCode);
		if(response ==null) {
			response = new HashMap<String, Object>();
		}
		return response;
	}

	@RequestMapping(params = "getEchallanById")
	public @ResponseBody List<Map<String, String>> getEchallanById(
			@RequestParam("echallanId") final String echallanId) {
		List<Map<String, String>> response = null;
		response = getModel().getEchallanById(echallanId);
		if(response ==null) {
			response = new ArrayList<Map<String,String>>();
		}

		return response;
	}

	@RequestMapping(params = "getTrafficUpdate")
	public @ResponseBody List<TraficUpdateDTO> getTrafficUpdate() {
		List<TraficUpdateDTO> traficDtos = new ArrayList<TraficUpdateDTO>();
		traficDtos = getModel().getTrafficUpdate();
		return traficDtos;
	}

	@RequestMapping(params = "getItmsUpdate")
	public @ResponseBody List<ItmsUpdateDTO> getItmsUpdate() {
		List<ItmsUpdateDTO> itmsDtos = new ArrayList<ItmsUpdateDTO>();
		itmsDtos = getModel().getItmsUpdate();
		return itmsDtos;
	}
	@RequestMapping(params = "getMDDAApiResponse")
	public @ResponseBody List<MDDAApiResponseDto> getMDDAApiResponse() {
		List<MDDAApiResponseDto> mDDAApiResponseDto=new ArrayList<>();
		mDDAApiResponseDto = getModel().getMDDAApiResponse();
		return mDDAApiResponseDto;
	}

	@RequestMapping(params = "getTotalMDDA")
	public @ResponseBody Map<String, Integer> getTotalMDDA() {
		return getModel().getTotalMDDA();
	}

	@RequestMapping(params = "getDsclBillDetData")
	public @ResponseBody List<Map<String, Object>>  getDsclBillDetData(@RequestParam("AccountNo") String AccountNo,@RequestParam("UserKey") String UserKey){
		List<Map<String, Object>>  responseMap = new ArrayList<Map<String, Object>>();
		responseMap =getModel().getDsclBillDetData(AccountNo,UserKey);		
		return responseMap;
	}


	@RequestMapping(method = RequestMethod.GET, params = "NewUPDCLEleCon")
	public ModelAndView newUPDCLEleCon(HttpServletRequest request) {
		FileUploadServiceValidator.getCurrent().sessionCleanUpForFileUpload();
		sessionCleanup(request);
		return new ModelAndView("NewUPDCLEleCon", MainetConstants.FORM_NAME, getModel());
	}

	@RequestMapping(params = "saveNewElecon1", method = { RequestMethod.POST })
	public @ResponseBody List<String> saveNewElecon1(final Model model, final HttpServletRequest request) {
		bindModel(request);
		final DoonIntegrationModel doonModel = this.getModel();
		NewUPDCLEleConDTO newUPDCLEleConDTO  = doonModel.getNewUPDCLEleConDTO();
		getModel().saveUpdclNewEleCon(newUPDCLEleConDTO);
		List<String> errList = new ArrayList<String>();
		for (ObjectError e : doonModel.getBindingResult().getAllErrors()) {
			errList.add(e.getDefaultMessage());
		}
		return errList;
	}

	List<String> validateNewUpdclCon(NewUPDCLEleConDTO newUPDCLEleConDTO){
		List<String> errList = new ArrayList<String>();
		return errList;
	}
	@RequestMapping(params = "getDoonWaterDetails")
	public @ResponseBody String getDoonWaterDetails(
			@RequestParam("connectionNo") final String connectionNo) {
		String response = null;
		response = getModel().getDoonWaterDetails(connectionNo);

		return response;
	}


	@RequestMapping(params = "getEBusServiceDetails",method = RequestMethod.POST)
	public @ResponseBody String getEBusServiceDetails() {
		String response = null;
		response = getModel().getEbusServiceDet();
		if(response!=null && !response.isEmpty()) {
			return response;
		}
		return response;
	}

	@RequestMapping(params = "getDistinctStops",method = RequestMethod.GET)
	public @ResponseBody List<Object> getDistinctStopsDet(@RequestParam("accessToken") final String accessToken) {
		List<Object> response = null;
		response = getModel().getDistinctStopsDetails(accessToken);
		return response;
	}

	@RequestMapping(params = "getRoutes",method = RequestMethod.GET)
	public @ResponseBody List<Object> getRoutesDet(@RequestParam("accessToken") final String accessToken) {
		List<Object> response = null;
		response = getModel().getRoutesDetails(accessToken);
		return response;
	}

	@RequestMapping(params = "getNearByStops",method = RequestMethod.POST)
	public @ResponseBody List<Object> getNearByStopsDet(@RequestParam("accessToken") final String accessToken,
			@RequestParam("latitude") final String latitude,@RequestParam("longitude") final String longitude,@RequestParam("radius") final String radius) {
		List<Object> response = null;
		response = getModel().getNearByStopsDetails(accessToken,latitude,longitude,radius);
		return response;
	}

	@RequestMapping(params = "getRouteTypes",method = RequestMethod.POST)
	public @ResponseBody List<Object> getRouteTypes(@RequestParam("accessToken") final String accessToken,
			@RequestParam("routeName") final String routeName) {
		List<Object> response = null;
		response = getModel().getRouteTypesDetails(accessToken,routeName);

		return response;
	}

	@RequestMapping(params = "getEnroutedDetails",method = RequestMethod.POST)
	public @ResponseBody List<Object> getEnrouteDetails(@RequestParam("accessToken") final String accessToken,
			@RequestParam("sourceId") final String sourceId,@RequestParam("destinationId") final String destinationId,@RequestParam("routeId") final String routeId) {
		List<Object> response = null;
		response = getModel().getEnrouteDetails(accessToken,sourceId,destinationId,routeId);
		return response;
	}

	@RequestMapping(params = "getArrivingBusesList",method = RequestMethod.POST)
	public @ResponseBody List<Object> getArrivingBusesList(@RequestParam("accessToken") final String accessToken,@RequestParam("stopId") final String stopId) {
		List<Object> response = null;
		response = getModel().getArrivingBusesList(accessToken,stopId);		
		return response;
	}

	@RequestMapping(params = "getTripPlannerDetails",method = RequestMethod.POST)
	public @ResponseBody List<Object> getTripPlannerDetails(@RequestParam("accessToken") final String accessToken, @RequestParam("source") final String source,@RequestParam("destination") final String destination,@RequestParam("time") final String time) {
		List<Object> response = null;
		response = getModel().getTripPlannerDetails(accessToken,source,destination,time);		
		return response;
	}

	@RequestMapping(params = "getFareDetails",method = RequestMethod.POST)
	public @ResponseBody List<Object> getFareDetails(@RequestParam("accessToken") final String accessToken, @RequestParam("srcId") final String srcId,@RequestParam("destId") final String destId,@RequestParam("routeId") final String routeId) {
		List<Object> response = null;
		response = getModel().getFareDetails(accessToken,srcId,destId,routeId);		
		return response;
	}

	@RequestMapping(params = "getTimeTableDetails",method = RequestMethod.POST)//proper data is not fatching
	public @ResponseBody Map<String, List> getTimeTableDetails(@RequestParam("accessToken") final String accessToken, @RequestParam("routeId") final String routeId,@RequestParam("time") final String time) {
		Map<String, List> response = null;
		response = getModel().getTimeTableDetails(accessToken,routeId,time);		
		return response;
	}

	@RequestMapping(params = "getRouteDetails",method = RequestMethod.POST)
	public @ResponseBody List<Object> getRouteDetails(@RequestParam("accessToken") final String accessToken, @RequestParam("routeId") final String routeId) {
		List<Object> response = null;
		response = getModel().getRouteDetails(accessToken,routeId);		
		return response;
	}

	@RequestMapping(params = "getScheduledTripDetails",method = RequestMethod.POST)
	public @ResponseBody List<Object> getScheduledTripDetails(@RequestParam("accessToken") final String accessToken, @RequestParam("routeId") final String routeId) {
		List<Object> response = null;
		response = getModel().getScheduledTripDetails(accessToken,routeId);		
		return response;
	}

	@RequestMapping(params = "getWayPointsDetails",method = RequestMethod.POST)
	public @ResponseBody List<Object> getWayPointsDetails(@RequestParam("accessToken") final String accessToken, @RequestParam("routeId") final String routeId, @RequestParam("fleetId") final String fleetId) {
		List<Object> response = null;
		response = getModel().getWayPointsDetails(accessToken,routeId,fleetId);		
		return response;
	}

	@RequestMapping(params = "getIncidentSubTypes",method = RequestMethod.GET)
	public @ResponseBody List<Object> getIncidentSubTypes(@RequestParam("accessToken") final String accessToken) {
		List<Object> response = null;
		response = getModel().getIncidentSubTypes(accessToken);		
		return response;
	} 


}
