package com.abm.mainet.common.constant;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.HashMap;

import org.apache.log4j.Logger;
import org.springframework.util.StringUtils;

/**
 * @author Pranit.Mhatre
 * @since JDK 1.7
 */
public class JspConstants extends HashMap<String, Object> {

    private static final long serialVersionUID = 7453099829660273108L;
    private static final Logger LOG = Logger.getLogger(JspConstants.class);

    public JspConstants() {
        final Class<MainetConstants> c = MainetConstants.class;
        final Field[] fields = c.getDeclaredFields();
        for (final Field field : fields) {
            final int modifier = field.getModifiers();
            if (Modifier.isPublic(modifier) && Modifier.isStatic(modifier) && Modifier.isFinal(modifier)) {
                try {
                    put(field.getName(), field.get(modifier));
                } catch (final IllegalAccessException ignored) {
                    LOG.error(MainetConstants.ERROR_OCCURED, ignored);
                }
            }
        }
    }

    @Override
    public String get(final Object key) {
        final Object value = super.get(key);

        String result = null;

        if (value instanceof Number) {
            result = String.valueOf(value);
        }

        if (!StringUtils.hasLength(result)) {
            throw new IllegalArgumentException("Check key! The key is wrong, no such constant!");
        }
        return result;
    }
}
