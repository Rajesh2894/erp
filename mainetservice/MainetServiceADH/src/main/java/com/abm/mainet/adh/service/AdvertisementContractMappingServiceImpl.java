package com.abm.mainet.adh.service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.abm.mainet.adh.dao.IAdvertisementContractMappingDao;
import com.abm.mainet.adh.domain.ADHBillMasEntity;
import com.abm.mainet.adh.domain.ADHContractMappingEntity;
import com.abm.mainet.adh.domain.HoardingMasterEntity;
import com.abm.mainet.adh.dto.ADHContractMappingDto;
import com.abm.mainet.adh.dto.HoardingMasterDto;
import com.abm.mainet.adh.repository.AdvertisementContractMappingRepository;
import com.abm.mainet.common.constant.MainetConstants;
import com.abm.mainet.common.domain.ContractDetailEntity;
import com.abm.mainet.common.domain.ContractInstalmentDetailEntity;
import com.abm.mainet.common.domain.ContractMastEntity;
import com.abm.mainet.common.domain.ContractPart2DetailEntity;
import com.abm.mainet.common.domain.Organisation;
import com.abm.mainet.common.master.dto.ContractMappingDTO;
import com.abm.mainet.common.master.dto.TbDepartment;
import com.abm.mainet.common.master.repository.ContractAgreementRepository;
import com.abm.mainet.common.utility.ApplicationContextProvider;
import com.abm.mainet.common.utility.CommonMasterUtility;
import com.abm.mainet.common.utility.CommonUtility;
import com.abm.mainet.common.utility.Utility;

/**
 * @author cherupelli.srikanth
 * @since 04 November
 */
@Service
public class AdvertisementContractMappingServiceImpl implements IAdvertisementContractMappingService {

    @Autowired
    private AdvertisementContractMappingRepository advertisementContractMappingRepository;

    @Autowired
    private ContractAgreementRepository contractAgreementRepository;

    @Autowired
    private IADHBillMasService adhBillMasService;

    @Override
    public List<String[]> getContNoByDeptIdAndOrgId(Long deptId, Long orgId) {
	return advertisementContractMappingRepository.findByContDeptAndOrgId(deptId, orgId);

    }

    @Override
    @Transactional
    public boolean saveADHContractMapping(ADHContractMappingDto contractMappingDto) {

	boolean status = false;

	List<ADHContractMappingEntity> adhContractMapEntityList = new ArrayList<>();

	contractMappingDto.getHoardingMasterList().forEach(hoarding -> {
	    ADHContractMappingEntity adhContractMapEntity = new ADHContractMappingEntity();
	    HoardingMasterEntity hoardingEntity = new HoardingMasterEntity();
	    BeanUtils.copyProperties(contractMappingDto, adhContractMapEntity);
	    ContractMastEntity contractMastEntity = new ContractMastEntity();
	    contractMastEntity.setContId(contractMappingDto.getContractId());
	    adhContractMapEntity.setContractMastEntity(contractMastEntity);
	    hoardingEntity.setHoardingId(hoarding.getHoardingId());
	    adhContractMapEntity.setHoardingEntity(hoardingEntity);
	    adhContractMapEntity.setContMapActive(MainetConstants.FlagY);
	    adhContractMapEntityList.add(adhContractMapEntity);
	});
	advertisementContractMappingRepository.save(adhContractMapEntityList);

	List<ContractInstalmentDetailEntity> contractInstallmentDetails = contractAgreementRepository
		.finAllRecords(contractMappingDto.getContractId(), MainetConstants.FlagY);

	List<ADHBillMasEntity> adhBillMasList = new ArrayList<>();
	contractInstallmentDetails.forEach(installment -> {
	    ADHBillMasEntity billMasEntity = new ADHBillMasEntity();
	    billMasEntity.setOrgId(contractMappingDto.getOrgId());
	    billMasEntity.setCreatedBy(contractMappingDto.getCreatedBy());
	    billMasEntity.setCreatedDate(contractMappingDto.getCreatedDate());
	    billMasEntity.setLgIpMac(contractMappingDto.getLgIpMac());
	    billMasEntity.setActive(MainetConstants.FlagY);
	    billMasEntity.setContractId(contractMappingDto.getContractId());
	    billMasEntity.setConitId(installment.getConitId());
	    billMasEntity.setBillDate(new Date());
	    billMasEntity.setBillAmount(installment.getConitAmount());

	    billMasEntity.setBalanceAmount(installment.getConitAmount());
	    billMasEntity.setPaidFlag(MainetConstants.FlagN);
	    billMasEntity.setStartDate(installment.getConitStartDate());
	    billMasEntity.setDueDate(installment.getConitDueDate());
	    billMasEntity.setTaxId(installment.getTaxId());
	    billMasEntity.setBillAmount(installment.getConitAmount());
	    billMasEntity.setBillType(MainetConstants.FlagB);
	    adhBillMasList.add(billMasEntity);
	});
	adhBillMasService.saveADHBillMas(adhBillMasList);
	contractAgreementRepository.updateContractMapFlag(contractMappingDto.getContractId(),
		contractMappingDto.getCreatedBy());
	status = true;
	return status;
    }

