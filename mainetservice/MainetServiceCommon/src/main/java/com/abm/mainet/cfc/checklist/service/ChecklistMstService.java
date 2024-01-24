package com.abm.mainet.cfc.checklist.service;

import com.abm.mainet.common.integration.dms.client.FileNetApplicationClient;

/**
 * Business Service Interface for entity ChecklistMstService.
 */
public interface ChecklistMstService {

    /**
     *
     * @param orgId
     * @param smServiceId
     * @return
     */
    /*
     * List<ChecklistMst> findAllDataByServiceIdOrgId(Long orgId, Long smServiceId);
     *//**
        * @param cfcChecklistMstDto
        * @param checklistMstList
        * @return
        */
    /*
     * void saveChecklistData(TbCfcChecklistMstDto cfcChecklistMstDto, List<ChecklistMst> checklistMstList);
     *//**
        * @param cfcChecklistMstDto
        * @param checklistMstList
        * @param checklistMstTemp
        * @return
        *//*
           * void updateChecklistData(TbCfcChecklistMstDto cfcChecklistMstDto, List<ChecklistMst> checklistMstList,
           * List<ChecklistMst> checklistTempList);
           */

    /**
     * @param directoryTree
     * @param deptid
     * @param serviceId
     * @param fileNetApplicationClient
     * @param appid
     * @param listOfChkboxStatus
     * @return
     */
    boolean uploadForChecklistVerification(String directoryTree, Long deptid,
            long serviceId, FileNetApplicationClient fileNetApplicationClient,
            Long appid, String[] listOfChkboxStatus);

}
