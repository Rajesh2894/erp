/**
 * 
 */
package com.abm.mainet.rnl.service;

import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.annotation.Resource;
import javax.jws.WebService;
import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.abm.mainet.common.constant.MainetConstants;
import com.abm.mainet.common.dao.ICFCApplicationMasterDAO;
import com.abm.mainet.common.domain.CFCApplicationAddressEntity;
import com.abm.mainet.common.domain.ServiceMaster;
import com.abm.mainet.common.domain.TbCfcApplicationMstEntity;
import com.abm.mainet.common.dto.ApplicantDetailDTO;
import com.abm.mainet.common.exception.FrameworkException;
import com.abm.mainet.common.master.repository.DepartmentJpaRepository;
import com.abm.mainet.common.master.repository.TbCfcApplicationMstJpaRepository;
import com.abm.mainet.common.service.ServiceMasterService;
import com.abm.mainet.common.utility.ApplicationContextProvider;
import com.abm.mainet.rnl.domain.EstateBooking;
import com.abm.mainet.rnl.dto.BookingReqDTO;
import com.abm.mainet.rnl.dto.EstateBookingDTO;
import com.abm.mainet.rnl.dto.PropInfoDTO;
import com.abm.mainet.rnl.repository.EstateBookingCancelRepository;
import com.abm.mainet.rnl.repository.EstateBookingRepository;
import com.abm.mainet.rnl.ui.model.MPBCancelModel;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;

/**
 * @author priti.singh
 *
 */
@Produces("application/json")
@WebService(endpointInterface = "com.abm.mainet.rnl.service.MpbCancellationService")
@Api(value = "/estaeBookingCancelation")
@Path("/estaeBookingCancelation")
@Service
public class MpbCancellationServiceImpl implements MpbCancellationService {

	@Autowired
	private EstateBookingRepository estateBookingRepository;
	@Autowired
	private ServiceMasterService serviceMasterService;
	@Resource
	private ICFCApplicationMasterDAO cfcApplicationMasterDAO;

	@Autowired
	private IEstateBookingService iEstateBookingService;

	@Autowired
	private EstateBookingCancelRepository estateBookingCancelRepository;

	@Autowired
	private DepartmentJpaRepository deptRepo;

	@Override
	@Consumes("application/json")
	@ApiOperation(value = "find All Booking Details", notes = "find All Booking Details")
	@POST
	@Path("/saveBookingCancellation")
	@Transactional
	public EstateBookingDTO saveBookingCancellation(EstateBookingDTO dto) {
		try {

			estateBookingRepository.cancelEstateBooking(dto.getBookingNo(), dto.getOrgId());
		} catch (Exception ex) {

			throw new FrameworkException("Exception occured while saving the Estate Booking Cancellation", ex);
		}

		return dto;
	}

	@Override
	@Consumes("application/json")
	@ApiOperation(value = "find All Booking Details", notes = "find All Booking Details")
	@POST
	@Path("/findAllDetailsbyBookingId")
	@Transactional
	public int findAllDetailsbyBookingId(Long id, Long orgId) {

		return estateBookingRepository.findAllDetailsByBookingId(id, orgId);
	}

	/*
	 * @Consumes("application/json")
	 * 
	 * @ApiOperation(value = "find All Booking Details", notes =
	 * "find All Booking Details")
	 * 
	 * @POST
	 * 
	 * @Path("/getBookedPropertyDetails/orgId/{orgId}")
	 * 
	 * @Transactional
	 */
	@Override
	public List<EstateBookingDTO> getBookedPropertyDetails(@PathParam("orgId") Long orgId) {

		TbCfcApplicationMstJpaRepository tbCfcApplicationMstJpaRepository = ApplicationContextProvider
				.getApplicationContext().getBean(TbCfcApplicationMstJpaRepository.class);
		// fetch data from estate Booking
		// TB_RL_ESTATE_BOOKING table
		MPBCancelModel model = null;
		List<EstateBookingDTO> bookingNos = new ArrayList<>();
		List<EstateBookingDTO> estateBookings = iEstateBookingService.fetchAllBookingsByOrg(orgId,
				MainetConstants.FlagY);
		SimpleDateFormat dateFormat = new SimpleDateFormat(MainetConstants.DATE_FORMAT_UPLOAD);
		Date currentDate = null;
		try {
			currentDate = dateFormat.parse(dateFormat.format(new Date()));
		} catch (ParseException e) {

			e.printStackTrace();
		} // make data for booking id

		for (EstateBookingDTO dto : estateBookings) {

			// Only Future Bookings will be visible

			if (dto.getCancelDate() == null
					&& (currentDate.before(dto.getFromDate()) || currentDate.equals(dto.getFromDate()))) {

				EstateBookingDTO bookingNo = new EstateBookingDTO();

				BeanUtils.copyProperties(dto, bookingNo);
				List<Object[]> appliInfoList = tbCfcApplicationMstJpaRepository.getApplicantInfo(dto.getApplicationId(),
						orgId);
				String name = "";
				for (final Object[] strings : appliInfoList) {
					if (strings[1] != null) {
						name = strings[1].toString();
					}
					bookingNo.setBookingNo(bookingNo.getBookingNo() + " - " + strings[0].toString() + " " + name + " "
							+ strings[2].toString());
				}
				bookingNos.add(bookingNo);

			}
		}

		return bookingNos;

	}

