package com.abm.mainet.common.utility;

import java.io.IOException;
import java.io.StringWriter;
import java.io.Writer;
import java.util.Map;

import org.codehaus.jackson.JsonFactory;
import org.codehaus.jackson.JsonGenerator;
import org.codehaus.jackson.map.ObjectMapper;

import com.abm.mainet.common.exception.FrameworkException;

public class JsonHelper {

    public static String toJsonString(final Map<String, String> jsonMap) {
        final Writer writer = new StringWriter();
        JsonGenerator jsonGenerator = null;
        ;
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
            }
        }
    }
}
