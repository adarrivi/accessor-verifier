package com.adarrivi.verifier.accessor;

import java.util.HashMap;
import java.util.Map;

class ClassFieldsConfig {

    private Map<Class<?>, Object> noDefaultContructorInstanceMap = new HashMap<>();

    ClassFieldsConfig() {
        // TODO Create a factory to move this logic from here?
        setPrimitiveInstances();
    }

    private void setPrimitiveInstances() {
        noDefaultContructorInstanceMap.put(int.class, 232456643);
        noDefaultContructorInstanceMap.put(long.class, 566545l);
        noDefaultContructorInstanceMap.put(double.class, 666577645d);
        noDefaultContructorInstanceMap.put(boolean.class, true);
        // TODO Add remaining primitives
    }

    Map<Class<?>, Object> getNoDefaultContructorInstanceMap() {
        return noDefaultContructorInstanceMap;
    }

    ClassFieldsConfig addNoDefaultConstructorInstance(Object givenInstance) {
        noDefaultContructorInstanceMap.put(givenInstance.getClass(), givenInstance);
        return this;
    }

}
