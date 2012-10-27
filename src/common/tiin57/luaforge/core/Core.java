package tiin57.luaforge.core;

import java.io.File;

import net.minecraft.client.Minecraft;
import cpw.mods.fml.common.Mod;
import cpw.mods.fml.common.Mod.Init;
import cpw.mods.fml.common.Mod.PostInit;
import cpw.mods.fml.common.Mod.PreInit;
import cpw.mods.fml.common.Mod.ServerStarting;
import cpw.mods.fml.common.event.FMLInitializationEvent;
import cpw.mods.fml.common.event.FMLPostInitializationEvent;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;
import cpw.mods.fml.common.event.FMLServerStartingEvent;
import cpw.mods.fml.common.network.NetworkMod;
import java.util.ArrayList;
import tiin57.luaforge.core.lua.LuaEnvironment;
import tiin57.luaforge.core.lua.LuaStartup;

@Mod(modid = "LuaForge", name = "LuaForge", version = "1.0.0.0", useMetadata = true)
@NetworkMod(
	clientSideRequired = true,
serverSideRequired = false)
public class Core {

    public static boolean[] plugins = new boolean[1];
    public static final String dirName = "/lcp-mods";
    public static ArrayList<LuaEnvironment> LuaMods = new ArrayList<LuaEnvironment>();

    @PreInit
    public void PreLoad(FMLPreInitializationEvent event) {

        File folder = new File(Minecraft.getMinecraftDir() + dirName);

        if (folder.exists() && folder.isDirectory()) {
            File[] listOfFiles = folder.listFiles();
            Log.info(listOfFiles.length + " mods found in " + dirName + " now loading");
            for (int i = 0; i < listOfFiles.length; i++) {
                if (listOfFiles[i].toString().endsWith(".lua") && !listOfFiles[i].isDirectory()) {
                    LuaEnvironment env = new LuaEnvironment(listOfFiles[i]);
                    LuaMods.add(env);
                } else if (listOfFiles[i].isDirectory()) {
                    LuaEnvironment env = new LuaEnvironment(listOfFiles[i], listOfFiles[i].getName());
                    LuaMods.add(env);
                }
            }
        } else {
            if (folder.mkdirs()) {
                Log.info("/lua-mods created");
            } else {
                Log.severe("Error creating /lua-mods");
            }
        }

        loadLuaMod(LuaStartup.PRESTARTUP);

    }

    @Init
    public void load(FMLInitializationEvent event) {
        Log.info("Sucessfully loaded");
        loadLuaMod(LuaStartup.STARTUP);
    }

    @PostInit
    public void postLoad(FMLPostInitializationEvent event) {
        loadLuaMod(LuaStartup.POSTSTARTUP);
    }

    @ServerStarting
    public void serverStarting(FMLServerStartingEvent event) {
        loadLuaMod(LuaStartup.SERVERSTARTUP);
    }

    public void loadLuaMod(LuaEnvironment env, LuaStartup startup) {
        if (env.startup == startup) {
            env.call();
        }
    }

    public void loadLuaMod(LuaStartup startup) {
        for (LuaEnvironment e : LuaMods) {
            loadLuaMod(e, startup);
        }
    }
}
