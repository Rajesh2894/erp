package com.abm.mainet.common.dao;

public interface IAuthorizationDAO {	

	public abstract boolean isAuthUrl(String url, Long gmid, Long orgid);

}