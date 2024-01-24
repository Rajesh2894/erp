
package com.abm.mainet.common.workflow.service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import java.util.concurrent.TimeUnit;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.abm.mainet.common.constant.MainetConstants;
import com.abm.mainet.common.domain.ComplaintType;
import com.abm.mainet.common.domain.Department;
import com.abm.mainet.common.domain.Organisation;
import com.abm.mainet.common.domain.ServiceMaster;
import com.abm.mainet.common.dto.ComplaintTypeBean;
import com.abm.mainet.common.entitlement.service.IEntitlementService;
import com.abm.mainet.common.master.dto.TbDepartment;
import com.abm.mainet.common.master.service.TbDepartmentService;
import com.abm.mainet.common.service.IEmployeeService;
import com.abm.mainet.common.utility.CommonMasterUtility;
import com.abm.mainet.common.utility.LookUp;
import com.abm.mainet.common.utility.UserSession;
import com.abm.mainet.common.utility.Utility;
import com.abm.mainet.common.workflow.dao.IWorkflowTypeDAO;
import com.abm.mainet.common.workflow.domain.ServicesEventEntity;
import com.abm.mainet.common.workflow.domain.WorkflowDet;
import com.abm.mainet.common.workflow.domain.WorkflowMas;
import com.abm.mainet.common.workflow.dto.TaskAssignment;
import com.abm.mainet.common.workflow.dto.TaskAssignmentRequest;
import com.abm.mainet.common.workflow.dto.UserTaskDTO;
import com.abm.mainet.common.workflow.dto.WorkFlowTypeGrid;
import com.abm.mainet.common.workflow.dto.WorkflowDetDTO;
import com.abm.mainet.common.workflow.dto.WorkflowMasDTO;
import com.abm.mainet.common.workflow.repository.IServicesEventEntityRepository;
import com.abm.mainet.common.workflow.repository.WorkFlowTypeRepository;
import com.abm.mainet.common.workflow.repository.WorkflowMappingRepository;

/**
 * WorkFlowTypeService is service provided to manage workflow master data, like defining workflow managing task in it.
 * 
 * @author ritesh.patil
 *
 */
@Service
@Repository
@Transactional(readOnly = true)
public class WorkFlowTypeSeviceImpl implements IWorkFlowTypeService {
    @Autowired
    private IServicesEventEntityRepository iServicesEventEntityRepository;

    @Autowired
    private WorkFlowTypeRepository workFlowTypeRepository;

    @Autowired
    private IWorkflowTypeDAO workflowTypeDAO;

    @Autowired
    private TbDepartmentService tbDepartmentService;

    @Autowired
    private IEmployeeService employeeService;

    @Autowired
    private IEntitlementService iEntitlementService;

    @Autowired
    private ITaskAssignmentService taskAssignmentService;

    @Autowired
    private IWorkflowTaskService iWorkflowTaskService;

    @Autowired
    WorkflowMappingRepository workflowMappingRepository;

    Logger logger = Logger.getLogger(WorkFlowTypeSeviceImpl.class);

    @Override
    public List<Object[]> findAllEventsByDeptAndServiceId(final Long orgId, final Long deptId, final Long serviceId) {
        logger.info("findAllEventsByDept(Long orgId, Long deptId) execution starts ");
        return iServicesEventEntityRepository.findAllEventsByDeptAndServiceId(orgId, deptId, serviceId);
    }

    @SuppressWarnings("deprecation")
    @Override
    @Transactional
    public boolean saveForm(WorkflowMasDTO dto) {
        logger.info("saveForm(WorkFlowTypeDTO dto) execution starts ");
        WorkflowMas workflowEntity = new WorkflowMas();
        BeanUtils.copyProperties(dto, workflowEntity, MainetConstants.WORKFLOWTYPE.ExludeCopyArrayFlow);

        Organisation org = new Organisation();
        org.setOrgid(dto.getCurrentOrgId());
        workflowEntity.setOrganisation(org);

        Department department = new Department();
        department.setDpDeptid(dto.getDeptId());
        workflowEntity.setDepartment(department);

        ServiceMaster serviceMaster = new ServiceMaster();
        serviceMaster.setSmServiceId(dto.getServiceId());
        workflowEntity.setService(serviceMaster);

        workflowEntity.setStatus(MainetConstants.Common_Constant.YES);
        workflowEntity.setCreatedBy(dto.getCreatedBy());
        workflowEntity.setCreateDate(new Date());
        workflowEntity.setLgIpMac(Utility.getMacAddress());
        if (dto.getComplaintId() != null && dto.getComplaintId() > 0) {
            ComplaintType complaintType = new ComplaintType();
            complaintType.setCompId(dto.getComplaintId());
            workflowEntity.setComplaint(complaintType);
        }
        List<WorkflowDetDTO> workflowDetDTOs = dto.getWorkflowDet();
        List<WorkflowDet> workflowDets = new ArrayList<WorkflowDet>();
        WorkflowDet workflowDetEntity;
        ServicesEventEntity servicesEventEntity;
        Organisation mappingOrg;
        Organisation workflowOrg;
        Department mappingdDept;
        for (WorkflowDetDTO flowdto : workflowDetDTOs) {
            workflowDetEntity = new WorkflowDet();

            BeanUtils.copyProperties(flowdto, workflowDetEntity,
                    MainetConstants.WORKFLOWTYPE.ExludeCopyArrayFlowMapping);
            servicesEventEntity = new ServicesEventEntity();
            servicesEventEntity.setServiceEventId(flowdto.getEventId());
            workflowDetEntity.setServicesEventEntity(servicesEventEntity);

            mappingOrg = new Organisation();
            mappingOrg.setOrgid(flowdto.getMapOrgId());
            workflowDetEntity.setEventOrganisation(mappingOrg);

            workflowOrg = new Organisation();
            workflowOrg.setOrgid(dto.getCurrentOrgId());
            workflowDetEntity.setCurrentOrganisation(workflowOrg);

            mappingdDept = new Department();
            mappingdDept.setDpDeptid(flowdto.getMapDeptId());
            workflowDetEntity.setEventDepartment(mappingdDept);
            workflowDetEntity.setWfId(workflowEntity);
            workflowDetEntity.setCreateDate(new Date());
            workflowDetEntity.setCreatedBy(workflowEntity.getCreatedBy());
            workflowDetEntity.setStatus(MainetConstants.Common_Constant.YES);
            workflowDetEntity.setLgIpMac(Utility.getMacAddress());
            convertSLAToMiliSec(workflowDetEntity, flowdto, workflowOrg);
            workflowDets.add(workflowDetEntity);
        }
        workflowEntity.setWorkflowDetList(workflowDets);
        workFlowTypeRepository.save(workflowEntity);
        logger.info("saveForm(WorkFlowTypeDTO dto) execution ends ");
        return true;
    }

