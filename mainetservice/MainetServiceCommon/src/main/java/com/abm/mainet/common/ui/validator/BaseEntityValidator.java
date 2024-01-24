package com.abm.mainet.common.ui.validator;

import java.beans.IntrospectionException;
import java.beans.PropertyDescriptor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Collection;

import org.springframework.beans.BeanWrapper;
import org.springframework.beans.BeanWrapperImpl;

import com.abm.mainet.common.exception.FrameworkException;
import com.abm.mainet.common.utility.ApplicationSession;

public abstract class BaseEntityValidator<T extends Object> {
    private EntityValidationContext<T> validationContext = null;

    public ApplicationSession getApplicationSession() {
        return ApplicationSession.getInstance();
    }

    private EntityValidationContext<T> getValidationContext() {
        return validationContext;
    }

    public final Collection<String> isValid(final T entity) {
        validationContext = new EntityValidationContext<>(entity);
        performValidations(entity, validationContext);

        return validationContext.getErrors();
    }

    /**
     * To get the value of the given property of the java bean class.
     * <p>
     * The java bean class contains setter/getter methods to get value of the property. While getting the value of the property it
     * will automatically invoke getter method.
     * </p>
     * @param beanClass the {@link Class} object containing java bean class.
     * @param propertyName the {@link String} literal containing property name.
     * @return {@link Object} the value of the property if such property exists otherwise throws {@link FrameworkException}.
     */
    private final Object getPropertyVal(final Object beanClass, final String propertyName) {
        Method method = null;

        try {
            method = new PropertyDescriptor(propertyName, beanClass.getClass()).getReadMethod();
        } catch (final IntrospectionException ex) {
            throw new FrameworkException("Unable to find method :" + beanClass.getClass().getSimpleName() + "." + method);
        }

        try {
            return method.invoke(beanClass);

        } catch (IllegalAccessException | IllegalArgumentException | InvocationTargetException ex) {
            throw new FrameworkException("Method invocation failed :" + ex.getLocalizedMessage());
        }
    }

    /**
     * To perform group validation of similar kind of hierarchical look up elements. That is the property which are having same
     * 'propertyPrefix' name.
     * @param prefixPropertyName the {@link String} containing property prefix name of baseEntity.
     * @return {@link Boolean} <code>true</code> if not validation error otherwise <code>false</code>.
     */
    public final boolean performGroupValidation(final String propertyPrefix) {
        boolean valid = true;

        int level = 1;

        final BeanWrapper wrapper = new BeanWrapperImpl(validationContext.getEntity());
        boolean foundProperty = false;

        do {
            final String propertyName = propertyPrefix + String.valueOf(level);
            foundProperty = wrapper.isReadableProperty(propertyName);

            if (foundProperty) {
                final Object object = wrapper.getPropertyValue(propertyName);

                if (object != null) {
                    valid = getValidationContext().rejectIfNotSelected((Long) object, propertyName) && valid;
                }

                ++level;
            }
        } while (foundProperty);

        return valid;
    }


    
   

    /**
     * To perform group search validation of similar kind of hierarchical look up elements. That is the property which are having
     * same 'propertyPrefix' name.
     * @param prefixPropertyName the {@link String} containing property prefix name of baseEntity.
     * @return {@link Boolean} <code>true</code> if not validation error otherwise <code>false</code>.
     */
    public final boolean performSearchGroupValidation(final String propertyPrefix) {
        boolean valid = false;

        for (int level = 1; level <= 5; level++) {
            final String propertyName = propertyPrefix + String.valueOf(level);
            final Object object = getPropertyVal(validationContext.getEntity(), propertyName);

            if (object != null) {
                valid = ((Long) object != 0) || valid;
            }
        }

        return valid;
    }

    protected abstract void performValidations(T entity, EntityValidationContext<T> entityValidationContext);
}
