package com.abm.mainet.council.service;

import java.lang.invoke.MethodHandles.Lookup;
import java.math.BigDecimal;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.annotation.Resource;
import javax.jws.WebMethod;
import javax.jws.WebService;
import javax.ws.rs.POST;
import javax.ws.rs.Path;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.json.JSONObject;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.ObjectUtils;
import org.springframework.web.bind.annotation.RequestBody;

import com.abm.mainet.common.audit.service.AuditService;
import com.abm.mainet.common.constant.MainetConstants;
import com.abm.mainet.common.constant.ServiceEndpoints;
import com.abm.mainet.common.domain.Organisation;
import com.abm.mainet.common.domain.ServiceMaster;
import com.abm.mainet.common.exception.FrameworkException;
import com.abm.mainet.common.integration.acccount.domain.AccountHeadSecondaryAccountCodeMasterEntity;
import com.abm.mainet.common.integration.acccount.dto.SecondaryheadMaster;
import com.abm.mainet.common.integration.acccount.dto.VendorBillApprovalDTO;
import com.abm.mainet.common.integration.acccount.service.SecondaryheadMasterService;
import com.abm.mainet.common.integration.dms.dao.IAttachDocsDao;
import com.abm.mainet.common.integration.dms.dto.FileUploadDTO;
import com.abm.mainet.common.integration.dms.service.IFileUploadService;
import com.abm.mainet.common.integration.dto.CommonProposalDTO;
import com.abm.mainet.common.integration.dto.DocumentDetailsVO;
import com.abm.mainet.common.master.repository.TbFinancialyearJpaRepository;
import com.abm.mainet.common.master.service.DepartmentService;
import com.abm.mainet.common.service.ServiceMasterService;
import com.abm.mainet.common.utility.ApplicationContextProvider;
import com.abm.mainet.common.utility.ApplicationSession;
import com.abm.mainet.common.utility.CommonMasterUtility;
import com.abm.mainet.common.utility.LookUp;
import com.abm.mainet.common.utility.RestClient;
import com.abm.mainet.common.utility.SeqGenFunctionUtility;
import com.abm.mainet.common.utility.Utility;
import com.abm.mainet.council.dao.ICouncilProposalMasterDao;
import com.abm.mainet.council.domain.CouncilBudgetDetEntity;
import com.abm.mainet.council.domain.CouncilProposalMasHistoryEntity;
import com.abm.mainet.council.domain.CouncilProposalMasterEntity;
import com.abm.mainet.council.domain.CouncilProposalWardEntity;
import com.abm.mainet.council.dto.CouncilMeetingMasterDto;
import com.abm.mainet.council.dto.CouncilProposalMasterDto;
import com.abm.mainet.council.dto.CouncilYearDetDto;
import com.abm.mainet.council.dto.MOMResolutionDto;
import com.abm.mainet.council.repository.CouncilProposalMasterRepository;
import com.abm.mainet.council.repository.CouncilProposalWardRepository;
import com.abm.mainet.council.repository.MOMRepository;
import com.fasterxml.jackson.databind.ObjectMapper;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

@WebService(endpointInterface = "com.abm.mainet.council.service.ICouncilProposalMasterService")
@Api(value = "/proposalMaster")
@Path("/proposalMaster")
@Service
public class CouncilProposalMasterServiceImpl implements ICouncilProposalMasterService {

    @Autowired
    ICouncilProposalMasterDao proposalMasterDao;

    @Autowired
    DepartmentService departmentService;

    @Autowired
    CouncilProposalMasterRepository councilProposalMasterRepository;

    @Autowired
    CouncilProposalWardRepository councilProposalWardRepository;

    @Autowired
    private ICouncilAgendaMasterService councilAgendaMasterService;

    @Autowired
    private IFileUploadService fileUpload;

    @Autowired
    private AuditService auditService;

    @Autowired
    ICouncilMOMService councilMOMService;

    @Autowired
    ICouncilMeetingMasterService councilMeetingMasterService;

    @Autowired
    private MOMRepository momRepository;
    
    @Autowired
    private IAttachDocsDao attachDocsDao;
    
    @Resource
    private TbFinancialyearJpaRepository tbFinancialyearJpaRepository;

    private static final Logger LOGGER = Logger.getLogger(CouncilProposalMasterServiceImpl.class);

