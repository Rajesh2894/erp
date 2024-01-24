/**
 * 
 */
package com.abm.mainet.rnl.service;

import java.util.List;

import com.abm.mainet.rnl.dto.BookingReqDTO;
import com.abm.mainet.rnl.dto.EstateBookingDTO;
import com.abm.mainet.rnl.dto.PropInfoDTO;

/**
 * @author priti.singh
 *
 */
public interface MPBCancellationService {

	List<EstateBookingDTO> fetchAllBookingsByOrg(Long userId, Long orgId);

	List<PropInfoDTO> fetchAllBookedPropertyDetails(String bookingNo, Long orgId);

	public EstateBookingDTO saveBookingCancellation(EstateBookingDTO dto);

	public BookingReqDTO getBookingDetailsByBookingId(String bookingId, Long orgId);

}
