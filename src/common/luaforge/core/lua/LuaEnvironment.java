package luaforge.core.lua;

import java.io.File;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Map;

import luaforge.core.Log;
import luaforge.core.api.LuaClassRegistry;
import luaforge.core.lua.libs.LogLib;

import org.luaj.vm2.Globals;
import org.luaj.vm2.LuaError;
import org.luaj.vm2.LuaString;
import org.luaj.vm2.LuaValue;
import org.luaj.vm2.compiler.LuaC;
import org.luaj.vm2.lib.Bit32Lib;
import org.luaj.vm2.lib.CoroutineLib;
import org.luaj.vm2.lib.DebugLib;
import org.luaj.vm2.lib.PackageLib;
import org.luaj.vm2.lib.StringLib;
import org.luaj.vm2.lib.TableLib;
import org.luaj.vm2.lib.jse.JseBaseLib;
import org.luaj.vm2.lib.jse.JseIoLib;
import org.luaj.vm2.lib.jse.JseMathLib;
import org.luaj.vm2.lib.jse.JseOsLib;
import org.luaj.vm2.lib.jse.LuajavaLib;

public class LuaEnvironment {
    
    public LuaStartup startup;
    
    private Globals _G = globals();
    private LuaValue chunk;
    
    private String modPath;
    private String modName;
    
    public LuaEnvironment(File modFile){
        modPath = modFile.getPath();
        modName = modFile.getName();
        load();
    }
    
    public LuaEnvironment(File modFile, String name){
        modPath = new File(modFile + "/main.lua").getPath();
        modName = name;
        
        callPropertiesFile(new File(modFile + "/properties.lua").getPath());
        startup = determineStartup();
        
        load();
    }
    
    public void load(){
        chunk = _G.loadFile(modPath);
    }
    
    public void call(){
        try {
			chunk.call(LuaValue.valueOf(modPath));
		}
		catch (LuaError e) {
			Log.severe(modName + " not loaded properly!");
            Log.severe(e.getMessage());
		}
        
    }
    
    private void callPropertiesFile(String path){
        try {
            LuaValue c = _G.loadFile(path);
			c.call(LuaValue.valueOf(path));
		}
		catch (LuaError e) {
			Log.severe(modName + " not loaded properly!");
            Log.severe(e.getMessage());
		}
    }
    
    public String getModName(){
        return modName;
    }
    
    private LuaStartup determineStartup(){
        try{
            String s = _G.get("startup").tojstring();
            if(s.equals("PRESTARTUP")){
                return LuaStartup.PRESTARTUP;
            } else if(s.equals("STARTUP")) {
                return LuaStartup.STARTUP;
            } else if(s.equals("POSTSTARTUP")) {
                return LuaStartup.POSTSTARTUP;
            } else if(s.equals("SERVERSTARTUP")) {
                return LuaStartup.SERVERSTARTUP;
            }else{
                return LuaStartup.STARTUP;
            }
        }catch(LuaError e){
            return LuaStartup.STARTUP;
        }
    }
    
    private static Globals globals() {
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
        _G.load(new DebugLib());
        
        // Custom libs
        _G.load(new LogLib());
        for(String s : LuaClassRegistry.methods.keySet()){
            Method[] methodsArray = new Method[LuaClassRegistry.methods.get(s).size()];
            _G.load(new LuaMethodLoader(s, LuaClassRegistry.methods.get(s).toArray(methodsArray)));
        }
        
		LuaC.install();
		_G.compiler = LuaC.instance;
		return _G;
	}
    
}
