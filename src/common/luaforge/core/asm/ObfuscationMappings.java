package luaforge.core.asm;

import java.util.HashMap;

public class ObfuscationMappings {

    private static HashMap<String, String> classMappings = new HashMap<String, String>();
    private static HashMap<String, HashMap<String, String>> methodMappings = new HashMap<String, HashMap<String, String>>();
    public static final boolean isObfuscated = isObfuscated();

    public static void initialize() {
        classMappings.put("net.minecraft.src.RenderEngine", "bap");
        
        HashMap<String,String> renderEngineMappings = new HashMap<String,String>();
        renderEngineMappings.put("getTexture", "b");
        methodMappings.put("net.minecraft.src.RenderEngine", renderEngineMappings);
    }

    public static String getClassName(String name) {
        return (isObfuscated) ? classMappings.get(name) : name;
    }
    
    public static String getMethodName(String className, String name) {
        return (isObfuscated) ? methodMappings.get(className).get(name) : name;
    }

    private static boolean isObfuscated() {
        return (ClassLoader.getSystemResourceAsStream("net/minecraft/src/") == null) ? true : false;
    }
}
