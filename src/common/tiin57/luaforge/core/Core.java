package tiin57.luaforge.core;

import java.io.File;

import net.minecraft.client.Minecraft;
import cpw.mods.fml.common.Mod;
import cpw.mods.fml.common.Mod.PreInit;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;
import cpw.mods.fml.common.network.NetworkMod;

@Mod (modid = "LuaForge", name = "LuaForge", version = "1.0.0.0")
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
				System.out.println("[LuaForge] " + listOfFiles.length + " mods found in /lua-mods");
				for (int i=0; i<listOfFiles.length; i++) {
					if (listOfFiles[i].toString().endsWith(".lua")) {
						ModLoad.loadMod(listOfFiles[i]);
				}
			}
		}
		else {
			folder.mkdirs();
			if (folder.exists() && folder.isDirectory()) {
				System.out.println("[LuaForge] /lua-mods created");
			}
			else {
				System.out.println("[LuaForge] Error creating /lua-mods");
			}
		}
	}
}
