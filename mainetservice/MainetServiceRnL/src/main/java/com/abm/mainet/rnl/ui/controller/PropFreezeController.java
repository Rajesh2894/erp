package com.abm.mainet.rnl.ui.controller;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;

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
import com.abm.mainet.common.constant.PrefixConstants;
import com.abm.mainet.common.service.ILocationMasService;
import com.abm.mainet.common.ui.controller.AbstractFormController;
import com.abm.mainet.common.utility.LookUp;
import com.abm.mainet.common.utility.UserSession;
import com.abm.mainet.common.utility.UtilityService;
import com.abm.mainet.rnl.service.IEstateBookingService;
import com.abm.mainet.rnl.service.IEstatePropertyService;
import com.abm.mainet.rnl.ui.model.PropFreezeModel;

/**
 * @author ritesh.patil
 *
 */

@Controller
@RequestMapping("/PropFreeze.html")
public class PropFreezeController extends AbstractFormController<PropFreezeModel> {

    @Autowired
    private ILocationMasService iLocationMasService;

    @Autowired
    private IEstateBookingService iEstateBookingService;

    @Autowired
    private IEstatePropertyService iEstatePropertyService;

    @RequestMapping(method = RequestMethod.POST)
    public ModelAndView index(final Model uiModel, final HttpServletRequest httpServletRequest) {
        sessionCleanup(httpServletRequest);
        this.getModel().setFreezeDTOList(iEstateBookingService.findAllFreezeBookingProp(
                UserSession.getCurrent().getOrganisation().getOrgid(), MainetConstants.RnLCommon.F_FLAG));
        return index();
    }

    /**
     * Shows a form page in order to create a new Estate
     * 
     * @param model
     * @return
     */
    @RequestMapping(params = "form", method = RequestMethod.POST)
    public ModelAndView formForCreate(@RequestParam(value = "propId", required = false) final Long propId,
            @RequestParam(value = "type", required = false) final String modeType) {

        final PropFreezeModel propFreezeModel = getModel();
        propFreezeModel.setModeType(MainetConstants.RnLCommon.MODE_CREATE);
        propFreezeModel.setLocationList(
                iLocationMasService.getLocationNameByOrgId(UserSession.getCurrent().getOrganisation().getOrgid()));
        return new ModelAndView(MainetConstants.PropFreeze.PROP_FREEZE_FORM, MainetConstants.FORM_NAME,
                propFreezeModel);
    }

    @ResponseBody
    @RequestMapping(params = "propList", method = RequestMethod.POST)
    public List<Object[]> getPropertyList(@RequestParam("esId") final Long esId) {
        final List<Object[]> list = iEstatePropertyService.findPropertiesForEstate(
                UserSession.getCurrent().getOrganisation().getOrgid(), esId, PrefixConstants.CPD_VALUE_RENT,
                PrefixConstants.CATEGORY_PREFIX_NAME);
        return list;
    }

    @ResponseBody
    @RequestMapping(params = "getVisibleDates", method = RequestMethod.POST)
    public List<String> getvisibledates(@RequestParam("propId") final long propId) {
        final List<String> fromAndtoDate = iEstateBookingService.getEstateBookingFromAndToDates(propId,
                UserSession.getCurrent().getOrganisation().getOrgid(), true);
        return fromAndtoDate;
    }

    @ResponseBody
    @RequestMapping(params = "unFreezeProp", method = RequestMethod.POST)
    public boolean deActiveEstateId(@RequestParam("id") final Long id) {
        iEstateBookingService.updateFreezeProperty(id, UserSession.getCurrent().getEmployee().getEmpId());
        return true;
    }

    @RequestMapping(params = "getShiftsBasedOnDate", method = { RequestMethod.GET, RequestMethod.POST })
    public @ResponseBody List<LookUp> getShiftsBasedOnDate(@RequestParam("fromDate") final String fromDate,
            @RequestParam("toDate") final String toDate, @RequestParam("propId") final Long propId) {
        List<LookUp> lookup = null;
        try {
            lookup = iEstateBookingService.getEstateBookingShifts(propId, fromDate, toDate,
                    UserSession.getCurrent().getOrganisation().getOrgid());
        } catch (final Exception exception) {
            logger.error("Exception found in getShiftsBasedOnDate method: ", exception);
        }
        return lookup;
    }

    @RequestMapping(params = "dateRangBetBookedDate", method = { RequestMethod.GET, RequestMethod.POST })
    public @ResponseBody String dateRangBetBookedDate(@RequestParam("fromDate") final String fromDate,
            @RequestParam("toDate") final String toDate, @RequestParam("propId") final Long propId,
            final HttpServletRequest httpServletRequest) {
        String flag = MainetConstants.EstateBooking.PASS;
        try {
            final List<String> fromAndtoDate = iEstateBookingService.getEstateBookingFromAndToDatesForGeneral(propId,
                    UserSession.getCurrent().getOrganisation().getOrgid());
            final Calendar calendar = new GregorianCalendar();
            final Date dateFrom = UtilityService.convertStringDateToDateFormat(fromDate);
            final Date dateTo = UtilityService.convertStringDateToDateFormat(toDate);
            calendar.setTime(dateFrom);
            final List<String> bookedDate = new ArrayList<>();
            while (calendar.getTime().before(dateTo) || calendar.getTime().equals(dateTo)) {
                final Date result = calendar.getTime();
                bookedDate.add(new SimpleDateFormat(MainetConstants.CommonConstants.DATE_F).format(result));
                calendar.add(Calendar.DATE, 1);
            }
            for (final String date : bookedDate) {
                if (fromAndtoDate.contains(date)) {
                    flag = MainetConstants.EstateBooking.FAIL;
                    ;
                    break;
                }
            }
        } catch (final Exception exception) {
            logger.error("Exception found in dateRangBetBookedDate method: ", exception);
        }
        return flag;
    }
    
    @RequestMapping(params = "getShiftsBasedOnpropId", method = { RequestMethod.GET, RequestMethod.POST })
    public @ResponseBody List<LookUp> getShiftsBasedOnDate(@RequestParam("propId") final Long propId) {
        List<LookUp> lookup = null;
        try {
            lookup = iEstateBookingService.getEstateBookingShiftsData(propId,
                    UserSession.getCurrent().getOrganisation().getOrgid());
        } catch (final Exception exception) {
            logger.error("Exception found in getShiftsBasedOnDate method: ", exception);
        }
        return lookup;
    }
    
}
