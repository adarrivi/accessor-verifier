package com.adarrivi.accessor;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import org.junit.Assert;

class Accessor {

    private Method getter;
    private Method setter;
    private Object valueInstance;
    private Field field;

    Accessor(Field field, Method getter, Method setter) {
        this.getter = getter;
        this.setter = setter;
        this.field = field;
    }

    Accessor(Field field, Method getter, Method setter, Object valueInstance) {
        this.getter = getter;
        this.setter = setter;
        this.field = field;
        this.valueInstance = valueInstance;
    }

    void findValueInstance() {
        try {
            valueInstance = getter.getReturnType().newInstance();
        } catch (InstantiationException | IllegalAccessException e) {
            throw new AssertionError(e);
        }
    }

    void verifyAccessors(Object victim) {
        try {
            verifyGetter(victim);
            verifySetter(victim);
        } catch (IllegalAccessException | IllegalArgumentException | InvocationTargetException e) {
            throw new AssertionError(e);
        }
    }

    private void verifyGetter(Object victim) throws IllegalAccessException, IllegalArgumentException, InvocationTargetException {
        setValueInstanceIntoField(victim);
        Object getterResult = getter.invoke(victim, (Object[]) null);
        Assert.assertEquals(valueInstance, getterResult);
    }

    private void verifySetter(Object victim) throws IllegalArgumentException, IllegalAccessException, InvocationTargetException {
        setValueInstanceIntoField(victim);
        setter.invoke(victim, valueInstance);
        Object valueFromField = getValueFromField(victim);
        Assert.assertEquals(valueInstance, valueFromField);
    }

    private void setValueInstanceIntoField(Object victim) throws IllegalArgumentException, IllegalAccessException {
        field.set(victim, valueInstance);
    }

    private Object getValueFromField(Object victim) throws IllegalArgumentException, IllegalAccessException {
        return field.get(victim);
    }

}
