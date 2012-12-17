package luaforge.core.lua.libs;

import cpw.mods.fml.common.registry.LanguageRegistry;
import java.util.HashMap;
import luaforge.core.api.LuaMethod;
import luaforge.luaj.vm2.LuaTable;
import luaforge.luaj.vm2.LuaValue;
import luaforge.luaj.vm2.Varargs;

public class CoreLib {
    
    public static HashMap<String, CustomTab> tabs = new HashMap<String, CustomTab>();

    @LuaMethod
    public static Varargs createItemStack(Varargs args) { // TODO: document createItemStack on the wiki
        LuaTable t = LuaValue.tableOf();
        t.set(LuaValue.valueOf("id"), LuaValue.valueOf(args.arg1().checkint()));
        t.set(LuaValue.valueOf("amount"), LuaValue.valueOf(args.arg(2).optint(1)));
        t.set(LuaValue.valueOf("metadata"), LuaValue.valueOf(args.arg(3).optint(0)));
        return t;
    }
    
    @LuaMethod
    public static Varargs createCreativeTab(Varargs args) { // TODO: Document createCreativeTab on the wiki
        String internalTabName = args.arg1().checkjstring();
        CustomTab t = new CustomTab(internalTabName, ReferenceLib.getItemStack(args.arg(2).checkint()));
        LanguageRegistry.instance().addStringLocalization("itemGroup." + internalTabName, "en_US", args.arg(3).checkjstring());
        tabs.put(internalTabName, t);
        return LuaValue.valueOf(internalTabName);
    }
    
}
