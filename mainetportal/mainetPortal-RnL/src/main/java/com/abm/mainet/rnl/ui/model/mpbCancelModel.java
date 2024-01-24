package com.abm.mainet.rnl.ui.model;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.abm.mainet.common.constant.MainetConstants;
import com.abm.mainet.common.domain.Department;
import com.abm.mainet.common.domain.PortalService;
import com.abm.mainet.common.dto.ChargeDetailDTO;
import com.abm.mainet.common.dto.CommonChallanDTO;
import com.abm.mainet.common.exception.FrameworkException;
import com.abm.mainet.common.service.IDepartmentService;
import com.abm.mainet.common.service.IPortalServiceMasterService;
import com.abm.mainet.common.ui.model.AbstractFormModel;
import com.abm.mainet.common.util.ApplicationSession;
import com.abm.mainet.common.util.CommonMasterUtility;
import com.abm.mainet.common.util.UserSession;
import com.abm.mainet.payment.dto.PaymentRequestDTO;
import com.abm.mainet.payment.service.IChallanService;
import com.abm.mainet.rnl.dto.BookingCancelDTO;
import com.abm.mainet.rnl.dto.BookingReqDTO;
import com.abm.mainet.rnl.dto.BookingResDTO;
import com.abm.mainet.rnl.dto.EstateBookingDTO;
import com.abm.mainet.rnl.dto.PropInfoDTO;
import com.abm.mainet.rnl.service.MPBCancellationService;

@Component
@Scope("session")
public class mpbCancelModel extends AbstractFormModel {

	@Autowired
	MPBCancellationService mpbCancellationService;

	@Autowired
	private IChallanService challanService;
	@Autowired
	private IDepartmentService idepartmentService;

	@Autowired
	private IPortalServiceMasterService iPortalServiceMasterService;

	private static final long serialVersionUID = -7903611958625558578L;

	private static final Logger LOGGER = Logger.getLogger(mpbCancelModel.class);

	private EstateBookingDTO estateBookingDTO = null;

	private List<EstateBookingDTO> estateBookings = new ArrayList<>();
	private BookingCancelDTO bookingCancelDTO = null;

	private List<BookingCancelDTO> bookingCancelList = new ArrayList<>();

	private double amountToPay;

	private PropInfoDTO propInfoDTO;

	private String paymentMode;

	private List<PropInfoDTO> propInfoDtoList = new ArrayList<>();

	private String receiptNo;
	private String propId;
	private BookingReqDTO bookingReqDTO = new BookingReqDTO();

	public EstateBookingDTO getEstateBookingDTO() {
		return estateBookingDTO;
	}

	public void setEstateBookingDTO(EstateBookingDTO estateBookingDTO) {
		this.estateBookingDTO = estateBookingDTO;
	}

	public List<EstateBookingDTO> getEstateBookings() {
		return estateBookings;
	}

	public void setEstateBookings(List<EstateBookingDTO> estateBookings) {
		this.estateBookings = estateBookings;
	}

	public List<BookingCancelDTO> getBookingCancelList() {
		return bookingCancelList;
	}

	public String getPaymentMode() {
		return paymentMode;
	}

	public void setPaymentMode(String paymentMode) {
		this.paymentMode = paymentMode;
	}

	public void setBookingCancelList(List<BookingCancelDTO> bookingCancelList) {
		this.bookingCancelList = bookingCancelList;
	}

	public double getAmountToPay() {
		return amountToPay;
	}

	public void setAmountToPay(double amountToPay) {
		this.amountToPay = amountToPay;
	}

	public BookingCancelDTO getBookingCancelDTO() {
		return bookingCancelDTO;
	}

	public void setBookingCancelDTO(BookingCancelDTO bookingCancelDTO) {
		this.bookingCancelDTO = bookingCancelDTO;
	}

	public PropInfoDTO getPropInfoDTO() {
		return propInfoDTO;
	}

	public void setPropInfoDTO(PropInfoDTO propInfoDTO) {
		this.propInfoDTO = propInfoDTO;
	}

	public List<PropInfoDTO> getPropInfoDtoList() {
		return propInfoDtoList;
	}

	public void setPropInfoDtoList(List<PropInfoDTO> propInfoDtoList) {
		this.propInfoDtoList = propInfoDtoList;
	}

	public String getReceiptNo() {
		return receiptNo;
	}

	public void setReceiptNo(String receiptNo) {
		this.receiptNo = receiptNo;
	}

	public BookingReqDTO getBookingReqDTO() {
		return bookingReqDTO;
	}

	public void setBookingReqDTO(BookingReqDTO bookingReqDTO) {
		this.bookingReqDTO = bookingReqDTO;
	}

