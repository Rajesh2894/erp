package com.abm.mainet.legal.service;

import java.util.ArrayList;
import java.util.Date;
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

import org.apache.log4j.Logger;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestBody;

import com.abm.mainet.common.audit.service.AuditService;
import com.abm.mainet.common.constant.MainetConstants;
import com.abm.mainet.common.constant.PrefixConstants;
import com.abm.mainet.common.domain.Employee;
import com.abm.mainet.common.domain.Organisation;
import com.abm.mainet.common.exception.FrameworkException;
import com.abm.mainet.common.master.service.TbOrganisationService;
import com.abm.mainet.common.service.IEmployeeService;
import com.abm.mainet.common.utility.CommonMasterUtility;
import com.abm.mainet.common.utility.LookUp;
import com.abm.mainet.common.utility.UserSession;
import com.abm.mainet.common.utility.Utility;
import com.abm.mainet.legal.domain.CaseHearing;
import com.abm.mainet.legal.domain.CaseHearingAttendeeDetails;
import com.abm.mainet.legal.domain.CaseHearingAttendeeDetailsHistory;
import com.abm.mainet.legal.domain.CaseHearingHistory;
import com.abm.mainet.legal.domain.TbLglComntRevwDtlEntity;
import com.abm.mainet.legal.domain.TbLglComntRevwDtlHistEntity;
import com.abm.mainet.legal.dto.CaseEntryDTO;
import com.abm.mainet.legal.dto.CaseHearingAttendeeDetailsDTO;
import com.abm.mainet.legal.dto.CaseHearingDTO;
import com.abm.mainet.legal.dto.TbLglComntRevwDtlDTO;
import com.abm.mainet.legal.repository.CaseHearingAttendeeDetailsRepository;
import com.abm.mainet.legal.repository.CaseHearingRepository;
import com.abm.mainet.legal.repository.TbLglComntRevwDtlRepository;
import com.abm.mainet.smsemail.dto.SMSAndEmailDTO;
import com.abm.mainet.smsemail.service.ISMSAndEmailService;

@Service
@Produces(value = { MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE })
@Consumes(value = { MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE })
@WebService(endpointInterface = "com.abm.mainet.legal.service.ICaseHearingService")
@Path(value = "/casehearingservice")
public class CaseHearingService implements ICaseHearingService {

    @Autowired
    private CaseHearingRepository caseHearingRepository;

    @Autowired
    private CaseHearingAttendeeDetailsRepository hearingAttendeeDetailsRepository;
    
    @Autowired
    private TbLglComntRevwDtlRepository tbLglComntRevwDtlRepository;

    @Autowired
    private AuditService auditService;

    @Autowired
    private ISMSAndEmailService iSMSAndEmailService;

    @Autowired
    private IEmployeeService employeeService;
    
    @Autowired
	TbOrganisationService  tbOrganisationService;


    private static final Logger LOGGER = Logger.getLogger(CaseHearingService.class);

    @Override
    @Transactional
    @POST
    @Path(value = "/save")
    public void saveCaseHearing(@RequestBody CaseHearingDTO caseHearingDto) {
        CaseHearing caseHearing = new CaseHearing();
        BeanUtils.copyProperties(caseHearingDto, caseHearing);
		if ((Utility.isEnvPrefixAvailable(UserSession.getCurrent().getOrganisation(), MainetConstants.ENV_TSCL)) || (Utility.isEnvPrefixAvailable(UserSession.getCurrent().getOrganisation(), MainetConstants.ENV_PSCL))) {
			if (caseHearing.getJudgeId().equals("")) {
				caseHearing.setJudgeId(null);
			}
		}
        caseHearingRepository.save(caseHearing);

        CaseHearingHistory history = new CaseHearingHistory();
        history.sethStatus(MainetConstants.InsertMode.ADD.getStatus());
        auditService.createHistory(caseHearing, history);

    }

    @Override
    @Transactional
    @POST
    @Path(value = "/saveAll")
    public List<CaseHearingDTO> saveAllCaseHearing(List<CaseHearingDTO> caseHearingDto) {
        List<CaseHearing> caseHearingList = new ArrayList<>();
        caseHearingDto.forEach(dto -> {
            CaseHearing caseHearing = new CaseHearing();
            BeanUtils.copyProperties(dto, caseHearing);
            caseHearingList.add(caseHearing);
        });

        List<CaseHearing> list = caseHearingRepository.save(caseHearingList);
        	
        List<Object> caseHearingHistoryList = new ArrayList<>();
        caseHearingList.forEach(entity -> {
            CaseHearingHistory history = new CaseHearingHistory();
            BeanUtils.copyProperties(entity, history);

            if (entity.getHrId() == null) {
                history.sethStatus(MainetConstants.InsertMode.ADD.getStatus());
            } else {
                history.sethStatus(MainetConstants.InsertMode.UPDATE.getStatus());
            }
            caseHearingHistoryList.add(history);
        });

        auditService.createHistoryForListObj(caseHearingHistoryList);
        List<CaseHearingDTO> dto = new ArrayList<>();
        for(CaseHearing caseHearing : list) {
        	CaseHearingDTO caseHearingDTO = new CaseHearingDTO();
        	BeanUtils.copyProperties(caseHearing, caseHearingDTO);
        	
        	dto.add(caseHearingDTO);
        }
        return dto;

    }

