package luaforge.asm;

import java.io.File;
import java.util.Map;

import cpw.mods.fml.relauncher.IFMLCallHook;
import cpw.mods.fml.relauncher.IFMLLoadingPlugin;
import cpw.mods.fml.relauncher.IFMLLoadingPlugin.TransformerExclusions;

@TransformerExclusions({"luaforge.asm"})
public class LuaforgeLoader implements IFMLLoadingPlugin, IFMLCallHook {

	public static File location;
	public static File minecraftLocation;
	public static boolean debug = false;

	@Override
	public Void call() throws Exception {
		System.out.println("LuaforgeLoader.call()"); //TODO: Remove
		ObfuscationMappings.initialize();
		TransformerA.addTransformerMap("luaforge/asm/luaforge_at.cfg");
		return null;
	}

	@Override
	public String[] getLibraryRequestClass() {
		return null;
	}

	@Override
	public String[] getASMTransformerClass() {
		return new String[] {
				"luaforge.asm.TransformerA",
				"luaforge.asm.TransformerB"
		};
	}

	@Override
	public String getModContainerClass() {
		return "luaforge.asm.LuaforgeModContainer";
	}

	@Override
	public String getSetupClass() {
		return "luaforge.asm.LuaforgeLoader";
	}

	@Override
	public void injectData(Map<String, Object> data) {
		
	}

}