    @WebMethod(exclude = true)
    @Transactional
    public List<CouncilProposalMasterDto> fetchProposalListByStatus(String proposalStatus, Long orgId) {
        List<CouncilProposalMasterDto> proposalMasterDtoList = new ArrayList<>();
        List<CouncilProposalMasterEntity> proposalMasterList = proposalMasterDao
                .fetchProposalListByStatus(proposalStatus, orgId);
        for (CouncilProposalMasterEntity proposalMasterEntity : proposalMasterList) {
            CouncilProposalMasterDto masterDto = new CouncilProposalMasterDto();
            BeanUtils.copyProperties(proposalMasterEntity, masterDto);
            // get department name
            String deptName = departmentService.fetchDepartmentDescById(proposalMasterEntity.getProposalDepId());
            String propoStatus = MainetConstants.Council.STATUS_APPROVED;
            switch (proposalMasterEntity.getProposalStatus()) {
            case MainetConstants.Council.DB_STATUS_REJECT:
                propoStatus = MainetConstants.Council.STATUS_REJECTED;
                break;
            case MainetConstants.Council.DB_STATUS_PENDING:
                propoStatus = MainetConstants.Council.STATUS_PENDING;
                break;
            case MainetConstants.Council.DB_STATUS_DRAFT:
                propoStatus = MainetConstants.Council.STATUS_DRAFT;
                break;
            default:
                break;
            }
            masterDto.setProposalStatus(propoStatus);
            masterDto.setProposalDeptName(deptName);
            if(masterDto.getProposalSubType()!=null)
            masterDto.setProposalSubTypeDesc(CommonMasterUtility.findLookUpDesc("PST",
            		orgId, masterDto.getProposalSubType()));
            if(masterDto.getCommitteeType()!=null)
            masterDto.setCommitteeTypeDesc(CommonMasterUtility.findLookUpDesc("CPT",
            		orgId, masterDto.getCommitteeType()));
            proposalMasterDtoList.add(masterDto);
        }
        return proposalMasterDtoList;
    }

    
    @WebMethod(exclude = true)
    @Transactional
    @Override
    public List<CouncilProposalMasterDto> fetchProposalListbyCommiteeStatus(String proposalStatus, Long orgId) {
        List<CouncilProposalMasterDto> proposalMasterDtoList = new ArrayList<>();
        List<CouncilProposalMasterEntity> proposalMasterList = proposalMasterDao
                .fetchProposalListByStatus(proposalStatus, orgId);
        for (CouncilProposalMasterEntity proposalMasterEntity : proposalMasterList) {
            CouncilProposalMasterDto masterDto = new CouncilProposalMasterDto();
            BeanUtils.copyProperties(proposalMasterEntity, masterDto);
            // get department name
            
            Long committeeId = councilProposalMasterRepository.fetchCommiteeId(masterDto.getCommitteeType(),masterDto.getOrgId());
            String deptName = departmentService.fetchDepartmentDescById(proposalMasterEntity.getProposalDepId());
            String propoStatus = MainetConstants.Council.STATUS_APPROVED;
            switch (proposalMasterEntity.getProposalStatus()) {
            case MainetConstants.Council.DB_STATUS_REJECT:
                propoStatus = MainetConstants.Council.STATUS_REJECTED;
                break;
            case MainetConstants.Council.DB_STATUS_PENDING:
                propoStatus = MainetConstants.Council.STATUS_PENDING;
                break;
            case MainetConstants.Council.DB_STATUS_DRAFT:
                propoStatus = MainetConstants.Council.STATUS_DRAFT;
                break;
            default:
                break;
            }
            masterDto.setProposalStatus(propoStatus);
            masterDto.setProposalDeptName(deptName);
            masterDto.setCommitteeType(committeeId);
            if(masterDto.getProposalSubType()!=null)
            masterDto.setProposalSubTypeDesc(CommonMasterUtility.findLookUpDesc("PST",
            		orgId, masterDto.getProposalSubType()));
            if(masterDto.getCommitteeType()!=null)
            masterDto.setCommitteeTypeDesc(CommonMasterUtility.findLookUpDesc("CPT",
            		orgId, masterDto.getCommitteeType()));
            proposalMasterDtoList.add(masterDto);
        }
        return proposalMasterDtoList;
    }

    
    
    
    
    
    
    
    @WebMethod(exclude = true)
    @Transactional
    public List<CouncilProposalMasterDto> findAllProposalByAgendaId(Long agendaId) {

        List<CouncilProposalMasterDto> proposalMasterDtos = new ArrayList<>();
        List<CouncilProposalMasterEntity> agendaProposalEntities = councilProposalMasterRepository
                .findAllByAgendaId(agendaId);
        // map entity into dto
        agendaProposalEntities.forEach(mastEntity -> {
            CouncilProposalMasterDto proposalMasterDto = new CouncilProposalMasterDto();
            BeanUtils.copyProperties(mastEntity, proposalMasterDto);
            // set agenda No in proposalDto
            // proposalMasterDto.setAgendaNo(mastEntity.getCouncilAgendaMasterEntity().getAgendaNo());
            // get department name and set in dto list
            proposalMasterDto
                    .setAgendaNo(councilAgendaMasterService.getCouncilAgendaMasterByAgendaId(agendaId).getAgendaNo());
            proposalMasterDto
                    .setProposalDeptName(departmentService.fetchDepartmentDescById(mastEntity.getProposalDepId()));
            proposalMasterDtos.add(proposalMasterDto);
        });
        return proposalMasterDtos;
    }

