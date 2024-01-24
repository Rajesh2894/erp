package com.abm.mainet.account.repository;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;

import com.abm.mainet.account.domain.TdsAcknowledgementPaymentEntity;

public interface TdsAcknowledgementPaymentMasterJpaRepository
        extends PagingAndSortingRepository<TdsAcknowledgementPaymentEntity, Long> {

    @Query("select d from TdsAcknowledgementPaymentEntity d where  d.orgId =:orgId and d.paymentId.paymentId=:paymentId")
    public List<TdsAcknowledgementPaymentEntity> checkPayIdExists(@Param("paymentId") long paymentId, @Param("orgId") long orgId);

    @Query("select v  from TdsAcknowledgementPaymentEntity v where  v.orgId =:orgId and v.paymentId.paymentId=:gridId")
    public List<TdsAcknowledgementPaymentEntity> TdsAckPaymentDetailsById(@Param("gridId") long gridId,
            @Param("orgId") Long orgId);

}
