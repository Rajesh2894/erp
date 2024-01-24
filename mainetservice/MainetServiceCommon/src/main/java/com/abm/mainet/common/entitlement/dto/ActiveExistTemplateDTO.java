/**
 * 
 */
package com.abm.mainet.common.entitlement.dto;

import java.util.Map;

import org.springframework.stereotype.Component;

/**
 * @author Harsha.Ramachandran
 *
 */
@Component
public class ActiveExistTemplateDTO {
	
	private Map<Long,String> roleEntitleIds;
	
	private Long dpDeptId;
	
	private String roleDescriptionEng;
	
	private String roleDescriptionReg;

	
	public Map<Long, String> getRoleEntitleIds() {
		return roleEntitleIds;
	}

	public void setRoleEntitleIds(Map<Long, String> roleEntitleIds) {
		this.roleEntitleIds = roleEntitleIds;
	}

	public Long getDpDeptId() {
		return dpDeptId;
	}

	public void setDpDeptId(Long dpDeptId) {
		this.dpDeptId = dpDeptId;
	}

	public String getRoleDescriptionEng() {
		return roleDescriptionEng;
	}

	public void setRoleDescriptionEng(String roleDescriptionEng) {
		this.roleDescriptionEng = roleDescriptionEng;
	}

	public String getRoleDescriptionReg() {
		return roleDescriptionReg;
	}

	public void setRoleDescriptionReg(String roleDescriptionReg) {
		this.roleDescriptionReg = roleDescriptionReg;
	}
	
	
}
