package luaforge.core.lua;

import java.lang.reflect.Method;
import org.luaj.vm2.LuaValue;

public class LuaBaseMethodLoader extends LuaMethodLoader {

    public LuaBaseMethodLoader(LuaEnvironment luaEnv, Method... methods) {
        super(luaEnv);
        this.methods = methods;
    }

    @Override
    public LuaValue call(LuaValue env) {
        globals = env.checkglobals();
        for (int i = 0; i < methods.length; i++) {
            env.set(methods[i].getName(), new LuaFunctionLoader(i, methods[i].getName()));
        }
        return env;

    }
}
