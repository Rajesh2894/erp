package com.abm.mainet.common.master.ui.model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.annotation.Resource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;
import org.springframework.web.context.WebApplicationContext;

import com.abm.mainet.cfc.loi.dao.ILoiMasterPaymentDAO;
import com.abm.mainet.common.constant.MainetConstants;
import com.abm.mainet.common.dao.ICFCApplicationMasterDAO;
import com.abm.mainet.common.repository.TbWorkOrderJpaRepository;
import com.abm.mainet.common.ui.model.AbstractFormModel;
import com.abm.mainet.common.utility.ApplicationSession;
import com.abm.mainet.common.utility.UserSession;

@Component
@Scope(value = WebApplicationContext.SCOPE_SESSION)
public class IssuanceCounterModel extends AbstractFormModel {

    private static final long serialVersionUID = -4442534621943983201L;

    private String applicationSelected;
    private String issuedFlag;
    private String typeSelected;

    public String getApplicationSelected() {
        return applicationSelected;
    }

    public void setApplicationSelected(String applicationSelected) {
        this.applicationSelected = applicationSelected;
    }
    
   
    public String getIssuedFlag() {
		return issuedFlag;
	}

	public void setIssuedFlag(String issuedFlag) {
		this.issuedFlag = issuedFlag;
	}
	

	public String getTypeSelected() {
		return typeSelected;
	}

	public void setTypeSelected(String typeSelected) {
		this.typeSelected = typeSelected;
	}


	@Autowired
    private ICFCApplicationMasterDAO cfcApplicationMasterDAO;
	
	@Autowired
	private ILoiMasterPaymentDAO loiMasterPaymentDAO;
	
	@Resource
    private TbWorkOrderJpaRepository tbWorkOrderJpaRepository;
	

    @Override
    public boolean saveForm() {
    	applicationSelected=applicationSelected.substring(0,applicationSelected.length()-1);
        String splitIds[] = applicationSelected.split(",");
       
        List<Long> applicationIds = new ArrayList<>();
        List<String> typeChecked = new ArrayList<>();
      
        Map<Long,String> hm = new HashMap<Long,String>();
        for (String arr:splitIds) {
        	 String arrs[] = arr.split("~");
        	 if (arrs.length>1) {
        		 applicationIds.add(Long.valueOf(arrs[0])) ;
        		 typeChecked.add(arrs[1].toString());
        		 hm.put(Long.valueOf(arrs[0]), arrs[1].toString());
        	 }
        }
        if (!applicationIds.isEmpty() && !typeChecked.isEmpty()) {
        Set<Long> keySet = hm.keySet();
        for(Long applicationId:keySet)
        {	
        	String tableType = hm.get(applicationId);
        	if (tableType.equals(MainetConstants.CERTIFICATE)) {
                cfcApplicationMasterDAO.updateIssuanceDataInCFCApplication(applicationId,
                        UserSession.getCurrent().getEmployee().getEmpId(), UserSession.getCurrent().getOrganisation().getOrgid());
            	}else if (tableType.equals(MainetConstants.LOI_STRING)) {
            		 loiMasterPaymentDAO.updateIssuanceDataInLoiMas(applicationId,
                             UserSession.getCurrent().getEmployee().getEmpId(), UserSession.getCurrent().getOrganisation().getOrgid());
            	}else if(tableType.equals(MainetConstants.WORKORDER)){
            		tbWorkOrderJpaRepository.updateIssuanceDataInWorkOrder(applicationId,UserSession.getCurrent().getEmployee().getEmpId(), UserSession.getCurrent().getOrganisation().getOrgid());
            	}
        }
         setSuccessMessage(ApplicationSession.getInstance().getMessage("issuance.save.success"));
        } else {
            setSuccessMessage(ApplicationSession.getInstance().getMessage("issuance.save.fail"));
        }
        return true;

    }

}
