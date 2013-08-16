package scriptforge.script.lib;

import net.minecraft.item.Item;
import scriptforge.api.LuaLib;
import tiin57.lib.luaj.vm2.LuaTable;
import tiin57.lib.luaj.vm2.LuaValue;
import tiin57.lib.luaj.vm2.lib.OneArgFunction;
import tiin57.lib.luaj.vm2.lib.jse.CoerceJavaToLua;

@LuaLib(name="item")
public class LibItem extends LuaTable {
	public LibItem() {
		set("new", new OneArgFunction() {
			public LuaValue call(LuaValue id) {
				if (!id.isint()) return LuaValue.NIL;
				Item item = new Item(id.checkint());
				return CoerceJavaToLua.coerce(item);
			}
		});
	}
}
