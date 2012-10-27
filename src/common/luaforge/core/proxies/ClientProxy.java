package luaforge.core.proxies;

import net.minecraftforge.client.MinecraftForgeClient;

public class ClientProxy extends CommonProxy {

    @Override
    public void registerRenderers() {

        for (String s : CommonProxy.ITEM_TEXTURES) {
            MinecraftForgeClient.preloadTexture(s);
        }

        for (String s : CommonProxy.BLOCK_TEXTURES) {
            MinecraftForgeClient.preloadTexture(s);
        }
    }
}
