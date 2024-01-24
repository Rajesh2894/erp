package com.abm.mainet.workManagement.service;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import javax.annotation.Resource;
import javax.jws.WebMethod;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.abm.mainet.common.audit.service.AuditService;
import com.abm.mainet.common.constant.MainetConstants;
import com.abm.mainet.common.constant.ServiceEndpoints;
import com.abm.mainet.common.domain.Organisation;
import com.abm.mainet.common.exception.FrameworkException;
import com.abm.mainet.common.integration.acccount.dto.VendorBillApprovalDTO;
import com.abm.mainet.common.integration.dms.dao.IAttachDocsDao;
import com.abm.mainet.common.master.service.TbOrganisationService;
import com.abm.mainet.common.utility.ApplicationContextProvider;
import com.abm.mainet.common.utility.CommonMasterUtility;
import com.abm.mainet.common.utility.LookUp;
import com.abm.mainet.common.utility.RestClient;
import com.abm.mainet.common.utility.SeqGenFunctionUtility;
import com.abm.mainet.common.utility.UserSession;
import com.abm.mainet.common.utility.Utility;
import com.abm.mainet.workManagement.dao.WmsProjectMasterDao;
import com.abm.mainet.workManagement.domain.ProjectBudgetDetEntity;
import com.abm.mainet.workManagement.domain.SchemeMaster;
import com.abm.mainet.workManagement.domain.TbWmsProjectMaster;
import com.abm.mainet.workManagement.domain.TbWmsProjectMasterHistory;
import com.abm.mainet.workManagement.domain.WorkDefinationEntity;
import com.abm.mainet.workManagement.domain.WorkDefinationYearDetEntity;
import com.abm.mainet.workManagement.dto.WmsProjectMasterDto;
import com.abm.mainet.workManagement.dto.WorkDefinationYearDetDto;
import com.abm.mainet.workManagement.dto.WorkDefinitionDto;
import com.abm.mainet.workManagement.repository.ProjectBudgetYearDetRepo;
import com.abm.mainet.workManagement.repository.WmsProjectMasterRepository;
import com.abm.mainet.workManagement.repository.WorkDefinationYearDetRepo;

@Service
public class WmsProjectMasterServiceImpl implements WmsProjectMasterService {

    @Autowired
    private WmsProjectMasterDao iWmsProjectMasterDao;

    @Autowired
    private WmsProjectMasterRepository projectMasterRepository;

    @Autowired
    private AuditService auditService;
    
    @Resource
	@Autowired
	private ProjectBudgetYearDetRepo workDefYearRepo;

    private static Logger logger = Logger.getLogger(WmsProjectMasterServiceImpl.class);

    @Override
    @Transactional
    public WmsProjectMasterDto saveUpdateProjectMaster(WmsProjectMasterDto wmsProjectMasterDto,
            List<Long> removeFileById) {
        TbWmsProjectMaster projectMasterEntity = new TbWmsProjectMaster();
        BeanUtils.copyProperties(wmsProjectMasterDto, projectMasterEntity);
        
        setFinanacialYearDetailsForUpdate(wmsProjectMasterDto, projectMasterEntity);
        SchemeMaster schemeMaster = new SchemeMaster();

        if (wmsProjectMasterDto.getSchId() != null && wmsProjectMasterDto.getSchId() != 0) {
            schemeMaster.setWmSchId(wmsProjectMasterDto.getSchId());
            projectMasterEntity.setSchId(schemeMaster);
        }
        TbWmsProjectMaster projectMasterEntityWithId = projectMasterRepository.save(projectMasterEntity);
        if (projectMasterEntity.getProjCode() == null || projectMasterEntity.getProjCode().isEmpty()) {

            final Long projSequence = ApplicationContextProvider.getApplicationContext().getBean(SeqGenFunctionUtility.class)
                    .generateSequenceNo(
                            MainetConstants.WorksManagement.WORKS_MANAGEMENT,
                            MainetConstants.WorksManagement.TB_WMS_PROJECT_MAST, MainetConstants.WorksManagement.PROJECT_CODE,
                            projectMasterEntity.getOrgId(), MainetConstants.FlagC, projectMasterEntity.getDpDeptId());

            projectMasterEntityWithId
                    .setProjCode(wmsProjectMasterDto.getDepartmentCode() + MainetConstants.WINDOWS_SLASH
                            + String.format(MainetConstants.WorksManagement.THREE_PERCENTILE, projSequence));
            projectMasterRepository.save(projectMasterEntityWithId);
        }
        if (removeFileById != null && !removeFileById.isEmpty()) {
            ApplicationContextProvider.getApplicationContext().getBean(IAttachDocsDao.class).updateRecord(removeFileById,
                    wmsProjectMasterDto.getUpdatedBy(), MainetConstants.FlagD);
        }
        try {
            TbWmsProjectMasterHistory projMasHistory = new TbWmsProjectMasterHistory();
            if (wmsProjectMasterDto.getUpdatedBy() == null)// to check add or edit mode
                projMasHistory.sethStatus(MainetConstants.InsertMode.ADD.getStatus());
            else {
                projMasHistory.sethStatus(MainetConstants.InsertMode.UPDATE.getStatus());
            }
            auditService.createHistory(projectMasterEntityWithId, projMasHistory);
        } catch (Exception exception) {
            logger.error("Exception occured when creating history : ", exception);
        }

        BeanUtils.copyProperties(projectMasterEntityWithId, wmsProjectMasterDto);
        return wmsProjectMasterDto;
    }

