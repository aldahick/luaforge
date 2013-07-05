package luaforge.lua.lib;

import luaforge.api.LuaLib;
import net.minecraft.block.Block;
import tiin57.lib.luaj.vm2.LuaTable;
import tiin57.lib.luaj.vm2.LuaValue;
import tiin57.lib.luaj.vm2.lib.TwoArgFunction;
import cpw.mods.fml.common.registry.GameRegistry;

@LuaLib(name="game")
public class LibGame extends LuaTable {
	public LibGame() {
		set("registerBlock", new TwoArgFunction() {
			public LuaValue call(LuaValue block, LuaValue name) {
				if (block.isuserdata() && name.isstring()) {
					Object b = block.checkuserdata();
					if (b instanceof Block) {
						GameRegistry.registerBlock((Block)b, name.tojstring());
					}
				}
				return LuaValue.NIL;
			}
		});
	}
}
