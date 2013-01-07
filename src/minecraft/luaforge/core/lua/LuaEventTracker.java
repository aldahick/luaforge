package luaforge.core.lua;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import luaforge.luaj.vm2.LuaTable;
import luaforge.luaj.vm2.LuaValue;
import luaforge.luaj.vm2.lib.ZeroArgFunction;
import net.minecraftforge.event.ForgeSubscribe;
import net.minecraftforge.event.entity.player.PlayerInteractEvent;

public class LuaEventTracker {
    
        
    public static HashMap<String, List<LuaValue>> unnamedEventHandlers = new HashMap<String, List<LuaValue>>();
    public static HashMap<String, HashMap<String, LuaValue>> namedEventHandlers = new HashMap<String, HashMap<String, LuaValue>>();
    public static List<String> possibleEvents = Arrays.asList(
                "PlayerInteractEvent"
            );
    
    public static void initialize() {
        for (String s : possibleEvents) {
            unnamedEventHandlers.put(s, new ArrayList<LuaValue>());
            namedEventHandlers.put(s, new HashMap<String, LuaValue>());
        }
    }
    
    @ForgeSubscribe
    public void playerInteractEvent(PlayerInteractEvent event) {
        LuaValue t = LuaValue.tableOf();
        switch (event.action) {
            case RIGHT_CLICK_AIR:
                t.set("action", LuaValue.valueOf("RIGHT_CLICK_AIR"));
                break;
            case RIGHT_CLICK_BLOCK:
                t.set("action", LuaValue.valueOf("RIGHT_CLICK_BLOCK"));
                break;
            case LEFT_CLICK_BLOCK:
                t.set("action", LuaValue.valueOf("LEFT_CLICK_BLOCK"));
        }
        t.set("x", LuaValue.valueOf(event.x));
        t.set("y", LuaValue.valueOf(event.y));
        t.set("z", LuaValue.valueOf(event.z));
        t.set("face", LuaValue.valueOf(event.face));
        t.set("setCancelled", new setCancelled(event));
        String e = "PlayerInteractEvent";
        for (LuaValue lv : namedEventHandlers.get(e).values()) {
            lv.checkfunction().call(t);
        }
        for (LuaValue lv : unnamedEventHandlers.get(e)) {
            lv.checkfunction().call(t);
        }
    }
    
    private static class setCancelled extends ZeroArgFunction {

        private PlayerInteractEvent event;
        
        public setCancelled(PlayerInteractEvent e) {
            event = e;
        }
        
        @Override
        public LuaValue call() {
            event.setCanceled(true);
            return LuaValue.NONE;
        }
        
    }
    
}
