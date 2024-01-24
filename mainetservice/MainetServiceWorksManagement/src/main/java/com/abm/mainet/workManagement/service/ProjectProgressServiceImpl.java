/**
 * 
 */
package com.abm.mainet.workManagement.service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.abm.mainet.common.constant.MainetConstants;
import com.abm.mainet.common.utility.CommonUtility;
import com.abm.mainet.common.utility.UserSession;
import com.abm.mainet.common.utility.Utility;
import com.abm.mainet.workManagement.dao.ProjectProgressDao;
import com.abm.mainet.workManagement.dto.MileStoneDTO;
import com.abm.mainet.workManagement.dto.ProjectProgressDto;

/**
 * @author Saiprasad.Vengurlekar
 *
 */
@Service
public class ProjectProgressServiceImpl implements ProjectProgressService {
    @Autowired
    ProjectProgressDao projectProgressDao;

    @Override
    @Transactional(readOnly = true)
    public List<ProjectProgressDto> getAllProjectProgressByDate(Date fromDate, Date toDate, Long orgId) {
        List<Object[]> projectProgressWithWorkId = projectProgressDao.getProjectProgressWithWorkId(orgId, fromDate,
                toDate);
        List<Object[]> projectProgress = projectProgressDao.getProjectProgress(orgId, fromDate, toDate);
        List<ProjectProgressDto> projectProgressWithWorkIdDto = new ArrayList<>();
        List<ProjectProgressDto> projectProgressDtoList = new ArrayList<>();
        List<ProjectProgressDto> allprojectProgressDtoList = new ArrayList<>();

        if (projectProgressWithWorkId != null && !projectProgressWithWorkId.isEmpty()) {
            ProjectProgressDto projectProgressDto = null;
            MileStoneDTO mileStoneDTO = null;
            int count = 0;
            for (final Object venObj[] : projectProgressWithWorkId) {
                projectProgressDto = new ProjectProgressDto();
                mileStoneDTO = new MileStoneDTO();

                Long prevProjId = Long.valueOf(venObj[0].toString());
                Long prevWorkId = Long.valueOf(venObj[4].toString());
                projectProgressDto.setProjectId(Long.valueOf(venObj[0].toString()));
                projectProgressDto.setProjNameEng(venObj[1].toString());
                projectProgressDto.setProjStartDate(CommonUtility.dateToString((Date) venObj[2]));
                if (venObj[3] != null) {
                    projectProgressDto.setProjEndDate(CommonUtility.dateToString((Date) venObj[3]));
                }
                projectProgressDto.setWorkId(Long.valueOf(venObj[4].toString()));
                projectProgressDto.setWorkcode(venObj[5].toString());
                projectProgressDto.setWorkName(venObj[6].toString());
                /* Remove As per SUDA UAT */
                /*
                 * projectProgressDto.setWorkStartDate(CommonUtility.dateToString((Date) venObj[7]));
                 * projectProgressDto.setWorkEndDate(CommonUtility.dateToString((Date) venObj[8]));
                 */
                mileStoneDTO.setMileStoneDesc(venObj[9].toString());
                mileStoneDTO.setMileStoneWeight(new BigDecimal(venObj[10].toString()));
                if (venObj[11] != null) {
                    mileStoneDTO.setMsPercent(new BigDecimal(venObj[11].toString()));
                }
              if (Utility.isEnvPrefixAvailable(UserSession.getCurrent().getOrganisation(), MainetConstants.ENV_PSCL)) {
                projectProgressDto.setWmSchNameEng(venObj[12].toString());
                if(venObj[13] != null)
                	projectProgressDto.setSchFundName(venObj[13].toString());
                projectProgressDto.setVmVendorname(venObj[14].toString());
                }
                
                if (count != 0) {
                    for (ProjectProgressDto dto : projectProgressWithWorkIdDto) {
                        if (dto.getProjectId() == prevProjId && prevWorkId == dto.getWorkId()) {
                            dto.getMileStoneDTO().add(mileStoneDTO);
                            break;
                        } else {
                            projectProgressDto.getMileStoneDTO().add(mileStoneDTO);
                            projectProgressWithWorkIdDto.add(projectProgressDto);
                            break;
                        }
                    }

                } else {
                    projectProgressDto.getMileStoneDTO().add(mileStoneDTO);
                    projectProgressWithWorkIdDto.add(projectProgressDto);
                }
                count++;

            }
            allprojectProgressDtoList.addAll(projectProgressWithWorkIdDto);
        }

        if (projectProgress != null && !projectProgress.isEmpty()) {

            ProjectProgressDto projectProgressDto = null;
            MileStoneDTO mileStoneDTO = null;
            int count = 0;
            for (final Object venObj[] : projectProgress) {
                projectProgressDto = new ProjectProgressDto();
                mileStoneDTO = new MileStoneDTO();
                Long prevProjId = Long.valueOf(venObj[0].toString());
                projectProgressDto.setProjectId(Long.valueOf(venObj[0].toString()));
                projectProgressDto.setProjNameEng(venObj[1].toString());
                projectProgressDto.setProjStartDate(CommonUtility.dateToString((Date) venObj[2]));
                if (venObj[3] != null) {
                    projectProgressDto.setProjEndDate(CommonUtility.dateToString((Date) venObj[3]));
                }
                mileStoneDTO.setMileStoneDesc(venObj[4].toString());
                mileStoneDTO.setMileStoneWeight(new BigDecimal(venObj[5].toString()));
                if (venObj[6] != null) {
                    mileStoneDTO.setMsPercent(new BigDecimal(venObj[6].toString()));
                }
                if (count != 0) {
                    for (ProjectProgressDto dto : projectProgressDtoList) {
                        if (dto.getProjectId() == prevProjId) {
                            dto.getMileStoneDTO().add(mileStoneDTO);
                            break;
                        } else {
                            projectProgressDto.getMileStoneDTO().add(mileStoneDTO);
                            projectProgressDtoList.add(projectProgressDto);
                            break;
                        }
                    }

                } else {
                    projectProgressDto.getMileStoneDTO().add(mileStoneDTO);
                    projectProgressDtoList.add(projectProgressDto);
                }
                count++;

            }
            allprojectProgressDtoList.addAll(projectProgressDtoList);
        }

        return allprojectProgressDtoList;
    }

}
