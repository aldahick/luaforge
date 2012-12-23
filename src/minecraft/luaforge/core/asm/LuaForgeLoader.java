package luaforge.core.asm;

import cpw.mods.fml.relauncher.IFMLCallHook;
import cpw.mods.fml.relauncher.IFMLLoadingPlugin;
import cpw.mods.fml.relauncher.IFMLLoadingPlugin.TransformerExclusions;
import java.io.File;
import java.util.Map;

@TransformerExclusions({"luaforge.core.asm"})
public class LuaForgeLoader implements IFMLLoadingPlugin, IFMLCallHook {

    public static File location;

    @Override
    public String[] getLibraryRequestClass() {
        return null;
    }

    @Override
    public String[] getASMTransformerClass() {
        return new String[]{
                    "luaforge.core.asm.TransformerA",
                    "luaforge.core.asm.TransformerB"
                };
    }

    @Override
    public String getModContainerClass() {
        return "luaforge.core.asm.LuaForgeModContainer";
    }

    @Override
    public String getSetupClass() {
        return "luaforge.core.asm.LuaForgeLoader";
    }

    @Override
    public void injectData(Map<String, Object> data) {
        if (data.containsKey("coremodLocation")) {
            location = (File) data.get("coremodLocation");
        }
    }

    @Override
    public Void call() throws Exception {
        ObfuscationMappings.initialize();
        TransformerA.addTransformerMap("luaforge/core/asm/luaforge_at.cfg");
        return null;
    }

}