    private void convertSLAToMiliSec(WorkflowDet workflowDetEntity, WorkflowDetDTO flowdto, Organisation org) {

        if (StringUtils.isEmpty(flowdto.getSla())
                || CommonMasterUtility.getNonHierarchicalLookUpObject(flowdto.getUnit(), org).getLookUpDesc() == null) {
            workflowDetEntity.setSlaCal(0l);
            // workflowDetEntity.setSla(null);
            // D#35991
            workflowDetEntity.setSla(StringUtils.isEmpty(workflowDetEntity.getSla()) ? null : workflowDetEntity.getSla());
        } else if (!flowdto.getSla().equals(StringUtils.EMPTY) && flowdto.getUnit() != 0
                && CommonMasterUtility.getNonHierarchicalLookUpObject(flowdto.getUnit(), org).getLookUpCode()
                        .equalsIgnoreCase(MainetConstants.FlagD)) {
            String[] slaSplit = flowdto.getSla().split("\\.");
            Long miliseconds;
            if (slaSplit.length >= 2) {
                miliseconds = TimeUnit.DAYS.toMillis(Long.valueOf(slaSplit[0]))
                        + TimeUnit.HOURS.toMillis(Long.valueOf(slaSplit[1]));
            } else {
                miliseconds = TimeUnit.DAYS.toMillis(Long.valueOf(slaSplit[0]));
            }
            workflowDetEntity.setSlaCal(miliseconds);
        } else if (flowdto.getUnit() != 0 && CommonMasterUtility.getNonHierarchicalLookUpObject(flowdto.getUnit(), org)
                .getLookUpCode().equalsIgnoreCase(MainetConstants.FlagH)) {

            String[] slaSplit = flowdto.getSla().split("\\.");
            Long miliseconds;
            if (slaSplit.length >= 2) {
                miliseconds = TimeUnit.HOURS.toMillis(Long.valueOf(slaSplit[0]))
                        + TimeUnit.MINUTES.toMillis(Long.valueOf(slaSplit[1]));
            } else {
                miliseconds = TimeUnit.HOURS.toMillis(Long.valueOf(slaSplit[0]));
            }
            workflowDetEntity.setSlaCal(miliseconds);

            // workflowDetEntity.setSlaCal(TimeUnit.HOURS.toMillis(Long.valueOf(flowdto.getSla())));
        } else if (flowdto.getUnit() != 0 && CommonMasterUtility.getNonHierarchicalLookUpObject(flowdto.getUnit(), org)
                .getLookUpCode().equalsIgnoreCase(MainetConstants.FlagS)) {
            workflowDetEntity.setSlaCal(TimeUnit.SECONDS.toMillis(Long.valueOf(flowdto.getSla())));
        } else if (flowdto.getUnit() != 0 && CommonMasterUtility.getNonHierarchicalLookUpObject(flowdto.getUnit(), org)
                .getLookUpCode().equalsIgnoreCase(MainetConstants.QUARTZ_SCHEDULE.JobFrequency.MINUTES)) {
            workflowDetEntity.setSlaCal(TimeUnit.MINUTES.toMillis(Long.valueOf(flowdto.getSla())));
        }
    }

