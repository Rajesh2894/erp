package com.abm.mainet.brms.core.exception;

import org.apache.log4j.Logger;

import com.abm.mainet.common.exception.FrameworkException;

/**
 * 
 * @author Vivek.Kumar
 * @since 30 May 2016
 */
public class RuleNotFoundException extends FrameworkException
{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -8039632551918356654L;
	
	private static final Logger LOGGER  = Logger.getLogger(RuleNotFoundException.class);

	public RuleNotFoundException()
	{
		super("Rule Not Found Exception");
	}

	public RuleNotFoundException(String message, Throwable cause)
	{
		super(message, cause);
		
		LOGGER.error(message, cause);
	}

	public RuleNotFoundException(String message)
	{
		super(message);
		
		LOGGER.error(message);
	}

	public RuleNotFoundException(Throwable cause)
	{
		super(cause);
		
		LOGGER.error(cause);
	}
}