    @Override
    @Transactional
    @POST
    @Path(value = "/update")
    public void updateCaseHearing(@RequestBody CaseHearingDTO caseHearingDto) {
        CaseHearing caseHearing = new CaseHearing();
        BeanUtils.copyProperties(caseHearingDto, caseHearing);

        caseHearingRepository.save(caseHearing);

        CaseHearingHistory history = new CaseHearingHistory();
        history.sethStatus(MainetConstants.InsertMode.UPDATE.getStatus());
        auditService.createHistory(caseHearing, history);

    }

    @Override
    @Transactional
    @WebMethod(exclude = true)
    public boolean deleteCaseHearing(CaseHearingDTO caseHearingDto) {
        CaseHearing CaseHearing = caseHearingRepository.findOne(caseHearingDto.getAdvId());
        if (CaseHearing == null)
            return false;

        caseHearingRepository.delete(caseHearingDto.getAdvId());

        CaseHearingHistory history = new CaseHearingHistory();
        history.sethStatus(MainetConstants.InsertMode.UPDATE.getStatus());
        auditService.createHistory(CaseHearing, history);

        return true;
    }

    @Transactional(readOnly = true)
    @Override
    @GET
    @Path(value = "/get/{id}")
    public CaseHearingDTO getCaseHearingById(@PathParam("id") Long id) {
        CaseHearing caseHearing = caseHearingRepository.findOne(id);
        CaseHearingDTO caseHearingDto = new CaseHearingDTO();
        BeanUtils.copyProperties(caseHearing, caseHearingDto);
        return caseHearingDto;
    }

    @Transactional(readOnly = true)
    @Override
    @GET
    @Path(value = "/get/{orgId}/{cseId}")
    public List<CaseHearingDTO> getHearingDetailByCaseId(@PathParam("orgId") Long orgId, @PathParam("cseId") Long cseId) {
        List<CaseHearingDTO> activeUserListDTOs = StreamSupport
                .stream(caseHearingRepository.findByOrgidAndCseId(orgId, cseId).spliterator(), false).map(entity -> {
                    CaseHearingDTO dto = new CaseHearingDTO();
                    BeanUtils.copyProperties(entity, dto);
                    return dto;
                })
                .collect(Collectors.toList());
        return activeUserListDTOs;
    }
    
    @Transactional(readOnly = true)
    @Override
    @GET
    @Path(value = "/get/{cseId}")
    public List<CaseHearingDTO> getHearingDetailsByCaseId(@PathParam("cseId") Long cseId) {
        List<CaseHearingDTO> activeUserListDTOs = StreamSupport
                .stream(caseHearingRepository.findByCseId(cseId).spliterator(), false).map(entity -> {
                    CaseHearingDTO dto = new CaseHearingDTO();
                    BeanUtils.copyProperties(entity, dto);
                    return dto;
                })
                .collect(Collectors.toList());
        return activeUserListDTOs;
    }