    @Override
    @Transactional(readOnly = true)
    public WmsProjectMasterDto getProjectMasterByProjId(Long projId) {
        WmsProjectMasterDto projectMasterDto = new WmsProjectMasterDto();
        TbWmsProjectMaster projectMasterEntity = projectMasterRepository.findOne(projId);
        BeanUtils.copyProperties(projectMasterEntity, projectMasterDto);
        if (projectMasterEntity.getSchId() != null) {
            projectMasterDto.setSchemeName(projectMasterEntity.getSchId().getWmSchCode());
            projectMasterDto.setSchId(projectMasterEntity.getSchId().getWmSchId());
            projectMasterDto.setSchmSourceCode(projectMasterEntity.getSchId().getWmSchCodeId1());
        }
        return projectMasterDto;
    }

    @Override
    @Transactional(readOnly = true)
    public boolean checkProjectWorkAssociation(Long projId, Long orgId) {
        boolean status = false;

        Long newOrgID = populateService(orgId);
        List<WorkDefinitionDto> definitionDtoList = ApplicationContextProvider.getApplicationContext()
                .getBean(WorkDefinitionService.class)
                .findAllWorkDefinationByProjId(newOrgID, projId);
        if (definitionDtoList != null && !definitionDtoList.isEmpty()) {
            status = true;
        }
        return status;
    }

    @Override
    @Transactional
    public void deleteProjectByProjId(Long projId) {
        TbWmsProjectMaster master = new TbWmsProjectMaster();
        master.setProjId(projId);
        projectMasterRepository.deleteProjectByProjId(projId);
        try {
            TbWmsProjectMasterHistory projMasHistory = new TbWmsProjectMasterHistory();
            TbWmsProjectMaster projMas = projectMasterRepository.findOne(projId);
            projMasHistory.sethStatus(MainetConstants.InsertMode.DELETE.getStatus());
            auditService.createHistory(projMas, projMasHistory);
        } catch (Exception exception) {
            logger.error("Exception occured when creating history : ", exception);
        }

    }

    @Override
    @Transactional(readOnly = true)
    public List<WmsProjectMasterDto> getProjectMasterList(Long sourceCode, Long sourceName, String projectName,
            String projCode, long orgId, Long dpDeptId, Long projStatus) {
        List<WmsProjectMasterDto> masterDtoList = new ArrayList<WmsProjectMasterDto>();
        Long newOrgID=null;
		if ((Utility.isEnvPrefixAvailable(UserSession.getCurrent().getOrganisation(), MainetConstants.ENV_TSCL)) && (Utility.isEnvPrefixAvailable(UserSession.getCurrent().getOrganisation(), MainetConstants.ENV_PSCL))) {
			newOrgID = orgId;
		} else {
			newOrgID = populateService(orgId);
		}
        List<TbWmsProjectMaster> projectMasterEntityList = iWmsProjectMasterDao.getProjectMasterList(sourceCode,
                sourceName, projectName, projCode, newOrgID, dpDeptId, projStatus);
        for (TbWmsProjectMaster tbWmsProjectMaster : projectMasterEntityList) {
            WmsProjectMasterDto masterDtoListObject = new WmsProjectMasterDto();
            BeanUtils.copyProperties(tbWmsProjectMaster, masterDtoListObject);
            if (tbWmsProjectMaster.getSchId() != null) {
            	if(UserSession.getCurrent().getLanguageId() == 1) {
                masterDtoListObject.setSchemeName(tbWmsProjectMaster.getSchId().getWmSchNameEng());
            	}
            	else
            	{
            		masterDtoListObject.setSchemeName(tbWmsProjectMaster.getSchId().getWmSchNameReg());
            	}
                masterDtoListObject.setSchId(tbWmsProjectMaster.getSchId().getWmSchId());
            }

            if (tbWmsProjectMaster.getProjStartDate() != null) {
                masterDtoListObject.setStartDateDesc(new SimpleDateFormat(MainetConstants.DATE_FORMAT)
                        .format(tbWmsProjectMaster.getProjStartDate()));
            }
            if (tbWmsProjectMaster.getProjEndDate() != null) {
                masterDtoListObject.setEndDateDesc(
                        new SimpleDateFormat(MainetConstants.DATE_FORMAT).format(tbWmsProjectMaster.getProjEndDate()));
            }

            masterDtoList.add(masterDtoListObject);
        }

        return masterDtoList;
    }

