package luaforge.core.proxies;

import net.minecraftforge.client.MinecraftForgeClient;

public class ClientProxy extends CommonProxy {

    @Override
    public void registerRenderers() {
        for (String s : CommonProxy.TEXTURES) {
            MinecraftForgeClient.preloadTexture(s);
        }
    }
    
    
    
    
    
    
}
