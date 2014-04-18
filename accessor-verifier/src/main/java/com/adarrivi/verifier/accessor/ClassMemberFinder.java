package com.adarrivi.verifier.accessor;

import java.beans.BeanInfo;
import java.beans.IntrospectionException;
import java.beans.Introspector;
import java.beans.PropertyDescriptor;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;

public class ClassMemberFinder {
    private Class<?> givenClass;
    private Collection<Field> allFields = new ArrayList<>();

    public ClassMemberFinder(Class<?> givenClass) {
        this.givenClass = givenClass;
    }

    void findFields() {
        findAllFieldsIncludingInherited();
        setAllFieldsAccessible();
    }

    private void findAllFieldsIncludingInherited() {
        for (Class<?> classToSearch = givenClass; !isObjectClassOrNull(classToSearch); classToSearch = classToSearch.getSuperclass()) {
            allFields.addAll(getAllFieldsFromClass(classToSearch));
        }
    }

    private boolean isObjectClassOrNull(Class<?> targetClass) {
        return targetClass == null || Object.class.equals(targetClass);
    }

    private Collection<Field> getAllFieldsFromClass(Class<?> targetClass) {
        Field[] fields = targetClass.getDeclaredFields();
        return Arrays.asList(fields);
    }

    private void setAllFieldsAccessible() {
        for (Field field : allFields) {
            field.setAccessible(true);
        }
    }

    Method findStandardSetterFromField(Field field) {
        PropertyDescriptor propertyDescriptor = findPropertyDescriptor(field);
        if (propertyDescriptor == null) {
            return null;
        }
        return propertyDescriptor.getWriteMethod();
    }

    Method findStandardGetterFromField(Field field) {
        PropertyDescriptor propertyDescriptor = findPropertyDescriptor(field);
        if (propertyDescriptor == null) {
            return null;
        }
        return propertyDescriptor.getReadMethod();
    }

    private PropertyDescriptor findPropertyDescriptor(Field field) {
        try {
            BeanInfo info = Introspector.getBeanInfo(givenClass);
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

    Collection<Field> getFields() {
        return allFields;
    }
}
