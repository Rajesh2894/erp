package com.abm.mainet.account.service;

import java.text.ParseException;
import java.util.Date;
import java.util.List;

import com.abm.mainet.account.dto.ReappropriationOfBudgetAuthorizationDTO;
import com.abm.mainet.common.domain.Organisation;

public interface ReappropriationOfBudgetAuthorizationService {

    List<ReappropriationOfBudgetAuthorizationDTO> findByAuthorizationGridData(Date frmDate, Date todate, Long cpdBugtypeId,
            String status, String budgIdentifyFlag, Long orgId);

    List<ReappropriationOfBudgetAuthorizationDTO> findAllGridData(String budgIdentifyFlag, Long orgId);

    ReappropriationOfBudgetAuthorizationDTO getDetailsUsingBudgetReappAuthorizationId(
            ReappropriationOfBudgetAuthorizationDTO tbAcBudgetReappAuthorization, int LanguageId, Organisation Organisation);

    ReappropriationOfBudgetAuthorizationDTO saveBudgetReappAuthorizationFormData(
            ReappropriationOfBudgetAuthorizationDTO tbAcBudgetReappAuthorization, int LanguageId, Organisation Organisation)
            throws ParseException;

    ReappropriationOfBudgetAuthorizationDTO populateBudgetReappropriationWorkFlowData(
            ReappropriationOfBudgetAuthorizationDTO tbAcBudgetReappOfAuthorization, int languageId,
            Organisation organisation, long actualTaskId);

    void forUpdateRevisedEstmtDataInBudgetExpRevTable(
            ReappropriationOfBudgetAuthorizationDTO tbAcBudgetReappOfAuthorizationCreated, int languageId,
            Organisation organisation) throws ParseException;

}