	/* service for portal to display details on enter booking no and orgId */

	@Override
	@Consumes("application/json")
	@ApiOperation(value = "find All Details", notes = "find All Details")
	@POST
	@Path("/getAllBookedDetails/{bookingNo}/{orgId}")
	@Transactional
	public List<PropInfoDTO> getAllBookedDetails(@PathParam("bookingNo") String bookingNo,
			@PathParam("orgId") Long orgId) {

		List<Object[]> estimateMastersEntity = estateBookingCancelRepository.getAllBookedData(bookingNo, orgId);
		List<PropInfoDTO> dtos = new ArrayList<PropInfoDTO>();

		estimateMastersEntity.forEach(abc -> {
			PropInfoDTO dto = new PropInfoDTO();
			dto.setAmount((BigDecimal) abc[1]);
			dto.setReceiptNo(abc[0].toString());
			dto.setCategory((String) abc[25]);
			dto.setPropName((String) abc[20]);
			dto.setAreaName((String) abc[32]);
			dto.setCity(abc[33].toString());
			dto.setPinCode(abc[30].toString());
			dto.setBookingNo(abc[10].toString());
			dto.setApplicantName((String) abc[5]);
			dto.setContactNo((String) abc[31]);
			dto.setBookingPuprpose((String) abc[15]);
			dto.setFromDate((Date) abc[12]);
			dto.setToDate((Date) abc[13]);
			dto.setPaymentModedesc((String) abc[26]);
			dto.setOrgName((String) abc[29]);
			dto.setDayPeriod(abc[16].toString());
			dto.setPropId(abc[8].toString());
			dtos.add(dto);
		});

		return dtos;
	}

	/* service for portal to display dropdown data */

	@Override
	@Consumes("application/json")
	@ApiOperation(value = "find All Booking Details", notes = "find All Booking Details")
	@POST
	@Path("/getBookedPropertyDetails/{userId}/{orgId}")
	@Transactional
	public List<EstateBookingDTO> getBookedPropertyBasedOnUseridNOrgid(@PathParam("userId") Long userId,
			@PathParam("orgId") Long orgId) {

		TbCfcApplicationMstJpaRepository tbCfcApplicationMstJpaRepository = ApplicationContextProvider
				.getApplicationContext().getBean(TbCfcApplicationMstJpaRepository.class);
		// fetch data from estate Booking // TB_RL_ESTATE_BOOKING table
		MPBCancelModel model = null;
		List<EstateBookingDTO> bookingNos = new ArrayList<>();
		List<EstateBookingDTO> estateBookings = new ArrayList<>();

		List<EstateBooking> estateBooking = estateBookingCancelRepository.getBookedDatabyUseridandOrgid(userId, orgId);

		estateBooking.stream().forEach(l -> {
			EstateBookingDTO estateBookingDTO = new EstateBookingDTO();

			estateBookingDTO.setApplicationId(l.getApplicationId());
			estateBookingDTO.setBookingNo(l.getBookingNo());
			estateBookingDTO.setFromDate(l.getFromDate());
			estateBookingDTO.setToDate(l.getToDate());
			estateBookingDTO.setId(l.getId());
			estateBookings.add(estateBookingDTO);

		});

		SimpleDateFormat dateFormat = new SimpleDateFormat(MainetConstants.DATE_FORMAT_UPLOAD);
		Date currentDate = null;
		try {
			currentDate = dateFormat.parse(dateFormat.format(new Date()));
		} catch (ParseException e) {

			e.printStackTrace();
		}

		for (EstateBookingDTO dto : estateBookings) {

			// Only Future Bookings will be visible

			if (dto.getCancelDate() == null
					&& (currentDate.before(dto.getFromDate()) || currentDate.equals(dto.getFromDate()))) {

				EstateBookingDTO bookingNo = new EstateBookingDTO();

				BeanUtils.copyProperties(dto, bookingNo);
				List<Object[]> appliInfoList = tbCfcApplicationMstJpaRepository.getApplicantInfo(dto.getApplicationId(),
						orgId);
				String name = "";
				for (final Object[] strings : appliInfoList) {
					if (strings[1] != null) {
						name = strings[1].toString();
					}
					bookingNo.setBookingNo(bookingNo.getBookingNo() + " - " + strings[0].toString() + " " + name + " "
							+ strings[2].toString());
				}
				bookingNos.add(bookingNo);

			}
		}
		return bookingNos;

	}

