package com.abm.mainet.cms.ui.model;

import java.util.ArrayList;
import java.util.List;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;
import org.springframework.web.context.WebApplicationContext;

import com.abm.mainet.cms.dto.ThemeMasterDTO;
import com.abm.mainet.common.ui.model.AbstractFormModel;

@Component
@Scope(value = WebApplicationContext.SCOPE_SESSION)
public class ThemeMasterFormModel extends AbstractFormModel {

    private static final long serialVersionUID = 5560728024318413907L;

    private List<ThemeMasterDTO> themeMasterDTOList = new ArrayList<>();

    public List<ThemeMasterDTO> getThemeMasterDTOList() {
        return themeMasterDTOList;
    }

    public void setThemeMasterDTOList(List<ThemeMasterDTO> themeMasterDTOList) {
        this.themeMasterDTOList = themeMasterDTOList;
    }

}