    @Transactional
    public void saveAndUpdateCaseHearingAttendee(List<CaseHearingAttendeeDetailsDTO> caseHearingAttendeeDetailsDTOs,
            CaseEntryDTO caseEntryDTO, List<Long> removeAttendeeIds, Organisation organisation) {
        try {
            // this is for only get constant attribute for set in entity
            Employee employee = employeeService.findEmployeeById(caseEntryDTO.getOfficeIncharge());

            if (!removeAttendeeIds.isEmpty()) {
                /* delete from TB_LGL_HEARINGATTENDEE_DETAILS by IDS */
                hearingAttendeeDetailsRepository.deleteHearingAttendeeDetailByIds(removeAttendeeIds);
            }
            List<CaseHearingAttendeeDetails> hearingAttendeeList = new ArrayList<>();
            List<Object> historyList = new ArrayList<Object>();
            // insert and update based on condition in TB_LGL_HEARINGATTENDEE_DETAILS and History Table
            caseHearingAttendeeDetailsDTOs.forEach(hearingAttendeeDetails -> {
                CaseHearingAttendeeDetails hearingDetails = new CaseHearingAttendeeDetails();
                BeanUtils.copyProperties(hearingAttendeeDetails, hearingDetails);
                hearingDetails.setCseId(caseEntryDTO.getCseId());
                if (hearingDetails.getHraId() != null) {
                    // update Attribute value set
                    hearingDetails.setOrgid(organisation.getOrgid());
                    hearingDetails.setUpdatedBy(employee.getEmpId());
                    hearingDetails.setUpdatedDate(new Date());
                    hearingDetails.setLgIpMacUpd(employee.getLgIpMacUpd());
                    hearingAttendeeList.add(hearingDetails);
                    CaseHearingAttendeeDetails updateEntity = hearingAttendeeDetailsRepository.save(hearingDetails);
                    // history table update
                    CaseHearingAttendeeDetailsHistory history = new CaseHearingAttendeeDetailsHistory();
                    history.sethStatus(MainetConstants.InsertMode.UPDATE.getStatus());
                    BeanUtils.copyProperties(updateEntity, history);
                    historyList.add(history);
                } else {
                    // insert Attribute value set
                    hearingDetails.setOrgid(organisation.getOrgid());
                    hearingDetails.setCreatedBy(employee.getEmpId());
                    hearingDetails.setCreatedDate(new Date());
                    hearingDetails.setLgIpMac(employee.getLgIpMac());
                    hearingAttendeeList.add(hearingDetails);
                    CaseHearingAttendeeDetails entity = hearingAttendeeDetailsRepository.save(hearingDetails);
                    // history table insert
                    CaseHearingAttendeeDetailsHistory history = new CaseHearingAttendeeDetailsHistory();
                    history.sethStatus(MainetConstants.InsertMode.ADD.getStatus());
                    BeanUtils.copyProperties(entity, history);
                    historyList.add(history);
                }
            });
            // check once insert in table or not
            // hearingAttendeeDetailsRepository.save(hearingAttendeeList);
            auditService.createHistoryForListObj(historyList);

            // Send Email and SMS
            caseHearingAttendeeDetailsDTOs.forEach(hearingAttendee -> {
                final SMSAndEmailDTO dto = new SMSAndEmailDTO();
                dto.setMobnumber(hearingAttendee.getHraPhoneNo());
                dto.setUserId(caseEntryDTO.getOfficeIncharge());
                // dto.setEmail(hearingAttendee.getHraEmailId());

                String menuUrl = "HearingDetails.html";
                Organisation org = new Organisation();
                org.setOrgid(caseEntryDTO.getOrgid());
                int langId = Utility.getDefaultLanguageId(org);
                iSMSAndEmailService.sendEmailSMS("LEGL", menuUrl,
                        PrefixConstants.SMS_EMAIL_ALERT_TYPE.SUBMITTED, dto, org, 1);
            });

        } catch (Exception e) {
            throw new FrameworkException("Exception occured when saving Or Update case hearing attendee details   ", e);
        }
    }
    
