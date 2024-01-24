package com.abm.mainet.rnl.ui.model;

import java.util.ArrayList;
import java.util.List;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.abm.mainet.common.dto.CommonChallanDTO;
import com.abm.mainet.common.ui.model.AbstractFormModel;
import com.abm.mainet.rnl.dto.BookingReqDTO;
import com.abm.mainet.rnl.dto.EstatePropMaster;
import com.abm.mainet.rnl.dto.EstatePropResponseDTO;


/**
 * @author pooja.maske
 *
 */
@Component
@Scope("session")
public class DuplicateReceiptPrintingModel extends AbstractFormModel {

	private static final long serialVersionUID = 2447434346296344411L;
	
	 private BookingReqDTO bookingReqDTO = new BookingReqDTO();
	 
	 private List<EstatePropMaster> estatePropMasterList = new ArrayList<>();
	 
	 private EstatePropMaster estatePropMasterDto = new EstatePropMaster();
	 
	 List<EstatePropResponseDTO> estatePropResponseDTO = new ArrayList<>();


	public BookingReqDTO getBookingReqDTO() {
		return bookingReqDTO;
	}

	public void setBookingReqDTO(BookingReqDTO bookingReqDTO) {
		this.bookingReqDTO = bookingReqDTO;
	}

	public List<EstatePropMaster> getEstatePropMasterList() {
		return estatePropMasterList;
	}

	public void setEstatePropMasterList(List<EstatePropMaster> estatePropMasterList) {
		this.estatePropMasterList = estatePropMasterList;
	}

	public EstatePropMaster getEstatePropMasterDto() {
		return estatePropMasterDto;
	}

	public void setEstatePropMasterDto(EstatePropMaster estatePropMasterDto) {
		this.estatePropMasterDto = estatePropMasterDto;
	}

	public List<EstatePropResponseDTO> getEstatePropResponseDTO() {
		return estatePropResponseDTO;
	}

	public void setEstatePropResponseDTO(List<EstatePropResponseDTO> estatePropResponseDTO) {
		this.estatePropResponseDTO = estatePropResponseDTO;
	}


	 
	 
}
