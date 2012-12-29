package luaforge.core.asm;

import cpw.mods.fml.relauncher.IFMLCallHook;
import cpw.mods.fml.relauncher.IFMLLoadingPlugin;
import cpw.mods.fml.relauncher.IFMLLoadingPlugin.TransformerExclusions;
import cpw.mods.fml.relauncher.RelaunchClassLoader;
import java.io.File;
import java.util.Map;
import luaforge.core.Core;
import luaforge.core.Log;
import luaforge.core.lua.LuaEnvironment;
import luaforge.core.lua.LuaStartup;

@TransformerExclusions({"luaforge.core.asm"})
public class LuaForgeLoader implements IFMLLoadingPlugin, IFMLCallHook {

    public static File location;
    public static File minecraftLocation;
    public static RelaunchClassLoader rcl;
    public static boolean debug = true;

    @Override
    public String[] getLibraryRequestClass() {
        return null;
    }

    @Override
    public String[] getASMTransformerClass() {
        return new String[]{
                    "luaforge.core.asm.TransformerA",
                    "luaforge.core.asm.TransformerB"
                };
    }

    @Override
    public String getModContainerClass() {
        return "luaforge.core.asm.LuaForgeModContainer";
    }

    @Override
    public String getSetupClass() {
        return "luaforge.core.asm.LuaForgeLoader";
    }

    @Override
    public void injectData(Map<String, Object> data) {
        rcl = (RelaunchClassLoader) data.get("classLoader");
        if (data.containsKey("coremodLocation")) {
            location = (File) data.get("coremodLocation");
        } else if (data.containsKey("mcLocation")) {
            minecraftLocation = (File) data.get("mcLocation");
        }
    }

    @Override
    public Void call() throws Exception {
        if (debug) { Log.debug("Debug mode active"); }
        ObfuscationMappings.initialize();
        TransformerA.addTransformerMap("luaforge/core/asm/luaforge_at.cfg");
        
        Core.registerDefaultLibs();
        File folder = new File(minecraftLocation, Core.dirName);
        try { // Inject all lua mods onto the classpath
            rcl.addURL(folder.toURI().toURL());
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

        if (folder.exists() && folder.isDirectory()) {
            File[] listOfFiles = folder.listFiles();
            int realLength = listOfFiles.length;
            for (int i = 0; i < listOfFiles.length; i++) {
                if (listOfFiles[i].isDirectory()) {
                    LuaEnvironment env = new LuaEnvironment(listOfFiles[i], listOfFiles[i].getName());
                    Core.LuaMods.add(env);
                    env.call();                    
                } else {
                    realLength--;
                }
            }
            if (realLength == 0) {
                Log.info(listOfFiles.length + " mods found in " + Core.dirName + ", nothing to load");
            } else if (realLength == 1) {
                Log.info(listOfFiles.length + " mod found in " + Core.dirName + " now loading");
            } else {
                Log.info(listOfFiles.length + " mods found in " + Core.dirName + " now loading");
            }
        } else {
            if (folder.mkdirs()) {
                Log.info(Core.dirName + " created");
            } else {
                Log.warning("Error creating " + Core.dirName);
            }
        }
        Core.loadLuaMod(LuaStartup.BEFOREMINECRAFT);
        return null;
    }

}
