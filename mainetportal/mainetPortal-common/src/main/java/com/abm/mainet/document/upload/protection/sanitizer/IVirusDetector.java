package com.abm.mainet.document.upload.protection.sanitizer;

import org.springframework.web.multipart.MultipartFile;

public interface IVirusDetector {
	boolean isSafe(MultipartFile fileToScan);
}
