package com.abm.mainet.common.utility;

import java.beans.IntrospectionException;
import java.beans.Introspector;
import java.beans.PropertyDescriptor;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.function.Predicate;
import java.util.stream.Collectors;

import org.apache.log4j.Logger;

/**
 * Provides utility methods which use java reflection to provide various
 * functionalities 
 * @author Vardan.Savarde
 *
 */
public final class ReflectionUtils {

    private static final Logger LOGGER = Logger.getLogger(ReflectionUtils.class);

    /**
     * Returns the name of the properties of the bean which have value as null
     * @param bean the bean whose properties are to be checked 
     * @param claxx the type of the bean
     * @return List<String> containing the property names
     * @throws IntrospectionException  if bean introspection fails
     */
    public final static <T> List<String> getNullPropertiesList(Object bean, Class<T> claxx) throws IntrospectionException {
    	if(bean == null || claxx == null) {
    		return new ArrayList<String>(0);
    	}
        PropertyDescriptor[] propDescArr = Introspector.getBeanInfo(claxx, Object.class).getPropertyDescriptors();

        return Arrays.stream(propDescArr)
          .filter(nulls(bean))
          .map(PropertyDescriptor::getName)
          .collect(Collectors.toList());
    }
    
    /**
     * Checks whether any of the properties of this bean has been set to not null values
     * @param bean the bean to be checked
     * @param claxx the type of the bean
     * @return true if any of the bean properties is not null else false. It returns false if any of the parameters are null
     * @throws IntrospectionException if bean introspection fails
     */
    public final static <T> boolean isBeanPopulated(Object bean, Class<T> claxx) throws IntrospectionException {
    	if(bean == null || claxx == null) {
    		return false;
    	}
        PropertyDescriptor[] propDescArr = Introspector.getBeanInfo(claxx, Object.class).getPropertyDescriptors();

        return Arrays.stream(propDescArr)
          .filter(notnulls(bean))
          .map(PropertyDescriptor::getName)
          .collect(Collectors.counting()) > 0;
    }

    private static Predicate<PropertyDescriptor> nulls(Object bean) {
        return pd -> {
            boolean result = false;
            try {
                Method getterMethod = pd.getReadMethod();
                result = (getterMethod != null && getterMethod.invoke(bean) == null);
            } catch (Exception e) {
            	LOGGER.error("error invoking getter method");
            }
            return result;
        };
    }
    
    private static Predicate<PropertyDescriptor> notnulls(Object bean) {
        return pd -> {
            boolean result = false;
            try {
                Method getterMethod = pd.getReadMethod();
                result = (getterMethod != null && getterMethod.invoke(bean) != null);
            } catch (Exception e) {
            	LOGGER.error("error invoking getter method");
            }
            return result;
        };
    }
}