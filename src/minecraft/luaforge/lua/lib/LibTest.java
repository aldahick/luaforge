package luaforge.lua.lib;

import luaforge.api.LuaLib;
import tiin57.lib.luaj.vm2.LuaValue;
import tiin57.lib.luaj.vm2.lib.OneArgFunction;

@LuaLib(name="test")
public class LibTest extends OneArgFunction {

	@Override
	public LuaValue call(LuaValue arg) {
		
		return LuaValue.NIL;
	}

}
