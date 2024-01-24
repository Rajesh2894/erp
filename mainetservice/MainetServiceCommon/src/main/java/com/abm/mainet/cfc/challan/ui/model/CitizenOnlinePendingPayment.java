
package com.abm.mainet.cfc.challan.ui.model;

import java.util.ArrayList;
import java.util.List;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.abm.mainet.common.domain.ScrutinyServiceDto;
import com.abm.mainet.common.ui.model.AbstractFormModel;

/**
 * @author cherupelli.srikanth
 *
 */

@Component
@Scope(value = "session")
public class CitizenOnlinePendingPayment extends AbstractFormModel{

	private static final long serialVersionUID = -5566243840514129954L;

	private List<ScrutinyServiceDto> serviceList = new ArrayList<ScrutinyServiceDto>();

	public List<ScrutinyServiceDto> getServiceList() {
		return serviceList;
	}

	public void setServiceList(List<ScrutinyServiceDto> serviceList) {
		this.serviceList = serviceList;
	}
}
