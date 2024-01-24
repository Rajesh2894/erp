/**
 * 
 */
package com.abm.mainet.common.repository;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.abm.mainet.common.domain.TbReceiptDuplicateEntity;

/**
 * @author sarojkumar.yadav
 *
 */
@Repository
public interface DuplicateReceiptRepository extends CrudRepository<TbReceiptDuplicateEntity, Long>{

    @Query("FROM TbReceiptDuplicateEntity r WHERE r.rmRcpId =:reciptId AND r.rmRcpNo =:receiptNo AND r.additionalRefNo=:assNo" )
	TbReceiptDuplicateEntity findByDupRcptByRcptIdAndrcptNoAndRefNo(@Param("reciptId") Long reciptId,@Param("receiptNo") Long receiptNo,@Param("assNo") String assNo);

    
    @Query("FROM TbReceiptDuplicateEntity r WHERE r.rmRcpId =:receiptId AND r.rmRcpNo =:receiptNo AND r.applicationId=:applcationId" )
	TbReceiptDuplicateEntity findByDupRcptByRcptIdAndrcptNoAndApplId(@Param("receiptId") Long receiptId, @Param("receiptNo") Long receiptNo, @Param("applcationId") Long applcationId);

}
