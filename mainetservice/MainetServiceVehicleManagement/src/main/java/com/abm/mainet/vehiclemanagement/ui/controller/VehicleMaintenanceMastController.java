package com.abm.mainet.vehiclemanagement.ui.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

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
import com.abm.mainet.common.domain.Employee;
import com.abm.mainet.common.domain.Organisation;
import com.abm.mainet.common.ui.controller.AbstractFormController;
import com.abm.mainet.common.utility.UserSession;
import com.abm.mainet.common.utility.Utility;
import com.abm.mainet.vehiclemanagement.service.IGenVehicleMasterService;
import com.abm.mainet.vehiclemanagement.service.IVehicleMaintenanceMasterService;
import com.abm.mainet.vehiclemanagement.ui.model.VehiclMaintenanceMasterModel;

/**
 * @author Ajay.Kumar
 *
 */
@Controller
@RequestMapping("/vehicleMaintenanceMasController.html")
public class VehicleMaintenanceMastController extends AbstractFormController<VehiclMaintenanceMasterModel> {

    @Autowired
    private IVehicleMaintenanceMasterService vehicleMaintenanceMasterService;
    
    @Autowired
	private IGenVehicleMasterService vehicleMasterService;

    /**
     * index
     * @param httpServletRequest
     * @return
     */
    @RequestMapping(method = { RequestMethod.POST, RequestMethod.GET })
    public ModelAndView index(final HttpServletRequest httpServletRequest,  Model model) {
        sessionCleanup(httpServletRequest);  
        Organisation organisation = UserSession.getCurrent().getOrganisation();
        this.getModel().setVehicleMaintenanceList(vehicleMaintenanceMasterService.serchVehicleMaintenance(null, null, organisation.getOrgid()));
        this.getModel().setCommonHelpDocs("VehicleMaintenanceMaster.html");
        if(Utility.isEnvPrefixAvailable(organisation, MainetConstants.ENV_PSCL))
        	mapVehicleIdAndNumber(organisation.getOrgid());

        return index();
    }

    /**
     * addPopulationForm
     * @param request
     * @return
     */
    @ResponseBody
    @RequestMapping(params = "AddVehicleMaintenanceMaster", method = RequestMethod.POST)
    public ModelAndView addPopulationForm(final HttpServletRequest request, Model model) {
    	sessionCleanup(request);
    	this.getModel().setSaveMode(MainetConstants.FlagA);
    	Organisation organisation = UserSession.getCurrent().getOrganisation();
    	if(Utility.isEnvPrefixAvailable(organisation, MainetConstants.ENV_PSCL))
    		this.getModel().setVehicleObjectList(vehicleMasterService.getUlbActiveVehiclesForMaintMasterAdd(organisation.getOrgid()));
        return new ModelAndView("addVehicleMaintenanceMast/Form", MainetConstants.FORM_NAME, this.getModel());
    }

    /**
     * editPopulationForm
     * @param request
     * @param vemeId
     * @return
     */
    @ResponseBody
    @RequestMapping(params = "editVehicleMaintenanceMaster", method = RequestMethod.POST)
    public ModelAndView editPopulationForm(final HttpServletRequest request, @RequestParam Long vemeId) {
        this.getModel().setVehicleMaintenanceMasterDTO(vehicleMaintenanceMasterService.getVehicleMaintenanceMaster(vemeId));
        if(Utility.isEnvPrefixAvailable(UserSession.getCurrent().getOrganisation(), MainetConstants.ENV_PSCL))
			this.getModel().getVehicleMaintenanceMasterDTO().setVeNo(vehicleMasterService
					.fetchVehicleNoByVeId(this.getModel().getVehicleMaintenanceMasterDTO().getVeId()));
        return new ModelAndView("editVehicleMaintenanceMast/Form", MainetConstants.FORM_NAME, this.getModel());

    }

    /**
     * search Vehicle Maintenance Master
     * @param request
     * @param vemeId
     * @return
     */
    @ResponseBody
    @RequestMapping(params = "viewVehicleMaintenanceMaster", method = RequestMethod.POST)
    public ModelAndView viewVehicleMaintenanceMaster(final HttpServletRequest request, @RequestParam Long vemeId) {
        this.getModel().setVehicleMaintenanceMasterDTO(vehicleMaintenanceMasterService.getVehicleMaintenanceMaster(vemeId));
        if(Utility.isEnvPrefixAvailable(UserSession.getCurrent().getOrganisation(), MainetConstants.ENV_PSCL))
			this.getModel().getVehicleMaintenanceMasterDTO().setVeNo(vehicleMasterService
					.fetchVehicleNoByVeId(this.getModel().getVehicleMaintenanceMasterDTO().getVeId()));
        return new ModelAndView("viewVehicleMaintenanceMast/Form", MainetConstants.FORM_NAME, this.getModel());

    }
    
