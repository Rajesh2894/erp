package com.abm.mainet.cms.dao;

import java.util.List;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.abm.mainet.cms.domain.ThemeMaster;

@Repository
public interface ThemeMasterRepository extends CrudRepository<ThemeMaster, Long> {
    ThemeMaster findThemeMasterByThemeId(Long themeId);

    List<ThemeMaster> findThemeMasterByOrgid(Long orgid);

}
