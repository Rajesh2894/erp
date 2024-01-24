package com.abm.mainet.autherization.service;

import java.io.Serializable;

import org.springframework.transaction.annotation.Transactional;
/**
 * 
 * @author Jugnu Pandey
 *
 */
@Transactional(readOnly=true)

public interface IAuthorizationService extends Serializable{
	
	public boolean isAuthUrl(String key, Long gmid, Long orgid);
}