    @Override
    @WebMethod(exclude = true)
    @Transactional
    public void saveCouncil(CouncilProposalMasterDto councilDto, List<DocumentDetailsVO> attachmentList,
            FileUploadDTO fileUploadDTO, Long deleteFileId,List<Long> removeYearIdList) {
        try {

            CouncilProposalMasterEntity counilMasterEntity = new CouncilProposalMasterEntity();
            
            Organisation org = new Organisation();
            org.setOrgid(councilDto.getOrgId());
            // Creating Dynamic Proposal Number
            if (councilDto.getProposalId() == null) {
                // 1st get Financial year and month name
                String year = Utility.getCurrentFinancialYear();
                String fromYear = year.substring(2, 4);
                String toYear = year.substring(7);

                String finacialYear = fromYear + MainetConstants.operator.HIPHEN + toYear;

                String monthName = new SimpleDateFormat(MainetConstants.Council.MONTH_FORMAT).format(new Date())
                        .toUpperCase();
                
                LookUp lookupComittee = null;
                Long sequence = null;
                String proposalNo = "";
				if (Utility.isEnvPrefixAvailable(org, MainetConstants.ENV_TSCL)) {
					try {
						lookupComittee = CommonMasterUtility.getNonHierarchicalLookUpObjectByPrefix(
								councilDto.getCommitteeType(), councilDto.getOrgId(), "CPT");
					} catch (Exception e) {
						LOGGER.error("Prefix CPT not found " + e);
					}
					sequence = ApplicationContextProvider.getApplicationContext()
							.getBean(SeqGenFunctionUtility.class)
							.generateSequenceNo(MainetConstants.Council.COUNCIL_MANAGEMENT,
									MainetConstants.Council.Proposal.TB_COU_PROPOSAL_MASTER,
									MainetConstants.Council.Proposal.PROPOSAL_NO, councilDto.getOrgId(),
									MainetConstants.FlagC, councilDto.getCommitteeType());
					proposalNo = finacialYear + MainetConstants.WINDOWS_SLASH + monthName
	                        + MainetConstants.WINDOWS_SLASH + lookupComittee.getLookUpCode() + MainetConstants.WINDOWS_SLASH + String.format("%05d", sequence);
				} else {

					sequence = ApplicationContextProvider.getApplicationContext()
							.getBean(SeqGenFunctionUtility.class)
							.generateSequenceNo(MainetConstants.Council.COUNCIL_MANAGEMENT,
									MainetConstants.Council.Proposal.TB_COU_PROPOSAL_MASTER,
									MainetConstants.Council.Proposal.PROPOSAL_NO, councilDto.getOrgId(),
									MainetConstants.FlagC, councilDto.getOrgId());
					proposalNo = finacialYear + MainetConstants.WINDOWS_SLASH + monthName
	                        + MainetConstants.WINDOWS_SLASH + String.format("%03d", sequence);
				}
                
                councilDto.setProposalNo(proposalNo);
            }

            // checking proposal coming from MOM or not
            if (StringUtils.equals(councilDto.getIsMOMProposal(), MainetConstants.FlagY)) {
                councilDto.setProposalStatus(MainetConstants.FlagA);

            }
           
            // updating proposal status as active by default as per requirement - workflow
            // is not applicable

            
            LookUp lookup1 = null;
            try {
            	lookup1 = CommonMasterUtility.getValueFromPrefixLookUp("PWR", MainetConstants.LandEstate.MODULE_LIVE_INTEGRATION, org);
            	 Date date1=new SimpleDateFormat("dd/MM/yyyy").parse(lookup1.getOtherField());  
            	if (lookup1!=null && Utility.compareDate(councilDto.getProposalDate(),date1)) {
            		councilDto.setProposalStatus(MainetConstants.FlagA);
            	}
            } catch (Exception e) {
            	LOGGER.error("Prefix PWR not found " + e);
            }


            ServiceMaster serviceMaster = new ServiceMaster();
            LookUp lookup = null;
            try {
            	lookup = CommonMasterUtility.getValueFromPrefixLookUp(councilDto.getProposalType(), MainetConstants.Council.PROPOSAL_TYPE_PREFIX, org);
            } catch (Exception e) {
            	LOGGER.error("Prefix PTS not found " + e);
            }
            if(lookup!=null && lookup.getOtherField()!=null && lookup.getOtherField()!="") {
            	String serviceCode = lookup.getOtherField();
            	serviceMaster = ApplicationContextProvider.getApplicationContext().getBean(ServiceMasterService.class)
                        .getServiceMasterByShortCode(serviceCode, councilDto.getOrgId());
            }
            else {
	            if (StringUtils.equals(councilDto.getProposalType(), MainetConstants.FlagF)) {
	                serviceMaster = ApplicationContextProvider.getApplicationContext().getBean(ServiceMasterService.class)
	                        .getServiceMasterByShortCode(MainetConstants.Council.Proposal.SERVICE_COUNCIL_PROPOSAL,
	                                councilDto.getOrgId());
	
	            }
	
	            if (StringUtils.equals(councilDto.getProposalType(), MainetConstants.FlagN)) {
	                serviceMaster = ApplicationContextProvider.getApplicationContext().getBean(ServiceMasterService.class)
	                        .getServiceMasterByShortCode("CPN", councilDto.getOrgId());
	
	            }
	            
	            if (StringUtils.equals(councilDto.getProposalType(), MainetConstants.FlagB)) {
	                serviceMaster = ApplicationContextProvider.getApplicationContext().getBean(ServiceMasterService.class)
	                        .getServiceMasterByShortCode("CPB", councilDto.getOrgId());
	
	            }
            }

            LookUp workflow = CommonMasterUtility.getNonHierarchicalLookUpObject(serviceMaster.getSmProcessId(), org);
            if (StringUtils.equals(workflow.getLookUpCode(), "NA")) {
                councilDto.setProposalStatus(MainetConstants.FlagA);
            }
            if(!CollectionUtils.isEmpty(councilDto.getYearDtos())){
            	BigDecimal proposalAmount= new BigDecimal(MainetConstants.WorksManagement.BIG_ZERO);
            	 for (CouncilYearDetDto dto : councilDto.getYearDtos()) {
            		 if(dto.getYeBugAmount()!=null)
            		 proposalAmount=proposalAmount.add(dto.getYeBugAmount());
            	 }
            	 councilDto.setProposalAmt(proposalAmount);
            }

            BeanUtils.copyProperties(councilDto, counilMasterEntity);
            if (!StringUtils.equals(councilDto.getProposalType(), MainetConstants.FlagN)) {
            setFinanacialYearDetailsForUpdate(councilDto, counilMasterEntity);
            }
            CouncilProposalMasterEntity proposalMasterEntity = councilProposalMasterRepository.save(counilMasterEntity);
            if(!CollectionUtils.isEmpty(removeYearIdList))
            councilProposalMasterRepository.iactiveYearsByIds(removeYearIdList, councilDto.getUpdatedBy());
            if (councilDto.getProposalId() != null) {
                // update in CouncilProposalWardEntity
                // so 1st delete old record and re-insert with new one
                counilMasterEntity.getWards().clear();
            }

            // create and update in proposal history table using ProposalId
            try {
                CouncilProposalMasHistoryEntity proposalHistory = new CouncilProposalMasHistoryEntity();
                if (councilDto.getUpdatedBy() == null) {
                    proposalHistory.setHistoryStatus(MainetConstants.InsertMode.ADD.getStatus());
                } else {
                    proposalHistory.setHistoryStatus(MainetConstants.InsertMode.UPDATE.getStatus());
                }
                auditService.createHistory(proposalMasterEntity, proposalHistory);
            } catch (Exception exception) {
                LOGGER.error("Exception occured when creating history : ", exception);
            }

            if (CollectionUtils.isEmpty(councilDto.getWards())) {

                CouncilProposalWardEntity wardEntity = new CouncilProposalWardEntity();
                wardEntity.setOrgId(counilMasterEntity.getOrgId());
                wardEntity.setProposalId(counilMasterEntity);
                wardEntity.setCreatedBy(councilDto.getCreatedBy());
                wardEntity.setCreatedDate(councilDto.getCreatedDate());
                wardEntity.setLgIpMac(councilDto.getLgIpMac());
                if (councilDto.getProposalId() != null) {
                    wardEntity.setUpdatedBy(councilDto.getUpdatedBy());
                    wardEntity.setUpdatedDate(councilDto.getUpdatedDate());
                    wardEntity.setLgIpMacUpd(councilDto.getLgIpMacUpd());
                }

                counilMasterEntity.getWards().add(wardEntity);

            } else {
                for (Long wardid : councilDto.getWards()) {
                    if (wardid != null) {
                        CouncilProposalWardEntity wardEntity = new CouncilProposalWardEntity();
                        wardEntity.setWardId(wardid);
                        wardEntity.setOrgId(counilMasterEntity.getOrgId());
                        wardEntity.setProposalId(counilMasterEntity);
                        wardEntity.setCreatedBy(councilDto.getCreatedBy());
                        wardEntity.setCreatedDate(councilDto.getCreatedDate());
                        wardEntity.setLgIpMac(councilDto.getLgIpMac());
                        if (councilDto.getProposalId() != null) {
                            wardEntity.setUpdatedBy(councilDto.getUpdatedBy());
                            wardEntity.setUpdatedDate(councilDto.getUpdatedDate());
                            wardEntity.setLgIpMacUpd(councilDto.getLgIpMacUpd());
                        }

                        counilMasterEntity.getWards().add(wardEntity);
                    }
                }
            }

            // this save for insertion in proposal ward table
            councilProposalMasterRepository.save(counilMasterEntity);

            // file upload code set here
            if (attachmentList != null && !attachmentList.isEmpty() && deleteFileId == null) {
                fileUploadDTO.setIdfId("PRO" + MainetConstants.WINDOWS_SLASH + proposalMasterEntity.getProposalId());
                fileUpload.doMasterFileUpload(attachmentList, fileUploadDTO);
            } else if (attachmentList != null && !attachmentList.isEmpty() && deleteFileId != null) {
                fileUploadDTO.setIdfId("PRO" + MainetConstants.WINDOWS_SLASH + proposalMasterEntity.getProposalId());
                fileUpload.doMasterFileUpload(attachmentList, fileUploadDTO);
                List<Long> deletedDocFiles = new ArrayList<>();
                deletedDocFiles.add(deleteFileId);
                ApplicationContextProvider.getApplicationContext().getBean(IAttachDocsDao.class)
                        .updateRecord(deletedDocFiles, councilDto.getUpdatedBy(), MainetConstants.FlagD);
            }
        } catch (Exception e) {
            throw new FrameworkException("Error in save proposal ", e);
        }
    }

