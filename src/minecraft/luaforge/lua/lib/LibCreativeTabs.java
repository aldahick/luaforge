package luaforge.lua.lib;

import luaforge.api.LuaLib;
import net.minecraft.creativetab.CreativeTabs;
import tiin57.lib.luaj.vm2.LuaTable;
import tiin57.lib.luaj.vm2.LuaValue;
import tiin57.lib.luaj.vm2.lib.jse.CoerceJavaToLua;

@LuaLib(name="creativetabs")
public class LibCreativeTabs extends LuaTable {
	public LibCreativeTabs() {
		set("tabAllSearch", c(CreativeTabs.tabAllSearch));
		set("tabBlock", c(CreativeTabs.tabBlock));
		set("tabBrewing", c(CreativeTabs.tabBrewing));
		set("tabCombat", c(CreativeTabs.tabCombat));
		set("tabDecorations", c(CreativeTabs.tabDecorations));
		set("tabFood", c(CreativeTabs.tabFood));
		set("tabInventory", c(CreativeTabs.tabInventory));
		set("tabMaterials", c(CreativeTabs.tabMaterials));
		set("tabMisc", c(CreativeTabs.tabMisc));
		set("tabRedstone", c(CreativeTabs.tabRedstone));
		set("tabTools", c(CreativeTabs.tabTools));
		set("tabTransport", c(CreativeTabs.tabTransport));
	}
	private LuaValue c(Object obj) {
		return CoerceJavaToLua.coerce(obj);
	}
}
