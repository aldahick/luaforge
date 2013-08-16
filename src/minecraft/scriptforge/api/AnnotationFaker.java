package scriptforge.api;

import java.lang.annotation.Annotation;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.util.HashMap;

public class AnnotationFaker {
	public static <T extends Annotation> T fake(Class<T> annotation, HashMap<String, Object> settings) {
        return (T) Proxy.newProxyInstance(annotation.getClassLoader(), new Class[]{annotation}, new FakedAnnotation(settings));
    }

    private static class FakedAnnotation implements InvocationHandler {

        private HashMap<String, Object> settings;

        public FakedAnnotation(HashMap<String, Object> settings) {
            this.settings = settings;
        }

        @Override
        public Object invoke(Object o, Method method, Object[] os) throws Throwable {
            Object returnData = settings.get(method.getName());
            if (returnData == null) {
                return method.getDefaultValue();
            }
            return returnData;
        }
    }
}
