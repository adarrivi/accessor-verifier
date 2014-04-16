package com.adarrivi;

import java.beans.BeanInfo;
import java.beans.IntrospectionException;
import java.beans.Introspector;
import java.beans.PropertyDescriptor;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

public class ClassMethodAccessor {

    private Class<?> aClass;
    private Collection<Field> allFields = new ArrayList<>();
    private Collection<Accessor> accessors = new ArrayList<>();
    private Map<Object, Object> instantiatedValuesMap = new HashMap<>();

    public ClassMethodAccessor(Class<?> aClass) {
        this.aClass = aClass;
    }

    public void verify(Object victim) {
        findImplementedAccessors();
        for (Accessor accessor : accessors) {
            accessor.verifyAccessors(victim);
        }
    }

    public <T> void addInstantiatedValue(Class<T> aClass, T aValue) {
        instantiatedValuesMap.put(aClass, aValue);
    }

    private void findImplementedAccessors() {
        findAllFieldsIncludingInherited();
        setAllFieldsAccessible();
        setUpAccessors();
    }

    private void findAllFieldsIncludingInherited() {
        for (Class<?> classToSearch = aClass; !isObjectClassOrNull(classToSearch); classToSearch = classToSearch.getSuperclass()) {
            allFields.addAll(getAllFieldsFromClass(classToSearch));
        }
    }

    private void setAllFieldsAccessible() {
        for (Field field : allFields) {
            field.setAccessible(true);
        }
    }

    private void setUpAccessors() {
        for (Field field : allFields) {
            Method getter = findStandardGetterFromField(field);
            Method setter = findStandardSetterFromField(field);
            Accessor accessor = new Accessor(field, getter, setter);
            Object instantiatedValue = instantiatedValuesMap.get(field.getType());
            if (instantiatedValue != null) {
                accessor.setValueInstance(instantiatedValue);
            } else {
                accessor.findValueInstance();
            }
            accessors.add(accessor);
        }
    }

    private Method findStandardSetterFromField(Field field) {
        PropertyDescriptor propertyDescriptor = findPropertyDescriptor(field);
        if (propertyDescriptor == null) {
            return null;
        }
        return propertyDescriptor.getWriteMethod();
    }

    private Method findStandardGetterFromField(Field field) {
        PropertyDescriptor propertyDescriptor = findPropertyDescriptor(field);
        if (propertyDescriptor == null) {
            return null;
        }
        return propertyDescriptor.getReadMethod();
    }

    private boolean isObjectClassOrNull(Class<?> targetClass) {
        return targetClass == null || Object.class.equals(targetClass);
    }

    private Collection<Field> getAllFieldsFromClass(Class<?> targetClass) {
        Field[] fields = targetClass.getDeclaredFields();
        return Arrays.asList(fields);
    }

    private PropertyDescriptor findPropertyDescriptor(Field field) {
        try {
            BeanInfo info = Introspector.getBeanInfo(aClass);
            for (PropertyDescriptor pd : info.getPropertyDescriptors()) {
                if (field.getName().equals(pd.getName())) {
                    return pd;
                }
            }
        } catch (IntrospectionException ex) {
            throw new AssertionError(ex);
        }
        return null;

    }
}
