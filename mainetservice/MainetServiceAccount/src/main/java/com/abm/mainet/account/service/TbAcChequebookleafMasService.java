package com.abm.mainet.account.service;

import java.util.List;
import java.util.Map;

import com.abm.mainet.account.domain.TbAcChequebookleafDetEntity;
import com.abm.mainet.account.dto.TbAcChequebookleafMas;

/**
 * Business Service Interface for entity TbAcChequebookleafMas.
 */
public interface TbAcChequebookleafMasService {

    TbAcChequebookleafMas findById(Long chequebookId);

    List<TbAcChequebookleafMas> findAll();

    TbAcChequebookleafMas update(TbAcChequebookleafMas entity);

    TbAcChequebookleafMas create(TbAcChequebookleafMas entity);

    void delete(Long chequebookId);

    List<TbAcChequebookleafMas> getChequeData(Long bmBankid, Long orgid);

    Long getChequeBookCount(Long bmBankid, Long orgid, String fromChequeNo, String toChequeNo);

    List<TbAcChequebookleafDetEntity> getIssuedChequeNumbers(Long bankAcId, Long statusId);

    Map<Long, String> getChequeRangeByBankAccountId(Long bankAccountId, Long orgId);

    List<Object[]> getChequeUtilizationDetails(Long chequeBookId, Long orgId);

    List<TbAcChequebookleafDetEntity> getChequeNumbers(Long bankAcId);

}
