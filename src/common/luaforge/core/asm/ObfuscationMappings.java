package luaforge.core.asm;

import java.util.HashMap;

public class ObfuscationMappings {

    private static HashMap<String, String> classMappings = new HashMap<String, String>();
    public static final boolean isObfuscated = isObfuscated();

    public static void initialize() {
        classMappings.put("net.minecraft.src.RenderEngine", "bai");
    }

    public static String getClassName(String name) {
        return (isObfuscated) ? classMappings.get(name) : name;
    }

    private static boolean isObfuscated() {
        return (ClassLoader.getSystemResourceAsStream("net/minecraft/src/") == null) ? true : false;
    }
}
