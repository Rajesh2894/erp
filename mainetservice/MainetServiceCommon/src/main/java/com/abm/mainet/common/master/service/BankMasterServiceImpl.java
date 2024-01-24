package com.abm.mainet.common.master.service;

import java.util.ArrayList;
import java.util.GregorianCalendar;
import java.util.List;

import javax.annotation.Resource;
import javax.jws.WebMethod;
import javax.jws.WebService;
import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.xml.datatype.DatatypeConfigurationException;
import javax.xml.datatype.DatatypeFactory;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.abm.mainet.common.constant.MainetConstants;
import com.abm.mainet.common.domain.BankMasterEntity;
import com.abm.mainet.common.exception.FrameworkException;
import com.abm.mainet.common.integration.acccount.hrms.bankmaster.soap.jaxws.client.WSBankMaster;
import com.abm.mainet.common.integration.acccount.service.BankMasterSoapWSProvisionService;
import com.abm.mainet.common.master.dao.BankMasterDao;
import com.abm.mainet.common.master.dto.BankMasterDTO;
import com.abm.mainet.common.master.dto.BankMasterUploadDTO;
import com.abm.mainet.common.master.mapper.BankMasterServiceMapper;
import com.abm.mainet.common.master.repository.BankMasterRepository;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

@Produces({ "application/xml", "application/json" })
@WebService(endpointInterface = "com.abm.mainet.common.master.service.BankMasterService")
@Api(value = "/bankMasterService")
@Path("/bankMasterService")
@Service
public class BankMasterServiceImpl implements BankMasterService {

    @Resource
    private BankMasterRepository bankMasterRepository;

    @Resource
    private BankMasterServiceMapper bankMasterServiceMapper;

    @Resource
    private BankMasterDao bankMasterDao;
    @Resource
    private BankMasterSoapWSProvisionService bankMasterSoapWSProvisionService;

    private static final String DATE_COVERT_EXCEPTION = "Exception while converting date to XMLGregorianCalendar :";

    @Override
    @Transactional
    public BankMasterEntity isCombinationIfscCodeExists(final String ifsc) {
        return bankMasterDao.isCombinationIfscCodeExists(ifsc);
    }

    @Override
    @Transactional
    public BankMasterEntity isCombinationBranchNameExists(final String bank, final String branch) {
        return bankMasterDao.isCombinationBranchNameExists(bank, branch);
    }

    @Override
    @Transactional
    public BankMasterDTO saveBankMasterFormData(final BankMasterDTO tbBankMaster) {

        final BankMasterEntity tbCustbanksMasEntity = new BankMasterEntity();
        bankMasterServiceMapper.mapBankMasterDTOToBankMasterEntity(tbBankMaster, tbCustbanksMasEntity);
        BankMasterEntity finalEntity = bankMasterRepository.save(tbCustbanksMasEntity);
        if (tbBankMaster.getBankId() == null) {
            insertBankMasterDataIntoPropertyTaxTableByUsingSoapWS(finalEntity);
        } else {
            updateBankMasterDataIntoPropertyTaxTableByUsingSoapWS(finalEntity);
        }
        return tbBankMaster;
    }

    private void insertBankMasterDataIntoPropertyTaxTableByUsingSoapWS(BankMasterEntity finalEntity) {

        try {
            WSBankMaster bankMaster = new WSBankMaster();
            // bankMaster.setModifiedIn("ModifiedIn-713170208");
            bankMaster.setBank(finalEntity.getBank());
            bankMaster.setAddress(finalEntity.getAddress());
            if (finalEntity.getLmodDate() != null) {
                GregorianCalendar cal = new GregorianCalendar();
                cal.setTime(finalEntity.getLmodDate());
                bankMaster.setCreatedAt(DatatypeFactory.newInstance().newXMLGregorianCalendar(cal));
            }
            // bankMaster.setMetadata("Metadata1038438358");
            bankMaster.setSheetName(finalEntity.getBankId().toString());
            if (finalEntity.getUpdatedBy() != null) {
                bankMaster.setModifiedBy(finalEntity.getUpdatedBy().toString());
            }
            if (finalEntity.getUpdatedDate() != null) {
                GregorianCalendar cal = new GregorianCalendar();
                cal.setTime(finalEntity.getUpdatedDate());
                bankMaster.setModifiedAt(DatatypeFactory.newInstance().newXMLGregorianCalendar(cal));
            }
            // bankMaster.setTenant("Tenant534976351");
            bankMaster.setBranch(finalEntity.getBranch());
            // bankMaster.setStatus("Status-1277314139");
            bankMaster.setCreatedBy(finalEntity.getCreatedBy().toString());
            // bankMaster.setSheetId("SheetId-1289257579");
            bankMaster.setBankStatus(finalEntity.getBankStatus());
            bankMaster.setCity(finalEntity.getCity());
            // bankMaster.setCaption("Caption1561290271");
            if (finalEntity.getContact() != null) {
                bankMaster.setContact(finalEntity.getContact());
            }
            bankMaster.setMICR(finalEntity.getMicr());
            // bankMaster.setAssignedTo("AssignedTo-11088973");
            bankMaster.setIFSCCode(finalEntity.getIfsc());
            bankMaster.setState(finalEntity.getState());
            // bankMaster.setProcessInstance("ProcessInstance-303912109");
            bankMaster.setBankId(finalEntity.getBankId());
            bankMaster.setDistrict(finalEntity.getDistrict());
            // bankMaster.setSheetMetadataName("SheetMetadataName-661954480");
            bankMasterSoapWSProvisionService.createBankMasterHead(bankMaster);

        } catch (DatatypeConfigurationException ex) {
            throw new FrameworkException(DATE_COVERT_EXCEPTION + ex);
        }
    }

