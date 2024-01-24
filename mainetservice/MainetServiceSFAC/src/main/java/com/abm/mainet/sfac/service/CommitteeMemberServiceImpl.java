/**
 * 
 */
package com.abm.mainet.sfac.service;

import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.abm.mainet.common.constant.MainetConstants;
import com.abm.mainet.common.exception.FrameworkException;
import com.abm.mainet.common.utility.CommonMasterUtility;
import com.abm.mainet.common.utility.LookUp;
import com.abm.mainet.common.utility.Utility;
import com.abm.mainet.sfac.dao.CommitteeMemberDao;
import com.abm.mainet.sfac.domain.CommitteeMemberMasterEntity;
import com.abm.mainet.sfac.domain.CommitteeMemberMasterHistory;
import com.abm.mainet.sfac.dto.CommitteeMemberMasterDto;
import com.abm.mainet.sfac.repository.CommitteeMemberHistRepository;
import com.abm.mainet.sfac.repository.CommitteeMemberRepository;

/**
 * @author pooja.maske
 *
 */
@Service
public class CommitteeMemberServiceImpl implements CommitteeMemberService {

	private static final Logger log = Logger.getLogger(CommitteeMemberServiceImpl.class);

	@Autowired
	private CommitteeMemberRepository comMemRepo;

	@Autowired
	private CommitteeMemberHistRepository comMemHistRepo;

	@Autowired
	private CommitteeMemberDao committeeMemberDao;

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.abm.mainet.sfac.service.CommitteeMemberService#saveCommitteeMemDetails(
	 * com.abm.mainet.sfac.dto.CommitteeMemberMasterDto)
	 */
	@Override
	@Transactional
	public CommitteeMemberMasterDto saveCommitteeMemDetails(CommitteeMemberMasterDto mastDto) {
		CommitteeMemberMasterEntity masEntity = new CommitteeMemberMasterEntity();
		try {
			log.info("saveCommitteeMemDetails started");
			BeanUtils.copyProperties(mastDto, masEntity);
			masEntity = comMemRepo.save(masEntity);
			saveHistoryData(masEntity);
		} catch (Exception e) {
			log.error("error occured while saving Committee member master  details" + e);
			throw new FrameworkException("error occured while saving  Committee member master  details" + e);
		}
		log.info("saveCommitteeMemDetails End");
		return mastDto;

	}

	/**
	 * @param masEntity
	 */
	private void saveHistoryData(CommitteeMemberMasterEntity masEntity) {
		CommitteeMemberMasterHistory histEn = new CommitteeMemberMasterHistory();
		try {
			log.info("saveHistoryData Started");
			BeanUtils.copyProperties(masEntity, histEn);
			histEn.setComMemberId(masEntity.getComMemberId());
			if (histEn.getUpdatedBy() == null)
				histEn.setHistoryStatus(MainetConstants.InsertMode.ADD.getStatus());
			else
				histEn.setHistoryStatus(MainetConstants.InsertMode.UPDATE.getStatus());
			comMemHistRepo.save(histEn);
			log.info("saveHistoryData Ended");
		} catch (Exception e) {
			log.error("Error Occured while saving Committee Member Master history data" + e);

		}
		log.info("saveHistoryData Ended");
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.abm.mainet.sfac.service.CommitteeMemberService#updateCommitteeMemDetails(
	 * com.abm.mainet.sfac.dto.CommitteeMemberMasterDto)
	 */
	@Override
	@Transactional
	public CommitteeMemberMasterDto updateCommitteeMemDetails(CommitteeMemberMasterDto mastDto) {
		CommitteeMemberMasterEntity masEntity = new CommitteeMemberMasterEntity();
		try {
			log.info("updateCommitteeMemDetails Started");
			BeanUtils.copyProperties(mastDto, masEntity);
			masEntity = comMemRepo.save(masEntity);
			saveHistoryData(masEntity);
		} catch (Exception e) {
			log.error("Error Occured while updating Committee Member Master data" + e);

		}
		log.info("updateCommitteeMemDetails Ended");
		return mastDto;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.abm.mainet.sfac.service.CommitteeMemberService#findAllCommitteeDet()
	 */
	@SuppressWarnings("deprecation")
	@Override
	public List<CommitteeMemberMasterDto> findAllCommitteeDet(Long orgId) {
		List<CommitteeMemberMasterDto> comMemDtoList = new ArrayList<>();
		try {
			log.info("findAllCommitteeDet started");
			List<CommitteeMemberMasterEntity> entityList = comMemRepo.findAllCommitteeDet(orgId);
			entityList.forEach(ent -> {
				CommitteeMemberMasterDto dto = new CommitteeMemberMasterDto();
				BeanUtils.copyProperties(ent, dto);
				if (ent.getStatus() != null) {
					LookUp lookup = CommonMasterUtility.getNonHierarchicalLookUpObject(ent.getStatus());
					if (lookup != null)
						dto.setStatusDesc(lookup.getDescLangFirst());
				}
				dto.setMemberSince(Utility.dateToString(ent.getFromDate()));
				comMemDtoList.add(dto);
			});
		} catch (Exception e) {
			log.error("Error occured while fetching committee deail in findAllCommitteeDet");
		}
		log.info("findAllCommitteeDet ended");
		return comMemDtoList;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.abm.mainet.sfac.service.CommitteeMemberService#getDetByCommiteeIdAndName(
	 * java.lang.Long, java.lang.Long, long)
	 */
	@SuppressWarnings("deprecation")
	@Override
	public List<CommitteeMemberMasterDto> getDetByCommiteeIdAndName(Long committeeTypeId, Long comMemberId,
			Long orgId) {
		List<CommitteeMemberMasterDto> comMemDtoList = new ArrayList<>();
		try {
			log.info("getDetByCommiteeIdAndName started");
			List<CommitteeMemberMasterEntity> entityList = committeeMemberDao.getDetByCommiteeIdAndName(committeeTypeId,
					comMemberId, orgId);
			entityList.forEach(ent -> {
				CommitteeMemberMasterDto dto = new CommitteeMemberMasterDto();
				BeanUtils.copyProperties(ent, dto);
				if (ent.getStatus() != null) {
					LookUp lookup = CommonMasterUtility.getNonHierarchicalLookUpObject(ent.getStatus());
					if (lookup != null)
						dto.setStatusDesc(lookup.getDescLangFirst());
				}
				dto.setMemberSince(Utility.dateToString(ent.getFromDate()));
				comMemDtoList.add(dto);
			});

		} catch (Exception e) {
			log.error("Error occured while fetching committee deail in getDetByCommiteeIdAndName");
		}
		log.info("getDetByCommiteeIdAndName ended");
		return comMemDtoList;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.abm.mainet.sfac.service.CommitteeMemberService#findById(java.lang.Long,
	 * java.lang.Long)
	 */
	@Override
	public CommitteeMemberMasterDto findById(Long comMemberId) {
		CommitteeMemberMasterDto dto = new CommitteeMemberMasterDto();
		try {
			log.info("findById started");
			CommitteeMemberMasterEntity entity = comMemRepo.findById(comMemberId);
			BeanUtils.copyProperties(entity, dto);
		} catch (Exception e) {
			log.error("Error occured while fetching committee member details by comMemberId infindById " + e);
		}
		log.info("findById started");
		return dto;
	}

}
