package com.abm.mainet.cms.ui.model;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.abm.mainet.cms.domain.EIPHomeImages;
import com.abm.mainet.cms.service.IEIPHomePageImageService;
import com.abm.mainet.common.constant.MainetConstants;
import com.abm.mainet.common.ui.model.AbstractEntryFormModel;
import com.abm.mainet.common.util.UserSession;
import com.abm.mainet.common.util.Utility;

/**
 * @author vinay.jangir
 *
 */
@Component
@Scope("session")
public class EIPHomeImagesSerachModel extends AbstractEntryFormModel<EIPHomeImages> {

    private static final long serialVersionUID = -6888694210020995826L;

    @Autowired
    private IEIPHomePageImageService iEIPHomePageImageService;


    public List<EIPHomeImages> querySearchResults(String flag) {
    	List<EIPHomeImages> imagesList = iEIPHomePageImageService.getEIPImagesList(UserSession.getCurrent().getOrganisation(), MainetConstants.FlagS,flag);
    	if(imagesList != null) {    		
    		imagesList.forEach(i->{
    			if(i.getImagePath()!=null && i.getImagePath()!= "") {
    				i.setImagePath(Utility.getImageDetails(i.getImagePath()));    				
    			}
    		});
    	}
    	
    	return imagesList;
    }

    @Override
    protected void initializeModel() {
        super.setCommonHelpDocs("EIPHomeImagesSearch.html");
    }

    String makkerchekkerflag;

    public String getMakkerchekkerflag() {
        return makkerchekkerflag;
    }

    public void setMakkerchekkerflag(final String makkerchekkerflag) {
        this.makkerchekkerflag = makkerchekkerflag;
    }

    public List<EIPHomeImages> getLogoResults(String flag) {
        final List<EIPHomeImages> list = iEIPHomePageImageService.getEIPImagesList(UserSession.getCurrent().getOrganisation(),
                MainetConstants.FlagL,flag);
        for (final EIPHomeImages eipHomeImages : list) {
            if (eipHomeImages.getHmImgOrder() == 1) {
                eipHomeImages.setModuleType(MainetConstants.LEFT);
            } else {
                eipHomeImages.setModuleType("Right");
            }
            if(eipHomeImages.getImagePath()!=null && eipHomeImages.getImagePath()!= "") {
            	eipHomeImages.setImagePath(Utility.getImageDetails(eipHomeImages.getImagePath()));    				
			}
        }

        return list;
    }

}
