package com.abm.mainet.care.dto;

import java.io.Serializable;
import java.util.Date;

import org.codehaus.jackson.annotate.JsonAutoDetect;
import org.codehaus.jackson.map.annotate.JsonDeserialize;
import org.codehaus.jackson.map.annotate.JsonSerialize;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonAutoDetect
@JsonSerialize
@JsonDeserialize
@JsonIgnoreProperties(ignoreUnknown = true)
public class CareFeedbackDTO implements Serializable {

    private static final long serialVersionUID = 1L;

    private Long id;
    private String ratings;
    private String tokenNumber;
    private String ratingsContent;
    private Integer ratingsStarCount;
    private Date feedbackDate;

    public Long getId() {
        return id;
    }

    public void setId(final Long id) {
        this.id = id;
    }

    public String getRatings() {
        return ratings;
    }

    public void setRatings(final String ratings) {
        this.ratings = ratings;
    }

    public String getRatingsContent() {
        return ratingsContent;
    }

    public void setRatingsContent(final String ratingsContent) {
        this.ratingsContent = ratingsContent;
    }

    public String getTokenNumber() {
        return tokenNumber;
    }

    public void setTokenNumber(final String tokenNumber) {
        this.tokenNumber = tokenNumber;
    }

    public Integer getRatingsStarCount() {
        return ratingsStarCount;
    }

    public void setRatingsStarCount(final Integer ratingsStarCount) {
        this.ratingsStarCount = ratingsStarCount;
    }

    public Date getFeedbackDate() {
        return feedbackDate;
    }

    public void setFeedbackDate(Date feedbackDate) {
        this.feedbackDate = feedbackDate;
    }

}
