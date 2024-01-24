package com.abm.mainet.cms.ui.model;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;
import org.springframework.web.context.WebApplicationContext;

import com.abm.mainet.cms.dto.NewsLetterSubscriptionDTO;
import com.abm.mainet.common.ui.model.AbstractFormModel;

@Component
@Scope(value = WebApplicationContext.SCOPE_SESSION)
public class NewsLetterSubscriptionModel extends AbstractFormModel {
    private static final long serialVersionUID = -392507547663234032L;

    private NewsLetterSubscriptionDTO subscription = new NewsLetterSubscriptionDTO();

    public NewsLetterSubscriptionDTO getSubscription() {
        return subscription;
    }

    public void setSubscription(NewsLetterSubscriptionDTO subscription) {
        this.subscription = subscription;
    }
}
