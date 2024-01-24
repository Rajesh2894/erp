/**
 * 
 */
package com.abm.mainet.common.service;

import java.util.ArrayList;
import java.util.List;

import javax.jws.WebService;
import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;

import org.apache.log4j.Logger;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.RequestBody;

import com.abm.mainet.common.constant.MainetConstants;
import com.abm.mainet.common.domain.DuplicateBillEntity;
import com.abm.mainet.common.dto.DuplicateBillDTO;
import com.abm.mainet.common.exception.FrameworkException;
import com.abm.mainet.common.repository.DuplicateBillRepository;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

/**
 * @author Saiprasad.Vengurlekar
 *
 */

@Service
@WebService(endpointInterface = "com.abm.mainet.common.service.IDuplicateBillService")
@Api(value = "/duplicateBillService")
@Path("/duplicateBillService")
public class DuplicateBillServiceImpl implements IDuplicateBillService {

	@Autowired
	private DuplicateBillRepository repository;

	private static final Logger LOGGER = Logger.getLogger(DuplicateBillServiceImpl.class);

	@Override
	@POST
	@Path("/saveDuplicateBill")
	@Transactional
	public String save(@RequestBody final List<DuplicateBillDTO> list) {

		String responseStatus = MainetConstants.WebServiceStatus.SUCCESS;
		List<DuplicateBillEntity> entityList = new ArrayList<>();
		try {
			if (!CollectionUtils.isEmpty(list)) {
				list.forEach(dto -> {
					DuplicateBillEntity entity = new DuplicateBillEntity();
					BeanUtils.copyProperties(dto, entity);
					entityList.add(entity);
				});
				repository.save(entityList);
				return responseStatus;
			}
		} catch (Exception e) {
			LOGGER.error("Exception ocours to save Duplicate Bill " + e);
			responseStatus = MainetConstants.WebServiceStatus.FAIL;
			throw new FrameworkException("Exception occours to save Duplicate Bill method" + e);
		}
		return responseStatus;
	}

	@Override
	@Consumes("application/json")
	@POST
	@ApiOperation(value = "getBillByBillId", notes = "getBillByBillId", response = Object.class)
	@Path("/getBillByBillId/bmId/{bmId}/orgId/{orgId}/deptCode/{deptCode}/serviceCode/{serviceCode}")
	public List<DuplicateBillDTO> findByBillId(@PathParam("bmId") Long bmId,
			@PathParam(MainetConstants.Common_Constant.ORGID) Long orgId, @PathParam("deptCode") String deptCode,
			@PathParam("serviceCode") String serviceCode) {
		List<DuplicateBillDTO> list = new ArrayList<>();
		try {
			List<DuplicateBillEntity> entityList = repository.findByBillId(bmId, orgId, deptCode, serviceCode);
			if (!CollectionUtils.isEmpty(entityList)) {
				entityList.forEach(data -> {
					DuplicateBillDTO dto = new DuplicateBillDTO();
					BeanUtils.copyProperties(data, dto);
					list.add(dto);
				});

			}

		} catch (Exception e) {
			LOGGER.error("Exception ocours to findByBillId() " + e);
			throw new FrameworkException("Exception occours in Duplicate Bill findByBillId() method" + e);
		}
		return list;
	}

	@Override
	@Consumes("application/json")
	@ApiOperation(value = "getBillsByReferenceNo", notes = "getBillsByReferenceNo", response = Object.class)
	@POST
	@Path("/getBillsByReferenceNo/refNo/{refNo}/orgId/{orgId}/deptCode/{deptCode}")
	public List<DuplicateBillDTO> findByReferenceNo(@PathParam("refNo") String refNo,
			@PathParam(MainetConstants.Common_Constant.ORGID) Long orgId, @PathParam("deptCode") String deptCode) {
		List<DuplicateBillDTO> list = new ArrayList<>();
		try {
			List<DuplicateBillEntity> entityList = repository.findByRefNo(refNo, orgId, deptCode);
			if (!CollectionUtils.isEmpty(entityList)) {
				entityList.forEach(data -> {
					DuplicateBillDTO dto = new DuplicateBillDTO();
					BeanUtils.copyProperties(data, dto);
					list.add(dto);
				});

			}
		} catch (Exception e) {
			LOGGER.error("Exception ocours to findByReferenceNo() " + e);
			throw new FrameworkException("Exception occours in Duplicate Bill findByReferenceNo() method" + e);
		}

		return list;
	}

	@Override
	public List<DuplicateBillDTO> findByRefNoAndYearId(String referenceId, Long orgId, Long yearId) {
		List<DuplicateBillDTO> dtoList = new ArrayList<DuplicateBillDTO>();;
		List<DuplicateBillEntity> findByRefNoAndYearId = repository.findByRefNoAndYearId(referenceId, orgId, yearId);
		if(org.apache.commons.collections.CollectionUtils.isNotEmpty(findByRefNoAndYearId)) {
			findByRefNoAndYearId.forEach(entity ->{
				DuplicateBillDTO dto = new DuplicateBillDTO();
					BeanUtils.copyProperties(entity, dto);
					dtoList.add(dto);
			});
			return dtoList;
		}else {
			return null;
		}
		
	}

}
