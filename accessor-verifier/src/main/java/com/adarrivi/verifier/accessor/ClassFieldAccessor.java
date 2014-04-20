package com.adarrivi.verifier.accessor;

import java.beans.PropertyDescriptor;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Map;
import java.util.Map.Entry;

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
        classMemberFinder.findMembers();
        createAccessors();
        verifyAllAccessors(victim);
    }

    private void createAccessors() {
        Map<Class<?>, Object> noDefaultContructorInstanceMap = classFieldsConfig.getFieldInstancesMap();
        for (Entry<Field, PropertyDescriptor> memberEntry : classMemberFinder.getClassMemberMap().entrySet()) {
            Object instantiatedValue = noDefaultContructorInstanceMap.get(memberEntry.getValue().getPropertyType());
            FieldAccessor fieldAccessor = null;
            if (instantiatedValue != null) {
                fieldAccessor = fieldAccessorFactory.getNewAccessorWithValueInstance(memberEntry, instantiatedValue);
            } else {
                fieldAccessor = fieldAccessorFactory.getNewAccessor(memberEntry);
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
