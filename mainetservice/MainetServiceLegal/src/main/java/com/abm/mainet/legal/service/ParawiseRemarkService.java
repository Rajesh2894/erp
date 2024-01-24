package com.abm.mainet.legal.service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

import javax.jws.WebMethod;
import javax.jws.WebService;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestBody;

import com.abm.mainet.common.audit.service.AuditService;
import com.abm.mainet.common.constant.MainetConstants;
import com.abm.mainet.common.integration.dms.service.IFileUploadService;
import com.abm.mainet.common.integration.dto.DocumentDetailsVO;
import com.abm.mainet.common.integration.dto.RequestDTO;
import com.abm.mainet.common.utility.ApplicationContextProvider;
import com.abm.mainet.legal.domain.ParawiseRemark;
import com.abm.mainet.legal.domain.ParawiseRemarkHistory;
import com.abm.mainet.legal.dto.ParawiseRemarkDTO;
import com.abm.mainet.legal.repository.ParawiseRemarkRepository;

@Service
@Produces(value = { MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE })
@Consumes(value = { MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE })
@WebService(endpointInterface = "com.abm.mainet.legal.service.IParawiseRemarkService")
@Path(value = "/parawiseremarkservice")
public class ParawiseRemarkService implements IParawiseRemarkService {

    @Autowired
    private ParawiseRemarkRepository parawiseRemarkRepository;

    @Autowired
    private AuditService auditService;

    @Override
    @Transactional
    @POST
    @Path(value = "/save")
    public void saveParawiseRemark(@RequestBody ParawiseRemarkDTO parawiseRemarkDto) {
        ParawiseRemark parawiseRemark = new ParawiseRemark();
        BeanUtils.copyProperties(parawiseRemarkDto, parawiseRemark);

        parawiseRemarkRepository.save(parawiseRemark);

        ParawiseRemarkHistory history = new ParawiseRemarkHistory();
        history.sethStatus(MainetConstants.InsertMode.ADD.getStatus());
        auditService.createHistory(parawiseRemark, history);

    }

    @Override
    @Transactional
    @POST
    @Path(value = "/saveAll")
    public void saveAllParawiseRemark(List<ParawiseRemarkDTO> parawiseRemarkDto, List<Long> removeParawiseIdsList) {
        List<ParawiseRemark> remarks = new ArrayList<>();
        List<Object> historyList = new ArrayList<>();
        parawiseRemarkDto.forEach(dto -> {
            ParawiseRemark parawiseRemark = new ParawiseRemark();
            if(removeParawiseIdsList.contains(dto.getParId())) {
         	   deleteParawiseRemark(dto);
         	  
            }else {
            	BeanUtils.copyProperties(dto, parawiseRemark);
                remarks.add(parawiseRemark);
            }
            
          
        });
        
       

        parawiseRemarkRepository.save(remarks);
        parawiseRemarkDto.forEach(dto -> {
            if (!dto.getAttachments().isEmpty()) {
                RequestDTO requestDTO = new RequestDTO();
                requestDTO.setOrgId(dto.getParentOrgId());
                requestDTO.setUserId(dto.getUpdatedBy() == null ? dto.getCreatedBy():dto.getUpdatedBy());
                requestDTO.setStatus(MainetConstants.FlagA);
                 requestDTO
                        .setIdfId(dto.getCaseId() + MainetConstants.WINDOWS_SLASH + dto.getParId());
                requestDTO.setDepartmentName(MainetConstants.Legal.SHORT_CODE);
                //requestDTO.setUserId(dto.getUpdatedBy());
                saveDocuments(dto.getAttachments(), requestDTO);
            }
        });

        remarks.forEach(entity -> {
            ParawiseRemarkHistory history = new ParawiseRemarkHistory();
            BeanUtils.copyProperties(entity, history);

            if (entity.getParId() == null) {
                history.sethStatus(MainetConstants.InsertMode.ADD.getStatus());
            } else {
                history.sethStatus(MainetConstants.InsertMode.UPDATE.getStatus());
            }

            historyList.add(history);
        });
        auditService.createHistoryForListObj(historyList);

    }

    @Override
    @Transactional
    @POST
    @Path(value = "/update")
    public void updateParawiseRemark(@RequestBody ParawiseRemarkDTO parawiseRemarkDto) {
        ParawiseRemark parawiseRemark = new ParawiseRemark();
        BeanUtils.copyProperties(parawiseRemarkDto, parawiseRemark);

        parawiseRemarkRepository.save(parawiseRemark);

        ParawiseRemarkHistory history = new ParawiseRemarkHistory();
        history.sethStatus(MainetConstants.InsertMode.UPDATE.getStatus());
        auditService.createHistory(parawiseRemark, history);

    }

    @Override
    @Transactional
    @WebMethod(exclude = true)
    public boolean deleteParawiseRemark(ParawiseRemarkDTO parawiseRemarkDto) {
        ParawiseRemark ParawiseRemark = parawiseRemarkRepository.findOne(parawiseRemarkDto.getParId());
        if (ParawiseRemark == null)
            return false;

        parawiseRemarkRepository.delete(parawiseRemarkDto.getParId());

        ParawiseRemarkHistory history = new ParawiseRemarkHistory();
        history.sethStatus(MainetConstants.InsertMode.UPDATE.getStatus());
        auditService.createHistory(ParawiseRemark, history);

        return true;
    }

    @Override
    @WebMethod(exclude = true)
    public List<ParawiseRemarkDTO> getAllParawiseRemark(Long orgid, Long caseId) {
        List<ParawiseRemarkDTO> activeUserListDTOs = StreamSupport
                .stream(parawiseRemarkRepository.findByOrgidAndCaseId(orgid, caseId).spliterator(), false).map(entity -> {
                    ParawiseRemarkDTO dto = new ParawiseRemarkDTO();
                    BeanUtils.copyProperties(entity, dto);
                    return dto;
                })
                .collect(Collectors.toList());
        return activeUserListDTOs;
    }

    @Override
    @WebMethod(exclude = true)
    public List<ParawiseRemarkDTO> getAllParawiseRemark(Long caseId) {
        List<ParawiseRemarkDTO> activeUserListDTOs = StreamSupport
                .stream(parawiseRemarkRepository.findByCaseId(caseId, MainetConstants.N_FLAG).spliterator(), false).map(entity -> {
                    ParawiseRemarkDTO dto = new ParawiseRemarkDTO();
                    BeanUtils.copyProperties(entity, dto);
                    return dto;
                })
                .collect(Collectors.toList());
        return activeUserListDTOs;
    }

    @Transactional(readOnly = true)
    @Override
    @GET
    @Path(value = "/get/{id}")
    public ParawiseRemarkDTO getParawiseRemarkById(@PathParam("id") Long id) {
        ParawiseRemark parawiseRemark = parawiseRemarkRepository.findOne(id);
        ParawiseRemarkDTO parawiseRemarkDto = new ParawiseRemarkDTO();
        BeanUtils.copyProperties(parawiseRemark, parawiseRemarkDto);
        return parawiseRemarkDto;
    }

    public void saveDocuments(List<DocumentDetailsVO> attachments, RequestDTO requestDTO) {

        ApplicationContextProvider.getApplicationContext().getBean(IFileUploadService.class).doMasterFileUpload(attachments,
                requestDTO);
    }

	

}
