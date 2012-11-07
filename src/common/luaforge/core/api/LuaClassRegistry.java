package luaforge.core.api;

import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.ArrayList;
import java.util.HashMap;
import luaforge.core.lua.LuaEnvironment;
import org.luaj.vm2.Varargs;

public class LuaClassRegistry {

    public static HashMap<String, ArrayList<Method>> methods = new HashMap<String, ArrayList<Method>>();

    public static void register(Object target) {

        for (Method method : target.getClass().getDeclaredMethods()) {
            if (method.isAnnotationPresent(LuaMethod.class)) {
                if (method.getReturnType().equals(Varargs.class)) {

                    String indexName = method.getAnnotation(LuaMethod.class).name();
                    String methodName = method.getName();

                    if (!Modifier.isStatic(method.getModifiers())) {
                        throw new IllegalArgumentException(methodName + " is not declared static");
                    }
                    int len = method.getParameterTypes().length;
                    if (len == 2) {
                        Class<?> paramType = method.getParameterTypes()[1];
                        if (!paramType.equals(LuaEnvironment.class)) {
                            throw new IllegalArgumentException(methodName + " requires the second parameter with type LuaEnvironment");
                        }
                    } else {
                        if(len != 1){
                            throw new IllegalArgumentException(method.getName() + " does not have at least 1 parameter or has to many parameters");
                        }
                    }

                    Class<?> paramType = method.getParameterTypes()[0];
                    if (!paramType.equals(Varargs.class)) {
                        throw new IllegalArgumentException(methodName + " requires 1 parameter with type Varargs");
                    }

                    if (methods.get(indexName) == null) {
                        methods.put(indexName, new ArrayList<Method>());
                    }
                    methods.get(indexName).add(method);
                }

            }
        }
    }
}