    @Transactional
    @Override
    public void saveAndUpdtHearingComntAndRevw(List<TbLglComntRevwDtlDTO> tbLglComntRevwDtlDTOList,
            CaseEntryDTO caseEntryDTO, List<Long> removeComntIds, Organisation organisation) {
        try {
            // this is for only get constant attribute for set in entity
            Employee employee = employeeService.findEmployeeById(caseEntryDTO.getOfficeIncharge());

            List<TbLglComntRevwDtlEntity> tbLglComntRevwDtlEntityList = new ArrayList<>();
            List<Object> historyList = new ArrayList<Object>();
            // insert and update based on condition in TB_LGL_COMNT_REVW_DTL and History Table
            tbLglComntRevwDtlDTOList.forEach(TbLglComntRevwDtlDTO -> {
            	TbLglComntRevwDtlEntity tbLglComntRevwDtlEntity = new TbLglComntRevwDtlEntity();
                BeanUtils.copyProperties(TbLglComntRevwDtlDTO, tbLglComntRevwDtlEntity);
                tbLglComntRevwDtlEntity.setCseId(caseEntryDTO.getCseId());
                if (tbLglComntRevwDtlEntity.getComntId() != null) {
                    // update Attribute value set
                	tbLglComntRevwDtlEntity.setOrgid(organisation.getOrgid());
                	tbLglComntRevwDtlEntity.setUpdatedBy(employee.getEmpId());
                	tbLglComntRevwDtlEntity.setUpdatedDate(new Date());
                	tbLglComntRevwDtlEntity.setLgIpMacUpd(employee.getLgIpMacUpd());
                	tbLglComntRevwDtlEntityList.add(tbLglComntRevwDtlEntity);
                	TbLglComntRevwDtlEntity updateEntity = tbLglComntRevwDtlRepository.save(tbLglComntRevwDtlEntity);
                    // history table update
                	TbLglComntRevwDtlHistEntity history = new TbLglComntRevwDtlHistEntity();
                    history.sethStatus(MainetConstants.InsertMode.UPDATE.getStatus());
                    BeanUtils.copyProperties(updateEntity, history);
                    historyList.add(history);
                } else {
                    // insert Attribute value set
                	tbLglComntRevwDtlEntity.setActiveStatus(MainetConstants.FlagA);
                	tbLglComntRevwDtlEntity.setOrgid(organisation.getOrgid());
                	tbLglComntRevwDtlEntity.setCreatedBy(employee.getEmpId());
                	tbLglComntRevwDtlEntity.setCreatedDate(new Date());
                	tbLglComntRevwDtlEntity.setLgIpMac(employee.getLgIpMac());
                	tbLglComntRevwDtlEntityList.add(tbLglComntRevwDtlEntity);
                	TbLglComntRevwDtlEntity updateEntity = tbLglComntRevwDtlRepository.save(tbLglComntRevwDtlEntity);
                    // history table insert
                	TbLglComntRevwDtlHistEntity history = new TbLglComntRevwDtlHistEntity();
                    history.sethStatus(MainetConstants.InsertMode.ADD.getStatus());
                    BeanUtils.copyProperties(updateEntity, history);
                    historyList.add(history);
                }
            });
            // check once insert in table or not
            // hearingAttendeeDetailsRepository.save(hearingAttendeeList);
            auditService.createHistoryForListObj(historyList);
            
            if (!removeComntIds.isEmpty()) {
                /*delete from TB_LGL_HEARINGATTENDEE_DETAILS by IDS*/ 
                tbLglComntRevwDtlRepository.deleteHearingComntRevwByIds(removeComntIds,UserSession.getCurrent().getEmployee().getEmpId());
           }

        } catch (Exception e) {
            throw new FrameworkException("Exception occured when saving Or Update case hearing comment/reviw details   ", e);
        }
    }

    @Override
    public List<CaseHearingAttendeeDetailsDTO> fetchHearingAttendeeDetailsByCaseId(Long cseId, Long orgId) {
        List<CaseHearingAttendeeDetailsDTO> hearingAttendeeDTOs = StreamSupport
                .stream(hearingAttendeeDetailsRepository.findByCseIdAndOrgid(cseId, orgId).spliterator(), false).map(entity -> {
                    CaseHearingAttendeeDetailsDTO dto = new CaseHearingAttendeeDetailsDTO();
                    BeanUtils.copyProperties(entity, dto);
                    return dto;
                }).collect(Collectors.toList());
        return hearingAttendeeDTOs;
    }
    @Override
    public List<CaseHearingAttendeeDetailsDTO> fetchHearingAttenDetailsByCaseIdUad(Long cseId) {
        List<CaseHearingAttendeeDetailsDTO> hearingAttendeeDTOs = StreamSupport
                .stream(hearingAttendeeDetailsRepository.findByCseId(cseId).spliterator(), false).map(entity -> {
                    CaseHearingAttendeeDetailsDTO dto = new CaseHearingAttendeeDetailsDTO();
                    BeanUtils.copyProperties(entity, dto);
                    return dto;
                }).collect(Collectors.toList());
        return hearingAttendeeDTOs;
    }
    
    @Override
    public List<TbLglComntRevwDtlDTO> fetchHearingComntsDetailsByCaseId(Long cseId, Long orgId) {
    	List<TbLglComntRevwDtlDTO> tbLglComntRevwDtlDTO;
    	final LookUp lookUp = CommonMasterUtility.getLookUpFromPrefixLookUpDesc(PrefixConstants.LegalPrefix.NO_OF_DAYS,
    			PrefixConstants.LegalPrefix.COMNT_REVW_DAYS,UserSession.getCurrent().getLanguageId(),
    			UserSession.getCurrent().getOrganisation());
    	final Integer noOfDays = Integer.parseInt(lookUp.getLookUpCode());
		if (orgId != null) {
			tbLglComntRevwDtlDTO = StreamSupport
					.stream(tbLglComntRevwDtlRepository.findByCseIdAndOrgid(cseId, orgId).spliterator(), false)
					.map(entity -> {
						TbLglComntRevwDtlDTO dto = new TbLglComntRevwDtlDTO();
						BeanUtils.copyProperties(entity, dto);
						if(Utility.getDaysBetDates(entity.getHrDate(), new Date())>=noOfDays) {
							dto.setDisableField(true);
						}
						return dto;
					}).collect(Collectors.toList());
		} else {
			tbLglComntRevwDtlDTO = StreamSupport
					.stream(tbLglComntRevwDtlRepository.findByCseId(cseId).spliterator(), false)
					.map(entity -> {
						TbLglComntRevwDtlDTO dto = new TbLglComntRevwDtlDTO();
						BeanUtils.copyProperties(entity, dto);
						if(Utility.getDaysBetDates(entity.getHrDate(), new Date())>=noOfDays) {
							dto.setDisableField(true);
						}
						return dto;
					}).collect(Collectors.toList());
		}
		return tbLglComntRevwDtlDTO;
    }

