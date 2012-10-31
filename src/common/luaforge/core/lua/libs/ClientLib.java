package luaforge.core.lua.libs;

import java.io.File;
import luaforge.core.api.LuaMethod;
import luaforge.core.lua.LuaEnvironment;
import luaforge.core.proxies.CommonProxy;
import org.luaj.vm2.LuaValue;
import org.luaj.vm2.Varargs;

public class ClientLib {

    @LuaMethod(name="client")
    public static Varargs preloadItemTexture(Varargs args, LuaEnvironment env){
        return preloadTexture(args, env);
    }
    
    @LuaMethod(name="client")
    public static Varargs preloadBlockTexture(Varargs args, LuaEnvironment env){
        return preloadTexture(args, env);
    }
    
    @LuaMethod(name="client")
    public static Varargs preloadTexture(Varargs args, LuaEnvironment env){
        CommonProxy.TEXTURES.add(new File(env.getModPath(), args.arg1().tojstring()).getPath());
        return LuaValue.NONE;
    }
    
}
