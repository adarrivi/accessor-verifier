package com.adarrivi.verifier.accessor;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Map;

class ClassFieldAccessor {

    private ClassFieldsConfig classFieldsConfig;
    private FieldAccessorFactory fieldAccessorFactory;
    private ClassMemberFinder classMemberFinder;

    private Collection<FieldAccessor> fieldAccessors = new ArrayList<>();

    ClassFieldAccessor(ClassMemberFinder classMemberFinder, ClassFieldsConfig classFieldsConfig, FieldAccessorFactory fieldAccessorFactory) {
        this.classMemberFinder = classMemberFinder;
        this.classFieldsConfig = classFieldsConfig;
        this.fieldAccessorFactory = fieldAccessorFactory;
    }

    void verify(Object victim) {
        classMemberFinder.findFields();
        createAccessors();
        verifyAllAccessors(victim);
    }

    private void createAccessors() {
        Map<Class<?>, Object> noDefaultContructorInstanceMap = classFieldsConfig.fidlInstancesMap();
        for (Field field : classMemberFinder.getFields()) {
            Method getter = classMemberFinder.findStandardGetterFromField(field);
            Method setter = classMemberFinder.findStandardSetterFromField(field);
            Object instantiatedValue = noDefaultContructorInstanceMap.get(field.getType());
            FieldAccessor fieldAccessor = null;
            if (instantiatedValue != null) {
                fieldAccessor = fieldAccessorFactory.getNewAccessorWithValueInstance(field, getter, setter, instantiatedValue);
            } else {
                fieldAccessor = fieldAccessorFactory.getNewAccessor(field, getter, setter);
            }
            fieldAccessors.add(fieldAccessor);
        }
    }

    private void verifyAllAccessors(Object victim) {
        for (FieldAccessor fieldAccessor : fieldAccessors) {
            fieldAccessor.verify(victim);
        }
    }
}
