package luaforge.core.lua.libs;

import luaforge.core.Log;
import luaforge.core.api.LuaMethod;

import luaforge.luaj.vm2.LuaValue;
import luaforge.luaj.vm2.Varargs;

public class LogLib {

    @LuaMethod(name = "log")
    public static Varargs info(Varargs args) {
        Log.info(args.arg1().tojstring());
        return LuaValue.NONE;
    }

    @LuaMethod(name = "log")
    public static Varargs warning(Varargs args) {
        Log.warning(args.arg1().tojstring());
        return LuaValue.NONE;
    }

    @LuaMethod(name = "log")
    public static Varargs severe(Varargs args) {
        Log.severe(args.arg1().tojstring());
        return LuaValue.NONE;
    }
}
