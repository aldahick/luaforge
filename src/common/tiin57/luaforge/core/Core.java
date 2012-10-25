package tiin57.luaforge.core;

import tiin57.luaforge.core.lua.ModLoad;
import java.io.File;

import net.minecraft.client.Minecraft;
import cpw.mods.fml.common.Mod;
import cpw.mods.fml.common.Mod.Init;
import cpw.mods.fml.common.Mod.PreInit;
import cpw.mods.fml.common.event.FMLInitializationEvent;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;
import cpw.mods.fml.common.network.NetworkMod;

@Mod (modid = "LuaForge", name = "LuaForge", version = "1.0.0.0", useMetadata = true)
@NetworkMod (
	clientSideRequired = true,
	serverSideRequired = false
	)
public class Core {
	public static boolean[] plugins = new boolean[1];
    
	@PreInit
	public void PreLoad(FMLPreInitializationEvent event) {
		
		File folder = new File(Minecraft.getMinecraftDir() + "/lcp-mods");
		
		if (folder.exists() && folder.isDirectory()) {
			File[] listOfFiles = folder.listFiles();
			String[] mods = new String[listOfFiles.length];
			Log.info(listOfFiles.length + " mods found in /lua-mods");
			for (int i=0; i<listOfFiles.length; i++) {
				if (listOfFiles[i].toString().endsWith(".lua")) {
					ModLoad.loadMod(listOfFiles[i]);
					Log.info(listOfFiles[i].toString());
				}
			}
		}
		else {
			folder.mkdirs();
			if (folder.exists() && folder.isDirectory()) {
                Log.info("/lua-mods created");
			} else {
                Log.severe("Error creating /lua-mods");
			}
		}
	}
    
    @Init
    public void load(FMLInitializationEvent event) {
        Log.info("Sucessfully loaded");
    }
    
}