	public String getPropId() {
		return propId;
	}

	public void setPropId(String propId) {
		this.propId = propId;
	}

	public boolean saveMPBCancellation() throws IOException {
		// final CommonChallanDTO offline = getOfflineDTO();
		this.getEstateBookingDTO();
		try {

			// bookingResDTO = new BookingResDTO();
			// bookingResDTO.setBookingNo(getEstateBookingDTO().getBookingNo());
			// setAndSaveChallanDto(offline, bookingResDTO);

			final CommonChallanDTO offline = getOfflineDTO();
			final String modeDesc = getNonHierarchicalLookUpObject(offline.getOflPaymentMode()).getLookUpCode();
			offline.setOfflinePaymentText(modeDesc);
			Long orgId = UserSession.getCurrent().getOrganisation().getOrgid();
			this.getEstateBookingDTO().setOrgId(orgId);
			mpbCancellationService.saveBookingCancellation(this.getEstateBookingDTO());

			if ((getOfflineDTO().getOnlineOfflineCheck() == null)
					|| getOfflineDTO().getOnlineOfflineCheck().isEmpty()) {
				addValidationError(getAppSession().getMessage("Payment Mode is Empty"));
			}

			BookingReqDTO bookingReqDTO = mpbCancellationService
					.getBookingDetailsByBookingId(this.getEstateBookingDTO().getBookingNo(), orgId);
			if (bookingReqDTO != null) {
				this.setBookingReqDTO(bookingReqDTO);
			}

			estateBookingDTO.setOrgId(UserSession.getCurrent().getOrganisation().getOrgid());
			estateBookingDTO.setLangId(UserSession.getCurrent().getLanguageId());
			estateBookingDTO.setCreatedBy(UserSession.getCurrent().getEmployee().getEmpId());
			estateBookingDTO.setCreatedDate(new Date());
			estateBookingDTO.setUpdatedDate(new Date());

			estateBookingDTO.setLgIpMac(UserSession.getCurrent().getEmployee().getEmppiservername());
			getBookingReqDTO().setPayAmount(getAmountToPay());

			getBookingReqDTO().setOrgId(UserSession.getCurrent().getOrganisation().getOrgid());
			getBookingReqDTO().setServiceId(bookingReqDTO.getServiceId());

			getBookingReqDTO().setDeptId(bookingReqDTO.getDeptId());
			getBookingReqDTO().setApplicationId(Long.valueOf(bookingReqDTO.getApplicationId()));
			getBookingReqDTO().getEstateBookingDTO()
					.setLgIpMac(UserSession.getCurrent().getEmployee().getEmppiservername());

			/*
			 * BookingResDTO outPutObject =
			 * serviceChallanFor.saveOrUpdateChangeUsage(getBookingReqDTO()); if
			 * (outPutObject.getApplicationNo() != null) {
			 * 
			 * setApmApplicationId(outPutObject.getApplicationNo());
			 * getBookingReqDTO().setApplicationId(outPutObject.getApplicationNo());
			 */
			if (getAmountToPay() > 0D) {
				getChallanFromServiceSite(offline, new BookingResDTO());
			}

			// savePortalMaster(getBookingReqDTO());
			setSuccessMessage(ApplicationSession.getInstance().getMessage("Your Application No. ")
					+ bookingReqDTO.getApplicationId() + MainetConstants.WHITE_SPACE
					+ ApplicationSession.getInstance().getMessage("and Booking No. ")
					+ bookingReqDTO.getEstateBookingDTO().getBookingNo() + MainetConstants.WHITE_SPACE
					+ "Cancel Successfully"
					+ ApplicationSession.getInstance().getMessage("Please Click on button to generate Receipt "));

			return true;

		} catch (FrameworkException exp) {
			LOGGER.warn("Error occured while saving the Data Legacy details:", exp);

		}
		return true;
	}

