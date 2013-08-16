package scriptforge.script.lib;

import net.minecraft.client.Minecraft;
import scriptforge.api.LuaLib;
import tiin57.lib.luaj.vm2.LuaTable;
import tiin57.lib.luaj.vm2.LuaValue;
import tiin57.lib.luaj.vm2.lib.ZeroArgFunction;

@LuaLib(name="core")
public class LibCore extends LuaTable {
	public LibCore() {
		set("getMinecraftDir", new ZeroArgFunction() {
			public LuaValue call() {
				return LuaValue.valueOf(Minecraft.getMinecraft().mcDataDir.getPath());
			}
		});
	}
}
