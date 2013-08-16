package scriptforge.asm;

import java.io.File;
import java.util.Map;

import cpw.mods.fml.relauncher.IFMLCallHook;
import cpw.mods.fml.relauncher.IFMLLoadingPlugin;
import cpw.mods.fml.relauncher.IFMLLoadingPlugin.TransformerExclusions;

@TransformerExclusions({"scriptforge.asm"})
public class ScriptforgeLoader implements IFMLLoadingPlugin, IFMLCallHook {

	public static File location;
	public static File minecraftLocation;
	public static boolean debug = false;

	@Override
	public Void call() throws Exception {
		ObfuscationMappings.initialize();
		TransformerA.addTransformerMap("scriptforge/asm/scriptforge_at.cfg");
		
		return null;
	}

	@Override
	public String[] getLibraryRequestClass() {
		return null;
	}

	@Override
	public String[] getASMTransformerClass() {
		return new String[] {
				"scriptforge.asm.TransformerA",
				"scriptforge.asm.TransformerB"
		};
	}

	@Override
	public String getModContainerClass() {
		return "scriptforge.asm.ScriptforgeModContainer";
	}

	@Override
	public String getSetupClass() {
		return "scriptforge.asm.ScriptforgeLoader";
	}

	@Override
	public void injectData(Map<String, Object> data) {
		
	}

}
