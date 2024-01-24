/**
 * 
 */
package com.abm.mainet.adh.service;

import java.util.Date;
import java.util.List;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.abm.mainet.adh.domain.RenewalRemainderNoticeEntity;
import com.abm.mainet.adh.dto.RenewalRemainderNoticeDto;
import com.abm.mainet.adh.repository.RenewalRemainderNoticeRepository;
import com.abm.mainet.common.constant.MainetConstants;
import com.abm.mainet.common.domain.FinancialYear;
import com.abm.mainet.common.domain.Organisation;
import com.abm.mainet.common.exception.FrameworkException;
import com.abm.mainet.common.master.service.TbFinancialyearService;
import com.abm.mainet.common.utility.ApplicationContextProvider;
import com.abm.mainet.common.utility.CommonMasterUtility;
import com.abm.mainet.common.utility.LookUp;
import com.abm.mainet.common.utility.SeqGenFunctionUtility;
import com.abm.mainet.common.utility.Utility;

/**
 * @author cherupelli.srikanth
 * @since 03 October 2019
 */
@Service
public class RenewalRemainderNoticeServiceImpl implements IRenewalRemainderNoticeService {

    @Autowired
    private RenewalRemainderNoticeRepository renewalRemainderNoticeRepository;

    @Override
    public void saveRenewalRemainderNotice(RenewalRemainderNoticeDto noticeDto) {
	try {
	    RenewalRemainderNoticeEntity remainderNoticeEntity = new RenewalRemainderNoticeEntity();
	    BeanUtils.copyProperties(noticeDto, remainderNoticeEntity);
	    renewalRemainderNoticeRepository.save(remainderNoticeEntity);
	} catch (Exception exception) {
	    throw new FrameworkException("Exception occured while saving the remainder notice data", exception);
	}

    }

    @Override
    public void updateRenealRemainderNotice(RenewalRemainderNoticeDto noticeDto) {
	try {
	    RenewalRemainderNoticeEntity remainderNoticeEntity = new RenewalRemainderNoticeEntity();
	    BeanUtils.copyProperties(noticeDto, remainderNoticeEntity);
	    renewalRemainderNoticeRepository.save(remainderNoticeEntity);
	} catch (Exception exception) {
	    throw new FrameworkException("Exception occured while updating the remainder notice data", exception);
	}

    }

    @Override
    public String genearateNoticeNo(Organisation organisation, Long noticeTypeId) {

	String noticeNum = null;

	Long generateSequenceNo = ApplicationContextProvider.getApplicationContext()
		.getBean(SeqGenFunctionUtility.class)
		.generateSequenceNo(MainetConstants.AdvertisingAndHoarding.ADH_SHORT_CODE, "TB_ADH_NOTICE", "notice_no",
			organisation.getOrgid(), null, null);

	FinancialYear financialYear = ApplicationContextProvider.getApplicationContext()
		.getBean(TbFinancialyearService.class).getFinanciaYearByDate(new Date());
	String licFinYear = null;
	if (financialYear != null) {
	    licFinYear = Utility.getFinancialYear(financialYear.getFaFromDate(), financialYear.getFaToDate());
	}

	LookUp noticeTypeLookUp = CommonMasterUtility.getNonHierarchicalLookUpObject(noticeTypeId, organisation);

	StringBuilder noticeno = new StringBuilder();
	noticeno.append(organisation.getOrgShortNm()).append(MainetConstants.WINDOWS_SLASH);
	if (noticeTypeLookUp != null) {
	    noticeno.append(noticeTypeLookUp.getLookUpCode()).append(MainetConstants.WINDOWS_SLASH);
	}
	noticeno.append(licFinYear).append(MainetConstants.WINDOWS_SLASH);
	noticeno.append(String.format(MainetConstants.AdvertisingAndHoarding.FOUR_NO_PERCENTILE, generateSequenceNo));
	noticeNum = noticeno.toString();
	return noticeNum;
    }

    @Override
    public List<Object[]> getNoticeCreatedDateByAgencyIdAndOrgId(Long agencyId, Long orgId) {

	return renewalRemainderNoticeRepository.findNoticeCreatedDateByAgencyIdAndOrgId(agencyId, orgId);
    }
}
