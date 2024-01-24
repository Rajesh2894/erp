package com.abm.mainet.rnl.dto;

import java.io.Serializable;

import com.abm.mainet.common.dto.ResponseDTO;

/**
 * @author ritesh.patil
 *
 */
public class BookingResDTO extends ResponseDTO implements Serializable {

    private static final long serialVersionUID = -6653209424095584576L;

    private String bookingNo;

    /**
     * @return the bookingNo
     */
    public String getBookingNo() {
        return bookingNo;
    }

    /**
     * @param bookingNo the bookingNo to set
     */
    public void setBookingNo(final String bookingNo) {
        this.bookingNo = bookingNo;
    }

}