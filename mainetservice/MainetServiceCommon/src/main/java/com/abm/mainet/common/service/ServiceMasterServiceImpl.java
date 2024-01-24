package com.abm.mainet.common.service;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.jws.WebService;
import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.abm.mainet.common.constant.MainetConstants;
import com.abm.mainet.common.constant.PrefixConstants;
import com.abm.mainet.common.dao.IServiceMasterDAO;
import com.abm.mainet.common.domain.Organisation;
import com.abm.mainet.common.domain.ServiceMaster;
import com.abm.mainet.common.exception.FrameworkException;
import com.abm.mainet.common.repository.ServiceMasterRepository;
import com.abm.mainet.common.utility.CommonMasterUtility;
import com.abm.mainet.common.utility.LookUp;

import io.swagger.annotations.Api;

/**
 * @author Vivek.Kumar
 * @since 21 March 2016
 */

@Produces({ "application/xml", "application/json" })
@WebService(endpointInterface = "com.abm.mainet.common.service.ServiceMasterService")
@Path("/servicemaster")
@Api(value = "/servicemaster")
@Service
public class ServiceMasterServiceImpl implements ServiceMasterService {

	private static final Logger logger = Logger.getLogger(ServiceMasterServiceImpl.class);

	@Resource
	private ServiceMasterRepository serviceMasterRespository;

	@Resource
	private IServiceMasterDAO serviceMasterDAO;

	@Resource
	private ICFCApplicationMasterService icfcApplicationMasterService;

	private static final String CHARGE_FLAG_NOT_CONFIGURED = "Charge applicable flag is not configured to identify whether service is chargeable or not.";
	private static final String SERVICE_ACTION_URL_NOT_CONFIGURED = "Service action Url is not configured in Service Master for serviceId=";
	private static final String SERVICE_ID_CANT_BE_ZERO = "ServiceId can not be zero(0)";
	private static final String ORG_ID_CANT_BE_ZERO = "orgId can not be zero(0)";

	@Override
	public String isChargeApplicable(final long serviceId, final long orgId) {

		String isChargeApplicable = StringUtils.EMPTY;
		if (serviceId == MainetConstants.ZERO_LONG) {
			throw new FrameworkException(SERVICE_ID_CANT_BE_ZERO);
		}
		if (orgId == MainetConstants.ZERO_LONG) {
			throw new FrameworkException(ORG_ID_CANT_BE_ZERO);
		}
		final List<Object> list = serviceMasterRespository.isChargeApplicable(serviceId, orgId);
		if ((list == null) || list.isEmpty()) {
			throw new FrameworkException(CHARGE_FLAG_NOT_CONFIGURED);
		} else {
			if ((long) list.get(MainetConstants.INDEX.ZERO) == MainetConstants.ZERO_LONG) {
				isChargeApplicable = MainetConstants.Common_Constant.NO;
			} else {
				isChargeApplicable = MainetConstants.Common_Constant.YES;
			}
		}

		return isChargeApplicable;
	}

