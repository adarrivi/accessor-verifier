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
import java.util.HashMap;
import java.util.Map;

class ClassMemberFinder {
    private Class<?> givenClass;
    private Collection<Field> allFields = new ArrayList<>();
    private Map<Field, PropertyDescriptor> classMemberMap = new HashMap<>();
    private Collection<PropertyDescriptor> propertyDescriptors = new ArrayList<>();

    ClassMemberFinder(Class<?> givenClass) {
        this.givenClass = givenClass;
    }

    Class<?> getGivenClass() {
        return givenClass;
    }

    void findMembers() {
        findAllFieldsIncludingInherited();
        setAllFieldsAccessible();
        setPropertyAccessors();
        mapPropertyDescriptorsWithFields();
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

    private void setPropertyAccessors() {
        try {
            BeanInfo info = Introspector.getBeanInfo(givenClass);
            propertyDescriptors.addAll(Arrays.asList(info.getPropertyDescriptors()));
        } catch (IntrospectionException ex) {
            throw new AssertionError(ex);
        }
    }

    private void mapPropertyDescriptorsWithFields() {
        for (Field field : allFields) {
            PropertyDescriptor propertyDescriptor = findPropertyDescriptor(field);
            if (propertyDescriptor != null) {
                classMemberMap.put(field, propertyDescriptor);
            }
        }
    }

    Map<Field, PropertyDescriptor> getClassMemberMap() {
        return classMemberMap;
    }

    Method findStandardSetterFromField(Field field) {
        PropertyDescriptor propertyDescriptor = classMemberMap.get(field);
        if (propertyDescriptor == null) {
            return null;
        }
        return propertyDescriptor.getWriteMethod();
    }

    Method findStandardGetterFromField(Field field) {
        PropertyDescriptor propertyDescriptor = classMemberMap.get(field);
        if (propertyDescriptor == null) {
            return null;
        }
        return propertyDescriptor.getReadMethod();
    }

    private PropertyDescriptor findPropertyDescriptor(Field field) {
        for (PropertyDescriptor pd : propertyDescriptors) {
            if (field.getName().equals(pd.getName())) {
                return pd;
            }
        }
        return null;
    }

}
