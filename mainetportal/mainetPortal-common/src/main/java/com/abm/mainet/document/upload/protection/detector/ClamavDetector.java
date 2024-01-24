package com.abm.mainet.document.upload.protection.detector;

import java.io.InputStream;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import com.abm.mainet.common.exception.FrameworkException;
import com.abm.mainet.common.util.ApplicationSession;
import com.abm.mainet.document.upload.protection.sanitizer.IVirusDetector;

import net.taldius.clamav.ClamAVScannerFactory;

import org.springframework.context.annotation.Primary;

/**
 * Scans the files using Open Source Anti-Virus Clamav.
 * Documentation at https://www.clamav.net
 * 
 * The following properties should be configured
 * clamav.av.host - Host name on which clamd process is running
 * clamav.av.port - Port at which clamd process is running
 * clamav.av.timeout - Timeout for connecting to clamd process
 * @author Vardan.Savarde
 *
 */
@Primary
@Component
public class ClamavDetector implements IVirusDetector {
	private static final Logger LOGGER = Logger.getLogger(ClamavDetector.class);
	
	//private ClamAVScanner scanner;
	private boolean isScanEnabled = false;
	
	public ClamavDetector() {
		initScanner();
	}
	/**
	 * @return true if no virus found else false
	 */
	@Override
	public boolean isSafe(MultipartFile fileToScan) {
		
		if(isScanEnabled) {
			boolean scanResult = false;
			try {
				//we have to return false if virus is found
				scanResult = checkVirus(fileToScan);
				return scanResult;
			} catch (Exception exp) {
				LOGGER.error("Exception while scanning for virus", exp);
			}
		}
		//return true if virus scan is disabled
		return true;
	}
	
	/**
	 * Method to scans files to check whether file is virus infected
	 *
	 * @param fileInputStream
	 * @return true if no virus found else false
	 * @throws Exception
	 */
	private boolean checkVirus(MultipartFile fileToScan) throws FrameworkException {
		//if virus scan is wrongly setup or any exception in virus scanning then return false
		boolean resScan = false;
		if (fileToScan != null) {
			InputStream file = null;
			try { 
				file = fileToScan.getInputStream();
				//return true if no virus found else false
				resScan = ClamAVScannerFactory.getScanner().performScan(file);
			}catch(Exception exc) {
				LOGGER.error("Error while reading file : " + fileToScan.getOriginalFilename(), exc);
				//if virus scan is wrongly setup or any exception in virus scanning then return false
			} finally {
				if(file != null) {
					try {
						file.close();
					}catch(Exception e) {
						LOGGER.error("Error while closing input stream", e);
					}
				}
			}
		} else {
			throw new FrameworkException("fileToScan is null");
		}
		return resScan;
	}

	/**
	 * Method to initialize clamAV scanner
	 */
	private void initScanner() {
		ApplicationSession appSession = ApplicationSession.getInstance();
		String isEnabled = "Y";
				//ApplicationSession.getInstance().getMessage("virus.scan.enabled");
		if(isEnabled != null && StringUtils.equalsIgnoreCase(isEnabled, "Y")) {
			isScanEnabled = true;
		}
		if(!isScanEnabled) {
			return;
		}
		String hostname = appSession.getMessage("clamav.av.host");
		String portStr = appSession.getMessage("clamav.av.port");
		String timeout = appSession.getMessage("clamav.av.timeout");
		
		ClamAVScannerFactory.setClamdHost(hostname); // Host ip where 'clamd' process is running
		ClamAVScannerFactory.setClamdPort(Integer.parseInt(portStr)); // Port on which 'clamd' process is listening
		ClamAVScannerFactory.setConnectionTimeout(Integer.parseInt(timeout));// Connection time out to connect 'clamd' process

		
	}

}
