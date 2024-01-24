package com.abm.mainet.property.dto;

import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

import org.codehaus.jackson.annotate.JsonAutoDetect;
import org.codehaus.jackson.map.annotate.JsonDeserialize;
import org.codehaus.jackson.map.annotate.JsonSerialize;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@XmlRootElement(namespace = "http://bhuiyan.cg.nic.in/", name = "ArrayOfKhasraDetails")
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonAutoDetect
@JsonSerialize
@JsonDeserialize

public class ArrayOfKhasraDetails {

    @XmlElement
    private List<KhasraDetails> KhasraDetails = new ArrayList<>(0);

    public List<KhasraDetails> getKhasraDetails() {
        return KhasraDetails;
    }

    public void setKhasraDetails(List<KhasraDetails> khasraDetails) {
        KhasraDetails = khasraDetails;
    }

}