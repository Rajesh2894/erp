package com.abm.mainet.rnl.service;

import java.util.Date;
import java.util.List;

import javax.jws.WebService;
import javax.ws.rs.Consumes;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import org.springframework.web.bind.annotation.RequestBody;

import com.abm.mainet.cfc.challan.dto.ChallanReceiptPrintDTO;
import com.abm.mainet.common.domain.Organisation;
import com.abm.mainet.common.dto.CommonChallanDTO;
import com.abm.mainet.common.dto.WardZoneBlockDTO;
import com.abm.mainet.common.master.dto.ContractAgreementSummaryDTO;
import com.abm.mainet.common.utility.LookUp;
import com.abm.mainet.common.workflow.dto.WorkflowTaskAction;
import com.abm.mainet.rnl.domain.EstateBooking;
import com.abm.mainet.rnl.domain.EstateBookingCancel;
import com.abm.mainet.rnl.dto.BookingCancelDTO;
import com.abm.mainet.rnl.dto.BookingReqDTO;
import com.abm.mainet.rnl.dto.BookingResDTO;
import com.abm.mainet.rnl.dto.EstateBookingDTO;
import com.abm.mainet.rnl.dto.EstatePropMaster;
import com.abm.mainet.rnl.dto.EstatePropReqestDTO;
import com.abm.mainet.rnl.dto.EstatePropResponseDTO;
import com.abm.mainet.rnl.dto.EstatePropertyEventDTO;
import com.abm.mainet.rnl.dto.EstatePropertyShiftDTO;
import com.abm.mainet.rnl.dto.PropFreezeDTO;
import com.abm.mainet.rnl.dto.PropInfoDTO;
import com.abm.mainet.rnl.dto.PropertyResDTO;
import com.abm.mainet.rnl.dto.TankerBookingDetailsDTO;

/**
 * @author ritesh.patil
 *
 */

@Consumes({ MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML })
@Produces(MediaType.APPLICATION_JSON)
@WebService
public interface IEstateBookingService {

	/**
	 * save Estate Booking
	 * 
	 * @param bookingReqDTO
	 * @param offline
	 * @param serviceName
	 * @return
	 */
	BookingResDTO saveEstateBooking(BookingReqDTO bookingReqDTO,CommonChallanDTO offline, String serviceName);

	/**
	 * get Word Zone Block By ApplicationId
	 * 
	 * @param applicationId
	 * @param serviceId
	 * @param orgId
	 * @return
	 */
	WardZoneBlockDTO getWordZoneBlockByApplicationId(Long applicationId, Long serviceId, Long orgId);

	/**
	 * get Estate Booking From And To Dates
	 * 
	 * @param id
	 * @param orgId
	 * @return
	 */
	List<String> getEstateBookingFromAndToDates(Long id, Long orgId,Boolean isAllShift);

	/**
	 * find By PropId
	 * 
	 * @param propId
	 * @param orgId
	 * @return
	 */
	List<EstateBookingDTO> findByPropId(Long propId, Long orgId);

	/**
	 * get Estate Booking From And To Dates For General
	 * 
	 * @param propId
	 * @param orgId
	 * @return
	 */
	List<String> getEstateBookingFromAndToDatesForGeneral( @RequestBody Long propId, Long orgId);

	/**
	 * get Estate Booking Shifts
	 * 
	 * @param propId
	 * @param fromdate
	 * @param todate
	 * @param orgId
	 * @return
	 */
	List<LookUp> getEstateBookingShifts(Long propId, String fromdate, String todate, Long orgId);

	/**
	 * find Booking
	 * 
	 * @param bookId
	 * @param orgId
	 * @return
	 */
	PropInfoDTO findBooking(Long bookId, Long orgId);

	/**
	 * save Freeze Property
	 * 
	 * @param estateBookingDTO
	 * @return
	 */
	boolean saveFreezeProperty(EstateBookingDTO estateBookingDTO);

	/**
	 * find All Freeze Booking Prop
	 * 
	 * @param orgId
	 * @param bookingStatus
	 * @return
	 */
	List<PropFreezeDTO> findAllFreezeBookingProp(Long orgId, String bookingStatus);

	/**
	 * update Freeze Property
	 * 
	 * @param id
	 * @param empId
	 * @return
	 */
	int updateFreezeProperty(Long id, Long empId);

	/**
	 * find Count For Property
	 * 
	 * @param orgId
	 * @param propId
	 * @return
	 */
	boolean findCountForProperty(Long orgId, Long propId);

