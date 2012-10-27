package luaforge.core;

public class Log {
    
    private static String name = "[LuaForge]" + " ";
    
    public static void info(String msg) {
        System.out.println(name + "[INFO] " + msg);
    }
    
    public static void warning(String msg) {
        System.out.println(name + "[WARNING] " + msg);
    }
    
    public static void severe(String msg) {
        System.out.println(name + "[SEVERE] " + msg);
    }
    
}
