package com.abm.mainet.rnl.ui.controller;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.abm.mainet.common.constant.MainetConstants;
import com.abm.mainet.common.constant.MainetConstants.AccountConstants;
import com.abm.mainet.common.constant.PrefixConstants;
import com.abm.mainet.common.dto.TbAcVendormaster;
import com.abm.mainet.common.exception.FrameworkException;
import com.abm.mainet.common.integration.acccount.service.SecondaryheadMasterService;
import com.abm.mainet.common.integration.dms.service.IFileUploadService;
import com.abm.mainet.common.master.repository.TbCfcApplicationMstJpaRepository;
import com.abm.mainet.common.master.service.TbAcVendormasterService;
import com.abm.mainet.common.ui.controller.AbstractFormController;
import com.abm.mainet.common.utility.ApplicationContextProvider;
import com.abm.mainet.common.utility.CommonMasterUtility;
import com.abm.mainet.common.utility.LookUp;
import com.abm.mainet.common.utility.UserSession;
import com.abm.mainet.rnl.dto.BookingCancelDTO;
import com.abm.mainet.rnl.dto.EstateBookingDTO;
import com.abm.mainet.rnl.service.IEstateBookingService;
import com.abm.mainet.rnl.ui.model.EstateBookingCancelModel;

/**
 * @author israt.ali
 *
 * 11-10-2019
 */
@Controller
@RequestMapping("EstateBookingCancel.html")
public class EstateBookingCancelController extends AbstractFormController<EstateBookingCancelModel> {

    @Autowired
    IFileUploadService fileUpload;

    @Autowired
    private IEstateBookingService iEstateBookingService;
    
    @Resource
	private SecondaryheadMasterService secondaryheadMasterService;

    @RequestMapping(method = { RequestMethod.POST, RequestMethod.GET })
    public ModelAndView index(final Model uiModel, final HttpServletRequest httpServletRequest) {
        sessionCleanup(httpServletRequest);
        fileUpload.sessionCleanUpForFileUpload();
        EstateBookingCancelModel model = getModel();
        TbCfcApplicationMstJpaRepository tbCfcApplicationMstJpaRepository = ApplicationContextProvider
                .getApplicationContext().getBean(TbCfcApplicationMstJpaRepository.class);
        // fetch data from estate Booking
        // TB_RL_ESTATE_BOOKING table
        List<EstateBookingDTO> bookingNos = new ArrayList<>();
        List<EstateBookingDTO> estateBookings = iEstateBookingService
                .fetchAllBookingsByOrg(UserSession.getCurrent().getOrganisation().getOrgid(), MainetConstants.FlagY);
        SimpleDateFormat dateFormat = new SimpleDateFormat(MainetConstants.DATE_FORMAT_UPLOAD);
        Date currentDate = null;
        try {
            currentDate = dateFormat.parse(dateFormat.format(new Date()));
        } catch (ParseException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        // make data for booking id
        for (EstateBookingDTO dto : estateBookings) {
            // DATE Comparison here like CancelDate< bookingDate doing here because no need to create again query for this
            // condition re-use method which is already made
            /*
             * if (dto.getCancelDate() == null && (currentDate.before(dto.getFromDate()))) {
             */
            EstateBookingDTO bookingNo = new EstateBookingDTO();
            BeanUtils.copyProperties(dto, bookingNo);
            List<Object[]> appliInfoList = tbCfcApplicationMstJpaRepository.getApplicantInfo(dto.getApplicationId(),
                    UserSession.getCurrent().getOrganisation().getOrgid());
            String name = "";
            for (final Object[] strings : appliInfoList) {
                if (strings[1] != null) {
                    name = strings[1].toString();
                }
                bookingNo.setBookingNo(
                        bookingNo.getBookingNo() + " - " + strings[0].toString() + " " + name + " " + strings[2].toString());
            }
            bookingNos.add(bookingNo);
            // }
        }
        model.setEstateBookings(bookingNos);
        model.setOfflineDTO(null);
        
        // fetch vendor list from vendor master
        final Long vendorStatus = CommonMasterUtility
                .getLookUpFromPrefixLookUpValue(AccountConstants.AC.getValue(), PrefixConstants.VSS,
                        UserSession.getCurrent().getLanguageId(), UserSession.getCurrent().getOrganisation())
                .getLookUpId();
        List<TbAcVendormaster> vendorList = ApplicationContextProvider.getApplicationContext()
                .getBean(TbAcVendormasterService.class)
                .getAllActiveVendors(UserSession.getCurrent().getOrganisation().getOrgid(), vendorStatus);
        this.getModel().setVendorList(vendorList);
        return new ModelAndView("estateBookingCancel", MainetConstants.FORM_NAME,
                model);
    }

    @RequestMapping(method = { RequestMethod.POST }, params = "getCharges")
    public ModelAndView getCharges(@RequestParam("applicationId") final Long applicationId,
            @RequestParam("orgId") final Long orgId, final HttpServletRequest httpServletRequest) {
        bindModel(httpServletRequest);
        fileUpload.sessionCleanUpForFileUpload();
       
        ModelAndView modelAndView = null;
        try {
            // Long propId = 61L;
            // getModel().findApplicableCheckListAndCharges(MainetConstants.RNL_ESTATE_BOOKING, propId, orgId);
            // fetch charges and display on estateBookingCancel.jsp
            List<BookingCancelDTO> bookingCancelList = iEstateBookingService.fetchChargesDetails(applicationId, orgId);
            this.getModel().setBookingCancelList(bookingCancelList);
            modelAndView = new ModelAndView("estateBookingCancelValidn", MainetConstants.CommonConstants.COMMAND,
                    this.getModel());

            if (bookingCancelList.isEmpty()) {
                this.getModel()
                        .addValidationError(getApplicationSession().getMessage("rnl.estate.booking.validation.fee.empty"));
            }

            if (getModel().getBindingResult() != null && this.getModel().getBindingResult().hasErrors()) {
                modelAndView.addObject(BindingResult.MODEL_KEY_PREFIX +
                        MainetConstants.FORM_NAME, getModel().getBindingResult());
            }
            
            LookUp lookup = CommonMasterUtility.getLookUpFromPrefixLookUpValue(
    				MainetConstants.BUG_HEAD_OPENING_BALANCE_MASTER.SLI_PREFIX_LIVE_VALUE,
    				MainetConstants.BUG_HEAD_OPENING_BALANCE_MASTER.SLI_PREFIX_VALUE,
    				UserSession.getCurrent().getLanguageId(), UserSession.getCurrent().getOrganisation());

    		if (lookup != null) {
    			this.getModel().setFlag(lookup.getLookUpCode());
    		}
            
            modelAndView.addObject(MainetConstants.AccountBillEntry.EXPENDITURE_HEAD_MAP, secondaryheadMasterService
    				.findExpenditureHeadMapAccountTypeIsOthers(UserSession.getCurrent().getOrganisation().getOrgid()));

        } catch (final Exception ex) {
            throw new FrameworkException("Charges not found " + ex.getMessage());
        }

        return modelAndView;
    }

}
