package com.abm.mainet.care.domain;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.codehaus.jackson.annotate.JsonAutoDetect;
import org.codehaus.jackson.map.annotate.JsonDeserialize;
import org.codehaus.jackson.map.annotate.JsonSerialize;
import org.hibernate.annotations.GenericGenerator;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@Entity
@Table(name = "TB_CARE_FEEDBACK")
@JsonAutoDetect
@JsonSerialize
@JsonDeserialize
@JsonIgnoreProperties(ignoreUnknown = true)
public class CareFeedback implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GenericGenerator(name = "MyCustomGenerator", strategy = "com.abm.mainet.common.utility.SequenceIdGenerator")
    @GeneratedValue(generator = "MyCustomGenerator")
    @Column(name = "ID")
    private Long id;

    @Column(name = "RATINGS")
    private String ratings;

    @Column(name = "TOKEN_NUMBER")
    private String tokenNumber;

    @Column(name = "RATINGS_CONTENT")
    private String ratingsContent;

    @Column(name = "RATINGS_STAR_COUNT")
    private Integer ratingsStarCount;

    @Temporal(TemporalType.DATE)
    @Column(name = "FEEDBACK_DATE")
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

    public String[] getPkValues() {
        return new String[] { "COM", "TB_CARE_FEEDBACK", "ID" };
    }

}
