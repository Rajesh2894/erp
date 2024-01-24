/**
 * 
 */
package com.abm.mainet.asset.repository;

import org.springframework.data.repository.PagingAndSortingRepository;

import com.abm.mainet.asset.domain.AssetRegisterUploadError;

/**
 * @author satish.rathore
 *
 */
public interface AssetRegisterUploadRepo
        extends PagingAndSortingRepository<AssetRegisterUploadError, Long>, AssetRegisterUploadRepoCustom {

}
