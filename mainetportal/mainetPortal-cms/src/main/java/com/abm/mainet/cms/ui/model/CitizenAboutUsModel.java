package com.abm.mainet.cms.ui.model;

import java.io.Serializable;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.abm.mainet.cms.domain.EIPAboutUs;
import com.abm.mainet.cms.domain.EIPAboutUsHistory;
import com.abm.mainet.cms.service.IEIPAboutUsService;
import com.abm.mainet.cms.service.ISectionService;
import com.abm.mainet.common.constant.MainetConstants;
import com.abm.mainet.common.dto.PortalPhotoDTO;
import com.abm.mainet.common.dto.PortalVideoDTO;
import com.abm.mainet.common.ui.model.AbstractFormModel;

@Component
@Scope(value = "session")
public class CitizenAboutUsModel extends AbstractFormModel implements Serializable {

    private static final long serialVersionUID = 7826092254142861404L;
    private int i = 0;
    private EIPAboutUs citizenAboutUs;
    private EIPAboutUsHistory citizenAboutUsHistroy;
    private String aboutUsDescFirstPara;
    private String contrastscheme;
    private String textsize;

    @Autowired
    private ISectionService iSectionService;

    public String getTextsize() {
        return textsize;
    }

    public void setTextsize(final String textsize) {
        this.textsize = textsize;
    }

    public String getContrastscheme() {
        return contrastscheme;
    }

    public void setContrastscheme(final String contrastscheme) {
        this.contrastscheme = contrastscheme;
    }

    private String aboutUsDescSecondPara;

    @Autowired
    private IEIPAboutUsService iEIPAboutUsService;

    public void getAboutUs() {
        setCitizenAboutUsHistroy(
                iEIPAboutUsService.getGuestAboutUs(getUserSession().getOrganisation(), MainetConstants.IsDeleted.NOT_DELETE));

        if (getCitizenAboutUsHistroy() != null) {
            if (getUserSession().getLanguageId() != 1L) {
                if (getCitizenAboutUsHistroy().getDescriptionReg1() != null) {
                    setAboutUsDescFirstPara(getCitizenAboutUsHistroy().getDescriptionReg());
                    setAboutUsDescSecondPara(getCitizenAboutUsHistroy().getDescriptionReg1());
                } else {
                    setAboutUsDescFirstPara(getCitizenAboutUsHistroy().getDescriptionReg());
                    setAboutUsDescSecondPara(MainetConstants.BLANK);
                }

            } else {
                if (getCitizenAboutUsHistroy().getDescriptionEn1() != null) {
                    setAboutUsDescFirstPara(getCitizenAboutUsHistroy().getDescriptionEn());
                    setAboutUsDescSecondPara(getCitizenAboutUsHistroy().getDescriptionEn1());
                } else {
                    setAboutUsDescFirstPara(getCitizenAboutUsHistroy().getDescriptionEn());
                    setAboutUsDescSecondPara(MainetConstants.BLANK);
                }
            }
        }
    }

    /**
     * @return the citizenAboutUs
     */
    public EIPAboutUs getCitizenAboutUs() {
        return citizenAboutUs;
    }

    /**
     * @param citizenAboutUs the citizenAboutUs to set
     */
    public void setCitizenAboutUs(final EIPAboutUs citizenAboutUs) {
        this.citizenAboutUs = citizenAboutUs;
    }

    /**
     * @return the aboutUsDescFirstPara
     */
    public String getAboutUsDescFirstPara() {
        return aboutUsDescFirstPara;
    }

    /**
     * @param aboutUsDescFirstPara the aboutUsDescFirstPara to set
     */
    public void setAboutUsDescFirstPara(final String aboutUsDescFirstPara) {
        this.aboutUsDescFirstPara = aboutUsDescFirstPara;
    }

    /**
     * @return the aboutUsDescSecondPara
     */
    public String getAboutUsDescSecondPara() {
        return aboutUsDescSecondPara;
    }

    /**
     * @param aboutUsDescSecondPara the aboutUsDescSecondPara to set
     */
    public void setAboutUsDescSecondPara(final String aboutUsDescSecondPara) {
        this.aboutUsDescSecondPara = aboutUsDescSecondPara;
    }

    @Override
    public String getActiveClass() {
        return "about";
    }

    public int getI() {
        return i;
    }

    public void setI(final int i) {
        this.i = i;
    }

    public List<String> getAllhtml(final String CK_Editer3) {
        return iSectionService.getAllhtml(CK_Editer3);
    }

    public EIPAboutUsHistory getCitizenAboutUsHistroy() {
        return citizenAboutUsHistroy;
    }

    public void setCitizenAboutUsHistroy(EIPAboutUsHistory citizenAboutUsHistroy) {
        this.citizenAboutUsHistroy = citizenAboutUsHistroy;
    }

    public List<PortalPhotoDTO> getAllphotos(final String Photogallery) {
        return iSectionService.findhomepagephotos(Photogallery);
    }

    public List<PortalVideoDTO> getAllvideos(final String videogallery) {
        return iSectionService.findhomepagevideos(videogallery);
    }

}