	// #88449-to get hearing details
	public List<CaseHearingDTO> getHearingDetailByOrgId(Long orgid,Date currDate) {
        List<CaseHearingDTO> activeUserListDTOs = StreamSupport
                .stream(caseHearingRepository.findHearingDetByOrgid(orgid, currDate).spliterator(), false).map(entity -> {
                    CaseHearingDTO dto = new CaseHearingDTO();
                    BeanUtils.copyProperties(entity, dto);
                    return dto;
                })
                .collect(Collectors.toList());
        return activeUserListDTOs;
	}

	@Override
	public List<TbLglComntRevwDtlDTO> fetchHearingComntsDetailsByHearingId(Long hrId, Long orgId) {
		List<TbLglComntRevwDtlDTO> tbLglComntRevwDtlDTO;
    	final LookUp lookUp = CommonMasterUtility.getLookUpFromPrefixLookUpDesc(PrefixConstants.LegalPrefix.NO_OF_DAYS,
    			PrefixConstants.LegalPrefix.COMNT_REVW_DAYS,MainetConstants.NUMBERS.ONE,
    			UserSession.getCurrent().getOrganisation());
    	final Integer noOfDays = Integer.parseInt(lookUp.getLookUpCode());
		if (orgId != null) {
			tbLglComntRevwDtlDTO = StreamSupport
					.stream(tbLglComntRevwDtlRepository.findByhrIdAndOrgid(hrId, orgId).spliterator(), false)
					.map(entity -> {
						TbLglComntRevwDtlDTO dto = new TbLglComntRevwDtlDTO();
						BeanUtils.copyProperties(entity, dto);
						if(Utility.getDaysBetDates(entity.getHrDate(), new Date())>=noOfDays) {
							dto.setDisableField(true);
						}
						return dto;
					}).collect(Collectors.toList());
		} else {
			tbLglComntRevwDtlDTO = StreamSupport
					.stream(tbLglComntRevwDtlRepository.findByhrId(hrId).spliterator(), false)
					.map(entity -> {
						TbLglComntRevwDtlDTO dto = new TbLglComntRevwDtlDTO();
						BeanUtils.copyProperties(entity, dto);
						if(Utility.getDaysBetDates(entity.getHrDate(), new Date())>=noOfDays) {
							dto.setDisableField(true);
						}
						return dto;
					}).collect(Collectors.toList());
		}
		return tbLglComntRevwDtlDTO;
	}

	@Override
	public List<CaseHearingAttendeeDetailsDTO> fetchHearingAttenDetailsByHearingId(Long hrId) {
		List<CaseHearingAttendeeDetailsDTO> hearingAttendeeDTOs = StreamSupport
                .stream(hearingAttendeeDetailsRepository.findByHrId(hrId).spliterator(), false).map(entity -> {
                    CaseHearingAttendeeDetailsDTO dto = new CaseHearingAttendeeDetailsDTO();
                    BeanUtils.copyProperties(entity, dto);
                    return dto;
                }).collect(Collectors.toList());
        return hearingAttendeeDTOs;
	}

	@Override
	public List<CaseHearingAttendeeDetailsDTO> fetchHearingAttendeeDetailsByHearingId(Long id, Long orgId) {
		List<CaseHearingAttendeeDetailsDTO> hearingAttendeeDTOs = StreamSupport
                .stream(hearingAttendeeDetailsRepository.findByHrIdAndOrgid(id, orgId).spliterator(), false).map(entity -> {
                    CaseHearingAttendeeDetailsDTO dto = new CaseHearingAttendeeDetailsDTO();
                    BeanUtils.copyProperties(entity, dto);
                    return dto;
                }).collect(Collectors.toList());
        return hearingAttendeeDTOs;
	}
    
    
}
