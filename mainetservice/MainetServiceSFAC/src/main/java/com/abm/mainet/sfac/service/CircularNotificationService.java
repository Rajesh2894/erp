package com.abm.mainet.sfac.service;

import java.util.List;

import com.abm.mainet.sfac.dto.CircularNotificationMasterDto;

public interface CircularNotificationService {

	CircularNotificationMasterDto saveAndUpdateApplication(CircularNotificationMasterDto mastDto);
	
	public CircularNotificationMasterDto getDetailById(Long cnId);

	List<CircularNotificationMasterDto> getCircularNotification(String circularTitle, String circularNo);

	

}
