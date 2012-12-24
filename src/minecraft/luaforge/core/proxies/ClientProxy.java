package luaforge.core.proxies;

import java.io.File;
import net.minecraft.client.Minecraft;
import net.minecraftforge.client.MinecraftForgeClient;

public class ClientProxy extends CommonProxy {

    @Override
    public File getDirectory() {
        return Minecraft.getMinecraftDir();
    }
    
    @Override
    public void registerRenderers() {
        for (String s : CommonProxy.TEXTURES) {
            MinecraftForgeClient.preloadTexture(s);
        }
    }
    
}
