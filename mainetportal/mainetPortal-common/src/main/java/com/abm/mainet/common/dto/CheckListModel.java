package com.abm.mainet.common.dto;

import java.util.HashMap;
import java.util.Map;

import com.abm.mainet.common.constant.MainetConstants;

public class CheckListModel extends CommonModel {

    private static final long serialVersionUID = -6152149635298602525L;
    private java.lang.String applicantType;
    private java.lang.String isOutStandingPending;
    private java.lang.String isExistingConnectionOrConsumerNo;
    private java.lang.String isExistingProperty;
    private String disConnectionType;

    private java.lang.String documentGroup;

    private java.lang.String documentGroupFactor;
    private Map<String, String> factor = new HashMap<>(0);

    private CheckListModel() {

    }

    public java.lang.String getApplicantType() {
        return applicantType;
    }

    public void setApplicantType(final java.lang.String applicantType) {
        this.applicantType = applicantType;
    }

    public java.lang.String getIsOutStandingPending() {
        return isOutStandingPending;
    }

    public void setIsOutStandingPending(final java.lang.String isOutStandingPending) {
        this.isOutStandingPending = isOutStandingPending;
    }

    public java.lang.String getIsExistingConnectionOrConsumerNo() {
        return isExistingConnectionOrConsumerNo;
    }

    public void setIsExistingConnectionOrConsumerNo(final java.lang.String isExistingConnectionOrConsumerNo) {
        this.isExistingConnectionOrConsumerNo = isExistingConnectionOrConsumerNo;
    }

    public java.lang.String getIsExistingProperty() {
        return isExistingProperty;
    }

    public void setIsExistingProperty(final java.lang.String isExistingProperty) {
        this.isExistingProperty = isExistingProperty;
    }

    public java.lang.String getDocumentGroup() {
        return documentGroup;
    }

    public void setDocumentGroup(final java.lang.String documentGroup) {
        this.documentGroup = documentGroup;
    }

    public String getDisConnectionType() {
        return disConnectionType;
    }

    public void setDisConnectionType(final String disConnectionType) {
        this.disConnectionType = disConnectionType;
    }

    public java.lang.String getDocumentGroupFactor() {
        return documentGroupFactor;
    }

    public void setDocumentGroupFactor(java.lang.String documentGroupFactor) {
        this.documentGroupFactor = documentGroupFactor;
    }

    public Map<String, String> getFactor() {
        return factor;
    }

    public void setFactor(Map<String, String> factor) {
        this.factor = factor;
    }

    @Override
    public String toString() {
        final StringBuilder builder = new StringBuilder();
        builder.append("CheckListModel [");
        builder.append(super.toString());
        builder.append(", applicantType=");
        builder.append(applicantType);
        builder.append(", isOutStandingPending=");
        builder.append(isOutStandingPending);
        builder.append(", isExistingConnectionOrConsumerNo=");
        builder.append(isExistingConnectionOrConsumerNo);
        builder.append(", isExistingProperty=");
        builder.append(isExistingProperty);
        builder.append(", documentGroup=");
        builder.append(documentGroup);
        builder.append(", disConnectionType=");
        builder.append(disConnectionType);
        builder.append(MainetConstants.operator.LEFT_SQUARE_BRACKET);
        return builder.toString();
    }

}