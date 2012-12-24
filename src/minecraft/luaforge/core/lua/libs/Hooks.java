package luaforge.core.lua.libs;

import luaforge.core.api.LuaMethod;
import luaforge.core.lua.LuaEnvironment;
import luaforge.luaj.vm2.LuaValue;
import luaforge.luaj.vm2.Varargs;

public class Hooks {
    
    @LuaMethod(name = "loader")
    public static Varargs beforeMinecraft(Varargs args, LuaEnvironment env) {
        env.startup[0] = args.arg1().checkfunction();
        return LuaValue.NONE;
    }
    
    @LuaMethod(name = "loader")
    public static Varargs preLoad(Varargs args, LuaEnvironment env) {
        env.startup[1] = args.arg1().checkfunction();
        return LuaValue.NONE;
    }
    
    @LuaMethod(name = "loader")
    public static Varargs load(Varargs args, LuaEnvironment env) {
        env.startup[2] = args.arg1().checkfunction();
        return LuaValue.NONE;
    }
    
    @LuaMethod(name = "loader")
    public static Varargs postLoad(Varargs args, LuaEnvironment env) {
        env.startup[3] = args.arg1().checkfunction();
        return LuaValue.NONE;
    }
    
    @LuaMethod(name = "loader")
    public static Varargs serverStarting(Varargs args, LuaEnvironment env) {
        env.startup[4] = args.arg1().checkfunction();
        return LuaValue.NONE;
    }
    
}
