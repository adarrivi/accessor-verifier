package com.adarrivi.verifier.accessor;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

class ClassFieldsConfig {

    private Map<Class<?>, Object> fieldInstancesMap = new HashMap<>();

    ClassFieldsConfig() {
        // TODO Create a factory to move this logic from here?
        setPrimitiveInstances();
        setCollectionInstances();
        setMapInstances();
    }

    private void setPrimitiveInstances() {
        fieldInstancesMap.put(byte.class, 3);
        fieldInstancesMap.put(short.class, 17);
        fieldInstancesMap.put(int.class, 232456643);
        fieldInstancesMap.put(Integer.class, Integer.valueOf(232456643));
        fieldInstancesMap.put(long.class, 566545L);
        fieldInstancesMap.put(Long.class, Long.valueOf(566545L));
        fieldInstancesMap.put(float.class, 4.56444f);
        fieldInstancesMap.put(Float.class, Float.valueOf(4.56444f));
        fieldInstancesMap.put(double.class, 666577645d);
        fieldInstancesMap.put(Double.class, Double.valueOf(666577645d));
        fieldInstancesMap.put(boolean.class, true);
        fieldInstancesMap.put(Boolean.class, Boolean.TRUE);
        fieldInstancesMap.put(char.class, 'b');
        fieldInstancesMap.put(Character.class, Character.valueOf('b'));
    }

    private void setCollectionInstances() {
        fieldInstancesMap.put(List.class, new ArrayList<>());
        fieldInstancesMap.put(Set.class, new HashSet<>());
        fieldInstancesMap.put(Collection.class, new HashSet<>());
    }

    private void setMapInstances() {
        fieldInstancesMap.put(Map.class, new HashMap<>());
    }

    Map<Class<?>, Object> getFieldInstancesMap() {
        return fieldInstancesMap;
    }

    ClassFieldsConfig addFieldInstance(Object givenInstance) {
        fieldInstancesMap.put(givenInstance.getClass(), givenInstance);
        return this;
    }

}
