package com.abm.mainet.property.ui.controller;

import java.io.Serializable;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.abm.mainet.common.constant.MainetConstants;
import com.abm.mainet.common.domain.FinancialYear;
import com.abm.mainet.common.service.IFinancialYearService;
import com.abm.mainet.common.ui.controller.AbstractFormController;
import com.abm.mainet.common.ui.model.JQGridResponse;
import com.abm.mainet.common.utility.UserSession;
import com.abm.mainet.common.utility.Utility;
import com.abm.mainet.property.dto.BillingScheduleDto;
import com.abm.mainet.property.dto.BillingShedualGridResponse;
import com.abm.mainet.property.service.BillingScheduleService;
import com.abm.mainet.property.ui.model.BillingScheduleModel;

/**
 * @author hiren.poriya
 * @since 21-Nov-2017
 */

@Controller
@RequestMapping("/BillingSchedule.html")
public class BillingScheduleController extends AbstractFormController<BillingScheduleModel> {



    @Autowired
    private BillingScheduleService billScheduleService;
    
    @Autowired
    private IFinancialYearService iFinancialYearService;

    /**
     * used for showing default home page for billing schedule
     * @param httpServletRequest
     * @return
     */
    @RequestMapping(method = { RequestMethod.POST })
    public ModelAndView index(HttpServletRequest httpServletRequest) {
        sessionCleanup(httpServletRequest);
        return defaultResult();
    }
    /**
     * this method is used for show grid data.
     * @param httpServletRequest
     * @param page
     * @param rows
     * @return
     */
    @RequestMapping(params = MainetConstants.Common_Constant.GET_GRID_DATA, produces = "application/json", method = RequestMethod.POST)
    public @ResponseBody JQGridResponse<? extends Serializable> getGridData(HttpServletRequest httpServletRequest,
            @RequestParam String page, @RequestParam String rows) {

        List<BillingShedualGridResponse> billinGridResponse = billScheduleService
                .findAllRecords(UserSession.getCurrent().getOrganisation().getOrgid());
        return this.getModel().paginate(httpServletRequest, page, rows, billinGridResponse);

    }

    /**
     * used for create,edit and view bill schedule details
     * @param id
     * @param modeType
     * @return
     * @throws Exception
     */
    @RequestMapping(params = MainetConstants.Common_Constant.FORM, method = RequestMethod.POST)
    public ModelAndView form(@RequestParam(value = MainetConstants.Common_Constant.ID, required = false) Long billId,
            @RequestParam(value = MainetConstants.Common_Constant.TYPE, required = false) String modeType) throws Exception {
        BillingScheduleModel billMoldel = this.getModel();
        populateModel(billMoldel, billId, modeType,UserSession.getCurrent().getOrganisation().getOrgid());
        return new ModelAndView(MainetConstants.Property.BillSchedule.FORM, MainetConstants.FORM_NAME, billMoldel);
    }

    /**
     * Populates the Spring MVC model with common details
     * @param billMoldel
     * @param id
     * @param modeType
     * @throws Exception 
     */
    private void populateModel(BillingScheduleModel billMoldel, Long billId, String modeType,Long orgId) throws Exception {
        if (billId == null) {
            final List<FinancialYear> finYearList = billScheduleService
                    .findAllFinYearNotMapInBillSchByOrgId(UserSession.getCurrent().getOrganisation().getOrgid());
            String financialYear = null;
            for (final FinancialYear finYearTemp : finYearList) {
                financialYear = Utility.getFinancialYearFromDate(finYearTemp.getFaFromDate());
                billMoldel.getFinancialYearMap().put(finYearTemp.getFaYear(), financialYear);
            }
            billMoldel.setBillScheduleDto(new BillingScheduleDto());
            billMoldel.setModeType(MainetConstants.MODE_CREATE);
        } else {
            BillingScheduleDto dto = billScheduleService.findById(billId);
            FinancialYear financialYear= iFinancialYearService.getFinincialYearsById(Long.valueOf(dto.getTbFinancialyears()),orgId);
            String finYearView  = Utility.getFinancialYearFromDate(financialYear.getFaFromDate());
            dto.setFinYearView(finYearView);
            billMoldel.setBillScheduleDto(dto);
            List<BillingScheduleDto> dtoList=billScheduleService.getDueDateDetail(billId, UserSession.getCurrent().getOrganisation());
            billMoldel.setBillSchDtoList(dtoList);
            if (modeType.equals(MainetConstants.MODE_EDIT)) {
                billMoldel.setModeType(MainetConstants.MODE_EDIT);
            } else {
                billMoldel.setModeType(MainetConstants.MODE_VIEW);
            }
        }
    }

    /**
     * Inactive Bill Schedule Data.
     * @param billId
     */
    @RequestMapping(params = MainetConstants.Property.BillSchedule.DELETE_SCHEDULE, method = RequestMethod.POST)
    public @ResponseBody Boolean deleteBillSchedule(HttpServletRequest httpServletRequest,
            @RequestParam(MainetConstants.Common_Constant.ID) Long billId) {
        billScheduleService.deleteBillSchedule(billId,UserSession.getCurrent().getOrganisation().getOrgid());
        return true;
    }
    
    @RequestMapping(params = MainetConstants.Property.BillSchedule.CREATE_SCHEDULE, method = RequestMethod.POST)
    public ModelAndView createBillSchedule(HttpServletRequest httpServletRequest,@RequestParam(value="schFreq") Long schFreq) {
    	this.bindModel(httpServletRequest);
    	this.getModel().getBillScheduleDto().setAsBillFrequency(schFreq);
        this.getModel().setBillSchDtoList(billScheduleService.createSchedule(schFreq,this.getModel().getBillSchDtoList(),UserSession.getCurrent().getOrganisation()));
        return new ModelAndView(MainetConstants.Property.BillSchedule.FORM, MainetConstants.FORM_NAME, this.getModel());
    }
}
