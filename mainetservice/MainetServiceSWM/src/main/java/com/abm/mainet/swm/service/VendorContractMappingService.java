/*
 * 
 */
package com.abm.mainet.swm.service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.abm.mainet.common.audit.service.AuditService;
import com.abm.mainet.common.constant.MainetConstants;
import com.abm.mainet.common.domain.ContractDetailEntity;
import com.abm.mainet.common.domain.ContractMastEntity;
import com.abm.mainet.common.domain.ContractPart2DetailEntity;
import com.abm.mainet.common.domain.TbAcVendormasterEntity;
import com.abm.mainet.common.master.dto.ContractMappingDTO;
import com.abm.mainet.common.master.dto.TbDepartment;
import com.abm.mainet.common.master.repository.ContractAgreementRepository;
import com.abm.mainet.common.utility.CommonUtility;
import com.abm.mainet.common.utility.Utility;
import com.abm.mainet.swm.domain.VendorContractMapping;
import com.abm.mainet.swm.domain.VendorContractMappingHistory;
import com.abm.mainet.swm.dto.VendorContractMappingDTO;
import com.abm.mainet.swm.mapper.VendorContractMappingMapper;
import com.abm.mainet.swm.repository.VendorContractMappingRepository;

/**
 * The Class VendorContractMappingServiceImpl.
 *
 * @author Lalit.Prusti
 * 
 * Created Date : 30-May-2018
 */
@Service
public class VendorContractMappingService implements IVendorContractMappingService {

    /** The vehicle master repository. */
    @Autowired
    private VendorContractMappingRepository vendorContractMappingRepository;

    /** The vehicle master mapper. */
    @Autowired
    private VendorContractMappingMapper vendorContractMappingMapper;

    /** The audit service. */
    @Autowired
    private AuditService auditService;

    /** The contract agreement repository. */
    @Autowired
    private ContractAgreementRepository contractAgreementRepository;

    /*
     * (non-Javadoc)
     * @see com.abm.mainet.swm.service.VendorContractMappingService#deleteVendorContractMapping(java.lang.Long)
     */
    @Override
    @Transactional
    public void delete(Long vendorContractMappingId, Long empId, String ipMacAdd) {
        VendorContractMapping master = vendorContractMappingRepository.findOne(vendorContractMappingId);
        // TODO: set delete flag master.setDeActive(MainetConstants.IsDeleted.DELETE);
        master.setUpdatedBy(empId);
        master.setUpdatedDate(new Date());
        master.setLgIpMacUpd(ipMacAdd);
        vendorContractMappingRepository.save(master);
        saveVendorHistory(master, MainetConstants.Transaction.Mode.DELETE);
    }

    /*
     * (non-Javadoc)
     * @see
     * com.abm.mainet.swm.service.VendorContractMappingService#getVendorContractMappingByVendorContractMappingId(java.lang.Long)
     */
    @Override
    @Transactional(readOnly = true)
    public VendorContractMappingDTO getById(Long vendorContractMapping) {
        return vendorContractMappingMapper
                .mapVendorContractMappingToVendorContractMappingDTO(
                        vendorContractMappingRepository.findOne(vendorContractMapping));
    }

    /**
     * Mapped.
     *
     * @param vendorContractMappingDetails the vendor contract mapping details
     * @return the vendor contract mapping
     */
    private VendorContractMapping mapped(VendorContractMappingDTO vendorContractMappingDetails) {
        VendorContractMapping master = vendorContractMappingMapper
                .mapVendorContractMappingDTOToVendorContractMapping(vendorContractMappingDetails);
        return master;
    }

    /*
     * (non-Javadoc)
     * @see com.abm.mainet.swm.service.VendorContractMappingService#saveVendorContractMapping(com.abm.mainet.swm.dto.
     * VendorContractMappingDTO)
     */
    @Override
    @Transactional
    public VendorContractMappingDTO save(VendorContractMappingDTO vendorContractMappingDetails) {
        VendorContractMapping master = mapped(vendorContractMappingDetails);
        master = vendorContractMappingRepository.save(master);

        contractAgreementRepository.updateContractMapFlag(vendorContractMappingDetails.getContId(),
                vendorContractMappingDetails.getCreatedBy());
        saveVendorHistory(master, MainetConstants.Transaction.Mode.ADD);
        return vendorContractMappingMapper.mapVendorContractMappingToVendorContractMappingDTO(master);
    }

