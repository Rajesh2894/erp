package com.abm.mainet.security;

import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.filter.GenericFilterBean;

import com.abm.mainet.common.dto.EmployeeDTO;

public class MoibleRequestValidatorFilter extends GenericFilterBean {

    private static final Logger LOG = LoggerFactory.getLogger(MoibleRequestValidatorFilter.class);
    private static JwtUtil jwtUtil = new JwtUtil();

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
	    throws IOException, ServletException {
	try {
	    HttpServletRequest httpRequest = (HttpServletRequest) request;
	    LOG.info(JwtUtil.getJwtToken() + ">>MoibleRequestValidatorFilter>>" + httpRequest.getHeader(JwtUtil.getJwtToken()));
	    LOG.info(JwtUtil.getJwtToken() + ">>MoibleRequestValidatorFilter>>" + httpRequest.getHeader("X_REQUESTED_BY"));
	    if (httpRequest.getHeader("X_REQUESTED_BY") != null && httpRequest.getHeader("X_REQUESTED_BY").indexOf("Mobile") != -1) {
		if (httpRequest.getHeader(JwtUtil.getJwtToken()) != null) {
		    EmployeeDTO user = jwtUtil.parseToken((String) httpRequest.getHeader(JwtUtil.getJwtToken()));
		    if (isEmployeeSessionValid(user, httpRequest)) {
			LOG.info("JWT TOKEN Verification SUCCESS FOR Mobile Request");
			//chain.doFilter(request, response);
		    } else {
			LOG.warn("JWT TOKEN isEmployeeSessionValid Failed FOR Mobile Request");
			// throw new FrameworkException("JWT TOKEN Verification
			// Failed FOR Mobile Request");
		    }
		} else {
		    LOG.warn("JWT TOKEN NULL Request Failed FOR Mobile Request");
		    // throw new FrameworkException("JWT TOKEN Verification
		    // Failed FOR Mobile Request");
		}
	    }
	} catch (Exception e) {
	    LOG.warn("JWT TOKEN Verification ERROR FOR Mobile Request");
	}
	chain.doFilter(request, response);
    }

    public Boolean isEmployeeSessionValid(EmployeeDTO user, HttpServletRequest httpRequest) {
	LOG.info(JwtUtil.getJwtToken() + user.getEmpId() +  ">>isEmployeeSessionValid>>" + httpRequest.getHeader("uid"));
	LOG.info(JwtUtil.getJwtToken() + user.getOrganisation().getOrgid()+  ">>isEmployeeSessionValid>>" + httpRequest.getHeader("oid"));
	Long orgId = Long.valueOf(httpRequest.getHeader("oid"));
	Long userId = Long.valueOf(httpRequest.getHeader("uid"));
	boolean validSession = false;
	if (orgId == user.getOrganisation().getOrgid() && userId == user.getEmpId()) {
	    validSession = true;
	}
	return validSession;

    }
}