    @Override
    public WorkflowMasDTO findById(Long id) {
        logger.info("findById(Long id) execution starts id= " + id);
        WorkflowMas workflowType = workFlowTypeRepository.findOne(id);
        WorkflowMasDTO workFlowTypeDTO = new WorkflowMasDTO();

        if (workflowType != null) {
            workFlowTypeDTO.setCurrentOrgId(workflowType.getOrganisation().getOrgid());
            workFlowTypeDTO.setDeptId(workflowType.getDepartment().getDpDeptid());
            workFlowTypeDTO.setServiceId(workflowType.getService().getSmServiceId());
            String serviceCode = workflowType.getService().getSmShortdesc();
            String prefixName = null;
			if (workflowType.getComplaint() != null) {
				workFlowTypeDTO.setComplaintId(workflowType.getComplaint().getCompId());
				
				//US 113577 - check whether environment is kdmc or not
				if (Utility.isEnvPrefixAvailable(workflowType.getOrganisation(),MainetConstants.ENV_PRODUCT)) {
					/*if kdmc environment then get wardzone by comlain type & respective department*/
					prefixName = tbDepartmentService.findDepartmentPrefixName(
							workflowType.getComplaint().getDepartmentComplaint().getDepartment().getDpDeptid(),
							workFlowTypeDTO.getCurrentOrgId());
		        }else {
		        	prefixName = tbDepartmentService.findDepartmentPrefixName(workFlowTypeDTO.getDeptId(),
							workFlowTypeDTO.getCurrentOrgId());
		        }
			}
			else if(Utility.isEnvPrefixAvailable(UserSession.getCurrent().getOrganisation(),MainetConstants.ENV_PSCL) && (MainetConstants.RoadCuttingConstant.RCP).equals(serviceCode)){
				prefixName = MainetConstants.COMMON_PREFIX.RCZ;
				}
			else if(Utility.isEnvPrefixAvailable(UserSession.getCurrent().getOrganisation(),MainetConstants.ENV_PSCL) && (MainetConstants.RoadCuttingConstant.RCW).equals(serviceCode)){
				prefixName = MainetConstants.COMMON_PREFIX.RWS;
			}
			else if (workFlowTypeDTO.getDeptId() != null && workFlowTypeDTO.getDeptId() > 0
					&& Utility.isEnvPrefixAvailable(workflowType.getOrganisation(), MainetConstants.ENV_ASCL) 
					&& StringUtils.equals(MainetConstants.DEPT_SHORT_NAME.PROPERTY, tbDepartmentService.findDepartmentShortCodeByDeptId(workFlowTypeDTO.getDeptId(),
							workFlowTypeDTO.getCurrentOrgId()))
					&& Utility.isEnvPrefixAvailable(workflowType.getOrganisation(), "PWZ")) {
				prefixName = "PWZ";
			}
			else {
                prefixName = tbDepartmentService.findDepartmentPrefixName(workFlowTypeDTO.getDeptId(),
                        workFlowTypeDTO.getCurrentOrgId());
            }
            workFlowTypeDTO.setPrefixName(prefixName);
            WorkflowDetDTO workflowDetDTO;
            BeanUtils.copyProperties(workflowType, workFlowTypeDTO);
            for (WorkflowDet workflowDet : workflowType.getWorkflowDetList()) {
                workflowDetDTO = new WorkflowDetDTO();
                BeanUtils.copyProperties(workflowDet, workflowDetDTO);

                if (workflowDet.getServicesEventEntity() != null)
                    workflowDetDTO.setEventId(workflowDet.getServicesEventEntity().getServiceEventId());
                Long mapedOrg = workflowDet.getEventOrganisation().getOrgid();
                Long mapedDept = workflowDet.getEventDepartment().getDpDeptid();

                workflowDetDTO.setMapOrgId(mapedOrg);
                workflowDetDTO.setMapDeptList(tbDepartmentService.findAllDepartmentByOrganization(mapedOrg,
                        MainetConstants.Common_Constant.ACTIVE_FLAG));
                workflowDetDTO.setMapOrgName(workflowDet.getEventOrganisation().getONlsOrgname());
                workflowDetDTO.setMapDeptId(mapedDept);
                workflowDetDTO.setMapRoleList(iEntitlementService.findAllRolesByOrg(mapedOrg));
                workflowDetDTO.setMapEmpList(employeeService.findAllEmpByOrg(mapedOrg));
                workflowDetDTO.setMapDeptName(workflowDet.getEventDepartment().getDpDeptdesc());
                workflowDetDTO.setHiddenRoleOrEmpId(workflowDet.getRoleOrEmpIds());

                workFlowTypeDTO.getWorkflowDet().add(workflowDetDTO);
            }
        }
        logger.info("findById(Long id) execution ends ");
        return workFlowTypeDTO;

    }

