package com.abm.mainet.adh.ui.controller;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.abm.mainet.adh.dto.HoardingMasterDto;
import com.abm.mainet.adh.service.IHoardingMasterService;
import com.abm.mainet.adh.ui.model.HoardingMasterModel;
import com.abm.mainet.common.constant.MainetConstants;
import com.abm.mainet.common.constant.ServiceEndpoints;
import com.abm.mainet.common.domain.LocationOperationWZMapping;
import com.abm.mainet.common.domain.Organisation;
import com.abm.mainet.common.domain.ServiceMaster;
import com.abm.mainet.common.master.dto.TbLocationMas;
import com.abm.mainet.common.service.ILocationMasService;
import com.abm.mainet.common.service.ServiceMasterService;
import com.abm.mainet.common.ui.controller.AbstractFormController;
import com.abm.mainet.common.utility.ApplicationContextProvider;
import com.abm.mainet.common.utility.CommonMasterUtility;
import com.abm.mainet.common.utility.LookUp;
import com.abm.mainet.common.utility.UserSession;
import com.abm.mainet.common.utility.Utility;

/**
 * 
 * @author Anwarul.Hassan
 * @since 07-Aug-2019
 */
@Controller
@RequestMapping("/HoardingMaster.html")
public class HoardingMasterController extends AbstractFormController<HoardingMasterModel> {

    @Autowired
    IHoardingMasterService hoardingMasterService;
    @Autowired
    private ILocationMasService locationMasService;

    /**
     * This method is used to load index page of Hoarding Master
     * @param request
     * @return Hoarding Master Summary Page
     */
    @RequestMapping(method = { RequestMethod.GET, RequestMethod.POST })
    public ModelAndView index(final Model model, final HttpServletRequest request) {
        sessionCleanup(request);
        List<HoardingMasterDto> hoardingMasterDtoList = hoardingMasterService
                .searchHoardingMasterData(UserSession.getCurrent().getOrganisation().getOrgid(), null, null, null, null, null,
                        null, null, null);
        for (HoardingMasterDto masterDto : hoardingMasterDtoList) {
            HoardingMasterDto dto = new HoardingMasterDto();
            BeanUtils.copyProperties(masterDto, dto);
            LookUp lookUp = CommonMasterUtility.getHierarchicalLookUp(dto.getHoardingTypeId1(),
                    UserSession.getCurrent().getOrganisation());
            dto.setHoardingTypeIdDesc(lookUp.getLookUpDesc());
            this.getModel().getHoardingMasterDtoList().add(dto);
        }
        // this.getModel().setHoardingMasterDtoList(hoardingMasterDtoList);

        ServiceMaster serviceMaster = ApplicationContextProvider.getApplicationContext().getBean(ServiceMasterService.class)
                .getServiceMasterByShortCode(MainetConstants.AdvertisingAndHoarding.ADH_SHORT_CODE,
                        UserSession.getCurrent().getOrganisation().getOrgid());

        this.getModel().setDeptId(serviceMaster.getTbDepartment().getDpDeptid());
        
        if(Utility.isEnvPrefixAvailable(UserSession.getCurrent().getOrganisation(), MainetConstants.ENV_PSCL)) {
        	List<TbLocationMas> locationList = locationMasService.fillAllLocationMasterDetails(UserSession.getCurrent().getOrganisation());
            this.getModel().setLocationList(locationList); 
        }else {
        	List<TbLocationMas> locationList = locationMasService.getlocationByDeptId(serviceMaster.getTbDepartment().getDpDeptid(),
                    UserSession.getCurrent().getOrganisation().getOrgid());
            this.getModel().setLocationList(locationList);
        }
        return index();
    }

    /**
     * This Method is used For add Hoarding Master
     * @param request
     * @return Hoarding Master Entry Form
     */
    @RequestMapping(params = MainetConstants.AdvertisingAndHoarding.ADD_HOARDING_MASTER, method = RequestMethod.POST)
    public ModelAndView addHoardingMaster(HttpServletRequest request) {
        this.getModel().setSaveMode(MainetConstants.FlagA);
        bindModel(request);
        return new ModelAndView(MainetConstants.AdvertisingAndHoarding.ADD_HOARDING_MASTER, MainetConstants.FORM_NAME,
                this.getModel());
    }

