package luaforge.core.api;

import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.ArrayList;
import java.util.HashMap;
import luaforge.core.lua.LuaEnvironment;
import org.luaj.vm2.Varargs;

public class LuaClassRegistry {

    public static HashMap<String, ArrayList<Method>> methods = new HashMap<String, ArrayList<Method>>();
    public static HashMap<String, Method> tables = new HashMap<String, Method>();

    public static void register(Object target) {

        for (Method method : target.getClass().getDeclaredMethods()) {
            
            if (method.isAnnotationPresent(LuaMethod.class)) {
                checkStatic(method);
                String indexName = method.getAnnotation(LuaMethod.class).name();
                if (method.getReturnType().equals(Varargs.class)) {
                    paramEquals(0, method, Varargs.class);
                    paramEquals(1, method, LuaEnvironment.class);
                    
                    if (methods.get(indexName) == null) {
                        methods.put(indexName, new ArrayList<Method>());
                    }
                    methods.get(indexName).add(method);
                }
            } else if (method.isAnnotationPresent(LuaTable.class)) {
                checkStatic(method);
                String indexName = method.getName();
                if(method.getReturnType().equals(HashMap.class)) {
                    tables.put(indexName, method);
                }
                // TODO: Implement field scanner
            }
        }
    }
    
    private static void checkStatic(Method method) {
        if (!Modifier.isStatic(method.getModifiers())) {
            throw new IllegalArgumentException(method.getName() + " is not declared static");
        }
    }

    private static void paramEquals(int index, Method method, Class<?> clazz) {
        String statement = "Parameter " + (index + 1) + " does not have type " + clazz.getSimpleName();
        if(method.getParameterTypes().length >= (index + 1)) {
            if ( !(method.getParameterTypes()[index].equals(clazz)) ) {
                throw new IllegalArgumentException(statement);
            }
        }
    }
}
