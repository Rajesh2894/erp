package com.abm.mainet.workManagement.service;

import java.util.List;
import java.util.Map;

import com.abm.mainet.common.integration.acccount.dto.VendorBillApprovalDTO;
import com.abm.mainet.workManagement.domain.ProjectBudgetDetEntity;
import com.abm.mainet.workManagement.dto.WmsProjectMasterDto;
import com.abm.mainet.workManagement.dto.WorkDefinationYearDetDto;

public interface WmsProjectMasterService {

    /**
     * method is used for get Project Master By ProjId
     * 
     * @param projId
     * @return WmsProjectMasterDto
     */
    WmsProjectMasterDto getProjectMasterByProjId(Long projId);

    /**
     * method is used for check Project Work Association
     * 
     * @param projId
     * @param orgId
     * @return boolean
     */
    boolean checkProjectWorkAssociation(Long projId, Long orgId);

    /**
     * method is used for delete Project By ProjId
     * 
     * @param projId
     */
    void deleteProjectByProjId(Long projId);

    /**
     * method is used for get Project Master List
     * 
     * @param startDate
     * @param endDate
     * @param projectName
     * @param projCode
     * @param orgid
     * @return List<WmsProjectMasterDto>
     */
    List<WmsProjectMasterDto> getProjectMasterList(Long sourceCode, Long sourceName, String projectName,
            String projCode, long orgid, Long dpDeptId, Long projStatus);

    /**
     * method is used for save Update Project Master
     * 
     * @param wmsProjectMasterDto
     * @param list
     * @return WmsProjectMasterDto
     */
    WmsProjectMasterDto saveUpdateProjectMaster(WmsProjectMasterDto wmsProjectMasterDto, List<Long> list);

    /**
     * method is used for get Active Project Master List By OrgId
     * 
     * @param orgid
     * @return List<WmsProjectMasterDto>
     */
    List<WmsProjectMasterDto> getActiveProjectMasterListByOrgId(long orgid);

    /**
     * method is used for get Active Project Master List Without Scheme By OrgId
     * 
     * @param orgid
     * @return List<WmsProjectMasterDto>
     */
    List<WmsProjectMasterDto> getActiveProjectMasterListWithoutSchemeByOrgId(long orgid);

    /**
     * method is used for get Active Project Master List By SchId
     * 
     * @param schId
     * @return
     */
    List<WmsProjectMasterDto> getActiveProjectMasterListBySchId(long schId);

    // List<WmsProjectMasterDto> getAllProjectMaster(List<Long> projId);
    /**
     * method is used for get All Project Master
     * 
     * @param orgId
     * @param projId
     * @return List<WmsProjectMasterDto>
     */
    List<WmsProjectMasterDto> getAllProjectMaster(Long orgId, List<Long> projId);

    /**
     * method is used for get All Project Associated Mile Stone
     * 
     * @param orgid
     * @return
     */
    List<Long> getAllProjectAssociatedMileStone(Long orgid);

    /**
     * method is used for get All Project Associated By MileStone
     * 
     * @param orgid
     * @param mileStoneFlag
     * @return
     */
    List<Object[]> getAllProjectAssociatedByMileStone(Long orgid, String mileStoneFlag);

    /**
     * get all active projects list by orgid and department id
     * 
     * @param orgid
     * @return
     */
    List<WmsProjectMasterDto> getAllActiveProjectsByDeptIdAndOrgId(Long deptId, Long orgid);

    /**
     * get all active projects associated Dept List by orgid
     * 
     * @param orgid
     * @return List<Long>
     */
    List<Long> getAllProjectAssociatedDeptList(Long orgid);

	List<WorkDefinationYearDetDto> getYearDetailByProjectId(WmsProjectMasterDto workDefinitionDto, Long orgId);

	void inactiveRemovedYearDetails(WmsProjectMasterDto wmsProjectMasterDto, List<Long> removeFileByIdVal);

	Map<Long, String> getBudgetExpenditure(VendorBillApprovalDTO billApprovalDTO);

}
