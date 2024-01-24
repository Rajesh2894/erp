/**
 * @author  : Harshit kumar
 * @since   : 04 Apr 2018
 * @comment : Utility file for RTI for common utility method
 * @method  : getPrefixDesc - for getting prefix desc
 *              
 */
package com.abm.mainet.rti.utility;

import java.util.List;

import org.springframework.stereotype.Component;

import com.abm.mainet.common.constant.MainetConstants;
import com.abm.mainet.common.domain.CFCApplicationAddressEntity;
import com.abm.mainet.common.domain.TbCfcApplicationMstEntity;
import com.abm.mainet.common.integration.dto.RequestDTO;
import com.abm.mainet.common.utility.CommonMasterUtility;
import com.abm.mainet.common.utility.LookUp;
import com.abm.mainet.common.utility.UserSession;

@Component
public class RtiUtility {

	public String getPrefixDesc(String code, Long id) {
		String prefixDesc = MainetConstants.BLANK;
		List<LookUp> lookUps = CommonMasterUtility.getLookUps(code, UserSession.getCurrent().getOrganisation());
		for (LookUp lookUp : lookUps) {
			if (lookUp.getLookUpId() == id) {
				prefixDesc = lookUp.getLookUpDesc();
				break;
			}
		}
		return prefixDesc;
	}

	public String getPrefixCode(String code, Long id) {
		String prefixCode = MainetConstants.BLANK;
		List<LookUp> lookUps = CommonMasterUtility.getLookUps(code, UserSession.getCurrent().getOrganisation());
		for (LookUp lookUp : lookUps) {
			if (lookUp.getLookUpId() == id) {
				prefixCode = lookUp.getLookUpCode();
				break;
			}
		}
		return prefixCode;
	}

	public Long getPrefixId(String code, String value) {
		Long prefixId = 0L;
		List<LookUp> lookUps = CommonMasterUtility.getLookUps(code, UserSession.getCurrent().getOrganisation());
		for (LookUp lookUp : lookUps) {
			if (lookUp.getLookUpCode().equalsIgnoreCase(value)) {
				prefixId = lookUp.getLookUpId();
				break;
			}
		}
		return prefixId;

	}

	/*
	 * public LookUp getLookupData(String code, Object value) { LookUp lookUp = new
	 * LookUp(); List<LookUp> lookUps = CommonMasterUtility.getLookUps(code,
	 * UserSession.getCurrent().getOrganisation()); for (LookUp lup : lookUps) {
	 * if() } return lookUp; }
	 */

	public static RequestDTO getApplicationDetails(TbCfcApplicationMstEntity app, CFCApplicationAddressEntity add) {
		RequestDTO cad = new RequestDTO();
		cad.setTitleId(app.getApmTitle());
		cad.setfName(app.getApmFname());
		cad.setmName(app.getApmMname());
		cad.setlName(app.getApmLname());
		cad.setGender(app.getApmSex());
		cad.setLangId(app.getLangId());
		cad.setMobileNo(add.getApaMobilno());
		cad.setEmail(add.getApaEmail());
		cad.setFlatBuildingNo(add.getApaFlatBuildingNo());
		cad.setBldgName(add.getApaBldgnm());
		cad.setRoadName(add.getApaRoadnm());
		cad.setBlockName(add.getApaBlockName());
		cad.setAreaName(add.getApaAreanm());
		cad.setCityName(add.getApaCityName());
		cad.setPincodeNo(add.getApaPincode());
		cad.setOrgId(app.getTbOrganisation().getOrgid());
		cad.setApplicationId(app.getApmApplicationId());
		cad.setServiceId(app.getTbServicesMst().getSmServiceId());
		return cad;
	}
}