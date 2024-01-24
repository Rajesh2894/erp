package com.abm.mainet.legal.ui.model;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.abm.mainet.common.constant.MainetConstants;
import com.abm.mainet.common.domain.Employee;
import com.abm.mainet.common.ui.model.AbstractFormModel;
import com.abm.mainet.common.utility.ApplicationSession;
import com.abm.mainet.common.utility.UserSession;
import com.abm.mainet.legal.dto.AdvocateMasterDTO;
import com.abm.mainet.legal.dto.CourtMasterDTO;
import com.abm.mainet.legal.service.IAdvocateMasterService;
import com.abm.mainet.legal.ui.validator.AdvocateMasterValidator;

@Component
@Scope("session")
public class AdvocateMasterModel extends AbstractFormModel {

    private static final long serialVersionUID = 1L;
    private String saveMode;

    private AdvocateMasterDTO advocateMasterDTO;
    private List<AdvocateMasterDTO> advocateMasterDTOList;
    private List<CourtMasterDTO> courtMasterDTOList = new ArrayList<>();
    private List<CourtMasterDTO> courtNameList;
    private String envFlag;
    
	@Autowired
	private IAdvocateMasterService advocateMasterService;

    @Override
    public boolean saveForm() {
        validateBean(advocateMasterDTO, AdvocateMasterValidator.class);
        if (hasValidationErrors()) {
            return false;
        } else {
            advocateMasterDTO.setOrgid(UserSession.getCurrent().getOrganisation().getOrgid());
            List<AdvocateMasterDTO> list = advocateMasterService.validateAdvocateMaster(advocateMasterDTO);
            //Defect #105841
            if(CollectionUtils.isNotEmpty(list)) {
            for (AdvocateMasterDTO dto : list) {
            	if(dto.getAdvMobile().equals(advocateMasterDTO.getAdvMobile())) {
            		this.addValidationError(ApplicationSession.getInstance().getMessage("lgl.validation.phone.exist")); 	           		  
            	}if(dto.getAdvEmail().equalsIgnoreCase(advocateMasterDTO.getAdvEmail())) {
					this.addValidationError(ApplicationSession.getInstance().getMessage("lgl.validation.email.exist"));				
				} 
				if(StringUtils.isNotEmpty(dto.getAdvPanno())) {
				if(dto.getAdvPanno().equalsIgnoreCase(advocateMasterDTO.getAdvPanno())) 
					this.addValidationError(ApplicationSession.getInstance().getMessage("lgl.validation.panNo.exist"));	
				}
				if(StringUtils.isNotEmpty(dto.getAdvUid())) {
				if(dto.getAdvUid().equalsIgnoreCase(advocateMasterDTO.getAdvUid())) 
					this.addValidationError(ApplicationSession.getInstance().getMessage("lgl.validation.adhar.exist"));									  
				}	
				if (hasValidationErrors()) {
		            return false;
		        }
			}   
            		
            }else {           
                Employee employee = UserSession.getCurrent().getEmployee();
                if (advocateMasterDTO.getAdvId() != null) {
                    advocateMasterDTO.setUpdatedBy(employee.getEmpId());
                    advocateMasterDTO.setUpdatedDate(new Date());
                    advocateMasterDTO.setLgIpMacUpd(employee.getEmppiservername());
                    advocateMasterService.updateAdvocateMaster(advocateMasterDTO);
                    setSuccessMessage(ApplicationSession.getInstance().getMessage("lgl.editAdvocateMaster"));
                } else {
                    advocateMasterDTO.setAdvStatus(MainetConstants.CommonConstants.Y);
                    advocateMasterDTO.setCreatedBy(employee.getEmpId());
                    advocateMasterDTO.setCreatedDate(new Date());
                    advocateMasterDTO.setLgIpMac(employee.getEmppiservername());
                    advocateMasterService.saveAdvocateMaster(advocateMasterDTO);
                    setSuccessMessage(ApplicationSession.getInstance().getMessage("lgl.saveAdvocateMaster"));
                }
   
                return true;
            }
        }
     
		return true;

    }
    

    public List<CourtMasterDTO> getCourtMasterDTOList() {
		return courtMasterDTOList;
	}


	public void setCourtMasterDTOList(List<CourtMasterDTO> courtMasterDTOList) {
		this.courtMasterDTOList = courtMasterDTOList;
	}


	public String getSaveMode() {
        return saveMode;
    }

    public void setSaveMode(String saveMode) {
        this.saveMode = saveMode;
    }

    public List<AdvocateMasterDTO> getAdvocateMasterDTOList() {
        return advocateMasterDTOList;
    }

    public void setAdvocateMasterDTOList(List<AdvocateMasterDTO> advocateMasterDTOList) {
        this.advocateMasterDTOList = advocateMasterDTOList;
    }

    public AdvocateMasterDTO getAdvocateMasterDTO() {
        return advocateMasterDTO;
    }

    public void setAdvocateMasterDTO(AdvocateMasterDTO advocateMasterDTO) {
        this.advocateMasterDTO = advocateMasterDTO;
    }


	public List<CourtMasterDTO> getCourtNameList() {
		return courtNameList;
	}


	public void setCourtNameList(List<CourtMasterDTO> courtNameList) {
		this.courtNameList = courtNameList;
	}


	public String getEnvFlag() {
		return envFlag;
	}


	public void setEnvFlag(String envFlag) {
		this.envFlag = envFlag;
	}
	
}
