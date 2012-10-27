package luaforge.core.lua;

import java.lang.reflect.Method;
import luaforge.core.Log;
import org.luaj.vm2.Globals;
import org.luaj.vm2.LuaValue;
import org.luaj.vm2.Varargs;
import org.luaj.vm2.lib.OneArgFunction;
import org.luaj.vm2.lib.VarArgFunction;

public class LuaBaseMethodLoader extends LuaMethodLoader {

    public LuaBaseMethodLoader(Method... methods) {
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
