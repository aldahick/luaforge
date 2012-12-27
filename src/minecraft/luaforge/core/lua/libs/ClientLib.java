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
        String s = args.arg1().checkjstring();
        File f = new File(env.getModPath(), s);
        if (!f.exists()) {
            Log.warning("ERROR: File not found: " + s);
        }
        CommonProxy.TEXTURES.add(f.getPath());
        return LuaValue.NONE;
    }
    
}
