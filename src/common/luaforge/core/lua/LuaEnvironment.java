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

    private Globals _G = globals(this);
    private LuaValue chunk;
    private String modPath;
    
    /* Properties */
    public LuaStartup startup;
    private String modName;
    private String version;
    private String author;
    /* * * * */

    public LuaEnvironment(File modFile, String name) {
        modPath = modFile.getPath();
        modName = name;
        setupProperties();
    }

    public void call() {
        String modMainPath = new File(modPath + "/main.lua").getPath();
        try {
            chunk = _G.loadFile(modMainPath);
            chunk.call(LuaValue.valueOf(modMainPath));
        } catch (LuaError e) {
            Log.severe(modName + " not loaded properly!");
            Log.severe(e.getMessage());
        }

    }

    private static Globals globals(LuaEnvironment env) {
        Globals globals = new Globals();
        globals.load(new JseBaseLib());
        globals.load(new PackageLib());
        globals.load(new Bit32Lib());
        globals.load(new TableLib());
        globals.load(new StringLib());
        globals.load(new CoroutineLib());
        globals.load(new JseMathLib());
        globals.load(new JseIoLib());
        globals.load(new JseOsLib());
        globals.load(new DebugLib());

        for (String s : LuaClassRegistry.methods.keySet()) {
            Method[] methodsArray = new Method[LuaClassRegistry.methods.get(s).size()];
            methodsArray = LuaClassRegistry.methods.get(s).toArray(methodsArray);
            if(s.isEmpty()){
                globals.load(new LuaBaseMethodLoader(env, methodsArray));
            }else{
                globals.load(new LuaIndexMethodLoader(env, s, methodsArray));
            }

        }

        LuaC.install();
        globals.compiler = LuaC.instance;
        return globals;
    }

    /* Property methods */
    
    private void setupProperties() {
        callPropertiesFile(new File(modPath + "/properties.lua").getPath());
        startup = determineStartup();
        version = determineProperty("version");
        author = determineProperty("author");
    }

    private void callPropertiesFile(String path) {
        try {
            LuaValue c = _G.loadFile(path);
            c.call(LuaValue.valueOf(path));
        } catch (LuaError e) {
            Log.severe(modName + " not loaded properly!");
            Log.severe(e.getMessage());
        }
    }

    private String determineProperty(String property) {
        try {
            return _G.get(property).tojstring();
        } catch (Exception e) {
            return "";
        }
    }

    private LuaStartup determineStartup() {
        try {
            String s = _G.get("startup").tojstring();
            if (s.equals("PRESTARTUP")) {
                return LuaStartup.PRESTARTUP;
            } else if (s.equals("STARTUP")) {
                return LuaStartup.STARTUP;
            } else if (s.equals("POSTSTARTUP")) {
                return LuaStartup.POSTSTARTUP;
            } else if (s.equals("SERVERSTARTUP")) {
                return LuaStartup.SERVERSTARTUP;
            } else {
                return LuaStartup.STARTUP;
            }
        } catch (LuaError e) {
            return LuaStartup.STARTUP;
        }
    }

    /* Mod information */
    
    public String getModPath() {
        return modPath;
    }
    
    public String getModName() {
        return modName;
    }

    public String getVersion() {
        return version;
    }

    public String getAuthor() {
        return author;
    }
}
