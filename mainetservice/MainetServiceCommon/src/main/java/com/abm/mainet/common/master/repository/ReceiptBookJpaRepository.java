package com.abm.mainet.common.master.repository;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;

import com.abm.mainet.common.domain.BookReceiptEntity;

public interface ReceiptBookJpaRepository
        extends PagingAndSortingRepository<BookReceiptEntity, Long>, CrudRepository<BookReceiptEntity, Long> {

    @Query("select b.rbId, b.bookreceiptNo,b.bookreceiptNoFrom,b.bookreceiptNoTo,b.totalbookReceiptNo, b.faYearId,b.empId from BookReceiptEntity b  where b.orgId=:orgId")
    List<Object[]> getAllReceiptBookData(@Param("orgId") Long orgId);

    @Query("select b from BookReceiptEntity b where b.orgId=:orgId and b.empId=:empId "
            + " and b.faYearId =:faYearId  ")
    List<BookReceiptEntity> findReceiptBookData(@Param("empId") Long empId,
            @Param("faYearId") Long faYearId, @Param("orgId") Long orgId);

}
