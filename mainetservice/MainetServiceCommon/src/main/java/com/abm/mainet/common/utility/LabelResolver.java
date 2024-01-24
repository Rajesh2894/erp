package com.abm.mainet.common.utility;

import com.abm.mainet.common.constant.MainetConstants;

public class LabelResolver {

    private final ApplicationSession applicationSession = ApplicationSession.getInstance();

    public String getGridColumHeader(final String gridColumns) {

        final String[] gridHeaders = gridColumns.split(MainetConstants.operator.COMMA);

        String labelProperties = "[";

        for (int i = 0; i < gridHeaders.length; i++) {
            labelProperties += "'" + applicationSession.getMessage(gridHeaders[i]);

            if ((i + 1) < gridHeaders.length) {
                labelProperties += "',";
            } else {
                labelProperties += "'";
            }
        }

        labelProperties += "]";

        return labelProperties;
    }

    public String getColumHeader(final String property) {
        return applicationSession.getMessage(property);
    }

}