    @Override
    @Transactional(readOnly = true)
    public List<ContractMappingDTO> findContractDeptWise(Long orgId, TbDepartment tbDepartment, String flag) {

	List<ContractMastEntity> contractMastList = null;
	if (flag.equals(MainetConstants.FlagE)) {
	    contractMastList = advertisementContractMappingRepository.findContractDeptWiseExist(orgId,
		    tbDepartment.getDpDeptid());
	}
	return getContactList(contractMastList, tbDepartment);
    }

    private List<ContractMappingDTO> getContactList(final List<ContractMastEntity> contractMastEntity,
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
		if (StringUtils.isNotBlank(contractPart2DetailEntity.getContp2Primary())) {
		    if (contractPart2DetailEntity.getContp2Type().equals("V")
			    && contractPart2DetailEntity.getContp2Primary().equals("Y")) {
			contractMappingDTO.setRepresentedBy(contractPart2DetailEntity.getContp2Name());
			contractMappingDTO.setVendorName(ApplicationContextProvider.getApplicationContext()
				.getBean(ContractAgreementRepository.class).getVenderNameOnVenderId(
					contractMastEntity2.getOrgId(), contractPart2DetailEntity.getVmVendorid()));

			break;
		    }
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

    @Override
    @Transactional
    public List<ContractMappingDTO> findContract(Long orgId, String contractNo, Date contactDate,
	    TbDepartment tbDepartment) {
	final List<ContractMastEntity> contractMastList = advertisementContractMappingRepository
		.findContractsExist(orgId, tbDepartment.getDpDeptid(), contractNo, contactDate);
	return getContactList(contractMastList, tbDepartment);
    }

    @Override
    @Transactional(readOnly = true)
    public List<ContractMappingDTO> getAdhContractsByContractId(final Long orgId, final Long contId,
	    final TbDepartment tbDepartment) {
	final List<ContractMastEntity> contractMastList = contractAgreementRepository.findContractsView(orgId, contId);
	return getContactList(contractMastList, tbDepartment);
    }

    @Override
    @Transactional(readOnly = true)
    public ADHContractMappingDto findByContractId(Long contId, Long orgId) {
	List<ADHContractMappingEntity> adhContractEntity = advertisementContractMappingRepository
		.findByContractMastEntityContIdAndOrgId(contId, orgId);
	ADHContractMappingDto adhContract = new ADHContractMappingDto();
	Organisation org = new Organisation();
	org.setOrgid(orgId);
	if (CollectionUtils.isNotEmpty(adhContractEntity)) {

	    for (ADHContractMappingEntity entity : adhContractEntity) {

		HoardingMasterDto hoardingDto = new HoardingMasterDto();
		hoardingDto.setHoardingNumber(entity.getHoardingEntity().getHoardingNumber());
		hoardingDto.setHoardingDescription(entity.getHoardingEntity().getHoardingDescription());
		hoardingDto.setHoardingHeight(entity.getHoardingEntity().getHoardingHeight());
		hoardingDto.setHoardingArea(entity.getHoardingEntity().getHoardingArea());
		hoardingDto.setHoardingLength(entity.getHoardingEntity().getHoardingLength());
		hoardingDto.setDisplayIdDesc(CommonMasterUtility
			.getNonHierarchicalLookUpObject(entity.getHoardingEntity().getDisplayTypeId(), org)
			.getDescLangFirst());
		adhContract.getHoardingMasterList().add(hoardingDto);

	    }

	    for (final ADHContractMappingEntity estateContractMapping : adhContractEntity) {
		adhContract.setHoardingId(estateContractMapping.getHoardingEntity().getHoardingId());
		break;
	    }
	}
	return adhContract;
    }

    @Override
    @Transactional
    public Long findContractByContIdAndOrgId(Long contId, Long orgId) {
	return advertisementContractMappingRepository.findCountByContId(contId, orgId);

    }

    @Override
    @Transactional
    public Long getContIdByHoardIdAndOrgId(Long hoardingId, Long orgId) {

	return advertisementContractMappingRepository.findHoardingExistByHoardIdAndOrgId(hoardingId, orgId);
    }

    @Override
    @Transactional
    public List<ContractMappingDTO> searchContractMappingData(Long orgId, String contractNo, Date contactDate,
	    TbDepartment tbDepartment) {
	List<ContractMastEntity> contractMastList = ApplicationContextProvider.getApplicationContext()
		.getBean(IAdvertisementContractMappingDao.class)
		.searchContractMappingData(orgId, contractNo, contactDate, tbDepartment);
	return getContactList(contractMastList, tbDepartment);
    }
}
