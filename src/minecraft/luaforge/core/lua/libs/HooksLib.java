package luaforge.core.lua.libs;

import luaforge.core.api.LuaMethod;
import luaforge.core.lua.LuaEventTracker;
import luaforge.luaj.vm2.LuaError;
import luaforge.luaj.vm2.LuaValue;
import luaforge.luaj.vm2.Varargs;

public class HooksLib {
    
    @LuaMethod(name = "hooks")
    public static Varargs register(Varargs args) {
        String eventName = checkEvent(args.arg1().checkjstring());
        LuaValue function = args.arg(2).checkfunction();
        String handlerName = args.arg(3).optjstring("");
        if (handlerName.isEmpty()) {
            LuaEventTracker.unnamedEventHandlers.get(eventName).add(function);
        } else {
            if (LuaEventTracker.namedEventHandlers.get(eventName).get(handlerName) != null) {
                return LuaValue.FALSE;
            }
            LuaEventTracker.namedEventHandlers.get(eventName).put(handlerName, function);
        }
        return LuaValue.TRUE;
    }
    
    @LuaMethod(name = "hooks")
    public static Varargs unregister(Varargs args) {
        String eventName = checkEvent(args.arg1().checkjstring());
        String handlerName = args.arg(2).checkjstring();
        LuaEventTracker.namedEventHandlers.get(eventName).remove(handlerName);
        return LuaValue.NONE;
    }
    
    @LuaMethod(name = "hooks")
    public static Varargs post_event(Varargs args) {
        return LuaValue.TRUE;
    }
    
    @LuaMethod(name = "hooks")
    public static Varargs create(Varargs args) {
        return LuaValue.TRUE;
    }
    
    private static String checkEvent(String e) {
        if (LuaEventTracker.possibleEvents.contains(e)) {
            return e;
        }
        throw new LuaError("Event non-existant: " + e);
    }
    
}