    @SuppressWarnings("deprecation")
    @Override
    @Transactional(rollbackFor = Exception.class, readOnly = false)
    public boolean saveEdit(WorkflowMasDTO dto, List<Long> removeChildIds) {
        WorkflowMas workflowEntity = new WorkflowMas();
        WorkflowDet workflowDet;
        workflowEntity = getWorkFlowById(dto.getWfId());

        workflowEntity.setUpdatedBy(dto.getUpdatedBy());
        workflowEntity.setUpdatedDate(new Date());
        workflowEntity.setLgIpMacUpd(Utility.getMacAddress());

        workflowEntity.setWorkflowMode(dto.getWorkflowMode());
        for (WorkflowDetDTO subDTO : dto.getWorkflowDet()) {
            if (subDTO.getWfdId() == null) {
                workflowDet = new WorkflowDet();

                workflowDet.setCreateDate(new Date());
                workflowDet.setCreatedBy(dto.getUpdatedBy());
                workflowDet.setLgIpMac(Utility.getMacAddress());
                setdata(dto, workflowEntity, workflowDet, subDTO);

                workflowEntity.getWorkflowDetList().add(workflowDet);
            } else {
                for (WorkflowDet dtoData : workflowEntity.getWorkflowDetList()) {
                    if (subDTO.getWfdId().equals(dtoData.getWfdId())) {
                        // D#9919
                        if (!StringUtils.isEmpty(subDTO.getRoleOrEmpIds())) {
                            setdata(dto, workflowEntity, dtoData, subDTO);
                            dtoData.setUpdatedDate(new Date());
                            dtoData.setUpdatedBy(dto.getUpdatedBy());
                            dtoData.setLgIpMacUpd(Utility.getMacAddress());
                        }
                    }
                }
            }
        }

        workflowEntity = workFlowTypeRepository.save(workflowEntity);

        if (!removeChildIds.isEmpty())
            workFlowTypeRepository.deleteRecordDetails(MainetConstants.Common_Constant.NO,
                    workflowEntity.getUpdatedBy(), removeChildIds);
        logger.info("saveEdit(WorkFlowTypeDTO dto,List<Long> removeChildIds) execution ends ");
        return true;

    }

    @Override
    public WorkflowMas getWorkFlowById(Long id) {
        logger.info("findById(Long id) execution starts id= " + id);
        WorkflowMas workflowMas = workFlowTypeRepository.findOne(id);
        return workflowMas;
    }

    /**
     * @param dto
     * @param workflowEntity
     * @param workflowDet
     * @param subDTO
     */
    private void setdata(WorkflowMasDTO dto, WorkflowMas workflowEntity, WorkflowDet workflowDet,
            WorkflowDetDTO subDTO) {
        ServicesEventEntity servicesEventEntity;
        servicesEventEntity = new ServicesEventEntity();
        BeanUtils.copyProperties(subDTO, workflowDet);
        servicesEventEntity.setServiceEventId(subDTO.getEventId());
        workflowDet.setServicesEventEntity(servicesEventEntity);

        Organisation mapedOrg = new Organisation();
        mapedOrg.setOrgid(subDTO.getMapOrgId());
        workflowDet.setEventOrganisation(mapedOrg);

        Organisation workflowOrg = new Organisation();
        workflowOrg.setOrgid(dto.getCurrentOrgId());
        workflowDet.setCurrentOrganisation(workflowOrg);

        Department mapedDept = new Department();
        mapedDept.setDpDeptid(subDTO.getMapDeptId());
        workflowDet.setEventDepartment(mapedDept);

        workflowDet.setStatus(MainetConstants.Common_Constant.YES);
        workflowDet.setWfId(workflowEntity);
        convertSLAToMiliSec(workflowDet, subDTO, workflowOrg);
    }

