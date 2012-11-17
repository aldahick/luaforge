package luaforge.core.proxies;

import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;
import javax.imageio.ImageIO;
import net.minecraftforge.client.MinecraftForgeClient;

public class ClientProxy extends CommonProxy {

    @Override
    public void registerRenderers() {
        
        for (String s : CommonProxy.TEXTURES) {
            MinecraftForgeClient.preloadTexture(s);
        }
    }
    
    private int[] getImageContentsAndAllocate(BufferedImage par1BufferedImage)
    {
        int var2 = par1BufferedImage.getWidth();
        int var3 = par1BufferedImage.getHeight();
        int[] var4 = new int[var2 * var3];
        par1BufferedImage.getRGB(0, 0, var2, var3, var4, 0, var2);
        return var4;
    }
    
    private BufferedImage readTextureImage(InputStream par1InputStream) throws IOException {
        BufferedImage var2 = ImageIO.read(par1InputStream);
        par1InputStream.close();
        return var2;
    }
    
    
    
}
