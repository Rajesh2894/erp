/**
 * 
 */
package com.abm.mainet.rnl.service;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestBody;

import com.abm.mainet.common.constant.MainetConstants;
import com.abm.mainet.common.domain.ContractDetailEntity;
import com.abm.mainet.common.domain.ContractInstalmentDetailEntity;
import com.abm.mainet.common.domain.ContractInstalmentDetailHistEntity;
import com.abm.mainet.common.domain.ContractMastEntity;
import com.abm.mainet.common.domain.ContractPart2DetailEntity;
import com.abm.mainet.common.domain.ContractPart2DetailHistEntity;
import com.abm.mainet.common.domain.Department;
import com.abm.mainet.common.exception.FrameworkException;
import com.abm.mainet.common.master.dto.ContractDetailDTO;
import com.abm.mainet.common.master.dto.ContractMastDTO;
import com.abm.mainet.common.master.dto.ContractPart2DetailDTO;
import com.abm.mainet.common.master.repository.ContractAgreementRepository;
import com.abm.mainet.common.master.service.TbDepartmentService;
import com.abm.mainet.common.repository.ContractInstalmentDetailHistRepository;
import com.abm.mainet.common.repository.ContractPart2DetailHistRepository;
import com.abm.mainet.rnl.domain.RLBillMaster;
import com.abm.mainet.rnl.domain.RLBillMasterHist;
import com.abm.mainet.rnl.repository.RLBillMasterHistRepository;
import com.abm.mainet.rnl.repository.TransferOfLeaseRepository;


/**
 * @author divya.marshettiwar
 *
 */
@Service
public class TransferOfLeaseServiceImpl implements ITransferOfLeaseService {
	
	@Autowired
    private TbDepartmentService iTbDepartmentService;
	
	@Autowired
	private TransferOfLeaseRepository transferOfLeaseRepository;
	
	@Autowired
	private ContractInstalmentDetailHistRepository contractInstalmentDetailHistRepository;
	
	@Autowired
	private RLBillMasterHistRepository rLBillMasterHistRepository;
	
	@Autowired
	private ContractPart2DetailHistRepository ContractPart2DetailHistRepository;
	
	@Autowired
	private ContractAgreementRepository contractAgreementRepository;

	@Override
	public ContractMastDTO searchContractDetails(String contNo, Long orgId) {
		ContractMastDTO contractDto = new ContractMastDTO();

		final Department dept = iTbDepartmentService.findDepartmentByCode(MainetConstants.DEPT_SHORT_NAME.RNL);
		Long contDept = dept.getDpDeptid();
		DecimalFormat decfor = new DecimalFormat("0.00"); 
		try {
			ContractMastEntity contractentity = transferOfLeaseRepository.getContractDetailsByContractNo(contNo, contDept, orgId);
			if (contractentity != null) {
				List<ContractDetailEntity> contractDetailEntity = transferOfLeaseRepository.getContractDetailsListData(contractentity.getContId(), orgId);
				List<ContractPart2DetailEntity> contractPart2DetailEntity = transferOfLeaseRepository.getContractPart2DetailsListData(contractentity.getContId(), orgId);
				Double totalBalanceAmount = transferOfLeaseRepository.getBalanceAmount(contractentity.getContId(), orgId);
				if (contractentity != null) {
					BeanUtils.copyProperties(contractentity, contractDto);
					
					if(totalBalanceAmount != null) {
						contractDto.setTotalBalanceAmount(decfor.format(totalBalanceAmount));
					}
					
					if (contractDetailEntity != null) {
						List<ContractDetailDTO> contractDetailsDto = new ArrayList<ContractDetailDTO>();
						contractDetailEntity.forEach(entity -> {
							ContractDetailDTO dto = new ContractDetailDTO();
							if (entity != null) {
								BeanUtils.copyProperties(entity, dto);
								dto.setAgreementAmount(decfor.format(entity.getContAmount()));
							}
							contractDetailsDto.add(dto);
						});
						contractDto.setContractDetailList(contractDetailsDto);
					}

					if (contractPart2DetailEntity != null) {
						List<ContractPart2DetailDTO> contractPart2DetailDTO = new ArrayList<ContractPart2DetailDTO>();
						contractPart2DetailEntity.forEach(entity -> {
							ContractPart2DetailDTO dto = new ContractPart2DetailDTO();
							if (entity != null) {
								BeanUtils.copyProperties(entity, dto);
								dto.setContp2Name(contractAgreementRepository.getVenderNameOnVenderId(orgId,
										dto.getVmVendorid())); 
							}
							contractPart2DetailDTO.add(dto);
						});
						contractDto.setContractPart2List(contractPart2DetailDTO);
					}
				}
			}
		} catch (Exception exception) {
			throw new FrameworkException("Error Occured While fetching the data", exception);
		}
		return contractDto;
	}
	
