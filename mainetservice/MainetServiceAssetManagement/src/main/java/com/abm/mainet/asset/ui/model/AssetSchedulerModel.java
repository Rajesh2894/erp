/**
 * 
 */
package com.abm.mainet.asset.ui.model;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;
import org.springframework.web.context.WebApplicationContext;

import com.abm.mainet.asset.ui.dto.AstSchedulerMasterDTO;
import com.abm.mainet.common.ui.model.AbstractFormModel;

/**
 * @author satish.rathore
 *
 */

/*
 * Defect #5054 Help document is not displayed after clicking on help icon.
 */
@Component
@Scope(value = WebApplicationContext.SCOPE_SESSION)
public class AssetSchedulerModel extends AbstractFormModel {

    /**
     * 
     */
    private static final long serialVersionUID = -7905657914637130292L;
    AstSchedulerMasterDTO astschDto = new AstSchedulerMasterDTO();

    public AstSchedulerMasterDTO getAstschDto() {
        return astschDto;
    }

    public void setAstschDto(AstSchedulerMasterDTO astschDto) {
        this.astschDto = astschDto;
    }

}