    @SuppressWarnings("deprecation")
    @Override
    public List<WorkFlowTypeGrid> findRecords(Long orgId, Long deptId, Long serviceId, String status) {

        logger.info("findAllRecords(String status) execution starts status=" + status);
        List<WorkflowMas> workflowList = workflowTypeDAO.getAllWorkFlows(orgId, deptId, serviceId);
        List<WorkFlowTypeGrid> workFlowTypeGrids = new ArrayList<WorkFlowTypeGrid>();
        WorkFlowTypeGrid workFlowTypeGrid = null;

        if (workflowList != null && !workflowList.isEmpty()) {
            workFlowTypeGrids = new ArrayList<WorkFlowTypeGrid>();
            for (WorkflowMas entity : workflowList) {
                workFlowTypeGrid = new WorkFlowTypeGrid();
                workFlowTypeGrid.setId(entity.getWfId());
                workFlowTypeGrid.setDepName(entity.getDepartment().getDpDeptdesc());
                workFlowTypeGrid.setDeptRegName(entity.getDepartment().getDpNameMar());
                workFlowTypeGrid.setDeptId(entity.getDepartment().getDpDeptid());
                workFlowTypeGrid.setOrgId(orgId);
                workFlowTypeGrid.setServiceId(entity.getService().getSmServiceId());
                workFlowTypeGrid.setServiceName(entity.getService().getSmServiceName());
                workFlowTypeGrid.setServiceNameReg(entity.getService().getSmServiceNameMar());
                workFlowTypeGrid.setStatus(entity.getStatus());
                if (entity.getWmSchCodeId2() != null)
                workFlowTypeGrid.setSchemeId(entity.getWmSchCodeId2());
                if (entity.getComplaint() != null) {
                    ComplaintTypeBean bean = new ComplaintTypeBean();
                    BeanUtils.copyProperties(entity.getComplaint(), bean);
                    workFlowTypeGrid.setComplaint(bean);
                }
                //to get ExtIdentifier id
                if(entity.getExtIdentifier() != null) {
                	workFlowTypeGrid.setExtIdentifier(entity.getExtIdentifier());
                }
                workFlowTypeGrid.setWorkFlowMode(CommonMasterUtility.findLookUpDesc(MainetConstants.WORKFLOWTYPE.WFM,
                        orgId, entity.getWorkflowMode()));
                if (entity.getType().equals(MainetConstants.WORKFLOWTYPE.Flag_A)) {
                    workFlowTypeGrid.setWorkflowType(MainetConstants.WORKFLOWTYPE.ALL);
                } else {
                    final StringBuilder wardZone = new StringBuilder();
                    wardZone.append(
                            entity.getCodIdOperLevel1() != null
                                    ? CommonMasterUtility.getHierarchicalLookUp(entity.getCodIdOperLevel1(), orgId)
                                            .getLookUpDesc()
                                    : MainetConstants.BLANK);
                    wardZone.append(entity.getCodIdOperLevel2() != null
                            ? MainetConstants.WINDOWS_SLASH_WITH_SPACE + (CommonMasterUtility
                                    .getHierarchicalLookUp(entity.getCodIdOperLevel2(), orgId).getLookUpDesc() == null
                                            ? "ALL"
                                            : CommonMasterUtility
                                                    .getHierarchicalLookUp(entity.getCodIdOperLevel2(), orgId).getLookUpDesc())
                                    : MainetConstants.BLANK);
                    wardZone.append(entity.getCodIdOperLevel3() != null
                            ? MainetConstants.WINDOWS_SLASH_WITH_SPACE + (CommonMasterUtility
                                    .getHierarchicalLookUp(entity.getCodIdOperLevel3(), orgId).getLookUpDesc() == null
                                    ? "ALL"
                                    : CommonMasterUtility
                                            .getHierarchicalLookUp(entity.getCodIdOperLevel3(), orgId).getLookUpDesc())
                                    : MainetConstants.BLANK);
                    wardZone.append(entity.getCodIdOperLevel4() != null
                            ? MainetConstants.WINDOWS_SLASH_WITH_SPACE + (CommonMasterUtility
                                    .getHierarchicalLookUp(entity.getCodIdOperLevel4(), orgId).getLookUpDesc() == null
                                    ? "ALL"
                                    : CommonMasterUtility
                                            .getHierarchicalLookUp(entity.getCodIdOperLevel4(), orgId).getLookUpDesc())
                                    : MainetConstants.BLANK);
                    wardZone.append(entity.getCodIdOperLevel5() != null
                            ? MainetConstants.WINDOWS_SLASH_WITH_SPACE + (CommonMasterUtility
                                    .getHierarchicalLookUp(entity.getCodIdOperLevel2(), orgId).getLookUpDesc() == null
                                    ? "ALL"
                                    : CommonMasterUtility
                                            .getHierarchicalLookUp(entity.getCodIdOperLevel2(), orgId).getLookUpDesc())
                                    : MainetConstants.BLANK);
                    workFlowTypeGrid.setWorkflowType(wardZone.toString());
                }
                workFlowTypeGrids.add(workFlowTypeGrid);
            }
        }

        logger.info("findAllRecords(String status) execution ends ");

        return workFlowTypeGrids;
    }

    @Override
    public List<WorkflowMas> getWorkFlowTypeByOrgDepartmentAndComplaintType(final Long orgId, final Long deptId,
            final Long compId) {
        logger.info(
                "getWorkFlowTypeByOrgDepartmentAndComplaintType(Long orgId, Long deptId, Long compId) execution starts ");
        return workFlowTypeRepository.getWorkFlowTypeByOrgDepartmentAndComplaintType(orgId, deptId, compId);
    }

    @Override
    @Transactional
    public boolean deleteWorkFLow(final String flag, final Long empId, final Long id) {
        workFlowTypeRepository.deleteWorkFLow(flag, empId, id);
        return true;
    }

    @Override
    public List<WorkFlowTypeGrid> findAllRecords(String status, String type) {
        logger.info("findAllRecords(String status) execution starts status=" + status);
        List<Object[]> objList = null;
        List<Object[]> allData = new ArrayList<>();

        if (type == null || type.isEmpty() || type.equals("Y")) {
            objList = workFlowTypeRepository.findAllComplaintRecords(status);
            allData.addAll(objList);
        }
        if (type == null || type.isEmpty() || type.equals("N")) {
            objList = workFlowTypeRepository.findAllServiceRecords(status);
            allData.addAll(objList);
        }
        List<WorkFlowTypeGrid> workFlowTypeGrids = null;
        WorkFlowTypeGrid workFlowTypeGrid = null;

        if (allData != null && !allData.isEmpty()) {
            workFlowTypeGrids = new ArrayList<WorkFlowTypeGrid>();
            for (Object[] obj : allData) {
                workFlowTypeGrid = new WorkFlowTypeGrid();
                workFlowTypeGrid.setId(Long.valueOf(String.valueOf(obj[0])));
                workFlowTypeGrid.setOrgName(String.valueOf(obj[1]));
                if (obj[2] != null)
                    workFlowTypeGrid.setOrgRegName(String.valueOf(obj[2]));
                workFlowTypeGrid.setDepName(String.valueOf(obj[3]));
                if (obj[4] != null)
                    workFlowTypeGrid.setDeptRegName(String.valueOf(obj[4]));
                workFlowTypeGrid.setDeptId(Long.valueOf(String.valueOf(obj[5])));
                workFlowTypeGrid.setOrgId(Long.valueOf(String.valueOf(obj[6])));
                if (obj[7] != null) {
                    workFlowTypeGrid.setComSerId(Long.valueOf(String.valueOf(obj[7])));
                    workFlowTypeGrid.setComSerDesc(String.valueOf(obj[8]));
                }
                workFlowTypeGrids.add(workFlowTypeGrid);
            }
        }
        logger.info("findAllRecords(String status) execution ends ");
        return workFlowTypeGrids;
    }

