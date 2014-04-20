package com.adarrivi.verifier.accessor;

import java.lang.reflect.Field;
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
            throw new AssertionError("Cannot instantiate the field type: " + field.getType().getName()
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
