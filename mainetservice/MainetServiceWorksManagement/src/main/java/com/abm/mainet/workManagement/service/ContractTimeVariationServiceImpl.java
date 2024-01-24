package com.abm.mainet.workManagement.service;

import java.util.List;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.abm.mainet.common.constant.MainetConstants;
import com.abm.mainet.common.domain.ContractDetailEntity;
import com.abm.mainet.common.domain.ContractMastEntity;
import com.abm.mainet.common.integration.dms.dao.IAttachDocsDao;
import com.abm.mainet.common.master.dao.IContractAgreementDao;
import com.abm.mainet.common.master.dto.ContractDetailDTO;
import com.abm.mainet.common.utility.ApplicationContextProvider;

/**
 * @author Saiprasad.Vengurlekar
 *
 */
@Service
public class ContractTimeVariationServiceImpl implements ContractTimeVariationService {

    @Autowired
    private IContractAgreementDao iContractAgreementDao;

    @Override
    @Transactional
    public void updateContractTimeVariation(ContractDetailDTO contractDetDTO) {
        ContractDetailEntity contractDetailEntity = new ContractDetailEntity();
        iContractAgreementDao.updateContractDetailActiveFlag(contractDetDTO.getContdId(),
                contractDetDTO.getCreatedBy());
        BeanUtils.copyProperties(contractDetDTO, contractDetailEntity);
        ContractMastEntity mastEntity = new ContractMastEntity();
        mastEntity.setContId(contractDetDTO.getContractId().longValue());
        contractDetailEntity.setContId(mastEntity);
        /*
         * As Per Requirements Need To insert A New Entry in Contract Detail After Updating Time Variation Period
         */
        contractDetailEntity.setContdId(0);
        iContractAgreementDao.saveUpdateContractDetailEntity(contractDetailEntity);
    }

    @Override
    @Transactional
    public void deleteVariationFileById(List<Long> variationFileById, Long empId) {

        ApplicationContextProvider.getApplicationContext().getBean(IAttachDocsDao.class).updateRecord(variationFileById, empId,
                MainetConstants.FlagD);

    }

}