    @Override
    public boolean isWorkFlowExist(Long orgId, Long deptId, Long serviceId, Long compId, String wardZoneType,
            Long codIdOperLevel1, Long codIdOperLevel2, Long codIdOperLevel3, Long codIdOperLevel4,
            Long codIdOperLevel5, BigDecimal fromAmount, BigDecimal toAmount, Long souceOfFund, Long schemeId, Long extIdentifier) {

        boolean isWorkFlowExist = false;
        List<WorkflowMas> masList = workflowTypeDAO.getDefinedActiveWorkFlows(orgId, deptId, serviceId, compId,schemeId,extIdentifier);
        if (masList.isEmpty())
            return isWorkFlowExist;

        String masWardZoneType = masList.get(0).getType();
        if (!masWardZoneType.equals(wardZoneType)) {
            // Requested WardZoneType is not same as existing WardZoneType
            return !isWorkFlowExist;
        }

        if (masWardZoneType.equals(MainetConstants.WORKFLOWTYPE.Flag_A)) {
            for (WorkflowMas w : masList) {
                if (souceOfFund != null && w.getWmSchCodeId1() != null && schemeId != null
                        && w.getWmSchCodeId2() != null && !isWorkFlowExist && extIdentifier != null && w.getExtIdentifier() != null) {
                    if ((souceOfFund.longValue() == w.getWmSchCodeId1().longValue())
                            && ((schemeId.longValue() == w.getWmSchCodeId2().longValue()))
                            && ((extIdentifier.longValue() == w.getExtIdentifier().longValue()))) {
                        boolean nonAmountWorkflow = true;
                        boolean hasAmountSlab = false;
                        BigDecimal masToAmount = w.getToAmount();
                        BigDecimal masFromAmount = w.getFromAmount();
                        if (masToAmount != null && masFromAmount != null) {
                            nonAmountWorkflow = false;
                            int a = fromAmount.compareTo(masFromAmount);
                            int b = fromAmount.compareTo(masToAmount);
                            int c = toAmount.compareTo(masFromAmount);
                            int d = toAmount.compareTo(masToAmount);
                            hasAmountSlab = a == 0 || b == 0 || c == 0 || d == 0 || (a + b) == 0 || (a + b) == 1
                                    || (c + d) == 0 || (c + d) == 1;
                        }
                        isWorkFlowExist = nonAmountWorkflow || hasAmountSlab;
                    } else {
                        isWorkFlowExist = false;
                    }
                }
                if (isWorkFlowExist)
                    break;
            }
        } else {
            for (WorkflowMas w : masList) {
                long formFirst = (codIdOperLevel1 == null) ? 0 : codIdOperLevel1;
                long formSecond = (codIdOperLevel2 == null) ? 0 : codIdOperLevel2;
                long formThird = (codIdOperLevel3 == null) ? 0 : codIdOperLevel3;
                long formFour = (codIdOperLevel4 == null) ? 0 : codIdOperLevel4;
                long formFive = (codIdOperLevel5 == null) ? 0 : codIdOperLevel5;
                boolean nonAmountWorkflow = false;
                boolean hasAmountSlab = false;
                boolean flag = false;
                BigDecimal masToAmount = w.getToAmount();
                BigDecimal masFromAmount = w.getFromAmount();
                long masFirst = (w.getCodIdOperLevel1() == null) ? 0 : w.getCodIdOperLevel1();
                long masSecond = (w.getCodIdOperLevel2() == null) ? 0 : w.getCodIdOperLevel2();
                long masThird = (w.getCodIdOperLevel3() == null) ? 0 : w.getCodIdOperLevel3();
                long masFour = (w.getCodIdOperLevel4() == null) ? 0 : w.getCodIdOperLevel4();
                long masFive = (w.getCodIdOperLevel5() == null) ? 0 : w.getCodIdOperLevel5();
                if (masToAmount != null && masFromAmount != null && (masFirst == formFirst && masSecond == formSecond
                        && masThird == formThird && masFour == formFour && masFive == formFive)) {
                    nonAmountWorkflow = true;
                    int a = fromAmount.compareTo(masFromAmount);
                    int b = fromAmount.compareTo(masToAmount);
                    int c = toAmount.compareTo(masFromAmount);
                    int d = toAmount.compareTo(masToAmount);
                    hasAmountSlab = a == 0 || b == 0 || c == 0 || d == 0 || (a + b) == 0 || (a + b) == 1 || (c + d) == 0
                            || (c + d) == 1;

                    if (!hasAmountSlab) {
                        nonAmountWorkflow = false;
                        flag = true;
                    }
                }
                hasAmountSlab = hasAmountSlab || (masFirst == formFirst && masSecond == formSecond
                        && masThird == formThird && masFour == formFour && masFive == formFive);
                isWorkFlowExist = (flag == true) ? false : (nonAmountWorkflow || hasAmountSlab);
                if (isWorkFlowExist)
                    break;
            }
        }
        return isWorkFlowExist;
    }

