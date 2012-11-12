package luaforge.core.lua.libs;

import java.util.HashMap;
import luaforge.core.api.LuaMethod;
import net.minecraft.src.Block;
import net.minecraft.src.ItemStack;
import org.luaj.vm2.LuaError;
import org.luaj.vm2.LuaValue;
import org.luaj.vm2.Varargs;

public class CraftingHandler {

    public static HashMap<ItemStack,Object[]> shapelessRecipes = new HashMap<ItemStack,Object[]>();
    
    @LuaMethod(name = "craftingHandler")
    public static Varargs addShapelessRecipe(Varargs args) { // TODO: Document on the wiki
        if(args.narg() < 2) {
            throw new LuaError("Requires at least 2 paramters");
        }
        ItemStack[] recipes = new ItemStack[args.narg() - 1];
        for(int i=2; i <= args.narg(); i++) {
            recipes[i - 2] = new ItemStack(Block.blocksList[args.arg(i).checkint()], 1);
        }
        shapelessRecipes.put(new ItemStack(Block.blocksList[args.arg1().checkint()], 1), recipes);
        return LuaValue.NONE;
    }
    
    
    
}
