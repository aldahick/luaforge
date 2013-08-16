package scriptforge.script.lib;

import scriptforge.Scriptforge;
import tiin57.lib.luaj.vm2.LuaValue;
import tiin57.lib.luaj.vm2.lib.LibFunction;
import tiin57.lib.luaj.vm2.lib.TwoArgFunction;
import tiin57.lib.luaj.vm2.lib.ZeroArgFunction;

public class luaforge extends TwoArgFunction {
	
	public luaforge() { }
	
	@Override
	public LuaValue call(LuaValue libname, LuaValue env) {
		LuaValue library = tableOf();
		for (String i : Scriptforge.luaLibs.keySet()) {
			library.set(i, Scriptforge.luaLibs.get(i));
		}
		return library;
	}
}
