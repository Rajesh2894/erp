package com.abm.mainet.legal.service;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
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

import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestBody;

import com.abm.mainet.common.audit.service.AuditService;
import com.abm.mainet.common.constant.MainetConstants;
import com.abm.mainet.legal.domain.AttendeeDetails;
import com.abm.mainet.legal.domain.JudgementDetail;
import com.abm.mainet.legal.domain.JudgementDetailHistory;
import com.abm.mainet.legal.dto.AttendeeDetailDto;
import com.abm.mainet.legal.dto.JudgementDetailDTO;
import com.abm.mainet.legal.repository.JudgementDetailRepository;

@Service
@Produces(value = { MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE })
@Consumes(value = { MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE })
@WebService(endpointInterface = "com.abm.mainet.legal.service.IJudgementDetailService")
@Path(value = "/judgementdetailservice")
public class JudgementDetailService implements IJudgementDetailService {

    @Autowired
    private JudgementDetailRepository judgementDetailRepository;

    @Autowired
    private AuditService auditService;

    @Override
    @Transactional
    @POST
    @Path(value = "/save")
    public void saveJudgementDetail(@RequestBody JudgementDetailDTO judgementDetailDto) {
	JudgementDetail judgementDetail = new JudgementDetail();
	BeanUtils.copyProperties(judgementDetailDto, judgementDetail);
	Set<AttendeeDetails> attendeeList = new HashSet<>();

	judgementDetailDto.getAttendeeDtoList().forEach(attendee -> {
	    AttendeeDetails attendeedetail = new AttendeeDetails();
	    BeanUtils.copyProperties(attendee, attendeedetail);
	    attendeedetail.setTbCaseJudgeDetail(judgementDetail);
	    attendeeList.add(attendeedetail);
	});
	judgementDetail.setTbAttendeeDetails(attendeeList);
	judgementDetailRepository.save(judgementDetail);

	JudgementDetailHistory history = new JudgementDetailHistory();
	history.setHStatus(MainetConstants.InsertMode.ADD.getStatus());
	auditService.createHistory(judgementDetail, history);

    }

    @Override
    @Transactional
    @POST
    @Path(value = "/saveAll")
    public void saveAllJudgementDetail(List<JudgementDetailDTO> judgementDetailDto) {
	List<JudgementDetail> remarks = new ArrayList<>();
	judgementDetailDto.forEach(dto -> {
	    JudgementDetail judgementDetail = new JudgementDetail();
	    BeanUtils.copyProperties(dto, judgementDetail);
	    remarks.add(judgementDetail);
	});

	judgementDetailRepository.save(remarks);

	remarks.forEach(entity -> {
	    JudgementDetailHistory history = new JudgementDetailHistory();
	    if (entity.getCjdId() == null) {
		history.setHStatus(MainetConstants.InsertMode.ADD.getStatus());
	    } else {
		history.setHStatus(MainetConstants.InsertMode.UPDATE.getStatus());
	    }

	    auditService.createHistory(entity, history);
	});

    }

    @Override
    @Transactional
    @POST
    @Path(value = "/update")
    public void updateJudgementDetail(@RequestBody JudgementDetailDTO judgementDetailDto, List<Long> removeAttendeeIds) {
	JudgementDetail judgementDetail = new JudgementDetail();
	BeanUtils.copyProperties(judgementDetailDto, judgementDetail);
	Set<AttendeeDetails> attendeeList = new HashSet<>();

	judgementDetailDto.getAttendeeDtoList().forEach(attendee -> {
	    AttendeeDetails attendeedetail = new AttendeeDetails();
	    BeanUtils.copyProperties(attendee, attendeedetail);
	    attendeedetail.setTbCaseJudgeDetail(judgementDetail);
	    attendeeList.add(attendeedetail);
	});
	judgementDetail.setTbAttendeeDetails(attendeeList);
	judgementDetailRepository.save(judgementDetail);

	JudgementDetailHistory history = new JudgementDetailHistory();
	history.setHStatus(MainetConstants.InsertMode.UPDATE.getStatus());
	if(CollectionUtils.isNotEmpty(removeAttendeeIds)) {
	    judgementDetailRepository.deleteAttendeeDetailByIds(removeAttendeeIds);
	}
	auditService.createHistory(judgementDetail, history);

    }