	@Override
	@Transactional
	public void updateTransferOfLeaseData(@RequestBody final ContractMastDTO contractMasterDto,Double appreciationRate, Long vendorList) {
		
		DecimalFormat decfor = new DecimalFormat("0.00"); 
		Date currentDate = new Date();
		Double currentAgreementAmount = contractMasterDto.getContractDetailList().get(0).getContAmount();
		Double appreciationRateAmount = (currentAgreementAmount * appreciationRate) / 100;
		Double appreciationAmount = (currentAgreementAmount + appreciationRateAmount) / contractMasterDto.getContractDetailList().get(0).getContInstallmentPeriod();
		
		try {
			//For ContractInstalmentDetail		
			List<ContractInstalmentDetailEntity> contractInstalmentDetailEntity = transferOfLeaseRepository
					.findContractInstalmentDetailByOrgIdAndContractId(currentDate, contractMasterDto.getContId(), contractMasterDto.getOrgId());
			
			if(contractInstalmentDetailEntity != null) {
				List<ContractInstalmentDetailHistEntity> contractInstalmentDetailHistEntity = new ArrayList<ContractInstalmentDetailHistEntity>();
				contractInstalmentDetailEntity.forEach(detailsentity -> {
					ContractInstalmentDetailHistEntity historyEntity = new ContractInstalmentDetailHistEntity();
					if(detailsentity != null) {
						BeanUtils.copyProperties(detailsentity, historyEntity);
						historyEntity.sethStatus("U");
					}
					contractInstalmentDetailHistEntity.add(historyEntity);
				});
				contractInstalmentDetailHistRepository.save(contractInstalmentDetailHistEntity);
			}
			
			transferOfLeaseRepository.updateContractInstallmentDetails(appreciationAmount, currentDate,
					contractMasterDto.getContId(), contractMasterDto.getOrgId());
			
			//For RLBillMaster
			List<RLBillMaster> rLBillMasterEntity = transferOfLeaseRepository
					.findRLBillMasterByOrgIdAndContractId(currentDate, contractMasterDto.getContId(), contractMasterDto.getOrgId());
			
			if(!rLBillMasterEntity.isEmpty()) {
				List<RLBillMasterHist> rLBillMasterHist = new ArrayList<RLBillMasterHist>();
				rLBillMasterEntity.forEach(rlentity -> {
					RLBillMasterHist historyEntity = new RLBillMasterHist();
					if(rlentity != null) {
						BeanUtils.copyProperties(rlentity, historyEntity);
						historyEntity.sethStatus("U");
					}
					rLBillMasterHist.add(historyEntity);
				});
				rLBillMasterHistRepository.save(rLBillMasterHist);
			}
			
			transferOfLeaseRepository.updateRLBillMaster(appreciationAmount, currentDate,
					contractMasterDto.getContId(), contractMasterDto.getOrgId());
			
			Double totalBalanceAmount = transferOfLeaseRepository.getBalanceAmount(contractMasterDto.getContId(), contractMasterDto.getOrgId());
			if(totalBalanceAmount != null) {
				contractMasterDto.setContBalanceAmt(Double.parseDouble(decfor.format(totalBalanceAmount)));
			}
			
			//For ContractPart2Details
			List<ContractPart2DetailEntity> contractPart2DetailEntity = transferOfLeaseRepository
					.findContractPart2DetailEntityByOrgIdAndContractId(contractMasterDto.getContId(), contractMasterDto.getOrgId());
			
			if(!contractPart2DetailEntity.isEmpty()) {
				List<ContractPart2DetailHistEntity> contractPart2DetailHistEntity = new ArrayList<ContractPart2DetailHistEntity>();
				contractPart2DetailEntity.forEach(part2entity -> {
					ContractPart2DetailHistEntity historyEntity = new ContractPart2DetailHistEntity();
					if(part2entity != null) {
						BeanUtils.copyProperties(part2entity, historyEntity);
						historyEntity.sethStatus("U");
					}
					contractPart2DetailHistEntity.add(historyEntity);
				});
				ContractPart2DetailHistRepository.save(contractPart2DetailHistEntity);
			}
			
			transferOfLeaseRepository.updateContractPart2Details(vendorList, 
					contractMasterDto.getContId(), contractMasterDto.getOrgId());
			
		}catch (Exception exception) {
			throw new FrameworkException("Error Occured While updating the data", exception);
		}
	}
}
