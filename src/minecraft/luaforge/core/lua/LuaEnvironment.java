package luaforge.core.lua;

import java.io.File;
import java.lang.reflect.Method;
import luaforge.core.Log;
import luaforge.core.api.LuaClassRegistry;
import luaforge.luaj.vm2.Globals;
import luaforge.luaj.vm2.LuaError;
import luaforge.luaj.vm2.LuaTable;
import luaforge.luaj.vm2.LuaValue;
import luaforge.luaj.vm2.Varargs;
import luaforge.luaj.vm2.compiler.LuaC;
import luaforge.luaj.vm2.lib.Bit32Lib;
import luaforge.luaj.vm2.lib.CoroutineLib;
import luaforge.luaj.vm2.lib.DebugLib;
import luaforge.luaj.vm2.lib.PackageLib;
import luaforge.luaj.vm2.lib.StringLib;
import luaforge.luaj.vm2.lib.TableLib;
import luaforge.luaj.vm2.lib.jse.JseBaseLib;
import luaforge.luaj.vm2.lib.jse.JseIoLib;
import luaforge.luaj.vm2.lib.jse.JseMathLib;
import luaforge.luaj.vm2.lib.jse.JseOsLib;
import luaforge.luaj.vm2.lib.jse.LuajavaLib;

public class LuaEnvironment {

    public Globals _G = globals(this);
    private LuaValue chunk;
    private String modPath;
    public LuaValue[] startup = new LuaValue[5];
    
    /* Properties */
    private String modName;
    private String version;
    private String[] author;
    private String credits;
    private String description;
    private String url;
    private String updateUrl;
    private String logoFile;
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
            throwLuaError(e);
        }

    }

    public void throwLuaError(LuaError e) {
        String message;
        message = "\n~~~~~~LUAFORGE MOD LOADING ERROR~~~~~~\n";
        message += "Fatal error detected in " + modName + "\n";
        message += e.getMessage();
        message += "\n~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~\n";
        throw new RuntimeException(message);
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
        globals.load(new LuajavaLib());

        for (String s : LuaClassRegistry.methods.keySet()) {
            Method[] methodsArray = new Method[LuaClassRegistry.methods.get(s).size()];
            methodsArray = LuaClassRegistry.methods.get(s).toArray(methodsArray);
            if (s.isEmpty()) {
                globals.load(new LuaBaseMethodLoader(env, methodsArray));
            } else {
                globals.load(new LuaIndexMethodLoader(env, s, methodsArray));
            }

        }

        for (String s : LuaClassRegistry.tables.keySet()) {
            globals.load(new LuaTableLoader(env, LuaClassRegistry.tables.get(s)));
        }

        LuaC.install();
        globals.compiler = LuaC.instance;
        return globals;
    }

    /* Property methods */
    private void setupProperties() {
        callPropertiesFile(new File(getModPath() + "/properties.lua").getPath());
        modName = (determineStringProperty("name").isEmpty()) ? modName : determineStringProperty("name");
        version = determineStringProperty("version");
        author = determineTableProperty("author");
        credits = determineStringProperty("credits");
        description = determineStringProperty("description");
        url = determineStringProperty("url");
        updateUrl = determineStringProperty("updateUrl");
        logoFile = "/" + new File(getModPath()).getName() + ((determineStringProperty("logoFile").startsWith("/")) ? determineStringProperty("logoFile") : "/" + determineStringProperty("logoFile"));
    }

    private void callPropertiesFile(String path) {
        try {
            LuaValue c = _G.loadFile(path);
            c.call(LuaValue.valueOf(path));
        } catch (LuaError e) {
            throwLuaError(e);
        }
    }
    
    private String[] determineTableProperty(String property) {
        try {
            LuaTable t = _G.get(property).opttable(new LuaTable(LuaValue.varargsOf( new LuaValue[]{} )));
            String[] table = new String[t.length()];
            for (int i=1; i <= t.length(); i++) {
                table[i - 1] = t.get(i).checkjstring();
            }
            return table;
        } catch (LuaError e) {
            throwLuaError(e);
            return null;
        }
    }

    private String determineStringProperty(String property) {
        try {
            return _G.get(property).checkjstring();
        } catch (LuaError e) {
            return "";
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

    public String[] getAuthor() {
        return author;
    }
    
    public String getCredits() {
        return credits;
    }
    
    public String getDescription() {
        return description;
    }
    
    public String getURL() {
        return url;
    }
    
    public String getUpdateURL() {
        return updateUrl;
    }
    
    public String getLogoFile() {
        return logoFile;
    }
}
