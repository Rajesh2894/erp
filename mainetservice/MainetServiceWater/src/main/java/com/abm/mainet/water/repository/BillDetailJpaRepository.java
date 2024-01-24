package com.abm.mainet.water.repository;

import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import com.abm.mainet.water.domain.TbWtBillDetEntity;

/**
 * @author Rahul.Yadav
 *
 */
@Repository
public interface BillDetailJpaRepository extends
        PagingAndSortingRepository<TbWtBillDetEntity, Long> {


}
