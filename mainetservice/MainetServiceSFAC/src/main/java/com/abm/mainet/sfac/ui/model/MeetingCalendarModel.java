/**
 * 
 */
package com.abm.mainet.sfac.ui.model;

import java.util.ArrayList;
import java.util.List;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.abm.mainet.common.ui.model.AbstractFormModel;
import com.abm.mainet.sfac.dto.MeetingMasterDto;

/**
 * @author pooja.maske
 *
 */

@Component
@Scope("session")
public class MeetingCalendarModel extends AbstractFormModel{

	/**
	 * 
	 */
	private static final long serialVersionUID = -3001536668372858749L;
	
	private List<MeetingMasterDto> mastDto = new ArrayList<>();
	
	private MeetingMasterDto masterDto = new MeetingMasterDto();

	/**
	 * @return the mastDto
	 */
	public List<MeetingMasterDto> getMastDto() {
		return mastDto;
	}

	/**
	 * @param mastDto the mastDto to set
	 */
	public void setMastDto(List<MeetingMasterDto> mastDto) {
		this.mastDto = mastDto;
	}

	/**
	 * @return the masterDto
	 */
	public MeetingMasterDto getMasterDto() {
		return masterDto;
	}

	/**
	 * @param masterDto the masterDto to set
	 */
	public void setMasterDto(MeetingMasterDto masterDto) {
		this.masterDto = masterDto;
	}
	
	

}
