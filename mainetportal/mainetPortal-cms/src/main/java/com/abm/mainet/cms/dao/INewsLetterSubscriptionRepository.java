package com.abm.mainet.cms.dao;

import java.util.List;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.abm.mainet.cms.domain.NewsLetterSubscription;

@Repository
public interface INewsLetterSubscriptionRepository extends CrudRepository<NewsLetterSubscription, Long> {

    NewsLetterSubscription findNewsLetterSubscriptionByEmailIdAndOrgid(String emailId, Long orgid);

    List<NewsLetterSubscription> findNewsLetterSubscriptionByOrgidAndEmailIdAndStatus(Long orgid, String email, String status);

    List<NewsLetterSubscription> findNewsLetterSubscriptionByOrgidAndStatus(Long orgid, String status);
}
