/**
 * 
 */
package com.abm.mainet.rnl.service;

import java.util.List;
import javax.jws.WebService;

import com.abm.mainet.rnl.dto.BookingReqDTO;
import com.abm.mainet.rnl.dto.EstateBookingDTO;
import com.abm.mainet.rnl.dto.PropInfoDTO;

/**
 * @author priti.singh
 *
 */
@WebService
public interface MpbCancellationService {

	public EstateBookingDTO saveBookingCancellation(final EstateBookingDTO dto);

	int findAllDetailsbyBookingId(Long id, Long orgId);

	public List<EstateBookingDTO> getBookedPropertyDetails(Long orgId);

	public List<PropInfoDTO> getAllBookedDetails(String bookingNo, Long orgId);

	public List<EstateBookingDTO> getBookedPropertyBasedOnUseridNOrgid(Long userId, Long orgId);

	public List<EstateBookingDTO> getAllBookedPropertyByOrgId(Long orgId);

	public BookingReqDTO getAllBookingDetailsByBookingId(Long bookingId, Long orgId);

}
