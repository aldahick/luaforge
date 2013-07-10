package luaforge.lua.lib;

import luaforge.api.LuaLib;
import net.minecraft.creativetab.CreativeTabs;
import tiin57.lib.luaj.vm2.LuaTable;
import tiin57.lib.luaj.vm2.lib.jse.CoerceJavaToLua;

@LuaLib(name="creativetabs")
public class LibCreativeTabs extends LuaTable {
	public LibCreativeTabs() {
		for (CreativeTabs i : CreativeTabs.creativeTabArray) {
			set(i.getTranslatedTabLabel().substring(10), CoerceJavaToLua.coerce(i));
		}
	}
}
