package com.abm.mainet.common.util;

import com.abm.mainet.common.constant.MainetConstants;

public class LabelResolver {

    private final ApplicationSession applicationSession = ApplicationSession.getInstance();

    public String getGridColumHeader(final String gridColumns) {

        final String[] gridHeaders = gridColumns.split(MainetConstants.operator.COMA);

        String labelProperties = MainetConstants.operator.RIGHT_SQUARE_BRACKET;

        for (int i = 0; i < gridHeaders.length; i++) {
            labelProperties += MainetConstants.operator.DOUBLE_QUOTES + applicationSession.getMessage(gridHeaders[i]);

            if ((i + 1) < gridHeaders.length) {
                labelProperties += MainetConstants.operator.DOUBLEQUOTES_COMA;
            } else {
                labelProperties += MainetConstants.operator.DOUBLE_QUOTES;
            }
        }

        labelProperties += MainetConstants.operator.LEFT_SQUARE_BRACKET;

        return labelProperties;
    }

    public String getColumHeader(final String property) {
        return applicationSession.getMessage(property);
    }

}
