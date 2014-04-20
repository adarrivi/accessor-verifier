package com.adarrivi.verifier.accessor;

import static org.mockito.Matchers.any;
import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.beans.PropertyDescriptor;
import java.lang.reflect.Field;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import com.adarrivi.verifier.TestUtil;
import com.adarrivi.verifier.sampleclass.ChildStub;

@RunWith(MockitoJUnitRunner.class)
public class ClassFieldAccessorTest {

    @Mock
    private ClassFieldsConfig classFieldsConfig;
    @Mock
    private FieldAccessorFactory fieldAccessorFactory;
    @Mock
    private ClassMemberFinder classMemberFinder;
    @InjectMocks
    private ClassFieldAccessor victim;

    private Field field;
    private PropertyDescriptor propertyDescriptor;
    @Mock
    private FieldAccessor fieldAccessor;
    @Mock
    private Object instantiatedFieldValue;
    @Mock
    private Object inputObjectToVerify;

    @Test
    public void verify_FindFieldsInitializedInClassMemberFinder() {
        givenNoFields();
        whenVerify();
        thenFindFieldsShouldBeInvoked();
    }

    private void givenNoFields() {
        doReturn(Collections.emptyMap()).when(classMemberFinder).getClassMemberMap();
    }

    private void whenVerify() {
        victim.verify(inputObjectToVerify);
    }

    private void thenFindFieldsShouldBeInvoked() {
        verify(classMemberFinder).findMembers();
    }

    @Test
    public void verify_EmptyDefaultInstanceConfig_VerificationDone() {
        givenField();
        givenNoDefaultInstanceConfig(Collections.<Class<?>, Object> emptyMap());
        givenFieldAccessor();
        whenVerify();
        thenFieldAccessorSouldBeVerified();
    }

    private void givenField() {
        setFieldFromTestClass();
        setPropertyDescriptorFromField();
        doReturn(Collections.singletonMap(field, propertyDescriptor)).when(classMemberFinder).getClassMemberMap();
    }

    private void setFieldFromTestClass() {
        // Because with mockito we cannot mock final, we need to get real
        // fields/propertyDescriptors
        field = ChildStub.class.getDeclaredFields()[0];
        field.setAccessible(true);
    }

    private void setPropertyDescriptorFromField() {
        propertyDescriptor = TestUtil.getPropertyDescriptor(field.getName());
    }

    private void givenNoDefaultInstanceConfig(Map<Class<?>, Object> noDefaultMap) {
        when(classFieldsConfig.getFieldInstancesMap()).thenReturn(noDefaultMap);
    }

    @SuppressWarnings("unchecked")
    private void givenFieldAccessor() {
        when(fieldAccessorFactory.getNewAccessor(any(Entry.class))).thenReturn(fieldAccessor);
    }

    private void thenFieldAccessorSouldBeVerified() {
        verify(fieldAccessor).verify(inputObjectToVerify);
    }

    @Test
    public void verify_DefaultInstanceConfig_VerificationDone() {
        givenField();
        Map<Class<?>, Object> singletonMap = new HashMap<>();
        singletonMap.put(field.getType(), instantiatedFieldValue);
        givenNoDefaultInstanceConfig(singletonMap);
        givenFieldAccessorForNoDefaultInstantiatedValue();
        whenVerify();
        thenFieldAccessorSouldBeVerified();
    }

    @SuppressWarnings("unchecked")
    private void givenFieldAccessorForNoDefaultInstantiatedValue() {
        when(fieldAccessorFactory.getNewAccessorWithValueInstance(any(Entry.class), eq(instantiatedFieldValue))).thenReturn(fieldAccessor);
    }
}