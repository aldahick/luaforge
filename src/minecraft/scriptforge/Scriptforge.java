package scriptforge;

import java.io.File;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import net.minecraftforge.common.Configuration;
import scriptforge.api.IScriptEnvironment;
import scriptforge.asm.AnnotationDiscoverer;
import scriptforge.script.LuaEnvironment;
import scriptforge.script.lib.LibBlock;
import scriptforge.script.lib.LibCore;
import scriptforge.script.lib.LibCreativeTabs;
import scriptforge.script.lib.LibGame;
import scriptforge.script.lib.LibMaterials;
import tiin57.lib.luaj.vm2.LuaValue;
import cpw.mods.fml.common.LoadController;
import cpw.mods.fml.common.Loader;
import cpw.mods.fml.common.Mod;
import cpw.mods.fml.common.ModContainer;
import cpw.mods.fml.common.event.FMLInitializationEvent;
import cpw.mods.fml.common.event.FMLPostInitializationEvent;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;
import cpw.mods.fml.common.network.NetworkMod;

@NetworkMod(clientSideRequired=true, serverSideRequired=false)
//@Mod(modid=Luaforge.MODID, name=Luaforge.NAME, version=Luaforge.VERSION)
public class Scriptforge {
	public static final String MODID = "Scriptforge";
	public static final String NAME = "Scriptforge";
	public static final String VERSION = "pre3";
	public static List<IScriptEnvironment> mods = new ArrayList<IScriptEnvironment>();
	public static HashMap<String, LuaValue> luaLibs = new HashMap<String, LuaValue>();
	public static File luamodDir = new File("luamods");
	public static List<String> rawlibnames = new ArrayList<String>();
	public static HashMap<String, ScriptModContainer> containers = new HashMap<String, ScriptModContainer>();
	
	public static boolean debug = true;
	
	public static final String STATE_BEFOREMINECRAFT = "beforeminecraft";
	public static final String STATE_PREINIT = "preinit";
	public static final String STATE_INIT = "init";
	public static final String STATE_POSTINIT = "postinit";
	
	public static void loadMods() {
		
		debug("loadMods()");
		registerLibs();
		luaLibs = AnnotationDiscoverer.findLibs();
		if (!luamodDir.exists()) {
			luamodDir.mkdir();
		}
		List<File> dirs = checkDir(luamodDir);
		for (File i : dirs) {
			mods.add(new LuaEnvironment(i));
		}
	}
	
	public static void callBefore() {
		debug("callBefore()");
//		for (LuaModContainer container : containers.values()) {
//			if (container.env.isNetworkMod) {
//				FMLNetworkHandler.instance().registerNetworkMod(new NetworkModHandler(container.wrapped, container.env.networkmod));
//			}
//		}
		loadMods(STATE_BEFOREMINECRAFT);
	}
	@Mod.Instance(Scriptforge.MODID)
	public static Scriptforge instance;
	
	@Mod.EventHandler
	public void preinit(FMLPreInitializationEvent evt) {
		Configuration cfg = new Configuration(evt.getSuggestedConfigurationFile());
		cfg.load();
		debug = cfg.get("Debugging", "Enabled", false).getBoolean(false);
		cfg.save();
		loadMods(STATE_PREINIT);
	}
	
	@Mod.EventHandler
	public void init(FMLInitializationEvent evt) {
		loadMods(STATE_INIT);
	}
	
	@Mod.EventHandler
	public void postinit(FMLPostInitializationEvent evt) {
		loadMods(STATE_POSTINIT);
	}
	
	public static void registerLibs() {
		registerLib(LibCore.class);
		registerLib(LibBlock.class);
		registerLib(LibGame.class);
		registerLib(LibMaterials.class);
		registerLib(LibCreativeTabs.class);
	}
	
	public static List<File> checkDir(File parent) {
		List<File> dirs = new ArrayList<File>();
		for (File i : parent.listFiles()) {
			if (i.isDirectory()) {
				dirs.add(i);
			}
		}
		return dirs;
	}
	
	/**
	 * Registers a Lua-side library to be loaded.
	 * 
	 * The class represented by className must be annotated with luaforge.api.LuaLib
	 * and it must be a subtype of LibFunction, directly or indirectly.
	 * 
	 * Make sure this class exists! If it doesn't, Luaforge will crash to prevent bad
	 * things from happening.
	 */
	public static void registerLib(String className) {
		rawlibnames.add(className);
	}
	
	/**
	 * Does the same thing as registerLib(), but with the Class object as an argument
	 * instead of the class name.
	 */
	public static void registerLib(Class<? extends LuaValue> libClass) {
		rawlibnames.add(libClass.getName());
	}
	
	public static void loadMods(String loadstate) {
		debug("loadMods(String)");
		try {
			for (IScriptEnvironment env : mods) {
				debug("Loading luamod "+env.modid());
				if (env.loadstate().equals(loadstate)) {
					ModContainer oldContainer;
					LoadController controller;
					Field lcf = Loader.class.getDeclaredField("modController");
					lcf.setAccessible(true);
					controller = (LoadController)lcf.get(Loader.instance());
					Field acf = LoadController.class.getDeclaredField("activeContainer");
					acf.setAccessible(true);
					oldContainer = (ModContainer)acf.get(controller);
					acf.set(controller, containers.get(env.modid()));
					debug("Calling its loadstate");
					env.callMain();
					acf.set(controller, oldContainer);
				}
			}
		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}
	
	public static void debug(Object msg) {
		if (debug) {
			System.out.println("[DEBUG] "+msg.toString());
		}
	}
}
