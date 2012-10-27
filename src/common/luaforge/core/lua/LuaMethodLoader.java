package luaforge.core.lua;

import java.lang.reflect.Method;
import luaforge.core.Log;
import org.luaj.vm2.Globals;
import org.luaj.vm2.Varargs;
import org.luaj.vm2.lib.OneArgFunction;
import org.luaj.vm2.lib.VarArgFunction;

public abstract class LuaMethodLoader extends OneArgFunction {

    public Globals globals;
    protected Method[] methods;

    final class LuaFunctionLoader extends VarArgFunction {

        public LuaFunctionLoader(int opcode, String name) {
            this.opcode = opcode;
            this.name = name;
        }

        @Override
        public Varargs invoke(Varargs args) {
            for (int i = 0; i < methods.length; i++) {
                if (i == opcode) {
                    try {
                        return (Varargs) methods[i].invoke(null, args);
                    } catch (Exception e) {
                        Log.severe("Invocation exception");
                        Log.severe(e.getMessage());
                    }
                    break;
                }
            }
            return NONE;
        }
    }
}
