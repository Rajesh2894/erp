package com.abm.mainet.water.ui.model;

import java.util.List;

import javax.annotation.Resource;

import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.abm.mainet.common.constant.MainetConstants;
import com.abm.mainet.common.constant.PrefixConstants;
import com.abm.mainet.common.ui.model.AbstractFormModel;
import com.abm.mainet.common.utility.UserSession;
import com.abm.mainet.common.utility.Utility;
import com.abm.mainet.water.dto.DemandNoticeRequestDTO;
import com.abm.mainet.water.dto.DemandNoticeResponseDTO;
import com.abm.mainet.water.dto.TbCsmrInfoDTO;
import com.abm.mainet.water.service.DemandNoticeGenarationService;
import com.abm.mainet.water.service.NewWaterConnectionService;

@Component
@Scope("session")
public class DemandNoticeGenerationModel extends
        AbstractFormModel {

    private static final long serialVersionUID = -3307269739839482467L;
    private String billCcnType;
    private TbCsmrInfoDTO entity = null;
    @Autowired
    private NewWaterConnectionService newWaterConnectionService;
    public String getBillCcnType() {
		return billCcnType;
	}

	public void setBillCcnType(String billCcnType) {
		this.billCcnType = billCcnType;
	}

	public TbCsmrInfoDTO getEntity() {
		return entity;
	}

	public void setEntity(TbCsmrInfoDTO entity) {
		this.entity = entity;
	}

	@Resource
    private DemandNoticeGenarationService demandNoticeGenarationService;

    private DemandNoticeRequestDTO dto = null;
    private List<DemandNoticeResponseDTO> response = null;

    @Override
    protected void initializeModel() {

        initializeLookupFields(PrefixConstants.WATERMODULEPREFIX.TRF);
        initializeLookupFields(PrefixConstants.WATERMODULEPREFIX.WWZ);
        initializeLookupFields(PrefixConstants.WATERMODULEPREFIX.CSZ);
        initializeLookupFields(PrefixConstants.WATERMODULEPREFIX.WDN);
        initializeLookupFields(MainetConstants.NewWaterServiceConstants.WMN);
    }

    @Override
    public boolean saveForm() {

    	if(Utility.isEnvPrefixAvailable(UserSession.getCurrent().getOrganisation(), "ASCL")) {
    		search();
    		if(hasValidationErrors()) {
    			return false;
    		}
    		if(CollectionUtils.isNotEmpty(response)) {
    			response.forEach(res ->{
    				res.setSelected(true);
    			});
    		}else {
    			 addValidationError(getAppSession()
                         .getMessage(
                                 "No record found"));
    			 return false;
    		}
    	}
        if (demandNoticeGenarationService
                .generateDemandNotice(response, UserSession
                        .getCurrent().getEmployee()
                        .getEmpId(),
                        UserSession
                                .getCurrent().getOrganisation()
                                .getOrgid(),
                        UserSession
                                .getCurrent().getLanguageId())) {
            setSuccessMessage(getAppSession().getMessage("demand.success"));
            return true;
        } else {
            addValidationError(getAppSession().getMessage(
                    "demand.select"));
            return false;
        }

    }

    /**
     * @return
     */
    public boolean search() {
        boolean result = false;
        response = null;
        dto.setOrgid(UserSession.getCurrent()
                .getOrganisation().getOrgid());
        Boolean checkFormValidations = false;
        if(MainetConstants.MENU.M.equals(getBillCcnType())){
        	checkFormValidations = checkFormValidations();
        	if(!checkFormValidations) {
        		return false;
        	}
        }
        if(MainetConstants.SEARCH.equals(getBillCcnType()) || checkFormValidations) {
        	Long csIdn = null;
        	if(MainetConstants.SEARCH.equals(getBillCcnType()) ){
        		 if ((getEntity().getCsCcn().equals(null))
                         || getEntity().getCsCcn()
                                 .isEmpty()) {
                     addValidationError(getAppSession().getMessage("water.billGen.ccnNumber"));
                 }

                 Long checkValidConn = newWaterConnectionService.checkValidConnectionNumber(getEntity().getCsCcn(),
                         UserSession.getCurrent().getOrganisation().getOrgid());
                 if (checkValidConn == null || checkValidConn <= 0) {
                     addValidationError(getAppSession()
                             .getMessage(
                                     "water.invalidConnection"));
                 }
        		TbCsmrInfoDTO csmrInfo = newWaterConnectionService.fetchConnectionDetailsByConnNo(getEntity().getCsCcn(),
    					UserSession.getCurrent().getOrganisation().getOrgid());
        		csIdn = csmrInfo.getCsIdn(); 
        	}
        	if(Utility.isEnvPrefixAvailable(UserSession.getCurrent()
                    .getOrganisation(), MainetConstants.ENV_ASCL)) {
                response = demandNoticeGenarationService.searchAllDefaulterForAscl(dto, csIdn);

        	}else {
                response = demandNoticeGenarationService.searchAllDefaulter(dto);
        	}
            if ((response == null) || response.isEmpty()) {
                addValidationError(getAppSession().getMessage(
                        "demand.noresult"));
            } else {
                result = true;
            }
        }
        return result;
    }

    private Boolean checkFormValidations() {
    	Boolean validate = true;
    	if (dto.getWdn() <= 0) {
            addValidationError(getAppSession().getMessage("demand.errormsg"));
            validate = false;
        }
    	
    	if(dto.getWwz1() <= 0) {
    		addValidationError(getAppSession().getMessage("Please select ward zone"));
            validate = false;
    	}
		/*
		 * if(!dto.getAmountFrom().isEmpty() && Double.valueOf(dto.getAmountFrom()) < 0)
		 * { addValidationError(getAppSession().getMessage("demand.amountFrom"));
		 * validate = false; }else if(!dto.getAmountFrom().isEmpty() &&
		 * Double.valueOf(dto.getAmountFrom()) > 0 && dto.getAmountTo().isEmpty()) {
		 * addValidationError(getAppSession().getMessage("demand.amountToMandatory"));
		 * validate = false; }else if(!dto.getAmountFrom().isEmpty() &&
		 * Double.valueOf(dto.getAmountTo()) < Double.valueOf(dto.getAmountFrom())) {
		 * addValidationError(getAppSession().getMessage("demand.amountTo")); validate =
		 * false; }
		 */ 
    	return validate;
	}

	@Override
    protected final String findPropertyPathPrefix(
            final String parentCode) {
        String result;
        switch (parentCode) {

        case PrefixConstants.NewWaterServiceConstants.TRF:
            result = "dto.trf";
            break;
        case PrefixConstants.NewWaterServiceConstants.WWZ:
            result = "dto.wwz";
            break;
        case PrefixConstants.WATERMODULEPREFIX.CSZ:
            result = "dto.csz";
            break;
        case PrefixConstants.WATERMODULEPREFIX.WDN:
            result = "dto.wdn";
            break;
        case PrefixConstants.NewWaterServiceConstants.WMN:
            result = "dto.wmn";
            break;
        default:
            result = MainetConstants.BLANK;
            break;
        }
        return result;
    }

    public DemandNoticeRequestDTO getDto() {
        return dto;
    }

    public void setDto(final DemandNoticeRequestDTO dto) {
        this.dto = dto;
    }

    public List<DemandNoticeResponseDTO> getResponse() {
        return response;
    }

    public void setResponse(
            final List<DemandNoticeResponseDTO> response) {
        this.response = response;
    }

}
