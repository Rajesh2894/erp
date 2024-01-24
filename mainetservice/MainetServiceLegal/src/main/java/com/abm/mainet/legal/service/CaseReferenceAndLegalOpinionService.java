package com.abm.mainet.legal.service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

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

import com.abm.mainet.common.audit.service.AuditService;
import com.abm.mainet.common.constant.MainetConstants;
import com.abm.mainet.legal.domain.LegalOpinion;
import com.abm.mainet.legal.domain.LegalOpinionHistory;
import com.abm.mainet.legal.domain.LegalReference;
import com.abm.mainet.legal.domain.LegalreferenceHistory;
import com.abm.mainet.legal.dto.CaseReferenceAndLegalOpinionDTO;
import com.abm.mainet.legal.dto.LegalOpinionDTO;
import com.abm.mainet.legal.dto.LegalReferenceDTO;
import com.abm.mainet.legal.repository.LegalOpinionRepository;
import com.abm.mainet.legal.repository.LegalReferenceRepository;

@Service
@Produces(value = { MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE })
@Consumes(value = { MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE })
@WebService(endpointInterface = "com.abm.mainet.legal.service.ICaseReferenceAndLegalOpinionService")
@Path(value = "/casereferenceAndLegalopinionservice")
public class CaseReferenceAndLegalOpinionService implements ICaseReferenceAndLegalOpinionService {

    @Autowired
    private LegalOpinionRepository legalOpinionRepository;

    @Autowired
    private LegalReferenceRepository legalReferenceRepository;

    @Autowired
    private AuditService auditService;

    @Override
    @Transactional
    @POST
    @Path(value = "/saveAll")
    public void saveAllCaseReferenceAndLegalOpinion(CaseReferenceAndLegalOpinionDTO caseReferenceAndLegalOpinionDto) {
        saveLegalOpenions(caseReferenceAndLegalOpinionDto);

        saveLegalReferences(caseReferenceAndLegalOpinionDto);

    }

    private void saveLegalReferences(CaseReferenceAndLegalOpinionDTO caseReferenceAndLegalOpinionDto) {
        List<LegalReference> legalReferences = new ArrayList<>();
        caseReferenceAndLegalOpinionDto.getLegalReference().forEach(dto -> {
            LegalReference legalReference = new LegalReference();
            BeanUtils.copyProperties(dto, legalReference);
            legalReferences.add(legalReference);
        });

        legalReferenceRepository.save(legalReferences);

        legalReferences.forEach(entity -> {
            LegalreferenceHistory history = new LegalreferenceHistory();
            if (entity.getLoReferenceid() == null) {
                history.sethStatus(MainetConstants.InsertMode.ADD.getStatus());
            } else {
                history.sethStatus(MainetConstants.InsertMode.UPDATE.getStatus());
            }

            auditService.createHistory(entity, history);
        });
    }

    private void saveLegalOpenions(CaseReferenceAndLegalOpinionDTO caseReferenceAndLegalOpinionDto) {
        List<LegalOpinion> legalOpinions = new ArrayList<>();
        caseReferenceAndLegalOpinionDto.getLegalOpinion().forEach(dto -> {
            LegalOpinion legalOpinion = new LegalOpinion();
            BeanUtils.copyProperties(dto, legalOpinion);
            legalOpinions.add(legalOpinion);
        });

        legalOpinionRepository.save(legalOpinions);

        legalOpinions.forEach(entity -> {
            LegalOpinionHistory history = new LegalOpinionHistory();
            if (entity.getLoOpinionid() == null) {
                history.sethStatus(MainetConstants.InsertMode.ADD.getStatus());
            } else {
                history.sethStatus(MainetConstants.InsertMode.UPDATE.getStatus());
            }

            auditService.createHistory(entity, history);
        });
    }

    @Transactional(readOnly = true)
    @GET
    @Path(value = "/get/{orgid}/{cseId}")
    public CaseReferenceAndLegalOpinionDTO getCaseReferenceAndLegalOpinionById(@PathParam("orgid") Long orgid,
            @PathParam("cseId") Long cseId) {
        CaseReferenceAndLegalOpinionDTO dto = new CaseReferenceAndLegalOpinionDTO();
        List<LegalOpinionDTO> legalOpinions = StreamSupport
                .stream(legalOpinionRepository.findByOrgidAndCseId(orgid, cseId).spliterator(), false).map(entity -> {
                    LegalOpinionDTO legalOpinion = new LegalOpinionDTO();
                    BeanUtils.copyProperties(entity, legalOpinion);
                    return legalOpinion;
                }).collect(Collectors.toList());

        List<LegalReferenceDTO> legalReferences = StreamSupport
                .stream(legalOpinionRepository.findByOrgidAndCseId(orgid, cseId).spliterator(), false).map(entity -> {
                    LegalReferenceDTO legalReference = new LegalReferenceDTO();
                    BeanUtils.copyProperties(entity, legalReference);
                    return legalReference;
                }).collect(Collectors.toList());

        dto.setLegalReference(legalReferences);
        dto.setLegalOpinion(legalOpinions);

        return dto;
    }

}
