/**
 *
 */
package com.abm.mainet.common.dto;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import org.codehaus.jackson.annotate.JsonAutoDetect;
import org.codehaus.jackson.map.annotate.JsonDeserialize;
import org.codehaus.jackson.map.annotate.JsonSerialize;

/**
 * @author vishnu.jagdale
 *
 */
@JsonAutoDetect
@JsonSerialize
@JsonDeserialize
public class ApplicationStatusResponseVO implements Serializable {

    private static final long serialVersionUID = 8676078424263304177L;

    private List<ApplicationDetail> appDetailDTO = new ArrayList<>();

    private String errorCode;

    private String errorCause;

    private String errorMsg;

    private List<WebServiceResponseDTO> wsInputErrorList = null;

    /**
     * @return the appDetailDTO
     */
    public List<ApplicationDetail> getAppDetailDTO() {
        return appDetailDTO;
    }

    /**
     * @param appDetailDTO the appDetailDTO to set
     */
    public void setAppDetailDTO(final List<ApplicationDetail> appDetailDTO) {
        this.appDetailDTO = appDetailDTO;
    }

    /**
     * @return the errorCode
     */
    public String getErrorCode() {
        return errorCode;
    }

    /**
     * @param errorCode the errorCode to set
     */
    public void setErrorCode(final String errorCode) {
        this.errorCode = errorCode;
    }

    /**
     * @return the errorCause
     */
    public String getErrorCause() {
        return errorCause;
    }

    /**
     * @param errorCause the errorCause to set
     */
    public void setErrorCause(final String errorCause) {
        this.errorCause = errorCause;
    }

    /**
     * @return the errorMsg
     */
    public String getErrorMsg() {
        return errorMsg;
    }

    /**
     * @param errorMsg the errorMsg to set
     */
    public void setErrorMsg(final String errorMsg) {
        this.errorMsg = errorMsg;
    }

    /**
     * @return the wsInputErrorList
     */
    public List<WebServiceResponseDTO> getWsInputErrorList() {
        return wsInputErrorList;
    }

    /**
     * @param wsInputErrorList the wsInputErrorList to set
     */
    public void setWsInputErrorList(final List<WebServiceResponseDTO> wsInputErrorList) {
        this.wsInputErrorList = wsInputErrorList;
    }

}
