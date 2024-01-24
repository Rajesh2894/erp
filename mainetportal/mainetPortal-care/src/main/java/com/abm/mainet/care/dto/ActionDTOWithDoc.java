package com.abm.mainet.care.dto;

import java.util.ArrayList;
import java.util.List;

import org.codehaus.jackson.map.annotate.JsonDeserialize;
import org.codehaus.jackson.map.annotate.JsonSerialize;

import com.abm.mainet.common.util.LookUp;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonSerialize
@JsonDeserialize
@JsonIgnoreProperties(ignoreUnknown = true)
public class ActionDTOWithDoc extends ActionDTO {

    /**
     * 
     */
    private static final long serialVersionUID = 1L;

    private List<String> attachementUrls = new ArrayList<>();

    private List<LookUp> attachements = new ArrayList<>();

    public List<String> getAttachementUrls() {
        return attachementUrls;
    }

    public void setAttachementUrls(List<String> attachementUrls) {
        this.attachementUrls = attachementUrls;
    }

    public ActionDTOWithDoc addAttachementUrls(String url) {
        this.attachementUrls.add(url);
        return this;
    }

    public List<LookUp> getAttachements() {
        return attachements;
    }

    public void setAttachements(List<LookUp> attachements) {
        this.attachements = attachements;
    }

    public ActionDTOWithDoc addAttachement(LookUp attachement) {
        this.attachements.add(attachement);
        return this;
    }

}