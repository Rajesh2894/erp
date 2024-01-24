package com.abm.mainet.mobile.service;

import org.springframework.stereotype.Service;

import com.abm.mainet.cms.domain.EipUserContactUs;



public interface IContactUsService {
	public Boolean saveContactUsDetails(EipUserContactUs contactUs);

}
