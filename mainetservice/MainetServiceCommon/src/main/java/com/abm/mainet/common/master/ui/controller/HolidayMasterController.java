package com.abm.mainet.common.master.ui.controller;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.abm.mainet.common.constant.MainetConstants;
import com.abm.mainet.common.dto.HolidayMasterDto;
import com.abm.mainet.common.dto.WorkTimeMasterDto;
import com.abm.mainet.common.master.service.HolidayMasterService;
import com.abm.mainet.common.master.ui.model.HolidayMasterModel;
import com.abm.mainet.common.ui.controller.AbstractFormController;
import com.abm.mainet.common.ui.model.JQGridResponse;
import com.abm.mainet.common.utility.UserSession;
import com.abm.mainet.common.utility.Utility;

/**
 * 
 * @author Jeetendra.Pal
 *
 */

@Controller
@RequestMapping("/HolidayMaster.html")
public class HolidayMasterController extends AbstractFormController<HolidayMasterModel> {

    private static final Logger LOGGER = Logger.getLogger(HolidayMasterController.class);

    @Resource
    HolidayMasterService holidayMasterService;

    @RequestMapping(method = { RequestMethod.POST })
    public ModelAndView index(final HttpServletRequest httpServletRequest) {
        sessionCleanup(httpServletRequest);
        this.getModel().setCommonHelpDocs("HolidayMaster.html");
        this.getModel().setWorkTimeMasterEntity(
                holidayMasterService.getworkTimeEntity(UserSession.getCurrent().getOrganisation().getOrgid()));

        return index();
    }

    @RequestMapping(method = RequestMethod.POST, params = "addHolidayDescription")
    public ModelAndView serachAdjustmentData(final HttpServletRequest request,
            @RequestParam("hoDate") final String hoDate) {
        bindModel(request);
        final HolidayMasterModel model = getModel();

        model.getHolidayDescriptionList().get(hoDate);
        if (model.getHolidayDescriptionList().containsKey(hoDate)) {
            model.getHolidayMasterEntity().setHoDescription(model.getHolidayDescriptionList().get(hoDate));
        } else if (model.getHolidayMasterEntity() != null) {
            model.getHolidayMasterEntity().setHoDescription(null);
        }

        return new ModelAndView("AddHolidayDescription", MainetConstants.FORM_NAME, model);
    }

    @RequestMapping(method = RequestMethod.POST, params = "editHolidayDescription")
    public ModelAndView editHolidayDescription(final HttpServletRequest request,
            @RequestParam(name = "hoDate", required = false) final String hoDate) {
        bindModel(request);
        final HolidayMasterModel model = getModel();

        model.getHolidayDescriptionList().get(hoDate);
        if (model.getHolidayDescriptionList().containsKey(hoDate)) {
            model.getHolidayDescriptionList().remove(hoDate);
        } else {
            model.getHolidayMasterEntity().setHoDescription(null);
        }

        return new ModelAndView("AddHolidayDescription", MainetConstants.FORM_NAME, model);
    }

    @ResponseBody
    @RequestMapping(method = RequestMethod.POST, params = "saveHolidayDescription")
    public String saveHolidayDescription(final HttpServletRequest httpServletRequest,
            @RequestParam("hoDate") final String holidayDate,
            @RequestParam("hoDescription") final String holidayDescription) {
        bindModel(httpServletRequest);
        Map<String, String> holidayDescriptionData = new HashMap<String, String>();

        if (!holidayDate.isEmpty()) {
            holidayDescriptionData.putAll(this.getModel().getHolidayDescriptionList());

            holidayDescriptionData.put(holidayDate, holidayDescription);
            this.getModel().setHolidayDescriptionList(holidayDescriptionData);
        }
        return this.getModel().getHolidayDescriptionList().toString();
    }

    @RequestMapping(method = RequestMethod.POST, params = "saveWorkTimeData")
    public ModelAndView saveWorkTimeData(final HttpServletRequest request) {
        bindModel(request);

        HolidayMasterModel holidayMasterModel = this.getModel();

        WorkTimeMasterDto workTimeEntity = holidayMasterModel.getWorkTimeMasterEntity();

        try {
            workTimeEntity.setWrStartTime(holidayMasterModel.stringToTimeConvet(workTimeEntity.getWrStartTimeString()));
            workTimeEntity.setWrEndTime(holidayMasterModel.stringToTimeConvet(workTimeEntity.getWrEndTimeString()));
        } catch (ParseException exception) {
            LOGGER.error("Exception occur in saveWorkTimeData()- String time to Time Stamp Conversion ", exception);
        }

        holidayMasterService.saveWorkTimeMaster(workTimeEntity, holidayMasterModel.getMode(),
                UserSession.getCurrent().getOrganisation().getOrgid(),
                UserSession.getCurrent().getEmployee().getUserId());

        return index();
    }

    @RequestMapping(method = RequestMethod.POST, params = "saveHolidayDetails")
    public ModelAndView saveHolidayDetails(final HttpServletRequest request) {

        bindModel(request);

        this.getModel().setHolidayMasterList(new ArrayList<HolidayMasterDto>());

        HolidayMasterModel holidayMasterModel = this.getModel();

        holidayMasterModel.prepareholidayDetailsEntity();

        holidayMasterService.saveHolidayDetailsList(holidayMasterModel.getHolidayMasterList(),
                UserSession.getCurrent().getOrganisation().getOrgid(),
                UserSession.getCurrent().getEmployee().getUserId());

        return index();
    }