    /**
     * deleteVehicleMaintenanceMaster
     * @param request
     * @param vemeId
     * @return
     */
    @ResponseBody
    @RequestMapping(params = "deleteVehicleMaintenanceMaster", method = RequestMethod.POST)
    public ModelAndView deleteVehicleMaintenanceMaster(final HttpServletRequest request, @RequestParam Long vemeId) {
        Employee emp = UserSession.getCurrent().getEmployee();
        vehicleMaintenanceMasterService.deleteVehicleMaintenanceMaster(vemeId, emp.getEmpId(), emp.getEmppiservername());
        sessionCleanup(request);
        this.getModel().setVehicleMaintenanceList(vehicleMaintenanceMasterService.serchVehicleMaintenance(null, null,
                        UserSession.getCurrent().getOrganisation().getOrgid()));
        return new ModelAndView("searchVehicleMaintenanceMast", MainetConstants.FORM_NAME, this.getModel());
    }

    /**
     * searchVehicleMaintenanceMaster
     * @param request
     * @param vehType
     * @return
     */
    @ResponseBody
    @RequestMapping(params = "searchVehicleMaintenanceMaster", method = RequestMethod.POST)
    public ModelAndView searchVehicleMaintenanceMaster(final HttpServletRequest request,
            @RequestParam(required = false) Long vehType, @RequestParam(required = false) Long veId) {
        sessionCleanup(request);
        Organisation organisation = UserSession.getCurrent().getOrganisation();
        this.getModel().getVehicleMaintenanceMasterDTO().setVeVetype(vehType);
        this.getModel().getVehicleMaintenanceMasterDTO().setVeId(veId);
        this.getModel().setVehicleMaintenanceList(vehicleMaintenanceMasterService.serchVehicleMaintenance(vehType, veId, organisation.getOrgid()));
        if(Utility.isEnvPrefixAvailable(organisation, MainetConstants.ENV_PSCL)) {
        	mapVehicleIdAndNumber(organisation.getOrgid());
        	serchVehicleNo(vehType, request);
        }
        return new ModelAndView("searchVehicleMaintenanceMast", MainetConstants.FORM_NAME, this.getModel());

    }

    private void mapVehicleIdAndNumber(Long orgId) {
		this.getModel().setVehicleObjectList(vehicleMasterService.getAllVehicleIdNumberObjectList(orgId));
    	 Map<Long, String> vehicleIdNumberMap = new HashMap<>();
    	 this.getModel().getVehicleObjectList().forEach(vehicleIdNumberObject -> {
         	vehicleIdNumberMap.put(Long.valueOf(vehicleIdNumberObject[0].toString()), vehicleIdNumberObject[1].toString());
         });;
         this.getModel().getVehicleMaintenanceList().forEach(maintenanceMasDto -> {
         	maintenanceMasDto.setVeNo(vehicleIdNumberMap.get(maintenanceMasDto.getVeId()));
         });
    }
    
    @RequestMapping(method = { RequestMethod.POST }, params = "vehicleNo")
	public @ResponseBody Map<Long, String> serchVehicleNo(@RequestParam("id") Long vehicleTypeId, final HttpServletRequest httpServletRequest) {
    	List<Object[]> vehicleObjectList;
    	if(MainetConstants.FlagA.equalsIgnoreCase(this.getModel().getSaveMode())) {
    		vehicleObjectList = this.getModel().getVehicleObjectList().stream().filter(vehicleObject -> 
    				Long.valueOf(vehicleObject[2].toString()).equals(vehicleTypeId)).collect(Collectors.toList());
    	} else {
    		vehicleObjectList = this.getModel().getVehicleObjectList().stream().filter(vehicleObject -> 
    				Long.valueOf(vehicleObject[2].toString()).equals(vehicleTypeId)).collect(Collectors.toList());
    	}    	
		Map<Long, String> data = new HashMap<>();
		vehicleObjectList.forEach(vehicleObject -> data.put(Long.valueOf(vehicleObject[0].toString()), vehicleObject[1].toString()));
		return data;
	}

}
