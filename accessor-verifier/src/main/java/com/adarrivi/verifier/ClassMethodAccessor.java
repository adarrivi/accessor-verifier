package com.adarrivi.verifier;

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

import com.adarrivi.verifier.accessor.Accessor;
import com.adarrivi.verifier.accessor.AccessorFactory;

public class ClassMethodAccessor {

    private AccessorConfiguration primitiveInstanceProvider;
    private AccessorFactory accessorFactory;

    private Class<?> aClass;
    private Collection<Field> allFields = new ArrayList<>();
    private Collection<Accessor> accessors = new ArrayList<>();
    private Map<Object, Object> instantiatedValuesMap = new HashMap<>();

    public ClassMethodAccessor(Class<?> aClass, AccessorConfiguration primitiveInstanceProvider, AccessorFactory accessorFactory) {
        this.aClass = aClass;
        this.primitiveInstanceProvider = primitiveInstanceProvider;
        this.accessorFactory = accessorFactory;
    }

    public void verify(Object victim) {
        instantiatedValuesMap.putAll(primitiveInstanceProvider.getPrimitiveInstancesMap());
        findImplementedAccessors();
        for (Accessor accessor : accessors) {
            accessor.verifyAccessors(victim);
        }
    }

    public <T> void addInstantiatedValue(Class<T> givenClass, T aValue) {
        instantiatedValuesMap.put(givenClass, aValue);
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
            Object instantiatedValue = instantiatedValuesMap.get(field.getType());
            Accessor accessor = null;
            if (instantiatedValue != null) {
                accessor = accessorFactory.getNewAccessorWithValueInstance(field, getter, setter, instantiatedValue);
            } else {
                accessor = accessorFactory.getNewAccessor(field, getter, setter);
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
