package com.abm.mainet.cms.service;

import java.util.Date;
import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.abm.mainet.cms.dao.INewsLetterSubscriptionRepository;
import com.abm.mainet.cms.domain.NewsLetterSubscription;
import com.abm.mainet.cms.dto.NewsLetterSubscriptionDTO;
import com.abm.mainet.common.constant.MainetConstants;
//import com.abm.mainet.common.exception.FrameworkException;
import com.abm.mainet.quartz.domain.QuartzSchedulerMaster;
import com.abm.mainet.smsemail.dto.EMail;
import com.abm.mainet.smsemail.service.ISMSAndEmailService;

@Service
public class NewsLetterSubscriptionService implements INewsLetterSubscriptionService {
	
	final Logger log = Logger.getLogger(NewsLetterSubscriptionService.class);

    @Autowired
    private INewsLetterSubscriptionRepository iNewsLetterSubscriptionRepository;

    @Autowired
    private ISMSAndEmailService emailService;

    @Autowired
    private ISectionService sectionService;

    @Override
    public boolean subscribeNewsLetter(NewsLetterSubscriptionDTO subscriberDetails) {
        NewsLetterSubscription subscriber = new NewsLetterSubscription();
        BeanUtils.copyProperties(subscriberDetails, subscriber);
        iNewsLetterSubscriptionRepository.save(subscriber);
        return true;

    }

    @Override
    public boolean unSubscribeNewsLetter(Long subscriptionId) {
        NewsLetterSubscription subscriberDetails = iNewsLetterSubscriptionRepository.findOne(subscriptionId);
        subscriberDetails.setSubscriptionEndtDate(new Date());
        subscriberDetails.setStatus(MainetConstants.IsLookUp.STATUS.NO);
        iNewsLetterSubscriptionRepository.save(subscriberDetails);
        return true;

    }

    @Override
    public boolean updateViewDate(Long subscriptionId) {
        NewsLetterSubscription subscriberDetails = iNewsLetterSubscriptionRepository.findOne(subscriptionId);
        subscriberDetails.setViewDate(new Date());
        iNewsLetterSubscriptionRepository.save(subscriberDetails);
        return true;

    }

    @Override
    public boolean validateSubscriber(NewsLetterSubscriptionDTO subscriberDetails) {
        boolean status = false;
        List<NewsLetterSubscription> emailList = iNewsLetterSubscriptionRepository
                .findNewsLetterSubscriptionByOrgidAndEmailIdAndStatus(
                        subscriberDetails.getOrgid(),
                        subscriberDetails.getEmailId(), MainetConstants.IsLookUp.STATUS.YES);
        if (emailList.isEmpty()) {
            status = true;
        } else {
            // If USer has Unsubscribe then allow to subscribe again
            if (emailList.get(0).getStatus().equalsIgnoreCase(MainetConstants.IsLookUp.STATUS.NO)) {
                status = true;
            }
        }

        return status;
    }

    // Method used to call from Quartz Scheduler.
    public void invokeMonthleyNewsLetter(QuartzSchedulerMaster master, List<Object> parameterList) {
        Long orgid = master.getOrgId().getOrgid();
        Long newsId = sectionService.getNewsLetterId("MONTHLYNEWSLETTER", master.getOrgId());
        if (null != newsId) {
            List<NewsLetterSubscription> emailList = iNewsLetterSubscriptionRepository
                    .findNewsLetterSubscriptionByOrgidAndStatus(orgid, MainetConstants.IsLookUp.STATUS.YES);
            EMail sendData = null;

            for (NewsLetterSubscription subscriber : emailList) {
                sendData = new EMail();
                sendData.setTo(new String[] { subscriber.getEmailId() });
                sendData.setSubject("MONTHLEY NEWSLETTER");
                sendData.setMessage(
                        "NewsLetter.html?Newsletter&newsId=" + newsId + "&subscriberId=" + subscriber.getSubscriptionID()
                                + "&langId=" + 1 + ":" + "NewsLetter.html?Newsletter&newsId=" + newsId + "&subscriberId="
                                + subscriber.getSubscriptionID() + "&langId=" + 2);

                sendData.setUnsubscribeLink("NewsLetter.html?Unsubscribe&subscriberId=" + subscriber.getSubscriptionID());
                emailService.sendHTMLNewsLetterEmail(sendData, master.getOrgId());

            }

        } else {
        	log.error("MONTHLY_NEWSLETTER section not defined");
        }
    }
}
