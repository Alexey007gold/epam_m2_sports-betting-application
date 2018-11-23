package test;

import com.epam.training.sportsbetting.service.DataService;
import com.epam.training.sportsbetting.service.impl.DataServiceImpl;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.Arrays;

public class TestUtils {

    private TestUtils() {
    }

    public static Object invokePrivateMethod(Object target, String methodName, Object... args) throws Exception {
        Class<?>[] classArray = Arrays.stream(args).map(Object::getClass).toArray(Class[]::new);
        return invokePrivateMethod(target, methodName, classArray, args);
    }

    public static Object invokePrivateMethod(Object target, String methodName, Class<?>[] argTypes, Object... args) throws Exception {
        Method method = target.getClass().getDeclaredMethod(methodName, argTypes);
        method.setAccessible(true);
        return method.invoke(target, args);
    }

    public static void setPrivateField(Object target, String fieldName, Object value) throws Exception {
        Field declaredField = target.getClass().getDeclaredField(fieldName);
        declaredField.setAccessible(true);
        declaredField.set(target, value);
    }

    public static void setPrivateFinalField(Class<?> target, String fieldName, Object value) throws Exception {
        Field declaredField = target.getDeclaredField(fieldName);
        declaredField.setAccessible(true);

        Field modifiersField = Field.class.getDeclaredField("modifiers");
        modifiersField.setAccessible(true);
        modifiersField.setInt(declaredField, declaredField.getModifiers() & ~Modifier.FINAL);

        declaredField.set(target, value);
    }

    public static DataService getNewDataServiceInstance() throws InstantiationException, IllegalAccessException, java.lang.reflect.InvocationTargetException {
        Constructor<DataServiceImpl> declaredConstructor =
                (Constructor<DataServiceImpl>) DataServiceImpl.class.getDeclaredConstructors()[0];
        declaredConstructor.setAccessible(true);
        return declaredConstructor.newInstance();
    }
}
