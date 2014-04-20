package com.adarrivi.verifier.accessor;

import java.beans.PropertyDescriptor;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.Map.Entry;

class FieldAccessor {

    private Field field;
    private Method getter;
    private Method setter;
    private Class<?> fieldClass;
    private Object valueInstance;

    FieldAccessor(Entry<Field, PropertyDescriptor> memberEntry) {
        this.field = memberEntry.getKey();
        PropertyDescriptor propertyDescriptor = memberEntry.getValue();
        this.getter = propertyDescriptor.getReadMethod();
        this.setter = propertyDescriptor.getWriteMethod();
        this.fieldClass = propertyDescriptor.getPropertyType();
    }

    FieldAccessor(Entry<Field, PropertyDescriptor> memberEntry, Object valueInstance) {
        this(memberEntry);
        this.valueInstance = valueInstance;
    }

    void findValueInstance() {
        try {
            valueInstance = fieldClass.newInstance();
        } catch (InstantiationException | IllegalAccessException e) {
            throw new AssertionError("Cannot instantiate the field type: " + fieldClass.getName()
                    + "; provide manually an instance for the given class using givenFieldInstances", e);
        }
    }

    void verify(Object victim) {
        try {
            verifyGetter(victim);
            verifySetter(victim);
        } catch (ReflectiveOperationException e) {
            throw new AssertionError(e);
        }
    }

    private void verifyGetter(Object victim) throws ReflectiveOperationException {
        if (getter == null) {
            return;
        }
        setValueInstanceIntoField(victim);
        Object getterResult = getter.invoke(victim, (Object[]) null);
        assertValueInstanceEqualsTo(getterResult, "getter");
    }

    private void verifySetter(Object victim) throws ReflectiveOperationException {
        if (setter == null) {
            return;
        }
        setValueInstanceIntoField(victim);
        setter.invoke(victim, valueInstance);
        assertValueInstanceEqualsTo(getValueFromField(victim), "setter");
    }

    private void assertValueInstanceEqualsTo(Object given, String operation) {
        if (!valueInstance.equals(given)) {
            throw new AssertionError("Verifying the " + operation + ", Expected: " + valueInstance + ", got: " + given);
        }
    }

    private void setValueInstanceIntoField(Object victim) throws ReflectiveOperationException {
        field.set(victim, valueInstance);
    }

    private Object getValueFromField(Object victim) throws ReflectiveOperationException {
        return field.get(victim);
    }

}
