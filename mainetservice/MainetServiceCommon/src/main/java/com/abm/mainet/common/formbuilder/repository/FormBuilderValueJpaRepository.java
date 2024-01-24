/**
 *
 */
package com.abm.mainet.common.formbuilder.repository;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.abm.mainet.common.formbuilder.domain.FormBuilderValueEntity;

/**
 * @author umashanker.kanaujiya
 *
 */
@Repository
public interface FormBuilderValueJpaRepository
        extends PagingAndSortingRepository<FormBuilderValueEntity, Long> {

    @Query("select sv from FormBuilderValueEntity sv"
            + " where  sv.slLabelId=:slLabelId and sv.saApplicationId=:saApplicationId"
            + " and sv.orgId.orgid =:orgId")
    FormBuilderValueEntity findFormLabelValueData(@Param("slLabelId") Long slLabelId,
            @Param("saApplicationId") Long saApplicationId, @Param("orgId") Long orgId);

}
