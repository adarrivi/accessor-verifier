package com.adarrivi.verifier.accessor;

import java.util.HashMap;
import java.util.Map;

class ClassFieldsConfig {

    private Map<Class<?>, Object> fieldInstancesMap = new HashMap<>();

    ClassFieldsConfig() {
        // TODO Create a factory to move this logic from here?
        setPrimitiveInstances();
    }

    private void setPrimitiveInstances() {
        fieldInstancesMap.put(byte.class, 3);
        fieldInstancesMap.put(short.class, 17);
        fieldInstancesMap.put(int.class, 232456643);
        fieldInstancesMap.put(long.class, 566545L);
        fieldInstancesMap.put(float.class, 4.56444f);
        fieldInstancesMap.put(double.class, 666577645d);
        fieldInstancesMap.put(boolean.class, true);
        fieldInstancesMap.put(char.class, 'b');
    }

    Map<Class<?>, Object> fidlInstancesMap() {
        return fieldInstancesMap;
    }

    ClassFieldsConfig addFieldInstance(Object givenInstance) {
        fieldInstancesMap.put(givenInstance.getClass(), givenInstance);
        return this;
    }

}
