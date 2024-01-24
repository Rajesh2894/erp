package com.abm.mainet.common.service;

import java.io.Serializable;
import java.util.List;

import com.abm.mainet.common.dto.CheckListModel;
import com.abm.mainet.common.dto.DocumentDetailsVO;
import com.abm.mainet.integration.ws.dto.WSRequestDTO;
import com.abm.mainet.integration.ws.dto.WSResponseDTO;

public interface ICommonBRMSService extends Serializable {

    /**
     * @return
     */
    // WSResponseDTO initializeModel();

    WSResponseDTO initializeModel(WSRequestDTO requestDTO);

    /**
     * @param dto
     * @return
     */
    List<DocumentDetailsVO> getChecklist(CheckListModel checklistModel);
    
    List<DocumentDetailsVO> getChecklistDocument(String application , Long orgId,String checkListFlag);

}
