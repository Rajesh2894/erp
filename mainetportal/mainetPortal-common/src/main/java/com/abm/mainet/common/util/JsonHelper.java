package com.abm.mainet.common.util;

import java.io.IOException;
import java.io.StringWriter;
import java.io.Writer;
import java.util.Map;

import org.codehaus.jackson.JsonFactory;
import org.codehaus.jackson.JsonGenerator;
import org.codehaus.jackson.map.ObjectMapper;
import org.apache.log4j.Logger;

import com.abm.mainet.common.constant.MainetConstants;
import com.abm.mainet.common.exception.FrameworkException;

public class JsonHelper {

    private static final Logger LOG = Logger.getLogger(JsonHelper.class);

    public static String toJsonString(final Map<String, String> jsonMap) {
        final Writer writer = new StringWriter();
        JsonGenerator jsonGenerator = null;
        try {
            jsonGenerator = new JsonFactory().createJsonGenerator(writer);
            final ObjectMapper mapper = new ObjectMapper();
            mapper.writeValue(jsonGenerator, jsonMap);
            jsonGenerator.close();
            return writer.toString();
        } catch (final IOException ex) {
            throw new FrameworkException("Error converting to Json", ex);
        } finally {
            try {
                if ((jsonGenerator != null) && !jsonGenerator.isClosed()) {
                    jsonGenerator.close();
                }

            } catch (final IOException e) {
                LOG.error(MainetConstants.ERROR_OCCURED, e);
            }
        }
    }
}
