package com.abm.mainet.common.util;

import java.lang.reflect.Field;
import java.lang.reflect.ParameterizedType;

import org.apache.log4j.Logger;

import com.abm.mainet.common.constant.MainetConstants;
import com.abm.mainet.common.exception.FrameworkException;

/**
 * @author Pranit.Mhatre
 */
public final class ReflectionUtils {
    private static final String CLASS_CANNOT_BE_NULL = "Bean class cannot be null";
    public static final String NESTED_PROPERTY_SEPARATOR = ".";
    private static final Logger LOG = Logger.getLogger(ReflectionUtils.class);

    public static Field getPropertyField(final Class<?> beanClass, final String property) throws NoSuchFieldException {
        if (beanClass == null) {
            throw new IllegalArgumentException(CLASS_CANNOT_BE_NULL);
        }

        Field field = null;
        try {
            field = beanClass.getDeclaredField(property);
        } catch (final NoSuchFieldException noSuchFieldException) {
            if (beanClass.getSuperclass() == null) {
                throw new FrameworkException("No such field available ", noSuchFieldException);
            }
            // look for the field in the superClass
            field = getPropertyField(beanClass.getSuperclass(), property);
        }
        return field;
    }

    public static Field getField(final Class<?> beanClass, String propertyPath) throws NoSuchFieldException {
        if (beanClass == null) {
            throw new IllegalArgumentException(CLASS_CANNOT_BE_NULL);
        }

        if (propertyPath.indexOf(MainetConstants.operator.RIGHT_SQUARE_BRACKET) != -1) {
            propertyPath = propertyPath.substring(0, propertyPath.indexOf(MainetConstants.operator.RIGHT_SQUARE_BRACKET));
        }

        // if the property path is simple then look for it directly on the class.
        if (propertyPath.indexOf(NESTED_PROPERTY_SEPARATOR) == -1) {
            // look if the field is declared in this class.
            return getPropertyField(beanClass, propertyPath);
        } else {
            // if the property is a compound one then split it and look for the
            // first field.
            // and recursively locate fields of fields.
            final String propertyName = propertyPath.substring(0, propertyPath.indexOf(NESTED_PROPERTY_SEPARATOR));
            final Field field = getField(beanClass, propertyName);

            // try to locate sub-properties
            return getField(getTargetType(field), propertyPath.substring(propertyPath.indexOf(NESTED_PROPERTY_SEPARATOR) + 1));
        }
    }

    public static Class<?> getTargetType(final Field field) {
        // Generic type, case when we have a Collection of ?
        if (field.getGenericType() instanceof ParameterizedType) {
            final ParameterizedType type = (ParameterizedType) field.getGenericType();
            if ((type.getActualTypeArguments().length == 1) && (type.getActualTypeArguments()[0] instanceof Class)) {
                return (Class<?>) type.getActualTypeArguments()[0];
            }
        }

        return field.getType();
    }

    public static Class<?> getPropertyClass(final Class<?> beanClass, final String propertyPath) {
        try {
            final Field field = getField(beanClass, propertyPath);
            return (getTargetType(field));
        } catch (final NoSuchFieldException e) {
            throw new IllegalArgumentException(propertyPath + " is not a property of " + beanClass.getName(), e);
        }
    }

    public static boolean isFieldDeclared(final Class<?> beanClass, final String propertyPath) {
        try {
            return getField(beanClass, propertyPath) != null;
        } catch (final NoSuchFieldException ex) {
            LOG.error(MainetConstants.ERROR_OCCURED, ex);
            return false;
        }
    }

    public static Object getPropertyValue(final Object bean, final String propertyPath) throws NoSuchFieldException {
        if (bean == null) {
            throw new IllegalArgumentException(CLASS_CANNOT_BE_NULL);
        }
        final Field field = ReflectionUtils.getField(bean.getClass(), propertyPath);
        field.setAccessible(true);
        try {
            return (field.get(bean));
        } catch (final IllegalAccessException ex) {
            throw new FrameworkException("Unable to access " + bean.getClass() + MainetConstants.operator.DOT + propertyPath, ex);
        }
    }
}