    @RequestMapping(method = RequestMethod.POST, params = "AddHolidayMaster")
    public ModelAndView openAddHolidayForm(final HttpServletRequest httpServletRequest,
            @RequestParam("typeFlag") final String typeFlag) {
        final HolidayMasterModel model = getModel();
        model.setTypeFlag(typeFlag);
        model.setWorkTimeMasterEntity(new WorkTimeMasterDto());
        model.setMode(MainetConstants.Common_Constant.ACTIVE_FLAG);

        Date endDate = Utility.getFullYearByDate(model.getHoYearStartDate());

        model.setHoYearEndDate(endDate);

        return new ModelAndView("AddHolidayMaster", MainetConstants.CommonConstants.COMMAND, model);
    }

    @RequestMapping(method = RequestMethod.POST, params = "EditHolidayMaster")
    public ModelAndView openEditHolidayForm(final HttpServletRequest httpServletRequest,
            @RequestParam("typeFlag") final String typeFlag) {
        final HolidayMasterModel model = getModel();
        model.setMode(MainetConstants.MODE_EDIT);
        model.setTypeFlag(typeFlag);
        this.getModel().setWorkTimeMasterEntity(
                holidayMasterService.getworkTimeEntity(UserSession.getCurrent().getOrganisation().getOrgid()));

        if (model.getWorkTimeMasterEntity() != null && model.getWorkTimeMasterEntity().getWrStartTime() != null
                && model.getWorkTimeMasterEntity().getWrEndTime() != null) {
            model.getWorkTimeMasterEntity()
                    .setWrStartTimeString(model.getWorkTimeMasterEntity().getWrStartTime().toString());
            model.getWorkTimeMasterEntity()
                    .setWrEndTimeString(model.getWorkTimeMasterEntity().getWrEndTime().toString());
        }

        if (!typeFlag.equals("W")) {
            List<HolidayMasterDto> holidayMasters = holidayMasterService.getHolidayDetailsList(
                    model.getHoYearStartDate(), model.getHoYearEndDate(),
                    UserSession.getCurrent().getOrganisation().getOrgid());

            model.setEditHolidayMasterData(holidayMasters);

            model.prepareHolidayDescriptionList(holidayMasters);
            Date endDate = Utility.getFullYearByDate(model.getHoYearStartDate());
            model.setHoYearEndDate(endDate);
        }
        return new ModelAndView("EditHolidayMaster", MainetConstants.CommonConstants.COMMAND, model);
    }

    @RequestMapping(params = "HolidayMasterGridData", produces = "application/json", method = RequestMethod.POST)
    public @ResponseBody JQGridResponse<HolidayMasterDto> getHolidayMasterGridData(
            final HttpServletRequest httpServletRequest, @RequestParam final String page,
            @RequestParam final String rows) {

        return getModel().paginate(httpServletRequest, page, rows, this.getModel().getHolidayMastersGrid());
    }

    @ResponseBody
    @RequestMapping(params = "deleteHoliday", method = RequestMethod.POST)
    public boolean deleteHoliday(@RequestParam("hoId") final Long hoId) {

        for (HolidayMasterDto holidayMaster : this.getModel().getHolidayMastersGrid()) {
            if (holidayMaster.getHoId().longValue() == hoId.longValue()) {
                this.getModel().getHolidayMastersGrid().remove(holidayMaster);
                break;
            }
        }
        return holidayMasterService.saveDeleteHolidayMaster(hoId,
                UserSession.getCurrent().getOrganisation().getOrgid());

    }

    @ResponseBody
    @RequestMapping(params = "getGridData", method = RequestMethod.POST)
    public String getGridData(@RequestParam("yearStartDate") final Date yearStartDate,
            @RequestParam("yearEndDate") final Date yearEndDate) {

        String count = MainetConstants.Common_Constant.NO;

        this.getModel().setHoYearStartDate(yearStartDate);

        Date endDate = Utility.getFullYearByDate(this.getModel().getHoYearStartDate());
        this.getModel().setHoYearEndDate(endDate);

        this.getModel().setHolidayMastersGrid(holidayMasterService.getGridData(yearStartDate, endDate,
                UserSession.getCurrent().getOrganisation().getOrgid()));
        if (!this.getModel().getHolidayMastersGrid().isEmpty()) {
            count = MainetConstants.Common_Constant.YES;
        }

        return count;
    }

    @ResponseBody
    @RequestMapping(params = "printHolidayDetails", method = RequestMethod.POST)
    public String printHolidayDetails(final HttpServletRequest httpServletRequest) {

        String data = null;

        if (!this.getModel().getHolidayMastersGrid().isEmpty()) {
            this.getModel().prepareHolidayDescriptionList(this.getModel().getHolidayMastersGrid());
            data = this.getModel().getHolidayDescriptionList().toString();
        } else {
            data = MainetConstants.Common_Constant.NO;
        }

        return data;

    }

    @ResponseBody
    @RequestMapping(params = "clearGridData", method = RequestMethod.POST)
    public void clearGridData(final HttpServletRequest httpServletRequest) {
        this.getModel().getHolidayMastersGrid().clear();
    }

    @ResponseBody
    @RequestMapping(params = "validateHolidayList", method = RequestMethod.POST)
    public String validateHolidayList(final HttpServletRequest httpServletRequest) {

        String data = null;

        if (!this.getModel().getHolidayDescriptionList().isEmpty()) {
            data = MainetConstants.Common_Constant.YES;
        } else {
            data = MainetConstants.Common_Constant.NO;
        }

        return data;

    }

}
