package com.abm.mainet.cfc.checklist.service;

import java.util.List;

import com.abm.mainet.cfc.checklist.dto.TbCfcChecklistMstDto;
import com.abm.mainet.cfc.checklist.dto.TbDocumentGroup;
import com.abm.mainet.common.master.dto.ServiceChecklistDTO;

/**
 * @author hiren.poriya
 *
 */
public interface TbDocumentMstService {

    /**
     *
     * @param orgId
     * @param groupId
     * @return
     */
    List<TbDocumentGroup> findAllByGroupIdIdOrgId(Long orgId, Long groupId);

    /**
     * @param cfcChecklistMstDto
     * @param tbDocGrouptList
     * @return
     */
    void saveDocumentlist(TbCfcChecklistMstDto cfcChecklistMstDto, List<TbDocumentGroup> tbDocGrouptList);

    /**
     * @param cfcChecklistMstDto
     * @param tbDocGrouptList
     * @param DocumentTemp
     * @return
     */
    void updateDocumentList(TbCfcChecklistMstDto cfcChecklistMstDto, List<TbDocumentGroup> tbDocGrouptList,
            List<TbDocumentGroup> DocumentTemp);

    List<ServiceChecklistDTO> getSearchDocumentData(Long orgId, Long groupId);

}
