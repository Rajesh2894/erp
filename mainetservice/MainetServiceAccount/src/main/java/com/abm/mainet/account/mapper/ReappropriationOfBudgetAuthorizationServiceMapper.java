
package com.abm.mainet.account.mapper;

import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;
import org.springframework.stereotype.Component;

import com.abm.mainet.account.domain.AccountBudgetReappropriationMasterEntity;
import com.abm.mainet.account.dto.ReappropriationOfBudgetAuthorizationDTO;
import com.abm.mainet.common.domain.Employee;
import com.abm.mainet.common.integration.acccount.domain.AccountBudgetCodeEntity;
import com.abm.mainet.common.utility.AbstractServiceMapper;

/**
 * Mapping between entity beans and display beans.
 */
@Component
public class ReappropriationOfBudgetAuthorizationServiceMapper extends AbstractServiceMapper {

    /**
     * ModelMapper : bean to bean mapping library.
     */
    private ModelMapper modelMapper;

    /**
     * Constructor.
     */
    public ReappropriationOfBudgetAuthorizationServiceMapper() {
        modelMapper = new ModelMapper();
        modelMapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);
    }

    /**
     * Mapping from 'AccountBudgetReappropriationMasterBeanEntity' to 'AccountBudgetReappropriationMasterBean'
     * @param accountBudgetReappropriationMasterEntity
     */
    public ReappropriationOfBudgetAuthorizationDTO mapAccountBudgetReappropriationMasterBeanEntityToReappropriationOfBudgetAuthorizationDTO(
            final AccountBudgetReappropriationMasterEntity accountBudgetReappropriationMasterEntity) {
        if (accountBudgetReappropriationMasterEntity == null) {
            return null;
        }

        // --- Generic mapping
        final ReappropriationOfBudgetAuthorizationDTO reappropriationOfBudgetAuthorizationDTO = map(
                accountBudgetReappropriationMasterEntity, ReappropriationOfBudgetAuthorizationDTO.class);

        if (accountBudgetReappropriationMasterEntity.getTbAcBudgetCodeMaster() != null) {
            reappropriationOfBudgetAuthorizationDTO
                    .setPrBudgetCodeid(accountBudgetReappropriationMasterEntity.getTbAcBudgetCodeMaster().getPrBudgetCodeid());
        }

        // --- Link mapping ( link to Employee )
        if (accountBudgetReappropriationMasterEntity.getEmployee() != null) {
            reappropriationOfBudgetAuthorizationDTO
                    .setApprovedBy(accountBudgetReappropriationMasterEntity.getEmployee().getEmpId());
        }

        if (accountBudgetReappropriationMasterEntity.getLmoddate() != null) {
            reappropriationOfBudgetAuthorizationDTO
                    .setPaEntrydate(accountBudgetReappropriationMasterEntity.getLmoddate());
        }

        return reappropriationOfBudgetAuthorizationDTO;
    }

    /**
     * Mapping from 'AccountBudgetReappropriationMasterBean' to 'AccountBudgetReappropriationMasterBeanEntity'
     * @param accountBudgetReappropriationMasterBean
     * @param accountBudgetReappropriationMasterEntity
     */
    public void mapReappropriationOfBudgetAuthorizationDTOToAccountBudgetReappropriationMasterEntity(
            final ReappropriationOfBudgetAuthorizationDTO reappropriationOfBudgetAuthorizationDTO,
            final AccountBudgetReappropriationMasterEntity accountBudgetReappropriationMasterEntity) {
        if (reappropriationOfBudgetAuthorizationDTO == null) {
            return;
        }

        // --- Generic mapping
        map(reappropriationOfBudgetAuthorizationDTO, accountBudgetReappropriationMasterEntity);

        if (reappropriationOfBudgetAuthorizationDTO.getDpDeptid() != null) {
            accountBudgetReappropriationMasterEntity.setDepartment(reappropriationOfBudgetAuthorizationDTO.getDpDeptid());
        }

        // --- Link mapping ( link : accountBudgetReappropriationMasterBean )
        if (hasLinkToEmployee(reappropriationOfBudgetAuthorizationDTO)) {
            final Employee employee = new Employee();
            employee.setEmpId(reappropriationOfBudgetAuthorizationDTO.getApprovedBy());
            if (employee.getEmpId() == null) {

            }
            accountBudgetReappropriationMasterEntity.setEmployee(employee);
        } else {
            accountBudgetReappropriationMasterEntity.setEmployee(null);
        }

        if (hasLinkToTbAcBudgetCodeMaster(reappropriationOfBudgetAuthorizationDTO)) {
            final AccountBudgetCodeEntity tbAcBudgetCodeMaster = new AccountBudgetCodeEntity();
            if (reappropriationOfBudgetAuthorizationDTO.getPrBudgetCodeid() == null) {
            }
            tbAcBudgetCodeMaster.setprBudgetCodeid(reappropriationOfBudgetAuthorizationDTO.getPrBudgetCodeid().longValue());

            accountBudgetReappropriationMasterEntity.setTbAcBudgetCodeMaster(tbAcBudgetCodeMaster);
        } else {
            accountBudgetReappropriationMasterEntity.setTbAcBudgetCodeMaster(null);
        }

    }

    /**
     * Verify that Employee id is valid.
     * @param Employee Employee
     * @return boolean
     */
    private boolean hasLinkToEmployee(final ReappropriationOfBudgetAuthorizationDTO reappropriationOfBudgetAuthorizationDTO) {
        if (reappropriationOfBudgetAuthorizationDTO.getApprovedBy() != null) {
            return true;
        }
        return false;
    }

    private boolean hasLinkToTbAcBudgetCodeMaster(
            final ReappropriationOfBudgetAuthorizationDTO reappropriationOfBudgetAuthorizationDTO) {
        if (reappropriationOfBudgetAuthorizationDTO.getPrBudgetCodeid() != null) {
            return true;
        }
        return false;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected ModelMapper getModelMapper() {
        return modelMapper;
    }

    protected void setModelMapper(final ModelMapper modelMapper) {
        this.modelMapper = modelMapper;
    }

}