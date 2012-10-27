package luaforge.core.lua.libs;

import luaforge.core.api.LuaMethod;
import luaforge.core.proxies.CommonProxy;
import org.luaj.vm2.LuaValue;
import org.luaj.vm2.Varargs;

public class ClientLib {

    @LuaMethod(name="client")
    public static Varargs preloadItemTexture(Varargs args){
        return preloadTexture(args);
    }
    
    @LuaMethod(name="client")
    public static Varargs preloadBlockTexture(Varargs args){
        return preloadTexture(args);
    }
    
    @LuaMethod(name="client")
    public static Varargs preloadTexture(Varargs args){
        CommonProxy.TEXTURES.add(args.arg1().tojstring());
        return LuaValue.NONE;
    }
    
}
