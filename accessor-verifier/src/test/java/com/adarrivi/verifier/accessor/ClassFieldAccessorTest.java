package com.adarrivi.verifier.accessor;

import static org.mockito.Matchers.any;
import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

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
        doReturn(Collections.emptyList()).when(classMemberFinder).getFields();
    }

    private void whenVerify() {
        victim.verify(inputObjectToVerify);
    }

    private void thenFindFieldsShouldBeInvoked() {
        verify(classMemberFinder).findFields();
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
        // Getting any field from the current class is enough.
        // Cannot mock field because is final
        field = getClass().getDeclaredFields()[0];
        doReturn(Collections.singleton(field)).when(classMemberFinder).getFields();
    }

    private void givenNoDefaultInstanceConfig(Map<Class<?>, Object> noDefaultMap) {
        when(classFieldsConfig.fidlInstancesMap()).thenReturn(noDefaultMap);
    }

    private void givenFieldAccessor() {
        when(fieldAccessorFactory.getNewAccessor(eq(field), any(Method.class), any(Method.class))).thenReturn(fieldAccessor);
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

    private void givenFieldAccessorForNoDefaultInstantiatedValue() {
        when(
                fieldAccessorFactory.getNewAccessorWithValueInstance(eq(field), any(Method.class), any(Method.class),
                        eq(instantiatedFieldValue))).thenReturn(fieldAccessor);
    }
}