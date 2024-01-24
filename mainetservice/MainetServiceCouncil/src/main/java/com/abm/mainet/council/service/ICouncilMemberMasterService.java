package com.abm.mainet.council.service;

import java.util.List;

import com.abm.mainet.common.integration.dms.dto.FileUploadDTO;
import com.abm.mainet.common.integration.dto.DocumentDetailsVO;
import com.abm.mainet.council.dto.CouncilMemberMasterDto;

public interface ICouncilMemberMasterService {

    /**
     * method is used for Save Update Council Member Master
     * 
     * @param model
     * @param attachmentList
     * @param uploadDTO
     * @param deleteFileId
     * @return boolean
     */
    public boolean saveCouncil(CouncilMemberMasterDto model, List<DocumentDetailsVO> attachmentList,
            FileUploadDTO uploadDTO, Long deleteFileId);

    /**
     * method is used for Search Council Member
     * 
     * @param couMemName
     * @param designation
     * @param couPartyAffilation
     * @param orgid
     * @param couEleWZId1
     * @param couEleWZId2
     * @return List<CouncilMemberMasterDto>
     */
    public List<CouncilMemberMasterDto> searchCouncilMasterData(String couMemName, Long designation, Long couPartyAffilation,
            Long orgid, Long couEleWZId1, Long couEleWZId2, int langId);

    /**
     * method is used for get Council Member Data by couId
     * 
     * @param couId
     * @return CouncilMemberMasterDto
     */
    CouncilMemberMasterDto getCouncilMemberMasterByCouId(Long couId);

    /**
     * method is used for get all Council Member Data
     * 
     * @return List<CouncilMemberMasterDto>
     */
    public List<CouncilMemberMasterDto> fetchAll(Long orgid);

}
