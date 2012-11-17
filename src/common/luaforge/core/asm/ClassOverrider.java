package luaforge.core.asm;

import java.io.InputStream;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;
import luaforge.core.Log;

public class ClassOverrider {

    public static byte[] override(String className, byte[] bytes) {

        if(!ObfuscationMappings.isObfuscated){
            Log.info("Development environment detected, not overriding " + className);
            return bytes;
        }
        
        try {
            ZipFile zip = new ZipFile(LuaForgeLoader.location);
            ZipEntry entry = zip.getEntry(className.replace('.', '/') + ".class");
            if (entry == null) {
                Log.severe("Unable to find class: " + className);
            } else {
                InputStream zin = zip.getInputStream(entry);
                bytes = new byte[(int) entry.getSize()];
                zin.read(bytes);
                zin.close();
                Log.info(className + " overriden by " + LuaForgeLoader.location.getName());
            }
            zip.close();
        } catch (Exception e) {
            throw new RuntimeException("Error overriding class: " + className, e);
        }

        return bytes;

    }

}
