package com.abm.mainet.adh.service;

import java.util.List;

import com.abm.mainet.adh.dto.RenewalRemainderNoticeDto;
import com.abm.mainet.common.domain.Organisation;

/**
 * @author cherupelli.srikanth
 * @since 03 October 2019
 */
public interface IRenewalRemainderNoticeService {

    void saveRenewalRemainderNotice(RenewalRemainderNoticeDto noticeDto);

    void updateRenealRemainderNotice(RenewalRemainderNoticeDto noticeDto);

    String genearateNoticeNo(Organisation organisation, Long noticeTypeId);

    List<Object[]> getNoticeCreatedDateByAgencyIdAndOrgId(Long agencyId, Long orgId);
}
