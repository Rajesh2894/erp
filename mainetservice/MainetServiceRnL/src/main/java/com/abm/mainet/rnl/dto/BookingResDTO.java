package com.abm.mainet.rnl.dto;

import java.io.Serializable;

import com.abm.mainet.cfc.challan.dto.ChallanReceiptPrintDTO;
import com.abm.mainet.common.integration.dto.ResponseDTO;

/**
 * @author ritesh.patil
 *
 */
public class BookingResDTO extends ResponseDTO implements Serializable {

    private static final long serialVersionUID = -6653209424095584576L;

    private ChallanReceiptPrintDTO challanReceiptPrintDTO;
    private String bookingNo;

    /**
     * @return the challanReceiptPrintDTO
     */
    public ChallanReceiptPrintDTO getChallanReceiptPrintDTO() {
        return challanReceiptPrintDTO;
    }

    /**
     * @param challanReceiptPrintDTO the challanReceiptPrintDTO to set
     */
    public void setChallanReceiptPrintDTO(final ChallanReceiptPrintDTO challanReceiptPrintDTO) {
        this.challanReceiptPrintDTO = challanReceiptPrintDTO;
    }

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