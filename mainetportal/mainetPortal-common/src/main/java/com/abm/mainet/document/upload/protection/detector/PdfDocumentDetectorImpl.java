package com.abm.mainet.document.upload.protection.detector;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Arrays;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.itextpdf.text.pdf.PdfArray;
import com.itextpdf.text.pdf.PdfDictionary;
import com.itextpdf.text.pdf.PdfName;
import com.itextpdf.text.pdf.PdfReader;

/**
 * Implementation of the detector for Adobe PDF document.
 * 
 *
 */
public class PdfDocumentDetectorImpl implements DocumentDetector {

	/** LOGGER */
	private static final Logger LOG = LoggerFactory.getLogger(PdfDocumentDetectorImpl.class);

	/**
	 * {@inheritDoc}
	 *
	 * @see eu.righettod.poc.detector.DocumentDetector#isSafe(java.io.File)
	 */
	@Override
	public boolean isSafe(File f) {
		boolean safeState = false;
		try {
			if ((f != null) && f.exists()) {
				byte[] pdfSignature = {37, 80, 68, 70};
	            try (InputStream inputStream = new FileInputStream(f);) {
	                byte[] header = new byte[4];
	                inputStream.read(header);
	                if (Arrays.equals(header, pdfSignature)) {
	                	inputStream.close();
	                    return true;
	                } else {
	                	inputStream.close();
	                	return false;
	                }
	            } catch (IOException ex) {
	            	return false;
	            }				
			}
		} catch (Exception e) {
			safeState = false;
			LOG.warn("Error during Pdf file analysis !", e);
		}
		return safeState;
	}

}