    @Override
    public boolean isEventMapped(List<Long> deletedList, Long orgId, Long deptId, Long serviceId) {
        boolean result = false;
        int count = workFlowTypeRepository.isEventMapped(deletedList, orgId, deptId, serviceId);
        if (count > 0) {
            result = true;
        }
        return result;
    }

    @Override
    public Set<LookUp> getCheckerSendBackEventList(final Long workflowTypeId, final Long taskId) {
        Set<LookUp> eventList = new LinkedHashSet<>();
        Long curentCheckerLevel = 1l;
        Long currentCheckerGroup = 1l;
        UserTaskDTO task = null;
        LookUp initiatorEvent = new LookUp();
        initiatorEvent.setDescLangFirst(MainetConstants.WorkFlow.EventNames.INITIATOR);
        initiatorEvent.setDescLangSecond(MainetConstants.WorkFlow.EventNames.INITIATOR);
        initiatorEvent.setLookUpId(0);
        initiatorEvent.setLookUpCode(0 + MainetConstants.operator.UNDER_SCORE + 0);
        eventList.add(initiatorEvent);

        if (taskId != null) {
            try {
                task = iWorkflowTaskService.findByTaskId(taskId);
            } catch (Exception e) {
                logger.warn("Unable to retrieve task data for task id " + taskId, e);
            }
            if (task != null) {
                if (task.getCurrentCheckerGroup() != null && task.getCurentCheckerLevel() != null) {
                    curentCheckerLevel = task.getCurentCheckerLevel();
                    currentCheckerGroup = task.getCurrentCheckerGroup();
                    if (curentCheckerLevel == 1 && currentCheckerGroup == 1)
                        return eventList;

                    TaskAssignmentRequest taskAssignmentRequest = new TaskAssignmentRequest();
                    taskAssignmentRequest.setWorkflowTypeId(task.getWorkflowId());
                    taskAssignmentRequest.setServiceEventName(MainetConstants.WorkFlow.EventNames.CHECKER);
                    LinkedHashMap<String, LinkedHashMap<String, TaskAssignment>> checkerLevelGroups = taskAssignmentService
                            .getEventLevelGroupsByWorkflowTypeAndEventName(taskAssignmentRequest);

                    // i iterator for task assignment level groups
                    for (int i = 1; i <= currentCheckerGroup; i++) {
                        LinkedHashMap<String, TaskAssignment> checkerLevels = checkerLevelGroups
                                .get(MainetConstants.GROUP + MainetConstants.operator.UNDER_SCORE + i);
                        // j iterator for task assignment levels for j'th task assignment level groups
                        for (int j = 1; j <= checkerLevels.size(); j++) {
                            TaskAssignment ta = checkerLevels
                                    .get(MainetConstants.LEVEL + MainetConstants.operator.UNDER_SCORE + j);
                            LookUp event = new LookUp();
                            event.setDescLangFirst(ta.getServiceEventName() + MainetConstants.WHITE_SPACE
                                    + MainetConstants.LEVEL + MainetConstants.WHITE_SPACE + j);
                            event.setDescLangSecond(ta.getServiceEventName() + MainetConstants.WHITE_SPACE
                                    + MainetConstants.LEVEL + MainetConstants.WHITE_SPACE + j);
                            event.setLookUpId(ta.getServiceEventId());
                            event.setLookUpCode(i + MainetConstants.operator.UNDER_SCORE + j);
                            event.setOtherField(ta.getActorId());
                            eventList.add(event);
                            long upToCheckerLevel = (curentCheckerLevel > 1l) ? curentCheckerLevel - 1l
                                    : curentCheckerLevel;
                            if (i == currentCheckerGroup && j == upToCheckerLevel)
                                break;
                        }
                    }
                } else {
                    logger.warn("Unable to find checker levels from task data for task id " + taskId);
                }
            }
        }
        return eventList;
    }

    @Override
    public boolean isLastTaskInCheckerTaskList(final Long taskId) {
        Long curentCheckerLevel = 1l;
        Long currentCheckerGroup = 1l;
        boolean isLastTaskInCheckerTaskList = false;
        UserTaskDTO task = null;
        if (taskId != null) {
            try {
                task = iWorkflowTaskService.findByTaskId(taskId);
            } catch (Exception e) {
                logger.warn("Unable to retrieve task data for task id " + taskId, e);
            }
            if (task != null) {
                if (task.getCurrentCheckerGroup() != null && task.getCurentCheckerLevel() != null) {

                    curentCheckerLevel = task.getCurentCheckerLevel();
                    currentCheckerGroup = task.getCurrentCheckerGroup();

                    TaskAssignmentRequest taskAssignmentRequest = new TaskAssignmentRequest();
                    taskAssignmentRequest.setWorkflowTypeId(task.getWorkflowId());
                    taskAssignmentRequest.setServiceEventName(MainetConstants.WorkFlow.EventNames.CHECKER);
                    LinkedHashMap<String, LinkedHashMap<String, TaskAssignment>> checkerLevelGroups = taskAssignmentService
                            .getEventLevelGroupsByWorkflowTypeAndEventName(taskAssignmentRequest);
                    int count = 0;
                    if (currentCheckerGroup == checkerLevelGroups.size()) {
                        LinkedHashMap<String, TaskAssignment> lastCheckerLevelGroup = checkerLevelGroups.get(
                                MainetConstants.GROUP + MainetConstants.operator.UNDER_SCORE + currentCheckerGroup);
                        if (curentCheckerLevel == lastCheckerLevelGroup.size())
                            isLastTaskInCheckerTaskList = true;
                    }else{
                    	for(Map.Entry<String, LinkedHashMap<String, TaskAssignment>> map : checkerLevelGroups.entrySet()){
                    		LinkedHashMap<String, TaskAssignment> groupValue = map.getValue();
                    		count = count + groupValue.size();
                    		}
                    	if(curentCheckerLevel == count){
                			isLastTaskInCheckerTaskList = true;
                    	}
                    }

                }

            }
        }
        return isLastTaskInCheckerTaskList;
    }