    private void updateBankMasterDataIntoPropertyTaxTableByUsingSoapWS(BankMasterEntity finalEntity) {

        try {
            WSBankMaster bankMaster = new WSBankMaster();
            // bankMaster.setModifiedIn("ModifiedIn-713170208");
            bankMaster.setBank(finalEntity.getBank());
            bankMaster.setAddress(finalEntity.getAddress());
            if (finalEntity.getLmodDate() != null) {
                GregorianCalendar cal = new GregorianCalendar();
                cal.setTime(finalEntity.getLmodDate());
                bankMaster.setCreatedAt(DatatypeFactory.newInstance().newXMLGregorianCalendar(cal));
            }
            // bankMaster.setMetadata("Metadata1038438358");
            bankMaster.setSheetName(finalEntity.getBankId().toString());
            if (finalEntity.getUpdatedBy() != null) {
                bankMaster.setModifiedBy(finalEntity.getUpdatedBy().toString());
            }
            if (finalEntity.getUpdatedDate() != null) {
                GregorianCalendar cal = new GregorianCalendar();
                cal.setTime(finalEntity.getUpdatedDate());
                bankMaster.setModifiedAt(DatatypeFactory.newInstance().newXMLGregorianCalendar(cal));
            }
            // bankMaster.setTenant("Tenant534976351");
            bankMaster.setBranch(finalEntity.getBranch());
            // bankMaster.setStatus("Status-1277314139");
            bankMaster.setCreatedBy(finalEntity.getCreatedBy().toString());
            // bankMaster.setSheetId("SheetId-1289257579");
            bankMaster.setBankStatus(finalEntity.getBankStatus());
            bankMaster.setCity(finalEntity.getCity());
            // bankMaster.setCaption("Caption1561290271");
            if (finalEntity.getContact() != null) {
                bankMaster.setContact(finalEntity.getContact());
            }
            bankMaster.setMICR(finalEntity.getMicr());
            // bankMaster.setAssignedTo("AssignedTo-11088973");
            bankMaster.setIFSCCode(finalEntity.getIfsc());
            bankMaster.setState(finalEntity.getState());
            // bankMaster.setProcessInstance("ProcessInstance-303912109");
            bankMaster.setBankId(finalEntity.getBankId());
            bankMaster.setDistrict(finalEntity.getDistrict());
            // bankMaster.setSheetMetadataName("SheetMetadataName-661954480");
            bankMasterSoapWSProvisionService.updateBankMasterHead(bankMaster);

        } catch (DatatypeConfigurationException ex) {
            throw new FrameworkException(DATE_COVERT_EXCEPTION + ex);
        }
    }

    @Override
    @Transactional
    public List<BankMasterDTO> findByAllGridData() {
        final List<BankMasterEntity> entities = bankMasterRepository.findByAllGridData();
        final List<BankMasterDTO> dto = new ArrayList<>();
        for (final BankMasterEntity bankMasterEntity : entities) {
            dto.add(bankMasterServiceMapper.mapBankMasterEntityToBankMasterDTO(bankMasterEntity));
        }
        return dto;
    }

