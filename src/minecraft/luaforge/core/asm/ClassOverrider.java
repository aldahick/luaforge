package luaforge.core.asm;

import java.io.InputStream;
import luaforge.core.Log;

public class ClassOverrider {

    public static InputStream override(String name) {
        return override(name, new byte[0]);
    }
    
    public static InputStream override(String className, byte[] bytes) {

        if(!ObfuscationMappings.isObfuscated){
            Log.info("Development environment detected, not overriding " + className);
            return null;
        }
        
        try {
            return ClassOverrider.class.getResourceAsStream("/classes/" + className.replace('.', '/') + ".class");
        } catch (Exception e) {
            throw new RuntimeException("Error overriding class: " + className, e);
        }

    }

}
