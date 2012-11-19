package luaforge.core.proxies;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Field;
import java.util.HashMap;

import javax.imageio.ImageIO;

import net.minecraft.client.Minecraft;
import net.minecraft.src.RenderEngine;

public class ClientProxy extends CommonProxy {

    private HashMap textureMap;

    @Override
    public void registerRenderers() {

        RenderEngine render = Minecraft.getMinecraft().renderEngine;

        if (textureMap == null) {
            Class c = RenderEngine.class;
            
            Field textureMapField = null;
            try {
                textureMapField = c.getDeclaredField("textureMap");
            } catch (Exception e) {}
            textureMapField.setAccessible(true);

            textureMap = null;
            try {
                textureMap = (HashMap) textureMapField.get(render);
            } catch (Exception e) {}

            if (textureMap == null) {
                System.out.println("Error occured when getting the RenderEngine instance.");
            }
        }

        for (String s : CommonProxy.TEXTURES) {

            InputStream inputStream = null;
            File file = new File(s);

            try {
                inputStream = new FileInputStream(file);
            } catch (FileNotFoundException e) {
                System.out.println("File not found when preloading textures - a Lua mod may have issues!");
                System.out.println("File was " + file.getPath());
            }

            if (inputStream != null) {
                BufferedImage var2 = null;
                try {
                    var2 = readTextureImage(inputStream);
                } catch (IOException e) {
                    System.out.println("Error reading file when preloading textures - a Lua mod may have issues!");
                    System.out.println("File was " + file.getPath());
                }

                if (var2 != null) {
                    textureMap.put(s, render.allocateAndSetupTexture(var2));
                }
            }

        }
    }

    private BufferedImage readTextureImage(InputStream stream) throws IOException {
        BufferedImage var2 = ImageIO.read(stream);
        stream.close();
        return var2;
    }
}
