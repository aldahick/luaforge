package luaforge.core.lua.libs;

import luaforge.core.api.LuaClassRegistry;
import luaforge.core.api.LuaMethod;

import org.luaj.vm2.LuaTable;
import org.luaj.vm2.LuaValue;
import org.luaj.vm2.Varargs;


public class BlockLib {
	@LuaMethod(name = "block")
	public static Varargs create (Varargs args) {
		if (args.istable(0)) {
			new BlockCreate(args);
		}
		return null;
	}
}
