package com.abm.mainet.cms.service;

import com.abm.mainet.cms.dto.NewsLetterSubscriptionDTO;

public interface INewsLetterSubscriptionService {

    boolean validateSubscriber(NewsLetterSubscriptionDTO subscriberDetails);

    boolean subscribeNewsLetter(NewsLetterSubscriptionDTO subscriberDetails);

    boolean unSubscribeNewsLetter(Long subscriptionId);

    boolean updateViewDate(Long subscriptionId);

}