    @Override
    @WebMethod(exclude = true)
    @Transactional
    public List<CouncilProposalMasterDto> searchProposalMasterData(Long proposalDepId, String proposalNo, Date fromDate,
            Date toDate, String proposalStatus, Long orgid, Long wardId, int langId,String type) {
        Organisation org = new Organisation();
        org.setOrgid(orgid);
        List<CouncilProposalMasterDto> councilProposalMastDtoList = new ArrayList<>();
        // if ward id getting than first find list of proposalId from
        // CouncilProposalWardEntity
        List<Long> proposalIds = new ArrayList<Long>();
        if (wardId != null) {
            List<CouncilProposalMasterEntity> masterEntities = new ArrayList<>();
            List<CouncilProposalWardEntity> wardList = councilProposalWardRepository.fetchAllProposalId(wardId, orgid);
            wardList.forEach(proposal -> {
                masterEntities.add(proposal.getProposalId());
            });
            masterEntities.forEach(ward -> {
                proposalIds.add(ward.getProposalId());
            });
        }

        List<CouncilProposalMasterEntity> proposalEntitiesList = proposalMasterDao.searchCouncilProposalData(
                proposalDepId, proposalNo, fromDate, toDate, proposalStatus, orgid, proposalIds,type);
        proposalEntitiesList.forEach(proposalEntity -> {
            CouncilProposalMasterDto dto = new CouncilProposalMasterDto();
            BeanUtils.copyProperties(proposalEntity, dto);
            List<String> wardTempList = new ArrayList<>();
            proposalEntity.getWards().forEach(set -> {
                // get name of lookup
                if (set.getWardId() != null && set.getWardId() != 0) {
                    LookUp lookUpEWZDec = CommonMasterUtility.getHierarchicalLookUp(set.getWardId(), orgid);
                    if (langId == 1) {
                        wardTempList.add(lookUpEWZDec.getDescLangFirst());
                    } else {
                        wardTempList.add(lookUpEWZDec.getDescLangSecond());
                    }
                }
            });
            // set department name
            String deptName = departmentService.fetchDepartmentDescById(proposalEntity.getProposalDepId());
            dto.setProposalDeptName(deptName);
            // set ward desc
            String joinedString = MainetConstants.Council.NOT_AVAILABLE;
            if (wardTempList.size() > 0) {
                joinedString = String.join(",", wardTempList);
            }
            dto.setWardDescJoin(joinedString);

            // getting meeting date and proposal meeting status from momResolution
            MOMResolutionDto momdtos = councilMOMService.getMeetingMOMDataById(dto.getProposalId(), dto.getOrgId());
            if (momdtos != null) {
                // get meeting date by using meeting Id
                CouncilMeetingMasterDto meetingDto = councilMeetingMasterService
                        .getMeetingDataById(momdtos.getMeetingId());
                dto.setMeetingDate(meetingDto.getMeetingDateDesc());
                String proposalMeetingStatus = ApplicationSession.getInstance().getMessage("council.notApplicable");
                /*
                 * String proposalMeetingStatus = CommonMasterUtility.findLookUpCodeDesc(
                 * MainetConstants.Council.MOM.MEETING_PROPOSAL_STATUS_PREFIX, momdtos.getOrgId(), momdtos.getStatus());
                 */
                if (!momdtos.getStatus().equals("0")) {
                    LookUp ppsLookup = CommonMasterUtility.getValueFromPrefixLookUp(momdtos.getStatus(),
                            MainetConstants.Council.MOM.MEETING_PROPOSAL_STATUS_PREFIX, org);
                    if (ppsLookup != null) {
                        proposalMeetingStatus = ppsLookup.getLookUpDesc();
                    }
                }

                dto.setMeetingProposalStatus(proposalMeetingStatus);
            } else if (proposalEntity.getAgendaId() != null && proposalEntity.getAgendaId() != 0) {
                /* dto.setMeetingDate(null); */
                // dto.setMeetingProposalStatus(MainetConstants.Council.Proposal.MEETING_DECISION);
                dto.setMeetingProposalStatus(ApplicationSession.getInstance().getMessage("council.intrim"));
            }
            councilProposalMastDtoList.add(dto);
        });

        return councilProposalMastDtoList;
    }

