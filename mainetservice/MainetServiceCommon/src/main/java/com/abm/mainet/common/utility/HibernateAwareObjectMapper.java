package com.abm.mainet.common.utility;

/**
 * Umashanker.kanaujiya
 */

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.hibernate4.Hibernate4Module;

public class HibernateAwareObjectMapper extends ObjectMapper {

    private static final long serialVersionUID = 4894995504469746288L;

    public HibernateAwareObjectMapper() {
        final Hibernate4Module hibernate4Module = new Hibernate4Module();
        registerModule(hibernate4Module);
    }
}
