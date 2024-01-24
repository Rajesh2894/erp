package com.abm.mainet.common.utility;

import java.util.Map;
import java.util.Properties;

public class WebProperties {
    private Map<String, Properties> propertiesMap;

    public static enum PROPERTIES_CATEGORY {
        ULB_DOMAIN("ulb.domain.config"), ACCESS_CONTROL("access.control.config");
        private final String value;

        PROPERTIES_CATEGORY(final String value) {
            this.value = value;
        }

        public String getValue() {
            return value;
        }
    }

    public void setPropertiesMap(final Map<String, Properties> uiProperties) {
        propertiesMap = uiProperties;
    }

    public String getProperty(final PROPERTIES_CATEGORY category, final String propertyName) {
        final Properties properties = propertiesMap.get(category.getValue());
        return properties.getProperty(propertyName);
    }

}
