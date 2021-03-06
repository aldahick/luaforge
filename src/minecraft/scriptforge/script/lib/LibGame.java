package scriptforge.script.lib;

import net.minecraft.block.Block;
import net.minecraft.item.Item;
import scriptforge.api.LuaLib;
import tiin57.lib.luaj.vm2.LuaTable;
import tiin57.lib.luaj.vm2.LuaValue;
import tiin57.lib.luaj.vm2.lib.TwoArgFunction;
import cpw.mods.fml.common.registry.GameRegistry;

@LuaLib(name="game")
public class LibGame extends LuaTable {
	public LibGame() {
		set("registerBlock", new TwoArgFunction() {
			public LuaValue call(LuaValue block, LuaValue name) {
				if (block.isuserdata()) {
					Object b = block.checkuserdata();
					if (b instanceof Block) {
						Block bl = (Block)b;
						GameRegistry.registerBlock(bl, bl.getUnlocalizedName());
					}
				}
				return LuaValue.NIL;
			}
		});
		set("registerItem", new TwoArgFunction() {
			public LuaValue call(LuaValue item, LuaValue name) {
				if (item.isuserdata()) {
					Object i = item.checkuserdata();
					if (i instanceof Item) {
						Item it = (Item)i;
						GameRegistry.registerItem(it, it.getUnlocalizedName());
					}
				}
				return LuaValue.NIL;
			}
		});
	}
}