    @Override
    @Transactional
    @WebMethod(exclude = true)
    public boolean deleteJudgementDetail(JudgementDetailDTO judgementDetailDto) {
	JudgementDetail JudgementDetail = judgementDetailRepository.findOne(judgementDetailDto.getCjdId());
	if (JudgementDetail == null)
	    return false;

	judgementDetailRepository.delete(judgementDetailDto.getCjdId());

	JudgementDetailHistory history = new JudgementDetailHistory();
	history.setHStatus(MainetConstants.InsertMode.UPDATE.getStatus());
	auditService.createHistory(JudgementDetail, history);

	return true;
    }

    @Transactional(readOnly = true)
    @Override
    @GET
    @Path(value = "/get/{id}")
    public JudgementDetailDTO getJudgementDetailById(@PathParam("id") Long id) {
	JudgementDetail judgementDetail = judgementDetailRepository.findOne(id);
	JudgementDetailDTO judgementDetailDto = new JudgementDetailDTO();
	BeanUtils.copyProperties(judgementDetail, judgementDetailDto);
	return judgementDetailDto;
    }

    @Transactional(readOnly = true)
    @Override
    @GET
    @Path(value = "/get/{orgId}/{cseId}")
    public List<JudgementDetailDTO> getHearingDetailByCaseId(@PathParam("orgId") Long orgId,
	    @PathParam("cseId") Long cseId) {
	List<JudgementDetailDTO> judgementDetail = StreamSupport
		.stream(judgementDetailRepository.findByOrgidAndCseId(orgId, cseId).spliterator(), false)
		.map(entity -> {
		    JudgementDetailDTO dto = new JudgementDetailDTO();
		    BeanUtils.copyProperties(entity, dto);
		    return dto;
		}).collect(Collectors.toList());
	return judgementDetail;
    }

    @Override
    public JudgementDetailDTO getJudgeDetailByCaseId(Long orgId, Long caseId) {

	JudgementDetail entity = judgementDetailRepository.findByCseId(orgId, caseId);
	JudgementDetailDTO judgementDetailDTO = new JudgementDetailDTO();
	List<AttendeeDetailDto> attendeeDtoList = new ArrayList<>();
	if (entity != null) {
	    BeanUtils.copyProperties(entity, judgementDetailDTO);
		if (CollectionUtils.isNotEmpty(entity.getTbAttendeeDetails())) {
		    entity.getTbAttendeeDetails().forEach(det -> {
			AttendeeDetailDto attendeedto = new AttendeeDetailDto();
			BeanUtils.copyProperties(det, attendeedto);
			attendeeDtoList.add(attendeedto);
		    });
		}
	}
	
	judgementDetailDTO.setAttendeeDtoList(attendeeDtoList);
	return judgementDetailDTO;
    }
    
    @Override
    public JudgementDetailDTO getJudgeDetailsByCaseId(Long caseId) {

	JudgementDetail entity = judgementDetailRepository.findJudgementDetByCseId(caseId);
	JudgementDetailDTO judgementDetailDTO = new JudgementDetailDTO();
	List<AttendeeDetailDto> attendeeDtoList = new ArrayList<>();
	if (entity != null) {
	    BeanUtils.copyProperties(entity, judgementDetailDTO);
		if (CollectionUtils.isNotEmpty(entity.getTbAttendeeDetails())) {
		    entity.getTbAttendeeDetails().forEach(det -> {
			AttendeeDetailDto attendeedto = new AttendeeDetailDto();
			BeanUtils.copyProperties(det, attendeedto);
			attendeeDtoList.add(attendeedto);
		    });
		}
	}
	
	judgementDetailDTO.setAttendeeDtoList(attendeeDtoList);
	return judgementDetailDTO;
    }

}
