package com.abm.mainet.mobile.dto;

import java.io.Serializable;

import org.codehaus.jackson.annotate.JsonAutoDetect;
import org.codehaus.jackson.map.annotate.JsonDeserialize;
import org.codehaus.jackson.map.annotate.JsonSerialize;

/**
 * @author umashanker.kanaujiya
 *
 */
@JsonAutoDetect
@JsonSerialize
@JsonDeserialize
public class EmployeeResponseDTO extends CommonAppResponseDTO implements Serializable {

    private static final long serialVersionUID = 6733796174035655408L;

}
