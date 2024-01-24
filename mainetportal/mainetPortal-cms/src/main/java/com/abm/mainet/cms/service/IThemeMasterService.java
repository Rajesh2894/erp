package com.abm.mainet.cms.service;

import java.util.List;
import java.util.Map;

import com.abm.mainet.cms.domain.ThemeMaster;
import com.abm.mainet.cms.dto.ThemeMasterDTO;

public interface IThemeMasterService {

    boolean saveThemeMaster(List<ThemeMasterDTO> master, long orgid, long empId);

    ThemeMaster getThemeMaster(ThemeMasterDTO masterDTO);

    ThemeMaster updateThemeMaster(ThemeMasterDTO masterDTO);

    ThemeMaster deleteThemeMaster(ThemeMasterDTO masterDTO);

    List<ThemeMasterDTO> getThemeMasterByOrgid(Long orgid);

    Map<String, String> getThemeMasterMapByOrgid(Long orgid);

}
