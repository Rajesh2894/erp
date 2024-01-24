package com.abm.mainet.council.service;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.abm.mainet.common.audit.service.AuditService;
import com.abm.mainet.common.constant.MainetConstants;
import com.abm.mainet.common.domain.Organisation;
import com.abm.mainet.common.exception.FrameworkException;
import com.abm.mainet.common.utility.ApplicationContextProvider;
import com.abm.mainet.common.utility.CommonMasterUtility;
import com.abm.mainet.common.utility.LookUp;
import com.abm.mainet.common.utility.SeqGenFunctionUtility;
import com.abm.mainet.common.utility.Utility;
import com.abm.mainet.council.dao.ICouncilAgendaMasterDao;
import com.abm.mainet.council.dao.ICouncilProposalMasterDao;
import com.abm.mainet.council.domain.CouncilAgendaMasterEntity;
import com.abm.mainet.council.domain.CouncilAgendaMasterHistoryEntity;
import com.abm.mainet.council.dto.CouncilAgendaMasterDto;
import com.abm.mainet.council.dto.CouncilMemberCommitteeMasterDto;
import com.abm.mainet.council.dto.CouncilProposalMasterDto;
import com.abm.mainet.council.repository.CouncilAgendaMasterRepository;
import com.abm.mainet.council.repository.CouncilProposalMasterRepository;

@Service
public class CouncilAgendaMasterServiceImpl implements ICouncilAgendaMasterService {
    // LOGGER IS USED WHEN SAVE COUNCIL AGENDA
    private static final Logger LOGGER = LoggerFactory.getLogger(CouncilAgendaMasterServiceImpl.class);

    @Autowired
    private CouncilAgendaMasterRepository councilAgendaMasterRepository;

    @Autowired
    ICouncilAgendaMasterDao iCouncilAgendaMasterDao;

    @Autowired
    ICouncilProposalMasterDao iCouncilProposalMasterDao;

    @Autowired
    AuditService auditService;
    
    @Autowired
    private CouncilProposalMasterRepository councilProposalMasterRepository;

    @Transactional
    public boolean saveCouncilAgenda(CouncilAgendaMasterDto councilAgendaMasterDto) {
        try {
            // check here 1st is update or create transaction based on id value
            Long agendaId = councilAgendaMasterDto.getAgendaId();
            CouncilAgendaMasterEntity councilAgendaMasterEntity = new CouncilAgendaMasterEntity();
            BeanUtils.copyProperties(councilAgendaMasterDto, councilAgendaMasterEntity);
            // by iterating proposalIds store in agenda details table
            String stringProposalIds = councilAgendaMasterDto.getProposalIds();
            String splitIds[] = stringProposalIds.split(MainetConstants.operator.COMMA);
            List<Long> proposalIds = new ArrayList<>();
            for (int i = 0; i < splitIds.length; i++) {
                proposalIds.add(Long.valueOf(splitIds[i]));
            }
            if (agendaId != null) {
                // first set the null against agenda id column(agendaId) in proposal master
                // table and than after set the agendaId to newly added proposals
                iCouncilProposalMasterDao.updateNullOfAgendaIdInProposalByAgendaId(agendaId);
                iCouncilProposalMasterDao.updateNullOfAgendaIdInProposalHistoryByAgendaId(agendaId);
                councilAgendaMasterRepository.save(councilAgendaMasterEntity);

            } else {
                String AgendaNo = "AG-"; // this will change after ask to nilima madam
                Organisation org = new Organisation();
                org.setOrgid(councilAgendaMasterDto.getOrgId());
				if (Utility.isEnvPrefixAvailable(org, MainetConstants.ENV_TSCL)) {
					String year = Utility.getCurrentFinancialYear();
	                String fromYear = year.substring(2, 4);
	                String toYear = year.substring(7);

	                String finacialYear = fromYear + MainetConstants.operator.HIPHEN + toYear;

	                
	                LookUp lookupComittee = null;
	                Long sequence = null;
					try {
						lookupComittee = CommonMasterUtility.getNonHierarchicalLookUpObjectByPrefix(
								councilAgendaMasterDto.getCommitteeTypeId(), councilAgendaMasterDto.getOrgId(), "CPT");
					} catch (Exception e) {
						LOGGER.error("Prefix CPT not found " + e);
					}
					sequence = ApplicationContextProvider.getApplicationContext()
							.getBean(SeqGenFunctionUtility.class)
							.generateSequenceNo(MainetConstants.Council.COUNCIL_MANAGEMENT,
									MainetConstants.Council.Proposal.TB_CMT_COUNCIL_AGENDA_MAST,
									"AGENDA_ID", councilAgendaMasterDto.getOrgId(),
									MainetConstants.FlagF, councilAgendaMasterDto.getCommitteeTypeId());
					String agendaNo = finacialYear + MainetConstants.WINDOWS_SLASH + lookupComittee.getLookUpCode() + MainetConstants.WINDOWS_SLASH + String.format("%05d", sequence);
					councilAgendaMasterEntity.setAgendaNo(agendaNo);
				} else {
	                Long agendaNo = councilAgendaMasterRepository.countByOrgId(councilAgendaMasterDto.getOrgId());
	                councilAgendaMasterEntity.setAgendaNo(Long.toString(agendaNo + 1));
				}
                CouncilAgendaMasterEntity agendaMasterEntity = councilAgendaMasterRepository.save(councilAgendaMasterEntity);
                // insert in agenda history table
                CouncilAgendaMasterHistoryEntity history = new CouncilAgendaMasterHistoryEntity();
                history.sethStatus(MainetConstants.InsertMode.ADD.getStatus());
                auditService.createHistory(agendaMasterEntity, history);
                agendaId = agendaMasterEntity.getAgendaId();
            }
            // update in proposal table and history table using proposalIds
            iCouncilProposalMasterDao.updateTheAgendaIdByProposalIds(proposalIds, agendaId);
            iCouncilProposalMasterDao.updateTheAgendaIdOfProposalHistoryByProposalIds(proposalIds, agendaId);

        } catch (FrameworkException ex) {
            throw new FrameworkException("Error in when save Agenda:", ex);
        }
        return MainetConstants.SUCCESS;
    }

