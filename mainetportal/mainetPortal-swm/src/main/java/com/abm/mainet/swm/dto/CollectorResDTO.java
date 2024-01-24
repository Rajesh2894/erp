package com.abm.mainet.swm.dto;

import java.io.Serializable;

import com.abm.mainet.common.dto.ResponseDTO;

/**
 * @author Sarojkumar.yadav
 *
 */
public class CollectorResDTO extends ResponseDTO implements Serializable {

	private static final long serialVersionUID = -6653209424095584576L;

	private String bookingNo;

	public String getBookingNo() {
		return bookingNo;
	}

	public void setBookingNo(String bookingNo) {
		this.bookingNo = bookingNo;
	}

	@Override
	public String toString() {
		return "CollectorResDTO [bookingNo=" + bookingNo + "]";
	}

}