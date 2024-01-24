package com.abm.mainet.rnl.service;

import java.util.Date;
import java.util.List;

import com.abm.mainet.common.master.dto.ContractMappingDTO;
import com.abm.mainet.common.master.dto.ContractMastDTO;
import com.abm.mainet.common.master.dto.TbDepartment;
import com.abm.mainet.common.workflow.dto.WorkflowTaskAction;
import com.abm.mainet.rnl.dto.EstateContMappingDTO;
import com.abm.mainet.rnl.dto.EstateContPrintDTO;

/**
 * @author ritesh.patil
 *
 */
public interface IEstateContractMappingService {

    boolean save(EstateContMappingDTO estateContMappingDTO);

    EstateContMappingDTO findByContractId(Long contId);

    EstateContPrintDTO findContractPrintValues(Long orgId, Long contId);

    boolean findCountForProperty(Long orgId, Long propId);

    List<ContractMappingDTO> findContractDeptWise(Long orgId, TbDepartment tbDepartment, String flag);

    List<ContractMappingDTO> findContract(Long orgId, String contractNo, Date contactDate, TbDepartment tbDepartment);

    List<ContractMappingDTO> findContractsByContractId(Long orgId, Long contId, TbDepartment tbDepartment);

    boolean getCountContractDeptWiseNotExist(Long orgId, Long deptId);

    void updateContractCloseFlag(String contCloseFlag, Long contId, Long empId);

    List<EstateContMappingDTO> findByPropertyId(Long propId);

    // T#37721
    List<Object[]> fetchEstatePropertyForBillPay(String status, Long orgId);

    boolean propertyExistInContractPeriosd(Long orgId, Long propId);

	String generateWorkflowRefNumber(Long org);
	
	EstateContMappingDTO findPropIdByAppId(String applicationId, long orgid);

	boolean executeApprovalWorkflowAction(WorkflowTaskAction taskAction, String eventName, Long serviceId,
			String serviceShortCode);

	EstateContMappingDTO findContractsByContractNo(Long orgId, String contNo);

	List<Object[]> fetchEstateDetails(String status, Long orgId, Long langId);

	List<Object[]> searchByData(String contId, String propertyContractNo, String estateName, String mobileNo, Long orgId);

	List<Object[]> fetchPropertyByEstateName(String estateName, Long orgId);

	void saveContractAdjustment(ContractMastDTO contractMastDTO, Long orgid, int languageId, Long empId, String ipAddress, List<Long> removeIds);

	void saveRlBillAdjustment(Long contId, Long orgId, int languageId, Long empId,String ipAddress, List<Long> removeIds);

	List<Object[]> fetchEstatePropertyDetails(String contNo, Long orgId);

	void updatePropertyMappingStatus(char flag, Long propId);

	boolean existsBySmShortdescAndAdditionalRefNo(Long contId);

}
