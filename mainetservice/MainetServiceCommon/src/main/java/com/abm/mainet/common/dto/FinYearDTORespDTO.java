package com.abm.mainet.common.dto;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import com.abm.mainet.common.integration.dto.ResponseDTO;
import com.abm.mainet.common.integration.dto.WebServiceResponseDTO;

/**
 * @author umashanker.kanaujiya
 *
 */
public class FinYearDTORespDTO extends ResponseDTO implements Serializable {

    private static final long serialVersionUID = -5276786162986257466L;
    private List<FinYearDTO> finList;
    private List<String> errorList = new ArrayList<>();
    private List<WebServiceResponseDTO> webServiceResponseDTOs;

    /**
     * @return the finList
     */
    public List<FinYearDTO> getFinList() {
        return finList;
    }

    /**
     * @param finList the finList to set
     */
    public void setFinList(final List<FinYearDTO> finList) {
        this.finList = finList;
    }

    /**
     * @return the errorList
     */
    public List<String> getErrorList() {
        return errorList;
    }

    /**
     * @param errorList the errorList to set
     */
    public void setErrorList(final List<String> errorList) {
        this.errorList = errorList;
    }

    /**
     * @return the webServiceResponseDTOs
     */
    public List<WebServiceResponseDTO> getWebServiceResponseDTOs() {
        return webServiceResponseDTOs;
    }

    /**
     * @param webServiceResponseDTOs the webServiceResponseDTOs to set
     */
    public void setWebServiceResponseDTOs(
            final List<WebServiceResponseDTO> webServiceResponseDTOs) {
        this.webServiceResponseDTOs = webServiceResponseDTOs;
    }

}
