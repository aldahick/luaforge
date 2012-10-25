package tiin57.luaforge.core.lua;

import java.io.File;

import org.luaj.vm2.Globals;
import org.luaj.vm2.LuaError;
import org.luaj.vm2.LuaValue;
import org.luaj.vm2.compiler.LuaC;
import org.luaj.vm2.lib.Bit32Lib;
import org.luaj.vm2.lib.CoroutineLib;
import org.luaj.vm2.lib.PackageLib;
import org.luaj.vm2.lib.StringLib;
import org.luaj.vm2.lib.TableLib;
import org.luaj.vm2.lib.jse.JseBaseLib;
import org.luaj.vm2.lib.jse.JseIoLib;
import org.luaj.vm2.lib.jse.JseMathLib;
import org.luaj.vm2.lib.jse.JseOsLib;
import org.luaj.vm2.lib.jse.JsePlatform;
import org.luaj.vm2.lib.jse.LuajavaLib;
import tiin57.luaforge.core.Log;
import tiin57.luaforge.core.lua.libs.LogLib;

public class ModLoad {
    
	public static void loadMod(File modFile) {
		String modPath = modFile.getPath();
		try {
            Globals _G = globals();
            LuaValue chunk;
			chunk = _G.loadFile(modPath);
			chunk.call(LuaValue.valueOf(modPath));
		}
		catch (LuaError e) {
			Log.severe(modPath.toString() + " not loaded properly!");
            Log.severe(e.getMessage());
		}
	}
    
    public static Globals globals() {
		Globals _G = new Globals();
		_G.load(new JseBaseLib());
		_G.load(new PackageLib());
		_G.load(new Bit32Lib());
		_G.load(new TableLib());
		_G.load(new StringLib());
		_G.load(new CoroutineLib());
		_G.load(new JseMathLib());
		_G.load(new JseIoLib());
		_G.load(new JseOsLib());
		_G.load(new LuajavaLib());
        
        // Custom libs
        _G.load(new LogLib());
        
		LuaC.install();
		_G.compiler = LuaC.instance;
		return _G;		
	}
}
