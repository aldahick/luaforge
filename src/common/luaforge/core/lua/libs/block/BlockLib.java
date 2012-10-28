package luaforge.core.lua.libs.block;

import luaforge.core.api.LuaMethod;
import org.luaj.vm2.LuaValue;
import org.luaj.vm2.Varargs;

public class BlockLib {

    @LuaMethod(name = "block")
    public static Varargs createBlock(Varargs args) {
        // TODO: Finish this
        return LuaValue.NONE;
    }

    @LuaMethod(name = "block")
    public static Varargs createBlockContainer(Varargs args) {
        // TODO: Create class BlockContainerTemplate
        return LuaValue.NONE;
    }
}