    @Override
    @WebMethod(exclude = true)
    @Transactional
    public CouncilProposalMasterDto getCouncilProposalMasterByproposalId(Long proposalId) {
        CouncilProposalMasterDto proposalMatserDto = new CouncilProposalMasterDto();
        CouncilProposalMasterEntity counilproposalMasterEntity = councilProposalMasterRepository.findOne(proposalId);
        Set<CouncilProposalWardEntity> wards = counilproposalMasterEntity.getWards();
        List<Long> wardIds = new ArrayList<Long>();
        wards.forEach(ward -> {
            wardIds.add(ward.getWardId());
        });
        // getting secondary Head code value using secHeadId
        String acHeadCode = "NA";
        SecondaryheadMaster secondaryheadMaster = ApplicationContextProvider.getApplicationContext()
                .getBean(SecondaryheadMasterService.class)
                .findBySacHeadId(counilproposalMasterEntity.getSacHeadId(), counilproposalMasterEntity.getOrgId());
        if (secondaryheadMaster != null) {
            acHeadCode = secondaryheadMaster.getAcHeadCode();
        }
        proposalMatserDto.setAcHeadCode(acHeadCode);
        BeanUtils.copyProperties(counilproposalMasterEntity, proposalMatserDto);
		if (!CollectionUtils.isEmpty(counilproposalMasterEntity.getCouncilBugDetEntity())) {
			List<CouncilYearDetDto> definationYearDetDto = new ArrayList<CouncilYearDetDto>();
			counilproposalMasterEntity.getCouncilBugDetEntity().forEach(entity -> {
				CouncilYearDetDto yearDto = new CouncilYearDetDto();
				BeanUtils.copyProperties(entity, yearDto);
				definationYearDetDto.add(yearDto);
			});
			proposalMatserDto.setYearDtos(definationYearDetDto);
		}
        // set long id wards in DTO
        proposalMatserDto.setWards(wardIds);
        proposalMatserDto.setCommitteeType(counilproposalMasterEntity.getCommitteeType());
        return proposalMatserDto;
    }

