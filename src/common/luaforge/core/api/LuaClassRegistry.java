package luaforge.core.api;

import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import luaforge.core.Log;
import luaforge.core.lua.LuaIndexMethodLoader;
import luaforge.core.lua.libs.LogLib;

import org.luaj.vm2.Globals;
import org.luaj.vm2.LuaValue;
import org.luaj.vm2.Varargs;
import org.luaj.vm2.compiler.LuaC;
import org.luaj.vm2.lib.Bit32Lib;
import org.luaj.vm2.lib.CoroutineLib;
import org.luaj.vm2.lib.DebugLib;
import org.luaj.vm2.lib.PackageLib;
import org.luaj.vm2.lib.StringLib;
import org.luaj.vm2.lib.TableLib;
import org.luaj.vm2.lib.jse.JseBaseLib;
import org.luaj.vm2.lib.jse.JseIoLib;
import org.luaj.vm2.lib.jse.JseMathLib;
import org.luaj.vm2.lib.jse.JseOsLib;

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

                    if (method.getParameterTypes().length != 1) {
                        throw new IllegalArgumentException(methodName + " requires 1 parameter with type Varargs");
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
