package com.adarrivi.verifier.accessor;

import java.lang.reflect.Field;
import java.lang.reflect.Method;

public class FieldAccessorFactory {

    FieldAccessor getNewAccessor(Field field, Method getter, Method setter) {
        FieldAccessor accessor = new FieldAccessor(field, getter, setter);
        accessor.findValueInstance();
        return accessor;
    }

    FieldAccessor getNewAccessorWithValueInstance(Field field, Method getter, Method setter, Object valueInstance) {
        return new FieldAccessor(field, getter, setter, valueInstance);
    }

}