    @Override
    @WebMethod(exclude = true)
    @Transactional
    public void updateProposalStatus(Long proposalId, String flag) {

        councilProposalMasterRepository.updateProposalStatus(proposalId, flag);
    }

    @Override
    @WebMethod(exclude = true)
    @Transactional
    public CouncilProposalMasterDto getCouncilProposalMasterByproposalNo(String proposalNo, Long orgId) {
        CouncilProposalMasterDto proposalDto = null;
        CouncilProposalMasterEntity proposalEntity = councilProposalMasterRepository
                .findProposalByProposalNo(proposalNo, orgId);
        if (proposalEntity != null) {
            proposalDto = new CouncilProposalMasterDto();
            BeanUtils.copyProperties(proposalEntity, proposalDto);
        }
        return proposalDto;
    }

    @Override
    @WebMethod(exclude = true)
    @Transactional
    public VendorBillApprovalDTO getBudgetExpenditureDetails(VendorBillApprovalDTO billApprovalDTO) {
        VendorBillApprovalDTO vendorBillApprovalDTO = null;
        ResponseEntity<?> response = null;

        try {
            response = RestClient.callRestTemplateClient(billApprovalDTO, ServiceEndpoints.SALARY_BILL_BUDGET_DETAILS);
            if ((response != null) && (response.getStatusCode() == HttpStatus.OK)) {
                LOGGER.info("get Budget Expenditure Details done successfully ::");
                vendorBillApprovalDTO = (VendorBillApprovalDTO) RestClient.castResponse(response,
                        VendorBillApprovalDTO.class);
            } else {
                LOGGER.error("get Budget Expenditure Details failed due to :"
                        + (response != null ? response.getBody() : MainetConstants.BLANK));

                throw new FrameworkException("get Budget Expenditure Details failed due to :"
                        + (response != null ? response.getBody() : MainetConstants.BLANK));
            }
        } catch (Exception ex) {
            LOGGER.error("Exception occured while fetching Budget Expenditure Details : " + ex);
            throw new FrameworkException(ex);
        }
        return vendorBillApprovalDTO;
    }
    
