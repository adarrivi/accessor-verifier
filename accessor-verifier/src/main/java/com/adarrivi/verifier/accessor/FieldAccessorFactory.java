package com.adarrivi.verifier.accessor;

import java.beans.PropertyDescriptor;
import java.lang.reflect.Field;
import java.util.Map.Entry;

class FieldAccessorFactory {

    FieldAccessor getNewAccessor(Entry<Field, PropertyDescriptor> memberEntry) {
        FieldAccessor accessor = new FieldAccessor(memberEntry);
        accessor.findValueInstance();
        return accessor;
    }

    FieldAccessor getNewAccessorWithValueInstance(Entry<Field, PropertyDescriptor> memberEntry, Object valueInstance) {
        return new FieldAccessor(memberEntry, valueInstance);
    }
}
