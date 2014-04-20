package com.adarrivi.verifier;

import java.beans.BeanInfo;
import java.beans.IntrospectionException;
import java.beans.Introspector;
import java.beans.PropertyDescriptor;
import java.lang.reflect.Field;
import java.lang.reflect.Method;

import com.adarrivi.verifier.sampleclass.ChildStub;

public class TestUtil {

    private TestUtil() {
        super();
    }

    public static PropertyDescriptor getPropertyDescriptor(String fieldName) {
        try {
            BeanInfo beanInfo = Introspector.getBeanInfo(ChildStub.class);
            for (PropertyDescriptor pd : beanInfo.getPropertyDescriptors()) {
                if (fieldName.equals(pd.getName())) {
                    return pd;
                }
            }
        } catch (IntrospectionException e) {
            throw new AssertionError(e);
        }
        throw new AssertionError("Could not find the property descriptor");
    }

    public static Field getSampleAccessibleField() {
        Field field = ChildStub.class.getDeclaredFields()[0];
        field.setAccessible(true);
        return field;
    }

    public static Method getSampleMethod() {
        return ChildStub.class.getDeclaredMethods()[0];
    }

}
