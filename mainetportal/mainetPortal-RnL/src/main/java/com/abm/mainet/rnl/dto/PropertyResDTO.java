package com.abm.mainet.rnl.dto;

import java.io.Serializable;
import java.util.List;

import org.codehaus.jackson.annotate.JsonAutoDetect;
import org.codehaus.jackson.map.annotate.JsonDeserialize;
import org.codehaus.jackson.map.annotate.JsonSerialize;

@JsonAutoDetect
@JsonSerialize
@JsonDeserialize
public class PropertyResDTO implements Serializable {

    private static final long serialVersionUID = 2397444009449919632L;
    List<EstatePropResponseDTO> estatePropResponseDTOs;

    /**
     * @return the estatePropResponseDTOs
     */
    public List<EstatePropResponseDTO> getEstatePropResponseDTOs() {
        return estatePropResponseDTOs;
    }

    /**
     * @param estatePropResponseDTOs the estatePropResponseDTOs to set
     */
    public void setEstatePropResponseDTOs(final List<EstatePropResponseDTO> estatePropResponseDTOs) {
        this.estatePropResponseDTOs = estatePropResponseDTOs;
    }

}
