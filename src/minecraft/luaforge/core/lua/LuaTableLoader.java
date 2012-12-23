package luaforge.core.lua;

import java.lang.reflect.Method;
import java.util.HashMap;
import luaforge.core.Log;
import luaforge.core.api.LuaTable;
import luaforge.luaj.vm2.Globals;
import luaforge.luaj.vm2.LuaValue;
import luaforge.luaj.vm2.lib.OneArgFunction;

public class LuaTableLoader extends OneArgFunction{

    private Globals globals;
    private LuaEnvironment luaEnv;
    
    private String tableName;
    private Method method;
    
    private HashMap<String, LuaValue> namedContents;
    
    @SuppressWarnings("unchecked")
    public LuaTableLoader(LuaEnvironment luaEnv, Method m) {
        this.luaEnv = luaEnv;
        this.method = m;
        this.tableName = m.getName();
        
        try {
            this.namedContents = (HashMap<String,LuaValue>) method.invoke(null);
        } catch (Exception e) {
            Log.severe(method.getName() + " could not be cast into HashMap<String,LuaValue>");
            this.namedContents = new HashMap<String, LuaValue>();
        }
    }
    
    @Override
    public LuaValue call(LuaValue env) {
        globals = env.checkglobals();
        env.set(tableName, LuaValue.listOf(new LuaValue[]{}));
        for (String s : namedContents.keySet()) {
            env.get(tableName).set(s, namedContents.get(s));
        }
        return env.get(tableName);
    }

}