	@Override
	public List<EstateBookingDTO> getAllBookedPropertyByOrgId(Long orgId) {

		List<EstateBooking> estateBookingList = estateBookingCancelRepository.getAllBookedPropertiesByOrgId(orgId);
		List<EstateBookingDTO> dtos = new ArrayList<>();
		for (EstateBooking estate : estateBookingList) {

			EstateBookingDTO dto = new EstateBookingDTO();
			if (dto.getApplicationId() != null) {
				estate.setApplicationId(dto.getApplicationId());
			}
			BeanUtils.copyProperties(estate, dto);
			dtos.add(dto);
		}
		return dtos;
	}

//add method  for Defect #39643
	@Override
	@POST
	@ApiOperation(value = "fetch Estate Booking Details by booking no", notes = "fetch Estate Booking Details by booking no", response = BookingReqDTO.class)
	@Path("/getAllBookingDetailsByBookingId/{bookingId}/{orgId}")
	public BookingReqDTO getAllBookingDetailsByBookingId(
			@ApiParam(value = "bookingId", required = true) @PathParam("bookingId") Long bookingId,
			@ApiParam(value = "orgId", required = true) @PathParam("orgId") Long orgId) {
		// TODO Auto-generated method stub
		BookingReqDTO bookingReqDto = new BookingReqDTO();
		EstateBooking estateBooking = estateBookingRepository.getbookingIdbyBookingNo(bookingId.toString(), orgId);
		if (estateBooking != null) {
			TbCfcApplicationMstEntity entity = cfcApplicationMasterDAO
					.getCFCApplicationMasterByApplicationId(estateBooking.getApplicationId(), orgId);
			CFCApplicationAddressEntity cfcApplicationAddress = cfcApplicationMasterDAO
					.getApplicantsDetailsDao(estateBooking.getApplicationId());
			ServiceMaster serviceMaster = serviceMasterService.getServiceByShortName(
					MainetConstants.EstateBooking.ESTATE_BOOKING_CANCELLATION_SERVICECODE, orgId);
			Long deptId = deptRepo.getDepartmentIdByDeptCode(MainetConstants.RnLCommon.RentLease);
			bookingReqDto.getEstateBookingDTO().setBookingNo(bookingId.toString());
			bookingReqDto.setBldgName(cfcApplicationAddress.getApaBldgnm());
			bookingReqDto.setAreaName(cfcApplicationAddress.getApaAreanm());
			bookingReqDto.setBlockName(cfcApplicationAddress.getApaBlockName());
			bookingReqDto.getApplicantDetailDto().setApplicantFirstName(entity.getApmFname());
			bookingReqDto.getApplicantDetailDto().setApplicantLastName(entity.getApmLname());
			bookingReqDto.getApplicantDetailDto().setApplicantMiddleName(entity.getApmMname());
			bookingReqDto.getApplicantDetailDto().setEmailId(cfcApplicationAddress.getApaEmail());
			bookingReqDto.getApplicantDetailDto().setGender(entity.getApmSex());
			bookingReqDto.getApplicantDetailDto().setMobileNo(cfcApplicationAddress.getApaMobilno());
			bookingReqDto.getEstateBookingDTO().setAmount(estateBooking.getAmount());
			bookingReqDto.getEstateBookingDTO().setBookingDate(estateBooking.getBookingDate());
			bookingReqDto.getEstateBookingDTO().setCancelDate(estateBooking.getCancelDate());
			bookingReqDto.getEstateBookingDTO().setFromDate(estateBooking.getFromDate());
			bookingReqDto.getEstateBookingDTO().setToDate(estateBooking.getToDate());
			bookingReqDto.setApplicationId(estateBooking.getApplicationId());
			bookingReqDto.setServiceId(serviceMaster.getSmServiceId());
			bookingReqDto.setDeptId(deptId);
		}

		return bookingReqDto;
	}

}