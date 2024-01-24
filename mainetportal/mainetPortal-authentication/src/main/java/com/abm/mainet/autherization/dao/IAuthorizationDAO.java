package com.abm.mainet.autherization.dao;

public interface IAuthorizationDAO {	

	public abstract boolean isAuthUrl(String url, Long gmid, Long orgid);

}