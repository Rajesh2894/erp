package com.abm.mainet.rnl.ui.model;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.abm.mainet.cfc.challan.domain.ChallanMaster;
import com.abm.mainet.cfc.challan.dto.ChallanReceiptPrintDTO;
import com.abm.mainet.cfc.challan.service.IChallanService;
import com.abm.mainet.common.constant.MainetConstants;
import com.abm.mainet.common.dao.ICFCApplicationMasterDAO;
import com.abm.mainet.common.domain.CFCApplicationAddressEntity;
import com.abm.mainet.common.domain.ServiceMaster;
import com.abm.mainet.common.domain.TbCfcApplicationMstEntity;
import com.abm.mainet.common.dto.CommonChallanDTO;
import com.abm.mainet.common.exception.FrameworkException;
import com.abm.mainet.common.integration.dto.ChargeDetailDTO;
import com.abm.mainet.common.master.repository.DepartmentJpaRepository;
import com.abm.mainet.common.master.service.TbServicesMstService;
import com.abm.mainet.common.service.ServiceMasterService;
import com.abm.mainet.common.ui.model.AbstractFormModel;
import com.abm.mainet.common.utility.CommonMasterUtility;
import com.abm.mainet.common.utility.UserSession;
import com.abm.mainet.rnl.domain.EstateBooking;
import com.abm.mainet.rnl.dto.BookingCancelDTO;
import com.abm.mainet.rnl.dto.BookingResDTO;
import com.abm.mainet.rnl.dto.EstateBookingDTO;
import com.abm.mainet.rnl.dto.EstatePropResponseDTO;
import com.abm.mainet.rnl.dto.PropInfoDTO;
import com.abm.mainet.rnl.repository.EstateBookingRepository;
import com.abm.mainet.rnl.service.IEstatePropertyService;
import com.abm.mainet.rnl.service.MpbCancellationService;

@Component
@Scope("session")
public class MPBCancelModel extends AbstractFormModel {

	@Autowired
	MpbCancellationService mpbCancellationService;
	@Resource
	private ICFCApplicationMasterDAO cfcApplicationMasterDAO;

	@Autowired
	private EstateBookingRepository estateBookingRepository;

	@Autowired
	private IEstatePropertyService iEstatePropertyService;

	@Autowired
	private IChallanService challanService;
	@Autowired
	private DepartmentJpaRepository deptRepo;

	@Autowired
	private ServiceMasterService serviceMasterService;

	private static final long serialVersionUID = -7903611958625558578L;

	private static final Logger LOGGER = Logger.getLogger(MPBCancelModel.class);

	private EstateBookingDTO estateBookingDTO = null;

	private BookingCancelDTO bookingCancelDTO = null;

	private List<EstateBookingDTO> estateBookings = new ArrayList<>();

	private double amountToPay;

	private PropInfoDTO propInfoDTO;

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

	public double getAmountToPay() {
		return amountToPay;
	}

	public void setAmountToPay(double amountToPay) {
		this.amountToPay = amountToPay;
	}

	public PropInfoDTO getPropInfoDTO() {
		return propInfoDTO;
	}

	public void setPropInfoDTO(PropInfoDTO propInfoDTO) {
		this.propInfoDTO = propInfoDTO;
	}

	public BookingCancelDTO getBookingCancelDTO() {
		return bookingCancelDTO;
	}

	public void setBookingCancelDTO(BookingCancelDTO bookingCancelDTO) {
		this.bookingCancelDTO = bookingCancelDTO;
	}

