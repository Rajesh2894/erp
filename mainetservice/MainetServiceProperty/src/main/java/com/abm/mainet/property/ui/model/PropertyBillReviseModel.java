/**
 * 
 */
package com.abm.mainet.property.ui.model;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.abm.mainet.common.ui.model.AbstractFormModel;
import com.abm.mainet.property.dto.ProvisionalAssesmentMstDto;

/**
 * @author cherupelli.srikanth
 * @since 01 July 2021
 */

@Component
@Scope("session")
public class PropertyBillReviseModel extends AbstractFormModel{

	private static final long serialVersionUID = -769667431159832015L;
	
	private ProvisionalAssesmentMstDto provisionalAssesmentMstDto = new ProvisionalAssesmentMstDto();// Main DTO to Bind

	public ProvisionalAssesmentMstDto getProvisionalAssesmentMstDto() {
		return provisionalAssesmentMstDto;
	}

	public void setProvisionalAssesmentMstDto(ProvisionalAssesmentMstDto provisionalAssesmentMstDto) {
		this.provisionalAssesmentMstDto = provisionalAssesmentMstDto;
	}
	
	
	

}