    @Override
    @Transactional(readOnly = true)
    public List<WmsProjectMasterDto> getActiveProjectMasterListByOrgId(long orgid) {
        List<WmsProjectMasterDto> dtoList = new ArrayList<WmsProjectMasterDto>();
		Long NewOrgId=null;
		if ((Utility.isEnvPrefixAvailable(UserSession.getCurrent().getOrganisation(), MainetConstants.ENV_TSCL )) && (Utility.isEnvPrefixAvailable(UserSession.getCurrent().getOrganisation(), MainetConstants.ENV_PSCL))) {
			NewOrgId = orgid;
		} else {
			NewOrgId = populateService(orgid);
		}
        WmsProjectMasterDto dto;
        List<TbWmsProjectMaster> masEntity = projectMasterRepository.getActiveProjectMasterListByOrgId(NewOrgId);
        for (TbWmsProjectMaster tbWmsProjectMaster : masEntity) {
            dto = new WmsProjectMasterDto();
            BeanUtils.copyProperties(tbWmsProjectMaster, dto);
            if (tbWmsProjectMaster.getSchId() != null) {
                dto.setSchemeName(tbWmsProjectMaster.getSchId().getWmSchNameEng());
                dto.setSchId(tbWmsProjectMaster.getSchId().getWmSchId());
            }
            dtoList.add(dto);
        }
        return dtoList;
    }

    @Override
    @Transactional(readOnly = true)
    public List<WmsProjectMasterDto> getAllProjectMaster(Long orgId, List<Long> projId) {
        List<WmsProjectMasterDto> dtoList = new ArrayList<WmsProjectMasterDto>();
         Long newOrgId = populateService(orgId);
        WmsProjectMasterDto dto;
        List<TbWmsProjectMaster> masEntity = projectMasterRepository.getAllProjectMaster(newOrgId, projId);
        for (TbWmsProjectMaster tbWmsProjectMaster : masEntity) {
            dto = new WmsProjectMasterDto();
            BeanUtils.copyProperties(tbWmsProjectMaster, dto);
            dtoList.add(dto);
        }
        return dtoList;
    }

    @Override
    @Transactional(readOnly = true)
    public List<Long> getAllProjectAssociatedMileStone(Long orgid) {
        Long newOrgId = populateService(orgid);
        return projectMasterRepository.getAllProjectAssociatedMileStone(newOrgId);
    }

    @Override
    @Transactional(readOnly = true)
    public List<Object[]> getAllProjectAssociatedByMileStone(Long orgid, String mileStoneFlag) {
        Long newOrgId = populateService(orgid);
        return iWmsProjectMasterDao.getAllProjectAssociationByMileStone(newOrgId, mileStoneFlag);
    }

    @Override
    @Transactional(readOnly = true)
    public List<WmsProjectMasterDto> getAllActiveProjectsByDeptIdAndOrgId(Long deptId, Long orgid) {
        List<WmsProjectMasterDto> dtoList = new ArrayList<>();
        Long newOrgId = populateService(orgid);
        List<TbWmsProjectMaster> masEntity = projectMasterRepository.getAllActiveProjectsByDeptIdAndOrgId(deptId,
                newOrgId);
        if (masEntity != null && !masEntity.isEmpty()) {
            masEntity.forEach(entity -> {
                WmsProjectMasterDto dto = new WmsProjectMasterDto();
                BeanUtils.copyProperties(entity, dto);
                dtoList.add(dto);
            });
        }
        return dtoList;
    }