	public boolean saveMPBCancellation() {
		// change for Defect #39643
		final CommonChallanDTO offline = getOfflineDTO();
		this.getEstateBookingDTO();
		try {
			this.getEstateBookingDTO().setOrgId(UserSession.getCurrent().getOrganisation().getOrgid());
			mpbCancellationService.saveBookingCancellation(this.getEstateBookingDTO());
			BookingResDTO bookingResDTO = new BookingResDTO();
			bookingResDTO.setBookingNo(getEstateBookingDTO().getBookingNo());
			if (!this.getBookingCancelDTO().getIsFree()) {
				setAndSaveChallanDto(offline, bookingResDTO);
			}

			setSuccessMessage(getAppSession().getMessage("Booking Cancelled Successfully for Booking No :")
					+ this.getEstateBookingDTO().getBookingNo());

		} catch (FrameworkException exp) {
			LOGGER.warn("Error occured while saving the Data Legacy details:", exp);

		}
		return true;
	}

//add for Defect #39643
	private void setAndSaveChallanDto(CommonChallanDTO offline, BookingResDTO bookingResDTO) {
		Long orgId = UserSession.getCurrent().getOrganisation().getOrgid();
		EstateBooking estateBooking = estateBookingRepository.getbookingIdbyBookingNo(bookingResDTO.getBookingNo(),
				orgId);
		Long deptId = deptRepo.getDepartmentIdByDeptCode(MainetConstants.RnLCommon.RentLease);

		ServiceMaster serviceMaster = serviceMasterService
				.getServiceByShortName(MainetConstants.EstateBooking.ESTATE_BOOKING_CANCELLATION_SERVICECODE, orgId);
		offline.setApplNo(estateBooking.getApplicationId());
		TbCfcApplicationMstEntity entity = cfcApplicationMasterDAO
				.getCFCApplicationMasterByApplicationId(estateBooking.getApplicationId(), orgId);
		CFCApplicationAddressEntity cfcApplicationAddress = cfcApplicationMasterDAO
				.getApplicantsDetailsDao(estateBooking.getApplicationId());
		offline.setAmountToPay(Double.toString(getAmountToPay()));
		offline.setOrgId(orgId);
		offline.setUserId(UserSession.getCurrent().getEmployee().getEmpId());
		offline.setLangId(UserSession.getCurrent().getLanguageId());
		offline.setChallanServiceType(MainetConstants.CHALLAN_RECEIPT_TYPE.NON_REVENUE_BASED);
		offline.setFaYearId(UserSession.getCurrent().getFinYearId());
		offline.setFinYearStartDate(UserSession.getCurrent().getFinStartDate());
		offline.setFinYearEndDate(UserSession.getCurrent().getFinEndDate());
		offline.setServiceId(serviceMaster.getSmServiceId());
		String aplicantName = entity.getApmFname() + MainetConstants.WHITE_SPACE;
		aplicantName += entity.getApmMname() == null ? MainetConstants.BLANK
				: entity.getApmMname() + MainetConstants.WHITE_SPACE;
		aplicantName += entity.getApmLname();
		offline.setApplicantName(aplicantName);
		offline.setMobileNumber(cfcApplicationAddress.getApaMobilno());
		offline.setEmailId(cfcApplicationAddress.getApaEmail());
		offline.setEmpType(UserSession.getCurrent().getEmployee().getEmplType());
		offline.setApplicantAddress(cfcApplicationAddress.getApaAreanm());

		for (ChargeDetailDTO dto : getChargesInfo()) {
			offline.getFeeIds().put(dto.getChargeCode(), dto.getChargeAmount());
		}

		offline.setLgIpMac(getClientIpAddress());
		offline.setLoggedLocId(UserSession.getCurrent().getLoggedLocId());
		offline.setDeptId(deptId);
		offline.setOfflinePaymentText(CommonMasterUtility
				.getNonHierarchicalLookUpObject(offline.getOflPaymentMode(), UserSession.getCurrent().getOrganisation())
				.getLookUpCode());

		if ((offline.getOnlineOfflineCheck() != null)
				&& offline.getOnlineOfflineCheck().equals(MainetConstants.MENU.N)) {

			final ChallanMaster responseChallan = challanService.InvokeGenerateChallan(offline);

			offline.setChallanNo(responseChallan.getChallanNo());
			offline.setChallanValidDate(responseChallan.getChallanValiDate());

			setOfflineDTO(offline);
		} else if ((offline.getOnlineOfflineCheck() != null)
				&& offline.getOnlineOfflineCheck().equals(MainetConstants.PAYMENT_TYPE.PAY_AT_COUNTER)) {
			long propId = mpbCancellationService.findAllDetailsbyBookingId(Long.valueOf(estateBooking.getId()),
					UserSession.getCurrent().getOrganisation().getOrgid());
			EstatePropResponseDTO estatePropResponseDTO = iEstatePropertyService.findPropertyForBooking(propId,
					UserSession.getCurrent().getOrganisation().getOrgid());

			String propertyName = estatePropResponseDTO.getPropName();
			offline.setServiceName(propertyName);
			offline.setDeptId(deptId);
			offline.setFromedate(estateBookingDTO.getFromDate());
			offline.setToDate(estateBookingDTO.getToDate());
			final ChallanReceiptPrintDTO printDto = challanService.savePayAtUlbCounter(offline,
					serviceMaster.getSmServiceName());
			setReceiptDTO(printDto);
			setSuccessMessage(getAppSession().getMessage("receipt genrate"));
		}
	}

}
