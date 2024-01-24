package com.abm.mainet.rnl.ui.controller;

import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.abm.mainet.cfc.challan.dto.ChallanReceiptPrintDTO;
import com.abm.mainet.common.constant.MainetConstants;
import com.abm.mainet.common.ui.controller.AbstractFormController;
import com.abm.mainet.common.utility.UserSession;
import com.abm.mainet.rnl.dto.EstatePropResponseDTO;
import com.abm.mainet.rnl.service.IEstateBookingService;
import com.abm.mainet.rnl.ui.model.DuplicateReceiptPrintingModel;


/**
 * @author pooja.maske
 *
 */

@Controller
@RequestMapping("DuplicateReceiptPrinting.html")
public class DuplicateReceiptPrintingController extends AbstractFormController<DuplicateReceiptPrintingModel> {
 
    @Autowired
    private IEstateBookingService iEstateBookingService;
	
 
	@RequestMapping(method = RequestMethod.POST)
	public ModelAndView index(final Model uiModel, final HttpServletRequest httpServletRequest) {
		sessionCleanup(httpServletRequest);
		getModel().bind(httpServletRequest);
		Long orgId = UserSession.getCurrent().getOrganisation().getOrgid();
		this.getModel().setEstatePropMasterList(iEstateBookingService.getPropetyDetailsByOrgId(orgId));
		return new ModelAndView(MainetConstants.EstateBooking.DUPLICATE_RECIEPT_PRINT_SUMMARY, MainetConstants.FORM_NAME, getModel());	
	}
	
	
	@RequestMapping(params = "getBookingDetails", method = RequestMethod.POST)
    public @ResponseBody List<EstatePropResponseDTO> getFilterdList(
            @RequestParam("fromDate") final Date fromDate, @RequestParam("toDate") final Date toDate,
            @RequestParam("propertyName") final String propertyName ,  final HttpServletRequest httpServletRequest) {
		Long orgId = UserSession.getCurrent().getOrganisation().getOrgid();
		
		List<EstatePropResponseDTO> EstatePropResponseDTO = iEstateBookingService.getBookingDetails(fromDate,toDate,propertyName,orgId);
		
		this.getModel().setEstatePropResponseDTO(EstatePropResponseDTO);		
	        return EstatePropResponseDTO ; 
    }
	
	@RequestMapping(params = "receiptDownload", method = { RequestMethod.POST })
	public ModelAndView receiptDownload(@RequestParam("receiptId") Long receiptId,
			final HttpServletRequest httpServletRequest, final HttpServletResponse httpServletResponse) {
		Long orgId = UserSession.getCurrent().getOrganisation().getOrgid();
		ChallanReceiptPrintDTO receiptDto = iEstateBookingService.getDuplicateReceiptDetail(receiptId, orgId);
		getModel().setReceiptDTO(receiptDto);
		if (receiptDto != null) {
			return new ModelAndView("ChallanAtULBReceiptPrint", MainetConstants.FORM_NAME, getModel());
		}
		return null;

	}
	

}
