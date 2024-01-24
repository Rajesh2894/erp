/**
 * 
 */
package com.abm.mainet.asset.repository;

import java.math.BigDecimal;
import java.util.Date;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.springframework.stereotype.Repository;

import com.abm.mainet.asset.domain.AssetInformation;
import com.abm.mainet.common.exception.FrameworkException;

/**
 * Repository Implementation Class for Asset Information
 * 
 * @author sarojkumar.yadav
 *
 */
@Repository
public class AssetInformationRepoRevImpl implements AssetInformationRevRepoCustom {

    @PersistenceContext
    private EntityManager entityManager;

  
}
