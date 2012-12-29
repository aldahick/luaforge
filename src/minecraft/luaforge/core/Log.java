package luaforge.core;

import luaforge.core.asm.LuaForgeLoader;

public class Log {

    private static String name = "[LuaForge]" + " ";
    
    public static void debug(String msg) {
        if (LuaForgeLoader.debug) {
            pprint("[DEBUG]", msg);
        }
    }

    public static void info(String msg) {
        pprint("[INFO]", msg);
    }

    public static void warning(String msg) {
        pprint("[WARNING]", msg);
    }

    public static void severe(String msg) {
        pprint("[SEVERE]", msg);
    }
    
    private static void pprint(String prefix, String msg) {
        System.out.println(name + prefix + " " + msg);
    }
}
