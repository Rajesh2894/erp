package com.abm.mainet.rnl.dto;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class BookingCalanderDTO implements Serializable {
    private static final long serialVersionUID = 546779085904122111L;
    private List<EstateBookingDTO> bookingDTOs = new ArrayList<>();

    /**
     * @return the bookingDTOs
     */
    public List<EstateBookingDTO> getBookingDTOs() {
        return bookingDTOs;
    }

    /**
     * @param bookingDTOs the bookingDTOs to set
     */
    public void setBookingDTOs(final List<EstateBookingDTO> bookingDTOs) {
        this.bookingDTOs = bookingDTOs;
    }
}
