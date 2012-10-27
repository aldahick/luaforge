package tiin57.luaforge.core.lua.libs;

import org.luaj.vm2.LuaTable;
import org.luaj.vm2.LuaValue;
import org.luaj.vm2.Varargs;

import tiin57.luaforge.core.api.LuaClassRegistry;
import tiin57.luaforge.core.api.LuaMethod;
import tiin57.luaforge.forgelibs.BlockCreate;

public class BlockLib {
	@LuaMethod(name = "block")
	public static Varargs create (Varargs args) {
		if (args.istable(0)) {
			new BlockCreate(args);
		}
		return null;
	}
}