    @Override
    @Transactional
    public List<BankMasterDTO> findByAllGridSearchData(final String bank, final String branch, final String ifsc,
            final String micr, final String city) {
        final List<BankMasterEntity> entities = bankMasterDao.findByAllGridSearchData(bank, branch, ifsc, micr, city);
        final List<BankMasterDTO> dto = new ArrayList<>();
        for (final BankMasterEntity bankMasterEntity : entities) {
            dto.add(bankMasterServiceMapper.mapBankMasterEntityToBankMasterDTO(bankMasterEntity));
        }
        return dto;
    }

    @Override
    @Transactional
    public BankMasterDTO getDetailsUsingBankId(BankMasterDTO bankMasterDTO) {

        final Long bankId = bankMasterDTO.getBankId();
        final BankMasterEntity bankMasterEntity = bankMasterRepository.findOne(bankId);
        bankMasterDTO = bankMasterServiceMapper.mapBankMasterEntityToBankMasterDTO(bankMasterEntity);
        return bankMasterDTO;
    }

    /*
     * (non-Javadoc)
     * @see com.abm.mainetservice.web.common.service.ITransactionMasterService#getBankList()
     */

    public List<BankMasterEntity> getBankList() {
        return bankMasterDao.getBankList();
    }

    @Override
    @Transactional
    public List<Object[]> getChequeDDNoBankAccountNames(final Long bankAccountId) {
        return bankMasterDao.getChequeDDNoBankAccountNames(bankAccountId);
    }

    @Override
    public BankMasterEntity isDuplicateMICR(final String micr) {
        return bankMasterDao.isDuplicateMICR(micr);
    }

    @Override
    public void saveBankMasterExcelData(BankMasterUploadDTO bankMasterUploadDto, Long orgId, int langId) {

        BankMasterEntity masterEntity = new BankMasterEntity();
        masterEntity.setCreatedBy(bankMasterUploadDto.getUserId());
        masterEntity.setLmodDate(bankMasterUploadDto.getLmoddate());
        masterEntity.setLangId(bankMasterUploadDto.getLangId());
        masterEntity.setLgIpMac(bankMasterUploadDto.getLgIpMac());
        masterEntity.setBank(bankMasterUploadDto.getBankName());
        masterEntity.setIfsc(bankMasterUploadDto.getIfscCode());
        if (bankMasterUploadDto.getMicrCode() != null)
        masterEntity.setMicr(bankMasterUploadDto.getMicrCode().toString());
        masterEntity.setBranch(bankMasterUploadDto.getBankBranch());
        masterEntity.setAddress(bankMasterUploadDto.getBranchAddr());
        masterEntity.setContact(bankMasterUploadDto.getContDet());
        masterEntity.setCity(bankMasterUploadDto.getCity());
        masterEntity.setDistrict(bankMasterUploadDto.getDistrict());
        masterEntity.setState(bankMasterUploadDto.getState());
        // masterEntity.setBankStatus(bankMasterUploadDto.getStatus());
        masterEntity.setBankStatus(MainetConstants.STATUS.ACTIVE);
        bankMasterRepository.save(masterEntity);

    }

    @Override
    @POST
    @WebMethod
    @Consumes("application/json")
    @Path("/getBankList")
    @Transactional(readOnly = true)
    public List<BankMasterDTO> getBankListDto() {
        List<BankMasterEntity> entity = bankMasterDao.getBankList();
        List<BankMasterDTO> dtoList = new ArrayList<BankMasterDTO>();
        entity.forEach(k -> {
            dtoList.add(bankMasterServiceMapper.mapBankMasterEntityToBankMasterDTO(k));
        });
        return dtoList;
    }

	@Override
	public List<BankMasterEntity> getBankListByName(String bankName) {
		// TODO Auto-generated method stub
		  return bankMasterDao.getBankListByName(bankName);
	}

	@Override
	public List<Object[]> getBankListGroupBy() {
		// TODO Auto-generated method stub
		return bankMasterDao.getBankListGroupBy();
	}

	@Override
	public String getBankBranchName(Long bankId) {
		// TODO Auto-generated method stub
		return bankMasterDao.getBankBranch(bankId);
	}

	/* (non-Javadoc)
	 * @see com.abm.mainet.common.master.service.BankMasterService#getBankById(java.lang.String)
	 */
	@Override
	public String getBankById(Long bankId) {
		// TODO Auto-generated method stub
		return bankMasterRepository.getBankById(bankId);
	}

	/* (non-Javadoc)
	 * @see com.abm.mainet.common.master.service.BankMasterService#getIfscCodeById(java.lang.Long)
	 */
	@Override
	public String getIfscCodeById(Long bankId) {
		return bankMasterRepository.getIfscCodeById(bankId);
	}
	
	public List<String> getBankDetails() {
        return bankMasterRepository.getBankDetails();
    }

}
