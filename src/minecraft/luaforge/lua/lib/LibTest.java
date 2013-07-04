package luaforge.lua.lib;

import luaforge.api.LuaLib;
import tiin57.lib.luaj.vm2.LuaValue;
import tiin57.lib.luaj.vm2.lib.ZeroArgFunction;

@LuaLib(name="test")
public class LibTest extends ZeroArgFunction {

	@Override
	public LuaValue call() {
		System.out.println("I AM A FUCKING GENIUS");
		return LuaValue.NIL;
	}

}
