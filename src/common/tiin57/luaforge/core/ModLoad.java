package tiin57.luaforge.core;

import java.io.File;

import org.luaj.vm2.Globals;
import org.luaj.vm2.LuaValue;
import org.luaj.vm2.lib.jse.JsePlatform;

public class ModLoad {
	public static void loadMod(File modFile) {
		String modPath = modFile.getPath();
		Globals globals = JsePlatform.standardGlobals();
		LuaValue chunk = globals.loadFile(modPath);
		chunk.call(LuaValue.valueOf(modPath));
	}
}
