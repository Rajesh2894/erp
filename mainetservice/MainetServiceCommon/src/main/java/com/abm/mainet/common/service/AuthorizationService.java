package com.abm.mainet.common.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.abm.mainet.common.dao.IAuthorizationDAO;


/**
 *  
 * @author Jugnu Pandey
 *
 */
@Service
public class AuthorizationService implements IAuthorizationService {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	@Autowired
	IAuthorizationDAO authorizationDAO;
	
	@Override
	public boolean isAuthUrl(String url, Long gmid, Long orgid) {
		return authorizationDAO.isAuthUrl(url,gmid,orgid);
	}

}
