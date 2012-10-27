package tiin57.luaforge.core.lua;

import tiin57.luaforge.core.api.LuaClassRegistry;
import tiin57.luaforge.core.lua.libs.*;

public class LibLoader {
	public static void loadLibs() {
		LuaClassRegistry.register(new BlockLib());
		LuaClassRegistry.register(new RegisterLib());
	}
}
