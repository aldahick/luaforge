package luaforge;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import luaforge.lua.AnnotationDiscoverer;
import luaforge.lua.LuaEnvironment;
import luaforge.lua.lib.LibTest;
import tiin57.lib.luaj.vm2.lib.LibFunction;
import cpw.mods.fml.common.Mod;
import cpw.mods.fml.common.event.FMLInitializationEvent;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;
import cpw.mods.fml.common.network.NetworkMod;

@NetworkMod(clientSideRequired=true, serverSideRequired=false)
@Mod(modid=Luaforge.MODID, name=Luaforge.NAME, version="@VERSION@")
public class Luaforge {
	public static final String MODID = "Luaforge";
	public static final String NAME = "Luaforge";
	public static final String VERSION = "1.0.0";
	public static List<LuaEnvironment> mods = new ArrayList<LuaEnvironment>();
	public static HashMap<String, LibFunction> luaLibs = new HashMap<String, LibFunction>();
	public static File luamodDir = new File("luamods");
	public static List<String> rawlibnames = new ArrayList<String>();
	
	@Mod.Instance("Luaforge")
	public static Luaforge instance;
	
	@Mod.EventHandler
	public void preinit(FMLPreInitializationEvent evt) {
		registerLibs();
		luaLibs = AnnotationDiscoverer.findLibs();
		if (!luamodDir.exists()) {
			luamodDir.mkdir();
		}
		List<File> dirs = checkDir(luamodDir);
		for (File i : dirs) {
			mods.add(new LuaEnvironment(i));
		}
		for (LuaEnvironment env : mods) {
			env.callMain();
		}
	}
	
	@Mod.EventHandler
	public void init(FMLInitializationEvent evt) {
		
	}
	
	public void registerLibs() {
		registerLib(LibTest.class);
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
	public static void registerLib(Class<? extends LibFunction> libClass) {
		rawlibnames.add(libClass.getName());
	}
}
