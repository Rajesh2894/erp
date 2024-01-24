/**
 * 
 */
package com.abm.mainet.common.master.ui.model;

import java.util.ArrayList;
import java.util.List;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.abm.mainet.common.master.dto.TbDepartment;
import com.abm.mainet.common.ui.model.AbstractFormModel;

/**
 * @author cherupelli.srikanth
 *@since 29 june 2020
 */
@Component
@Scope("session")
public class CommonManualReceiptEntryModel extends AbstractFormModel{

	private static final long serialVersionUID = -3548949077815909960L;

	private List<TbDepartment> deparatmentList = new ArrayList<>();
	
	private String depShortCode;

	public List<TbDepartment> getDeparatmentList() {
		return deparatmentList;
	}

	public void setDeparatmentList(List<TbDepartment> deparatmentList) {
		this.deparatmentList = deparatmentList;
	}

	public String getDepShortCode() {
		return depShortCode;
	}

	public void setDepShortCode(String depShortCode) {
		this.depShortCode = depShortCode;
	}
	
	
}
