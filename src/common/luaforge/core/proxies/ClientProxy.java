package luaforge.core.proxies;

import luaforge.core.ImageLoader;
import net.minecraftforge.client.MinecraftForgeClient;

public class ClientProxy extends CommonProxy {

    @Override
    public void registerRenderers() {
        for (String s : CommonProxy.TEXTURES) {
            ImageLoader.preloadTexture(s);
            MinecraftForgeClient.preloadTexture(s);
        }
    }
    
    
    
    
    
    
}
