package com.abm.mainet.cfc.objection.service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.commons.collections.CollectionUtils;
import org.codehaus.plexus.util.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.abm.mainet.cfc.objection.domain.NoticeMasterEntity;
import com.abm.mainet.cfc.objection.dto.NoticeMasterDto;
import com.abm.mainet.cfc.objection.repository.NoticeMasterRepository;
import com.abm.mainet.common.constant.MainetConstants;
import com.abm.mainet.common.domain.Organisation;
import com.abm.mainet.common.utility.CommonMasterUtility;
import com.abm.mainet.common.utility.LookUp;
import com.abm.mainet.common.utility.SeqGenFunctionUtility;
import com.abm.mainet.common.utility.Utility;

@Service
public class NoticeMasterServiceImpl implements NoticeMasterService {

    /**
     * 
     */
    private static final long serialVersionUID = 1L;

    @Autowired
    private SeqGenFunctionUtility seqGenFunctionUtility;

    @Autowired
    private NoticeMasterRepository noticeMasterRepository;

    @Override
    public List<String> saveListOfNoticeMaster(List<NoticeMasterDto> notDtoList, Long orgId, Long empId, String macAddress,
            Long notTypeId,
            Long finYearId) {
        final String ipAddress = Utility.getMacAddress();
        List<String> notNoList = new ArrayList<>();
        notDtoList.forEach(notDto -> {
            NoticeMasterEntity notMastEnt = new NoticeMasterEntity();
            BeanUtils.copyProperties(notDto, notMastEnt);
            notMastEnt.setCreationDate(new Date());
            notMastEnt.setCreatedBy(empId);
            notMastEnt.setOrgId(orgId);
            notMastEnt.setLgIpMac(ipAddress);
            notMastEnt.setNotDate(new Date());
            String notNo = getNoticeNo(orgId, finYearId, notTypeId);
            notMastEnt.setNotNo(notNo);
            notDto.setNotNo(notNo);
            noticeMasterRepository.save(notMastEnt);
            notNoList.add(notNo);

        });
        return notNoList;
    }

    @Override
    public String saveNoticeMaster(NoticeMasterDto notDto, Long orgId, Long empId, String macAddress, Long notTypeId,
            Long finYearId) {
        final String ipAddress = Utility.getMacAddress();
        NoticeMasterEntity notMastEnt = new NoticeMasterEntity();
        BeanUtils.copyProperties(notDto, notMastEnt);
        notMastEnt.setCreationDate(new Date());
        notMastEnt.setCreatedBy(empId);
        notMastEnt.setOrgId(orgId);
        notMastEnt.setLgIpMac(ipAddress);
        notMastEnt.setNotDate(new Date());
        notMastEnt.setNotTyp(notTypeId);
        String notNo = getNoticeNo(orgId, finYearId, notTypeId);
        notMastEnt.setNotNo(notNo);
        noticeMasterRepository.save(notMastEnt);
        return notNo;
    }

    @Override
    public int getCountOfNotBeforeDueDateByRefNoAndNotNo(Long orgId, String refNo, String notNo) {
        return noticeMasterRepository.getCountOfNotBeforeDueDateByRefNoAndNotNo(orgId, refNo, notNo);
    }

    @Override
    public int getCountOfNotByRefNoAndNotNo(Long orgId, String refNo, String notNo) {
        return noticeMasterRepository.getCountOfNotByRefNoAndNotNo(orgId, refNo, notNo);
    }

    @Override
    public NoticeMasterDto getNoticeByRefNo(Long orgId, String refNo) {
        NoticeMasterEntity notEnt = noticeMasterRepository.getNoticeByRefNo(orgId, refNo);
        NoticeMasterDto notDto = null;
        if (notEnt != null) {
            notDto = new NoticeMasterDto();
            BeanUtils.copyProperties(notEnt, notDto);
        }
        return notDto;
    }

