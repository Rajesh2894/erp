package com.abm.mainet.ui;

import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpServletResponseWrapper;

public class CustomHttpServletResponseWrapper extends HttpServletResponseWrapper {
	public CustomHttpServletResponseWrapper(HttpServletResponse response) {
		super(response);
	}

	/* (non-Javadoc)
	 * @see javax.servlet.http.HttpServletResponseWrapper#encodeRedirectURL(java.lang.String)
	 */
	@Override
	public String encodeRedirectURL(String url) {
		return url;
	}
	
	
}
