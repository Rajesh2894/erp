package com.abm.mainet.workManagement.service;

import java.util.Date;
import java.util.List;

import javax.jws.WebService;
import javax.ws.rs.Consumes;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import org.springframework.web.bind.annotation.RequestBody;

import com.abm.mainet.common.integration.dto.DocumentDetailsVO;
import com.abm.mainet.common.integration.dto.RequestDTO;
import com.abm.mainet.workManagement.dto.InspectionRaBillDto;
import com.abm.mainet.workManagement.dto.VigilanceDto;

@WebService
@Consumes({ MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML })
@Produces(MediaType.APPLICATION_JSON)
public interface VigilanceService {

    /**
     * get filter vigilance list by refType and/or refNumber and/or memoDate and/or inspectionDate and/or orgId
     * 
     * @param refType
     * @param refNumber
     * @param memoDate
     * @param inspectionDate
     * @param orgId
     * @return
     */
    List<VigilanceDto> getFilterVigilanceList(String refType, String refNumber, Date memoDate, Date inspectionDate,
            Long orgId);

    /**
     * get vigilance Data by vigilance Id
     * 
     * @param vigilanceId
     * @return
     */
    VigilanceDto getVigilanceById(Long vigilanceId);

    /**
     * used to save Vigilance Entity and Uploaded Documents
     * 
     * @param vigilanceDto
     * @param requestDTO
     * @param attachments
     */
    void saveVigilance(VigilanceDto vigilanceDto, RequestDTO requestDTO, List<DocumentDetailsVO> attachments);

    /**
     * used to update Vigilance Entity and Uploaded Documents
     * 
     * @param vigilanceDto
     * @param attachments
     * @param requestDTO
     * @param recipientDetails
     * @param removeFileId
     */
    void updateVigilance(VigilanceDto vigilanceDto, List<DocumentDetailsVO> attachments, RequestDTO requestDTO,
            List<Long> removeFileId);

    /**
     * get Inspection Data By ReferenceNo
     * @param referenceNo
     * @return list of inspection details(Vigilance)
     */
    public List<VigilanceDto> getVigilanceByReferenceNo(String referenceNo, Long orgid);

    /**
     * 
     * @param inspectionRa
     * @return
     */
    InspectionRaBillDto saveInspection(@RequestBody InspectionRaBillDto inspectionRa);


	List<VigilanceDto> getVigilanceDetByProjIdAndWorkId(Long projectId, Long workId, Long orgid);

}
