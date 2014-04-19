package com.adarrivi.verifier.accessor;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

class FieldAccessor {

    private Field field;
    private Method getter;
    private Method setter;
    private Object valueInstance;

    FieldAccessor(Field field, Method getter, Method setter) {
        this.getter = getter;
        this.setter = setter;
        this.field = field;
    }

    FieldAccessor(Field field, Method getter, Method setter, Object valueInstance) {
        this.getter = getter;
        this.setter = setter;
        this.field = field;
        this.valueInstance = valueInstance;
    }

    void findValueInstance() {
        try {
            valueInstance = field.getType().newInstance();
        } catch (InstantiationException | IllegalAccessException e) {
            throw new AssertionError(e);
        }
    }

    void verify(Object victim) {
        try {
            verifyGetter(victim);
            verifySetter(victim);
        } catch (IllegalAccessException | IllegalArgumentException | InvocationTargetException e) {
            throw new AssertionError(e);
        }
    }

    private void verifyGetter(Object victim) throws IllegalAccessException, IllegalArgumentException, InvocationTargetException {
        if (getter == null) {
            return;
        }
        setValueInstanceIntoField(victim);
        Object getterResult = getter.invoke(victim, (Object[]) null);
        assertValueInstanceEqualsTo(getterResult);
    }

    private void verifySetter(Object victim) throws IllegalArgumentException, IllegalAccessException, InvocationTargetException {
        if (setter == null) {
            return;
        }
        setValueInstanceIntoField(victim);
        setter.invoke(victim, valueInstance);
        assertValueInstanceEqualsTo(getValueFromField(victim));
    }

    private void assertValueInstanceEqualsTo(Object given) {
        if (!valueInstance.equals(given)) {
            throw new AssertionError("Expected: " + valueInstance + ", got: " + given);
        }
    }

    private void setValueInstanceIntoField(Object victim) throws IllegalArgumentException, IllegalAccessException {
        field.set(victim, valueInstance);
    }

    private Object getValueFromField(Object victim) throws IllegalArgumentException, IllegalAccessException {
        return field.get(victim);
    }

}
