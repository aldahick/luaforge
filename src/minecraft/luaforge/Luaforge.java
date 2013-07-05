package luaforge;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import luaforge.lua.AnnotationDiscoverer;
import luaforge.lua.LuaEnvironment;
import luaforge.lua.lib.LibBlock;
import luaforge.lua.lib.LibCore;
import luaforge.lua.lib.LibCreativeTabs;
import luaforge.lua.lib.LibGame;
import luaforge.lua.lib.LibMaterials;
import tiin57.lib.luaj.vm2.LuaValue;
import cpw.mods.fml.common.Mod;
import cpw.mods.fml.common.event.FMLInitializationEvent;
import cpw.mods.fml.common.event.FMLPostInitializationEvent;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;
import cpw.mods.fml.common.network.NetworkMod;

@NetworkMod(clientSideRequired=true, serverSideRequired=false)
@Mod(modid=Luaforge.MODID, name=Luaforge.NAME, version=Luaforge.VERSION)
public class Luaforge {
	public static final String MODID = "Luaforge";
	public static final String NAME = "Luaforge";
	public static final String VERSION = "1.0.0";
	public static List<LuaEnvironment> mods = new ArrayList<LuaEnvironment>();
	public static HashMap<String, LuaValue> luaLibs = new HashMap<String, LuaValue>();
	public static File luamodDir = new File("luamods");
	public static List<String> rawlibnames = new ArrayList<String>();
	
	public static final String STATE_PREINIT = "preinit";
	public static final String STATE_INIT = "init";
	public static final String STATE_POSTINIT = "postinit";
	
	public static void loadMods() {
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
	
	@Mod.Instance("Luaforge")
	public static Luaforge instance;
	
	@Mod.EventHandler
	public void preinit(FMLPreInitializationEvent evt) {
		
		for (LuaEnvironment env : mods) {
			if (env.loadstate.equals(STATE_PREINIT)) {
				env.callMain();
			}
		}
	}
	
	@Mod.EventHandler
	public void init(FMLInitializationEvent evt) {
		for (LuaEnvironment env : mods) {
			if (env.loadstate.equals(STATE_INIT)) {
				env.callMain();
			}
		}
	}
	
	@Mod.EventHandler
	public void postinit(FMLPostInitializationEvent evt) {
		for (LuaEnvironment env : mods) {
			if (env.loadstate.equals(STATE_POSTINIT)) {
				env.callMain();
			}
		}
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
	 * Does the same thing as registerLuaLib(), but with the Class object as an argument
	 * instead of the class name.
	 */
	public static void registerLib(Class<? extends LuaValue> libClass) {
		rawlibnames.add(libClass.getName());
	}
}
