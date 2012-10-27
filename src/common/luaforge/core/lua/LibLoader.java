package luaforge.core.lua;

import luaforge.core.api.LuaClassRegistry;
import luaforge.core.lua.libs.*;

public class LibLoader {
	public static void loadLibs() {
		LuaClassRegistry.register(new BlockLib());
		LuaClassRegistry.register(new RegisterLib());
	}
}