    /**
     * This method is used for search Hoarding Master
     * @param hoardingNumber
     * @param hoardingStatus
     * @param hoardingType
     * @param hoardingSubType
     * @param hoardingLocation
     * @param request
     * @return HoardingMasterDto list
     */
    @ResponseBody
    @RequestMapping(params = MainetConstants.AdvertisingAndHoarding.SEARCH_HOARDING_MASTER, method = RequestMethod.POST)
    List<HoardingMasterDto> searchHoardingMaster(
            @RequestParam(value = MainetConstants.AdvertisingAndHoarding.HOARDING_NUMBER, required = false) String hoardingNumber,
            @RequestParam(value = MainetConstants.AdvertisingAndHoarding.HOARDING_STATUS, required = false) Long hoardingStatus,
            @RequestParam(value = MainetConstants.AdvertisingAndHoarding.HOARDING_TYPE, required = false) Long hoardingType,
            @RequestParam(value = MainetConstants.AdvertisingAndHoarding.HOARDING_SUB_TYPE, required = false) Long hoardingSubType,
            @RequestParam(value = MainetConstants.AdvertisingAndHoarding.HOARDING_SUB_TYPE3, required = false) Long hoardingSubType3,
            @RequestParam(value = MainetConstants.AdvertisingAndHoarding.HOARDING_SUB_TYPE4, required = false) Long hoardingSubType4,
            @RequestParam(value = MainetConstants.AdvertisingAndHoarding.HOARDING_SUB_TYPE5, required = false) Long hoardingSubType5,
            @RequestParam(value = MainetConstants.AdvertisingAndHoarding.HOARDING_LOCATION, required = false) Long hoardingLocation,
            HttpServletRequest request) {
        bindModel(request);

        List<HoardingMasterDto> masterDtos = hoardingMasterService.searchHoardingMasterData(
                UserSession.getCurrent().getOrganisation().getOrgid(), hoardingNumber,
                hoardingStatus, hoardingType, hoardingSubType, hoardingSubType3, hoardingSubType4,
                hoardingSubType5, hoardingLocation);
        List<HoardingMasterDto> masterDtoList = new ArrayList<>();
        if (CollectionUtils.isNotEmpty(masterDtos)) {
            for (HoardingMasterDto masterDto : masterDtos) {
                HoardingMasterDto dto = new HoardingMasterDto();
                BeanUtils.copyProperties(masterDto, dto);
                LookUp lookUp = CommonMasterUtility.getHierarchicalLookUp(dto.getHoardingTypeId1(),
                        UserSession.getCurrent().getOrganisation());
                if (lookUp != null && lookUp.getLookUpDesc() != null) {
                    dto.setHoardingTypeIdDesc(lookUp.getLookUpDesc());
                }
                masterDtoList.add(dto);
            }
        }
        return masterDtoList;
    }

    /**
     * This method is used for edit or view Hoarding Master Entry Form
     * @param hoardingId
     * @return Hoarding Master Entry Form for edit or view
     */
    @RequestMapping(params = MainetConstants.EDIT, method = RequestMethod.POST)
    public ModelAndView editOrViewHoardingMaster(
            @RequestParam(MainetConstants.AdvertisingAndHoarding.HOARDING_ID) Long hoardingId,
            @RequestParam(value = MainetConstants.AdvertisingAndHoarding.SAVE_MODE, required = true) String saveMode) {
        this.getModel().setSaveMode(saveMode);

        HoardingMasterDto masterDto = hoardingMasterService
                .getByOrgIdAndHoardingId(UserSession.getCurrent().getOrganisation().getOrgid(), hoardingId);
        if (masterDto != null) {
            this.getModel().setHoardingMasterDto(masterDto);
        }
        Organisation org = UserSession.getCurrent().getOrganisation();
        LookUp gisFlag = CommonMasterUtility.getValueFromPrefixLookUp("GIS",
                "MLI", org);
        if (gisFlag != null) {
            getModel().setGisValue(gisFlag.getOtherField());
            String GISURL = ServiceEndpoints.GisItegration.GIS_URI + ServiceEndpoints.GisItegration.ADH_LAYER_NAME;
            getModel().setgISUri(GISURL);
        }

        return new ModelAndView(MainetConstants.AdvertisingAndHoarding.ADD_HOARDING_MASTER, MainetConstants.FORM_NAME,
                this.getModel());
    }
    /*
     * For mapping Location and operational ward zone
     */

    @ResponseBody
    @RequestMapping(params = MainetConstants.AdvertisingAndHoarding.GET_LOCATION_MAPPING, method = RequestMethod.POST)
    public String getLocationMapping(
            @RequestParam(MainetConstants.AdvertisingAndHoarding.LOCATION_ID) Long locationId) {
        String response = MainetConstants.BLANK;
        LocationOperationWZMapping wzMapping = locationMasService.findbyLocationAndDepartment(
                locationId, this.getModel().getDeptId());
        if (wzMapping == null) {
            response = "N";
        }
        return response;
    }

}