    @Override
    @WebMethod(exclude = true)
    @Transactional
    public VendorBillApprovalDTO getCouncilBudgetExpenditureDetail(VendorBillApprovalDTO billApprovalDTO) {
        VendorBillApprovalDTO vendorBillApprovalDTO = null;
        ResponseEntity<?> response = null;

        try {
            response = RestClient.callRestTemplateClient(billApprovalDTO, ServiceEndpoints.COUNCIL_BILL_BUDGET_DETAILS);
            if ((response != null) && (response.getStatusCode() == HttpStatus.OK)) {
                LOGGER.info("get Budget Expenditure Details done successfully ::");
                vendorBillApprovalDTO = (VendorBillApprovalDTO) RestClient.castResponse(response,
                        VendorBillApprovalDTO.class);
            } else {
                LOGGER.error("get Budget Expenditure Details failed due to :"
                        + (response != null ? response.getBody() : MainetConstants.BLANK));

                throw new FrameworkException("get Budget Expenditure Details failed due to :"
                        + (response != null ? response.getBody() : MainetConstants.BLANK));
            }
        } catch (Exception ex) {
            LOGGER.error("Exception occured while fetching Budget Expenditure Details : " + ex);
            throw new FrameworkException(ex);
        }
        return vendorBillApprovalDTO;
    }

    @Override
    @WebMethod(exclude = true)
    @Transactional
    public List<CouncilProposalMasterDto> findProposalBasedOnStatusByAgendaId(String proposalStatus, Long agendaId) {
        List<CouncilProposalMasterDto> proposalMasterDtos = new ArrayList<>();

        List<CouncilProposalMasterEntity> agendaProposalEntities = councilProposalMasterRepository
                .findProposalsByStatusAndAgendaId(proposalStatus, agendaId);
        // map entity into dto
        agendaProposalEntities.forEach(mastEntity -> {
            CouncilProposalMasterDto proposalMasterDto = new CouncilProposalMasterDto();
            BeanUtils.copyProperties(mastEntity, proposalMasterDto);
            // when need uncomment below code
            // set agenda No in proposalDto
            // get department name and set in dto list
            // proposalMasterDto.setAgendaNo(councilAgendaMasterService.getCouncilAgendaMasterByAgendaId(agendaId).getAgendaNo());
            // proposalMasterDto.setProposalDeptName(departmentService.fetchDepartmentDescById(mastEntity.getProposalDepId()));
            proposalMasterDtos.add(proposalMasterDto);
        });
        return proposalMasterDtos;
    }

    @Override
    @POST
    @ApiOperation(value = "Fetch Proposal Number", notes = "Response", response = Object.class)
    @Path("/proposalDetails")
    public List<CommonProposalDTO> findProposalsByDeptIdAndOrgId(@RequestBody final CommonProposalDTO proposalDto) {
        List<CommonProposalDTO> proposalList = new ArrayList<CommonProposalDTO>();
        // getting list of approved proposal in meeting
        List<Long> proposalIds = momRepository.findProposalsByStatus(MainetConstants.Council.MOM.APPROVED_PROPOSAL_CODE,
                proposalDto.getOrgId());
        if (!proposalIds.isEmpty()) {
            // getting list of proposal using filter of final approved proposal
            List<CouncilProposalMasterEntity> proposalDetailedList = councilProposalMasterRepository
                    .findProposalsByProposalIdOfMOM(proposalDto.getProposalDepId(), proposalDto.getOrgId(),
                            proposalIds);
            proposalDetailedList.forEach(proposalData -> {
                CommonProposalDTO proposalMasterDto = new CommonProposalDTO();
                BeanUtils.copyProperties(proposalData, proposalMasterDto);
                proposalList.add(proposalMasterDto);
            });
        }
        return proposalList;
    }
    
    
    private void setFinanacialYearDetailsForUpdate(CouncilProposalMasterDto councilDefDto, CouncilProposalMasterEntity councilDefEntity) {
		if (councilDefDto.getYearDtos() != null) {
			List<CouncilBudgetDetEntity> defYearEntityList = new ArrayList<>();
			councilDefDto.getYearDtos().forEach(defYear -> {
				//if (defYear.getFinActiveFlag() != null) {
					if (defYear.getFaYearId() != null
							|| (defYear.getFinanceCodeDesc() != null
									&& !defYear.getFinanceCodeDesc().equals(StringUtils.EMPTY))
							|| defYear.getYearPercntWork() != null
							|| defYear.getYeBugAmount() != null || defYear.getSacHeadId() != null) {
						CouncilBudgetDetEntity defYearEntity = new CouncilBudgetDetEntity();
						BeanUtils.copyProperties(defYear, defYearEntity);
						if (defYear.getCbId() != null) {
							defYearEntity.setUpdatedBy(councilDefDto.getCreatedBy());
							defYearEntity.setUpdatedDate(new Date());
							defYearEntity.setLgIpMacUpd(councilDefDto.getLgIpMac());
						} else {
							setCreateYearCommonDetails(councilDefDto, defYearEntity);
						}
						defYearEntity.setCouncilMasEntity(councilDefEntity);
						defYearEntityList.add(defYearEntity);
					}
				//}
			});
			councilDefEntity.setCouncilBugDetEntity(defYearEntityList);
		}
	}
    
    private void setCreateYearCommonDetails(CouncilProposalMasterDto councilDefDto, CouncilBudgetDetEntity defYearEntity) {
		defYearEntity.setCreatedBy(councilDefDto.getCreatedBy());
		defYearEntity.setCreatedDate(new Date());
		defYearEntity.setLgIpMac(councilDefDto.getLgIpMac());
		defYearEntity.setYeActive(MainetConstants.Common_Constant.YES);
		defYearEntity.setOrgId(councilDefDto.getOrgId());
	}
    
