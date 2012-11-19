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

import org.luaj.vm2.LuaValue;

import net.minecraft.client.Minecraft;
import net.minecraft.src.RenderEngine;
import net.minecraftforge.client.MinecraftForgeClient;

public class ClientProxy extends CommonProxy {

    @Override
    public void registerRenderers() {
    	
    	InputStream inputStream = null;
    	
    	Class c = RenderEngine.class;
    	
    	RenderEngine render = Minecraft.getMinecraft().renderEngine;
    	
    	Field textureMapField = null;
    	
    	try {
			textureMapField = c.getDeclaredField("textureMap");
		} catch (SecurityException e1) {
		} catch (NoSuchFieldException e1) {
		}
    	
    	textureMapField.setAccessible(true);
    	
    	HashMap textureMap = null;
		try 
		{
			textureMap = (HashMap) textureMapField.get(render);
		} 
		catch (IllegalArgumentException e1) 
		{
		} 
		catch (IllegalAccessException e1) 
		{
		}
    	
		if (textureMap == null)
		{
			System.out.println("Error occured when getting the RenderEngine instance. Go bitch at the LuaForge devs!");
		}
        
        for (String s : CommonProxy.TEXTURES) {
        	
        	File file = new File(s);
        	
        	try {
    			inputStream = new FileInputStream(file);
    		} catch (FileNotFoundException e) {
    			System.out.println("File not found when preloading textures - a Lua mod may have issues!");
    			System.out.println("File was " + file.getPath());
    		}
        	
        	
        	
        	if (inputStream != null)
        	{
                BufferedImage var2 = null;
    			try {
    				var2 = readTextureImage(inputStream);
    			} catch (IOException e) {
        			System.out.println("Error reading file when preloading textures - a Lua mod may have issues!");
        			System.out.println("File was " + file.getPath());
    			}
    			
    			if (var2 != null)
    				textureMap.put(s, render.allocateAndSetupTexture(var2));
        	}
            
            
            
        }
    }
    
    private BufferedImage readTextureImage(InputStream par1InputStream) throws IOException {
        BufferedImage var2 = ImageIO.read(par1InputStream);
        par1InputStream.close();
        return var2;
    }
    
    
    
}
