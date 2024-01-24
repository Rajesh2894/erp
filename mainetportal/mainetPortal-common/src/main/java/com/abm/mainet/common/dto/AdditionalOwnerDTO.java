package com.abm.mainet.common.dto;

public class AdditionalOwnerDTO {
    private String additionalOwnerFirstName;
    private String additionalOwnerMiddleName;
    private String additionalOwnerLastName;
    private Long additionalOwnertTitle;

    public String getAdditionalOwnerFirstName() {
        return additionalOwnerFirstName;
    }

    public void setAdditionalOwnerFirstName(final String additionalOwnerFirstName) {
        this.additionalOwnerFirstName = additionalOwnerFirstName;
    }

    public String getAdditionalOwnerMiddleName() {
        return additionalOwnerMiddleName;
    }

    public void setAdditionalOwnerMiddleName(final String additionalOwnerMiddleName) {
        this.additionalOwnerMiddleName = additionalOwnerMiddleName;
    }

    public String getAdditionalOwnerLastName() {
        return additionalOwnerLastName;
    }

    public void setAdditionalOwnerLastName(final String additionalOwnerLastName) {
        this.additionalOwnerLastName = additionalOwnerLastName;
    }

    public Long getAdditionalOwnertTitle() {
        return additionalOwnertTitle;
    }

    public void setAdditionalOwnertTitle(final Long additionalOwnertTitle) {
        this.additionalOwnertTitle = additionalOwnertTitle;
    }
}
