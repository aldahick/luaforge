package tiin57.luaforge.core.lua.libs;

import org.luaj.vm2.Globals;
import org.luaj.vm2.LuaTable;
import org.luaj.vm2.LuaValue;
import org.luaj.vm2.Varargs;
import org.luaj.vm2.lib.OneArgFunction;
import org.luaj.vm2.lib.VarArgFunction;
import tiin57.luaforge.core.Log;

public class LogLib extends OneArgFunction {
    
    public Globals globals;
    
    @Override
    public LuaValue call(LuaValue env) {
        globals = env.checkglobals();
        LuaTable log = new LuaTable();
        
        log.set("info", new info());
        log.set("warning", new warning());
        log.set("severe", new severe());
        
        env.set("log", log);
        return log;
    }
    
    final class info extends VarArgFunction{
        @Override
        public Varargs invoke(Varargs args){
            Log.info(args.tojstring());
            return LuaValue.NONE;
        }
    }
    
    final class warning extends VarArgFunction{
        @Override
        public Varargs invoke(Varargs args){
            Log.warning(args.tojstring());
            return LuaValue.NONE;
        }
    }
    
    final class severe extends VarArgFunction{
        @Override
        public Varargs invoke(Varargs args){
            Log.severe(args.tojstring());
            return LuaValue.NONE;
        }
    }
    
}