    @Override
    @Transactional(readOnly = true)
    public List<WmsProjectMasterDto> getActiveProjectMasterListWithoutSchemeByOrgId(long orgid) {
        List<WmsProjectMasterDto> dtoList = new ArrayList<WmsProjectMasterDto>();
        Long newOrgID = populateService(orgid);
        WmsProjectMasterDto dto;
        List<TbWmsProjectMaster> masEntity = projectMasterRepository
                .getActiveProjectMasterListWithoutSchemeByOrgId(newOrgID);
        for (TbWmsProjectMaster tbWmsProjectMaster : masEntity) {
            dto = new WmsProjectMasterDto();
            BeanUtils.copyProperties(tbWmsProjectMaster, dto);
            if (tbWmsProjectMaster.getSchId() != null) {
                dto.setSchemeName(tbWmsProjectMaster.getSchId().getWmSchNameEng());
                dto.setSchId(tbWmsProjectMaster.getSchId().getWmSchId());
            }
            dtoList.add(dto);
        }
        return dtoList;
    }

    @Override
    @Transactional(readOnly = true)
    public List<WmsProjectMasterDto> getActiveProjectMasterListBySchId(long schId) {
        List<WmsProjectMasterDto> dtoList = new ArrayList<WmsProjectMasterDto>();

        WmsProjectMasterDto dto;
        List<TbWmsProjectMaster> masEntity = projectMasterRepository.getActiveProjectMasterListBySchId(schId);
        for (TbWmsProjectMaster tbWmsProjectMaster : masEntity) {
            dto = new WmsProjectMasterDto();
            BeanUtils.copyProperties(tbWmsProjectMaster, dto);
            if (tbWmsProjectMaster.getSchId() != null) {
                dto.setSchemeName(tbWmsProjectMaster.getSchId().getWmSchNameEng());
                dto.setSchId(tbWmsProjectMaster.getSchId().getWmSchId());
            }
            dtoList.add(dto);
        }
        return dtoList;
    }

    @Override
    @Transactional(readOnly = true)
    public List<Long> getAllProjectAssociatedDeptList(Long orgid) {
        Long newOrgId = populateService(orgid);
        return projectMasterRepository.getAllProjectAssociatedDeptList(newOrgId);
    }

    /**
     * Check Whether Default organization or Not
     * @param orgId
     * @return orgId
     */
    private Long populateService(Long orgId) {
    	if ((!Utility.isEnvPrefixAvailable(UserSession.getCurrent().getOrganisation(), MainetConstants.ENV_TSCL )) && (!Utility.isEnvPrefixAvailable(UserSession.getCurrent().getOrganisation(), MainetConstants.ENV_PSCL))) {
        Organisation organisation = new Organisation();
        organisation.setOrgid(orgId);

        Organisation org = ApplicationContextProvider.getApplicationContext().getBean(TbOrganisationService.class)
                .findDefaultOrganisation();
        List<LookUp> lookUps = CommonMasterUtility.getLookUps(MainetConstants.WorksManagement.CSR, organisation).stream()
                .filter(c -> c.getDefaultVal().equals(MainetConstants.FlagY)).collect(Collectors.toList());
        if (lookUps.get(0).getLookUpCode().equals(MainetConstants.YES)) {
            orgId = org.getOrgid();
        }
      }
        return orgId;
    }
    
    private void createNewFinancialYearsDetails(WmsProjectMasterDto workDefDto, TbWmsProjectMaster workDefEntity) {
		if (workDefDto.getYearDtos() != null && !workDefDto.getYearDtos().isEmpty()) {
			List<ProjectBudgetDetEntity> defYearEntityList = new ArrayList<>();
			workDefDto.getYearDtos().forEach(defYear -> {
				if (defYear.getFaYearId() != null
						|| (defYear.getFinanceCodeDesc() != null
								&& !defYear.getFinanceCodeDesc().equals(StringUtils.EMPTY))
						|| defYear.getYearPercntWork() != null
						|| defYear.getYeBugAmount() != null || defYear.getSacHeadId() != null) {
					ProjectBudgetDetEntity defYearEntity = new ProjectBudgetDetEntity();
					BeanUtils.copyProperties(defYear, defYearEntity);
					setCreateYearCommonDetails(workDefDto, defYearEntity);
					defYearEntity.setProjectMasEntity(workDefEntity);
					defYearEntityList.add(defYearEntity);
				}
			});

			workDefEntity.setProjectBugDetEntity(defYearEntityList);
		}
	}
    