    @Override
    @WebMethod(exclude = true)
    @Transactional
    public CouncilProposalMasterDto generateReport(CouncilProposalMasterDto proposalMasterDto) {
    	proposalMasterDto.setProposalDeptName(departmentService.fetchDepartmentDescById(proposalMasterDto.getProposalDepId()));
    	if (proposalMasterDto.getYearDtos() != null && !proposalMasterDto.getProposalType().equals("N")) {
			for (CouncilYearDetDto yearDto : proposalMasterDto.getYearDtos()) {
				List<Object[]> finYearFormToDate = tbFinancialyearJpaRepository.getFinanceYearFrmDate(yearDto.getFaYearId());
				Date fromDate = null;
				Date toDate = null;
				for (Object[] objects : finYearFormToDate) {
					if (objects[0] != null && objects[1] != null) {
						fromDate = (Date) objects[0];
						toDate = (Date) objects[1];
					}
				}
				List<Object[]> listOfObj = null;
				final Object[] obj=councilProposalMasterRepository.getBudgetId(yearDto.getSacHeadId());
				if(!ObjectUtils.isEmpty(obj)) {
				Object[] bugObj = (Object[]) obj[0];
				Long budgetId = Long.valueOf(bugObj[0].toString());
				String budgetCode = bugObj[1].toString();
				yearDto.setBudgetCodeDesc(budgetCode);
				listOfObj = councilProposalMasterRepository.getExpenditureDetailsForBillEntryFormViewWithFieldId(
						proposalMasterDto.getOrgId(), yearDto.getFaYearId(), budgetId, fromDate, toDate,
						proposalMasterDto.getYearDtos().get(0).getFiledId());
				if (listOfObj != null) {
					for (Object[] objects : listOfObj) {
						if (objects[1] != null) {
							yearDto.setOrginalAmount(new BigDecimal(objects[1].toString()));
						}
						if (objects[5] != null) {
							yearDto.setCurntYrAmount(new BigDecimal(objects[5].toString()));
						}
						if (objects[6] != null) {
							yearDto.setNxtYrAmount(new BigDecimal(objects[6].toString()));
						}
						DateFormat dateFormat = new SimpleDateFormat("dd.MM.yyyy");

						// Parse the start date string to a Date object
						Date startDate = null;
						try {
						    startDate = dateFormat.parse("01.04.2023");
						} catch (ParseException e) {
						    // Handle parsing exception if required
						    e.printStackTrace();
						    // You might want to throw an exception or return an error message here
						}

						// Now you can use the parsed startDate in your query method
						yearDto.setCrntAsOnAmount(councilProposalMasterRepository.fetchYeBugAmount(yearDto.getSacHeadId(), proposalMasterDto.getOrgId(), startDate));
						yearDto.setCrntNxtYrAmount(yearDto.getCurntYrAmount().add(yearDto.getNxtYrAmount()));
						if(yearDto.getCrntAsOnAmount() != null) {
							yearDto.setAmountForNewProposal(yearDto.getOrginalAmount()
									.subtract(yearDto.getCrntNxtYrAmount().add(yearDto.getCrntAsOnAmount())));
							yearDto.setCurrentYearAmount(yearDto.getCurntYrAmount()
									.add(yearDto.getCrntAsOnAmount().add(yearDto.getYeBugAmount())));
						}
						
						
						yearDto.setCurrentNextYearAmount(
								yearDto.getCurrentYearAmount().add(yearDto.getCrntNxtYrAmount()));
						yearDto.setRemainingAmount(
								yearDto.getOrginalAmount().subtract(yearDto.getCurrentNextYearAmount()));
						 
						 }
				}
				}
			}
		}
       
        return proposalMasterDto;
    }
    
    @SuppressWarnings("unchecked")
	@Override
    @WebMethod(exclude = true)
    @Transactional
    public  Map<Long, String>getBudgetExpenditure(VendorBillApprovalDTO billApprovalDTO) {
    	Map<Long, String> dto = new HashMap<>();
        ResponseEntity<?> response = null;
        Object responseObj = null;

        try {
            response = RestClient.callRestTemplateClient(billApprovalDTO, ServiceEndpoints.GET_BUDGET);
            if (response != null ) {
            	responseObj = response.getBody();
                dto = (Map<Long, String>)responseObj;
    		}
        } catch (Exception ex) {
            LOGGER.error("Exception occured while fetching Budget Expenditure Details : " + ex);
            throw new FrameworkException(ex);
        }
        return dto;
    }
    
    @Override
	public void updateUploadedFileDeleteRecords(List<Long> removeFileById, Long updatedBy) {
		attachDocsDao.updateRecord(removeFileById, updatedBy, MainetConstants.RnLCommon.Flag_D);
	}
    
};
