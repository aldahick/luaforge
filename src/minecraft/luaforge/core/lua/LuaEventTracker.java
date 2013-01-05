package luaforge.core.lua;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import luaforge.luaj.vm2.LuaValue;
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
        String e = "PlayerInteractEvent";
        for (LuaValue lv : namedEventHandlers.get(e).values()) {
            lv.checkfunction().call();
        }
        for (LuaValue lv : unnamedEventHandlers.get(e)) {
            lv.checkfunction().call();
        }
    }
    
}
