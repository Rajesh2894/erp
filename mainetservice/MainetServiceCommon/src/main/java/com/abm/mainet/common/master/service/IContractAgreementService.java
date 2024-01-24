package com.abm.mainet.common.master.service;

import java.io.File;
import java.util.List;
import java.util.Map;

import com.abm.mainet.common.dto.TbAcVendormaster;
import com.abm.mainet.common.master.dto.ContractAgreementSummaryDTO;
import com.abm.mainet.common.master.dto.ContractDetailDTO;
import com.abm.mainet.common.master.dto.ContractMastDTO;
import com.abm.mainet.common.utility.LookUp;

/**
 * @author apurva.salgaonkar
 *
 */
public interface IContractAgreementService {

    List<Object[]> getDepartmentsMappedWithContractParti1(Long orgId);

    public List<Object[]> getVenderList(final Long orgId, final Long venTypeId, final Long statusId);

    public TbAcVendormaster getVenderTypeIdByVenderId(Long orgId, Long venderId);

    List<ContractAgreementSummaryDTO> getContractAgreementSummaryData(
            Long orgId, String contractNo, String contractDate, Long deptId,
            Long venderId, String viewClosedCon, String renewal) throws Exception;

    ContractMastDTO findById(Long contId, Long orgId);

    String findContractMapedOrNot(Long orgId, Long contId);

    ContractAgreementSummaryDTO findByContractNo(Long orgId, String contNo);

    ContractMastDTO saveContractAgreement(ContractMastDTO contractMastDTO, Long orgId, int langId,
            Long empId, String modeType, Map<String, File> UploadMap);

    int updateContractMapFlag(Long contId, Long empId);

    /**
     * used to get LOA details by LOA numbner and Dept Shot code( used to get data from different module based on dept Code)
     * @param orgId
     * @param laoNumber
     * @param deptShortCode
     * @return
     */
    ContractMastDTO getLoaDetailsByLoaNumber(Long orgId, String laoNumber, String deptShortCode);

    ContractDetailDTO getContractDetail(Long contId);

    ContractMastDTO getContractByLoaNo(String loaNo, Long orgId);

    void deleteContractDocFileById(List<Long> enclosureRemoveById, Long empId);

    List<ContractAgreementSummaryDTO> getContractAgreementFilterData(
            Long orgId, String contractNo, String contractDate, String viewClosedCon);

	List<Object[]> getBillDetailbyContAndMobile(String contNo, String mobileNo,Long orgId);

	List<Object[]> getEstateList(Long orgId);

	List<Object[]> getPropListByEstateId(Long orgId, Long estateId);

	List<ContractAgreementSummaryDTO> getRLContractAgreementSummaryData(
            Long orgId, String contractNo, String contractDate, Long deptId, String viewClosedCon, String renewal,Long estateId, Long propId, int langId) throws Exception;

	List<LookUp> getAllDesgBasedOnDept(Long deptId, Long orgId);

	List<Object[]> getBillDetailbyContAndMobileWorkFlow(String contNo, String wfRefno, Long orgId);

}
