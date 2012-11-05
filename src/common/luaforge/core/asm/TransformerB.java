package luaforge.core.asm;

import cpw.mods.fml.relauncher.FMLRelauncher;
import cpw.mods.fml.relauncher.IClassTransformer;

public class TransformerB implements IClassTransformer {

    @Override
    public byte[] transform(String name, byte[] bytes) {
        try {
            if (FMLRelauncher.side().equals("CLIENT")) {
                if (name.equals(ObfuscationMappings.getClassName("net.minecraft.src.RenderEngine"))) {
                    bytes = ClassOverrider.override(name, bytes);
                }
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        return bytes;
    }
}
