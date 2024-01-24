package com.abm.mainet.security;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;

/**
 *
 * @author Kavali.Kiran
 *
 */
public class XSSContentCheckerFilter implements Filter {

    // private String mode;

    @Override
    public void destroy() {

    }

    @Override
    public void doFilter(final ServletRequest request, final ServletResponse response,
            final FilterChain chain) throws IOException, ServletException {

        chain.doFilter(new XSSContentCheckerInterceptor((HttpServletRequest) request), response);
    }

    @Override
    public void init(final FilterConfig arg0) throws ServletException {
        /*
         * if ((mode == null) || mode.equals("")) { setMode("DENY"); }
         */

    }

    /*
     * public void setMode(final String mode) { this.mode = mode; }
     */

}