	/**
	 * get Filtered Rented Properties
	 * 
	 * @param categoryId
	 * @param eventId
	 * @param capacityFrom
	 * @param capacityTo
	 * @param rentFrom
	 * @param rentTo
	 * @param org
	 * @return
	 */
	PropertyResDTO getFilteredRentedProperties(Integer categoryId, Long eventId, long capacityFrom, long capacityTo,
			double rentFrom, double rentTo, Organisation org);

	/**
	 * get All Rented Properties
	 * 
	 * @param subCategoryName
	 * @param prefixName
	 * @param orgId
	 * @param subCategoryType
	 * @return
	 */
	PropertyResDTO getAllRentedProperties(@RequestBody String subCategoryName, String prefixName, Long orgId,
			Integer subCategoryType);

	/**
	 * get Shift Details From Date And To date
	 * 
	 * @param propId
	 * @param fromdate
	 * @param todate
	 * @param orgId
	 * @return EstatePropertShiftDto List
	 */
	List<EstatePropertyShiftDTO> getShiftDetailsFromDateAndTodate(@RequestBody Long propId, String fromdate, String todate,
			Long orgId);

	/**
	 * Update Estate Booking Status
	 * 
	 * @param estateBookingId
	 * @param empId
	 */
	void updateEstateBookingStatus(Long estateBookingId, Long empId);

	List<EstateBookingDTO> fetchAllBookingsByOrg(Long orgId, String bookingStatus);

	List<BookingCancelDTO> fetchChargesDetails(Long apmApplicationId, Long orgId);

	void updateDataForCancelBooking(EstateBookingDTO estateBookingDTO,
			List<EstateBookingCancel> estateBookingCancelEntities, List<BookingCancelDTO> bookingCancelList);

	

	EstateBooking findbookingIdbyBookingNo(String bookingNo, Long orgId);
	
	 public List<EstatePropertyEventDTO> getEventOrPropertyId(Integer categoryId, Long orgId);
	 
	
	 public void enableEstateBookingStatus(String bookingNo, Long orgId,Date fromDate,Date toDate);
	 
	 CommonChallanDTO getForChallanVerification(Long orgId, Long applicationId);
	        
	 List<EstateBookingDTO> checkedReceiptValiadtion(long orgId,Long propId);
	 
	
	 CommonChallanDTO getForPropertyname(Long applicationId, Long orgId);
	 
	 
	  Boolean checkPropertyBookedByEventId(Long propId,List<Long> eventIds, Long orgId);

	List<EstatePropResponseDTO> getBookingDetails(Date fromDate, Date toDate, String propertyName, Long orgId);

	EstateBookingDTO getBookingDetailsByApplId(Long apmApplicationId, Long orgId);

	List<EstatePropMaster> getPropetyDetailsByOrgId(Long orgId);

	ChallanReceiptPrintDTO getDuplicateReceiptDetail(Long receiptId, Long orgId);


	List<EstatePropResponseDTO> getEstateBookingDetails(EstatePropResponseDTO requestDto);
	
	List<ContractAgreementSummaryDTO> getSummaryDataFromPortal(Long orgId);

	ContractAgreementSummaryDTO fetchSearchData(String contNo, String propertyContractNo, Long orgId);

	List<String> checkData(String contNo, String inputAmount, Long orgId);

	ContractAgreementSummaryDTO upadteDataFromPortal(ContractAgreementSummaryDTO requestDto);

	BookingResDTO saveWaterTanker(BookingReqDTO bookingReqDTO, CommonChallanDTO offline, String smServiceName);

	List<EstateBookingDTO> getDetailByAppIdAndOrgId(long applicationId, long orgid);
	
	Long findPropIdByAppId(Long applicationId, long orgid);

	boolean executeApprovalWorkflowAction(WorkflowTaskAction taskAction, String smShortdesc, Long smServiceId,
			String smShortdesc2);

	BookingReqDTO saveDriverDetail(BookingReqDTO bookingReqDTO);

	void saveReturnDetail(BookingReqDTO bookingReqDTO);

	TankerBookingDetailsDTO getDriverData(Long id);

	PropertyResDTO getFilteredWaterTanker(Integer categoryId, Long eventId, Organisation organisation);

	ContractAgreementSummaryDTO fetchSearchDataUsingContAndProp(String contNo, String propertyContractNo, Long orgId);

	BookingReqDTO getWaterTankerDetailByAppId(Long applicationId, Long orgId);

	List<LookUp> getEstateBookingShiftsData(Long propId, Long orgId);
	    
}
