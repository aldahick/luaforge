package tiin57.luaforge.core.lua.libs;

import org.luaj.vm2.LuaTable;
import org.luaj.vm2.LuaValue;
import org.luaj.vm2.Varargs;
import org.luaj.vm2.lib.OneArgFunction;
import org.luaj.vm2.lib.VarArgFunction;
import tiin57.luaforge.core.Log;

public class LogLib extends OneArgFunction{
    
    private static final int INIT      = 0;
    private static final int INFO      = 1;
    private static final int WARNING   = 2;
    private static final int SEVERE    = 3;

    private static final String[] NAMES = {
            "info",
            "warning",
            "severe",
    };
    
    @Override
    public LuaValue call(LuaValue env){
        LuaTable t = new LuaTable();
        bind(t, this.getClass(), NAMES, INFO);
        env.get("server").set("log", t);
        
        return t;
    }
    
    @Override
    public Varargs invoke(Varargs args) {
        try {
            switch(opcode){
                case INIT:
                    return call();
                case INFO:
                    Log.info(args.arg1().tojstring());
                    return NONE;
                case WARNING:
                    Log.warning(args.arg1().tojstring());
                    return NONE;
                case SEVERE:
                    Log.severe(args.arg1().tojstring());
                    return NONE;
                default:
                    return NONE;
            }
        }catch(Exception e){
            return varargsOf(NIL, valueOf(e.getMessage()));
        }
    }
    
}
