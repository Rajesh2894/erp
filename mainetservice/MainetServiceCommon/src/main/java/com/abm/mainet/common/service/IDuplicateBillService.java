/**
 * 
 */
package com.abm.mainet.common.service;

import java.util.List;

import javax.jws.WebService;
import javax.ws.rs.Consumes;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import com.abm.mainet.common.domain.DuplicateBillEntity;
import com.abm.mainet.common.dto.DuplicateBillDTO;

/**
 * @author Saiprasad.Vengurlekar
 *
 */
@WebService
@Consumes({ MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML })
@Produces(MediaType.APPLICATION_JSON)
public interface IDuplicateBillService {

	/**
	 * To Save The Duplicate Bill
	 * 
	 * @param dto
	 * @return DuplicateBillDTO
	 */
	public String save(final List<DuplicateBillDTO> list);

	/**
	 * To get Duplicate Bill By Bill Id
	 * 
	 * @param bmId
	 * @param orgId
	 * @param deptCode
	 * @param serviceCode
	 * @return List<DuplicateBillDTO>
	 */
	public List<DuplicateBillDTO> findByBillId(Long bmId, Long orgId, String deptCode, String serviceCode);

	/**
	 * To get Duplicate Bills By Reference No
	 * 
	 * @param bmId
	 * @param orgId
	 * @param deptCode
	 * @param serviceCode
	 * @return List<DuplicateBillDTO>
	 */
	public List<DuplicateBillDTO> findByReferenceNo(String refNo, Long orgId, String deptCode);
	
	List<DuplicateBillDTO> findByRefNoAndYearId(String referenceId, Long orgId, Long yearId);
}
