package luaforge;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import luaforge.lua.LuaEnvironment;
import cpw.mods.fml.common.Mod;
import cpw.mods.fml.common.event.FMLInitializationEvent;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;

@Mod(modid=Luaforge.MODID, name=Luaforge.NAME, version="@VERSION@")
public class Luaforge {
	public static final String MODID = "Luaforge";
	public static final String NAME = "Luaforge";
	public static final String VERSION = "1.0.0";
	public static List<LuaEnvironment> mods = new ArrayList<LuaEnvironment>();
	
	public static File luamodDir = new File("luamods");
	
	@Mod.Instance("Luaforge")
	public static Luaforge instance;
	
	@Mod.EventHandler
	public void preinit(FMLPreInitializationEvent evt) {
		if (!luamodDir.exists()) {
			luamodDir.mkdir();
		}
		List<File> dirs = checkDir(luamodDir);
		for (File i : dirs) {
			
		}
	}
	
	@Mod.EventHandler
	public void init(FMLInitializationEvent evt) {
		
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
}
