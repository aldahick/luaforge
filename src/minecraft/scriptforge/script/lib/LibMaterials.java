package scriptforge.script.lib;

import net.minecraft.block.material.Material;
import net.minecraft.creativetab.CreativeTabs;
import scriptforge.api.LuaLib;
import tiin57.lib.luaj.vm2.LuaTable;
import tiin57.lib.luaj.vm2.lib.jse.CoerceJavaToLua;

@LuaLib(name="materials")
public class LibMaterials extends LuaTable {
	public LibMaterials() {
		set("rock", CoerceJavaToLua.coerce(Material.rock));
	}
}
