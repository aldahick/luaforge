package luaforge.core.lua.libs;

import java.io.File;
import luaforge.core.Log;
import luaforge.core.api.LuaMethod;
import luaforge.core.lua.LuaEnvironment;
import luaforge.core.proxies.CommonProxy;
import luaforge.luaj.vm2.LuaValue;
import luaforge.luaj.vm2.Varargs;

public class ClientLib {

    @LuaMethod(name="client")
    public static Varargs preloadTexture(Varargs args, LuaEnvironment env){
        String arg = args.arg1().checkjstring();
        String s = "/" + (new File(env.getModPath()).getName()) + ((arg.startsWith("/")) ? "" : "/") + arg;
        if (ClientLib.class.getResourceAsStream(s) == null) {
            Log.warning("The file " + arg + " could not be located in the mod " + env.getModName());
        }
        CommonProxy.TEXTURES.add(s);
        return LuaValue.NONE;
    }
    
}