	private void getChallanFromServiceSite(CommonChallanDTO offline, BookingResDTO bookingResDTO) {

		offline.setApplNo(bookingReqDTO.getApplicationId());
		offline.setAmountToPay(Double.toString(getAmountToPay()));
		offline.setUserId(UserSession.getCurrent().getEmployee().getEmpId());
		offline.setOrgId(UserSession.getCurrent().getOrganisation().getOrgid());
		offline.setEmpType(UserSession.getCurrent().getEmployee().getEmplType());
		offline.setLangId(UserSession.getCurrent().getLanguageId());
		offline.setLgIpMac(UserSession.getCurrent().getEmployee().getEmppiservername());
		offline.setChallanServiceType(MainetConstants.CHALLAN_RECEIPT_TYPE.NON_REVENUE_BASED);
		offline.setDeptId(getBookingReqDTO().getDeptId());
		offline.setServiceId(bookingReqDTO.getServiceId());
		String aplicantName = getBookingReqDTO().getApplicantDetailDto().getApplicantFirstName()
				+ MainetConstants.WHITE_SPACE;
		aplicantName += getBookingReqDTO().getApplicantDetailDto().getApplicantMiddleName() == null
				? MainetConstants.BLANK
				: getBookingReqDTO().getApplicantDetailDto().getApplicantMiddleName() + MainetConstants.WHITE_SPACE;
		aplicantName += getBookingReqDTO().getApplicantDetailDto().getApplicantLastName();
		offline.setApplicantName(aplicantName);
		offline.setMobileNumber(getBookingReqDTO().getApplicantDetailDto().getMobileNo());
		offline.setEmailId(getBookingReqDTO().getApplicantDetailDto().getEmailId());
		offline.setEmpType(UserSession.getCurrent().getEmployee().getEmplType());
		offline.setApplicantAddress(getBookingReqDTO().getApplicantDetailDto().getAreaName() + ","
				+ getBookingReqDTO().getApplicantDetailDto().getVillageTownSub());
		offline.setEmailId(getApplicantDetailDto().getEmailId());
		for (final ChargeDetailDTO dto : getChargesInfo()) {
			offline.getFeeIds().put(dto.getChargeCode(), dto.getChargeAmount());
		}
		offline.setDeptId(getBookingReqDTO().getDeptId());
		if ((offline.getOnlineOfflineCheck() != null) && offline.getOnlineOfflineCheck().equals("Y")) {
			setPaymentMode("Online");
		}
		if ((offline.getOnlineOfflineCheck() != null) && offline.getOnlineOfflineCheck().equals("N")) {
			setPaymentMode(CommonMasterUtility.getNonHierarchicalLookUpObject(offline.getOflPaymentMode(),
					UserSession.getCurrent().getOrganisation()).getLookUpDesc());
			offline = challanService.generateChallanNumber(offline);
			setOfflineDTO(offline);
		}
	}

	@Override
	public void redirectToPayDetails(final HttpServletRequest httpServletRequest,
			final PaymentRequestDTO payURequestDTO) {

		final BookingReqDTO bookingReqDTO = getBookingReqDTO();
		String userName = (bookingReqDTO.getApplicantDetailDto().getApplicantFirstName() == null ? " "
				: bookingReqDTO.getApplicantDetailDto().getApplicantFirstName()) + " ";
		userName += bookingReqDTO.getApplicantDetailDto().getApplicantMiddleName() == null ? " "
				: bookingReqDTO.getApplicantDetailDto().getApplicantMiddleName() + " ";
		userName += bookingReqDTO.getApplicantDetailDto().getApplicantLastName() == null ? " "
				: bookingReqDTO.getApplicantDetailDto().getApplicantLastName();
		final PortalService portalServiceMaster = iPortalServiceMasterService.getService(bookingReqDTO.getServiceId(),
				UserSession.getCurrent().getOrganisation().getOrgid());
		payURequestDTO.setUdf3("CitizenHome.html");
		payURequestDTO.setUdf5(portalServiceMaster.getShortName());
		payURequestDTO.setUdf7(String.valueOf(bookingReqDTO.getApplicationId()));
		payURequestDTO.setApplicantName(userName);
		payURequestDTO.setServiceId(portalServiceMaster.getServiceId());
		payURequestDTO.setUdf2(String.valueOf(bookingReqDTO.getApplicationId()));
		payURequestDTO.setMobNo(bookingReqDTO.getApplicantDetailDto().getMobileNo());
		payURequestDTO.setDueAmt(new BigDecimal(getAmountToPay()));
		payURequestDTO.setEmail(bookingReqDTO.getApplicantDetailDto().getEmailId());
		payURequestDTO.setApplicationId(String.valueOf(bookingReqDTO.getApplicationId()));
		payURequestDTO.setOrgId(bookingReqDTO.getEstateBookingDTO().getOrgId());
		// Adding department Id in udf10 of payURequestDTO object
		if (portalServiceMaster != null) {
			payURequestDTO.setUdf10(portalServiceMaster.getPsmDpDeptid().toString());
			if (UserSession.getCurrent().getLanguageId() == MainetConstants.ENGLISH) {
				payURequestDTO.setServiceName(portalServiceMaster.getServiceName());
			} else {
				payURequestDTO.setServiceName(portalServiceMaster.getServiceNameReg());
			}
		}
	}

}
