package com.abm.mainet.common.exception.service;

import java.util.Date;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.abm.mainet.common.exception.domain.ExceptionLogDtl;
import com.abm.mainet.common.exception.repository.ExceptionLogDtlRepository;
import com.abm.mainet.common.utility.UserSession;

@Service
public class SaveExceptionDetailsImpl implements SaveExceptionDetails {

	private static final Logger LOGGER = LoggerFactory.getLogger(SaveExceptionDetailsImpl.class);

	@Autowired
	private ExceptionLogDtlRepository exceptionLogDtlRepository;

	@Override
	@Transactional
	public void saveExcepionDetails(Throwable e, String methodName, String url, String fileName) {
		String endpoint = null;
		try {
			ExceptionLogDtl exceptionLogDtl = new ExceptionLogDtl();
			exceptionLogDtl.setExceptionDetail(e.getMessage());
			exceptionLogDtl.setExceptionClass(e.getClass().getCanonicalName());
			exceptionLogDtl.setFileName(fileName);
			exceptionLogDtl.setOrgId(UserSession.getCurrent().getOrganisation().getOrgid());
			exceptionLogDtl.setCreatedBy(UserSession.getCurrent().getEmployee().getEmpId());
			exceptionLogDtl.setMethodName(methodName);
			endpoint = StringUtils.substring(url, StringUtils.ordinalIndexOf(url, "/", 3) + 1); // Part of URL after
																								// port_no/
			url = StringUtils.substring(url, 0, StringUtils.ordinalIndexOf(url, "/", 3) + 1); // Part of URL upto
																								// port_no/
			exceptionLogDtl.setUrl(url);
			exceptionLogDtl.setEndpoint(endpoint);
			exceptionLogDtl.setCreatedDate(new Date());
			exceptionLogDtl.setLgIpMac(UserSession.getCurrent().getEmployee().getEmppiservername());

			exceptionLogDtlRepository.save(exceptionLogDtl);
		} catch (Exception e1) {
			LOGGER.error("Exception occured while saving exception details to DB :: " + e1);
		}

	}
}
