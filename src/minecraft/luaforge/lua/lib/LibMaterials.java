package luaforge.lua.lib;

import luaforge.api.LuaLib;
import net.minecraft.block.material.Material;
import net.minecraft.creativetab.CreativeTabs;
import tiin57.lib.luaj.vm2.LuaTable;
import tiin57.lib.luaj.vm2.lib.jse.CoerceJavaToLua;

@LuaLib(name="materials")
public class LibMaterials extends LuaTable {
	public LibMaterials() {
		set("rock", CoerceJavaToLua.coerce(Material.rock));
	}
}