    private String getNoticeNo(Long orgId, Long finYearId, Long notTypeCpdType) {
        String fifthPar = notTypeCpdType.toString() + finYearId.toString();
        final Long sequence = seqGenFunctionUtility.generateSequenceNo(MainetConstants.CommonConstants.COM,
                MainetConstants.Objection.TB_NOTICE_MAS,
                MainetConstants.Objection.INS_ID, orgId,
                MainetConstants.FlagC, Long.valueOf(fifthPar));
        final String propNo = sequence.toString();
        final String paddingPropNo = String.format(MainetConstants.CommonMasterUi.PADDING_SIX, Integer.parseInt(propNo));
        return orgId.toString().concat(paddingPropNo);
    }

    @Override
    public NoticeMasterDto getMaxNoticeByRefNo(Long orgId, String refNo, Long dpDeptid) {
        NoticeMasterEntity notEnt = noticeMasterRepository.getMaxNoticeByRefNo(orgId, refNo, dpDeptid);
        NoticeMasterDto notDto = null;
        if (notEnt != null) {
            notDto = new NoticeMasterDto();
            BeanUtils.copyProperties(notEnt, notDto);
        }
        return notDto;

    }

    @Override
	public List<NoticeMasterDto> getAllNoticeByRefNo(Long orgId, String refNo, String flatNo) {
        List<NoticeMasterDto> noticeDtoList = null;
        List<NoticeMasterEntity> allNoticeByRefNo =new ArrayList<>();
		if (StringUtils.isNotBlank(flatNo)) {
			allNoticeByRefNo = noticeMasterRepository.getAllNoticeByRefNo(orgId, refNo, flatNo);
		} else {
			allNoticeByRefNo = noticeMasterRepository.getAllNoticeByRefNo(orgId, refNo);
		}
        if (CollectionUtils.isNotEmpty(allNoticeByRefNo)) {
            noticeDtoList = new ArrayList<NoticeMasterDto>();
            for (NoticeMasterEntity noticeEntity : allNoticeByRefNo) {
                NoticeMasterDto noticeDto = new NoticeMasterDto();
                BeanUtils.copyProperties(noticeEntity, noticeDto);
                // get notice type desc
                Organisation organisation = new Organisation();
                organisation.setOrgid(orgId);
				if (noticeEntity.getNotTyp() != null) {
					LookUp noticeLookup = CommonMasterUtility.getNonHierarchicalLookUpObject(noticeEntity.getNotTyp(),
							organisation);
					noticeDto.setNoticeTypeDesc(noticeLookup.getLookUpDesc());
					noticeDtoList.add(noticeDto);
				}
            }
        }
        return noticeDtoList;
    }

    @Override
    public NoticeMasterDto getNoticeByNoticeNo(Long orgId, String noticeNo) {
        List<NoticeMasterEntity> notEntList = noticeMasterRepository.getNoticeByNoticeNo(orgId, noticeNo);
        NoticeMasterDto notDto = null;
        if(CollectionUtils.isNotEmpty(notEntList)) {
        	NoticeMasterEntity notEnt = notEntList.get(0);
        	 if (notEnt != null) {
                 notDto = new NoticeMasterDto();
                 BeanUtils.copyProperties(notEnt, notDto);
             }
        }
       
        return notDto;
    }

	@Override
	@Transactional
	public int getCountOfNotByApplNoAndNotNo(Long orgId, Long apmApplicationId, String noticeNo) {		
		 return noticeMasterRepository.getCountOfNotByApplNoNoAndNotNo(orgId, apmApplicationId, noticeNo);
	}

	@Override
	@Transactional
	public int getCountOfNotBeforeDueDateByApplNoAndNotNo(Long orgId, Long apmApplicationId, String noticeNo) {	
		return noticeMasterRepository.getCountOfNotBeforeDueDateByApplNoAndNotNo(orgId, apmApplicationId, noticeNo);
	}

	@Override
	public Long getNoticeIdByApplicationId(Long apmApplicationId) {
		return noticeMasterRepository.getNoticeIdByApplicationId(apmApplicationId);
	}
}
