package luaforge.core;

import cpw.mods.fml.client.FMLClientHandler;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Field;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.imageio.ImageIO;
import net.minecraft.client.Minecraft;
import net.minecraft.src.RenderEngine;

public class ImageLoader {

    private static RenderEngine renderEngine = FMLClientHandler.instance().getClient().renderEngine;
    
    public static void preloadTexture(String s) {
        if(s.startsWith(".")){
            s = s.substring(2);
        }
        File path = new File(Minecraft.getMinecraftDir().getAbsolutePath().substring(0, Minecraft.getMinecraftDir().getAbsolutePath().length() - 1) + s);
        BufferedImage image = null;
        try {
            image = readTextureImage(new FileInputStream(path));
        } catch (IOException ex) {
            Log.severe("Unable to get the BufferedImage object: " + ex.getMessage());
        }
        int setup = renderEngine.allocateAndSetupTexture(image);
        renderEngine.bindTexture(setup);
    }
    
    
    /* Private methods from renderEngine not worth using reflection for */
    
    /**
     * Returns a BufferedImage read off the provided input stream.  Args: inputStream
     */
    private static BufferedImage readTextureImage(InputStream par1InputStream) throws IOException
    {
        BufferedImage var2 = ImageIO.read(par1InputStream);
        par1InputStream.close();
        return var2;
    }
    
}