    @Override
    public boolean isLastTaskInCheckerTaskListGroup(final Long taskId) {
        Long curentCheckerLevel = 1l;
        Long currentCheckerGroup = 1l;
        boolean isLastTaskInCheckerTaskList = false;
        UserTaskDTO task = null;
        if (taskId != null) {
            try {
                task = iWorkflowTaskService.findByTaskId(taskId);
            } catch (Exception e) {
                logger.warn("Unable to retrieve task data for task id " + taskId, e);
            }
            if (task != null) {
                if (task.getCurrentCheckerGroup() != null && task.getCurentCheckerLevel() != null) {
                    curentCheckerLevel = task.getCurentCheckerLevel();
                    currentCheckerGroup = task.getCurrentCheckerGroup();

                    TaskAssignmentRequest taskAssignmentRequest = new TaskAssignmentRequest();
                    taskAssignmentRequest.setWorkflowTypeId(task.getWorkflowId());
                    taskAssignmentRequest.setServiceEventName(MainetConstants.WorkFlow.EventNames.CHECKER);
                    LinkedHashMap<String, LinkedHashMap<String, TaskAssignment>> checkerLevelGroups = taskAssignmentService
                            .getEventLevelGroupsByWorkflowTypeAndEventName(taskAssignmentRequest);

                    LinkedHashMap<String, TaskAssignment> curentCheckerLevelGroup = checkerLevelGroups
                            .get(MainetConstants.GROUP + MainetConstants.operator.UNDER_SCORE + currentCheckerGroup);
                    if (curentCheckerLevel == curentCheckerLevelGroup.size())
                        isLastTaskInCheckerTaskList = true;

                }

            }
        }

        return isLastTaskInCheckerTaskList;
    }

    @Override
    @Transactional(readOnly = true)
    public Long findDepartmentIdBytaskName(String taskName, Long orgId, Long workFlowId) {

        Long deptId = workFlowTypeRepository.findDepartmentByTaskName(taskName, orgId, workFlowId);

        return deptId;
    }

    @Override
    @Transactional(readOnly = true)
    public List<TbDepartment> getAllWorkflowDefinedDepartmentsByOrgId(Long orgId, String serviceShortCode) {
        logger.info("getAllWorkflowDefinedDepartmentsByOrgId");
        List<TbDepartment> depts = new ArrayList<>();
        TbDepartment dept = null;
        List<Department> departments = workFlowTypeRepository.getAllWorkflowDefinedDepartmentByOrgId(orgId,
                serviceShortCode);
        if (!departments.isEmpty()) {
            for (Department department : departments) {
                dept = new TbDepartment();
                BeanUtils.copyProperties(department, dept);
                depts.add(dept);
            }
        }
        logger.info("getAllWorkflowDefinedDepartmentsByOrgId execution ends ");
        return depts;
    }

    @Override
    public boolean checkPendingTask(List<Long> wfdIds, Long orgId) {
        boolean pendingTask = false;
        for (Long wfdId : wfdIds) {
            // get data from tb_workflow_det by using wfdId
            WorkflowDet wfDet = workflowMappingRepository.findOne(wfdId);
            // get WFID ,EEVENT_ID
            Long wfId = wfDet.getWfId().getWfId();
            Long eventId = wfDet.getServicesEventEntity().getSystemModuleFunction().getSmfid();
            // native query get data from tb_workflow_task
            Object[] pendingTaskData = workflowMappingRepository.getPendingTaskPresent(wfId, eventId,
                    wfDet.getWfId().getOrganisation().getOrgid());
            if (pendingTaskData.length != 0) {
                pendingTask = true;
                Object[] object = (Object[]) pendingTaskData[0];
                logger.info("Delete when pending task is WFTASK_ID " + object[0] + " workflow request id " + object[1]);
                break;
            }
        }

        return pendingTask;
    }
    
    @Override
	public boolean curentCheckerLevel(Long taskId) {
		UserTaskDTO task = null;
		boolean curentCheckerLevel = false;
		if (taskId != null) {
			try {
				task = iWorkflowTaskService.findByTaskId(taskId);
			} catch (Exception e) {
				logger.warn("Unable to retrieve task data for task id " + taskId, e);
			}
			if (task != null) {
				if (task.getCurrentCheckerGroup() != null && task.getCurentCheckerLevel() != null) {
					if (task.getCurentCheckerLevel() == 1) {
						curentCheckerLevel = true;
					}
				}
			}
		}
		return curentCheckerLevel;
	}
    
}
