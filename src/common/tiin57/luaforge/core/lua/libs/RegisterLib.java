package tiin57.luaforge.core.lua.libs;

import org.luaj.vm2.Varargs;

import tiin57.luaforge.core.api.LuaMethod;

public class RegisterLib {
	public static String modid = null;
	public static String name = null;
	public static String version = null;
	@LuaMethod(name = "registry")
	public static void registerMod(Varargs args) {
		Object[] obj = (new Object[] {
				args.arg(1).tojstring(),
				args.arg(2).tojstring(),
				args.arg(3).tojstring(),
		});
		modid = obj[0].toString();
		name = obj[1].toString();
		version = obj[2].toString();
	}
}
