package com.abm.mainet.cms.service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.apache.log4j.Logger;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.abm.mainet.cms.dao.ThemeMasterRepository;
import com.abm.mainet.cms.domain.ThemeMaster;
import com.abm.mainet.cms.dto.ThemeMasterDTO;
import com.abm.mainet.common.constant.MainetConstants;
import com.abm.mainet.common.util.UserSession;
import com.abm.mainet.common.util.Utility;

@Service
public class ThemeMasterService implements IThemeMasterService {

    private Logger LOG = Logger.getLogger(ThemeMasterService.class);

    @Autowired
    private ThemeMasterRepository themeMasterRepository;

    @Override
    public boolean saveThemeMaster(List<ThemeMasterDTO> master, long orgid, long empId) {
        ThemeMaster themeMaster = null;

        List<ThemeMaster> themeMasterList = new ArrayList<>(master.size());
        Date currentDate = new Date();
       // String macAddress = Utility.getMacAddress();
        String macAddress = UserSession.getCurrent().getEmployee().getEmppiservername();
        

        for (ThemeMasterDTO themeMasterDTO : master) {
            themeMaster = new ThemeMaster();
            BeanUtils.copyProperties(themeMasterDTO, themeMaster);

            if (null == themeMaster.getThemeId()) {
                themeMaster.setOrgid(orgid);
                themeMaster.setCreatedDate(currentDate);
                themeMaster.setLgipmac(macAddress);
                themeMaster.setCreatedBy(empId);
            } else {
                themeMaster.setUpdatedBy(empId);
                themeMaster.setLgipmacupd(macAddress);
                themeMaster.setUpdatedDate(currentDate);

            }

            themeMasterList.add(themeMaster);
        }

        try {
            themeMasterRepository.save(themeMasterList);
        } catch (Exception e) {
            LOG.error(MainetConstants.ERROR_OCCURED, e);
            return false;
        }

        return true;
    }

    @Override
    public ThemeMaster getThemeMaster(ThemeMasterDTO master) {
        return null;
    }

    @Override
    public ThemeMaster updateThemeMaster(ThemeMasterDTO master) {
        ThemeMaster themeMaster = new ThemeMaster();
        BeanUtils.copyProperties(master, themeMaster);
        return themeMasterRepository.save(themeMaster);
    }

    @Override
    public ThemeMaster deleteThemeMaster(ThemeMasterDTO master) {
        ThemeMaster themeMaster = new ThemeMaster();
        BeanUtils.copyProperties(master, themeMaster);
        return themeMasterRepository.save(themeMaster);
    }

    @Override
    public List<ThemeMasterDTO> getThemeMasterByOrgid(Long orgid) {

        List<ThemeMaster> masterList = themeMasterRepository.findThemeMasterByOrgid(orgid);
        ThemeMasterDTO themeMasterDTO = null;
        List<ThemeMasterDTO> themeMasterList = new ArrayList<>(masterList.size());
        for (ThemeMaster themeMaster : masterList) {
            themeMasterDTO = new ThemeMasterDTO();
            BeanUtils.copyProperties(themeMaster, themeMasterDTO);
            themeMasterList.add(themeMasterDTO);
        }
        return themeMasterList;
    }

    @Override
    public Map<String, String> getThemeMasterMapByOrgid(Long orgid) {

        return getThemeMasterByOrgid(orgid).stream()
                .collect(Collectors.toMap(ThemeMasterDTO::getSection, ThemeMasterDTO::getStatus));
    }

}
