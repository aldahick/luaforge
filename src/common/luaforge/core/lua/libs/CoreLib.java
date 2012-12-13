package luaforge.core.lua.libs;

import luaforge.core.api.LuaMethod;
import luaforge.luaj.vm2.LuaTable;
import luaforge.luaj.vm2.LuaValue;
import luaforge.luaj.vm2.Varargs;

public class CoreLib {

    @LuaMethod
    public static Varargs createItemStack(Varargs args) { // TODO: document createItemStack on the wiki
        LuaTable t = LuaValue.tableOf();
        t.set(LuaValue.valueOf("id"), LuaValue.valueOf(args.arg1().checkint()));
        t.set(LuaValue.valueOf("amount"), LuaValue.valueOf(args.arg(2).optint(0)));
        t.set(LuaValue.valueOf("meta_data"), LuaValue.valueOf(args.arg(3).optint(1)));
        return t;
    }
    
}
