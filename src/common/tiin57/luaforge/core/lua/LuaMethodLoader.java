package tiin57.luaforge.core.lua;

import java.lang.reflect.Method;
import org.luaj.vm2.Globals;
import org.luaj.vm2.LuaTable;
import org.luaj.vm2.LuaValue;
import org.luaj.vm2.Varargs;
import org.luaj.vm2.lib.OneArgFunction;
import org.luaj.vm2.lib.VarArgFunction;
import tiin57.luaforge.core.Log;

public class LuaMethodLoader extends OneArgFunction {

    public Globals globals;
    private String indexName;
    private String[] methodNames;
    private Method[] methods;

    public LuaMethodLoader(String name, Method... methods) {
        indexName = name;

        methodNames = new String[methods.length];
        for (int i = 0; i < methods.length; i++) {
            methodNames[i] = methods[i].getName();
        }
        this.methods = methods;
    }

    @Override
    public LuaValue call(LuaValue env) {
        globals = env.checkglobals();
        LuaTable t = new LuaTable();

        for (int i = 0; i < methodNames.length; i++) {
            t.set(methodNames[i], new LuaFunctionLoader(i, methodNames[i]));
        }

        env.set(indexName, t);
        return t;
    }

    final class LuaFunctionLoader extends VarArgFunction {

        public LuaFunctionLoader(int opcode, String name) {
            this.opcode = opcode;
            this.name = name;
        }

        @Override
        public Varargs invoke(Varargs args) {
            for (int i = 0; i < methodNames.length; i++) {
                if (i == opcode) {
                    try {
                        return (Varargs) methods[i].invoke(null, args);
                    } catch (Exception e) {
                        Log.severe("invocation exception");
                        Log.severe(e.getMessage());
                    }
                    break;
                }
            }
            return NONE;
        }
    }
}