    private void setFinanacialYearDetailsForUpdate(WmsProjectMasterDto workDefDto, TbWmsProjectMaster workDefEntity) {
		if (workDefDto.getYearDtos() != null) {
			List<ProjectBudgetDetEntity> defYearEntityList = new ArrayList<>();
			workDefDto.getYearDtos().forEach(defYear -> {
				//if (defYear.getFinActiveFlag() != null) {
					if (defYear.getFaYearId() != null
							|| (defYear.getFinanceCodeDesc() != null
									&& !defYear.getFinanceCodeDesc().equals(StringUtils.EMPTY))
							|| defYear.getYearPercntWork() != null
							|| defYear.getYeBugAmount() != null || defYear.getSacHeadId() != null) {
						ProjectBudgetDetEntity defYearEntity = new ProjectBudgetDetEntity();
						BeanUtils.copyProperties(defYear, defYearEntity);
						if (defYear.getPbId() != null) {
							defYearEntity.setUpdatedBy(workDefDto.getCreatedBy());
							defYearEntity.setUpdatedDate(new Date());
							defYearEntity.setLgIpMacUpd(workDefDto.getLgIpMac());
						} else {
							setCreateYearCommonDetails(workDefDto, defYearEntity);
						}
						defYearEntity.setProjectMasEntity(workDefEntity);
						defYearEntityList.add(defYearEntity);
					}
				//}
			});
			workDefEntity.setProjectBugDetEntity(defYearEntityList);
		}
	}
    
    private void setCreateYearCommonDetails(WmsProjectMasterDto workDefDto, ProjectBudgetDetEntity defYearEntity) {
		defYearEntity.setCreatedBy(workDefDto.getCreatedBy());
		defYearEntity.setCreatedDate(new Date());
		defYearEntity.setLgIpMac(workDefDto.getLgIpMac());
		defYearEntity.setYeActive(MainetConstants.Common_Constant.YES);
		defYearEntity.setOrgId(workDefDto.getOrgId());
	}
    
    @Override
	public List<WorkDefinationYearDetDto> getYearDetailByProjectId(WmsProjectMasterDto workDefinitionDto, Long orgId) {
    	//TbWmsProjectMaster definationEntity = new TbWmsProjectMaster();
		List<WorkDefinationYearDetDto> definationYearDetDto = new ArrayList<WorkDefinationYearDetDto>();
		//BeanUtils.copyProperties(workDefinitionDto, definationEntity);

		List<ProjectBudgetDetEntity> definationYearDetEntity = workDefYearRepo
				.getYearDetByProjectId(workDefinitionDto.getProjId());

		definationYearDetEntity.forEach(entity -> {

			WorkDefinationYearDetDto yearDto = new WorkDefinationYearDetDto();
			BeanUtils.copyProperties(entity, yearDto);
			//yearDto.setPbId(entity.get);
			definationYearDetDto.add(yearDto);

		});

		return definationYearDetDto;
	}
    
    
  // inactive removed work definition year details by set its flag as 'N'
    @Override
    @Transactional
    public void inactiveRemovedYearDetails(WmsProjectMasterDto workDefDto, List<Long> removeYearIdList) {
 		if (removeYearIdList != null && !removeYearIdList.isEmpty()) {
 			workDefYearRepo.iactiveYearsByIds(removeYearIdList, workDefDto.getUpdatedBy());
 		}
 	}

    @SuppressWarnings("unchecked")
	@Override
    @WebMethod(exclude = true)
    @Transactional
    public  Map<Long, String>getBudgetExpenditure(VendorBillApprovalDTO billApprovalDTO) {
    	Map<Long, String> dto = new HashMap<>();
        ResponseEntity<?> response = null;
        Object responseObj = null;

        try {
            response = RestClient.callRestTemplateClient(billApprovalDTO, ServiceEndpoints.GET_BUDGET);
            if (response != null ) {
            	responseObj = response.getBody();
                dto = (Map<Long, String>)responseObj;
    		}
        } catch (Exception ex) {
           // LOGGER.error("Exception occured while fetching Budget Expenditure Details : " + ex);
            throw new FrameworkException(ex);
        }
        return dto;
    }
	
}
