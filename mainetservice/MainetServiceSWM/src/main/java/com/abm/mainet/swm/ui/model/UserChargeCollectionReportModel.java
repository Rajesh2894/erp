package com.abm.mainet.swm.ui.model;

import java.util.ArrayList;
import java.util.List;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.abm.mainet.common.ui.model.AbstractFormModel;
import com.abm.mainet.swm.dto.UserChargeCollectionDTO;

@Component
@Scope("session")
public class UserChargeCollectionReportModel extends AbstractFormModel {

    private static final long serialVersionUID = 1L;
    
    UserChargeCollectionDTO userCharges = new UserChargeCollectionDTO();    

    List<UserChargeCollectionDTO> usercollections = new ArrayList<>();

    public UserChargeCollectionDTO getUserCharges() {
        return userCharges;
    }

    public void setUserCharges(UserChargeCollectionDTO userCharges) {
        this.userCharges = userCharges;
    }

    public List<UserChargeCollectionDTO> getUsercollections() {
        return usercollections;
    }

    public void setUsercollections(List<UserChargeCollectionDTO> usercollections) {
        this.usercollections = usercollections;
    }
    
    
  
    

}