	@Override
	public List<String> getServiceActionUrlParams(final long smServiceId, final long orgId) {

		final List<String> paramList = new ArrayList<>();
		final List<String> list = serviceMasterRespository.fetchServiceShortdesc(smServiceId, orgId);
		if ((list == null) || list.isEmpty() || (list.get(MainetConstants.INDEX.ZERO) == null)) {
			throw new FrameworkException(SERVICE_ACTION_URL_NOT_CONFIGURED + smServiceId);
		} else {
			String shortCode = list.get(MainetConstants.INDEX.ZERO);
			String serviceURL = MainetConstants.URLBasedOnShortCode.valueOf(shortCode).getUrl();
			paramList.add(serviceURL);
		}
		return paramList;

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.abm.mainetservice.core.service.ServiceMasterService#getServiceByShortName
	 * (java.lang.String, long)
	 */
	@Override
	@Transactional
	public ServiceMaster getServiceByShortName(final String shortName, final long orgId) {

		final ServiceMaster master = serviceMasterRespository.getServiceMasterByShrotName(shortName, orgId);

		if (master != null) {
			return master;
		} else {
			throw new FrameworkException(SERVICE_ACTION_URL_NOT_CONFIGURED + shortName);

		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.abm.mainetservice.core.service.ServiceMasterService#
	 * findAllServicesByDepartment(java.lang.Long, java.lang.Long)
	 */
	@Override
	@Transactional(readOnly = true)
	public List<Object[]> findAllActiveServicesByDepartment(final Long orgId, final Long depId,
			final Long activeStatusId) {
		final List<Object[]> entities = serviceMasterRespository.findAllActiveServicesByDepartment(orgId,
				Long.valueOf(depId), activeStatusId);
		return entities;
	}

	@Override
	@Transactional(readOnly = true)
	public String fetchServiceShortCode(final Long serviceId, final Long orgId) {
		return serviceMasterRespository.getServiceShortCode(serviceId, orgId);
	}

	@Override
	public ServiceMaster getServiceMaster(final Long serviceId, final Long orgId) {
		return serviceMasterRespository.getServiceMaster(serviceId, orgId);
	}

	@Override
	@Transactional(readOnly = true)
	public ServiceMaster getServiceMasterByShortCode(String shortCode, Long orgId) {
		return serviceMasterDAO.getServiceMasterByShortCode(shortCode, orgId);
	}

	@Override
	@Transactional(readOnly = true)
	public List<Object[]> findAllActiveServicesForDepartment(final long orgId, final long depId) {

		final Long activeLookUpId = CommonMasterUtility.lookUpIdByLookUpCodeAndPrefix(PrefixConstants.IsLookUp.ACTIVE,
				PrefixConstants.ACN, orgId);
		System.out.println(activeLookUpId);
		final List<Object[]> entities = serviceMasterRespository.findAllServicesByDepartment(orgId, Long.valueOf(depId),
				activeLookUpId);

		return entities;

	}

	@Override
	@Transactional
	public String getServiceNameByServiceId(final long smServiceId) {
		return serviceMasterDAO.getServiceNameByServiceId(smServiceId);
	}

	@Override
	public boolean isServiceRTS(final Long serviceId, final Long orgId) {
		int count = serviceMasterRespository.getCountOfIsServiceRTS(serviceId, orgId);
		if (count > 0) {
			return true;
		} else {
			return false;
		}
	}

	@Override
	public String getProcessName(final Long smServiceId, final Long orgId) {
		String processName = null;
		final ServiceMaster serviceMaster = getServiceMaster(smServiceId, orgId);
		if (serviceMaster != null && serviceMaster.getSmProcessId() != null) {
			Organisation org = new Organisation();
			org.setOrgid(orgId);
			final LookUp processLookUp = CommonMasterUtility
					.getNonHierarchicalLookUpObject(serviceMaster.getSmProcessId(), org);
			if (processLookUp != null && !MainetConstants.CommonConstants.NA.equals(processLookUp.getLookUpCode())) {
				processName = processLookUp.getLookUpCode().toLowerCase();
			}
		}
		return processName;
	}

	@POST
	@Path("/getServiceDetailWhichIsActual/{orgId}/{depId}/{activeStatusId}/{notActualFlag}")
	@Consumes({ "application/xml", "application/json" })
	@Override
	@Transactional
	public List<Object[]> findAllActiveServicesWhichIsNotActual(@PathParam("orgId") Long orgId,
			@PathParam("depId") Long depId, @PathParam("activeStatusId") Long activeStatusId,
			@PathParam("notActualFlag") String notActualFlag) {
		List<Object[]> entities = null;
		try {
			entities = serviceMasterRespository.findAllActiveServicesWhichIsNotActual(orgId, depId, activeStatusId,
					notActualFlag);
		} catch (Exception ex) {
			logger.error("exception occur while fatching services please check inputs again", ex);
			throw new FrameworkException("exception occur while fatching services please check inputs again", ex);
		}
		return entities;
	}

	@POST
	@Path("/getDeptIdByServiceShortName/{orgId}/{serviceShortCode}")
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes({ "application/xml", "application/json" })
	@Override
	public Long getDeptIdByServiceShortName(@PathParam(value = "orgId") Long orgId,
			@PathParam(value = "serviceShortCode") String serviceShortCode) {
		logger.info("getDeptIdByServiceShortName() method execution Starts");
		if (orgId != null && orgId != 0 && !StringUtils.isEmpty(serviceShortCode)) {
			final ServiceMaster master = serviceMasterRespository.getServiceMasterByShrotName(serviceShortCode, orgId);
			if (master != null) {
				return master.getTbDepartment().getDpDeptid();
			} else {
				logger.error("exception occur while fetching service Department Id By ServiceShortName");
				throw new FrameworkException(
						"Service action Url is not configured in Service Master for service Code = "
								+ serviceShortCode);
			}
		} else {
			return orgId;
		}

	}

	@Transactional
	@Override
	@POST
	@Path("/getServiceIdByShortName/{orgId}/{serviceCode}")
	@Produces(MediaType.APPLICATION_JSON)
	public Long getServiceIdByShortName(@PathParam(value = "orgId") Long orgId,
			@PathParam(value = "serviceCode") String serviceCode) {
		Long serviceId = serviceMasterRespository.findServiceIdByShortCodeAndOrgId(serviceCode, orgId);
		if (serviceId != null) {
			return serviceId;
		} else {
			throw new FrameworkException(SERVICE_ACTION_URL_NOT_CONFIGURED + serviceCode);

		}
	}

	@Transactional
	@Override
	@POST
	@Path("/getServiceExternalFlag/{serviceId}/{orgId}")
	@Produces(MediaType.APPLICATION_JSON)
	public Map<String, String> getServiceExternalFlag(@PathParam(value = "orgId") Long orgId,
			@PathParam(value = "serviceId") Long serviceId) {
		Map<String, String> serviceDataMap = new LinkedHashMap<String, String>();
		String serviceExternalFlag = serviceMasterRespository.getServiceExternalFlag(serviceId, orgId);
		if (serviceExternalFlag != null) {
			serviceDataMap.put(MainetConstants.RightToService.EXTERNAL_FLAG, serviceExternalFlag);
		} else {
			serviceDataMap.put(MainetConstants.RightToService.EXTERNAL_FLAG, MainetConstants.FlagN);
		}
		return serviceDataMap;
	}

	@Transactional
	@Override
	@POST
	@Path("/getDeptIdAndShortCode/{orgId}/{serviceCode}")
	@Produces(MediaType.APPLICATION_JSON)
	public Map<String, String> getDeptIdAndShortCode(@PathParam(value = "orgId") Long orgId,
			@PathParam(value = "serviceCode") String serviceCode) {
		Map<String, String> serviceDataMap = new LinkedHashMap<String, String>();
		final ServiceMaster master = serviceMasterRespository.getServiceMasterByShrotName(serviceCode, orgId);
		if (master != null) {
			serviceDataMap.put(MainetConstants.Common_Constant.DEPTID,
					master.getTbDepartment().getDpDeptid().toString());
			serviceDataMap.put(MainetConstants.Common_Constant.DEPT_CODE, master.getTbDepartment().getDpDeptcode());
			return serviceDataMap;

		}
		return serviceDataMap;
	}


}
