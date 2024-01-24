package com.abm.mainet.common.dto;

import java.io.Serializable;
import java.util.List;

import com.abm.mainet.common.domain.ViewPrefixDetails;

public class PrefixDTO implements Serializable {

    private static final long serialVersionUID = 2764347756296458414L;

    private List<ViewPrefixDetails> viewPrefixDetailsByType;
    private List<String> allStartupPrefix;
    private List<String> nonReplicatePrefix;

    public List<ViewPrefixDetails> getViewPrefixDetailsByType() {
        return viewPrefixDetailsByType;
    }

    public void setViewPrefixDetailsByType(
            final List<ViewPrefixDetails> viewPrefixDetailsByType) {
        this.viewPrefixDetailsByType = viewPrefixDetailsByType;
    }

    public List<String> getAllStartupPrefix() {
        return allStartupPrefix;
    }

    public void setAllStartupPrefix(final List<String> allStartupPrefix) {
        this.allStartupPrefix = allStartupPrefix;
    }

    public List<String> getNonReplicatePrefix() {
        return nonReplicatePrefix;
    }

    public void setNonReplicatePrefix(final List<String> nonReplicatePrefix) {
        this.nonReplicatePrefix = nonReplicatePrefix;
    }

}
