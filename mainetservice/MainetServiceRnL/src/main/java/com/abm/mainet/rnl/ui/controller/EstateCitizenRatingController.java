package com.abm.mainet.rnl.ui.controller;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import com.abm.mainet.common.constant.MainetConstants;
import com.abm.mainet.common.constant.PrefixConstants;
import com.abm.mainet.common.master.repository.TbCfcApplicationMstJpaRepository;
import com.abm.mainet.common.ui.controller.AbstractFormController;
import com.abm.mainet.common.utility.ApplicationContextProvider;
import com.abm.mainet.common.utility.CommonMasterUtility;
import com.abm.mainet.common.utility.LookUp;
import com.abm.mainet.common.utility.UserSession;
import com.abm.mainet.rnl.dto.EstateBookingDTO;
import com.abm.mainet.rnl.service.IEstateBookingService;
import com.abm.mainet.rnl.ui.model.EstateCitizenRatingModel;

@Controller
@RequestMapping(MainetConstants.RnLCommon.CITIZEN_RATING_URL)
public class EstateCitizenRatingController extends AbstractFormController<EstateCitizenRatingModel> {

    @Autowired
    private IEstateBookingService iEstateBookingService;

    @RequestMapping(method = { RequestMethod.POST, RequestMethod.GET })
    public ModelAndView index(final Model model, HttpServletRequest request) throws Exception {
        this.sessionCleanup(request);
        getModel().bind(request);
        this.getModel().setCommonHelpDocs(MainetConstants.RnLCommon.CITIZEN_RATING_URL);
        // fetch data from estate Booking
        // TB_RL_ESTATE_BOOKING table
        List<EstateBookingDTO> citizenNames = new ArrayList<>();
        List<EstateBookingDTO> estateBookings = iEstateBookingService
                .fetchAllBookingsByOrg(UserSession.getCurrent().getOrganisation().getOrgid(), MainetConstants.FlagY);
        SimpleDateFormat dateFormat = new SimpleDateFormat(MainetConstants.DATE_FORMAT_UPLOAD);
        Date currentDate = null;
        try {
            currentDate = dateFormat.parse(dateFormat.format(new Date()));
        } catch (ParseException e) {
            e.printStackTrace();
        }
        // make data for booking id
        for (EstateBookingDTO dto : estateBookings) {
            // DATE Comparison here like CancelDate< bookingDate doing here because no need to create again query for this
            // condition re-use method which is already made
            if (dto.getCancelDate() == null && (currentDate.before(dto.getFromDate()))) {
                EstateBookingDTO citizen = new EstateBookingDTO();
                BeanUtils.copyProperties(dto, citizen);
                List<Object[]> appliInfoList = ApplicationContextProvider
                        .getApplicationContext().getBean(TbCfcApplicationMstJpaRepository.class)
                        .getApplicantInfo(dto.getApplicationId(),
                                UserSession.getCurrent().getOrganisation().getOrgid());
                String name = "";
                for (final Object[] strings : appliInfoList) {
                    if (strings[1] != null) {
                        name = strings[1].toString();
                    }
                    citizen.setBookingNo(
                            citizen.getBookingNo() + " - " + strings[0].toString() + " " + name + " " + strings[2].toString());
                }
                citizenNames.add(citizen);
            }
        }
        List<LookUp> lookupListtest = new ArrayList<>();
        lookupListtest = CommonMasterUtility.lookUpListByPrefix(PrefixConstants.RnLPrefix.RATING,
                UserSession.getCurrent().getOrganisation().getOrgid());
        this.getModel().setLookupList(lookupListtest);
        this.getModel().setEstateBookings(citizenNames);
        return new ModelAndView(MainetConstants.RnLCommon.CITIZEN_RATING_FORM, MainetConstants.FORM_NAME, this.getModel());
    }
}