    /*
     * (non-Javadoc)
     * @see com.abm.mainet.swm.service.IVendorContractMappingService#save(java.util.List)
     */
    @Override
    @Transactional
    public List<VendorContractMappingDTO> save(List<VendorContractMappingDTO> vendorContractMappingDTOList) {
        List<VendorContractMapping> masterList = vendorContractMappingMapper
                .mapVendorContractMappingDTOListToVendorContractMappingList(vendorContractMappingDTOList);
        masterList = vendorContractMappingRepository.save(masterList);

        contractAgreementRepository.updateContractMapFlag(vendorContractMappingDTOList.get(0).getContId(),
                vendorContractMappingDTOList.get(0).getCreatedBy());
        masterList.forEach(master -> {
            VendorContractMappingHistory masterHistory = new VendorContractMappingHistory();
            masterHistory.setHStatus(MainetConstants.Transaction.Mode.ADD);
            auditService.createHistory(master, masterHistory);
        });

        return vendorContractMappingMapper.mapVendorContractMappingListToVendorContractMappingDTOList(masterList);
    }

    /**
     * Save vendor history.
     *
     * @param master the master
     * @param status the status
     */
    private void saveVendorHistory(VendorContractMapping master, String status) {
        VendorContractMappingHistory masterHistory = new VendorContractMappingHistory();
        masterHistory.setHStatus(status);
        auditService.createHistory(master, masterHistory);
    }

    /*
     * (non-Javadoc)
     * @see com.abm.mainet.swm.service.VendorContractMappingService#updateVendorContractMapping(com.abm.mainet.swm.dto.
     * VendorContractMappingDTO)
     */
    @Override
    @Transactional
    public VendorContractMappingDTO update(VendorContractMappingDTO vendorContractMappingDetails) {
        VendorContractMapping master = mapped(vendorContractMappingDetails);
        master = vendorContractMappingRepository.save(master);
        saveVendorHistory(master, MainetConstants.Transaction.Mode.UPDATE);
        return vendorContractMappingMapper.mapVendorContractMappingToVendorContractMappingDTO(master);
    }

    /*
     * (non-Javadoc)
     * @see com.abm.mainet.swm.service.VendorContractMappingService#findContractDeptWise(java.lang.Long,
     * com.abm.mainet.common.master.dto.TbDepartment, java.lang.String)
     */
    @Override
    @Transactional(readOnly = true)
    public List<ContractMappingDTO> findContractDeptWise(final Long orgId, final TbDepartment tbDepartment,
            final String flag) {

        List<ContractMastEntity> contractMastList = null;
        if (flag.equals("E")) {
            contractMastList = vendorContractMappingRepository.findContractDeptWiseNotMapped(orgId, tbDepartment.getDpDeptid());
        } else {
            contractMastList = vendorContractMappingRepository.findContractDeptWiseMapped(orgId, tbDepartment.getDpDeptid());
        }
        return setupContactList(contractMastList, tbDepartment);
    }

    /**
     * Setup contact list.
     *
     * @param contractMastEntity the contract mast entity
     * @param tbDepartment the tb department
     * @return the list
     */
    private List<ContractMappingDTO> setupContactList(final List<ContractMastEntity> contractMastEntity,
            final TbDepartment tbDepartment) {
        ContractMappingDTO contractMappingDTO = null;
        List<ContractPart2DetailEntity> contractPart2DetailEntities = null;
        List<ContractDetailEntity> contractDetailEntities = null;
        final List<ContractMappingDTO> contractMastDTOs = new ArrayList<>();
        for (final ContractMastEntity contractMastEntity2 : contractMastEntity) {
            contractMappingDTO = new ContractMappingDTO();
            contractMappingDTO.setContId(contractMastEntity2.getContId());
            contractMappingDTO.setContractNo(contractMastEntity2.getContNo());
            contractMappingDTO.setContDate(Utility.dateToString(contractMastEntity2.getContDate()));
            contractMappingDTO.setDeptName(tbDepartment.getDpDeptdesc());
            contractMappingDTO.setDeptNameReg(tbDepartment.getDpNameMar());
            contractPart2DetailEntities = contractMastEntity2.getContractPart2List();
            for (final ContractPart2DetailEntity contractPart2DetailEntity : contractPart2DetailEntities) {
                if (contractPart2DetailEntity.getContp2Type().equals("V")
                        && contractPart2DetailEntity.getContp2Primary().equals("Y")) {
                    contractMappingDTO.setRepresentedBy(contractPart2DetailEntity.getContp2Name());
                    contractMappingDTO.setVendorName(contractPart2DetailEntity.getVmVendorid().toString());
                    break;
                }
            }
            contractDetailEntities = contractMastEntity2.getContractDetailList();
            for (final ContractDetailEntity contractDetailEntity : contractDetailEntities) {
                contractMappingDTO.setToDate(CommonUtility.dateToString(contractDetailEntity.getContToDate()));
                contractMappingDTO.setFromDate(CommonUtility.dateToString(contractDetailEntity.getContFromDate()));
            }
            contractMastDTOs.add(contractMappingDTO);
        }
        return contractMastDTOs;
    }

