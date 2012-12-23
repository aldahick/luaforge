package luaforge.core.proxies;

import java.io.File;
import java.util.ArrayList;

public class CommonProxy {

    public static ArrayList<String> TEXTURES = new ArrayList<String>();

    public void registerRenderers() {
    }
    
    public File getDirectory() {
        return new File(".");
    }
}
