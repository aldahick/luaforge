package luaforge.core;

import cpw.mods.fml.common.Mod;
import cpw.mods.fml.common.Mod.Init;
import cpw.mods.fml.common.Mod.Instance;
import cpw.mods.fml.common.Mod.PostInit;
import cpw.mods.fml.common.Mod.PreInit;
import cpw.mods.fml.common.Mod.ServerStarting;
import cpw.mods.fml.common.SidedProxy;
import cpw.mods.fml.common.event.FMLInitializationEvent;
import cpw.mods.fml.common.event.FMLPostInitializationEvent;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;
import cpw.mods.fml.common.event.FMLServerStartingEvent;
import cpw.mods.fml.common.network.NetworkMod;
import java.util.ArrayList;
import luaforge.core.api.LuaClassRegistry;
import luaforge.core.lua.LuaEnvironment;
import luaforge.core.lua.LuaEventTracker;
import luaforge.core.lua.LuaStartup;
import luaforge.core.lua.libs.*;
import luaforge.core.lua.libs.block.*;
import luaforge.core.lua.libs.item.*;
import luaforge.core.proxies.CommonProxy;
import luaforge.luaj.vm2.LuaError;
import luaforge.luaj.vm2.LuaValue;
import net.minecraftforge.common.MinecraftForge;

@Mod(modid = "LuaForge", name = "LuaForge", version = "1.0.0", useMetadata = true)
@NetworkMod(clientSideRequired = true, serverSideRequired = false)
public class Core {

    public static final String dirName = "luaforge-mods";
    public static ArrayList<LuaEnvironment> LuaMods = new ArrayList<LuaEnvironment>();
    @Instance("LuaForge")
    public static Core core;
    @SidedProxy(clientSide = "luaforge.core.proxies.ClientProxy", serverSide = "luaforge.core.proxies.CommonProxy")
    public static CommonProxy proxy;

    @PreInit
    public void preLoad(FMLPreInitializationEvent event) {
        MinecraftForge.EVENT_BUS.register(new LuaEventTracker());
        loadLuaMod(LuaStartup.PRESTARTUP);
    }

    @Init
    public void load(FMLInitializationEvent event) {
        loadLuaMod(LuaStartup.STARTUP);
        proxy.registerRenderers();
        Log.info("Sucessfully loaded");
    }

    @PostInit
    public void postLoad(FMLPostInitializationEvent event) {
        loadLuaMod(LuaStartup.POSTSTARTUP);
    }

    @ServerStarting
    public void serverStarting(FMLServerStartingEvent event) {
        loadLuaMod(LuaStartup.SERVERSTARTUP);
    }

    public static void loadLuaMod(LuaEnvironment env, LuaStartup startup) {
        LuaValue s;
        try {
            switch (startup) {
                case BEFOREMINECRAFT:
                    if ((s = env.startup[0]) != null) {
                        s.call();
                    }
                    return;
                case PRESTARTUP:
                    if ((s = env.startup[1]) != null) {
                        s.call();
                    }
                    return;
                case STARTUP:
                    if ((s = env.startup[2]) != null) {
                        s.call();
                    }
                    return;
                case POSTSTARTUP:
                    if ((s = env.startup[3]) != null) {
                        s.call();
                    }
                    return;
                case SERVERSTARTUP:
                    if ((s = env.startup[4]) != null) {
                        s.call();
                    }
            }
        } catch (LuaError e) {
            env.throwLuaError(e);
        }
    }

    public static void loadLuaMod(LuaStartup startup) {
        for (LuaEnvironment e : LuaMods) {
            loadLuaMod(e, startup);
        }
    }

    public static void registerDefaultLibs() {
        LuaClassRegistry.register(new TablesLib());
        LuaClassRegistry.register(new LogLib());
        LuaClassRegistry.register(new ClientLib());
        LuaClassRegistry.register(new BlockLib());
        LuaClassRegistry.register(new ItemLib());
        LuaClassRegistry.register(new ReferenceLib());
        LuaClassRegistry.register(new CraftingHandler());
        LuaClassRegistry.register(new CoreLib());
        LuaClassRegistry.register(new HooksLib());
        LuaClassRegistry.register(new LoaderLib());
    }
}