    /*
     * (non-Javadoc)
     * @see com.abm.mainet.swm.service.VendorContractMappingService#findContract(java.lang.Long, java.lang.String, java.util.Date,
     * com.abm.mainet.common.master.dto.TbDepartment)
     */
    @Override
    @Transactional(readOnly = true)
    public List<ContractMappingDTO> findContract(final Long orgId, final String contractNo, final Date contactDate,
            final TbDepartment tbDepartment) {
        final List<ContractMastEntity> contractMastList = vendorContractMappingRepository.findContractsExist(orgId,
                tbDepartment.getDpDeptid(), contractNo, contactDate);
        return setupContactList(contractMastList, tbDepartment);
    }

    /*
     * (non-Javadoc)
     * @see com.abm.mainet.swm.service.VendorContractMappingService#findContractsByContractId(java.lang.Long, java.lang.Long,
     * com.abm.mainet.common.master.dto.TbDepartment)
     */
    @Override
    @Transactional(readOnly = true)
    public List<ContractMappingDTO> findContractsByContractId(final Long orgId, final Long contId,
            final TbDepartment tbDepartment) {
        final List<ContractMastEntity> contractMastList = contractAgreementRepository.findContractsView(orgId, contId);
        return setupContactList(contractMastList, tbDepartment);
    }

    /*
     * (non-Javadoc)
     * @see com.abm.mainet.swm.service.VendorContractMappingService#getById(java.lang.Long, java.lang.Long)
     */
    @Override
    @Transactional(readOnly = true)
    public List<VendorContractMappingDTO> getById(Long contractId, Long orgId) {
        return vendorContractMappingMapper.mapVendorContractMappingListToVendorContractMappingDTOList(
                vendorContractMappingRepository.findByContIdAndOrgid(contractId, orgId));

    }

    /*
     * (non-Javadoc)
     * @see com.abm.mainet.swm.service.IVendorContractMappingService#findDesignationById(java.lang.Long)
     */
    @Override
    @Transactional(readOnly = true)
    public String findDesignationById(Long desigId) {
        // TODO Auto-generated method stub
        return vendorContractMappingRepository.findDesigNameById(desigId);
    }

    /*
     * (non-Javadoc)
     * @see com.abm.mainet.swm.service.IVendorContractMappingService#validateContractMapping(java.util.List)
     */
    @Override
    @Transactional(readOnly = true)
    public boolean validateContractMapping(List<VendorContractMappingDTO> vendorMappingList) {
        // TODO Auto-generated method stub
        boolean status = false;
        Long vendorConList = null;
        for (VendorContractMappingDTO vendotContractDto : vendorMappingList) {
            vendorConList = vendorContractMappingRepository.findContractExitOrNot(
                    vendotContractDto.getCodWard1(), vendotContractDto.getCodWard2(), vendotContractDto.getCodWard3(),
                    vendotContractDto.getCodWard4(), vendotContractDto.getCodWard5(), vendotContractDto.getMapTaskId(),
                    vendotContractDto.getMapWastetype(), vendotContractDto.getBeatId(),
                    vendotContractDto.getOrgid());
            if (vendorConList == 0) {
                status = true;
            }
        }
        return status;

    }
    
    /*
     * (non-Javadoc)
     * @see com.abm.mainet.swm.service.IVendorContractMappingService#findDesignationById(java.lang.Long)
     */
    @Override
    @Transactional(readOnly = true)
    public  List<ContractMappingDTO>  getContractMappedByOrgId(Long orgId) {
        // TODO Auto-generated method stub
    	List<ContractMastEntity> contList=vendorContractMappingRepository.getContractMappedByOrgId(orgId);
    	List<ContractMappingDTO> contractMappingDTOList = new ArrayList<ContractMappingDTO>();
    	for (final ContractMastEntity contractMastEntity2 : contList) {
    		ContractMappingDTO dto=new ContractMappingDTO();
    		if(contractMastEntity2.getContNo()!=null) {
    		dto.setContractNo(contractMastEntity2.getContNo());
    		contractMappingDTOList.add(dto);
    		}
    	}
    	return contractMappingDTOList;
    }
}
