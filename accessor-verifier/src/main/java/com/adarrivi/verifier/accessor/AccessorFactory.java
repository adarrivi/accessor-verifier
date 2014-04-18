package com.adarrivi.verifier.accessor;

import java.lang.reflect.Field;
import java.lang.reflect.Method;

public class AccessorFactory {

    public Accessor getNewAccessor(Field field, Method getter, Method setter) {
        Accessor accessor = new Accessor(field, getter, setter);
        accessor.findValueInstance();
        return accessor;
    }

    public Accessor getNewAccessorWithValueInstance(Field field, Method getter, Method setter, Object valueInstance) {
        return new Accessor(field, getter, setter, valueInstance);
    }

}
