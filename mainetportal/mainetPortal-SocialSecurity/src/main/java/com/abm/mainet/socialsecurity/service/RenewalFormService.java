/**
 * 
 */
package com.abm.mainet.socialsecurity.service;

import com.abm.mainet.socialsecurity.ui.dto.RenewalFormDto;
import com.abm.mainet.socialsecurity.ui.dto.SchemeAppEntityToDto;

/**
 * @author priti.singh
 *
 */
public interface RenewalFormService {

	public RenewalFormDto findRenewalDetails(Long applicationId, Long orgId);

	public RenewalFormDto saveRenewalDetails(final RenewalFormDto dto);

	public SchemeAppEntityToDto fetchDataOnBenef(RenewalFormDto dto);

	

}
