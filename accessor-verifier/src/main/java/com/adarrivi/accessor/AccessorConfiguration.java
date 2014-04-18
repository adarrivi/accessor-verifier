package com.adarrivi.accessor;

import java.util.HashMap;
import java.util.Map;

public class AccessorConfiguration {

    private Map<Class<?>, Object> primitivesMap = new HashMap<>();

    public AccessorConfiguration() {
        primitivesMap.put(int.class, 232456643);
        primitivesMap.put(long.class, 566545l);
        primitivesMap.put(double.class, 666577645d);
        primitivesMap.put(boolean.class, true);
    }

    Map<Class<?>, Object> getPrimitiveInstancesMap() {
        return primitivesMap;
    }

    public AccessorConfiguration addCustomInstance(Object givenInstance) {
        primitivesMap.put(givenInstance.getClass(), givenInstance);
        return this;
    }

}
