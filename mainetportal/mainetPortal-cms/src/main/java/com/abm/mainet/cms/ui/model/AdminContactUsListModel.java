package com.abm.mainet.cms.ui.model;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.abm.mainet.cms.domain.EIPContactUs;
import com.abm.mainet.cms.domain.Feedback;
import com.abm.mainet.cms.service.IEIPContactUsService;
import com.abm.mainet.common.constant.PrefixConstants;
import com.abm.mainet.common.ui.model.AbstractFormModel;
import com.abm.mainet.common.util.CommonMasterUtility;
import com.abm.mainet.common.util.LookUp;
import com.abm.mainet.common.util.UserSession;

@Component
@Scope("session")
public class AdminContactUsListModel extends AbstractFormModel {

    private static final long serialVersionUID = 1722050084535281317L;

    @Autowired
    private IEIPContactUsService iEIPContactUsService;

    public String makkerchekkerflag;

    public String getMakkerchekkerflag() {
        return makkerchekkerflag;
    }

    public void setMakkerchekkerflag(final String makkerchekkerflag) {
        this.makkerchekkerflag = makkerchekkerflag;
    }

    public List<EIPContactUs> generateContactList(String flag) {
        final List<EIPContactUs> list = iEIPContactUsService.getContactList(UserSession.getCurrent().getOrganisation(), flag);
        final List<EIPContactUs> newList = new ArrayList<>();
        EIPContactUs contactUs = null;
        final int languageID = UserSession.getCurrent().getLanguageId();

        for (final EIPContactUs eipContactUs : list) {

            if (languageID == 2) {
                contactUs = new EIPContactUs();
                contactUs.setContactUsId(eipContactUs.getContactUsId());
                contactUs.setContactNameEn(eipContactUs.getContactNameReg());
                contactUs.setMuncipalityName(eipContactUs.getMuncipalityNameReg());
                contactUs.setDepartmentEn(eipContactUs.getDepartmentReg());            
              //  contactUs.setDesignationEn(eipContactUs.getDesignationEn());
                contactUs.setDesignationEn(eipContactUs.getDesignationReg());
                contactUs
                        .setTelephoneNo1En(eipContactUs.getTelephoneNo1En());
                contactUs.setEmail1(eipContactUs.getEmail1());
               contactUs.setFaxNo1En(eipContactUs.getFaxNo1En());
                contactUs.setSequenceNo(eipContactUs.getSequenceNo());
                if (eipContactUs.getFlag() != null) {
                    if (eipContactUs.getFlag().equals("P")) {
                        contactUs.setFlag(getAppSession().getMessage("eip.admin.contactUs.contacttypep"));
                    } else {
                        contactUs.setFlag(getAppSession().getMessage("eip.admin.contactUs.contacttypes"));
                    }
                }
                newList.add(contactUs);

            } else {
                eipContactUs
                        .setTelephoneNo1En(eipContactUs.getTelephoneNo1En());
                
                eipContactUs.setEmail1(eipContactUs.getEmail1());
                if (eipContactUs.getFlag() != null) {
                    if (eipContactUs.getFlag().equals("P")) {
                        eipContactUs.setFlag(getAppSession().getMessage("eip.admin.contactUs.contacttypep"));
                    } else {
                        eipContactUs.setFlag(getAppSession().getMessage("eip.admin.contactUs.contacttypes"));
                    }
                }
                newList.add(eipContactUs);
            }

        }
       
        return newList;
    }
}