    @Override
    @Transactional(readOnly = true)
    public List<CouncilAgendaMasterDto> searchCouncilAgendaMasterData(Long committeeTypeId, String agendaNo,
            String fromDate, String toDate, Long orgid) {
        List<CouncilAgendaMasterDto> councilAgendaMasterDtoList = new ArrayList<>();
        List<CouncilAgendaMasterEntity> agendaMasterEntities = iCouncilAgendaMasterDao
                .searchCouncilAgendaMasterData(committeeTypeId, agendaNo, fromDate, toDate, orgid);
        Organisation organisation = new Organisation();
        organisation.setOrgid(orgid);
        agendaMasterEntities.forEach(mastEntity -> {
            CouncilAgendaMasterDto dto = new CouncilAgendaMasterDto();
            BeanUtils.copyProperties(mastEntity, dto);
            dto.setAgenDate(Utility.dateToString(mastEntity.getAgendaDate()));
            dto.setCommitteeType(CommonMasterUtility
                    .getNonHierarchicalLookUpObject(mastEntity.getCommitteeTypeId(), organisation)
                    .getLookUpDesc());
            councilAgendaMasterDtoList.add(dto);
        });
        return councilAgendaMasterDtoList;
    }

    @Override
    @Transactional(readOnly = true)
    public CouncilAgendaMasterDto getCouncilAgendaMasterByAgendaId(Long agendaId) {
        CouncilAgendaMasterDto councilAgendaMasterDto = new CouncilAgendaMasterDto();
        CouncilAgendaMasterEntity councilAgendaMasterEntity = councilAgendaMasterRepository.findOne(agendaId);
        BeanUtils.copyProperties(councilAgendaMasterEntity, councilAgendaMasterDto);
        // set date and committeeType Name in dto
        councilAgendaMasterDto.setAgenDate(Utility.dateToString(councilAgendaMasterEntity.getAgendaDate()));
        Organisation organisation = new Organisation();
        organisation.setOrgid(councilAgendaMasterDto.getOrgId());
        councilAgendaMasterDto.setCommitteeType(CommonMasterUtility.getNonHierarchicalLookUpObject(
                councilAgendaMasterEntity.getCommitteeTypeId(), organisation).getLookUpDesc());
        return councilAgendaMasterDto;
    }
    
    @Override
    @Transactional
    public void updateSubjectNo(List<CouncilProposalMasterDto> dto) {
    	 for (int i = 0; i < dto.size(); i++) {
    		 if(dto.get(i).getSubNo()!=null)
    			 councilProposalMasterRepository.updateSerialNo(dto.get(i).getProposalId(),dto.get(i).getSubNo());
    	 }
    }

}
