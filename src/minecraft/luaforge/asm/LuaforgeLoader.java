package luaforge.asm;

import java.util.Map;

import cpw.mods.fml.relauncher.IFMLCallHook;
import cpw.mods.fml.relauncher.IFMLLoadingPlugin;
import cpw.mods.fml.relauncher.IFMLLoadingPlugin.TransformerExclusions;

@TransformerExclusions({"luaforge.asm"})
public class LuaforgeLoader implements IFMLLoadingPlugin, IFMLCallHook {
	
	@Override
	public Void call() throws Exception {
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
		// TODO Auto-generated method stub
		
	}

}
